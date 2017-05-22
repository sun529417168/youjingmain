package com.youjing.yjeducation.wiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.nfc.Tag;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.YJGiftModel;

import java.util.HashMap;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/6/8
 * Time 11:20
 */

public class HorizontalListViewAdapter extends BaseAdapter{
    private String TAG = "HorizontalListViewAdapter";
    private Context mContext;
    private LayoutInflater mInflater;
    Bitmap iconBitmap;

    List<YJGiftModel> yjGiftModelList;

    public int selectIndex = -1;

    public HorizontalListViewAdapter(Context context,   List<YJGiftModel> yjGiftModelList){
        this.mContext = context;
        this.yjGiftModelList = yjGiftModelList;
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);


    }
    @Override
    public int getCount() {
        if (yjGiftModelList != null){
            return yjGiftModelList.size();
        }
        return 0;
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
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.horizontal_list_gift_item, null);
            holder.img_gift_select=(ImageView)convertView.findViewById(R.id.img_gift_select);
            holder.img_gift=(ImageView)convertView.findViewById(R.id.img_gift);
            holder.img_gift_info=(ImageView)convertView.findViewById(R.id.img_gift_info);
            holder.img_whale_money=(ImageView)convertView.findViewById(R.id.img_whale_money);
            holder.re_item=(RelativeLayout)convertView.findViewById(R.id.re_item);

            holder.txt_gift_name=(TextView)convertView.findViewById(R.id.txt_gift_name);
            holder.txt_whale_money_num=(TextView)convertView.findViewById(R.id.txt_whale_money_num);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        if (position == 0){
            holder.img_gift_select.setVisibility(View.VISIBLE);
        }else {
            holder.img_gift_select.setVisibility(View.GONE);
        }
        if (selectIndex != -1 ){
            if(position != selectIndex){
                holder.img_gift_select.setVisibility(View.GONE);
            }else{
                holder.img_gift_select.setVisibility(View.VISIBLE);
            }
        }


        if (yjGiftModelList != null){
            YJGiftModel yjGiftModel = yjGiftModelList.get(position);

            String giftName = yjGiftModel.getName();
            if (giftName != null  && !TextUtils.isEmpty(giftName)){
                holder.txt_gift_name.setText(giftName);
            }

            String price = yjGiftModel.getDiscountPrice();
            String payFree = yjGiftModel.getPayFee();
            if (!TextUtils.isEmpty(payFree) && payFree.equals("Yes")){
                    holder.img_whale_money.setVisibility(View.GONE);
                    holder.img_gift_info.setVisibility(View.VISIBLE);
                    holder.txt_whale_money_num.setText("免费");

            }else {
                holder.txt_whale_money_num.setText(price);
                holder.img_whale_money.setVisibility(View.VISIBLE);
                holder.img_gift_info.setVisibility(View.GONE);
            }

            String giftPic = yjGiftModel.getGiftPic();
            if (giftPic != null & !TextUtils.isEmpty(giftPic)) {
                BitmapUtils.create(mContext).display(holder.img_gift, giftPic);
            }

        }

        return convertView;
    }

    private static class ViewHolder {
        private ImageView img_gift;
        private ImageView img_whale_money;
        private ImageView img_gift_select;
        private ImageView img_gift_info;
        private RelativeLayout re_item;
        private TextView txt_gift_name;
        private TextView txt_whale_money_num;


    }

    public void setSelectIndex(int i){
        selectIndex = i;
    }
}