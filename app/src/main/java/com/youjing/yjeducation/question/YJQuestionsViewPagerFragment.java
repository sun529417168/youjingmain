package com.youjing.yjeducation.question;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.YJAnswerModel;
import com.youjing.yjeducation.model.YJQuestionsModel;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJLiveRoomQuestionDialog;
import com.youjing.yjeducation.util.StringUtils;

/**
 * user  秦伟宁
 * Date 2016/6/22
 * Time 14:33
 */
public class YJQuestionsViewPagerFragment extends Fragment {

    public View view;
    private Context context;
    private YJQuestionsModel yjQuestionsModel;
    private List<YJAnswerModel> yjAnswerModelList;
    private Integer number=0, sum=0;
    private static boolean isNextPage = true;
    private int isShow = 1;
    public TextView tv_type, tv_number, tv_sum, tv_answer, tv_knowledge,
            tv_statistics;
    public WebView wv_title, wv_analysis, wv_AskQuestion;
    public LinearLayout answerLayout, mLayoutGroup, layoutLine, layoutAnswer,
            layoutAskQuestion;
    private Button isShow_bt;

    public static final String[] ENLISH_DIGITAL = {"A","B","C","D","E","F","G","L","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
    public YJQuestionsViewPagerFragment() {

    }


    public YJQuestionsViewPagerFragment(Context context, YJQuestionsModel yjQuestionsModel,
                                      Integer number, Integer sum) {
        this.context = context;
        this.yjQuestionsModel = yjQuestionsModel;
        this.number = number;
        StringUtils.Log(TAG, "number=" + number);
        this.sum = sum;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.questions_layout_viewpager, container,
                false);

        mLayoutGroup = (LinearLayout) view.findViewById(R.id.item_RadioGroup);
        tv_type = (TextView) view
                .findViewById(R.id.textView_QuestionsMain_vPager_examQuestionType);
        tv_number = (TextView) view
                .findViewById(R.id.textView_QuestionsMain_vPager_CompletedNumber);
        tv_sum = (TextView) view
                .findViewById(R.id.textView_QuestionsMain_vPager_CompletedSum);
        wv_title = (WebView) view
                .findViewById(R.id.webview_QuestionsMain_vPager_examQuestionTitle);

        return view;
    }

    public enum ExamQuestionType {
        SINGLE_CHOICE_QUESTION("单选题"), MULTIPLE_CHOICE_QUESTION("多选题"), UndefinedChoice("不定项"), TrueOrFalse(
                "判断题"), AskQuestion("问答题"), AskCalculation("计算题"), Material(
                "材料"), A1("A1型题"), // 单选
        A2("A2型题"), // 单选
        A3("A3型题"), // 材料
        A4("A4型题"), // 材料
        B1("B1型题"), // 材料
        A("A型题"), // 单选
        B("B型题"), // 材料
        B0("B0型题"), // B材料单选
        X("X型题"), // 多选
        C("C型题"); // 材料

        private String chineseName;

        private ExamQuestionType(String chineseName) {
            this.chineseName = chineseName;
        }

        public String getChineseName() {
            return chineseName;
        }
    }

    private String TAG = "QuestionsViewPagerFragment";
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        yjAnswerModelList  = yjQuestionsModel.getAnswerList();
        tv_number.setText(number+"");
        tv_sum.setText("/" + sum + "题");

        StringUtils.Log(TAG, "number=" + number);
        yjAnswerModelList = yjQuestionsModel.getAnswerList();
        if (!TextUtils.isEmpty(yjQuestionsModel.getQuestionsTypes())) {
            try {
                tv_type.setText("【" + ExamQuestionType.valueOf(yjQuestionsModel.getQuestionsTypes()).getChineseName() + "】");// 题类型
                WebViewUtils.initDatas(wv_title, yjQuestionsModel.getQuestionsText(), "");// 题目
            } catch (Exception e) {
            }
        } else {

        }

        if ("SINGLE_CHOICE_QUESTION".equals(yjQuestionsModel.getQuestionsTypes())) {// 单选
            final List<TextView> textViews = new ArrayList<TextView>();
            final List<RadioButton> radioButtons = new ArrayList<RadioButton>();
            for (int i = 0; i < yjAnswerModelList.size(); i++) {

                LinearLayout layout = initLinearLayout(context, LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT);
                final String option =  i+"";
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                FrameLayout optionsLayout = (FrameLayout) inflater.inflate(R.layout.questions_options_radiobutton_item, null);
                final RadioButton radioButton = (RadioButton) optionsLayout.findViewById(R.id.questions_options_item_RadioButton);
                radioButton.setId(10000 + i);
                // final TextView tv_sequence = (TextView) optionsLayout
                // .findViewById(R.id.questions_options_RadioButton_item_TextView);
                if (option.equals("0")) {
                    radioButton.setButtonDrawable(R.drawable.radiobutton_single_a);
                } else if (option.equals("1")) {
                    radioButton.setButtonDrawable(R.drawable.radiobutton_single_b);
                } else if (option.equals("2")) {
                    radioButton.setButtonDrawable(R.drawable.radiobutton_single_c);
                } else if (option.equals("3")) {
                    radioButton.setButtonDrawable(R.drawable.radiobutton_single_d);
                } else if (option.equals("4")) {
                    radioButton.setButtonDrawable(R.drawable.radiobutton_single_e);
                } else if (option.equals("5")) {
                    radioButton.setButtonDrawable(R.drawable.radiobutton_single_e);
                } else if (option.equals("6")) {
                    radioButton.setButtonDrawable(R.drawable.radiobutton_single_e);
                }


                LinearLayout wvLyaout = initLinearLayout(context, LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                WebView wv_sequenceText = WebViewUtils.initWebView(context, "", yjAnswerModelList.get(i).getAnswerText(), "", false);

                layout.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String answer = "";

                        for (int j = 0; j < radioButtons.size(); j++) {
                            radioButtons.get(j).setChecked(false);
                        }
                        radioButton.setChecked(true);
                        StringUtils.Log(TAG, "option=" + option);
                       ;
                        answer =formatEnlishDigital(Integer.parseInt(option))+ yjAnswerModelList.get(Integer.parseInt(option)).getAnswerId();
                        if (answer == "") {
                            AYJLiveRoomQuestionDialog.questionsAnswerMap.put(yjQuestionsModel.getQuestionsId(), null);
                        } else {
                            AYJLiveRoomQuestionDialog.questionsAnswerMap.put(yjQuestionsModel.getQuestionsId(), answer);
                        }
                        if (isNextPage()) {
                            AYJLiveRoomQuestionDialog.NextPage();
                            setNextPage(false);
                        }
                    }
                });
                // QuestionsViewPagerControl.initSelectRadioButton(info.getId(),
                // radioButton, tv_sequence, radioButton.getId());
                wvLyaout.addView(wv_sequenceText);
                layout.addView(optionsLayout);
                layout.addView(wvLyaout);
                mLayoutGroup.addView(layout);// 往父容器RadioGroup中添加
                radioButtons.add(radioButton);
                optionsLayout =null;
                wv_sequenceText = null;
                wvLyaout = null;
                layout = null;

