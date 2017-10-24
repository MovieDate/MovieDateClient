package com.example.zhuocong.comxzc9.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.TransitionRes;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.MoviesAdapter;
import com.example.zhuocong.comxzc9.entity.yingxun.MoviesData;
import com.example.zhuocong.comxzc9.entity.yingxun.MoviesMovies;
import com.example.zhuocong.comxzc9.entity.yingxun.ResultMovies;
import com.example.zhuocong.comxzc9.ui.activity.MovieTest;
import com.example.zhuocong.comxzc9.ui.activity.MoviesDetails;
import com.example.zhuocong.comxzc9.ui.activity.YueyingDetails;
import com.example.zhuocong.comxzc9.ui.activity.ZhanLangActivity;
import com.example.zhuocong.comxzc9.ui.basefragment.BaseFragment;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.List;

import immortalz.me.library.TransitionsHeleper;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class YingXunFragment extends BaseFragment implements AdapterView.OnItemClickListener {
   /* private Button tv_yingxun_movietime;
    private Button tv_yingxun_future;
    private Button tv_yingxun_hotmovie;*/
    private TextView tv_yingxun_movietime;
    private TextView tv_yingxun_future;
    private TextView tv_yingxun_hotmovie;

    private String moviceJsonUrl;
    private String moviesname;
    private String scores;
    private String lengths;
    private String types;
    private String times;
    private String messages;
    private String detailss;
    private String directors;
    private String stars;
    private ListView movielistview;
    private List<ResultMovies> moviesListListStr;
    private  List<MoviesMovies> moviesMoviesList;
    private  ResultMovies resultMovies ;
    private MoviesAdapter moviesAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView=inflater.inflate(R.layout.fragment_yingxun, null);
        mContext=getActivity();
        findView();
        initView();
        return mBaseView;
    }

    @Override
    protected void findView() {
        tv_yingxun_movietime=(TextView)mBaseView.findViewById(R.id.tv_yingxun_movietime);
        tv_yingxun_future=(TextView)mBaseView.findViewById(R.id.tv_yingxun_future);
        tv_yingxun_hotmovie=(TextView)mBaseView.findViewById(R.id.tv_yingxun_hotmovie);
        movielistview=(ListView)mBaseView.findViewById(R.id.movielistview);

        moviceJsonUrl = "http://api.avatardata.cn/Movies/LookUp?key=c2d84e946e4a4f5ab106c6ab8112c42c&wd=江门万达影城&location=江门";
        initData();

        tv_yingxun_movietime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"功能有待更新，请期待！",Toast.LENGTH_SHORT).show();
            }
        });

        tv_yingxun_future.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"功能有待更新，请期待！",Toast.LENGTH_SHORT).show();
            }
        });

        movielistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //获取数值传到详情页面显示
                TextView moviename = (TextView) view.findViewById(R.id.moviename);
                moviesname=moviename.getText().toString().trim();
                TextView picurl = (TextView) view.findViewById(R.id.picurl);
                String url=picurl.getText().toString().trim();
                Log.d("testRun","moviesname=="+moviesname);
                TextView score = (TextView) view.findViewById(R.id.score);
                scores=score.getText().toString().trim();
                TextView type = (TextView) view.findViewById(R.id.type);
                TextView length = (TextView) view.findViewById(R.id.length);
                lengths=length.getText().toString().trim();
                types=type.getText().toString().trim();
                TextView time = (TextView) view.findViewById(R.id.time);
                times=time.getText().toString().trim();
                TextView moviemessage = (TextView) view.findViewById(R.id.moviemessage);
                messages=moviemessage.getText().toString().trim();
                TextView details = (TextView) view.findViewById(R.id.details);
                detailss=details.getText().toString().trim();
                TextView director = (TextView) view.findViewById(R.id.director);
                directors=director.getText().toString().trim();
                TextView moviestar = (TextView) view.findViewById(R.id.moviestar);
                stars=moviestar.getText().toString().trim();
                Intent intent=new Intent();
                intent.putExtra("moviesname",moviesname);
                intent.putExtra("scores",scores);
                intent.putExtra("lengths",lengths);
                intent.putExtra("types",types);
                intent.putExtra("times",times);
                intent.putExtra("detailss",detailss);
                intent.putExtra("directors",directors);
                intent.putExtra("messages",messages);
                intent.putExtra("stars",stars);
                intent.setClass(getActivity(),MoviesDetails.class);
                TransitionsHeleper.startActivity(getActivity(),intent,view,url);

            }
        });

    }


    private void initData(){

        //网络请求比较耗时，在线程中请求网络，但线程中不能更新UI，需要传送数据到主线程中更新UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                //post方式连接  url
                OkHttpUtils.get(moviceJsonUrl, new OkHttpUtils.ResultCallback() {
                    @Override
                    public void onSuccess(Object response) {//url请求成功返回的数据
                        Message message = new Message();
                        message.what = 0;
                        message.obj = response;
//                        userDataStr = response.toString();
                        Log.d("testRun", "后台返回给我们的数据userDataStr=="+response);
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(Exception e) {//url请求失败
                        Log.d("testRun", "服务器连接失败，请重试！{------");
                        Toast.makeText(getActivity(), "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    //Gson解析数据
                    Gson gson=new Gson();
                    msg.obj.toString();
                    try {
                        MoviesData moviesData=(MoviesData) gson.fromJson(msg.obj.toString(),MoviesData.class);
                        moviesListListStr=moviesData.getResult();
                        ResultMovies resultMovies=moviesData.getResult().get(0);
                        String moviesss= String.valueOf(resultMovies.getMovies());
                        List<MoviesMovies> moviesMoviesList=resultMovies.getMovies();
                        Log.d("testRun","==="+moviesMoviesList);
                        moviesAdapter = new MoviesAdapter(moviesMoviesList,getActivity());
                        movielistview.setAdapter(moviesAdapter);
                    }catch (JsonSyntaxException e1) {
                        Toast.makeText(getActivity(), "内部数据解析异常", Toast.LENGTH_SHORT).show();
                        Log.e("JsonSyntaxException",""+e1.getMessage());
                        e1.printStackTrace();
                    }


                    break;

            }
        }

    };
    @Override
    protected void initView() {


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

}
