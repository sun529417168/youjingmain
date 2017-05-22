package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.talkfun.sdk.module.VoteOption;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * if my son want to be a programmer, i will break his legs.
 * Created by zhangzemin on 16/1/20.
 */
public class VoteAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<VoteOption> optionList = new ArrayList<>();
    private Context mContext;
    private final int SINGLE = 1;
    private final int MULTIPLE = 2;
    private int checkboxStyle = SINGLE;
    private char[] choice = new char[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public VoteAdapter(Context context, List<VoteOption> optionList) {
        this.optionList = optionList;
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public void setOptionList(List<VoteOption> optionList) {
        this.optionList = optionList;
        notifyDataSetChanged();
    }

    public void setCheckboxStatus(int pos) {
        optionList.get(pos).setIsSelected(true);
        notifyDataSetChanged();
    }

    public void setCheckboxStyle(boolean style) {
        checkboxStyle = style ? SINGLE : MULTIPLE;
    }

    @Override
    public int getCount() {
        return this.optionList.size();
    }


    @Override
    public Object getItem(int position) {
        return optionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.ht_vote_list_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        VoteOption option = optionList.get(position);
        switch (checkboxStyle) {
            case SINGLE:
                viewHolder.itemCb.setVisibility(View.GONE);
                viewHolder.singleItemCb.setVisibility(View.VISIBLE);
                viewHolder.singleItemCb.setChecked(option.isSelected());
                break;
            case MULTIPLE:
                viewHolder.itemCb.setVisibility(View.VISIBLE);
                viewHolder.itemCb.setChecked(option.isSelected());
                viewHolder.singleItemCb.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        viewHolder.itemContent.setText(option.getContent());
        if (position <= choice.length)
            viewHolder.choice.setText(choice[position] + "");

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.choice_item)
        CheckBox itemCb;
        @Bind(R.id.single_choice_item)
        CheckBox singleItemCb;
        @Bind(R.id.choiceTv)
        TextView choice;
        @Bind(R.id.choice_content)
        TextView itemContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}