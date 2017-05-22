package com.youjing.yjeducation.model;

import java.io.Serializable;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/4/9
 * Time 16:49
 */
public class YJCourseListModel implements Serializable {
    private String knowledgeId;
    private String name;
    private List<YJCourseModel> courseList;

    public String getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(String knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<YJCourseModel> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<YJCourseModel> courseList) {
        this.courseList = courseList;
    }

    @Override
    public String toString() {
        return "YJCourseListModel{" +
                "knowledgeId='" + knowledgeId + '\'' +
                ", name='" + name + '\'' +
                ", courseList=" + courseList +
                '}';
    }
}
