package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.Collect;

import java.util.List;

/**
 * Created by zhuocong on 2017/8/29.
 */

public class MycollectAdapter extends BaseAdapter {
    private List<Collect> collectList;
    private LayoutInflater inflater;

    public MycollectAdapter(List<Collect> collectList, Context context){
        this.collectList=collectList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return collectList==null?0:collectList.size();
    }

    @Override
    public Collect getItem(int position) {
        return collectList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.collectlist, null);
        Collect collect =  getItem(position);
        //在view视图中查找id为image_photo的控件
        TextView collectlist_tv_postid = (TextView) view.findViewById(R.id.collectlist_tv_postid);
        TextView collectlist_tv_personid = (TextView) view.findViewById(R.id.collectlist_tv_personid);
        TextView collectlist_tv_collecttime = (TextView) view.findViewById(R.id.collectlist_tv_collecttime);
        String id= String.valueOf(collect.getId());
        collectlist_tv_postid.setText(id);
        collectlist_tv_personid.setText("收藏人："+collect.getCollecterId());
        collectlist_tv_collecttime.setText("时间："+collect.getCollectTime());

        return view;
    }


}
