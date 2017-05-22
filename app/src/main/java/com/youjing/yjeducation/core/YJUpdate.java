package com.youjing.yjeducation.core;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.YJVersionInfoModel;
import com.youjing.yjeducation.ui.actualize.activity.YJGuideActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMainActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJUpdateDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJUpdateRecommendedDialog;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.StringUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.utils.base.VParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 16:55
 */
public class YJUpdate {
    private IVActivity mIVActivity;
    private boolean interceptFlag;
    private int progress;
    private boolean mShowToast;
    private boolean mIsAutoLogin;
    private boolean mIsLogin;
    private boolean mIsFirst;
    private static final int SHOW_PROGRESS = 0;
    private static final int DOWN_OVER = 1;
    public static final String TAG = "YJUpdate";
    String customerToken;
    String customerId;
    private YJBaseActivity baseActivity = new YJBaseActivity();

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHOW_PROGRESS:
                    YJUpdateDialog dialog = (YJUpdateDialog) msg.obj;
                    dialog.setProgressState(progress);
                    break;
                case DOWN_OVER:
                    YJUpdateDialog dialogOver = (YJUpdateDialog) msg.obj;
                    dialogOver.close();
                    installApp();
                    break;
                default:
                    break;
            }
        }
    };

    public YJUpdate(IVActivity IVActivity, boolean showToast, boolean isAutoLogin) {
        mIVActivity = IVActivity;
        mShowToast = showToast;
        mIsAutoLogin = isAutoLogin;
        StringUtils.Log(TAG, "showToast = " + showToast + " isAutoLogin = " + isAutoLogin);
    }

    public void UpdateApp(final boolean flag) {
        customerToken = PreferenceManager.getDefaultSharedPreferences(mIVActivity.getContext()).getString("customerToken", "");
        customerId = PreferenceManager.getDefaultSharedPreferences(mIVActivity.getContext()).getString("customerId", "");
        YJGlobal.setCustomertoken(customerToken);
        YJGlobal.setCustomerId(TextUtils.isEmpty(customerId) ? 0 : Integer.parseInt(customerId));
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(mIVActivity.getContext()).getBoolean("isLogin", false);
        if (TextUtils.isEmpty(customerId) || customerId.equals("0")) {
            mIsLogin = false;
            YJGlobal.setCustomertoken("");
            YJGlobal.setCustomerId(0);
            StringUtils.Log(TAG, "mIsLogin=false");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mIVActivity.getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isLogin", false);
            editor.putString("customerToken", "");
            editor.putString("customerId", "");

            editor.commit();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_ANDROID_VERSION, null, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "UpdateApp失败=" + s);
                mIVActivity.showToast(mIVActivity.getContext().getString(R.string.no_net_work));
                mIVActivity.finishAll();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject json = null;
                    json = new JSONObject(s);
                    StringUtils.Log(TAG, "成功s=" + s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            final YJVersionInfoModel yjVersionInfoModel = JSON.parseObject(jsonData.getString("nextVersion"), YJVersionInfoModel.class);
                            if (TextUtils.isEmpty(yjVersionInfoModel.getCompelUpdate())) {
                                autoLogin();
                            } else {
                                SharedUtil.setInteger(mIVActivity.getContext(), "baseIndex", 0);
                                YJUpdateRecommendedDialog.IOnCloseListener listener = new YJUpdateRecommendedDialog.IOnCloseListener() {
                                    @Override
                                    public void onClose() {
                                        String site = yjVersionInfoModel.getAddress();
                                        StringUtils.Log(TAG, "LOADING site = " + site);
                                        doNewVersionUpdate(mIVActivity, site, flag);
                                    }
                                };
                                switch (yjVersionInfoModel.getCompelUpdate()) {
                                    case "Yes":
                                        YJShowDialog.showDialogUpdate(mIVActivity, yjVersionInfoModel.getVersionDesc(), yjVersionInfoModel.getVersionNum(), 1, false, listener);
                                        break;
                                    case "No":
                                        YJShowDialog.showDialogUpdate(mIVActivity, yjVersionInfoModel.getVersionDesc(), yjVersionInfoModel.getVersionNum(), 1, true, listener);
                                        break;
                                }

                            }
                            break;
                        case 500:

                            break;
                        case 600:
                            mIVActivity.startActivity(mIVActivity.createIntent(YJLoginActivity.class, mIVActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            mIVActivity.finishAll();
                            break;
                        case 700:
                            StringUtils.Log(TAG, "获得700的值===" + json.getString("msg"));
                            break;
                        default:
                            break;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void autoLogin() {
        if (mIsAutoLogin) {

            mIsLogin = PreferenceManager.getDefaultSharedPreferences(mIVActivity.getContext()).getBoolean("isLogin", false);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean isFirst = PreferenceManager.getDefaultSharedPreferences(mIVActivity.getContext()).getBoolean("isFirst", true);
                    if (isFirst) {
                        mIVActivity.startActivity(YJGuideActivity.class);
                        mIVActivity.finish();
                        //getBannerNoLogin(true);
                    } else if (mIsLogin) {
                        customerToken = PreferenceManager.getDefaultSharedPreferences(mIVActivity.getContext()).getString("customerToken", "");
                        customerId = PreferenceManager.getDefaultSharedPreferences(mIVActivity.getContext()).getString("customerId", "");
                        YJGlobal.setCustomertoken(customerToken);
                        YJGlobal.setCustomerId(Integer.parseInt(customerId));
                        getAddCustomerActive();
                        mIVActivity.startActivity(YJMainActivity.class);
                        mIVActivity.finish();
                      //  getBanner(false);
                    } else {
                        mIVActivity.startActivity(mIVActivity.createIntent(YJLoginActivity.class, mIVActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 3).set(YJLoginActivity.MY_LOGIN_OUT, false)));
                    }
                }
            }, 1000);
        }
    }


    /**
     * stt
     * 创建时间：2016.5.16
     * 方法说明：统计用户活跃度
     */
    private void getAddCustomerActive() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_ADD_CUSTOMERACTIVE, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getAddCustomerActive失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "getAddCustomerActive成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "成功");
                            break;
                        case 300:
                            StringUtils.Log(TAG, "参数不合法");
                            break;
                        case 500:
                            StringUtils.Log(TAG, "服务器异常");
                            break;
                        case 600:
                            mIVActivity.startActivity(mIVActivity.createIntent(YJLoginActivity.class, mIVActivity.createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            mIVActivity.finishAll();
                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doNewVersionUpdate(final IVActivity activity, String site, final boolean flag) {
        interceptFlag = true;
        YJUpdateDialog.IWCloseUpdateListener listener = new YJUpdateDialog.IWCloseUpdateListener() {
            @Override
            public void onBtnCancelUpdate() {
                if (flag) {
                    // MobclickAgent.onKillProcess(activity.getContext());
                    activity.finish();
                    System.exit(0);
                    interceptFlag = false;
                } else {
                    interceptFlag = false;
                }
            }
        };
        VParams data = activity.createTransmitData(YJUpdateDialog.KEY_LISTENER, listener);
        final YJUpdateDialog dialog = new YJUpdateDialog();
        activity.showDialog(dialog, data);
        downFile(activity, site, dialog);
    }


    private void downFile(final IVActivity activity, final String url, final YJUpdateDialog dialog) {
        new Thread() {
            public void run() {
                try {
                    URL apkUrl = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) apkUrl.openConnection();
                    conn.connect();
                    int length = conn.getContentLength();
                    InputStream is = conn.getInputStream();
                    File file;
                    if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
                        file = new File(Environment.getExternalStorageDirectory() + "/youjing");
                    } else {
                        file = new File("/mnt/sdcard2/youjing");
                    }
                    if (!file.exists()) {
                        if (!file.mkdir()) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    activity.showToast(mIVActivity.getContext().getResources().getString(R.string.txt_update_file));
                                }
                            });
                            return;
                        }
                    }
                    File apkFile = new File(file, "youjing.apk");
                    if (!apkFile.exists()) {
                        apkFile.createNewFile();
                    }
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    byte buf[] = new byte[1024];
                    while (interceptFlag) {
                        int num_read = is.read(buf);
                        count += num_read;
                        progress = (int) (((float) count / length) * 100);
                        mHandler.obtainMessage(SHOW_PROGRESS, dialog).sendToTarget();
                        if (num_read <= 0) {
                            mHandler.obtainMessage(DOWN_OVER, dialog).sendToTarget();
                            break;
                        }
                        fos.write(buf, 0, num_read);
                        fos.flush();
                    }
                    fos.close();
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            StringUtils.Log(TAG, "load fail");
                            activity.showToast(mIVActivity.getContext().getResources().getString(R.string.txt_update_fail));
                        }
                    });
                }
            }
        }.start();
    }

    private void installApp() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            file = new File(Environment.getExternalStorageDirectory() + "/youjing/youjing.apk");
        } else {
            file = new File("/mnt/sdcard2/youjing/youjing.apk");
        }
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        mIVActivity.startActivity(intent);
    }

}
