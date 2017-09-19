package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zhuocong.comxzc9.R;

/**
 * Created by zhuocong on 2017/8/24.
 */

public class UserMyyueyingActivity extends Activity {

    private ImageView fmyueying_img_back;
    private LinearLayout fmyyueying_ll__start;
    private LinearLayout fmyyueying_ll__join;
    private LinearLayout fmyyueying_ll__finish;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_myyueying);
        initView();
    }

    public void initView(){

        fmyueying_img_back=(ImageView)this.findViewById(R.id.fmyueying_img_back);
        fmyyueying_ll__start=(LinearLayout)this.findViewById(R.id.fmyyueying_ll__start);
        fmyyueying_ll__join=(LinearLayout)this.findViewById(R.id.fmyyueying_ll__join);
        fmyyueying_ll__finish=(LinearLayout)this.findViewById(R.id.fmyyueying_ll__finish);

        fmyueying_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        fmyyueying_ll__start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UserMyyueyingActivity.this,UserMyyueyingBymeActivity.class);
                startActivity(intent);

            }
        });

        fmyyueying_ll__join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UserMyyueyingActivity.this,UserMyyueyingJoinActivity.class);
                startActivity(intent);

            }
        });

        fmyyueying_ll__finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(UserMyyueyingActivity.this,UserMyyueyingHistory.class);
                startActivity(intent);

            }
        });
    }



}
