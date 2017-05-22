package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/23
 * Time 16:01
 */
public class YJMyRewardModel implements Serializable {
    private String rewardType; //奖励类型
    private String customerId;//用户ID
    private String reward;//奖励
    private String createDate;//奖励时间

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "YJMyRewardModel{" +
                "rewardType='" + rewardType + '\'' +
                ", customerId='" + customerId + '\'' +
                ", reward='" + reward + '\'' +
                ", createDate='" + createDate + '\'' +
                '}';
    }
}
