package com.youjing.yjeducation.model;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * user  秦伟宁
 * Date 2016/4/9
 * Time 15:31
 */
public class YJCourseTypeModel implements Serializable {

    private String name;
    private String courseTypeId;
    private String pic;
    private String mark;
    private String isGroup;
    private String smallPic;//"smallPic": "http://img.bydian.cn/bydian/2016/03/17/6fc6e67e-5296-487a-b5ba-97ba447fcba1.jpg", //课程类型小图

    private ArrayList<CourseListModel> courseList;//课程类型下的列表，数据存在，有多少展示多少

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public ArrayList<CourseListModel> getCourseList() {
        return courseList;
    }

    public void setCourseList(ArrayList<CourseListModel> courseList) {
        this.courseList = courseList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    @Override
    public String toString() {
        return "YJCourseTypeModel{" +
                "name='" + name + '\'' +
                ", courseTypeId='" + courseTypeId + '\'' +
                ", pic='" + pic + '\'' +
                ", mark='" + mark + '\'' +
                ", isGroup='" + isGroup + '\'' +
                ", smallPic='" + smallPic + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
