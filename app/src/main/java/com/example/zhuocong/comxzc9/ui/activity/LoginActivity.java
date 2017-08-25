package com.example.zhuocong.comxzc9.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.session.PlaybackState;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.ui.fragment.UserFragment;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/21.
 */

public class LoginActivity extends Activity {

    private String userDataStr;
    private User userInfo;

    private EditText et_login_account;
    private EditText et_login_psd;
    private Button bt_login_login;
    private TextView tv_login_find;
    private TextView tv_login_register;

    private String account;
    private String name;
    private String psd;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面
        setContentView(R.layout.activity_login);
        initView();
        initMotion();

    }

    public void initView() {

        //初始化控件
        et_login_account = (EditText) this.findViewById(R.id.et_login_account);
        et_login_psd = (EditText) this.findViewById(R.id.et_login_psd);
        bt_login_login = (Button) this.findViewById(R.id.bt_login_login);
        tv_login_find = (TextView) this.findViewById(R.id.tv_login_find);
        tv_login_register = (TextView) this.findViewById(R.id.tv_login_register);

    }

    public void saveUser(String phone) {
        //运用okhttp框架 子线程获取后台数据
        final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param phoneParam = new OkHttpUtils.Param("phone", phone);
        list.add(phoneParam);

        new Thread(new Runnable() {
            @Override
            public void run() {

                OkHttpUtils.post(APPConfig.findUserByPhone, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        //读取数据
                        String userData = response.toString();
                        Log.d("testRun", "LoginActivity user信息缓存成功 " + userData);
                        //将userData的json串直接缓存到本地
                        SharedPrefsUtil.putValue(LoginActivity.this, APPConfig.USERDATA, userData);
                        userDataStr = SharedPrefsUtil.getValue(LoginActivity.this,APPConfig.USERDATA,"");

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("testRun", "网络请求失败------");
                        Toast.makeText(LoginActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                }, list);
            }
        }).start();
    }


    /**
     * 初始化监听等
     */
    private void initMotion() {
        bt_login_login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //获取账号 密码
                account = et_login_account.getText().toString().trim();
                psd = et_login_psd.getText().toString().trim();
                if (account.equals("") || psd.equals("")) {
                    Toast.makeText(LoginActivity.this, "账号密码不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("testRun", " 登陆  loginActivity  ---  account:" + account + " psd:" + psd);
                    //运用okhttp框架 子线程获取后台数据
                    final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
                    OkHttpUtils.Param accountParam = new OkHttpUtils.Param("phone", account);
                    OkHttpUtils.Param psdParam = new OkHttpUtils.Param("password", psd);
                    list.add(accountParam);
                    list.add(psdParam);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //post方式连接  url
                            OkHttpUtils.post(APPConfig.login, new OkHttpUtils.ResultCallback() {
                                @Override
                                public void onSuccess(Object response) {
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = response;
                                    String result = response.toString();
                                    if (result.equals("login_success")) {
                                        Toast.makeText(LoginActivity.this,"登陆成功！", Toast.LENGTH_SHORT).show();
                                        handler.sendMessage(message);

                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this,"账号或密码错误，登陆失败！",Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Exception e) {
                                    Log.d("testRun", "请求失败loginActivity----new Thread(new Runnable() {------");
                                    Toast.makeText(LoginActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            }, list);

                        }
                    }).start();
                }
            }
        });

        tv_login_register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //跳转页面
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
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
                    if (result.equals("login_success") ) {
                        //保存登录状态和当前用户信息
                        SharedPrefsUtil.putValue(LoginActivity.this, APPConfig.IS_LOGIN,true);
                        SharedPrefsUtil.putValue(LoginActivity.this, APPConfig.ACCOUNT, account);
                        SharedPrefsUtil.putValue(LoginActivity.this, APPConfig.PSW, psd);
                        saveUser(account);
                        //登录成功
                        Toast.makeText(LoginActivity.this,"登陆成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(LoginActivity.this,"账号或密码错误，登陆失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }


    };



}
