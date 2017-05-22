package com.youjing.yjeducation.core;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;

import com.umeng.analytics.MobclickAgent;
import com.youjing.yjeducation.component.ActivityBox;
import com.youjing.yjeducation.ui.dispaly.dialog.TextDeviceDialog;

import org.vwork.mobile.ui.AVActivity;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
public class YJBaseActivity extends AVActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
