package com.youjing.yjeducation.wiget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.PopupWindow;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJCourseListModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;

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
 * Date 2016/3/25
 * Time 17:54
 */
public class CourseClassifyPopWindow extends PopupWindow {

    private Context context;
    private String TAG = "CourseClassifyPopWindow";
    private int mCourseNum;
    protected String courseTypeId;
    private final String ACTION_NAME = "发送年级分类";

    private List list;
    private GridView girdView;
    private View conentView;
    private GridViewAdapter adapter;
    HashMap isSelectTy;
    private IVActivity ivActivity;
    private boolean mIsLogin = false;

    public CourseClassifyPopWindow(final Activity context, IVActivity ivActivity) {
        this.context = context;
        this.ivActivity = ivActivity;
        mCourseNum = PreferenceManager.getDefaultSharedPreferences(context).getInt("courseNum", 0);
        courseTypeId = PreferenceManager.getDefaultSharedPreferences(context).getString("courseTypeId", "");
        // project = PreferenceManager.getDefaultSharedPreferences(context).getInt("project", 1);
        setList();
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("isLogin", false);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.course_classify_gridview, null);

        girdView = (GridView) conentView.findViewById(R.id.activity_course_grid);
        adapter = new GridViewAdapter(context, list, isSelectTy, "选择课程");
        girdView.setAdapter(adapter);


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
                notifyCourse("CourseClassifyPopWindow消失", -1);

                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
                context.getWindow().setAttributes(lp);
            }
        });
    }


    private void setList() {
        list = new ArrayList();
        isSelectTy = new HashMap();
        if (list.size() == 0) {
            for (int i = 0; i < YJGlobal.getYJCOURSETYPESBASEMEDEL().size(); i++) {
                list.add(YJGlobal.getYJCOURSETYPESBASEMEDEL().get(i).getName());
                isSelectTy.put(i, false);
            }
            isSelectTy.put(mCourseNum, true);
        } else {
            list.clear();
        }
    }

    public static int temp = -1;

    public class GridViewAdapter extends BaseAdapter {
        Activity context;
        private List list;
        private String str;
        HashMap isSelect;

        public GridViewAdapter(Activity context, List list, HashMap isSelect, String str) {
            this.context = context;
            this.list = list;
            this.isSelect = isSelect;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
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
                convertView = inflater.inflate(R.layout.gridview_course_item, null);
                holder = new Holder();
                holder.ib_item = (Button) convertView.findViewById(R.id.ib_item);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.ib_item.setPressed((boolean) isSelect.get(position));
            holder.ib_item.setText((String) list.get(position));
            /*if (list.get(position).equals(str)) {
                holder.tvContent.setChecked(true);
            }*/
            holder.ib_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSelect.put(position, true);
                    if (view.isPressed()) {
                        for (int i = 0; i < list.size(); i++) {
                            //把其他的checkbox设置为false
                            if (i != position) {
                                isSelect.put(i, false);
                            } else {
                                saveGrade((String) list.get(position), position);
                            }
                        }
                    }
//                    ( new AVActivity()).notifyListener(YJNotifyTag.TITLE_GRADE,list.get(position));

                    notifyCourse((String) list.get(position), position);
                    //通知适配器更改
                    GridViewAdapter.this.notifyDataSetChanged();
                    getCourseType(position);

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

    }

    //发送一个广播 修改title年级
    private void notifyCourse(String course, int position) {
        Intent mIntent = new Intent(ACTION_NAME);
        mIntent.putExtra("course", course);
        if (position != -1) {
            mIntent.putExtra("position", position);
            StringUtils.Log(TAG, "position=" + position);
        }

        //发送广播
        context.sendBroadcast(mIntent);
    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
        } else {
            this.dismiss();
        }
    }

    private void saveGrade(String course, int num) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course", course);
        editor.putInt("courseNum", num);
        editor.commit();
    }


    private void getCourseType(int position) {
        final String isGroup = YJGlobal.getYJCOURSETYPESBASEMEDEL().get(position).getIsGroup();
        StringUtils.Log(TAG, "isGroup=" + isGroup);
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("subjectId", YJGlobal.getMy_subjectId());
            objectMap.put("gradeId", YJGlobal.getMy_grade_id());
            objectMap.put("courseTypeId", YJGlobal.getYJCOURSETYPESBASEMEDEL().get(position).getCourseTypeId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_LIST, objectMap, mIsLogin, new TextHttpResponseHandler() {
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
                            List<YJCourseListModel> yjCourseListModelList = JSON.parseArray(jsonData.getString("knowledgeList"), YJCourseListModel.class);
                            ivActivity.notifyListener(YJNotifyTag.COURSE_LIST_MODEL, getYjCourseModel(yjCourseListModelList));
                                     /*  if (isGroup.equals("No")) {
                        ivActivity.notifyListener(YJNotifyTag.COURSE_LIST_MODEL, getYjCourseModel(yjCourseListModelList));
                    } else {
                        ivActivity.notifyListener(YJNotifyTag.COURSE_LIST_TYPE, yjCourseListModelList);
                    }*/

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
                            ivActivity.startActivity(ivActivity.createIntent(YJLoginActivity.class, ivActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            ivActivity.finishAll();
                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                CourseClassifyPopWindow.this.dismiss();
            }
        });
    }

    private List<YJCourseModel> getYjCourseModel(List<YJCourseListModel> yjCourseListModelList) {
        List<YJCourseModel> yjCourseModelsList = new ArrayList<YJCourseModel>();
        for (int i = 0; i < yjCourseListModelList.size(); i++) {
            for (int j = 0; j < yjCourseListModelList.get(i).getCourseList().size(); j++) {
                yjCourseModelsList.add(yjCourseListModelList.get(i).getCourseList().get(j));
            }
        }
        return yjCourseModelsList;
    }
}