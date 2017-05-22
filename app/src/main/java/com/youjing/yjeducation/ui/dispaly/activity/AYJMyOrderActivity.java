package com.youjing.yjeducation.ui.dispaly.activity;

import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJOrderModel;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMyWalletActivity;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.SharedUtil;
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
import org.vwork.utils.notification.IVNotificationListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 21:00
 */
@VLayoutTag(R.layout.activity_my_order)
public abstract class AYJMyOrderActivity extends BaseSwipeBackActivity implements IVClickDelegate, IVAdapterDelegate {
    @VViewTag(R.id.msgListView)
    protected MsgListView mMsgListView;
    protected List<YJOrderModel> yjOrderModelList = new ArrayList<YJOrderModel>();
    private VAdapter mVAdapter;
    protected int page = 1;
    protected PaginationInfo paginationInfo;

    private String TAG = "AYJMyOrderActivity";

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "我的订单", true);
        if (SharedUtil.getInteger(getContext(), "baseIndex", 0) != -1) {
            SharedUtil.setInteger(getContext(), "baseIndex", SharedUtil.getInteger(getContext(), "baseIndex", 0) + 1);
        }
        initData();
    }
    private void initUI(){
        if (yjOrderModelList.size() == 0) {
            findViewById(R.id.relayout_my_order).setBackgroundColor(getResources().getColor(R.color.huise));
            mMsgListView.setVisibility(View.GONE);
            findViewById(R.id.my_order_nothing).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.relayout_my_order).setBackgroundColor(getResources().getColor(R.color.white));
            mMsgListView.setVisibility(View.VISIBLE);
            findViewById(R.id.my_order_nothing).setVisibility(View.GONE);
        }
        if (yjOrderModelList != null) {
            StringUtils.Log(TAG, yjOrderModelList.toString());
        }
        bindNotifyListener();
        initXListView();

    }

    private  void initData(){
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MY_ORDER, null, true, new TextHttpResponseHandler() {
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
                            yjOrderModelList = JSON.parseArray(jsonData.getString("orderList"), YJOrderModel.class);
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

    @Override
    public void onClick(View view) {

    }

    protected void initXListView() {
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
                yjOrderModelList.clear();
                getMyOrder();
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
        mMsgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }


    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJOrderItem();
    }

    @Override
    public int getAdapterItemCount(View view) {

        if (yjOrderModelList == null) {
            return 0;
        } else {
            return yjOrderModelList.size();
        }

    }

    @VLayoutTag(R.layout.my_order_item)
    class YJOrderItem extends AVAdapterItem {
        @VViewTag(R.id.txt_name)
        private TextView mTxt_name;
        @VViewTag(R.id.txt_teacher_name)
        private TextView mTxt_teacher_name;
        @VViewTag(R.id.txt_rmb_num)
        private TextView mTxt_rmb_num;
        @VViewTag(R.id.txt_order_num)
        private TextView mTxt_order_num;
        @VViewTag(R.id.txt_buy_info)
        private TextView mTxt_buy_info;
        @VViewTag(R.id.txt_order_time)
        private TextView mTtxt_order_time;
        @VViewTag(R.id.btn_pay_now)
        private Button mBtn_pay_now;
        @VViewTag(R.id.img_teacher_head)
        private ImageView img_teacher_head;

        @Override
        public void update(final int position) {
            super.update(position);
            if (yjOrderModelList != null) {
                mTxt_rmb_num.setText(String.valueOf(yjOrderModelList.get(position).getPayMoney()));
                mTxt_order_num.setText("订单号：" + yjOrderModelList.get(position).getOrderNumber());
                mTtxt_order_time.setText(TimeUtil.getTime(yjOrderModelList.get(position).getCreateDateTime()));

                if (yjOrderModelList.get(position).getOrderRecordType().equals("PAY_GOID")) {
                    img_teacher_head.setVisibility(View.GONE);
                    mTxt_teacher_name.setVisibility(View.GONE);
                    mTxt_name.setText("鲸币订单");

                    if (yjOrderModelList.get(position).getOrderStatus().equals("NOT_PAY")) {
                        mTxt_buy_info.setVisibility(View.GONE);
                        mBtn_pay_now.setVisibility(View.VISIBLE);
                        mBtn_pay_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ClickUtil.isFastDoubleClick()) {
                                    return;
                                }
                                getMyOrderInfo(yjOrderModelList.get(position));
                            }
                        });
                    } else if (yjOrderModelList.get(position).getOrderStatus().equals("SUCCESS_PAY")) {
                        mBtn_pay_now.setVisibility(View.GONE);
                        mTxt_buy_info.setVisibility(View.VISIBLE);
                        mTxt_buy_info.setText("已支付");
                    } else if (yjOrderModelList.get(position).getOrderStatus().equals("CANCEL_PAY")) {
                        mBtn_pay_now.setVisibility(View.GONE);
                        mTxt_buy_info.setVisibility(View.VISIBLE);
                        mTxt_buy_info.setText("已取消");
                    } else if (yjOrderModelList.get(position).getOrderStatus().equals("OVERDUE_PAY")) {
                        mBtn_pay_now.setVisibility(View.GONE);
                        mTxt_buy_info.setVisibility(View.VISIBLE);
                        mTxt_buy_info.setText("已过期");
                    }
                } else if (yjOrderModelList.get(position).getOrderRecordType().equals("BUY_COURSE")) {
                    img_teacher_head.setVisibility(View.VISIBLE);
                    mTxt_teacher_name.setVisibility(View.VISIBLE);
                    mTxt_name.setText(yjOrderModelList.get(position).getGoodsName());
                    mTxt_teacher_name.setText(yjOrderModelList.get(position).getTrueName());

                    if (yjOrderModelList.get(position).getOrderStatus().equals("NOT_PAY")) {
                        mTxt_buy_info.setVisibility(View.GONE);
                        mBtn_pay_now.setVisibility(View.VISIBLE);
                        mBtn_pay_now.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ClickUtil.isFastDoubleClick()) {
                                    return;
                                }
                                mPosition = position;
                                getMyOrderInfo(yjOrderModelList.get(position));
                            }
                        });
                    } else if (yjOrderModelList.get(position).getOrderStatus().equals("SUCCESS_PAY")) {
                        mBtn_pay_now.setVisibility(View.GONE);
                        mTxt_buy_info.setVisibility(View.VISIBLE);
                        mTxt_buy_info.setText("已支付");
                    } else if (yjOrderModelList.get(position).getOrderStatus().equals("CANCEL_PAY")) {
                        mBtn_pay_now.setVisibility(View.GONE);
                        mTxt_buy_info.setVisibility(View.VISIBLE);
                        mTxt_buy_info.setText("已取消");
                    } else if (yjOrderModelList.get(position).getOrderStatus().equals("OVERDUE_PAY")) {
                        mBtn_pay_now.setVisibility(View.GONE);
                        mTxt_buy_info.setVisibility(View.VISIBLE);
                        mTxt_buy_info.setText("已过期");
                    }
                }
            }
        }
    }
    private int mPosition = -1;

    private void bindNotifyListener() {

        addListener(YJNotifyTag.PAY_SUCESS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                StringUtils.Log(TAG, "jingbijingbijingbi");
                String info = (String) value;
                if (info.equals("jingbi")) {
                    startActivity(YJMyWalletActivity.class);
                    finish();
                } else {
                    if (mPosition != -1) {
                        getMyOrder();
                        getCourseCatalog(yjOrderModelList.get(mPosition).getGoodsId());
                    }
                }
            }
        });
        addListener(YJNotifyTag.MY_ORDER_LIST_LOAD_MORE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjOrderModelList.addAll((Collection<? extends YJOrderModel>) o);
                if (page > paginationInfo.getTotalPages()) {
                    showToast(getResources().getString(R.string.load_more_nothing));
                }

            }

        });
        addListener(YJNotifyTag.MY_ORDER_LIST_PAGE_INFO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paginationInfo = (PaginationInfo) value;
            }
        });
    }

    public abstract void getCourseCatalog(String courseId);

    public abstract void getMyOrder();

    public abstract void getAddLoadMore(int page);

    public abstract void getMyOrderInfo(YJOrderModel yjOrderModel);
}