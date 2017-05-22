package com.youjing.yjeducation.ui.dispaly.dialog;

import android.content.Context;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJMyInviteModel;
import com.youjing.yjeducation.talkfun.customtalkfun.CustomBasiHtActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJInviteFriendActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.ClickUtil;

import org.apache.http.Header;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.HashMap;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
@VLayoutTag(R.layout.dialog_invite_code)
public  class AYJInviteCodeDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialog_cancel)
    private Button dialog_cancel;
    @VViewTag(R.id.dialog_ok)
    private Button dialog_ok;
    @VViewTag(R.id.input_edt)
    private EditText input_edt;
    @VViewTag(R.id.txt_code_wrong)
    private TextView txt_code_wrong;
    private String TAG = "AYJInviteCodeDialog";
    @Override
    protected void onLoadedView() {
        txt_code_wrong.setVisibility(View.GONE);
        inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

    }

    @Override
    public void onClick(View view) {
        if(view == dialog_cancel) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            close();
        } else if (view == dialog_ok) {
            onBtnOKClick();
        }
    }
    private void onBtnOKClick(){
        if (TextUtils.isEmpty(input_edt.getText())){
            showToast("请输入邀请码");
            return;
        }
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("invitedCode", input_edt.getText());

        } catch (Exception e) {
            e.printStackTrace();
        }
            YJStudentReqManager.getServerData(YJReqURLProtocol.ACTIVE_INVITE_CODE, objectMap, true, new TextHttpResponseHandler() {
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
                                txt_code_wrong.setVisibility(View.GONE);
                                JSONObject jsonData = new JSONObject(json.getString("data"));
                                 String rewardCoin = jsonData.getString("rewardCoin");
                                 String customerCoin = jsonData.getString("customerCoin");
                                YJGlobal.getYjUser().setCoin(customerCoin);

                                if (!TextUtils.isEmpty(rewardCoin) && !rewardCoin.equals("0")){
                                    showToast("您获得"+rewardCoin+"鲸币");
                                }
                                notifyListener(YJNotifyTag.INVITE_SUCESS,"");
                                close();
                            case 300:
                                txt_code_wrong.setVisibility(View.VISIBLE);
                                break;
                            case 400:
                                txt_code_wrong.setVisibility(View.VISIBLE);
                                break;
                            case 401:
                                txt_code_wrong.setVisibility(View.VISIBLE);
                                break;
                            case 402:
                                txt_code_wrong.setVisibility(View.VISIBLE);
                                break;
                            case 403:
                                txt_code_wrong.setVisibility(View.VISIBLE);
                                break;
                            case 500:
                                txt_code_wrong.setVisibility(View.VISIBLE);
                                break;
                            case 600:
                                startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                                finishAll();
                                break;
                            default:
                                break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
    }

    private InputMethodManager inputMethodManager;
    private boolean hideKeyboard(){
        if(inputMethodManager.isActive(input_edt)){
            //因为是在fragment下，所以用了getView()获取view，也可以用findViewById（）来获取父控件
            getView().requestFocus();//强制获取焦点，不然getActivity().getCurrentFocus().getWindowToken()会报错
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            inputMethodManager.restartInput(input_edt);
            return true;
        }
        return false;
    }
}
