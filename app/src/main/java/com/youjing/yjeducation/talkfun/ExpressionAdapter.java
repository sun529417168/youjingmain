package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


import com.youjing.yjeducation.util.DimensionUtils;

import java.util.List;

/**
 * Created by asus on 2015/11/24.
 */
public class ExpressionAdapter extends BaseAdapter {
    private List<ExpressionEntity> mDatas;
    private Context mContext;
    private int padding = 0;
    private int imageWidth = 50;
    private int imageHeight = 50;

    public ExpressionAdapter(Context context, List<ExpressionEntity> datas){
        mContext = context;
        mDatas = datas;
        padding = DimensionUtils.dip2px(mContext, 4);
        imageWidth = DimensionUtils.dip2px(mContext, 30);
        imageHeight = DimensionUtils.dip2px(mContext, 30);
    }
   @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        if(mDatas == null || mDatas.size() <= position)
            return null;
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null) {
            imageView = new ImageView(mContext);
           // imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //ScreenUtils.dip2px(activity, 50);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,imageHeight));
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);

            imageView.setPadding(padding, padding,padding, padding);
        }else{
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mDatas.get(position).resId);
        return imageView;
    }
}
