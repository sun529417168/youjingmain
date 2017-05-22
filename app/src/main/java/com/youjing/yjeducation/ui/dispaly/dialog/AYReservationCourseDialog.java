package com.youjing.yjeducation.ui.dispaly.dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
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
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.talkfun.customtalkfun.YJCustomLiveActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJLiveShareDialog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * user  秦伟宁
 * Date 2016/6/16
 * Time 11:12
 */
@VLayoutTag(R.layout.dialog_live_reservation_course)
public class AYReservationCourseDialog extends AVDialog implements IVClickDelegate, IVAdapterDelegate {

    private String  TAG = "AYReservationCourseDialog";

    @VViewTag(R.id.lv_course_info_list)
    private ListView lv_course_info_list;
    @VViewTag(R.id.btn_enter)
    private Button btn_enter;
    @VViewTag(R.id.btn_reservation)
    private Button btn_reservation;
    @VViewTag(R.id.iv_go_back)
    private ImageView iv_go_back;
    @VViewTag(R.id.img_qq)
    private ImageView img_qq;
    @VViewTag(R.id.tv_zhubo)
    private TextView tv_zhubo;
    @VViewTag(R.id.tv_memberTotal)
    private TextView tv_memberTotal;
    @VViewTag(R.id.txt_time_hour)
    private TextView txt_time_day;
    @VViewTag(R.id.txt_time_minute)
    private TextView txt_time_hour;
    @VViewTag(R.id.txt_time_second)
    private TextView txt_time_minute;
    @VViewTag(R.id.txt_qq_num)
    private TextView txt_qq_num;
    @VViewTag(R.id.txt_qq_info)
    private TextView txt_qq_info;
    @VViewTag(R.id.txt_title_hour)
    private TextView txt_title_hour;
    @VViewTag(R.id.txt_reservation_title)
    private TextView txt_reservation_title;
    @VViewTag(R.id.tv_live_title)
    private TextView tv_live_title;
    @VViewTag(R.id.img_share)
    private ImageView img_share;


    private VAdapter mVAdapter;
    private boolean isLiveOver = false;
    private String mTeacherName, mUseNum, courseVideoId,courseId,isReservation,android_key,qqGroup,courseCatalogId,courseName;
    private long  difTime;
    public static final VParamKey<String> TEACHER_NAME = new VParamKey<>(null);
    public static final VParamKey<String> USER_NUM = new VParamKey<>(null);
    public static final VParamKey<String> COURSEVIDEOID = new VParamKey<>(null);
    public static final VParamKey<String> COURSEID = new VParamKey<>(null);
    public static final VParamKey<String> ISRESERVATION = new VParamKey<>(null);
    public static final VParamKey<Long> DIF_TIME = new VParamKey<>(null);
    private List<YJCourseModel>  yjCourseModelList;
    public static final VParamKey<List<YJCourseModel>> RESERVATION_COURSE_MODEL_LIST = new VParamKey<List<YJCourseModel>>(null);
    public static final VParamKey<String> ANDROID_KEY = new VParamKey<>(null);
    public static final VParamKey<String> QQ_GROUP = new VParamKey<>(null);
    public static final VParamKey<String> COURSECATALOGID = new VParamKey<>(null);
    public static final VParamKey<String> COURSE_TITLE= new VParamKey<>(null);
    public static final VParamKey<Boolean> IS_LIVE_OVEW = new VParamKey<>(false);
    private IUiListener qqShareListener;

