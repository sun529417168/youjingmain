package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCardModel;
import com.youjing.yjeducation.model.YJTeacherAskModel;
import com.youjing.yjeducation.ui.actualize.activity.YJMainActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJLoginDialog;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DialogUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.wiget.CustomImage;
import com.youjing.yjeducation.wiget.MorePopWindow;
import com.youjing.yjeducation.wiget.MsgListView;

import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVVirtualActivity;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.Collection;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:57
 */
@VLayoutTag(R.layout.activity_teacher)
public abstract class AYJTeacherActivity extends AVVirtualActivity implements IVClickDelegate, IVAdapterDelegate {
    @VViewTag(R.id.txt_title)
    private TextView txt_title;
    @VViewTag(R.id.iv_arrow)
    private ImageView mIv_arrow;
    @VViewTag(R.id.msgListView)
    protected MsgListView mMsgListView;
    @VViewTag(R.id.layout_title)
    private RelativeLayout layout_title;
    @VViewTag(R.id.re_title)
    private RelativeLayout re_title;
    private String TAG = "AYJTeacherActivity";


    private static final String ACTION_NAME = "发送广播";
    private String mGrade;
    private String mSubject;
    private int mGradeNum, mSubjectNum;
    protected boolean mIsLogin = false;
    private RotateAnimation animation, reverseAnimation;
    private MorePopWindow morePopWindow;

    protected List<YJTeacherAskModel> yjTeacherAskModelList;
    protected int page = 1;
    protected PaginationInfo paginationInfo;
    protected Dialog progDialog;
    protected int timeOut = 0;

    @Override
    protected void onLoadedView() {
        StringUtils.showEvaluationDialog(AYJTeacherActivity.this);
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        mGrade = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("grade", YJGlobal.getGradeList().get(0).getGradeName());
        mSubject = PreferenceManager.getDefaultSharedPreferences(getContext()).getString("subject", YJGlobal.getGradeList().get(0).getSubjectVos().get(0).getSubjectName());

        mGradeNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("gradeNum", 0);
        mSubjectNum = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt("subjectNum", 0);

        txt_title.setText(mGrade + mSubject);
        if (YJGlobal.getGradeList() != null) {
            getTeacherList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
        }
        initAnimation();
        registerBoradcastReceiver();
        getNotifyListener();
        initXListView();
    }

