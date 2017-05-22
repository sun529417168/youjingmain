package com.youjing.yjeducation.talkfun.plackback;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.talkfun.ExpressionUtil;
import com.youjing.yjeducation.util.DimensionUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.talkfun.sdk.module.ChatEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/2/24.
 */
public class PlaybackChatAdapter extends BaseAdapter {
    private List<ChatEntity> chatLists = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private int MXA_LENGTH = 200;

    public PlaybackChatAdapter(Context context, List<ChatEntity> list) {
        chatLists = list;
        layoutInflater = LayoutInflater.from(context);
        ExpressionUtil.tvImgWidth = DimensionUtils.dip2px(context, 50);
        ExpressionUtil.tvImgHeight = DimensionUtils.dip2px(context, 50);
    }

    public void setChatLists(List<ChatEntity> list) {
        chatLists = list;
        notifyDataSetChanged();
    }

    public synchronized void appendList(ChatEntity chatMessageEntity) {
        chatLists.add(chatMessageEntity);
        if (chatLists.size() >= MXA_LENGTH) {
            chatLists.remove(0).release();
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return chatLists.size();
    }

    @Override
    public Object getItem(int position) {
        return chatLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleViewHolder simpleViewHolder = null;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.simple_chat_item_layout_talkfun, null);
            simpleViewHolder = new SimpleViewHolder(convertView);
            convertView.setTag(simpleViewHolder);
        } else {
            simpleViewHolder = (SimpleViewHolder) convertView.getTag();
        }
        ChatEntity chatMessageEntity = (ChatEntity) chatLists.get(position);

        if (simpleViewHolder != null) {
            simpleViewHolder.nameTv.setText("");
            String identity = chatMessageEntity.getRole();
            if (identity.equals("user")) {
                simpleViewHolder.identityTv.setVisibility(View.GONE);
            } else if (identity.equals("admin")) {
                simpleViewHolder.identityTv.setVisibility(View.VISIBLE);
                simpleViewHolder.identityTv.setText(convertView.getContext().getResources().getString(R.string.teacher));
            } else if (identity.equals("spadmin")) {
                simpleViewHolder.identityTv.setVisibility(View.VISIBLE);
                simpleViewHolder.identityTv.setText(convertView.getContext().getResources().getString(R.string.assistants));
            } else {
                simpleViewHolder.identityTv.setVisibility(View.GONE);
            }
            String name = chatMessageEntity.getNickname();
            if (name != null & !TextUtils.isEmpty(name)) {
                simpleViewHolder.nameTv.setText(name);
            }
            String time = chatMessageEntity.getTime();
            if (!TextUtils.isEmpty(time)) {
                time = TimeUtil.displayHHMMSS(time);
                if (time != null)
                    simpleViewHolder.timeTv.setText(time);
            }
            String content = chatMessageEntity.getMsg();
            if (content != null & !TextUtils.isEmpty(content)) {
                //  viewHolder.contentTv.setText(content);
                SpannableString spannableString = ExpressionUtil.getExpressionString(convertView.getContext(), content, "mipmap");
                simpleViewHolder.contentTv.setText(spannableString);
            }
        }
        return convertView;
    }

    class SimpleViewHolder {
        @Bind(R.id.identity)
        TextView identityTv;
        @Bind(R.id.name)
        TextView nameTv;
        @Bind(R.id.content)
        TextView contentTv;
        @Bind(R.id.time)
        TextView timeTv;

        SimpleViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

}
