package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;


import com.youjing.yjeducation.model.ExpressionData;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: document your custom view class.
 */
public class ExpressionView extends FrameLayout implements AdapterView.OnItemClickListener{

    private Context mContext;
    private ViewPager mPagerContainer;
    /**表情适配器列表*/
    private List<ExpressionAdapter> mEmoAdapters;
    private List<View> mPageViews;
    /**每页列数*/
    private int mPageColumn = 5;
    private ExpressionData mDataAction;
    /**当前页*/
    private int mCurrentPage;
    public ExpressionView(Context context, ExpressionData dataAction) {
        super(context);
        init(dataAction,null, 0);
    }

    public ExpressionView(Context context, ExpressionData dataAction, AttributeSet attrs) {
        super(context, attrs);
        init(dataAction,attrs, 0);
    }

    public ExpressionView(Context context, ExpressionData dataAction, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(dataAction,attrs, defStyle);
    }

    private void init(ExpressionData dataAction,AttributeSet attrs, int defStyle) {
        mContext = this.getContext();
        mDataAction = dataAction;
        initUI();
    }

    /**
     * 初始化UI
     * */
    private void initUI(){
        initViews();
        initViewPager();
    }

    /**
     * 初始化iewPager
     * */
    private void  initViewPager(){
        mPagerContainer = new ViewPager(mContext);
        this.addView(mPagerContainer);
        ViewPagerAdapter pageAdapeter = new ViewPagerAdapter(mPageViews);
        mPagerContainer.setAdapter(pageAdapeter);
        mPagerContainer.setCurrentItem(0);
        mPagerContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mCurrentPage = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /***
     * 初始化表情GridView
     * */
    private void initViews(){
        if(mEmoAdapters == null)
            mEmoAdapters = new ArrayList<ExpressionAdapter>();
        if(mPageViews == null)
            mPageViews = new ArrayList<View>();
        GridView gridView;
        ExpressionAdapter emoAdapter;
        for(int i = 0,len=mDataAction.getPageEmoEntitys().size();i<len;i++){
            gridView = new GridView(mContext);
            emoAdapter = new ExpressionAdapter(mContext,mDataAction.getPageEmoEntitys().get(i));
            gridView.setAdapter(emoAdapter);
            mEmoAdapters.add(emoAdapter);

            gridView.setNumColumns(mPageColumn);
            gridView.setBackgroundColor(Color.TRANSPARENT);
            gridView.setHorizontalSpacing(1);
            gridView.setVerticalSpacing(1);
            gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
            gridView.setCacheColorHint(0);
            gridView.setPadding(5, 0, 5, 0);
            gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            gridView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
            gridView.setGravity(Gravity.CENTER);gridView.setGravity(Gravity.CENTER);
            gridView.setOnItemClickListener(this);
            gridView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mPageViews.add(gridView);
        }
    }

    private OnExpressionSelectedListener mListener;

    /**
     * 设置选中表情监听事件回调接口
     * */
    public void setOnEmotionSelectedListener(OnExpressionSelectedListener listener){
        mListener = listener;
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ExpressionEntity entry= (ExpressionEntity) mEmoAdapters.get(mCurrentPage).getItem(position);
        if (entry != null && !TextUtils.isEmpty(entry.character)) {
            if(mListener != null)
                mListener.OnExpressionSelected(entry);

        }
    }
}
