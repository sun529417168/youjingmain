package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.model.YJCourseChanneModel;
import com.youjing.yjeducation.util.ScanBannerUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.adapter.CourseNewRecommendedAdapter;
import com.youjing.yjeducation.adapter.CourseTypesAdapter;
import com.youjing.yjeducation.adapter.HorizontalSpecialbarListViewAdapter;
import com.youjing.yjeducation.adapter.ViewPagerAdapter;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.model.YJCommendCourseTypeMedel;
import com.youjing.yjeducation.model.YJCourseListModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJCourseTypeModel;
import com.youjing.yjeducation.model.YJCourseTypesBaseMedel;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.model.YJIndexUserInfo;
import com.youjing.yjeducation.model.YJRecommendCourseLiveModel;
import com.youjing.yjeducation.model.YJRecommendCourseModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.scan.CaptureActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJLoginDialog;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.wiget.CustomImage;
import com.youjing.yjeducation.wiget.ImageCycleView;
import com.youjing.yjeducation.wiget.MorePopWindow;

import org.vwork.mobile.ui.AVVirtualActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.ArrayList;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:55
 */

@VLayoutTag(R.layout.activity_yjmain)
public abstract class AYJCourseActivity extends AVVirtualActivity implements IVClickDelegate {
    @VViewTag(R.id.txt_title)
    private TextView txt_title;
    @VViewTag(R.id.layout_title)
    protected RelativeLayout layout_title;
    @VViewTag(R.id.lv_course)
    protected ListView mLv_course;
    @VViewTag(R.id.iv_arrow)
    protected ImageView mIv_arrow;
    @VViewTag(R.id.re_no_data_bg)
    protected LinearLayout re_no_data_bg;
    @VViewTag(R.id.re_title)
    protected RelativeLayout re_title;
    @VViewTag(R.id.course_list_head_leave_img)
    protected ImageView mCourse_list_head_leave_img;
    @VViewTag(R.id.re_sacn)
    private RelativeLayout re_sacn;
    @VViewTag(R.id.txt_scan)
    private TextView txt_scan;
    @VViewTag(R.id.img_scan)
    private ImageView img_scan;


    private final String ACTION_NAME = "发送广播";
    private String TAG = "AYJCourseActivity";
    protected String mGrade = "";
    protected String mSubject = "";

    private MorePopWindow morePopWindow;
    private LinearLayout mLl_list_header, mLl_user_info;
    private Handler mHandler;
    protected ImageCycleView mCycleView;
    protected TextView mTxt_mycourse, mTxt_lern, mTxt_have_buy_num, mTxt_over_step;
    protected TextView mCourse_list_head_leave_text;
    protected RelativeLayout mCourse_list_head_level;
    protected TextView mCourse_list_head_sign, mCourse_list_head_signData;
    protected TextView mNickName;
    private MyAdapter myAdapter;
    private RotateAnimation animation, reverseAnimation;
    protected List<YJCourseTypeModel> yjCourseTypeModelList;
    protected List<YJRecommendCourseModel> yjRecommendCourseModelList;
    protected List<YJRecommendCourseLiveModel> yjRecommendCourseLiveModelList;
    protected List<YJCourseChanneModel> yjCourseChanneModel;
    protected List<YJCourseTypesBaseMedel> yjCourseTypesBaseMedel;
    protected YJUser mYjUser;
    protected YJIndexUserInfo yjIndexUserInfo;
    protected CustomImage mImg_user_photo;
    protected boolean mIsLogin = false;
    protected boolean isFirst = false;
    protected GridView gridview;
    protected ViewPager mViewPager;
    protected LinearLayout linear;
    int currentItem = 0;
    protected int mGradeNum, mSubjectNum;
    private List<View> listViews;
    private ViewPagerAdapter adapter;
    protected Dialog progDialog;
    private GridView hListView;
    private HorizontalSpecialbarListViewAdapter horizontalSpecialbarListViewAdapter;

    protected CourseNewRecommendedAdapter courseNewRecommendAdapter;
    protected CourseTypesAdapter courseTypesAdapter;
    protected List<YJGradeModel> yjGradeModelList;

