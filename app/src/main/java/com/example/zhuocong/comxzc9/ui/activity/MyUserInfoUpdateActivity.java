package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.DateTimeDialogUtils;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/23.
 */

public class MyUserInfoUpdateActivity extends FragmentActivity implements View.OnClickListener, DateTimeDialogUtils.MyOnDateSetListener{
    private String userDataStr;
    private User userInfo;
    private String userDataStr2;
    private User userInfo2;

    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:00");
    private DateTimeDialogUtils dateTimeDialog;

    private LinearLayout ll_one;
    private LinearLayout ll_two;
    private ImageView img_back;
    private TextView et_updateinfo;

    private TextView tv_account;
    private TextView tv_name;
    private EditText et_nickname;
    private EditText et_age;
    private TextView tv_gender;
    private TextView tv_phone;
    private EditText et_address;
    private EditText et_signature;
    private TextView et_birthday;
    private EditText et_xingZuo;
    private EditText et_height;
    private EditText et_weight;
    private EditText et_job;
    private EditText et_habit;
    private String id;
    private String name;
    private String nickname;
    private String age;
    private String sgender;
    private String gender;
    private String phone;
    private String address;
    private String signature;
    private String birthday;
    private String xingZuo;
    private String height;
    private String weight;
    private String job;
    private String habit;

    //Gson解析数据
     Gson gson=new Gson();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo_update);
        dateTimeDialog = new DateTimeDialogUtils(this, null, this);
        initView();
        initData();
        initMotion();


    }

    private void initView(){
        userDataStr = SharedPrefsUtil.getValue(MyUserInfoUpdateActivity.this, APPConfig.USERDATA,"");
       // Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);

        img_back = (ImageView) this.findViewById(R.id.amyinfo_img_backupa);
        et_updateinfo=(TextView)this.findViewById(R.id.updatemyinfo_et_updateinfo);

        tv_account = (TextView) this.findViewById(R.id.updatemyinfo_tv_accountupa);
        tv_name = (TextView) this.findViewById(R.id.updatemyinfo_tv_name);
        et_nickname = (EditText) this.findViewById(R.id.updatemyinfo_et_nickname);
        et_age=(EditText) this.findViewById(R.id.updatemyinfo_et_age);
        tv_gender = (TextView) this.findViewById(R.id.updatemyinfo_tv_gender);
        tv_phone = (TextView) this.findViewById(R.id.updatemyinfo_tv_phone);
        et_address=(EditText) this.findViewById(R.id.updatemyinfo_et_address);
        et_signature = (EditText) this.findViewById(R.id.updatemyinfo_et_signature);
        et_birthday= (TextView) this.findViewById(R.id.updatemyinfo_et_birthday);
        et_xingZuo= (EditText) this.findViewById(R.id.updatemyinfo_et_xingZuo);
        et_height= (EditText) this.findViewById(R.id.updatemyinfo_et_height);
        et_weight= (EditText) this.findViewById(R.id.updatemyinfo_et_weight);
        et_job= (EditText) this.findViewById(R.id.updatemyinfo_et_job);
        et_habit =(EditText)this.findViewById(R.id.updatemyinfo_et_habit);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent intent = new Intent();
                intent.setClass(MyUserInfoUpdateActivity.this,MyUserInfoActivity.class );
                startActivity(intent);*/
                finish();
            }
        });

        et_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MyUserInfoUpdateActivity.this,"点击了！",Toast.LENGTH_LONG).show();
                showAll();
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
                        Message message2 = new Message();
                        message2.what = 1;
                        message2.obj = response;
