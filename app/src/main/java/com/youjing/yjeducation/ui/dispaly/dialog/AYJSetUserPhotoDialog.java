package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/4/15
 * Time 13:52
 */

@VLayoutTag(R.layout.dialog_user_photo)
public abstract class AYJSetUserPhotoDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.btn_cancel)
    private Button mBtnCancel;
    @VViewTag(R.id.btn_take_photo)
    private Button mBtnTakePhoto;
    @VViewTag(R.id.btn_select_from_album)
    private Button mBtnSelectPhoto;
    @VViewTag(R.id.re_gray_bg)
    private RelativeLayout re_gray_bg;

    @Override
    public void onClick(View view) {
        if (view == mBtnCancel) {
            close();
        } else if (view == mBtnTakePhoto) {
            onTakePhotoClick();
        } else if (view == mBtnSelectPhoto) {
            onSelectPhotoClick();
        }else if (view == re_gray_bg) {
            close();
        }
    }

    protected abstract void onTakePhotoClick();
    protected abstract void onSelectPhotoClick();
}
