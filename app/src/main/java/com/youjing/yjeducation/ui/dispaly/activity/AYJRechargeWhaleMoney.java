package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Context;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJOrderModel;
import com.youjing.yjeducation.model.YJWhalePackageModel;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.util.YjGetUserInfo;
import com.youjing.yjeducation.wxapi.WXPayEntryActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 16:39
 */
@VLayoutTag(R.layout.activity_recharge_whale_money_no_wx)
public abstract class AYJRechargeWhaleMoney extends BaseSwipeBackActivity implements IVClickDelegate {
    @VViewTag(R.id.gv_pay_whale)
    private GridView mGv_pay_whale;
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
    @VViewTag(R.id.view_line)
    private View mView_line;
    @VViewTag(R.id.lay_item)
    private RelativeLayout mLay_item;
    @VViewTag(R.id.txt_name)
    private TextView mTxt_name;
    @VViewTag(R.id.txt_rmb_num)
    private TextView mTtxt_rmb_num;
    @VViewTag(R.id.txt_whale_money_num)
    protected TextView mTxt_whale_money_num;
    @VViewTag(R.id.txt_rmb_money)
    protected TextView mTxt_rmb_money;

    @VViewTag(R.id.txt_teacher_name)
    protected TextView txt_teacher_name;
    @VViewTag(R.id.img_course_teacher)
    protected ImageView img_course_teacher;

    @VViewTag(R.id.img_new_course)
    protected ImageView img_new_course;



    private HashMap isSelect;
    private CheckBoxAdapter mCheckBoxAdapter;
    private String TAG = "AYJRechargeWhaleMoney";
    private int mPosotion = 0;
    private List<YJWhalePackageModel> yjWhalePackageModelList;
    private String virtualCurrencyId;
    protected String mOrderId = "";

    public static final VParamKey<YJCourseModel> COURSE_MODEL = new VParamKey<YJCourseModel>(null);
    public static final VParamKey<Boolean>   VISIBLE_FLAG = new VParamKey<Boolean>(false);

    public static final VParamKey<YJOrderModel> ORDER_MODEL = new VParamKey<YJOrderModel>(null);
    protected YJCourseModel mYjCourseModel;
    protected YJOrderModel mYjOrderModel;
    protected String courseId = "";
    protected String orderId = "";
    protected String orderRecordType = "";

