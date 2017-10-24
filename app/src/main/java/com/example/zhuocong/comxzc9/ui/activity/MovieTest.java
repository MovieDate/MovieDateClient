package com.example.zhuocong.comxzc9.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.MoviesAdapter;
import com.example.zhuocong.comxzc9.adapter.MycollectAdapter;
import com.example.zhuocong.comxzc9.entity.CollectList;
import com.example.zhuocong.comxzc9.entity.yingxun.MoviesData;
import com.example.zhuocong.comxzc9.entity.yingxun.MoviesMovies;
import com.example.zhuocong.comxzc9.entity.yingxun.ResultMovies;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by zhuocong on 2017/9/20.
 */

public class MovieTest extends AppCompatActivity{
    private String moviceJsonUrl;
    private TextView moviename;
    private ListView movielistview;
    private ImageView mImageView1;
    private  List<ResultMovies> moviesListListStr;
    private  List<MoviesMovies> moviesMoviesList;
    private  ResultMovies resultMovies ;
    private MoviesAdapter moviesAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

       /* moviename = (TextView) findViewById(R.id.moviename);
        mImageView1 = (ImageView) findViewById(R.id.moviepic);*/
        movielistview=(ListView)findViewById(R.id.movielistview);

        moviceJsonUrl = "http://api.avatardata.cn/Movies/LookUp?key=c2d84e946e4a4f5ab106c6ab8112c42c&wd=江门万达影城&location=江门";
        initData();
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
                        Toast.makeText(MovieTest.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();

    }
    public void initpic(final String movicepicUrl) {
        //线程中不能更新图片
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    //网络中获取图片
                    Bitmap bitmap = null;
                    URL pictureUrl = new URL(movicepicUrl);
                    InputStream in = pictureUrl.openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                    //线程中不能更新图片,回到主线程中更新图片
                    Message message = new Message();
                    message.what = 1;
                    message.obj = bitmap;
                    handler.sendMessage(message);

                    in.close();
                } catch (MalformedJsonException e)
                {
                    e.printStackTrace();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
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
                    MoviesData moviesData=(MoviesData) gson.fromJson(msg.obj.toString(),MoviesData.class);
                    moviesListListStr=moviesData.getResult();
                    ResultMovies resultMovies=moviesData.getResult().get(0);
                    String moviesss= String.valueOf(resultMovies.getMovies());
                    List<MoviesMovies> moviesMoviesList=resultMovies.getMovies();

                    Log.d("testRun","==="+moviesMoviesList);
                    /*collectListList = gson.fromJson(collectListListStr, new TypeToken<List<CollectList>>() {}.getType());*/

                    /*moviesMoviesList=resultMovies.getMovies();*/
                    /*moviesMoviesList=*/
                   /* moviesAdapter = new MoviesAdapter(moviesMoviesList,MovieTest.this);
                    movielistview.setAdapter(moviesAdapter);*/
                    /*JSONArray jsonArray = gson.get;*//*msg.obj.toString().getJSONArray("review");*/



                   /* if (!moviesData.getReason().equals("Succes")) {
                        Toast.makeText(MovieTest.this, "没有找到数据，请重试！", Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(MainActivity.this, moviesData.getResult().get(0).getMovies().get(0).getMovie_name(), Toast.LENGTH_SHORT).show();
                        String str = "";
                        //拼接每部电影名字并显示
                        for (int i = 0; i < moviesData.getResult().get(0).getMovies().size(); i++) {
                           *//* Log.d("testRun","==="+moviesData.getResult().get(0).getMovies().get(i).getMovie_name());*//*
                            str+=moviesData.getResult().get(0).getMovies().get(i).getMovie_name()+" ";
                        }
                        moviename.setText(str);
                        initpic( moviesData.getResult().get(0).getMovies().get(0).getMovie_picture());
                        initpic(moviesData.getResult().get(1).getMovies().get(1).getMovie_picture());
                    }
*/
                    break;
                case 1:
                    //部署图片
                    mImageView1.setImageBitmap((Bitmap) msg.obj);
                    break;
            }
        }

    };
}
