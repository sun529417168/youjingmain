package com.youjing.yjeducation.wiget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/14
 * Time 11:53
 */

public class SendGiftPopWindow extends PopupWindow implements View.OnClickListener {

    private Context context;
    private String TAG = "SendGiftPopWindow";

    private List list;
    private View  conentView;
    private ViewPager mViewpager;
    private View view1 ,view2;
    private List viewList = null;
    public SendGiftPopWindow(final Activity context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //conentView = inflater.inflate(R.layout.test_gridview, null);
        conentView = inflater.inflate(R.layout.activity_send_gift, null);
        mViewpager = (ViewPager)conentView.findViewById(R.id.viewpager);

        view1 = inflater.inflate(R.layout.viewpager_gift_item, null);



        view2 = inflater.inflate(R.layout.viewpager_gift_item, null);

        if(viewList  == null){
            viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        }else {
            viewList.clear();
        }
        viewList.add(view1);
        viewList.add(view2);
        GiftPagerAdapter mGiftPagerAdapter = new GiftPagerAdapter(viewList);
        mViewpager.setAdapter(mGiftPagerAdapter);


        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(00000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
       // this.setAnimationStyle(R.style.AnimationPreview);

        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.5f;
        context.getWindow().setAttributes(lp);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = context.getWindow().getAttributes();
                lp.alpha = 1f;
               context.getWindow().setAttributes(lp);
            }
        });


    }
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
          //  this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
            this.showAtLocation(parent, Gravity.NO_GRAVITY, YJConfig.mScreenWidth, YJConfig.mScreenHeight);
        } else {
            this.dismiss();
        }
    }
    @Override
    public void onClick(View view) {

    }
}