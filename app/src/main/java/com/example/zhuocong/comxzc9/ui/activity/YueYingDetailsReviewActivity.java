package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;

/**
 * Created by zhuocong on 2017/9/18.
 */

public class YueYingDetailsReviewActivity extends Activity {
    private ImageView details_review_img_back;
    private TextView details_review_name;
    private TextView details_review_friendid;
    private TextView details_review_tv_details;
    private TextView details_review_tv_reviewTime;
    private ListView detailsreviewlistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_yueying_details_review);
        initView();
        initData();

    }

    public void initView(){

        details_review_img_back=(ImageView)this.findViewById(R.id.details_review_img_back);
        details_review_name=(TextView)this.findViewById(R.id.details_review_name);
        details_review_friendid=(TextView)this.findViewById(R.id.details_review_friendid);
        details_review_tv_reviewTime=(TextView)this.findViewById(R.id.details_review_tv_reviewTime);
        detailsreviewlistview=(ListView)this.findViewById(R.id.detailsreviewlistview);
        details_review_tv_details=(TextView)this.findViewById(R.id.details_review_tv_details);
    }

    public void initData(){
        Intent intent=new Intent();
        String name=this.getIntent().getStringExtra("name");
        String friendid=this.getIntent().getStringExtra("id");
        String details=this.getIntent().getStringExtra("details");
        String reviewTime=this.getIntent().getStringExtra("reviewTime");

        details_review_name.setText(name);
        details_review_friendid.setText(friendid);
        details_review_tv_details.setText(details);
        details_review_tv_reviewTime.setText(reviewTime);

    }
}
