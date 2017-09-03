package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.entity.Review;
import com.example.zhuocong.comxzc9.entity.ReviewList;

import java.util.List;

/**
 * Created by zhuocong on 2017/9/2.
 */

public class ReviewAdapter extends BaseAdapter{
    private List<ReviewList> reviewListList;
    private LayoutInflater inflater;

    public ReviewAdapter(List<ReviewList> reviewListList, Context context){
        this.reviewListList=reviewListList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return reviewListList == null ? 0 : reviewListList.size();
    }

    @Override
    public ReviewList getItem(int position) {
        return reviewListList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.activity_reviewlist, null);
        ReviewList reviewListList =  getItem(position);
        //在view视图中查找的控件
        TextView review_name=(TextView) view.findViewById(R.id.review_name);
        TextView review_friendid=(TextView) view.findViewById(R.id.review_friendid);
        TextView review_tv_details=(TextView) view.findViewById(R.id.review_tv_details);
        TextView review_tv_reviewTime=(TextView) view.findViewById(R.id.review_tv_reviewTime);
        review_name.setText(reviewListList.getName());
        String id= String.valueOf(reviewListList.getPostPersonId());
        review_friendid.setText(id);
        Log.d("testRun","id="+id);
        review_tv_details.setText(reviewListList.getReviewDetails());
        review_tv_reviewTime.setText(reviewListList.getReviewTime());

        return view;
    }
}
