package com.example.zhuocong.comxzc9.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.utils.DateTimeDialogUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuocong on 2017/9/6.
 */

public class DataSelect extends FragmentActivity implements View.OnClickListener, DateTimeDialogUtils.MyOnDateSetListener{
    private Button mButton1, mButton2, mButton3, mButton4, mButton5;
    private TextView time;
    // 日期 格式化 工具
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:00");
    private DateTimeDialogUtils dateTimeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_main);

        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mButton5 = (Button) findViewById(R.id.button5);
        time = (TextView) findViewById(R.id.time);

        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
        mButton5.setOnClickListener(this);




        dateTimeDialog = new DateTimeDialogUtils(this, null, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button7:
                showAll();
                break;

        }
    }

    private void showAll() {
        dateTimeDialog.hideOrShow();
    }



    @Override
    public void onDateSet(Date date) {
        time.setText(mFormatter.format(date) + "");
        Log.d("testRun","time="+mFormatter.format(date));
    }
}
