package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.MycollectAdapter;
import com.example.zhuocong.comxzc9.adapter.YueyingFragmentAdapter;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.Collect;
import com.example.zhuocong.comxzc9.entity.Post;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/24.
 */

public class UserMyCollectActivity extends Activity{

    private String userDataStr;
    private User userInfo;
    private Collect collectInfo;

    private String collecterId;

    private ListView mycollectlistview;
    private List<Collect> collectList;
    private MycollectAdapter mycollectAdapter;
    private ImageView mycollect_img_back;
    Gson gson = new Gson();
    List<String> list=new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_mycollect);
        initView();
        initData();
        String Size=SharedPrefsUtil.getValue(UserMyCollectActivity.this,APPConfig.SIZE,"");
        int size= Integer.parseInt(Size);

    }

    public void initView(){
        mycollectlistview=(ListView)this.findViewById(R.id.mycollectlistview);
        mycollect_img_back=(ImageView)this.findViewById(R.id.mycollect_img_back);

        userDataStr = SharedPrefsUtil.getValue(UserMyCollectActivity.this,APPConfig.USERDATA,"");
        Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);

        mycollect_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mycollectlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView collectlist_tv_postid = (TextView) view.findViewById(R.id.collectlist_tv_postid);
                String id=collectlist_tv_postid.getText().toString().trim();
                SharedPrefsUtil.putValue(UserMyCollectActivity.this, APPConfig.PID,id);
                Intent intent=new Intent();
                intent.setClass(UserMyCollectActivity.this, YueyingDetails.class);
                startActivity(intent);
            }
        });

    }
    public void initData(){
        collecterId= String.valueOf(userInfo.getId());
        Log.d("TestRun","collecterId="+collecterId);
        final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param collecterIdParam =new OkHttpUtils.Param("collecterId",collecterId);
        list.add(collecterIdParam);

        new Thread(new Runnable() {
            @Override
            public void run() {

                //post方式连接  url
                OkHttpUtils.post(APPConfig.findCollectByCollecterId, new OkHttpUtils.ResultCallback() {

                    @Override
                    public void onSuccess(Object response) {

                        Message message = new Message();
                        message.what = 0;
                        message.obj = response;
                        String collectDateStr=response.toString();
                        Log.d("testRun","collectDateStr="+collectDateStr);
                        //获取单个帖子id
                        List<Collect> collectlist=new ArrayList<Collect>();
                        collectlist=gson.fromJson(collectDateStr,new TypeToken<List<Collect>>(){}.getType());
                        final String size= String.valueOf(collectlist.size());
                        //将列表长度存在本地，方便后面调用
                        SharedPrefsUtil.putValue(UserMyCollectActivity.this,APPConfig.SIZE,size);
                        //数组存储帖子id
                        String collectArr[]=new String[collectlist.size()];
                        for (int i=0;i<collectlist.size();++i) {
                            collectArr[i] = String.valueOf(collectlist.get(i).getPostId());
                            Log.d("testRun", "id" + i + "=" + collectArr[i]);
                            final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
                            OkHttpUtils.Param collecterIdParam =new OkHttpUtils.Param("id",collectArr[i]);
                            list.add(collecterIdParam);

                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    //post方式连接  url
                                    OkHttpUtils.post(APPConfig.findPostByid, new OkHttpUtils.ResultCallback() {

                                        @Override
                                        public void onSuccess(Object response) {
                                            Message message = new Message();
                                            message.what = 1;
                                            message.obj = response;
                                            String postDateStr=response.toString();
                                            handler.sendMessage(message);

                                        }

                                        @Override
                                        public void onFailure(Exception e) {
                                            Log.d("postDateStr","服务器连接失败，请重试！");
                                            Toast.makeText(UserMyCollectActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                                        }
                                    },list);

                                }
                            }).start();


                        }

                       /* collectDateStr1=SharedPrefsUtil.getValue(UserMyCollectActivity.this,APPConfig.COLLECTDATA,"");
                        Log.d("testRun","111="+collectDateStr1);*/
                        /*SharedPrefsUtil.putValue(UserMyCollectActivity.this,APPConfig.COLLECTDATA,collectDateStr);*/
                        handler.sendMessage(message);

                    }

                    @Override
                    public void onFailure(Exception e) {Log.d("testRun","daole="+3);
                        Log.d("postDateStr","服务器连接失败，请重试！");
                        Toast.makeText(UserMyCollectActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                },list);

            }
        }).start();

    }

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg){
           switch (msg.what){
               case 0:
                   String collectDateStr=msg.obj.toString();
                   Log.d("testRun","collectDateStr2="+collectDateStr);

                   if (collectDateStr.equals("nodata")) {
                       Toast.makeText(UserMyCollectActivity.this, "还没有用户发起约影", Toast.LENGTH_SHORT).show();
                   } else {
                       //gson解析数据时，

                       try {
                           collectList = gson.fromJson(collectDateStr, new TypeToken<List<Collect>>() {}.getType());
                           if (collectList != null && collectList.size() > 0) {
                               Log.d("testRun", "collectList=" + collectList);
                               mycollectAdapter = new MycollectAdapter(collectList,UserMyCollectActivity.this);
                               mycollectlistview.setAdapter(mycollectAdapter);
                           } else {

                           }
                       } catch (JsonSyntaxException e1) {

                           Toast.makeText(UserMyCollectActivity.this, "内部数据解析异常", Toast.LENGTH_SHORT).show();
                           Log.e("JsonSyntaxException",""+e1.getMessage());
                           e1.printStackTrace();
                       }

                   }
                   break;
               case 1:
                   String Size=SharedPrefsUtil.getValue(UserMyCollectActivity.this,APPConfig.SIZE,"");
                   int size= Integer.parseInt(Size);
                   String si=SharedPrefsUtil.getValue(UserMyCollectActivity.this,APPConfig.SI,"");
                   int i= Integer.parseInt(si);
                   String arr[]=new String[size];
                   for (int a=0;a<size;a++){
                       arr[i]=msg.obj.toString();
                       Log.d("testRun","arr"+i+arr[i]);
                   }

                   break;
           }




        }

    };

}
