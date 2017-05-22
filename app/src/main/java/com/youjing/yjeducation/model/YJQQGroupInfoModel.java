package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/6/22
 * Time 10:39
 */
public class YJQQGroupInfoModel implements Serializable {

    private  String QQGroupNumber;
    private  String Android_key;

    public String getQQGroupNumber() {
        return QQGroupNumber;
    }

    public void setQQGroupNumber(String QQGroupNumber) {
        this.QQGroupNumber = QQGroupNumber;
    }

    public String getAndroid_key() {
        return Android_key;
    }

    public void setAndroid_key(String android_key) {
        Android_key = android_key;
    }

    @Override
    public String toString() {
        return "YJQQGroupInfoModel{" +
                "QQGroupNumber='" + QQGroupNumber + '\'' +
                ", Android_key='" + Android_key + '\'' +
                '}';
    }
}
