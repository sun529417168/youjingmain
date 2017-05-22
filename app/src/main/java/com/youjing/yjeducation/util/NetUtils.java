package com.youjing.yjeducation.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by w7 on 2016/5/3.
 */
public class NetUtils {


    /**
     * sharePerferences 的 字段名称代表意义
     *  watch_video    是否3g观看
     *  download_video 是否3g缓存
     *  new_message    是否接受新信息
     */


    // 判断当前是否使用的是 WIFI网络
    public static boolean isWifiActive(Context icontext) {
        Context context = icontext.getApplicationContext();
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info;
        if (connectivity != null) {
            info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    // 判断3G是否可用 返回True为可用，false不可用
    public static boolean is3GCanUseWatchVideo(Activity activity) {
        Boolean isUse = false;

        SharedUtil.getPreference(activity);
        Boolean status3G = SharedUtil.getBoolean(activity, "watch_video", false);
        Boolean statusWifi = isWifiActive(activity);

        if(status3G || statusWifi){
            isUse=true;
        }

        return isUse;
    }
}
