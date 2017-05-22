package com.youjing.yjeducation.ui.dispaly.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.push.CloudPushService;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.dao.MessageDao;
import com.youjing.yjeducation.util.SharedUtil;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * @author stt
 *         创建于2015.6.23
 *         说明：测试用
 *         无返回值
 */
@VLayoutTag(R.layout.text_device)
public class TextDeviceDialog extends AVDialog {
    @VViewTag(R.id.device_1)
    private TextView textview1;
    @VViewTag(R.id.device_2)
    private TextView textview2;
    @VViewTag(R.id.device_3)
    private TextView textview3;

    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        textview1.setText(TextUtils.isEmpty(YJGlobal.getDeviceid()) ? "hello" : YJGlobal.getDeviceid());
//        textview2.setText(TextUtils.isEmpty(AlibabaSDK.getService(CloudPushService.class).getDeviceId()) ? "hello" : AlibabaSDK.getService(CloudPushService.class).getDeviceId());
        textview2.setText(TextUtils.isEmpty(SharedUtil.getString(getContext(), "hello")) ? "hello" : SharedUtil.getString(getContext(), "hello"));
        textview3.setText(TextUtils.isEmpty(SharedUtil.getString(getContext(), "content")) ? "hello" : SharedUtil.getString(getContext(), "content"));
    }
}
