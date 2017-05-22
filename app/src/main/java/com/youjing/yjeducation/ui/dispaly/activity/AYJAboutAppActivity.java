package com.youjing.yjeducation.ui.dispaly.activity;

import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJTitleLayoutController;

import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/4/5
 * Time 15:40
 */
@VLayoutTag(R.layout.activity_about_app)
public class AYJAboutAppActivity extends YJBaseActivity {
    @VViewTag(R.id.txt_app_version)
    private TextView mTxt_app_version;

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "关于游鲸", true);

        mTxt_app_version.setText("版本号： "+YJGlobal.getVersionName());
    }
}
