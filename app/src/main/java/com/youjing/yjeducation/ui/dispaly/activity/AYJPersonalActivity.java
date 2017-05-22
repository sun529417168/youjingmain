package com.youjing.yjeducation.ui.dispaly.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.component.ActivityBox;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCount;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.actualize.activity.YJActivateGiftCard;
import com.youjing.yjeducation.ui.actualize.activity.YJDownLoadActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJFeedBackActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMedalActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMyWalletActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJPersonalCenterLevelActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJRechargeWhaleMoney;
import com.youjing.yjeducation.ui.actualize.activity.YJSettingActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJUserInfoActivity;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.GetDistrictData;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.YjGetUserInfo;
import com.youjing.yjeducation.wiget.CustomImage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVVirtualActivity;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.notification.IVNotificationListener;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 18:01
 */
@VLayoutTag(R.layout.activity_my)
public abstract class AYJPersonalActivity extends AVVirtualActivity implements IVClickDelegate {

    private String TAG = "AYJPersonalActivity";
    @VViewTag(R.id.re_setting)
    private RelativeLayout mRe_setting;
    @VViewTag(R.id.re_my_order)
    private RelativeLayout mRe_my_order;
    @VViewTag(R.id.re_my_card)
    private RelativeLayout mRe_my_card;
    @VViewTag(R.id.re_down_load)
    private RelativeLayout mRe_down_load;
    @VViewTag(R.id.img_user_photo)
    protected CustomImage mImg_user_photo;
    @VViewTag(R.id.btn_recharge)
    private Button mBtn_recharge;
    @VViewTag(R.id.re_active_card)
    private RelativeLayout mRe_active_card;
    @VViewTag(R.id.re_my_task)
    private RelativeLayout mRe_my_task;
    @VViewTag(R.id.re_my_course)
    private RelativeLayout mRe_my_course;
    @VViewTag(R.id.img_message)
    private ImageView mImg_message;
    @VViewTag(R.id.txt_user_name)
    protected TextView mTxt_user_name;
    @VViewTag(R.id.txt_whale_num)
    protected TextView mTxt_whale_num;
    @VViewTag(R.id.rl_money)
    protected RelativeLayout mRl_money;
    @VViewTag(R.id.layout_my_gallery)
    protected LinearLayout mLayout_my_gallery;
    @VViewTag(R.id.img_my_medal_arrow_layout)
    protected RelativeLayout mImg_my_medal_arrow_layout;
    @VViewTag(R.id.re_coupon)
    protected RelativeLayout re_coupon;

    @VViewTag(R.id.txt_unpaid_course)
    protected TextView mTxt_unpaid_course;
    @VViewTag(R.id.txt_unpaid_order)
    protected TextView mTxt_unpaid_order;
    @VViewTag(R.id.txt_unpaid_wallet)
    protected TextView mTxt_unpaid_wallet;
    @VViewTag(R.id.txt_unpaid_task)
    protected TextView mTxt_unpaid_task;
    @VViewTag(R.id.txt_level_num)
    protected TextView mTxt_level_num;
    @VViewTag(R.id.txt_coupon_num)
    protected TextView txt_coupon_num;
    @VViewTag(R.id.txt_level)
    protected TextView txt_level;
    @VViewTag(R.id.layout_my_medal_and_leaveName)
    private LinearLayout mLayout_my_medal_and_leaveName;
    @VViewTag(R.id.invite_friends_layout)
    private RelativeLayout mInvite_friends_layout;
    @VViewTag(R.id.img_message_little)
    protected ImageView mLmg_message_little;
    @VViewTag(R.id.my_course_titleRed)
    protected ImageView mMy_course_titleRed;//我的课程
    @VViewTag(R.id.my_order_titleRed)
    protected ImageView mMy_order_titleRed;//我的订单
    @VViewTag(R.id.my_task_titleRed)
    protected ImageView mMy_task_titleRed;//我的任务中心
    @VViewTag(R.id.my_gift_titleRed)
    protected ImageView mMy_gift_titleRed;//我的礼卡包
    @VViewTag(R.id.my_friends_titleRed)
    protected ImageView mMy_friends_titleRed;//邀请好友
    @VViewTag(R.id.my_coupon_titleRed)
    protected ImageView mMy_coupon_titleRed;//优惠券
    @VViewTag(R.id.re_fed_back)
    private RelativeLayout mRe_fed_back;//帮助与反馈


