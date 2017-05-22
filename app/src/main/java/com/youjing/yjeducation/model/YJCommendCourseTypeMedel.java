package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * 课程类型的实体类（首页上部分图书类型展示列表数据，此列表存在，有多少展示多少，此列表不存在，则不展示此模块。）
 * 孙腾腾
 * 创建时间：2016.7.1
 *
 * @return
 */
public class YJCommendCourseTypeMedel implements Serializable {
    private String name;//    "name": "期末考试",
    private String courseTypeId;//            "courseTypeId": 1,
    private String smallPic;//            "smallPic": "http://img.bydian.cn/bydian/2016/03/17/6fc6e67e-5296-487a-b5ba-97ba447fcba1.jpg", //课程类型小图，此图片在此模块展示
    private String pic;//            "pic": "http://img.bydian.cn/bydian/2016/03/17/da4ae296-c318-491d-86cb-a24538eacf9e.png",//课程类型标准图,此图片在进入列表页时顶部展示此图片
    private String isGroup;//            "isGroup": "No",//是否按知识点分组展示
    private String mark;//            "mark": "这是课程类型1的说明"

    public YJCommendCourseTypeMedel() {
    }

    public YJCommendCourseTypeMedel(String name, String courseTypeId, String smallPic, String pic, String isGroup, String mark) {
        this.name = name;
        this.courseTypeId = courseTypeId;
        this.smallPic = smallPic;
        this.pic = pic;
        this.isGroup = isGroup;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseTypeId() {
        return courseTypeId;
    }

    public void setCourseTypeId(String courseTypeId) {
        this.courseTypeId = courseTypeId;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "YJCommendCourseTypeMedel{" +
                "name='" + name + '\'' +
                ", courseTypeId='" + courseTypeId + '\'' +
                ", smallPic='" + smallPic + '\'' +
                ", pic='" + pic + '\'' +
                ", isGroup='" + isGroup + '\'' +
                ", mark='" + mark + '\'' +
                '}';
    }
}
