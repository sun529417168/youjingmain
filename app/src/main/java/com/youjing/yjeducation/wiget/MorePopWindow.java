package com.youjing.yjeducation.wiget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;

import com.youjing.yjeducation.model.YJCourseChanneModel;
import com.youjing.yjeducation.model.YJCourseTypesBaseMedel;
import com.youjing.yjeducation.util.StringUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCommendCourseTypeMedel;
import com.youjing.yjeducation.model.YJCourseTypeModel;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.model.YJRecommendCourseLiveModel;
import com.youjing.yjeducation.model.YJRecommendCourseModel;
import com.youjing.yjeducation.model.YJSubjectModel;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.util.MakeSign;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/14
 * Time 11:53
 */

public class MorePopWindow extends PopupWindow implements View.OnClickListener {

    private Context context;
    private String TAG = "yjRecommendCourseList";
    private int mGrade, mSubject;
    private final String ACTION_NAME = "发送广播";

    private List<YJGradeModel> list;
    private List mGradeList;
    private IVActivity ivActivity;
    private GridView mGirdView, mSubjectView;
    private LinearLayout mSubjectLayout;
    private TextView mSubjectInfo;
    private CheckBoxAdapter adapter, checkBoxAdapter;
    private View conentView;
    private List<YJSubjectModel> mSubjectModelList;
    private List subjectList;
    private HashMap isSubjectSelect, isSelectTy;
    private boolean mIsLogin = false;
    private boolean mIsFirstLogin;

    public MorePopWindow(final Activity context, IVActivity ivActivity) {
        this.context = context;
        this.ivActivity = ivActivity;
        mGrade = PreferenceManager.getDefaultSharedPreferences(context).getInt("gradeNum", 0);
        mSubject = PreferenceManager.getDefaultSharedPreferences(context).getInt("subjectNum", 0);
        setList();
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isLogin", false);
        mIsFirstLogin = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("mIsFirstLogin", true);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.test_gridview, null);

        mGirdView = (GridView) conentView.findViewById(R.id.activity_main_grid);
        mSubjectView = (GridView) conentView.findViewById(R.id.activity_main_subject);

