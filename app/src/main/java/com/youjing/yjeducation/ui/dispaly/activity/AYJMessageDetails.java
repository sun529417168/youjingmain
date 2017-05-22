package com.youjing.yjeducation.ui.dispaly.activity;

import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.model.YJMessageModel;
import com.youjing.yjeducation.util.TimeUtil;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * Created by w7 on 2016/5/3.
 */
@VLayoutTag(R.layout.activity_system_message_details)
public class AYJMessageDetails extends YJBaseActivity implements IVClickDelegate {


    private String TAG = "AYJMessageDetails";

    private YJMessageModel mYjMessageModel;
    public static final VParamKey<YJMessageModel> YJ_MESSAGE_MODEL = new VParamKey<YJMessageModel>(null);

    @VViewTag(R.id.txt_details_title)
    private TextView mTxt_details_title;
    @VViewTag(R.id.txt_details_time)
    private TextView mTxt_details_time;
    @VViewTag(R.id.wv_details_content)
    private WebView mMv_details_content;
    @VViewTag(R.id.rl_back)
    private RelativeLayout rl_back;

    @Override
    protected void onLoadedView() {
        mYjMessageModel = getTransmitData(YJ_MESSAGE_MODEL);


        initWebView();

        String head = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1,user-scalable=no\">";
        String body = "<html><header>" + head + "<style>body{padding:0; margin:0;}img{max-width:100%; width:100%; height:auto; display:block; margin:0 auto;}</style>" + "</header>"
                + mYjMessageModel.getContent() + "</body></html>";
        mMv_details_content.loadData(body,
                "text/html; charset=UTF-8", null);

        mTxt_details_title.setText(mYjMessageModel.getTitle());
        String[] timeArray = TimeUtil.timeAgo(mYjMessageModel.getCreateDate()).split(",");
        if (Integer.parseInt(timeArray[0]) > 2) {
            mTxt_details_time.setText(TimeUtil.getMonthTime(mYjMessageModel.getCreateDate()));
        } else if (Integer.parseInt(timeArray[0]) <= 2 && Integer.parseInt(timeArray[0]) >= 1) {
            mTxt_details_time.setText(timeArray[0] + "天前");
        } else if (Integer.parseInt(timeArray[1]) <= 24 && Integer.parseInt(timeArray[1])!=0) {
            mTxt_details_time.setText(timeArray[1] + "小时前");
        } else if (Integer.parseInt(timeArray[2]) <= 60) {
            mTxt_details_time.setText(timeArray[1] + "分钟前");
        }else if (Integer.parseInt(timeArray[2]) == 0) {
            mTxt_details_time.setText("现在");
        }

        StringUtils.Log(TAG, "getContent=" + mYjMessageModel.getContent() + "\n\ngetTitle=" + mYjMessageModel.getTitle() + "\n\ngetCreateDate=" + mYjMessageModel.getCreateDate());
    }

    @Override
    public void onClick(View view) {
        if (view==rl_back){
            finish();
        }
    }

    private void initWebView() {
        mMv_details_content.getSettings().setJavaScriptEnabled(true);
        mMv_details_content.getSettings().setSupportZoom(true);
        mMv_details_content.getSettings().setBuiltInZoomControls(true);
        mMv_details_content.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mMv_details_content.requestFocus();
    }
}
