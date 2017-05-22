package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/6/23
 * Time 21:16
 */
public class YJAnwserResultModel implements Serializable {
    private String rightAnswer;
    private String questionsId;
    private String isRight;
    private String cusAnswer;

    public String getRightAnswer() {
        return rightAnswer;
    }

    public void setRightAnswer(String rightAnswer) {
        this.rightAnswer = rightAnswer;
    }

    public String getQuestionsId() {
        return questionsId;
    }

    public void setQuestionsId(String questionsId) {
        this.questionsId = questionsId;
    }

    public String getIsRight() {
        return isRight;
    }

    public void setIsRight(String isRight) {
        this.isRight = isRight;
    }

    public String getCusAnswer() {
        return cusAnswer;
    }

    public void setCusAnswer(String cusAnswer) {
        this.cusAnswer = cusAnswer;
    }

    @Override
    public String toString() {
        return "YJAnwserResultModel{" +
                "rightAnswer='" + rightAnswer + '\'' +
                ", questionsId='" + questionsId + '\'' +
                ", isRight='" + isRight + '\'' +
                ", cusAnswer='" + cusAnswer + '\'' +
                '}';
    }
}
