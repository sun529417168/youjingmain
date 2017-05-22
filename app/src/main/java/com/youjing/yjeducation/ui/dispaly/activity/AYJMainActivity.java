package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;

import com.baidu.mobstat.StatService;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.component.ActivityBox;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLiveChannelActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJPersonalActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJTeacherActivity;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.wiget.TabItemView;

import org.vwork.mobile.ui.adapter.VPageViewAdapter;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.delegate.IVPageViewAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVPageViewChangeDelegate;
import org.vwork.mobile.ui.listener.VPageViewChangeListener;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.mobile.ui.widget.VPageView;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.notification.IVNotificationListener;

@VLayoutTag(R.layout.main)
public class AYJMainActivity extends YJBaseActivity implements IVPageViewAdapterDelegate, IVPageViewChangeDelegate, IVClickDelegate {
    @VViewTag(R.id.pager_main)
    private VPageView mPagerMain;
    @VViewTag(R.id.itv_course)
    private TabItemView mItv_course;
    @VViewTag(R.id.itv_teacher)
    private TabItemView mItv_teacher;
    @VViewTag(R.id.tiv_challenge)
    private TabItemView mTiv_challenge;
    @VViewTag(R.id.tiv_my)
    private TabItemView mTiv_my;
    @VViewTag(R.id.main_live_little_red)
    private ImageView mMain_live_little_red;
    @VViewTag(R.id.main_me_little_red)
    private ImageView mMain_me_little_red;

    public static final VParamKey<String> MAIN_POSITION = new VParamKey<String>(null);

    private String TAG = "AYJMainActivity";
    public String position = "0";
    private IUiListener qqShareListener;

    @Override
    protected void onLoadedView() {
        position = getTransmitData(MAIN_POSITION);
        VPageViewAdapter adapter = new VPageViewAdapter(this, mPagerMain);
        VPageViewChangeListener listener = new VPageViewChangeListener(this, mPagerMain);
        mPagerMain.setAdapter(adapter);
        mPagerMain.setChangeListener(listener);
        initTabItemView();

        bindNotifyListener();
        StringUtils.Log(TAG, "position=" + position);
        this.showTitleRed();
        this.showMeTitleRed();
        ActivityBox.AYJMainActivity = this;
        qqShareListener = new IUiListener() {
            @Override
            public void onCancel() {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast("分享取消");
            }

            @Override
            public void onComplete(Object response) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "sucess");
            }

