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

public class UserUpdatePswActivity extends Activity {
    private Button btUpdatePsw;
    private TextView tv_account;
    private EditText etOldPsw;
    private EditText etNewPsw;
    private ImageView ivBack;
    private String account;
    private String oldPsw;
    private String newPsw;
    private String userId;
    private String userDataStr;
    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_updatepsw);
        initView();
        initDatas();

    }
    private void initView() {
        userDataStr= SharedPrefsUtil.getValue(UserUpdatePswActivity.this, APPConfig.USERDATA, "");
        Gson gson=new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);
        account = userInfo.getPhone();

        etNewPsw= (EditText)this.findViewById(R.id.updatepsw_et__newpsd);
        etOldPsw= (EditText) this.findViewById(R.id.updatepsw_et__oldpsd);
        btUpdatePsw= (Button) this.findViewById(R.id.bt_updatepsw_pss);
        ivBack=(ImageView)this.findViewById(R.id.image_updatepsw_back);
        tv_account=(TextView)this.findViewById(R.id.updatepsw_tv_account);


        tv_account.setText("账号："+account);
    }

    private void initDatas() {

        btUpdatePsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取账号 密码
                oldPsw= etOldPsw.getText().toString().trim();
                newPsw= etNewPsw.getText().toString().trim();
                if (oldPsw.equals("")||newPsw.equals("")){
                    Toast.makeText(UserUpdatePswActivity.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                }else {
                    //运用okhttp框架 子线程获取后台数据
                    final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
                    OkHttpUtils.Param phoneParam = new OkHttpUtils.Param("phone", account);
                    OkHttpUtils.Param oldPswParam = new OkHttpUtils.Param("oldPassword", oldPsw);
                    OkHttpUtils.Param newPsdParam = new OkHttpUtils.Param("newPassword", newPsw);
                    list.add(phoneParam);
                    list.add(oldPswParam);
                    list.add(newPsdParam);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //post方式连接  url
                            OkHttpUtils.post(APPConfig.updatePassword, new OkHttpUtils.ResultCallback() {
                                @Override
                                public void onSuccess(Object response) {
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = response;
                                    handler.sendMessage(message);
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.d("testRun", "请求失败loginActivity----new Thread(new Runnable() {------");
                                    Toast.makeText(UserUpdatePswActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                                }
                            }, list);
                        }
                    }).start();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent=new Intent();
//                intent.setClass(UserUpdatePswActivity.this,MainActivity.class);
//                startActivity(intent);
                finish();
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
                    if (result.equals("updatePsw_success")){
                        Toast.makeText(UserUpdatePswActivity.this,"密码修改成功，请记住密码！",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(UserUpdatePswActivity.this,"密码修改失败，请重试！",Toast.LENGTH_SHORT).show();

                    }
                    break;
            }
        }

    };


}
