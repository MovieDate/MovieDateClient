package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/24.
 */

public class UserUpdatePhoneActivity extends Activity {
    private String userDataStr;
    private User userInfo;

    private String account;
    private String phone;
    private String id;
    Gson gson = new Gson();

    private TextView tv_account;
    private EditText et_phone;
    private Button bt_updatephone_updateph;
    private ImageView updatephone_back;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_updatephone);
        initView();
        initData();
    }

    public void initView(){

        userDataStr = SharedPrefsUtil.getValue(UserUpdatePhoneActivity.this, APPConfig.USERDATA,"");
        Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);
        account = userInfo.getPhone();
        //初始化控件
        tv_account =(TextView)this.findViewById(R.id.updatephone_tv_account);
        et_phone=(EditText)this.findViewById(R.id.updatephone_et_phone);
        bt_updatephone_updateph=(Button)this.findViewById(R.id.bt_updatephone_updateph);
        updatephone_back=(ImageView)this.findViewById(R.id.image_updatephone_back);

        tv_account.setText("原手机号："+account);


        updatephone_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(UserUpdatePhoneActivity.this,MainActivity.class );
                startActivity(intent);
                finish();
            }
        });




    }
    public void initData(){
        bt_updatephone_updateph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取账号 密码
                id= ""+userInfo.getId();
                phone= et_phone.getText().toString().trim();
                if (phone.equals("")){
                    Toast.makeText(UserUpdatePhoneActivity.this, "不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    if (phone.equals(account)) {
                        Toast.makeText(UserUpdatePhoneActivity.this, "新号码和旧号码相同，请重试", Toast.LENGTH_SHORT).show();
                    }else {
                        //运用okhttp框架 子线程获取后台数据
                        final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
                        OkHttpUtils.Param userIdParam = new OkHttpUtils.Param("id", id);
                        OkHttpUtils.Param phoneParam = new OkHttpUtils.Param("phone", phone);
                        list.add(userIdParam);
                        list.add(phoneParam);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //post方式连接  url
                                OkHttpUtils.post(APPConfig.updatePhone, new OkHttpUtils.ResultCallback() {
                                    @Override
                                    public void onSuccess(Object response) {
                                        Message message = new Message();
                                        message.what = 0;
                                        message.obj = response;
                                        handler.sendMessage(message);
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(UserUpdatePhoneActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                                    }
                                }, list);
                            }
                        }).start();
                    }

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
                    if (result.equals("update_success")){
                        Toast.makeText(UserUpdatePhoneActivity.this,"手机号码更换成功,请重新登录！",Toast.LENGTH_SHORT).show();
                        SharedPrefsUtil.putValue(UserUpdatePhoneActivity.this, APPConfig.ACCOUNT, phone);
                        userInfo.setPhone(phone);
                        SharedPrefsUtil.putValue(UserUpdatePhoneActivity.this, APPConfig.USERDATA, gson.toJson(userInfo));
                        Intent intent=new Intent();
                        intent.setClass(UserUpdatePhoneActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(UserUpdatePhoneActivity.this,"手机号码更换失败，请重试！",Toast.LENGTH_SHORT).show();

                    }
                    break;
            }
        }

    };


}
