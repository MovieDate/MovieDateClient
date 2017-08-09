package com.example.zhuocong.comxzc9.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.fragment.UserFragment;
import com.example.zhuocong.comxzc9.fragment.XiaoXiFragment;
import com.example.zhuocong.comxzc9.fragment.YingXunFragment;
import com.example.zhuocong.comxzc9.fragment.YueYingFragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
   //声明对象
    //上下文
    private Context mContext;
    //约影
    private RelativeLayout rlYueYing;
    //约影fragment类对象
    private YueYingFragment mYueYingFragment;
    //“约影”单选按钮
    private RadioButton rbtnYueYing;
    //消息
    private RelativeLayout rlXiaoXi;
    //消息fragment类对象
    private XiaoXiFragment mXiaoXiFragment;
    //“消息”单选按钮
    private RadioButton rbtnXiaoXi;
    //影讯
    private RelativeLayout rlYingXun;
    //影讯fragment类对象
    private YingXunFragment mYingXunFragment;
    // “影讯”单选按钮
    private RadioButton rbtnYingXun;
    //个人中心
    private RelativeLayout rlUser;
    //个人中心fragment类对象
    private UserFragment mUserFragment;
    //“个人中心”单选按钮
    private RadioButton rbtnUser;
    //导航fragment列表

    private List<Fragment> mFragmentList;
    int featureId;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //菜单样式
        setOverflowButtonAlways();

        //Fragment转换
        findView();
        initView();
        initNav();
        initFragment();


    }

    //获取控件
    public void findView(){
        rlYueYing= (RelativeLayout) findViewById(R.id.rlYueYing);
        rlXiaoXi= (RelativeLayout) findViewById(R.id.rlXiaoXi);
        rlYingXun= (RelativeLayout) findViewById(R.id.rlYingXun);
        rlUser= (RelativeLayout) findViewById(R.id.rlUser);

        rbtnYueYing= (RadioButton) findViewById(R.id.rbtnYueYing);
        rbtnXiaoXi= (RadioButton) findViewById(R.id.rbtnXiaoXi);
        rbtnYingXun= (RadioButton) findViewById(R.id.rbtnYingXun);
        rbtnUser= (RadioButton) findViewById(R.id.rbtnUser);
    }

    //初始化控件
    private void initView(){

    }

    //初始化导航栏
    private  void initNav(){
        rlYueYing.setOnClickListener(this);
        rlXiaoXi.setOnClickListener(this);
        rlYingXun.setOnClickListener(this);
        rlUser.setOnClickListener(this);
    }

    //初始化fragment类
    private void initFragment(){
        mYueYingFragment = new YueYingFragment();
        mXiaoXiFragment = new XiaoXiFragment();
        mYingXunFragment = new YingXunFragment();
        mUserFragment = new UserFragment();

        mFragmentList = new ArrayList<>();
        mFragmentList.add(mYueYingFragment);
        mFragmentList.add(mXiaoXiFragment);
        mFragmentList.add(mYingXunFragment);
        mFragmentList.add(mUserFragment);

        FragmentManager fm =getFragmentManager();
        FragmentTransaction ft =fm.beginTransaction();

        ft.add(R.id.fl_content,mYueYingFragment);
        ft.add(R.id.fl_content,mXiaoXiFragment);
        ft.add(R.id.fl_content,mYingXunFragment);
        ft.add(R.id.fl_content,mUserFragment);

        hideAllFragment();
        showFragment(mYueYingFragment);
        ft.commit();

    }

    //显示指定的Fragment
    private void showFragment(Fragment fragment){
        // 获取Fragment管理器
        FragmentManager fm = getFragmentManager();
        // 获取Fragment事物对象
        FragmentTransaction ft= fm.beginTransaction();
        //显示
        ft.show(fragment);
        //提交
        ft.commit();
    }

    //隐藏Fragment
    private void hideAllFragment(){
        // 获取Fragment管理器
        FragmentManager fm = getFragmentManager();
        // 获取Fragment事物对象
        FragmentTransaction ft= fm.beginTransaction();
        for (Fragment fragment:mFragmentList){
            ft.hide(fragment);
        }
        //提交
        ft.commit();
    }

    //改变单选按钮的状态
    private void changeRadioButtonState(boolean A, boolean B, boolean C,
                                        boolean D) {
        rbtnYueYing.setChecked(A);
        rbtnXiaoXi.setChecked(B);
        rbtnYingXun.setChecked(C);
        rbtnUser.setChecked(D);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.rlYueYing:
                changeRadioButtonState(true,false,false,false);
                hideAllFragment();
                showFragment(mYueYingFragment);
                break;
            case R.id.rlXiaoXi:
                changeRadioButtonState(false,true,false,false);
                hideAllFragment();
                showFragment(mXiaoXiFragment);
                break;
            case R.id.rlYingXun:
                changeRadioButtonState(false,false,true,false);
                hideAllFragment();
                showFragment(mYingXunFragment);
                break;
            case R.id.rlUser:
                changeRadioButtonState(false,false,false,true);
                hideAllFragment();
                showFragment(mUserFragment);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            return true;
        }
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_start) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    //设置弹出菜单样式
    private void setOverflowButtonAlways(){

        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKey =ViewConfiguration.class.getDeclaredField("hasPermanentMenuKey");
            menuKey.setAccessible(true);
            menuKey.setBoolean(config,false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
