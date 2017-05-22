package com.youjing.yjeducation.ui.actualize.activity;

import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.dispaly.activity.AYJActivateGiftCard;

/**
 * user  秦伟宁
 * Date 2016/3/21
 * Time 16:13
 */
public  class YJActivateGiftCard extends AYJActivateGiftCard {

    private String TAG = "YJActivateGiftCard";

    private YJUser mYjUser;

//    @Override
//    public void activateCard(String admin, String password) {
//        Map<String, Object> objectMap = new HashMap<>();
//        RequestParams params = new RequestParams();
//
//        params.put("cardCode", admin);
//        params.put("password", password);
//        StringUtils.Log(TAG, "失败=");
//        params.put("customerId", Long.valueOf(mYjUser.getCustomerId()));
//
//        YJGlobal.getStudentReqManager().activateCard(params, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                StringUtils.Log(TAG, "失败=" + s);
//            }
//
//            @Override
//            public void onSuccess(int i, Header[] headers, String s) {
//                try {
//                    StringUtils.Log(TAG, "成功s=" + s);
//                    JSONObject json = null;
//                    json = new JSONObject(s);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
