package com.youjing.yjeducation.talkfun.plackback;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.youjing.yjeducation.R;
import com.talkfun.sdk.data.PlaybackDataManage;
import com.talkfun.sdk.event.playbackMsgListener.AutoScrollListener;
import com.talkfun.sdk.event.playbackMsgListener.HtDispatchPlaybackMsgListener;
import com.talkfun.sdk.module.ChatEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/12.
 */
public class PlaybackChatFragment extends PlaybackBasicFragment implements SwipeRefreshLayout.OnRefreshListener,
        HtDispatchPlaybackMsgListener, AutoScrollListener {
    @Bind(R.id.chat_lv)
    ListView chatLv;
    @Bind(R.id.play_back_input)
    RelativeLayout inputLayout;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;

    private String tag;
    private PlaybackChatAdapter chatAdapter;
    private List<ChatEntity> chatMessageEntityList = new ArrayList<>();

    public PlaybackChatFragment() {

    }

    public static PlaybackChatFragment create(String tag) {
        PlaybackChatFragment cf = new PlaybackChatFragment();
        Bundle bundle = new Bundle();
        bundle.putString("chat", tag);
        cf.setArguments(bundle);
        return cf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getString("chat") != null) {
            tag = getArguments().getString("chat");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.playback_chat_fragment_layout, container, false);
        ButterKnife.bind(this, layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.blue, android.R.color.holo_red_light, android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        chatMessageEntityList = PlaybackDataManage.getInstance().getChatList();
        chatAdapter = new PlaybackChatAdapter(getActivity(), chatMessageEntityList);
        chatLv.setAdapter(chatAdapter);
        chatLv.setOnScrollListener(scrollListener);
        chatLv.setOnTouchListener(touchListener);

        inputLayout.setVisibility(View.GONE);
        PlaybackDataManage.getInstance().setChatListener(this);
        return layout;
    }

    @Override
    public void onRefresh() {
        PlaybackDataManage.getInstance().loadDownMoreData(PlaybackDataManage.DataType.CHAT);
    }

    @Override
    public void getPlaybackMsgSuccess(int position) {
        if (isShow && chatAdapter != null) {
            chatAdapter.notifyDataSetChanged();
            chatLv.setSelection(position);
        }
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void getPlaybackMsgFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void scrollToItem(final int pos) {
        if (isShow && chatAdapter != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    chatAdapter.notifyDataSetChanged();
                    chatLv.setSelection(pos);
                }
            });
        }
    }

    @Override
    public void updateAdapter() {
        if (chatAdapter != null)
            chatAdapter.notifyDataSetChanged();
    }

    @Override
    void getMoreData() {
        if (chatLv.getLastVisiblePosition() + 1 == chatMessageEntityList.size()) {
            PlaybackDataManage.getInstance().loadUpMordData(PlaybackDataManage.DataType.CHAT);
        }
    }

    @Override
    public void startAutoScroll() {
        PlaybackDataManage.getInstance().startAutoScroll(this, PlaybackDataManage.DataType.CHAT);
    }

    @Override
    void resetAdapterData() {
        chatMessageEntityList = PlaybackDataManage.getInstance().getChatList();
        chatAdapter.setChatLists(chatMessageEntityList);
    }

}
