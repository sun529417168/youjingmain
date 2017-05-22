package com.youjing.yjeducation.ui.dispaly.dialog;

import com.youjing.yjeducation.util.StringUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


import com.youjing.yjeducation.R;
import com.youjing.yjeducation.wiget.PickerView;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_birthday_select)
public abstract class AYJSelectBirthdayDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.pv_year)
    protected PickerView mPvYear;
    @VViewTag(R.id.pv_month)
    protected PickerView mPvMonth;
    @VViewTag(R.id.pv_day)
    protected PickerView mPvDay;
    @VViewTag(R.id.btn_ok)
    private Button mBtnOk;
    @VViewTag(R.id.btn_cancel)
    private Button mBtnCancel;

    final private List<String> mYears = new ArrayList<>();
    final private List<String> mMonths = new ArrayList<>();
    final private List<String> mDays = new ArrayList<>();
    private Calendar mCal;
    private int mCurrentYear;
    private int mCurrentMonth;
    private int mCurrentDay;
    private int mBeginYear = 1960;

    @Override
    public void onClick(View view) {
        if (view == mBtnCancel) {
            close();
        } else if (view == mBtnOk) {
             onBtnOkClick(mPvYear.getSelectedStr(), mPvMonth.getSelectedStr(), mPvDay.getSelectedStr());
        }
    }

    @Override
    protected void onLoadedView() {
        initData();

        mPvYear.setData(mYears);
        mPvYear.setSelected(mYears.indexOf(String.valueOf(mCurrentYear - 10 + "年")));
        mPvYear.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text, int index) {
                List<String> mMonthsNew = new ArrayList<>();
                if (mCurrentYear == (mBeginYear + index)) {
                    for (int i = 1; i <= mCurrentMonth; i++) {
                        mMonthsNew.add(i + "月");
                    }
                    mPvMonth.setData(mMonthsNew);
                    if (mCurrentMonth == mPvMonth.getSelected() + 1 || 1 == mPvMonth.getSelected()) {
                        mPvDay.setData(getDayList(true, mPvMonth.getSelected()));
                    }

                } else if (mCurrentYear > (mBeginYear + index)) {
                    for (int i = 1; i <= 12; i++) {
                        mMonthsNew.add(i + "月");
                    }

                        mPvMonth.setData(mMonthsNew);

                   // mPvDay.setData(getDayList(false, index));
                }
                StringUtils.Log("DateDialog--Year", text + index);
                StringUtils.Log("DateDialog--Year", "mCurrentYear=" + mCurrentYear + ":::::::mPvYear.getSelected()=" + mPvYear.getSelected());
            }
        });


        mPvMonth.setData(mMonths);
        mPvMonth.setSelected(mMonths.indexOf(String.valueOf(2 + "月")));
        mPvMonth.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text, int index) {
                StringUtils.Log("mCurrentYear","mCurrentYear="+mCurrentYear+":::mPvYear.getSelected()="+mPvYear.getSelected() );
                if (mCurrentMonth == mPvMonth.getSelected() + 1 && mCurrentYear == mPvYear.getSelected()+mBeginYear) {
                    mPvDay.setData(getDayList(true, index));
                } else {
                        mPvDay.setData(getDayList(false, index));

                }
                StringUtils.Log("DateDialog-Month", text + index);
            }
        });

        mPvDay.setData(mDays);
        mPvDay.setSelected(mDays.indexOf(String.valueOf(2 + "日")));
        mPvDay.setOnSelectListener(new PickerView.onSelectListener() {

            @Override
            public void onSelect(String text, int index) {
                StringUtils.Log("DateDialog--Day", text + index);
            }
        });
    }

    private List<String> getDayList(boolean flag, int selectMonth) {
        List<String> mDaysNew = new ArrayList<>();

        if (flag && mCurrentMonth == selectMonth + 1) {
            for (int i = 1; i <= mCurrentDay; i++) {
                mDaysNew.add(i + "日");
            }
            if(mCurrentDay == 1){
                mPvDay.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
            }
        } else {
            for (int i = 1; i <= 28; i++) {
                mDaysNew.add(i + "日");
            }
            StringUtils.Log("DateDialog--Day", "selectMonth="+selectMonth);
            // 2月份需要判断是否为闰月
            if (1 == selectMonth) {
                if (isLeapYear(mBeginYear + mPvYear.getSelected())) {
                    mDaysNew.add("29日");
                }
            } else if (3 == selectMonth || 5 == selectMonth || 8 == selectMonth || 10 == selectMonth) {
                mDaysNew.add("29日");
                mDaysNew.add("30日");
            } else {
                mDaysNew.add("29日");
                mDaysNew.add("30日");
                mDaysNew.add("31日");
            }
        }
        return mDaysNew;
    }

    private void initData() {
        mCal = Calendar.getInstance();
        mCurrentYear = mCal.get(Calendar.YEAR);
        mCurrentMonth = mCal.get(Calendar.MONTH) + 1;
        mCurrentDay = mCal.get(Calendar.DAY_OF_MONTH);

        for (int i = mBeginYear; i <= mCurrentYear; i++) {
            mYears.add(i + "年");
        }
        for (int i = 1; i <= 12; i++) {
            mMonths.add(i + "月");
        }
        for (int i = 1; i <= 31; i++) {
            mDays.add(i + "日");
        }
    }

    /**
     * 判断是否为闰年
     *
     * @return 是否为闰年
     */
    private boolean isLeapYear(int year) {
        if (0 == year % 100) {
            if (0 == year % 400) {
                return true;
            }
        } else if (0 == year % 4) {
            return true;
        }
        return false;
    }

    protected abstract void onBtnOkClick(String year, String month, String day);
}
