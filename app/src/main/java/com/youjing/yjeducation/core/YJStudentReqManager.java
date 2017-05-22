package com.youjing.yjeducation.core;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.util.MakeSign;

import java.util.HashMap;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 17:01
 */
public class YJStudentReqManager {

    /**
     * 获得服务器数据请求
     *
     * @param map
     * @param aHandler
     */
    public static void getServerData(String url, Map<String, Object> map, boolean isLogin, TextHttpResponseHandler aHandler) {
        if (map == null) {
            map = new HashMap<String, Object>();
        }
        map.put("platform", YJConfig.PLATFORM);
        map.put("version", YJGlobal.getVersionName());
        map.put("deviceId", YJGlobal.getDeviceid());
        map.put("pkg_channel", YJGlobal.getChannel());
        if (isLogin) {
            map.put("customerToken", YJGlobal.getCustomertoken());
            map.put("customerId", YJGlobal.getCustomerId());
        }
        RequestParams params = new RequestParams();
        for (String key : map.keySet()) {
            params.put(key, map.get(key));
        }
        params.put("sign", MakeSign.makeSignNew(map));
        YJGlobal.getAsyncHttpClient().post(url, params, aHandler);

    }
/*    public void login(RequestParams prarams, TextHttpResponseHandler aHandler) {
        StringUtils.Log("登录接口", "登陆链接 = " + YJReqURLProtocol.LOGIN);
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.LOGIN, getParams(prarams), aHandler);
    }*/

 /*   public void register(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.REGISTER, getParams(prarams), aHandler);
    }*/

/*    public void getLoopPicture(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.LOOP_PICTURE, prarams, aHandler);
    }

    public void getVerificationCode(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_VERIFICATION_CODE, getParams(prarams), aHandler);
    }

    public void modifyPassword(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.MODIFYPASSWORD, getParams(prarams), aHandler);
    }

    //区分已登录和未登录
    public void getGrade(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_GRADE, getLoginParams(prarams), aHandler);
    }

    public void getGradeNoLogin(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_GRADE, getParams(prarams), aHandler);
    }


    public void getCourseType(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSETYPE, getLoginParams(prarams), aHandler);
    }

    public void getCourseTypeNoLogin(boolean isLogin, RequestParams prarams, TextHttpResponseHandler aHandler) {
        if (isLogin) {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSETYPE, getLoginParams(prarams), aHandler);
        } else {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSETYPE, getParams(prarams), aHandler);
        }
    }

    public void getCourseList(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSE_LIST, getLoginParams(prarams), aHandler);
    }

    public void getCourseListNoLogin(boolean isLogin, RequestParams prarams, TextHttpResponseHandler aHandler) {
        if (isLogin) {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSE_LIST, getLoginParams(prarams), aHandler);
        } else {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSE_LIST, getParams(prarams), aHandler);
        }
    }

    public void getOpenClassList(boolean isLogin, RequestParams prarams, TextHttpResponseHandler aHandler) {
        if (isLogin) {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_OPEN_CLASS_LIST, getLoginParams(prarams), aHandler);
        } else {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_OPEN_CLASS_LIST, getParams(prarams), aHandler);
        }
    }


    public void getCourseCatalog(boolean isLogin, RequestParams prarams, TextHttpResponseHandler aHandler) {
        if (isLogin) {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSE_CATALOG, getLoginParams(prarams), aHandler);
        } else {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSE_CATALOG, getParams(prarams), aHandler);
        }

    }

    public void getCourseCatalognoLogin(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COURSE_CATALOG, getParams(prarams), aHandler);
    }


    public void getOrderInfo(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_ORDER_INFO, getLoginParams(prarams), aHandler);
    }*/

