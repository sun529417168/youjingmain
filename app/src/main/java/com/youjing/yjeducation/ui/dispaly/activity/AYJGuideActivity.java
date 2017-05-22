package com.youjing.yjeducation.ui.dispaly.activity;

import com.youjing.yjeducation.util.StringUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;

/**
 * user  秦伟宁
 * Date 2016/3/11
 * Time 11:43
 */
public abstract class AYJGuideActivity extends YJBaseActivity {

    private String TAG = "AYJGuideActivity";
    ViewFlipper mViewFlipper = null;
    private Button mBtnExperience;
    private ImageView mImgFocus1;
    private ImageView mImgFocus2;
    private ImageView mImgFocus3;

    float startX;

    @Override
    protected void onLoadingView() {
        setContentView(R.layout.guide);

        init();
    }

    private void init() {
        mViewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
        mBtnExperience = (Button) findViewById(R.id.btn_experience);
        mImgFocus1 = (ImageView) findViewById(R.id.img_focus1);
        //mImgFocus2 = (ImageView) findViewById(R.id.img_focus2);
        mImgFocus3 = (ImageView) findViewById(R.id.img_focus3);
        mImgFocus1.setEnabled(false);
       // mImgFocus2.setEnabled(true);
        mImgFocus3.setEnabled(true);
        mBtnExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               start();
            }
        });
    }

    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_UP:

                if (event.getX() > startX) {
                  /*  if (mViewFlipper.getDisplayedChild() == mViewFlipper.getChildCount() - 3) {
                        break;
                    }*/
                    if (mViewFlipper.getDisplayedChild() == mViewFlipper.getChildCount() - 2) {
                        break;
                    }
                    mViewFlipper.setInAnimation(this, R.anim.right_in);
                    mViewFlipper.setOutAnimation(this, R.anim.right_out);
                    mViewFlipper.showNext();
                    change(mViewFlipper.getDisplayedChild());
                } else if (event.getX() < startX) {

                    StringUtils.Log(TAG,"mViewFlipper.getDisplayedChild()="+mViewFlipper.getDisplayedChild());
                    StringUtils.Log(TAG,"mViewFlipper.getChildCount()="+mViewFlipper.getChildCount());
                    if (mViewFlipper.getDisplayedChild()+1 == mViewFlipper.getChildCount()) {
                        start();
                    }
                  /*  if (mViewFlipper.getDisplayedChild()+2 == mViewFlipper.getChildCount()) {
                        start();
                    }*/
                    mViewFlipper.setInAnimation(this, R.anim.left_in);
                    mViewFlipper.setOutAnimation(this, R.anim.left_out);
                    mViewFlipper.showPrevious();
                    change(mViewFlipper.getDisplayedChild());

                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private void change(int position) {
        switch (position) {
            case 0:
                mImgFocus1.setEnabled(false);
             //   mImgFocus2.setEnabled(true);
                mImgFocus3.setEnabled(true);
                mImgFocus1.setVisibility(View.VISIBLE);
               // mImgFocus2.setVisibility(View.VISIBLE);
                mImgFocus3.setVisibility(View.VISIBLE);
                break;
       /*     case 1:
                mImgFocus1.setEnabled(true);
                mImgFocus2.setEnabled(true);
                mImgFocus3.setEnabled(false);
                mImgFocus1.setVisibility(View.VISIBLE);
                mImgFocus2.setVisibility(View.VISIBLE);
                mImgFocus3.setVisibility(View.VISIBLE);
                break;*/
            case 1:
                mImgFocus1.setEnabled(true);
             //   mImgFocus2.setEnabled(false);
                mImgFocus3.setEnabled(false);
                mImgFocus1.setVisibility(View.VISIBLE);
            //    mImgFocus2.setVisibility(View.VISIBLE);
                mImgFocus3.setVisibility(View.VISIBLE);
        }
    }

    protected abstract void start();
}
