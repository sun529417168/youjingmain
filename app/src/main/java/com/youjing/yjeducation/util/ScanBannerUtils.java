package com.youjing.yjeducation.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;

import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCourseTypesBaseMedel;
import com.youjing.yjeducation.talkfun.YJPlaybackModeTwo;
import com.youjing.yjeducation.talkfun.customtalkfun.YJCustomLiveActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJScanWebviewActivity;

import org.apache.http.Header;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author stt
 *         类说明：扫一扫或者banner区域的逻辑跳转
 *         创建时间：2016.7.20
 */
public class ScanBannerUtils {
    protected static List<YJCourseTypesBaseMedel> yjCourseTypeModelList;
    private static boolean isHasCourseTypeId = true;
    private static YJBaseActivity mActivity;


    private static Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    mActivity.notifyListener(YJNotifyTag.MY_TASK, "");
                    break;
                }
                case 1: {
                    mActivity.notifyListener(YJNotifyTag.MY_CARD, "");
                    break;
                }
                default:
                    break;
            }
        }
    };


    public static void intentPage(YJBaseActivity activity, String msg, String TAG, boolean mIsLogin, int index) {//index作为表示，如果是1那么传入的页面要关闭，如果是0不做处理
        mActivity = activity;
        boolean isInApp = false;
        String strRequestKeyAndValues = "";
        StringUtils.Log("传过来的URL===", "mYjCourseModel=" + msg);
        Map<String, String> mapRequest = ParseURL.URLRequest(msg);
        for (String strRequestKey : mapRequest.keySet()) {
            String strRequestValue = mapRequest.get(strRequestKey);
            strRequestKeyAndValues += "key:" + strRequestKey + ",Value:" + strRequestValue + ";";
            StringUtils.Log(TAG, "strRequestKeyAndValues=" + strRequestKeyAndValues);
            if (strRequestKey.equals("yj_type_one")) {
                isInApp = true;
            }
        }
        if (!isInApp) {
            Intent intent = new Intent();
            intent.setClass(activity, AYJScanWebviewActivity.class);
            intent.putExtra("msg", msg);

            activity.startActivity(intent);
        } else {
            if (!TextUtils.isEmpty(mapRequest.get("yj_type_one")) && mapRequest.get("yj_type_one").equals("in")) {
                String yj_type_app = mapRequest.get("yj_type_app");
                StringUtils.Log(TAG, "yj_type_app=" + yj_type_app);
                if (!TextUtils.isEmpty(yj_type_app)) {
                    Intent intent = null;
                    switch (yj_type_app) {
                        case "course_list":
                            String courseTypeId = mapRequest.get("coursetypeid");
                            if (!TextUtils.isEmpty(courseTypeId)) {
                                scanCourseList((YJBaseActivity) activity, courseTypeId);
                            }
                            if (index == 1) {
                                activity.finish();
                            }
                            break;
                        case "course_detail":
                            String courseId = mapRequest.get("courseid");
                            String catalogId = mapRequest.get("catalogid");
                            if (!TextUtils.isEmpty(courseId)) {
                                intent = new Intent(activity, YJCoursePlayNewActivity.class);
                                intent.putExtra("courseId", courseId);
                                intent.putExtra("catalogId", catalogId);
                                activity.startActivity(intent);
                            }
                            if (index == 1) {
                                activity.finish();
                            }
                            break;
                        case "live_menu":
                            activity.notifyListener(YJNotifyTag.MAIN_VIDEO, 2);
                            if (index == 1) {
                                activity.finish();
                            }
                            break;
                        case "questions_list":
                            activity.notifyListener(YJNotifyTag.MAIN_QUESTION, 1);
                            if (index == 1) {
                                activity.finish();
                            }
                            break;
                        case "task_list":
                            if (mIsLogin) {
                                activity.notifyListener(YJNotifyTag.MAIN_MY, 3);
                                final Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    public void run() {
                                        mHandler.sendEmptyMessage(0);
                                        timer.cancel();
                                    }
                                }, 300);
                            } else {                                                                                           //1：去我的卡包  2：任务中心
                                activity.startActivity(activity.createIntent(YJLoginActivity.class, activity.createTransmitData(YJLoginActivity.REGIST_INFO, "2").set(YJLoginActivity.LOGIN_OUT, -1).set(YJLoginActivity.MY_LOGIN_OUT, false)));
                            }
                            if (index == 1) {
                                activity.finish();
                            }
                            break;
                        case "card_list":
                            if (mIsLogin) {
                                activity.notifyListener(YJNotifyTag.MAIN_MY, 3);
                                final Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    public void run() {
                                        mHandler.sendEmptyMessage(1);
                                        timer.cancel();
                                    }
                                }, 300);
                            } else {                                                                                        //1：去我的卡包  2：任务中心
                                activity.startActivity(activity.createIntent(YJLoginActivity.class, activity.createTransmitData(YJLoginActivity.REGIST_INFO, "1").set(YJLoginActivity.LOGIN_OUT, -1).set(YJLoginActivity.MY_LOGIN_OUT, false)));
                            }
                            if (index == 1) {
                                activity.finish();
                            }
                            break;
                        case "create_order":
                            String orderCourseId = mapRequest.get("courseid");
                            String orderCatalogId = mapRequest.get("catalogid");
                            if (!TextUtils.isEmpty(orderCourseId)) {
                                intent = new Intent(activity, YJCoursePlayNewActivity.class);
                                intent.putExtra("courseId", orderCourseId);
                                intent.putExtra("catalogId", orderCatalogId);
                                intent.putExtra("isScan", "Yes");
                                activity.startActivity(intent);
                            }
                            if (index == 1) {
                                activity.finish();
                            }
                            break;
                        default:
                            activity.showToast("无法识别");
                            break;
                    }
                }
            } else if (!TextUtils.isEmpty(mapRequest.get("yj_type_one")) && mapRequest.get("yj_type_one").equals("out")) {
                Intent intent = new Intent();
                intent.setClass((Context) activity, AYJScanWebviewActivity.class);
                StringUtils.Log("ScanBannerUtils", "msg2=" + msg);
                intent.putExtra("msg", msg);
                activity.startActivity(intent);
            }
        }
    }

    private static void scanCourseList(YJBaseActivity activity, String courseTypeId) {
        yjCourseTypeModelList = YJGlobal.getYJCOURSETYPESBASEMEDEL();
        if (yjCourseTypeModelList != null) {
            StringUtils.Log("yjCourseTypeModelList","yjCourseTypeModelList="+yjCourseTypeModelList);
            for (int i = 0; i < yjCourseTypeModelList.size(); i++) {
                if (yjCourseTypeModelList.get(i).getCourseTypeId().equals(courseTypeId)) {
                    isHasCourseTypeId = false;
                    saveGrade(yjCourseTypeModelList.get(i).getName(), i, activity, courseTypeId);
                    Intent intent = new Intent(activity, YJCourseListlActivity.class);
                    intent.putExtra("courseTypeId", courseTypeId);
                    activity.startActivity(intent);
                }
            }
            if (isHasCourseTypeId) {
                saveGrade(yjCourseTypeModelList.get(0).getName(), 0, activity, yjCourseTypeModelList.get(0).getCourseTypeId());
                Intent intent = new Intent(activity, YJCourseListlActivity.class);
                intent.putExtra("courseTypeId", yjCourseTypeModelList.get(0).getCourseTypeId());
                activity.startActivity(intent);
            }
            isHasCourseTypeId = true;
        } else {
            return;
        }

    }


    private static void saveGrade(String course, int num, YJBaseActivity activity, String courseTypeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course", course);
        editor.putInt("courseNum", num);
        editor.putString("courseTypeId", courseTypeId);
        editor.commit();
    }


    // 获取直播间初始化字段
    public static void getLiveInfo(final IVActivity ivActivity,final String courseId, final String courseType, final String courseCatalogId ,final boolean isShowDialog, final int isLiveType) {

        Map<String, Object> objectMap = new HashMap<>();
        try {
            StringUtils.Log("ScanBannerUtils", "courseId=" + courseId + "::::::::::courseCatalogId=" + courseCatalogId);
            objectMap.put("courseId", courseId);
            if (!TextUtils.isEmpty(courseCatalogId)){
                objectMap.put("courseCatalogId", courseCatalogId);
                if (courseCatalogId.equals("0")){
                    objectMap.put("courseType", "openClass");
                }else {
                    objectMap.put("courseType", courseType);
                }

            }else {
                objectMap.put("courseType", courseType);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_All_LIVE_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ivActivity.showToast(ivActivity.getContext().getString(R.string.no_net_work));
                return;
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log("ScanBannerUtils", "getLiveInfo=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            JSONObject jsonRoomInfo = new JSONObject(jsonData.getString("roomInfo"));
                            JSONObject jsonLiveAttachInfo = new JSONObject(jsonData.getString("liveAttachInfo"));
                            JSONObject jsonTeacherInfo = new JSONObject(jsonLiveAttachInfo.getString("teacher"));
                            String token = jsonRoomInfo.getString("access_token");
                            String courseName = jsonLiveAttachInfo.getString("courseName");
                            String endTime = jsonLiveAttachInfo.getString("planEndDate");
                            String startTime = jsonLiveAttachInfo.getString("planDate");
                            String courseVideoId = jsonLiveAttachInfo.getString("courseVideoId");

                            String mTeacherId = jsonTeacherInfo.getString("teacherId");
                            String teacherName = jsonTeacherInfo.getString("trueName");

                            Intent intent = new Intent();
                            intent.setClass(ivActivity.getContext(), YJCustomLiveActivity.class);
                            intent.putExtra("token", token);
                            intent.putExtra("teacherId", mTeacherId);
                            intent.putExtra("teacherName", teacherName);
                            intent.putExtra("courseVideoId", courseVideoId);
                            intent.putExtra("courseId", courseId);
                            intent.putExtra("startTime", startTime);
                            intent.putExtra("endTime", endTime);
                            intent.putExtra("courseName", courseName);
                            intent.putExtra("courseCatalogId", courseCatalogId);
                            intent.putExtra("isShowDialog", isShowDialog);
                            intent.putExtra("isLiveType", isLiveType);

                            intent.putExtra("isPlayback", false);
                            intent.putExtra("mode", 2);
                            ivActivity.startActivity(intent);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 500:
                            break;
                        case 600:
                            ivActivity.startActivity(ivActivity.createIntent(YJLoginActivity.class, ivActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            ivActivity.finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //进入回放页面
    public static void getTakenBack(final IVActivity ivActivity,String courseVideoId,String teacherId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("teacherId", teacherId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_LIVE_BACK_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ivActivity.showToast(ivActivity.getContext().getString(R.string.no_net_work));
                return;
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            JSONObject jsonTaken = new JSONObject(jsonData.getString("replayInfo"));
                            String token = jsonTaken.getString("access_token");
                            if (!TextUtils.isEmpty(token)) {
                                Intent intent = new Intent();
                                intent.setClass(ivActivity.getContext(), YJPlaybackModeTwo.class);
                                intent.putExtra("token", token);
                                intent.putExtra("isPlayback", true);
                                intent.putExtra("mode", 2);
                                ivActivity.startActivity(intent);
                            } else {
                                ivActivity.showToast("回放准备中");
                                return;
                            }
                            break;
                        case 300:

                            break;
                        case 400:

                            break;
                        case 401:
                            ivActivity.showToast("回放准备中");
                            break;
                        case 500:
                            break;
                        case 600:
                            ivActivity.startActivity(ivActivity.createIntent(YJLoginActivity.class, ivActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            ivActivity.finishAll();
                            break;
                        default:
                            break;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
