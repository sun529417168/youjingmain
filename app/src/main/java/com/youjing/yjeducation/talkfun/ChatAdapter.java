package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.model.YJGiftModel;
import com.youjing.yjeducation.util.DimensionUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.talkfun.sdk.HtSdk;
import com.talkfun.sdk.event.Callback;
import com.talkfun.sdk.module.BriefVoteEntity;
import com.talkfun.sdk.module.ChatEntity;
import com.talkfun.sdk.module.HtBaseMessageEntity;
import com.talkfun.sdk.module.VotePubEntity;
import com.talkfun.sdk.module.system.HtVoteSystemEntity;
import com.youjing.yjeducation.util.YJLiveRoomUtil;
import com.youjing.yjeducation.wiget.CustomImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * if my son want to be programmer , i will break his legs.
 * Created by tony on 2015/11/18.
 */
public class ChatAdapter extends BaseAdapter {
    private List<HtBaseMessageEntity> chatLists = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private int MXA_LENGTH = 200;
    private final int SYSTEM_NORMAL = 0;
    private final int SYSTEM_VOTE = 2;
    private final int SIMPLE = 1;
    private final int TYPE_COUNT = 3;
    private Context mContext;

    private YJGiftModel yjGiftModel;
    private String TAG = "ChatAdapter";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            VotePubEntity votePubEntity = (VotePubEntity) bundle.getSerializable("VoteResult");

        }
    };

    public ChatAdapter(Context context, List<HtBaseMessageEntity> list) {
        mContext = context;
        chatLists = list;
        layoutInflater = LayoutInflater.from(context);
        ExpressionUtil.tvImgWidth = DimensionUtils.dip2px(context, 50);
        ExpressionUtil.tvImgHeight = DimensionUtils.dip2px(context, 50);
    }

    public void setChatLists(List<HtBaseMessageEntity> list) {
        chatLists = list;
        notifyDataSetChanged();
    }

    public void bindData(List<HtBaseMessageEntity> list){
        chatLists.clear();
        for (int i =0 , j = list.size() -1;i <=j ; i++){
            chatLists.add(list.get(i));
        }
    }

    public void appendList(HtBaseMessageEntity chatMessageEntity) {
        chatLists.add(chatMessageEntity);
        if (chatLists.size() >= MXA_LENGTH) {
            chatLists.remove(0).release();
            StringUtils.Log("tony", "chat list size = " + chatLists.size());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        switch (chatLists.get(position).getRole()) {
            case "user":
            case "admin":
            case "spadmin":
                return SIMPLE;
            case "system_vote":
                return SYSTEM_VOTE;
            default:
                return SYSTEM_NORMAL;
        }
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
        SystemViewHolder systemViewHolder = null;
        SystemVoteViewHolder systemVoteViewHolder = null;
        GiftViewHolder giftViewHolder = null;

        int type = getItemViewType(position);

        if (convertView == null) {
            switch (type) {
                case SIMPLE:
                    convertView = layoutInflater.inflate(R.layout.simple_chat_item_layout, null);
                    simpleViewHolder = new SimpleViewHolder(convertView);
                    convertView.setTag(simpleViewHolder);
                    break;
                case SYSTEM_NORMAL:
                    convertView = layoutInflater.inflate(R.layout.system_chat_item_layout, null);
                    systemViewHolder = new SystemViewHolder(convertView);
                    convertView.setTag(systemViewHolder);
                    break;
                case SYSTEM_VOTE:
                    convertView = layoutInflater.inflate(R.layout.vote_chat_item_layout, null);
                    systemVoteViewHolder = new SystemVoteViewHolder(convertView);
                    convertView.setTag(systemVoteViewHolder);
                default:
                    break;
            }
        } else {
            switch (type) {
                case SIMPLE:
                    simpleViewHolder = (SimpleViewHolder) convertView.getTag();
                    break;
                case SYSTEM_NORMAL:
                    systemViewHolder = (SystemViewHolder) convertView.getTag();
                    break;
                case SYSTEM_VOTE:
                    systemVoteViewHolder = (SystemVoteViewHolder) convertView.getTag();
                    break;
                default:
                    break;
            }
        }
        switch (type) {
            case SIMPLE:
                ChatEntity chatMessageEntity = (ChatEntity) chatLists.get(position);
                if (simpleViewHolder != null) {
                        simpleViewHolder.img_gift.setVisibility(View.GONE);
                    simpleViewHolder.txt_gift_num.setVisibility(View.GONE);
                        simpleViewHolder.nameTv.setText("");
                        String identity = chatMessageEntity.getRole();
                        if (identity.equals("user")) {
                            simpleViewHolder.contentTv.setTextColor(mContext.getResources().getColor(R.color.white));
                            simpleViewHolder.identityTv.setVisibility(View.GONE);
                        } else if (identity.equals("admin")) {
                            simpleViewHolder.identityTv.setVisibility(View.GONE);
                            simpleViewHolder.contentTv.setTextColor(mContext.getResources().getColor(R.color.yellow_text));
                            simpleViewHolder.identityTv.setText(convertView.getContext().getResources().getString(R.string.teacher));
                        } else if (identity.equals("spadmin")) {
                            simpleViewHolder.identityTv.setVisibility(View.GONE);
                            simpleViewHolder.contentTv.setTextColor(mContext.getResources().getColor(R.color.yellow_text));
                            simpleViewHolder.identityTv.setText(convertView.getContext().getResources().getString(R.string.assistants));
                        } else {
                            simpleViewHolder.identityTv.setVisibility(View.GONE);
                            simpleViewHolder.contentTv.setTextColor(mContext.getResources().getColor(R.color.white));
                        }
                        String name = chatMessageEntity.getNickname();
                        if (name != null & !TextUtils.isEmpty(name)) {
                            simpleViewHolder.nameTv.setText(name);
                        }
                        String userUrl = chatMessageEntity.getAvatar();
                        if (userUrl != null & !TextUtils.isEmpty(userUrl)) {
                            BitmapUtils.create(mContext).display( simpleViewHolder.img_user_photo, userUrl);
                        }

                        String time = chatMessageEntity.getTime();
                        if (!TextUtils.isEmpty(time)) {
                            time = TimeUtil.displayHHMMSS(time);
                            if (time != null)
                                simpleViewHolder.timeTv.setText(time);
                        }
                        String content = chatMessageEntity.getMsg();
                        if (content != null & !TextUtils.isEmpty(content) ) {
                            //  viewHolder.contentTv.setText(content);
                            try {
                                if (YJLiveRoomUtil.isRedStr(content) || YJLiveRoomUtil.isGiftStr(content)){
                                    StringUtils.Log(TAG,"content="+content);
                                    simpleViewHolder.img_gift.setVisibility(View.GONE);
                                    simpleViewHolder.txt_gift_num.setVisibility(View.GONE);
                                    simpleViewHolder.contentTv.setVisibility(View.VISIBLE);
                                    content =  content.substring(content.indexOf("]")+1, content.length());

                                    SpannableString spannableString = ExpressionUtil.getExpressionString(convertView.getContext(), content, "mipmap");
                                    simpleViewHolder.contentTv.setText(spannableString);

                                }else{
                                    StringUtils.Log(TAG,"content2="+content);
                                    simpleViewHolder.img_gift.setVisibility(View.GONE);
                                    simpleViewHolder.txt_gift_num.setVisibility(View.GONE);
                                    simpleViewHolder.contentTv.setVisibility(View.VISIBLE);
                                    SpannableString spannableString = ExpressionUtil.getExpressionString(convertView.getContext(), content, "mipmap");
                                    simpleViewHolder.contentTv.setText(spannableString);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                }
                break;
            case SYSTEM_NORMAL:
                ChatEntity fakeChatEntity = (ChatEntity) chatLists.get(position);
                String role = fakeChatEntity.getRole();
                if (role.equals("system_broadcast")) {
                    systemViewHolder.systemImg.setImageResource(R.mipmap.ht_broadcast);
                } else if (role.equals("system_lottery")) {
                    systemViewHolder.systemImg.setImageResource(R.mipmap.ht_notify);
                }
                systemViewHolder.systemMsg.setText(fakeChatEntity.getMsg());
                break;
            case SYSTEM_VOTE:
                HtVoteSystemEntity voteEntity = (HtVoteSystemEntity) chatLists.get(position);
                systemVoteViewHolder.systemMsg.setText(voteEntity.getContent());
                final String vid = voteEntity.getVid();
                systemVoteViewHolder.checkVote.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        HtSdk.getInstance().getVote(vid, new Callback() {
                            @Override
                            public void success(final Object result) {
                                Runnable runnable = new Runnable() {
                                    @Override
                                    public void run() {
                                        Message msg = new Message();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("VoteResult", parseVote((JSONObject) result));
                                        msg.setData(bundle);
                                        handler.sendMessage(msg);
                                    }
                                };
                                new Thread(runnable).start();
                            }

                            @Override
                            public void failed(String failed) {
                                Toast.makeText(mContext, failed, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            default:
                break;
        }
        return convertView;
    }

    private VotePubEntity parseVote(JSONObject args) {
        VotePubEntity votePubEntity = new VotePubEntity();
        JSONObject info = args.optJSONObject("info");
        int isShow = args.optInt("isShow");
        votePubEntity.setIsShow(isShow);
        if (info != null) {
            String title = info.optString("title");
            String nickname = info.optString("nickname");
            String startTime = info.optString("startTime");
            String noticeTime = info.optString("noticeTime");
            String vid = info.optString("vid");

            votePubEntity.setStartTime(startTime);
            votePubEntity.setNickname(nickname);
            votePubEntity.setTitle(title);
            votePubEntity.setVid(vid);
            // 广播出去, 谁发起/结束了投票
        }
        JSONArray statusList = args.optJSONArray("statsList");
        if (statusList != null) {
            List<BriefVoteEntity> briefVoteEntityList = new ArrayList<>();
            for (int i = 0, j = statusList.length(); i < j; i++) {
                BriefVoteEntity briefVoteEntity = new BriefVoteEntity();
                try {
                    JSONObject object = statusList.getJSONObject(i);
                    briefVoteEntity.setOp(object.optString("op"));
                    briefVoteEntity.setOpNum(object.optInt("opNum"));
                    briefVoteEntity.setPercent(object.optInt("percent"));
                    briefVoteEntityList.add(briefVoteEntity);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            votePubEntity.setBriefVoteEntityList(briefVoteEntityList);
        }
        return votePubEntity;
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
        @Bind(R.id.img_user_photo)
        CustomImage img_user_photo;

        @Bind(R.id.img_gift)
        ImageView img_gift;
        @Bind(R.id.txt_gift_num)
        TextView txt_gift_num;
        SimpleViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    class SystemViewHolder {
        @Bind(R.id.system_msg)
        TextView systemMsg;
        @Bind(R.id.icon)
        ImageView systemImg;

        SystemViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }

    class SystemVoteViewHolder {
        @Bind(R.id.check_vote)
        TextView checkVote;
        @Bind(R.id.system_msg)
        TextView systemMsg;

        SystemVoteViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    class GiftViewHolder {
        @Bind(R.id.check_vote)
        TextView checkVote;
        @Bind(R.id.system_msg)
        TextView systemMsg;

        GiftViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
