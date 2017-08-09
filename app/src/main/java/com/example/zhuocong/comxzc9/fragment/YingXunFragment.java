package com.example.zhuocong.comxzc9.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.basefragment.BaseFragment;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class YingXunFragment extends BaseFragment {
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

    }

    @Override
    protected void initView() {

    }
}
