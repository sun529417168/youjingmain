package com.youjing.yjeducation.core;

import android.content.Context;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.sdk.android.AlibabaSDK;
import com.alibaba.sdk.android.push.CloudPushService;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.util.StringUtils;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * user  秦伟宁
 * Date 2016/5/21
 * Time 16:46
 */
public class YJUserStudyData {
    private static String TAG = "YJUserStudyData";
    private static String mStudyId = "";
    private static String mLiveStudyId;

    //点播开始学习行为
    public static void catalogBeginStudy(YJCourseModel mYjCourseModel, int position, boolean isFirst) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseId", mYjCourseModel.getCourseId());
            objectMap.put("courseCatalogId", mYjCourseModel.getCourseCatalogList().get(position).getCourseCatalogId());
            objectMap.put("courseVideoId", mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoId());
            objectMap.put("courseVideoShape", mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoShape());
            objectMap.put("execType", "begin");
            if (isFirst) {
                if (!TextUtils.isEmpty(mYjCourseModel.getLastStudyTime())) {
                    objectMap.put("pointTime", mYjCourseModel.getLastStudyTime());
                } else {
                    objectMap.put("pointTime", "0");
                }
            } else {
                objectMap.put("pointTime", "0");
            }
            objectMap.put("studyTime", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SAVE_STUDENT_DATA, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "catalogBeginStudy失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "catalogBeginStudy成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            mStudyId = jsonData.getString("studyId");
                            startTimer();
                            break;
                        case 500:
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

    //点播记录结束学习记录状态
    public static void catalogEndStudy(YJCourseModel mYjCourseModel, int position, long lastPosition) {

        if(TextUtils.isEmpty(mStudyId)){
            return;
        }
        Map<String, Object> objectMap = new HashMap<>();
        try {
            endTimer();
            objectMap.put("courseId", mYjCourseModel.getCourseId());
            objectMap.put("courseCatalogId", mYjCourseModel.getCourseCatalogList().get(position).getCourseCatalogId());
            objectMap.put("courseVideoId", mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoId());
            objectMap.put("courseVideoShape", mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoShape());
            objectMap.put("execType", "end");
            objectMap.put("studyId", mStudyId);
            objectMap.put("pointTime", lastPosition / 1000 + "");
            objectMap.put("studyTime", pointTime + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SAVE_STUDENT_DATA, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "catalogEndStudy失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "catalogEndStudy成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            String study = json.getString("data");
                            break;

                        case 500:
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


    //点播记录结束学习记录状态
    public static void catalogEndStudyNoP(YJCourseModel mYjCourseModel, int position) {
        if(TextUtils.isEmpty(mStudyId)){
            return;
        }
        Map<String, Object> objectMap = new HashMap<>();
        try {
            endTimer();
            objectMap.put("courseId", mYjCourseModel.getCourseId());
            objectMap.put("courseCatalogId", mYjCourseModel.getCourseCatalogList().get(position).getCourseCatalogId());
            objectMap.put("courseVideoId", mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoId());
            objectMap.put("courseVideoShape", mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoShape());
            objectMap.put("execType", "end");
            objectMap.put("studyId", mStudyId);
            objectMap.put("pointTime", pointTime + "");
            objectMap.put("studyTime", pointTime + "");


        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SAVE_STUDENT_DATA, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "catalogEndStudy失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "catalogEndStudy成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            String study = json.getString("data");
                            break;

                        case 500:
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


    private static YJCourseModel liveYjCourseModel;
    private static int livePosition;

    //直播开始学习行为
    public static void liveCatalogBeginStudy(YJCourseModel mYjCourseModel, int position) {
        liveYjCourseModel = mYjCourseModel;
        livePosition = position;
        if(!TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getCourseCatalogId()) && mYjCourseModel.getCourseCatalogList().get(position).getCourseCatalogId().equals("0")){
            return;
        }
        Map<String, Object> objectMap = new HashMap<>();
        try {
            StringUtils.Log(TAG, "catalogBeginStudy  position=" + position);
            objectMap.put("courseId", mYjCourseModel.getCourseId());
            objectMap.put("courseCatalogId", mYjCourseModel.getCourseCatalogList().get(position).getCourseCatalogId());
            objectMap.put("courseVideoId", mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoId());
            objectMap.put("courseVideoShape", "LIVE");
            objectMap.put("execType", "begin");
            objectMap.put("pointTime", "0");
            objectMap.put("studyTime", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SAVE_STUDENT_DATA, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "liveCatalogBeginStudy失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "liveCatalogBeginStudy成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            mLiveStudyId = jsonData.getString("studyId");
                            startTimer();
                            break;

                        case 500:
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


    //直播记录结束学习记录状态
    public static void liveCatalogEndStudy() {

        if(TextUtils.isEmpty(mStudyId)){
            return;
        }
        if(!TextUtils.isEmpty(liveYjCourseModel.getCourseCatalogList().get(livePosition).getCourseCatalogId()) && liveYjCourseModel.getCourseCatalogList().get(livePosition).getCourseCatalogId().equals("0")){
            return;
        }
        Map<String, Object> objectMap = new HashMap<>();
        try {
            endTimer();
            objectMap.put("courseId", liveYjCourseModel.getCourseId());
            objectMap.put("courseCatalogId", liveYjCourseModel.getCourseCatalogList().get(livePosition).getCourseCatalogId());
            objectMap.put("courseVideoId", liveYjCourseModel.getCourseCatalogList().get(livePosition).getCourseVideoId());
            objectMap.put("courseVideoShape", "LIVE");
            objectMap.put("execType", "end");
            objectMap.put("studyId", mLiveStudyId);
            objectMap.put("pointTime", pointTime + "");
            objectMap.put("studyTime", pointTime + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SAVE_STUDENT_DATA, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "liveCatalogEndStudy失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "liveCatalogEndStudy成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            String study = json.getString("data");
                            break;

                        case 500:
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


    //获得计时器时常
    private static long getTime() {
        long time = SystemClock.uptimeMillis();

        return time;
    }

    public static long pointTime = 0;
    private static Timer timer;

    //计时器开始
    public static void startTimer() {
        pointTime = 0;
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    StringUtils.Log(TAG, "pointTime=" + pointTime);
                    pointTime = pointTime + 1;
                }
            }, 0, 1000);
        } else {
            timer.cancel();
            timer = null;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {

                    StringUtils.Log(TAG, "pointTime=" + pointTime);
                    pointTime = pointTime + 1;
                }
            }, 0, 1000);
        }

    }

    //计时器结束
    public static void endTimer() {
        timer.cancel();
        timer = null;
    }


    public static void setDevice(Context context) {
        if (TextUtils.isEmpty(AlibabaSDK.getService(CloudPushService.class).getDeviceId())) {
            return;
        }
        boolean mIsLogin = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isLogin", false);
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("customerId", YJGlobal.getCustomerId());
        objectMap.put("deviceNo", AlibabaSDK.getService(CloudPushService.class).getDeviceId());
        YJStudentReqManager.getServerData(YJReqURLProtocol.SET_DEVICE, objectMap, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {


            }


        });

    }
}
