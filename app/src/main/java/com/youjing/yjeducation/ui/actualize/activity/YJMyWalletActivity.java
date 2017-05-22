package com.youjing.yjeducation.ui.actualize.activity;

import com.youjing.yjeducation.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJConsumeHistoryModel;
import com.youjing.yjeducation.model.YJCourseListModel;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCourseListActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJMyWalletActivity;
import com.youjing.yjeducation.util.MakeSign;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/31
 * Time 20:13
 */
public class YJMyWalletActivity extends AYJMyWalletActivity {

    @Override
    protected void getMyConsumHistory() {
        startActivity(createIntent(YJConsumeHistoryActivity.class));
    }
}
