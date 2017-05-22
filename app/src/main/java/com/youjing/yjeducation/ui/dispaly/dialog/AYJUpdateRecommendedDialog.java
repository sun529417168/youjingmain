package com.youjing.yjeducation.ui.dispaly.dialog;

import android.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJUpdate;
import com.youjing.yjeducation.ui.actualize.dialog.YJUpdateDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJUpdateTipDialog;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * @author stt
 *         创建于2016.7.5
 *         说明：app推荐更新的dialog
 */
@VLayoutTag(R.layout.dialog_update_recommended)
public class AYJUpdateRecommendedDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialog_update_recommended_close)
    private RelativeLayout mDialog_update_recommended_close;//关闭按钮
    @VViewTag(R.id.dialog_update_recommended_version)
    private TextView mDialog_update_recommended_version;//更新版本号
    @VViewTag(R.id.dialog_update_recommended_infoLayout)
    private LinearLayout mDialog_update_recommended_infoLayout;//动态添加更新的内容信息
    @VViewTag(R.id.dialog_update_recommended_btn)
    private RelativeLayout mDialog_update_recommended_btn;//更新下载
    @VViewTag(R.id.dialog_update_recommended_text)
    private TextView mDialog_update_recommended_text;//更新下载的内容信息

    private String mInfo;
    private String versionNum;
    private int mStatus;
    private boolean mIsFlag;
    private IOnCloseListener mListener;

    public static final VParamKey<String> KEY_INFO = new VParamKey<>(null);
    public static final VParamKey<String> VERSIONNUM = new VParamKey<>(null);
    public static final VParamKey<Integer> KEY_FLAG = new VParamKey<>(-1);
    public static final VParamKey<IOnCloseListener> KEY_LISTENER = new VParamKey<>(null);
    public static final VParamKey<Boolean> KEY_LOGIN = new VParamKey<>(false);


    @Override
    protected void onLoadingView() {
        super.onLoadingView();
        mInfo = getTransmitData(KEY_INFO);
        mStatus = getTransmitData(KEY_FLAG);
        mListener = getTransmitData(KEY_LISTENER);
        mIsFlag = getTransmitData(KEY_LOGIN);
        versionNum = getTransmitData(VERSIONNUM);
    }


    @Override
    protected void onLoadedView() {
        mDialog_update_recommended_text.setText(mInfo);
        mDialog_update_recommended_version.setText(versionNum);
        if (mIsFlag) {
            mDialog_update_recommended_close.setVisibility(View.VISIBLE);
        } else {
            mDialog_update_recommended_close.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {


        mDialog_update_recommended_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                YJUpdate update = new YJUpdate(AYJUpdateRecommendedDialog.this, true, true);
                update.autoLogin();
                close();
            }
        });


        mDialog_update_recommended_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                if (mListener != null) mListener.onClose();
            }
        });

    }

    public interface IOnCloseListener {
        void onClose();
    }
}
