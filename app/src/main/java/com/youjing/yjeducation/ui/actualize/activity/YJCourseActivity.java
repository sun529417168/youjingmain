package com.youjing.yjeducation.ui.actualize.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.model.YJBannerModel;
import com.youjing.yjeducation.model.YJCommendCourseTypeMedel;
import com.youjing.yjeducation.model.YJCourseCatalogModel;
import com.youjing.yjeducation.model.YJCourseChanneModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJCourseTypeModel;
import com.youjing.yjeducation.model.YJCourseTypesBaseMedel;
import com.youjing.yjeducation.model.YJCustomerSignModel;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.model.YJIndexUserInfo;
import com.youjing.yjeducation.model.YJRecommendCourseLiveModel;
import com.youjing.yjeducation.model.YJRecommendCourseModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.actualize.dialog.YJSignInDialog;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCourseActivity;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.MyRequestUtils;
import com.youjing.yjeducation.util.ScanBannerUtils;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.wiget.ImageCycleView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.utils.base.VParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:55
 */
public class YJCourseActivity extends AYJCourseActivity {
    private String TAG = "YJCourseActivity";
    private List<YJBannerModel> yjBannerModelList;
    private boolean isCoutseType = false;

    private ArrayList<String> urlList;
    private ArrayList<String> linkList;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    notifyListener(YJNotifyTag.MY_TASK, "");
                    break;
                }

                default:
                    break;
            }
        }
    };

    @Override
    protected void initAllData() {
        getBanner();
    }

    @Override
    protected void initViewPager() {
        urlList = new ArrayList<>();
        linkList = new ArrayList<>();
        //  yjBannerModelList = YJGlobal.getYjBannerModelList();
        if (yjBannerModelList != null) {
            for (YJBannerModel yjBannerModel : yjBannerModelList) {
                urlList.add(yjBannerModel.getBannerPic());
                linkList.add(yjBannerModel.getJumpUrl());

                initCarsuelView(urlList, linkList);
            }
        } else {
            getBanner();
        }

    }

    @Override
    public void getCourseType(String gradeId, String subjectId) {
        getALLCourseType(gradeId, subjectId, true);
    }

    //获取banner接口
    protected void getBanner() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.BANER_PICTURE, null, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getBannerNoLogin失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功getBannerNoLogin=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjBannerModelList = JSON.parseArray(jsonData.getString("bannerList"), YJBannerModel.class);
                            YJGlobal.setYjBannerModelList(yjBannerModelList);
                            getGrade();
                            break;
                        case 500:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 2).set(YJLoginActivity.MY_LOGIN_OUT, false)));
                            showToast("自动登录失败");
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

    //获得年级接口
    private void getGrade() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_GRADE, null, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getGradeLogin失败=" + s);
                showToast(getContext().getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功getGradeLogin=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjGradeModelList = JSON.parseArray(jsonData.getString("grades"), YJGradeModel.class);
                            YJGlobal.setGradeList(yjGradeModelList);

                            mGrade = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("grade", yjGradeModelList.get(0).getGradeName());
                            mSubject = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("subject", yjGradeModelList.get(0).getSubjectVos().get(0).getSubjectName());
                            if (isFirst) {
                                if (yjGradeModelList != null) {
                                    YJGlobal.setMy_grade(YJGlobal.getGradeList().get(0).getGradeName());
                                    YJGlobal.setMy_subject(YJGlobal.getGradeList().get(0).getSubjectVos().get(0).getSubjectName());
                                    getALLCourseType(YJGlobal.getGradeList().get(0).getGradeId(), YJGlobal.getGradeList().get(0).getSubjectVos().get(0).getSubjectId(),false);
                                }
                            } else {
                                if (yjGradeModelList != null) {
                                    if (mGradeNum >= yjGradeModelList.size()) {
                                        getALLCourseType(yjGradeModelList.get(0).getGradeId(), yjGradeModelList.get(0).getSubjectVos().get(0).getSubjectId(),false);
                                    } else if (mSubjectNum >= yjGradeModelList.get(mGradeNum).getSubjectVos().size()) {
                                        getALLCourseType(yjGradeModelList.get(0).getGradeId(), yjGradeModelList.get(0).getSubjectVos().get(0).getSubjectId(),false);
                                    } else {
                                        getALLCourseType(yjGradeModelList.get(mGradeNum).getGradeId(), yjGradeModelList.get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), false);
                                    }
                                }
                            }
                            break;
                        case 300:
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

    //所有课程数据
    private void getALLCourseType(final String gradeId, final String subjectId,final boolean isOnlyInitCourseType) {

        StringUtils.Log(TAG, "gradeId=" + gradeId + "subjectId=" + subjectId);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", subjectId);
            objectMap.put("gradeId", gradeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSETYPE, objectMap, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getALLCourseType失败=" + s);
                showToast(getString(R.string.no_net_work));
                YJGlobal.setMy_subjectId(subjectId);
                YJGlobal.setMy_grade_id(gradeId);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "getALLCourseType成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjCourseTypeModelList = JSON.parseArray(jsonData.getString("courseTypes"), YJCourseTypeModel.class);
                            yjRecommendCourseModelList = JSON.parseArray(jsonData.getString("recommendCourseList"), YJRecommendCourseModel.class);
                            yjRecommendCourseLiveModelList = JSON.parseArray(jsonData.getString("recommendCourseLiveList"), YJRecommendCourseLiveModel.class);
                            yjCourseChanneModel = JSON.parseArray(jsonData.getString("courseChannelList"), YJCourseChanneModel.class);
                            if (yjCourseChanneModel.size() == 3) {
                                YJCourseChanneModel commendCourseTypeMedel = new YJCourseChanneModel();
                                commendCourseTypeMedel.setName("更多");
                                yjCourseChanneModel.add(commendCourseTypeMedel);
                            }
                            yjCourseTypesBaseMedel = JSON.parseArray(jsonData.getString("courseTypesBase"), YJCourseTypesBaseMedel.class);
                            YJGlobal.setYjCourseTypeModelList(yjCourseTypeModelList);
                            YJGlobal.setYjRecommendCourseModelListlList(yjRecommendCourseModelList);
                            YJGlobal.setYJCOURSETYPESBASEMEDEL(yjCourseTypesBaseMedel);
                            YJGlobal.setMy_subjectId(subjectId);
                            YJGlobal.setMy_grade_id(gradeId);
                            notifyListener(YJNotifyTag.NEW_COURSE_RECOMMENDED, yjRecommendCourseModelList);
                            notifyListener(YJNotifyTag.COURSE_TYPE, yjCourseTypeModelList);
                            notifyListener(YJNotifyTag.LIVE_COURSE_RECOMMENDED, yjRecommendCourseLiveModelList);
                            notifyListener(YJNotifyTag.COURSE_TYPES, yjCourseChanneModel);
                            if (!isOnlyInitCourseType) {
                                if (mIsLogin) {
                                    getUserInfo();
                                    StringUtils.Log(TAG, "getUserInfo");
                                } else {
                                    initUI();
                                    StringUtils.Log(TAG, "initUI");
                                }
                            }
                            break;
                        case 300:
                            break;
                        case 400:
                            StringUtils.Log(TAG, "400");
                            YJGlobal.setYjCourseTypeModelList(null);
                            notifyListener(YJNotifyTag.COURSE_TYPE, null);
                            notifyListener(YJNotifyTag.NEW_COURSE_RECOMMENDED, null);
                            notifyListener(YJNotifyTag.LIVE_COURSE_RECOMMENDED, null);
                            notifyListener(YJNotifyTag.COURSE_TYPES, null);
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

    //个人信息接口
    private void getUserInfo() {

        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getUserInfo失败=" + s);
                showToast(getContext().getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功getUserInfo=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            mYjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                            StringUtils.Log(TAG, "yjUser.toString()=" + mYjUser.toString());
                            YJGlobal.setYjUser(mYjUser);
                            getUserIndexInfo(true);
                            break;
                        case 300:
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

    private void getUserIndexInfo(final boolean isInitUI) {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INDEX, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                showToast(getString(R.string.no_net_work));
                StringUtils.Log(TAG, "getUserIndexInfo失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "getUserIndexInfo成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjIndexUserInfo = JSON.parseObject(jsonData.getString("customer"), YJIndexUserInfo.class);
                            StringUtils.Log(TAG, "yjIndexUserInfo.toString()=" + yjIndexUserInfo.toString());
                            YJGlobal.setYjIndexUserInfo(yjIndexUserInfo);
                            if (isInitUI) {
                                initUI();
                            } else {
                                if (yjIndexUserInfo != null) {
                                    if (!TextUtils.isEmpty(yjIndexUserInfo.getGoingStudyDays())) {
                                        mTxt_lern.setText(yjIndexUserInfo.getGoingStudyDays());
                                    } else {
                                        mTxt_lern.setText("0");
                                    }
                                    if (!TextUtils.isEmpty(yjIndexUserInfo.getCustomerCourseCount()) && !TextUtils.isEmpty(yjIndexUserInfo.getCustomerOverCourseCount())) {
                                        mTxt_have_buy_num.setText(yjIndexUserInfo.getCustomerOverCourseCount() + "/" + yjIndexUserInfo.getCustomerCourseCount());
                                    } else {
                                        mTxt_have_buy_num.setText(0 + "/" + 0);
                                    }
                                    if (!TextUtils.isEmpty(yjIndexUserInfo.getCustomerWeekRanking())) {
                                        if (yjIndexUserInfo.getCustomerWeekRanking().equals("0")) {
                                            mTxt_over_step.setText("暂无");
                                        } else {
                                            mTxt_over_step.setText(yjIndexUserInfo.getCustomerWeekRanking() + "%");
                                        }
                                    } else {
                                        mTxt_over_step.setText("暂无");
                                    }

                                    if (!TextUtils.isEmpty(yjIndexUserInfo.getLevel())) {
                                        mCourse_list_head_leave_text.setText(yjIndexUserInfo.getLevel());
                                    } else {
                                        mCourse_list_head_leave_text.setText("1");
                                    }
                                    if (!TextUtils.isEmpty(yjIndexUserInfo.getNickName())) {
                                        mNickName.setText(yjIndexUserInfo.getNickName());
                                    }
                                    if (!TextUtils.isEmpty(yjIndexUserInfo.getPic())) {
                                        StringUtils.Log(TAG, "pic=" + yjIndexUserInfo.getPic());
                                        BitmapUtils.create(getContext()).display(mImg_user_photo, yjIndexUserInfo.getPic());
                                    }
                                    signinSetData(yjIndexUserInfo);
                                }
                            }
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

    /**
     * 初始化轮播图
     */
    private void initCarsuelView(ArrayList<String> urlList, final ArrayList<String> linkList) {
        LinearLayout.LayoutParams cParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mCycleView.setLayoutParams(cParams);
        ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {
            @Override
            public void onImageClick(int position, View imageView) {
                if (StringUtils.isUrl(yjBannerModelList.get(position).getJumpUrl()) == false) {
                    showToast("无法识别的地址");
                    return;
                }
                StringUtils.Log("banner链接地址", yjBannerModelList.get(position).getJumpUrl());
                ScanBannerUtils.intentPage((YJBaseActivity) getActivity(), yjBannerModelList.get(position).getJumpUrl(), TAG, mIsLogin, 0);
            }

            @Override
            public void displayImage(String imageURL, ImageView imageView) {
                BitmapUtils.create(getContext()).display(imageView, imageURL);
            }
        };
        /**设置数据*/
        //mCycleView.setImageResources(imageDescList,urlList, mAdCycleViewListener);
        mCycleView.setImageResources(urlList, mAdCycleViewListener);
        mCycleView.startImageCycle();
    }

    /**
     * 得到屏幕的高度
     *
     * @param context
     * @return
     */
    private static int getScreenHeight(Context context) {
        if (null == context) {
            return 0;
        }
        DisplayMetrics dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    @Override
    public void getMyCourse() {
        startActivity(YJMyCourseActivity.class);
    }

    @Override
    public void getIndexInfo() {
        getUserIndexInfo(false);
    }


    /**
     * 首页直播推荐正在直播
     *
     * @param position
     */
    @Override
    public void getTakenLive(final int position, final boolean isShowDialog, final int isLiveType) {
        ScanBannerUtils.getLiveInfo(this, yjRecommendCourseLiveModelList.get(position).getCourseId(), "course", yjRecommendCourseLiveModelList.get(position).getCourseCatalogId(), isShowDialog, isLiveType);
        beginStudy(position);
    }

    /**
     * 首页直播已结束
     *
     * @param position
     */
    @Override
    public void getTakenBack(final int position) {
        ScanBannerUtils.getTakenBack(this, yjRecommendCourseLiveModelList.get(position).getCourseVideoId(), yjRecommendCourseLiveModelList.get(position).getTeacher().getTeacherId());
        beginStudy(position);
    }

    private void beginStudy(int position){
        YJCourseCatalogModel yjCourseCatalogModel = new YJCourseCatalogModel();
        yjCourseCatalogModel.setCourseCatalogId(yjRecommendCourseLiveModelList.get(position).getCourseCatalogId());
        yjCourseCatalogModel.setCourseVideoId(yjRecommendCourseLiveModelList.get(position).getCourseVideoId());

        List<YJCourseCatalogModel> yjCourseCatalogModelList = new ArrayList<YJCourseCatalogModel>();
        yjCourseCatalogModelList.add(yjCourseCatalogModel);

        YJCourseModel yjCourseModel = new YJCourseModel();
        yjCourseModel.setCourseId(yjRecommendCourseLiveModelList.get(position).getCourseId());
        yjCourseModel.setCourseCatalogList(yjCourseCatalogModelList);

        YJUserStudyData.liveCatalogBeginStudy(yjCourseModel, 0);
    }

    /**
     * 首页直播推荐判断这个用户是否购买课程
     *
     * @param position
     */
    @Override
    public void getisBuy(final int position) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseId", yjRecommendCourseLiveModelList.get(position).getCourseId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_ISBUY, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            String isBuy = jsonData.getString("isBuy");
                            switch (isBuy) {
                                case "true":

                                    if (yjRecommendCourseLiveModelList.get(position).getLiveStatus().equals("ing")) {
                                        //   progDialog = DialogUtil.showWaitDialog(getContext());//加载数据的loading
                                        getTakenLive(position, false, 1);

                                    }
                                    if (yjRecommendCourseLiveModelList.get(position).getLiveStatus().equals("over")) {
                                        //  progDialog = DialogUtil.showWaitDialog(getContext());//加载数据的loading
                                        if ("Yes".equals(yjRecommendCourseLiveModelList.get(position).getIsReplay())) {
                                            getTakenBack(position);
                                        }
                                        if ("No".equals(yjRecommendCourseLiveModelList.get(position).getIsReplay())) {
                                            getTakenLive(position, true, 2);
                                        }

                                    }
                                    if (yjRecommendCourseLiveModelList.get(position).getLiveStatus().equals("no")) {
                                        getTakenLive(position, true, 1);
                                    }
                                    break;
                                case "false":
                                    if (ClickUtil.isFastDoubleClick()) {
                                        return;
                                    }
                                    Intent in = new Intent(getContext(), YJCoursePlayNewActivity.class);
                                    in.putExtra("courseId", yjRecommendCourseLiveModelList.get(position).getCourseId());
                                    startActivity(in);
                                    break;
                            }
                            break;
                        case 300:
                          /*  if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                                progDialog.dismiss();
                            }*/
                            showToast("参数不合法");
                            break;
                        case 500:
                          /*  if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                                progDialog.dismiss();
                            }*/
                            showToast("服务器异常");
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
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


    /**
     * 课程首页用户签到的接口
     */
    @Override
    public void getSign() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_CUSTOMER_SIGN, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            YJCustomerSignModel customerSignModel = JSON.parseObject(json.getString("data"), YJCustomerSignModel.class);
                            mCourse_list_head_level.setBackgroundResource(R.mipmap.signin_background_yes);
                            mCourse_list_head_sign.setText("已签到");
                            mCourse_list_head_signData.setVisibility(View.VISIBLE);
                            mCourse_list_head_signData.setText("已连续" + customerSignModel.getCustomer().getSignDays() + "天");
                            MyRequestUtils.getUserInfo(YJCourseActivity.this, true);
                            YJSignInDialog yjSignInDialog = new YJSignInDialog();
                            VParams data = createTransmitData(yjSignInDialog.SIGNINDATANUM, customerSignModel.getCustomer().getSignDays()).set(yjSignInDialog.SIGNINWHALENUM, customerSignModel.getRewardCoin());
                            showDialog(yjSignInDialog, data);

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
