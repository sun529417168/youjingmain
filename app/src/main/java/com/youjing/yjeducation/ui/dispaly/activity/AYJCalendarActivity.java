package com.youjing.yjeducation.ui.dispaly.activity;

import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.util.TimeUtil;

import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.Calendar;
import java.util.Date;

/**
 * user  秦伟宁
 * Date 2016/4/5
 * Time 15:40
 */
@VLayoutTag(R.layout.activity_calendar)
public class AYJCalendarActivity extends YJBaseActivity {
    @VViewTag(R.id.calendarView)
    private MaterialCalendarView calendarView;

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "日历", true);

        calendarView.setSelectionColor(getResources().getColor(R.color.yellow_text));
    }
}
