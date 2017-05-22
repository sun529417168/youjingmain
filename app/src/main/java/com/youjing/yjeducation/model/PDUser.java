package com.youjing.yjeducation.model;

import java.util.Date;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
public class PDUser
{
    private String id;
    private String loginName;// 登录名
    private String password;// 密码
    private String no;        // 工号
    private String name;    // 姓名
    private String email;    // 邮箱
    private String phone;    // 电话
    private String mobile;    // 手机
    private String userType;// 用户类型
    private String loginIp;    // 最后登陆IP
    private Date   loginDate;    // 最后登陆日期
    private String loginFlag;    // 是否允许登陆
    private String photo;    // 头像
    private String oldLoginName;// 原登录名
    private String newPassword;    // 新密码
    private String qq;	// qq
    private String sex;	// 性别
    private Date birthday;	// 邮箱

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String aId)
    {
        id = aId;
    }

    public String getLoginName()
    {
        return loginName;
    }

    public void setLoginName(String aLoginName)
    {
        loginName = aLoginName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String aPassword)
    {
        password = aPassword;
    }

    public String getNo()
    {
        return no;
    }

    public void setNo(String aNo)
    {
        no = aNo;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String aName)
    {
        name = aName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String aEmail)
    {
        email = aEmail;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String aPhone)
    {
        phone = aPhone;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String aMobile)
    {
        mobile = aMobile;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String aUserType)
    {
        userType = aUserType;
    }

    public String getLoginIp()
    {
        return loginIp;
    }

    public void setLoginIp(String aLoginIp)
    {
        loginIp = aLoginIp;
    }

    public Date getLoginDate()
    {
        return loginDate;
    }

    public void setLoginDate(Date aLoginDate)
    {
        loginDate = aLoginDate;
    }

    public String getLoginFlag()
    {
        return loginFlag;
    }

    public void setLoginFlag(String aLoginFlag)
    {
        loginFlag = aLoginFlag;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String aPhoto)
    {
        photo = aPhoto;
    }

    public String getOldLoginName()
    {
        return oldLoginName;
    }

    public void setOldLoginName(String aOldLoginName)
    {
        oldLoginName = aOldLoginName;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String aNewPassword)
    {
        newPassword = aNewPassword;
    }

    public String getOldLoginIp()
    {
        return oldLoginIp;
    }

    public void setOldLoginIp(String aOldLoginIp)
    {
        oldLoginIp = aOldLoginIp;
    }

    public Date getOldLoginDate()
    {
        return oldLoginDate;
    }

    public void setOldLoginDate(Date aOldLoginDate)
    {
        oldLoginDate = aOldLoginDate;
    }

    private String oldLoginIp;    // 上次登陆IP
    private Date   oldLoginDate;    // 上次登陆日期

    @Override
    public String toString() {
        return "PDUser{" +
                "id='" + id + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", no='" + no + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userType='" + userType + '\'' +
                ", loginIp='" + loginIp + '\'' +
                ", loginDate=" + loginDate +
                ", loginFlag='" + loginFlag + '\'' +
                ", photo='" + photo + '\'' +
                ", oldLoginName='" + oldLoginName + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", qq='" + qq + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", oldLoginIp='" + oldLoginIp + '\'' +
                ", oldLoginDate=" + oldLoginDate +
                '}';
    }
}
