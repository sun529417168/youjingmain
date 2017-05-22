package com.youjing.yjeducation.takephoto.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.youjing.yjeducation.util.StringUtils;

import com.youjing.yjeducation.core.BaseSwipeBackActivity;

/**
 * 继承这个类来让Activity获取拍照的能力<br>
 * @author JPH
 * Date:2015.08.05
 */
public class TakePhotoFragmentActivity extends BaseSwipeBackActivity implements TakePhoto.TakeResultListener{
    private TakePhoto takePhoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     *  获取TakePhoto实例
     * @return
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto=new TakePhotoImpl(this,this);
        }
        return takePhoto;
    }
    @Override
    public void takeSuccess(String imagePath) {
        StringUtils.Log("info", "takeSuccess：" + imagePath);
    }
    @Override
    public void takeFail(String msg) {
        StringUtils.Log("info", "takeFail:" + msg);
    }
    @Override
    public void takeCancel() {
        StringUtils.Log("info", "用户取消");
    }
}
