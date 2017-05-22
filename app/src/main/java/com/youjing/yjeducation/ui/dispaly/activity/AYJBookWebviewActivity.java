package com.youjing.yjeducation.ui.dispaly.activity;

import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

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

@VLayoutTag(R.layout.activity_book_webview)
public class AYJBookWebviewActivity extends YJBaseActivity implements IVClickDelegate{
    @VViewTag(R.id.web_view)
    private WebView mWebView;
  @VViewTag(R.id.rl_back)
    private RelativeLayout rl_back;
    @VViewTag(R.id.img_back)
    private ImageButton img_back;


    public static final VParamKey<String> URL = new VParamKey<String>(null);
    private String userURL;
    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        userURL = getTransmitData(URL);
        loadWebView(userURL);
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
        if (view == rl_back){
            finish();
        }else if (view == img_back ){
            finish();
        }
    }
}
