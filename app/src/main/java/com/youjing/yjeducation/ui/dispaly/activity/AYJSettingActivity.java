package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.ui.actualize.activity.YJAboutAppActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJFeedBackActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMedalActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJPersonalActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJPersonalCenterLevelActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJUpdatePassword;
import com.youjing.yjeducation.ui.actualize.dialog.YJClearCacheDialog;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.SharedUtil;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.notification.IVNotificationListener;

import java.io.File;
import java.math.BigDecimal;

/**
 * user  秦伟宁
 * Date 2016/3/31
 * Time 17:50
 */
@VLayoutTag(R.layout.activity_setting)
public abstract class AYJSettingActivity extends BaseSwipeBackActivity implements IVClickDelegate {
    @VViewTag(R.id.re_about_app)
    private RelativeLayout mRe_about_app;
    @VViewTag(R.id.re_watch_video)
    private RelativeLayout mRe_watch_video;
    @VViewTag(R.id.re_download_video)
    private RelativeLayout mRe_download_video;
    @VViewTag(R.id.re_new_message)
    private RelativeLayout mRe_new_message;
    @VViewTag(R.id.re_wipe_cache)
    private RelativeLayout mRe_wipe_cache;
    @VViewTag(R.id.update_password_layout)
    private RelativeLayout mUpdate_password_layout;
    @VViewTag(R.id.re_login_out)
    private RelativeLayout mRe_login_out;
    @VViewTag(R.id.cb_new_message)
    private CheckBox mCb_new_message;
    @VViewTag(R.id.cb_watch_video)
    private CheckBox mCb_watch_video;
    @VViewTag(R.id.cb_download_video)
    private CheckBox mCb_download_video;
    @VViewTag(R.id.txt_wipe_cache)
    protected TextView mTxt_wipe_cache;
    @VViewTag(R.id.rl_send_score)
    private RelativeLayout mRl_send_score;
    @VViewTag(R.id.lay_answer_time_order)
    private RelativeLayout mLay_answer_time_order;
    @VViewTag(R.id.lay_question_note)
    private RelativeLayout mLay_question_note;


    private String cacheSize;

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "设置", true);
        getAppCache();
        SharedUtil.getPreference(this);

        mTxt_wipe_cache.setText(cacheSize);
        boolean canWatchVideo = SharedUtil.getBoolean(this, "watch_video", false);
        if (canWatchVideo == false) {
            mCb_watch_video.setChecked(false);
        } else {
            mCb_watch_video.setChecked(true);
        }

        boolean canDownLoadVideo = SharedUtil.getBoolean(this, "download_video", false);
        if (canDownLoadVideo == false) {
            mCb_download_video.setChecked(false);
        } else {
            mCb_download_video.setChecked(true);
        }

        boolean canMessage = SharedUtil.getBoolean(this, "new_message", false);
        if (canMessage == false) {
            mCb_new_message.setChecked(false);
        } else {
            mCb_new_message.setChecked(true);
        }

        getNotifyListener();
    }

    private void getNotifyListener() {
        addListener(YJNotifyTag.CLEAN_CACHE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                wipeCache();
            }

        });
    }

    @Override
    public void onClick(View view) {
        if (view == mRe_about_app) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJAboutAppActivity.class);
//            startActivity(AYJCalendarActivity.class);
        } else if (view == mRe_wipe_cache) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            YJClearCacheDialog yjClearCacheDialog = new YJClearCacheDialog();
            showDialog(yjClearCacheDialog);
        } else if (view == mRe_watch_video) {

            if (mCb_watch_video.isChecked()) {
                SharedUtil.setBoolean(this, "watch_video", false);
                StringUtils.Log("hujiachen", "unChecked=" + SharedUtil.getBoolean(this, "watch_video", false));
                mCb_watch_video.setChecked(false);
            } else {
                SharedUtil.setBoolean(this, "watch_video", true);
                StringUtils.Log("hujiachen", "checked=" + SharedUtil.getBoolean(this, "watch_video", false));
                mCb_watch_video.setChecked(true);
            }
        } else if (view == mCb_watch_video) {

            if (mCb_watch_video.isChecked()) {
                SharedUtil.setBoolean(this, "watch_video", false);
                StringUtils.Log("hujiachen", "unChecked=" + SharedUtil.getBoolean(this, "watch_video", false));
                mCb_watch_video.setChecked(false);
            } else {
                SharedUtil.setBoolean(this, "watch_video", true);
                StringUtils.Log("hujiachen", "checked=" + SharedUtil.getBoolean(this, "watch_video", false));
                mCb_watch_video.setChecked(true);
            }
        } else if (view == mRe_download_video) {

            if (mCb_download_video.isChecked()) {
                SharedUtil.setBoolean(this, "download_video", false);
                mCb_download_video.setChecked(false);
            } else {
                SharedUtil.setBoolean(this, "download_video", true);
                mCb_download_video.setChecked(true);
            }
        } else if (view == mCb_download_video) {

            if (mCb_download_video.isChecked()) {
                SharedUtil.setBoolean(this, "download_video", false);
                mCb_download_video.setChecked(false);
            } else {
                SharedUtil.setBoolean(this, "download_video", true);
                mCb_download_video.setChecked(true);
            }
        } else if (view == mRe_new_message) {
            if (mCb_new_message.isChecked()) {
                SharedUtil.setBoolean(this, "new_message", false);
                mCb_new_message.setChecked(false);
            } else {
                SharedUtil.setBoolean(this, "new_message", true);
                mCb_new_message.setChecked(true);
            }
        } else if (view == mCb_new_message) {
            if (mCb_new_message.isChecked()) {
                SharedUtil.setBoolean(this, "new_message", false);
                mCb_new_message.setChecked(false);
            } else {
                SharedUtil.setBoolean(this, "new_message", true);
                mCb_new_message.setChecked(true);
            }
        } else if (view == mRl_send_score) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            sendScore(this);
        } else if (view == mRe_login_out) {
            loginOut();
        } else if (view == mLay_answer_time_order) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJMedalActivity.class);
        } else if (view == mLay_question_note) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(YJPersonalCenterLevelActivity.class);
        } else if (view == mUpdate_password_layout) {
            startActivity(YJUpdatePassword.class);
        }


    }

    private void getAppCache() {
        try {
            cacheSize = getTotalCacheSize(getContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize / 2);
    }

    // 获取文件
    //Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
    //Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            //           return size + "Byte";
            return "0";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }


    protected abstract void wipeCache();

    protected abstract void loginOut();

    protected abstract void sendScore(Activity activity);
}
