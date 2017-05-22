package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.push.CloudPushService;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.component.ActivityBox;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.dao.MessageDao;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.ui.actualize.activity.YJActivateGiftCard;
import com.youjing.yjeducation.ui.actualize.activity.YJForgetPasswordActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJGuideActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMainActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJRegisterActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJWebviewActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJClearCacheDialog;
import com.youjing.yjeducation.ui.dispaly.dialog.TextDeviceDialog;
import com.youjing.yjeducation.util.CheckPhone;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.YJStringWhiteSpaceUtil;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONObject;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.base.VStringUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * user  秦伟宁
 * Date 2016/3/17
 * Time 19:31
 */
@VLayoutTag(R.layout.login)
public abstract class AYJLoginActivity extends YJBaseActivity implements IVClickDelegate {
    @VViewTag(R.id.txt_gift_card)
    private TextView mTxt_gift_card;
    @VViewTag(R.id.txt_register)
    private TextView mTxt_register;
    @VViewTag(R.id.login_forget_password)
    private TextView mLogin_forget_password;
    @VViewTag(R.id.txt_wechat_login)
    private TextView mTxt_wechat_login;
    @VViewTag(R.id.txt_qq_login)
    private TextView mTxt_qq_login;
    @VViewTag(R.id.txt_sina_login)
    private TextView mTxt_sina_login;
    @VViewTag(R.id.edt_account)
    private EditText mEdt_account;
    @VViewTag(R.id.login_edt_security_code)
    private EditText mLogin_edt_security_code;//验证码
    @VViewTag(R.id.edt_password)
    private EditText mEdt_password;
    @VViewTag(R.id.login_skip)
    private TextView mLogin_skip;//跳过登陆
    @VViewTag(R.id.txt_app_user_num)
    private TextView txt_app_user_num;
    @VViewTag(R.id.login_teacherName)
    private TextView mLogin_teacherName;//老师的姓名

    @VViewTag(R.id.btn_login)
    private TextView mBtn_login;
    @VViewTag(R.id.rl_back)
    private RelativeLayout rl_back;
    @VViewTag(R.id.re_tel)
    private RelativeLayout re_tel;
    @VViewTag(R.id.login_security_code)
    protected Button mLogin_security_code;//获取验证码
    @VViewTag(R.id.login_register)
    protected TextView mLogin_register;//使用条款隐私政策
    @VViewTag(R.id.login_hidden_password)
    protected RelativeLayout mLogin_hidden_password;//隐藏显示密码布局
    @VViewTag(R.id.login_hidden_password_image)
    protected ImageView mLogin_hidden_password_image;//隐藏显示密码图片
    boolean isClick = true;
    @VViewTag(R.id.login_videoView)
    protected VideoView mLogin_videoView;//背景动画
    @VViewTag(R.id.login_layout_security_code)
    protected LinearLayout mLogin_layout_security_code;//请输入验证码的布局
    @VViewTag(R.id.login_view_security)
    protected View mLogin_view_security;//请输入验证码的横线
    @VViewTag(R.id.login_view_password)
    protected View mLogin_view_password;//请输入密码的横线
    @VViewTag(R.id.login_layout_password)
    protected LinearLayout mLogin_layout_password;//请输入密码的布局
    private Uri mVideoUri;

    public int mloginOut = -1;
    public static final VParamKey<Integer> LOGIN_OUT = new VParamKey<Integer>(null);
    public static final VParamKey<Boolean> MY_LOGIN_OUT = new VParamKey<Boolean>(null);
    public static final VParamKey<String> REGIST_INFO = new VParamKey<String>(null);
    protected String mRegistInfo = "";
    protected boolean mIsLogin = false;

