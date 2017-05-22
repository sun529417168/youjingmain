package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.ui.actualize.dialog.YJShareDialog;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * @author stt
 *         创建于2016.7.5
 *         说明：点击签到成功后的dialog
 */
@VLayoutTag(R.layout.dialog_signin_success)
public class AYJSignInDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialog_signin_dataNum)
    private TextView mDialog_signin_dataNum;//签到天数
    @VViewTag(R.id.dialog_signin_whaleNum)
    private TextView mDialog_signin_whaleNum;//签到获得的鲸币
    @VViewTag(R.id.dialog_signin_success_close)
    private ImageView mDialog_signin_success_close;//关闭
    @VViewTag(R.id.dialog_signin_success_share)
    private LinearLayout mDialog_signin_success_share;//分享按钮
    public static final VParamKey<String> SIGNINDATANUM = new VParamKey<>(null);
    public static final VParamKey<String> SIGNINWHALENUM = new VParamKey<>(null);
    private String signData, signWhale;

    @Override
    protected void onLoadedView() {
        signData = getTransmitData(SIGNINDATANUM);
        signWhale = getTransmitData(SIGNINWHALENUM);
        mDialog_signin_dataNum.setText("第" + signData + "天签到获得");
        mDialog_signin_whaleNum.setText("x" + signWhale);
    }

    @Override
    public void onClick(View view) {
        if (view == mDialog_signin_success_close) {
            this.close();
        } else if (view == mDialog_signin_success_share) {
            showDialog(new YJShareDialog());
        }
    }
}