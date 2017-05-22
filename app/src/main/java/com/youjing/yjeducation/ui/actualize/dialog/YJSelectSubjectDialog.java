package com.youjing.yjeducation.ui.actualize.dialog;

import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJSelectSubjectDialog;

/**
 * user  秦伟宁
 * Date 2016/4/1
 * Time 14:39
 */
public class YJSelectSubjectDialog extends AYJSelectSubjectDialog {
    private String TAG = "YJSelectSubjectDialog";

    @Override
    protected void onBtnOkClick(final String gradeValue,final int id) {
        notifyListener(YJNotifyTag.USER_SUBJECT, gradeValue+","+id);
        close();
    }
}