            @Override
            public void onError(UiError e) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast(getString(R.string.share_fail));
            }
        };
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
            }
        }
    }

    public void showTitleRed() {
        if (SharedUtil.getBoolean(getContext(), "levelRed", false)) {
            mMain_live_little_red.setVisibility(View.VISIBLE);
        } else {
            mMain_live_little_red.setVisibility(View.INVISIBLE);
        }
    }

    public void showMeTitleRed() {
        if (SharedUtil.getBoolean(getContext(), "MyMessageRed", false) || SharedUtil.getBoolean(getContext(), "MyCourseRed", false) || SharedUtil.getBoolean(getContext(), "MyOrderRed", false) || SharedUtil.getBoolean(getContext(), "MyTaskRed", false) || SharedUtil.getBoolean(getContext(), "MyGiftRed", false)|| SharedUtil.getBoolean(getContext(), "MyFriendsRed", false)||SharedUtil.getBoolean(getContext(), "MyCouponRed", false)) {
            mMain_me_little_red.setVisibility(View.VISIBLE);
        } else {
            mMain_me_little_red.setVisibility(View.INVISIBLE);
        }
    }

    private void bindNotifyListener() {

        addListener(YJNotifyTag.MAIN_LESSON, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                int id = (int) value;
                mItv_course.setTabSelected(true);
                mItv_course.setContentLogoBack(R.mipmap.main_course_select);
                mPagerMain.setCurrentItem(id);

                mItv_teacher.setTabSelected(false);
                mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
                mTiv_challenge.setTabSelected(false);
                mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
                mTiv_my.setTabSelected(false);
                mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
            }

        });

        addListener(YJNotifyTag.MAIN_QUESTION, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                int id = (int) value;
                mItv_teacher.setTabSelected(true);
                mItv_teacher.setContentLogoBack(R.mipmap.main_ask_select);
                mPagerMain.setCurrentItem(id);

                mItv_course.setTabSelected(false);
                mItv_course.setContentLogoBack(R.mipmap.main_course_default);
                mTiv_challenge.setTabSelected(false);
                mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
                mTiv_my.setTabSelected(false);
                mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
            }
        });

        addListener(YJNotifyTag.MAIN_VIDEO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                int id = (int) value;
                mTiv_challenge.setTabSelected(true);
                mTiv_challenge.setContentLogoBack(R.mipmap.main_live_select);
                mPagerMain.setCurrentItem(id);

                mItv_teacher.setTabSelected(false);
                mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
                mItv_course.setTabSelected(false);
                mItv_course.setContentLogoBack(R.mipmap.main_course_default);
                mTiv_my.setTabSelected(false);
                mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
            }
        });
        addListener(YJNotifyTag.MAIN_MY, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                int id = (int) value;
                mTiv_challenge.setTabSelected(false);
                mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
                mPagerMain.setCurrentItem(id);

                mItv_teacher.setTabSelected(false);
                mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
                mItv_course.setTabSelected(false);
                mItv_course.setContentLogoBack(R.mipmap.main_course_default);
                mTiv_my.setTabSelected(true);
                mTiv_my.setContentLogoBack(R.mipmap.main_my_select);
            }
        });
    }

    private void initTabItemView() {
        mItv_course.setTabSelected(true);
        mItv_course.setContentLogoBack(R.mipmap.main_course_select);
        mPagerMain.setCurrentItem(0);


        if ("0".equals(position)) {
            mItv_course.setTabSelected(true);
            mItv_course.setContentLogoBack(R.mipmap.main_course_select);
            mPagerMain.setCurrentItem(0);
            StringUtils.Log(TAG, "position=" + position);
            mItv_teacher.setTabSelected(false);
            mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
            mTiv_challenge.setTabSelected(false);
            mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
            mTiv_my.setTabSelected(false);
            mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
        } else if ("1".equals(position)) {
            StringUtils.Log(TAG, "position=" + position);
            mItv_teacher.setTabSelected(true);
            mItv_teacher.setContentLogoBack(R.mipmap.main_ask_select);
            mPagerMain.setCurrentItem(1);

            mItv_course.setTabSelected(false);
            mItv_course.setContentLogoBack(R.mipmap.main_course_default);
            mTiv_challenge.setTabSelected(false);
            mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
            mTiv_my.setTabSelected(false);
            mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
        } else if ("2".equals(position)) {
            StringUtils.Log(TAG, "position=" + position);
            mTiv_challenge.setTabSelected(true);
            mTiv_challenge.setContentLogoBack(R.mipmap.main_live_select);
            mPagerMain.setCurrentItem(2);

            mItv_teacher.setTabSelected(false);
            mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
            mItv_course.setTabSelected(false);
            mItv_course.setContentLogoBack(R.mipmap.main_course_default);
            mTiv_my.setTabSelected(false);
            mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
        } else if ("3".equals(position)) {
            StringUtils.Log(TAG, "position=" + position);
            mTiv_challenge.setTabSelected(false);
            mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
            mPagerMain.setCurrentItem(3);

            mItv_teacher.setTabSelected(false);
            mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
            mItv_course.setTabSelected(false);
            mItv_course.setContentLogoBack(R.mipmap.main_course_default);
            mTiv_my.setTabSelected(true);
            mTiv_my.setContentLogoBack(R.mipmap.main_my_select);
        }

        mItv_course.setContentTextString(getString(R.string.txt_course));
        mItv_teacher.setContentTextString("提问");
        mTiv_challenge.setContentTextString("直播");
        mTiv_my.setContentTextString(getString(R.string.txt_my));
    }

    @Override
    public void onPageViewChanged(VPageView vPageView, int old, int cur) {
    /*    if (old != -1) {
            mButtons[old].setEnabled(true);
        }
        mButtons[cur].setEnabled(false);
        if (cur == 0) {
            mButtons[0].setEnabled(false);
        } else if (cur == 1) {
            mButtons[1].setEnabled(false);
        }*/
        StringUtils.Log(TAG, "old=" + old + ":::::::cur=" + cur);
    }

    private boolean mIsLogin;

    @Override
    public void onClick(View view) {
        if (view == mItv_course) {
            mItv_course.setTabSelected(true);
            mItv_course.setContentLogoBack(R.mipmap.main_course_select);
            mPagerMain.setCurrentItem(0);

            mItv_teacher.setTabSelected(false);
            mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
            mTiv_challenge.setTabSelected(false);
            mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
            mTiv_my.setTabSelected(false);
            mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
        } else if (view == mItv_teacher) {
            mItv_teacher.setTabSelected(true);
            mItv_teacher.setContentLogoBack(R.mipmap.main_ask_select);
            mPagerMain.setCurrentItem(1);

            mItv_course.setTabSelected(false);
            mItv_course.setContentLogoBack(R.mipmap.main_course_default);
            mTiv_challenge.setTabSelected(false);
            mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
            mTiv_my.setTabSelected(false);
            mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
        } else if (view == mTiv_challenge) {
            SharedUtil.setBoolean(getContext(), "levelRed", false);
            mMain_live_little_red.setVisibility(View.INVISIBLE);
            mTiv_challenge.setTabSelected(true);
            mTiv_challenge.setContentLogoBack(R.mipmap.main_live_select);
            mPagerMain.setCurrentItem(2);

            mItv_teacher.setTabSelected(false);
            mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
            mItv_course.setTabSelected(false);
            mItv_course.setContentLogoBack(R.mipmap.main_course_default);
            mTiv_my.setTabSelected(false);
            mTiv_my.setContentLogoBack(R.mipmap.main_my_default);
        } else if (view == mTiv_my) {
            mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
            if (!mIsLogin) {
                startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, -1).set(YJLoginActivity.MY_LOGIN_OUT, false)));
            } else {
                mTiv_my.setTabSelected(true);
                mTiv_my.setContentLogoBack(R.mipmap.main_my_select);
                mPagerMain.setCurrentItem(3);
                mTiv_challenge.setTabSelected(false);
                mTiv_challenge.setContentLogoBack(R.mipmap.main_live_default);
                mItv_teacher.setTabSelected(false);
                mItv_teacher.setContentLogoBack(R.mipmap.main_ask_default);
                mItv_course.setTabSelected(false);
                mItv_course.setContentLogoBack(R.mipmap.main_course_default);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    public Fragment[] createPageViewFragments(VPageView vPageView) {
        return new Fragment[]{startVirtualActivity(new YJCourseActivity()), startVirtualActivity(new YJTeacherActivity()), startVirtualActivity(new YJLiveChannelActivity()), startVirtualActivity(new YJPersonalActivity())};
    }


    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            showToast("再按一退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finishAll();
            System.exit(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.showMeTitleRed();

        StatService.onResume(getContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(getContext());
    }
}
