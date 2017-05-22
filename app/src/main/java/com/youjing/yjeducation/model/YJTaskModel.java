package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/5/10
 * Time 21:19
 */
public class YJTaskModel implements Serializable {
    private String taskId;        //
    private String taskName;        //
    private String giveXNB;        //
    private String giveEXP;        //
    private String medalId;        //
    private String remark;        //
    private String haveFinishPoint;        //
    private String allPoint;        //
    private String customerTaskStatus;        //
    private String isDoleReward;        //
    private String taskObjectivePage;        //            "taskObjectivePage": "Index_Page"//做任务的目标页 No_Page:无跳转,Current_Page:当前页,Index_Page:首页,Answer_Page:提问频道,PayGoid_Page:充值页,CustomerInfo_Page:个人信息编辑页

    public String getTaskObjectivePage() {
        return taskObjectivePage;
    }

    public void setTaskObjectivePage(String taskObjectivePage) {
        this.taskObjectivePage = taskObjectivePage;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getGiveXNB() {
        return giveXNB;
    }

    public void setGiveXNB(String giveXNB) {
        this.giveXNB = giveXNB;
    }

    public String getGiveEXP() {
        return giveEXP;
    }

    public void setGiveEXP(String giveEXP) {
        this.giveEXP = giveEXP;
    }

    public String getMedalId() {
        return medalId;
    }

    public void setMedalId(String medalId) {
        this.medalId = medalId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHaveFinishPoint() {
        return haveFinishPoint;
    }

    public void setHaveFinishPoint(String haveFinishPoint) {
        this.haveFinishPoint = haveFinishPoint;
    }

    public String getAllPoint() {
        return allPoint;
    }

    public void setAllPoint(String allPoint) {
        this.allPoint = allPoint;
    }

    public String getCustomerTaskStatus() {
        return customerTaskStatus;
    }

    public void setCustomerTaskStatus(String customerTaskStatus) {
        this.customerTaskStatus = customerTaskStatus;
    }

    public String getIsDoleReward() {
        return isDoleReward;
    }

    public void setIsDoleReward(String isDoleReward) {
        this.isDoleReward = isDoleReward;
    }

    @Override
    public String toString() {
        return "YJTaskModel{" +
                "allPoint='" + allPoint + '\'' +
                ", taskId='" + taskId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", giveXNB='" + giveXNB + '\'' +
                ", giveEXP='" + giveEXP + '\'' +
                ", medalId='" + medalId + '\'' +
                ", remark='" + remark + '\'' +
                ", haveFinishPoint='" + haveFinishPoint + '\'' +
                ", customerTaskStatus='" + customerTaskStatus + '\'' +
                ", isDoleReward='" + isDoleReward + '\'' +
                ", taskObjectivePage='" + taskObjectivePage + '\'' +
                '}';
    }
}
