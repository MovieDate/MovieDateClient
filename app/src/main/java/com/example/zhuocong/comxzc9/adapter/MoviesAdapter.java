package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.entity.CollectList;
import com.example.zhuocong.comxzc9.entity.yingxun.MoviesMovies;
import com.example.zhuocong.comxzc9.entity.yingxun.ResultMovies;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.MalformedJsonException;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/29.
 */

public class MoviesAdapter extends BaseAdapter {
    private List<MoviesMovies> moviesMoviesList;
    private LayoutInflater inflater;
    private Context context;

    public MoviesAdapter(List<MoviesMovies> moviesMoviesList, Context context){
        this.context = context;
        this.moviesMoviesList=moviesMoviesList;
        this.inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return moviesMoviesList==null?0:moviesMoviesList.size();
    }

    @Override
    public MoviesMovies getItem(int position) {
        return moviesMoviesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //加载布局为一个视图
        View view = inflater.inflate(R.layout.movieslist, null);
        final MoviesMovies moviesMovies =  getItem(position);
        //在view视图中查找id为image_photo的控件
        final ImageView moviepic = (ImageView) view.findViewById(R.id.moviepic);
        TextView moviename = (TextView) view.findViewById(R.id.moviename);
        TextView moviestar = (TextView) view.findViewById(R.id.moviestar);
        TextView moviemessage = (TextView) view.findViewById(R.id.moviemessage);
        moviename.setText(moviesMovies.getMovie_name());
        moviestar.setText("主演："+moviesMovies.getMovie_starring());
        moviestar.setEllipsize(TextUtils.TruncateAt.END);
        moviestar.setMaxLines(1);
        moviestar.setEllipsize(TextUtils.TruncateAt.END);
        moviemessage.setText(""+moviesMovies.getMovie_message());
        moviestar.setEllipsize(TextUtils.TruncateAt.END);
        moviemessage.setMaxLines(1);
        moviemessage.setEllipsize(TextUtils.TruncateAt.END);

        TextView picurl = (TextView) view.findViewById(R.id.picurl);
        picurl.setText(moviesMovies.getMovie_picture());
        TextView score = (TextView) view.findViewById(R.id.score);
        score.setText(moviesMovies.getMovie_score());
        TextView length = (TextView) view.findViewById(R.id.length);
        length.setText(moviesMovies.getMovie_length());
        TextView type = (TextView) view.findViewById(R.id.type);
        type.setText(moviesMovies.getMovie_tags());
        TextView time = (TextView) view.findViewById(R.id.time);
        time.setText(moviesMovies.getMovie_release_dat());
        TextView details = (TextView) view.findViewById(R.id.details);
        details.setText(moviesMovies.getMovie_description());
        TextView director = (TextView) view.findViewById(R.id.director);
        director.setText(moviesMovies.getMovie_director());






        Picasso.with(context).load(moviesMovies.getMovie_picture()).into(moviepic);
        return view;
    }







}
