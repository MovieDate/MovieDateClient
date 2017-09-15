package com.example.zhuocong.comxzc9.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.zhuocong.comxzc9.R;
import com.hyphenate.chat.EMMessage;
import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.recyclerview.MultiItemCommonAdapter;
import com.zhy.base.adapter.recyclerview.MultiItemTypeSupport;


import java.util.List;

/**
 * Created by z on 2016/5/26.
 */
public class ChatAdapter extends MultiItemCommonAdapter<EMMessage> {
    /*MultiItemCommonAdapter<EMMessage>*/
    public static final int TYPE_FROM = 0;
    public static final int TYPE_SEND = 1;

    public ChatAdapter(Context context, List<EMMessage> datas) {
        super(context, datas, new MultiItemTypeSupport<EMMessage>() {

            @Override
            public int getLayoutId(int itemType) {
                if (itemType == TYPE_FROM)
                    return R.layout.chat_from;
                else
                    return R.layout.chat_send;
            }

            @Override
            public int getItemViewType(int position, EMMessage msg) {
                if (msg.direct() == EMMessage.Direct.RECEIVE)
                    return TYPE_FROM;
                else return TYPE_SEND;
            }
        });
    }

    @Override
    public void convert(ViewHolder holder, EMMessage msg) {
        String s = msg.getBody().toString();
        String text = s.substring(5, s.length() - 1);
        switch (holder.getLayoutId()) {
            case R.layout.chat_from:
                holder.setText(R.id.chat_from_name,msg.getFrom());
                holder.setText(R.id.chat_from_content, text);
                break;
            case R.layout.chat_send:
                holder.setText(R.id.chat_send_name,msg.getFrom());
                holder.setText(R.id.chat_send_content, text);
                break;
        }
    }
}
