package com.youjing.yjeducation.ui.actualize.activity;

import com.youjing.yjeducation.util.StringUtils;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.ui.dispaly.activity.AYJForgetPasswordActivity;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.utils.base.VStringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/17
 * Time 19:54
 */
public class YJForgetPasswordActivity extends AYJForgetPasswordActivity {
    private String TAG = "YJForgetPasswordActivity";

    @Override
    protected void register(String phone, String mpassword,String mScurity) {

        if ((mpassword.getBytes().length != mpassword.length())) {
            showToast(getString(R.string.password_is_chinese));
            return;
        }
        if (VStringUtil.isNullOrEmpty(mpassword)) {
            showToast(getString(R.string.place_input_password));
            return;
        }
        if (mpassword.length() < 6) {
            showToast(getString(R.string.password_greater_than_six));
            return;
        }
        if (mpassword.length() > 16) {
            showToast(getString(R.string.password_greater_than_sixteen));
            return;
        }

        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("mobile", phone);
            objectMap.put("password", DES.encryptDES(mpassword));
            objectMap.put("code", mScurity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.MODIFYPASSWORD, objectMap, false, new TextHttpResponseHandler() {
           @Override
           public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
               StringUtils.Log(TAG, "失败=" + s);
               CustomToast.makeText(getContext(), "修改失败", Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onSuccess(int i, Header[] headers, String s) {
               try {
                   JSONObject json = null;
                   json = new JSONObject(s);
                   StringUtils.Log(TAG, "成功s=" + s);
                   switch (json.getInt("code")) {
                       case 200:
                           showToast("修改成功");
                           finish();

                           break;
                       case 300:

                           break;
                       case 301:
                           CustomToast.makeText(getContext(), "验证码不合法", Toast.LENGTH_SHORT).show();
                           break;
                       case 302:

                           break;
                       case 400:
                           CustomToast.makeText(getContext(), "用户不存在", Toast.LENGTH_SHORT).show();
                           break;
                       case 500:
                           CustomToast.makeText(getContext(), "服务器异常", Toast.LENGTH_SHORT).show();
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
