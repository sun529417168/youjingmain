package com.youjing.yjeducation.ui.dispaly.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.Display;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.calendar.CalendarAdapter;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJCalendarDialog;
import com.youjing.yjeducation.util.TimeUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.IVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.base.VParams;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@VLayoutTag(R.layout.calendar)
public abstract class AYJCalendarDialog extends AVDialog implements IVClickDelegate {


    private GestureDetector gestureDetector = null;
    private CalendarAdapter calV = null;

    private GridView gridView = null;
    private static int jumpMonth = 0; // 每次滑动，增加或减去一个月,默认为0（即显示当前月）
    private static int jumpYear = 0; // 滑动跨越一年，则增加或者减去一年,默认为0(即当前年)
    private int year_c = 0;
    private int month_c = 0;
    private int day_c = 0;
    private String currentDate = "";
    /** 每次添加gridview到viewflipper中时给的标记 */
    private int gvFlag = 0;
    private  String months[] = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    /** 上个月 */
    @VViewTag(R.id.prevMonth)
    private RelativeLayout prevMonth;
    /** 下个月 */
    @VViewTag(R.id.nextMonth)
    private RelativeLayout nextMonth;
    /** 当前的年月，现在日历顶端 */
    @VViewTag(R.id.currentMonth)
    private TextView currentMonth;
    @VViewTag(R.id.flipper)
    private ViewFlipper flipper;
    @VViewTag(R.id.re_calendar_bg)
    private RelativeLayout re_calendar_bg;

    public static boolean firstInit = true;
    private int mGradeNum, mSubjectNum;
    public static  int mCurrentYear;
    public static  int mCurrentMonth;
    private JSONArray jsonArray;
    private String classType = "";
    public static final VParamKey<JSONArray> CALENDAR_DATA = new VParamKey<JSONArray>(null);
    public static final VParamKey<String> CLASS_TYPE= new VParamKey<String>(null);
    public static final VParamKey<Date> DATE= new VParamKey<Date>(null);
    private String TAG = "AYJCalendarDialog";
    protected boolean mIsLogin = false;
    private Date   date;

