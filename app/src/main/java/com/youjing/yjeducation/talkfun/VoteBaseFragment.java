package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.youjing.yjeducation.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/21.
 */
public abstract class VoteBaseFragment extends DialogFragment {
    protected Context context;
    protected View layout;
    @Bind(R.id.cancel)
    ImageView cancelImg;
    @Bind(R.id.founders)
    TextView foundersTv;
    @Bind(R.id.time)
    TextView timeTv;
    @Bind(R.id.title)
    TextView titleTv;
    @Bind(R.id.vote)
    Button voteBtn;
    @Bind(R.id.choice)
    ListView chooseLv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        context = getActivity();
        layout = inflater.inflate(R.layout.ht_vote_layout, container);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ButterKnife.bind(this, layout);
        initViewEvent();
        return layout;
    }

    abstract void initViewEvent();

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
