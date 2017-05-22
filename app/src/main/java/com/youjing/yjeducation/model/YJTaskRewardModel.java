package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * Created by w7 on 2016/5/14.
 */
public class YJTaskRewardModel implements Serializable {
    private String rewardCoin;//"rewardCoin": 30, //奖励虚拟币数量
    private String rewardMedal;//"rewardMedal": "", //奖励勋章
    private String rewardExp;//"rewardExp": 50//奖励经验值

    public YJTaskRewardModel() {

    }

    public YJTaskRewardModel(String rewardCoin, String rewardExp, String rewardMedal) {
        this.rewardCoin = rewardCoin;
        this.rewardExp = rewardExp;
        this.rewardMedal = rewardMedal;
    }

    public String getRewardCoin() {
        return rewardCoin;
    }

    public void setRewardCoin(String rewardCoin) {
        this.rewardCoin = rewardCoin;
    }

    public String getRewardExp() {
        return rewardExp;
    }

    public void setRewardExp(String rewardExp) {
        this.rewardExp = rewardExp;
    }

    public String getRewardMedal() {
        return rewardMedal;
    }

    public void setRewardMedal(String rewardMedal) {
        this.rewardMedal = rewardMedal;
    }

    @Override
    public String toString() {
        return "YJTaskRewardModel{" +
                "rewardCoin='" + rewardCoin + '\'' +
                ", rewardMedal='" + rewardMedal + '\'' +
                ", rewardExp='" + rewardExp + '\'' +
                '}';
    }
}
