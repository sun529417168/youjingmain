package com.youjing.yjeducation.ui.dispaly.dialog;

import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.YJLivelUpMedel;
import com.youjing.yjeducation.ui.actualize.dialog.YJShareDialog;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * @author stt
 *         创建于2016.6.24
 *         说明：升级弹窗的dialog
 *         无返回值
 */
@VLayoutTag(R.layout.dialog_upgrade_level)
public class AYJUpgradeLevelDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialog_upgrade_level_info)
    private TextView mDialog_upgrade_level_info;
    @VViewTag(R.id.dialog_upgrade_level_coinNum)
    private TextView mDialog_upgrade_level_coinNum;
    @VViewTag(R.id.dialog_upgrade_level_experience)
    private TextView mDialog_upgrade_level_experience;
    @VViewTag(R.id.dialog_upgrade_level_medal)
    private TextView mDialog_upgrade_level_medal;
    @VViewTag(R.id.dialog_upgrade_level_medal_image)
    private ImageView mDialog_upgrade_level_medal_image;
    @VViewTag(R.id.dialog_upgrade_level_share)
    private Button mBtnShare;
    public static final VParamKey<YJLivelUpMedel> TXT_INFO = new VParamKey<>(null);
    private YJLivelUpMedel yjLivelUpMedel;

    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        yjLivelUpMedel = getTransmitData(TXT_INFO);
        if (TextUtils.isEmpty(yjLivelUpMedel.getLevel())) {
            mDialog_upgrade_level_info.setVisibility(View.INVISIBLE);
        } else {
            mDialog_upgrade_level_info.setText("恭喜您升级到V" + yjLivelUpMedel.getLevel());
        }
        if (TextUtils.isEmpty(yjLivelUpMedel.getRewardCoin())) {
            findViewById(R.id.dialog_upgrade_level_jingbiLayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.dialog_upgrade_level_jingbiLayout).setVisibility(View.VISIBLE);
            mDialog_upgrade_level_coinNum.setText(yjLivelUpMedel.getRewardCoin());
        }
        if (TextUtils.isEmpty(yjLivelUpMedel.getRewardExp())) {
            findViewById(R.id.dialog_upgrade_level_jingyanLayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.dialog_upgrade_level_jingyanLayout).setVisibility(View.VISIBLE);
            mDialog_upgrade_level_experience.setText(yjLivelUpMedel.getRewardExp());
        }
        if (TextUtils.isEmpty(yjLivelUpMedel.getRewardMedal())) {
            findViewById(R.id.dialog_upgrade_level_xunzhangLayout).setVisibility(View.GONE);
        } else {
            findViewById(R.id.dialog_upgrade_level_xunzhangLayout).setVisibility(View.VISIBLE);
            mDialog_upgrade_level_medal.setText("一枚");
        }
        BitmapUtils.create(getContext()).display(mDialog_upgrade_level_medal_image, yjLivelUpMedel.getRewardMedal());
    }


    @Override
    public void onClick(View view) {
        if (view == mBtnShare) {
            showDialog(new YJShareDialog());
        }
    }
}
