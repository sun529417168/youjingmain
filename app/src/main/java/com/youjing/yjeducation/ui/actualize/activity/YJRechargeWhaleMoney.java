package com.youjing.yjeducation.ui.actualize.activity;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alipay.sdk.app.PayTask;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJOrderModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.dispaly.activity.AYJBuyCourseActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJRechargeWhaleMoney;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.YJPayResult;
import com.youjing.yjeducation.wiget.CustomToast;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.youjing.yjeducation.wxapi.WXPayEntryActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 16:39
 */
public class YJRechargeWhaleMoney extends AYJRechargeWhaleMoney  {
    private String TAG = "YJRechargeWhaleMoney";
    private String payInfo;



    private IWXAPI api = WXAPIFactory.createWXAPI(this, null);
    ;

    @Override
    protected void bindNotifyListener() {
        addListener(YJNotifyTag.COLSE_RECHARGE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paySuccess();
            }
        });
    }

    @Override
    protected void onBtnConfirmPayClick(int index, String goodsId) {
        if (index == 2) {
            showToast("微信支付");
            if (!TextUtils.isEmpty(mOrderId)){
                getOrderInfo("WeChatPay", mOrderId);
            }else {
                createOrder("WeChatPay", goodsId);
            }
        } else {
            if (!TextUtils.isEmpty(mOrderId)){
                getOrderInfo("Alipay", mOrderId);
            }else {
                createOrder("Alipay", goodsId);
            }
            showToast("支付宝支付");

        }
    }

    private void aliPay() {
        //订单号
        // setOutTradeNo("66889966");

        // pay();
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
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        paySuccess();
                        StringUtils.Log(TAG, "resultInfo==" + resultInfo);
                    } else {
                        // 判断resultStatus 为非“9000”则代表可能支付失败
                        // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            showToast("支付结果确认中");

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
//                            CustomToast.makeText(getContext(), "支付失败", Toast.LENGTH_SHORT).show();

                            showToast("支付失败");
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
                PayTask alipay = new PayTask(YJRechargeWhaleMoney.this);
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
//            req.extData			= "app data"; // optional
            api.registerApp(req.appId);
            api.sendReq(req);
//           boolean flag =  api.sendReq(req);

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
                StringUtils.Log(TAG, "失败s=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:

                            if (mYjCourseModel != null) {
                                payWhale(mYjCourseModel.getCourseId());
                            } else {
                                getUserInfo(2);
                            }
                            break;
                        case 300:
                            // showToast("参数不合法");
                            break;
                        case 400:
//                              CustomToast.makeText(getContext(), "订单不存在", Toast.LENGTH_SHORT).show();
                            break;
                        case 401:
//                              CustomToast.makeText(getContext(), "订单和用户不匹配", Toast.LENGTH_SHORT).show();
                            break;
                        case 402:
//                              CustomToast.makeText(getContext(), "未选支付方式", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
//                              CustomToast.makeText(getContext(), "支付方式不合法", Toast.LENGTH_SHORT).show();
                            break;
                        case 404:
//                              CustomToast.makeText(getContext(), "订单保存异常", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
//                              CustomToast.makeText(getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
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
            objectMap.put("orderRecordType", "PAY_GOID");

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
                StringUtils.Log(TAG, "失败s=" + s);
                showToast(getString(R.string.no_net_work));
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
                            String orderId = jsonData.getString("orderId");
                            mOrderId = orderId;
                            getOrderInfo(payType, orderId);
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
//                                     CustomToast.makeText(getContext(), "支付方式不合法", Toast.LENGTH_SHORT).show();
                            break;
                        case 403:
//                                     CustomToast.makeText(getContext(), "订单创建异常", Toast.LENGTH_SHORT).show();
                            break;
                        case 500:
//                                     CustomToast.makeText(getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
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
                StringUtils.Log(TAG, "失败s=" + s);
                showToast(getString(R.string.no_net_work));
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
                                SharedUtil.setString(getContext(), "wxpat", "recharge");
                                payWxpay(order);
                            }
                            break;
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
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
                StringUtils.Log(TAG, "失败s=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            getUserInfo(2);

                            break;
                        case 300:
                            // CustomToast.makeText(getContext(), "参数不合法", Toast.LENGTH_SHORT).show();
                            getUserInfo(1);
                            break;
                        case 400:
//                                     CustomToast.makeText(getContext(), "课程不存在", Toast.LENGTH_SHORT).show();
                            getUserInfo(1);
                            break;
                        case 401:
                            //CustomToast.makeText(getContext(), "非鲸币付费课程", Toast.LENGTH_SHORT).show();
                            getUserInfo(1);
                            break;
                        case 402:
                            // CustomToast.makeText(getContext(), "用户附属信息不存在", Toast.LENGTH_SHORT).show();
                            getUserInfo(1);
                            break;
                        case 403:
                            showToast("鲸币余额不足");
                            CustomToast.makeText(getContext(), "鲸币余额不足", Toast.LENGTH_SHORT).show();
                            getUserInfo(1);
                            break;
                        case 500:
//                                     CustomToast.makeText(getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
                            getUserInfo(1);
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

    private void getUserInfo(final int position) {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
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
                            YJUser yjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                            StringUtils.Log(TAG, "yjUser.toString()=" + yjUser.toString());
                            YJGlobal.setYjUser(yjUser);
                            mTxt_whale_money_num.setText(yjUser.getCoin());
                            if (position == 2) {
                                notifyListener(YJNotifyTag.PAY_SUCESS, "jingbi");
                                CustomToast.makeTexts(getContext(), "购买成功", 0).show();
                                finish();
                            }
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

}
