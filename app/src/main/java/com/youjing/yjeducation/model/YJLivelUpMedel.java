package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * 云推送升级弹框的实体类
 * 孙腾腾
 * 创建时间：2016.06.25
 *
 * @return
 */
public class YJLivelUpMedel implements Serializable {
    private String levelId;//    "levelId": "5",//等级ID
    private String level;//            "level": "5",''//等级
    private String rewardCoin;//            "rewardCoin": "100",//奖励虚拟币，存在则说明有虚拟币奖励
    private String rewardExp;//            "rewardExp": "100",//奖励经验，存在则说明有经验奖励。
    private String rewardMedal;//            "rewardMedal": "http: //www.baidu.com"//等级获得的勋章，存在则说明有勋章奖励。

    public YJLivelUpMedel() {
    }

    public YJLivelUpMedel(String levelId, String level, String rewardCoin, String rewardExp, String rewardMedal) {
        this.levelId = levelId;
        this.level = level;
        this.rewardCoin = rewardCoin;
        this.rewardExp = rewardExp;
        this.rewardMedal = rewardMedal;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
        return "YJLivelUpMedel{" +
                "levelId='" + levelId + '\'' +
                ", level='" + level + '\'' +
                ", rewardCoin='" + rewardCoin + '\'' +
                ", rewardExp='" + rewardExp + '\'' +
                ", rewardMedal='" + rewardMedal + '\'' +
                '}';
    }
}
