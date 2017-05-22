package com.youjing.yjeducation.ui.actualize.activity;

import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCourseListModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCourseListActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJFeedBackActivity;
import com.youjing.yjeducation.util.CheckMail;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.StringUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/5
 * Time 14:40
 */
public class YJFeedBackActivity extends AYJFeedBackActivity {

    private YJUser mYjUser;

    private String TAG = "YJFeedBackActivity";

    @Override
    public void sendBack(String content, String email) {
        mYjUser= YJGlobal.getYjUser();
        if(TextUtils.isEmpty(content)){
            showToast("反馈信息不能为空");
            return;
        }else if(content.length()>2000){
            showToast("反馈信息不能超过2000字");
            return;
        }else if(TextUtils.isEmpty(email)){
            showToast("邮箱信息不能为空");
            return;
        }else if(!CheckMail.isEmail(email)){
            showToast("请输入正确的邮箱");
            return;
        }

        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("feedbackContent", content);
            objectMap.put("customerEmail", email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.MESSAGE_BACK, objectMap, true, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                        StringUtils.Log(TAG, "失败=" + s);
                        showToast(getString(R.string.no_net_work));
                    }

                    @Override
                    public void onSuccess(int i, Header[] headers, String s) {
                        StringUtils.Log(TAG, "成功=" + s);
                        try {
                            JSONObject json = null;
                            json = new JSONObject(s);
                            switch (json.getInt("code")) {
                                case 200:
                                    String code = json.getString("code");
                                    if (code.equals("200")) {
                                       showToast("提交反馈成功");
                                        finish();
                                    } else if (code.equals("300")) {
                                        StringUtils.tip(getApplicationContext(), getString(R.string.code_300));
                                    } else if (code.equals("500")) {
                                        StringUtils.tip(getApplicationContext(), getString(R.string.code_500));
                                    }
                                    break;
                                case 300:
                                    break;
                                case 400:
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
