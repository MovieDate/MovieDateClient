package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.ApplyViewAdapter;
import com.example.zhuocong.comxzc9.adapter.YueyingFragmentAdapter;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.PersonList;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/9/2.
 */

public class ApplyViewActivity extends Activity{
    private ImageView apply_img_back;
    private ListView applylistview;
    private Button apply_bt_want;
    private List<PersonList> personListList;
    Gson gson=new Gson();
    private ApplyViewAdapter applyViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applyview);
        initView();
        initMotion();
    }

    public void initView(){
        apply_img_back=(ImageView)this.findViewById(R.id.apply_img_back);
        applylistview=(ListView)this.findViewById(R.id.applylistview);


        apply_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        applylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView person_friendid=(TextView)view.findViewById(R.id.person_friendid);
                String id=person_friendid.getText().toString().trim();
                Log.d("testRun","friendId222 = "+id);
                Intent intent=new Intent();
                intent.setClass(ApplyViewActivity.this, AllPersonInfo.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    public void initMotion(){
        String postId=this.getIntent().getStringExtra("postId2");
        Log.d("testRun","postId = "+postId);

        final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param postIdParam =new OkHttpUtils.Param("postId",postId);
        list.add(postIdParam);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //post
                OkHttpUtils.post(APPConfig.findPersonByPostId, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {
                        Message message=new Message();
                        message.what=0;
                        message.obj=response.toString();
                        String ApplyDataStr=response.toString();
                        Log.d("testRun","ApplyDataStr="+ApplyDataStr);
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(Exception e) {

                    }
                },list);

            }
        }).start();

    }

    private Handler handler=new Handler(){
      @Override
      public void handleMessage(Message msg) {
          switch (msg.what) {
              case 0:
                  String ApplyDataStr=msg.obj.toString();
                  Log.d("testRun","ApplyDataStr2="+ApplyDataStr);
                  if (ApplyDataStr.equals("nodata")) {
                      Toast.makeText(ApplyViewActivity.this, "还没有用户发起约影", Toast.LENGTH_SHORT).show();
                  } else {
                      //gson解析数据时，
                      try {
                          personListList = gson.fromJson(ApplyDataStr, new TypeToken<List<PersonList>>() {}.getType());
                          if (personListList != null && personListList.size()> 0) {
                              Log.d("personListList", "personListList=" + personListList);
                              applyViewAdapter = new ApplyViewAdapter(personListList,ApplyViewActivity.this);
                              applylistview.setAdapter(applyViewAdapter);
                          } else {

                          }
                      } catch (JsonSyntaxException e1) {
                          Toast.makeText(ApplyViewActivity.this, "内部数据解析异常", Toast.LENGTH_SHORT).show();
                          Log.e("JsonSyntaxException",""+e1.getMessage());
                          e1.printStackTrace();
                      }
                  }

                  break;
          }
      }
    };


}
