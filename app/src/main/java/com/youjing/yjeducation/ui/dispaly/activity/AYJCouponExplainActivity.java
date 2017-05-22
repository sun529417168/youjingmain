package com.youjing.yjeducation.ui.dispaly.activity;

import android.view.View;
import android.widget.RelativeLayout;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.wiget.MsgListView;

import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 21:00
 */
@VLayoutTag(R.layout.activity_coupon_explain)
public  abstract class AYJCouponExplainActivity extends YJBaseActivity {

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "优惠券说明", true);

    }


}
