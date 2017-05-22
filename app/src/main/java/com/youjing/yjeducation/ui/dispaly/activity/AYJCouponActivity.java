package com.youjing.yjeducation.ui.dispaly.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCardModel;
import com.youjing.yjeducation.model.YJCouponModel;
import com.youjing.yjeducation.ui.actualize.activity.YJCouponExplainActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.wiget.MsgListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.Collection;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 21:00
 */
@VLayoutTag(R.layout.activity_coupon)
public  abstract class AYJCouponActivity extends BaseSwipeBackActivity implements IVClickDelegate, IVAdapterDelegate {
    @VViewTag(R.id.msgListView)
    protected MsgListView mMsgListView;
    @VViewTag(R.id.re_coupon_explain)
    private RelativeLayout re_coupon_explain;
    @VViewTag(R.id.my_cards_nothing)
    private ImageView my_cards_nothing;
    @VViewTag(R.id.relayout_my_coupon)
    private RelativeLayout relayout_my_coupon;

    private VAdapter mVAdapter;
    private String TAG = "AYJCouponActivity";
    protected int page = 1;
    protected PaginationInfo paginationInfo;
    protected List<YJCouponModel> yjCouponModelList;
    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "优惠券", true);
        getMyCoupon(true);
    }
    private void initUI(){
        if (yjCouponModelList != null){
            StringUtils.Log(TAG,"yjCouponModelList = "+yjCouponModelList.toString());
        }

        if (yjCouponModelList != null && yjCouponModelList.size() == 0){
            relayout_my_coupon.setBackgroundColor(getResources().getColor(R.color.huise));
            mMsgListView.setVisibility(View.GONE);
            my_cards_nothing.setVisibility(View.VISIBLE);
        }else {
            relayout_my_coupon.setBackgroundColor(getResources().getColor(R.color.white));
            mMsgListView.setVisibility(View.VISIBLE);
            my_cards_nothing.setVisibility(View.GONE);
        }
        initXListView();
        getNotifyListener();
    }

    private void initXListView() {
        //MsgListView
        if (mVAdapter == null) {
            mVAdapter = new VAdapter(this, mMsgListView);
        } else {
            mVAdapter.notifyDataSetChanged();
        }

        mMsgListView.setAdapter(mVAdapter);
        mMsgListView.setPullRefreshEnable(true);
        mMsgListView.setPullLoadEnable(true);
        mMsgListView.setXListViewListener(new MsgListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                paginationInfo = null;
                if (yjCouponModelList != null) {
                    yjCouponModelList.clear();
                }
                getMyCoupon(false);
                mMsgListView.stopRefresh();
                mMsgListView.setRefreshTime(System.currentTimeMillis());
            }

            @Override
            public void onLoadMore() {
                if (paginationInfo != null) {
                    if (paginationInfo.getCurrentPage() <= paginationInfo.getTotalPages()) {
                        getAddLoadMore(++page);
                    } else if (paginationInfo.getCurrentPage() > paginationInfo.getTotalPages()) {
                        showToast(getResources().getString(R.string.load_more_nothing));
                    }
                } else {
                    getAddLoadMore(++page);
                }
            }
        });

    }

    private void getNotifyListener() {
        addListener(YJNotifyTag.MY_COUPON_LIST_LOAD_MORE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjCouponModelList.addAll((Collection<? extends YJCouponModel>) o);
                if (page > paginationInfo.getTotalPages()) {
                    showToast(getResources().getString(R.string.load_more_nothing));
                }

            }

        });
        addListener(YJNotifyTag.MY_COUPON_LIST_PAGE_INFO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paginationInfo = (PaginationInfo) value;
            }
        });
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJCouponItem();
    }
    @Override
    public int getAdapterItemCount(View view) {
        if (yjCouponModelList != null && yjCouponModelList.size() >0){
            return  yjCouponModelList.size();
        }else {
            return 0;
        }
    }
    @Override
    public void onClick(View view) {
        if (view == re_coupon_explain){
            startActivity(YJCouponExplainActivity.class);
        }
    }

    @VLayoutTag(R.layout.my_coupon_item)
    class YJCouponItem extends AVAdapterItem {
        @VViewTag(R.id.txt_rmb_num)
        private TextView txt_rmb_num;
        @VViewTag(R.id.txt_rmb)
        private TextView txt_rmb;
        @VViewTag(R.id.txt_name)
        private TextView txt_name;
        @VViewTag(R.id.txt_coupon_info)
        private TextView txt_coupon_info;
        @VViewTag(R.id.txt_time)
        private TextView txt_time;
        @VViewTag(R.id.img_coupon_status)
        private ImageView img_coupon_status;
        @VViewTag(R.id.re_left)
        private RelativeLayout re_left;
        @Override
        public void update(final int position) {
            super.update(position);
            if (yjCouponModelList != null){

                RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams)re_left.getLayoutParams();
                linearParams.width = YJConfig.mScreenWidth/2;
                re_left.setLayoutParams(linearParams);

                YJCouponModel yjCouponModel = yjCouponModelList.get(position);
                if (!TextUtils.isEmpty(yjCouponModel.getUseDesc())){
                    txt_coupon_info.setText(yjCouponModel.getUseDesc());
                }
                if(!TextUtils.isEmpty(yjCouponModel.getTimeLimit()) && yjCouponModel.getTimeLimit().equals("Restrict")){
                    txt_time.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(yjCouponModel.getCanUseStopTime())){
                        txt_time.setText("有效期至"+TimeUtil.getYearTime(Long.parseLong(yjCouponModel.getCanUseStopTime())));
                    }
                }else if (!TextUtils.isEmpty(yjCouponModel.getTimeLimit()) && yjCouponModel.getTimeLimit().equals("UnLimited")){
                    txt_time.setVisibility(View.GONE);
                }


                if (!TextUtils.isEmpty(yjCouponModel.getCouponType()) && yjCouponModel.getCouponType().equals("Money") ){
                    if (!TextUtils.isEmpty(yjCouponModel.getPreferentialMoney()) ){
                        txt_rmb.setVisibility(View.VISIBLE);
                        txt_rmb_num.setText(yjCouponModel.getPreferentialMoney());
                    }
                    txt_name.setText("现金优惠券，仅限购课");
                }else if (!TextUtils.isEmpty(yjCouponModel.getCouponType()) && yjCouponModel.getCouponType().equals("Discount") ){
                    if (!TextUtils.isEmpty(yjCouponModel.getDiscountDegree()) ){
                        txt_rmb.setVisibility(View.GONE);
                        txt_rmb_num.setText(Float.parseFloat(yjCouponModel.getDiscountDegree())*10 + "折");
                    }
                    txt_name.setText("折扣优惠券，仅限购课");
                }
                if (!TextUtils.isEmpty(yjCouponModel.getCouponStatus()) && yjCouponModel.getCouponStatus().equals("Overdue") ){
                    img_coupon_status.setImageResource(R.mipmap.img_coupon_time_out);
                    txt_rmb_num.setTextColor(getResources().getColor(R.color.coupon_gray));
                    txt_rmb.setTextColor(getResources().getColor(R.color.coupon_gray));
                    txt_name.setTextColor(getResources().getColor(R.color.coupon_gray));
                    txt_coupon_info.setTextColor(getResources().getColor(R.color.coupon_gray));
                    txt_time.setTextColor(getResources().getColor(R.color.coupon_gray));

                }else if(!TextUtils.isEmpty(yjCouponModel.getCouponStatus()) && yjCouponModel.getCouponStatus().equals("CanUse") ){
                    if (!TextUtils.isEmpty(yjCouponModel.getIsUsed()) && yjCouponModel.getIsUsed().equals("Yes")){
                        img_coupon_status.setImageResource(R.mipmap.img_coupon_has_use);
                        txt_rmb_num.setTextColor(getResources().getColor(R.color.coupon_gray));
                        txt_rmb.setTextColor(getResources().getColor(R.color.coupon_gray));
                        txt_name.setTextColor(getResources().getColor(R.color.coupon_gray));
                        txt_coupon_info.setTextColor(getResources().getColor(R.color.coupon_gray));
                        txt_time.setTextColor(getResources().getColor(R.color.coupon_gray));
                    }else if (!TextUtils.isEmpty(yjCouponModel.getIsUsed()) && yjCouponModel.getIsUsed().equals("No")){
                        img_coupon_status.setImageResource(R.mipmap.img_coupon_no_use);
                        txt_rmb_num.setTextColor(getResources().getColor(R.color.yellow_text));
                        txt_rmb.setTextColor(getResources().getColor(R.color.yellow_text));
                        txt_name.setTextColor(getResources().getColor(R.color.black));
                        txt_coupon_info.setTextColor(getResources().getColor(R.color.black));
                        txt_time.setTextColor(getResources().getColor(R.color.gray_text));
                    }
                }
            }
        }
    }

    public void getMyCoupon(final boolean isInitData) {

        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MY_COUPON, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjCouponModelList = JSON.parseArray(jsonData.getString("couponList"), YJCouponModel.class);
                            if (isInitData){
                                initUI();
                            }else {
                                if (mVAdapter == null) {
                                    mVAdapter = new VAdapter(AYJCouponActivity.this, mMsgListView);
                                    mMsgListView.setAdapter(mVAdapter);
                                } else {
                                    mVAdapter.notifyDataSetChanged();
                                }
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


    public abstract void getAddLoadMore(int page);
}
