package com.youjing.yjeducation.core;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.Environment;
import com.alibaba.sdk.android.callback.InitResultCallback;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.wxlib.util.SysUtil;
import com.jcmore2.appcrash.AppCrash;
import com.lecloud.config.LeCloudPlayerConfig;
import com.letv.proxy.LeCloudProxy;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.PlatformConfig;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.openim.CustomSampleHelper;
import com.youjing.yjeducation.talkfun.MainConsts;
import com.youjing.yjeducation.talkfun.NetMonitor;
import com.youjing.yjeducation.ui.actualize.activity.YJSplashActivity;
import com.youjing.yjeducation.util.CrashHandler;
import com.youjing.yjeducation.util.FileUtils;
import com.youjing.yjeducation.util.StringUtils;

import org.vwork.mobile.ui.VApplication;

import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
public class YJApplication extends VApplication implements Thread.UncaughtExceptionHandler {
    private String TAG = "YJApplication";
    public static final String NAMESPACE = "openimdemo";
    private static YJApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        //必须首先执行这部分代码, 如果在":TCMSSevice"进程中，无需进行云旺（OpenIM）和app业务的初始化，以节省内存;
        SysUtil.setApplication(this);
        if (SysUtil.isTCMSServiceProcess(this)) {
            return;
        }
        //第一个参数是Application Context
        //这里的APP_KEY即应用创建时申请的APP_KEY，同时初始化必须是在主进程中
        if (SysUtil.isMainProcess()) {
            CustomSampleHelper.initCustom();
            YWAPI.init(this, YJConfig.IM_APP_KEY);
        }

        CrashReport.initCrashReport(getApplicationContext(), YJConfig.BUGLY_APP_ID, false);
        PlatformConfig.setSinaWeibo(YJConfig.SINA_APP_KEY, YJConfig.SINA_APP_SECRET);
        mInstance = this;
        calcScreenSize();
        //获取versionName
        YJGlobal.setVersionName(FileUtils.getVersionName(getApplicationContext()));
        StringUtils.Log(TAG, "FileUtils.getVersionName(getApplicationContext()) = " + FileUtils.getVersionName(getApplicationContext()));
        //获取deviceid
        YJGlobal.setDeviceid(FileUtils.getDeviceId(getApplicationContext()));


        String processName = getProcessName(this, android.os.Process.myPid());
        if (getApplicationInfo().packageName.equals(processName)) {
            //TODO CrashHandler是一个抓取崩溃log的工具类（可选）
            CrashHandler.getInstance(this);
            LeCloudPlayerConfig.getInstance().setDeveloperMode(true).setIsApp().setUseLiveToVod(true);
            LeCloudProxy.init(getApplicationContext());
        }
//        initImageLoader();
        MainConsts.setIsConnected(NetMonitor.isNetworkAvailable(getApplicationContext()));

        //友盟统计新增场景设置
        MobclickAgent.setScenarioType(getApplicationContext(), MobclickAgent.EScenarioType.E_UM_NORMAL);
        MobclickAgent.enableEncrypt(true);
        MultiDex.install(this);
        initOneSDK(this);//初始化阿里云推送的SDK

       // Thread.setDefaultUncaughtExceptionHandler(this);
        AppCrash.init(this);
    }

    private static Context sContext;

    public static Context getContext() {
        return sContext;
    }

    public static String getProcessName(Context cxt, int pid) {
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps != null) {
            for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
                if (procInfo.pid == pid) {
                    return procInfo.processName;
                }
            }
        }
        return null;
    }



    public void exitApp() {
        System.gc();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private int mScreenWidth, mScreenHeight;

    //获取屏幕的高和款
    private void calcScreenSize() {
        DisplayMetrics dm = new DisplayMetrics();
        ((WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        YJConfig.mScreenWidth = mScreenWidth;
        YJConfig.mScreenHeight = mScreenHeight;

        StringUtils.Log("手机屏幕宽度", "" + YJConfig.mScreenWidth);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 初始化AlibabaSDK
     *
     * @param applicationContext
     */
    private void initOneSDK(final Context applicationContext) {
        AlibabaSDK.setEnvironment(Environment.ONLINE);
//        AlibabaSDK.setEnvironment(Environment.TEST);
        AlibabaSDK.asyncInit(applicationContext, new InitResultCallback() {

            @Override
            public void onSuccess() {
                StringUtils.Log(TAG, "init onesdk success");
                //alibabaSDK初始化成功后，初始化云推送通道
                initCloudChannel(applicationContext);
            }

            @Override
            public void onFailure(int code, String message) {
                StringUtils.Log(TAG, "init onesdk failed : " + message);
            }
        });
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(final Context applicationContext) {
        CloudPushService pushService = AlibabaSDK.getService(CloudPushService.class);
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess() {
                StringUtils.Log(TAG, "init cloudchannel success" + AlibabaSDK.getService(CloudPushService.class).getDeviceId());
                YJUserStudyData.setDevice(applicationContext);
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                StringUtils.Log(TAG, "init cloudchannel failed === errorMessage:" + errorMessage + ",errorCode:" + errorCode);
            }
        });
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
