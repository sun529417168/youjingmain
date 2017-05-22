package com.youjing.yjeducation.ui.dispaly.dialog;

import com.youjing.yjeducation.core.YJNotifyTag;

/**
 * user  秦伟宁
 * Date 2016/4/15
 * Time 13:54
 */
public class YJSetUserPhotoDialog extends AYJSetUserPhotoDialog {
    public static final int PHOTO_CAMERA = 0;
    public static final int PHOTO_FILE = 1;

    @Override
    protected void onTakePhotoClick() {
        notifyListener(YJNotifyTag.USER_PHOTO, PHOTO_CAMERA);
        close();
    }

    @Override
    protected void onSelectPhotoClick() {
        notifyListener(YJNotifyTag.USER_PHOTO, PHOTO_FILE);
        close();
    }
}
