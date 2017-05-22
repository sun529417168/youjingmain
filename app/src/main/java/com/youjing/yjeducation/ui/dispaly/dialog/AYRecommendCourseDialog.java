package com.youjing.yjeducation.ui.dispaly.dialog;

import android.content.Intent;
import android.nfc.Tag;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.ui.actualize.activity.YJBuyCourseActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJRechargeWhaleMoney;
import com.youjing.yjeducation.ui.actualize.dialog.YJRecommendCourseDialog;
import com.youjing.yjeducation.util.YjGetUserInfo;

import org.apache.http.Header;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.base.VParams;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/6/16
 * Time 11:12
 */
@VLayoutTag(R.layout.dialog_live_recommend_course)
public class AYRecommendCourseDialog extends AVDialog implements IVClickDelegate, IVAdapterDelegate,YjGetUserInfo.UserCoin {

    @VViewTag(R.id.img_delete)
    private RelativeLayout img_delete;
    @VViewTag(R.id.lv_live_recommend)
    private ListView lv_live_recommend;
    private VAdapter mVAdapter;

    private String TAG = "AYRecommendCourseDialog";
    private List<YJCourseModel> yjCourseModelList;
    public static final VParamKey<List<YJCourseModel>> RECOMMEND_COURSE_MODEL_LIST = new VParamKey<List<YJCourseModel>>(null);
    public static final VParamKey<String> COURSE_VIDEOID = new VParamKey<>(null);

    private String courseVideoId;
    private YjGetUserInfo yjGetUserInfo;
    protected String coin;
    private  YJCourseModel yjCourseModel;

    @Override
    protected void onLoadedView() {
        yjGetUserInfo = new YjGetUserInfo(this,this);
        yjCourseModelList = getTransmitData(RECOMMEND_COURSE_MODEL_LIST);
        if (yjCourseModelList != null) {
            StringUtils.Log(TAG, "yjCourseModelList=" + yjCourseModelList.toString());
        }
        courseVideoId = getTransmitData(COURSE_VIDEOID);
        if (mVAdapter == null) {
            mVAdapter = new VAdapter(this, lv_live_recommend);
        } else {
            mVAdapter.notifyDataSetChanged();
        }
        lv_live_recommend.setAdapter(mVAdapter);

        bindNotifyListener();
    }

