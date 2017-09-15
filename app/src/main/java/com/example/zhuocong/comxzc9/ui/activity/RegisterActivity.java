package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuocong on 2017/8/22.
 */

public class RegisterActivity extends Activity {

    // 弹出框
    private ProgressDialog mDialog;
    private EditText et_register_phone;
    private EditText et_register_psd;
    private EditText et_register_pswaffirm;
    private Button bt_register_reg;
    private EditText et_register_name;
    private RadioButton rb_register_gender0;
    private RadioButton rb_register_gender1;
    private ImageView register_img_back;
    private TextView register_id;

    private String account;
    private String psd;
    private String pswaffirm;
    private String gender;
    private String name;
    private Boolean isValid;
    private String id;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面
        setContentView(R.layout.activity_register);
        initView();


    }

    public void initView(){
        //初始化控件
        et_register_phone=(EditText)this.findViewById(R.id.et_register_phone);
        et_register_psd=(EditText)this.findViewById(R.id.et_register_psd);
        et_register_pswaffirm =(EditText)this.findViewById(R.id.et_register_pswaffirm);
        bt_register_reg=(Button)this.findViewById(R.id.bt_register_reg);
        et_register_name=(EditText)this.findViewById(R.id.et_register_name);
        rb_register_gender0=(RadioButton)this.findViewById(R.id.rb_register_gender0);
        rb_register_gender1=(RadioButton)this.findViewById(R.id.rb_register_gender1);
        register_img_back=(ImageView)this.findViewById(R.id.register_img_back);
        register_id=(TextView)this.findViewById(R.id.register_id);

        register_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bt_register_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
                initMotion();

            }
        });

    }

    //初始化监听
    public void initMotion(){
                //获取数据
                account=et_register_phone.getText().toString().trim();
                psd=et_register_psd.getText().toString().trim();
                pswaffirm=et_register_pswaffirm.getText().toString().trim();
                name=et_register_name.getText().toString().trim();
                if(rb_register_gender0.isChecked()){
                    gender="0";
                }
                if(rb_register_gender1.isChecked()){
                    gender="1";
                }
                //验证手机号码
                isPhoneNumberValid(account);
                if (account.equals("") || psd.equals("")||pswaffirm.equals("")||name.equals("")) {
                    Toast.makeText(RegisterActivity.this, "信息未完整，无法注册！", Toast.LENGTH_SHORT).show();
                }/*else if (!isValid.equals(true)){
                    Toast.makeText(RegisterActivity.this, "手机号码格式错误，请重新输入", Toast.LENGTH_SHORT).show();
                }*/ else if ( !psd.equals(pswaffirm) ){
                    Toast.makeText(RegisterActivity.this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("testRun", " 登陆  loginActivity  ---  account:" + account + " psd:" + psd+"pswaffirm"+pswaffirm);
                    //运用okhttp框架 子线程获取后台数据
                    final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
                    OkHttpUtils.Param accountParam = new OkHttpUtils.Param("phone", account);
                    OkHttpUtils.Param psdParam = new OkHttpUtils.Param("password", psd);
                    OkHttpUtils.Param nameParam =new OkHttpUtils.Param("name",name);
                    OkHttpUtils.Param genderParam =new OkHttpUtils.Param("gender",gender);
                    list.add(accountParam);
                    list.add(psdParam);
                    list.add(nameParam);
                    list.add(genderParam);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //post方式连接  url
                            OkHttpUtils.post(APPConfig.register, new OkHttpUtils.ResultCallback(){

                                @Override
                                public void onSuccess(Object response) {
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = response;
                                    String result = response.toString();
                                    if (!result.equals("add_failed")) {
                                        // Toast.LENGTH_SHORT停留时间LENGTH_LONG                                       //
                                        /*Toast.makeText(RegisterActivity.this, "注册成功1！", Toast.LENGTH_LONG).show();*/
                                        Intent intent = new Intent();
                                        intent.setClass(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        handler.sendMessage(message);
                                    } else {
                                        /*Toast.makeText(RegisterActivity.this, "填写错误，注册失败！", Toast.LENGTH_SHORT).show();*/
                                    }
                                }
                                @Override
                                public void onFailure(Exception e) {
                                    Log.d("testRun", "请求失败loginActivity----new Thread(new Runnable() {------");
                                    Toast.makeText(RegisterActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                                }

                            },list);

                        }
                    }).start();
                }

    }

    /**
     * 注册方法
     */
    private void signUp() {
        // 注册是耗时过程，所以要显示一个dialog来提示下用户
        mDialog = new ProgressDialog(this);
        mDialog.setMessage("注册中，请稍后...");
        mDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String mid = et_register_phone.getText().toString().trim();;
                    String password = et_register_psd.getText().toString().trim();
                    Log.d("testRun","password="+psd);
                    Log.d("testRun","mid="+mid);
                    EMClient.getInstance().createAccount(mid, password);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!RegisterActivity.this.isFinishing()) {
                                mDialog.dismiss();
                            }
                            Toast.makeText(RegisterActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!RegisterActivity.this.isFinishing()) {
                                mDialog.dismiss();
                            }
                            /**
                             * 关于错误码可以参考官方api详细说明
                             * http://www.easemob.com/apidoc/android/chat3.0/classcom_1_1hyphenate_1_1_e_m_error.html
                             */
                            int errorCode = e.getErrorCode();
                            String message = e.getMessage();
                            Log.d("lzan13", String.format("sign up - errorCode:%d, errorMsg:%s", errorCode, e.getMessage()));
                            switch (errorCode) {
                                // 网络错误
                                case EMError.NETWORK_ERROR:
                                    Toast.makeText(RegisterActivity.this, "网络错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                // 用户已存在
                                case EMError.USER_ALREADY_EXIST:
                                    Toast.makeText(RegisterActivity.this, "用户已存在 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                // 参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册
                                case EMError.USER_ILLEGAL_ARGUMENT:
                                    Toast.makeText(RegisterActivity.this, "参数不合法，一般情况是username 使用了uuid导致，不能使用uuid注册 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                // 服务器未知错误
                                case EMError.SERVER_UNKNOWN_ERROR:
                                    Toast.makeText(RegisterActivity.this, "服务器未知错误 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                case EMError.USER_REG_FAILED:
                                    Toast.makeText(RegisterActivity.this, "账户注册失败 code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                                default:
                                    Toast.makeText(RegisterActivity.this, "ml_sign_up_failed code: " + errorCode + ", message:" + message, Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    String result = (String)msg.obj;
                    Gson gson=new Gson();
                    User userInof=gson.fromJson(result,User.class);
                    id= String.valueOf(userInof.getId());
                    register_id.setText(id);
                    Log.d("testRun","id="+id);
                    if (!result.equals("add_faild") ){
                        Toast.makeText(RegisterActivity.this,"注册成功3！", Toast.LENGTH_LONG).show();

                    }
                    else{
                        Toast.makeText(RegisterActivity.this,"填写错误，注册失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

            }

    };


    public static boolean isPhoneNumberValid(String phoneNumber) {
        boolean isValid = false;
        String expression = "((^(13|15|18)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNumber;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches() ) {
            isValid = true;
        }
        return isValid;
    }


}
