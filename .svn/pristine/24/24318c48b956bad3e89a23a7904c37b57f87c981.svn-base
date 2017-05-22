package com.youjing.yjeducation.talkfun.plackback;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.talkfun.sdk.data.PlaybackDataManage;


/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/14.
 */
public abstract class PlaybackBasicFragment extends Fragment {


    public boolean isShow = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isShow = isVisibleToUser;
        if (isVisibleToUser) {
            updateAdapter();
            startAutoScroll();
        } else {
            stopAutoScroll();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isShow) {
            resetAdapterData();
            startAutoScroll();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (isShow) {
            stopAutoScroll();
        }
    }

    // list view 上拉加载更多
    public AbsListView.OnScrollListener scrollListener = new AbsListView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (scrollState == SCROLL_STATE_IDLE)
                getMoreData();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    };

    //滑动 list view时停止跟随
    public Handler handler = new Handler();
    public View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.removeCallbacksAndMessages(null);
                    pauseAutoScroll();
                    break;
                case MotionEvent.ACTION_UP:

                    handler.removeCallbacksAndMessages(null);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            resumeAutoScroll();
                        }
                    }, 1000);
                    break;
            }
            return false;
        }
    };

    public void stopAutoScroll() {
        PlaybackDataManage.getInstance().stopAutoScroll();
    }

    public void pauseAutoScroll() {
        PlaybackDataManage.getInstance().pauseAutoScroll();
    }

    public void resumeAutoScroll() {
        PlaybackDataManage.getInstance().resumeAutoScroll();
    }

    abstract void updateAdapter();

    abstract void getMoreData();

    abstract void startAutoScroll();

    abstract void resetAdapterData();
}