package com.youjing.yjeducation.ui.dispaly.activity;

import android.annotation.TargetApi;
import android.os.Build;

import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJApplication;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCardModel;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.util.YjGetUserInfo;
import com.youjing.yjeducation.wiget.CustomToast;
import com.youjing.yjeducation.wiget.MsgListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 21:00
 */
@VLayoutTag(R.layout.activity_my_cards)
public abstract class AYJMyCardsActivity extends BaseSwipeBackActivity implements IVClickDelegate, IVAdapterDelegate ,YjGetUserInfo.UserCoin{
    @VViewTag(R.id.msgListView)
    protected MsgListView mMsgListView;
    private YJApplication application;

    protected List<YJCardModel> yjCardModelList;
    private VAdapter mVAdapter;
    protected int page = 1;
    protected PaginationInfo paginationInfo;

    private String TAG = "AYJMyCardsActivity";
    private YjGetUserInfo yjGetUserInfo;

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "我的卡包", true);
        yjGetUserInfo = new YjGetUserInfo(this,this);
        getMyCard(true);
    }
    private void initUI(){
        if (yjCardModelList.size() == 0) {
            findViewById(R.id.relayout_my_cards).setBackgroundColor(getResources().getColor(R.color.huise));
            mMsgListView.setVisibility(View.GONE);
            findViewById(R.id.my_cards_nothing).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.relayout_my_cards).setBackgroundColor(getResources().getColor(R.color.white));
            mMsgListView.setVisibility(View.VISIBLE);
            findViewById(R.id.my_cards_nothing).setVisibility(View.GONE);
        }
        initXListView();
        getNotifyListener();
    }
    protected void initXListView() {
        //MsgListView
        if (mVAdapter == null) {
            mVAdapter = new VAdapter(this, mMsgListView);
            mMsgListView.setAdapter(mVAdapter);
        } else {
            mVAdapter.notifyDataSetChanged();
        }

        mMsgListView.setPullRefreshEnable(true);
        mMsgListView.setPullLoadEnable(true);
        mMsgListView.setXListViewListener(new MsgListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                paginationInfo = null;
                if (yjCardModelList != null) {
                    yjCardModelList.clear();
                }
                getMyCard(false);
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
        mMsgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    return;
                }
                if (yjCardModelList.get(i - 1).getStatus().equals("NORMAL")) {
                    activationCard(view, i - 1);
                } else if (yjCardModelList.get(i - 1).getStatus().equals("USED")) {
                    showToast("礼卡已激活");
                } else {
                    showToast("礼卡已过期");
                }
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
    @Override
    public void getCoin(IVActivity view, YJUser mYjUser) {
        YJGlobal.setYjUser(mYjUser);
    }
    private void getNotifyListener() {

        addListener(YJNotifyTag.MY_CARD_LIST_LOAD_MORE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjCardModelList.addAll((Collection<? extends YJCardModel>) o);
                if (page > paginationInfo.getTotalPages()) {
                    showToast(getResources().getString(R.string.load_more_nothing));
                }

            }

        });
        addListener(YJNotifyTag.MY_CARD_LIST_PAGE_INFO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paginationInfo = (PaginationInfo) value;
            }
        });
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJOrderItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjCardModelList != null) {
            return yjCardModelList.size();
        } else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.my_cards_item)
    class YJOrderItem extends AVAdapterItem {
        @VViewTag(R.id.my_cards_item_lay_item)
        private RelativeLayout mLay_item;
        @VViewTag(R.id.txt_card_num)
        private TextView mTxt_card_num;
        @VViewTag(R.id.txt_time)
        private TextView mTxt_time;

        String createDate = null;
        String useDate = null;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void update(final int position) {
            super.update(position);
            if (yjCardModelList != null) {

                if (yjCardModelList.get(position).getStatus().equals("NORMAL")) {
                    if (yjCardModelList.get(position).getTimeLimit().equals("UNLIMITED")) {//无限日期
                        mLay_item.setBackgroundResource(R.mipmap.img_card_no_activate_bg);
                        mTxt_card_num.setText(yjCardModelList.get(position).getPrice());
//                        activationCard(mLay_item, position);
                        mTxt_time.setVisibility(View.GONE);
                    } else {//有限
                        createDate = TimeUtil.getTime(yjCardModelList.get(position).getStartDate());
                        useDate = TimeUtil.getTime(yjCardModelList.get(position).getStopDate());
                        if (System.currentTimeMillis() > yjCardModelList.get(position).getStopDate()) {
                            mLay_item.setBackgroundResource(R.mipmap.img_card_date_bg);
                            mTxt_card_num.setText(yjCardModelList.get(position).getPrice());
                            mTxt_time.setVisibility(View.VISIBLE);
                            mTxt_time.setText("有效期：" + createDate + "至" + useDate);
                        } else {
                            mLay_item.setBackgroundResource(R.mipmap.img_card_no_activate_bg);
                            mTxt_card_num.setText(yjCardModelList.get(position).getPrice());
//                            activationCard(mLay_item, position);
                            mTxt_time.setVisibility(View.VISIBLE);
                            mTxt_time.setText("有效期：" + createDate + "至" + useDate);
                        }

                    }
//                    mLay_item.setBackground(getResources().getDrawable(R.mipmap.img_card_no_activate_bg));
//                    activationCard(mLay_item, position);
//                    if (yjCardModelList.get(position).getUseTime() != null) {
//                        if (System.currentTimeMillis() > yjCardModelList.get(position).getUseTime()) {
//                            mLay_item.setBackground(getResources().getDrawable(R.mipmap.img_card_date_bg));
//                        }
//                        mTxt_card_num.setText(yjCardModelList.get(position).getPrice());
//                        String createDate = TimeUtil.getTime(yjCardModelList.get(position).getCreateDate());
//                        String useDate = TimeUtil.getTime(yjCardModelList.get(position).getUseTime());
//                        mTxt_time.setText("有效期：" + createDate + "至" + useDate);
//                    }else{
//
//                    }
                } else if (yjCardModelList.get(position).getStatus().equals("USED")) {// 已激活
                    if (yjCardModelList.get(position).getTimeLimit().equals("UNLIMITED")) {
                        mLay_item.setBackgroundResource(R.mipmap.img_card_activated_bg);
                        mTxt_card_num.setText(yjCardModelList.get(position).getPrice());
                        mTxt_time.setVisibility(View.GONE);
                    } else {
                        createDate = TimeUtil.getTime(yjCardModelList.get(position).getStartDate());
                        useDate = TimeUtil.getTime(yjCardModelList.get(position).getStopDate());
                        mLay_item.setBackgroundResource(R.mipmap.img_card_activated_bg);
                        mTxt_card_num.setText(yjCardModelList.get(position).getPrice());
                        mTxt_time.setText("有效期：" + createDate + "至" + useDate);
                        mTxt_time.setVisibility(View.VISIBLE);
                    }

                }else {
                    if (yjCardModelList.get(position).getTimeLimit().equals("UNLIMITED")) {//无限日期
                        mLay_item.setBackgroundResource(R.mipmap.img_card_date_bg);
                        mTxt_card_num.setText(yjCardModelList.get(position).getPrice());
//                        activationCard(mLay_item, position);
                        mTxt_time.setVisibility(View.GONE);
                    } else {//有限
                        createDate = TimeUtil.getTime(yjCardModelList.get(position).getStartDate());
                        useDate = TimeUtil.getTime(yjCardModelList.get(position).getStopDate());
                         mLay_item.setBackgroundResource(R.mipmap.img_card_date_bg);
                         mTxt_card_num.setText(yjCardModelList.get(position).getPrice());
                        mTxt_time.setVisibility(View.VISIBLE);
                         mTxt_time.setText("有效期：" + createDate + "至" + useDate);


                    }
                }
                StringUtils.Log(TAG, "System.currentTimeMillis()=" + System.currentTimeMillis());
            }

        }



    }
    public void activationCard(final View view, final int position) {
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(final View view) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("giftCardId", yjCardModelList.get(position).getGiftCardId());
            StringUtils.Log("传入的id===", yjCardModelList.get(position).getGiftCardId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.ACTIVATE_CARD, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    StringUtils.Log("点击领取卡包数据=====", yjCardModelList.toString());
                    JSONObject json = null;
                    json = new JSONObject(s);
                    String code = json.getString("code");
                    if (code.equals("200")) {
                        view.setBackgroundResource(R.mipmap.img_card_activated_bg);
                        String money = json.getJSONObject("data").getString("giftCardCoin");

                        YJGlobal.setYjUser(null);
                        yjGetUserInfo.getUserCoinInfo(AYJMyCardsActivity.this);
                        CustomToast.toastCards(getContext(),money);
                        getMyCard(false);
                        notifyListener(YJNotifyTag.USER_COIN, "");
                    } else if (code.equals("300")) {
                         StringUtils.tip(getApplicationContext(), getString(R.string.code_300));
                    } else if (code.equals("400")) {
                         StringUtils.tip(getApplicationContext(), getString(R.string.code_400));
                    } else if (code.equals("401")) {
                         StringUtils.tip(getApplicationContext(), getString(R.string.code_401));
                    } else if (code.equals("402")) {
                         StringUtils.tip(getApplicationContext(), getString(R.string.code_402));
                    } else if (code.equals("403")) {
                        StringUtils.tip(getApplicationContext(), getString(R.string.code_403));
                    } else if (code.equals("404")) {
                        StringUtils.tip(getApplicationContext(), getString(R.string.code_404));
                    } else if (code.equals("405")) {
                         StringUtils.tip(getApplicationContext(), getString(R.string.code_405));
                    } else if (code.equals("500")) {
                         StringUtils.tip(getApplicationContext(), getString(R.string.code_500));
                    } else if (code.equals("600")) {
                        startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                        finishAll();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//                }
//            });
    }

    public void getMyCard(final boolean isinitData) {

        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_CARD, null, true, new TextHttpResponseHandler() {
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
                            yjCardModelList = JSON.parseArray(jsonData.getString("giftCardList"), YJCardModel.class);
                            if (isinitData){
                                initUI();
                            }else {
                                if (mVAdapter == null) {
                                    mVAdapter = new VAdapter(AYJMyCardsActivity.this, mMsgListView);
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