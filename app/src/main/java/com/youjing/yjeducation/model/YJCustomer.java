package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * 点击签到成功后获取的实体类
 * 孙腾腾
 * 创建时间：2016.07.4
 *
 * @return
 */
public class YJCustomer implements Serializable {
    private String level;//        "level": 6,
    private String nickName;//                "nickName": "改过来",
    private String mobile;//                "mobile": "18301156575",
    private String weekRanking;//                "weekRanking": 4,
    private String levelName;//                "levelName": "哇卡卡卡",
    private String pic;//                "pic": "http://img.bydian.cn/bydian/2016/05/13/8ddcc49b-e83e-452e-9fe4-66d46d99a3a8.png",
    private String experience;//                "experience": 2070,
    private String allCount;//                "allCount": 376,
    private String isSign;//                "isSign": "Yes",
    private String customerWeekRanking;//                "customerWeekRanking": 99,
    private String signDays;//                "signDays": 7,
    private String customerOverCourseCount;//                "customerOverCourseCount": 0,
    private String customerCourseCount;//                "customerCourseCount": 13,
    private String customerId;//                "customerId": 10,
    private String coin;//                "coin": 1216,
    private String goingStudyDays;//                "goingStudyDays": 12

    public YJCustomer() {
    }

    public YJCustomer(String level, String nickName, String mobile, String weekRanking, String levelName, String pic, String experience, String allCount, String isSign, String customerWeekRanking, String signDays, String customerOverCourseCount, String customerCourseCount, String customerId, String coin, String goingStudyDays) {
        this.level = level;
        this.nickName = nickName;
        this.mobile = mobile;
        this.weekRanking = weekRanking;
        this.levelName = levelName;
        this.pic = pic;
        this.experience = experience;
        this.allCount = allCount;
        this.isSign = isSign;
        this.customerWeekRanking = customerWeekRanking;
        this.signDays = signDays;
        this.customerOverCourseCount = customerOverCourseCount;
        this.customerCourseCount = customerCourseCount;
        this.customerId = customerId;
        this.coin = coin;
        this.goingStudyDays = goingStudyDays;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWeekRanking() {
        return weekRanking;
    }

    public void setWeekRanking(String weekRanking) {
        this.weekRanking = weekRanking;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
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

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }

    public String getCustomerWeekRanking() {
        return customerWeekRanking;
    }

    public void setCustomerWeekRanking(String customerWeekRanking) {
        this.customerWeekRanking = customerWeekRanking;
    }

    public String getSignDays() {
        return signDays;
    }

    public void setSignDays(String signDays) {
        this.signDays = signDays;
    }

    public String getCustomerOverCourseCount() {
        return customerOverCourseCount;
    }

    public void setCustomerOverCourseCount(String customerOverCourseCount) {
        this.customerOverCourseCount = customerOverCourseCount;
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

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getGoingStudyDays() {
        return goingStudyDays;
    }

    public void setGoingStudyDays(String goingStudyDays) {
        this.goingStudyDays = goingStudyDays;
    }

    @Override
    public String toString() {
        return "YJCustomer{" +
                "level='" + level + '\'' +
                ", nickName='" + nickName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", weekRanking='" + weekRanking + '\'' +
                ", levelName='" + levelName + '\'' +
                ", pic='" + pic + '\'' +
                ", experience='" + experience + '\'' +
                ", allCount='" + allCount + '\'' +
                ", isSign='" + isSign + '\'' +
                ", customerWeekRanking='" + customerWeekRanking + '\'' +
                ", signDays='" + signDays + '\'' +
                ", customerOverCourseCount='" + customerOverCourseCount + '\'' +
                ", customerCourseCount='" + customerCourseCount + '\'' +
                ", customerId='" + customerId + '\'' +
                ", coin='" + coin + '\'' +
                ", goingStudyDays='" + goingStudyDays + '\'' +
                '}';
    }
}
