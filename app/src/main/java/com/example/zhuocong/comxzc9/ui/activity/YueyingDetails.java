package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.Post;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/28.
 */

public class YueyingDetails extends Activity {

    private String postDataStr;
    private Post postInfo;
    private Post postInfo2;
    private User userInfo2;
    private String userDataStr;
    private User userInfo;

    private ImageView details_img_back;
    private LinearLayout details_ll_myuser;
    private TextView details_tv_nickname;
    private TextView details_tv_posttime;
    private TextView details_tv_moviename;
    private TextView details_tv_site;
    private TextView details_tv_movietime;
    private TextView details_tv_sex;
    private TextView details_tv_movietype;
    private TextView details_tv_details;
    private TextView details_tv_apply;
    private TextView details_tv_review;
    private TextView details_tv_collect;
    private ListView reviewlistview;
    private TextView review_details;
    private TextView details_tv_check;

    private String postId;
    private String collecterId;
    private String collectTime;
    private String postPersonId;
    Gson gson= new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_yueying_details);
        initView();
        initData();
        initMotion();
    }

    public void initView(){
        //读取数据
        userDataStr = SharedPrefsUtil.getValue(YueyingDetails.this,APPConfig.USERDATA,"");
        userInfo=gson.fromJson(userDataStr,User.class);
        //初始化控件
        details_img_back=(ImageView)this.findViewById(R.id.details_img_back);
        details_ll_myuser=(LinearLayout)this.findViewById(R.id.details_ll_myuser);
        details_tv_nickname=(TextView)this.findViewById(R.id.details_tv_nickname);
        details_tv_posttime=(TextView)this.findViewById(R.id.details_tv_posttime) ;
        details_tv_moviename=(TextView)this.findViewById(R.id.details_tv_moviename);
        details_tv_site=(TextView)this.findViewById(R.id.details_tv_site);
        details_tv_movietime=(TextView)this.findViewById(R.id.details_tv_movietime);
        details_tv_sex=(TextView)this.findViewById(R.id.details_tv_sex);
        details_tv_movietype=(TextView)this.findViewById(R.id.details_tv_movietype);
        details_tv_details=(TextView)this.findViewById(R.id.details_tv_details);
        details_tv_apply=(TextView)this.findViewById(R.id.details_tv_apply);
        details_tv_review=(TextView)this.findViewById(R.id.details_tv_review);
        details_tv_collect=(TextView)this.findViewById(R.id.details_tv_collect);
        reviewlistview=(ListView)this.findViewById(R.id.reviewlistview);
        review_details=(TextView)this.findViewById(R.id.review_details);
        details_tv_check=(TextView)this.findViewById(R.id.details_tv_check);

        //设置点击事件
        details_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        details_ll_myuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(YueyingDetails.this,AllPersonInfo.class);
                postDataStr = SharedPrefsUtil.getValue(YueyingDetails.this,APPConfig.POSTDATA,"");
                Gson gson=new Gson();
                postInfo=gson.fromJson(postDataStr,Post.class);
                postPersonId = String.valueOf(postInfo.getPostPersonId());
                Log.d("testRun","postPersonId="+postPersonId);
                intent.putExtra("id",postPersonId);
                startActivity(intent);
            }
        });

        details_tv_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(YueyingDetails.this,"待更新",Toast.LENGTH_SHORT).show();

            }
        });

        details_tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(YueyingDetails.this,ApplyViewActivity.class);
                String postId2=postId;
                Log.d("testRun","postId2 = "+postId2);
                intent.putExtra("postId2",postId2);
                startActivity(intent);
                finish();
            }
        });

    }

    private void initData(){
        /*id= String.valueOf(postInfo.getId());*/
        postId=this.getIntent().getStringExtra("postId");
        Log.d("testRun","id = "+postId);
        //访问需要的参数
        final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param postIdParam = new OkHttpUtils.Param("id", postId);
        list.add(postIdParam);

        new Thread(new Runnable() {

            @Override
            public void run() {
                //post方式访问
                OkHttpUtils.post(APPConfig.findPostByid, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        Message message = new Message();
                        message.what = 0;
                        message.obj = response;
                        String postData = response.toString();
                        Log.d("testRun","postData = "+postData);
                        Log.d("testRun", "后台返回给我们的数据collectpostData=="+response.toString());
                        SharedPrefsUtil.putValue(YueyingDetails.this, APPConfig.POSTDATA, postData);
                        postDataStr = SharedPrefsUtil.getValue(YueyingDetails.this,APPConfig.POSTDATA,"");
                        handler.sendMessage(message);

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("testRun", "服务器连接失败，请重试！{------");
                        Toast.makeText(YueyingDetails.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                },list);

            }
        }).start();
    }

    public void initMotion(){
        details_tv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                collecterId= String.valueOf(userInfo.getId());
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                collectTime= format.format(date);
                Log.d("testRun","postId = "+postId);
                Log.d("testRun","collecterId = "+collecterId);
                Log.d("testRun","collectTime = "+collectTime);
                final List<OkHttpUtils.Param> list =new ArrayList<OkHttpUtils.Param>();
                OkHttpUtils.Param postidParam= new OkHttpUtils.Param("postId",postId);
                OkHttpUtils.Param collecterIdParam = new OkHttpUtils.Param("collecterId",collecterId);
                OkHttpUtils.Param collectTimeParam = new OkHttpUtils.Param("collectTime",collectTime);
                list.add(postidParam);
                list.add(collecterIdParam);
                list.add(collectTimeParam);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //post 方式连接
                        OkHttpUtils.post(APPConfig.addCollectByCollecterId, new OkHttpUtils.ResultCallback() {
                            @Override
                            public void onSuccess(Object response) {
                                Message message = new Message();
                                message.what = 1;
                                message.obj = response;
                                String result = response.toString();
                                if (result.equals("add_success")){
                                    Toast.makeText(YueyingDetails.this,"收藏成功！", Toast.LENGTH_SHORT).show();
                                }else if (result.equals("hasadd")){
                                    Toast.makeText(YueyingDetails.this,"已经收藏！",Toast.LENGTH_SHORT).show();
                                }

                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.d("testRun", "请求失败loginActivity----new Thread(new Runnable() {------");
                                Toast.makeText(YueyingDetails.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                            }
                        },list);
                    }
                }).start();

            }
        });



    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //Gson解析数据
                    Gson gson=new Gson();
                    String postDataStr2=msg.obj.toString();
                    Log.d("testRun","postDataStr2"+postDataStr2);
                    if (postDataStr2.equals("nodata")) {
                        Toast.makeText(YueyingDetails.this, "没有找到数据，请重试！", Toast.LENGTH_SHORT).show();
                    } else {
                        postInfo2=gson.fromJson(postDataStr2,Post.class);
                        details_tv_posttime.setText("发布于"+postInfo2.getPostTime());
                        details_tv_moviename.setText(postInfo2.getMovieName());
                        details_tv_site.setText(postInfo2.getSite());
                        details_tv_movietime.setText(postInfo2.getMovieTime());
                        if (postInfo2.getSex() == 0) {
                            details_tv_sex.setText("男");
                        }else if (postInfo2.getSex() == 1){
                            details_tv_sex.setText("女");
                        }else if (postInfo2.getSex()==2){
                            details_tv_sex.setText("不限");
                        }
                        if (postInfo2.getMovieType() == 0) {
                            details_tv_movietype.setText("两人单独约影");
                        }else {
                            details_tv_movietype.setText("多人群体约影");
                        }
                        details_tv_details.setText(postInfo2.getDetails());
                        postPersonId = String.valueOf(postInfo2.getPostPersonId());
                        Log.d("testRun","postPersonId="+postPersonId);
                        //访问需要的参数
                        final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
                        OkHttpUtils.Param postPersonIdParam = new OkHttpUtils.Param("id", postPersonId);
                        list.add(postPersonIdParam);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //post
                                OkHttpUtils.post(APPConfig.findUserById, new OkHttpUtils.ResultCallback() {
                                    @Override
                                    public void onSuccess(Object response) {
                                        Message message = new Message();
                                        message.what = 2;
                                        message.obj = response;
                                        String  userDataStr2= response.toString();
                                        handler.sendMessage(message);
                                        Log.d("testRun","userDataStr2"+userDataStr2);
                                        Gson gson1 = new Gson();
                                        userInfo2 = gson1.fromJson(userDataStr2, User.class);
                                        if (userInfo2.getNickname().equals("未填写")){
                                            details_tv_nickname.setText(userInfo2.getName());
                                            Log.d("testRun","Nickname"+userInfo2.getName());
                                        }else{
                                            details_tv_nickname.setText(userInfo2.getNickname());
                                            Log.d("testRun","Nickname"+userInfo2.getNickname());
                                        }



                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        Log.d("testRun", "请求失败loginActivity----new Thread(new Runnable() {------");
                                        Toast.makeText(YueyingDetails.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                                    }
                                },list);
                            }
                        }).start();
                        //查看用户姓名结束

                        //设置立即报名按钮状态
                        if((userInfo.getId())==(postInfo2.getPostPersonId())){
                            details_tv_apply.setVisibility(View.GONE);
                        }else {
                            details_tv_apply.setVisibility(View.VISIBLE);
                        }

                        //设置查看报名状态按钮状态
                        if (postInfo2.getMovieType()==0&&((userInfo.getId())!=(postInfo2.getPostPersonId()))){
                            details_tv_check.setVisibility(View.GONE);
                        }else {
                            details_tv_check.setVisibility(View.VISIBLE);
                        }

                        details_tv_apply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                String postId= String.valueOf(postInfo2.getId());
                                String startPersonId= String.valueOf(postInfo2.getPostPersonId());
                                String byPersonId= String.valueOf(userInfo.getId());
                                Log.d("testRun","postId="+postId+"startPersonId="+startPersonId+"byPersonId="+byPersonId);

                                final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
                                OkHttpUtils.Param postIdParam= new OkHttpUtils.Param("postId",postId);
                                OkHttpUtils.Param startPersonIdParam=new OkHttpUtils.Param("startPersonId",startPersonId);
                                OkHttpUtils.Param byPersonIdParam =new OkHttpUtils.Param("byPersonId",byPersonId);
                                list.add(postIdParam);
                                list.add(startPersonIdParam);
                                list.add(byPersonIdParam);
                                if (postInfo2.getSex()==userInfo.getGender()||postInfo2.getSex()==2){
                                    Log.d("testRun","对象："+postInfo2.getSex()+"性别："+userInfo.getGender());
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //post
                                            OkHttpUtils.post(APPConfig.addPersonByPostId, new OkHttpUtils.ResultCallback() {
                                                @Override
                                                public void onSuccess(Object response) {
                                                    String result =response.toString();
                                                    Log.d("testRun","result="+result);
                                                    if (result.equals("add_success")){
                                                        Log.d("testRun","apply");
                                                        Toast.makeText(YueyingDetails.this,"报名成功",Toast.LENGTH_SHORT).show();
                                                    }else if (result.equals("ending")){
                                                        Log.d("testRun","ending");
                                                        Toast.makeText(YueyingDetails.this,"报名人数已满，请查看其它约影帖子",Toast.LENGTH_SHORT).show();

                                                    }else if(result.equals("hasjoined")){
                                                        Toast.makeText(YueyingDetails.this,"您已经报名了，具体的请看报名情况",Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Exception e) {
                                                    Log.d("testRun", "网络请求失败------");
                                                    Toast.makeText(YueyingDetails.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                                                }
                                            },list);
                                        }
                                    }).start();
                                }else {
                                    Toast.makeText(YueyingDetails.this,"性别不符合该帖约影要求，请查看其它约影帖子",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                    break;
            }
        }

    };


}
