package com.youjing.yjeducation.talkfun;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.util.TimeUtil;
import com.talkfun.sdk.config.MtConfig;
import com.talkfun.sdk.module.QuestionEntity;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * if my son want to be programmer , i will break his legs.
 * Created by tony on 2015/11/20.
 */
public class QuestionAdapter extends BaseAdapter {
    private List<QuestionEntity> questionEntityList = new LinkedList<>();
    private LayoutInflater layoutInflater;
    private int MAX_LENGTH = 200;

    public QuestionAdapter(Context context, List<QuestionEntity> list) {
        questionEntityList = list;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setQuestionEntity(List<QuestionEntity> list) {
        questionEntityList = list;
        notifyDataSetChanged();
    }

    public void appendQuestion(QuestionEntity questionEntity) {
        String sn = questionEntity.getSn();
        if (sn.equals("-1") || sn.equals("0")) {   // 说明是答案 ,找到插入答案的位置，并插入
            int temp = questionEntityList.size();
            String qid = questionEntity.getQid();
            for (int i = temp - 1; i >= 0; i--) {
                if (questionEntityList.get(i).getQid().equals(qid)) {
                    temp = ++i;
                    break;
                }
            }
            questionEntityList.add(temp, questionEntity);
        } else { // 说明是 问题.如果是问题还要根据 sn  排序
            if (questionEntityList.size() > 0) {
                for (int i = 0, j = questionEntityList.size(); i < j; i++) {
                    QuestionEntity tempQuestionEntity = questionEntityList.get(i);
                    int tempSn = Integer.valueOf(tempQuestionEntity.getSn());
                    //如果是问题.则通过比较 sn 来排序
                    //如果是回答,则无需比较,直接对比下一条
                    if (tempSn > 0) {
                        int newEntitySn = Integer.valueOf(sn);
                        // 新的问题号小于该项的问题号,说明新问题项在该项问题的前面
                        if (newEntitySn < tempSn) {
                            if (i == 0) {
                                questionEntityList.add(0, questionEntity);
                            } else {
                                questionEntityList.add(i, questionEntity);
                            }
                            break;
                        }
                    }
                    if (i == j - 1)
                        questionEntityList.add(questionEntity);
                }
            } else {
                questionEntityList.add(questionEntity);
            }
        }
        if (questionEntityList.size() >= MAX_LENGTH) {
            questionEntityList.remove(0).release();
        }
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return questionEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return questionEntityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.question_item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        QuestionEntity questionEntity = questionEntityList.get(position);
//        QuestionEntity questionEntity = questionEntityList.get(position);
        if (!questionEntity.getSn().equals("-1") && !questionEntity.getSn().equals("0")) {
            viewHolder.questionNum.setText(questionEntity.getSn() + ")");
            viewHolder.dividLine.setVisibility(View.GONE);
            convertView.setPadding(0, 10, 0, 0);
        } else {
            viewHolder.questionNum.setText("");
            viewHolder.dividLine.setVisibility(View.VISIBLE);
            convertView.setPadding(0, 0, 0, 0);
        }
        viewHolder.nameTv.setText(questionEntity.getNickname());
        String identity = questionEntity.getRole();
        if (identity.equals("user")) {
            viewHolder.identityTv.setVisibility(View.GONE);
        } else if (identity.equals("admin")) {
            viewHolder.identityTv.setVisibility(View.VISIBLE);
            viewHolder.identityTv.setText(convertView.getContext().getResources().getString(R.string.assistants));
        } else {
            viewHolder.identityTv.setVisibility(View.VISIBLE);
            viewHolder.identityTv.setText(convertView.getContext().getResources().getString(R.string.teacher));
        }
        String name = questionEntity.getNickname();
        if (name != null & !TextUtils.isEmpty(name)) {
            viewHolder.nameTv.setText(name);
        }
        String content = questionEntity.getContent();
        if (content != null & !TextUtils.isEmpty(content)) {
            viewHolder.contentTv.setText(content);
        }
        String time = questionEntity.getTime();
        if (!TextUtils.isEmpty(time)) {
            if (MtConfig.getInstance().playType == MtConfig.TYPE_PLAYBACK)
                time = TimeUtil.displayHHMMSS(time);
            else time = TimeUtil.displayTime(time);
            viewHolder.timeTv.setText(time);
        }
        return convertView;
    }

    class ViewHolder {
        @Bind(R.id.identity)
        TextView identityTv;
        @Bind(R.id.name)
        TextView nameTv;
        @Bind(R.id.content)
        TextView contentTv;
        @Bind(R.id.question_num)
        TextView questionNum;
        @Bind(R.id.divider)
        View dividLine;
        @Bind(R.id.time)
        TextView timeTv;

        ViewHolder(View v) {
            ButterKnife.bind(this, v);
        }

    }
}
