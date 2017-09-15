package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;

/**
 * Created by zhuocong on 2017/9/15.
 */

public class ZhanLangActivity extends Activity {
    private TextView buy;
    private ImageView zhanlang_img_back;
    private TextView more;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhanlang);
        buy=(TextView)this.findViewById(R.id.buy);
        zhanlang_img_back=(ImageView)this.findViewById(R.id.zhanlang_img_back);
        more=(TextView)this.findViewById(R.id.more);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ZhanLangActivity.this,"购买通道暂未完善，有待更新！",Toast.LENGTH_SHORT).show();
            }
        });
        zhanlang_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ZhanLangActivity.this,"未完善，有待更新！",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
