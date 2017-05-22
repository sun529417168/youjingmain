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
@VLayoutTag(R.layout.dialog_order_select)
public class AYJOrderSelectDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.re_dialog_bg_left)
    private RelativeLayout  re_dialog_bg_left;
    @VViewTag(R.id.re_dialog_bg_below)
    private RelativeLayout  re_dialog_bg_below;
    @VViewTag(R.id.txt_whale_order)
    private TextView mTxt_whale_order;
    @VViewTag(R.id.txt_rmb_order)
    private TextView mTxt_rmb_order;

    @Override
    public void onClick(View view) {
        if(view == re_dialog_bg_left){
            this.close();
        }else if(view == re_dialog_bg_below){
            this.close();
        }else if(view == mTxt_whale_order){
            showToast("鲸币订单");
            this.close();
        }else if(view == mTxt_rmb_order){
            showToast("现金订单");
            this.close();
        }

    }

    @Override
    protected void onLoadedView() {
        super.onLoadedView();

    }
}
