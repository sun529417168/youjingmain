package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/16
 * Time 14:06
 */
public class YJUser implements Serializable {
    private String birthday; //用户生日
    private String registerIp; //用户区
    private String gradeId; //用户年级ID
    private String level;//用户级别
    private String nickName; //用户昵称
    private String sex;  //用户性别
    private String mobile; //用户手机号
    private String pic; //用户头像地址
    private String experience;  //用户经验值
    private String subjectId;  //用户科目ID
    private String customerId; //用户ID
    private String parentMobile; //用户家长手机号
    private String addressProvinceId;//省份Id
    private String addressCityId;//城市Id
    private String addressDistrictId;//地区Id
    private String qqNumber;//"qqNumber": 965899507,//用户QQ号

    public YJUser() {
    }

    public String getQqNumber() {
        return qqNumber;
    }

    public void setQqNumber(String qqNumber) {
        this.qqNumber = qqNumber;
    }

    public String getAddressProvinceId() {
        return addressProvinceId;
    }

    public void setAddressProvinceId(String addressProvinceId) {
        this.addressProvinceId = addressProvinceId;
    }

    public String getAddressCityId() {
        return addressCityId;
    }

    public void setAddressCityId(String addressCityId) {
        this.addressCityId = addressCityId;
    }

    public String getAddressDistrictId() {
        return addressDistrictId;
    }

    public void setAddressDistrictId(String addressDistrictId) {
        this.addressDistrictId = addressDistrictId;
    }

    public String getParentMobile() {
        return parentMobile;
    }

    public void setParentMobile(String parentMobile) {
        this.parentMobile = parentMobile;
    }

    private String coin;  //用户鲸币数量

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegisterIp() {
        return registerIp;
    }

    public void setRegisterIp(String registerIp) {
        this.registerIp = registerIp;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }


    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public YJUser(String birthday, String registerIp, String gradeId, String level, String nickName, String sex, String mobile, String pic, String experience, String subjectId, String customerId, String parentMobile, String addressProvinceId, String addressCityId, String addressDistrictId, String qqNumber, String coin) {
        this.birthday = birthday;
        this.registerIp = registerIp;
        this.gradeId = gradeId;
        this.level = level;
        this.nickName = nickName;
        this.sex = sex;
        this.mobile = mobile;
        this.pic = pic;
        this.experience = experience;
        this.subjectId = subjectId;
        this.customerId = customerId;
        this.parentMobile = parentMobile;
        this.addressProvinceId = addressProvinceId;
        this.addressCityId = addressCityId;
        this.addressDistrictId = addressDistrictId;
        this.qqNumber = qqNumber;
        this.coin = coin;
    }

    @Override
    public String toString() {
        return "YJUser{" +
                "birthday='" + birthday + '\'' +
                ", registerIp='" + registerIp + '\'' +
                ", gradeId='" + gradeId + '\'' +
                ", level='" + level + '\'' +
                ", nickName='" + nickName + '\'' +
                ", sex='" + sex + '\'' +
                ", mobile='" + mobile + '\'' +
                ", pic='" + pic + '\'' +
                ", experience='" + experience + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", parentMobile='" + parentMobile + '\'' +
                ", addressProvinceId='" + addressProvinceId + '\'' +
                ", addressCityId='" + addressCityId + '\'' +
                ", addressDistrictId='" + addressDistrictId + '\'' +
                ", qqNumber='" + qqNumber + '\'' +
                ", coin='" + coin + '\'' +
                '}';
    }
}
