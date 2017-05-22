package com.youjing.yjeducation.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.sdk.android.push.notification.CPushMessage;
import com.youjing.yjeducation.component.ActivityBox;
import com.youjing.yjeducation.model.YJCourseTypeModel;
import com.youjing.yjeducation.model.YJLivelUpMedel;
import com.youjing.yjeducation.model.YJMedalGetMedel;
import com.youjing.yjeducation.ui.actualize.activity.YJObtainMedalDialogActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJUpgradeLevelDialogActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author stt
 *         类说明：阿里云推送过来信息后根据字段做判断
 *         创建时间：2016.6.24
 */
public class MyUtils {

    public static void intentAvtivity(Context context, CPushMessage cPushMessage) {
        Intent intent = null;
        Bundle bundle = null;
        try {
            JSONObject json = null;
            json = new JSONObject(cPushMessage.getContent());
            switch (json.getString("messageType")) {
                case "level_up"://等级提升（对应返回等级提升相关数据）
                    YJLivelUpMedel livelUpMedel = JSON.parseObject(json.getString("levelUp"), YJLivelUpMedel.class);
                    intent = new Intent(context, YJUpgradeLevelDialogActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    bundle = new Bundle();
                    bundle.putSerializable("livelUpMedel", livelUpMedel);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    break;
                case "medal_get"://获得勋章（对应返回勋章获得提升相关数据）
                    YJMedalGetMedel medalGetMedel = JSON.parseObject(json.getString("medalGet"), YJMedalGetMedel.class);
                    intent = new Intent(context, YJObtainMedalDialogActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    bundle = new Bundle();
                    bundle.putSerializable("medalGetMedel", medalGetMedel);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                    break;
                case "live_menu"://直播频道红点（无对应数据模型）
                    SharedUtil.setBoolean(context, "levelRed", true);
                    // 更新main界面的livel的红点
                    ActivityBox.AYJMainActivity.showTitleRed();
                    break;
                case "system_msg"://系统消息红点（无对应数据模型）
                    SharedUtil.setBoolean(context, "MyMessageRed", true);
                    ActivityBox.AYJPersonalActivity.showMessageRed(true);
                    ActivityBox.AYJMainActivity.showMeTitleRed();
                    break;
                case "card_apk"://卡包红点（无对应数据模型）
                    SharedUtil.setBoolean(context, "MyGiftRed", true);
                    ActivityBox.AYJPersonalActivity.showGiftRed(true);
                    ActivityBox.AYJMainActivity.showMeTitleRed();
                    break;
                case "order_new"://新订单红点（无对应数据模型）
                    SharedUtil.setBoolean(context, "MyOrderRed", true);
                    ActivityBox.AYJPersonalActivity.showOrderRed(true);
                    ActivityBox.AYJMainActivity.showMeTitleRed();
                    break;
                case "course_new"://新课程红点（无对应数据模型）
                    SharedUtil.setBoolean(context, "MyCourseRed", true);
                    ActivityBox.AYJPersonalActivity.showCourseRed(true);
                    ActivityBox.AYJMainActivity.showMeTitleRed();
                    break;
                case "task_list"://任务列表红点（无对应数据模型）
                    SharedUtil.setBoolean(context, "MyTaskRed", true);
                    ActivityBox.AYJPersonalActivity.showTaskRed(true);
                    ActivityBox.AYJMainActivity.showMeTitleRed();
                    break;
                case "coupon_list"://优惠券列表红点（无对应数据模型）
                    SharedUtil.setBoolean(context, "MyCouponRed", true);
                    ActivityBox.AYJPersonalActivity.showCoupon(true);
                    ActivityBox.AYJMainActivity.showMeTitleRed();
                    break;
                case "invited_list"://邀请列表红点（无对应数据模型）
                    SharedUtil.setBoolean(context, "MyFriendsRed", true);
                    ActivityBox.AYJPersonalActivity.showInvite(true);
                    ActivityBox.AYJMainActivity.showMeTitleRed();
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
