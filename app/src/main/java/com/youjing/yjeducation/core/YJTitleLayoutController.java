package com.youjing.yjeducation.core;

import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.IVActivity;

/**
 * user  秦伟宁
 * Date 2016/3/4
 * Time 10:43
 */
public class YJTitleLayoutController {
    public static void initTitle(final IVActivity act, String title, boolean showBack) {
        TextView textView = (TextView) act.findViewById(R.id.txt_title);
        textView.setText(title);
        if (showBack) {
            ImageButton back = (ImageButton) act.findViewById(R.id.img_back);
            back.setImageResource(R.drawable.title_back);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    act.finish();
                }
            });
            back.setVisibility(View.VISIBLE);
        }

    }

    public static void initTitleBuleBg(final IVActivity act, String title, boolean showBack) {
        TextView textView = (TextView) act.findViewById(R.id.txt_title);
        RelativeLayout back = (RelativeLayout) act.findViewById(R.id.rl_back);
        final ImageButton backImage = (ImageButton) act.findViewById(R.id.img_back);
        textView.setText(title);
        if (showBack) {
//            backImage.setImageResource(R.drawable.return_back_image);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    act.finish();
                }
            });
            back.setVisibility(View.VISIBLE);
        }
        back.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    backImage.setBackgroundResource(R.mipmap.title_return_down);
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    backImage.setBackgroundResource(R.mipmap.title_return);
                }
                return false;
            }
        });
    }
}
