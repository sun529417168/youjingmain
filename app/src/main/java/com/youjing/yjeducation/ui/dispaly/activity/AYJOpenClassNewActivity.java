package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.model.YJCourseCatalogModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJOpenClassModel;
import com.youjing.yjeducation.talkfun.YJPlaybackModeTwo;
import com.youjing.yjeducation.talkfun.customtalkfun.YJCustomLiveActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJLoginDialog;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DialogUtil;
import com.youjing.yjeducation.util.ScanBannerUtils;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.wiget.CustomImage;

import org.apache.http.Header;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.utils.notification.IVNotificationListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:59
 */
public  class AYJOpenClassNewActivity extends Fragment implements View.OnClickListener {

    private String TAG = "AYJOpenClassNewActivity";
    private List<YJOpenClassModel> yjOpenClassModelList;
    private ListView lv_openclass;
    private static IVActivity ivActivity;
    private MyAdapter adapter = null;
    private LinearLayout ll_no_data;
    private TextView txt_select_course;
    protected boolean mIsLogin = false;
    protected Dialog progDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.activity_open_class_new, container, false);
        lv_openclass = (ListView)layout.findViewById(R.id.lv_openclass);
        ll_no_data = (LinearLayout)layout.findViewById(R.id.ll_no_data);
        txt_select_course = (TextView)layout.findViewById(R.id.txt_select_course);
        txt_select_course.setOnClickListener(this);

        if (yjOpenClassModelList != null && yjOpenClassModelList.size() > 0){
            lv_openclass.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.GONE);
        }else {
            ll_no_data.setVisibility(View.VISIBLE);
            lv_openclass.setVisibility(View.GONE);
        }
        if (adapter == null) {
            adapter = new MyAdapter();
            lv_openclass.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        StringUtils.Log(TAG, "onCreateView");

        lv_openclass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                if (mIsLogin) {
                    if (yjOpenClassModelList != null) {
                        if (yjOpenClassModelList.get(i).getLiveStatus().equals("ing")) {
                            getTakenLive(i, false, 1);
                        }
                        if (yjOpenClassModelList.get(i).getLiveStatus().equals("over")) {
                            if ("Yes".equals(yjOpenClassModelList.get(i).getIsReplay())) {
                                getTakenBack(i);
                            }
                            if ("No".equals(yjOpenClassModelList.get(i).getIsReplay())) {
                                getTakenLive(i, true, 2);//true 代表是否弹出预约页面， 1代表弹直播未开始，2代表弹出直播已结束
                            }
                        }
                        if (yjOpenClassModelList.get(i).getLiveStatus().equals("no")) {
                            getTakenLive(i, true, 1);
                        }
                    }
                } else {
                    ivActivity.startActivity(YJLoginDialog.class);
                }
            }
        });
        return layout;
    }

    public static AYJOpenClassNewActivity create(List<YJOpenClassModel> yjCourseLiveList,IVActivity activity) {
        AYJOpenClassNewActivity ayjOpenClassNewActivity = new AYJOpenClassNewActivity();
        Bundle bundle = new Bundle();
        ivActivity = activity;
        bundle.putSerializable("openlist", (Serializable) yjCourseLiveList);
        ayjOpenClassNewActivity.setArguments(bundle);
        return ayjOpenClassNewActivity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getSerializable("openlist")!= null) {
            yjOpenClassModelList  = ( List<YJOpenClassModel>)getArguments().getSerializable("openlist");
        }
        if (ivActivity != null){
            mIsLogin = PreferenceManager.getDefaultSharedPreferences(ivActivity.getContext()).getBoolean("isLogin", false);
            getNotifyListener();
        }

    }

    private void getNotifyListener() {
        ivActivity.addListener(YJNotifyTag.LIVE_CHANNEL_OPEN_CLASS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                yjOpenClassModelList = (List<YJOpenClassModel>) o;

                if (yjOpenClassModelList == null) {
                    StringUtils.Log(TAG,"yjOpenClassModelList == null");
                    ll_no_data.setVisibility(View.VISIBLE);
                    lv_openclass.setVisibility(View.GONE);
                } else {
                    if (yjOpenClassModelList .size() == 0){
                        ll_no_data.setVisibility(View.VISIBLE);
                        lv_openclass.setVisibility(View.GONE);
                    }else {
                        StringUtils.Log(TAG,"yjOpenClassModelList != null");
                        lv_openclass.setVisibility(View.VISIBLE);
                        ll_no_data.setVisibility(View.GONE);
                        if (adapter == null) {
                            adapter = new MyAdapter();
                            lv_openclass.setAdapter(adapter);
                        } else {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    public void run() {
                                        lv_openclass.setAdapter(adapter);
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    }

                }
            }

        });

        ivActivity.addListener(YJNotifyTag.USER_LOGIN, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                mIsLogin = PreferenceManager.getDefaultSharedPreferences(ivActivity.getContext()).getBoolean("isLogin", false);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view == txt_select_course){
            ivActivity.notifyListener(YJNotifyTag.LIVE_CHANNEL_SHOW_DAILOG,"");
        }
    }
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (yjOpenClassModelList != null && yjOpenClassModelList.size() >0){
                return yjOpenClassModelList.size();
            }else {
                return 0;
            }
        }
        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_live_channel_openclass_item, null);
                holder.img_teacher_photo =(CustomImage) convertView.findViewById(R.id.img_teacher_photo);
                holder.txt_course_name =(TextView) convertView.findViewById(R.id.txt_course_name);
                holder.txt_time =(TextView) convertView.findViewById(R.id.txt_time);
                holder.txt_course_status =(TextView) convertView.findViewById(R.id.txt_course_status);
                holder.txt_user_num =(TextView) convertView.findViewById(R.id.txt_user_num);
                holder.img_course_living =(ImageView) convertView.findViewById(R.id.img_course_living);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (yjOpenClassModelList != null){
               YJOpenClassModel yjOpenClassModel =  yjOpenClassModelList.get(position);
                if (!TextUtils.isEmpty(yjOpenClassModel.getTeacher().getTeacherPic())) {
                    BitmapUtils.create(getContext()).display(holder.img_teacher_photo, yjOpenClassModel.getTeacher().getTeacherPic());
                }
                if (!TextUtils.isEmpty(yjOpenClassModel.getCourseName())) {
                    holder.txt_course_name.setText(yjOpenClassModel.getName());
                } else {
                    holder.txt_course_name.setText("课程名称");
                }

                if( yjOpenClassModel.getLiveStatus().equals("ing")) {
                    holder.txt_course_status.setVisibility(View.VISIBLE);
                    holder.img_course_living.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(yjOpenClassModel.getLookStudent())) {
                        holder.txt_user_num.setText("观看人数： "+yjOpenClassModel.getLookStudent());
                    } else {
                        holder.txt_user_num.setText("观看人数： 0");
                    }
                } else if(yjOpenClassModel.getLiveStatus().equals("over")){
                    holder.txt_course_status.setVisibility(View.GONE);
                    holder.img_course_living.setVisibility(View.GONE);
                    if (!TextUtils.isEmpty(yjOpenClassModel.getLookStudent())) {
                        holder.txt_user_num.setText("观看人数： "+yjOpenClassModel.getLookStudent());
                    } else {
                        holder.txt_user_num.setText("观看人数： 0");
                    }
                }else if(yjOpenClassModel.getLiveStatus().equals("no")){
                    if (!TextUtils.isEmpty(yjOpenClassModel.getReservationCount())) {
                        holder.txt_user_num.setText("预约人数： "+yjOpenClassModel.getReservationCount());
                    } else {
                        holder.txt_user_num.setText("预约人数： 0");
                    }
                    holder.txt_course_status.setVisibility(View.GONE);
                    holder.img_course_living.setVisibility(View.GONE);
                }
                if(!TextUtils.isEmpty(yjOpenClassModel.getPlanDate()) && !TextUtils.isEmpty(yjOpenClassModel.getPlanEndDate())){
                    String planDate = TimeUtil.getYearTime(Long.parseLong(yjOpenClassModel.getPlanDate()));
                    String planEndDate = TextUtils.isEmpty(yjOpenClassModel.getPlanEndDate()) ? "" : TimeUtil.getSecondMInTime(Long.parseLong(yjOpenClassModel.getPlanEndDate()));
                    holder.txt_time.setText((("3".equals(StringUtils.getDateDetail(planDate)) ? TimeUtil.getMonthTime(Long.parseLong(yjOpenClassModel.getPlanDate())) : StringUtils.getDateDetail(planDate)) + " " + TimeUtil.getSecondMInTime(Long.parseLong(yjOpenClassModel.getPlanDate()))) + "-" + planEndDate);
                }else {
                    holder.txt_time.setText("暂无开课日期");
                }
            }
            return convertView;
        }
    }
    private class ViewHolder {
        private TextView txt_course_name;
        private TextView txt_time;
        private TextView txt_course_status;
        private TextView txt_user_num;
        private CustomImage img_teacher_photo;
        private ImageView img_course_living;
    }
    public void getTakenLive(final int position,final boolean isShowDialog,final int isLiveType) {
        ScanBannerUtils.getLiveInfo(ivActivity, yjOpenClassModelList.get(position).getOpenClassId(), "openClass", "", isShowDialog, isLiveType);
    }

    public void getTakenBack(int position) {
        ScanBannerUtils.getTakenBack(ivActivity, yjOpenClassModelList.get(position).getCourseVideoId(), yjOpenClassModelList.get(position).getTeacher().getTeacherId());
    }
}