package com.youjing.yjeducation.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youjing.yjeducation.R;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 17:07
 */
public class MServicePullToRefreshListView extends ListView implements AbsListView.OnScrollListener {

    private static final String TAG = "listview";

    private final static int RELEASE_To_REFRESH = 0;
    private final static int PULL_To_REFRESH = 1;
    private final static int REFRESHING = 2;
    private final static int DONE = 3;
    private final static int LOADING = 4;
    private final static int FAIL = 5;
    private final static int RATIO = 3;
    private LayoutInflater inflater;
    private LinearLayout headView;
    private TextView mTextviewTips;
    private ImageView arrowImageView;
    private ProgressBar progressBar;
    private RotateAnimation animation;
    private RotateAnimation reverseAnimation;
    private boolean isRecored;
    private int headContentWidth;
    private int headContentHeight;
    private int startY;
    private int startX;
    private int firstItemIndex;
    private int state;
    private boolean isBack;
    private OnRefreshListener refreshListener;
    private boolean isRefreshable;

    public MServicePullToRefreshListView(Context context) {
        super(context);
        init(context);
    }

    public MServicePullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setCacheColorHint(context.getResources().getColor(R.color.bg_color));
        inflater = LayoutInflater.from(context);
        headView = (LinearLayout) inflater.inflate(R.layout.refresh_head, null);
        arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
        arrowImageView.setMinimumWidth(70);
        arrowImageView.setMinimumHeight(50);
        progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
        mTextviewTips = (TextView) headView.findViewById(R.id.head_tipsTextView);
        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headContentWidth = headView.getMeasuredWidth();
        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();
        addHeaderView(headView, null, false);
        setOnScrollListener(this);
        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);
        reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);
        state = DONE;
        isRefreshable = false;
    }

    public void onScroll(AbsListView arg0, int firstVisiableItem, int visibleItemCount, int totalItemCount) {
        firstItemIndex = firstVisiableItem;
    }

    public void onScrollStateChanged(AbsListView arg0, int arg1) {
    }

//    public boolean dispatchTouchEvent(MotionEvent event){
//        if(Math.abs(event.getY() - startY) < Math.abs(event.getX() - startX)){
//            return false;
//        }
//        return  super.dispatchTouchEvent(event);
//    }

    //    touch状态后的各种事件的处理
    public boolean onTouchEvent(MotionEvent event) {

        if (isRefreshable) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (firstItemIndex == 0 && !isRecored) {
                        isRecored = true;
                        startY = (int) event.getY();
                        startX = (int) event.getX();
                    }
                    break;

                case MotionEvent.ACTION_UP:

                    if (state != REFRESHING && state != LOADING) {
                        if (state == DONE) {
                        }
                        if (state == PULL_To_REFRESH) {
                            state = DONE;
                            changeHeaderViewByState();
                        }
                        if (state == RELEASE_To_REFRESH) {
                            state = REFRESHING;
                            changeHeaderViewByState();
                            onRefresh();
                        }
                    }

                    isRecored = false;
                    isBack = false;

                    break;

                case MotionEvent.ACTION_MOVE:
                    int tempY = (int) event.getY();

                    if (!isRecored && firstItemIndex == 0) {
                        isRecored = true;
                        startY = tempY;
                    }

                    if (state != REFRESHING && isRecored && state != LOADING) {
                        if (state == RELEASE_To_REFRESH) {

                            setSelection(0);
                            if (((tempY - startY) / RATIO < headContentHeight) && (tempY - startY) > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            } else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();
                            } else {
                            }
                        }
                        if (state == PULL_To_REFRESH) {

                            setSelection(0);
                            if ((tempY - startY) / RATIO >= headContentHeight) {
                                state = RELEASE_To_REFRESH;
                                isBack = true;
                                changeHeaderViewByState();
                            } else if (tempY - startY <= 0) {
                                state = DONE;
                                changeHeaderViewByState();
                            }
                        }
                        if (state == DONE) {
                            if (tempY - startY > 0) {
                                state = PULL_To_REFRESH;
                                changeHeaderViewByState();
                            }
                        }
                        if (state == PULL_To_REFRESH) {
                            headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);

                        }
                        if (state == RELEASE_To_REFRESH) {
                            headView.setPadding(0, (tempY - startY) / RATIO - headContentHeight, 0, 0);
                        }

                    }

                    break;
            }
        }

        return super.onTouchEvent(event);
    }

    //    头部变化
    private void changeHeaderViewByState() {
        switch (state) {
            case RELEASE_To_REFRESH:
                arrowImageView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                mTextviewTips.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(animation);
                mTextviewTips.setText("松开刷新");
                break;
            case PULL_To_REFRESH:
                progressBar.setVisibility(View.GONE);
                mTextviewTips.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.VISIBLE);
                if (isBack) {
                    isBack = false;
                    arrowImageView.clearAnimation();
                    arrowImageView.startAnimation(reverseAnimation);
                    mTextviewTips.setText("下拉刷新");
                } else {
                    mTextviewTips.setText("下拉刷新");
                }
                break;

            case REFRESHING:
                headView.setPadding(0, 0, 0, 0);
                progressBar.setVisibility(View.VISIBLE);
                arrowImageView.clearAnimation();
                arrowImageView.setVisibility(View.GONE);
                mTextviewTips.setText("正在刷新...");
                break;
            case DONE:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                progressBar.setVisibility(View.GONE);
                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.mipmap.img_pull_refresh_arrow);
                mTextviewTips.setText("下拉刷新");
                break;
            case FAIL:
                headView.setPadding(0, -1 * headContentHeight, 0, 0);
                progressBar.setVisibility(View.GONE);
                arrowImageView.clearAnimation();
                arrowImageView.setImageResource(R.mipmap.img_pull_refresh_arrow);
                break;
        }
    }

    //    刷新监听
    public void setonRefreshListener(boolean isEnable) {
        isRefreshable = isEnable;
    }

    public void setonRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    //    刷新完成
    public void onRefreshComplete() {
        state = DONE;
        changeHeaderViewByState();
    }

    public void onRefreshFail() {
        state = FAIL;
        changeHeaderViewByState();
    }

    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public void setAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);
    }

    public interface onGetServiceRoomListener {
        void getRoomList();
    }
}