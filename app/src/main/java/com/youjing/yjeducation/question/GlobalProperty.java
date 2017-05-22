package com.youjing.yjeducation.question;

public class GlobalProperty {

    public static String _pkey = "Android";
    public static String salt = "qiming!@#";

    public final static int CITY = 205;// 是否需要选择地区
    public final static int SUCCEED = 200;// 登陆成功or发送成功
    public final static int INEXISTENCE = 201;// 用户不存在
    public final static int INCORRECTNESS = 202;// 密码不正确
    public final static int ILLEGAL = 203;// 参数违法
    public final static int ABNORMAL_SERVER = 500;// 服务器异常
    public final static int NETWORK_ANOMALY = 400;// 网络异常
    public final static int MOBILE_NOEXIST = 1;// 手机号不存在
    public final static int MOBILE_EXIST = -1;// 手机号已存在
    public final static int ACCOUNT_IS_EMPTY = -2;// 账号不能为空
    public final static int PASSWORD_IS_EMPTY = -3;// 密码不能为空
    public final static int WRONG_PHONE_NUMBER = -4;// 错误手机号码
    public final static int PHONE_VERIFICATION_CODE_ERROR = -5;// 手机验证码错误
    public final static int LENGTH_OF_VERIFICATION_CODE_ERROR = -6;// 验证码长度错误
    public final static int PHONE_VERIFICATION_CODE_IS_EMPTY = -7;// 手机验证码为空
    public final static int PASSWORD_LENGTH_ERROR = -8;// 密码长度错误
    public final static int ENTER_THE_PASSWORD_TWICE = -9;// 两次输入的密码不同
    public final static int NULL = -10;// 参数为空
    public final static int EMAIL_EXIST = -11;// 邮箱已存在
    public final static int ORIGINAL_INCORRECTNESS = -12;// 原密码输入错误
    public final static int LATEST_VERSION = -13;// 最新版本
    public final static int TEMPORARILY_VERSION = -14;// 允许暂时不升级
    public final static int MUST_VERSION = -15;// 必须升级
    public final static int OTHER_VERSION = -16;// 其他
    public final static int NONE_VERSION = -17;// 没有最新版本
    public final static int Book_INEXISTENCE = -18;// 电子书不存在

    // 题库fragment索引值
    public static final int QUESTIONBANK_FRAGMENT_INDEX = 0;
    // 视频fragment索引值
    public static final int VIDEO_FRAGMENT_INDEX = 1;
    // 直播fragment索引值
    public static final int LIVEBROADCAST_FRAGMENT_INDEX = 2;
    // 资讯fragment索引值
    public static final int INFORMATION_FRAGMENT_INDEX = 3;

    // 服务器地址
    // public static String serverIP = "http://192.168.1.102:7080/";
    // public static String serverIP = "http://192.168.1.101:6080/app-web/";
    // public static String serverIP = "http://192.168.1.101:9011/app-web/";

    // public static String serverIP = "http://192.168.1.106:66/";
    // public static String serverIP = "http://116.255.139.73:76/";
       public static String serverIP = "http://android.xueaedu.com:76/";// 线上地址
    // public static String serverIP = "http://app.qmtiku.com:76/";// 外网测试地址

    // 项目ID

