package com.youjing.yjeducation.model;

import java.io.Serializable;

public class DistrictModel implements Serializable {
    private String name;
    private String districtId;

    @Override
    public String toString() {
        return "DistrictModel{" +
                "name='" + name + '\'' +
                ", districtId='" + districtId + '\'' +
                '}';
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public DistrictModel() {
        super();
    }

    public DistrictModel(String name, String districtId) {
        super();
        this.name = name;
        this.districtId = districtId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
