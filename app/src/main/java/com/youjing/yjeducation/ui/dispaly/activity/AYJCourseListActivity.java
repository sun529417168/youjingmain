package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCourseListModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.talkfun.NetMonitor;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.ACache;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DialogUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.wiget.CourseClassifyPopWindow;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/14
 * Time 20:42
 */

@VLayoutTag(R.layout.activity_course_list)
public abstract class AYJCourseListActivity extends YJBaseActivity implements IVClickDelegate, IVAdapterDelegate {
    @VViewTag(R.id.txt_title)
    private TextView txt_title;
    @VViewTag(R.id.iv_arrow)
    private ImageView mIv_arrow;
    @VViewTag(R.id.rl_back)
    private RelativeLayout img_back;
    @VViewTag(R.id.layout_title)
    private RelativeLayout layout_title;

    protected MsgListView mMsgListView;
    protected boolean mIsLogin = false;
    private String TAG = "AYJCourseListActivity";
    private Handler mHandler;
    private CourseClassifyPopWindow mCourseClassifyPopWindow;
    private final String ACTION_NAME = "发送年级分类";
    private RotateAnimation animation, reverseAnimation;
    private List<YJCourseListModel> mYjCourseListModel;
    protected List<YJCourseModel> mYjCourseModelList;


    protected String mCourse;
    protected String courseTypeId;
    public int mPosition = -1;
    public int page = 1;
    protected PaginationInfo paginationInfo;
    private ACache mCache;
    protected Dialog progDialog;
    protected int netWork = -1;

    @Override
    protected void onLoadedView() {
        mCache = ACache.get(this);
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        mMsgListView = (MsgListView) findViewById(R.id.msgListView);
        mCourse = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("course", "选择课程");
        courseTypeId = getIntent().getStringExtra("courseTypeId");
        txt_title.setText(mCourse);
        getNotifyListener();
        getNotifyListeners();
        registerBoradcastReceiver();
        initAnimation();
        if (NetMonitor.isNetworkAvailable(this)) {//如果有网络走请求数据，否则本地缓存
            netWork = 0;
            progDialog = DialogUtil.showWaitDialog(getContext());//加载数据的loading 先注释掉 用的时候换样式
            getCourseListDetail(courseTypeId);
        } else {
            netWork = 1;
            mYjCourseModelList = (List<YJCourseModel>) StringUtils.arrayList((Object[]) mCache.getAsObject("mYjCourseModelList" + courseTypeId));
            if (mYjCourseModelList == null) {
                findViewById(R.id.activity_course_list_nothing).setVisibility(View.VISIBLE);
                mMsgListView.setVisibility(View.GONE);
            } else {
                initXListView();
            }
        }
        mHandler = new Handler();
    }

    public void getCourseListDetail(final String courseTypeId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", YJGlobal.getMy_subjectId());
            objectMap.put("gradeId", YJGlobal.getMy_grade_id());
            objectMap.put("courseTypeId", courseTypeId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_LIST, objectMap, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                    progDialog.dismiss();
                }
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                    progDialog.dismiss();
                }
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            mYjCourseListModel = JSON.parseArray(jsonData.getString("knowledgeList"), YJCourseListModel.class);
                            mYjCourseModelList = getYjCourseModel(mYjCourseListModel);
                            mCache.put("mYjCourseModelList" + courseTypeId, mYjCourseModelList.toArray());
                            if (mYjCourseModelList != null) {
                                initXListView();
                            }
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

    protected List<YJCourseModel> getYjCourseModel(List<YJCourseListModel> yjCourseListModelList) {
        List<YJCourseModel> yjCourseModelsList = new ArrayList<YJCourseModel>();
        for (int i = 0; i < yjCourseListModelList.size(); i++) {
            for (int j = 0; j < yjCourseListModelList.get(i).getCourseList().size(); j++) {
                yjCourseModelsList.add(yjCourseListModelList.get(i).getCourseList().get(j));
            }
        }
        return yjCourseModelsList;
    }

