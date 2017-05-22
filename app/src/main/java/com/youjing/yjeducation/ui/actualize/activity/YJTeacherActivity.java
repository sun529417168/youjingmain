package com.youjing.yjeducation.ui.actualize.activity;

import android.content.Intent;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.wxlib.util.SysUtil;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJIMGroupModel;
import com.youjing.yjeducation.model.YJTeacherAskModel;
import com.youjing.yjeducation.openim.UserProfileSampleHelper;
import com.youjing.yjeducation.ui.dispaly.activity.AYJTeacherActivity;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.util.SharedUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:58
 */
public class YJTeacherActivity extends AYJTeacherActivity {

    private String TAG = "YJTeacherActivity";

    @Override
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
                mMsgListView.stopRefresh();
                mMsgListView.setRefreshTime(System.currentTimeMillis());
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
                            mMsgListView.stopRefresh();
                            mMsgListView.setRefreshTime(System.currentTimeMillis());
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

    @Override
    public void getAddLoadMore(String gradeId, String subjectId, int page) {
        StringUtils.Log(TAG, "gradeId=" + gradeId + "subjectId=" + subjectId);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", subjectId);
            objectMap.put("gradeId", gradeId);
            objectMap.put("currentPage", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_TEACHER_LIST, objectMap, mIsLogin, new TextHttpResponseHandler() {
              @Override
              public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                  StringUtils.Log(TAG, "失败=" + s);
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
                              List<YJTeacherAskModel> yjTeacherAskModelList = JSON.parseArray(jsonData.getString("teacherList"), YJTeacherAskModel.class);
                              YJGlobal.setYjTeacherAskModelList(yjTeacherAskModelList);
                              PaginationInfo paginationInfo = JSON.parseObject(jsonData.getString("pagination"), PaginationInfo.class);
                              StringUtils.Log("分页信息", paginationInfo.toString());
                              notifyListener(YJNotifyTag.TEACHER_LIST_PAGE_INFO, paginationInfo);
                              notifyListener(YJNotifyTag.TEACHER_LIST_LOAD_MORE, yjTeacherAskModelList);
                              break;
                          case 300:
                              break;
                          case 400:
                              StringUtils.Log(TAG, "400");
                             // notifyListener(YJNotifyTag.TEACHER_LIST_LOAD_MORE, null);
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
    public void enterGroup(int position) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("groupId", yjTeacherAskModelList.get(position).getGroupId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GO_TO_GROUP, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                    progDialog.dismiss();
                }
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
                            YJIMGroupModel yjimGroupModel = JSON.parseObject(json.getString("data"), YJIMGroupModel.class);
                            //判断公告是否为新公告
                            boolean isNew = getGroupNotice(yjimGroupModel);
                            YJGlobal.setNoticeIsNew(isNew);
                            YJGlobal.setYjimGroupModel(yjimGroupModel);
                            gotoGroup(yjimGroupModel);
                            break;
                        case 300:
                            if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                                progDialog.dismiss();
                            }
                            break;
                        case 400:
                            if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                                progDialog.dismiss();
                            }
                            break;
                        case 401:
                            if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                                progDialog.dismiss();
                            }
                            break;
                        case 402:
                            if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                                progDialog.dismiss();
                            }
                            break;
                        case 500:
                            if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                                progDialog.dismiss();
                            }
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

    //开始登录
    private String userid = "";//用户账号
    private String password = "";//密码
    private long tribeId = 0;//群id

    private void gotoGroup(final YJIMGroupModel yjimGroupModel) {


        try {
            userid = DES.decryptDES(yjimGroupModel.getAliUserId());
            password = DES.decryptDES(yjimGroupModel.getAliUserPassword());
            tribeId = Long.parseLong(DES.decryptDES(yjimGroupModel.getAliGroupId()));

            if (SysUtil.isMainProcess()) {
                YWIMKit mIMKit = YWAPI.getIMKitInstance(userid, DES.decryptDES(yjimGroupModel.getAliAppKey()));
                YJGlobal.setmIMKit(mIMKit);
                UserProfileSampleHelper.initProfileCallback();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        IYWLoginService loginService = YJGlobal.getmIMKit().getLoginService();
        YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
        loginService.login(loginParam, new IWxCallback() {

            @Override
            public void onSuccess(Object... arg0) {
                //保存公告id
                saveGroupNotice(yjimGroupModel);

                Intent intent = YJGlobal.getmIMKit().getTribeChattingActivityIntent(tribeId);
                YJGlobal.getmIMKit().setEnableNotification(false);
                startActivity(intent);
//                if (timeOut>0){
//                    timeOut=-1;
//                }
                if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                    progDialog.dismiss();
                }
            }

            @Override
            public void onProgress(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onError(int errCode, String description) {
                //如果登录失败，errCode为错误码,description是错误的具体描述信息
                StringUtils.Log(TAG,"description"+description);
            }
        });
    }


    private void saveGroupNotice(YJIMGroupModel yjimGroupModel) {
        String aliGroupNotice = YJGlobal.getCustomerId() + yjimGroupModel.getAliGroupId() + yjimGroupModel.getAliGroupNoticeId();
        SharedUtil.setString(getContext(), aliGroupNotice, aliGroupNotice);
    }

    private boolean getGroupNotice(YJIMGroupModel yjimGroupModel) {
        SharedUtil.getPreference(getContext());

        String aliGroupNotice = YJGlobal.getCustomerId() + yjimGroupModel.getAliGroupId() + yjimGroupModel.getAliGroupNoticeId();
        String notice = SharedUtil.getString(getContext(), aliGroupNotice, "");
        if (TextUtils.isEmpty(notice)) {
            return true;
        } else {
            if (aliGroupNotice.equals(notice)) {
                return false;
            } else {
                return true;
            }
        }
    }
}
