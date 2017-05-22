package com.youjing.yjeducation.model;

import java.io.Serializable;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/4/23
 * Time 16:01
 */
public class YJMyInviteModel implements Serializable {
    private String isWasInvited; //是否已被邀请 Yes:是 No:否 如果已经被邀请，则用户不能再输入邀请码。（具体效果与产品定义）
    private String customerInvited;//用户的邀请码
    private List<YJMyRewardModel> invitedRewardList;//我的邀请奖励数据列表


    public String getIsWasInvited() {
        return isWasInvited;
    }

    public void setIsWasInvited(String isWasInvited) {
        this.isWasInvited = isWasInvited;
    }

    public String getCustomerInvited() {
        return customerInvited;
    }

    public void setCustomerInvited(String customerInvited) {
        this.customerInvited = customerInvited;
    }

    public List<YJMyRewardModel> getInvitedRewardList() {
        return invitedRewardList;
    }

    public void setInvitedRewardList(List<YJMyRewardModel> invitedRewardList) {
        this.invitedRewardList = invitedRewardList;
    }

    @Override
    public String toString() {
        return "YJMyInviteModel{" +
                "isWasInvited='" + isWasInvited + '\'' +
                ", customerInvited='" + customerInvited + '\'' +
                ", invitedRewardList=" + invitedRewardList +
                '}';
    }
}
