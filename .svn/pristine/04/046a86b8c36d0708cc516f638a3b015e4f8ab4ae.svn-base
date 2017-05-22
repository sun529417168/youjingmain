package com.youjing.yjeducation.ui.dispaly.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.ui.actualize.dialog.YJEvaluationOverDialog;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.mobile.ui.utils.VViewsTag;

import static android.widget.TextView.*;

/**
 * user  秦伟宁
 * Date 2016/6/16
 * Time 11:12
 */
@VLayoutTag(R.layout.dialog_live_evaluation)
public class AYJEvaluationDialog extends AVDialog implements IVClickDelegate {

    @VViewTag(R.id.img_delete)
    private RelativeLayout img_delete;
    @VViewTag(R.id.re_gray_bg)
    private RelativeLayout re_gray_bg;

    @VViewsTag({R.id.btn_start1, R.id.btn_start2, R.id.btn_start3, R.id.btn_start4, R.id.btn_start5})
    private ImageButton[] mBtnStarts;

    @VViewTag(R.id.ed_comment)
    private EditText ed_comment;
    @VViewTag(R.id.btn_comment)
    private Button btn_comment;

    private int mPosition = 0;


    @Override
    protected void onLoadedView() {
        ed_comment.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }

        });
    }

    @Override
    public void onClick(View view) {
        if (view == img_delete) {
            this.close();
        } else if (view == re_gray_bg) {
            //this.close();
        } else if (view == btn_comment) {
            courseScore();
        } else if (view == mBtnStarts[0]) {
            evaluation(1);
        } else if (view == mBtnStarts[1]) {
            evaluation(2);
        } else if (view == mBtnStarts[2]) {
            evaluation(3);
        } else if (view == mBtnStarts[3]) {
            evaluation(4);
        } else if (view == mBtnStarts[4]) {
            evaluation(5);
        }
    }

    private String mComment;

    private void evaluation(int position) {
        for (int i = 0; i < 5; i++) {
            mBtnStarts[i].setImageResource(R.mipmap.img_start_no_comment);
            if (i < position) {
                mBtnStarts[i].setImageResource(R.mipmap.img_start_comment);
            }
        }
        switch (position) {
            case 1:
                mPosition = 1;
                ed_comment.setHint("很差");
                break;
            case 2:
                mPosition = 2;
                ed_comment.setHint("差");
                break;
            case 3:
                mPosition = 3;
                ed_comment.setHint("一般");
                break;
            case 4:
                mPosition = 4;
                ed_comment.setHint("很满意");
                break;
            case 5:
                mPosition = 5;
                ed_comment.setHint("非常满意");
                break;
        }

    }

    private void courseScore() {

        if (mPosition == 0) {
            showToast("请评价");
        } else {
            mComment = ed_comment.getText().toString().trim();
            if (TextUtils.isEmpty(mComment)) {
                mComment = ed_comment.getHint().toString().trim();
            }
            notifyListener(YJNotifyTag.COURSE_SCORE, mPosition + "_" + mComment);
            close();
        }
    }


}
