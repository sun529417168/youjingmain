package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * 获得直播推荐的实体类
 * 孙腾腾
 * 创建时间：2016.06.13
 *
 * @return
 */
public class YJRecommendCourseLiveModel implements Serializable {
    private String teacherId;//            "teacherId": 7,
    private YJTeacherModel teacher;//            teacher
    private String courseVideoType;// "courseVideoType": "OpenClass", //课程类型，OpenClass:公开课 其他：均为正常直播；
    private String name;// "name": "简易方程", //直播课名称
    private String lookStudent;// "lookStudent": 12, //观看学生数
    private String courseShape;//    "courseShape": LIVE, //课程类型 LIVE：直播课程 DVR：录播课程
    private String courseId;//            "courseId": 2, //课程ID
    private String courseVideoId;// "courseVideoId": 50, //视频ID
    private String planEndDate;// "planEndDate": 1465831571000,//直播计划结束时间
    private String planDate;//  "planDate": 1465779600000, //直播计划开始时间
    private String  isReplay;//  "isReplay": "Yes",//是否有回放 Yes:有回放 No:无回放 当liveStatus未over时，使用此状态判断是否有回放。其他情况不使用此状态
    private String liveStatus;// "liveStatus": "ing"//直播状态
    private String courseCatalogId;

    public String getIsReplay() {
        return isReplay;
    }

    public void setIsReplay(String isReplay) {
        this.isReplay = isReplay;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public YJTeacherModel getTeacher() {
        return teacher;
    }

    public void setTeacher(YJTeacherModel teacher) {
        this.teacher = teacher;
    }

    public String getCourseVideoType() {
        return courseVideoType;
    }

    public void setCourseVideoType(String courseVideoType) {
        this.courseVideoType = courseVideoType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLookStudent() {
        return lookStudent;
    }

    public void setLookStudent(String lookStudent) {
        this.lookStudent = lookStudent;
    }

    public String getCourseShape() {
        return courseShape;
    }

    public void setCourseShape(String courseShape) {
        this.courseShape = courseShape;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseVideoId() {
        return courseVideoId;
    }

    public void setCourseVideoId(String courseVideoId) {
        this.courseVideoId = courseVideoId;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getCourseCatalogId() {
        return courseCatalogId;
    }

    public void setCourseCatalogId(String courseCatalogId) {
        this.courseCatalogId = courseCatalogId;
    }

    @Override
    public String toString() {
        return "YJRecommendCourseLiveModel{" +
                "teacherId='" + teacherId + '\'' +
                ", teacher=" + teacher +
                ", courseVideoType='" + courseVideoType + '\'' +
                ", name='" + name + '\'' +
                ", lookStudent='" + lookStudent + '\'' +
                ", courseShape='" + courseShape + '\'' +
                ", courseId='" + courseId + '\'' +
                ", courseVideoId='" + courseVideoId + '\'' +
                ", planEndDate='" + planEndDate + '\'' +
                ", planDate='" + planDate + '\'' +
                ", liveStatus='" + liveStatus + '\'' +
                ", courseCatalogId='" + courseCatalogId + '\'' +
                '}';
    }
}
