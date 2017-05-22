package com.youjing.yjeducation.wiget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 课程首页的新课推荐的GridView
 *孙腾腾
 * 创建时间：2016.06.12
 * @return
 */
public class CourseNewGridview extends GridView {
    public CourseNewGridview(Context context) {
        super(context);
    }

    public CourseNewGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CourseNewGridview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CourseNewGridview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
