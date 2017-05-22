package com.youjing.yjeducation.model;

import java.io.Serializable;
import java.util.List;

public class CityModel implements Serializable {
	private String name;
	private String cityId;

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	private List<DistrictModel> districtList;


	@Override
	public String toString() {
		return "CityModel{" +
				"name='" + name + '\'' +
				", cityId='" + cityId + '\'' +
				", districtList=" + districtList +
				'}';
	}

	public CityModel() {
		super();
	}

	public CityModel(String name, List<DistrictModel> districtList) {
		super();
		this.name = name;
		this.districtList = districtList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<DistrictModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DistrictModel> districtList) {
		this.districtList = districtList;
	}

}
