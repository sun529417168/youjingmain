package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * Created by w7 on 2016/5/12.
 */
public class YJMedalModel  implements Serializable {
private String isGet;//            "isGet": "Yes", //学员是否拥有 拥有：Yes 未拥有：No
private String isDoleReward;//            "isDoleReward": "No",//获得的勋章是否提示过
private String name;//            "name": "学习达人", //勋章名称
private String remark;//            "remark": "sadfasfasdf", //勋章描述
private String iconUrl;//            "iconUrl": "http://www.baidu.com", private String isGet;//勋章图片
private String notGetIcon;//            "notGetIcon": "http://www.baidu.com", private String isGet;//勋章图片(未获得时图片)
private String ownNumber;//            "ownNumber": 1, //勋章拥有人数
private String medalId;//            "medalId": 1 //勋章id

    public String getIsGet() {
        return isGet;
    }

    public void setIsGet(String isGet) {
        this.isGet = isGet;
    }

    public String getIsDoleReward() {
        return isDoleReward;
    }

    public void setIsDoleReward(String isDoleReward) {
        this.isDoleReward = isDoleReward;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getNotGetIcon() {
        return notGetIcon;
    }

    public void setNotGetIcon(String notGetIcon) {
        this.notGetIcon = notGetIcon;
    }

    public String getOwnNumber() {
        return ownNumber;
    }

    public void setOwnNumber(String ownNumber) {
        this.ownNumber = ownNumber;
    }

    public String getMedalId() {
        return medalId;
    }

    public void setMedalId(String medalId) {
        this.medalId = medalId;
    }

    @Override
    public String toString() {
        return "YJMedalModel{" +
                "isGet='" + isGet + '\'' +
                ", isDoleReward='" + isDoleReward + '\'' +
                ", name='" + name + '\'' +
                ", remark='" + remark + '\'' +
                ", iconUrl='" + iconUrl + '\'' +
                ", notGetIcon='" + notGetIcon + '\'' +
                ", ownNumber='" + ownNumber + '\'' +
                ", medalId='" + medalId + '\'' +
                '}';
    }
}
