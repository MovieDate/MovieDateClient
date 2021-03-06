package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.DateTimeDialogUtils;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/27.
 *
 */

public class StarPostActivity extends FragmentActivity implements View.OnClickListener, DateTimeDialogUtils.MyOnDateSetListener {
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:00");
    private DateTimeDialogUtils dateTimeDialog;

    private ImageView starpost_img_back;
    private TextView starpost_tv_post;
    private EditText starpost_et_moviename;
    private EditText starpost_et_site;
    private Button starpost_et_movietime;
    private RadioButton starpost_rb_gender0;
    private RadioButton starpost_rb_gender1;
    private RadioButton starpost_rb_gender2;
    private RadioButton starpost_rb_movietype0;
    private RadioButton starpost_rb_movietype1;
    private EditText starpost_rb_details;

    private String userDataStr;
    private User userInfo;

    private String movieName;
    private String site;
    private String postPersonId;
    private String postTime;
    private String movieTime;
    private String sex;
    private String movieType;
    private String details;

    private Spinner spinner;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    private Spinner spinner2;
    private List<String> data_list2;
    private ArrayAdapter<String> arr_adapter2;

    private String site1;
    private String site2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置页面
        setContentView(R.layout.activity_starpost);
        dateTimeDialog = new DateTimeDialogUtils(this, null, this);
        init();
        initMotion();

        //数据
        data_list = new ArrayList<String>();
        data_list.add("江门");

        data_list2 = new ArrayList<String>();
        data_list2.add("蓬江区");
        data_list2.add("外海区");
        data_list2.add("新会");
        data_list2.add("台山");

        //适配器
        arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        arr_adapter2= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list2);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arr_adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        spinner2.setAdapter(arr_adapter2);


    }

    public void init(){
        userDataStr = SharedPrefsUtil.getValue(StarPostActivity.this, APPConfig.USERDATA,"");
        Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);

        //初始化控件
        starpost_img_back=(ImageView)findViewById(R.id.starpost_img_back);
        starpost_tv_post=(TextView)findViewById(R.id.starpost_tv_post);
        starpost_et_moviename=(EditText)findViewById(R.id.starpost_et_moviename);
        /*starpost_et_site=(EditText)findViewById(R.id.starpost_et_site);*/
        starpost_et_movietime=(Button) findViewById(R.id.starpost_et_movietime);
        starpost_rb_gender0=(RadioButton)findViewById(R.id.starpost_rb_gender0);
        starpost_rb_gender1=(RadioButton)findViewById(R.id.starpost_rb_gender1);
        starpost_rb_gender2=(RadioButton)findViewById(R.id.starpost_rb_gender2);
        starpost_rb_movietype0=(RadioButton)findViewById(R.id.starpost_rb_movietype0);
        starpost_rb_movietype1=(RadioButton)findViewById(R.id.starpost_rb_movietype1);
        starpost_rb_details=(EditText)findViewById(R.id.starpost_rb_details);
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner2=(Spinner)findViewById(R.id.spinner2);

        starpost_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        starpost_et_movietime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAll();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                site1=(String)spinner.getSelectedItem();
                Toast.makeText(StarPostActivity.this,"item="+site1,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                site2=(String)spinner2.getSelectedItem();
                Toast.makeText(StarPostActivity.this,"item2="+site2,Toast.LENGTH_SHORT).show();
                site=site1+site2;
                Toast.makeText(StarPostActivity.this,"site="+site,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    public void initMotion(){
        starpost_tv_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取数据
                postPersonId= String.valueOf(userInfo.getId());
                Log.d("test","id"+postPersonId);
                movieName=starpost_et_moviename.getText().toString().trim();
                site=site1+"市"+site2;
                Log.d("test","site="+site);
                /*movieTime=starpost_et_movietime.getText().toString().trim();*/
                if (starpost_rb_gender0.isChecked()){
                    sex="0";
                }else if (starpost_rb_gender1.isChecked()){
                    sex="1";
                }else if (starpost_rb_gender2.isChecked()){
                    sex="2";
                }
                if (starpost_rb_movietype0.isChecked()){
                    movieType="0";
                }else if (starpost_rb_movietype1.isChecked()){
                    movieType="1";
                }
                details=starpost_rb_details.getText().toString().trim();
                Date date=new Date();
                DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                postTime= format.format(date);
                Log.d("test","postime:"+postTime);

                //验证输入项
                if(movieName.equals("")||site.equals("")||movieTime.equals("")||details.equals("")){
                    Toast.makeText(StarPostActivity.this,"信息未输入完全，请再次确认",Toast.LENGTH_SHORT).show();
                }else{
                    final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
                    OkHttpUtils.Param postPersonIdParam =new OkHttpUtils.Param("postPersonId",postPersonId);
                    OkHttpUtils.Param postTimeParam =new OkHttpUtils.Param("postTime",postTime);
                    OkHttpUtils.Param movieNameParam =new OkHttpUtils.Param("movieName",movieName);
                    OkHttpUtils.Param siteParam =new OkHttpUtils.Param("site",site);
                    OkHttpUtils.Param movieTimeParam =new OkHttpUtils.Param("movieTime",movieTime);
                    OkHttpUtils.Param sexParam =new OkHttpUtils.Param("sex",sex);
                    OkHttpUtils.Param movieTypeParam =new OkHttpUtils.Param("movieType",movieType);
                    OkHttpUtils.Param detailsParam =new OkHttpUtils.Param("details",details);
                    list.add(postPersonIdParam);
                    list.add(postTimeParam);
                    list.add(movieNameParam);
                    list.add(siteParam);
                    list.add(movieTimeParam);
                    list.add(sexParam);
                    list.add(movieTypeParam);
                    list.add(detailsParam);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //post方式获取
                            OkHttpUtils.post(APPConfig.post, new OkHttpUtils.ResultCallback() {
                                @Override
                                public void onSuccess(Object response) {
                                    Message message = new Message();
                                    message.what = 0;
                                    message.obj = response;
                                    String result = response.toString();
                                    if (result.equals("add_success")){
                                        handler.sendMessage(message);
                                    }else {
                                        Toast.makeText(StarPostActivity.this,"发布失败！",Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Log.d("testRun", "请求失败loginActivity----new Thread(new Runnable() {------");
                                    Toast.makeText(StarPostActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                                }
                            },list);

                        }
                    }).start();

                }
            }
        });

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String result = (String)msg.obj;
                    if (result.equals("add_success") ){
                        Toast.makeText(StarPostActivity.this,"发布成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        intent.setClass(StarPostActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(StarPostActivity.this,"发布失败！",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }

    };

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.starpost_et_movietime:

                showAll();
                break;

        }

    }

    private void showAll() {
        dateTimeDialog.hideOrShow();
    }


    @Override
    public void onDateSet(Date date) {
        movieTime= mFormatter.format(date);
        starpost_et_movietime.setText(movieTime);
        Log.d("testRun","movieTime="+movieTime);

    }
}
