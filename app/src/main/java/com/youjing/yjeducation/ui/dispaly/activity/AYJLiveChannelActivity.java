package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextPaint;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJOpenClassModel;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJCalendarDialog;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVVirtualActivity;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParams;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:59
 */
@VLayoutTag(R.layout.activity_live_channel)
public  class AYJLiveChannelActivity extends AVVirtualActivity implements IVClickDelegate ,ViewPager.OnPageChangeListener{
    @VViewTag(R.id.view_pager)
    private ViewPager view_pager;
    @VViewTag(R.id.re_open_class)
    private RelativeLayout re_open_class;
    @VViewTag(R.id.re_live_class)
    private RelativeLayout re_live_class;
    @VViewTag(R.id.txt_live_class)
    private TextView txt_live_class;
    @VViewTag(R.id.txt_open_class)
    private TextView txt_open_class;
    @VViewTag(R.id.view_line_live_class)
    private View view_line_live_class;
    @VViewTag(R.id.view_line_open_class)
    private View view_line_open_class;
    @VViewTag(R.id.re_calendar)
    private RelativeLayout re_calendar;
    @VViewTag(R.id.re_right_arrow)
    private RelativeLayout re_right_arrow;
    @VViewTag(R.id.re_left_arrow)
    private RelativeLayout re_left_arrow;
    @VViewTag(R.id.txt_month)
    private TextView txt_month;
    @VViewTag(R.id.txt_day)
    private TextView txt_day;


    private String mGrade;
    private String mSubject;
    private String  todayDate;
    private int mGradeNum, mSubjectNum;
    protected boolean mIsLogin = false;
    private List<Fragment> fragmentList;
    private final String ACTION_NAME = "发送广播";
    private String TAG = "AYJLiveChannelActivity";
    protected List<YJOpenClassModel> yjCourseOpenClassModelList;
    protected List<YJOpenClassModel> yjCourseLiveList;
    private YJFragmentListAdapter fragmentListAdapter;
    private  String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    @Override
    protected void onLoadedView() {

        StringUtils.showEvaluationDialog(AYJLiveChannelActivity.this);
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        mGrade = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("grade", YJGlobal.getGradeList().get(0).getGradeName());
        mSubject = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("subject", YJGlobal.getGradeList().get(0).getSubjectVos().get(0).getSubjectName());

        mGradeNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("gradeNum", 0);
        mSubjectNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("subjectNum", 0);

        if (YJGlobal.getGradeList() != null) {
            getLiveList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(),"");
            StringUtils.Log(TAG, "YJGlobal.getMy_grade_id=" + YJGlobal.getGradeList().get(mGradeNum).getGradeId());
            StringUtils.Log(TAG, "YJGlobal.getMy_subjectId=" + YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
        }
        registerBoradcastReceiver();
        getNotifyListener();
    }
    private void initViewPager() {

        if(fragmentList == null){
            fragmentList = new ArrayList<>();
        }else {
            fragmentList.clear();
        }
        AYJLiveListNewActivity ayjLiveListNewActivity = AYJLiveListNewActivity.create(yjCourseLiveList,this);;
        fragmentList.add(ayjLiveListNewActivity);
        AYJOpenClassNewActivity ayjOpenClassNewActivity = AYJOpenClassNewActivity.create(yjCourseOpenClassModelList,this);
        fragmentList.add(ayjOpenClassNewActivity);
        fragmentListAdapter = new YJFragmentListAdapter(getContext(), fragmentList);
        view_pager.setAdapter(fragmentListAdapter);
        view_pager.addOnPageChangeListener(this);

    }