                // textViews.add(tv_sequence);
            }
        } else if ("MULTIPLE_CHOICE_QUESTION".equals(yjQuestionsModel.getQuestionsTypes())) {// 多选
            // final List<TextView> textViews = new ArrayList<TextView>();
            final List<CheckBox> checkBoxs = new ArrayList<CheckBox>();
            final List<String> answers = new ArrayList<String>();
            for (int i = 0; i < yjAnswerModelList.size(); i++) {
                LinearLayout layout = initLinearLayout(context, LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
                final String option = i+"";

                LayoutInflater inflater = LayoutInflater.from(getActivity());
                FrameLayout optionsLayout = (FrameLayout) inflater.inflate(R.layout.questions_options_checkbox_item, null);
                final CheckBox checkBox = (CheckBox) optionsLayout.findViewById(R.id.questions_options_item_CheckBox);
                checkBox.setId(1000 + i);
                // final TextView tv_sequence = (TextView) optionsLayout
                // .findViewById(R.id.questions_options_CheckBox_item_TextView);
                // tv_sequence.setText(option);
                if (option.equals("0")) {
                    checkBox.setButtonDrawable(R.drawable.radiobutton_multi_a);
                } else if (option.equals("1")) {
                    checkBox.setButtonDrawable(R.drawable.radiobutton_multi_b);
                } else if (option.equals("2")) {
                    checkBox.setButtonDrawable(R.drawable.radiobutton_multi_c);
                } else if (option.equals("3")) {
                    checkBox.setButtonDrawable(R.drawable.radiobutton_multi_d);
                } else if (option.equals("4")) {
                    checkBox.setButtonDrawable(R.drawable.radiobutton_multi_e);
                } else if (option.equals("5")) {
                    checkBox.setButtonDrawable(R.drawable.radiobutton_multi_e);
                } else if (option.equals("6")) {
                    checkBox.setButtonDrawable(R.drawable.radiobutton_multi_e);
                }


                WebView wv_sequenceText = WebViewUtils.initWebView(context, "", yjAnswerModelList.get(i).getAnswerText(), "", false);

                layout.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (!checkBox.isChecked()) {
                            checkBox.setChecked(true);
                            // tv_sequence.setTextColor(getResources().getColor(
                            // color.white));
                        } else {
                            checkBox.setChecked(false);
                            // tv_sequence.setTextColor(getResources().getColor(
                            // color.blue));
                        }

                        String answer = "";
                        for (int j = 0; j < checkBoxs.size(); j++) {
                            if (checkBoxs.get(j).isChecked()) {
                                answer = answer + answers.get(j);
                            }
                        }

                      /*  if (answer == "") {
                            QuestionsActivity.questionsAnswerMap.put(
                                    info.getId(), null);
                        } else {
                            QuestionsActivity.questionsAnswerMap.put(
                                    info.getId(), answer);
                        }*/
                    }
                });
                // QuestionsViewPagerControl.initSelectCheckBox(info.getId(),
                // checkBox, tv_sequence);
                layout.addView(optionsLayout);
                layout.addView(wv_sequenceText);
                mLayoutGroup.addView(layout);// 往父容器RadioGroup中添加
                checkBoxs.add(checkBox);
                // textViews.add(tv_sequence);
                answers.add(option);
                optionsLayout = null;
                wv_sequenceText = null;
                layout =null;
            }
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle bundle = new Bundle();
        bundle.putBooleanArray("daan", new boolean[]{});
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }



    public static boolean isNextPage() {
        return isNextPage;
    }

    public static void setNextPage(boolean isNextPage) {
        YJQuestionsViewPagerFragment.isNextPage = isNextPage;
    }

    public static LinearLayout initLinearLayout(Context context, int layoutWidth, int layoutHeight) {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new LayoutParams(layoutWidth, layoutWidth));
        layout.setGravity(Gravity.CENTER_VERTICAL);
        return layout;
    }

    public static String formatEnlishDigital(Integer digital){
        return ENLISH_DIGITAL[digital];
    }
}
