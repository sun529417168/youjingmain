package com.youjing.yjeducation.ui.actualize.dialog;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.model.YJIndexUserInfo;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJLoginDialog;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.MakeSign;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 13:49
 */
public class YJLoginDialog extends AYJLoginDialog {
    private String TAG = "YJLoginDialog";

    @Override
    protected void login( final String phone,final String password) {

        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("mobile", phone);
            objectMap.put("password", DES.encryptDES(password));
        } catch (Exception e) {
            e.printStackTrace();
        }
      /*  RequestParams params = new RequestParams();
        for (String key : objectMap.keySet()) {
            params.put(key, objectMap.get(key));
        }

        params.put("sign", MakeSign.makeSign(objectMap));*/
        YJStudentReqManager.getServerData(YJReqURLProtocol.LOGIN, objectMap, false, new TextHttpResponseHandler() {
             @Override
             public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                 StringUtils.Log(TAG, "失败=" + s);
                 showToast(getString(R.string.no_net_work));
             }

             @Override
             public void onSuccess(int i, Header[] headers, String s) {
                 try {
                     JSONObject json = null;
                     json = new JSONObject(s);
                     StringUtils.Log(TAG, "成功s=" + s);
                     switch (json.getInt("code")) {
                         case 200:

                             JSONObject jsonData = new JSONObject(json.getString("data"));
                             String customerToken = jsonData.getString("customerToken");
                             YJGlobal.setCustomertoken(customerToken);
                             int customerId = jsonData.getInt("customerId");
                             YJGlobal.setCustomerId(customerId);

                             rememberToken(customerToken, customerId + "");
                             YJUserStudyData.setDevice(getContext());
                             getUserIndexInfo();
                             break;
                         case 300:
                             showToast("参数不合法");
                             break;
                         case 400:
                             showToast("用户不存在");
                             break;
                         case 401:
                             // showToast("用户被锁定");
                             break;
                         case 402:
                             showToast("密码不正确");
                             break;
                         case 500:
                             //showToast("服务器异常");
                             break;
                         case 600:
                             //showToast("服务器异常");
                             showToast("用户已登录");
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
    private void  getUserIndexInfo(){
   /*     Map<String, Object> objectMap = new HashMap<>();
        RequestParams params = new RequestParams();
        params.put("sign", MakeSign.makeSignLogined(objectMap));*/
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_USER_INDEX, null, true, new TextHttpResponseHandler() {
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
                     JSONObject jsonData = new JSONObject(json.getString("data"));
                     YJIndexUserInfo yjIndexUserInfo = JSON.parseObject(jsonData.getString("customer"), YJIndexUserInfo.class);
                     StringUtils.Log(TAG, "yjIndexUserInfo.toString()=" + yjIndexUserInfo.toString());
                     YJGlobal.setYjIndexUserInfo(yjIndexUserInfo);
                     getGrade();

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         });
    }
    private void  getUserInfo(){

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
                   JSONObject jsonData = new JSONObject(json.getString("data"));
                   YJUser yjUser = JSON.parseObject(jsonData.getString("customer"), YJUser.class);
                   StringUtils.Log(TAG, "yjUser.toString()=" + yjUser.toString());
                   YJGlobal.setYjUser(yjUser);
                   notifyListener(YJNotifyTag.USER_LOGIN, true);
                   YJGlobal.setSucessInfo("登录成功");
                   finish();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
           }
       });
    }
    private void getGrade(){
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_GRADE, null, true, new TextHttpResponseHandler() {
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
                     JSONObject jsonData = new JSONObject(json.getString("data"));
                     List<YJGradeModel> yjGradeModelList = JSON.parseArray(jsonData.getString("grades"), YJGradeModel.class);
                  /*  String customerToken = jsonData.getString("customerToken");
                    YJGlobal.setCustomertoken(customerToken);
                    int customerId = jsonData.getInt("customerId");
                    YJGlobal.setCustomerId(customerId);*/
                     StringUtils.Log(TAG, "yjGradeListModel.toString()=" + yjGradeModelList.toString());
                     YJGlobal.setGradeList(yjGradeModelList);
                     getUserInfo();
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         });
    }

    private void rememberToken(String customerToken, String customerId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin", true);
        editor.putString("customerToken", customerToken);
        editor.putString("customerId", customerId);

        editor.commit();
    }
}