    public class YJFragmentListAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        private Context context;
        public YJFragmentListAdapter(Context context, List<Fragment> fragmentList) {
            super(((FragmentActivity)context).getSupportFragmentManager());
            this.fragments = fragmentList;
            this.context = context;
        }
        @Override
        public int getCount() {
            return fragments.size();
        }
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == re_live_class) {
            selectOne();
        } else if (view == re_open_class) {
            selectTwo();
        }else if (view == re_calendar) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            if (view_pager.getCurrentItem() == 0){
                getLiveCalendarData(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), "liveClass");
            }else {
                getLiveCalendarData(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), "openClass");
            }

        }else if (view == re_left_arrow) {
            getLiveList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(),TimeUtil.getLastDay(todayDate));
        }else if (view == re_right_arrow) {
            getLiveList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(),TimeUtil.getNextDay(todayDate));
        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        switch (position) {
            case 0:
                selectOne();
                break;
            case 1:
                selectTwo();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void selectOne(){
        TextPaint live = txt_live_class.getPaint();
        live.setFakeBoldText(true);

        TextPaint open = txt_open_class.getPaint();
        open.setFakeBoldText(false);

        view_line_live_class.setVisibility(View.VISIBLE);
        view_line_open_class.setVisibility(View.GONE);
        txt_live_class.setTextColor(getResources().getColor(R.color.white));
        txt_open_class.setTextColor(getResources().getColor(R.color.white_light));
        view_pager.setCurrentItem(0);
    }
    private void selectTwo(){
        TextPaint open = txt_open_class.getPaint();
        open.setFakeBoldText(true);

        TextPaint live = txt_live_class.getPaint();
        live.setFakeBoldText(false);
        txt_open_class.setTextColor(getResources().getColor(R.color.white));
        txt_live_class.setTextColor(getResources().getColor(R.color.white_light));
        view_line_live_class.setVisibility(View.GONE);
        view_line_open_class.setVisibility(View.VISIBLE);
        view_pager.setCurrentItem(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        StringUtils.showEvaluationDialog(AYJLiveChannelActivity.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mBroadcastReceiver);
    }
    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction(ACTION_NAME);
        //注册广播
        getContext().registerReceiver(mBroadcastReceiver, myIntentFilter);
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_NAME)) {

                String grade = intent.getStringExtra("grade");
                String subject = intent.getStringExtra("subject");
                if (!TextUtils.isEmpty(subject)) {
                    mSubject = subject;
                }
                if (!TextUtils.isEmpty(grade)) {
                    if (!grade.equals("MorePopWindow消失")) {
                        mGrade = grade;
                    }
                }
                if (!TextUtils.isEmpty(grade)) {
                    if (grade.equals("MorePopWindow消失")) {
                        mGradeNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("gradeNum", 0);
                        mSubjectNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("subjectNum", 0);
                        if (YJGlobal.getGradeList() != null) {
                            getLiveList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), "");
                        }
                    }
                }
            }
        }
    };

    private  void getNotifyListener() {
        addListener(YJNotifyTag.LIVE_CHANNEL_SELECT_DATE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                String date = (String)o;
                getLiveList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(),date);

            }
        });
        addListener(YJNotifyTag.PAY_SUCESS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                if (!TextUtils.isEmpty(todayDate)){
                    getLiveList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(),todayDate);
                }else {
                    getLiveList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(),"");
                }
            }
        });
        addListener(YJNotifyTag.USER_LOGIN, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
            }
        });
        addListener(YJNotifyTag.LIVE_CHANNEL_SHOW_DAILOG, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                getLiveCalendarData(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), "openClass");
            }
        });
    }
    public void getLiveList(final String gradeId, final String subjectId,final String time) {
        StringUtils.Log(TAG, "gradeId=" + gradeId + "subjectId=" + subjectId);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", subjectId);
            objectMap.put("gradeId", gradeId);
            if (!TextUtils.isEmpty(time)){
                StringUtils.Log(TAG,"time="+time);
                objectMap.put("queryDate", time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_LIVE_CHANNEL_LIST, objectMap, mIsLogin, new TextHttpResponseHandler() {
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
                            todayDate = jsonData.getString("queryDate");
                            if (TextUtils.isEmpty(time)){
                               YJGlobal.setNowTime(todayDate);
                            }

                            if (!TextUtils.isEmpty(todayDate)) {
                                txt_day.setText(TimeUtil.strToDay(todayDate));
                                txt_month.setText(months[Integer.parseInt(TimeUtil.strToMonth(todayDate)) - 1]);
                            }

                            yjCourseOpenClassModelList = JSON.parseArray(jsonData.getString("openClassList"), YJOpenClassModel.class);
                            YJGlobal.setYjOpenClassModelList(yjCourseOpenClassModelList);
                            StringUtils.Log(TAG, "yjCourseOpenClassModelList.toString()=" + yjCourseOpenClassModelList.toString());
                            yjCourseLiveList = JSON.parseArray(jsonData.getString("courseVideoList"), YJOpenClassModel.class);

                            if (fragmentListAdapter == null) {
                                initViewPager();
                            } else {
                                notifyListener(YJNotifyTag.LIVE_CHANNEL_LIVE_LIST, yjCourseLiveList);
                                notifyListener(YJNotifyTag.LIVE_CHANNEL_OPEN_CLASS, yjCourseOpenClassModelList);
                            }
                            YJGlobal.setMy_subjectId(subjectId);
                            YJGlobal.setMy_grade_id(gradeId);
                            //notifyListener(YJNotifyTag.OPEN_CLASS_LIST, yjCourseTypeModelList);
                            break;
                        case 300:
                            break;
                        case 400:
                            StringUtils.Log(TAG, "400");
                            //YJGlobal.setYjCourseTypeModelList(null);
//                            notifyListener(YJNotifyTag.OPEN_CLASS_LIST, null);
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


    public void getLiveCalendarData(final String gradeId, final String subjectId,final String classType) {
        StringUtils.Log(TAG, "gradeId=" + gradeId + "subjectId=" + subjectId);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", subjectId);
            objectMap.put("gradeId", gradeId);
            objectMap.put("courseTypeFlag", classType);
            if (!TextUtils.isEmpty(todayDate)){
                objectMap.put("queryDate", todayDate);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_LIVE_CHANNEL_CALENDAR, objectMap, mIsLogin, new TextHttpResponseHandler() {
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
                            JSONArray jsonArray = jsonData.getJSONArray("courseVideoDateList");
                            YJCalendarDialog yjCalendarDialog = new YJCalendarDialog();

                            VParams data = createTransmitData(yjCalendarDialog.CALENDAR_DATA, jsonArray).set(yjCalendarDialog.CLASS_TYPE,classType).set(yjCalendarDialog.DATE,TimeUtil.getDate(todayDate));
                            showDialog(yjCalendarDialog, data);
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
