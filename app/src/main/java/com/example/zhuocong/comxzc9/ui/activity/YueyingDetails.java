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

    private String postId;
    private String collecterId;
    private String collectTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_yueying_details);
        initView();
        initData();
        initMotion();
    }

    public void initView(){
        userDataStr = SharedPrefsUtil.getValue(YueyingDetails.this,APPConfig.USERDATA,"");
        Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);


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
                intent.setClass(YueyingDetails.this,MyUserInfoActivity.class);
                startActivity(intent);
            }
        });

        details_tv_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(YueyingDetails.this,"待更新",Toast.LENGTH_SHORT).show();

            }
        });



    }

    private void initData(){
        /*id= String.valueOf(postInfo.getId());*/
        postId=SharedPrefsUtil.getValue(YueyingDetails.this, APPConfig.PID,"");
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
                postId=SharedPrefsUtil.getValue(YueyingDetails.this, APPConfig.PID,"");
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
                    if (postDataStr.equals("nodata")) {
                        Toast.makeText(YueyingDetails.this, "没有找到数据，请重试！", Toast.LENGTH_SHORT).show();
                    } else {//如果后台成功返回User数据，则显示出来，这里我只显示一部分，其他还要补充进来
                        details_tv_nickname.setText(userInfo.getNickname());
                        postInfo=gson.fromJson(postDataStr,Post.class);
                        details_tv_posttime.setText("发布于"+postInfo.getPostTime());
                        details_tv_moviename.setText(postInfo.getMovieName());
                        details_tv_site.setText(postInfo.getSite());
                        details_tv_movietime.setText(postInfo.getMovieTime());
                        if (postInfo.getSex() == 0) {
                            details_tv_sex.setText("男");
                        }else if (postInfo.getSex() == 1){
                            details_tv_sex.setText("女");
                        }else if (postInfo.getSex()==2){
                            details_tv_sex.setText("不限");
                        }
                        if (postInfo.getMovieType() == 0) {
                            details_tv_movietype.setText("两人单独约影");
                        }else {
                            details_tv_movietype.setText("多人群体约影");
                        }
                        details_tv_details.setText(postInfo.getDetails());
                    }
                    break;
            }
        }

    };


}