//                        userDataStr = response.toString();
                        Log.d("testRun", "后台返回给我们的数据userDataStr=="+response.toString());
                        handler.sendMessage(message2);
                    }

                    @Override
                    public void onFailure(Exception e) {//url请求失败
                        Log.d("testRun", "服务器连接失败，请重试！{------");
                        Toast.makeText(MyUserInfoUpdateActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                }, list);
            }
        }).start();

    }


    public void initMotion(){
        //设置监听
        et_updateinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取数据
                name=tv_name.getText().toString().trim();
                nickname=et_nickname.getText().toString().trim();
                age=et_age.getText().toString().trim();
                sgender=tv_gender.getText().toString().trim();
                if(sgender=="男")
                {
                    gender="0";
                }else {
                    gender="1";
                }
                phone=tv_phone.getText().toString().trim();
                address=et_address.getText().toString().trim();
                signature=et_signature.getText().toString().trim();
                Log.d("testRun","birthday22="+birthday);
                if (birthday==null){
                    birthday=et_birthday.getText().toString().trim();
                    Log.d("testRun","birthday33="+birthday);
                }
                xingZuo=et_xingZuo.getText().toString().trim();
                height=et_height.getText().toString().trim();
                weight=et_weight.getText().toString().trim();
                job=et_job.getText().toString().trim();
                habit=et_habit.getText().toString().trim();

                Log.e("testRun", " 登陆  loginActivity  ---  name:" + name+"nickname:" +nickname
                        +"age:" +age+"gender:" +gender+"phone:" +phone+"address:" +address+"signature:" +signature+"birthday:" +birthday
                        +"xingZuo:" +xingZuo+"height:" +height+"weight:" +weight+"job:" +job+"habit:" +habit);
                final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
                OkHttpUtils.Param nameParam = new OkHttpUtils.Param("name", name);
                OkHttpUtils.Param nicknameParam = new OkHttpUtils.Param("nickname", nickname);
                OkHttpUtils.Param ageParam =new OkHttpUtils.Param("age",age);
                OkHttpUtils.Param phoneParam =new OkHttpUtils.Param("phone",phone);
                OkHttpUtils.Param addressParam = new OkHttpUtils.Param("address", address);
                OkHttpUtils.Param signatureParam = new OkHttpUtils.Param("signature", signature);
                OkHttpUtils.Param birthdayParam =new OkHttpUtils.Param("birthday",birthday);
                OkHttpUtils.Param xingZuoParam =new OkHttpUtils.Param("xingZuo",xingZuo);
                OkHttpUtils.Param heightParam = new OkHttpUtils.Param("height", height);
                OkHttpUtils.Param weightParam = new OkHttpUtils.Param("weight", weight);
                OkHttpUtils.Param genderParam =new OkHttpUtils.Param("gender",gender);
                OkHttpUtils.Param jobParam =new OkHttpUtils.Param("job",job);
                OkHttpUtils.Param habitParam = new OkHttpUtils.Param("habit",habit);

                list.add(nameParam);
                list.add(nicknameParam);
                list.add(ageParam);
                list.add(genderParam);
                list.add(phoneParam);
                list.add(addressParam);
                list.add(signatureParam);
                list.add(birthdayParam);
                list.add(xingZuoParam);
                list.add(heightParam);
                list.add(weightParam);
                list.add(jobParam);
                list.add(habitParam);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //post方式连接  url
                        OkHttpUtils.post(APPConfig.modify, new OkHttpUtils.ResultCallback(){
                            @Override
                            public void onSuccess(Object response) {
                                Message message = new Message();
                                message.what = 0;
                                message.obj = response;
                                String result = response.toString();
                                Log.e("testRun", " 登陆  loginActivity  ---  account:" + result );
                                if (result.equals("add_success")) {
                                    // Toast.LENGTH_SHORT停留时间LENGTH_LONG                                       //
                                    Toast.makeText(MyUserInfoUpdateActivity.this,"修改成功！", Toast.LENGTH_LONG).show();
                                    handler.sendMessage(message);

                                }
                                else{
                                    Toast.makeText(MyUserInfoUpdateActivity.this,"填写错误，修改失败！",Toast.LENGTH_SHORT).show();

                                }

                            }

                            @Override
                            public void onFailure(Exception e) {

                                Toast.makeText(MyUserInfoUpdateActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                            }

                        },list);
                    }
                }).start();
            }
        });
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    //Gson解析数据
                    //Gson gson=new Gson();
                    userDataStr2 = msg.obj.toString();
                    if (userDataStr2.equals("nodata")) {
                        Toast.makeText(MyUserInfoUpdateActivity.this, "没有找到数据，请重试！", Toast.LENGTH_SHORT).show();
                    } else {//如果后台成功返回User数据，则显示出来，这里我只显示一部分，其他还要补充进来
                        userInfo2=gson.fromJson(userDataStr2,User.class);
                        tv_account.setText("账号："+userInfo2.getPhone());
                        tv_name.setText(userInfo2.getName());
                        et_nickname.setText(userInfo2.getNickname());
                        et_age.setText(userInfo2.getAge()+"");
                        if (userInfo2.getGender() == 0) {
                            tv_gender.setText("男");
                        }else {
                            tv_gender.setText("女");
                        }
                        et_birthday.setText(userInfo2.getBirthday());
                        et_xingZuo.setText(userInfo2.getXingZuo());
                        et_height.setText(userInfo2.getHeight());
                        et_weight.setText(userInfo2.getWeight());
                        tv_phone.setText(userInfo2.getPhone());
                        et_address.setText(userInfo2.getAddress());
                        et_job.setText(userInfo2.getJob());
                        et_signature.setText(userInfo2.getSignature());
                        et_habit.setText(userInfo2.getHabit());
                    }
                    break;
                case 0:
                    String result = (String)msg.obj;
                    if (result.equals("add_success") ){
                        Toast.makeText(MyUserInfoUpdateActivity.this,"修改成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(MyUserInfoUpdateActivity.this, MyUserInfoActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(MyUserInfoUpdateActivity.this,"填写错误2，修改失败！",Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }

    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.updatemyinfo_et_birthday:

                showAll();
                break;

        }

    }

    private void showAll() {
        dateTimeDialog.hideOrShow();
    }

    @Override
    public void onDateSet(Date date) {
        birthday= mFormatter.format(date);
        et_birthday.setText(birthday);
        Log.d("testRun","birthday="+birthday);
    }
}
