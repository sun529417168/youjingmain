package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import com.youjing.yjeducation.util.StringUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.ui.actualize.activity.YJWebviewActivity;
import com.youjing.yjeducation.util.CheckPhone;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/17
 * Time 19:41
 */
@VLayoutTag(R.layout.register)
public abstract class AYJRegisterActivity extends BaseSwipeBackActivity implements IVClickDelegate {
    @VViewTag(R.id.edt_account)
    private EditText mEdt_account;
    @VViewTag(R.id.edt_password)
    private EditText mEdt_password;
    @VViewTag(R.id.edt_security_code)
    private EditText mEdt_security_code;
    @VViewTag(R.id.bt_get_security_code)
    private Button mBt_get_security_code;
    @VViewTag(R.id.btn_regist_next)
    private TextView mBtn_regist_next;
    @VViewTag(R.id.txt_wechat_login)
    private TextView mTxt_wechat_login;
    @VViewTag(R.id.txt_qq_login)
    private TextView mTxt_qq_login;
    @VViewTag(R.id.txt_sina_login)
    private TextView mTxt_sina_login;
    @VViewTag(R.id.tv_register)
    private TextView mTv_register;
    @VViewTag(R.id.tv_phone_num)
    private TextView mTv_phone_num;
    @VViewTag(R.id.re_tel)
    private RelativeLayout re_tel;
    @VViewTag(R.id.register_rl_back)
    private RelativeLayout mRegister_rl_back;
    @VViewTag(R.id.register_videoView)
    protected VideoView mRegister_videoView;//背景动画
    @VViewTag(R.id.register_hidden_password)
    protected RelativeLayout mRegister_hidden_password;//隐藏显示密码布局
    @VViewTag(R.id.register_hidden_password_image)
    protected ImageView mRegister_hidden_password_image;//隐藏显示密码图片

