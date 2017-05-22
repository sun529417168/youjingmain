package com.youjing.yjeducation.question;

import android.app.Application;

public class CustomerInfo extends Application {
    public static CustomerInfo mInstance;
    private static String customerId;// 学员ID
    private static String subjectId;// 当前科目ID
    private static String categoryId ;// App 项目ID 证券从业资格：1
    // 一级建造师： 4
    private static String userName; // 员工名称
    private static String pwd;// 员工密码
    private static int  wxpay=0;// 历年真题
    private static  int wxpayByTop=0;

    public static int getWxpayByTop() {
        return wxpayByTop;
    }

    public static  void setWxpayByTop(int wxpayByTop) {
        CustomerInfo.wxpayByTop = wxpayByTop;
    }

    public static int getWxpay() {
        return wxpay;
    }

    public static void setWxpay(int wxpay) {
        CustomerInfo.wxpay = wxpay;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        CustomerInfo.userName = userName;
    }

    public static String getPwd() {
        return pwd;
    }
    public static String isPay="0";  // 历年真题是否购买
    public static String isPay_top="0";//考前押题是否购买

    public static String getIsPay_top() {
        return isPay_top;
    }

    public static void setIsPay_top(String isPay_top) {
        CustomerInfo.isPay_top = isPay_top;
    }

    public static String getIsPay() {
        return isPay;
    }

    public static void setIsPay(String isPay) {
        CustomerInfo.isPay = isPay;
    }

    public static void setPwd(String pwd) {
        CustomerInfo.pwd = pwd;
    }

    private static String categoryName;// 当前项目名
    private static String chapterId;// 当前章节Id
    private static String leafId;// 对应子节点Id
    private static String paperId;// 当前试卷ID 只有历年真题和付费题库才有
    private static String realOrPayExam;// 判断是否是历年真题或者付费题库
    private static int questionsType;// 当前试题类型
    private static String examQuestionId;// 当前试题ID
    private static String scrollTerms;// 智能组卷筛选条件
    private static String paperResultId;// 复制试卷的Id 提交试卷用
    private static boolean allErrorTopicAnalysisFlag;// 查看全部错题解析标记
    private static boolean errorTopicAnalysisFlag;// 查看错题解析标记
    private static boolean knowledgeFlag;// 查看知识点标记
    private static String showGradeType;// 显示成绩类型
    private static boolean loginMode;// 登录方式
    private static boolean finish;// 判断内容为空在什么时候不清除Activity
    private static boolean productFinishLoad;
    private static boolean handoutType;// 请求讲义类型
    private static String handoutDataType;// 存储讲义数据类型
    private static String bookId;// 讲义内容请求ID
    private static boolean isDeleteError;// 请求讲义类型

    private static String privateeduId;// privateeduId
    private static String goodsId;// 商品ID
    private static String goodsType;// 商品类型
    private static int textWidth;// 做题页面选项文本宽高
    private static int textHeight;// 做题页面选项文本宽高
    private static String courseId;// 视频列表课程ID
    private static boolean isCourse;// 是否是视频支付
    private static String VideoID;// 视频播放视频ID
    private static String UserID;// 视频播放用户ID
    private static boolean isVideoParticularsHide;
    private static String  subjectName;//科目名称

    public static String getSubjectName() {
        return subjectName;
    }

    public static void setSubjectName(String subjectName) {
        CustomerInfo.subjectName = subjectName;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        mInstance = this;
    }

    // 返回本来实例类
    public static CustomerInfo getInstance() {
        return mInstance;
    }

    public static boolean isCourse() {
        return isCourse;
    }

    public static void setCourse(boolean isCourse) {
        CustomerInfo.isCourse = isCourse;
    }

    public static String getVideoID() {
        return VideoID;
    }

    public static boolean isVideoParticularsHide() {
        return isVideoParticularsHide;
    }

    public static void setVideoParticularsHide(boolean isVideoParticularsHide) {
        CustomerInfo.isVideoParticularsHide = isVideoParticularsHide;
    }

    public static void setVideoID(String videoID) {
        VideoID = videoID;
    }

    public static String getUserID() {
        return UserID;
    }

    public static void setUserID(String userID) {
        UserID = userID;
    }

    public static String getCourseId() {
        return courseId;
    }

    public static void setCourseId(String courseId) {
        CustomerInfo.courseId = courseId;
    }

    public static String getCategoryName() {
        return categoryName;
    }

    public static boolean isProductFinishLoad() {
        return productFinishLoad;
    }

    public static void setProductFinishLoad(boolean productFinishLoad) {
        CustomerInfo.productFinishLoad = productFinishLoad;
    }

    public static void setCategoryName(String categoryName) {
        CustomerInfo.categoryName = categoryName;
    }

    public static int getTextWidth() {
        return textWidth;
    }

    public static void setTextWidth(int textWidth) {
        CustomerInfo.textWidth = textWidth;
    }

    public static int getTextHeight() {
        return textHeight;
    }

    public static void setTextHeight(int textHeight) {
        CustomerInfo.textHeight = textHeight;
    }

    public static String getPrivateeduId() {
        return privateeduId;
    }

