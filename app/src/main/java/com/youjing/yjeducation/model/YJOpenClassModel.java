package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/23
 * Time 16:01
 */
public class YJOpenClassModel  implements Serializable {
    private String speaker;
    private String name;
    private String startTime;
    private String endTime;
    private String planDate;
    private String planEndDate;
    private String openClassId;
    private String courseVideoId;
    private String courseCatalogId;
    private String openClassImage;
    private String courseId;
    private String liveStatus;
    private String courseName;
    private String notice;
    private String isBuy;
    private String lookStudent;
    private String reservationCount;
    private YJTeacherModel teacher;
    private String isReplay;//"isReplay": "Yes", //结束的课程是否有回放。Yes：有，No:无。（此状态，在直播状态为已结束【over】时使用。）


    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getPlanEndDate() {
        return planEndDate;
    }

    public void setPlanEndDate(String planEndDate) {
        this.planEndDate = planEndDate;
    }

    public String getOpenClassId() {
        return openClassId;
    }

    public void setOpenClassId(String openClassId) {
        this.openClassId = openClassId;
    }

    public String getCourseVideoId() {
        return courseVideoId;
    }

    public void setCourseVideoId(String courseVideoId) {
        this.courseVideoId = courseVideoId;
    }

    public String getCourseCatalogId() {
        return courseCatalogId;
    }

    public void setCourseCatalogId(String courseCatalogId) {
        this.courseCatalogId = courseCatalogId;
    }

    public String getOpenClassImage() {
        return openClassImage;
    }

    public void setOpenClassImage(String openClassImage) {
        this.openClassImage = openClassImage;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(String liveStatus) {
        this.liveStatus = liveStatus;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getIsBuy() {
        return isBuy;
    }

    public void setIsBuy(String isBuy) {
        this.isBuy = isBuy;
    }

    public String getLookStudent() {
        return lookStudent;
    }

    public void setLookStudent(String lookStudent) {
        this.lookStudent = lookStudent;
    }

    public String getReservationCount() {
        return reservationCount;
    }

    public void setReservationCount(String reservationCount) {
        this.reservationCount = reservationCount;
    }

    public YJTeacherModel getTeacher() {
        return teacher;
    }

    public void setTeacher(YJTeacherModel teacher) {
        this.teacher = teacher;
    }

    public String getIsReplay() {
        return isReplay;
    }

    public void setIsReplay(String isReplay) {
        this.isReplay = isReplay;
    }

    @Override
    public String toString() {
        return "YJOpenClassModel{" +
                "speaker='" + speaker + '\'' +
                ", name='" + name + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", planDate='" + planDate + '\'' +
                ", planEndDate='" + planEndDate + '\'' +
                ", openClassId='" + openClassId + '\'' +
                ", courseVideoId='" + courseVideoId + '\'' +
                ", courseCatalogId='" + courseCatalogId + '\'' +
                ", openClassImage='" + openClassImage + '\'' +
                ", courseId='" + courseId + '\'' +
                ", liveStatus='" + liveStatus + '\'' +
                ", courseName='" + courseName + '\'' +
                ", notice='" + notice + '\'' +
                ", isBuy='" + isBuy + '\'' +
                ", lookStudent='" + lookStudent + '\'' +
                ", reservationCount='" + reservationCount + '\'' +
                ", teacher=" + teacher +
                ", isReplay='" + isReplay + '\'' +
                '}';
    }
}
