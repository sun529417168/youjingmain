package com.youjing.yjeducation.ui.actualize.activity;

import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.CustomerMedalModel;
import com.youjing.yjeducation.model.YJCardModel;
import com.youjing.yjeducation.model.YJCount;
import com.youjing.yjeducation.model.YJCouponModel;
import com.youjing.yjeducation.model.YJMessageModel;
import com.youjing.yjeducation.model.YJMyCourseModel;
import com.youjing.yjeducation.model.YJMyInviteModel;
import com.youjing.yjeducation.model.YJOrderModel;
import com.youjing.yjeducation.model.YJTaskTypeModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.dispaly.activity.AYJInviteFriendActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJPersonalActivity;
import com.youjing.yjeducation.util.SharedUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 18:01
 */
public class YJPersonalActivity extends AYJPersonalActivity {
    @VViewTag(R.id.layout_my_gallery)
    private LinearLayout mLayout_my_gallery;
    private String TAG = "YJPersonalActivity";

    private YJUser mYjUser;
    private YJCount mYjCount;
    LinearLayout group;
    @Override
    public void getMyOrder() {
        startActivity(YJMyOrderActivity.class);
    }

    @Override
    public void getMessage() {
        SharedUtil.setString(getContext(), "newNoticeId", mYjCount.getNoticeId());
        startActivity(YJSystemMessageActivity.class);
    }

    @Override
    public void getMyCourse() {
        startActivity(YJMyCourseActivity.class);
    }
    @Override
    public void getMyCard() {
        startActivity(YJMyCardsActivity.class);
    }

    @Override
    public void getMyCount() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COUNT, null, true, new TextHttpResponseHandler() {
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
                            mYjCount = JSON.parseObject(jsonData.getString("customer"), YJCount.class);
                            StringUtils.Log("请求个人大概===", mYjCount.toString());
                            if (null != mYjCount.getCustomerMedalList()) {
                                setMedal(mYjCount.getCustomerMedalList());
                            }
                            if (null != mYjCount) {
                                if (!TextUtils.isEmpty(mYjCount.getCustomerCardPackageCount()) && !mYjCount.getCustomerCardPackageCount().equals("null")) {
                                    mTxt_unpaid_wallet.setText(mYjCount.getCustomerCardPackageCount());
                                } else {
                                    mTxt_unpaid_wallet.setText("0");
                                }
                                if (!TextUtils.isEmpty(mYjCount.getCustomerCourseCount()) && !mYjCount.getCustomerCourseCount().equals("null")) {
                                    mTxt_unpaid_course.setText(mYjCount.getCustomerCourseCount());
                                } else {
                                    mTxt_unpaid_course.setText("0");
                                }
                                if (!TextUtils.isEmpty(mYjCount.getCustomerTaskCount()) && !mYjCount.getCustomerTaskCount().equals("null")) {
                                    mTxt_unpaid_task.setText(mYjCount.getCustomerTaskCount());
                                } else {
                                    mTxt_unpaid_task.setText("0");
                                }
                                if (!TextUtils.isEmpty(mYjCount.getCustomerOrderCount()) && !mYjCount.getCustomerOrderCount().equals("null")) {
                                    mTxt_unpaid_order.setText(mYjCount.getCustomerOrderCount());
                                } else {
                                    mTxt_unpaid_order.setText("0");
                                }
                                if (!TextUtils.isEmpty(mYjCount.getLevelName()) && !mYjCount.getLevelName().equals("null")) {
                                    txt_level.setText(mYjCount.getLevelName());
                                } else {
                                    txt_level.setText("水手");
                                }
                                if (!TextUtils.isEmpty(mYjCount.getLevel()) && !mYjCount.getLevel().equals("null")) {
                                    mTxt_level_num.setText(mYjCount.getLevel());
                                } else {
                                    mTxt_level_num.setText("1");
                                }

                                if (!TextUtils.isEmpty(mYjCount.getNickName()) && !mYjCount.getNickName().equals("null")) {
                                    mTxt_user_name.setText(mYjCount.getNickName());
                                } else {
                                    mTxt_user_name.setText("未填写");
                                }
                                if (!TextUtils.isEmpty(mYjCount.getCoin()) && !mYjCount.getCoin().equals("null")) {
                                    mTxt_whale_num.setText(mYjCount.getCoin());
                                } else {
                                    mTxt_whale_num.setText("0");
                                }

                                if(!TextUtils.isEmpty(mYjCount.getCustomerCouponCount()) && !mYjCount.getCustomerCouponCount().equals("null")) {
                                    txt_coupon_num.setText(mYjCount.getCustomerCouponCount());
                                } else {
                                    txt_coupon_num.setText("0");
                                }
                                if (!TextUtils.isEmpty(mYjCount.getPic()) && !mYjCount.getPic().equals("null")) {
                                    initPhoto(mYjCount.getPic());
                                } else {
                                    mImg_user_photo.setImageResource(R.mipmap.img_app_logo);
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

    private void setMedal(ArrayList<CustomerMedalModel> customerMedalModel) {
        LinearLayout group = (LinearLayout) findViewById(R.id.layout_my_gallery);
        if (group.getChildCount() > 0) {
            group.removeAllViews();
        }
        for (int i = 0; i < customerMedalModel.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(100, 100);
            params.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(params);
            BitmapUtils.create(getContext()).display(imageView, customerMedalModel.get(i).getIconUrl());
            group.addView(imageView);
        }
    }

    @Override
    public void getMyTask() {
        startActivity(YJMyTaskActivity.class);
    }

    @Override
    public void getMyInvite() {
        startActivity(YJInviteFriendActivity.class);
    }

    @Override
    public void getMyCoupon() {
        startActivity(YJCouponActivity.class);
    }


    private void initPhoto(String head) {
        if (LOAD_BITMAP == null) {
            LOAD_BITMAP = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo);
        }
        if (NO_LOAD_BITMAP == null) {
            NO_LOAD_BITMAP = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo);
        }
        StringUtils.Log(TAG, "head = " + head);
        BitmapUtils.create(getContext()).display(mImg_user_photo, head, LOAD_BITMAP, NO_LOAD_BITMAP);
    }
}
