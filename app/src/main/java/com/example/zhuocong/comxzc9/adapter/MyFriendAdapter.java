package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.Friend;
import com.example.zhuocong.comxzc9.entity.FriendList;

import java.util.List;

/**
 * Created by zhuocong on 2017/8/30.
 */

public class MyFriendAdapter extends BaseAdapter {
    private List<FriendList> friendListList;
    private LayoutInflater inflater;
    public MyFriendAdapter(List<FriendList> friendListList, Context context){
        this.friendListList=friendListList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return friendListList==null?0:friendListList.size();
    }

    @Override
    public FriendList getItem(int position) {
        return friendListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.myfriendlist, null);
        FriendList friendList =  getItem(position);
        //在view视图中查找id为image_photo的控件
        TextView myfriend_name= (TextView) view.findViewById(R.id.myfriend_name);
        TextView myfriend_friendid=(TextView)view.findViewById(R.id.myfriend_friendid);
        myfriend_name.setText(friendList.getName());
        String id=String.valueOf(friendList.getFriendId());
        myfriend_friendid.setText(id);

        return view;
    }
}
