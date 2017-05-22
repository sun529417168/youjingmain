package com.youjing.yjeducation.ui.actualize.activity;

import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCourseListModel;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCourseListActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/15
 * Time 11:01
 */
public class YJCourseListlActivity extends AYJCourseListActivity {
    private String TAG = "YJCourseListlActivity";

    @Override
    public void refresh(String courseTypeId) {

        Map<String, Object> objectMap = new HashMap<>();

        try {
            objectMap.put("subjectId", YJGlobal.getMy_subjectId());
            objectMap.put("gradeId", YJGlobal.getMy_grade_id());
            objectMap.put("courseTypeId", courseTypeId);
            objectMap.put("currentPage", 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_LIST, objectMap, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                mMsgListView.stopRefresh();
                mMsgListView.setRefreshTime(System.currentTimeMillis());
                showToast(getString(R.string.no_net_work));
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
                            List<YJCourseListModel> yjCourseListModelList = JSON.parseArray(jsonData.getString("knowledgeList"), YJCourseListModel.class);

                            mYjCourseModelList = getYjCourseModel(yjCourseListModelList);
                            if (mYjCourseModelList != null) {
                                initXListView();
                            }
                            mMsgListView.stopRefresh();
                            mMsgListView.setRefreshTime(System.currentTimeMillis());
                        case 300:
                            break;
                        case 400:
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

    @Override
    public void loadmore(String courseTypeId, int page) {
        Map<String, Object> objectMap = new HashMap<>();

        try {
            objectMap.put("subjectId", YJGlobal.getMy_subjectId());
            objectMap.put("gradeId", YJGlobal.getMy_grade_id());
            objectMap.put("courseTypeId", courseTypeId);
            objectMap.put("currentPage", page);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_LIST, objectMap, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
                mMsgListView.stopRefresh();
                mMsgListView.setRefreshTime(System.currentTimeMillis());
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
                            List<YJCourseListModel> yjCourseListModelList = JSON.parseArray(jsonData.getString("knowledgeList"), YJCourseListModel.class);
                            PaginationInfo paginationInfo = JSON.parseObject(jsonData.getString("pagination"), PaginationInfo.class);
                            StringUtils.Log("分页信息", paginationInfo.toString());
                            notifyListener(YJNotifyTag.COURSE_LIST_PAGE_INFO, paginationInfo);
                            notifyListener(YJNotifyTag.COURSE_LIST_LOAD_MORE, getYjCourseModel(yjCourseListModelList));
                            break;
                        case 300:
                            break;
                        case 400:
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