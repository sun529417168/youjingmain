package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_message_select)
public class AYJMessageSelectDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.re_dialog_bg_left)
    private RelativeLayout re_dialog_bg_left;
    @VViewTag(R.id.re_dialog_bg_below)
    private RelativeLayout re_dialog_bg_below;
    @VViewTag(R.id.txt_active)
    private TextView txt_active;
    @VViewTag(R.id.txt_new)
    private TextView txt_new;
    @VViewTag(R.id.txt_system)
    private TextView txt_system;

    @Override
    public void onClick(View view) {
        if (view == re_dialog_bg_left) {
            this.close();
        } else if (view == re_dialog_bg_below) {
            this.close();
        } else if (view == txt_active) {
            showToast("活动推广");
            this.close();
        } else if (view == txt_new) {
            showToast("新科上架");
            this.close();
        } else if (view == txt_system) {
            showToast("系统通知");
            this.close();
        }

    }

    @Override
    protected void onLoadedView() {
        super.onLoadedView();

    }
}
