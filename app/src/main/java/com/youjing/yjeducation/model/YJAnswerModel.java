package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/6/23
 * Time 14:28
 */
public class YJAnswerModel implements Serializable{

    private String  answerText;//答案
    private String  rightFlag;//是否正确答案 Yes：是 No：否
    private String  sort;//答案排序 按sort升序排列
    private String  answerId;//答案id

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getRightFlag() {
        return rightFlag;
    }

    public void setRightFlag(String rightFlag) {
        this.rightFlag = rightFlag;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }

    @Override
    public String toString() {
        return "YJAnswerModel{" +
                "answerText='" + answerText + '\'' +
                ", rightFlag='" + rightFlag + '\'' +
                ", sort='" + sort + '\'' +
                ", answerId='" + answerId + '\'' +
                '}';
    }
}
