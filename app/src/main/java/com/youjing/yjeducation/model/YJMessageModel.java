package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/18
 * Time 17:46
 */
public class YJMessageModel implements Serializable {
    private String announcementId;        //消息ID
    private String title;        // 消息标题
    private String type;        //消息类型
    private String isRead;//"isRead": "Yes", //消息是否阅读过 Yes:已读 No:未读
    private long createDate;        // 消息创建时间
    private String content;        // 富文本消息内容
    private String contentText;    // 消息内容

    public String getIsRead() {
        return isRead;
    }

    public void setIsRead(String isRead) {
        this.isRead = isRead;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(String announcementId) {
        this.announcementId = announcementId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "YJMessageModel{" +
                "announcementId='" + announcementId + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", isRead='" + isRead + '\'' +
                ", createDate=" + createDate +
                ", content='" + content + '\'' +
                ", contentText='" + contentText + '\'' +
                '}';
    }
}
