package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.MyFriendAdapter;
import com.example.zhuocong.comxzc9.commom.APPConfig;
import com.example.zhuocong.comxzc9.entity.FriendList;
import com.example.zhuocong.comxzc9.entity.User;
import com.example.zhuocong.comxzc9.utils.OkHttpUtils;
import com.example.zhuocong.comxzc9.utils.SharedPrefsUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuocong on 2017/8/30.
 */

public class XiaoxiMyFriend extends Activity{

    private ListView myfriendlistview;
    private String userDataStr;
    private User userInfo;
    private List<FriendList> friendListList;
    private MyFriendAdapter myFriendAdapter;
    Gson gson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_xiaoxi_myfriend);

        initView();
        initData();
    }

    public void initView(){
        myfriendlistview=(ListView)this.findViewById(R.id.myfriendlistview);
        userDataStr = SharedPrefsUtil.getValue(XiaoxiMyFriend.this, APPConfig.USERDATA,"");
        Gson gson= new Gson();
        userInfo=gson.fromJson(userDataStr,User.class);
        myfriendlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView myfriend_friendid=(TextView)view.findViewById(R.id.myfriend_friendid);
                String id=myfriend_friendid.getText().toString().trim();
                Log.d("testRun","friendId = "+id);
                Intent intent=new Intent();
                intent.setClass(XiaoxiMyFriend.this, AllPersonInfo.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
    }

    public void initData(){
        String myId=String.valueOf(userInfo.getId());
        Log.d("TestRun","myId="+myId);
        final List<OkHttpUtils.Param> list=new ArrayList<OkHttpUtils.Param>();
        OkHttpUtils.Param myIdParam=new OkHttpUtils.Param("myId",myId);
        list.add(myIdParam);

        new Thread(new Runnable() {
            @Override
            public void run() {
                //ppost方式连接
                OkHttpUtils.post(APPConfig.findFriendByMyId, new OkHttpUtils.ResultCallback() {

                    @Override
                    public void onSuccess(Object response) {
                        Message message=new Message();
                        message.what=0;
                        message.obj=response;
                        String FriendListListStr =response.toString();
                        Log.d("TestRun","FriendListList=="+FriendListListStr);
                        handler.sendMessage(message);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.d("postDateStr","服务器连接失败，请重试！");
                        Toast.makeText(XiaoxiMyFriend.this, "服务器连接失败，请重试！", Toast.LENGTH_SHORT).show();
                    }
                },list);

            }
        }).start();
    }

    private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg){
        String FriendListListStr=msg.obj.toString();
        Log.d("TestRun","FriendListList2=="+FriendListListStr);
        if (FriendListListStr==null){
            Toast.makeText(XiaoxiMyFriend.this, "暂时没有已添加的影友", Toast.LENGTH_SHORT).show();
        }else {
            try {
                //gson解析数据
                friendListList = gson.fromJson(FriendListListStr, new TypeToken<List<FriendList>>() {
                }.getType());
                if (friendListList != null && friendListList.size() > 0) {
                    Log.d("testRun", "friendListList=" + friendListList);
                    myFriendAdapter = new MyFriendAdapter(friendListList, XiaoxiMyFriend.this);
                    myfriendlistview.setAdapter(myFriendAdapter);
                }
            } catch (JsonSyntaxException e1) {
                Toast.makeText(XiaoxiMyFriend.this, "内部数据解析异常", Toast.LENGTH_SHORT).show();
                Log.e("JsonSyntaxException",""+e1.getMessage());
                e1.printStackTrace();
            }

        }


    }
    };
}
