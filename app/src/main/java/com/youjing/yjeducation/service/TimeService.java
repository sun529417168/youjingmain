package com.youjing.yjeducation.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import com.youjing.yjeducation.util.StringUtils;

import com.youjing.yjeducation.util.SharedUtil;

import java.util.Calendar;

/**
 * stt
 * 创建时间：2016.5.16
 * 类说明：统计用户活跃度
 */
public class TimeService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private Calendar mCalendar;

    @Override
    public void onCreate() {
        super.onCreate();

        new Thread() {
            public void run() {
                while (true) {
//                    setTime();
                    setTime2();
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }


    private void setTime2() {
        long sysTime = System.currentTimeMillis();
        CharSequence sysTimeStr = DateFormat.format("HH:mm", sysTime);
        if ("00:00".equals(sysTimeStr.toString().trim())) {
            SharedUtil.setString(getApplicationContext(), "CustomerActive", "");
        }
    }

    private void setTime() {
        if (null == mCalendar) {
            mCalendar = Calendar.getInstance();
        }
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        //获取小时
        mCalendar.get(Calendar.HOUR_OF_DAY);
        if (Calendar.HOUR_OF_DAY == 0) {
            SharedUtil.setString(getApplicationContext(), "CustomerActive", "");
        }
    }
}
