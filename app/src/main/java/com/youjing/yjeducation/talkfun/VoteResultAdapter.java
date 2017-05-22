package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.talkfun.sdk.module.BriefVoteEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/21.
 */
public class VoteResultAdapter extends BaseAdapter {
    private List<BriefVoteEntity> briefVoteEntityList = new ArrayList<>();
    private Context context;
    private LayoutInflater layoutInflater;
    private char[] choice = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public VoteResultAdapter(List<BriefVoteEntity> list, Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        briefVoteEntityList = list;
    }

    @Override
    public int getCount() {
        return briefVoteEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return briefVoteEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.ht_vote_result_list_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        BriefVoteEntity briefVoteEntity = briefVoteEntityList.get(position);
        if (position <= choice.length)
            viewHolder.numTv.setText(choice[position]+"");
        String percent = briefVoteEntity.getOpNum() + "(" + briefVoteEntity.getPercent() + "%)";
        viewHolder.percentTv.setText(percent);
        viewHolder.votePb.setProgress(briefVoteEntity.getPercent());
        return convertView;
    }


    static class ViewHolder {
        @Bind(R.id.num)
        TextView numTv;
        @Bind(R.id.progress)
        ProgressBar votePb;
        @Bind(R.id.percent)
        TextView percentTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
