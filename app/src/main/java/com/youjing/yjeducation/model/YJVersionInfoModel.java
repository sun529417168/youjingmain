package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/5/21
 * Time 12:10
 */
public class YJVersionInfoModel implements Serializable {

    private String versionId;//    "versionId": 4, //版本ID
    private String versionNum;//            "versionNum": "V1.0.3", //版本号
    private String address;//            "address": "fsdfsa", //版本包地址
    private String versionDesc;//            "versionDesc": "sdfsadf", //版本描述
    private String compelUpdate;//            "compelUpdate": "Yes", //是否强制更新 强制更新:Yes 不强制更新:No
    private String examineStatus;//            "examineStatus": "Yes", // 版本状态()
    private String operatePlatforms;//            "operatePlatforms": "Android"

    public YJVersionInfoModel() {
    }

    public YJVersionInfoModel(String versionId, String versionNum, String address, String versionDesc, String compelUpdate, String examineStatus, String operatePlatforms) {
        this.versionId = versionId;
        this.versionNum = versionNum;
        this.address = address;
        this.versionDesc = versionDesc;
        this.compelUpdate = compelUpdate;
        this.examineStatus = examineStatus;
        this.operatePlatforms = operatePlatforms;
    }

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(String versionNum) {
        this.versionNum = versionNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getVersionDesc() {
        return versionDesc;
    }

    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    public String getCompelUpdate() {
        return compelUpdate;
    }

    public void setCompelUpdate(String compelUpdate) {
        this.compelUpdate = compelUpdate;
    }

    public String getExamineStatus() {
        return examineStatus;
    }

    public void setExamineStatus(String examineStatus) {
        this.examineStatus = examineStatus;
    }

    public String getOperatePlatforms() {
        return operatePlatforms;
    }

    public void setOperatePlatforms(String operatePlatforms) {
        this.operatePlatforms = operatePlatforms;
    }

    @Override
    public String toString() {
        return "YJVersionInfoModel{" +
                "versionId='" + versionId + '\'' +
                ", versionNum='" + versionNum + '\'' +
                ", address='" + address + '\'' +
                ", versionDesc='" + versionDesc + '\'' +
                ", compelUpdate='" + compelUpdate + '\'' +
                ", examineStatus='" + examineStatus + '\'' +
                ", operatePlatforms='" + operatePlatforms + '\'' +
                '}';
    }
}
