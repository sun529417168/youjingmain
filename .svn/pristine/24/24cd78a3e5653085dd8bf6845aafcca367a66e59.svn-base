package com.youjing.yjeducation.talkfun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.talkfun.sdk.event.HtLotteryListener;


/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/19.
 */
public class LotteryFragment extends DialogFragment implements HtLotteryListener, View.OnClickListener {

    private ImageView leftImg;
    private ImageView centerImg;
    private ImageView rightImg;
    private LinearLayout lotteryLinearLayout;
    private ImageView cancelImg;
    private TextView textView;
    private RelativeLayout lotteryBg;
    private ImageView centerCancel;
    private TextView myName;

    private boolean lotteryStatus = false;
    private static final String TAG = "status";
    private static final String WINNER = "winner";
    private static final String IS_CURRENT_USER = "is_current_user";
    private String winner = null;
    private boolean isCurrentUser = false;

    public static LotteryFragment create(boolean status, String name, boolean isCurrentUser) {
        LotteryFragment lotteryFragment = new LotteryFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(TAG, status);
        bundle.putString(WINNER, name);
        bundle.putBoolean(IS_CURRENT_USER, isCurrentUser);
        lotteryFragment.setArguments(bundle);
        return lotteryFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        lotteryStatus = bundle.getBoolean(TAG);
        winner = bundle.getString(WINNER);
        isCurrentUser = bundle.getBoolean(IS_CURRENT_USER);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.loadingFragment);
        setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.lottery_layout, container);
//        ButterKnife.bind(this, layout);
        initView(layout);
        cancelImg.setOnClickListener(this);
        centerCancel.setOnClickListener(this);
        return layout;
    }

    private void initView(View layout) {
        leftImg = (ImageView) layout.findViewById(R.id.left);
        centerImg = (ImageView) layout.findViewById(R.id.center);
        rightImg = (ImageView) layout.findViewById(R.id.right);
        lotteryLinearLayout = (LinearLayout) layout.findViewById(R.id.lottery_area);
        cancelImg = (ImageView) layout.findViewById(R.id.cancel);
        textView = (TextView) layout.findViewById(R.id.winner);
        lotteryBg = (RelativeLayout) layout.findViewById(R.id.lottery_bg);
        centerCancel = (ImageView) layout.findViewById(R.id.my_cancel);
        myName = (TextView) layout.findViewById(R.id.my_name);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lotteryStatus)
            lotteryStart();
        else if (!TextUtils.isEmpty(winner)) {
            lotteryStop(winner, isCurrentUser);
        } else {
            dismiss();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void lotteryStart() {
        resetView();
        lotteryStatus = true;
        leftImg.post(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -leftImg.getTop(), lotteryLinearLayout.getHeight());
                setTranslateAnimation(translateAnimation, new AccelerateInterpolator());
                leftImg.startAnimation(translateAnimation);
            }
        });

        centerImg.post(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, lotteryLinearLayout.getHeight(), -centerImg.getTop());
                setTranslateAnimation(translateAnimation, new LinearInterpolator());
                centerImg.startAnimation(translateAnimation);
            }
        });

        rightImg.post(new Runnable() {
            @Override
            public void run() {
                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, -rightImg.getTop(), lotteryLinearLayout.getHeight());
                setTranslateAnimation(translateAnimation, new AccelerateDecelerateInterpolator());
                rightImg.startAnimation(translateAnimation);
            }
        });
    }

    private void setTranslateAnimation(TranslateAnimation translateAnimation, Interpolator interpolator) {
        translateAnimation.setInterpolator(interpolator);
        translateAnimation.setDuration(200);
        translateAnimation.setRepeatCount(Animation.INFINITE);
        translateAnimation.setRepeatMode(Animation.RESTART);
    }

    private void resetView() {
        if (!lotteryStatus) {
            leftImg.setVisibility(View.VISIBLE);
            centerImg.setVisibility(View.VISIBLE);
            rightImg.setVisibility(View.VISIBLE);
            lotteryLinearLayout.setBackgroundResource(R.mipmap.lottering);
            textView.setVisibility(View.INVISIBLE);
            cancelImg.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void lotteryStop(String name, boolean isCurrentUser) {
        leftImg.clearAnimation();
        centerImg.clearAnimation();
        rightImg.clearAnimation();
        leftImg.setVisibility(View.INVISIBLE);
        centerImg.setVisibility(View.INVISIBLE);
        rightImg.setVisibility(View.INVISIBLE);

        if (!isCurrentUser) {
            if (!TextUtils.isEmpty(name)) {
                textView.setText(name);
                winner = name;
            } else cancelLottery();
            textView.setVisibility(View.VISIBLE);
            cancelImg.setVisibility(View.VISIBLE);
            cancelImg.setClickable(true);
            myName.setVisibility(View.INVISIBLE);
            centerCancel.setVisibility(View.INVISIBLE);
            centerCancel.setClickable(false);
            lotteryBg.setBackgroundResource(R.mipmap.lottery_result);
        } else {
            if (!TextUtils.isEmpty(name))
                myName.setText(name);
            else cancelLottery();
            myName.setVisibility(View.VISIBLE);
            centerCancel.setVisibility(View.VISIBLE);
            centerCancel.setClickable(true);
            cancelImg.setVisibility(View.INVISIBLE);
            textView.setVisibility(View.INVISIBLE);
            cancelImg.setClickable(false);
            lotteryBg.setBackgroundResource(R.mipmap.my_lottery);
        }
    }


    @Override
    public void onClick(View v) {
        cancelLottery();
    }

    private void cancelLottery() {
        dismiss();
        lotteryStatus = false;
    }

}