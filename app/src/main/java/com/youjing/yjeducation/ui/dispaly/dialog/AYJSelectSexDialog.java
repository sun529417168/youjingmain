package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.Button;

import com.youjing.yjeducation.R;
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
@VLayoutTag(R.layout.dialog_sex_select)
public abstract class AYJSelectSexDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.pv_subject)
    PickerView mPv_subject;
    @VViewTag(R.id.btn_ok)
    private Button mBtnOk;
    @VViewTag(R.id.btn_cancel)
    private Button mBtnCancel;

    public final int PRIMARY_SCHOOL_N = 0;
    private String[] sex;

    @Override
    public void onClick(View view)
    {
        if (view == mBtnCancel) {
            close();
        } else if (view == mBtnOk) {

            onBtnOkClick(sex[mPv_subject.getSelected()]);
        }
    }

    @Override
    protected void onLoadedView() {
        initData();
        List<String> subjectList = new ArrayList<>();
        for (int i = 0; i < sex.length; i++) {
            subjectList.add(sex[i]);
        }
        mPv_subject.setData(subjectList);
        mPv_subject.setSelected(PRIMARY_SCHOOL_N);
    }
    private void initData() {
        sex = getContext().getResources().getStringArray(R.array.sex);

    }

    protected abstract void onBtnOkClick(String sexValue);
}