package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
@VLayoutTag(R.layout.dialog_exit)
public abstract class AYJExitDialog  extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.btn_cancel)
    private Button mBtnCancel;
    @VViewTag(R.id.btn_ok)
    private Button mBtnOk;
    @VViewTag(R.id.btn_close)
    private ImageButton mBtnClose;

    @Override
    public void onClick(View view) {
        if(view == mBtnCancel) {
            onBtnCancelClick();
        } else if (view == mBtnClose) {
            close();
        } else
            onBtnOkClick();
    }

    protected abstract void onBtnCancelClick();

    protected abstract void onBtnOkClick();
}
