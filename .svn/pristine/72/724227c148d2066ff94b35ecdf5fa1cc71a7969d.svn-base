package com.youjing.yjeducation.ui.actualize.dialog;

import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJSelectGradeDialog;

/**
 * user  秦伟宁
 * Date 2016/4/1
 * Time 14:39
 */
public class YJSelectGradeDialog extends AYJSelectGradeDialog {
    private String TAG = "YJSelectGradeDialog";

    @Override
    protected void onBtnOkClick(final String gradeValue,final  int gradeId) {
        notifyListener(YJNotifyTag.USER_GRADE, gradeValue+","+gradeId);
        close();
    }
}
