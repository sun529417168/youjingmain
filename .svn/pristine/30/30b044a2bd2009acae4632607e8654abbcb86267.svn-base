package com.youjing.yjeducation.ui.dispaly.dialog;

import android.view.View;
import android.widget.Button;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_down_load)
public class AYJDownLoadDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.btn_cancel)
    private Button mBtn_cancel;
    @VViewTag(R.id.btn_ok)
    private Button mBtn_ok;
    @Override
    public void onClick(View view) {
            if(view == mBtn_cancel){
                this.close();
            }else if(view == mBtn_ok){
                showToast("确认下载");
                this.close();
        }

    }

}
