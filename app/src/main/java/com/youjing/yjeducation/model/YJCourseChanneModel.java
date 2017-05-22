package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * （以前的非记不可那四个）
 * 首页banner区下部
 * 孙腾腾
 * 创建时间：2016.9.3
 *
 * @return
 */
public class YJCourseChanneModel implements Serializable {
    private String chooseCourseButtonId;//    "chooseCourseButtonId": 2, //课程频道ID
    private String name;//            "name": "test", //课程频道名称
    private String buttonPic;//            "buttonPic": "http://file-dev.youjing.cn/img/2016/09/03/fb1d6f7e-54ff-4cee-ac95-810534872a61.png", //课程频道按钮头像
    private String remark;//            "remark": "test", //课程频道简介
    private String jumpUrl;//            "jumpUrl": "1"//课程频道按钮点击跳转地址

    public YJCourseChanneModel() {
    }

    public YJCourseChanneModel(String chooseCourseButtonId, String name, String buttonPic, String remark, String jumpUrl) {
        this.chooseCourseButtonId = chooseCourseButtonId;
        this.name = name;
        this.buttonPic = buttonPic;
        this.remark = remark;
        this.jumpUrl = jumpUrl;
    }

    public String getChooseCourseButtonId() {
        return chooseCourseButtonId;
    }

    public void setChooseCourseButtonId(String chooseCourseButtonId) {
        this.chooseCourseButtonId = chooseCourseButtonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getButtonPic() {
        return buttonPic;
    }

    public void setButtonPic(String buttonPic) {
        this.buttonPic = buttonPic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    @Override
    public String toString() {
        return "YJCourseChanneModel{" +
                "chooseCourseButtonId='" + chooseCourseButtonId + '\'' +
                ", name='" + name + '\'' +
                ", buttonPic='" + buttonPic + '\'' +
                ", remark='" + remark + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                '}';
    }
}
