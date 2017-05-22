package com.youjing.yjeducation.ui.dispaly.activity;

import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCardModel;
import com.youjing.yjeducation.model.YJConsumeHistoryModel;
import com.youjing.yjeducation.ui.actualize.activity.YJConsumeHistoryActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.wiget.MsgListView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/4/5
 * Time 16:39
 */
@VLayoutTag(R.layout.activity_consume_history)
public abstract class AYJConsumeHistoryActivity extends YJBaseActivity implements IVAdapterDelegate {
    @VViewTag(R.id.msgListView)
    protected MsgListView mMsgListView;
    protected int page = 1;
    protected PaginationInfo paginationInfo;


    private VAdapter mVAdapter;

    private String TAG = "AYJConsumeHistoryActivity";
    private List<YJConsumeHistoryModel> yjConsumeHistoryModelList = new ArrayList<YJConsumeHistoryModel>();

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "记账本", true);
        initData();
    }

    private void initUI(){
        if (yjConsumeHistoryModelList.size() == 0) {
            findViewById(R.id.cunsume_history_layout).setBackgroundColor(getResources().getColor(R.color.huise));
            mMsgListView.setVisibility(View.GONE);
            findViewById(R.id.comsume_history_nothing).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.cunsume_history_layout).setBackgroundColor(getResources().getColor(R.color.white));
            mMsgListView.setVisibility(View.VISIBLE);
            findViewById(R.id.comsume_history_nothing).setVisibility(View.GONE);
        }
        initXListView();
        getNotifyListener();
    }
    private void initData() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_CONSUM_HISTORY, null, true, new TextHttpResponseHandler() {
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
                             yjConsumeHistoryModelList = JSON.parseArray(jsonData.getString("coinLogList"), YJConsumeHistoryModel.class);
                            if (yjConsumeHistoryModelList != null){
                                initUI();
                            }else {
                                showToast(getString(R.string.no_net_work));
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
                yjConsumeHistoryModelList.clear();
                initData();
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

            }
        });
    }

    private void getNotifyListener() {

        addListener(YJNotifyTag.CONSUM_HISTORY_LIST_LOAD_MORE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjConsumeHistoryModelList.addAll((Collection<? extends YJConsumeHistoryModel>) o);
                if (page > paginationInfo.getTotalPages()) {
                    showToast(getResources().getString(R.string.load_more_nothing));
                }

            }

        });
        addListener(YJNotifyTag.CONSUM_HISTORY_LIST_PAGE_INFO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paginationInfo = (PaginationInfo) value;
            }
        });
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJConsumeHistoryListItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjConsumeHistoryModelList != null) {
            return yjConsumeHistoryModelList.size();
        } else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.activity_consume_history_item)
    class YJConsumeHistoryListItem extends AVAdapterItem {
        @VViewTag(R.id.txt_whale_money)
        private TextView mTxt_whale_money;
        @VViewTag(R.id.txt_time)
        private TextView mTxt_time;
        @VViewTag(R.id.txt_name)
        private TextView mTxt_name;

        @Override
        public void update(int position) {
            super.update(position);
            if (yjConsumeHistoryModelList != null) {
                mTxt_name.setText(yjConsumeHistoryModelList.get(position).getExtMsg());
                mTxt_time.setText(TimeUtil.getTime(Long.parseLong(yjConsumeHistoryModelList.get(position).getCreateDate())));

                if (yjConsumeHistoryModelList.get(position).getCoinFlow().equals("IN")) {
                    mTxt_whale_money.setText("+" + yjConsumeHistoryModelList.get(position).getCoin());
                } else if (yjConsumeHistoryModelList.get(position).getCoinFlow().equals("OUT")) {
                    mTxt_whale_money.setText("-" + yjConsumeHistoryModelList.get(position).getCoin());
                }
            } else {
                mTxt_name.setText("");
                mTxt_time.setText("");
                mTxt_whale_money.setText("");
            }

        }
    }

    public abstract void getAddLoadMore(int page);
}
