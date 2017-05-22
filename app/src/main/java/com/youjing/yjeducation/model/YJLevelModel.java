package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * Created by w7 on 2016/5/13.
 */
public class YJLevelModel implements Serializable {
    private String currentLevel;//        "currentLevel": 6, //用户当前等级
    private String currentLevelName;//                "currentLevelName": "哇卡卡卡", //用户当前等级称谓
    private String beforelevelName;//                "beforelevelName": "水手长", //上一等级称谓
    private String currentExp;//                "currentExp": 1570, //用户当前经验值
    private String nextLevelName;//                "nextLevelName": "三副", //下一等级称谓
    private String beforeExp;//                "beforeExp": 1000, //上一等级所需经验值
    private String nextLevel;//                "nextLevel": 7, //下一等级级别
    private String nextExp;//                "nextExp": 3000, //下一等级所需经验值
    private String beforeLevel;//                "beforeLevel": 5//上一等级级别
    private String currentLevelExp;//                "currentLevelExp": 1500, //当前等级所需经验值

    public YJLevelModel() {
    }

    public YJLevelModel(String beforeExp, String beforeLevel, String beforelevelName, String currentExp, String currentLevel, String currentLevelExp, String currentLevelName, String nextExp, String nextLevel, String nextLevelName) {
        this.beforeExp = beforeExp;
        this.beforeLevel = beforeLevel;
        this.beforelevelName = beforelevelName;
        this.currentExp = currentExp;
        this.currentLevel = currentLevel;
        this.currentLevelExp = currentLevelExp;
        this.currentLevelName = currentLevelName;
        this.nextExp = nextExp;
        this.nextLevel = nextLevel;
        this.nextLevelName = nextLevelName;
    }

    public String getBeforeExp() {
        return beforeExp;
    }

    public void setBeforeExp(String beforeExp) {
        this.beforeExp = beforeExp;
    }

    public String getBeforeLevel() {
        return beforeLevel;
    }

    public void setBeforeLevel(String beforeLevel) {
        this.beforeLevel = beforeLevel;
    }

    public String getBeforelevelName() {
        return beforelevelName;
    }

    public void setBeforelevelName(String beforelevelName) {
        this.beforelevelName = beforelevelName;
    }

    public String getCurrentExp() {
        return currentExp;
    }

    public void setCurrentExp(String currentExp) {
        this.currentExp = currentExp;
    }

    public String getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(String currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getCurrentLevelExp() {
        return currentLevelExp;
    }

    public void setCurrentLevelExp(String currentLevelExp) {
        this.currentLevelExp = currentLevelExp;
    }

    public String getCurrentLevelName() {
        return currentLevelName;
    }

    public void setCurrentLevelName(String currentLevelName) {
        this.currentLevelName = currentLevelName;
    }

    public String getNextExp() {
        return nextExp;
    }

    public void setNextExp(String nextExp) {
        this.nextExp = nextExp;
    }

    public String getNextLevel() {
        return nextLevel;
    }

    public void setNextLevel(String nextLevel) {
        this.nextLevel = nextLevel;
    }

    public String getNextLevelName() {
        return nextLevelName;
    }

    public void setNextLevelName(String nextLevelName) {
        this.nextLevelName = nextLevelName;
    }

    @Override
    public String toString() {
        return "YJLevelModel{" +
                "beforeExp='" + beforeExp + '\'' +
                ", currentLevel='" + currentLevel + '\'' +
                ", currentLevelName='" + currentLevelName + '\'' +
                ", beforelevelName='" + beforelevelName + '\'' +
                ", currentExp='" + currentExp + '\'' +
                ", nextLevelName='" + nextLevelName + '\'' +
                ", nextLevel='" + nextLevel + '\'' +
                ", nextExp='" + nextExp + '\'' +
                ", beforeLevel='" + beforeLevel + '\'' +
                ", currentLevelExp='" + currentLevelExp + '\'' +
                '}';
    }
}
