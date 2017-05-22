package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.loopj.android.http.TextHttpResponseHandler;
import com.umeng.analytics.AnalyticsConfig;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.ui.actualize.activity.YJWebviewActivity;
import com.youjing.yjeducation.util.CheckPhone;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VStringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author stt
 *         类说明：修改密码
 *         创建时间：2016.7.12
 */
@VLayoutTag(R.layout.activity_update_password)
public class AYJUpdatePassword extends YJBaseActivity implements IVClickDelegate {
    private String TAG = "AYJUpdatePassword";
    @VViewTag(R.id.activity_update_password_imputPhone)
    private EditText mActivity_update_password_imputPhone;//输入手机号
    @VViewTag(R.id.activity_update_password_inputCode)
    private EditText mActivity_update_password_inputCode;//输入验证码
    @VViewTag(R.id.activity_update_password_btnCode)
    private Button mActivity_update_password_btnCode;//获取验证码
    @VViewTag(R.id.activity_update_password_inputNewPassword)
    private EditText mActivity_update_password_inputNewPassword;//输入新密码
    @VViewTag(R.id.activity_update_password_sure)
    private TextView mActivity_update_password_sure;//确认修改密码
    @VViewTag(R.id.activity_update_password_back_layout)
    private RelativeLayout mActivity_update_password_back_layout;//返回按钮
    @VViewTag(R.id.update_hidden_password)
    protected RelativeLayout mUpdate_hidden_password;//隐藏显示密码布局
    @VViewTag(R.id.update_hidden_password_image)
    protected ImageView mUpdate_hidden_password_image;//隐藏显示密码图片
    @VViewTag(R.id.update_password_videoView)
    protected VideoView mUpdate_password_videoView;//背景动画
    private int mResidueSecond = 120;
    boolean isClick = true;
    private String mStrVerification;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mResidueSecond > 1) {
                mResidueSecond--;
                mActivity_update_password_btnCode.setTextColor(getResources().getColor(R.color.black));
                mActivity_update_password_btnCode.setText(mResidueSecond + getString(R.string.second) + "重新获取");
                mActivity_update_password_btnCode.setEnabled(false);
                postRunnable(this, 1000);
            } else if (mResidueSecond == 1) {
                onRecovery();
            }
        }
    };

    @Override
    protected void onLoadedView() {
        playVideo();
        //此方法是监控输入
        mActivity_update_password_imputPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && !TextUtils.isEmpty(mActivity_update_password_inputNewPassword.getText()) && !TextUtils.isEmpty(mActivity_update_password_inputCode.getText())) {
                    mActivity_update_password_sure.setTextColor(getResources().getColor(R.color.white));
                    mActivity_update_password_sure.setBackgroundResource(R.mipmap.login_button_blue);
                } else {
                    mActivity_update_password_sure.setTextColor(getResources().getColor(R.color.gray_login));
                    mActivity_update_password_sure.setBackgroundResource(R.mipmap.login_button);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mActivity_update_password_inputNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && !TextUtils.isEmpty(mActivity_update_password_imputPhone.getText()) && !TextUtils.isEmpty(mActivity_update_password_inputCode.getText())) {
                    mActivity_update_password_sure.setTextColor(getResources().getColor(R.color.white));
                    mActivity_update_password_sure.setBackgroundResource(R.mipmap.login_button_blue);
                } else {
                    mActivity_update_password_sure.setTextColor(getResources().getColor(R.color.gray_login));
                    mActivity_update_password_sure.setBackgroundResource(R.mipmap.login_button);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mActivity_update_password_inputCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && !TextUtils.isEmpty(mActivity_update_password_imputPhone.getText()) && !TextUtils.isEmpty(mActivity_update_password_inputNewPassword.getText())) {
                    mActivity_update_password_sure.setTextColor(getResources().getColor(R.color.white));
                    mActivity_update_password_sure.setBackgroundResource(R.mipmap.login_button_blue);
                } else {
                    mActivity_update_password_sure.setTextColor(getResources().getColor(R.color.gray_login));
                    mActivity_update_password_sure.setBackgroundResource(R.mipmap.login_button);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void playVideo() {
        Uri mVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome_video);
        mUpdate_password_videoView.setVideoURI(mVideoUri);
        mUpdate_password_videoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mUpdate_password_videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == mActivity_update_password_btnCode) {
            String phone = mActivity_update_password_imputPhone.getText().toString().trim();
            if (!CheckPhone.isMobileNum(phone)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_phone), Toast.LENGTH_SHORT).show();
                return;
            } else {
                getVerification(phone);
            }
        } else if (view == mActivity_update_password_sure) {

            //startActivity(YJActivateGiftCard.class);

            String phone = mActivity_update_password_imputPhone.getText().toString().trim();
            String mpassword = mActivity_update_password_inputNewPassword.getText().toString().trim();
            String mScurity = mActivity_update_password_inputCode.getText().toString().trim();
            if (TextUtils.isEmpty(phone)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_phone_number), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CheckPhone.isMobileNum(phone)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_phone), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mScurity)) {
                CustomToast.makeText(getContext(), getString(R.string.update_password_inputCode), Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(mpassword)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_login_password), Toast.LENGTH_SHORT).show();
                return;
            }
            register(phone, mpassword, mScurity);
        } else if (view == mActivity_update_password_back_layout) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            finish();
        } else if (view == mUpdate_hidden_password) {
            if (isClick) {
                //设置EditText的密码为可见的
                mActivity_update_password_inputNewPassword.setTransformationMethod(HideReturnsTransformationMethod
                        .getInstance());
                mUpdate_hidden_password_image.setImageResource(R.mipmap.login_accord);
                isClick = false;
            } else {
                //设置密码为隐藏的
                mActivity_update_password_inputNewPassword.setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
                mUpdate_hidden_password_image.setImageResource(R.mipmap.login_hidden);
                isClick = true;
            }
        }
    }

    public void getCode(String phone) {

        mActivity_update_password_btnCode.setEnabled(false);
        mActivity_update_password_btnCode.setBackgroundResource(R.mipmap.security_code_bg);
        mActivity_update_password_btnCode.setTextColor(getResources().getColor(R.color.gray_text));
        mActivity_update_password_btnCode.setText(mResidueSecond + getString(R.string.second) + "重新获取");
        postRunnable(mRunnable, 1000);

    }

    public void onRecovery() {
        mActivity_update_password_btnCode.setText(getString(R.string.get_code_again));

        mActivity_update_password_btnCode.setBackgroundResource(R.mipmap.security_code_bg_select);
        mActivity_update_password_btnCode.setTextColor(getResources().getColor(R.color.white));
        mResidueSecond = 120;
        mActivity_update_password_btnCode.setEnabled(true);
    }

    protected void getVerification(final String phone) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("mobile", phone);
            objectMap.put("type", "2");
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

    protected void register(final String phone, final String mpassword, String mScurity) {

        if ((mpassword.getBytes().length != mpassword.length())) {
            showToast(getString(R.string.password_is_chinese));
            return;
        }
        if (VStringUtil.isNullOrEmpty(mpassword)) {
            showToast(getString(R.string.update_password_inputNewPassword));
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
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("mobile", phone);
            objectMap.put("password", DES.encryptDES(mpassword));
            objectMap.put("code", mScurity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.UPDATEPASSWORD, objectMap, false, new TextHttpResponseHandler() {
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
                            showToast("修改成功");
                            AYJUpdatePassword.this.finish();
                            break;
                        case 300:
                            CustomToast.makeText(getContext(), "参数不合法", Toast.LENGTH_SHORT).show();
                            break;
                        case 301:
                            CustomToast.makeText(getContext(), "验证码不合法", Toast.LENGTH_SHORT).show();
                            break;
                        case 400:
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
