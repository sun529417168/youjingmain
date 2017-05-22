package com.youjing.yjeducation.util;

/**
 * user  秦伟宁
 * Date 2016/5/5
 * Time 16:13
 */
public class ClickUtil  {
    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 1300) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
    public static boolean isFastDoubleClick2() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isFastClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 300) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 直播间赠送礼物免费的点击间隔
     * @return
     */
    public static boolean isGiftClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 180*1000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
