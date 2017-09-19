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
import com.example.zhuocong.comxzc9.adapter.MypostAdapter;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.CollectList;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/9/17.
 */

public class UserMyyueyingBymeActivity extends Activity {
    private ImageView byme_img_back;
    private ListView bymelistview;
    private TextView byme_tv_refresh;

    private MypostAdapter mypostAdapter;
    private List<PostList> postListList;
    private String userDataStr;
    private User userInfo;
    private String postPersonId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_history_byme);
        initView();
        initData();
    }

    public void initView(){
        userDataStr = SharedPrefsUtil.getValue(UserMyyueyingBymeActivity.this, APPConfig.USERDATA,"");
        Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);

        byme_img_back=(ImageView)this.findViewById(R.id.byme_img_back);
        bymelistview=(ListView)this.findViewById(R.id.bymelistview);
        byme_tv_refresh=(TextView)this.findViewById(R.id.byme_tv_refresh);

        byme_tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });

        byme_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        bymelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView collectlist_tv_postid = (TextView) view.findViewById(R.id.collectlist_tv_postid);
                String id=collectlist_tv_postid.getText().toString().trim();
                Intent intent=new Intent();
                intent.setClass(UserMyyueyingBymeActivity.this, YueyingDetails.class);
                intent.putExtra("postId",id);
                Log.d("testRun","PostID="+id);
                startActivity(intent);
            }
        });

    }

    public void initData(){
        postPersonId= String.valueOf(userInfo.getId());
        Log.d("testRun","postPersonId="+postPersonId);

        final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param postPersonIdParam =new OkHttpUtils.Param("postPersonId",postPersonId);
        list.add(postPersonIdParam);

        new Thread(new Runnable() {
            @Override
            public void run() {

                //post方式连接  url
                OkHttpUtils.post(APPConfig.findPostBypostPersonId, new OkHttpUtils.ResultCallback() {

                    @Override
                    public void onSuccess(Object response) {

                        Message message = new Message();
                        message.what = 0;
                        message.obj = response;
                        String postbymeListListStr=response.toString();
                        Log.d("testRun","postbymeListListStr="+postbymeListListStr);
                        handler.sendMessage(message);

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("postDateStr","服务器连接失败，请重试！");
                        Toast.makeText(UserMyyueyingBymeActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                },list);

            }
        }).start();
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    String postbymeListListStr=msg.obj.toString();
                    Log.d("testRun","postbymeListListStr="+postbymeListListStr);

                    if (postbymeListListStr.equals("nodata")) {
                        Toast.makeText(UserMyyueyingBymeActivity.this, "暂时没有正在进行中的发起约影", Toast.LENGTH_SHORT).show();
                    } else {
                        //gson解析数据时，
                        try {
                            Gson gosn =new Gson();
                            postListList = gosn.fromJson(postbymeListListStr, new TypeToken<List<PostList>>() {}.getType());
                            if (postListList != null && postListList.size() > 0) {
                                Log.d("testRun", "postListList=" + postListList);
                                mypostAdapter = new MypostAdapter(postListList,UserMyyueyingBymeActivity.this);
                                bymelistview.setAdapter(mypostAdapter);
                            } else {

                            }
                        } catch (JsonSyntaxException e1) {
                            Toast.makeText(UserMyyueyingBymeActivity.this, "内部数据解析异常", Toast.LENGTH_SHORT).show();
                            Log.e("JsonSyntaxException",""+e1.getMessage());
                            e1.printStackTrace();
                        }

                    }
                    break;

            }
        }
    };
}
