package com.youjing.yjeducation.question;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.LinearLayout;

public class WebViewUtils {
    public static void initDatas(WebView webView, String data, String imgUrl) {
        WebSettings settings = webView.getSettings();
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setBuiltInZoomControls(false);
        settings.setSupportZoom(false);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultFontSize(14);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {//去掉硬件加速 防止闪屏
//			webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
//		}

        String html = WebViewUtils.webViewHtml(webView) + data;
        if (!imgUrl.equals("")) {
            html = html + "\n" + "<img src=\"" + imgUrl + "\"/>";
        }
        webView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }


    public static WebView initWebView(Context context, String option, String optionContent, String imgUrl, boolean isShow) {
        WebView webView = new WebviewTouch(context);
        webView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.removeJavascriptInterface("searchBoxJavaBredge_");//防止攻击者可以向页面植入Javascript，通过反射在客户端中执行任意恶意代码。（在4.0至4.2的Android系统上，Webview会增加searchBoxJavaBredge_，导致远程代码执行。）
        webView.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });

        String temp = optionContent;
        if (isShow) {
            temp = option + "、" + temp;
        }
        initDatas(webView, temp, imgUrl);
        return webView;
    }


    public static class WebviewTouch extends WebView {
        public WebviewTouch(Context context) {
            super(context);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            // TODO Auto-generated method stub
            return false;
        }
    }

    public static String webViewHtml(WebView webView) {
        String html = "<style type=\"text/css\">img {border: none;vertical-align: middle;  max-width:" + (webView.getWidth() - 10) + "px;}</style><div style=\"line-height: 25px;\">";
        return html;
    }

    public static WebView initwebWebViewOther(Context context, String data, String imgUrl) {
        WebView webView = new WebviewTouch(context);
        webView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        webView.setHorizontalScrollBarEnabled(false);//水平不显示
        webView.setVerticalScrollBarEnabled(false); //垂直不显示
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webView.setOnLongClickListener(new OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        initDatas(webView, data, imgUrl);
        return webView;
    }
}
