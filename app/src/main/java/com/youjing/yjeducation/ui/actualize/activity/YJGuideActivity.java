package com.youjing.yjeducation.ui.actualize.activity;

import com.youjing.yjeducation.ui.dispaly.activity.AYJGuideActivity;

/**
 * user  秦伟宁
 * Date 2016/3/11
 * Time 11:46
 */
public class YJGuideActivity extends AYJGuideActivity {

    @Override
    protected void start() {
//        startActivity(YJMainActivity.class);
        startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 3).set(YJLoginActivity.MY_LOGIN_OUT, false)));
        finishAll();
    }

    @Override
    protected boolean onBackKeyClick() {
        finishAll();
        return true;
    }

}
