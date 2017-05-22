package com.youjing.yjeducation.ui.actualize.dialog;

import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;

import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJShareDialog;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONObject;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.HashMap;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 14:16
 */
public class YJShareDialog extends AYJShareDialog {

    private String TAG = "YJShareDialog";
    @Override
    protected void getNotifyListener() {
        addListener(YJNotifyTag.SHARE_SUCESS_INFO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                String sucessInfo = (String)o;
                if (!TextUtils.isEmpty(sucessInfo) && sucessInfo.equals("sucess")){

                    CustomToast.makeTexts(getContext(),"分享成功",0).show();
                    shareApp(mIsLogin);
                    close();
                }else if (!TextUtils.isEmpty(sucessInfo) && sucessInfo.equals("fail")){
                    close();
                }
            }
        });
    }
    private void  shareApp(boolean isLogin){
        Map<String, Object> objectMap = new HashMap<>();
        try {
            if (mPosition == 1){
                objectMap.put("shareChannel", "Wechat");
            }else if(mPosition ==2){
                objectMap.put("shareChannel", "WechatFriends");
            }else if(mPosition == 3){
                objectMap.put("shareChannel", "QQ");
            }
            objectMap.put("themeTitle", getString(R.string.share_title));
            objectMap.put("shareUrl",getString(R.string.share_url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.SHARE_SUCESS, objectMap, isLogin, new TextHttpResponseHandler() {
                  @Override
                  public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                      showToast(getString(R.string.no_net_work));
                      StringUtils.Log(TAG, "失败=" + s);
                  }

                  @Override
                  public void onSuccess(int i, Header[] headers, String s) {
                      try {
                          JSONObject json = null;
                          json = new JSONObject(s);
                          StringUtils.Log(TAG, "成功s=" + s);
                          switch (json.getInt("code")) {
                              case 200:
                                  notifyListener(YJNotifyTag.MY_TASK_SUCESS, "");
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
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }
              });
    }
}
