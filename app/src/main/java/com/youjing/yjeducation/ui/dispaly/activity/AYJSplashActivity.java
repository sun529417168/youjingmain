package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Intent;
import android.net.Uri;
import com.youjing.yjeducation.util.StringUtils;

import com.baidu.mobstat.StatService;
import com.umeng.analytics.AnalyticsConfig;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJUpdate;
import com.youjing.yjeducation.service.TimeService;
import com.youjing.yjeducation.util.StringUtils;

import org.vwork.mobile.ui.utils.VLayoutTag;

import static u.aly.x.R;

/**
 * user  秦伟宁
 * Date 2016/3/11
 * Time 10:46
 */
@VLayoutTag(R.layout.splash)
public class AYJSplashActivity extends YJBaseActivity {

    private String TAG = "AYJSplashActivity";
    @Override
    protected void onLoadingView() {
        super.onLoadingView();
        StringUtils.Log(TAG,"渠道号"+AnalyticsConfig.getChannel(getContext()));
        YJGlobal.setChannel(AnalyticsConfig.getChannel(getContext()));

        Intent i_getvalue = getIntent();
        String action = i_getvalue.getAction();

        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = i_getvalue.getData();
            if (uri != null) {
                String name = uri.getQueryParameter("name");
                String age = uri.getQueryParameter("age");
            }
        }
        YJUpdate update = new YJUpdate(this, false, true);
        update.UpdateApp(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(getContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(getContext());
    }
}