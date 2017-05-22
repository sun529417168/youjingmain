package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/23
 * Time 16:01
 */
public class YJCardModel implements Serializable {
    private String giftCardId;
    private String giftCardBatchId;
    private String cardCode;
    private String customerId;
    private String status;
    private Long useTime;
    private Long createDate;
    private Long startDate;
    private Long stopDate;
    private String price;
    private String timeLimit;


    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getStopDate() {
        return stopDate;
    }

    public void setStopDate(Long stopDate) {
        this.stopDate = stopDate;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getGiftCardId() {
        return giftCardId;
    }

    public void setGiftCardId(String giftCardId) {
        this.giftCardId = giftCardId;
    }

    public String getGiftCardBatchId() {
        return giftCardBatchId;
    }

    public void setGiftCardBatchId(String giftCardBatchId) {
        this.giftCardBatchId = giftCardBatchId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "YJCardModel{" +
                "giftCardId='" + giftCardId + '\'' +
                ", giftCardBatchId='" + giftCardBatchId + '\'' +
                ", cardCode='" + cardCode + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status='" + status + '\'' +
                ", useTime='" + useTime + '\'' +
                ", createDate='" + createDate + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
