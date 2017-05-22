package com.youjing.yjeducation.component;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.youjing.yjeducation.util.MyUtils;

import java.util.Map;

/**
 * @author: stt
 * @since: 2016.06.22
 * @version: 1.1
 * @feature: 用于接收推送的通知和消息
 */
public class MyMessageReceiver extends MessageReceiver {

    //消息接收部分的LOG_TAG
    public static final String REC_TAG = "receiver";

    /**
     * 推送通知的回调方法
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        //TODO 处理推送通知
        if (null != extraMap) {
            for (Map.Entry<String, String> entry : extraMap.entrySet()) {
                StringUtils.Log(REC_TAG, "@Get diy param : Key=" + entry.getKey() + " , Value=" + entry.getValue());
            }
        } else {
            StringUtils.Log(REC_TAG, "@收到通知 && 自定义消息为空");
        }
        StringUtils.Log(REC_TAG, "收到一条推送通知 ： " + title);
    }

    /**
     * 推送消息的回调方法
     *
     * @param context
     * @param cPushMessage
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        try {
            StringUtils.Log("MyMessageReceiver===", "收到一条推送消息 ： " + cPushMessage.getContent());
//            new MessageDao(context).add(new MessageEntity(cPushMessage.getMessageId().substring(6, 16), Integer.valueOf(cPushMessage.getAppId()), cPushMessage.getTitle(), cPushMessage.getContent(), new SimpleDateFormat("HH:mm:ss").format(new Date())));
            MyUtils.intentAvtivity(context, cPushMessage);
        } catch (Exception e) {
            StringUtils.Log(REC_TAG, e.toString());
        }

    }


    /**
     * 从通知栏打开通知的扩展处理
     *
     * @param context
     * @param title
     * @param summary
     * @param extraMap
     */
    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {
        StringUtils.Log(REC_TAG, "onNotificationOpened ： " + " : " + title + " : " + summary + " : " + extraMap);
    }

    @Override
    public void onNotificationRemoved(Context context, String messageId) {
        StringUtils.Log(REC_TAG, "onNotificationRemoved ： " + messageId);
    }
}