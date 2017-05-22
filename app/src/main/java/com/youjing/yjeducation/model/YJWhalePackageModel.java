package com.youjing.yjeducation.model;

import java.io.Serializable;

/**
 * user  秦伟宁
 * Date 2016/4/9
 * Time 11:40
 */
public class YJWhalePackageModel implements Serializable {
    private String number;//鲸币包鲸币数量
    private String virtualCurrencyId;
    private String originalPrice; //鲸币包售价
    private String discountPrice; //鲸币包售价
    private String buyCondition; //鲸币包购买条件 UNSPECIFIED：不限次数 ONETIME：只能购买一次

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getVirtualCurrencyId() {
        return virtualCurrencyId;
    }

    public void setVirtualCurrencyId(String virtualCurrencyId) {
        this.virtualCurrencyId = virtualCurrencyId;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getBuyCondition() {
        return buyCondition;
    }

    public void setBuyCondition(String buyCondition) {
        this.buyCondition = buyCondition;
    }

    @Override
    public String toString() {
        return "YJWhalePackageModel{" +
                "number='" + number + '\'' +
                ", virtualCurrencyId='" + virtualCurrencyId + '\'' +
                ", originalPrice='" + originalPrice + '\'' +
                ", discountPrice='" + discountPrice + '\'' +
                ", buyCondition='" + buyCondition + '\'' +
                '}';
    }
}
