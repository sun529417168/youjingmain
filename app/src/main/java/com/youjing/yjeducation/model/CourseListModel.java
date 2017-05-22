package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * 课程类型下的列表
 * 孙腾腾
 * 创建时间：2016.7.2
 *
 * @return
 */
public class CourseListModel implements Serializable {
    private String payCoin;//    "payCoin": 2000, //支付鲸币
    private String coursePic;// "coursePic": "http://file.youjing.cn/img/2016/06/12/78d12935-55de-4d42-bfc4-c43f26c0a6d3.png", //课程图片
    private String teacherId;//"teacherId": 7, //老师ID
    private String payMoney;//"payMoney": 0, //支付价钱
    private String coursePayWay;//"coursePayWay": "XNB", //课程支付方式 XNB：金币 RMB：人民币 FREE：免费
    private String originalCoin;//"originalCoin": 2000, //定义鲸币价
    private String name;//"name": "测试原生课程一", //课程名称
    private String courseShape;//"courseShape": "DVR", //课程类型 DVR：点播课 LIVE：直播课
    private String isNew;//"isNew": "No", //是否是新课
    private String courseId;//"courseId": 96, //课程ID
    private String originalMoney;//"originalMoney": 0//定义人民币价格

    public CourseListModel() {
    }

    public CourseListModel(String payCoin, String coursePic, String teacherId, String payMoney, String coursePayWay, String originalCoin, String name, String courseShape, String isNew, String courseId, String originalMoney) {
        this.payCoin = payCoin;
        this.coursePic = coursePic;
        this.teacherId = teacherId;
        this.payMoney = payMoney;
        this.coursePayWay = coursePayWay;
        this.originalCoin = originalCoin;
        this.name = name;
        this.courseShape = courseShape;
        this.isNew = isNew;
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

    public String getIsNew() {
        return isNew;
    }

    public void setIsNew(String isNew) {
        this.isNew = isNew;
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
        return "CourseListModel{" +
                "payCoin='" + payCoin + '\'' +
                ", coursePic='" + coursePic + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", coursePayWay='" + coursePayWay + '\'' +
                ", originalCoin='" + originalCoin + '\'' +
                ", name='" + name + '\'' +
                ", courseShape='" + courseShape + '\'' +
                ", isNew='" + isNew + '\'' +
                ", courseId='" + courseId + '\'' +
                ", originalMoney='" + originalMoney + '\'' +
                '}';
    }
}
