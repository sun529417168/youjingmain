package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.Button;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * 直播列表，课程详情没有回放数据的时候弹出的dialog
 * 孙腾腾
 * 创建时间：2016.06.24
 */
@VLayoutTag(R.layout.dialog_isreplay)
public class AYJIsReplayDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialot_isreplay_close)
    private Button mCancle;

    @Override
    public void onClick(View view) {
        if (view == mCancle) {
            close();
        }
    }
}
