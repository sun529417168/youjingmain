package com.youjing.yjeducation.ui.actualize.activity;

import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCardModel;
import com.youjing.yjeducation.model.YJConsumeHistoryModel;
import com.youjing.yjeducation.ui.dispaly.activity.AYJConsumeHistoryActivity;
import com.youjing.yjeducation.util.MakeSign;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/5
 * Time 16:39
 */
public class YJConsumeHistoryActivity extends AYJConsumeHistoryActivity {
    private String TAG = "YJConsumeHistoryActivity";

    @Override
    public void getAddLoadMore(int page) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("currentPage", page);
            StringUtils.Log("传入的页数", page+"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_CONSUM_HISTORY, objectMap, true, new TextHttpResponseHandler() {
                     @Override
                     public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                         showToast(getString(R.string.no_net_work));
                         StringUtils.Log(TAG, "失败=" + s);
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
                                     List<YJConsumeHistoryModel> yjConsumeHistoryModelList = JSON.parseArray(jsonData.getString("coinLogList"), YJConsumeHistoryModel.class);
                                     PaginationInfo paginationInfo = JSON.parseObject(jsonData.getString("pagination"), PaginationInfo.class);
                                     StringUtils.Log("分页信息", paginationInfo.toString());
                                     notifyListener(YJNotifyTag.CONSUM_HISTORY_LIST_PAGE_INFO, paginationInfo);
                                     notifyListener(YJNotifyTag.CONSUM_HISTORY_LIST_LOAD_MORE, yjConsumeHistoryModelList);
                                     break;
                                 case 300:
                                     break;
                                 case 400:
                                     StringUtils.Log(TAG, "400");
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
