package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.ReviewAdapter;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.Post;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.entity.Review;
import com.example.zhuocong.comxzc9.entity.ReviewList;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

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
    private List<ReviewList> reviewListList;
    private ReviewList reviewlist;
    private ReviewAdapter reviewAdapter;

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
    private LinearLayout details_tv_apply;
    private LinearLayout details_tv_review;
    private LinearLayout details_tv_collect;
    private ListView reviewlistview;
    private TextView review_details;
    private LinearLayout details_tv_check;
    private EditText details_et_review;
    private TextView details_tv_finish;
    private ImageView details_img_share;
    private TextView review_tv_delete;

    private String reviewid;
    private Review reviewInfo;
    private String postId;
    private String collecterId;
    private String collectTime;
    private String postPersonId;
    Gson gson= new Gson();

    private EditText et_sendDicscuss;
    private TextView tv_send;
    private TextView tv_send2;
    private TextView tv_unsend;
    private LinearLayout ll_sendDiscuss;
    private long account;
    private String id;
    private  String sdetails;
    private  String sreviewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_yueying_details);
        initView();
        initData();
        initMotion();
        initReview();
        sendReview();
        finishmovie();

        sendtoReview();
    }

    public void initView(){
        //读取数据
        userDataStr = SharedPrefsUtil.getValue(YueyingDetails.this,APPConfig.USERDATA,"");
        userInfo=gson.fromJson(userDataStr,User.class);
        Log.d("testRun","testId="+userInfo.getId());
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
        details_tv_apply=(LinearLayout)this.findViewById(R.id.details_tv_apply);
        details_tv_review=(LinearLayout)this.findViewById(R.id.details_tv_review);
        details_tv_collect=(LinearLayout)this.findViewById(R.id.details_tv_collect);
        reviewlistview=(ListView)this.findViewById(R.id.reviewlistview);
        review_details=(TextView)this.findViewById(R.id.review_details);
        details_tv_check=(LinearLayout)this.findViewById(R.id.details_tv_check);
        et_sendDicscuss = (EditText) this.findViewById(R.id.et_activity_shareinfo_discusscontent);
        tv_send = (TextView) this.findViewById(R.id.bt_activity_shareinfo_send);
        tv_send2 = (TextView) this.findViewById(R.id.bt_activity_shareinfo_send2);
        tv_unsend = (TextView) this.findViewById(R.id.bt_activity_shareinfo_unsend);
        ll_sendDiscuss = (LinearLayout) this.findViewById(R.id.ll_activity_shareinfo_senddiscuss);
        details_img_share=(ImageView)this.findViewById(R.id.details_img_share);
        details_tv_finish=(TextView)findViewById(R.id.details_tv_finish);


        et_sendDicscuss.setVisibility(View.GONE);
        ll_sendDiscuss.setVisibility(View.GONE);
        tv_send.setVisibility(View.GONE);
        tv_unsend.setVisibility(View.GONE);

        //设置分享点击事件（待完善，只能分享文字）
        details_img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));*/
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
                intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent, getTitle()));

                Intent shareIntent=new Intent(Intent.ACTION_SEND);
                shareIntent.setComponent(new ComponentName("com.example.zhuocong.comxzc9","com.example.zhuocong.comxzc9.ui.activity.YueyingDetails.class"));
                //这里就是组织内容了，
                // shareIntent.setType("text/plain");
                shareIntent.setType("image/*");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
                shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(shareIntent);
            }
        });

        //评论按键的点击事件
        details_tv_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_sendDicscuss.setVisibility(View.VISIBLE);
                ll_sendDiscuss.setVisibility(View.VISIBLE);
                tv_send.setVisibility(View.VISIBLE);
                tv_unsend.setVisibility(View.VISIBLE);
                tv_send2.setVisibility(View.GONE);
            }
        });

        //设置返回键的点击事件
        details_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //设置查看用户信息的点击事件
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


        //设置查看报名情况的点击事件
        details_tv_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(YueyingDetails.this,ApplyViewActivity.class);
                String postId2=postId;
                Log.d("testRun","postId2 = "+postId2);
                intent.putExtra("postId2",postId2);
                startActivity(intent);
            }
        });

        //评论时取消按键
        tv_unsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_sendDiscuss.setVisibility(View.GONE);
                tv_unsend.setVisibility(View.GONE);
                tv_send.setVisibility(View.GONE);
                et_sendDicscuss.setVisibility(View.GONE);
            }
        });

        //评论框点击事件
        et_sendDicscuss.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){//没被点击时候，这些控件被隐藏
                    Log.d("test","未点击");
                    et_sendDicscuss.setVisibility(View.GONE);
                    ll_sendDiscuss.setVisibility(View.GONE);
                    tv_send.setVisibility(View.GONE);
                    tv_unsend.setVisibility(View.GONE);
                }else {
                    Log.d("test","yi点击");
                    //被点击的时候。显示这些控件
                    et_sendDicscuss.setVisibility(View.VISIBLE);
                    ll_sendDiscuss.setVisibility(View.VISIBLE);
                    tv_send.setVisibility(View.GONE);
                    tv_send2.setVisibility(View.VISIBLE);
                    tv_unsend.setVisibility(View.VISIBLE);
                }

            }
        });


        //设置点击评论跳转页面
        reviewlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /*TextView review_name=(TextView) view.findViewById(R.id.review_name);*/
                TextView review_friendid=(TextView) view.findViewById(R.id.review_friendid);
                TextView review_tv_details=(TextView) view.findViewById(R.id.review_tv_details);
                TextView review_tv_reviewTime=(TextView) view.findViewById(R.id.review_tv_reviewTime);
                /*String name=review_name.getText().toString().trim();
                Log.d("testRun","name="+name);*/
                id= review_friendid.getText().toString().trim();
                Log.d("testRun","id="+id);
                sdetails=review_tv_details.getText().toString().trim();
                Log.d("testRun","details="+sdetails);
                sreviewTime=review_tv_reviewTime.getText().toString().trim();
                Log.d("testRun","reviewTime="+sreviewTime);




                et_sendDicscuss.setVisibility(View.VISIBLE);
                ll_sendDiscuss.setVisibility(View.VISIBLE);
                tv_send.setVisibility(View.GONE);
                tv_unsend.setVisibility(View.VISIBLE);
                tv_send2.setVisibility(View.VISIBLE);
                account=l+1;

            }
        });

    }

    //加载帖子内容
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

    //完成约影功能
    public void finishmovie(){
        postId=this.getIntent().getStringExtra("postId");
        details_tv_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //访问需要的参数
                final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
                OkHttpUtils.Param postIdParam = new OkHttpUtils.Param("id", postId);
                list.add(postIdParam);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //post
                        OkHttpUtils.post(APPConfig.updatePostEndTimeById, new OkHttpUtils.ResultCallback() {
                            @Override
                            public void onSuccess(Object response) {
                              String updresult=response.toString();
                                if (updresult.equals("update_success")){
                                    Toast.makeText(YueyingDetails.this,"您的这次约影已完成，欢迎您继续发起新的约影",Toast.LENGTH_SHORT).show();
                                    details_tv_finish.setVisibility(View.GONE);
                                }
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
        });
    }


    //收藏功能
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

    //查看该帖子的所有评论
    public  void initReview(){
        Log.d("testRun","后台postId2="+postId);
        final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param postParam=new OkHttpUtils.Param("postId",postId);
        list.add(postParam);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //post
                OkHttpUtils.post(APPConfig.findReviewByPostId, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = response;
                        String reviewDataStr=response.toString();
                        Log.d("testRun","后台给的数据reviewDataStr="+reviewDataStr);
                        handler.sendMessage(message);
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

    //发表评论功能
    public void sendReview(){
        tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postPersonId= String.valueOf(userInfo.getId());
                String reviewDetails= et_sendDicscuss.getText().toString().trim();
                final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
                OkHttpUtils.Param postIdParam =new OkHttpUtils.Param("postId",postId);
                OkHttpUtils.Param postPersonIdParam =new OkHttpUtils.Param("postPersonId",postPersonId);
                OkHttpUtils.Param reviewDetailsParam=new OkHttpUtils.Param("reviewDetails",reviewDetails);
                list.add(postIdParam);
                list.add(postPersonIdParam);
                list.add(reviewDetailsParam);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //POST连接
                        OkHttpUtils.post(APPConfig.reviewDetailsParam, new OkHttpUtils.ResultCallback() {
                            @Override
                            public void onSuccess(Object response) {
                                String result=response.toString();
                                Log.d("test","result="+result);
                                if (result.equals("add_success")){
                                    Toast.makeText(YueyingDetails.this,"评论成功！",Toast.LENGTH_SHORT).show();
                                    et_sendDicscuss.setVisibility(View.GONE);
                                    ll_sendDiscuss.setVisibility(View.GONE);
                                    tv_send.setVisibility(View.GONE);
                                    tv_unsend.setVisibility(View.GONE);
                                    initReview();
                                    /*Intent intent=new Intent();
                                    intent.setClass(YueyingDetails.this,YueyingDetails.class);
                                    startActivity(intent);*/

                                }else {
                                    Toast.makeText(YueyingDetails.this,"评论失败，请稍后再试！",Toast.LENGTH_SHORT).show();
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

    //发表评论功能
    public void sendtoReview(){
        tv_send2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postPersonId= String.valueOf(userInfo.getId());
                String reviewDetails= "回复"+account+"楼"+"\n"+et_sendDicscuss.getText().toString().trim();

                final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
                OkHttpUtils.Param postIdParam =new OkHttpUtils.Param("postId",postId);
                OkHttpUtils.Param postPersonIdParam =new OkHttpUtils.Param("postPersonId",postPersonId);
                OkHttpUtils.Param reviewDetailsParam=new OkHttpUtils.Param("reviewDetails",reviewDetails);
                list.add(postIdParam);
                list.add(postPersonIdParam);
                list.add(reviewDetailsParam);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //POST
                        OkHttpUtils.post(APPConfig.reviewDetailsParam, new OkHttpUtils.ResultCallback() {
                            @Override
                            public void onSuccess(Object response) {
                                String ToReview=response.toString();
                                if (ToReview.equals("add_success")){
                                    Toast.makeText(YueyingDetails.this,"评论成功！",Toast.LENGTH_SHORT).show();
                                    et_sendDicscuss.setVisibility(View.GONE);
                                    ll_sendDiscuss.setVisibility(View.GONE);
                                    tv_send.setVisibility(View.GONE);
                                    tv_unsend.setVisibility(View.GONE);
                                    initReview();
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


                        //完成约影按钮
                        if(postInfo2.getEndTime()==null&&postInfo2.getPostPersonId()==userInfo.getId()){
                            details_tv_finish.setVisibility(View.VISIBLE);
                        }else {
                            details_tv_finish.setVisibility(View.GONE);

                        }

                        //设置查看报名状态按钮状态
                        if (postInfo2.getMovieType()==0&&((userInfo.getId())!=(postInfo2.getPostPersonId()))){
                            details_tv_check.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText(YueyingDetails.this,"由于该约影为单独约影，您没有权限查看报名情况！",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        details_tv_apply.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //设置立即报名按钮状态
                                if ((userInfo.getId()) == (postInfo2.getPostPersonId())) {
                                    Toast.makeText(YueyingDetails.this, "禁止本人报名自己发布的约影", Toast.LENGTH_SHORT).show();
                                } else {
                                String postId = String.valueOf(postInfo2.getId());
                                String startPersonId = String.valueOf(postInfo2.getPostPersonId());
                                String byPersonId = String.valueOf(userInfo.getId());
                                Log.d("testRun", "postId=" + postId + "startPersonId=" + startPersonId + "byPersonId=" + byPersonId);

                                final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
                                OkHttpUtils.Param postIdParam = new OkHttpUtils.Param("postId", postId);
                                OkHttpUtils.Param startPersonIdParam = new OkHttpUtils.Param("startPersonId", startPersonId);
                                OkHttpUtils.Param byPersonIdParam = new OkHttpUtils.Param("byPersonId", byPersonId);
                                list.add(postIdParam);
                                list.add(startPersonIdParam);
                                list.add(byPersonIdParam);
                                if (postInfo2.getSex() == userInfo.getGender() || postInfo2.getSex() == 2) {
                                    Log.d("testRun", "对象：" + postInfo2.getSex() + "性别：" + userInfo.getGender());
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //post
                                            OkHttpUtils.post(APPConfig.addPersonByPostId, new OkHttpUtils.ResultCallback() {
                                                @Override
                                                public void onSuccess(Object response) {
                                                    String result = response.toString();
                                                    Log.d("testRun", "result=" + result);
                                                    if (result.equals("add_success")) {
                                                        Log.d("testRun", "apply");
                                                        Toast.makeText(YueyingDetails.this, "报名成功", Toast.LENGTH_SHORT).show();
                                                    } else if (result.equals("ending")) {
                                                        Log.d("testRun", "ending");
                                                        Toast.makeText(YueyingDetails.this, "报名人数已满，请查看其它约影帖子", Toast.LENGTH_SHORT).show();

                                                    } else if (result.equals("hasjoined")) {
                                                        Toast.makeText(YueyingDetails.this, "您已经报名了，具体的请看报名情况", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Exception e) {
                                                    Log.d("testRun", "网络请求失败------");
                                                    Toast.makeText(YueyingDetails.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                                                }
                                            }, list);
                                        }
                                    }).start();
                                } else {
                                    Toast.makeText(YueyingDetails.this, "性别不符合该帖约影要求，请查看其它约影帖子", Toast.LENGTH_SHORT).show();
                                }
                            }
                            }
                        });


                    }
                    break;
                case 1:
                    String reviewDataStr=msg.obj.toString();
                    Log.d("testRun","reviewDataStr="+reviewDataStr);
                    if (reviewDataStr.equals("nodata")){
                        Toast.makeText(YueyingDetails.this, "还没有用户评论", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                            Gson gson1=new Gson();
                            reviewListList=gson1.fromJson(reviewDataStr,new TypeToken<List<ReviewList>>() {}.getType());
                            if (reviewListList!=null&&reviewListList.size()>0){
                                Log.d("reviewListList", "reviewListList333=" + reviewListList);
                                Intent intent=new Intent();
                                intent.putExtra("userId",userInfo.getId());
                                Log.d("testRun","userId==="+userInfo.getId());
                                reviewAdapter=new ReviewAdapter(reviewListList,YueyingDetails.this,String.valueOf(userInfo.getId()),String.valueOf(postId));
                                reviewlistview.setAdapter(reviewAdapter);
                                setListViewHeight(reviewlistview);/* android:focusableInTouchMode="true" 设置该属性即可使跳转页面置顶显示*/

                            }else {

                            }
                        }catch (JsonSyntaxException e1) {
                            Toast.makeText(YueyingDetails.this, "内部数据解析异常", Toast.LENGTH_SHORT).show();
                            Log.e("JsonSyntaxException",""+e1.getMessage());
                            e1.printStackTrace();
                        }

                    }
                    break;
            }
        }

    };

    /**
     * 重新计算ListView的高度，解决ScrollView和ListView两个View都有滚动的效果，在嵌套使用时起冲突的问题
     * @param listView
     */
    private void setListViewHeight(ListView listView) {
//        DisplayMetrics metrics = new DisplayMetrics();
//        float dpToCm = (metrics.density * 2.54f *133*shareList2.size())/ metrics.xdpi;
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        totalHeight+=20;
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }


}
