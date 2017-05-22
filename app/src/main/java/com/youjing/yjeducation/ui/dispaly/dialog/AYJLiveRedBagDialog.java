package com.youjing.yjeducation.ui.dispaly.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.connect.share.QQShare;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.Tencent;
import com.umeng.socialize.UMShareListener;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.core.YJGlobal;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_live_red_bag)
public abstract class AYJLiveRedBagDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.re_gray_bg)
    private RelativeLayout re_gray_bg;
    @VViewTag(R.id.img_reb_bag_de)
    private ImageView img_reb_bag_de;
    @VViewTag(R.id.txt_whale_num)
    private TextView txt_whale_num;
    @VViewTag(R.id.txt_user_name)
    private TextView txt_user_name;
    @VViewTag(R.id.txt_get)
    private TextView txt_get;
    @VViewTag(R.id.obtain_money)
    private TextView obtain_money;
    @VViewTag(R.id.img_rmb)
    private ImageView img_rmb;

    private String mWhaleNum;
    public static final VParamKey<String> WHALE_NUM = new VParamKey<>(null);

    @Override
    protected void onLoadedView() {
        super.onLoadedView();

        mWhaleNum = getTransmitData(WHALE_NUM);
        if (!TextUtils.isEmpty(mWhaleNum)) {
            txt_whale_num.setText(mWhaleNum + "鲸币");
            obtain_money.setText("　鲸币可以购买课程，赠送礼物");
            if (!TextUtils.isEmpty(YJGlobal.getYjUser().getNickName())) {
                txt_user_name.setText("恭喜" + YJGlobal.getYjUser().getNickName() + "同学");
            }
        } else {
            txt_get.setVisibility(View.GONE);
            img_rmb.setVisibility(View.GONE);
            txt_whale_num.setText("很遗憾！！！");
            obtain_money.setText("手慢了，没有抢到，还有下次哦");
            if (!TextUtils.isEmpty(YJGlobal.getYjUser().getNickName())) {
                txt_user_name.setText( YJGlobal.getYjUser().getNickName() + "同学");
            }
        }


    }

    @Override
    public void onClick(View view) {
        if (view == re_gray_bg) {
            close();
        } else if (view == img_reb_bag_de) {
            close();
        }
    }
}
