package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/23
 * Time 16:01
 */
public class YJCouponModel implements Serializable {
    private String timeLimit;//是否有使用时间限制 Restrict:有时间显示，UnLimited：无使用时间限制
    private String couponType;//优惠券类型 Discount:折扣券 Money:现金券
    private String canUseStartTime;//可使用开始时间，如果有使用时间限制，则使用此时间
    private String canUseStopTime;//可使用结束时间，如果有使用时间限制，则使用此时间
    private String couponBatchId;//优惠券批次ID
    private String couponStatus;//优惠券状态 CanUse：可使用（含已使用、未使用）Overdue:已过期
    private String couponId;//优惠券ID
    private String couponCode;//优惠券号码
    private String isUsed;//是否使用 Yes:已使用，No:未使用
    private String activationTime;//使用时间 针对已使用的优惠券有此数据
    private String useDesc;//优惠券使用描述
    private String preferentialMoney;//现金券的面额（现金券使用）
    private String discountDegree;//折扣券的力度（折扣券使用）
    private String moneyLimit;//使用此优惠券订单需要满足的最低限额（满XXX可以使用）


    public String getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(String moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCanUseStartTime() {
        return canUseStartTime;
    }

    public void setCanUseStartTime(String canUseStartTime) {
        this.canUseStartTime = canUseStartTime;
    }

    public String getCanUseStopTime() {
        return canUseStopTime;
    }

    public void setCanUseStopTime(String canUseStopTime) {
        this.canUseStopTime = canUseStopTime;
    }

    public String getCouponBatchId() {
        return couponBatchId;
    }

    public void setCouponBatchId(String couponBatchId) {
        this.couponBatchId = couponBatchId;
    }

    public String getCouponStatus() {
        return couponStatus;
    }

    public void setCouponStatus(String couponStatus) {
        this.couponStatus = couponStatus;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(String isUsed) {
        this.isUsed = isUsed;
    }

    public String getActivationTime() {
        return activationTime;
    }

    public void setActivationTime(String activationTime) {
        this.activationTime = activationTime;
    }

    public String getUseDesc() {
        return useDesc;
    }

    public void setUseDesc(String useDesc) {
        this.useDesc = useDesc;
    }

    public String getPreferentialMoney() {
        return preferentialMoney;
    }

    public void setPreferentialMoney(String preferentialMoney) {
        this.preferentialMoney = preferentialMoney;
    }

    public String getDiscountDegree() {
        return discountDegree;
    }

    public void setDiscountDegree(String discountDegree) {
        this.discountDegree = discountDegree;
    }

    @Override
    public String toString() {
        return "YJCouponModel{" +
                "timeLimit='" + timeLimit + '\'' +
                ", couponType='" + couponType + '\'' +
                ", canUseStartTime='" + canUseStartTime + '\'' +
                ", canUserStopTime='" + canUseStopTime + '\'' +
                ", couponBatchId='" + couponBatchId + '\'' +
                ", couponStatus='" + couponStatus + '\'' +
                ", couponId='" + couponId + '\'' +
                ", couponCode='" + couponCode + '\'' +
                ", isUsed='" + isUsed + '\'' +
                ", activationTime='" + activationTime + '\'' +
                ", useDesc='" + useDesc + '\'' +
                ", preferentialMoney='" + preferentialMoney + '\'' +
                ", discountDegree='" + discountDegree + '\'' +
                ", moneyLimit='" + moneyLimit + '\'' +
                '}';
    }
}
