package com.youjing.yjeducation.ui.actualize.dialog;

import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJSelectAreaDialog;

/**
 * user  秦伟宁
 * Date 2016/4/5
 * Time 13:22
 */
public class YJSelectAreaDialog extends AYJSelectAreaDialog {
    @Override
    protected void onBtnOkClick(String area) {
        notifyListener(YJNotifyTag.USER_AREA, area);
        close();
    }
}
