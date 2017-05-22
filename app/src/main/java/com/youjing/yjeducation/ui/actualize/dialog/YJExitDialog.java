package com.youjing.yjeducation.ui.actualize.dialog;

import com.youjing.yjeducation.ui.dispaly.dialog.AYJExitDialog;
/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
public class YJExitDialog extends AYJExitDialog {
    @Override
    protected void onBtnCancelClick() {
        close();
    }

    @Override
    protected void onBtnOkClick() {
        finish();
    }
}
