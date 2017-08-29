package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;

/**
 * Created by zhuocong on 2017/8/24.
 */

public class UserSettingActivity extends Activity {

    private ImageView img_back;
    private LinearLayout ll_help;
    private LinearLayout ll_about;
    private LinearLayout ll_assist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_setting);
        initView();

    }

    public void initView(){
        img_back=(ImageView)this.findViewById(R.id.fsettings_img_back);
        ll_help=(LinearLayout)this.findViewById(R.id.fsettings_ll_help);
        ll_about=(LinearLayout)this.findViewById(R.id.fsettings_ll_about);
        ll_assist=(LinearLayout)this.findViewById(R.id.fsettings_ll_assist);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent();
                intent.setClass(UserSettingActivity.this,MainActivity.class);
                startActivity(intent);*/
                finish();
            }
        });
        ll_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettingActivity.this,"此功能有待更新",Toast.LENGTH_SHORT).show();
            }
        });
        ll_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettingActivity.this,"此功能有待更新",Toast.LENGTH_SHORT).show();
            }
        });
        ll_assist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(UserSettingActivity.this,"此功能有待更新",Toast.LENGTH_SHORT).show();
            }
        });
    }



}
