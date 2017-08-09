package com.example.zhuocong.comxzc9.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.ui.activity.MyUserInfoActivity;
import com.example.zhuocong.comxzc9.ui.basefragment.BaseFragment;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class UserFragment extends BaseFragment {

    private LinearLayout ll_myinfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView=inflater.inflate(R.layout.fragment_user, null);
        mContext=getActivity();

        ll_myinfo = (LinearLayout)mBaseView.findViewById(R.id.fprofile_ll_myinfo);
        ll_myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyUserInfoActivity.class);
                startActivity(intent);
            }
        });

        findView();
        initView();
        return mBaseView;
    }

    @Override
    protected void findView() {

    }

    @Override
    protected void initView() {

    }
}
