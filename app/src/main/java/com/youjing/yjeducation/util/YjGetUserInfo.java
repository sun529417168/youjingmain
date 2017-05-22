package com.youjing.yjeducation.util;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.UiThread;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.letv.universal.iplay.ISplayer;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJCourseExpiredDialog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.utils.base.VParams;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * user  秦伟宁
 * Date 2016/9/1
 * Time 11:55
 */
public class YjGetUserInfo {
    public static YJUser mYjUser;
    private static UserCoin userCoin;
    private IVActivity ivActivity;

    public YjGetUserInfo(IVActivity ivActivity, UserCoin userCoin) {
        this.userCoin = userCoin;
        this.ivActivity = ivActivity;
    }

    public static void  getUserCoin(final IVActivity ivActivity, final TextView textView) {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log("YjGetUserInfo", "getUserInfo失败=" + s);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log("getUserCoin", "成功getUserInfo=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            mYjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                            YJGlobal.setYjUser(mYjUser);
                            textView.setText(mYjUser.getCoin());
                            break;
                        case 300:
                            break;
                        case 500:
                            break;
                        case 600:
                            ivActivity.startActivity(ivActivity.createIntent(YJLoginActivity.class, ivActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            ivActivity.finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public static void getUserCoinInfo(final IVActivity ivActivity) {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log("YjGetUserInfo", "getUserInfo失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log("getUserCoin", "成功getUserInfo=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            mYjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                            YJGlobal.setYjUser(mYjUser);
                            userCoin.getCoin(ivActivity,mYjUser);
                            break;
                        case 300:
                            break;
                        case 500:
                            break;
                        case 600:
                            ivActivity.startActivity(ivActivity.createIntent(YJLoginActivity.class, ivActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            ivActivity.finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface UserCoin {
        void getCoin(IVActivity view, YJUser mYjUser);
    }



}


















