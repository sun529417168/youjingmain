package com.youjing.yjeducation.ui.actualize.activity;

import android.content.Intent;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.model.YJCourseCatalogModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJTeacherAskModel;
import com.youjing.yjeducation.talkfun.YJPlaybackModeTwo;
import com.youjing.yjeducation.talkfun.customtalkfun.YJCustomLiveActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCoursePlayNewActivity;
import com.youjing.yjeducation.util.DialogUtil;
import com.youjing.yjeducation.util.ScanBannerUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:34
 */
public class YJCoursePlayNewActivity extends AYJCoursePlayNewActivity {
    private String TAG = "YJCoursePlayNewActivity";
    private String mStudyId;
    @Override
    public  void getTakenLive( final int position,final boolean isShowDialog,final int isLiveType ) {
        isYJCustomLiveActivityDestroy = false;

        ScanBannerUtils.getLiveInfo(this, mYjCourseModel.getCourseId(), "course", mYjCourseModel.getCourseCatalogList().get(position).getCourseCatalogId(), isShowDialog, isLiveType);
        YJUserStudyData.liveCatalogBeginStudy(mYjCourseModel, position);
    }

    @Override
    public void getTakenBack(final int position) {
        if ("No".equals(mYjCourseModel.getCourseCatalogList().get(position).getIsReplay())) {
//            showDialog(new YJIsReplayDialog());
            getTakenLive(position,true,2);
            return;
        }
        ScanBannerUtils.getTakenBack(this,mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoId(),mYjCourseModel.getTeacher().getTeacherId());
        YJUserStudyData.liveCatalogBeginStudy(mYjCourseModel, position);
    }


    public void getTeacherList(final String gradeId, final String subjectId) {
        StringUtils.Log(TAG, "gradeId=" + gradeId + "subjectId=" + subjectId);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", subjectId);
            objectMap.put("gradeId", gradeId);
            objectMap.put("currentPage", 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_TEACHER_LIST, objectMap, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
                YJGlobal.setMy_subjectId(subjectId);
                YJGlobal.setMy_grade_id(gradeId);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            List<YJTeacherAskModel> yjTeacherAskModelList = JSON.parseArray(jsonData.getString("teacherList"), YJTeacherAskModel.class);
                            YJGlobal.setYjTeacherAskModelList(yjTeacherAskModelList);
                            StringUtils.Log(TAG, "yjTeacherAskModelList.toString()=" + yjTeacherAskModelList.toString());

                            YJGlobal.setMy_subjectId(subjectId);
                            YJGlobal.setMy_grade_id(gradeId);
                            notifyListener(YJNotifyTag.TEACHER_LIST, yjTeacherAskModelList);
                            break;
                        case 300:
                            break;
                        case 400:
                            StringUtils.Log(TAG, "400");
                            // YJGlobal.setYjCourseTypeModelList(null);
//                            notifyListener(YJNotifyTag.TEACHER_LIST, null);
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
