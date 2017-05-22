package com.youjing.yjeducation.ui.dispaly.activity;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJDownLoadNoActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJDownLoadOkActivity;

import org.vwork.mobile.ui.adapter.VPageViewAdapter;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.delegate.IVPageViewAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVPageViewChangeDelegate;
import org.vwork.mobile.ui.listener.VPageViewChangeListener;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.mobile.ui.widget.VPageView;

/**
 * user  秦伟宁
 * Date 2016/3/31
 * Time 20:13
 */
@VLayoutTag(R.layout.activity_down_load)
public class AYJDownLoadActivity extends YJBaseActivity implements IVPageViewAdapterDelegate, IVPageViewChangeDelegate, IVClickDelegate{

    @VViewTag(R.id.pager_download)
    private VPageView mPager_download;
    @VViewTag(R.id.txt_down_load_ok)
    private TextView mTxt_down_load_ok;
    @VViewTag(R.id.txt_down_load_no)
    private TextView mTxt_down_load_no;
    @VViewTag(R.id.re_down_load)
    private RelativeLayout mRe_down_load;

    @Override
    protected void onLoadedView() {
        VPageViewAdapter adapter = new VPageViewAdapter(this, mPager_download);
        VPageViewChangeListener listener = new VPageViewChangeListener(this, mPager_download);
        mPager_download.setAdapter(adapter);
        mPager_download.setChangeListener(listener);
        mPager_download.setCurrentItem(0);
    }

    @Override
    public void onClick(View view) {
        if (view == mTxt_down_load_ok){
            mPager_download.setCurrentItem(0);
            mRe_down_load.setBackgroundResource(R.mipmap.btn_down_no);
            mTxt_down_load_ok.setTextColor(getResources().getColor(R.color.blue_title));
            mTxt_down_load_no.setTextColor(getResources().getColor(R.color.white));
        }else if(view == mTxt_down_load_no){
            mPager_download.setCurrentItem(1);
            mRe_down_load.setBackgroundResource(R.mipmap.btn_down_ok);
            mTxt_down_load_ok.setTextColor(getResources().getColor(R.color.white));
            mTxt_down_load_no.setTextColor(getResources().getColor(R.color.blue_title));
        }
    }

    @Override
    public Fragment[] createPageViewFragments(VPageView vPageView) {
        return new Fragment[]{startVirtualActivity(new YJDownLoadOkActivity()), startVirtualActivity(new YJDownLoadNoActivity())};
    }

    @Override
    public void onPageViewChanged(VPageView vPageView, int i, int i1) {

    }
}