    protected void initXListView() {
        //MsgListView相关

        mMsgListView.setPullRefreshEnable(true);
        mMsgListView.setPullLoadEnable(true);
        mMsgListView.setXListViewListener(new MsgListView.IXListViewListener() {
            @Override
            public void onRefresh() {

                if (YJGlobal.getGradeList() != null) {
                    page = 1;
                    paginationInfo = null;
                    yjTeacherAskModelList.clear();
                    getTeacherList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
                    StringUtils.Log(TAG, "YJGlobal.getMy_grade_id=" + YJGlobal.getGradeList().get(mGradeNum).getGradeId());
                    StringUtils.Log(TAG, "YJGlobal.getMy_subjectId=" + YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
                }
            }

            @Override
            public void onLoadMore() {
                if (paginationInfo != null) {
                    if (paginationInfo.getCurrentPage() <= paginationInfo.getTotalPages()) {
                        getAddLoadMore(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), ++page);
                    } else if (paginationInfo.getCurrentPage() > paginationInfo.getTotalPages()) {
                        showToast(getResources().getString(R.string.load_more_nothing));
                    }
                } else {
                    getAddLoadMore(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId(), ++page);
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
                if (mIsLogin) {
                    progDialog = DialogUtil.showWaitDialog(getContext());//加载数据的loading 先注释掉 用的时候换样式
//                    timeOut =6;
//                    new TimeThread().start(); //启动新的线程
                    enterGroup(i-1);
                } else {
                    /*YJLoginDialog dialog = new YJLoginDialog();
                    showDialog(dialog);*/
                    startActivity(YJLoginDialog.class);
                }
            }
        });
    }



    @Override
    public void onClick(View view) {
        if (view == txt_title) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showPopWindow();
            mIv_arrow.clearAnimation();
            mIv_arrow.startAnimation(animation);

        } else if (view == layout_title) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showPopWindow();
            mIv_arrow.clearAnimation();
            mIv_arrow.startAnimation(animation);

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
        StringUtils.showEvaluationDialog(AYJTeacherActivity.this);
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
                            getTeacherList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
                            StringUtils.Log(TAG, "YJGlobal.getMy_grade_id=" + YJGlobal.getGradeList().get(mGradeNum).getGradeId());
                            StringUtils.Log(TAG, "YJGlobal.getMy_subjectId=" + YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
                        }

                    }

                }

            }
        }
    };

    private VAdapter adapter;

    private void getNotifyListener() {
        addListener(YJNotifyTag.TEACHER_LIST, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjTeacherAskModelList = (List<YJTeacherAskModel>) o;
                if (yjTeacherAskModelList != null && yjTeacherAskModelList.size() == 0) {
                    findViewById(R.id.activity_teacher_list_nothing).setVisibility(View.VISIBLE);
                    findViewById(R.id.activity_teacher_data).setVisibility(View.GONE);
                } else {
                    findViewById(R.id.activity_teacher_list_nothing).setVisibility(View.GONE);
                    findViewById(R.id.activity_teacher_data).setVisibility(View.VISIBLE);
                }
                if (yjTeacherAskModelList == null) {
                    adapter.notifyDataSetChanged();
                } else {
                    if (adapter == null) {
                        adapter = new VAdapter(AYJTeacherActivity.this, mMsgListView);
                        mMsgListView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            }

        });
        addListener(YJNotifyTag.USER_LOGIN, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
                if (YJGlobal.getGradeList() != null) {
                    getTeacherList(YJGlobal.getGradeList().get(mGradeNum).getGradeId(), YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
                    StringUtils.Log(TAG, "YJGlobal.getMy_grade_id=" + YJGlobal.getGradeList().get(mGradeNum).getGradeId());
                    StringUtils.Log(TAG, "YJGlobal.getMy_subjectId=" + YJGlobal.getGradeList().get(mGradeNum).getSubjectVos().get(mSubjectNum).getSubjectId());
                }
            }
        });

        addListener(YJNotifyTag.TEACHER_LIST_LOAD_MORE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjTeacherAskModelList.addAll((Collection<? extends YJTeacherAskModel>) o);
                if (page > paginationInfo.getTotalPages()) {
                    showToast(getResources().getString(R.string.load_more_nothing));
                }

            }

        });
        addListener(YJNotifyTag.TEACHER_LIST_PAGE_INFO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                paginationInfo = (PaginationInfo) value;
            }
        });

    }

    private void showPopWindow() {
        morePopWindow = new MorePopWindow(getActivity(), getTopActivity());
        morePopWindow.showPopupWindow(re_title);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getContext().unregisterReceiver(mBroadcastReceiver);
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJMainTeacherListItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjTeacherAskModelList != null) {
            return yjTeacherAskModelList.size();
        } else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.activity_main_teacher_item)
    class YJMainTeacherListItem extends AVAdapterItem {
        @VViewTag(R.id.img_teacher_photo)
        private CustomImage img_teacher_photo;
        @VViewTag(R.id.txt_student_num)
        private TextView txt_student_num;
        @VViewTag(R.id.txt_teacher_name)
        private TextView txt_teacher_name;
        @VViewTag(R.id.txt_online_status)
        private TextView txt_online_status;
        @VViewTag(R.id.txt_course_num)
        private TextView txt_course_num;
        @VViewTag(R.id.txt_enter)
        private TextView txt_enter;

        @Override
        public void update(final int position) {
            if (yjTeacherAskModelList != null) {
                if (!TextUtils.isEmpty(yjTeacherAskModelList.get(position).getTeacherPic())) {
                    BitmapUtils.create(getContext()).display(img_teacher_photo, yjTeacherAskModelList.get(position).getTeacherPic());
                } else {
                    img_teacher_photo.setImageResource(R.mipmap.img_app_logo);
                }
                txt_student_num.setText("学生数: " + yjTeacherAskModelList.get(position).getStudentCount());
                txt_teacher_name.setText(yjTeacherAskModelList.get(position).getTrueName());
                txt_course_num.setText("课程数: " + yjTeacherAskModelList.get(position).getCourseCount());
                if (yjTeacherAskModelList.get(position).getIsOnline().equals("No")) {
                    txt_online_status.setText("离线");
                    txt_enter.setTextColor(getResources().getColor(R.color.gray_text));
                    txt_enter.setBackgroundResource(R.mipmap.img_btn_gray_bg);
                    txt_online_status.setTextColor(getResources().getColor(R.color.gray_text));
                    txt_online_status.setBackgroundResource(R.mipmap.img_on_status_no);
                } else {
                    txt_online_status.setText("在线");
                    txt_enter.setTextColor(getResources().getColor(R.color.white));
                    txt_enter.setBackgroundResource(R.mipmap.img_btn_bule_bg);
                    txt_online_status.setTextColor(getResources().getColor(R.color.white));
                    txt_online_status.setBackgroundResource(R.mipmap.img_yellow_bg);
                }
            }
            txt_enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ClickUtil.isFastDoubleClick()) {
                        return;
                    }
                    if (mIsLogin) {
                        progDialog = DialogUtil.showWaitDialog(getContext());//加载数据的loading 先注释掉 用的时候换样式
//                        timeOut =6;
//                        new TimeThread().start(); //启动新的线程
                        enterGroup(position);
                    } else {
                    /*YJLoginDialog dialog = new YJLoginDialog();
                    showDialog(dialog);*/
                        startActivity(YJLoginDialog.class);
                    }
                }
            });
        }
    }


    public abstract void getTeacherList(String gradeId, String subjectId);

    public abstract void getAddLoadMore(String gradeId, String subjectId, int page);

    public abstract void enterGroup(int position);


}
