package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.Post;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;

import java.util.List;

/**
 * Created by zhuocong on 2017/8/25.
 */

public class YueyingFragmentAdapter extends BaseAdapter {
    private List<Post> postList;
    private LayoutInflater inflater;
    public YueyingFragmentAdapter(){

    }
    public YueyingFragmentAdapter(List<Post> postList, Context context){
        this.postList=postList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return postList == null ? 0 : postList.size();
    }

    @Override
    public Post getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.yueyinglist, null);
        Post post =  getItem(position);
        //在view视图中查找id为image_photo的控件
        TextView tv_movieName = (TextView) view.findViewById(R.id.yueyinglist_tv_movieName);
        TextView tv_site = (TextView) view.findViewById(R.id.yueyinglist_tv_site);
        TextView tv_postTime = (TextView) view.findViewById(R.id.yueyinglist_tv_postTime);
        TextView tv_id=(TextView)view.findViewById(R.id.yueyinglist_tv_id);
        String id = String.valueOf(post.getId());
        tv_id.setText(id);
        tv_movieName.setText("约影影片："+post.getMovieName());
        tv_site.setText("约影地点："+post.getSite());
        tv_postTime.setText("发布于"+post.getPostTime());

        return view;
    }
}
