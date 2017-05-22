package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * 云推送获得勋章弹框的实体类
 * 孙腾腾
 * 创建时间：2016.06.25
 *
 * @return
 */
public class YJMedalGetMedel implements Serializable {
    private String medalId;//    "medalId": "5",//勋章ID
    private String medalName;//            "medalName": "5",//勋章名称
    private String medalPic;//            "medalPic": "http: //www.baidu.com"//勋章图片地址

    public YJMedalGetMedel() {
    }

    public YJMedalGetMedel(String medalId, String medalName, String medalPic) {
        this.medalId = medalId;
        this.medalName = medalName;
        this.medalPic = medalPic;
    }

    public String getMedalId() {
        return medalId;
    }

    public void setMedalId(String medalId) {
        this.medalId = medalId;
    }

    public String getMedalName() {
        return medalName;
    }

    public void setMedalName(String medalName) {
        this.medalName = medalName;
    }

    public String getMedalPic() {
        return medalPic;
    }

    public void setMedalPic(String medalPic) {
        this.medalPic = medalPic;
    }

    @Override
    public String toString() {
        return "YJMedalGetMedel{" +
                "medalId='" + medalId + '\'' +
                ", medalName='" + medalName + '\'' +
                ", medalPic='" + medalPic + '\'' +
                '}';
    }
}
