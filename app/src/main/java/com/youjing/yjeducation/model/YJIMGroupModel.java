package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/5/7
 * Time 11:49
 */
public class YJIMGroupModel implements Serializable {
    private String aliUserPassword;
    private String aliGroupId;
    private String aliAppKey;
    private String aliUserId;
    private String aliGroupNoticeId;   //群内公告的ID
    private String aliGroupNotice;    //群内公告内容


    public String getAliUserPassword() {
        return aliUserPassword;
    }

    public void setAliUserPassword(String aliUserPassword) {
        this.aliUserPassword = aliUserPassword;
    }

    public String getAliGroupId() {
        return aliGroupId;
    }

    public void setAliGroupId(String aliGroupId) {
        this.aliGroupId = aliGroupId;
    }

    public String getAliAppKey() {
        return aliAppKey;
    }

    public void setAliAppKey(String aliAppKey) {
        this.aliAppKey = aliAppKey;
    }

    public String getAliUserId() {
        return aliUserId;
    }

    public void setAliUserId(String aliUserId) {
        this.aliUserId = aliUserId;
    }

    public String getAliGroupNoticeId() {
        return aliGroupNoticeId;
    }

    public void setAliGroupNoticeId(String aliGroupNoticeId) {
        this.aliGroupNoticeId = aliGroupNoticeId;
    }

    public String getAliGroupNotice() {
        return aliGroupNotice;
    }

    public void setAliGroupNotice(String aliGroupNotice) {
        this.aliGroupNotice = aliGroupNotice;
    }

    @Override
    public String toString() {
        return "YJIMGroupModel{" +
                "aliUserPassword='" + aliUserPassword + '\'' +
                ", aliGroupId='" + aliGroupId + '\'' +
                ", aliAppKey='" + aliAppKey + '\'' +
                ", aliUserId='" + aliUserId + '\'' +
                ", aliGroupNoticeId='" + aliGroupNoticeId + '\'' +
                ", aliGroupNotice='" + aliGroupNotice + '\'' +
                '}';
    }
}