    // 1 获取手机验证短信接口
    public static String phoneNumber = "customer/mobileCode.do";
    // 2 注册接口
    public static String register = "customer/register.do";
    // 修改密码接口
    public static String forgetPwd = "customer/changeCustomerPwdByMobile.do";
    // 修改密码手机验证短信接口
    public static String phoneNumberUpdate = "customer/UpdatePwdMobileCode.do";
    // 3 登陆接口
    public static String login = "customer/login.do";
    // 4 获取用户全部学科信息接口
    public static String allSubjectInformation = "common/getUserAllSubjects.do";
    // 5 获取用户某个学科信息接口
    public static String getSingleSubjectsInfo = "common/GetSingleSubjectsInfo.do";
    // 6 获取用户某个科目章节试题信息接口
    public static String getSubjectUnderChapterInfo = "common/GetSubjectUnderChapterInfo.do";
    // 7 获取科目下真题
    public static String getSubjectRealExamInfo = "exam/getSubjectRealExamInfo.do";
    // 8 付费题库信息接口
    public static String getSubjectPayExamInfo = "exam/getSubjectPayExamInfo.do";
    // 请求状态
    public static String realExam = "realExam";// 真题
    public static String payExam = "payExam";// 付费
    // 9请求试题接口
    public static String makePaper = "exam/makePaper.do";
    // 请求类型
    public final static int TYPE_SECTION = 1;// 章节练习
    public final static int TYPE_TOPIC = 2;// 历年真题
    public final static int TYPE_SCROLL = 3;// 智能组卷
    public final static int TYPE_PAY = 4;// 付费题库
    public final static int TYPE_ERROR = 5;// 我的错题
    public final static int TYPE_FLAG = 6;// 我的标记
    public final static int TYPE_CONTINUE = 7;// 继续练习
    public final static int TYPE_EXERCISE = 8;// 我的练习

    // 10 获取我的错题（章节、知识点）列表
    public static String errorExam = "exam/ErrorExam.do";
    // 11 获取我的标记（章节、知识点）列表
    public static String markExam = "exam/MarkExam.do";

    // 15 试题标记接口
    public static String markQuestions = "exam/markQuestions.do";
    // 16 试题纠错接口
    public static String correct = "exam/correct.do";
    // 17 提交试卷接口
    public static String submitPaper = "exam/submitPaper.do";
    // 18 获得试卷成绩接口
    public static String getExamResultData = "exam/getExamResultData.do";
    // 19 临时保存试卷接口
    public static String tempSavePaper = "exam/tempSavePaper.do";
    // 20 继续练习接口
    public static String continueDoExam = "exam/continueDoExam.do";
    // 21 试题分析接口
    public static String examQuestionAnalysis = "exam/examQuestionAnalysis.do";
    // 22 获得用户基本资料接口
    public static String getCustomerBaseInfo = "customer/getCustomerBaseInfo.do";
    // 23 绑定用户资料接口
    public static String bindCustomerInfo = "customer/bindCustomerInfo.do";
    // 24 错题和标记试题分析接口
    public static String errorAndMarkAnalysis = "exam/errorAndMarkAnalysis.do";
    // 25 修改密码
    public static String changeCustomerPsw = "customer/changeCustomerPsw.do";
    // 26 考试计划信息接口
    public static String examPlan = "common/examPlan.do";
    // 27 版本检测接口
    public static String checkVersion = "version/CheckVersion.do";
    public static String checkVersion2 = "version/CheckVersion2.do";

    // 29 电子书列表接口
    public static String bookList = "book/bookList.do";
    // 30 电子下载接口
    public static String downBook = "book/downBook.do";
    // 31 获得订单号接口
    public static String getProductOrder = "pay/getProductOrder.do";
    // 32 支付成功后接口
    public static String payOrderSucess = "pay/payOrderSucess.do";

    // 33 学习分析第一版接口
    public static String analysis = "exam/analysis.do";

    // 35 产品信息接口
    public static String getProductInfo = "pay/getProductInfo.do";

    // 38 移除错题接口
    public static String removeCustomerCorrect = "customer/removeCustomerCorrect.do";
    // 39 用户做题历史列表接口
    public static String myDoExamHistory = "customer/myDoExamHistory.do";
    // 40 获得用户试题类型
    public static String examQuestionType = "exam/ExamQuestionType.do";
    // 视频获取列表
    public static String courseList = "course/courseList.do";
    // 视频详细页面
    public static String courseInfo = "course/CourseInfo.do";
    // 获取所有城市
    public static String cityList = "common/getAllSubjectsCity.do";

    // 用户切换城市
    public static String cityUpdate = "customer/updatCustomerCity.do";


    //错误日志提交
    public static String log = "http://gwy.qmtiku.com/crashrpt.do";


}
