package com.youjing.yjeducation.ui.dispaly.activity;

import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJCouponModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJOrderModel;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJCouponDialog;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.YjGetUserInfo;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.base.VParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 16:39
 */
@VLayoutTag(R.layout.activity_buy_course_rmb_no_wx)
public abstract class AYJBuyCourseActivity extends BaseSwipeBackActivity implements IVClickDelegate {
    @VViewTag(R.id.re_ali_pay)
    private RelativeLayout mRe_ali_pay;
    @VViewTag(R.id.re_wx_pay)
    private RelativeLayout mRe_wx_pay;
    @VViewTag(R.id.rb_wx_pay)
    private RadioButton mRb_wx_pay;
    @VViewTag(R.id.rb_ali_pay)
    private RadioButton mRb_ali_pay;
    @VViewTag(R.id.btn_pay_group)
    private RadioGroup mBtn_pay_group;
    @VViewTag(R.id.btn_pay_now)
    private Button mBtn_pay_now;
    @VViewTag(R.id.re_pay_type)
    private RelativeLayout mRe_pay_type;
    @VViewTag(R.id.re_whale_num)
    private RelativeLayout mRe_whale_num;
    @VViewTag(R.id.re_pay_select)
    protected RelativeLayout re_pay_select;

    @VViewTag(R.id.txt_name)
    private TextView mTxt_name;
    @VViewTag(R.id.txt_teacher_name)
    private TextView mTxt_teacher_name;
    @VViewTag(R.id.txt_money_num_rmb)
    private TextView mTxt_money_num_rmb;
    @VViewTag(R.id.txt_rmb_num)
    private TextView mTxt_rmb_num;
    @VViewTag(R.id.fl_rmb)
    private FrameLayout fl_rmb;
    @VViewTag(R.id.img_rmb)
    private ImageView img_rmb;
    @VViewTag(R.id.img_course_teacher)
    private ImageView img_course_teacher;
    @VViewTag(R.id.txt_whale_money_num)
    protected TextView txt_whale_money_num;
    @VViewTag(R.id.txt_rmb_money)
    protected TextView mTxt_rmb_money;
    @VViewTag(R.id.img_new_course)
    private ImageView img_new_course;
    @VViewTag(R.id.re_select_coupon)
    private RelativeLayout re_select_coupon;
    @VViewTag(R.id.txt_coupon)
    protected TextView txt_coupon;
    @VViewTag(R.id.txt_coupon_order)
    protected TextView txt_coupon_order;
    @VViewTag(R.id.btn_coupon_right)
    protected ImageView btn_coupon_right;

    protected YJCourseModel mYjCourseModel;
    protected YJOrderModel mYjOrderModel;
    protected YJCouponModel couponModel;
    private String TAG = "AYJBuyCourseActivity";
    protected List<YJCouponModel> yjCouponModelList = null;
    public static final VParamKey<YJCourseModel> COURSE_MODEL = new VParamKey<YJCourseModel>(null);
    public static final VParamKey<YJOrderModel> ORDER_MODEL = new VParamKey<YJOrderModel>(null);
    public static final VParamKey<Boolean> VISIBLE_FLAG = new VParamKey<Boolean>(false);
    private boolean visileFlag;

    protected int mCouponPosition = -1;
    protected boolean isFree = false;

    protected String mOrderId = "";
    protected int indexCoupon = 0;//0是代表没有使用优惠券，1是代表有了优惠券

    protected String courseId = "";
    protected String orderId = "";
    protected String orderRecordType = "";

