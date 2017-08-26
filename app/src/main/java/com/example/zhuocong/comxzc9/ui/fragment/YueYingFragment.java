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
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.YueyingFragmentAdapter;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.Post;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.ui.activity.MainActivity;
import com.example.zhuocong.comxzc9.ui.basefragment.BaseFragment;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class YueYingFragment extends BaseFragment{


    private ListView yueyinglistview;
    private Button test;
    private String userDataStr;
    private Post postInfo;
    private PostList postListInfo;
    private YueyingFragmentAdapter yueyingFragmentAdapter;
    Gson gson = new Gson();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView=inflater.inflate(R.layout.fragment_yueying, null);
        mContext=getActivity();

        yueyinglistview=(ListView)mBaseView.findViewById(R.id.yueyinglistview);
        test=(Button)mBaseView.findViewById(R.id.test);


        initMotion();
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"111",Toast.LENGTH_SHORT).show();
            }
        });
        findView();
        initView();
        return mBaseView;
    }


    public void initMotion(){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //post方式连接  url
                        OkHttpUtils.post(APPConfig.allPost, new OkHttpUtils.ResultCallback() {
                            @Override
                            public void onSuccess(Object response) {

                                Message message = new Message();
                                message.what = 0;
                                message.obj = response;
                                String postDateStr=response.toString();
                                Log.d("t","post"+postDateStr);
                                List<Post> postList = gson.fromJson("postDateStr", new TypeToken<List<Post>>() {}.getType());
                                yueyingFragmentAdapter=new YueyingFragmentAdapter(postList,getActivity());
                                handler.sendMessage(message);
                            }

                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(getActivity(), "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }).start();

    }

    private Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg){

                yueyinglistview.setAdapter(yueyingFragmentAdapter);

        }

    };


    @Override
    protected void findView() {

    }

    @Override
    protected void initView() {



    }



}
