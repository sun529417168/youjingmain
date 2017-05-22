package com.youjing.yjeducation.ui.dispaly.dialog;

import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.YJMedalModel;

import org.vwork.mobile.ui.AVDialog;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * @author stt
 *         创建于2015.5.14
 *         说明：清楚缓存的dialog
 *         无返回值
 */
@VLayoutTag(R.layout.dialog_medal_details)
public class AYJMedalDetailsDialog extends AVDialog implements IVClickDelegate{
    /**
     * 详情页的勋章图片
     */
    @VViewTag(R.id.medal_details_image)
    private ImageView mMedal_details_image;
    /**
     * 勋章描述
     */
    @VViewTag(R.id.medal_details_remark)
    private TextView mMedal_details_remark;
    /**
     * 勋章的获取方式
     */
    @VViewTag(R.id.medal_details_access)
    private TextView mMedal_details_access;
    /**
     * 以获取的人数
     */
    @VViewTag(R.id.medal_details_personnum)
    private TextView mMedal_details_personnum;
    @VViewTag(R.id.dialog_medal_details)
    private RelativeLayout mDialog_medal_details;

    public static final VParamKey<YJMedalModel> MEDAL_MODEL     = new VParamKey<YJMedalModel>(null);
    private YJMedalModel medalModel;

    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        medalModel = getTransmitData(MEDAL_MODEL);

//        setFinishOnTouchOutside(true);
//        this.setCancelable(true);// 设置点击屏幕Dialog消失
        if ("Yes".equals(medalModel.getIsGet())) {
            BitmapUtils.create(getContext()).display(mMedal_details_image,  medalModel.getIconUrl());
        } else if ("No".equals(medalModel.getIsGet())) {
            BitmapUtils.create(getContext()).display(mMedal_details_image,  medalModel.getNotGetIcon());
        }
        mMedal_details_remark.setText(medalModel.getName());
        mMedal_details_access.setText("获取方式："+medalModel.getRemark());
        mMedal_details_personnum.setText("已获取人数：" + medalModel.getOwnNumber() + "人");
        setButtonUseless();
    }
    public void setButtonUseless() {

        Field field;

        try {
            field = getContext().getClass().getDeclaredField("mAlert");
            field.setAccessible(true);

            Object obj = field.get(getContext());

            field = obj.getClass().getDeclaredField("mHandler");
            field.setAccessible(true);

            field.set(obj, new ButtonHandler(null));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClick(View view) {
        if(view == mDialog_medal_details){
            close();
        }
    }

    private static final class ButtonHandler extends Handler {
        // Button clicks have Message.what as the BUTTON{1,2,3} constant
        private static final int MSG_DISMISS_DIALOG = 1;

        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialog) {
            mDialog = new WeakReference<DialogInterface>(dialog);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case DialogInterface.BUTTON_POSITIVE:
                case DialogInterface.BUTTON_NEGATIVE:
                case DialogInterface.BUTTON_NEUTRAL:
                    ((DialogInterface.OnClickListener) msg.obj).onClick(
                            mDialog.get(), msg.what);
                    break;

                // 下面这两句导致dialog消失
                 case MSG_DISMISS_DIALOG:
                 ((DialogInterface) msg.obj).dismiss();
            }
        }
    }
}
