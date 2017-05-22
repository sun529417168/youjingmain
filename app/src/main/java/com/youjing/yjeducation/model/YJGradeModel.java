package com.youjing.yjeducation.model;

import java.io.Serializable;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/4/9
 * Time 11:40
 */
public class YJGradeModel implements Serializable {
    private String gradeId;
    private String gradeName;
    private List<YJSubjectModel> subjectVos;

    public List<YJSubjectModel> getSubjectVos() {
        return subjectVos;
    }

    public void setSubjectVos(List<YJSubjectModel> subjectVos) {
        this.subjectVos = subjectVos;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    @Override
    public String toString() {
        return "YJGradeModel{" +
                "gradeId='" + gradeId + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", subjectVos=" + subjectVos +
                '}';
    }
}