    protected void initXListView() {
        //MsgListView相关
        mMsgListView.setVisibility(View.VISIBLE);
        VAdapter adapter = new VAdapter(this, mMsgListView);
        mMsgListView.setAdapter(adapter);
        mMsgListView.setPullRefreshEnable(true);
        mMsgListView.setPullLoadEnable(true);
        mMsgListView.setXListViewListener(new MsgListView.IXListViewListener() {
            @Override
            public void onRefresh() {
                page = 1;
                paginationInfo = null;
                mYjCourseModelList.clear();
                refresh(courseTypeId);
            }

            @Override
            public void onLoadMore() {
                if (paginationInfo != null) {
                    if (paginationInfo.getCurrentPage() <= paginationInfo.getTotalPages()) {
                        loadmore(courseTypeId, ++page);
                    } else if (paginationInfo.getCurrentPage() > paginationInfo.getTotalPages()) {
                        showToast(getResources().getString(R.string.load_more_nothing));
                    }
                } else {
                    loadmore(courseTypeId, ++page);
                }
            }
        });
        mMsgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                if (i == 0) {
                    return;
                }
                Intent in = new Intent(getContext(), YJCoursePlayNewActivity.class);
                in.putExtra("courseId", mYjCourseModelList.get(i - 1).getCourseId());
                startActivity(in);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == txt_title) {
            if (netWork == 1) {
                showToast("请检查网络");
                return;
            }
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showPopWindow();
            mIv_arrow.clearAnimation();
            mIv_arrow.startAnimation(animation);
        } else if (view == layout_title) {
            if (netWork == 1) {
                showToast("请检查网络");
                return;
            }
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showPopWindow();
            mIv_arrow.clearAnimation();
            mIv_arrow.startAnimation(animation);
        } else if (view == img_back) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            finish();
        }
    }

    private void showPopWindow() {
        mCourseClassifyPopWindow = new CourseClassifyPopWindow(this, getTopActivity());
        mCourseClassifyPopWindow.showPopupWindow(txt_title);
    }

    private void initAnimation() {
        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);


        reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJCourseItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (mYjCourseModelList != null) {
            return mYjCourseModelList.size();
        } else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.course_item)
    class YJCourseItem extends AVAdapterItem {
        @VViewTag(R.id.lay_item)
        private RelativeLayout mLay_item;
        @VViewTag(R.id.view_line)
        private View mView_line;
        @VViewTag(R.id.img_course_teacher)
        private ImageView mIimg_course_teacher;
        @VViewTag(R.id.txt_name)
        private TextView mTxt_name;
        @VViewTag(R.id.txt_teacher_name)
        private TextView mTxt_teacher_name;
        @VViewTag(R.id.txt_money_num)
        private TextView mTxt_money_num;
        @VViewTag(R.id.txt_rmb_num)
        private TextView mTxt_rmb_num;
        @VViewTag(R.id.txt_buy_info)
        private TextView txt_buy_info;
        @VViewTag(R.id.fl_rmb)
        private FrameLayout fl_rmb;
        @VViewTag(R.id.img_rmb)
        private ImageView img_rmb;
        @VViewTag(R.id.img_new_course)
        private ImageView img_new_course;

        @Override
        public void update(int position) {
            if (mYjCourseModelList != null) {
                mTxt_name.setText(mYjCourseModelList.get(position).getName());
                mTxt_teacher_name.setText(mYjCourseModelList.get(position).getTeacher().getTrueName());
                if (!TextUtils.isEmpty(mYjCourseModelList.get(position).getCoursePic())) {
                    BitmapUtils.create(getContext()).display(mIimg_course_teacher, mYjCourseModelList.get(position).getCoursePic());
                } else {
                    mIimg_course_teacher.setImageResource(R.mipmap.img_no_data_bg);
                }

                if (mYjCourseModelList.get(position).getIsBuy().equals("Yes")) {
                    fl_rmb.setVisibility(View.GONE);
                    img_rmb.setVisibility(View.GONE);
                    txt_buy_info.setVisibility(View.GONE);
                    mTxt_rmb_num.setText("已购买");
                    mTxt_rmb_num.setTextColor(getResources().getColor(R.color.gray_text));
                    txt_buy_info.setText("开始学习");
                    txt_buy_info.setTextColor(getResources().getColor(R.color.blue_title));
                } else if (mYjCourseModelList.get(position).getIsBuy().equals("No")) {

                    mTxt_rmb_num.setTextColor(getResources().getColor(R.color.yellow_text));
                    txt_buy_info.setText("购买课程");
                    txt_buy_info.setVisibility(View.GONE);
                    txt_buy_info.setTextColor(getResources().getColor(R.color.yellow_text));

                    if (mYjCourseModelList.get(position).getCoursePayWay().equals("RMB")) {
                        img_rmb.setVisibility(View.VISIBLE);
                        fl_rmb.setVisibility(View.VISIBLE);

                        img_rmb.setImageResource(R.mipmap.img_rmb);
                        mTxt_money_num.setVisibility(View.VISIBLE);

                        mTxt_money_num.setText("￥" + mYjCourseModelList.get(position).getOriginalMoney() + "");
                        mTxt_rmb_num.setText(mYjCourseModelList.get(position).getPayMoney() + "");
                        if (mYjCourseModelList.get(position).getOriginalMoney() <= mYjCourseModelList.get(position).getPayMoney()) {
                            fl_rmb.setVisibility(View.GONE);
                        }

                    } else if (mYjCourseModelList.get(position).getCoursePayWay().equals("XNB")) {
                        fl_rmb.setVisibility(View.VISIBLE);
                        img_rmb.setVisibility(View.VISIBLE);
                        mTxt_money_num.setVisibility(View.VISIBLE);

                        mTxt_money_num.setText(mYjCourseModelList.get(position).getOriginalCoin() + "");
                        img_rmb.setImageResource(R.mipmap.whale_money);
                        mTxt_rmb_num.setText(mYjCourseModelList.get(position).getPayCoin() + "");
                        if (mYjCourseModelList.get(position).getOriginalCoin() <= mYjCourseModelList.get(position).getPayCoin()) {
                            fl_rmb.setVisibility(View.GONE);
                        }
                    } else if (mYjCourseModelList.get(position).getCoursePayWay().equals("FREE")) {
                        fl_rmb.setVisibility(View.GONE);
                        img_rmb.setVisibility(View.GONE);
                        mTxt_rmb_num.setText("免费");
                        txt_buy_info.setVisibility(View.GONE);
                    }

                }
                if (mYjCourseModelList.get(position).getIsNew().equals("No")) {
                    img_new_course.setVisibility(View.GONE);
                } else if (mYjCourseModelList.get(position).getIsNew().equals("Yes")) {
                    img_new_course.setVisibility(View.VISIBLE);
                }

            }


        }

    }


    /*存放控件*/
    public final class ViewHolder {
        public TextView txt_name;
        public View mView_line;
        public ImageView img_course_teacher;
        public TextView txt_teacher_name;
        public TextView txt_money_num;
        public TextView txt_rmb_num;
    }


    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {
                mCourse = intent.getStringExtra("course");
                int position = intent.getIntExtra("position", -1);
                if (position != -1) {
                    mPosition = position;
                    courseTypeId = YJGlobal.getYJCOURSETYPESBASEMEDEL().get(position).getCourseTypeId();
                }
                if (!mCourse.equals("CourseClassifyPopWindow消失")) {
                    txt_title.setText(mCourse);
                }
                mIv_arrow.clearAnimation();
                mIv_arrow.startAnimation(reverseAnimation);
            }
        }
    };

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        //注册广播
        getContext().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeListeners();
        unregisterReceiver(mBroadcastReceiver);
    }


    private void getNotifyListener() {
        addListener(YJNotifyTag.COURSE_LIST_LOAD_MORE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                mYjCourseModelList.addAll((Collection<? extends YJCourseModel>) o);
                if (page > paginationInfo.getTotalPages()) {
                    showToast(getResources().getString(R.string.load_more_nothing));
                }

            }

        });
        addListener(YJNotifyTag.COURSE_LIST_PAGE_INFO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paginationInfo = (PaginationInfo) value;
            }
        });
    }

    private void getNotifyListeners() {
        addListener(YJNotifyTag.COURSE_LIST_MODEL, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                mYjCourseModelList = (List<YJCourseModel>) o;
                initXListView();
            }
        });

        addListener(YJNotifyTag.PAY_SUCESS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
                refresh(courseTypeId);
            }
        });
        addListener(YJNotifyTag.USER_LOGIN, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
                refresh(courseTypeId);
            }
        });
    }


    public abstract void refresh(String courseTypeId);

    public abstract void loadmore(String courseTypeId, int page);
}