    public static void setPrivateeduId(String privateeduId) {
        CustomerInfo.privateeduId = privateeduId;
    }

    public static String getGoodsType() {
        return goodsType;
    }

    public static void setGoodsType(String goodsType) {
        CustomerInfo.goodsType = goodsType;
    }

    public static String getGoodsId() {
        return goodsId;
    }

    public static void setGoodsId(String goodsId) {
        CustomerInfo.goodsId = goodsId;
    }

    public static boolean isDeleteError() {
        return isDeleteError;
    }

    public static void setDeleteError(boolean isDeleteError) {
        CustomerInfo.isDeleteError = isDeleteError;
    }

    public static String getLeafId() {
        return leafId;
    }

    public static void setLeafId(String leafId) {
        CustomerInfo.leafId = leafId;
    }

    public static String getHandoutDataType() {
        return handoutDataType;
    }

    public static void setHandoutDataType(String handoutDataType) {
        CustomerInfo.handoutDataType = handoutDataType;
    }

    public static CustomerInfo getmInstance() {
        return mInstance;
    }

    public static void setmInstance(CustomerInfo mInstance) {
        CustomerInfo.mInstance = mInstance;
    }

    public static String getBookId() {
        return bookId;
    }

    public static void setBookId(String bookId) {
        CustomerInfo.bookId = bookId;
    }

    public static boolean isHandoutType() {
        return handoutType;
    }

    public static void setHandoutType(boolean handoutType) {
        CustomerInfo.handoutType = handoutType;
    }

    public static boolean isFinish() {
        return finish;
    }

    public static void setFinish(boolean finish) {
        CustomerInfo.finish = finish;
    }

    public static boolean isLoginMode() {
        return loginMode;
    }

    public static void setLoginMode(boolean loginMode) {
        CustomerInfo.loginMode = loginMode;
    }

    public static String getExamQuestionId() {
        return examQuestionId;
    }

    public static void setExamQuestionId(String examQuestionId) {
        CustomerInfo.examQuestionId = examQuestionId;
    }

    public static String getScrollTerms() {
        return scrollTerms;
    }

    public static void setScrollTerms(String scrollTerms) {
        CustomerInfo.scrollTerms = scrollTerms;
    }

    public static String getShowGradeType() {
        return showGradeType;
    }

    public static void setShowGradeType(String showGradeType) {
        CustomerInfo.showGradeType = showGradeType;
    }

    public static boolean isAllErrorTopicAnalysisFlag() {
        return allErrorTopicAnalysisFlag;
    }

    public static void setAllErrorTopicAnalysisFlag(
            boolean allErrorTopicAnalysisFlag) {
        CustomerInfo.allErrorTopicAnalysisFlag = allErrorTopicAnalysisFlag;
    }

    public static boolean isKnowledgeFlag() {
        return knowledgeFlag;
    }

    public static void setKnowledgeFlag(boolean knowledgeFlag) {
        CustomerInfo.knowledgeFlag = knowledgeFlag;
    }

    public static boolean isErrorTopicAnalysisFlag() {
        return errorTopicAnalysisFlag;
    }

    public static void setErrorTopicAnalysisFlag(boolean errorTopicAnalysisFlag) {
        CustomerInfo.errorTopicAnalysisFlag = errorTopicAnalysisFlag;
    }

    public static String getPaperResultId() {
        return paperResultId;
    }

    public static void setPaperResultId(String paperResultId) {
        CustomerInfo.paperResultId = paperResultId;
    }

    public static String getRealOrPayExam() {
        return realOrPayExam;
    }

    public static void setRealOrPayExam(String realOrPayExam) {
        CustomerInfo.realOrPayExam = realOrPayExam;
    }

    public static int getQuestionsType() {
        return questionsType;
    }

    public static void setQuestionsType(int questionsType) {
        CustomerInfo.questionsType = questionsType;
    }

    public static String getPaperId() {
        return paperId;
    }

    public static void setPaperId(String paperId) {
        CustomerInfo.paperId = paperId;
    }

    public static String getChapterId() {
        return chapterId;
    }

    public static void setChapterId(String chapterId) {
        CustomerInfo.chapterId = chapterId;
    }

    public static String getCustomerId() {
        return customerId;
    }

    public static void setCustomerId(String customerId) {
        CustomerInfo.customerId = customerId;
    }

    public static String getSubjectId() {
        return subjectId;
    }

    public static void setSubjectId(String subjectId) {
        CustomerInfo.subjectId = subjectId;
    }

    public static String getCategoryId() {
        return categoryId;
    }

    public static void setCategoryId(String categoryId) {
        CustomerInfo.categoryId = categoryId;
    }
    private static String goodsIdPay;
    private static String goodsNamePay;

    public static String getGoodsIdPay() {
        return goodsIdPay;
    }

    public static void setGoodsIdPay(String goodsIdPay) {
        CustomerInfo.goodsIdPay = goodsIdPay;
    }

    public static String getGoodsNamePay() {
        return goodsNamePay;
    }

    public static void setGoodsNamePay(String goodsNamePay) {
        CustomerInfo.goodsNamePay = goodsNamePay;
    }
}
