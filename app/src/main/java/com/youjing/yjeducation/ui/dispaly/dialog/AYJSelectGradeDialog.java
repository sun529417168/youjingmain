package com.youjing.yjeducation.ui.dispaly.dialog;

import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.Button;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
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
@VLayoutTag(R.layout.dialog_grade_select)
public abstract class AYJSelectGradeDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.pv_period)
    PickerView mPvPeriod;
//    @VViewTag(R.id.pv_grade)
//    PickerView mPvGrade;
    @VViewTag(R.id.btn_ok)
    private Button mBtnOk;
    @VViewTag(R.id.btn_cancel)
    private Button mBtnCancel;

    public final int PRIMARY_SCHOOL_N = 0;
    public final int MIDDLE_SCHOOL_N  = 1;
    public final int HIGH_SCHOOL_N    = 2;
    private String[] primaryList;
    private String[] middleList;
    private String[] highList;

    private String TAG="AYJSelectGradeDialog";

    @Override
    public void onClick(View view)
    {
        if (view == mBtnCancel)
        {
            close();
        } else if (view == mBtnOk)
        {
            onBtnOkClick(mPvPeriod.getSelectedStr(), Integer.parseInt(TranslationUtils.translationNameGradeId(mPvPeriod.getSelectedStr())));
//            onBtnOkClick(String.valueOf(PDGlobal.getGradeList().indexOf(mPvGrade.getSelectedStr()) + 1));
        }
    }

    @Override
    protected void onLoadedView()
    {
        initData();
//        final String[][] gradeStrs = new String[][]{primaryList, middleList, highList};

        final List<String> periods = new ArrayList<>();
//        String[] periodStrs = getContext().getResources().getStringArray(R.array.period);

        int length=YJGlobal.getGradeList().size();
        StringUtils.Log(TAG,length+"=length");
        for (int i=0;i<length;i++){
            StringUtils.Log(TAG,YJGlobal.getGradeList().get(i).getGradeName()+":"+i);
            periods.add(YJGlobal.getGradeList().get(i).getGradeName());
        }
//        for (int i = 0; i < periodStrs.length; i++)
//        {
//            periods.add(periodStrs[i]);
//        }
//
        mPvPeriod.setData(periods);
        mPvPeriod.setSelected(PRIMARY_SCHOOL_N);
//        mPvPeriod.setOnSelectListener(new PickerView.onSelectListener()
//        {
//            @Override
//            public void onSelect(String text, int index)
//            {
//                List<String> grades2 = new ArrayList<String>();
//                for (int i = 0; i < gradeStrs[index].length; i++)
//                {
//                    grades2.add(gradeStrs[index][i]);
//                }
//                mPvGrade.setData(grades2);
//            }
//        });
//
//        List<String> grades = new ArrayList<>();
//        for (int i = 0; i < gradeStrs[PRIMARY_SCHOOL_N].length; i++)
//        {
//            grades.add(gradeStrs[PRIMARY_SCHOOL_N][i]);
//        }
//        mPvGrade.setData(grades);
//        mPvGrade.setSelected(PRIMARY_SCHOOL_N);
    }

    private void initData()
    {
        primaryList = getContext().getResources().getStringArray(R.array.primary_school_grade);
        middleList = getContext().getResources().getStringArray(R.array.middle_school_grade);
        highList = getContext().getResources().getStringArray(R.array.high_school_grade);
    }

    protected abstract void onBtnOkClick(String gradeValue,int gradeId);
}