package com.youjing.yjeducation.ui.dispaly.activity;

import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
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
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMainActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMyWalletActivity;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.MakeSign;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * user  秦伟宁
 * Date 2016/3/21
 * Time 16:52
 */
@VLayoutTag(R.layout.activity_activate_gift_card_success)
public class AYJActivateGiftCardSuccess extends YJBaseActivity implements IVClickDelegate {
    private String TAG = "AYJActivateGiftCardSuccess";
    @VViewTag(R.id.btn_activate_success)
    private Button mBtn_activate;

    @VViewTag(R.id.obtain_money)
    private TextView mObtain_money;
    @VViewTag(R.id.txt_regist_info)
    private TextView txt_regist_info;

    @VViewTag(R.id.rl_back)
    private RelativeLayout mRl_back;
    private String regCount;
    public static final VParamKey<String> MONEY = new VParamKey<String>(null);
    protected String mRegistInfo = "";
    public static final VParamKey<String> REGIST_INFO = new VParamKey<String>(null);
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    notifyListener(YJNotifyTag.MY_TASK, "");
                    StringUtils.Log(TAG, "MY_TASK");
                    break;
                }
                case 1: {
                    notifyListener(YJNotifyTag.MY_CARD, "");
                    StringUtils.Log(TAG, "MY_CARD");
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "注册成功", true);
        //flag = getIntent().getStringExtra("flag");
        mRegistInfo = getTransmitData(REGIST_INFO);
        getRegSuccess();


        mRl_back.setVisibility(View.GONE);

    }

    private void intData(String regCount) {
        txt_regist_info.setText("注册成功，奖励"+(TextUtils.isEmpty(regCount)?"0":regCount)+"鲸币，还有注册大礼包，赶快领取！");
        mBtn_activate.setText("去领取");
    }


    /**
     * stt
     * 时间:2016.5.17
     * 方法说明：注册成功后获取鲸币的几口
     */
    public void getRegSuccess() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_REGSUCCESS, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
//                mMsgListView.stopRefresh();
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
                            regCount = jsonData.getString("regCount");
                            String totalCount = jsonData.getString("totalCount");
                            mObtain_money.setText("恭喜您获得" + (TextUtils.isEmpty(regCount)?"0":regCount) + "鲸币");
                            if (!TextUtils.isEmpty(mRegistInfo) && mRegistInfo.equals("注册大礼包")) {
                                intData(regCount);
                            }
                            break;
                        case 300:
                            showToast(json.getString("msg"));
                            break;
                        case 500:
                            showToast(json.getString("msg"));
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

    @Override
    public void onClick(View view) {
        if (view == mBtn_activate) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }

            //   notifyListener(YJNotifyTag.MAIN_MY, 3);
            if (!TextUtils.isEmpty(mRegistInfo)) {
                startActivity(createIntent(YJMainActivity.class, createTransmitData(YJMainActivity.MAIN_POSITION, "3")));
                final Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    public void run() {
                        if(mRegistInfo.equals("注册大礼包")){
                            mHandler.sendEmptyMessage(0);
                        }else if (mRegistInfo.equals("2")){//2，任务中心
                            mHandler.sendEmptyMessage(0);
                        }else if (mRegistInfo.equals("1")){//1，我的卡包
                            mHandler.sendEmptyMessage(1);
                        }
                        timer.cancel();
                    }
                }, 300);
            } else {
                startActivity(createIntent(YJMainActivity.class, createTransmitData(YJMainActivity.MAIN_POSITION, "3")));
                finishAll();
            }

        }
    }

    @Override
    protected boolean onBackKeyClick() {
        startActivity(YJMainActivity.class);
        finishAll();
        return true;

    }
}
