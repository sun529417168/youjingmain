package com.youjing.yjeducation.talkfun.plackback;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.util.TimeUtil;
import com.squareup.picasso.Picasso;
import com.talkfun.sdk.module.ChapterEntity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 15/12/29.
 */
public class SectionAdapter extends BaseAdapter {
    private List<ChapterEntity> chapterList;
    private LayoutInflater layoutInflater;
    private Context mContext;

    public SectionAdapter(Context context, List<ChapterEntity> list) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
        chapterList = list;
    }

    public void setChapterList(List<ChapterEntity> list) {
        chapterList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return chapterList.size();
    }

    @Override
    public Object getItem(int position) {
        return chapterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.section_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChapterEntity chapterEntity = chapterList.get(position);
        if (Integer.valueOf(chapterEntity.getPage()) < 10000) {
            String time = TimeUtil.displayHHMMSS(chapterEntity.getTime());
            String page = chapterEntity.getPage();
            if (time != null)
                page += "   " + time;
            viewHolder.pageTv.setText(page);

            viewHolder.courseTv.setText(chapterEntity.getCourse());

//        Picasso.with(mContext).load(R.mipmap.portrait).into(viewHolder.previewImg);
            viewHolder.previewImg.setImageResource(R.mipmap.portrait);
            if (chapterEntity.getThumb() != null) {
                Picasso.with(mContext).load(chapterEntity.getThumb()).into(viewHolder.previewImg);
            }
            if (chapterEntity.getIsCurrentItem()) {
                viewHolder.sectionNum.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.current_section_bg));
                viewHolder.sectionNum.setText("播放中");
                viewHolder.sectionNum.setTextColor(Color.WHITE);
            } else {
                viewHolder.sectionNum.setBackgroundDrawable(null);
                viewHolder.sectionNum.setText("");
                viewHolder.sectionNum.setTextColor(mContext.getResources().getColor(R.color.secondaryTextColor));
            }
        } else {
            viewHolder.sectionNum.setText("");
            viewHolder.courseTv.setText("");
            viewHolder.pageTv.setText("");
            viewHolder.previewImg.setImageResource(R.drawable.white_board_placeholder);
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.preview)
        ImageView previewImg;
        @Bind(R.id.page)
        TextView pageTv;
        @Bind(R.id.course)
        TextView courseTv;
        @Bind(R.id.section_num)
        TextView sectionNum;

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
