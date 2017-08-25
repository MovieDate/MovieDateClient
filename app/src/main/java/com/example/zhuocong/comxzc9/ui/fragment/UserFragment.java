package com.example.zhuocong.comxzc9.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.ui.activity.LoginActivity;
import com.example.zhuocong.comxzc9.ui.activity.UserMyCollectActivity;
import com.example.zhuocong.comxzc9.ui.activity.UserMyyueyingActivity;
import com.example.zhuocong.comxzc9.ui.activity.UserSettingActivity;
import com.example.zhuocong.comxzc9.ui.activity.UserUpdatePhoneActivity;
import com.example.zhuocong.comxzc9.ui.activity.MyUserInfoActivity;
import com.example.zhuocong.comxzc9.ui.activity.UserUpdatePswActivity;
import com.example.zhuocong.comxzc9.ui.basefragment.BaseFragment;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class UserFragment extends BaseFragment{
    private View view = null;
    private LinearLayout ll_myinfo;
    private TextView fprofile_tv_name;
    private TextView fprofile_tv_account;
    private String userDataStr;
    private User userInfo;
    private LinearLayout ll_mycollect;
    private LinearLayout ll_myyueying;
    private LinearLayout ll_updatephone;
    private LinearLayout ll_updatepsw;
    private LinearLayout ll_setting;
    private LinearLayout ll_logout;
    private LinearLayout ll__finish;

    private String account;
    private String name;





    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mBaseView=inflater.inflate(R.layout.fragment_user, null);

        account=SharedPrefsUtil.getValue(getActivity(),APPConfig.ACCOUNT,"");
        name =SharedPrefsUtil.getValue(getActivity(),APPConfig.NAME,"");
        Log.d("test","name"+name+"account"+account);

        mContext=getActivity();
        view=new View(getActivity());
        init(mBaseView);
        initDate();


        ll_mycollect=(LinearLayout)mBaseView.findViewById(R.id.fprofile_ll_mycollect);
        ll_myyueying=(LinearLayout)mBaseView.findViewById(R.id.fprofile_ll_myyueying);
        ll_updatephone=(LinearLayout)mBaseView.findViewById(R.id.fprofile_ll_updatephone);
        ll_updatepsw=(LinearLayout)mBaseView.findViewById(R.id.fprofile_ll_updatepsw);
        ll_setting=(LinearLayout)mBaseView.findViewById(R.id.fprofile_ll_setting);
        ll_logout=(LinearLayout)mBaseView.findViewById(R.id.fprofile_ll_logout);
        ll__finish=(LinearLayout)mBaseView.findViewById(R.id.fprofile_ll__finish);
        ll_myinfo = (LinearLayout)mBaseView.findViewById(R.id.fprofile_ll_myinfo);
        ll_myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), MyUserInfoActivity.class);
                startActivity(intent);
            }
        });
        ll_mycollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), UserMyCollectActivity.class);
                startActivity(intent);

            }
        });
        ll_myyueying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), UserMyyueyingActivity.class);
                startActivity(intent);

            }
        });
        ll_updatephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), UserUpdatePhoneActivity.class);
                startActivity(intent);

            }
        });
        ll_updatepsw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), UserUpdatePswActivity.class);
                startActivity(intent);

            }
        });
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(getActivity(), UserSettingActivity.class);
                startActivity(intent);

            }
        });
        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefsUtil.putValue(getActivity(), APPConfig.IS_LOGIN,false);
                Intent intent = new Intent();
                intent.setClass(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
        ll__finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });

        findView();
        initView();
        return mBaseView;
    }

    private void init(View view) {
        userDataStr = SharedPrefsUtil.getValue(getActivity(), APPConfig.USERDATA,"");
        Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);

        fprofile_tv_name =(TextView)view.findViewById(R.id.fprofile_tv_name);
        fprofile_tv_account =(TextView)view.findViewById(R.id.fprofile_tv_account);

    }

    private void initDate(){
        //fprofile_tv_name.setText("姓名："+name);
        fprofile_tv_account.setText("账号："+account);
    }


    @Override
    protected void findView() {




    }

    @Override
    protected void initView() {

    }




}
