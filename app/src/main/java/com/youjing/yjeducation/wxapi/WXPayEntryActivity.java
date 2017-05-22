package com.youjing.yjeducation.wxapi;

import android.content.Intent;
import android.os.Bundle;
import com.youjing.yjeducation.util.StringUtils;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.util.SharedUtil;

/**
 * user  秦伟宁
 * Date 2016/4/14
 * Time 11:58
 */
public class WXPayEntryActivity extends YJBaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringUtils.Log(TAG, "111111111111");
        api = WXAPIFactory.createWXAPI(this, YJConfig.APP_ID, false);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        StringUtils.Log(TAG, "onPayFinish, errCode = " + resp.errCode);
        switch (resp.errCode) {
            case 0:
                if ("buycourse".equals(SharedUtil.getString(getContext(), "wxpat"))) {
                    showToast("购买成功");
                    notifyListener(YJNotifyTag.COLSE_BUYCOURSE, "");
                    this.finish();
                }
                if ("recharge".equals(SharedUtil.getString(getContext(), "wxpat"))) {
                    notifyListener(YJNotifyTag.COLSE_RECHARGE, "");
                    this.finish();
                }
                break;
            case -1:
                showToast("支付失败");
                notifyListener(YJNotifyTag.WX_PAY_CANCEL,"");
                this.finish();
                break;
            case -2:
                showToast("取消购买");
                this.finish();
                break;
        }
    }

}