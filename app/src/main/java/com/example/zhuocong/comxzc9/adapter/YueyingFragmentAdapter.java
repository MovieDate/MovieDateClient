package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.Post;

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
        TextView image_photo = (TextView) view.findViewById(R.id.image_photo);
        TextView tv_name = (TextView) view.findViewById(R.id.name);
        TextView tv_age = (TextView) view.findViewById(R.id.age);
        image_photo.setText(post.getMovieName());
        tv_name.setText(post.getDetails());
        tv_age.setText(post.getSite());
        return view;
    }
}
