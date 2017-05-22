package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.util.StringUtils;
import com.talkfun.sdk.HtSdk;
import com.talkfun.sdk.event.Callback;
import com.talkfun.sdk.event.HtVoteListener;
import com.talkfun.sdk.module.VoteEntity;
import com.talkfun.sdk.module.VoteOption;
import com.talkfun.sdk.module.VotePubEntity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/20.
 */
public class VoteFragment extends VoteBaseFragment implements HtVoteListener {
    private List<VoteOption> optionList = new ArrayList<>();
    private VoteEntity voteEntity;
    private boolean isSingleChoose = false;
    private VoteAdapter voteAdapter;
    private boolean isSelected = false;
    private static final String DATA_TAG = "vote";
    private static final String MODE_TAG = "mode";
    private static final String HOLDER = "holder";
    private int mLastPosition = -1;
    //多选的标志
    private List<Integer> mLastPositionList = new LinkedList<>();
    private Callback mCallback;

    public static VoteFragment create(VoteEntity voteEntity, boolean isSingleChoose, Callback callback) {
        VoteFragment voteFragment = new VoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DATA_TAG, voteEntity);
        bundle.putBoolean(MODE_TAG, isSingleChoose);
        bundle.putSerializable(HOLDER, callback);
        voteFragment.setArguments(bundle);
        return voteFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        this.voteEntity = (VoteEntity) bundle.getSerializable(DATA_TAG);
        if (voteEntity != null && voteEntity.getOpList() != null) {
            optionList = voteEntity.getOpList();
            voteAdapter = new VoteAdapter(getActivity(), optionList);
        }
        mCallback = (Callback) bundle.getSerializable(HOLDER);
        isSingleChoose = bundle.getBoolean(MODE_TAG);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.htVoteStyle);
        setCancelable(false);
    }


    /**
     * 初始化控件的事件
     */
    @Override
    void initViewEvent() {
        if (voteAdapter != null) {
            chooseLv.setAdapter(voteAdapter);
        }
        voteBtn.setOnClickListener(clickListener);
        cancelImg.setOnClickListener(clickListener);
        chooseLv.setOnItemClickListener(itemClickListener);
        chooseLv.setItemsCanFocus(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (voteEntity != null)
            voteStart(voteEntity);
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public void voteStart(VoteEntity voteEntity) {
        optionList = voteEntity.getOpList();
        if (voteAdapter != null) {
            voteAdapter.setOptionList(optionList);
        } else {
            voteAdapter = new VoteAdapter(getActivity(), optionList);
            chooseLv.setAdapter(voteAdapter);
        }
        String title = null;
        if (isSingleChoose) {
            title = context.getResources().getString(R.string.single_choice);
            chooseLv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            voteAdapter.setCheckboxStyle(true);
        } else {
            title = context.getResources().getString(R.string.multiple_choice);
            chooseLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            voteAdapter.setCheckboxStyle(false);
        }
        title += voteEntity.getTitle();
        titleTv.setText(title);
        foundersTv.setText(voteEntity.getNickname());
        timeTv.setText(voteEntity.getStartTime());
    }

    @Override
    public void voteStop(VotePubEntity votePubEntity) {

    }

    /**
     * 将投票按钮设置为可点击状态
     */
    private void changeVoteBtnStatus(boolean isSelected) {
        Context context = getActivity();
        if (isSelected) {
            voteBtn.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ht_vote_btn_checked_bg));
            voteBtn.setClickable(true);
        } else {
            voteBtn.setBackgroundDrawable(
                    context.getResources().getDrawable(R.drawable.ht_vote_btn_unchecked_bg));
            voteBtn.setClickable(false);
        }
    }

    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //判断选中的条数,并根据单选/多选 来设置投票按钮的状态
            if (isSingleChoose) {
                //先将选中的项置为相反状态
                optionList.get(position).setIsSelected(!optionList.get(position).isSelected());
                voteAdapter.notifyDataSetChanged();
                isSelected = true;
                //如果选中的项跟上一次选中的项是一样的.则将上次选中的标志位重置为-1
                if (mLastPosition == position) {
                    isSelected = false;
                    mLastPosition = -1;
                }
                // 将上次选中的项重置为为选中状态
                if (mLastPosition >= 0) {
                    optionList.get(mLastPosition).setIsSelected(false);
                }
                //将当前选中的项赋予上次选中项的标志位
                mLastPosition = position;
            } else {
                //用于纪录当前点击项是否点击过.若点击过.则移除该项
                boolean isChecked = false;
                //当前选项时候已被选择,如果有则移除
                if (mLastPositionList.contains(position)) {
                    isChecked = true;
                    mLastPositionList.remove(mLastPositionList.indexOf(position));
                    voteAdapter.notifyDataSetChanged();
                }
                if (mLastPositionList.size() >= voteEntity.getOptional()) {
                    //提示用户选项已达上限
                    StringUtils.tip(context, context.getResources().getString(R.string.ht_achieve_limit));
                    return;
                } else {
                    optionList.get(position).setIsSelected(!optionList.get(position).isSelected());
                    voteAdapter.notifyDataSetChanged();
                    //不添加点击过的项
                    if (!isChecked)
                        mLastPositionList.add(position);
                }
                isSelected = mLastPositionList.size() > 0;
            }
            changeVoteBtnStatus(isSelected);
        }
    };

    //点击事件
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.vote:
                    sendVote();
                    break;
                case R.id.cancel:
                    release();
                    break;
                default:
                    break;
            }
        }
    };

    //投票
    private void sendVote() {
        if (voteEntity != null) {
            List<Integer> integerList = new ArrayList<>();
            for (int i = 0; i < optionList.size(); i++) {
                if (optionList.get(i).isSelected()) {
                    integerList.add(i + 1);
                }
            }
            HtSdk.getInstance().sendVote(voteEntity.getVoteId(), integerList.toString(), mCallback);
            release();
        }
    }

    private void release() {
        isSelected = false;
        mLastPositionList.clear();
        dismiss();
    }


}
