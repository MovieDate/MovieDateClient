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
import com.example.zhuocong.comxzc9.entity.CollectList;
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
    private List<Post> postList;
    private List<CollectList> collectListList;
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
                Intent intent=new Intent();
                intent.setClass(UserMyCollectActivity.this, YueyingDetails.class);
                intent.putExtra("postId",id);
                Log.d("testRun","PostID="+id);
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
                OkHttpUtils.post(APPConfig.findCollectByCollecterId2, new OkHttpUtils.ResultCallback() {

                    @Override
                    public void onSuccess(Object response) {

                        Message message = new Message();
                        message.what = 0;
                        message.obj = response;
                        String collectListListStr=response.toString();
                        Log.d("testRun","collectDateStr="+collectListListStr);
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

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg){
           switch (msg.what){
               case 0:
                   String collectListListStr=msg.obj.toString();
                   Log.d("testRun","collectListListStr="+collectListListStr);

                   if (collectListListStr==null) {
                       Toast.makeText(UserMyCollectActivity.this, "暂时没有收藏约影", Toast.LENGTH_SHORT).show();
                   } else {
                       //gson解析数据时，
                       try {
                           collectListList = gson.fromJson(collectListListStr, new TypeToken<List<CollectList>>() {}.getType());
                           if (collectListList != null && collectListList.size() > 0) {
                               Log.d("testRun", "collectListList=" + collectListList);
                               mycollectAdapter = new MycollectAdapter(collectListList,UserMyCollectActivity.this);
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
           }

        }

    };

}
