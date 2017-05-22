package com.youjing.yjeducation.ui.actualize.dialog;

import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJSelectBirthdayDialog;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
public class YJSelectBirthdayDialog extends AYJSelectBirthdayDialog {
    @Override
    protected void onBtnOkClick(String year, String month, String day) {
        if (Integer.parseInt(month.replace("月", ""))<10) {
            month = "0"+month.replace("月", "");
        }
        else {
            month = month.replace("月", "");
        }
        if (Integer.parseInt(day.replace("日", ""))<10) {
            day = "0"+day.replace("日", "");
        }else {
            day = day.replace("日", "");
        }
        final String birthday = year.replace("年", "") + "-" + month + "-" + day;

        notifyListener(YJNotifyTag.USER_BIRTHDAY, birthday);
        close();
    }
}
