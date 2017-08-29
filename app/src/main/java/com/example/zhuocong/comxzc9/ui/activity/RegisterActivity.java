package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhuocong on 2017/8/22.
 */

public class RegisterActivity extends Activity {
    private EditText et_register_phone;
    private EditText et_register_psd;
    private EditText et_register_pswaffirm;
    private Button bt_register_reg;
    private EditText et_register_name;
    private RadioButton rb_register_gender0;
    private RadioButton rb_register_gender1;
    private ImageView register_img_back;

    private String account;
    private String psd;
    private String pswaffirm;
    private String gender;
    private String name;
    private Boolean isValid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面
        setContentView(R.layout.activity_register);
        initView();
        initMotion();

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

        register_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    //c初始化监听
    public void initMotion(){
        bt_register_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                                    if (result.equals("add_success")) {
                                        // Toast.LENGTH_SHORT停留时间LENGTH_LONG                                       //
                                        Toast.makeText(RegisterActivity.this,"注册成功！", Toast.LENGTH_SHORT).show();
                                        handler.sendMessage(message);

                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,"填写错误，注册失败！",Toast.LENGTH_SHORT).show();
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
        });


    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String result = (String)msg.obj;
                    if (result.equals("add_success") ){
                        Toast.makeText(RegisterActivity.this,"注册成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

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
