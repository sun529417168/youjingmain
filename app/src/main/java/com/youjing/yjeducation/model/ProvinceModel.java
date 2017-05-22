package com.youjing.yjeducation.model;

import java.io.Serializable;
import java.util.List;

public class ProvinceModel implements Serializable {
    private String name;
    private String provinceId;

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    private List<CityModel> cityList;

    public ProvinceModel() {
        super();
    }

    @Override
    public String toString() {
        return "ProvinceModel{" +
                "name='" + name + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityList=" + cityList +
                '}';
    }

    public ProvinceModel(String name, List<CityModel> cityList) {
        super();
        this.name = name;
        this.cityList = cityList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CityModel> getCityList() {
        return cityList;
    }

    public void setCityList(List<CityModel> cityList) {
        this.cityList = cityList;
    }

}
