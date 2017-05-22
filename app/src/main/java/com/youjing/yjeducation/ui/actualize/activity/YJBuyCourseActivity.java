package com.youjing.yjeducation.ui.actualize.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCouponModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.dispaly.activity.AYJBuyCourseActivity;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.YJPayResult;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.utils.notification.IVNotificationListener;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 16:39
 */
public class YJBuyCourseActivity extends AYJBuyCourseActivity {
    private String TAG = "YJBuyCourseActivity";
    private String payInfo;



    private IWXAPI api = WXAPIFactory.createWXAPI(this, null);


    @Override
    protected void bindNotifyListener() {
        addListener(YJNotifyTag.WX_PAY_CANCEL, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                finish();
            }
        });
        addListener(YJNotifyTag.COLSE_BUYCOURSE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paySuccess();
            }
        });
        addListener(YJNotifyTag.COUPON_MODEL, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                couponModel = (YJCouponModel)value;
                if (couponModel != null){
                    if (!TextUtils.isEmpty(couponModel.getCouponType()) && couponModel.getCouponType().equals("Discount")){
                        txt_coupon.setText("使用"+ + Double.parseDouble(couponModel.getDiscountDegree())*10 + "折优惠券");
                        indexCoupon = 1;
                        for (int i= 1; i< yjCouponModelList.size();i++){
                            if (couponModel.getCouponId().equals(yjCouponModelList.get(i).getCouponId())){
                                mCouponPosition = i;
                                break;
                            }
                        }
                        Float numMoney = mYjCourseModel.getPayMoney()* Float.parseFloat(couponModel.getDiscountDegree());
                        re_pay_select.setVisibility(View.VISIBLE);
                        if (numMoney >= 0.01){
                            mTxt_rmb_money.setText("￥" + new BigDecimal(StringUtils.getMoneyStr(numMoney+"")).setScale(2, BigDecimal.ROUND_UP));
                        }else {
                            mTxt_rmb_money.setText("￥" + new BigDecimal(numMoney).setScale(2, BigDecimal.ROUND_UP));
                        }
                        isFree = false;
                    }else if (!TextUtils.isEmpty(couponModel.getCouponType()) && couponModel.getCouponType().equals("Money") ){
                        txt_coupon.setText("使用"+couponModel.getPreferentialMoney()+"元优惠券");
                        indexCoupon = 1;
                        for (int i= 1; i< yjCouponModelList.size();i++){
                            if (couponModel.getCouponId().equals(yjCouponModelList.get(i).getCouponId())){
                                mCouponPosition = i;
                                break;
                            }
                        }
                        if (mYjCourseModel != null && !TextUtils.isEmpty(couponModel.getPreferentialMoney())){
                            if (Float.parseFloat(couponModel.getPreferentialMoney()) >= mYjCourseModel.getPayMoney()){
                                re_pay_select.setVisibility(View.GONE);
                                mTxt_rmb_money.setText("￥0");
                                isFree = true;
                            }else {
                                re_pay_select.setVisibility(View.VISIBLE);
                                Float numMoney = mYjCourseModel.getPayMoney()-Float.parseFloat(couponModel.getPreferentialMoney());
                                DecimalFormat fnum  =   new  DecimalFormat("##0.00");
                                mTxt_rmb_money.setText("￥" + fnum.format(numMoney));
                                isFree = false;
                            }
                        }
                    }
                }else {
                    if (mYjCourseModel != null){
                        mTxt_rmb_money.setText("￥"+mYjCourseModel.getPayMoney());
                    }else if (mYjOrderModel != null){
                        mTxt_rmb_money.setText("￥"+mYjOrderModel.getPayMoney());
                    }
                    mCouponPosition = 0;
                    isFree = false;
                    txt_coupon.setText("未使用优惠券");
                    indexCoupon = 0;
                    re_pay_select.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onBtnConfirmPayClick(int index) {
        if (index == 2) {
            if (mYjCourseModel != null) {
                //生成订单后去支付
                if (!TextUtils.isEmpty(mOrderId)){
                    getOrderInfo("WeChatPay", mOrderId);
                }else {
                    if (isFree){
                        createOrderFree("FreePay", mYjCourseModel.getCourseId());
                    }else {
                        createOrder("WeChatPay", mYjCourseModel.getCourseId());
                    }
                }
            }
            if (mYjOrderModel != null) {
                mOrderId = mYjOrderModel.getOrderId();
                if (isFree){
                    createOrderFree("FreePay", mYjOrderModel.getOrderId());
                }else {
                    getOrderInfo("WeChatPay", mYjOrderModel.getOrderId());
                }
            }

        } else if (index == 1) {
            if (mYjCourseModel != null) {
                if (!TextUtils.isEmpty(mOrderId)){
                    getOrderInfo("Alipay", mOrderId);
                }else {
                    if (isFree){
                        createOrderFree("FreePay", mYjCourseModel.getCourseId());
                    }else {
                        createOrder("Alipay", mYjCourseModel.getCourseId());
                    }
                }
            }
            if (mYjOrderModel != null) {
                mOrderId = mYjOrderModel.getOrderId();
                if (isFree){
                    createOrderFree("FreePay", mYjOrderModel.getOrderId());
                }else {
                    getOrderInfo("Alipay", mYjOrderModel.getOrderId());
                }
            }
        } else if (index == 3) {
            payWhale(mYjCourseModel.getCourseId());
        }
    }



    //鲸币购买课程
    private void payWhale(String courseId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            StringUtils.Log(TAG, "courseId=" + courseId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.PAY_COURSE_WHALE_MONEY, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                showToast(getString(R.string.no_net_work));
                StringUtils.Log(TAG, "失败s=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            notifyListener(YJNotifyTag.LOGIN_SUCESS, "购买成功");
                            notifyListener(YJNotifyTag.USER_COIN, "");
                            getUserInfo();
                            break;
                        case 300:
                            // showToast("参数不合法");
                            break;
                        case 400:
                            // showToast("课程不存在");
                            break;
                        case 401:
                            //  showToast("非鲸币付费课程 ");
                            break;
                        case 402:
                            //  showToast("用户附属信息不存在");
                            break;
                        case 403:
//                                      CustomToast.makeText(getContext(), "鲸币余额不足", Toast.LENGTH_SHORT).show();
                            showToast("鲸币余额不足");
                            break;
                        case 500:
                            // showToast("服务器异常");
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


    public static final int SDK_PAY_FLAG = 1;
    public static final int SDK_CHECK_FLAG = 2;
    /**
     * 支付宝支付返回结果
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    YJPayResult payResult = new YJPayResult((String) msg.obj);

                    // 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
                    String resultInfo = payResult.getResult();

                    String resultStatus = payResult.getResultStatus();

                    StringUtils.Log(TAG, "resultStatus==" + resultStatus);
                    StringUtils.Log(TAG, "resultInfo==" + resultInfo);
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        paySuccess();
                        StringUtils.Log(TAG, "resultInfo==" + resultInfo);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showToast("支付结果确认中");

                        } else if (TextUtils.equals(resultStatus, "6001")){
                            //用户主动取消支付
                            showToast("取消购买");
                        }else {
                            // 其他值就可以判断为支付失败，或者系统返回的错误
                            showToast("支付失败");
                            finish();
                        }
                    }
                    break;
                }
                case SDK_CHECK_FLAG: {
                    showToast("检查结果为：" + msg.obj);

                    break;
                }
                default:
                    break;
            }
        }
    };


    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void payAlipay(final String s) {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(YJBuyCourseActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(s, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * call wx sdk pay. 调用微信SDK支付
     */
    public void payWxpay(final String s) {
        PayReq req = new PayReq();
        JSONObject json = null;
        try {
            json = new JSONObject(s);
            req.appId = json.getString("appId");
            req.partnerId = json.getString("partnerId");
            req.prepayId = json.getString("prepayId");
            req.nonceStr = json.getString("nonceStr");
            req.timeStamp = json.getString("timeStamp");
            req.packageValue = "Sign=WXPay";
            req.sign = json.getString("sign");
            req.extData = "app data"; // optional
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.registerApp(req.appId);
            api.sendReq(req);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    //支付宝支付成功调用
    private void paySuccess() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            StringUtils.Log(TAG, "mOrderId=" + mOrderId);
            objectMap.put("orderId", mOrderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.PAY_SUCESS, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                showToast(getString(R.string.no_net_work));
                StringUtils.Log(TAG, "失败s=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            notifyListener(YJNotifyTag.PAY_SUCESS, "rmb");
                            finish();
                            break;
                        case 300:
                            break;
                        case 400:
//                                      CustomToast.makeText(getContext(), "订单不存在", Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
//                                      CustomToast.makeText(getContext(), "订单和用户不匹配", Toast.LENGTH_SHORT).show();
                            break;
                        case 402:
//                                      CustomToast.makeText(getContext(), "未选支付方式", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
//                                      CustomToast.makeText(getContext(), "支付方式不合法", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
//                                      CustomToast.makeText(getContext(), "订单保存异常", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
//                                      CustomToast.makeText(getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
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


    private String order = "";

    //创建订单
    private void createOrder(final String payType, final String goodsId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("payChannel", payType);
            objectMap.put("goodsId", goodsId);
            objectMap.put("orderRecordType", "BUY_COURSE");

            if (couponModel != null && !TextUtils.isEmpty(couponModel.getCouponId())){
                objectMap.put("couponId", couponModel.getCouponId());
            }
            if (mYjCourseModel != null) {
                if (!TextUtils.isEmpty(mYjCourseModel.getSaleWayId())) {
                    objectMap.put("saleWayId", mYjCourseModel.getSaleWayId());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.CREATE_ORDER, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                showToast(getString(R.string.no_net_work));
                StringUtils.Log(TAG, "createOrder失败s=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "createOrder成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            String orderId = jsonData.getString("orderId");
                            mOrderId = orderId;
                            getOrderInfo(payType, orderId);
                            break;
                        case 300:
                            showToast("参数不合法");
                            break;
                        case 400:
                            showToast("订单中存在不合法的商品类型");
                            break;
                        case 401:
                            showToast("未选支付方式");
                            break;
                        case 402:
                            showToast("支付方式不合法");
                            break;
                        case 403:
                            showToast("订单创建异常");
                            break;
                        case 500:
                            showToast("服务器异常");
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

    private void getOrderInfo(final String payType, final String orderId) {

        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("payChannel", payType);
            objectMap.put("orderId", orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_ORDER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                showToast(getString(R.string.no_net_work));
                StringUtils.Log(TAG, "失败s=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            order = jsonData.getString("orderInfo");
                            if (payType.equals("Alipay")) {
                                payAlipay(order);
                            } else if (payType.equals("WeChatPay")) {
                                SharedUtil.setString(getContext(), "wxpat", "buycourse");
                                payWxpay(order);
                            }
                            break;
                        case 300:
                            showToast("参数不合法");
                            break;
                        case 400:
                            showToast("订单中存在不合法的商品类型");
                            break;
                        case 401:
                            showToast("未选支付方式");
                            break;
                        case 402:
                            showToast("支付方式不合法");
                            break;
                        case 403:
                            showToast("订单创建异常");
                            break;
                        case 500:
                            showToast("服务器异常");
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


    private void getUserInfo() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
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
                            YJUser yjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                            StringUtils.Log(TAG, "yjUser.toString()=" + yjUser.toString());
                            YJGlobal.setYjUser(yjUser);
                            txt_whale_money_num.setText(yjUser.getCoin());
                            notifyListener(YJNotifyTag.PAY_SUCESS, true);
                            CustomToast.makeTexts(getContext(), "购买成功", 0).show();
                            finish();
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


    //我的可用的优惠卷
    @Override
    protected void getMyCoupon(String goodsId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("goodsId", goodsId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MY_COUPON_CANUSE, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                txt_coupon.setText("暂无优惠券可用");
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
                             yjCouponModelList = JSON.parseArray(jsonData.getString("payCanUseCouponList"), YJCouponModel.class);
                             couponModel = yjCouponModelList.get(0);
                            if (couponModel.getCouponType().equals("Discount")){
                                txt_coupon.setText("使用" + Double.parseDouble(yjCouponModelList.get(0).getDiscountDegree())*10 + "折优惠券");
                                indexCoupon = 1;
                                Float numMoney = mYjCourseModel.getPayMoney()* Float.parseFloat(yjCouponModelList.get(0).getDiscountDegree());
                                re_pay_select.setVisibility(View.VISIBLE);

                                if (numMoney >= 0.01){
                                    mTxt_rmb_money.setText("￥" + new BigDecimal(StringUtils.getMoneyStr(numMoney+"")).setScale(2, BigDecimal.ROUND_UP));
                                }else {
                                    mTxt_rmb_money.setText("￥" + new BigDecimal(numMoney).setScale(2, BigDecimal.ROUND_UP));
                                }
                                isFree = false;

                            }else if(couponModel.getCouponType().equals("Money")){
                                txt_coupon.setText("使用" + yjCouponModelList.get(0).getPreferentialMoney() + "元优惠券");
                                indexCoupon = 1;
                                if (mYjCourseModel != null && !TextUtils.isEmpty(yjCouponModelList.get(0).getPreferentialMoney())){
                                    if (Float.parseFloat(yjCouponModelList.get(0).getPreferentialMoney()) >= mYjCourseModel.getPayMoney()){
                                        re_pay_select.setVisibility(View.GONE);
                                        mTxt_rmb_money.setText("￥0");
                                        isFree = true;
                                    }else {
                                        re_pay_select.setVisibility(View.VISIBLE);
                                        Float numMoney = mYjCourseModel.getPayMoney()-Float.parseFloat(yjCouponModelList.get(0).getPreferentialMoney());
                                        DecimalFormat fnum  =   new  DecimalFormat("##0.00");
                                        mTxt_rmb_money.setText("￥" + fnum.format(numMoney));
                                        isFree = false;
                                    }
                                }
                            }
                        case 300:
                            break;
                        case 400:
                            txt_coupon.setText("暂无优惠券可用");
                            break;
                        case 401:
                            txt_coupon.setText("暂无优惠券可用");
                            btn_coupon_right.setVisibility(View.INVISIBLE);
                            couponModel = null;
                            break;
                        case 402:
                            txt_coupon.setText("暂无优惠券可用");
                            break;
                        case 403:
                            txt_coupon.setText("暂无优惠券可用");
                            break;
                        case 500:
                            txt_coupon.setText("暂无优惠券可用");
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //优惠卷免费 创建订单
    private void createOrderFree(final String payType, final String goodsId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("payChannel", payType);
            objectMap.put("goodsId", goodsId);
            objectMap.put("orderRecordType", "BUY_COURSE");

            if (couponModel != null && !TextUtils.isEmpty(couponModel.getCouponId())){
                objectMap.put("couponId", couponModel.getCouponId());
            }
            if (mYjCourseModel != null) {
                if (!TextUtils.isEmpty(mYjCourseModel.getSaleWayId())) {
                    objectMap.put("saleWayId", mYjCourseModel.getSaleWayId());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.CREATE_ORDER, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                showToast(getString(R.string.no_net_work));
                StringUtils.Log(TAG, "createOrderFree失败s=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "createOrderFree成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            CustomToast.makeTexts(getContext(), "购买成功", 0).show();
                            notifyListener(YJNotifyTag.PAY_SUCESS, "rmb");
                            finish();
                            break;
                        case 300:
                            showToast("参数不合法");
                            break;
                        case 400:
                            showToast("订单中存在不合法的商品类型");
                            break;
                        case 401:
                            showToast("未选支付方式");
                            break;
                        case 402:
                            showToast("支付方式不合法");
                            break;
                        case 403:
                            showToast("订单创建异常");
                            break;
                        case 500:
                            showToast("服务器异常");
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
