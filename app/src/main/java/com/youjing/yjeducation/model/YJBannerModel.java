package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/5/5
 * Time 10:27
 */
public class YJBannerModel implements Serializable {

    private String bannerId;        // 图片名
    private String bannerPic;        // 图片链接
    private String remark;        // 跳转链接
    private String title;        //
    private String jumpUrl;        // 描述
    private String jumpPosition;        // 描述
    private String stationJumpPosition;        // 描述
    private String stationJumpPositionWhere;        // // 跳转页面使用条件，为null或无此属性，则不关注，如果存在，则根据stationJumpPosition类型选择性使用，CourseListByCourseType：此条件是课程类型ID。

    public String getStationJumpPositionWhere() {
        return stationJumpPositionWhere;
    }

    public void setStationJumpPositionWhere(String stationJumpPositionWhere) {
        this.stationJumpPositionWhere = stationJumpPositionWhere;
    }

    public String getBannerId() {
        return bannerId;
    }

    public void setBannerId(String bannerId) {
        this.bannerId = bannerId;
    }

    public String getBannerPic() {
        return bannerPic;
    }

    public void setBannerPic(String bannerPic) {
        this.bannerPic = bannerPic;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public String getJumpPosition() {
        return jumpPosition;
    }

    public void setJumpPosition(String jumpPosition) {
        this.jumpPosition = jumpPosition;
    }

    public String getStationJumpPosition() {
        return stationJumpPosition;
    }

    public void setStationJumpPosition(String stationJumpPosition) {
        this.stationJumpPosition = stationJumpPosition;
    }

    @Override
    public String toString() {
        return "YJBannerModel{" +
                "bannerId='" + bannerId + '\'' +
                ", bannerPic='" + bannerPic + '\'' +
                ", remark='" + remark + '\'' +
                ", title='" + title + '\'' +
                ", jumpUrl='" + jumpUrl + '\'' +
                ", jumpPosition='" + jumpPosition + '\'' +
                ", stationJumpPosition='" + stationJumpPosition + '\'' +
                ", stationJumpPositionWhere='" + stationJumpPositionWhere + '\'' +
                '}';
    }
}
