package com.example.zhuocong.comxzc9.ui.activity;
/*未能实现动态刷新，待解决
* 生日栏还可以继续优化
* */
import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class MyUserInfoActivity extends Activity{

    private String userDataStr;
    private User userInfo;

    private LinearLayout ll_one;
    private LinearLayout ll_two;
    private ImageView img_back;
    private TextView tv_updateinfo;

    private TextView tv_account;
    private TextView tv_name;
    private TextView tv_nickname;
    private TextView tv_age;
    private TextView tv_gender;
    private TextView tv_phone;
    private TextView tv_address;
    private TextView tv_signature;
    private TextView tv_birthday;
    private TextView tv_xingZuo;
    private TextView tv_height;
    private TextView tv_weight;
    private TextView tv_job;
    private TextView tv_habit;

    public void refresh() {
        onCreate(null);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        initView();
        initData();
    }

    private void initView(){
        userDataStr = SharedPrefsUtil.getValue(MyUserInfoActivity.this,APPConfig.USERDATA,"");
        Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);

        img_back = (ImageView) this.findViewById(R.id.amyinfo_img_back);
        tv_updateinfo = (TextView) this.findViewById(R.id.amyinfo_tv_updateinfo);

        tv_account = (TextView) this.findViewById(R.id.amyinfo_tv_account);
        tv_name = (TextView) this.findViewById(R.id.amyinfo_tv_name);
        tv_nickname = (TextView) this.findViewById(R.id.amyinfo_tv_nickname);
        tv_age=(TextView) this.findViewById(R.id.amyinfo_tv_age);
        tv_gender = (TextView) this.findViewById(R.id.amyinfo_tv_gender);
        tv_phone = (TextView) this.findViewById(R.id.amyinfo_tv_phone);
        tv_address=(TextView) this.findViewById(R.id.amyinfo_tv_address);
        tv_signature = (TextView) this.findViewById(R.id.amyinfo_tv_signature);
        tv_birthday= (TextView) this.findViewById(R.id.amyinfo_tv_birthday);
        tv_xingZuo= (TextView) this.findViewById(R.id.amyinfo_tv_xingZuo);
        tv_height= (TextView) this.findViewById(R.id.amyinfo_tv_height);
        tv_weight= (TextView) this.findViewById(R.id.amyinfo_tv_weight);
        tv_job= (TextView) this.findViewById(R.id.amyinfo_tv_job);
        tv_habit=(TextView)this.findViewById(R.id.amyinfo_tv_habit);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setClass(MyUserInfoActivity.this,MainActivity.class );
                startActivity(intent);*/
                finish();
            }
        });
        tv_updateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MyUserInfoActivity.this,MyUserInfoUpdateActivity.class );
                startActivity(intent);
//                Toast.makeText(MyUserInfoActivity.this, "更新待完成", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initData(){
        String phone=userInfo.getPhone();
        //添加网络请求中需要的参数，查找个人信息需要的参数是phone
        final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param accountParam = new OkHttpUtils.Param("phone", phone);
        list.add(accountParam);

        //网络请求比较耗时，在线程中请求网络，但线程中不能更新UI，需要传送数据到主线程中更新UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                //post方式连接  url
                OkHttpUtils.post(APPConfig.findUserByPhone, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {//url请求成功返回的数据
                        Message message = new Message();
                        message.what = 0;
                        message.obj = response;
//                        userDataStr = response.toString();
                        Log.d("testRun", "后台返回给我们的数据userDataStr=="+response.toString());
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(Exception e) {//url请求失败
                        Log.d("testRun", "服务器连接失败，请重试！{------");
                        Toast.makeText(MyUserInfoActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                }, list);
            }
        }).start();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                //Gson解析数据
                    Gson gson=new Gson();
                    //userDataStr = msg.obj.toString();
                    if (userDataStr.equals("nodata")) {
                        Toast.makeText(MyUserInfoActivity.this, "没有找到数据，请重试！", Toast.LENGTH_SHORT).show();
                    } else {//如果后台成功返回User数据，则显示出来，这里我只显示一部分，其他还要补充进来
                        userInfo=gson.fromJson(userDataStr,User.class);
                        tv_account.setText("账号："+userInfo.getPhone());
                        tv_name.setText(userInfo.getName());
                        tv_nickname.setText(userInfo.getNickname());
                        tv_age.setText(userInfo.getAge()+"");
                        if (userInfo.getGender() == 0) {
                            tv_gender.setText("男");
                        }else {
                            tv_gender.setText("女");
                        }
                        tv_birthday.setText(userInfo.getBirthday());
                        tv_xingZuo.setText(userInfo.getXingZuo());
                        tv_height.setText(userInfo.getHeight());
                        tv_weight.setText(userInfo.getWeight());
                        tv_phone.setText(userInfo.getPhone());
                        tv_address.setText(userInfo.getAddress());
                        tv_job.setText(userInfo.getJob());
                        tv_signature.setText(userInfo.getSignature());
                        tv_habit.setText(userInfo.getHabit());
                    }
                    break;
            }
        }

    };

}
