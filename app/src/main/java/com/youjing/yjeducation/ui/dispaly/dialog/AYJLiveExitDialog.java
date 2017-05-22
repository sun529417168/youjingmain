package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.talkfun.customtalkfun.CustomBasiHtActivity;
import com.youjing.yjeducation.util.ClickUtil;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
@VLayoutTag(R.layout.dialog_live_exit)
public abstract class AYJLiveExitDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialog_cancel)
    private Button dialog_cancel;
    @VViewTag(R.id.dialog_ok)
    private Button dialog_ok;

    @Override
    public void onClick(View view) {
        if(view == dialog_cancel) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            onBtnCancelClick();
            CustomBasiHtActivity.isShowDialog = true;
        } else if (view == dialog_ok) {
            close();
            CustomBasiHtActivity.isShowDialog = true;
        }
    }
    protected abstract void onBtnCancelClick();

}