    private boolean isClick = false;
    @Override
    protected void onLoadedView() {
        isFirst = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isFirst", true);
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        mGradeNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("gradeNum", 0);
        mSubjectNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("subjectNum", 0);
        StringUtils.showEvaluationDialog(AYJCourseActivity.this);

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLl_list_header = (LinearLayout) inflater.inflate(R.layout.course_list_head, null);
        mCycleView = (ImageCycleView) mLl_list_header.findViewById(R.id.cycleView);
        hListView = (GridView) mLl_list_header.findViewById(R.id.course_list_head_Specialbar);
        mTxt_lern = (TextView) mLl_list_header.findViewById(R.id.txt_lern);
        mTxt_have_buy_num = (TextView) mLl_list_header.findViewById(R.id.txt_have_buy_num);
        mTxt_over_step = (TextView) mLl_list_header.findViewById(R.id.txt_over_step);
        mCourse_list_head_leave_text = (TextView) mLl_list_header.findViewById(R.id.course_list_head_leave_text);
        mCourse_list_head_level = (RelativeLayout) mLl_list_header.findViewById(R.id.course_list_head_level);
        mCourse_list_head_sign = (TextView) mLl_list_header.findViewById(R.id.course_list_head_sign);
        mCourse_list_head_signData = (TextView) mLl_list_header.findViewById(R.id.course_list_head_signData);
        mViewPager = (ViewPager) mLl_list_header.findViewById(R.id.course_list_head_live_cycleView);//直播推荐的viewpager
        gridview = (GridView) mLl_list_header.findViewById(R.id.course_list_head_gridview);//新课推荐的GridView
        linear = (LinearLayout) mLl_list_header.findViewById(R.id.course_list_head_live_linear);
        mLl_user_info = (LinearLayout) mLl_list_header.findViewById(R.id.ll_user_info);

        //mAdapter = new VAdapter(this, mLv_course);
        mTxt_mycourse = (TextView) mLl_list_header.findViewById(R.id.txt_mycourse);
        mNickName = (TextView) mLl_list_header.findViewById(R.id.txt_user_name);
        mImg_user_photo = (CustomImage) mLl_list_header.findViewById(R.id.img_user_photo);
        initAllData();
    }

    protected void initUI() {
        getNotifyListener();
        //webview加载内存卡文件
        //webView.loadUrl("file:///mnt/sdcard/a.html");
        //webview加载app中资源文件
        // webView.loadUrl("file:///android_asset/a.html");
        //webview加载网络文件
        //webView.loadUrl("http://wap.baidu.com");
        registerBoradcastReceiver();
        txt_title.setText(mGrade + mSubject);
        initListHead();
        initViewPager();
        initAnimation();
        if (isFirst) {
            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, 300);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirst", false);
            editor.commit();
        }
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

