package com.youjing.yjeducation.ui.actualize.activity;

import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.ui.dispaly.activity.AYJChangeParentOrName;

/**
 * Created by w7 on 2016/4/30.
 */
public class YJChangeParentOrName extends AYJChangeParentOrName {


    private String TAG = "YJChangeParentOrName";

    @Override
    protected void changeSomthing(String somthing, String kinds) {
        if (kinds.equals("PARENT")) {
            notifyListener(YJNotifyTag.USER_PARENT, somthing);
            finish();
        } else if (kinds.equals("NICKNAME")) {
            notifyListener(YJNotifyTag.USER_NICKNAME, somthing);
            finish();
        } else if ("QQ".equals(kinds)) {
            notifyListener(YJNotifyTag.USER_QQ, somthing);
            finish();
        }
    }

}
