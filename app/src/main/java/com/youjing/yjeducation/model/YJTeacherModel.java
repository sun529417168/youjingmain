package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/11
 * Time 10:29
 */
public class YJTeacherModel implements Serializable {
    private String teacherId;
    private String nickName;
    private String trueName;
    private String teacherPic;
    private String teacherDesc;//"teacherDesc": "<p>请叫我酥饼大人</p>",
    private String description;
    private String courseCount;
    private String studentCount;
    private String isOnline;
    private String briefIntroduction;

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getTeacherPic() {
        return teacherPic;
    }

    public void setTeacherPic(String teacherPic) {
        this.teacherPic = teacherPic;
    }

    public String getTeacherDesc() {
        return teacherDesc;
    }

    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getBriefIntroduction() {
        return briefIntroduction;
    }

    public void setBriefIntroduction(String briefIntroduction) {
        this.briefIntroduction = briefIntroduction;
    }

    @Override
    public String toString() {
        return "YJTeacherModel{" +
                "teacherId='" + teacherId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", trueName='" + trueName + '\'' +
                ", teacherPic='" + teacherPic + '\'' +
                ", teacherDesc='" + teacherDesc + '\'' +
                ", description='" + description + '\'' +
                ", courseCount='" + courseCount + '\'' +
                ", studentCount='" + studentCount + '\'' +
                ", isOnline='" + isOnline + '\'' +
                ", briefIntroduction='" + briefIntroduction + '\'' +
                '}';
    }
}
