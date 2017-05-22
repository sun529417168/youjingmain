package com.youjing.yjeducation.ui.dispaly.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.YJAnwserResultModel;

import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/6/16
 * Time 11:12
 */
@VLayoutTag(R.layout.dialog_live_question_result)
public class AYJAnswerResultDialog extends AVDialog implements IVClickDelegate, IVAdapterDelegate {
    @VViewTag(R.id.img_delete)
    private RelativeLayout img_delete;
    @VViewTag(R.id.lv_question_result)
    private ListView lv_question_result;
    @VViewTag(R.id.txt_question_over)
    private TextView txt_question_over;


    private List<YJAnwserResultModel> yjAnwserResultModelList;
    private String TAG = "AYJLiveRoomQuestionDialog";
    public static final VParamKey<List<YJAnwserResultModel>> ANSWER_RESULT_LIST = new VParamKey<List<YJAnwserResultModel>>(null);

    @Override
    protected void onLoadedView() {
        yjAnwserResultModelList = getTransmitData(ANSWER_RESULT_LIST);

    }

    @Override
    public void onClick(View view) {
        if (view == img_delete) {
            close();
        } else if (view == txt_question_over) {
            close();
        }
    }


    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJQuestionResultItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjAnwserResultModelList != null) {
            return yjAnwserResultModelList.size();
        } else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.dialog_question_result_item)
    class YJQuestionResultItem extends AVAdapterItem {
        @VViewTag(R.id.txt_question_result_num)
        private TextView txt_question_result_num;
        @VViewTag(R.id.txt_question_iscorrect)
        private TextView txt_question_iscorrect;
        @VViewTag(R.id.txt_question_correct_option)
        private TextView txt_question_correct_option;
        @VViewTag(R.id.txt_question_error_option)
        private TextView txt_question_error_option;

        @VViewTag(R.id.txt_question_result_no)
        private TextView txt_question_result_no;
        @VViewTag(R.id.txt_question_result_your)
        private TextView txt_question_result_your;

        @Override
        public void update(final int position) {

            if (yjAnwserResultModelList != null) {
                txt_question_result_num.setText(position + 1 + "、");

                YJAnwserResultModel yjAnwserResultModel = yjAnwserResultModelList.get(position);

                if (!TextUtils.isEmpty(yjAnwserResultModel.getIsRight()) && yjAnwserResultModel.getIsRight().equals("Yes")) {
                    txt_question_iscorrect.setText("答对了!");
                    txt_question_iscorrect.setTextColor(getResources().getColor(R.color.blue_text));
                    txt_question_result_num.setTextColor(getResources().getColor(R.color.blue_text));

                    txt_question_error_option.setVisibility(View.GONE);
                    txt_question_result_no.setVisibility(View.GONE);
                    txt_question_result_your.setVisibility(View.GONE);
                } else {
                    txt_question_iscorrect.setText("答错了!");
                    txt_question_iscorrect.setTextColor(getResources().getColor(R.color.red_text_live));
                    txt_question_result_num.setTextColor(getResources().getColor(R.color.red_text_live));

                    txt_question_result_your.setVisibility(View.VISIBLE);
                    if (!TextUtils.isEmpty(yjAnwserResultModel.getCusAnswer())) {
                        txt_question_error_option.setText(yjAnwserResultModel.getCusAnswer());
                        txt_question_error_option.setVisibility(View.VISIBLE);
                        txt_question_result_no.setVisibility(View.GONE);
                    } else {
                        txt_question_result_no.setVisibility(View.VISIBLE);
                        txt_question_error_option.setVisibility(View.GONE);
                    }
                }
                txt_question_correct_option.setText(yjAnwserResultModel.getRightAnswer());
            }

        }
    }
}
