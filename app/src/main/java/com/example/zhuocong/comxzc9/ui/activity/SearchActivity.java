package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.SearchAdapter;
import com.example.zhuocong.comxzc9.adapter.YueyingFragmentAdapter;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.PostList;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/28.
 */

public class SearchActivity extends Activity {
    private EditText search_et_details;
    private ImageView search_img_back;
    private ListView searchlistview;
    private TextView search_tv_search;
    private List<PostList> postListList;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initData();
    }

    public void initView(){
        //初始化控件
        search_et_details =(EditText)this.findViewById(R.id.search_et_details);
        search_img_back=(ImageView)this.findViewById(R.id.search_img_back);
        searchlistview=(ListView)this.findViewById(R.id.searchlistview);
        search_tv_search=(TextView) this.findViewById(R.id.search_tv_search);

        //设置返回键点击事件
        search_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        searchlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tv_id=(TextView)view.findViewById(R.id.yueyinglist_tv_id);
                String id=tv_id.getText().toString().trim();
                Log.d("testRun","id = "+id);
                /*SharedPrefsUtil.putValue(getActivity(), APPConfig.PID,id);*/
                Intent intent=new Intent();
                intent.setClass(SearchActivity.this, YueyingDetails.class);
                intent.putExtra("postId",id);
                startActivity(intent);
            }
        });
    }

    public void initData(){
        search_tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String details=search_et_details.getText().toString().trim();
                Log.d("testRun","details="+details);
                final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
                OkHttpUtils.Param detailsParam = new OkHttpUtils.Param("details",details);
                list.add(detailsParam);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //POST链接
                        OkHttpUtils.post(APPConfig.findposttab, new OkHttpUtils.ResultCallback() {
                            @Override
                            public void onSuccess(Object response) {
                                Message message=new Message();
                                message.what=0;
                                message.obj=response.toString();
                                String searchDataStc=response.toString();
                                Log.d("testRun","searchDataStc="+searchDataStc);
                                handler.sendMessage(message);

                            }

                            @Override
                            public void onFailure(Exception e) {
                                Log.d("testRun", "服务器连接失败，请重试！{------");
                                Toast.makeText(SearchActivity.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                            }
                        },list);
                    }
                }).start();
            }
        });
    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    String searchDataStr = msg.obj.toString();
                    if (searchDataStr.equals("nodata")){
                        Toast.makeText(SearchActivity.this, "抱歉！没有搜索到你所需要的内容", Toast.LENGTH_SHORT).show();
                    }else {
                        try {
                        Gson gson = new Gson();
                        postListList = gson.fromJson(searchDataStr, new TypeToken<List<PostList>>() {
                        }.getType());
                        if (postListList != null && postListList.size() > 0) {
                            Log.d("postListList", "postListList=" + postListList);
                            searchAdapter = new SearchAdapter(postListList, SearchActivity.this);
                            searchlistview.setAdapter(searchAdapter);
                        } else {

                        }
                        } catch (JsonSyntaxException e1) {
                            Toast.makeText(SearchActivity.this, "内部数据解析异常", Toast.LENGTH_SHORT).show();
                            Log.e("JsonSyntaxException",""+e1.getMessage());
                            e1.printStackTrace();
                        }
                    }

                    break;
            }
        }
    };
}