        isClick = true;
    }

    private void initListHead() {

        if (mYjUser != null) {
            if (!TextUtils.isEmpty(mYjUser.getNickName())) {
                mNickName.setText(mYjUser.getNickName());
            }
            if (!TextUtils.isEmpty(mYjUser.getPic())) {
                StringUtils.Log(TAG, "pic=" + mYjUser.getPic());
                BitmapUtils.create(getContext()).display(mImg_user_photo, mYjUser.getPic());
            }
        }
        if (yjIndexUserInfo != null) {
            if (!TextUtils.isEmpty(yjIndexUserInfo.getGoingStudyDays())) {
                mTxt_lern.setText(yjIndexUserInfo.getGoingStudyDays());
            } else {
                mTxt_lern.setText("0");
            }
            if (!TextUtils.isEmpty(yjIndexUserInfo.getCustomerCourseCount()) && !TextUtils.isEmpty(yjIndexUserInfo.getCustomerOverCourseCount())) {
                mTxt_have_buy_num.setText(yjIndexUserInfo.getCustomerOverCourseCount() + "/" + yjIndexUserInfo.getCustomerCourseCount());
            } else {
                mTxt_have_buy_num.setText(0 + "/" + 0);
            }
            if (!TextUtils.isEmpty(yjIndexUserInfo.getCustomerWeekRanking())) {
                if (yjIndexUserInfo.getCustomerWeekRanking().equals("0")) {
                    mTxt_over_step.setText("暂无");
                    mTxt_over_step.setTextSize(17);
                } else {
                    mTxt_over_step.setText(yjIndexUserInfo.getCustomerWeekRanking() + "%");
                }
            } else {
                mTxt_over_step.setText("暂无");
                mTxt_over_step.setTextSize(17);
            }
            if (!TextUtils.isEmpty(yjIndexUserInfo.getLevel())) {
                mCourse_list_head_leave_text.setText(yjIndexUserInfo.getLevel());
            } else {
                mCourse_list_head_leave_text.setText("1");
            }

            signinSetData(yjIndexUserInfo);
        }

        if (mIsLogin) {
            mLl_user_info.setVisibility(View.VISIBLE);
        } else {
            mLl_user_info.setVisibility(View.GONE);
        }
        mTxt_mycourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                getMyCourse();
            }
        });
        mCourse_list_head_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                if ("Yes".equals(yjIndexUserInfo.getIsSign())) {
                    showToast("用户已签到");
                }
                if ("No".equals(yjIndexUserInfo.getIsSign())) {
                    getSign();
                }

            }
        });
        mLv_course.addHeaderView(mLl_list_header);

        if (yjCourseTypeModelList == null || yjCourseTypeModelList.size() == 0) {
            yjCourseTypeModelList = new ArrayList<YJCourseTypeModel>();
            if (courseTypesAdapter == null) {
                courseTypesAdapter = new CourseTypesAdapter(getContext(), (ArrayList<YJCourseTypeModel>) yjCourseTypeModelList);
                mLv_course.setAdapter(courseTypesAdapter);
            } else {
                courseTypesAdapter.notifyDataSetChanged();
            }
            re_no_data_bg.setVisibility(View.VISIBLE);
        } else {
            re_no_data_bg.setVisibility(View.GONE);
            if (courseTypesAdapter == null) {
                courseTypesAdapter = new CourseTypesAdapter(getContext(), (ArrayList<YJCourseTypeModel>) yjCourseTypeModelList);
                mLv_course.setAdapter(courseTypesAdapter);
            } else {
                courseTypesAdapter.notifyDataSetChanged();
            }
        }


        if (yjCourseChanneModel == null || yjCourseChanneModel.size() == 0) {
            hListView.setVisibility(View.GONE);
        } else {
            hListView.setVisibility(View.VISIBLE);
            if (horizontalSpecialbarListViewAdapter == null) {
                horizontalSpecialbarListViewAdapter = new HorizontalSpecialbarListViewAdapter(getContext(), yjCourseChanneModel);
                hListView.setAdapter(horizontalSpecialbarListViewAdapter);
            } else {
                horizontalSpecialbarListViewAdapter.notifyDataSetChanged();
            }
        }
        hListView.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消GridView中Item选中时默认的背景色
        hListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position != 3) {
                    ScanBannerUtils.intentPage((YJBaseActivity) getActivity(), yjCourseChanneModel.get(position).getJumpUrl(), TAG, mIsLogin, 0);
                } else {
                    Intent in = new Intent(getContext(), AYJScanWebviewActivity.class);
                    in.putExtra("msg", "http://file.youjing.cn/topic/wx.html");
                    startActivity(in);
                }

            }
        });


        if (yjRecommendCourseModelList == null || yjRecommendCourseModelList.size() == 0) {
            mLl_list_header.findViewById(R.id.layout_course_new).setVisibility(View.GONE);
            StringUtils.Log(TAG, "yjRecommendCourseModelList=null");
        } else {
            mLl_list_header.findViewById(R.id.layout_course_new).setVisibility(View.VISIBLE);
            StringUtils.Log(TAG, "yjRecommendCourseModelList=" + yjRecommendCourseModelList.toString());
            if (courseNewRecommendAdapter == null) {
                courseNewRecommendAdapter = new CourseNewRecommendedAdapter(getContext(), yjRecommendCourseModelList);
                gridview.setAdapter(courseNewRecommendAdapter);
            } else {
                courseNewRecommendAdapter.notifyDataSetChanged();
            }

        }
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消GridView中Item选中时默认的背景色
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                Intent in = new Intent(getContext(), YJCoursePlayNewActivity.class);
                in.putExtra("courseId", yjRecommendCourseModelList.get(position).getCourseId());
                startActivity(in);
            }
        });
        if (yjRecommendCourseLiveModelList == null || yjRecommendCourseLiveModelList.size() == 0) {
            mLl_list_header.findViewById(R.id.layout_course_live).setVisibility(View.GONE);
            mLl_list_header.findViewById(R.id.ll_gray_view).setVisibility(View.GONE);
        } else {
            mLl_list_header.findViewById(R.id.layout_course_live).setVisibility(View.VISIBLE);
            mLl_list_header.findViewById(R.id.ll_gray_view).setVisibility(View.VISIBLE);
            initData();
            initDot();
            if (adapter == null) {
                adapter = new ViewPagerAdapter(listViews);
                mViewPager.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                currentItem = position;
                updateDot(currentItem);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        /**
         * 注视掉以后想自己滑动解开注释就行了
         */
//        mCourseHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                currentItem++;
//                if (currentItem >= yjRecommendCourseLiveModelList.size()) {
//                    currentItem = 0;
//                }
//                mViewPager.setCurrentItem(currentItem);
//                updateDot(currentItem);
//                mCourseHandler.postDelayed(this, 5000);
//            }
//        }, 5000);
    }

    protected void signinSetData(YJIndexUserInfo yjIndexUserInfo) {
        if (!TextUtils.isEmpty(yjIndexUserInfo.getIsSign())) {
            if ("Yes".equals(yjIndexUserInfo.getIsSign())) {
                mCourse_list_head_level.setBackgroundResource(R.mipmap.signin_background_yes);
                mCourse_list_head_sign.setText("已签到");
                if (TextUtils.isEmpty(yjIndexUserInfo.getSignDays()) || "0".equals(yjIndexUserInfo.getSignDays())) {
                    mCourse_list_head_signData.setVisibility(View.INVISIBLE);
                } else {
                    mCourse_list_head_signData.setVisibility(View.VISIBLE);
                    mCourse_list_head_signData.setText("已连续" + yjIndexUserInfo.getSignDays() + "天");
                }

            }
            if ("No".equals(yjIndexUserInfo.getIsSign())) {
                mCourse_list_head_level.setBackgroundResource(R.mipmap.signin_background_no);
                mCourse_list_head_sign.setText("签到");
                if (TextUtils.isEmpty(yjIndexUserInfo.getSignDays()) || "0".equals(yjIndexUserInfo.getSignDays())) {
                    mCourse_list_head_signData.setVisibility(View.INVISIBLE);
                } else {
                    mCourse_list_head_signData.setVisibility(View.VISIBLE);
                    mCourse_list_head_signData.setText("已连续" + yjIndexUserInfo.getSignDays() + "天");
                }
            }
        } else {
            mCourse_list_head_level.setBackgroundResource(R.mipmap.signin_background_no);
            mCourse_list_head_sign.setText("签到");
            mCourse_list_head_signData.setText("已连续" + yjIndexUserInfo.getSignDays() + "天");
        }
    }

    private Runnable mRunnable = new Runnable() {
        public void run() {
            showPopWindow();
        }
    };
    private boolean arrowFlag = true;

    @Override
    public void onClick(View view) {
        if (view == layout_title) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            if (isClick){
                showPopWindow();
                mIv_arrow.clearAnimation();
                mIv_arrow.startAnimation(animation);
            }

        } else if (view == txt_title) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            if (isClick){
                showPopWindow();
                mIv_arrow.clearAnimation();
                mIv_arrow.startAnimation(animation);
            }

        } else if (view == re_sacn) {
            startActivity(CaptureActivity.class);
        } else if (view == txt_scan) {
            startActivity(CaptureActivity.class);
        } else if (view == img_scan) {
            startActivity(CaptureActivity.class);
        }
    }

    private void showPopWindow() {
        morePopWindow = new MorePopWindow(getActivity(), getTopActivity());
        morePopWindow.showPopupWindow(re_title);
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
                    txt_title.setText(mGrade + subject);
                }
                if (!TextUtils.isEmpty(grade)) {
                    if (!grade.equals("MorePopWindow消失")) {
                        mGrade = grade;
                        txt_title.setText(grade + mSubject);
                    }
                }
                if (!TextUtils.isEmpty(grade)) {
                    if (grade.equals("MorePopWindow消失")) {
                        if (animFlag) {
                            mIv_arrow.clearAnimation();
                            mIv_arrow.startAnimation(reverseAnimation);
                        }

                        mGradeNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("gradeNum", 0);
                        mSubjectNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("subjectNum", 0);
                        if (YJGlobal.getGradeList() != null) {
                            getCourseType(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
                        }
                    }

                }

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
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mBroadcastReceiver);
    }

    private boolean animFlag = false;

    @Override
    public void onPause() {
        super.onPause();
        animFlag = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        animFlag = true;
        StringUtils.showEvaluationDialog(AYJCourseActivity.this);
        if (mIsLogin) {
            getIndexInfo();
        }
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (yjCourseTypeModelList != null) {
                re_no_data_bg.setVisibility(View.GONE);
                return yjCourseTypeModelList.size();
            } else {
                re_no_data_bg.setVisibility(View.VISIBLE);
                return 0;
            }

        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {

            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.course_front_item, null);
                viewHolder.mTxt_up = (TextView) convertView.findViewById(R.id.txt_up);
                viewHolder.mTxt_mid = (TextView) convertView.findViewById(R.id.txt_mid);
                viewHolder.mTxt_down = (TextView) convertView.findViewById(R.id.txt_down);
                viewHolder.mImg_course_type_bg = (ImageView) convertView.findViewById(R.id.img_course_type_bg);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (yjCourseTypeModelList != null) {
                viewHolder.mTxt_up.setText(YJGlobal.getGradeList().get(mGradeNum).getGradeName() + YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectName());
                viewHolder.mTxt_mid.setText(yjCourseTypeModelList.get(i).getName());
                viewHolder.mTxt_down.setText(yjCourseTypeModelList.get(i).getMark());
                BitmapUtils.create(getContext()).display(viewHolder.mImg_course_type_bg, yjCourseTypeModelList.get(i).getPic());
            }
            return convertView;
        }

        class ViewHolder {
            private TextView mTxt_up;
            private TextView mTxt_mid;
            private TextView mTxt_down;
            private ImageView mImg_course_type_bg;
        }

    }

    protected abstract void initViewPager();

    private void getNotifyListener() {
        addListener(YJNotifyTag.COURSE_TYPE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjCourseTypeModelList = (List<YJCourseTypeModel>) o;
                if (yjCourseTypeModelList == null || yjCourseTypeModelList.size() == 0) {
                    yjCourseTypeModelList = new ArrayList<YJCourseTypeModel>();
                    courseTypesAdapter = new CourseTypesAdapter(getContext(), (ArrayList<YJCourseTypeModel>) yjCourseTypeModelList);
                    mLv_course.setAdapter(courseTypesAdapter);
                    courseTypesAdapter.notifyDataSetChanged();
                    re_no_data_bg.setVisibility(View.VISIBLE);
                } else {
                    re_no_data_bg.setVisibility(View.GONE);
                    courseTypesAdapter = new CourseTypesAdapter(getContext(), (ArrayList<YJCourseTypeModel>) yjCourseTypeModelList);
                    mLv_course.setAdapter(courseTypesAdapter);
                    courseTypesAdapter.notifyDataSetChanged();
                }
            }

        });
        addListener(YJNotifyTag.NEW_COURSE_RECOMMENDED, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjRecommendCourseModelList = (ArrayList<YJRecommendCourseModel>) o;
                if (yjRecommendCourseModelList == null || yjRecommendCourseModelList.size() == 0) {
                    mLl_list_header.findViewById(R.id.layout_course_new).setVisibility(View.GONE);
                } else {
                    mLl_list_header.findViewById(R.id.layout_course_new).setVisibility(View.VISIBLE);
                    courseNewRecommendAdapter = new CourseNewRecommendedAdapter(getContext(), yjRecommendCourseModelList);
                    gridview.setAdapter(courseNewRecommendAdapter);
                    courseNewRecommendAdapter.notifyDataSetChanged();

                }

            }

        });
        addListener(YJNotifyTag.LIVE_COURSE_RECOMMENDED, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjRecommendCourseLiveModelList = (ArrayList<YJRecommendCourseLiveModel>) o;
                if (yjRecommendCourseLiveModelList == null || yjRecommendCourseLiveModelList.size() == 0) {
                    mLl_list_header.findViewById(R.id.layout_course_live).setVisibility(View.GONE);
                    mLl_list_header.findViewById(R.id.ll_gray_view).setVisibility(View.GONE);
                } else {
                    initData();
                    initDot();
                    mLl_list_header.findViewById(R.id.layout_course_live).setVisibility(View.VISIBLE);
                    mLl_list_header.findViewById(R.id.ll_gray_view).setVisibility(View.VISIBLE);
                    adapter = new ViewPagerAdapter(listViews);
                    mViewPager.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }


            }

        });
        addListener(YJNotifyTag.COURSE_TYPES, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjCourseChanneModel = (List<YJCourseChanneModel>) o;
                if (yjCourseChanneModel == null || yjCourseChanneModel.size() == 0) {
                    hListView.setVisibility(View.GONE);
                } else {
                    hListView.setVisibility(View.VISIBLE);
                    horizontalSpecialbarListViewAdapter = new HorizontalSpecialbarListViewAdapter(getContext(), yjCourseChanneModel);
                    hListView.setAdapter(horizontalSpecialbarListViewAdapter);
                    horizontalSpecialbarListViewAdapter.notifyDataSetChanged();
                }


            }

        });

        addListener(YJNotifyTag.USER_LOGIN, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
                if (mIsLogin) {
                    mLl_user_info.setVisibility(View.VISIBLE);
                } else {
                    mLl_user_info.setVisibility(View.GONE);
                }
                mYjUser = YJGlobal.getYjUser();
                if (mYjUser != null) {
                    if (!TextUtils.isEmpty(mYjUser.getNickName())) {
                        mNickName.setText(mYjUser.getNickName());
                    }
                    if (!TextUtils.isEmpty(mYjUser.getPic())) {
                        StringUtils.Log(TAG, "pic=" + mYjUser.getPic());
                        BitmapUtils.create(getContext()).display(mImg_user_photo, mYjUser.getPic());
                    }
                }
                yjIndexUserInfo = YJGlobal.getYjIndexUserInfo();
                if (yjIndexUserInfo != null) {
                    if (!TextUtils.isEmpty(yjIndexUserInfo.getGoingStudyDays())) {
                        mTxt_lern.setText(yjIndexUserInfo.getGoingStudyDays());
                    } else {
                        mTxt_lern.setText("0");
                    }
                    if (!TextUtils.isEmpty(yjIndexUserInfo.getCustomerCourseCount()) && !TextUtils.isEmpty(yjIndexUserInfo.getCustomerOverCourseCount())) {
                        mTxt_have_buy_num.setText(yjIndexUserInfo.getCustomerOverCourseCount() + "/" + yjIndexUserInfo.getCustomerCourseCount());
                    } else {
                        mTxt_have_buy_num.setText(0 + "/" + 0);
                    }
                    if (!TextUtils.isEmpty(yjIndexUserInfo.getCustomerWeekRanking())) {
                        if (yjIndexUserInfo.getCustomerWeekRanking().equals("0")) {
                            mTxt_over_step.setText("暂无");
                        } else {
                            mTxt_over_step.setText(yjIndexUserInfo.getCustomerWeekRanking() + "%");
                        }
                    } else {
                        mTxt_over_step.setText("暂无");
                    }

                    if (!TextUtils.isEmpty(yjIndexUserInfo.getLevel())) {
                        mCourse_list_head_leave_text.setText(yjIndexUserInfo.getLevel());
                    } else {
                        mCourse_list_head_leave_text.setText("1");
                    }

                    if (!TextUtils.isEmpty(yjIndexUserInfo.getNickName())) {
                        mNickName.setText(yjIndexUserInfo.getNickName());
                    } else {
                        mNickName.setText("未填写");
                    }
                    if (!TextUtils.isEmpty(yjIndexUserInfo.getPic())) {
                        StringUtils.Log(TAG, "pic=" + yjIndexUserInfo.getPic());
                        BitmapUtils.create(getContext()).display(mImg_user_photo, yjIndexUserInfo.getPic());
                    } else {
                        mImg_user_photo.setImageResource(R.mipmap.img_app_logo);
                    }
                    signinSetData(yjIndexUserInfo);
                }
            }
        });
        addListener(YJNotifyTag.HEAD_USER, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String name = (String) value;
                BitmapUtils.create(getContext()).display(mImg_user_photo, name);
            }
        });
    }


    protected void saveGrade(String course, int num, String courseTypeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course", course);
        editor.putString("courseTypeId", courseTypeId);
        editor.putInt("courseNum", num);
        editor.commit();
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



    public abstract void getCourseType(String gradeId, String subjectId);

    public abstract void getMyCourse();

    public abstract void getIndexInfo();


    public abstract void getTakenLive(int position, boolean isShowDialog, int isLiveType);


    public abstract void getTakenBack(int position);

    public abstract void getisBuy(int position);

    public abstract void getSign();

    protected abstract void initAllData();

    private Handler mCourseHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            currentItem++;
            if (currentItem == yjRecommendCourseLiveModelList.size()) {
                currentItem = 0;
            }
            mViewPager.setCurrentItem(currentItem);
            return false;
        }
    });

    private void initData() {
        listViews = new ArrayList<View>();
        currentItem = 0;
        for (int i = 0; i < yjRecommendCourseLiveModelList.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_live_course_recommended, null);
            ImageView teacherPic = (ImageView) view.findViewById(R.id.img_live_course_photo);//老师头像
            TextView liveDate = (TextView) view.findViewById(R.id.item_live_course_date);//直播日期
            TextView content = (TextView) view.findViewById(R.id.item_live_course_info);//内容
            TextView teacherName = (TextView) view.findViewById(R.id.item_live_course_teacherName);//老师姓名
            TextView studentCount = (TextView) view.findViewById(R.id.item_live_course_count);//学生人数
            TextView stateText = (TextView) view.findViewById(R.id.item_live_course_state_text);//状态
            ImageView stateImage = (ImageView) view.findViewById(R.id.item_live_course_state_image);//状态的图标
            BitmapUtils.create(getContext()).display(teacherPic, yjRecommendCourseLiveModelList.get(i).getTeacher().getTeacherPic());
//            String planDate = TextUtils.isEmpty(yjRecommendCourseLiveModelList.get(i).getPlanDate()) ? "" : TimeUtil.getSecondTime(Long.parseLong(yjRecommendCourseLiveModelList.get(i).getPlanDate()));
            String planDate = TimeUtil.getYearTime(Long.parseLong(yjRecommendCourseLiveModelList.get(i).getPlanDate()));
            String planEndDate = TextUtils.isEmpty(yjRecommendCourseLiveModelList.get(i).getPlanEndDate()) ? "" : TimeUtil.getSecondMInTime(Long.parseLong(yjRecommendCourseLiveModelList.get(i).getPlanEndDate()));
//            liveDate.setText(planDate + "-" + planEndDate);
            liveDate.setText((("3".equals(StringUtils.getDateDetail(planDate)) ? TimeUtil.getMonthTime(Long.parseLong(yjRecommendCourseLiveModelList.get(i).getPlanDate())) : StringUtils.getDateDetail(planDate)) + " " + TimeUtil.getSecondMInTime(Long.parseLong(yjRecommendCourseLiveModelList.get(i).getPlanDate()))) + "-" + planEndDate);
            content.setText(yjRecommendCourseLiveModelList.get(i).getName());
            teacherName.setText(yjRecommendCourseLiveModelList.get(i).getTeacher().getTrueName());

            if ("no".equals(yjRecommendCourseLiveModelList.get(i).getLiveStatus())) {
                stateText.setText(R.string.live_no_start_title);
                stateImage.setBackgroundResource(R.mipmap.item_live_course_no);
                studentCount.setVisibility(View.INVISIBLE);
                view.findViewById(R.id.image_count).setVisibility(View.INVISIBLE);
            }
            if ("ing".equals(yjRecommendCourseLiveModelList.get(i).getLiveStatus())) {
                stateText.setText(R.string.live_ing_title);
                stateImage.setBackgroundResource(R.mipmap.item_live_course_ing);
                studentCount.setText(yjRecommendCourseLiveModelList.get(i).getLookStudent());
            }
            if ("over".equals(yjRecommendCourseLiveModelList.get(i).getLiveStatus())) {
                if ("Yes".equals(yjRecommendCourseLiveModelList.get(i).getIsReplay())) {
                    stateText.setText(R.string.live_look_back_title);
                    stateText.setTextColor(Color.parseColor("#0caafe"));
                    stateImage.setVisibility(View.GONE);
                }
                if ("No".equals(yjRecommendCourseLiveModelList.get(i).getIsReplay())) {
                    stateText.setText(R.string.live_back_ready_title);
                    stateText.setTextColor(Color.parseColor("#969696"));
                    stateImage.setVisibility(View.GONE);
                }
                studentCount.setText(yjRecommendCourseLiveModelList.get(i).getLookStudent());
            }
            view.setOnClickListener(onClickListener);
            listViews.add(view);
        }

    }

    /**
     * 点击事件
     */
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mIsLogin) {
                if ("OpenClass".equals(yjRecommendCourseLiveModelList.get(currentItem).getCourseVideoType())) {//公开课
                    if (yjRecommendCourseLiveModelList.get(currentItem).getLiveStatus().equals("ing")) {
                        getTakenLive(currentItem, false, 1);

                    }
                    if (yjRecommendCourseLiveModelList.get(currentItem).getLiveStatus().equals("over")) {
                        if ("Yes".equals(yjRecommendCourseLiveModelList.get(currentItem).getIsReplay())) {
                            getTakenBack(currentItem);
                        }
                        if ("No".equals(yjRecommendCourseLiveModelList.get(currentItem).getIsReplay())) {
                            getTakenLive(currentItem, true, 2);
                        }
                    }
                    if (yjRecommendCourseLiveModelList.get(currentItem).getLiveStatus().equals("no")) {
//                        showToast("直播未开始");
                        getTakenLive(currentItem, true, 1);
                    }
                } else {//正常直播课
                    //  progDialog = DialogUtil.showWaitDialog(getContext());//加载数据的loading
                    getisBuy(currentItem);
                }
            } else {
                startActivity(YJLoginDialog.class);
            }
        }
    };

    /**
     * 初始化坐标点
     */
    private void initDot() {
        if (linear.getChildCount() > 0) {
            linear.removeAllViews();
        }
        for (int i = 0; i < yjRecommendCourseLiveModelList.size(); i++) {
            ImageView view = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 10;
            params.rightMargin = 10;
            view.setLayoutParams(params);
            if (i == 0) {
                view.setBackgroundResource(R.mipmap.item_live_yuan_yes);
            } else {
                view.setBackgroundResource(R.mipmap.item_live_yuan_no);
            }
            linear.addView(view);
        }
    }

    /**
     * 更新坐标点
     *
     * @param position
     */
    private void updateDot(int position) {
        linear.removeAllViewsInLayout();
        for (int i = 0; i < yjRecommendCourseLiveModelList.size(); i++) {
            ImageView view = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20, 20);
            params.leftMargin = 10;
            params.rightMargin = 10;
            view.setLayoutParams(params);
            if (i == position) {
                view.setBackgroundResource(R.mipmap.item_live_yuan_yes);
            } else {
                view.setBackgroundResource(R.mipmap.item_live_yuan_no);
            }
            linear.addView(view);
        }
    }

}