    protected Bitmap LOAD_BITMAP;
    protected Bitmap NO_LOAD_BITMAP;
    protected String imagePath;


    private YJUser mYjUser;
    private YJCount mYjCount;
    public static int flag = -1;


    @Override
    protected void onLoadedView() {

        StringUtils.showEvaluationDialog(AYJPersonalActivity.this);
        GetDistrictData.setData(getContext());
        inintData();
    }
    private void inintUI() {
        showCount();
        bindNotifyListener();
        this.showMessageRed(SharedUtil.getBoolean(getContext(), "MyMessageRed", false));
        this.showCourseRed(SharedUtil.getBoolean(getContext(), "MyCourseRed", false));
        this.showOrderRed(SharedUtil.getBoolean(getContext(), "MyOrderRed", false));
        this.showTaskRed(SharedUtil.getBoolean(getContext(), "MyTaskRed", false));
        this.showGiftRed(SharedUtil.getBoolean(getContext(), "MyGiftRed", false));
        this.showInvite(SharedUtil.getBoolean(getContext(), "MyFriendsRed", false));
        this.showCoupon(SharedUtil.getBoolean(getContext(), "MyCouponRed", false));
        ActivityBox.AYJPersonalActivity = this;
    }
    private void inintData() {
        //个人信息接口
            YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    StringUtils.Log(TAG, "getUserInfo失败=" + s);
                    showToast(getContext().getString(R.string.no_net_work));
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        StringUtils.Log(TAG, "成功getUserInfo=" + s);
                        JSONObject json = null;
                        json = new JSONObject(s);
                        switch (json.getInt("code")) {
                            case 200:
                                JSONObject jsonData = new JSONObject(json.getString("data"));
                                mYjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                                StringUtils.Log(TAG, "yjUser.toString()=" + mYjUser.toString());
                                YJGlobal.setYjUser(mYjUser);
                                inintUI();
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
    public void showMessageRed(boolean isFlag) {
        StringUtils.showTitleRed(isFlag, mLmg_message_little);
    }

    public void showCourseRed(boolean isFlag) {
        StringUtils.showTitleRed(isFlag, mMy_course_titleRed);
    }

    public void showOrderRed(boolean isFlag) {
        StringUtils.showTitleRed(isFlag, mMy_order_titleRed);
    }

    public void showTaskRed(boolean isFlag) {
        StringUtils.showTitleRed(isFlag, mMy_task_titleRed);
    }

    public void showInvite(boolean isFlag) {
        StringUtils.showTitleRed(isFlag, mMy_friends_titleRed);
    }

    public void showGiftRed(boolean isFlag) {
        StringUtils.showTitleRed(isFlag, mMy_gift_titleRed);
    }

    public void showCoupon(boolean isFlag) {
        StringUtils.showTitleRed(isFlag, mMy_coupon_titleRed);
    }

    @Override
    public void onClick(View view) {
        if (view == mRe_setting) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJSettingActivity.class);
        } else if (view == mImg_user_photo) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJUserInfoActivity.class);
        } else if (view == mBtn_recharge) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
//            startActivity(YJMyWalletActivity.class);
            startActivity(YJRechargeWhaleMoney.class);
        } else if (view == mRl_money) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJMyWalletActivity.class);
        } else if (view == re_coupon) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            SharedUtil.setBoolean(getContext(), "MyCouponRed", false);
            mMy_coupon_titleRed.setVisibility(View.INVISIBLE);
