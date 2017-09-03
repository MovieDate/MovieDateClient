package com.example.zhuocong.comxzc9.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.YueyingFragmentAdapter;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.Post;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.ui.activity.LoginActivity;
import com.example.zhuocong.comxzc9.ui.activity.RegisterActivity;
import com.example.zhuocong.comxzc9.ui.activity.YueyingDetails;
import com.example.zhuocong.comxzc9.ui.basefragment.BaseFragment;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class YueYingFragment extends BaseFragment{


    private ListView yueyinglistview;

    private List<Post> postList;
    private List<PostList> postListList;
    private YueyingFragmentAdapter yueyingFragmentAdapter;
    Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView=inflater.inflate(R.layout.fragment_yueying, null);
        mContext=getActivity();

        yueyinglistview=(ListView)mBaseView.findViewById(R.id.yueyinglistview);


        initMotion();
        findView();
        initView();

        yueyinglistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv_id=(TextView)view.findViewById(R.id.yueyinglist_tv_id);
                String id=tv_id.getText().toString().trim();
                Log.d("testRun","id = "+id);
                /*SharedPrefsUtil.putValue(getActivity(), APPConfig.PID,id);*/
                Intent intent=new Intent();
                intent.setClass(getActivity(), YueyingDetails.class);
                intent.putExtra("postId",id);
                startActivity(intent);
            }
        });
        return mBaseView;
    }


    public void initMotion(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                //post方式连接  url
                OkHttpUtils.get(APPConfig.allPost, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        Message message = new Message();
                        message.what = 0;
                        message.obj = response;
                        String postlistDateStr=response.toString();
                        Log.d("postlistDateStr","post2"+postlistDateStr);
                        handler.sendMessage(message);

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("postDateStr","服务器连接失败，请重试！");
                        Toast.makeText(getActivity(), "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();

    }

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg){
            String postlistDateStr=msg.obj.toString();
            Log.d("postlistDateStr", "postlistDateStr1=" + postlistDateStr);
            if (postlistDateStr.equals("nodata")) {
                Toast.makeText(getActivity(), "还没有用户发起约影", Toast.LENGTH_SHORT).show();
            } else {
                //gson解析数据时，
                try {
                    postListList = gson.fromJson(postlistDateStr, new TypeToken<List<PostList>>() {}.getType());
                    if (postListList != null && postListList.size() > 0) {
                        Log.d("postListList", "postListList=" + postListList);
                        yueyingFragmentAdapter = new YueyingFragmentAdapter(postListList, getActivity());
                        yueyinglistview.setAdapter(yueyingFragmentAdapter);
                    } else {

                    }
                } catch (JsonSyntaxException e1) {
                    Toast.makeText(getActivity(), "内部数据解析异常", Toast.LENGTH_SHORT).show();
                    Log.e("JsonSyntaxException",""+e1.getMessage());
                    e1.printStackTrace();
                }
            }



        }

    };



    @Override
    protected void findView() {

    }

    @Override
    protected void initView() {



    }



}