    @Override
    public void onClick(View view) {
        if (view == img_delete) {
            this.close();
        }
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJRecommendCourseItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjCourseModelList != null) {
            return yjCourseModelList.size();
        } else {
            return 0;
        }

    }

    @Override
    public void getCoin(IVActivity view, YJUser mYjUser) {
        coin = mYjUser.getCoin();
        if (Integer.parseInt(coin) >= yjCourseModel.getPayCoin()) {
            Intent intent = new Intent(getContext(),YJBuyCourseActivity.class);
            intent.putExtra("courseId", yjCourseModel.getCourseId());
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(),YJRechargeWhaleMoney.class);
            intent.putExtra("courseId",  yjCourseModel.getCourseId());
            intent.putExtra("visileFlag", true);
            startActivity(intent);
        }
    }

    @VLayoutTag(R.layout.dialog_recommend_course_item)
    class YJRecommendCourseItem extends AVAdapterItem {
        @VViewTag(R.id.txt_name)
        private TextView txt_name;
        @VViewTag(R.id.txt_teacher_name)
        private TextView txt_teacher_name;
        @VViewTag(R.id.txt_rmb_num)
        private TextView txt_rmb_num;
        @VViewTag(R.id.txt_money_num)
        private TextView txt_money_num;
        @VViewTag(R.id.btn_buy_course)
        private Button btn_buy_course;
        @VViewTag(R.id.img_rmb)
        private ImageView img_rmb;
        @VViewTag(R.id.img_rmb_right)
        private ImageView img_rmb_right;
        @VViewTag(R.id.view_line_g)
        private View view_line_g;


        @Override
        public void update(final int position) {
            super.update(position);
            if (yjCourseModelList != null) {
                 yjCourseModel = yjCourseModelList.get(position);
                if (!TextUtils.isEmpty(yjCourseModel.getName())) {
                    txt_name.setText(yjCourseModel.getName());
                }
                if (!TextUtils.isEmpty(yjCourseModel.getTeacher().getTrueName())) {
                    txt_teacher_name.setText("讲师：　" + yjCourseModel.getTeacher().getTrueName());
                }
                if (!TextUtils.isEmpty(yjCourseModel.getCoursePayWay()) && yjCourseModel.getCoursePayWay().equals("XNB")) {
                    img_rmb_right.setVisibility(View.VISIBLE);
                    img_rmb_right.setImageResource(R.mipmap.img_whale_gray);

                    img_rmb.setImageResource(R.mipmap.whale_money);
                    txt_rmb_num.setText(yjCourseModel.getPayCoin() + "");
                    txt_money_num.setText(yjCourseModel.getOriginalCoin() + "");
                    view_line_g.setVisibility(View.VISIBLE);
                } else if (!TextUtils.isEmpty(yjCourseModel.getCoursePayWay()) && yjCourseModel.getCoursePayWay().equals("RMB")) {
                    img_rmb_right.setVisibility(View.GONE);
                    img_rmb.setImageResource(R.mipmap.img_rmb);
                    txt_rmb_num.setText(yjCourseModel.getPayMoney() + "");
                    txt_money_num.setText("￥" + yjCourseModel.getOriginalMoney());
                    view_line_g.setVisibility(View.VISIBLE);
                }else if(!TextUtils.isEmpty(yjCourseModel.getCoursePayWay()) && yjCourseModel.getCoursePayWay().equals("FREE")){
                    img_rmb_right.setVisibility(View.GONE);
                    img_rmb.setVisibility(View.GONE);
                    txt_money_num.setVisibility(View.GONE);
                    view_line_g.setVisibility(View.GONE);
                    txt_rmb_num.setText("免费");
                }

                if (!TextUtils.isEmpty(yjCourseModel.getIsBuy()) && yjCourseModel.getIsBuy().equals("Yes")) {
                    btn_buy_course.setBackgroundResource(R.drawable.shape_gray_round);
                    btn_buy_course.setText("已购买");
                    btn_buy_course.setClickable(false);

                } else if (!TextUtils.isEmpty(yjCourseModel.getIsBuy()) && yjCourseModel.getIsBuy().equals("No")) {
                    btn_buy_course.setBackgroundResource(R.drawable.shape_yellow_round);
                    btn_buy_course.setClickable(true);
                    btn_buy_course.setText("立即购买");
                    if (yjCourseModel.getCoursePayWay().equals("FREE")){
                        btn_buy_course.setText("立即进入");
                    }
                    btn_buy_course.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            yjCourseModel = yjCourseModelList.get(position);
                            StringUtils.Log(TAG,"yjCourseModel.getCoursePayWay="+yjCourseModel.getCoursePayWay());
                            StringUtils.Log(TAG,"getCourseId="+yjCourseModel.getCourseId());
                            if (yjCourseModel.getCoursePayWay().equals("XNB")) {
                                yjGetUserInfo.getUserCoinInfo(AYRecommendCourseDialog.this);
                            } else if (yjCourseModel.getCoursePayWay().equals("RMB")) {
                                Intent intent = new Intent(getContext(),YJBuyCourseActivity.class);
                                intent.putExtra("courseId", yjCourseModel.getCourseId());
                                startActivity(intent);
                            }else {
                                Intent in = new Intent(getContext(), YJCoursePlayNewActivity.class);
                                in.putExtra("courseId", yjCourseModel.getCourseId());
                                startActivity(in);
                            }
                            buyCourse(yjCourseModel);
                        }
                    });
                }
            }
        }
    }

    //购买课程
    private void buyCourse(YJCourseModel yjCourseModel) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseId", yjCourseModel.getCourseId());
            objectMap.put("courseVideoId", courseVideoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.LIVE_BUY_COURSE, objectMap, true, new TextHttpResponseHandler() {
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
                            JSONObject jsonData = new JSONObject(json.getString("data"));

                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
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

    //推荐课程列表
    private void recommendCourseList() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseVideoId", courseVideoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_RECOMMEND, objectMap, true, new TextHttpResponseHandler() {
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
                            JSONObject jsonData = new JSONObject(json.getString("data"));

                            yjCourseModelList = JSON.parseArray(jsonData.getString("courseList"), YJCourseModel.class);

                            mVAdapter.notifyDataSetChanged();
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
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

    private void bindNotifyListener() {

        addListener(YJNotifyTag.PAY_SUCESS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                recommendCourseList();
            }
        });

    }
}
