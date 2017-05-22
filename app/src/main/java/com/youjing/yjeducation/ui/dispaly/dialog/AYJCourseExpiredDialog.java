package com.youjing.yjeducation.ui.dispaly.dialog;

import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * user  秦伟宁
 * Date 2016/5/15
 * Time 10:51
 */
@VLayoutTag(R.layout.dialog_course_expired)
public class AYJCourseExpiredDialog extends AVDialog implements IVClickDelegate {

    private String TAG  = "AYJCourseExpiredDialog";
    @VViewTag(R.id.btn_ok)
    private Button mBtn_ok;
    @VViewTag(R.id.txt_down)
    private TextView txt_down;

    private String mTxtInfo ;
    public static final VParamKey<String> TXT_INFO     = new VParamKey<>(null);
    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        mTxtInfo = getTransmitData(TXT_INFO);
        StringUtils.Log(TAG,"mTxtInfo="+mTxtInfo);
        txt_down.setText(mTxtInfo);

    }
    @Override
    public void onClick(View view) {
        if(view == mBtn_ok){
            this.close();
            if (!TextUtils.isEmpty(mTxtInfo)){
                if (mTxtInfo.equals("请续费后观看")){
                    notifyListener(YJNotifyTag.COURSE_CLOSE,false);
                }else if (mTxtInfo.equals("请购买其他课程")){
                    notifyListener(YJNotifyTag.COURSE_CLOSE,true);
                }
            }

        }

    }

}