    private String monthFirstDay = "";
    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        jumpMonth = 0;
        jsonArray = getTransmitData(CALENDAR_DATA);
        classType = getTransmitData(CLASS_TYPE);
        mGradeNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("gradeNum", 0);
        mSubjectNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("subjectNum", 0);
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        date = getTransmitData(DATE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        currentDate = sdf.format(date); // 当期日期

        StringUtils.Log(TAG,"currentDate=="+currentDate);
        StringUtils.Log(TAG,"jsonArray=="+jsonArray.toString());
        year_c = Integer.parseInt(currentDate.split("-")[0]);
        mCurrentYear = year_c;
        month_c = Integer.parseInt(currentDate.split("-")[1]);
        mCurrentMonth = month_c;
        day_c = Integer.parseInt(currentDate.split("-")[2]);
        monthFirstDay = (new SimpleDateFormat("yyyy-MM").format(date))+"-01";
        gestureDetector = new GestureDetector(getContext(), new MyGestureListener());
        flipper.removeAllViews();
        if (YJGlobal.getNowTime()!= null) {
            if (TimeUtil.getMonth(date) > TimeUtil.getMonth(YJGlobal.getNowTime())) {
                calV = new CalendarAdapter(getContext(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,jsonArray,1);
            }else if(TimeUtil.getMonth(date)  < TimeUtil.getMonth(YJGlobal.getNowTime())) {
                calV = new CalendarAdapter(getContext(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,jsonArray,-1);
            }else {
                calV = new CalendarAdapter(getContext(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,jsonArray,0);
            }
        }else {
            if (TimeUtil.getMonth(date)  > TimeUtil.getMonth(new Date()) ){
                calV = new CalendarAdapter(getContext(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,jsonArray,1);
            }else if(TimeUtil.getMonth(date)  < TimeUtil.getMonth(new Date()) ){
                calV = new CalendarAdapter(getContext(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,jsonArray,-1);
            }else {
                calV = new CalendarAdapter(getContext(), getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,jsonArray,0);
            }
        }
        addGridView();
        gridView.setAdapter(calV);
        flipper.addView(gridView, 0);
        addTextToTopTextView(currentMonth);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int gvFlag = 0; // 每次添加gridview到viewflipper中时给的标记
            if (e1.getX() - e2.getX() > 120) {
                // 像左滑动
              //  enterNextMonth(gvFlag);
                return true;
            } else if (e1.getX() - e2.getX() < -120) {
                // 向右滑动
                //enterPrevMonth(gvFlag);
                return true;
            }
            return false;
        }
    }
    /**
     * 移动到下一个月
     *
     * @param gvFlag
     */
    private void enterNextMonth(int gvFlag,JSONArray mjsonArray,int monthFlag) {
        addGridView(); // 添加一个gridView

        AYJCalendarDialog.firstInit = true;
        calV = new CalendarAdapter(getContext(), this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,mjsonArray,monthFlag);
        gridView.setAdapter(calV);
        addTextToTopTextView(currentMonth); // 移动到下一月后，将当月显示在头标题中
        gvFlag++;
        flipper.addView(gridView, gvFlag);
        flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_left_out));
        flipper.showNext();
        flipper.removeViewAt(0);
    }

    /**
     * 移动到上一个月
     *
     * @param gvFlag
     */
    private void enterPrevMonth(int gvFlag,JSONArray mjsonArray,int monthFlag) {
        addGridView(); // 添加一个gridView
        AYJCalendarDialog.firstInit = true;
        calV = new CalendarAdapter(getContext(), this.getResources(), jumpMonth, jumpYear, year_c, month_c, day_c,mjsonArray,monthFlag);
        gridView.setAdapter(calV);
        gvFlag++;
        addTextToTopTextView(currentMonth); // 移动到上一月后，将当月显示在头标题中
        flipper.addView(gridView, gvFlag);

        flipper.setInAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_in));
        flipper.setOutAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.push_right_out));
        flipper.showPrevious();
        flipper.removeViewAt(0);
    }

    /**
     * 添加头部的年份 闰哪月等信息
     *
     * @param view
     */
    public void addTextToTopTextView(TextView view) {
        StringBuffer textDate = new StringBuffer();
        // draw = getResources().getDrawable(R.drawable.top_day);
        // view.setBackgroundDrawable(draw);
        textDate.append(calV.getShowMonth()).append(" "+calV.getShowYear()).append("\t");
        view.setText(textDate);
    }

    private void addGridView() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT);
        // 取得屏幕的宽度和高度
        gridView = new GridView(getContext());
        gridView.setBackgroundColor(getResources().getColor(R.color.translucence));
        gridView.setNumColumns(7);
        gridView.setGravity(Gravity.CENTER_VERTICAL);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        // 去除gridView边框
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        gridView.setOnTouchListener(new View.OnTouchListener() {
            // 将gridview中的触摸事件回传给gestureDetector

            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                // 点击任何一个item，得到这个item的日期(排除点击的是周日到周六(点击不响应))
                int startPosition = calV.getStartPositon();
                int endPosition = calV.getEndPosition();
                if (startPosition <= position + 7 && position <= endPosition - 7) {
                    calV.setSeclection(position);
                    calV.notifyDataSetChanged();
                    AYJCalendarDialog.firstInit = false;
                     scheduleDay = calV.getDateByClickItem(position).split("\\.")[0]; // 这一天的阳历
                      StringUtils.Log(TAG,"scheduleDay="+scheduleDay);
                      day_c = Integer.parseInt(scheduleDay);
                     //这一天的阴历
                     scheduleYear = calV.getShowYear();
                    mCurrentYear = Integer.parseInt(scheduleYear);
                     scheduleMonth = calV.getShowMonth();
                    mCurrentMonth = Integer.parseInt(calV.getShowNumMonth());
                }
            }
        });
        gridView.setLayoutParams(params);
    }
    String  scheduleDay;
    String  scheduleYear ;
    String  scheduleMonth;
    private void closeCalendarDialog() {
        if (TextUtils.isEmpty(scheduleYear) || TextUtils.isEmpty(scheduleMonth) || TextUtils.isEmpty(scheduleDay)){
            notifyListener(YJNotifyTag.LIVE_CHANNEL_SELECT_DATE,currentDate);
        }else {
            for (int i = 0; i < months.length ;i++ ){
                if (scheduleMonth .equals(months[i])){
                    int j = i +1;
                    if (j <= 9){
                        String  m  =  "0"+j;
                        notifyListener(YJNotifyTag.LIVE_CHANNEL_SELECT_DATE,scheduleYear + "-" + m + "-" + scheduleDay);
                    }else {
                        notifyListener(YJNotifyTag.LIVE_CHANNEL_SELECT_DATE,scheduleYear + "-" + j + "-" + scheduleDay);
                    }
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view == prevMonth ){
            jumpMonth--; // 上一个月
            getLiveCalendarData(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), getMonthFirstDay(false), false);
        }else if (view == nextMonth){
            jumpMonth++; // 下一个月
            getLiveCalendarData(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), getMonthFirstDay(true), true);
        }else if (view == re_calendar_bg){
            closeCalendarDialog();
            AYJCalendarDialog.firstInit = true;
            close();
        }
    }
    private String getMonthFirstDay(boolean isAdd){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(monthFirstDay, pos);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strtodate);
        if (isAdd){
            calendar.add(Calendar.MONTH, +1);
        }else {
            calendar.add(Calendar.MONTH, -1);
        }
        Date mdate = calendar.getTime();
        String time = formatter.format(mdate);
        monthFirstDay = time;
        return  time;
    }
    public void getLiveCalendarData(final String gradeId, final String subjectId ,final String queryDate,final boolean isNextMonth) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", subjectId);
            objectMap.put("gradeId", gradeId);
            objectMap.put("queryDate", queryDate);
            if (!TextUtils.isEmpty(classType)){
                objectMap.put("courseTypeFlag", classType);
            }else {
                objectMap.put("courseTypeFlag", "liveClass");
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
                            //带有数据的时间list 例如：{2016-08-25；2016-08-30；2016-08-28}
                            JSONArray jsonArray = jsonData.getJSONArray("courseVideoDateList");
                            if (isNextMonth){
                                if (YJGlobal.getNowTime()!= null) {
                                    if (TimeUtil.getMonth(queryDate) > TimeUtil.getMonth(YJGlobal.getNowTime())) {
                                        enterNextMonth(gvFlag,jsonArray,1);
                                    }else if(TimeUtil.getMonth(queryDate) < TimeUtil.getMonth(YJGlobal.getNowTime())){
                                        enterNextMonth(gvFlag,jsonArray,-1);
                                    }else {
                                        enterNextMonth(gvFlag,jsonArray,0);
                                    }
                                }else {
                                    if (TimeUtil.getMonth(queryDate)  > TimeUtil.getMonth(new Date())) {
                                        enterNextMonth(gvFlag,jsonArray,1);
                                    }else if(TimeUtil.getMonth(queryDate) < TimeUtil.getMonth(new Date())){
                                        enterNextMonth(gvFlag,jsonArray,-1);
                                    }else {
                                        enterNextMonth(gvFlag,jsonArray,0);
                                    }
                                }
                            }else {
                                if (YJGlobal.getNowTime()!= null) {
                                    if (TimeUtil.getMonth(queryDate) > TimeUtil.getMonth(YJGlobal.getNowTime())) {
                                        enterPrevMonth(gvFlag,jsonArray,1);
                                    }else if(TimeUtil.getMonth(queryDate) < TimeUtil.getMonth(YJGlobal.getNowTime())){
                                        enterPrevMonth(gvFlag, jsonArray, -1);
                                    }else {
                                        enterPrevMonth(gvFlag, jsonArray, 0);
                                    }
                                }else {
                                    if (TimeUtil.getMonth(queryDate) > TimeUtil.getMonth(new Date())){
                                        enterPrevMonth(gvFlag, jsonArray, 1);
                                    }else if(TimeUtil.getMonth(queryDate)  < TimeUtil.getMonth(new Date())){
                                        enterPrevMonth(gvFlag, jsonArray,-1);
                                    }else {
                                        enterPrevMonth(gvFlag, jsonArray, 0);
                                    }
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

}
