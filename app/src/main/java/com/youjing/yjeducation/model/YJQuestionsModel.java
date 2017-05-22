package com.youjing.yjeducation.model;

import java.io.Serializable;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/6/23
 * Time 14:31
 */
public class YJQuestionsModel implements Serializable{
    private List<YJAnswerModel> answerList; //试题答案列表
    private String questionsId;//试题Id
    private String questionsTypes;//试题类型 SINGLE_CHOICE_QUESTION：单选题 MULTIPLE_CHOICE_QUESTION：多选题
    private String questionsText; //"导入题 单项选择题一"

    public List<YJAnswerModel> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<YJAnswerModel> answerList) {
        this.answerList = answerList;
    }

    public String getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(String questionsId) {
        this.questionsId = questionsId;
    }

    public String getQuestionsTypes() {
        return questionsTypes;
    }

    public void setQuestionsTypes(String questionsTypes) {
        this.questionsTypes = questionsTypes;
    }

    public String getQuestionsText() {
        return questionsText;
    }

    public void setQuestionsText(String questionsText) {
        this.questionsText = questionsText;
    }

    @Override
    public String toString() {
        return "YJQuestionsModel{" +
                "answerList=" + answerList +
                ", questionsId='" + questionsId + '\'' +
                ", questionsTypes='" + questionsTypes + '\'' +
                ", questionsText='" + questionsText + '\'' +
                '}';
    }
}
