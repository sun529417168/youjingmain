package com.youjing.yjeducation.model;

import java.io.Serializable;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/5/10
 * Time 21:23
 */
public class YJTaskTypeModel implements Serializable {

    private String taskType;
    private String taskTypeName;
    private List<YJTaskModel> taskVoList;

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public List<YJTaskModel> getTaskVoList() {
        return taskVoList;
    }

    public void setTaskVoList(List<YJTaskModel> taskVoList) {
        this.taskVoList = taskVoList;
    }

    @Override
    public String toString() {
        return "YJTaskTypeModel{" +
                "taskType='" + taskType + '\'' +
                ", taskTypeName='" + taskTypeName + '\'' +
                ", taskVoList=" + taskVoList +
                '}';
    }
}
