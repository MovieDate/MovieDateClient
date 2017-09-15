package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.Collect;
import com.example.zhuocong.comxzc9.entity.CollectList;
import com.example.zhuocong.comxzc9.entity.Post;

import java.util.List;

/**
 * Created by zhuocong on 2017/8/29.
 */

public class MycollectAdapter extends BaseAdapter {
    private List<CollectList> collectListList;
    private LayoutInflater inflater;

    public MycollectAdapter(List<CollectList> collectListList, Context context){
        this.collectListList=collectListList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return collectListList==null?0:collectListList.size();
    }

    @Override
    public CollectList getItem(int position) {
        return collectListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.collectlist, null);
        CollectList collectList =  getItem(position);
        //在view视图中查找id为image_photo的控件
        TextView collectlist_tv_nickname = (TextView) view.findViewById(R.id.collectlist_tv_nickname);
        TextView collectlist_tv_movieName = (TextView) view.findViewById(R.id.collectlist_tv_movieName);
        TextView collectlist_tv_site = (TextView) view.findViewById(R.id.collectlist_tv_site);
        TextView collectlist_tv_details = (TextView) view.findViewById(R.id.collectlist_tv_details);
        TextView collectlist_tv_collecttime = (TextView) view.findViewById(R.id.collectlist_tv_collecttime);
        TextView collectlist_tv_postid = (TextView) view.findViewById(R.id.collectlist_tv_postid);
        String id= String.valueOf(collectList.getPostId());

        collectlist_tv_postid.setText(id);
        Log.d("testRun","name="+collectList.getNickname());
        if (collectList.getNickname().equals("未填写")){
            collectlist_tv_nickname.setText(collectList.getName());
            Log.d("testRun","Nickname"+collectList.getName());
        }else{
            collectlist_tv_nickname.setText(collectList.getNickname());
            Log.d("testRun","Nickname"+collectList.getNickname());
        }
        collectlist_tv_movieName.setText("约影影片:\n"+collectList.getMovieName());
        collectlist_tv_site.setText(collectList.getSite());
        collectlist_tv_details.setText(collectList.getDetails());
        collectlist_tv_collecttime.setText("收藏于:"+collectList.getCollectTime());

        return view;
    }


}
