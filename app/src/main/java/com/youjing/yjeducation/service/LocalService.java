package com.youjing.yjeducation.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.youjing.yjeducation.util.StringUtils;

import com.youjing.yjeducation.util.SharedUtil;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * stt
 * 创建时间：2016.5.16
 * 类说明：统计用户活跃度
 */
public class LocalService extends Service {
    //主要功能，广播接收器
    private final BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        //添加过滤器并注册
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(receiver, filter);


        //创建计划任务
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //每天凌晨自动更新数据
//                SharedUtil.isValue(getApplicationContext(), "CustomerActive");
                SharedUtil.setString(getApplicationContext(), "CustomerActive", "");
            }
        };
        Timer timer = new Timer(true);
        int hour = new Date().getHours();
        timer.schedule(task, (24 - hour) * 3600 * 1000, 24 * 3600 * 1000);
        //timer.schedule(task,180*1000, 180*1000);//测试用
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
