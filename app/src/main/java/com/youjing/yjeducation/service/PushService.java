package com.youjing.yjeducation.service;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;

/**
 * stt
 * 创建时间：2016.6.27
 * 类说明：统计用户活跃度
 */
public class PushService extends Service {
    private Context context;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public PushService() {
    }

    public PushService(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setMessage("是否接受文件?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        Dialog ad = builder.create();
//      ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_DIALOG); //系统中关机对话框就是这个属性
        ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        ad.setCanceledOnTouchOutside(false);                                   //点击外面区域不会让dialog消失
        ad.show();
    }
}
