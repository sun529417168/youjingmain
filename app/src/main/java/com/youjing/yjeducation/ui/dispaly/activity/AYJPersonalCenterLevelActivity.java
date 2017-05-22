package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJLevelModel;
import com.youjing.yjeducation.model.YJMedalModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.MakeSign;
import com.youjing.yjeducation.wiget.CustomImage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author stt
 *         类说明：个人中心等级
 *         创建时间：2016.5.12
 */
@VLayoutTag(R.layout.activity_personal_center_level)
public class AYJPersonalCenterLevelActivity extends YJBaseActivity {
    private String TAG = "AYJPersonalCenterLevelActivity";
    @VViewTag(R.id.personal_center_level_logo_left_text)
    private TextView mPersonal_center_level_logo_left_text;//上一等级级别
    @VViewTag(R.id.personal_center_level_logo_right_text)
    private TextView mPersonal_center_level_logo_right_text;//下一等级级别
    @VViewTag(R.id.personal_center_level_progressbar_current)
    private ProgressBar mPersonal_center_level_progressbar_current;//升下一级别级进度条
    @VViewTag(R.id.personal_center_level_center_photo)
    private CustomImage mPersonal_center_level_center_photo;//中间logo图片
    @VViewTag(R.id.personal_center_level_currentLevel)
    private TextView mPersonal_center_level_currentLevel;//当前等级级别
    @VViewTag(R.id.personal_center_level_center_beforeExp)
    private TextView mPersonal_center_level_center_beforeExp;//上一等级所需经验值
    @VViewTag(R.id.personal_center_level_center_nextExp)
    private TextView mPersonal_center_level_center_nextExp;//下一等级所需经验值
    @VViewTag(R.id.personal_center_level_current_name)
    private TextView mPersonal_center_level_current_name;//当前称谓
    @VViewTag(R.id.personal_center_level_bottom_info)
    private TextView mPersonal_center_level_bottom_info;//当前经验值753，距离V5升级需247经验值
    private YJUser mYjUser;
    private String imagePath;
    private Bitmap LOAD_BITMAP;
    private Bitmap NO_LOAD_BITMAP;

    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        YJTitleLayoutController.initTitleBuleBg(this, "等级", true);
        mYjUser = YJGlobal.getYjUser();
        if (null != mYjUser) {
            imagePath = mYjUser.getPic();
            initPhoto(imagePath);
        }
        getMedalList();
    }

    public void getMedalList() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_LEVEL_INFO, null, true, new TextHttpResponseHandler() {
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
                                 YJLevelModel yjLevelModel = JSON.parseObject(jsonData.getString("levelInfo"), YJLevelModel.class);
//                            YJGlobal.setYjOpenClassModelList(mYJMedalModelList);
                                 StringUtils.Log(TAG, "AYJMedalActivity.toString()=" + yjLevelModel.toString());
                                 setDate(yjLevelModel);

//                            notifyListener(YJNotifyTag.OPEN_CLASS_LIST, yjOpenClassModelList);
//                            mMsgListView.stopRefresh();
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

    private void initPhoto(String head) {
        if (LOAD_BITMAP == null) {
            LOAD_BITMAP = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo);
        }
        if (NO_LOAD_BITMAP == null) {
            NO_LOAD_BITMAP = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo);
        }
        StringUtils.Log(TAG, "head = " + head);
        BitmapUtils.create(getContext()).display(mPersonal_center_level_center_photo, head, LOAD_BITMAP, NO_LOAD_BITMAP);
    }

    public void setDate(YJLevelModel date) {

        mPersonal_center_level_logo_left_text.setText(TextUtils.isEmpty(date.getBeforeLevel()) ? "" : date.getBeforeLevel());
        mPersonal_center_level_logo_right_text.setText(TextUtils.isEmpty(date.getNextLevel()) ? "" : date.getNextLevel());
        mPersonal_center_level_center_beforeExp.setText(TextUtils.isEmpty(date.getBeforeExp()) ? "0" : date.getBeforeExp());
        mPersonal_center_level_center_nextExp.setText(TextUtils.isEmpty(date.getNextExp()) ? "0" : date.getNextExp());
        mPersonal_center_level_currentLevel.setText(date.getCurrentLevel());
        mPersonal_center_level_current_name.setText(date.getCurrentLevelName());
        mPersonal_center_level_progressbar_current.setProgress(div(sub(date.getCurrentExp(), date.getCurrentLevelExp()), sub(date.getNextExp(), date.getCurrentLevelExp())));//进度条
        mPersonal_center_level_bottom_info.setText("当前经验值" + (date.getCurrentExp() == null ? "0" : date.getCurrentExp()) + "，距离V" + (date.getNextLevel() == null ? "0" : date.getNextLevel()) + "升级需" + (int) sub(date.getNextExp(), date.getCurrentExp()) + "经验值");
    }


    /**
     * 减法
     *
     * @param value1
     * @param value2
     * @return
     */
    public static double sub(String value1, String value2) {
        if (TextUtils.isEmpty(value1) && TextUtils.isEmpty(value2)) {
            return 0;
        }
        double v1 = Double.parseDouble(value1.trim());
        double v2 = Double.parseDouble(value2.trim());
        BigDecimal b1 = new BigDecimal(Double.valueOf(v1));
        BigDecimal b2 = new BigDecimal(Double.valueOf(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 除法精确到两位
     *
     * @param value1
     * @param value2
     * @return
     */
    public static int div(double value1, double value2) {

        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return (int) mul(b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @return 两个参数的积
     */
    public static double mul(double v1) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(100));
        return b1.multiply(b2).doubleValue();
    }
}