    public void upLoadFile(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.UP_LOAD_FILE, getLoginParams(prarams), aHandler);
    }

  /*  public void getUserInfo(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_USER_INFO, getLoginParams(prarams), aHandler);
    }

    public void getMyOrder(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_MY_ORDER, getLoginParams(prarams), aHandler);
    }

    public void getMyTeacher(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_MY_ORDER, getLoginParams(prarams), aHandler);
    }

    public void setUserInfo(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.SET_USER_INFO, getLoginParams(prarams), aHandler);
    }

    public void createOrder(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.CREATE_ORDER, getLoginParams(prarams), aHandler);
    }

    public void getWhaleMoneyPackage(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.WHALE_MONEY_PACKAGE, getLoginParams(prarams), aHandler);
    }

    public void paySucess(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.PAY_SUCESS, getLoginParams(prarams), aHandler);
    }

    public void getMessage(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_MESSAGE, getLoginParams(prarams), aHandler);
    }

    public void getConsumHistory(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_CONSUM_HISTORY, getLoginParams(prarams), aHandler);
    }

    public void payCourseWhaleMoney(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.PAY_COURSE_WHALE_MONEY, getLoginParams(prarams), aHandler);
    }

    public void loginOut(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.LOGIN_OUT, getLoginParams(prarams), aHandler);
    }

    public void getMyCard(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_CARD, getLoginParams(prarams), aHandler);
    }

    public void getMyCourse(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_MY_COURSE, getLoginParams(prarams), aHandler);
    }

    public void getUserIndexInfo(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_USER_INDEX, getLoginParams(prarams), aHandler);
    }

    public void getLiveInfo(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_LIVE_INFO, getLoginParams(prarams), aHandler);
    }

    public void getLiveBackInfo(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_LIVE_BACK_INFO, getLoginParams(prarams), aHandler);
    }

    //获取我的页面的所有信息的数量
    public void getMyCount(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_COUNT, getLoginParams(prarams), aHandler);
    }

    //激活礼品卡
    public void activateCard(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.ACTIVATE_CARD, getLoginParams(prarams), aHandler);
    }

    //信息反馈
    public void messageBack(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.MESSAGE_BACK, getLoginParams(prarams), aHandler);
    }

    public void getBanner(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.BANER_PICTURE, getLoginParams(prarams), aHandler);
    }

    public void getBannerNoLogin(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.BANER_PICTURE, getParams(prarams), aHandler);
    }

    public void getArea(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_AREA, getLoginParams(prarams), aHandler);
    }

    //教师列表
    public void getTeacherList(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_TEACHER_LIST, getParams(prarams), aHandler);
    }

    public void gotoGroup(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GO_TO_GROUP, getLoginParams(prarams), aHandler);
    }

    public void saveStudentData(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.SAVE_STUDENT_DATA, getLoginParams(prarams), aHandler);
    }

    //信息反馈
    public void getMyTask(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_MY_TASK, getLoginParams(prarams), aHandler);
    }

    //获取订单信息
    public void getMyOrderInfo(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_MY_ORDER_INFO, getLoginParams(prarams), aHandler);
    }

    //分享成功调用
    public void shareSucess(boolean isLogin,RequestParams prarams, TextHttpResponseHandler aHandler) {

        if (isLogin){
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.SHARE_SUCESS, getLoginParams(prarams), aHandler);
        }else {
            YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.SHARE_SUCESS, getParams(prarams), aHandler);
        }

    }
    //版本控制
    public void getAndroidVersion(RequestParams prarams, TextHttpResponseHandler aHandler) {

        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_ANDROID_VERSION, getParams(prarams), aHandler);
    }

    *//**
     * stt
     * 获取等级勋章
     * 2016.5.13
     *
     * @param prarams
     * @param aHandler
     *//*
    public void getMedal(RequestParams prarams, TextHttpResponseHandler aHandler) {
        StringUtils.Log("获取迅勋章的接口", "获取勋章接口 = " + YJReqURLProtocol.GET_MEDAL_LIST);
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_MEDAL_LIST, getParams(prarams), aHandler);
    }

    *//**
     * stt
     * 任务奖励领取
     * 时间：2016.5.14
     *
     * @param prarams
     * @param aHandler
     *//*
    public void getTaskReward(RequestParams prarams, TextHttpResponseHandler aHandler) {
        StringUtils.Log("获取迅勋章的接口", "获取勋章接口 = " + YJReqURLProtocol.GET_TASK_REWARD);
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_TASK_REWARD, getParams(prarams), aHandler);
    }

    *//**
     * stt
     * 个人等级信息
     * 时间：2016.5.14
     *
     * @param prarams
     * @param aHandler
     *//*
    public void getLevelInfo(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_LEVEL_INFO, getParams(prarams), aHandler);
    }

    *//**
     * stt
     * 修改用户消息读取记录
     * 时间：2016.5.15
     *
     * @param prarams
     * @param aHandler
     *//*
    public void getUpdateMessge(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_UPDATE_MESSAGE, getParams(prarams), aHandler);
    }

    *//**
     * stt
     * 统计用户活跃度
     * 时间：2016.5.16
     *
     * @param prarams
     * @param aHandler
     *//*
    public void getAddCustomerActive(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_ADD_CUSTOMERACTIVE, getParams(prarams), aHandler);
    }

    */

    /**
     * stt
     * 注册成功后获取鲸币的数量
     * 时间：2016.5.16
     *
     * @param prarams
     * @param
     *//*
    public void getRegsuccess(RequestParams prarams, TextHttpResponseHandler aHandler) {
        YJGlobal.getAsyncHttpClient().post(YJReqURLProtocol.GET_REGSUCCESS, getParams(prarams), aHandler);
    }*/
    protected RequestParams getParams(RequestParams prarams) {
        prarams.put("platform", YJConfig.PLATFORM);
        prarams.put("deviceId", YJGlobal.getDeviceid());
        prarams.put("version", YJGlobal.getVersionName());
        return prarams;
    }

    protected RequestParams getLoginParams(RequestParams prarams) {
        prarams.put("platform", YJConfig.PLATFORM);
        prarams.put("deviceId", YJGlobal.getDeviceid());
        prarams.put("version", YJGlobal.getVersionName());
        prarams.put("customerToken", YJGlobal.getCustomertoken());
        prarams.put("customerId", YJGlobal.getCustomerId());
        return prarams;
    }
}
