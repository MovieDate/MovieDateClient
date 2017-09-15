package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/9/11.
 */

public class AddActivity extends Activity{

    private EditText add_et_details;
    private TextView add_tv_search;
    private ImageView add_img_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();

    }

    public void initView(){
        add_et_details=(EditText)this.findViewById(R.id.add_et_details);
        add_tv_search=(TextView)this.findViewById(R.id.add_tv_search);
        add_img_back=(ImageView)this.findViewById(R.id.add_img_back);

        add_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        add_tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });

    }

    public void initData(){
        String details=add_et_details.getText().toString().trim();
        Log.d("testRun","details="+details);

        final List<OkHttpUtils.Param> list= new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param detailsParam =new OkHttpUtils.Param("phone",details);
        list.add(detailsParam);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //post方式
                OkHttpUtils.post(APPConfig.findUserByPhone, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        String result=response.toString();
                        Log.d("testRun","result="+result);
                        if (result.equals("nodata")){
                            Toast.makeText(AddActivity.this,"该用户不存在，请重新输入！",Toast.LENGTH_LONG).show();
                        }else {
                            Gson gson =new Gson();
                            User userInof= gson.fromJson(result,User.class);
                            String id= String.valueOf(userInof.getId());
                            Intent intent=new Intent();
                            intent.setClass(AddActivity.this,AllPersonInfo.class);
                            intent.putExtra("id",id);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("testRun", "服务器连接失败，请重试！{------");
                        Toast.makeText(AddActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                },list);

            }
        }).start();
    }


}
