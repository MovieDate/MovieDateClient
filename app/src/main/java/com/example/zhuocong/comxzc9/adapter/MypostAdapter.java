package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.CollectList;
import com.example.zhuocong.comxzc9.entity.PostList;

import java.util.List;

/**
 * Created by zhuocong on 2017/9/17.
 */

public class MypostAdapter extends BaseAdapter {
    private List<PostList> postListList;
    private LayoutInflater inflater;
    public MypostAdapter(List<PostList> postListList, Context context){
        this.postListList=postListList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return postListList==null?0:postListList.size();
    }

    @Override
    public PostList getItem(int position) {
        return postListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.collectlist, null);
        PostList postList =  getItem(position);
        //在view视图中查找id为image_photo的控件
        TextView collectlist_tv_nickname = (TextView) view.findViewById(R.id.collectlist_tv_nickname);
        TextView collectlist_tv_movieName = (TextView) view.findViewById(R.id.collectlist_tv_movieName);
        TextView collectlist_tv_site = (TextView) view.findViewById(R.id.collectlist_tv_site);
        TextView collectlist_tv_details = (TextView) view.findViewById(R.id.collectlist_tv_details);
        TextView collectlist_tv_collecttime = (TextView) view.findViewById(R.id.collectlist_tv_collecttime);
        TextView collectlist_tv_postid = (TextView) view.findViewById(R.id.collectlist_tv_postid);
        String id= String.valueOf(postList.getId());

        collectlist_tv_postid.setText(id);
        Log.d("testRun","name="+postList.getNickname());
        if (postList.getNickname().equals("未填写")){
            collectlist_tv_nickname.setText(postList.getName());
            Log.d("testRun","Nickname"+postList.getName());
        }else{
            collectlist_tv_nickname.setText(postList.getNickname());
            Log.d("testRun","Nickname"+postList.getNickname());
        }
        collectlist_tv_movieName.setText("约影影片:\n"+postList.getMovieName());
        collectlist_tv_site.setText(postList.getSite());
        collectlist_tv_details.setText(postList.getDetails());
        collectlist_tv_collecttime.setText("发布于:"+postList.getPostTime());

        return view;
    }
}
