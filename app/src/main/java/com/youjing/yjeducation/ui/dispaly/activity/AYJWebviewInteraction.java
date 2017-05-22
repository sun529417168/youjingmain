package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.model.YJCourseTypeModel;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.ParseURL;
import com.youjing.yjeducation.util.StringUtils;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * user  秦伟宁
 * Date 2016/7/9
 * Time 10:33
 */
@VLayoutTag(R.layout.activity_banner_webview)
public class AYJWebviewInteraction extends YJBaseActivity implements IVClickDelegate {

    private String msg;
    @VViewTag(R.id.web_view)
    private WebView mWebView;
    @VViewTag(R.id.webview_rl_back)
    private RelativeLayout mWebview_rl_back;//返回按钮
    @VViewTag(R.id.webview_txt_title)
    private TextView mWebview_txt_title;//标题

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebview_txt_title.setText("活动");
        msg = getIntent().getStringExtra("msg");

        loadWebView(msg);
    }


    private void loadWebView(String url) {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.requestFocus();


        String uri = "file:///android_asset/js_test2.html";
//        String uri = "http://10.10.10.244:8087/activity/view.do";
        mWebView.loadUrl(uri);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.addJavascriptInterface(new MyJava(this), "javaObject");
    }

    @Override
    public void onClick(View view) {
        if (view == mWebview_rl_back) {
            finish();
        }
    }

    private class MyJava {
        private Context mContext;

        public MyJava(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public void javaFun(String name) {
            name = "http://10.10.10.226:6080/qrCode/jump.do?yj_type_one=in&yj_type_app=course_detail&courseId=466";
            showToast("js传来的参数" + name);
//            startActivity(YJAboutAppActivity.class);
            handleDecode(name);
        }
    }

    protected boolean mIsLogin = false;
    private boolean isInApp = false;
    private String TAG = "";
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    notifyListener(YJNotifyTag.MY_TASK, "");
                    break;
                }
                case 1: {
                    notifyListener(YJNotifyTag.MY_CARD, "");
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 结果处理
     */
    public void handleDecode(String msg) {
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        if (msg == null || "".equals(msg)) {
            showToast("无法识别");
            return;
        }
        if (StringUtils.isUrl(msg) == false) {
            showToast("无法识别的二维码");
            this.finish();
            return;
        }
        StringUtils.Log(TAG, "msg=" + msg);
        String strRequestKeyAndValues = "";
        Map<String, String> mapRequest = ParseURL.URLRequest(msg);
        for (String strRequestKey : mapRequest.keySet()) {
            String strRequestValue = mapRequest.get(strRequestKey);
            strRequestKeyAndValues += "key:" + strRequestKey + ",Value:" + strRequestValue + ";";
            StringUtils.Log(TAG, "strRequestKeyAndValues=" + strRequestKeyAndValues);
            if (strRequestKey.equals("yj_type_one")) {
                isInApp = true;
            }
        }
        if (!isInApp) {

        } else {
            if (!TextUtils.isEmpty(mapRequest.get("yj_type_one")) && mapRequest.get("yj_type_one").equals("in")) {
                String yj_type_app = mapRequest.get("yj_type_app");
                StringUtils.Log(TAG, "yj_type_app=" + yj_type_app);
                if (!TextUtils.isEmpty(yj_type_app)) {
                    switch (yj_type_app) {
                        case "course_list":
                            String courseTypeId = mapRequest.get("coursetypeid");
                            if (!TextUtils.isEmpty(courseTypeId)) {
                                scanCourseList(courseTypeId);
                            }
                            finish();
                            break;
                        case "course_detail":
                            String courseId = mapRequest.get("courseid");
                            StringUtils.Log(TAG, "courseid=" + courseId);
                            if (!TextUtils.isEmpty(courseId)) {
                                getCourseCatalog(courseId, false);
                            }
//                            finish();
                            break;
                        case "live_menu":
                            notifyListener(YJNotifyTag.MAIN_VIDEO, 2);
                            finish();
                            break;
                        case "questions_list":
                            notifyListener(YJNotifyTag.MAIN_QUESTION, 1);
                            finish();
                            break;
                        case "task_list":
                            if (mIsLogin) {
                                notifyListener(YJNotifyTag.MAIN_MY, 3);
                                final Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    public void run() {
                                        mHandler.sendEmptyMessage(0);
                                        timer.cancel();
                                    }
                                }, 300);
                            } else {                                                                                           //1：去我的卡包  2：任务中心
                                startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.REGIST_INFO, "2").set(YJLoginActivity.LOGIN_OUT, -1).set(YJLoginActivity.MY_LOGIN_OUT, false)));
                            }
                            finish();
                            break;
                        case "card_list":
                            if (mIsLogin) {
                                notifyListener(YJNotifyTag.MAIN_MY, 3);
                                final Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    public void run() {
                                        mHandler.sendEmptyMessage(1);
                                        timer.cancel();
                                    }
                                }, 300);
                            } else {                                                                                        //1：去我的卡包  2：任务中心
                                startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.REGIST_INFO, "1").set(YJLoginActivity.LOGIN_OUT, -1).set(YJLoginActivity.MY_LOGIN_OUT, false)));
                            }
                            finish();
                            break;
                        case "create_order":
                            String orderCourseId = mapRequest.get("courseid");
                            if (!TextUtils.isEmpty(orderCourseId)) {
                                getCourseCatalog(orderCourseId, true);
                            }
                            finish();
                            break;
                        default:
                            showToast("无法识别");
                            break;
                    }
                }
            } else if (!TextUtils.isEmpty(mapRequest.get("yj_type_one")) && mapRequest.get("yj_type_one").equals("out")) {

            }
        }
    }

    //扫码进入直播详情页面
    private void getCourseCatalog(String courseId, final boolean isScan) {
        Intent in = new Intent(this,YJCoursePlayNewActivity.class);
        in.putExtra("courseId",courseId);
        startActivity(in);
    }


    private boolean isHasCourseTypeId = true;
    protected List<YJCourseTypeModel> yjCourseTypeModelList;

    private void scanCourseList(String courseTypeId) {
        yjCourseTypeModelList = YJGlobal.getYjCourseTypeModelList();
        if (yjCourseTypeModelList != null) {
            for (int i = 0; i < yjCourseTypeModelList.size(); i++) {
                if (yjCourseTypeModelList.get(i).getCourseTypeId().equals(courseTypeId)) {
                    isHasCourseTypeId = false;
                    saveGrade(yjCourseTypeModelList.get(i).getName(), i);
                    Intent intent = new Intent(getContext(),YJCourseListlActivity.class);
                    intent.putExtra("courseTypeId",yjCourseTypeModelList.get(i).getCourseTypeId());
                    startActivity(intent);
                }
            }
            if (isHasCourseTypeId) {
                saveGrade(yjCourseTypeModelList.get(0).getName(), 0);
                Intent intent = new Intent(getContext(),YJCourseListlActivity.class);
                intent.putExtra("courseTypeId",yjCourseTypeModelList.get(0).getCourseTypeId());
                startActivity(intent);
            }
        } else {
            return;
        }

    }


    private void saveGrade(String course, int num) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course", course);
        editor.putInt("courseNum", num);
        editor.commit();
    }

}
