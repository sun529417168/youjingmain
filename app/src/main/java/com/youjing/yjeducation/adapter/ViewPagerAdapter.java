package com.youjing.yjeducation.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author stt
 *         创建时间：2016.6.12
 */
public class ViewPagerAdapter extends PagerAdapter {
    private List<View> list;
    View.OnClickListener onClickListener;
    public ViewPagerAdapter(List<View> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(list.get(position));
        return list.get(position);
    }




    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
