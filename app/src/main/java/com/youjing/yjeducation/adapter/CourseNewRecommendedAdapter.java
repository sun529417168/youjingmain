package com.youjing.yjeducation.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.YJRecommendCourseModel;
import com.youjing.yjeducation.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stt
 *         类说明：课程首页的新课推荐的适配器
 *         创建时间：2016.6.7
 */
public class CourseNewRecommendedAdapter extends BaseAdapter {
    private List<YJRecommendCourseModel> courseList = new ArrayList<YJRecommendCourseModel>();
    private Context context;
    private LayoutInflater layoutInflater;

    public CourseNewRecommendedAdapter() {
    }

    public CourseNewRecommendedAdapter(Context context, List<YJRecommendCourseModel> courseList) {
        this.context = context;
        this.courseList = courseList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return courseList.size() == 0 ? 0 : courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_new_course_recommended, null);
            holder.imageview = (ImageView) convertView.findViewById(R.id.item_new_course_image);
            holder.textInfo = (TextView) convertView.findViewById(R.id.item_new_course_info);
            holder.moneyLogo = (ImageView) convertView.findViewById(R.id.item_new_course_money_logo);
            holder.money = (TextView) convertView.findViewById(R.id.item_new_course_money);
            holder.moneyOld = (TextView) convertView.findViewById(R.id.item_new_course_money_old);
            holder.moneyText = (TextView) convertView.findViewById(R.id.item_new_course_money_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        YJRecommendCourseModel courseModel = courseList.get(position);
        StringUtils.Log("适配器数据", courseModel.toString() + "====" + courseList.size());
        BitmapUtils.create(context).display(holder.imageview, courseModel.getCoursePic());
        holder.textInfo.setText(courseModel.getName());
        if ("RMB".equals(courseModel.getCoursePayWay())) {//如果是人民币
            holder.moneyLogo.setBackgroundResource(R.mipmap.img_rmb);
            holder.moneyText.setVisibility(View.GONE);
            holder.money.setVisibility(View.VISIBLE);
            if (Double.parseDouble(courseModel.getPayMoney()) < Double.parseDouble(courseModel.getOriginalMoney())) {
                holder.money.setText(courseModel.getPayMoney());
                holder.moneyOld.setVisibility(View.VISIBLE);
                holder.moneyOld.setText("￥"+courseModel.getOriginalMoney());
                holder.moneyOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            if (Double.parseDouble(courseModel.getPayMoney()) >= Double.parseDouble(courseModel.getOriginalMoney())) {
                holder.money.setText(courseModel.getPayMoney());
                holder.moneyOld.setVisibility(View.GONE);
            }
        }else if ("XNB".equals(courseModel.getCoursePayWay())) {//如果是鲸币
            holder.moneyLogo.setBackgroundResource(R.mipmap.whale_money);
            holder.moneyText.setVisibility(View.GONE);
            holder.money.setVisibility(View.VISIBLE);
            if (Integer.parseInt(courseModel.getPayCoin()) < Integer.parseInt(courseModel.getOriginalCoin())) {
                holder.money.setText(courseModel.getPayCoin());
                holder.moneyOld.setVisibility(View.VISIBLE);
                holder.moneyOld.setText(courseModel.getOriginalCoin());
                holder.moneyOld.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
            if (Integer.parseInt(courseModel.getPayCoin()) >= Integer.parseInt(courseModel.getOriginalCoin())) {
                holder.money.setText(courseModel.getPayCoin());
                holder.moneyOld.setVisibility(View.GONE);
            }
        }else if ("FREE".equals(courseModel.getCoursePayWay())) {//如果是免费
            holder.moneyText.setVisibility(View.VISIBLE);
            holder.moneyText.setText("免费");
            holder.moneyLogo.setVisibility(View.GONE);
            holder.money.setVisibility(View.GONE);
            holder.moneyOld.setVisibility(View.GONE);
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageview, moneyLogo;
        TextView textInfo, moneyText, money, moneyOld;
    }

    public void clearList() {
        this.courseList.clear();
    }

    public void addList(ArrayList<YJRecommendCourseModel> courseList) {
        this.courseList.addAll(courseList);
    }
}
