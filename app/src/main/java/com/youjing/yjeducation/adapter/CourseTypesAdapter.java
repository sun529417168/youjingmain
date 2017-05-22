package com.youjing.yjeducation.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.model.YJCourseTypeModel;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;

import java.util.ArrayList;

/**
 * @author stt
 *         类说明：课程首页的底部课程类型的适配器
 *         创建时间：2016.7.2
 */
public class CourseTypesAdapter extends BaseAdapter {
    private ArrayList<YJCourseTypeModel> courseTypeList = new ArrayList<YJCourseTypeModel>();
    private Context context;
    private LayoutInflater layoutInflater;
    private CourseTypesGridviewAdapter courseTypesGridviewAdapter;

    public CourseTypesAdapter(Context context, ArrayList<YJCourseTypeModel> courseTypeList) {
        this.courseTypeList = courseTypeList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return courseTypeList.size() == 0 || courseTypeList == null ? 0 : courseTypeList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseTypeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_comendtypes, null);
            holder.textInfo = (TextView) convertView.findViewById(R.id.item_comendtypes_title);
            holder.gridview = (GridView) convertView.findViewById(R.id.item_comendtypes_gridview);
            holder.layout = (LinearLayout) convertView.findViewById(R.id.item_comendtypes_layout);
            holder.layoutTiele = (RelativeLayout) convertView.findViewById(R.id.item_comendtypes_layout_title);
            holder.view = convertView.findViewById(R.id.item_comendtypes_view);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final YJCourseTypeModel courseTypeModel = courseTypeList.get(position);
        if (courseTypeModel.getCourseList().size() == 0) {
            holder.layoutTiele.setVisibility(View.GONE);
            holder.view.setVisibility(View.GONE);
        } else {
            holder.view.setVisibility(View.VISIBLE);
            holder.textInfo.setText(courseTypeModel.getName());
            courseTypesGridviewAdapter = new CourseTypesGridviewAdapter(context, courseTypeModel.getCourseList());
            holder.gridview.setAdapter(courseTypesGridviewAdapter);
        }
        holder.gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消GridView中Item选中时默认的背景色
        holder.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent(context,YJCoursePlayNewActivity.class);
                in.putExtra("courseId",courseTypeModel.getCourseList().get(position).getCourseId());
                context.startActivity(in);
            }
        });
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < YJGlobal.getYJCOURSETYPESBASEMEDEL().size(); i++) {
                    if (courseTypeModel.getCourseTypeId().equals(YJGlobal.getYJCOURSETYPESBASEMEDEL().get(i).getCourseTypeId())) {
                        saveGrade(courseTypeModel.getName(), i,courseTypeModel.getCourseTypeId());
                        Intent intent = new Intent(context,YJCourseListlActivity.class);
                        intent.putExtra("courseTypeId",courseTypeModel.getCourseTypeId());
                        context.startActivity(intent);
                    }
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView textInfo;
        GridView gridview;
        LinearLayout layout;
        RelativeLayout layoutTiele;
        View view;
    }
    protected void saveGrade(String course, int num, String courseTypeId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course", course);
        editor.putString("courseTypeId", courseTypeId);
        editor.putInt("courseNum", num);
        editor.commit();
    }
}
