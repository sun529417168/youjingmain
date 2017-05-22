package com.youjing.yjeducation.question;

import java.io.Serializable;
import java.util.List;

public class QuestionsData implements Serializable{
    private String paperResultId;
    private String privateEdu;
    private String paperTime;
    private String c;
    private String m;
    private String code;
    private List<QuestionsDataInfo> data;
    private List<TiXingJieShao> paperCatalogVos;

    public List<TiXingJieShao> getPaperCatalogVos() {
        return paperCatalogVos;
    }

    public void setPaperCatalogVos(List<TiXingJieShao> paperCatalogVos) {
        this.paperCatalogVos = paperCatalogVos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPaperResultId() {
        return paperResultId;
    }

    public void setPaperResultId(String paperResultId) {
        this.paperResultId = paperResultId;
    }

    public String getPrivateEdu() {
        return privateEdu;
    }

    public void setPrivateEdu(String privateEdu) {
        this.privateEdu = privateEdu;
    }

    public String getPaperTime() {
        return paperTime;
    }

    public void setPaperTime(String paperTime) {
        this.paperTime = paperTime;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public List<QuestionsDataInfo> getData() {
        return data;
    }

    public void setData(List<QuestionsDataInfo> data) {
        this.data = data;
    }
}
