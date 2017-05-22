package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/16
 * Time 14:06
 */
public class YJIndexUserInfo implements Serializable {
    private String customerOverCourseCount;
    private String nickName;
    private String level;
    private String levelName;//"levelName": "哇卡卡卡", //用户等级称谓
    private String customerCourseCount;
    private String customerId;
    private String goingStudyDays;
    private String pic; //用户头像地址
    private String experience;  //用户经验值
    private String customerWeekRanking;
    private String coin;  //用户鲸币数量
    private String signDays;//    "signDays": 0, //用户连续签到天数。如果连续签到天数是0，根据UI产品确定是否显示。（本期定义为，连续签到天数为0，则不显示“已连续签到……”的话术）
    private String isSign;//            "isSign": Yes, //当天是否签到，Yes:已签到，No:未签到

    public String getSignDays() {
        return signDays;
    }

    public void setSignDays(String signDays) {
        this.signDays = signDays;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }

    public String getCustomerOverCourseCount() {
        return customerOverCourseCount;
    }

    public void setCustomerOverCourseCount(String customerOverCourseCount) {
        this.customerOverCourseCount = customerOverCourseCount;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getCustomerCourseCount() {
        return customerCourseCount;
    }

    public void setCustomerCourseCount(String customerCourseCount) {
        this.customerCourseCount = customerCourseCount;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getGoingStudyDays() {
        return goingStudyDays;
    }

    public void setGoingStudyDays(String goingStudyDays) {
        this.goingStudyDays = goingStudyDays;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCustomerWeekRanking() {
        return customerWeekRanking;
    }

    public void setCustomerWeekRanking(String customerWeekRanking) {
        this.customerWeekRanking = customerWeekRanking;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "YJIndexUserInfo{" +
                "customerOverCourseCount='" + customerOverCourseCount + '\'' +
                ", nickName='" + nickName + '\'' +
                ", level='" + level + '\'' +
                ", levelName='" + levelName + '\'' +
                ", customerCourseCount='" + customerCourseCount + '\'' +
                ", customerId='" + customerId + '\'' +
                ", goingStudyDays='" + goingStudyDays + '\'' +
                ", pic='" + pic + '\'' +
                ", experience='" + experience + '\'' +
                ", customerWeekRanking='" + customerWeekRanking + '\'' +
                ", coin='" + coin + '\'' +
                ", signDays='" + signDays + '\'' +
                ", isSign='" + isSign + '\'' +
                '}';
    }
}