    protected String mCoin = "";
    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "购买课程", true);
       // mYjCourseModel = getTransmitData(COURSE_MODEL);
      //  mYjOrderModel = getTransmitData(ORDER_MODEL);
      //  visileFlag = getTransmitData(VISIBLE_FLAG);

        courseId =  getIntent().getStringExtra("courseId");
        orderId =  getIntent().getStringExtra("orderId");
        orderRecordType =  getIntent().getStringExtra("orderRecordType");

        if (!TextUtils.isEmpty(courseId)){
            initCourseData();
        }else if (!TextUtils.isEmpty(orderId) && !TextUtils.isEmpty(orderRecordType)){
            initOrderData();
        }
    }
    private void initUI(){
        if (mYjCourseModel != null) {
            StringUtils.Log(TAG, "mYjCourseModel=" + mYjCourseModel.toString());
            mTxt_teacher_name.setText(mYjCourseModel.getTeacher().getTrueName());
            mTxt_name.setText(mYjCourseModel.getName());
            if (!TextUtils.isEmpty(mYjCourseModel.getIsNew()) && mYjCourseModel.getIsNew().equals("Yes")) {
                img_new_course.setVisibility(View.VISIBLE);
            } else {
                img_new_course.setVisibility(View.GONE);
            }
            BitmapUtils.create(getContext()).display(img_course_teacher, mYjCourseModel.getCoursePic());
            if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                mRe_pay_type.setVisibility(View.GONE);
                fl_rmb.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(mCoin)){
                    txt_whale_money_num.setText(mCoin);
                }else {
                    YjGetUserInfo.getUserCoin((IVActivity) getContext(), txt_whale_money_num);
                }
                img_rmb.setImageResource(R.mipmap.whale_money);
                mTxt_rmb_num.setText(mYjCourseModel.getPayCoin() + "");
                mTxt_rmb_money.setText(mYjCourseModel.getPayCoin() + "鲸币");
            }
            if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                mRe_whale_num.setVisibility(View.GONE);
                mRe_pay_type.setVisibility(View.VISIBLE);
                img_rmb.setImageResource(R.mipmap.img_rmb);
                mTxt_rmb_money.setText("￥" + StringUtils.getMoneyStr(mYjCourseModel.getPayMoney() + ""));
                mTxt_money_num_rmb.setText("￥"+StringUtils.getMoneyStr(mYjCourseModel.getOriginalMoney()+""));
                if (mYjCourseModel.getOriginalMoney() <= mYjCourseModel.getPayMoney()){
                    mTxt_money_num_rmb.setVisibility(View.GONE);
                    fl_rmb.setVisibility(View.GONE);
                }else {
                    mTxt_money_num_rmb.setVisibility(View.VISIBLE);
                    fl_rmb.setVisibility(View.VISIBLE);
                }
                mTxt_rmb_num.setText(mYjCourseModel.getPayMoney() + "");
            }
        }
        if (mYjOrderModel != null) {
            StringUtils.Log(TAG, "mYjOrderMode=" + mYjOrderModel.toString());
            mRe_whale_num.setVisibility(View.GONE);
            mRe_pay_type.setVisibility(View.VISIBLE);
            img_rmb.setImageResource(R.mipmap.img_rmb);
            if (TextUtils.isEmpty(mYjOrderModel.getIsNew())) {
                if (mYjOrderModel.getIsNew().equals("Yes")) {
                    img_new_course.setVisibility(View.VISIBLE);
                } else {
                    img_new_course.setVisibility(View.GONE);
                }
            }
            mTxt_name.setText(mYjOrderModel.getGoodsName());
            mTxt_teacher_name.setText(mYjOrderModel.getTrueName());
            mTxt_rmb_money.setText("￥" + StringUtils.getMoneyStr(mYjOrderModel.getPayMoney()));

            mTxt_money_num_rmb.setText(StringUtils.getMoneyStr(mYjOrderModel.getGoodsOriginalMoney()));
            if (Float.parseFloat(mYjOrderModel.getGoodsOriginalMoney()) <= Float.parseFloat(mYjOrderModel.getGoodsPayMoney())){
                mTxt_money_num_rmb.setVisibility(View.GONE);
                fl_rmb.setVisibility(View.GONE);
            }else {
                mTxt_money_num_rmb.setVisibility(View.VISIBLE);
                fl_rmb.setVisibility(View.VISIBLE);
            }
            mTxt_rmb_num.setText(StringUtils.getMoneyStr(mYjOrderModel.getGoodsPayMoney()));
            BitmapUtils.create(getContext()).display(img_course_teacher, mYjOrderModel.getCoursePic());

            if (mYjOrderModel.getOrderUsedCoupon() != null) {
                re_select_coupon.setVisibility(View.VISIBLE);
                btn_coupon_right.setVisibility(View.INVISIBLE);
                txt_coupon.setVisibility(View.GONE);
                txt_coupon_order.setVisibility(View.VISIBLE);
                indexCoupon = 1;
                if (!TextUtils.isEmpty(mYjOrderModel.getOrderUsedCoupon().getCouponType()) && mYjOrderModel.getOrderUsedCoupon().getCouponType().equals("Money")){
                    txt_coupon_order.setText("使用" + mYjOrderModel.getOrderUsedCoupon().getPreferentialMoney() + "元优惠券");
                }else if (!TextUtils.isEmpty(mYjOrderModel.getOrderUsedCoupon().getCouponType()) && mYjOrderModel.getOrderUsedCoupon().getCouponType().equals("Discount")){
                    txt_coupon_order.setText("使用" + Float.parseFloat(mYjOrderModel.getOrderUsedCoupon().getDiscountDegree())*10 + "折优惠券");
                }
            } else {
                indexCoupon = 0;
                txt_coupon_order.setVisibility(View.VISIBLE);
                txt_coupon_order.setText("暂无优惠券可用");
                re_select_coupon.setVisibility(View.VISIBLE);
                btn_coupon_right.setVisibility(View.INVISIBLE);
            }
        }
        //优惠卷列表
        if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getCourseId())) {
            getMyCoupon(mYjCourseModel.getCourseId());
        }
        bindNotifyListener();
    }
    private void initCourseData() {
            Map<String, Object> objectMap = new HashMap<>();
            try {
                objectMap.put("courseId", courseId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            YJStudentReqManager.getServerData(YJReqURLProtocol.GET_BUY_COURSE_INFO, objectMap, true, new TextHttpResponseHandler() {
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
                                JSONObject jsonObject = new JSONObject(json.getString("data"));
                                JSONObject customerInfo = new JSONObject(jsonObject.getString("customerInfo"));
                                mCoin =  customerInfo.getString("coin");
                                mYjCourseModel = JSON.parseObject(jsonObject.getString("courseInfo"), YJCourseModel.class);
                                StringUtils.Log(TAG, "yjCourseModel=" + mYjCourseModel.toString());
                                initUI();
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
    public void initOrderData( ) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("orderId", orderId);
            objectMap.put("orderRecordType", orderRecordType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MY_ORDER_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getMyOrderInfo失败=" + s);
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
                            JSONObject jsonObject = new JSONObject(json.getString("data"));
                            mYjOrderModel = JSON.parseObject(jsonObject.getString("orderInfo"), YJOrderModel.class);
                            initUI();
                            StringUtils.Log(TAG, "yjOrderModel=" + mYjOrderModel.toString());
                            break;
                        case 300:
                            showToast("订单错误，请重新下单");
                            finish();
                            break;
                        case 301:
                            showToast("订单错误，请重新下单");
                            finish();
                            break;
                        case 400:
                            showToast("订单错误，请重新下单");
                            finish();
                            break;
                        case 401:
                            showToast("订单错误，请重新下单");
                            finish();
                            break;
                        case 402:
                            showToast("订单错误，请重新下单");
                            finish();
                            break;
                        case 403:
                            showToast("该课程已经被下架了，下次记得早点来哦~");
                            finish();
                            break;
                        case 404:
                            showToast("该课程价格有变，请重新下单支付");
                            finish();
                            break;
                        case 405:
                            showToast("你已经购买过该课程啦，速去我的课程查看");
                            finish();
                            break;
                        case 406:
                            showToast("特价商品只可以购买一次");
                            finish();
                            break;
                        case 500:
                            showToast("订单错误，请重新下单");
                            finish();
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
    protected abstract void bindNotifyListener();

    public static int temp = -1;

    @Override
    public void onClick(View view) {
        if (view == mRe_wx_pay) {
            mRb_ali_pay.setChecked(false);
            mRb_wx_pay.setChecked(true);
        } else if (view == mRe_ali_pay) {
            mRb_wx_pay.setChecked(false);
            mRb_ali_pay.setChecked(true);
        } else if (view == mRb_wx_pay) {
            mRb_ali_pay.setChecked(false);
            mRb_wx_pay.setChecked(true);
        } else if (view == mRb_ali_pay) {
            mRb_wx_pay.setChecked(false);
            mRb_ali_pay.setChecked(true);
        } else if (view == re_select_coupon) {
            if (mYjCourseModel != null) {
                if (!TextUtils.isEmpty(mOrderId)) {
                    if (couponModel!= null){
                        if (!TextUtils.isEmpty(couponModel.getCouponType()) && couponModel.getCouponType().equals("Money")){
                            showToast(indexCoupon == 0 ? "暂无优惠券可用" : "已使用" + couponModel.getPreferentialMoney() + "元优惠券");
                        }else if (!TextUtils.isEmpty(couponModel.getCouponType()) && couponModel.getCouponType().equals("Discount")){
                            showToast(indexCoupon == 0 ? "暂无优惠券可用" : "已使用" + Float.parseFloat(couponModel.getDiscountDegree())*10 + "折优惠券");
                        }
                    }else {
                        showToast("订单已生成");
                    }
                } else {
                    if (yjCouponModelList != null) {
                        YJCouponDialog yjCouponDialog = new YJCouponDialog();
                        StringUtils.Log(TAG, "yjCouponModelList=" + yjCouponModelList.toString());
                        VParams data = createTransmitData(YJCouponDialog.YJ_COUPON_MODEL_LIST, yjCouponModelList).set(YJCouponDialog.COUPON_POSITION, mCouponPosition);
                        showDialog(yjCouponDialog, data);
                    } else {
//                        showToast("暂无优惠券可用");
                    }
                }
            } else if (mYjOrderModel != null) {
                if (mYjOrderModel.getOrderUsedCoupon() != null){
                    if (!TextUtils.isEmpty(mYjOrderModel.getOrderUsedCoupon().getCouponType()) && mYjOrderModel.getOrderUsedCoupon().getCouponType().equals("Money")){
                        showToast(indexCoupon == 0 ? "暂无优惠券可用" : "已使用" + mYjOrderModel.getOrderUsedCoupon().getPreferentialMoney() + "元优惠券");
                    }else if (!TextUtils.isEmpty(mYjOrderModel.getOrderUsedCoupon().getCouponType()) && mYjOrderModel.getOrderUsedCoupon().getCouponType().equals("Discount")){
                        showToast(indexCoupon == 0 ? "暂无优惠券可用" : "已使用" +Float.parseFloat(mYjOrderModel.getOrderUsedCoupon().getDiscountDegree())*10 + "折优惠券");
                    }
                }else {
                    showToast("暂无优惠券可用");
                }
            }
        } else if (view == mBtn_pay_now) {
            int index = -1;

            if (mRb_ali_pay.isChecked()) {
                index = 1;
                StringUtils.Log(TAG, "index = 1");
            } else if (mRb_wx_pay.isChecked()) {
                index = 2;
                StringUtils.Log(TAG, "index = 2");
            }
            if (mYjCourseModel != null) {
                if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                    index = 3;
                }
            }
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            onBtnConfirmPayClick(index);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mYjCourseModel == null) {
            return;
        }
        if (!TextUtils.isEmpty(mOrderId)) {
            btn_coupon_right.setVisibility(View.INVISIBLE);
        } else {
            btn_coupon_right.setVisibility(View.VISIBLE);
        }
    }

    protected abstract void onBtnConfirmPayClick(int index);

    protected abstract void getMyCoupon(String goodsId);
}
