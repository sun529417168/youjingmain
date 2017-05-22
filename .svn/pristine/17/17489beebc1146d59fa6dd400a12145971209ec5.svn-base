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
 * @author stt
 * 创建于2016.5.11
 * 说明：清楚缓存的dialog
 * 无返回值
 */
@VLayoutTag(R.layout.dialog_clearcache)
public abstract class AYJClearCacheDialog extends AVDialog implements IVClickDelegate {
    @VViewTag(R.id.dialog_clearcache_cancel)
    private Button mCancle;
    @VViewTag(R.id.dialog_clearcache_ok)
    private Button mOk;
    @Override
    public void onClick(View view) {
        if(view == mCancle){
           close();
        }else if(view == mOk){
//            clearAllCache(getContext());
            notifyListener(YJNotifyTag.CLEAN_CACHE,"");
            close();
        }
    }
}
