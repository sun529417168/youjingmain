package com.youjing.yjeducation.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.youjing.yjeducation.util.StringUtils;
import android.widget.Toast;

import com.youjing.yjeducation.common.config.YJConfig;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;

/**
 * user  秦伟宁
 * Date 2016/5/17
 * Time 14:02
 */
public class WXEntryActivity extends YJBaseActivity implements IWXAPIEventHandler {

    private String TAG = "WXEntryActivity";
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StringUtils.Log(TAG,"WXEntryActivity");
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, YJConfig.APP_ID, false);
        api.registerApp(YJConfig.APP_ID);
        api.handleIntent(getIntent(), this);

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq baseReq) {
       // Toast.makeText(this, "baseReq = ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResp(BaseResp baseResp) {
      //  Toast.makeText(this, "openid = ", Toast.LENGTH_SHORT).show();
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO,"sucess");
               // Toast.makeText(this, "分享成功 ", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO,"fail");
                Toast.makeText(this, "分享取消 ", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO,"fail");
                Toast.makeText(this, "分享错误 ", Toast.LENGTH_SHORT).show();
                finish();
                break;
            default:
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO,"fail");
                finish();
                break;
        }
    }
}
