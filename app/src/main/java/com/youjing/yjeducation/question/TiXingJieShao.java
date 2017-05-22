package com.youjing.yjeducation.question;

import java.io.Serializable;

public class TiXingJieShao implements Serializable {

    private int id;
    private String paperCatalogTitle;
    private String paperCatalogType;
    private int paperId;
    private int examQuestionCount;
    private double score;
    private String mask;
    private String sequence;
    private String sumQuestions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaperCatalogTitle() {
        return paperCatalogTitle;
    }

    public void setPaperCatalogTitle(String paperCatalogTitle) {
        this.paperCatalogTitle = paperCatalogTitle;
    }

    public String getPaperCatalogType() {
        return paperCatalogType;
    }

    public void setPaperCatalogType(String paperCatalogType) {
        this.paperCatalogType = paperCatalogType;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public int getExamQuestionCount() {
        return examQuestionCount;
    }

    public void setExamQuestionCount(int examQuestionCount) {
        this.examQuestionCount = examQuestionCount;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getSumQuestions() {
        return sumQuestions;
    }

    public void setSumQuestions(String sumQuestions) {
        this.sumQuestions = sumQuestions;
    }

}
