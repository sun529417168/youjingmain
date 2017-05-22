package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/6/14
 * Time 15:11
 */
public class YJGiftModel implements Serializable {

    private String timeLimit;
    private String virtualGiftDesc;
    private String payFee;
    private String originalPrice;
    private String name;
    private String giftPic;
    private String discountPrice;
    private String giftStatus;
    private String startTime;
    private String stopTime;
    private String virtualGiftId;

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getVirtualGiftDesc() {
        return virtualGiftDesc;
    }

    public void setVirtualGiftDesc(String virtualGiftDesc) {
        this.virtualGiftDesc = virtualGiftDesc;
    }

    public String getPayFee() {
        return payFee;
    }

    public void setPayFee(String payFee) {
        this.payFee = payFee;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGiftPic() {
        return giftPic;
    }

    public void setGiftPic(String giftPic) {
        this.giftPic = giftPic;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public String getVirtualGiftId() {
        return virtualGiftId;
    }

    public void setVirtualGiftId(String virtualGiftId) {
        this.virtualGiftId = virtualGiftId;
    }

    @Override
    public String toString() {
        return "YJGiftModel{" +
                "timeLimit='" + timeLimit + '\'' +
                ", virtualGiftDesc='" + virtualGiftDesc + '\'' +
                ", payFee='" + payFee + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", name='" + name + '\'' +
                ", giftPic='" + giftPic + '\'' +
                ", discountPrice='" + discountPrice + '\'' +
                ", giftStatus='" + giftStatus + '\'' +
                ", startTime='" + startTime + '\'' +
                ", stopTime='" + stopTime + '\'' +
                ", virtualGiftId='" + virtualGiftId + '\'' +
                '}';
    }
}
