package com.youjing.yjeducation.talkfun;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youjing.yjeducation.R;
import com.talkfun.sdk.HtSdk;
import com.talkfun.sdk.consts.MtConsts;
import com.talkfun.sdk.event.Callback;
import com.talkfun.sdk.module.QuestionEntity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 良好的代码风格能个人带来一个美好的下午
 * Created by tony on 2015/9/16.
 */
public class QuestionFragment extends Fragment implements DispatchQuestion {
    @Bind(R.id.chat_lv)
    ListView questionLv;
    @Bind(R.id.input_edt)
    EditText inputEdt;

    //    String tag;
    String questionContent;
    @Bind(R.id.input)
    RelativeLayout sendFlower;
    @Bind(R.id.send_btn)
    TextView sendBtn;
    @Bind(R.id.flower_btn)
    ImageView flower;
    @Bind(R.id.flower_num)
    TextView redDot;
    private QuestionAdapter questionAdapter;
    private ArrayList<QuestionEntity> questionEntitiesList = new ArrayList<>();
    boolean isShow = false;

    public QuestionFragment() {

    }


    public static QuestionFragment create(ArrayList<QuestionEntity> list) {
        QuestionFragment qf = new QuestionFragment();
        Bundle bundle = new Bundle();
//        bundle.putString("question", tag);
        bundle.putSerializable("list", list);
        qf.setArguments(bundle);
        return qf;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && questionAdapter != null)
            questionAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getSerializable("list") != null) {
            questionEntitiesList = (ArrayList<QuestionEntity>) getArguments().getSerializable("list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View layout = inflater.inflate(R.layout.livein_chat_fragment_layout, container, false);
        ButterKnife.bind(this, layout);
        questionAdapter = new QuestionAdapter(getActivity(), questionEntitiesList);
        questionLv.setAdapter(questionAdapter);
        redDot.setVisibility(View.INVISIBLE);
        flower.setVisibility(View.INVISIBLE);
        sendBtn.setVisibility(View.VISIBLE);
        return layout;
    }

    //    public void setQuestionEntitiesList(ArrayList<QuestionEntity> questionEntitiesList) {
//        this.questionEntitiesList = questionEntitiesList;
//        questionAdapter.setQuestionEntity(questionEntitiesList);
//    }
//
    @OnClick({R.id.send_btn})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                questionContent = inputEdt.getText().toString().trim();

                HtSdk.getInstance().emit(MtConsts.QUESTION, questionContent, callback);
                inputEdt.setText("");
                break;
            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", questionEntitiesList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            if (savedInstanceState == null)
                return;
            ArrayList<QuestionEntity> list = (ArrayList<QuestionEntity>) savedInstanceState.getSerializable("list");
            if (list != null && list.size() > 0) {
                if (questionAdapter != null) {
                    questionEntitiesList = list;
                    questionAdapter.setQuestionEntity(list);
                }
            }
        } catch (NullPointerException e) {

        }
    }

    private Callback callback = new Callback() {
        @Override
        public void success(Object result) {

        }

        @Override
        public void failed(String failed) {
            if (!TextUtils.isEmpty(failed)) {
                Toast.makeText(getActivity(), failed, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void getQuestion(QuestionEntity questionEntity) {
        if (questionEntity != null) {
            if (questionAdapter != null) {
                questionAdapter.appendQuestion(questionEntity);
            }
            if (questionLv != null) {
                questionLv.setSelection(questionLv.getBottom());
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
