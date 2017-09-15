package com.example.zhuocong.comxzc9.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/9/14.
 */

public class AddConfirmActivity extends AddActivity{

    private ImageView addconfirm_img_back;
    private TextView addconfirm_tv_send;
    private EditText addconfirm_et_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_confirm);

        init();

    }

    private void init(){
        addconfirm_img_back=(ImageView)this.findViewById(R.id.addconfirm_img_back);
        addconfirm_tv_send=(TextView)this.findViewById(R.id.addconfirm_tv_send);
        addconfirm_et_details=(EditText)this.findViewById(R.id.addconfirm_et_details);
        addconfirm_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addconfirm_tv_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addfriend();
                addfriendbyhx();
            }
        });
    }

    private void addfriend(){
        String friendId=this.getIntent().getStringExtra("friendId");
        Log.d("testRun","friendId="+friendId);
        String myId=this.getIntent().getStringExtra("myId");
        Log.d("TestRun","myId="+myId);
        final List<OkHttpUtils.Param> list = new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param myIdParam = new OkHttpUtils.Param("myId", myId);
        OkHttpUtils.Param idParam= new OkHttpUtils.Param("friendId",friendId);
        list.add(myIdParam);
        list.add(idParam);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //post方式
                OkHttpUtils.post(APPConfig.addFriendByMyId, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        String result =response.toString();
                        Log.d("TestRun","result3=="+result);
                        if (result.equals("add_success")){
                            Toast.makeText(AddConfirmActivity.this,"添加成功！",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(AddConfirmActivity.this,"添加失败！",Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("postDateStr","服务器连接失败，请重试！");
                        Toast.makeText(AddConfirmActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                },list);
            }
        }).start();
    }

    /*环信添加好友*/
    private void addfriendbyhx(){
        String phone=this.getIntent().getStringExtra("phone");
        Log.d("testRun","phone="+phone);
        String details=addconfirm_et_details.getText().toString().trim();
        Log.d("testRun","details="+details);
        try {
            EMClient.getInstance().contactManager().addContact(phone,details);
            Toast.makeText(AddConfirmActivity.this,"HX添加成功！",Toast.LENGTH_SHORT).show();
        } catch (HyphenateException e) {
            e.printStackTrace();
            Toast.makeText(AddConfirmActivity.this,"HX添加不成功！",Toast.LENGTH_SHORT).show();
        }
    }
}
