package com.youjing.yjeducation.ui.dispaly.activity;

import android.os.Build;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJBannerModel;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * user  秦伟宁
 * Date 2016/5/5
 * Time 11:19
 */

@VLayoutTag(R.layout.activity_banner_webview)
public class AYJWebviewActivity extends YJBaseActivity implements IVClickDelegate {
    @VViewTag(R.id.web_view)
    private WebView mWebView;
    @VViewTag(R.id.webview_rl_back)
    private RelativeLayout mWebview_rl_back;//返回按钮
    @VViewTag(R.id.webview_txt_title)
    private TextView mWebview_txt_title;//标题

    private YJBannerModel yjBannerModel;
    public static final VParamKey<YJBannerModel> YJ_BANNER_MODEL = new VParamKey<YJBannerModel>(null);
    public static final String USERAGREEMENT = "";

    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        String userURL = getIntent().getStringExtra("userURL");
        yjBannerModel = getTransmitData(YJ_BANNER_MODEL);
        if (yjBannerModel != null) {
            loadWebView(yjBannerModel.getJumpUrl());
            mWebview_txt_title.setText(yjBannerModel.getTitle());
        } else if (!TextUtils.isEmpty(userURL)) {
            loadWebView(userURL);
            mWebview_txt_title.setText("注册条款和隐私政策");
        }
    }

    private void loadWebView(String url) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.requestFocus();
        mWebView.loadUrl(url);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                loadWebView(url);
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == mWebview_rl_back) {
            finish();
        }
    }
}
