package com.youjing.yjeducation.core;

import com.alibaba.mobileim.YWIMKit;
import com.loopj.android.http.AsyncHttpClient;
import com.youjing.yjeducation.model.YJBannerModel;
import com.youjing.yjeducation.model.YJCount;
import com.youjing.yjeducation.model.YJCouponModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJCourseTypeModel;
import com.youjing.yjeducation.model.YJCourseTypesBaseMedel;
import com.youjing.yjeducation.model.YJGiftModel;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.model.YJIMGroupModel;
import com.youjing.yjeducation.model.YJIndexUserInfo;
import com.youjing.yjeducation.model.YJMyCourseModel;
import com.youjing.yjeducation.model.YJOpenClassModel;
import com.youjing.yjeducation.model.YJRecommendCourseLiveModel;
import com.youjing.yjeducation.model.YJRecommendCourseModel;
import com.youjing.yjeducation.model.YJTeacherAskModel;
import com.youjing.yjeducation.model.YJUser;

import org.vwork.mobile.ui.AVActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
public class YJGlobal {
    private static AsyncHttpClient ASYNC_HTTPCLIENT;
    private static YJStudentReqManager STUDENT_REQ_MANAGER;
    private static String DEVICEID;
    private static String VERSIONNAME;
    private static List LOOP_PICTURES;
    private static AVActivity AVACTIVITY;
    private static String CUSTOMERTOKEN;
    private static int CUSTOMERID;
    private static List<YJGradeModel> GRADE_LIST;
    private static String MY_GRADE;
    private static String MY_GRADE_ID;
    private static String MY_SUBJECT;
    private static String MY_SUBJECTID;
    private static List<YJCourseTypeModel> YJCOURSETYPEMODELLIST;
    private static List<YJRecommendCourseModel> YJRECOMMENDCOURSELIST;
    private static List<YJRecommendCourseLiveModel> YJRECOMMENDCOURSELIVELIST;
    private static List<YJCourseTypesBaseMedel> YJCOURSETYPESBASEMEDEL;
    private static YJUser YJUSER;
    private static YJCount YJCOUNT;
    private static YJIndexUserInfo YJINDEXUSERINFO;
    private static YJCourseModel YJCOURSEMODEL;
    private static List<YJMyCourseModel> YJMYCOURSEMODELLIST;
    private static List<YJOpenClassModel> YJOPENCLASSMODELLIST;
    private static YWIMKit MIMKIT;
    private static List<YJBannerModel> YJBANNERMODELLIST;
    private static List<YJTeacherAskModel> YJTEACHERASKMODELLIST;
    private static boolean NOTICEISNEW;
    private static YJIMGroupModel YJIMGROUPMODEL;
    private static String SUCESSINFO;
    private static List<YJGiftModel> YJGIFTMODELLIST;
    private static String NOWTIME;
    private static String CHANNEL = "";

    public static String getChannel() {
        return CHANNEL;
    }

    public static void setChannel(String channel) {
        YJGlobal.CHANNEL = channel;
    }

    public static String getNowTime() {
        return NOWTIME;
    }

    public static void setNowTime(String nowTime) {
        YJGlobal.NOWTIME = nowTime;
    }

    public static List<YJGiftModel> getYjGiftModelList() {
        return YJGIFTMODELLIST;
    }

    public static void setYjGiftModelList(List<YJGiftModel> yjGiftModelList) {
        YJGlobal.YJGIFTMODELLIST = yjGiftModelList;
    }

    public static String getSucessInfo() {
        return SUCESSINFO;
    }

    public static void setSucessInfo(String sucessInfo) {
        YJGlobal.SUCESSINFO = sucessInfo;
    }

    public static YJIMGroupModel getYjimGroupModel() {
        return YJIMGROUPMODEL;
    }

    public static void setYjimGroupModel(YJIMGroupModel yjimGroupModel) {
        YJGlobal.YJIMGROUPMODEL = yjimGroupModel;
    }

    public static boolean getNoticeIsNew() {
        return NOTICEISNEW;
    }

    public static void setNoticeIsNew(boolean noticeIsNew) {
        YJGlobal.NOTICEISNEW = noticeIsNew;
    }

    public static List<YJTeacherAskModel> getYjTeacherAskModelList() {
        return YJTEACHERASKMODELLIST;
    }

    public static void setYjTeacherAskModelList(List<YJTeacherAskModel> yjTeacherAskModelList) {
        YJGlobal.YJTEACHERASKMODELLIST = yjTeacherAskModelList;
    }

    public static List<YJBannerModel> getYjBannerModelList() {
        return YJBANNERMODELLIST;
    }

    public static void setYjBannerModelList(List<YJBannerModel> yjBannerModelList) {
        YJGlobal.YJBANNERMODELLIST = yjBannerModelList;
    }

    public static YWIMKit getmIMKit() {
        return MIMKIT;
    }

    public static void setmIMKit(YWIMKit mIMKit) {
        YJGlobal.MIMKIT = mIMKit;
    }

