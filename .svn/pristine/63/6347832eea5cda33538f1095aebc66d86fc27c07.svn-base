package com.youjing.yjeducation.ui.actualize.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:19
 */
@VLayoutTag(R.layout.dialog_base)
public class YJBaseDialog  extends AVDialog implements IVClickDelegate {
    public static final VParamKey<String> KEY_INFO = new VParamKey<>(null);
    public static final VParamKey<String[]> KEY_BUTTONS = new VParamKey<>(null);
    public static final VParamKey<Boolean> KEY_ONE_LISTENER = new VParamKey<>(false);
    public static final VParamKey<Boolean> KEY_APP_EXIT = new VParamKey<>(false);
    public static final VParamKey<Boolean> KEY_LOGIN = new VParamKey<>(false);
    public static final VParamKey<IOnCloseListener> KEY_LISTENER = new VParamKey<>(null);
    public static final VParamKey<Boolean> KEY_BACK = new VParamKey<>(false);
    public static final VParamKey<Boolean> KEY_CLOSE = new VParamKey<>(true);
    public static final VParamKey<String> KEY_BUTTON = new VParamKey<>(null);

    @VViewTag(R.id.btn_close)
    private ImageButton mBtnClose;
    @VViewTag(R.id.txt)
    private TextView mTxtInfo;
    @VViewTag(R.id.btn_cancel)
    private Button mBtnCancel;
    @VViewTag(R.id.btn_ok)
    private Button mBtnOk;
    private String mInfo;
    private String mOneBtnText;
    private String mTwoBtnText;
    private boolean mOneListener;
    private boolean mAppExit;
    private boolean mLogin;
    private boolean back;
    private boolean mClose;

    private IOnCloseListener mListener;

    @Override
    protected void onLoadingView() {
        String mInfoArray = getTransmitData(KEY_INFO);
        mInfo = mInfoArray;
        String[] mBtnArray = getTransmitData(KEY_BUTTONS);
        mListener = getTransmitData(KEY_LISTENER);
        mOneListener = getTransmitData(KEY_ONE_LISTENER);
        mAppExit = getTransmitData(KEY_APP_EXIT);
        mLogin = getTransmitData(KEY_LOGIN);
        String mBtn = getTransmitData(KEY_BUTTON);
        back = getTransmitData(KEY_BACK);
        mClose = getTransmitData(KEY_CLOSE);
        if (mBtnArray == null) {
            if (mBtn != null) {
                mTwoBtnText = mBtn;
            } else mTwoBtnText = getString(R.string.ok);
            return;
        } else if (mBtnArray.length >= 2) mTwoBtnText = mBtnArray[1];
        mOneBtnText = mBtnArray[0];

    }

    @Override
    protected void onLoadedView() {
        mTxtInfo.setText(mInfo);
        mBtnOk.setText(mTwoBtnText);
        setCancelable(false);
        if (mOneBtnText != null && !mOneBtnText.equals("")) {
            mBtnCancel.setText(mOneBtnText);
            mBtnCancel.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == mBtnOk) {
            ifClose();
        } else if (view == mBtnCancel) {
            if (mAppExit) {
                close();
                finishAll();
            }
            close();
        } else if (view == mBtnClose && !mAppExit && !back) {
            close();
        }
    }

//                if (mLogin) {
//                    PDUpdateInfo update = new PDUpdateInfo(PDBaseDialog.this, true, PDGlobal.getAppType(getContext()), false);
//                    update.beginLogin();

    private void ifClose() {
        if (mListener != null) mListener.onClose();
        if (mClose) close();
    }


    public interface IOnCloseListener {
        void onClose();
    }
}