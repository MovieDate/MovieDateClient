package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.Post;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;

import java.util.List;

/**
 * Created by zhuocong on 2017/8/25.
 */

public class YueyingFragmentAdapter extends BaseAdapter {
    private List<PostList> postListList;
    private LayoutInflater inflater;
    public YueyingFragmentAdapter(){

    }
    public YueyingFragmentAdapter(List<PostList> postListList, Context context){
        this.postListList=postListList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return postListList == null ? 0 : postListList.size();
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
        View view = inflater.inflate(R.layout.yueyinglist, null);
        PostList postListList =  getItem(position);
        //在view视图中查找id为image_photo的控件
        TextView tv_nickname = (TextView) view.findViewById(R.id.yueyinglist_tv_nickname);
        TextView tv_gender = (TextView) view.findViewById(R.id.yueyinglist_tv_gender);
        TextView tv_movieName = (TextView) view.findViewById(R.id.yueyinglist_tv_movieName);
        TextView tv_site = (TextView) view.findViewById(R.id.yueyinglist_tv_site);
        TextView tv_postTime = (TextView) view.findViewById(R.id.yueyinglist_tv_postTime);
        TextView tv_id=(TextView)view.findViewById(R.id.yueyinglist_tv_id);
        String id = String.valueOf(postListList.getId());
        tv_id.setText(id);
        if (postListList.getNickname().equals("未填写")){
            tv_nickname.setText(postListList.getName());
            Log.d("testRun","Nickname"+postListList.getName());
        }else{
            tv_nickname.setText(postListList.getNickname());
            Log.d("testRun","Nickname"+postListList.getNickname());
        }
        if (postListList.getGender() == 0) {
            tv_gender.setText("男");
        }else if (postListList.getGender() == 1){
            tv_gender.setText("女");
        }
        tv_movieName.setText("约影影片：\n"+postListList.getMovieName());
        tv_site.setText("约影地点：\n"+postListList.getSite());
        tv_postTime.setText("发布于"+postListList.getPostTime());

        return view;
    }
}
