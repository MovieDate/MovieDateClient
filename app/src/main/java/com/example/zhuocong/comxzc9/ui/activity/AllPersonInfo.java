package com.example.zhuocong.comxzc9.ui.activity;

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

/**
 * Created by zhuocong on 2017/8/30.
 */

public class AllPersonInfo extends Activity{
    private String userDataStr;
    private User userInfo;
    private String userData;
    private User userinfo;

    private LinearLayout ll_one;
    private LinearLayout ll_two;
    private ImageView img_back;
    private TextView allperson_tv_add;

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

    private String id;
    private String myId;
    private String phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allpersoninfo);
        initView();
        initData();
        initupa();

    }

    private void initView(){
        //读取数据
        userDataStr = SharedPrefsUtil.getValue(AllPersonInfo.this,APPConfig.USERDATA,"");
        Gson gson=new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);
        Log.d("testRun","testId="+userInfo.getId());
        img_back = (ImageView) this.findViewById(R.id.allperson_img_back);
        allperson_tv_add = (TextView) this.findViewById(R.id.allperson_tv_add);

        tv_account = (TextView) this.findViewById(R.id.allperson_tv_account);
        tv_name = (TextView) this.findViewById(R.id.allperson_tv_name);
        tv_nickname = (TextView) this.findViewById(R.id.allperson_tv_nickname);
        tv_age=(TextView) this.findViewById(R.id.allperson_tv_age);
        tv_gender = (TextView) this.findViewById(R.id.allperson_tv_gender);
        tv_phone = (TextView) this.findViewById(R.id.allperson_tv_phone);
        tv_address=(TextView) this.findViewById(R.id.allperson_tv_address);
        tv_signature = (TextView) this.findViewById(R.id.allperson_tv_signature);
        tv_birthday= (TextView) this.findViewById(R.id.allperson_tv_birthday);
        tv_xingZuo= (TextView) this.findViewById(R.id.allperson_tv_xingZuo);
        tv_height= (TextView) this.findViewById(R.id.allperson_tv_height);
        tv_weight= (TextView) this.findViewById(R.id.allperson_tv_weight);
        tv_job= (TextView) this.findViewById(R.id.allperson_tv_job);
        tv_habit=(TextView)this.findViewById(R.id.allperson_tv_habit);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        allperson_tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(AllPersonInfo.this,AddConfirmActivity.class);
                intent.putExtra("friendId",id);
                intent.putExtra("myId",myId);
                intent.putExtra("phone",phone);
                startActivity(intent);
                /*addfriend();*/
            }
        });

    }

    private void initData(){
        String id=this.getIntent().getStringExtra("id");
        Log.d("testRun","postPersonId="+id);
        //添加网络请求中需要的参数
        final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param idParam = new OkHttpUtils.Param("id", id);
        list.add(idParam);

        //网络请求比较耗时，在线程中请求网络，但线程中不能更新UI，需要传送数据到主线程中更新UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                //post方式连接  url
                OkHttpUtils.post(APPConfig.findUserById, new OkHttpUtils.ResultCallback() {
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
                        Toast.makeText(AllPersonInfo.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                }, list);
            }
        }).start();

    }

    private void initupa(){
        id=this.getIntent().getStringExtra("id");
        Log.d("testRun","postPersonId="+id);
        myId=String.valueOf(userInfo.getId());
        Log.d("TestRun","myId="+userInfo.getId());
        final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param myIdParam = new OkHttpUtils.Param("myId", myId);
        OkHttpUtils.Param idParam= new OkHttpUtils.Param("friendId",id);
        list.add(myIdParam);
        list.add(idParam);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //post方式
                OkHttpUtils.post(APPConfig.findFriendByMyIdFriendId, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        String result =response.toString();
                        Log.d("TestRun","result2=="+result);
                        if (result.equals("nodata")){
                            allperson_tv_add.setVisibility(View.VISIBLE);
                        }else {
                            allperson_tv_add.setVisibility( View.INVISIBLE);
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("postDateStr","服务器连接失败，请重试！");
                        Toast.makeText(AllPersonInfo.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                },list);
            }
        }).start();
    }

    private  void addfriend(){
        String id=this.getIntent().getStringExtra("id");
        Log.d("testRun","postPersonId="+id);
        String myId=String.valueOf(userInfo.getId());
        Log.d("TestRun","myId="+myId);
        final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param myIdParam = new OkHttpUtils.Param("myId", myId);
        OkHttpUtils.Param idParam= new OkHttpUtils.Param("friendId",id);
        list.add(myIdParam);
        list.add(idParam);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //post方式
                OkHttpUtils.post(APPConfig.addFriendByMyId, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        String result =response.toString();
                        Log.d("TestRun","result3=="+result);
                        if (result.equals("add_success")){
                            Toast.makeText(AllPersonInfo.this,"添加成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(AllPersonInfo.this,"添加失败！",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("postDateStr","服务器连接失败，请重试！");
                        Toast.makeText(AllPersonInfo.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                },list);
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
                    userData= msg.obj.toString();
                    if (userData.equals("nodata")) {
                        Toast.makeText(AllPersonInfo.this, "没有找到数据，请重试！", Toast.LENGTH_SHORT).show();
                    } else {//如果后台成功返回User数据，则显示出来，这里我只显示一部分，其他还要补充进来
                        userinfo=gson.fromJson(userData,User.class);
                        phone=userinfo.getPhone();
                        tv_account.setText("账号："+userinfo.getPhone());
                        tv_name.setText(userinfo.getName());
                        tv_nickname.setText(userinfo.getNickname());
                        tv_age.setText(userinfo.getAge()+"");
                        if (userinfo.getGender() == 0) {
                            tv_gender.setText("男");
                        }else {
                            tv_gender.setText("女");
                        }
                        tv_birthday.setText(userinfo.getBirthday());
                        tv_xingZuo.setText(userinfo.getXingZuo());
                        tv_height.setText(userinfo.getHeight());
                        tv_weight.setText(userinfo.getWeight());
                        tv_phone.setText(userinfo.getPhone());
                        tv_address.setText(userinfo.getAddress());
                        tv_job.setText(userinfo.getJob());
                        tv_signature.setText(userinfo.getSignature());
                        tv_habit.setText(userinfo.getHabit());
                    }
                    break;
            }
        }

    };
}
