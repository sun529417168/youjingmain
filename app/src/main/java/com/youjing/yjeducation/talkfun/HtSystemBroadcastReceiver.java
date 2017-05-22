package com.youjing.yjeducation.talkfun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/27.
 */
public class HtSystemBroadcastReceiver extends BroadcastReceiver {
    private SystemMessageReceiver mSystemMessageReceiver;

    public HtSystemBroadcastReceiver(SystemMessageReceiver receiver) {
        this.mSystemMessageReceiver = receiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mSystemMessageReceiver != null)
            mSystemMessageReceiver.receiverSystemMessage(intent);
    }

    public interface SystemMessageReceiver {
        void receiverSystemMessage(Intent intent);
    }
}
