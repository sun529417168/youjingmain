package com.youjing.yjeducation.ui.dispaly.dialog;

import android.os.Handler;
import android.os.Message;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.Timer;
import java.util.TimerTask;

/**
 * user  秦伟宁
 * Date 2016/3/25
 * Time 10:54
 */
@VLayoutTag(R.layout.dialog_login_success)
public class AYJLoginSuccessDialog extends AVDialog implements IVClickDelegate {

    private  String TAG = "AYJLoginSuccessDialog";
    @VViewTag(R.id.txt_down)
    private TextView txt_down;

    private boolean isDestroy = true;

    private Handler mainHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if(isDestroy){
                        close();
                    }
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    public void onClick(View view) {

    }


    @Override
    protected void onLoadedView() {
        super.onLoadedView();

        StringUtils.Log(TAG, "AYJLoginSuccessDialog");
        txt_down.setText(YJGlobal.getSucessInfo());
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                if(isDestroy){
                    StringUtils.Log(TAG, "isDestroy true");
                    mainHandler.sendEmptyMessage(1);
                }else {
                    StringUtils.Log(TAG, "isDestroy false");
                }
                timer.cancel(); // also just top the timer thread, otherwise, you may receive a crash report
            }
        }, 800);

    }
    @Override
    public void onDestroy() {
        isDestroy = false;
        super.onDestroy();

    }

    @Override
    public void onClosed() {
        isDestroy = false;
        super.onClosed();
    }
}
