package com.youjing.yjeducation.ui.dispaly.dialog;

import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.model.YJCouponModel;
import com.youjing.yjeducation.util.TimeUtil;

import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
@VLayoutTag(R.layout.dialog_coupon)
public  class AYJCouponDialog extends AVDialog implements IVClickDelegate ,IVAdapterDelegate {
    @VViewTag(R.id.rl_dialog_bg)
    private RelativeLayout rl_dialog_bg;
    @VViewTag(R.id.lv_coupon)
    private ListView lv_coupon;
    @VViewTag(R.id.btn_ok)
    private Button btn_ok;


    protected List<YJCouponModel> yjCouponModelList;
    public static final VParamKey<List<YJCouponModel>> YJ_COUPON_MODEL_LIST = new VParamKey<List<YJCouponModel>>(null);
    public static final VParamKey<Integer> COUPON_POSITION = new VParamKey<Integer>(null);
    private String TAG = "AYJCouponDialog";
    private  VAdapter vadapter = null;
    private int mCouponPosition = -1;



    @Override
    protected void onLoadedView() {
        yjCouponModelList = getTransmitData(YJ_COUPON_MODEL_LIST);
        mCouponPosition = getTransmitData(COUPON_POSITION);
        if (yjCouponModelList.get(0)!= null){
            yjCouponModelList.add(0,null);
        }
        StringUtils.Log(TAG, "yjCouponModelList=" + yjCouponModelList.toString());
        if(vadapter == null){
            vadapter = new VAdapter(this,lv_coupon);
            lv_coupon.setAdapter(vadapter);
        }else {
            vadapter.notifyDataSetChanged();
        }
        lv_coupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                StringUtils.Log(TAG,"i="+i);
                mPosition = i;
                selectIndex = i;
                vadapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view == rl_dialog_bg){
            close();
        }else if(view == btn_ok){
            if(yjCouponModelList != null &&  yjCouponModelList.size() >0){
                StringUtils.Log(TAG, "mPosition=" +mPosition);
                if (mPosition == 0){
                    notifyListener(YJNotifyTag.COUPON_MODEL,null);
                }else {
                    notifyListener(YJNotifyTag.COUPON_MODEL,yjCouponModelList.get(mPosition));
                    StringUtils.Log(TAG, "yjCouponModelList.get(mPosition)=" +yjCouponModelList.get(mPosition));
                }
            }
            close();
        }
    }
    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJCouponItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjCouponModelList != null && yjCouponModelList.size() > 0){
            return yjCouponModelList.size();
        }
        return 0;
    }

    private boolean isFirst = true;
    private int mPosition = 1;
    private int selectIndex = -1;
    @VLayoutTag(R.layout.dialog_coupon_item)
    class YJCouponItem extends AVAdapterItem {
        @VViewTag(R.id.rb_ispay)
        private RadioButton rb_ispay;
        @VViewTag(R.id.txt_rmb_money)
        private TextView txt_rmb_money;
        @VViewTag(R.id.txt_time)
        private TextView txt_time;
        @VViewTag(R.id.txt_info)
        private TextView txt_info;

        @VViewTag(R.id.re_no_coupon)
        private RelativeLayout  re_no_coupon;
        @VViewTag(R.id.re_top)
        private RelativeLayout  re_top;
        @VViewTag(R.id.re_bottom)
        private RelativeLayout  re_bottom;
        @Override
        public void update(int position) {
            if (position == 0){
                re_no_coupon.setVisibility(View.VISIBLE);
                re_top.setVisibility(View.GONE);
                re_bottom.setVisibility(View.GONE);
            }else {
                re_no_coupon.setVisibility(View.GONE);
                re_top.setVisibility(View.VISIBLE);
                re_bottom.setVisibility(View.VISIBLE);
            }

            if (mCouponPosition == -1){
                if (position == 1){
                    rb_ispay.setChecked(true);
                }else {
                    rb_ispay.setChecked(false);
                }
            }else {
                if (position == mCouponPosition ){
                    rb_ispay.setChecked(true);
                }else {
                    rb_ispay.setChecked(false);
                }
            }
            StringUtils.Log(TAG,"mCouponPosition="+mCouponPosition);
            if (selectIndex != -1){
                if (selectIndex == position){
                    rb_ispay.setChecked(true);
                }else {
                    rb_ispay.setChecked(false);
                }
            }
            if (yjCouponModelList != null){
                YJCouponModel yjCouponModel = yjCouponModelList.get(position);

                if (yjCouponModel != null){
                    if(!TextUtils.isEmpty(yjCouponModel.getTimeLimit()) && yjCouponModel.getTimeLimit().equals("Restrict")){
                        if (!TextUtils.isEmpty(yjCouponModel.getCanUseStopTime())){
                            txt_time.setText("有效期至"+TimeUtil.getYearTime(Long.parseLong(yjCouponModel.getCanUseStopTime())));
                        }
                    }else if (!TextUtils.isEmpty(yjCouponModel.getTimeLimit()) && yjCouponModel.getTimeLimit().equals("UnLimited")){
                        txt_time.setText("本券长期有效");
                    }

                    if (!TextUtils.isEmpty(yjCouponModel.getCouponType()) && yjCouponModel.getCouponType().equals("Money") ){
                        if (!TextUtils.isEmpty(yjCouponModel.getPreferentialMoney()) ){
                            txt_rmb_money.setText("￥"+yjCouponModel.getPreferentialMoney());
                        }
                    }else if (!TextUtils.isEmpty(yjCouponModel.getCouponType()) && yjCouponModel.getCouponType().equals("Discount") ){
                        //折扣卷
                        if (!TextUtils.isEmpty(yjCouponModel.getDiscountDegree()) ){
                            txt_rmb_money.setText(Float.parseFloat(yjCouponModel.getDiscountDegree())*10 +"折");
                        }
                    }

                    if(!TextUtils.isEmpty(yjCouponModel.getMoneyLimit())){
                        if (!TextUtils.isEmpty(yjCouponModel.getMoneyLimit())){
                            txt_info.setText("满"+yjCouponModel.getMoneyLimit()+"元可用");
                        }
                    }
                }
            }
        }
    }
}
