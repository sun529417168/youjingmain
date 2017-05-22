package com.youjing.yjeducation.ui.actualize.dialog;

import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJSelectSexDialog;

/**
 * user  秦伟宁
 * Date 2016/4/5
 * Time 11:34
 */
public class YJSelectSexDialog extends AYJSelectSexDialog {
    @Override
    protected void onBtnOkClick(String sexValue) {
        notifyListener(YJNotifyTag.USER_SEX, sexValue);
        close();
    }
}
