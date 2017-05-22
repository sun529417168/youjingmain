package com.youjing.yjeducation.ui.dispaly.activity;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.actualize.activity.YJActivateGiftCardSuccess;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMainActivity;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.StringUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.base.VStringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/21
 * Time 16:12
 */
@VLayoutTag(R.layout.activity_activate_gift_card)
public abstract class AYJActivateGiftCard extends YJBaseActivity implements IVClickDelegate {
    @VViewTag(R.id.btn_activate)
    private Button mBtn_activate;
    @VViewTag(R.id.edt_account)
    private EditText mEdt_account;
    @VViewTag(R.id.edt_password)
    private EditText mEdt_password;

    @VViewTag(R.id.txt_gift_card)
    private TextView mTxt_gift_card;
    @VViewTag(R.id.rl_back)
    private RelativeLayout mRl_back;

    private YJUser mYjUser;

    private String TAG = "YJActivateGiftCard";

    public static final VParamKey<String> FLAGE = new VParamKey<String>(null);
    String flag = "0";

    @Override
    protected void onLoadedView() {

        YJTitleLayoutController.initTitleBuleBg(this, getString(R.string.txt_activate_gift_card), true);
        flag = getTransmitData(FLAGE);

        if ("1".equals(flag)) {
            mTxt_gift_card.setVisibility(View.VISIBLE);
            mRl_back.setVisibility(View.GONE);
        } else {
            mTxt_gift_card.setVisibility(View.GONE);
            mRl_back.setVisibility(View.VISIBLE);
        }

//        mYjUser = YJGlobal.getYjUser();

//        SpannableStringBuilder builder = new SpannableStringBuilder(mTxt_login_address.getText().toString());
        //ForegroundColorSpan 为文字前景色，BackgroundColorSpan为文字背景色
        // Color.parseColor("#00CCFF") 返回 int 数值 ;
        ForegroundColorSpan buleSpan = new ForegroundColorSpan(Color.parseColor("#0caafe"));
//        builder.setSpan(buleSpan, 10, 22, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        mTxt_login_address.setText(builder);
    }

    @Override
    public void onClick(View view) {
        if (view == mBtn_activate) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            String cardNum = mEdt_account.getText().toString().trim();
            String password = mEdt_password.getText().toString().trim();
//             startActivity(YJActivateGiftCardSuccess.class);

            if (VStringUtil.isNullOrEmpty(cardNum)) {
                showToast(getString(R.string.txt_card_number));
                return;
            } else if (VStringUtil.isNullOrEmpty(password)) {
                showToast(getString(R.string.txt_login_password));
                return;
            } else {
                mYjUser = YJGlobal.getYjUser();
                activateCard(cardNum, password);
            }


        } else if (view == mTxt_gift_card) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJMainActivity.class);
            finish();
        } else if (view == mRl_back) {
            finish();
        }
    }

    //激活礼品卡
    public void activateCard(String admin, String password) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("cardCode", admin);
            objectMap.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.ACTIVATE_CARD, objectMap, true, new TextHttpResponseHandler() {
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
                    String code = json.getString("code");
                    if (code.equals("200")) {
                        String money = json.getJSONObject("data").getString("giftCardCoin");
                        mYjUser.setCoin(Integer.parseInt(mYjUser.getCoin()) + Integer.parseInt(money) + "");
                        notifyListener(YJNotifyTag.USER_COIN, "");
                        startActivity(createIntent(YJActivateGiftCardSuccess.class, createTransmitData(YJActivateGiftCardSuccess.MONEY, money + "," + flag)));
                        finish();
                    } else if (code.equals("300")) {
                       // StringUtils.tip(getApplicationContext(), getString(R.string.code_300));
                    } else if (code.equals("400")) {
                       // StringUtils.tip(getApplicationContext(), getString(R.string.code_400));
                    } else if (code.equals("401")) {
                      //  StringUtils.tip(getApplicationContext(), getString(R.string.code_401));
                    } else if (code.equals("402")) {
                      //  StringUtils.tip(getApplicationContext(), getString(R.string.code_402));
                    } else if (code.equals("403")) {
                     //   StringUtils.tip(getApplicationContext(), getString(R.string.code_403));
                    } else if (code.equals("404")) {
                      //  StringUtils.tip(getApplicationContext(), getString(R.string.code_404));
                    } else if (code.equals("405")) {
                       // StringUtils.tip(getApplicationContext(), getString(R.string.code_405));
                    } else if (code.equals("500")) {
                       // StringUtils.tip(getApplicationContext(), getString(R.string.code_500));
                    } else if (code.equals("600")) {
                        startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                        finishAll();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ("1".equals(flag)) {
            startActivity(YJMainActivity.class);
            finish();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}