package com.youjing.yjeducation.ui.dispaly.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.mobile.ui.utils.VViewsTag;
import org.vwork.utils.base.VParamKey;

/**
 * user  秦伟宁
 * Date 2016/6/16
 * Time 11:12
 */
@VLayoutTag(R.layout.dialog_live_evaluation_over)
public class AYJEvaluationOverDialog extends AVDialog implements IVClickDelegate {

    @VViewTag(R.id.img_delete)
    private RelativeLayout img_delete;
    @VViewTag(R.id.re_gray_bg)
    private RelativeLayout re_gray_bg;

    @VViewsTag({R.id.btn_start1, R.id.btn_start2, R.id.btn_start3, R.id.btn_start4, R.id.btn_start5})
    private ImageButton[] mBtnStarts;

    @VViewTag(R.id.ed_comment)
    private TextView ed_comment;

    public static final VParamKey<String> SCOREREMARK   = new VParamKey<>(null);
    public static final VParamKey<String> SCORE   = new VParamKey<>(null);


    private String mScore;
    private String mScoreremark;
    @Override
    protected void onLoadedView() {
        mScore = getTransmitData(SCORE);
        mScoreremark = getTransmitData(SCOREREMARK);

        if (!TextUtils.isEmpty(mScoreremark)){
            ed_comment.setText(mScoreremark);
        }
        evaluation(Float.parseFloat(mScore));
    }

    @Override
    public void onClick(View view) {
        if(view == img_delete) {
            this.close();
        }else if(view == re_gray_bg){
            this.close();
        }
    }

    private void evaluation(float position) {
        for(int i = 0; i < 5; i++) {
            mBtnStarts[i].setImageResource(R.mipmap.img_start_no_comment);
            if(i < position) {
                mBtnStarts[i].setImageResource(R.mipmap.img_start_comment);
            }
        }
    }
}
