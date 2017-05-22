package com.youjing.yjeducation.ui.dispaly.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJForgetPasswordActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJRegisterActivity;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.YJStringWhiteSpaceUtil;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VStringUtil;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_login)
public abstract class AYJLoginDialog extends YJBaseActivity implements IVClickDelegate {
    @VViewTag(R.id.rl_dialog_bg)
    private RelativeLayout mRl_dialog_bg;
    @VViewTag(R.id.btn_login)
    private Button mBtn_login;
    @VViewTag(R.id.edt_account)
    private EditText mEdt_account;
    @VViewTag(R.id.edt_password)
    private EditText mEdt_password;
    @VViewTag(R.id.txt_forget_password)
    private TextView txt_forget_password;
    @VViewTag(R.id.txt_register)
    private TextView mTxt_register;


  /*  protected int mStatus;
    public AYJLoginDialog(int status) {
        this.mStatus = status;
    }
    public AYJLoginDialog() {
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onLoadedView() {
        super.onLoadedView();


        mEdt_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() > 0 && !TextUtils.isEmpty(mEdt_password.getText())) {
                    mBtn_login.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtn_login.setTextColor(getResources().getColor(R.color.light_white));
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
                if (charSequence.length() > 0 && !TextUtils.isEmpty(mEdt_account.getText())) {
                    mBtn_login.setTextColor(getResources().getColor(R.color.white));
                } else {
                    mBtn_login.setTextColor(getResources().getColor(R.color.light_white));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
    public  boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = { 0, 0 };
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
        if(view == mRl_dialog_bg){
            finish();
        }else if(view == mBtn_login){
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            String phone = mEdt_account.getText().toString().trim();
            String password = mEdt_password.getText().toString().trim();

            if (VStringUtil.isNullOrEmpty(phone)) {
                showToast(getString(R.string.txt_phone_number));
                return;
            }
            if (VStringUtil.isNullOrEmpty(password)) {
                showToast(getString(R.string.txt_login_password));
                return;
            }
            char[] cTemp = password.toCharArray();
            char endChar = cTemp[cTemp.length - 1];
            if (YJStringWhiteSpaceUtil.checkIsWhiteSpace(endChar)) {
                showToast(getString(R.string.n_password_have_space_input_again));
                return;
            }

            login(phone, password);
        }else if(view == txt_forget_password){
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            finish();
           startActivity(YJForgetPasswordActivity.class);
        }else if(view == mTxt_register){
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            finish();
            startActivity(createIntent(YJRegisterActivity.class, createTransmitData(YJRegisterActivity.LOGIN_FLAG,true).set(YJRegisterActivity.REGIST_INFO, "").set(YJRegisterActivity.POSITION, -1)));
        }

    }

    @Override
    public void finish() {
        super.finish();

        overridePendingTransition(R.anim.pull_up_in, R.anim.pull_up_out);

    }
    protected abstract void  login(String phone,String password);
}
