package com.youjing.yjeducation.ui.actualize.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.wxlib.util.IWxCallback;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.ui.dispaly.activity.AYJSettingActivity;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.util.MarketUtils;
import com.youjing.yjeducation.util.SharedUtil;

import org.apache.http.Header;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/31
 * Time 17:50
 */
public class YJSettingActivity extends AYJSettingActivity {
    private String TAG = "YJSettingActivity";


    @Override
    protected void wipeCache() {
        clearAllCache(getContext());
        mTxt_wipe_cache.setText("0");
    }

    @Override
    protected void loginOut() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.LOGIN_OUT, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            logOut();
                            titleRedFalse();
                            YJUserStudyData.setDevice(getContext());
                            finishAll();
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, false)));
                            logOutIm();
                            break;
                        case 300:
                            break;
                        case 400:
                            showToast("用户不存在");
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void sendScore(Activity activity) {

        Intent i = MarketUtils.getIntent(activity);
        boolean b = MarketUtils.judge(activity, i);
        if (b == false) {
            startActivity(i);
        } else {
            showToast("您的手机没有安装应用市场");
        }

    }

    /**
     * 清除缓存
     *
     * @param context
     */
    public static void clearAllCache(Context context) {
        deleteDir(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            deleteDir(context.getExternalCacheDir());
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        if (dir != null) {
            return dir.delete();
        }
        return false;
    }

    private void titleRedFalse() {
        SharedUtil.setBoolean(getContext(), "MyMessageRed", false);
        SharedUtil.setBoolean(getContext(), "MyCourseRed", false);
        SharedUtil.setBoolean(getContext(), "MyOrderRed", false);
        SharedUtil.setBoolean(getContext(), "MyTaskRed", false);
        SharedUtil.setBoolean(getContext(), "MyGiftRed", false);
        SharedUtil.setBoolean(getContext(), "MyFriendsRed", false);
        SharedUtil.setBoolean(getContext(), "MyCouponRed", false);
    }


    private void logOut() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", false);
        // editor.putString("phone", "");
        // editor.putString("password", "");

        editor.commit();
    }

    private void logOutIm() {
        IYWLoginService loginService = YJGlobal.getmIMKit().getLoginService();
        loginService.logout(new com.alibaba.mobileim.channel.event.IWxCallback() {
            @Override
            public void onSuccess(Object... objects) {
                StringUtils.Log(TAG, "成功logOutIm");
            }

            @Override
            public void onError(int i, String s) {
                StringUtils.Log(TAG, "失败logOutIm=");
            }

            @Override
            public void onProgress(int i) {

            }
        });
    }

}
