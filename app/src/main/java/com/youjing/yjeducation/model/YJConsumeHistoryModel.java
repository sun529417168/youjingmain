package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/18
 * Time 21:28
 */
public class YJConsumeHistoryModel implements Serializable {
    private String extKey; //外部商品ID
    private String customerId;//用户ID
    private String extMsg;//鲸币流向描述
    private String coinFlow;      //鲸币流向类型
    private String customerCoinLogId;      //主键ID
    private String createDate;       //创建时间
    private String coin;       //鲸币数量
    private String coinLogType;        // 鲸币获得or消费商品类型

    public String getExtKey() {
        return extKey;
    }

    public void setExtKey(String extKey) {
        this.extKey = extKey;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getExtMsg() {
        return extMsg;
    }

    public void setExtMsg(String extMsg) {
        this.extMsg = extMsg;
    }

    public String getCoinFlow() {
        return coinFlow;
    }

    public void setCoinFlow(String coinFlow) {
        this.coinFlow = coinFlow;
    }

    public String getCustomerCoinLogId() {
        return customerCoinLogId;
    }

    public void setCustomerCoinLogId(String customerCoinLogId) {
        this.customerCoinLogId = customerCoinLogId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getCoinLogType() {
        return coinLogType;
    }

    public void setCoinLogType(String coinLogType) {
        this.coinLogType = coinLogType;
    }

    @Override
    public String toString() {
        return "YJConsumeHistoryModel{" +
                "extKey='" + extKey + '\'' +
                ", customerId='" + customerId + '\'' +
                ", extMsg='" + extMsg + '\'' +
                ", coinFlow='" + coinFlow + '\'' +
                ", customerCoinLogId='" + customerCoinLogId + '\'' +
                ", createDate='" + createDate + '\'' +
                ", coin='" + coin + '\'' +
                ", coinLogType='" + coinLogType + '\'' +
                '}';
    }
}
