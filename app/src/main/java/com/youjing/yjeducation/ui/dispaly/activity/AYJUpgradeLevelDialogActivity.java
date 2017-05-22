package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.model.YJLivelUpMedel;
import com.youjing.yjeducation.ui.actualize.dialog.YJShareDialog;

import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * @author stt
 *         类说明：升级弹框
 *         创建时间：2016.6.24
 */
public class AYJUpgradeLevelDialogActivity extends YJBaseActivity {
    private TextView mDialog_upgrade_level_info;//提升到多少级别
    private TextView mDialog_upgrade_level_coinNum;//获得鲸币
    private TextView mDialog_upgrade_level_experience;//获得经验
    private TextView mDialog_upgrade_level_medal;//获得勋章
    private ImageView mDialog_upgrade_level_medal_image;//获得勋章图片
    private Button mDialog_upgrade_level_share;//立即分享
    private RelativeLayout mUpgrade_level_activity;
    private IUiListener qqShareListener;

    private YJLivelUpMedel yjLivelUpMedel = new YJLivelUpMedel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 设置没有标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_upgrade_level);
        init();
        yjLivelUpMedel = (YJLivelUpMedel) this.getIntent().getSerializableExtra("livelUpMedel");
        setLivelUpMedel();
        mDialog_upgrade_level_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                YJShareDialog yjShareDialog = new YJShareDialog();
                showDialog(yjShareDialog);
            }
        });
        mUpgrade_level_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        qqShareListener = new IUiListener() {
            @Override
            public void onCancel() {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast("分享取消");
            }

            @Override
            public void onComplete(Object response) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "sucess");
                //showToast(getString(R.string.share_success));
            }

            @Override
            public void onError(UiError e) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast(getString(R.string.share_fail));
            }
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
            }
        }
    }

    private void init() {
        mDialog_upgrade_level_info = (TextView) findViewById(R.id.dialog_upgrade_level_info);
        mDialog_upgrade_level_coinNum = (TextView) findViewById(R.id.dialog_upgrade_level_coinNum);
        mDialog_upgrade_level_experience = (TextView) findViewById(R.id.dialog_upgrade_level_experience);
        mDialog_upgrade_level_medal = (TextView) findViewById(R.id.dialog_upgrade_level_medal);
        mDialog_upgrade_level_medal_image = (ImageView) findViewById(R.id.dialog_upgrade_level_medal_image);
        mDialog_upgrade_level_share = (Button) findViewById(R.id.dialog_upgrade_level_share);
        mUpgrade_level_activity = (RelativeLayout) findViewById(R.id.upgrade_level_activity);
    }

    /**
     * 给控件赋值
     */
    private void setLivelUpMedel() {
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
            mDialog_upgrade_level_medal_image.setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.dialog_upgrade_level_xunzhangLayout).setVisibility(View.VISIBLE);
            mDialog_upgrade_level_medal.setText("一枚");
            BitmapUtils.create(this).display(mDialog_upgrade_level_medal_image, yjLivelUpMedel.getRewardMedal());
        }
    }


}
