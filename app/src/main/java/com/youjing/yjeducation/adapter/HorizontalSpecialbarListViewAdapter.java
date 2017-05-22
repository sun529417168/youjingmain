package com.youjing.yjeducation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.YJCommendCourseTypeMedel;
import com.youjing.yjeducation.model.YJCourseChanneModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author stt
 *         类说明：课程首页的配套图书专栏的适配器
 *         创建时间：2016.7.1
 */
public class HorizontalSpecialbarListViewAdapter extends BaseAdapter {
    private List<YJCourseChanneModel> typeMedelList = new ArrayList<YJCourseChanneModel>();
    private Context mContext;
    private LayoutInflater layoutInflater;

    public HorizontalSpecialbarListViewAdapter(Context context, List<YJCourseChanneModel> typeMedelList) {
        this.mContext = context;
        this.typeMedelList = typeMedelList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return typeMedelList.size() == 0 ? 0 : typeMedelList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeMedelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_specialbar_list, null);
            holder.mImage = (ImageView) convertView.findViewById(R.id.item_specialbar_list_image);
            holder.mTitle = (TextView) convertView.findViewById(R.id.item_specialbar_list_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        YJCourseChanneModel yjCommendCourseTypeMedel = typeMedelList.get(position);
        if (position!=3){
            holder.mTitle.setText(yjCommendCourseTypeMedel.getName());
            BitmapUtils.create(mContext).display(holder.mImage, yjCommendCourseTypeMedel.getButtonPic());
        }else{
            holder.mTitle.setText("更多");
            holder.mImage.setBackgroundResource(R.mipmap.img_more);
        }
        return convertView;
    }

    private static class ViewHolder {
        private TextView mTitle;
        private ImageView mImage;
    }
}