        mSubjectInfo = (TextView) conentView.findViewById(R.id.test_gridview_subject_info);
        mSubjectInfo.setVisibility(subjectList.size() <= 1 ? View.VISIBLE : View.INVISIBLE);
        adapter = new CheckBoxAdapter(context, mGradeList, isSelectTy, "一年级", "grade");
        checkBoxAdapter = new CheckBoxAdapter(context, subjectList, isSubjectSelect, "数学", "subject");
        mGirdView.setAdapter(adapter);
        mSubjectView.setAdapter(checkBoxAdapter);

        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.AnimationPreview);

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.5f;
        context.getWindow().setAttributes(lp);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                notifyCourse("MorePopWindow消失", true);

                mIsFirstLogin = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("mIsFirstLogin", true);
                if (mIsFirstLogin) {
                    saveFisetInfo(YJGlobal.getMy_grade_id(), YJGlobal.getGradeList().get(0).getSubjectVos().get(0).getSubjectId());
                    saveFirstLogin(false);
                }

                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
                context.getWindow().setAttributes(lp);
            }
        });


    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, parent.getLayoutParams().height, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 初始化list，添加数据,初始化checkbox的值
     */
    private void setList() {
        list = YJGlobal.getGradeList();
        mGradeList = new ArrayList();
        isSelectTy = new HashMap();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                mGradeList.add(list.get(i).getGradeName());
                isSelectTy.put(i, false);
            }
            isSelectTy.put(mGrade, true);
            YJGlobal.setMy_grade((String) mGradeList.get(mGrade));
            YJGlobal.setMy_grade_id(list.get(mGrade).getGradeId());

            mSubjectModelList = list.get(mGrade).getSubjectVos();
            ;
        }

        subjectList = new ArrayList();
        isSubjectSelect = new HashMap();
        if (mSubjectModelList != null) {
            for (int i = 0; i < mSubjectModelList.size(); i++) {
                subjectList.add(mSubjectModelList.get(i).getSubjectName());
                isSubjectSelect.put(i, false);
            }
            isSubjectSelect.put(mSubject, true);
            YJGlobal.setMy_subject((String) subjectList.get(mSubject));
        }

    }

    private void setData() {

    }


    public static int temp = -1;

    public class CheckBoxAdapter extends BaseAdapter {
        Activity context;
        private List mList;
        private String str;
        private String mFlag;

        HashMap isSelect;

        public CheckBoxAdapter(Activity context, List list, HashMap isSelect, String str, String mFlag) {
            this.context = context;
            this.mList = list;
            this.isSelect = isSelect;
            this.mFlag = mFlag;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final Holder holder;
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.gridview_item, null);
                holder = new Holder();
                holder.ib_item = (Button) convertView.findViewById(R.id.ib_item);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.ib_item.setPressed((boolean) isSelect.get(position));
            holder.ib_item.setText((String) mList.get(position));
            /*if (list.get(position).equals(str)) {
                holder.tvContent.setChecked(true);
            }*/
            holder.ib_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mFlag.equals("grade")) {
                        isSelect.put(position, true);
                        if (view.isPressed()) {
                            mSubjectModelList = list.get(position).getSubjectVos();
                            subjectList = new ArrayList();
                            isSubjectSelect = new HashMap();
                            if (mSubjectModelList != null) {
                                for (int i = 0; i < mSubjectModelList.size(); i++) {
                                    subjectList.add(mSubjectModelList.get(i).getSubjectName());
                                    isSubjectSelect.put(i, false);
                                }
                                isSubjectSelect.put(0, true);
                            }
                            checkBoxAdapter = new CheckBoxAdapter(context, subjectList, isSubjectSelect, "数学", "subject");
                            mSubjectView.setAdapter(checkBoxAdapter);
                        }
                    } else {
                        isSubjectSelect.put(position, true);
                    }
                    if (view.isPressed()) {
                        for (int i = 0; i < list.size(); i++) {
                            //把其他的checkbox设置为false
                            if (i != position) {
                                if (mFlag.equals("grade")) {
                                    isSelect.put(i, false);
                                } else {
                                    isSubjectSelect.put(i, false);
                                }
                            } else {
                                if (mFlag.equals("grade")) {
                                    saveGrade((String) mList.get(position), position);
                                    YJGlobal.setMy_grade((String) mList.get(position));
                                    YJGlobal.setMy_grade_id(list.get(position).getGradeId());
                                    notifyCourse((String) mList.get(position), true);

                                    notifyCourse(list.get(position).getSubjectVos().get(0).getSubjectName(), false);
                                    saveProject(list.get(position).getSubjectVos().get(0).getSubjectName(), 0);
                                } else {
                                    saveProject((String) mList.get(position), position);
                                    YJGlobal.setMy_subject((String) mList.get(position));
                                    if (mIsFirstLogin) {
//                                        saveFisetInfo(YJGlobal.getMy_grade_id(), YJGlobal.getGradeList().get(Integer.parseInt(YJGlobal.getMy_grade_id())-1).getSubjectVos().get(position).getSubjectId());
                                        saveFisetInfo(YJGlobal.getMy_grade_id(), translationIdSubjectName().get(position).getSubjectId());
                                        //   StringUtils.Log(TAG, "YJGlobal.getMy_grade_id()=" + YJGlobal.getMy_grade_id()+"id="+YJGlobal.getGradeList().get(Integer.parseInt(YJGlobal.getMy_grade_id())).getSubjectVos().get(position).getSubjectId());
                                        saveFirstLogin(false);
                                    }
                                    notifyCourse((String) mList.get(position), false);
                                }

                            }
                        }
                    }

                    //通知适配器更改
                    CheckBoxAdapter.this.notifyDataSetChanged();

                    if (mSubjectModelList.size() > 1 && mFlag.equals("subject")) {
                        getCourseType(mSubjectModelList.get(position).getSubjectId());
                    } else if (mSubjectModelList.size() == 1) {
                        getCourseType(mSubjectModelList.get(0).getSubjectId());
                    }
                }
            });
            if (temp == position) {
                holder.ib_item.setPressed(true);
            }
            return convertView;
        }

        class Holder {
            CheckBox tvContent;
            Button ib_item;
        }

        public void notifyDataSetChanged(String str) {
            this.str = str;
            this.notifyDataSetChanged();
        }

    }

    //发送一个广播 修改title年级
    private void notifyCourse(String info, boolean mflag) {
        Intent mIntent = new Intent(ACTION_NAME);
        if (mflag) {
            mIntent.putExtra("grade", info);
        } else {
            mIntent.putExtra("subject", info);
        }

        //发送广播
        context.sendBroadcast(mIntent);
    }

    private void saveGrade(String grade, int num) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("grade", grade);
        editor.putInt("gradeNum", num);
        editor.commit();
    }

    private void saveProject(String subject, int num) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("subject", subject);
        editor.putInt("subjectNum", num);
        editor.commit();
    }

    //获得课程类型
    private void getCourseType(final String subjectId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("gradeId", YJGlobal.getMy_grade_id());
            objectMap.put("subjectId", subjectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSETYPE, objectMap, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                ivActivity.showToast(ivActivity.getContext().getString(R.string.no_net_work));
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
                            List<YJCourseTypeModel> yjCourseTypeModelList = JSON.parseArray(jsonData.getString("courseTypes"), YJCourseTypeModel.class);
                            List<YJRecommendCourseModel> yjRecommendCourseList = JSON.parseArray(jsonData.getString("recommendCourseList"), YJRecommendCourseModel.class);
                            List<YJRecommendCourseLiveModel> yjRecommendCourseLiveList = JSON.parseArray(jsonData.getString("recommendCourseLiveList"), YJRecommendCourseLiveModel.class);
                            List<YJCourseChanneModel> yjCourseChanneModel = JSON.parseArray(jsonData.getString("courseChannelList"), YJCourseChanneModel.class);
                            YJGlobal.setYjCourseTypeModelList(yjCourseTypeModelList);
                            YJGlobal.setYjRecommendCourseModelListlList(yjRecommendCourseList);
                            StringUtils.Log("直播推荐", yjRecommendCourseLiveList.toString() + "===数量===" + yjRecommendCourseLiveList.size());
                            ivActivity.notifyListener(YJNotifyTag.COURSE_TYPE, yjCourseTypeModelList);
                            ivActivity.notifyListener(YJNotifyTag.NEW_COURSE_RECOMMENDED, yjRecommendCourseList);
                            ivActivity.notifyListener(YJNotifyTag.LIVE_COURSE_RECOMMENDED, yjRecommendCourseLiveList);
                            ivActivity.notifyListener(YJNotifyTag.COURSE_TYPES, yjCourseChanneModel);
                            YJGlobal.setMy_subjectId(subjectId);
                            break;
                        case 300:
                            break;
                        case 400:
                            StringUtils.Log(TAG, "400");
                            YJGlobal.setYjCourseTypeModelList(null);
                            ivActivity.notifyListener(YJNotifyTag.COURSE_TYPE, null);
                            ivActivity.notifyListener(YJNotifyTag.NEW_COURSE_RECOMMENDED, null);
                            ivActivity.notifyListener(YJNotifyTag.LIVE_COURSE_RECOMMENDED, null);
                            ivActivity.notifyListener(YJNotifyTag.COURSE_TYPES, null);
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            ivActivity.startActivity(ivActivity.createIntent(YJLoginActivity.class, ivActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            ivActivity.finishAll();
                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                MorePopWindow.this.dismiss();
            }
        });
    }

    private void saveFisetInfo(String grade, String subject) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("first_grade", grade);
        editor.putString("first_subject", subject);
        editor.commit();
    }

    private void saveFirstLogin(boolean mIsFirstLogin) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("mIsFirstLogin", mIsFirstLogin);
        editor.commit();
    }


    private static List<YJSubjectModel> msubjectModellist;

    public List<YJSubjectModel> translationIdSubjectName() {
        int p = YJGlobal.getGradeList().size();
        for (int j = 0; j < p; j++) {
            if (YJGlobal.getGradeList().get(j).getGradeId().equals(YJGlobal.getMy_grade_id())) {
                msubjectModellist = YJGlobal.getGradeList().get(j).getSubjectVos();
            }
        }
        return msubjectModellist;
    }

}