package com.youjing.yjeducation.ui.dispaly.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.UMShareListener;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_live_share)
public abstract class AYJLiveShareDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.btn_wx)
    private ImageButton btn_wx;
    @VViewTag(R.id.btn_qq)
    private ImageButton btn_qq;
    @VViewTag(R.id.btn_sina)
    private ImageButton btn_sina;
    @VViewTag(R.id.img_delete)
    private RelativeLayout img_delete;
    @VViewTag(R.id.re_gray_bg)
    private RelativeLayout re_gray_bg;



   protected boolean mIsLogin;
    private String TAG = "AYJShareDialog";
    private IWXAPI mApi;
    private UMShareListener umShareListener;
    private String mWinXinId;
    private Tencent mTencent;

    protected int mPosition = -1;


    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);

        mTencent = Tencent.createInstance(YJConfig.QQ_APP_ID, getActivity().getApplicationContext());
        initWXdata();

        getNotifyListener();
    }

    private void initWXdata() {

        mWinXinId = YJConfig.APP_ID;
        mApi = WXAPIFactory.createWXAPI(getContext(), mWinXinId);
        mApi.registerApp(mWinXinId);
    }

  /*    private void initShareData() {

          new ShareAction(getActivity())
                  .setCallback(umShareListener)
                  .withText("hello umeng video")
                  .withTargetUrl("http://www.baidu.com")
                  .withMedia(image)
                  .setPlatform(SHARE_MEDIA.SINA)
                  .share();

      }
    new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareActivity.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this,platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };


*/

    @Override
    public void onClick(View view) {
       if(view == img_delete) {
            this.close();
        }else if(view == re_gray_bg){
           this.close();
       } else if (view == btn_wx) {
            mPosition = 1;
            if (mApi.isWXAppInstalled()) {
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = getString(R.string.share_url);
                StringUtils.Log(TAG, " webpageUrl = " + webpage.webpageUrl);
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = getString(R.string.share_title);
                msg.description = getString(R.string.share_info);
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_wx_logo);
                msg.setThumbImage(thumb);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneSession;
                mApi.sendReq(req);
            } else {
                showToast(getString(R.string.txt_share_fail));
                return;
            }

        } else if (view == btn_qq) {
            mPosition = 3;
            final Bundle params = new Bundle();
            params.putString(QQShare.SHARE_TO_QQ_TITLE, getString(R.string.share_title));
            params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, getString(R.string.share_url));
            params.putString(QQShare.SHARE_TO_QQ_SUMMARY, getString(R.string.share_info));
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "http://youjing.oss-cn-beijing.aliyuncs.com/common/img_app_share.png");
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, getString(R.string.app_name));
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_DEFAULT);
            doShareToQQ(params);

            //朋友圈
        } else if (view == btn_sina) {
            mPosition = 2;
            //  UMImage image = new UMImage(getContext(), BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo));
   /*         UMImage image = new UMImage(getActivity(), "http://www.umeng.com/images/pic/social/integrated_3.png");
            new ShareAction(getActivity()).setPlatform(SHARE_MEDIA.SINA).setCallback(umShareListener)
                    .withText("umeng")
                    .withMedia(image)
//                       .withExtra(new UMImage(ShareActivity.this,R.drawable.ic_launcher))
                    .withTargetUrl("http://dev.umeng.com")
                    .share();
        }*/

            if (mApi.isWXAppInstalled()) {
                WXWebpageObject webpage = new WXWebpageObject();
                webpage.webpageUrl = getString(R.string.share_url);
                WXMediaMessage msg = new WXMediaMessage(webpage);
                msg.title = getString(R.string.share_title);
                msg.description = getString(R.string.share_info);
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_wx_logo);
                msg.setThumbImage(thumb);
                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("webpage");
                req.message = msg;
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                mApi.sendReq(req);
            } else {
                showToast(getString(R.string.txt_share_fail));
                return;
            }

        }
    }

    private void doShareToQQ(final Bundle params) {
        if (null == mTencent) {
            showToast(getString(R.string.share_fail));
            return;
        }
        mTencent.shareToQQ(getActivity(), params, null);
    }

    public static String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

     protected abstract void getNotifyListener();
}