    private boolean myLoginOut = false;
    protected int mResidueSecond = 120;
    protected int loginIndex = 0;//默认是0，如果是1那就走登陆，如果是2那就走注册
    protected String mGrade;
    protected String mSubject;
    public static final String VIDEO_NAME = "welcome_video.mp4";
    protected String teacherName[] = {"杨锐", "梁志强", "王磊", "钱多多","梁国臣"};
    private String TAG = "AYJLoginActivity";
    protected Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mResidueSecond > 1) {
                mResidueSecond--;
                mLogin_security_code.setTextColor(getResources().getColor(R.color.black));
                mLogin_security_code.setText(mResidueSecond + getString(R.string.second) + "重新获取");
                mLogin_security_code.setEnabled(false);
                postRunnable(this, 1000);
            } else if (mResidueSecond == 1) {
                onRecovery();
            }
        }
    };

    @Override
    protected void onLoadedView() {
        playVideo();
        mloginOut = getTransmitData(LOGIN_OUT);
        mRegistInfo = getTransmitData(REGIST_INFO);
        myLoginOut = getTransmitData(MY_LOGIN_OUT);
        mLogin_skip.setVisibility(mloginOut == 3 ? View.VISIBLE : View.GONE);
        findViewById(R.id.rl_back).setVisibility(mloginOut == 3 ? View.INVISIBLE : View.VISIBLE);

        mEdt_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (CheckPhone.isMobileNum(charSequence.toString())) {
                    getCheckMobile(charSequence.toString());
                } else {
                    mLogin_layout_password.setVisibility(View.GONE);
                    mLogin_view_password.setVisibility(View.GONE);
                    mLogin_layout_security_code.setVisibility(View.GONE);
                    mLogin_edt_security_code.setText("");
                    mEdt_password.setText("");
                    mLogin_view_security.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        if (myLoginOut) {
            showToast("用户已在其他设备登录");
            logOut();
        }

        SpannableStringBuilder sbuilder = new SpannableStringBuilder(mLogin_register.getText().toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan buleSpans = new ForegroundColorSpan(Color.parseColor("#0caafe") + 1);
        sbuilder.setSpan(buleSpans, 7, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mLogin_register.setText(sbuilder);


        //随机
        Random random = new Random();
        final int index = random.nextInt(teacherName.length);
        mLogin_teacherName.setText(teacherName[index] + "老师的课程正在直播");

        getRegCount();

        //此方法是监控输入
        mEdt_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (loginIndex == 1){
                    if (charSequence.length() > 0  && !TextUtils.isEmpty(mEdt_password.getText())) {
                        mBtn_login.setTextColor(getResources().getColor(R.color.white));
                        mBtn_login.setBackgroundResource(R.mipmap.login_button_blue);

                    } else {
                        mBtn_login.setTextColor(getResources().getColor(R.color.gray_login));
                        mBtn_login.setBackgroundResource(R.mipmap.login_button);
                    }
                }else if (loginIndex == 2){
                    if (charSequence.length() > 0  && !TextUtils.isEmpty(mEdt_password.getText()) && !TextUtils.isEmpty(mLogin_edt_security_code.getText())) {
                        mBtn_login.setTextColor(getResources().getColor(R.color.white));
                        mBtn_login.setBackgroundResource(R.mipmap.login_button_blue);

                    } else {
                        mBtn_login.setTextColor(getResources().getColor(R.color.gray_login));
                        mBtn_login.setBackgroundResource(R.mipmap.login_button);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

            mLogin_edt_security_code.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (loginIndex  == 2){
                        if (charSequence.length() > 0 && !TextUtils.isEmpty(mEdt_account.getText()) && !TextUtils.isEmpty(mEdt_password.getText())) {
                            mBtn_login.setTextColor(getResources().getColor(R.color.white));
                            mBtn_login.setBackgroundResource(R.mipmap.login_button_blue);
                        } else {
                            mBtn_login.setTextColor(getResources().getColor(R.color.gray_login));
                            mBtn_login.setBackgroundResource(R.mipmap.login_button);
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        mEdt_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (loginIndex == 1){
                    if (charSequence.length() > 0  && !TextUtils.isEmpty(mEdt_account.getText())) {
                        mBtn_login.setTextColor(getResources().getColor(R.color.white));
                        mBtn_login.setBackgroundResource(R.mipmap.login_button_blue);

                    } else {
                        mBtn_login.setTextColor(getResources().getColor(R.color.gray_login));
                        mBtn_login.setBackgroundResource(R.mipmap.login_button);
                    }
                }else if (loginIndex == 2){
                    if (charSequence.length() > 0  && !TextUtils.isEmpty(mEdt_account.getText()) && !TextUtils.isEmpty(mLogin_edt_security_code.getText())) {
                        mBtn_login.setTextColor(getResources().getColor(R.color.white));
                        mBtn_login.setBackgroundResource(R.mipmap.login_button_blue);

                    } else {
                        mBtn_login.setTextColor(getResources().getColor(R.color.gray_login));
                        mBtn_login.setBackgroundResource(R.mipmap.login_button);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void playVideo() {
        mVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome_video);
        mLogin_videoView.setVideoURI(mVideoUri);
        mLogin_videoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mLogin_videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });
    }

    /**
     * 验证手机号是否被注册过
     *
     * @param phone
     */
    protected void getCheckMobile(final String phone) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("mobile", phone);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_CHECKMOBILE, objectMap, false, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200://成功 (手机号已注册)
                            loginIndex = 1;
                            mLogin_layout_password.setVisibility(View.VISIBLE);
                            mLogin_view_password.setVisibility(View.VISIBLE);
                            mLogin_layout_security_code.setVisibility(View.GONE);
                            mLogin_view_security.setVisibility(View.GONE);
                            mLogin_edt_security_code.setText("");
                            break;
                        case 400://用户不存在 (手机号未注册)
                            loginIndex = 2;
                            mLogin_layout_password.setVisibility(View.VISIBLE);
                            mLogin_view_password.setVisibility(View.VISIBLE);
                            mLogin_layout_security_code.setVisibility(View.VISIBLE);
                            mLogin_view_security.setVisibility(View.VISIBLE);
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

    /**
     * 获得注册用户数
     */
    protected void getRegCount() {
        Map<String, Object> objectMap = new HashMap<>();
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_REGCOUNT, objectMap, false, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200://成功
                            JSONObject jsonObject = new JSONObject(json.getString("data"));
                            String regCount = jsonObject.getString("regCount");
                            SpannableStringBuilder builder = new SpannableStringBuilder("已有" + regCount + "位加入游鲸大家庭");
                            ForegroundColorSpan buleSpan = new ForegroundColorSpan(Color.parseColor("#0caafe"));
                            builder.setSpan(buleSpan, 2, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            txt_app_user_num.setText(builder);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }


    @Override
    public void onClick(View view) {
        if (view == mTxt_register) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(createIntent(YJRegisterActivity.class, createTransmitData(YJRegisterActivity.LOGIN_FLAG, false).set(YJRegisterActivity.REGIST_INFO, mRegistInfo).set(YJRegisterActivity.POSITION, mloginOut)));
        } else if (view == mTxt_gift_card) {
            startActivity(YJActivateGiftCard.class);
        } else if (view == mBtn_login) {
            String phone = mEdt_account.getText().toString().trim();
            String password = mEdt_password.getText().toString().trim();
            String security_code = mLogin_edt_security_code.getText().toString().trim();
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            if (VStringUtil.isNullOrEmpty(phone)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_phone_number), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!CheckPhone.isMobileNum(phone)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_phone), Toast.LENGTH_SHORT).show();
                return;
            }
            if (loginIndex == 2 && VStringUtil.isNullOrEmpty(security_code)) {
                CustomToast.makeText(getContext(), getString(R.string.update_password_inputCode), Toast.LENGTH_SHORT).show();
                return;
            }

            if (VStringUtil.isNullOrEmpty(password)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_login_password), Toast.LENGTH_SHORT).show();
                return;
            }
            char[] cTemp = password.toCharArray();
            char endChar = cTemp[cTemp.length - 1];
            if (YJStringWhiteSpaceUtil.checkIsWhiteSpace(endChar)) {
                CustomToast.makeText(getContext(), getString(R.string.n_password_have_space_input_again), Toast.LENGTH_SHORT).show();
                return;
            }
            if (loginIndex == 1) {
                login(phone, password);
            } else if (loginIndex == 2) {
                register(phone, password, security_code);
            }

        } else if (view == mLogin_forget_password) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJForgetPasswordActivity.class);
        } else if (view == mTxt_wechat_login) {

        } else if (view == mTxt_sina_login) {

        } else if (view == mTxt_qq_login) {

        } else if (view == re_tel) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01053733201"));
            startActivity(intent);
        } else if (view == rl_back) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            if (mloginOut == 1) {
                startActivity(YJMainActivity.class);
                finish();
            } else if (mloginOut == 2) {
                finishAll();
            } else if (mloginOut == 3) {
               // getBannerNoLogin(false);
               startActivity(YJMainActivity.class);
                finish();

            } else {
                finish();
            }
        } else if (view == mLogin_skip) {
           // getBannerNoLogin(false);
            startActivity(YJMainActivity.class);
            finish();
        } else if (view == mLogin_register) {
            Intent in = new Intent(AYJLoginActivity.this, YJWebviewActivity.class);
            in.putExtra("userURL", YJReqURLProtocol.USER_AGREEMENT);
            startActivity(in);
        } else if (view == mLogin_security_code) {
            String phone = mEdt_account.getText().toString().trim();
            if (!CheckPhone.isMobileNum(phone)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_phone), Toast.LENGTH_SHORT).show();
                return;
            } else {
                getVerification(phone);
            }
        } else if (view == mLogin_hidden_password) {
            if (isClick) {
                //设置EditText的密码为可见的
                mEdt_password.setTransformationMethod(HideReturnsTransformationMethod
                        .getInstance());
                mLogin_hidden_password_image.setImageResource(R.mipmap.login_accord);
                isClick = false;
            } else {
                //设置密码为隐藏的
                mEdt_password.setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
                mLogin_hidden_password_image.setImageResource(R.mipmap.login_hidden);
                isClick = true;
            }
        }
    }

    @Override
    protected boolean onBackKeyClick() {
        if (mloginOut == 1) {
            startActivity(YJMainActivity.class);
            finish();
            return true;
        } else if (mloginOut == 2) {
            finishAll();
            return true;
        } else if (mloginOut == 3) {
            startActivity(YJMainActivity.class);
            finish();
           // getBannerNoLogin(false);
            return true;
        } else {
            finish();
        }
        return super.onBackKeyClick();
    }


    protected abstract void login(String phone, String password);

  //  protected abstract void getBannerNoLogin(boolean flag);

    protected abstract void getVerification(String phone);

    protected abstract void onRecovery();

    protected abstract void register(String phone, String mpassword, String mScurity);

    private void logOut() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", false);
        // editor.putString("phone", "");
        // editor.putString("password", "");

        editor.commit();
    }
}
