package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * 点击签到成功后获取的实体类
 * 孙腾腾
 * 创建时间：2016.07.4
 *
 * @return
 */
public class YJCustomerSignModel implements Serializable {
    private String rewardMedal;//    "rewardMedal": 0, //签到奖励勋章
    private String rewardRemard;//            "rewardRemard": "签到1天赠送虚拟币", //签到奖励描述
    private String rewardCoin;//            "rewardCoin": 50, //签到奖励虚拟币
    private String rewardExp;//            "rewardExp": 0,//签到奖励经验
    private YJCustomer customer;//            "customer":

    public YJCustomerSignModel() {
    }

    public YJCustomerSignModel(String rewardMedal, String rewardRemard, String rewardCoin, String rewardExp, YJCustomer customer) {
        this.rewardMedal = rewardMedal;
        this.rewardRemard = rewardRemard;
        this.rewardCoin = rewardCoin;
        this.rewardExp = rewardExp;
        this.customer = customer;
    }


    public String getRewardMedal() {
        return rewardMedal;
    }

    public void setRewardMedal(String rewardMedal) {
        this.rewardMedal = rewardMedal;
    }

    public String getRewardRemard() {
        return rewardRemard;
    }

    public void setRewardRemard(String rewardRemard) {
        this.rewardRemard = rewardRemard;
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

    public YJCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(YJCustomer customer) {
        this.customer = customer;
    }


    @Override
    public String toString() {
        return "YJCustomerSignModel{" +
                "rewardMedal='" + rewardMedal + '\'' +
                ", rewardRemard='" + rewardRemard + '\'' +
                ", rewardCoin='" + rewardCoin + '\'' +
                ", rewardExp='" + rewardExp + '\'' +
                ", customer=" + customer +
                '}';
    }
}
