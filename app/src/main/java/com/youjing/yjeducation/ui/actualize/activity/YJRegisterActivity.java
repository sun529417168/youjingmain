package com.youjing.yjeducation.ui.actualize.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.security.mobile.module.commonutils.LOG;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.umeng.analytics.AnalyticsConfig;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.model.YJBannerModel;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.model.YJIndexUserInfo;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.dispaly.activity.AYJActivateGiftCard;
import com.youjing.yjeducation.ui.dispaly.activity.AYJRegisterActivity;
import com.youjing.yjeducation.util.ChineseStringUtil;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.FileUtils;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.util.NetUtils;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.utils.base.VStringUtil;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * user  秦伟宁
 * Date 2016/3/17
 * Time 19:54
 */
public class YJRegisterActivity extends AYJRegisterActivity {
    private String TAG = "YJRegisterActivity";
    String mGrade;
    String mSubject;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    notifyListener(YJNotifyTag.MY_TASK, "");
                    StringUtils.Log(TAG, "MY_TASK");
                    break;
                }
                case 1: {
                    notifyListener(YJNotifyTag.MY_CARD, "");
                    StringUtils.Log(TAG, "MY_CARD");
                    break;
                }
                default:
                    break;
            }
        }
    };
    @Override
    protected void register(final String phone, final String mpassword, String mScurity) {

        if ((mpassword.getBytes().length != mpassword.length())) {
            showToast(getString(R.string.password_is_chinese));
            return;
        }
        if (VStringUtil.isNullOrEmpty(mpassword)) {
            showToast(getString(R.string.place_input_password));
            return;
        }
        if (mpassword.length() < 6) {
            showToast(getString(R.string.password_greater_than_six));
            return;
        }
        if (mpassword.length() > 16) {
            showToast(getString(R.string.password_greater_than_sixteen));
            return;
        }
        if (YJGlobal.getGradeList() != null){
            StringUtils.Log(TAG, "YJGlobal.getGradeList() != null");
            mGrade = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("first_grade", YJGlobal.getGradeList().get(0).getGradeName());
            mSubject = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("first_subject", YJGlobal.getGradeList().get(0).getSubjectVos().get(0).getSubjectName());
        }else {
            mGrade = "一年级";
            mSubject = "数学";
        }
        StringUtils.Log(TAG, "mGrade=" + mGrade + ":::::mSubject=" + mSubject);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("mobile", phone);
            objectMap.put("gradeId", mGrade);
            objectMap.put("subjectId", mSubject);
            objectMap.put("password", DES.encryptDES(mpassword));
            objectMap.put("code", mScurity);
            objectMap.put("regUseAppChannel", AnalyticsConfig.getChannel(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.REGISTER, objectMap, false, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                CustomToast.makeText(getContext(), getString(R.string.no_net_work), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            if (SharedUtil.getInteger(getContext(), "baseIndex", 0)!=-1){
                                SharedUtil.setInteger(getContext(), "baseIndex", SharedUtil.getInteger(getContext(), "baseIndex", 0) + 1);
                            }
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            String customerToken = jsonData.getString("customerToken");
                            YJGlobal.setCustomertoken(customerToken);
                            int customerId = jsonData.getInt("customerId");
                            YJGlobal.setCustomerId(customerId);
                            rememberToken(customerToken, customerId + "");
                            YJUserStudyData.setDevice(getContext());
                            getBanner();
                            break;
                        case 300:
                            CustomToast.makeText(getContext(), "注册失败", Toast.LENGTH_SHORT).show();
                            break;
                        case 301:
                            CustomToast.makeText(getContext(), "验证码不合法", Toast.LENGTH_SHORT).show();
                            break;
                        case 302:
                            CustomToast.makeText(getContext(), "注册失败", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
                            CustomToast.makeText(getContext(), "用户已存在", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            CustomToast.makeText(getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
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


    private void getUserInfo() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                CustomToast.makeText(getContext(), getString(R.string.no_net_work), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    JSONObject jsonData = new JSONObject(json.getString("data"));
                    YJUser yjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                    StringUtils.Log(TAG, "yjUser.toString()=" + yjUser.toString());
                    YJGlobal.setYjUser(yjUser);
                    getUserIndexInfo();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getGrade() {

        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_GRADE, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                CustomToast.makeText(getContext(), getString(R.string.no_net_work), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    JSONObject jsonData = new JSONObject(json.getString("data"));
                    List<YJGradeModel> yjGradeModelList = JSON.parseArray(jsonData.getString("grades"), YJGradeModel.class);
                    StringUtils.Log(TAG, "yjGradeListModel.toString()=" + yjGradeModelList.toString());
                    YJGlobal.setGradeList(yjGradeModelList);
                    notifyListener(YJNotifyTag.USER_LOGIN, true);
                     CustomToast.makeTexts(getContext(),"注册成功",0).show();
                    if (loginFlag) {
                        finish();
                    } else {
                      /*  startActivity(createIntent(YJActivateGiftCardSuccess.class, createTransmitData(YJActivateGiftCardSuccess.REGIST_INFO, mRegistInfo)));
                        finishAll();*/

                        if (!TextUtils.isEmpty(mRegistInfo)) {
                            finishTo(YJMainActivity.class);
                            notifyListener(YJNotifyTag.MAIN_MY, 3);

                            final Timer timer = new Timer();
                            timer.schedule(new TimerTask() {
                                public void run() {
                                    if(mRegistInfo.equals("注册大礼包")){
                                        mHandler.sendEmptyMessage(0);
                                    }else if (mRegistInfo.equals("2")){//2，任务中心
                                        mHandler.sendEmptyMessage(0);
                                    }else if (mRegistInfo.equals("1")){//1，我的卡包
                                        mHandler.sendEmptyMessage(1);
                                    }
                                    timer.cancel();
                                }
                            }, 300);
                        } else {
                            StringUtils.Log(TAG,"mRegistInfo == '' ");
                            if (position == -1){
                                StringUtils.Log(TAG, "position = -1");
                                finishTo(YJMainActivity.class);
                                notifyListener(YJNotifyTag.MAIN_MY, 3);
                            }else {
                                StringUtils.Log(TAG,"position = "+position);
                                startActivity(createIntent(YJMainActivity.class, createTransmitData(YJMainActivity.MAIN_POSITION, "0")));
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getUserIndexInfo() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INDEX, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                CustomToast.makeText(getContext(), getString(R.string.no_net_work), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    JSONObject jsonData = new JSONObject(json.getString("data"));
                    YJIndexUserInfo yjIndexUserInfo = JSON.parseObject(jsonData.getString("customer"), YJIndexUserInfo.class);
                    StringUtils.Log(TAG, "yjIndexUserInfo.toString()=" + yjIndexUserInfo.toString());
                    YJGlobal.setYjIndexUserInfo(yjIndexUserInfo);
                    getGrade();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void rememberToken(String customerToken, String customerId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", true);
        editor.putString("customerToken", customerToken);
        editor.putString("customerId", customerId);

        editor.commit();
    }


    private void getBanner() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.BANER_PICTURE, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                CustomToast.makeText(getContext(), getString(R.string.no_net_work), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            List<YJBannerModel> yjBannerModelList = JSON.parseArray(jsonData.getString("bannerList"), YJBannerModel.class);
                            YJGlobal.setYjBannerModelList(yjBannerModelList);
                            getUserInfo();
                            break;
                        case 500:
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