    private boolean visileFlag ;
    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "购买鲸币", true);

        courseId =  getIntent().getStringExtra("courseId");
        orderId =  getIntent().getStringExtra("orderId");
        orderRecordType =  getIntent().getStringExtra("orderRecordType");
        visileFlag = getIntent().getBooleanExtra("visileFlag",false);
        if (!TextUtils.isEmpty(courseId)){
            initCourseData();
        }else if (!TextUtils.isEmpty(orderId) && !TextUtils.isEmpty(orderRecordType)){
            initOrderData();
        }else {
            initUI();
        }
    }
    private void initUI(){
        YjGetUserInfo.getUserCoin((IVActivity) getContext(), mTxt_whale_money_num);
        if(mYjCourseModel != null){
            mTtxt_rmb_num.setText(mYjCourseModel.getPayCoin()+"");
            mTxt_name.setText(mYjCourseModel.getName());
            BitmapUtils.create(getContext()).display(img_course_teacher, mYjCourseModel.getCoursePic());
            txt_teacher_name.setText(mYjCourseModel.getTeacher().getTrueName());
            if (!TextUtils.isEmpty(mYjCourseModel.getIsNew()) && mYjCourseModel.getIsNew().equals("Yes")){
                img_new_course.setVisibility(View.VISIBLE);
            }else {
                img_new_course.setVisibility(View.GONE);
            }
        }
        if(mYjOrderModel != null){
            StringUtils.Log(TAG, "mYjOrderModel=" + mYjOrderModel.toString());
            mTtxt_rmb_num.setText(mYjOrderModel.getPayMoney() + "");
            BitmapUtils.create(getContext()).display(img_course_teacher, mYjOrderModel.getCoursePic());
        }
        if(visileFlag){
            mView_line.setVisibility(View.VISIBLE);
            mLay_item.setVisibility(View.VISIBLE);
        }else {
            mView_line.setVisibility(View.GONE);
            mLay_item.setVisibility(View.GONE);
        }
        getWhalePackage();
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

    private void setData() {
        isSelect = new HashMap();
        if (yjWhalePackageModelList != null && yjWhalePackageModelList.size() > 0) {
            for (int i = 0; i < yjWhalePackageModelList.size(); i++) {
                isSelect.put(i, false);
            }
            isSelect.put(0, true);
            mTxt_rmb_money.setText("￥"+yjWhalePackageModelList.get(0).getDiscountPrice());

        } else {
            isSelect.put(0, true);
        }
    }

    //获得套餐列表
    private void getWhalePackage() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.WHALE_MONEY_PACKAGE, null, true, new TextHttpResponseHandler() {
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
                                   yjWhalePackageModelList = JSON.parseArray(jsonData.getString("virtualCurrencyList"), YJWhalePackageModel.class);

                                   setData();
                                   if (mCheckBoxAdapter == null) {
                                       mCheckBoxAdapter = new CheckBoxAdapter(getContext(), isSelect);
                                   } else {
                                       mCheckBoxAdapter.notifyDataSetChanged();
                                   }
                                   mGv_pay_whale.setAdapter(mCheckBoxAdapter);


                                   break;
                               case 300:
                                   showToast("参数不合法");
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

            mRb_wx_pay.setChecked(true);
            mRb_ali_pay.setChecked(false);
        } else if (view == mRb_ali_pay) {

            mRb_ali_pay.setChecked(true);
            mRb_wx_pay.setChecked(false);
        } else if (view == mBtn_pay_now) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            int index = -1;
            if (mRb_ali_pay.isChecked()) {
                index = 1;
            } else if (mRb_wx_pay.isChecked()) {
                index = 2;
            }
            if (yjWhalePackageModelList != null) {
                StringUtils.Log(TAG,"mPosotion2222="+mPosotion);
                virtualCurrencyId = yjWhalePackageModelList.get(mPosotion).getVirtualCurrencyId();
                if(mYjOrderModel != null){
                    onBtnConfirmPayClick(index, mYjOrderModel.getGoodsId());
                }else {
                    onBtnConfirmPayClick(index, virtualCurrencyId);
                }

            }

        }

    }

    public class CheckBoxAdapter extends BaseAdapter {
        Context context;
        HashMap isSelecte;

        public CheckBoxAdapter(Context context, HashMap isSelect) {
            this.context = context;
            this.isSelecte = isSelect;
        }

        @Override
        public int getCount() {
            if (yjWhalePackageModelList != null) {
                return yjWhalePackageModelList.size();
            } else {
                return 0;
            }

        }

        @Override
        public Object getItem(int position) {
            if (yjWhalePackageModelList != null) {
                return yjWhalePackageModelList.get(position);
            } else {
                return position;
            }
        }
        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                holder = new Holder();
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_pay_whale_item, null);

                holder.mBtn_pay_whale = (CheckBox) convertView.findViewById(R.id.btn_pay_whale);
                holder.mTxt_rmb_money = (TextView) convertView.findViewById(R.id.txt_rmb_money);
                holder.mTxt_whale_money = (TextView) convertView.findViewById(R.id.txt_whale_money);
                holder.mRe_gray_bg = (RelativeLayout) convertView.findViewById(R.id.re_gray_bg);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            if (yjWhalePackageModelList != null && yjWhalePackageModelList.size() > 0) {
                holder.mTxt_rmb_money.setText("￥"+yjWhalePackageModelList.get(position).getDiscountPrice());
                holder.mTxt_whale_money.setText(yjWhalePackageModelList.get(position).getNumber() + "鲸币");

                if(mYjOrderModel != null){
                    holder.mRe_gray_bg.setVisibility(View.VISIBLE);
                    mTxt_rmb_money.setText("￥"+ mYjOrderModel.getPayMoney());
                    if(!TextUtils.isEmpty(yjWhalePackageModelList.get(position).getVirtualCurrencyId()) && yjWhalePackageModelList.get(position).getVirtualCurrencyId().equals(mYjOrderModel.getGoodsId())){
                        if (!TextUtils.isEmpty(yjWhalePackageModelList.get(position).getBuyCondition()) && yjWhalePackageModelList.get(position).getBuyCondition().equals("ONETIME")){
                            holder.mBtn_pay_whale.setBackgroundResource(R.drawable.pay_button_one_bg);
                        }else {
                            holder.mBtn_pay_whale.setBackgroundResource(R.drawable.pay_button_bg);
                        }
                        holder.mBtn_pay_whale.setChecked((true));
                        holder.mRe_gray_bg.setBackgroundColor(getResources().getColor(R.color.transparent));
                    }
                }else {
                    if(!TextUtils.isEmpty(yjWhalePackageModelList.get(position).getBuyCondition()) && yjWhalePackageModelList.get(position).getBuyCondition().equals("ONETIME")){
                        holder.mBtn_pay_whale.setBackgroundResource(R.drawable.pay_button_one_bg);
                    }else {
                        holder.mBtn_pay_whale.setBackgroundResource(R.drawable.pay_button_bg);
                    }
                    holder.mBtn_pay_whale.setChecked((boolean) isSelecte.get(position));
                    holder.mBtn_pay_whale.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mPosotion = position;
                            StringUtils.Log(TAG,"mPosotion111="+mPosotion);
                            isSelecte.put(position, true);
                            mTxt_rmb_money.setText("￥"+ yjWhalePackageModelList.get(position).getDiscountPrice());
                            if (view.isPressed()) {
                                for (int i = 0; i < yjWhalePackageModelList.size(); i++) {
                                    //把其他的checkbox设置为false
                                    if (i != position) {
                                        isSelecte.put(i, false);
                                    }
                                }
                            }
                            CheckBoxAdapter.this.notifyDataSetChanged();
                        }

                    });
                }

            }

            if (temp == position) {
                holder.mBtn_pay_whale.setPressed(true);
            }
            return convertView;
        }
    }

    static class Holder {
        protected CheckBox mBtn_pay_whale;
        protected TextView mTxt_rmb_money;
        protected TextView mTxt_whale_money;
        protected RelativeLayout mRe_gray_bg;

    }

    protected abstract void onBtnConfirmPayClick(int index, String goodsId);
}
