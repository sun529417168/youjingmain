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
import com.youjing.yjeducation.talkfun.QuestionAdapter;
import com.talkfun.sdk.HtSdk;
import com.talkfun.sdk.data.PlaybackDataManage;
import com.talkfun.sdk.event.playbackMsgListener.AutoScrollListener;
import com.talkfun.sdk.event.playbackMsgListener.HtDispatchPlaybackMsgListener;
import com.talkfun.sdk.module.QuestionEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/12.
 */
public class PlaybackQuestionFragment extends PlaybackBasicFragment implements HtDispatchPlaybackMsgListener,
        SwipeRefreshLayout.OnRefreshListener, AutoScrollListener {
    @Bind(R.id.question_lv)
    ListView questionLv;
    @Bind(R.id.question_input_layout)
    RelativeLayout inputLayout;
    @Bind(R.id.swipe)
    SwipeRefreshLayout swipeRefreshLayout;
    String tag;
    private QuestionAdapter questionAdapter;
    private List<QuestionEntity> questionEntitiesList = new ArrayList<>();

    public PlaybackQuestionFragment() {

    }

    public static PlaybackQuestionFragment create(String tag) {
        PlaybackQuestionFragment qf = new PlaybackQuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("question", tag);
        qf.setArguments(bundle);
        return qf;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getString("question") != null) {
            tag = getArguments().getString("question");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.playback_question_fragment_layout, container, false);
        ButterKnife.bind(this, layout);

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue,
                android.R.color.holo_red_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        HtSdk.getInstance().setFilterQuestionFlag(true);
        questionEntitiesList = PlaybackDataManage.getInstance().getQuestionList();
        questionAdapter = new QuestionAdapter(getActivity(), questionEntitiesList);
        questionLv.setAdapter(questionAdapter);
        questionLv.setOnTouchListener(touchListener);

        questionLv.setOnScrollListener(scrollListener);
        inputLayout.setVisibility(View.GONE);

        PlaybackDataManage.getInstance().setQuestionListener(this);
        return layout;
    }

    @Override
    public void scrollToItem(final int pos) {
        if (isShow && questionAdapter != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    questionAdapter.notifyDataSetChanged();
                    questionLv.setSelection(pos);
                }
            });
        }
    }

    @Override
    public void resetAdapterData() {
        questionEntitiesList = PlaybackDataManage.getInstance().getQuestionList();
        questionAdapter.setQuestionEntity(questionEntitiesList);
    }

    @Override
    public void getPlaybackMsgSuccess(int position) {
        updateAdapter();
        if (isShow) {
            questionLv.setSelection(position);
        }
        stopSwipeRefresh();
    }

    @Override
    public void getPlaybackMsgFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
        stopSwipeRefresh();
    }

    private void stopSwipeRefresh() {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        PlaybackDataManage.getInstance().loadDownMoreData(PlaybackDataManage.DataType.QUESTION);
    }

    @Override
    public void updateAdapter() {
        if (questionAdapter != null)
            questionAdapter.notifyDataSetChanged();
    }

    @Override
    void getMoreData() {
        if (questionLv.getLastVisiblePosition() + 1 == questionEntitiesList.size()) {
            PlaybackDataManage.getInstance().loadUpMordData(PlaybackDataManage.DataType.QUESTION);
        }
    }

    @Override
    public void startAutoScroll() {
        PlaybackDataManage.getInstance().startAutoScroll(this, PlaybackDataManage.DataType.QUESTION);
    }

}
