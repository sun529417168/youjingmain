package com.youjing.yjeducation.ui.dispaly.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.util.ClickUtil;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/4/5
 * Time 14:39
 */
@VLayoutTag(R.layout.activity_feed_back)
public abstract class AYJFeedBackActivity extends BaseSwipeBackActivity implements IVClickDelegate {

    private String TAG="AYJFeedBackActivity";

    @VViewTag(R.id.btn_send_back)
    private Button mBtn_send_back;
    @VViewTag(R.id.ed_content)
    private EditText mEd_content;
    @VViewTag(R.id.ed_mail)
    private EditText mEd_mail;
    @VViewTag(R.id.txt_font_count)
    private TextView mTxt_font_count;

    private YJUser mYjUser;

    private String content;
    private String email;

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "帮助与反馈", true);

        mEd_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (null != mTxt_font_count) {
                    mTxt_font_count.setText((start+1) + "/1000");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        content = mEd_content.getText().toString();
        email = mEd_mail.getText().toString().trim();

        if (view == mBtn_send_back) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            sendBack(content, email);
        }
    }


    public abstract void sendBack(String content, String email);
}
