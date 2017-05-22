package com.youjing.yjeducation.ui.dispaly.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVVirtualActivity;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:55
 */

@VLayoutTag(R.layout.activity_download_ok)
public  class AYJDownLoadOkActivity extends AVVirtualActivity implements IVClickDelegate,IVAdapterDelegate {
    @VViewTag(R.id.lv_course_download)
    private ListView mLv_course_download;

    private VAdapter adapter = null;

    @Override
    protected void onLoadedView() {

        if(adapter == null){
            adapter = new VAdapter(this, mLv_course_download);
            mLv_course_download.setAdapter(adapter);
        }else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJCourseDownLoadItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        return 10;
    }

    @VLayoutTag(R.layout.course_download_item)
    class YJCourseDownLoadItem extends AVAdapterItem {
        @VViewTag(R.id.lay_item)
        private RelativeLayout mLay_item;
        @VViewTag(R.id.view_line)
        private View mView_line;
        @VViewTag(R.id.img_course_teacher)
        private ImageView mIimg_course_teacher;
        @VViewTag(R.id.txt_name)
        private TextView mTxt_name;
        @VViewTag(R.id.txt_teacher_name)
        private TextView mTxt_teacher_name;
        @VViewTag(R.id.rb_select_download)
        private RadioButton rb_select_download;
        @Override
        public void update(int position) {
            rb_select_download.setVisibility(View.GONE);
            super.update(position);
        }


    }
}
