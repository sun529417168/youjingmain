package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by tony_zhang on 2015/8/31.
 */
public class NetMonitor {
    /**
     * 检测网络是否可用
     *
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connManager.getActiveNetworkInfo() != null) {
//        return connManager.getActiveNetworkInfo().isAvailable();
//        }
        return connManager.getActiveNetworkInfo() != null;
    }
}
