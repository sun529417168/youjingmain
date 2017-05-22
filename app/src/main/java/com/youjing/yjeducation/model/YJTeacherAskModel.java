package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/5/5
 * Time 18:05
 */
public class YJTeacherAskModel implements Serializable {

    private String trueName;//老师真名
    private String studentCount;//老师学生数
    private String teacherId; //老师ID
    private String nickName;//老师昵称
    private String teacherPic;//老师简介
    private String courseCount;//z课程数
    private String teacherDesc;//老师头像
    private String isOnline;//老师是否在线 Yes:在线 No:不在线
    private String groupId;

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(String studentCount) {
        this.studentCount = studentCount;
    }

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

    public String getTeacherPic() {
        return teacherPic;
    }

    public void setTeacherPic(String teacherPic) {
        this.teacherPic = teacherPic;
    }

    public String getCourseCount() {
        return courseCount;
    }

    public void setCourseCount(String courseCount) {
        this.courseCount = courseCount;
    }

    public String getTeacherDesc() {
        return teacherDesc;
    }

    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "YJTeacherAskModel{" +
                "trueName='" + trueName + '\'' +
                ", studentCount='" + studentCount + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", teacherPic='" + teacherPic + '\'' +
                ", courseCount='" + courseCount + '\'' +
                ", teacherDesc='" + teacherDesc + '\'' +
                ", isOnline='" + isOnline + '\'' +
                ", groupId='" + groupId + '\'' +
                '}';
    }
}
