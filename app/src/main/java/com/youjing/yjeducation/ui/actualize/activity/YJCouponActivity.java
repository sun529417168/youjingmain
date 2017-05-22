package com.youjing.yjeducation.ui.actualize.activity;

import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCouponModel;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCouponActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/7/11
 * Time 18:07
 */
public class YJCouponActivity extends AYJCouponActivity {

    private String TAG = "YJCouponActivity";
    @Override
    public void getAddLoadMore(int page) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("currentPage", page);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MY_COUPON, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            List<YJCouponModel> yjCouponModelList = JSON.parseArray(jsonData.getString("couponList"), YJCouponModel.class);
                            PaginationInfo paginationInfo = JSON.parseObject(jsonData.getString("pagination"), PaginationInfo.class);
                            StringUtils.Log("分页信息", paginationInfo.toString());
                            notifyListener(YJNotifyTag.MY_COUPON_LIST_PAGE_INFO, paginationInfo);
                            notifyListener(YJNotifyTag.MY_COUPON_LIST_LOAD_MORE, yjCouponModelList);
                            break;
                        case 300:
                            break;
                        case 400:
                            StringUtils.Log(TAG, "400");
                            //   notifyListener(YJNotifyTag.MY_CARD_LIST_LOAD_MORE, null);
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}