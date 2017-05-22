package com.youjing.yjeducation.util;

import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * user  秦伟宁
 * Date 2016/6/29
 * Time 10:49
 */
public  class AnimationUtils {

    public static TranslateAnimation moveToViewBottom(int time) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        mHiddenAction.setDuration(time);
        return mHiddenAction;
    }


    /**
     * 从控件的底部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation moveToViewLocation(int time) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(time);
        return mHiddenAction;
    }






    /**
     * 从控件的顶部移动到控件所在位置
     *
     * @return
     */
    public static TranslateAnimation upmoveToViewLocation(int time) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mHiddenAction.setDuration(time);
        return mHiddenAction;
    }


    /**
     * 从控件所在位置向上移动消失
     * @return
     */
    public static TranslateAnimation upmoveToInvisable(int time) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(time);
        return mHiddenAction;
    }
}