    public static List<YJOpenClassModel> getYjOpenClassModelList() {
        return YJOPENCLASSMODELLIST;
    }

    public static void setYjOpenClassModelList(List<YJOpenClassModel> yjOpenClassModelList) {
        YJGlobal.YJOPENCLASSMODELLIST = yjOpenClassModelList;
    }

    public static YJIndexUserInfo getYjIndexUserInfo() {
        return YJINDEXUSERINFO;
    }

    public static void setYjIndexUserInfo(YJIndexUserInfo yjIndexUserInfo) {
        YJGlobal.YJINDEXUSERINFO = yjIndexUserInfo;
    }

    public static YJCourseModel getYjCourseModel() {
        return YJCOURSEMODEL;
    }

    public static void setYjCourseModel(YJCourseModel yjCourseModel) {
        YJGlobal.YJCOURSEMODEL = yjCourseModel;
    }

    public static YJUser getYjUser() {
        return YJUSER;
    }

    public static YJCount getYjCount() {
        return YJCOUNT;
    }

    public static void setYjUser(YJUser yjUser) {
        YJGlobal.YJUSER = yjUser;
    }

    public static void setYjCount(YJCount yjCount) {
        YJGlobal.YJCOUNT = yjCount;
    }

    public static List<YJCourseTypeModel> getYjCourseTypeModelList() {
        return YJCOURSETYPEMODELLIST;
    }

    public static void setYjCourseTypeModelList(List<YJCourseTypeModel> yjCourseTypeModelList) {
        YJGlobal.YJCOURSETYPEMODELLIST = yjCourseTypeModelList;
    }

    public static List<YJRecommendCourseModel> getYjRecommendCourseModel() {
        return YJRECOMMENDCOURSELIST;
    }

    public static void setYjRecommendCourseModelListlList(List<YJRecommendCourseModel> yjRecommendCourseModelList) {
        YJGlobal.YJRECOMMENDCOURSELIST = yjRecommendCourseModelList;
    }

    public static String getMy_grade() {
        return MY_GRADE;
    }

    public static void setMy_grade(String my_grade) {
        YJGlobal.MY_GRADE = my_grade;
    }

    public static String getMy_grade_id() {
        return MY_GRADE_ID;
    }

    public static void setMy_grade_id(String my_grade_id) {
        YJGlobal.MY_GRADE_ID = my_grade_id;
    }

    public static String getMy_subject() {
        return MY_SUBJECT;
    }

    public static void setMy_subject(String my_subject) {
        YJGlobal.MY_SUBJECT = my_subject;
    }

    public static String getMy_subjectId() {
        return MY_SUBJECTID;
    }

    public static void setMy_subjectId(String my_subjectId) {
        YJGlobal.MY_SUBJECTID = my_subjectId;
    }


    public static List<YJGradeModel> getGradeList() {
        return GRADE_LIST;
    }

    public static void setGradeList(List<YJGradeModel> gradeList) {
        GRADE_LIST = gradeList;
    }

    public static int getCustomerId() {
        return CUSTOMERID;
    }

    public static void setCustomerId(int customerId) {
        YJGlobal.CUSTOMERID = customerId;
    }

    public static String getCustomertoken() {
        return CUSTOMERTOKEN;
    }

    public static void setCustomertoken(String customertoken) {
        YJGlobal.CUSTOMERTOKEN = customertoken;
    }

    public static AVActivity getAVActivity() {
        return AVACTIVITY;
    }

    public static void setAVActivity(AVActivity avActivity) {
        YJGlobal.AVACTIVITY = avActivity;
    }


    public static String getDeviceid() {
        return DEVICEID;
    }

    public static void setDeviceid(String deviceid) {
        YJGlobal.DEVICEID = deviceid;
    }

    public static String getVersionName() {
        return VERSIONNAME;
    }

    public static void setVersionName(String VERSIONNAME) {
        YJGlobal.VERSIONNAME = VERSIONNAME;
    }

    public static AsyncHttpClient getAsyncHttpClient() {
        if (ASYNC_HTTPCLIENT == null) {
            ASYNC_HTTPCLIENT = new AsyncHttpClient();
        }
        return ASYNC_HTTPCLIENT;
    }

    public static YJStudentReqManager getStudentReqManager() {
        if (STUDENT_REQ_MANAGER == null) {
            STUDENT_REQ_MANAGER = new YJStudentReqManager();
        }
        return STUDENT_REQ_MANAGER;
    }

    public static void setLoopPictures(List loopPictures) {
        LOOP_PICTURES = loopPictures;
    }

    public static List getLoopPictures() {
        return LOOP_PICTURES;
    }

    public static List<YJCourseTypesBaseMedel> getYJCOURSETYPESBASEMEDEL() {
        return YJCOURSETYPESBASEMEDEL;
    }

    public static void setYJCOURSETYPESBASEMEDEL(List<YJCourseTypesBaseMedel> YJCOURSETYPESBASEMEDEL) {
        YJGlobal.YJCOURSETYPESBASEMEDEL = YJCOURSETYPESBASEMEDEL;
    }
}