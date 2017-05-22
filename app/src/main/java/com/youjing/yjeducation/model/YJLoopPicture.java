package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/3/17
 * Time 11:11
 */
public class YJLoopPicture implements Serializable {
    private String name;        // 图片名
    private String pictureUrl;        // 图片链接
    private String linkUrl;        // 跳转链接
    private String content;        // 描述

    @Override
    public String toString() {
        return "PDLoopPicture{" +
                "name='" + name + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}