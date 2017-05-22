package com.youjing.yjeducation.core;

import com.youjing.yjeducation.ui.actualize.dialog.YJUpdateRecommendedDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJUpdateTipDialog;

import org.vwork.mobile.ui.IVActivity;
import org.vwork.utils.base.VParams;

/**
 * user  秦伟宁
 * Date 2016/5/21
 * Time 11:28
 */
public class YJShowDialog {
    //标题，内容，一个或两个按键
    public static void showDialogUpdate(IVActivity activity, String strInfo, String versionNum, int flag, boolean isLogin, YJUpdateRecommendedDialog.IOnCloseListener listener) {
//        YJUpdateTipDialog dialog = new YJUpdateTipDialog();
//        VParams data = activity.createTransmitData(YJUpdateTipDialog.KEY_INFO, strInfo).set(YJUpdateTipDialog.KEY_FLAG, flag).set(YJUpdateTipDialog.KEY_LOGIN, isLogin).set(YJUpdateTipDialog.KEY_LISTENER, listener);
//        activity.showDialog(dialog, data);
        YJUpdateRecommendedDialog dialog = new YJUpdateRecommendedDialog();
        VParams data = activity.createTransmitData(YJUpdateRecommendedDialog.KEY_INFO, strInfo).set(YJUpdateRecommendedDialog.KEY_FLAG, flag).set(YJUpdateRecommendedDialog.KEY_LOGIN, isLogin).set(YJUpdateRecommendedDialog.KEY_LISTENER, listener).set(YJUpdateRecommendedDialog.VERSIONNUM, versionNum);
        dialog.setCancelable(false);
        activity.showDialog(dialog, data);
    }
}
