package com.youjing.yjeducation.talkfun;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.View;

import com.youjing.yjeducation.R;
import com.talkfun.sdk.module.BriefVoteEntity;
import com.talkfun.sdk.module.VotePubEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/21.
 */
public class VoteResultFragment extends VoteBaseFragment implements View.OnClickListener {
    private static final String TAG = "tag";
    private List<BriefVoteEntity> briefVoteEntityList = new ArrayList<>();
    private VoteResultAdapter voteResultAdapter;
    private VotePubEntity votePubEntity;

    public static VoteResultFragment create(VotePubEntity votePubEntity ) {
        VoteResultFragment voteResultFragment = new VoteResultFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TAG, votePubEntity);
        voteResultFragment.setArguments(bundle);
        return voteResultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.votePubEntity = (VotePubEntity) bundle.getSerializable(TAG);
        if (votePubEntity != null && votePubEntity.getBriefVoteEntityList() != null) {
            briefVoteEntityList = votePubEntity.getBriefVoteEntityList();
            voteResultAdapter = new VoteResultAdapter(briefVoteEntityList, getActivity());
        }
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.htVoteStyle);
        setCancelable(false);
    }

    @Override
    void initViewEvent() {
        cancelImg.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        showResult(votePubEntity);
    }

    private void showResult(VotePubEntity votePubEntity) {
        if (voteResultAdapter == null) {
            briefVoteEntityList = votePubEntity.getBriefVoteEntityList();
            voteResultAdapter = new VoteResultAdapter(briefVoteEntityList, getActivity());
        }
        chooseLv.setAdapter(voteResultAdapter);
        voteBtn.setVisibility(View.INVISIBLE);
        String title = votePubEntity.getTitle();
        if (!TextUtils.isEmpty(title))
            titleTv.setText(title);
        foundersTv.setText(votePubEntity.getNickname());
        timeTv.setText(votePubEntity.getStartTime());
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
