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

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.model.PaginationInfo;
import com.youjing.yjeducation.model.YJCourseCatalogModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJOpenClassModel;
import com.youjing.yjeducation.talkfun.YJPlaybackModeTwo;
import com.youjing.yjeducation.talkfun.customtalkfun.YJCustomLiveActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJLoginDialog;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DialogUtil;
import com.youjing.yjeducation.util.ScanBannerUtils;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.wiget.CustomImage;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.adapter.VAdapter;
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
public  class AYJLiveListNewActivity extends Fragment implements View.OnClickListener {
    private String TAG = "AYJLiveListNewActivity";
    private ListView lv_liveclass;
    private MyAdapter adapter = null;
    private static IVActivity ivActivity;
    private List<YJOpenClassModel> yjCourseLiveList;
    private LinearLayout ll_no_data;
    private TextView txt_select_course;
    protected boolean mIsLogin = false;
    protected Dialog progDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.activity_liveclass_list_new, container, false);
        lv_liveclass = (ListView)layout.findViewById(R.id.lv_liveclass);
        ll_no_data = (LinearLayout)layout.findViewById(R.id.ll_no_data);
        txt_select_course = (TextView)layout.findViewById(R.id.txt_select_course);
        txt_select_course.setOnClickListener(this);
        if (yjCourseLiveList != null && yjCourseLiveList.size() >0 ){
            lv_liveclass.setVisibility(View.VISIBLE);
            ll_no_data.setVisibility(View.GONE);
        }else {
            ll_no_data.setVisibility(View.VISIBLE);
            lv_liveclass.setVisibility(View.GONE);
        }
        if (adapter == null) {
            adapter = new MyAdapter();
            lv_liveclass.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        StringUtils.Log(TAG, "onCreateView ");
        lv_liveclass.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                if (mIsLogin) {
                    if (yjCourseLiveList != null) {
                        StringUtils.Log(TAG, "yjCourseLiveList=" + yjCourseLiveList.toString());
                        if (!TextUtils.isEmpty(yjCourseLiveList.get(i).getIsBuy()) && yjCourseLiveList.get(i).getIsBuy().equals("Yes")) {
                            if (yjCourseLiveList.get(i).getLiveStatus().equals("ing")) {
                                getTakenLive(i, false, 1);
                            }
                            if (yjCourseLiveList.get(i).getLiveStatus().equals("over")) {
                                if ("Yes".equals(yjCourseLiveList.get(i).getIsReplay())) {
                                    getTakenBack(i);
                                }
                                if ("No".equals(yjCourseLiveList.get(i).getIsReplay())) {
                                    getTakenLive(i, true, 2);//true 代表是否弹出预约页面， 1代表弹直播未开始，2代表弹出直播已结束
                                }
                            }
                            if (yjCourseLiveList.get(i).getLiveStatus().equals("no")) {
                                getCourseCatalog(yjCourseLiveList.get(i).getCourseId());
                            }
                        } else {
                            getCourseCatalog(yjCourseLiveList.get(i).getCourseId());
                        }
                    }
                } else {
                    ivActivity.startActivity(YJLoginDialog.class);
                }
            }
        });

        return layout;
    }
    public static AYJLiveListNewActivity create(List<YJOpenClassModel> yjCourseLiveList,IVActivity activity) {
        AYJLiveListNewActivity ayjLiveListNewActivity = new AYJLiveListNewActivity();
        Bundle bundle = new Bundle();
        ivActivity = activity;
        bundle.putSerializable("livelist", (Serializable) yjCourseLiveList);
        ayjLiveListNewActivity.setArguments(bundle);
        return ayjLiveListNewActivity;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getSerializable("livelist")!= null) {
            yjCourseLiveList  = ( List<YJOpenClassModel>)getArguments().getSerializable("livelist");
        }
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(ivActivity.getContext()).getBoolean("isLogin", false);
        getNotifyListener();
    }


    private  void getNotifyListener() {
        if (ivActivity != null){
            ivActivity.addListener(YJNotifyTag.LIVE_CHANNEL_LIVE_LIST, new IVNotificationListener() {
                @Override
                public void onNotify(String s, Object o) {
                    yjCourseLiveList = (List<YJOpenClassModel>) o;
                    StringUtils.Log(TAG, "getNotifyListener");
                    if (yjCourseLiveList == null) {
                        StringUtils.Log(TAG,"yjCourseLiveList == null");
                        ll_no_data.setVisibility(View.VISIBLE);
                        lv_liveclass.setVisibility(View.GONE);
                    } else {
                        if (yjCourseLiveList.size() == 0){
                            StringUtils.Log(TAG, "yjCourseLiveList != null");
                            ll_no_data.setVisibility(View.VISIBLE);
                            lv_liveclass.setVisibility(View.GONE);
                        }else {
                            lv_liveclass.setVisibility(View.VISIBLE);
                            ll_no_data.setVisibility(View.GONE);

                            if (adapter == null) {
                                adapter = new MyAdapter();
                                lv_liveclass.setAdapter(adapter);
                            } else {
                                if (getActivity() != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        public void run() {
                                            StringUtils.Log(TAG, "notifyDataSetChanged=");
                                            lv_liveclass.setAdapter(adapter);
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
    }
    @Override
    public void onClick(View view) {
        if (view == txt_select_course) {
            if (ivActivity != null){
                ivActivity.notifyListener(YJNotifyTag.MAIN_LESSON, 0);
            }
        }
    }

    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (yjCourseLiveList != null && yjCourseLiveList.size() >0){
                return yjCourseLiveList.size();
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
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_live_channel_item, null);
                holder.img_teacher_photo =(CustomImage) convertView.findViewById(R.id.img_teacher_photo);
                holder.txt_course_name =(TextView) convertView.findViewById(R.id.txt_course_name);
                holder.txt_time =(TextView) convertView.findViewById(R.id.txt_time);
                holder.txt_course_status =(TextView) convertView.findViewById(R.id.txt_course_status);
                holder.txt_user_num =(TextView) convertView.findViewById(R.id.txt_user_num);
                holder.txt_title =(TextView) convertView.findViewById(R.id.txt_title);
                holder.img_course_living =(ImageView) convertView.findViewById(R.id.img_course_living);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            if (yjCourseLiveList != null){
                YJOpenClassModel yjOpenClassModel =  yjCourseLiveList.get(position);
                if (!TextUtils.isEmpty(yjOpenClassModel.getTeacher().getTeacherPic())) {
                    BitmapUtils.create(getContext()).display(holder.img_teacher_photo,yjOpenClassModel.getTeacher().getTeacherPic());
                }
                if (!TextUtils.isEmpty(yjOpenClassModel.getCourseName())) {
                    holder.txt_course_name.setText(yjOpenClassModel.getCourseName());
                } else {
                    holder.txt_course_name.setText("课程名称");
                }

                if (!TextUtils.isEmpty(yjOpenClassModel.getName())) {
                    holder.txt_title.setText(yjOpenClassModel.getName());
                } else {
                    holder.txt_title.setText("课程名称");
                }

                if (!TextUtils.isEmpty(yjOpenClassModel.getLookStudent())) {
                    holder.txt_user_num.setText(yjOpenClassModel.getLookStudent());
                } else {
                    holder.txt_user_num.setText("0");
                }
                if( yjOpenClassModel.getLiveStatus().equals("ing")) {
                    holder.txt_course_status.setVisibility(View.VISIBLE);
                    holder.img_course_living.setVisibility(View.VISIBLE);
                } else {
                    holder.txt_course_status.setVisibility(View.GONE);
                    holder.img_course_living.setVisibility(View.GONE);
                }

                if(!TextUtils.isEmpty(yjOpenClassModel.getPlanDate()) ){
                    String planDate = TimeUtil.getSecondMInTimeLive(Long.parseLong(yjOpenClassModel.getPlanDate()));
                    holder.txt_time.setText(planDate);
                }else {
                    holder.txt_time.setText("暂无开课日期");
                }
            }

            return convertView;
        }
    }
    private class ViewHolder {
        private TextView txt_course_name;
        private TextView txt_title;
        private TextView txt_time;
        private TextView txt_course_status;
        private TextView txt_user_num;
        private CustomImage img_teacher_photo;
        private ImageView img_course_living;
    }


    public void getTakenLive(final int position,final boolean isShowDialog,final int isLiveType) {
        ScanBannerUtils.getLiveInfo(ivActivity, yjCourseLiveList.get(position).getCourseId(), "course", yjCourseLiveList.get(position).getCourseCatalogId(), isShowDialog, isLiveType);
        beginStudy(position);
    }

    public void getTakenBack(int position) {
        ScanBannerUtils.getTakenBack(ivActivity, yjCourseLiveList.get(position).getCourseVideoId(), yjCourseLiveList.get(position).getTeacher().getTeacherId());
        beginStudy(position);
    }
    //学习记录
    private void beginStudy(int position){
        YJCourseCatalogModel yjCourseCatalogModel = new YJCourseCatalogModel();
        yjCourseCatalogModel.setCourseCatalogId(yjCourseLiveList.get(position).getCourseCatalogId());
        yjCourseCatalogModel.setCourseVideoId(yjCourseLiveList.get(position).getCourseVideoId());
        List<YJCourseCatalogModel> yjCourseCatalogModelList = new ArrayList<YJCourseCatalogModel>();
        yjCourseCatalogModelList.add(yjCourseCatalogModel);

        YJCourseModel yjCourseModel = new YJCourseModel();
        yjCourseModel.setCourseId(yjCourseLiveList.get(position).getCourseId());
        yjCourseModel.setCourseCatalogList(yjCourseCatalogModelList);
        YJUserStudyData.liveCatalogBeginStudy(yjCourseModel, 0);
    }


    //进入详情页面
    public  void getCourseCatalog( String courseId) {
        Intent in = new Intent(getContext(),YJCoursePlayNewActivity.class);
        in.putExtra("courseId",courseId);
        startActivity(in);
    }
}
