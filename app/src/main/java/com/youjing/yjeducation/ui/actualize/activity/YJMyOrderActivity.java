package com.youjing.yjeducation.ui.actualize.activity;

import android.content.Intent;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCourseListModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJOrderModel;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCourseListActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJMyOrderActivity;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 21:02
 */
public class YJMyOrderActivity extends AYJMyOrderActivity {
    private String TAG = "YJMyOrderActivity";

    @Override
    public void getCourseCatalog(String courseId) {

        Intent in = new Intent(this,YJCoursePlayNewActivity.class);
        in.putExtra("courseId",courseId);
        startActivity(in);
    }

    @Override
    public void getMyOrder() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("currentPage", 1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MY_ORDER, objectMap, true, new TextHttpResponseHandler() {
                     @Override
                     public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                         StringUtils.Log(TAG, "失败=" + s);
                         showToast(getString(R.string.no_net_work));
                         mMsgListView.stopRefresh();
                         mMsgListView.setRefreshTime(System.currentTimeMillis());
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
                                     yjOrderModelList = JSON.parseArray(jsonData.getString("orderList"), YJOrderModel.class);
                                     if (yjOrderModelList != null) {
                                         initXListView();
                                     }
                                     mMsgListView.stopRefresh();
                                     mMsgListView.setRefreshTime(System.currentTimeMillis());
                                     break;
                                 case 300:
                                     break;
                                 case 400:
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

    @Override
    public void getAddLoadMore(int page) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("currentPage", page);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MY_ORDER, objectMap, true, new TextHttpResponseHandler() {
                     @Override
                     public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                         StringUtils.Log(TAG, "失败=" + s);
                         showToast(getString(R.string.no_net_work));
                         mMsgListView.stopRefresh();
                         mMsgListView.setRefreshTime(System.currentTimeMillis());
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
                                     List<YJOrderModel> yjOrderModelList = JSON.parseArray(jsonData.getString("orderList"), YJOrderModel.class);
                                     PaginationInfo paginationInfo = JSON.parseObject(jsonData.getString("pagination"), PaginationInfo.class);
                                     StringUtils.Log("分页信息", paginationInfo.toString());
                                     notifyListener(YJNotifyTag.MY_ORDER_LIST_PAGE_INFO, paginationInfo);
                                     notifyListener(YJNotifyTag.MY_ORDER_LIST_LOAD_MORE, yjOrderModelList);
                                     break;
                                 case 300:
                                     break;
                                 case 400:
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

    @Override
    public void getMyOrderInfo(YJOrderModel yjOrderModel) {
        if (yjOrderModel.getOrderRecordType().equals("PAY_GOID")) {
            //startActivity(createIntent(YJRechargeWhaleMoney.class, createTransmitData(YJRechargeWhaleMoney.ORDER_MODEL, yjOrderModel)));
            Intent intent = new Intent(getContext(),YJRechargeWhaleMoney.class);
            intent.putExtra("orderId", yjOrderModel.getOrderId());
            intent.putExtra("orderRecordType", "PAY_GOID");
            startActivity(intent);
        } else if (yjOrderModel.getOrderRecordType().equals("BUY_COURSE")) {
            // startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.ORDER_MODEL, yjOrderModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
            Intent intent = new Intent(getContext(),YJBuyCourseActivity.class);
            intent.putExtra("orderId", yjOrderModel.getOrderId());
            intent.putExtra("orderRecordType", "BUY_COURSE");
            startActivity(intent);

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        onRefresh();
    }

    private void onRefresh(){
        page = 1;
        paginationInfo = null;
        yjOrderModelList.clear();
        getMyOrder();
    }
}
