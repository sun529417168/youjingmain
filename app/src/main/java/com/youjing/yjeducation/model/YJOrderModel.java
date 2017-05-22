package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/16
 * Time 15:31
 */
public class YJOrderModel implements Serializable {

    private String trueName; //老师真名
    private String orderNumber; //订单编号
    private String payDateTime; //支付时间
    private String payMoney; //订单支付金额
    private String teacherId; //老师ID
    private String orderMoney; //订单金额
    private String orderId; //订单ID
    private String orderRecordType; //订单记录类型
    private String goodsId; //商品ID
    private String nickName; //老师昵称
    private String orderStatus; //订单状态
    private String goodsName; //订单商品名称
    private String isNew ;
    private String coursePic ; //商品图片
    private String goodsOriginalMoney ; //商品定义的价格
    private String goodsPayMoney ; //商品定义的支付价格
    private long createDateTime; //订单创建时间
    private long courseTypeId; //订单创建时间
    private YJCouponModel orderUsedCoupon;

    public YJCouponModel getOrderUsedCoupon() {
        return orderUsedCoupon;
    }

    public void setOrderUsedCoupon(YJCouponModel orderUsedCoupon) {
        this.orderUsedCoupon = orderUsedCoupon;
    }

    public String getGoodsOriginalMoney() {
        return goodsOriginalMoney;
    }

    public void setGoodsOriginalMoney(String goodsOriginalMoney) {
        this.goodsOriginalMoney = goodsOriginalMoney;
    }

    public String getGoodsPayMoney() {
        return goodsPayMoney;
    }

    public void setGoodsPayMoney(String goodsPayMoney) {
        this.goodsPayMoney = goodsPayMoney;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPayDateTime() {
        return payDateTime;
    }

    public void setPayDateTime(String payDateTime) {
        this.payDateTime = payDateTime;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderRecordType() {
        return orderRecordType;
    }

    public void setOrderRecordType(String orderRecordType) {
        this.orderRecordType = orderRecordType;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public long getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(long createDateTime) {
        this.createDateTime = createDateTime;
    }

    public long getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(long courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    @Override
    public String toString() {
        return "YJOrderModel{" +
                "trueName='" + trueName + '\'' +
                ", orderNumber='" + orderNumber + '\'' +
                ", payDateTime='" + payDateTime + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", orderMoney='" + orderMoney + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderRecordType='" + orderRecordType + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", isNew='" + isNew + '\'' +
                ", coursePic='" + coursePic + '\'' +
                ", goodsOriginalMoney='" + goodsOriginalMoney + '\'' +
                ", goodsPayMoney='" + goodsPayMoney + '\'' +
                ", createDateTime=" + createDateTime +
                ", courseTypeId=" + courseTypeId +
                ", orderUsedCoupon=" + orderUsedCoupon +
                '}';
    }
}
