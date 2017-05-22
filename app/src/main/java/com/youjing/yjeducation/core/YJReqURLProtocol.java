package com.youjing.yjeducation.core;

import com.youjing.yjeducation.common.config.YJConfig;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 17:03
 */
public class YJReqURLProtocol {


    public static final String BANER_PICTURE = YJConfig.YJ_HTTP_ADDRESS + "/operate/getBanner.do";
    public static final String GET_VERIFICATION_CODE = YJConfig.YJ_HTTP_ADDRESS + "/customer/getSmsCode.do";
    public static final String REGISTER = YJConfig.YJ_HTTP_ADDRESS + "/customer/reg.do";
    public static final String LOGIN = YJConfig.YJ_HTTP_ADDRESS + "/customer/login.do";
    public static final String MODIFYPASSWORD = YJConfig.YJ_HTTP_ADDRESS + "/customer/modifyPassword.do";
    public static final String GET_GRADE = YJConfig.YJ_HTTP_ADDRESS + "/common/getGrade.do";
    public static final String GET_COURSETYPE = YJConfig.YJ_HTTP_ADDRESS + "/course/getCourseType.do";
    public static final String GET_COURSE_LIST = YJConfig.YJ_HTTP_ADDRESS + "/course/getCourseList.do";
    public static final String GET_COURSE_CATALOG = YJConfig.YJ_HTTP_ADDRESS + "/course/getCourseCatalog.do";
    public static final String GET_ORDER_INFO = YJConfig.YJ_HTTP_ADDRESS + "/pay/GetPayInfo.do";
    public static final String UP_LOAD_FILE = YJConfig.YJ_HTTP_ADDRESS + "/attachment/upload.do";
    public static final String GET_USER_INFO = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerInfo.do";
    public static final String GET_MY_ORDER = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerOrder.do";
    public static final String GET_MY_TEACHER = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerTeacher.do";
    public static final String SET_USER_INFO = YJConfig.YJ_HTTP_ADDRESS + "/customer/updateCustomerInfo.do";
    public static final String CREATE_ORDER = YJConfig.YJ_HTTP_ADDRESS + "/order/createOrder.do";
    public static final String WHALE_MONEY_PACKAGE = YJConfig.YJ_HTTP_ADDRESS + "/virtualCurrency/getCanBuyList.do";
    public static final String PAY_SUCESS = YJConfig.YJ_HTTP_ADDRESS + "/pay/paySuccess.do";
    public static final String GET_MESSAGE = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerNotice.do";
    public static final String GET_CONSUM_HISTORY = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerCoinLog.do";
    public static final String PAY_COURSE_WHALE_MONEY = YJConfig.YJ_HTTP_ADDRESS + "/customer/buyCourseForCoin.do";
    public static final String LOGIN_OUT = YJConfig.YJ_HTTP_ADDRESS + "/customer/outLogin.do";
    public static final String GET_CARD = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerGiftCard.do";
    public static final String GET_MY_COURSE = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerCourse.do";
    public static final String GET_USER_INDEX = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerIndexInfo.do";
    public static final String GET_COUNT = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerAboutInfo.do";// 获取我的页面的所有信息的数量
    public static final String ACTIVATE_CARD = YJConfig.YJ_HTTP_ADDRESS + "/giftCard/active.do";// 激活礼品卡
    public static final String GET_LIVE_INFO = YJConfig.YJ_HTTP_ADDRESS + "/live/getLiveInfo.do";
    public static final String GET_LIVE_BACK_INFO = YJConfig.YJ_HTTP_ADDRESS + "/live/getReplayInfo.do";
    public static final String GET_OPEN_CLASS_LIST = YJConfig.YJ_HTTP_ADDRESS + "/course/getOpenClassList.do";
    public static final String MESSAGE_BACK = YJConfig.YJ_HTTP_ADDRESS + "/common/saveHelpAndFeedback.do";// 信息反馈
    public static final String GET_AREA = YJConfig.YJ_HTTP_ADDRESS + "/common/getProvince.do";// 省市县
    public static final String GET_TEACHER_LIST = YJConfig.YJ_HTTP_ADDRESS + "/openIm/getTeacherList.do";//
    public static final String GO_TO_GROUP = YJConfig.YJ_HTTP_ADDRESS + "/openIm/gotoGroup.do";//
    public static final String SAVE_STUDENT_DATA = YJConfig.YJ_HTTP_ADDRESS + "/course/courseCatalogStudy.do";//保存学员学习行为数据（学员开始观看、结束观看、观看过程中定时保存时使用）
    public static final String GET_MY_TASK = YJConfig.YJ_HTTP_ADDRESS + "/operate/getTaskList.do";//
    public static final String GET_MEDAL_LIST = YJConfig.YJ_HTTP_ADDRESS + "/operate/getMedalList.do";//获取用户勋章的接口
    public static final String GET_LEVEL_INFO = YJConfig.YJ_HTTP_ADDRESS + "/operate/getLevelInfo.do";//获取用户等级的接口
    public static final String GET_MY_ORDER_INFO = YJConfig.YJ_HTTP_ADDRESS + "/order/getOrderInfo.do";//订单信息
    public static final String GET_TASK_REWARD = YJConfig.YJ_HTTP_ADDRESS + "/operate/getTaskReward.do";//任务奖励领取的接口
    public static final String GET_UPDATE_MESSAGE = YJConfig.YJ_HTTP_ADDRESS + "/customer/updateCustomerMsgReadRecord.do";//用户消息是否读取记录的接口
    public static final String GET_ADD_CUSTOMERACTIVE = YJConfig.YJ_HTTP_ADDRESS + "/customer/addCustomerActive.do";//统计用户活跃度的接口
    public static final String GET_REGSUCCESS = YJConfig.YJ_HTTP_ADDRESS + "/customer/regSuccess.do";//注册成功后获取鲸币数量的接口
    public static final String USER_AGREEMENT = YJConfig.YJ_HTTP_ADDRESS + "html/user-agreement.html";//用户协议页
    public static final String SHARE_SUCESS = YJConfig.YJ_HTTP_ADDRESS + "/customer/customerShare.do";//分享成功
    public static final String GET_ANDROID_VERSION = YJConfig.YJ_HTTP_ADDRESS + "/operate/getAndroidVersion.do";//版本控制
    public static final String GET_GIFT_LIST = YJConfig.YJ_HTTP_ADDRESS + "/virtualGift/getCanUseList.do";//直播间礼物列表
    public static final String SEND_GIFT = YJConfig.YJ_HTTP_ADDRESS + "/virtualGift/virtualGiftSend.do";//虚拟礼物消费
    public static final String GET_RED_INFO = YJConfig.YJ_HTTP_ADDRESS + "/customer/getRedInfo.do";//抢红包
    public static final String GET_COURSE_ISBUY = YJConfig.YJ_HTTP_ADDRESS + "/course/getCourseIsBuy.do";
    public static final String SET_DEVICE = YJConfig.YJ_HTTP_ADDRESS + "/common/setDevice.do";//注册用户设备和用户ID接口
    public static final String COURSE_SCORE = YJConfig.YJ_HTTP_ADDRESS + "/course/courseScore.do";//课程评分
    public static final String COURSE_SCORE_DETAIL = YJConfig.YJ_HTTP_ADDRESS + "/course/courseScoreInfo.do";//课程评分详情
    public static final String GET_COURSE_RECOMMEND = YJConfig.YJ_HTTP_ADDRESS + "/course/courseRecommend.do";//推荐课程列表
    public static final String GET_LIVE_RESERVATIONINFO = YJConfig.YJ_HTTP_ADDRESS + "/live/getLiveReservationInfo.do";//直播页面预约情况
    public static final String RESERVATION_LIVE_COURSE = YJConfig.YJ_HTTP_ADDRESS + "/live/liveReservation.do";//直播预约
    public static final String LIVE_BUY_COURSE = YJConfig.YJ_HTTP_ADDRESS + "/live/liveClickBuyCourse.do";//直播页面  购买课程
    public static final String GET_QUESTIONS_LIVE = YJConfig.YJ_HTTP_ADDRESS + "/questions/getQuestionsForLive.do";//直播页面答题
    public static final String GET_QUESTIONS_RESULT_LIVE = YJConfig.YJ_HTTP_ADDRESS + "/questions/getQuestionsResultForLive.do";//直播页面答题结果
    public static final String PRAISE_CLICK_LIVE = YJConfig.YJ_HTTP_ADDRESS + "/live/liveClickLike.do";//直播页面点赞
    public static final String LIVE_RANKING_STUDY = YJConfig.YJ_HTTP_ADDRESS + "/live/liveRankingForStudyTimes.do";//直播排行榜--学霸榜
    public static final String LIVE_RANKING_RICH = YJConfig.YJ_HTTP_ADDRESS + "/live/liveRankingForRich.do";//直播排行榜--富豪榜
    public static final String LIVE_RANKING_TEACHER = YJConfig.YJ_HTTP_ADDRESS + "/live/liveRankingForTeacher.do";//直播排行榜--名师榜
    public static final String GET_CUSTOMER_SIGN = YJConfig.YJ_HTTP_ADDRESS + "/customer/customerSign.do";//用户在首页点击签到（个人中心首页点击签到按钮时使用）
    public static final String GET_MY_INVITE = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerInvitedInfo.do";//邀请好友
    public static final String ACTIVE_INVITE_CODE = YJConfig.YJ_HTTP_ADDRESS + "/customer/activeInvitedCode.do";//激活邀请码
    public static final String GET_MY_COUPON = YJConfig.YJ_HTTP_ADDRESS + "/customer/getCustomerCoupon.do";//优惠卷
    public static final String GET_MY_COUPON_CANUSE = YJConfig.YJ_HTTP_ADDRESS + "/order/getPayCanUseCoupon.do";//可以使用的优惠卷
    public static final String UPDATEPASSWORD = YJConfig.YJ_HTTP_ADDRESS + "/customer/modifyPassword.do";//修改密码
    public static final String BOOK_COURSE = YJConfig.YJ_HTTP_ADDRESS + "/book/bookClick.do";//课程详情页-相关课程 点击推荐的图书时使用
    public static final String GET_CHECKMOBILE = YJConfig.YJ_HTTP_ADDRESS + "/common/checkMobile.do";//检验手机号是否注册
    public static final String GET_REGCOUNT = YJConfig.YJ_HTTP_ADDRESS + "/common/getRegCount.do";//获得注册用户数

    public static final String GET_LIVE_CHANNEL_LIST = YJConfig.YJ_HTTP_ADDRESS + "/course/getLiveChannelData.do";//直播频道
    public static final String GET_LIVE_CHANNEL_CALENDAR = YJConfig.YJ_HTTP_ADDRESS + "/course/getLiveChannelCalendarData.do";//直播频道日历
    public static final String GET_BUY_COURSE_INFO = YJConfig.YJ_HTTP_ADDRESS + "/course/getCourseInfo.do";//获得 下单页面的课程信息
    public static final String GET_All_LIVE_INFO = YJConfig.YJ_HTTP_ADDRESS + "/live/getLiveInfoForAll.do";//直播间直播信息(统一入口全量信息)



}