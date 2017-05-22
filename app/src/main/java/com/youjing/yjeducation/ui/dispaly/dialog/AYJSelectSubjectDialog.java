package com.youjing.yjeducation.ui.dispaly.dialog;

import com.youjing.yjeducation.util.StringUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.util.TranslationUtils;
import com.youjing.yjeducation.wiget.PickerView;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.ArrayList;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_subject_select)
public abstract class AYJSelectSubjectDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.pv_subject)
    PickerView mPv_subject;
    @VViewTag(R.id.btn_ok)
    private Button mBtnOk;
    @VViewTag(R.id.btn_cancel)
    private Button mBtnCancel;

    public final int PRIMARY_SCHOOL_N = 0;

    private String TAG = "AYJSelectSubjectDialog";
    private YJUser mYjUser;
    private int length;
    private  List<String> subjectList;

    @Override
    public void onClick(View view) {
        if (view == mBtnCancel) {
            close();
        } else if (view == mBtnOk) {
            if (length == 1){
                onBtnOkClick(subjectList.get(0), Integer.parseInt(TranslationUtils.translationNameSubjectId(subjectList.get(0))));
            }else {
                onBtnOkClick(mPv_subject.getSelectedStr(), Integer.parseInt(TranslationUtils.translationNameSubjectId(mPv_subject.getSelectedStr())));
            }

//            close();
//            onBtnOkClick(String.valueOf(PDGlobal.getGradeList().indexOf(mPvGrade.getSelectedStr()) + 1));
        }
    }

    @Override
    protected void onLoadedView() {
        initData();
        mYjUser = YJGlobal.getYjUser();
        int position = TranslationUtils.searchNameByIdSubect(mYjUser.getGradeId());
        StringUtils.Log("hujiachen", "aaaa=" + Integer.parseInt(mYjUser.getGradeId()));
        StringUtils.Log("hujiachen", "aaaa=" + YJGlobal.getGradeList().get(position).getSubjectVos());
        StringUtils.Log("hujiachen", "aaaa=" + YJGlobal.getGradeList().get(position).getSubjectVos().get(0));

        length = YJGlobal.getGradeList().get(position).getSubjectVos().size();
        StringUtils.Log(TAG,"length="+length);
        subjectList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
//            subjectList.add(subject[i]);
            subjectList.add(YJGlobal.getGradeList().get(position).getSubjectVos().get(i).getSubjectName());
        }

        mPv_subject.setSelected(PRIMARY_SCHOOL_N);
        StringUtils.Log(TAG, "subjectList=" + subjectList.size());
        mPv_subject.setData(subjectList);
        if (subjectList.size() == 1) {
            mPv_subject.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        } else {
            mPv_subject.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
        }


    }

    private void initData() {
        //subject = getContext().getResources().getStringArray(R.array.subject);
        //subject=
        // YJGlobal.getGradeList().get(Integer.parseInt(mYjUser.getGradeId()));

    }

    protected abstract void onBtnOkClick(String gradeValue, int id);
}