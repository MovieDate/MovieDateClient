package com.example.zhuocong.comxzc9.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.entity.Review;
import com.example.zhuocong.comxzc9.entity.ReviewList;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.ui.activity.MyUserInfoActivity;
import com.example.zhuocong.comxzc9.ui.activity.YueyingDetails;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/9/2.
 */

public class ReviewAdapter extends BaseAdapter{
    private List<ReviewList> reviewListList;
    private LayoutInflater inflater;
    private ViewHolder holder;
    private String userId;
    private String postId;
    private String postPersonId;
    private String reviewDetails;
    private String reviewTime;
    private int saveposition;
    private Context context;

    public ReviewAdapter(List<ReviewList> reviewListList, Context context,String userId,String postId){
        this.context = context;
        this.reviewListList=reviewListList;
        this.inflater=LayoutInflater.from(context);
        this.postId=postId;
        this.userId=userId;
        //viewholder是优化listview的
        holder = new ViewHolder();
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
       /* View view = inflater.inflate(R.layout.activity_reviewlist, null);
        ReviewList reviewListList =  getItem(position);
        //在view视图中查找的控件
        TextView reviewlist_account=(TextView) view.findViewById(R.id.reviewlist_account);
        TextView review_name=(TextView) view.findViewById(R.id.review_name);
        TextView review_friendid=(TextView) view.findViewById(R.id.review_friendid);
        TextView review_tv_details=(TextView) view.findViewById(R.id.review_tv_details);
        TextView review_tv_reviewTime=(TextView) view.findViewById(R.id.review_tv_reviewTime);
        TextView review_tv_delete=(TextView) view.findViewById(R.id.review_tv_delete);
        review_name.setText(reviewListList.getName());
        reviewlist_account.setText("第"+(position+1)+"楼");
        postPersonId= String.valueOf(reviewListList.getPostPersonId());
        review_friendid.setText(postPersonId);
        Log.d("testRun","postPersonId="+postPersonId);
        review_tv_details.setText(reviewListList.getReviewDetails());
        review_tv_reviewTime.setText(reviewListList.getReviewTime());
        Log.d("testRun","postID2222==="+postId);
        if (userId.equals(postPersonId)){
            review_tv_delete.setVisibility(View.VISIBLE);
        }
        reviewDetails=review_tv_details.getText().toString().trim();
        Log.d("testRun","reviewDetails==="+reviewDetails);
        reviewTime=review_tv_reviewTime.getText().toString().trim();
        Log.d("testRun","reviewTime==="+reviewTime);*/

        convertView = inflater.inflate(R.layout.activity_reviewlist, null);
        holder.reviewlist_account=(TextView) convertView.findViewById(R.id.reviewlist_account);
        holder.review_name=(TextView) convertView.findViewById(R.id.review_name);
        holder.review_friendid=(TextView) convertView.findViewById(R.id.review_friendid);
        holder.review_tv_details=(TextView) convertView.findViewById(R.id.review_tv_details);
        holder.review_tv_reviewTime=(TextView) convertView.findViewById(R.id.review_tv_reviewTime);
        holder.review_tv_delete=(TextView) convertView.findViewById(R.id.review_tv_delete);

        holder.review_name.setText(reviewListList.get(position).getName());
        Log.d("testRun","review_name"+position+"====="+reviewListList.get(position).getName());
        holder.reviewlist_account.setText("第"+(position+1)+"楼");
        postPersonId= String.valueOf(reviewListList.get(position).getPostPersonId());
        Log.d("testRun","postPersonId"+position+"====="+reviewListList.get(position).getPostPersonId());
        holder.review_friendid.setText(postPersonId);
        Log.d("testRun","postPersonId="+postPersonId);
        holder.review_tv_details.setText(reviewListList.get(position).getReviewDetails());
        holder.review_tv_reviewTime.setText(reviewListList.get(position).getReviewTime());
        Log.d("testRun","postID2222==="+postId);
        if (userId.equals(postPersonId)){
            holder.review_tv_delete.setVisibility(View.VISIBLE);
        }
        reviewDetails=holder.review_tv_details.getText().toString().trim();
        Log.d("testRun","reviewDetails==="+reviewDetails);
        reviewTime=holder.review_tv_reviewTime.getText().toString().trim();
        Log.d("testRun","reviewTime==="+reviewTime);

        holder.review_tv_delete.setTag(position);// TODO 这里是关键

       holder.review_tv_delete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveposition = Integer.parseInt(view.getTag().toString());// TODO
                Log.d("testRun", "saveposition=" + saveposition);
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("温馨提示");
                dialog.setMessage("是否删除该评论？");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
                        OkHttpUtils.Param postIdParam = new OkHttpUtils.Param("postId", postId);
                        OkHttpUtils.Param postPersonIdParam = new OkHttpUtils.Param("postPersonId", postPersonId);
                        OkHttpUtils.Param reviewDetailsParam = new OkHttpUtils.Param("reviewDetails", reviewListList.get(saveposition).getReviewDetails());
                        OkHttpUtils.Param reviewTimeParam = new OkHttpUtils.Param("reviewTime", reviewListList.get(saveposition).getReviewTime());
                        list.add(postIdParam);
                        list.add(postPersonIdParam);
                        list.add(reviewDetailsParam);
                        list.add(reviewTimeParam);
                        Log.d("testRun", "postId==" + postId + "postPersonId==" + postPersonId +
                                "reviewDetails==" + reviewListList.get(saveposition).getReviewDetails()
                                + "reviewTime==" + reviewListList.get(saveposition).getReviewTime());

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //post
                                OkHttpUtils.post(APPConfig.deleteReviewByReviewDetails, new OkHttpUtils.ResultCallback() {
                                    @Override
                                    public void onSuccess(Object response) {
                                        String DElresult = response.toString();
                                        Log.d("testRun", "DElresult" + DElresult);
                                        if (DElresult.equals("del_success")) {
                                            Log.d("testRun", "success");
                                            reviewListList.remove(saveposition);
                                            notifyDataSetChanged();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Exception e) {
                                        Toast.makeText(context, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                                        Log.d("testRun", "请求失败loginActivity----new Thread(new Runnable() {------");
                                    }
                                }, list);
                            }
                        }).start();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
            }
        });

        return convertView;
    }


    private class ViewHolder {
        TextView reviewlist_account;
        TextView review_name;
        TextView review_friendid;
        TextView review_tv_details;
        TextView review_tv_reviewTime;
        TextView review_tv_delete;
    }


}
