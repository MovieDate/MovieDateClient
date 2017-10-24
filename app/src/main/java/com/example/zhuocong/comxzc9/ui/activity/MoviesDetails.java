package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.squareup.picasso.Picasso;

import immortalz.me.library.TransitionsHeleper;
import immortalz.me.library.bean.InfoBean;
import immortalz.me.library.method.ColorShowMethod;

/**
 * Created by zhuocong on 2017/10/20.
 */

public class MoviesDetails extends Activity{

    private ImageView moviedetails_picture;
    private ImageView zhanlang_img_back;
    private LinearLayout moviedetails_yugao;
    private TextView moviedetails_moviesname;
    private TextView moviedetails_moviesscore;
    private TextView moviedetails_movieslength;
    private TextView moviedetails_moviestype;
    private TextView moviedetails_moviestime;
    private TextView moviedetails_moviesmessge;
    private TextView moviedetails_moviesdetails;
    private TextView moviedetails_director;
    private TextView moviedetails_actor;
    private LinearLayout moviedetails_haibao;
    private LinearLayout moviedetails_about;
    private LinearLayout moviedetails_paihang;
    private Button moviedetails_tobuy;

    private String moviesname;
    private String scores;
    private String lengths;
    private String types;
    private String times;
    private String messages;
    private String detailss;
    private String directors;
    private String stars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviesdetails);

        initData();
        initView();

        TransitionsHeleper.getInstance()
                .setShowMethod(new ColorShowMethod(R.color.gray,
                        R.color.gray_font) {
                    @Override
                    public void loadCopyView(InfoBean bean, ImageView copyView) {
                        Picasso.with(MoviesDetails.this).load(bean.getImgUrl()).into(moviedetails_picture);
                    }

                    @Override
                    public void loadTargetView(InfoBean bean, ImageView targetView) {
                        Picasso.with(MoviesDetails.this).load(bean.getImgUrl()).into(moviedetails_picture);
                    }

                }).show(this, moviedetails_picture);
    }

    private void initView(){
        moviedetails_picture=(ImageView)findViewById(R.id.moviedetails_picture);
        zhanlang_img_back=(ImageView)findViewById(R.id.zhanlang_img_back);
        moviedetails_yugao=(LinearLayout)findViewById(R.id.moviedetails_yugao);
        moviedetails_moviesname=(TextView)findViewById(R.id.moviedetails_moviesname);
        moviedetails_moviesscore=(TextView)findViewById(R.id.moviedetails_moviesscore);
        moviedetails_movieslength=(TextView)findViewById(R.id.moviedetails_movieslength);
        moviedetails_moviestype=(TextView)findViewById(R.id.moviedetails_moviestype);
        moviedetails_moviestime=(TextView)findViewById(R.id.moviedetails_moviestime);
        moviedetails_moviesmessge=(TextView)findViewById(R.id.moviedetails_moviesmessge);
        moviedetails_moviesdetails=(TextView)findViewById(R.id.moviedetails_moviesdetails);
        moviedetails_director=(TextView)findViewById(R.id.moviedetails_director);
        moviedetails_actor=(TextView)findViewById(R.id.moviedetails_actor);
        moviedetails_haibao=(LinearLayout)findViewById(R.id.moviedetails_haibao);
        moviedetails_about=(LinearLayout)findViewById(R.id.moviedetails_about);
        moviedetails_paihang=(LinearLayout)findViewById(R.id.moviedetails_about);
        moviedetails_tobuy=(Button)findViewById(R.id.moviedetails_tobuy);

        moviedetails_moviesname.setText("  "+moviesname);
        moviedetails_moviesscore.setText("  "+scores);
        moviedetails_movieslength.setText("  "+lengths);
        moviedetails_moviestype.setText("  "+types);
        if (times==null){
            moviedetails_moviestime.setText("未能获取");
        }else {
            moviedetails_moviestime.setText("  "+times);
        }
        moviedetails_moviesdetails.setText(detailss);
        moviedetails_director.setText("     "+directors);
        moviedetails_moviesmessge.setText(messages);
        moviedetails_actor.setText(stars);

        zhanlang_img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        moviedetails_haibao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MoviesDetails.this,"功能有待更新，请期待！",Toast.LENGTH_SHORT).show();
            }
        });
        moviedetails_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MoviesDetails.this,"功能有待更新，请期待！",Toast.LENGTH_SHORT).show();
            }
        });
        moviedetails_paihang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MoviesDetails.this,"功能有待更新，请期待！",Toast.LENGTH_SHORT).show();
            }
        });
        moviedetails_tobuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MoviesDetails.this,"功能有待更新，请期待！",Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void initData(){
        //获取传来的值，在上面settext写入。
         moviesname=this.getIntent().getStringExtra("moviesname");
         scores=this.getIntent().getStringExtra("scores");
         lengths=this.getIntent().getStringExtra("lengths");
         types=this.getIntent().getStringExtra("types");
         times=this.getIntent().getStringExtra("times");
         detailss=this.getIntent().getStringExtra("detailss");
         directors=this.getIntent().getStringExtra("directors");
         messages=this.getIntent().getStringExtra("messages");
         stars=this.getIntent().getStringExtra("stars");

        /*Toast.makeText(MoviesDetails.this,"moviesname="+moviesname+"scores="+scores+"lengths="+lengths+
                "types="+types+"times="+times+"detailss="+detailss+
                "directors="+directors+"messages="+messages+"stars="+stars,Toast.LENGTH_SHORT).show();*/
    }
}
