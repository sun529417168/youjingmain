package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/23
 * Time 17:10
 */
public class YJMyCourseModel implements Serializable {
    private String lastStudyTime;//    "lastStudyTime": 0, //上次学习章节的时间数 单位：秒
    private String trueName;//            "trueName": "姜舒", //老师真实名称
    private String beginDate;//            "beginDate": "2016-04-12", //服务开始时间
    private String courseServiceStatus;//            "courseServiceStatus": "OVER", //服务状态
    private String endDate;//            "endDate": "2016-04-19", //服务结束时间
    private String courseId;//            "courseId": "1", //课程ID
    private String courseShape;//            "courseShape": "DVR", //课程类型
    private String coursePic;//            "coursePic": "http://www.baidu.com", //课程图片
    private String nickName;//            "nickName": "酥饼大人", //老师昵称
    private String name;//            "name": "77878", //课程名称
    private String courseStatus;//            "courseStatus": "SELLING",//课程服务状态 SELLING:在售,其他:不在售
    private String lastNumber;//            "lastNumber": 0,//上次学习章节数
    private String studyScheduler;//            "studyScheduler": 20, //学习进度，此数据是根据总进度100，后台系统折算后的数据，直接使用。
    private String studyStatus;//            "studyStatus": "Study_No"//学习状态 Study_No：未学习,Study_Ing:学习中，Study_Over:已学完
    private String courseRanking;//            "courseRanking": "4"//本课排名 如果是0或者没有,则显示"暂无"
    private String lookCount;//            "lookCount": "5"//观看次数 后台已处理,直接显示该值
    private String lookAllTime;//            "lookAllTime": "1000"//观看总时长 单位秒

    public YJMyCourseModel() {
    }

    public YJMyCourseModel(String beginDate, String courseId, String coursePic, String courseRanking, String courseServiceStatus, String courseShape, String courseStatus, String endDate, String lastNumber, String lastStudyTime, String lookAllTime, String lookCount, String name, String nickName, String studyScheduler, String studyStatus, String trueName) {
        this.beginDate = beginDate;
        this.courseId = courseId;
        this.coursePic = coursePic;
        this.courseRanking = courseRanking;
        this.courseServiceStatus = courseServiceStatus;
        this.courseShape = courseShape;
        this.courseStatus = courseStatus;
        this.endDate = endDate;
        this.lastNumber = lastNumber;
        this.lastStudyTime = lastStudyTime;
        this.lookAllTime = lookAllTime;
        this.lookCount = lookCount;
        this.name = name;
        this.nickName = nickName;
        this.studyScheduler = studyScheduler;
        this.studyStatus = studyStatus;
        this.trueName = trueName;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public String getCourseRanking() {
        return courseRanking;
    }

    public void setCourseRanking(String courseRanking) {
        this.courseRanking = courseRanking;
    }

    public String getCourseServiceStatus() {
        return courseServiceStatus;
    }

    public void setCourseServiceStatus(String courseServiceStatus) {
        this.courseServiceStatus = courseServiceStatus;
    }

    public String getCourseShape() {
        return courseShape;
    }

    public void setCourseShape(String courseShape) {
        this.courseShape = courseShape;
    }

    public String getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getLastNumber() {
        return lastNumber;
    }

    public void setLastNumber(String lastNumber) {
        this.lastNumber = lastNumber;
    }

    public String getLastStudyTime() {
        return lastStudyTime;
    }

    public void setLastStudyTime(String lastStudyTime) {
        this.lastStudyTime = lastStudyTime;
    }

    public String getLookAllTime() {
        return lookAllTime;
    }

    public void setLookAllTime(String lookAllTime) {
        this.lookAllTime = lookAllTime;
    }

    public String getLookCount() {
        return lookCount;
    }

    public void setLookCount(String lookCount) {
        this.lookCount = lookCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getStudyScheduler() {
        return studyScheduler;
    }

    public void setStudyScheduler(String studyScheduler) {
        this.studyScheduler = studyScheduler;
    }

    public String getStudyStatus() {
        return studyStatus;
    }

    public void setStudyStatus(String studyStatus) {
        this.studyStatus = studyStatus;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    @Override
    public String toString() {
        return "YJMyCourseModel{" +
                "beginDate='" + beginDate + '\'' +
                ", lastStudyTime='" + lastStudyTime + '\'' +
                ", trueName='" + trueName + '\'' +
                ", courseServiceStatus='" + courseServiceStatus + '\'' +
                ", endDate='" + endDate + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseShape='" + courseShape + '\'' +
                ", coursePic='" + coursePic + '\'' +
                ", nickName='" + nickName + '\'' +
                ", name='" + name + '\'' +
                ", courseStatus='" + courseStatus + '\'' +
                ", lastNumber='" + lastNumber + '\'' +
                ", studyScheduler='" + studyScheduler + '\'' +
                ", studyStatus='" + studyStatus + '\'' +
                ", courseRanking='" + courseRanking + '\'' +
                ", lookCount='" + lookCount + '\'' +
                ", lookAllTime='" + lookAllTime + '\'' +
                '}';
    }
}
