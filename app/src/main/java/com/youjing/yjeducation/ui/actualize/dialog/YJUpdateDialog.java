package com.youjing.yjeducation.ui.actualize.dialog;

import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.youjing.yjeducation.R;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.utils.base.VParamKey;

/**
 * user  秦伟宁
 * Date 2016/5/21
 * Time 13:41
 */
public class YJUpdateDialog  extends AVDialog
{
    public static final VParamKey<IWCloseUpdateListener> KEY_LISTENER = new VParamKey<>(null);
    private IWCloseUpdateListener mIWCloseUpdateListener;
    private ProgressBar mProgressBar;
    private Button mBtnUpdate;

    @Override
    protected void onLoadingView() {
        setContentView(R.layout.dialog_update);
    }

    @Override
    protected void onLoadedView()
    {
        mProgressBar = (ProgressBar) findViewById(R.id.pro_bar);
        mBtnUpdate = (Button) findViewById(R.id.btn_cancel_update);
        mIWCloseUpdateListener = getTransmitData(KEY_LISTENER);
        setCancelable(false);
        mBtnUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                mIWCloseUpdateListener.onBtnCancelUpdate();
                close();
            }
        });
    }

    public interface IWCloseUpdateListener
    {
        void onBtnCancelUpdate();
    }

    public void setProgressState(int progress)
    {
        mProgressBar.setProgress(progress);
    }
}
