package com.example.zhuocong.comxzc9.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhuocong.comxzc9.R;
import com.example.zhuocong.comxzc9.adapter.ChatHistoryAdapter;
import com.example.zhuocong.comxzc9.ui.activity.ChatActivity;
import com.example.zhuocong.comxzc9.ui.activity.XiaoxiMyFriend;
import com.example.zhuocong.comxzc9.ui.activity.XiaoxiMygroup;
import com.example.zhuocong.comxzc9.ui.basefragment.BaseFragment;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuocong on 2017/8/4.
 */

public class XiaoXiFragment extends BaseFragment {

    //所有的会话列表
    private List<EMConversation> conversationList = new ArrayList<EMConversation>();
    private ListView chatHistoryListView;
    private ChatHistoryAdapter adapter ;

    private Button NewFriend;
    private Button MyFriend;
    private Button MyGroup;
    private Button Answer;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mBaseView=inflater.inflate(R.layout.fragment_xiaoxi, null);
        mContext=getActivity();

        conversationList.addAll(loadConversationWithRecentChat());
        chatHistoryListView=(ListView)mBaseView.findViewById(R.id.chatHistoryListView);
        adapter = new ChatHistoryAdapter(getActivity(), conversationList);
        chatHistoryListView.setAdapter(adapter);

        chatHistoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                EMConversation conversation = (EMConversation) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("phone", conversation.getUserName());
                startActivity(intent);
            }

        });


        int count = 0;
        for(int i=0; i<conversationList.size(); i++){
            count += conversationList.get(i).getUnreadMsgCount();
        }
        Log.v("count total", count+"");
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
                Intent intent=new Intent();
                intent.setClass(getActivity(),XiaoxiMygroup.class);
                startActivity(intent);

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

    /**
     * 获取所有会话Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
     * @return
     */
    private Collection<? extends EMConversation> loadConversationWithRecentChat() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();


        List<Pair<Long, EMConversation>> sortList = new ArrayList<Pair<Long, EMConversation>>();
        synchronized(conversations){
            for(EMConversation conversation : conversations.values()){
                if(conversation.getAllMessages().size() != 0){
                    sortList.add(new Pair<Long, EMConversation>
                            (conversation.getLastMessage().getMsgTime(), conversation)
                    );
                }
            }
        }

        try{
            sortConversationByLastChatTime(sortList);
        }catch(Exception e){
            e.printStackTrace();
        }

        List<EMConversation> list = new ArrayList<EMConversation>();
        for(Pair<Long, EMConversation> sortItem : sortList){
            list.add(sortItem.second);
        }

        return list;
    }


    /**
     * 根据最后一条消息的时间排序
     * @param sortList
     */
    private void sortConversationByLastChatTime(
            List<Pair<Long, EMConversation>> sortList) {
        Collections.sort(sortList, new Comparator<Pair<Long, EMConversation>>(){

            @Override
            public int compare(Pair<Long, EMConversation> con1,
                               Pair<Long, EMConversation> con2) {
                if(con1.first == con2.first){
                    return 0;
                }else if(con2.first > con1.first){
                    return 1;
                }else{
                    return -1;
                }
            }
        });
    }

}
