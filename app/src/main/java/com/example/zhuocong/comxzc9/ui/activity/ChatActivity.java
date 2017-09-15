package com.example.zhuocong.comxzc9.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.ChatAdapter;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.zhy.base.adapter.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends Activity implements View.OnClickListener {

    private TextView back;
    private TextView btnsetting;
    private RecyclerView rvlist;
    private EditText mEditText;
    private TextView biaoqing;
    private List<EMMessage> mDatas = new ArrayList<>();
    private EMChatManager mEMChatManager;
    private String uName;
    private TextView send;
    private ChatAdapter mAdapter;
    private EMMessageListener mMsgListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chater);
        send = (TextView) this.findViewById(R.id.send);
        biaoqing = (TextView) this.findViewById(R.id.biaoqing);
        mEditText = (EditText) this.findViewById(R.id.msg);
        rvlist = (RecyclerView)this.findViewById(R.id.rv_list);
        btnsetting = (TextView) this.findViewById(R.id.btn_setting);
        back = (TextView) this.findViewById(R.id.back);
        biaoqing.setOnClickListener(this);
        btnsetting.setOnClickListener(this);
        back.setOnClickListener(this);
        send.setOnClickListener(this);

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mEMChatManager.removeMessageListener(mMsgListener);
    }

    private void initData() {

        /*Intent intent = getIntent();
        uName = intent.getStringExtra(Constant.CHAT_TO_NAME);*/
        Intent intent= new Intent();
        uName=this.getIntent().getStringExtra("phone");
        Log.d("testRun","uname="+uName);
        mEMChatManager = EMClient.getInstance().chatManager();
        EMConversation conversation = mEMChatManager.getConversation(uName);
        if (conversation != null)
            mDatas = conversation.getAllMessages();
        if (null == mDatas) {//如果获取到null的话就重新新建一个list,然后再设置给recycleview
            mDatas = new ArrayList<>();
        }
        mAdapter = new ChatAdapter(getApplicationContext(), mDatas);
        rvlist.setLayoutManager(new LinearLayoutManager(this));
        rvlist.setItemAnimator(new DefaultItemAnimator());
//添加分割线
        rvlist.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
        rvlist.setAdapter(mAdapter);
        rvlist.scrollToPosition(mDatas.size()-1);


        mMsgListener = new EMMessageListener() {

            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                //收到消息
                for (EMMessage msg : messages) {
                    mDatas.add(mDatas.size(), msg);
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        rvlist.scrollToPosition(mDatas.size() - 1);
                    }
                });

            }

            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                //收到透传消息
            }

            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
                //收到已读回执
            }

            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> message) {
                //收到已送达回执
            }

            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                //消息状态变动
            }
        };
        EMClient.getInstance().chatManager().addMessageListener(mMsgListener);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_setting:

                break;
            case R.id.back:
                finish();
                break;
            case R.id.biaoqing:
                break;
            case R.id.send:
                EMMessage msg = EMMessage.createTxtSendMessage(mEditText.getText().toString(), uName);
                mEMChatManager.sendMessage(msg);
                mDatas.add(mDatas.size(), msg);
                mAdapter.notifyDataSetChanged();
                rvlist.scrollToPosition(mDatas.size() - 1);
                break;
            default:
                break;
        }
    }
}
