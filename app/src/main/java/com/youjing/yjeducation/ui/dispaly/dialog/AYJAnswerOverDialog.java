package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.Button;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/6/24
 * Time 14:19
 */
@VLayoutTag(R.layout.dialog_answer_over)
public class AYJAnswerOverDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialog_cancel)
    private Button dialog_cancel;
    @VViewTag(R.id.dialog_ok)
    private Button dialog_ok;


    @Override
    protected void onLoadedView() {
        super.onLoadedView();
    }

    @Override
    public void onClick(View view) {
        if(view == dialog_cancel){
            notifyListener(YJNotifyTag.ANSWER_OVER,"");
            close();
        }else if (view == dialog_ok){
            close();
        }
    }
}