//            startActivity(YJCouponActivity.class);
            getMyCoupon();
        } else if (view == mRe_my_order) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            SharedUtil.setBoolean(getContext(), "MyOrderRed", false);
            mMy_order_titleRed.setVisibility(View.INVISIBLE);
            getMyOrder();
        } else if (view == mRe_my_card) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            SharedUtil.setBoolean(getContext(), "MyGiftRed", false);
            mMy_gift_titleRed.setVisibility(View.INVISIBLE);
            getMyCard();
        } else if (view == mRe_my_task) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            SharedUtil.setBoolean(getContext(), "MyTaskRed", false);
            mMy_task_titleRed.setVisibility(View.INVISIBLE);
            getMyTask();
        } else if (view == mImg_message) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            SharedUtil.setBoolean(getContext(), "MyMessageRed", false);
            mLmg_message_little.setVisibility(View.INVISIBLE);
            getMessage();

        } else if (view == mRe_down_load) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJDownLoadActivity.class);
        } else if (view == mRe_my_course) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            SharedUtil.setBoolean(getContext(), "MyCourseRed", false);
            mMy_course_titleRed.setVisibility(View.INVISIBLE);
            getMyCourse();
        } else if (view == mRe_active_card) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJActivateGiftCard.class);
        } else if (view == mLayout_my_gallery) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJMedalActivity.class);
        } else if (view == mImg_my_medal_arrow_layout) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJMedalActivity.class);
        } else if (view == mLayout_my_medal_and_leaveName) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJPersonalCenterLevelActivity.class);
        } else if (view == mInvite_friends_layout) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            SharedUtil.setBoolean(getContext(), "MyFriendsRed", false);
            mMy_friends_titleRed.setVisibility(View.INVISIBLE);
            getMyInvite();
        } else if (view == mRe_fed_back) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJFeedBackActivity.class);
        }

    }


    private void bindNotifyListener() {
        getParentActivity().getApp().getTopActivity().addListener(YJNotifyTag.HEAD_USER, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String head = (String) value;
                initPhoto(head);
            }
        });

        getParentActivity().getApp().getTopActivity().addListener(YJNotifyTag.USER_NICKNAME, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String name = (String) value;
                mTxt_user_name.setText(name);
            }
        });

        addListener(YJNotifyTag.PAY_SUCESS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                YjGetUserInfo.getUserCoin((IVActivity) getContext(),mTxt_whale_num);
            }
        });
        addListener(YJNotifyTag.MY_TASK, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {

                if (ClickUtil.isFastClick()) {
                    return;
                }
                getMyTask();
                StringUtils.Log(TAG, "getMyTask");
            }
        });

        addListener(YJNotifyTag.MY_CARD, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {

                if (ClickUtil.isFastClick()) {
                    return;
                }
                getMyCard();
                StringUtils.Log(TAG, "getMyCard");
            }
        });
    }

    private void initPhoto(String head) {
        if (LOAD_BITMAP == null) {
            LOAD_BITMAP = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo);
        }
        if (NO_LOAD_BITMAP == null) {
            NO_LOAD_BITMAP = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo);
        }
        StringUtils.Log(TAG, "head = " + head);
        BitmapUtils.create(getContext()).display(mImg_user_photo, head + "@0o_0l_150w_80q.src", LOAD_BITMAP, NO_LOAD_BITMAP);
    }

    //显示Count
    private void showCount() {
        getMyCount();
    }

    public abstract void getMyOrder();

    public abstract void getMessage();

    public abstract void getMyCourse();

    public abstract void getMyCard();

    public abstract void getMyCount();

    public abstract void getMyTask();

    public abstract void getMyInvite();

    public abstract void getMyCoupon();

    @Override
    public void onResume() {
        super.onResume();
        StringUtils.showEvaluationDialog(AYJPersonalActivity.this);
        this.showMessageRed(SharedUtil.getBoolean(getContext(), "MyMessageRed", false));
        this.showCourseRed(SharedUtil.getBoolean(getContext(), "MyCourseRed", false));
        this.showOrderRed(SharedUtil.getBoolean(getContext(), "MyOrderRed", false));
        this.showTaskRed(SharedUtil.getBoolean(getContext(), "MyTaskRed", false));
        this.showGiftRed(SharedUtil.getBoolean(getContext(), "MyGiftRed", false));
        this.showInvite(SharedUtil.getBoolean(getContext(), "MyFriendsRed", false));
        this.showCoupon(SharedUtil.getBoolean(getContext(), "MyCouponRed", false));
        showCount();
    }
}