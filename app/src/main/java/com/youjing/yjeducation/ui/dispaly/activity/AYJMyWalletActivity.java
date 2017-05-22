package com.youjing.yjeducation.ui.dispaly.activity;

import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJRechargeWhaleMoney;
import com.youjing.yjeducation.ui.actualize.activity.YJTodayProfitActivity;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.YjGetUserInfo;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.notification.IVNotificationListener;

/**
 * user  秦伟宁
 * Date 2016/3/31
 * Time 20:13
 */
@VLayoutTag(R.layout.activity_my_wallet)
public abstract class AYJMyWalletActivity extends BaseSwipeBackActivity implements IVClickDelegate {

    @VViewTag(R.id.re_totay_profit)
    private RelativeLayout mRe_totay_profit;
    @VViewTag(R.id.re_consume_history)
    private RelativeLayout mRre_consume_history;
    @VViewTag(R.id.btn_recharge)
    private Button btn_recharge;
    @VViewTag(R.id.txt_whale_money_num)
    private TextView txt_whale_money_num;
    @VViewTag(R.id.re_appoint_course)
    private RelativeLayout mRe_appoint_course;
    @VViewTag(R.id.re_goto_question)
    private RelativeLayout mRe_goto_question;
    @VViewTag(R.id.re_goto_video)
    private RelativeLayout mRe_goto_video;

    @VViewTag(R.id.rl_back)
    private RelativeLayout mRl_back;

    private YJUser mYjUser;

    public static final VParamKey<String> FLAGE = new VParamKey<String>(null);
    String flag="0";
    private  String TAG = "AYJMyWalletActivity";
    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "我的钱包", true);

        flag = getTransmitData(FLAGE);

        getUserInfo();
        bindNotifyListener();
    }

    @Override
    public void onClick(View view) {
        if (view == mRe_totay_profit) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJTodayProfitActivity.class);
        } else if (view == mRre_consume_history) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            getMyConsumHistory();
        } else if (view == btn_recharge) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJRechargeWhaleMoney.class);
        } else if (view == mRe_appoint_course) {// 找课程
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            notifyListener(YJNotifyTag.MAIN_LESSON, 0);
            finish();
        } else if (view == mRe_goto_question) {//
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            notifyListener(YJNotifyTag.MAIN_QUESTION, 1);
            finish();
        } else if (view == mRe_goto_video) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            notifyListener(YJNotifyTag.MAIN_VIDEO, 2);
            finish();
        }else if(view==mRl_back){
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            finish();
        }
    }

    private void bindNotifyListener() {

        addListener(YJNotifyTag.PAY_SUCESS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                YjGetUserInfo.getUserCoin((IVActivity) getContext(), txt_whale_money_num);
            }
        });

    }

    protected abstract void getMyConsumHistory();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeListeners();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ("1".equals(flag)){
           // startActivity(YJMainActivity.class);
           // finish();
            return true;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }


    private void getUserInfo() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INFO, null, true, new TextHttpResponseHandler() {
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
                                     YJUser yjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                                     YJGlobal.setYjUser(yjUser);
                                     if (!TextUtils.isEmpty(yjUser.getCoin())) {
                                         txt_whale_money_num.setText(yjUser.getCoin());
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
