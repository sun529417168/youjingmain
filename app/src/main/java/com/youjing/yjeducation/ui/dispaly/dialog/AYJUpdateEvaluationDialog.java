package com.youjing.yjeducation.ui.dispaly.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.ui.actualize.dialog.YJShareDialog;
import com.youjing.yjeducation.util.MarketUtils;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.StringUtils;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * @author stt
 *         创建于2016.7.5
 *         说明：app更新评价的dialog
 */
@VLayoutTag(R.layout.dialog_update_evaluation)
public class AYJUpdateEvaluationDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialog_update_evaluation_close)
    private RelativeLayout mDialog_update_evaluation_close;//关闭按钮
    @VViewTag(R.id.dialog_update_evaluation_teasing)
    private Button mDialog_update_evaluation_teasing;//黑洞吐槽
    @VViewTag(R.id.dialog_update_evaluation_praise)
    private Button mDialog_update_evaluation_praise;//24K好评

    @Override
    public void onClick(View view) {
        if (view == mDialog_update_evaluation_close) {
            this.close();
        }
        if (view == mDialog_update_evaluation_teasing) {
            StringUtils.toScore((IVActivity) getContext());
        }
        if (view == mDialog_update_evaluation_praise) {
            StringUtils.toScore((IVActivity) getContext());
        }
    }
}
