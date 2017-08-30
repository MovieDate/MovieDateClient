package com.example.zhuocong.comxzc9.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.ui.activity.XiaoxiMyFriend;
import com.example.zhuocong.comxzc9.ui.basefragment.BaseFragment;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class XiaoXiFragment extends BaseFragment {

    private Button NewFriend;
    private Button MyFriend;
    private Button MyGroup;
    private Button Answer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView=inflater.inflate(R.layout.fragment_xiaoxi, null);
        mContext=getActivity();

        findView();
        initView();
        return mBaseView;
    }

    @Override
    protected void findView() {
        NewFriend=(Button)mBaseView.findViewById(R.id.NewFriend);
        MyFriend=(Button)mBaseView.findViewById(R.id.MyFriend);
        MyGroup=(Button)mBaseView.findViewById(R.id.MyGroup);
        Answer=(Button)mBaseView.findViewById(R.id.Answer);

        NewFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        MyFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(),XiaoxiMyFriend.class);
                startActivity(intent);

            }
        });

        MyGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    protected void initView() {

    }
}
