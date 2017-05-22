package com.youjing.yjeducation.util;

import android.content.Intent;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJIndexUserInfo;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stt
 *         类说明：公共请求数据接口的工具类
 *         创建时间：2016.7.5
 */
public class MyRequestUtils {
    public static final String TAG = "MyRequestUtils";

    public static void getUserInfo(final YJCourseActivity mIVActivity, final boolean loginFlag) {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getUserInfo失败=" + s);
                mIVActivity.showToast(mIVActivity.getContext().getString(R.string.no_net_work));
                mIVActivity.finishAll();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            YJUser yjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                            StringUtils.Log(TAG, "yjUser.toString()=" + yjUser.toString());
                            YJGlobal.setYjUser(yjUser);
                            getUserIndexInfo(mIVActivity, loginFlag);
                            break;
                        case 300:
                            break;
                        case 500:
                            break;
                        case 600:
                            mIVActivity.startActivity(mIVActivity.createIntent(YJLoginActivity.class, mIVActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            mIVActivity.finishAll();
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

    public static void getUserIndexInfo(final YJCourseActivity mIVActivity, final boolean loginFlag) {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INDEX, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getUserIndexInfo失败=" + s);
                mIVActivity.showToast(mIVActivity.getContext().getString(R.string.no_net_work));
                mIVActivity.finishAll();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            YJIndexUserInfo yjIndexUserInfo = JSON.parseObject(jsonData.getString("customer"), YJIndexUserInfo.class);
                            StringUtils.Log(TAG, "yjIndexUserInfo.toString()=" + yjIndexUserInfo.toString());
                            YJGlobal.setYjIndexUserInfo(yjIndexUserInfo);
                            break;
                        case 300:
                            break;
                        case 500:
                            break;
                        case 600:
                            mIVActivity.startActivity(mIVActivity.createIntent(YJLoginActivity.class, mIVActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            mIVActivity.finishAll();
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


    /**
     * stt
     * 方法说明：课程列表的点击时间（暑期衔接课那种列表）
     *
     * @serialData 2016.07.28
     */
    public static void getCourseCatalog(final YJBaseActivity activity, String courseId) {
        Intent in = new Intent(activity,YJCoursePlayNewActivity.class);
        in.putExtra("courseId",courseId);
        activity.startActivity(in);
    }
}
