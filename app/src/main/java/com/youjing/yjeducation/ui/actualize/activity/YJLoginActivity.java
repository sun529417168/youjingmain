package com.youjing.yjeducation.ui.actualize.activity;

import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
import com.youjing.yjeducation.ui.dispaly.activity.AYJLoginActivity;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.FileUtils;
import com.youjing.yjeducation.util.MakeSign;
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
 * Time 19:33
 */
public class YJLoginActivity extends AYJLoginActivity {
    private String TAG = "YJLoginActivity";
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
    protected void login(final String phone, final String password) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("mobile", phone);
            objectMap.put("password", DES.encryptDES(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.LOGIN, objectMap, false, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                CustomToast.makeText(getContext(), "登录失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            if (SharedUtil.getInteger(getContext(), "baseIndex", 0) != -1) {
                                SharedUtil.setInteger(getContext(), "baseIndex", SharedUtil.getInteger(getContext(), "baseIndex", 0) + 1);
                            }
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            String customerToken = jsonData.getString("customerToken");
                            YJGlobal.setCustomertoken(customerToken);
                            int customerId = jsonData.getInt("customerId");
                            YJGlobal.setCustomerId(customerId);
                            rememberToken(customerToken, customerId + "");
                            YJUserStudyData.setDevice(getContext());

                            loginOrRegistSucess();

                            break;
                        case 300:
                            break;
                        case 400:
                            CustomToast.makeText(getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
                            CustomToast.makeText(getContext(), "用户被锁定", Toast.LENGTH_SHORT).show();
                            break;
                        case 402:
                            CustomToast.makeText(getContext(), "密码不正确", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
                            CustomToast.makeText(getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
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
    protected void getVerification(final String phone) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("mobile", phone);
            objectMap.put("type", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_VERIFICATION_CODE, objectMap, false, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                CustomToast.makeText(getContext(), "发送失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            getCode(phone);
                            showToast("验证码已发送");


                            break;
                        case 300:
                            //  CustomToast.makeText(getContext(), "参数不合法", Toast.LENGTH_SHORT).show();
                            break;
                        case 301:
                            break;
                        case 302:

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

    public void getCode(String phone) {

        mLogin_security_code.setEnabled(false);
        mLogin_security_code.setBackgroundResource(R.mipmap.security_code_bg);
        mLogin_security_code.setTextColor(getResources().getColor(R.color.gray_text));
        mLogin_security_code.setText(mResidueSecond + getString(R.string.second) + "重新获取");
        postRunnable(mRunnable, 1000);

    }

    public void onRecovery() {
        mLogin_security_code.setText(getString(R.string.get_code_again));

        mLogin_security_code.setBackgroundResource(R.mipmap.security_code_bg_select);
        mLogin_security_code.setTextColor(getResources().getColor(R.color.white));
        mResidueSecond = 120;
        mLogin_security_code.setEnabled(true);
    }


    private void loginOrRegistSucess() {
        notifyListener(YJNotifyTag.USER_LOGIN, true);

        if (loginIndex == 1) {
            CustomToast.makeTexts(getContext(), "登录成功", 0).show();
            if (TextUtils.isEmpty(mRegistInfo)) {
                if (mloginOut == -1) {
                    StringUtils.Log(TAG, "mloginOut == -1");
                    notifyListener(YJNotifyTag.MAIN_MY, 3);
                } else {
                    StringUtils.Log(TAG, "mloginOut == " + mloginOut);
                    startActivity(YJMainActivity.class);
                }
                finish();
            } else {
                notifyListener(YJNotifyTag.MAIN_MY, 3);
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        if (mRegistInfo.equals("注册大礼包")) {
                            mHandler.sendEmptyMessage(0);
                        } else if (mRegistInfo.equals("2")) {//2，任务中心
                            mHandler.sendEmptyMessage(0);
                        } else if (mRegistInfo.equals("1")) {//1，我的卡包
                            mHandler.sendEmptyMessage(1);
                        }

                        timer.cancel();
                    }
                }, 300);
                finish();
            }
        } else if (loginIndex == 2) {
            CustomToast.makeTexts(getContext(), "注册成功", 0).show();
            if (!TextUtils.isEmpty(mRegistInfo)) {
                finishTo(YJMainActivity.class);
                notifyListener(YJNotifyTag.MAIN_MY, 3);

                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        if (mRegistInfo.equals("注册大礼包")) {
                            mHandler.sendEmptyMessage(0);
                        } else if (mRegistInfo.equals("2")) {//2，任务中心
                            mHandler.sendEmptyMessage(0);
                        } else if (mRegistInfo.equals("1")) {//1，我的卡包
                            mHandler.sendEmptyMessage(1);
                        }
                        timer.cancel();
                    }
                }, 300);
            } else {
                StringUtils.Log(TAG, "mRegistInfo == '' ");
                if (mloginOut == -1) {
                    StringUtils.Log(TAG, "position = -1");
                    finishTo(YJMainActivity.class);
                    notifyListener(YJNotifyTag.MAIN_MY, 3);
                } else {
                    startActivity(createIntent(YJMainActivity.class, createTransmitData(YJMainActivity.MAIN_POSITION, "0")));
                }
            }
        }

    }

    private void rememberToken(String customerToken, String customerId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", true);
        editor.putString("customerToken", customerToken);
        editor.putString("customerId", customerId);

        editor.commit();
    }



    //走注册流程
    @Override
    protected void register(String phone, String mpassword, String mScurity) {
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
        if (YJGlobal.getGradeList() != null) {
            StringUtils.Log(TAG, "YJGlobal.getGradeList() != null");
            mGrade = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("first_grade", YJGlobal.getGradeList().get(0).getGradeName());
            mSubject = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("first_subject", YJGlobal.getGradeList().get(0).getSubjectVos().get(0).getSubjectName());
        } else {
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
                            if (SharedUtil.getInteger(getContext(), "baseIndex", 0) != -1) {
                                SharedUtil.setInteger(getContext(), "baseIndex", SharedUtil.getInteger(getContext(), "baseIndex", 0) + 1);
                            }
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            String customerToken = jsonData.getString("customerToken");
                            YJGlobal.setCustomertoken(customerToken);
                            int customerId = jsonData.getInt("customerId");
                            YJGlobal.setCustomerId(customerId);
                            rememberToken(customerToken, customerId + "");
                            YJUserStudyData.setDevice(getContext());

                            loginOrRegistSucess();
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
}
