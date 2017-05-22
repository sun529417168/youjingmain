package com.youjing.yjeducation.ui.dispaly.activity;

import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJMessageModel;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMessageDetails;
import com.youjing.yjeducation.ui.actualize.dialog.YJMessageSelectDialog;
import com.youjing.yjeducation.util.ClickUtil;
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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 21:00
 */
@VLayoutTag(R.layout.activity_system_message)
public abstract class AYJSystemMessageActivity extends BaseSwipeBackActivity implements IVClickDelegate, IVAdapterDelegate {
    @VViewTag(R.id.msgListView)
    private MsgListView mMsgListView;
    @VViewTag(R.id.rl_back)
    private RelativeLayout mRl_back;
    @VViewTag(R.id.txt_selcet)
    private TextView mTxt_selcet;
    @VViewTag(R.id.system_msg_nothing)
    private RelativeLayout mSystem_msg_nothing;// 没有数据的时候

    private VAdapter mVAdapter;
    protected int page = 1;
    protected PaginationInfo paginationInfo;

    private List<YJMessageModel> yjMessageModelList;
    private final String TAG = "AYJSystemMessageActivity";

    @Override
    protected void onLoadedView() {
        if (mVAdapter == null) {
            mVAdapter = new VAdapter(this, mMsgListView);
        } else {
            mVAdapter.notifyDataSetChanged();
        }
        initData();
    }
    private  void initUI(){
        if (yjMessageModelList == null || yjMessageModelList.size() == 0) {
            mSystem_msg_nothing.setVisibility(View.VISIBLE);
            mMsgListView.setVisibility(View.GONE);
        }
        initXListView();
        getNotifyListener();
    }
    @Override
    public void onClick(View view) {
        if (view == mRl_back) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            this.finish();
        } else if (view == mTxt_selcet) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            YJMessageSelectDialog yjMessageSelectDialog = new YJMessageSelectDialog();
            showDialog(yjMessageSelectDialog);
        }
    }

    public void initData() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("gradeId", YJGlobal.getMy_grade_id());
            objectMap.put("subjectId", YJGlobal.getMy_subjectId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MESSAGE, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjMessageModelList = JSON.parseArray(jsonData.getString("noticeList"), YJMessageModel.class);
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

    private void initXListView() {
        //MsgListView
        mMsgListView.setAdapter(mVAdapter);
        mMsgListView.setPullRefreshEnable(true);
        mMsgListView.setPullLoadEnable(true);
        mMsgListView.setXListViewListener(new MsgListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                paginationInfo = null;
                yjMessageModelList.clear();
                getMessage();
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
                    StringUtils.Log("当前页数", (++page) + "");
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
                if ("No".equals(yjMessageModelList.get(i - 1).getIsRead())) {
                    getUpdateMessage(yjMessageModelList.get(i - 1).getAnnouncementId());
                }
                startActivity(createIntent(YJMessageDetails.class, createTransmitData(YJMessageDetails.YJ_MESSAGE_MODEL, yjMessageModelList.get(i - 1))));
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getMessage();
    }

    private void getNotifyListener() {

        addListener(YJNotifyTag.MESSAGE_LIST_LOAD_MORE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjMessageModelList.addAll((Collection<? extends YJMessageModel>) o);
                if (page > paginationInfo.getTotalPages()) {
                    showToast(getResources().getString(R.string.load_more_nothing));
                }

            }

        });
        addListener(YJNotifyTag.MESSAGE_LIST_PAGE_INFO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paginationInfo = (PaginationInfo) value;
            }
        });
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJMessageItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjMessageModelList != null) {
            return yjMessageModelList.size();
        } else {
            return 0;
        }

    }

    public void getMessage() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("gradeId", YJGlobal.getMy_grade_id());
            objectMap.put("subjectId", YJGlobal.getMy_subjectId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MESSAGE, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMsgListView.stopRefresh();
                mMsgListView.setRefreshTime(System.currentTimeMillis());
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            mMsgListView.stopRefresh();
                            mMsgListView.setRefreshTime(System.currentTimeMillis());
                            yjMessageModelList = JSON.parseArray(jsonData.getString("noticeList"), YJMessageModel.class);
                            mVAdapter.notifyDataSetChanged();
                            mMsgListView.setAdapter(mVAdapter);
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

    /**
     * stt
     * 方法说明：点击item的消息
     * 调用是否已读的接口
     * 创建时间：2016.5.15
     */
    public void getUpdateMessage(String messageId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("messageId", messageId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_UPDATE_MESSAGE, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            break;
                        case 300:
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


    @VLayoutTag(R.layout.system_message_item)
    class YJMessageItem extends AVAdapterItem {
        @VViewTag(R.id.txt_message_time)
        private TextView mTxt_message_time;
        @VViewTag(R.id.txt_message_head)
        private TextView mTxt_message_head;
        @VViewTag(R.id.txt_message_content)
        private TextView mTxt_message_content;
        @VViewTag(R.id.txt_message_type)
        private TextView mTxt_message_type;
        @VViewTag(R.id.img_message_point)
        protected ImageView mImg_message_point;


        public String a = "";

        @Override
        public void update(final int position) {
            super.update(position);
            if (yjMessageModelList != null) {
                mTxt_message_head.setText(yjMessageModelList.get(position).getTitle());
                mTxt_message_content.setText(yjMessageModelList.get(position).getContentText());

                if ("Yes".equals(yjMessageModelList.get(position).getIsRead())) {
                    mImg_message_point.setVisibility(View.INVISIBLE);
                } else if ("No".equals(yjMessageModelList.get(position).getIsRead())) {
                    mImg_message_point.setVisibility(View.VISIBLE);
                }
                String[] timeArray = TimeUtil.timeAgo(yjMessageModelList.get(position).getCreateDate()).split(",");
                if (Integer.parseInt(timeArray[0]) > 2) {
                    mTxt_message_time.setText(TimeUtil.getMonthTime(yjMessageModelList.get(position).getCreateDate()));
                } else if (Integer.parseInt(timeArray[0]) <= 2 && Integer.parseInt(timeArray[0]) >= 1) {
                    mTxt_message_time.setText(timeArray[0] + "天前");
                } else if (Integer.parseInt(timeArray[1]) <= 24 && Integer.parseInt(timeArray[1]) != 0) {
                    mTxt_message_time.setText(timeArray[1] + "小时前");
                } else if (Integer.parseInt(timeArray[2]) <= 60) {
                    mTxt_message_time.setText(timeArray[1] + "分钟前");
                } else if (Integer.parseInt(timeArray[2]) == 0) {
                    mTxt_message_time.setText("现在");
                }

            }


        }

    }

    public abstract void getAddLoadMore(int page);
}
