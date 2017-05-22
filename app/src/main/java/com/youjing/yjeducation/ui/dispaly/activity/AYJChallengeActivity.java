package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Intent;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.talkfun.HttpRequest;
import com.youjing.yjeducation.talkfun.MainConsts;
import com.youjing.yjeducation.talkfun.NetMonitor;
import com.youjing.yjeducation.talkfun.YJLiveActivity;

import org.json.JSONObject;
import org.vwork.mobile.ui.AVVirtualActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:59
 */
@VLayoutTag(R.layout.activity_challenge)
public class AYJChallengeActivity extends AVVirtualActivity implements IVClickDelegate {

    private String TAG = "AYJChallengeActivity";
    @VViewTag(R.id.btn_live)
    private Button btn_live;
    @VViewTag(R.id.btn_live2)
    private Button btn_live2;

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, getString(R.string.txt_challenge), false);

        MainConsts.setIsConnected(NetMonitor.isNetworkAvailable(getContext()));
    }
    @Override
    public void onClick(View view) {
            if (view == btn_live){

               if (!MainConsts.isConnected()) {
                    Toast.makeText(getActivity(), getString(R.string.not_connect), Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = "1111";
                String key = "test-qifei";
                String url = MainConsts.getLiveLogInUrl(name, key);
                StringUtils.Log(TAG, "url:" + url);

                HttpRequest request = new HttpRequest();
                request.setRequestListener(new HttpRequest.IHttpRequestListener() {
                    @Override
                    public void onRequestCompleted(String responseStr) {
                        // TODO Auto-generated method stub
                        parseJson(responseStr);
                    }

                    @Override
                    public void onIOError(String errorStr) {
                        // TODO Auto-generated method stub

                        showToast("网络错误");
                    }
                });
                request.sendRequestWithGET(url);
            } else if(view == btn_live2){
                startActivity(LiveTestActivity.class);
            }
    }


    /**
     * 解析服务器返回结果
     *
     * @param strResult
     */
    private int mode =2;
    private void parseJson(String strResult) {
        try {
            JSONObject jsonObj = new JSONObject(strResult);
            if (jsonObj.getInt("code") == 0) {
                JSONObject dataObj = jsonObj.getJSONObject("data");
                String token = dataObj.getString("access_token");

                StringUtils.Log("token","token="+token);
                if (token != null) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), YJLiveActivity.class);
                    intent.putExtra("token", token);
                    intent.putExtra("isPlayback", false);
                    intent.putExtra("mode", mode);
                    startActivity(intent);
                }
            } else {
               showToast("请检查密匙是否正确");
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
    }

}
