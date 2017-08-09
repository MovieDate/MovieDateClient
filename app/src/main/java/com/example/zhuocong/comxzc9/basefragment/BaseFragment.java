package com.example.zhuocong.comxzc9.basefragment;

import android.app.Fragment;
import android.content.Context;
import android.view.View;

/**
 * Created by zhuocong on 2017/8/4.
 */

public abstract class BaseFragment extends Fragment{
    //基础视图
    protected View mBaseView;
    //上下文
    protected Context mContext;
    //获取控件
    protected abstract void findView();
    //初始化控件
    protected abstract void initView();
}
