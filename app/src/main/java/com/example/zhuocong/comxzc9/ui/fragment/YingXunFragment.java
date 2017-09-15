package com.example.zhuocong.comxzc9.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.ui.activity.ZhanLangActivity;
import com.example.zhuocong.comxzc9.ui.basefragment.BaseFragment;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class YingXunFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private Button tv_yingxun_movietime;
    private Button tv_yingxun_future;
    private Button tv_yingxun_hotmovie;
    private LinearLayout zhanlang2;
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
        tv_yingxun_movietime=(Button)mBaseView.findViewById(R.id.tv_yingxun_movietime);
        tv_yingxun_future=(Button)mBaseView.findViewById(R.id.tv_yingxun_future);
        tv_yingxun_hotmovie=(Button)mBaseView.findViewById(R.id.tv_yingxun_hotmovie);
        zhanlang2=(LinearLayout)mBaseView.findViewById(R.id.zhanlang2);

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

        zhanlang2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();
                intent.setClass(getActivity(), ZhanLangActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView() {


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
