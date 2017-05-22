package com.youjing.yjeducation.talkfun;

import com.talkfun.sdk.module.ChatEntity;
import com.talkfun.sdk.module.Group;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/3/10.
 */
public interface DispatchPrivateChat {
    void getPrivateChat(ChatEntity chatEntity);
    void newGroup(Group group);
    void groupDestroy(String group);
}
