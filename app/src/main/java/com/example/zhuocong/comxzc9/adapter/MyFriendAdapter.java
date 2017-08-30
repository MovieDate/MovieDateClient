package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.Friend;

import java.util.List;

/**
 * Created by zhuocong on 2017/8/30.
 */

public class MyFriendAdapter extends BaseAdapter {
    private List<Friend> friendList;
    private LayoutInflater inflater;
    public MyFriendAdapter(List<Friend> friendList, Context context){
        this.friendList=friendList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return friendList==null?0:friendList.size();
    }

    @Override
    public Friend getItem(int position) {
        return friendList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.myfriendlist, null);
        Friend friend =  getItem(position);
        //在view视图中查找id为image_photo的控件
        TextView myfriend_id= (TextView) view.findViewById(R.id.myfriend_id);
        String id= String.valueOf(friend.getFriendId());
        myfriend_id.setText(id);

        return view;
    }
}
