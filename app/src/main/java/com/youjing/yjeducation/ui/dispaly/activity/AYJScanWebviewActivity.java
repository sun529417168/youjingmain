package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.util.ScanBannerUtils;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/7/7
 * Time 16:21
 */
@VLayoutTag(R.layout.activity_banner_webview)
public class AYJScanWebviewActivity extends YJBaseActivity implements IVClickDelegate {
    private String msg;
    protected boolean mIsLogin = false;
    private String TAG = "AYJScanWebviewActivity";

    @VViewTag(R.id.webview_rl_back)
    private RelativeLayout mWebview_rl_back;//返回按钮
    @VViewTag(R.id.web_view)
    private WebView mWebView;//webview
    @VViewTag(R.id.webview_txt_title)
    private TextView mWebview_txt_title;//标题


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    AYJScanWebviewActivity.this.finish();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        msg = getIntent().getStringExtra("msg");
        StringUtils.Log(TAG, msg);
        if (msg.indexOf("?") >= 0) {
            msg = msg + "&pkg_channel=" + YJGlobal.getChannel();
        } else {
            msg = msg + "?pkg_channel=" + YJGlobal.getChannel();
        }
        StringUtils.Log(TAG, "msg=" + msg);
        loadWebView(msg);
    }

    private void loadWebView(String url) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.requestFocus();

        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }else {
                    mWebView.loadUrl(url);
                }
                return true;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    return super.shouldInterceptRequest(view, url);
                } else {
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                    return null;
                }
            }
        });
        mWebView.addJavascriptInterface(new ResolveURL(this), "youjing");
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mWebview_txt_title.setText(title);
            }
        };
        // 设置setWebChromeClient对象
        mWebView.setWebChromeClient(wvcc);
        // 创建WebViewClient对象
        WebViewClient wvc = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 使用自己的WebView组件来响应Url加载事件，而不是使用默认浏览器器加载页面
                if (url.startsWith("scheme:") || url.startsWith("scheme:")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }else {
                    mWebView.loadUrl(url);
                }
                // 消耗掉这个事件。Android中返回True的即到此为止吧,事件就会不会冒泡传递了，我们称之为消耗掉
                return true;
            }
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (url.startsWith("http") || url.startsWith("https")) {
                    return super.shouldInterceptRequest(view, url);
                } else {
                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(in);
                    return null;
                }
            }
        };
        mWebView.setWebViewClient(wvc);
    }

    @Override
    public void onClick(View view) {
        if (view == mWebview_rl_back) {
            StringUtils.Log("onclick", "onclick==");
            AYJScanWebviewActivity.this.finish();
        }
    }

    private class ResolveURL {
        private Context mContext;

        public ResolveURL(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public void goApp(String name) {
            ScanBannerUtils.intentPage(AYJScanWebviewActivity.this, name, TAG, mIsLogin, 2);
            Message message = new Message();
            message.what = 0;
            mHandler.sendMessage(message);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebView != null){
            mWebView.destroy();
        }
    }



    @Override
    public void finish() {
      //  ViewGroup view = (ViewGroup) getWindow().getDecorView();
      // view.removeAllViews();
        super.finish();
    }
}