    static int day = 3;
    static int hour = 20;
    static int minute = 40;
    private boolean isRun = true;
    Timer timer;
    TimerTask  timerTask;
    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                computeTime();
                txt_time_day.setText(day+"");
                txt_time_hour.setText(hour+"");
                txt_time_minute.setText(minute+"");
                if (day==0&&hour==0&&minute==0) {
                    timer.cancel();
                    timer = null;
                    if (getParentActivity() != null){
                        if (!isLiveOver){
                            close();
                        }
                    }
                }
            }
        }
    };

    @Override
    protected void onLoadedView() {

        mTeacherName = getTransmitData(TEACHER_NAME);
        mUseNum = getTransmitData(USER_NUM);
        courseVideoId = getTransmitData(COURSEVIDEOID);
        courseId = getTransmitData(COURSEID);
        isReservation = getTransmitData(ISRESERVATION);
        yjCourseModelList = getTransmitData(RESERVATION_COURSE_MODEL_LIST);
        difTime = getTransmitData(DIF_TIME);
        qqGroup = getTransmitData(QQ_GROUP);
        android_key = getTransmitData(ANDROID_KEY);
        courseCatalogId = getTransmitData(COURSECATALOGID);
        isLiveOver = getTransmitData(IS_LIVE_OVEW);
        courseName = getTransmitData(COURSE_TITLE);

        if (isLiveOver){
            txt_title_hour.setVisibility(View.GONE);
            txt_time_day.setVisibility(View.GONE);
            btn_reservation.setVisibility(View.GONE);
            txt_reservation_title.setText("老师已经下课了，距离直播回放：");
        }else {
            txt_title_hour.setVisibility(View.VISIBLE);
            txt_time_day.setVisibility(View.VISIBLE);
            btn_reservation.setVisibility(View.VISIBLE);
            txt_reservation_title.setText("距离直播课开始：");
        }

       // difTime = 5000;
        if (difTime > 0 ){
            day   = (int)((difTime / 1000) / (3600 * 24));
            hour = (int)((difTime / 1000) - day * 86400) / 3600;

            minute = (int)((difTime / 1000) - day * 86400 - hour * 3600) / 60;
        }else {
            hour = 0;
            minute = 0;
        }
        if (!TextUtils.isEmpty(mTeacherName)) {
            tv_zhubo.setText(mTeacherName);
        }
        if (!TextUtils.isEmpty(mUseNum)) {
            tv_memberTotal.setText(mUseNum);
        }

        if (!TextUtils.isEmpty(courseName)){
            tv_live_title.setText(courseName);
        }
        if (!TextUtils.isEmpty(isReservation) && isReservation.equals("Yes")){
            btn_reservation.setBackgroundResource(R.drawable.shape_gray_round);
            btn_reservation.setText("已预约");
            btn_reservation.setClickable(false);
            img_share.setVisibility(View.VISIBLE);
            StringUtils.Log(TAG, "isReservation1=" + isReservation);
        }else if (!TextUtils.isEmpty(isReservation) && isReservation.equals("No")){
            btn_reservation.setBackgroundResource(R.drawable.shape_blue_round);
            btn_reservation.setClickable(true);
            btn_reservation.setText("预约");
            img_share.setVisibility(View.INVISIBLE);
            StringUtils.Log(TAG, "isReservation2=" + isReservation);
        }
        if (!TextUtils.isEmpty(qqGroup) &&!TextUtils.isEmpty(android_key)){
            txt_qq_num.setText(qqGroup);
            txt_qq_num.setVisibility(View.VISIBLE);
            btn_enter.setVisibility(View.VISIBLE);
            txt_qq_info.setVisibility(View.VISIBLE);
            img_qq.setVisibility(View.VISIBLE);
        }else {
            txt_qq_num.setVisibility(View.GONE);
            btn_enter.setVisibility(View.GONE);
            txt_qq_info.setVisibility(View.GONE);
            img_qq.setVisibility(View.GONE);
        }



        if (mVAdapter == null) {
            mVAdapter = new VAdapter(this, lv_course_info_list);
        } else {
            mVAdapter.notifyDataSetChanged();
        }
        lv_course_info_list.setAdapter(mVAdapter);


        timerTask = new TimerTask() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 1;
                timeHandler.sendMessage(msg);
            }
        };
        timer = new Timer();
        timer.schedule(timerTask,0,1000*60);

        bindNotifyListener();


        qqShareListener = new IUiListener() {
            @Override
            public void onCancel() {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast("分享取消");
            }

            @Override
            public void onComplete(Object response) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "sucess");
                //showToast(getString(R.string.share_success));
            }

            @Override
            public void onError(UiError e) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast(getString(R.string.share_fail));
            }
        };
    }

    @Override
    public void onClick(View view) {
        if (view == btn_enter) {

            //加群功能
            if (!TextUtils.isEmpty(android_key)){
                String key = android_key;
                Intent intent = new Intent();
                intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
                // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                try {
                    startActivity(intent);
                    return  ;
                } catch (Exception e) {
                    // 未安装手Q或安装的版本不支持
                    return ;
                }
            }

        } else if (view == iv_go_back) {
            finish();
            close();
        } else if (view == btn_reservation) {
            reservationCourse();
        } else if (view == img_share) {

            YJLiveShareDialog yjLiveShareDialog = new YJLiveShareDialog();
            showDialog(yjLiveShareDialog);
        }

    }
    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJReservationCourseItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjCourseModelList !=null){
            return yjCourseModelList.size();
        }else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.dialog_reservation_course_item)
    class YJReservationCourseItem extends AVAdapterItem {
        @VViewTag(R.id.txt_name)
        private TextView txt_name;
        @VViewTag(R.id.lay_item)
        private RelativeLayout lay_item;
        @Override
        public void update(final int position) {
            if (yjCourseModelList != null) {
               final YJCourseModel yjCourseModel =  yjCourseModelList.get(position);
                if (!TextUtils.isEmpty(yjCourseModel.getName())){
                    txt_name.setText(yjCourseModel.getName());
                }
                lay_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getCourseCatalog(yjCourseModel);
                    }
                });
            }
        }
    }

    @Override
    public void destroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        super.destroy();
    }

    /**
     * 倒计时计算
     */
    private void computeTime() {
        minute--;
            if (minute < 0) {
                minute = 59;
                hour--;
                if (hour < 0) {
                    // 倒计时结束
                    hour = 23;
                    hour--;
                }
            }
    }


    //直播用户预约
    private void reservationCourse() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.RESERVATION_LIVE_COURSE, objectMap, true, new TextHttpResponseHandler() {
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

                            showToast("预约成功");
                            img_share.setVisibility(View.VISIBLE);
                            btn_reservation.setBackgroundResource(R.drawable.shape_gray_round);
                            btn_reservation.setText("已预约");
                            btn_reservation.setClickable(false);
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

    private void getCourseCatalog(YJCourseModel yjCourseModel) {
        Intent in = new Intent(getContext(),YJCoursePlayNewActivity.class);
        in.putExtra("courseId",yjCourseModel.getCourseId());
        startActivity(in);
    }

    private void bindNotifyListener() {

        addListener(YJNotifyTag.COLSE_DIALOG_RESERVATION, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                if (getParentActivity() != null) {
                    YJCustomLiveActivity.isShowDialog = false;

                    if (!getParentActivity().isFinished()) {
                        if (!isFinished()) {
                            close();
                        }

                    }

                }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
    }

}