    boolean isClick = true;
    private int mResidueSecond = 120;
    private String mStrVerification;
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mResidueSecond > 1) {
                mResidueSecond--;
                mBt_get_security_code.setTextColor(getResources().getColor(R.color.black));
                mBt_get_security_code.setText(mResidueSecond + getString(R.string.second) + "重新获取");
                mBt_get_security_code.setEnabled(false);
                postRunnable(this, 1000);
            } else if (mResidueSecond == 1) {
                onRecovery();
            }
        }
    };
    public static final VParamKey<Boolean> LOGIN_FLAG = new VParamKey<Boolean>(false);
    public static final VParamKey<String> REGIST_INFO = new VParamKey<String>(null);

    //判断是否是从首页的登录页面进入注册       POSITION= 3   是；  POSITION= -1   不是
    public static final VParamKey<Integer> POSITION = new VParamKey<Integer>(null);
    private String TAG = "YJRegisterActivity";
    public static final String VIDEO_NAME = "welcome_video.mp4";
    //判断是否是从登录弹框进入的，正常注册时false，登录弹框注册时true;
    protected boolean loginFlag = false;
    protected String mRegistInfo = "";
    protected int position = -1;

    @Override
    protected void onLoadedView() {
        playVideo();
        loginFlag = getTransmitData(LOGIN_FLAG);
        mRegistInfo = getTransmitData(REGIST_INFO);
        position = getTransmitData(POSITION);
        //此方法是监控输入
        mEdt_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0  && !TextUtils.isEmpty(mEdt_password.getText()) && !TextUtils.isEmpty(mEdt_security_code.getText())) {
                    mBtn_regist_next.setTextColor(getResources().getColor(R.color.white));
                    mBtn_regist_next.setBackgroundResource(R.mipmap.login_button_blue);

                } else {
                    mBtn_regist_next.setTextColor(getResources().getColor(R.color.gray_login));
                    mBtn_regist_next.setBackgroundResource(R.mipmap.login_button);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        mEdt_security_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() > 0 && !TextUtils.isEmpty(mEdt_account.getText()) && !TextUtils.isEmpty(mEdt_password.getText())) {
                    mBtn_regist_next.setTextColor(getResources().getColor(R.color.white));
                    mBtn_regist_next.setBackgroundResource(R.mipmap.login_button_blue);
                } else {
                    mBtn_regist_next.setTextColor(getResources().getColor(R.color.gray_login));
                    mBtn_regist_next.setBackgroundResource(R.mipmap.login_button);
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

                if (charSequence.length() > 0 && !TextUtils.isEmpty(mEdt_account.getText()) && !TextUtils.isEmpty(mEdt_security_code.getText())) {
                    mBtn_regist_next.setTextColor(getResources().getColor(R.color.white));
                    mBtn_regist_next.setBackgroundResource(R.mipmap.login_button_blue);

                } else {
                    mBtn_regist_next.setTextColor(getResources().getColor(R.color.gray_login));
                    mBtn_regist_next.setBackgroundResource(R.mipmap.login_button);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        SpannableStringBuilder builder = new SpannableStringBuilder(mTv_register.getText().toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        ForegroundColorSpan buleSpan = new ForegroundColorSpan(Color.parseColor("#0caafe"));
        ForegroundColorSpan buleSpans = new ForegroundColorSpan(Color.parseColor("#0caafe") + 1);


//        builder.setSpan(buleSpan, 7, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        builder.setSpan(buleSpans, 14, 20, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(buleSpans, 7, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTv_register.setText(builder);

    }

    private void playVideo() {
        Uri mVideoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcome_video);
        mRegister_videoView.setVideoURI(mVideoUri);
        mRegister_videoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mRegister_videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
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
        if (view == mBt_get_security_code) {
            String phone = mEdt_account.getText().toString().trim();
            if (!CheckPhone.isMobileNum(phone)) {
                CustomToast.makeText(getContext(), getString(R.string.txt_phone), Toast.LENGTH_SHORT).show();
                return;
            } else {
                getVerification(phone);
            }

        } else if (view == mBtn_regist_next) {

            //startActivity(YJActivateGiftCard.class);

            String phone = mEdt_account.getText().toString().trim();
            String mpassword = mEdt_password.getText().toString().trim();
            String mScurity = mEdt_security_code.getText().toString().trim();
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
        } else if (view == mTv_register) {
            Intent in = new Intent(AYJRegisterActivity.this, YJWebviewActivity.class);
            in.putExtra("userURL", YJReqURLProtocol.USER_AGREEMENT);
            startActivity(in);
        } else if (view == re_tel) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01053733201"));
            startActivity(intent);
        } else if (view == mRegister_rl_back) {
            finish();
        } else if (view == mRegister_hidden_password) {
            if (isClick) {
                //设置EditText的密码为可见的
                mEdt_password.setTransformationMethod(HideReturnsTransformationMethod
                        .getInstance());
                mRegister_hidden_password_image.setImageResource(R.mipmap.login_accord);
                isClick = false;
            } else {
                //设置密码为隐藏的
                mEdt_password.setTransformationMethod(PasswordTransformationMethod
                        .getInstance());
                mRegister_hidden_password_image.setImageResource(R.mipmap.login_hidden);
                isClick = true;
            }
        }

    }

    public void getCode(String phone) {

        mBt_get_security_code.setEnabled(false);
        mBt_get_security_code.setBackgroundResource(R.mipmap.security_code_bg);
        mBt_get_security_code.setTextColor(getResources().getColor(R.color.gray_text));
        mBt_get_security_code.setText(mResidueSecond + getString(R.string.second) + "重新获取");
        postRunnable(mRunnable, 1000);

    }

    public void onRecovery() {
        mBt_get_security_code.setText(getString(R.string.get_code_again));

        mBt_get_security_code.setBackgroundResource(R.mipmap.security_code_bg_select);
        mBt_get_security_code.setTextColor(getResources().getColor(R.color.white));
        mResidueSecond = 120;
        mBt_get_security_code.setEnabled(true);
    }

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
             /*  JSONObject json = null;
                try {
                    json = new JSONObject(s);
                    String name = json.getString("name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        });
        if (YJGlobal.getGradeList() == null) {
            getGradeNoLogin();
        }
    }

    private void getGradeNoLogin() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_GRADE, null, false, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getGradeLogin失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功getGradeLogin=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            List<YJGradeModel> yjGradeModelList = JSON.parseArray(jsonData.getString("grades"), YJGradeModel.class);
                            YJGlobal.setGradeList(yjGradeModelList);

                            break;
                        case 300:
                            break;
                        case 500:
                            break;
                        case 600:
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

    protected abstract void register(String phone, String mpassword, String mScurity);

}
