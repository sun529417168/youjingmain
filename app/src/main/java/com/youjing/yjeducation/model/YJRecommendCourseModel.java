package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * 获得新课推荐的实体类
 * 孙腾腾
 * 创建时间：2016.06.8
 *
 * @return
 */
public class YJRecommendCourseModel implements Serializable {
    private String payCoin;//            "payCoin": 10,支付金币
    private String coursePic;//            "coursePic": "http://img.bydian.cn/img/2016/05/25/a3ba85d4-0ae5-4908-8710-13460af92b11.png",
    private String teacherId;//            "teacherId": 44,
    private String payMoney;//            "payMoney": 0,支付人民币
    private String coursePayWay;//            "coursePayWay": "XNB", //付费方式 RMB：人民币 XNB：鲸币 FREE：免费
    private String originalCoin;//            "originalCoin": 10,定义的金币
    private String name;//            "name": "纯直播测试",
    private String courseShape;//            "courseShape": "LIVE",  //课程形态 DVR：点播 LIVE：直播
    private String courseId;//            "courseId": 93,
    private String originalMoney;//            "originalMoney": 0定义的人民币

    public YJRecommendCourseModel() {
    }

    public YJRecommendCourseModel(String payCoin, String coursePic, String teacherId, String payMoney, String coursePayWay, String originalCoin, String name, String courseShape, String courseId, String originalMoney) {
        this.payCoin = payCoin;
        this.coursePic = coursePic;
        this.teacherId = teacherId;
        this.payMoney = payMoney;
        this.coursePayWay = coursePayWay;
        this.originalCoin = originalCoin;
        this.name = name;
        this.courseShape = courseShape;
        this.courseId = courseId;
        this.originalMoney = originalMoney;
    }

    public String getPayCoin() {
        return payCoin;
    }

    public void setPayCoin(String payCoin) {
        this.payCoin = payCoin;
    }

    public String getCoursePic() {
        return coursePic;
    }

    public void setCoursePic(String coursePic) {
        this.coursePic = coursePic;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getCoursePayWay() {
        return coursePayWay;
    }

    public void setCoursePayWay(String coursePayWay) {
        this.coursePayWay = coursePayWay;
    }

    public String getOriginalCoin() {
        return originalCoin;
    }

    public void setOriginalCoin(String originalCoin) {
        this.originalCoin = originalCoin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseShape() {
        return courseShape;
    }

    public void setCourseShape(String courseShape) {
        this.courseShape = courseShape;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getOriginalMoney() {
        return originalMoney;
    }

    public void setOriginalMoney(String originalMoney) {
        this.originalMoney = originalMoney;
    }

    @Override
    public String toString() {
        return "YJRecommendCourseModel{" +
                "payCoin='" + payCoin + '\'' +
                ", coursePic='" + coursePic + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", coursePayWay='" + coursePayWay + '\'' +
                ", originalCoin='" + originalCoin + '\'' +
                ", name='" + name + '\'' +
                ", courseShape='" + courseShape + '\'' +
                ", courseId='" + courseId + '\'' +
                ", originalMoney='" + originalMoney + '\'' +
                '}';
    }
}
