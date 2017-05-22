package com.youjing.yjeducation.scan;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.model.YJCourseTypeModel;
import com.youjing.yjeducation.ui.actualize.activity.YJCourseListlActivity;
import com.youjing.yjeducation.util.ScanBannerUtils;
import com.youjing.yjeducation.util.StringUtils;
import com.zf.myzxing.camera.CameraManager;
import com.zf.myzxing.control.AmbientLightManager;
import com.zf.myzxing.control.BeepManager;
import com.zf.myzxing.decode.FinishListener;
import com.zf.myzxing.decode.InactivityTimer;
import com.zf.myzxing.view.ViewfinderView;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class CaptureActivity extends YJBaseActivity implements
        SurfaceHolder.Callback {
    private RelativeLayout btn_back;
    private Button btn_torch;
    private boolean isTorchOn = false;
    private CameraManager cameraManager;
    private CaptureActivityHandler handler;
    private Result savedResultToShow;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Collection<BarcodeFormat> decodeFormats;
    private Map<DecodeHintType, ?> decodeHints;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private BeepManager beepManager;
    private AmbientLightManager ambientLightManager;
    private Set<String> set = new HashSet<String>();
    private StringBuffer sb = new StringBuffer();
    private String TAG = "CaptureActivity";
    protected boolean mIsLogin = false;

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    private boolean isInApp = false;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    notifyListener(YJNotifyTag.MY_TASK, "");
                    break;
                }
                case 1: {
                    notifyListener(YJNotifyTag.MY_CARD, "");
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.capture);
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        btn_back = (RelativeLayout) findViewById(R.id.rl_back);
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CaptureActivity.this.finish();
            }
        });
        btn_torch = (Button) findViewById(R.id.btn_torch);

	/*	btn_torch.setOnClickListener(new OnClickListener() {
            @Override
			public void onClick(View v) {
				String str   = JsonFileUtils.readFileData(CaptureActivity.this, "scan").replace("[", "");
				String str1   = str.replace("]", "");
				Toast.makeText(CaptureActivity.this, str1, Toast.LENGTH_LONG).show();
			}
		});*/

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        beepManager = new BeepManager(this);
        ambientLightManager = new AmbientLightManager(this);
    }

    /**
     * 结果处理
     */
    public void handleDecode(Result rawResult, Bitmap barcode, float scaleFactor) {
        inactivityTimer.onActivity();
        beepManager.playBeepSoundAndVibrate();

        String msg = rawResult.getText();
        if (msg == null || "".equals(msg)) {
            showToast("无法识别");
            return;
        }
        if (StringUtils.isUrl(msg) == false) {
            showToast("无法识别的二维码");
            this.finish();
            return;
        }
        if (msg.contains("http://weixin.qq.com")){
            showToast("无法识别的二维码");
            this.finish();
            return;
        }

        StringUtils.Log(TAG, "msg=" + msg);
        ScanBannerUtils.intentPage(CaptureActivity.this,msg,TAG,mIsLogin,1);
        finish();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();

        cameraManager = new CameraManager(getApplication());

        viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
        viewfinderView.setCameraManager(cameraManager);

        handler = null;
        resetStatusView();

        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        beepManager.updatePrefs();
        ambientLightManager.start(cameraManager);

        inactivityTimer.onResume();
        decodeFormats = null;
        characterSet = null;
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        inactivityTimer.onPause();
        ambientLightManager.stop();
        cameraManager.closeDriver();
        if (!hasSurface) {
            SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            surfaceHolder.removeCallback(this);
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        viewfinderView.recycleLineDrawable();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_CAMERA:// 拦截相机键
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void decodeOrStoreSavedBitmap(Bitmap bitmap, Result result) {
        if (handler == null) {
            savedResultToShow = result;
        } else {
            if (result != null) {
                savedResultToShow = result;
            }
            if (savedResultToShow != null) {
                Message message = Message.obtain(handler,
                        com.zf.myzxing.R.id.decode_succeeded, savedResultToShow);
                handler.sendMessage(message);
            }
            savedResultToShow = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            return;
        }
        if (cameraManager.isOpen()) {
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            if (handler == null) {
                handler = new CaptureActivityHandler(this, decodeFormats, decodeHints, characterSet, cameraManager);
            }
            decodeOrStoreSavedBitmap(null, null);
        } catch (IOException ioe) {
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            displayFrameworkBugMessageAndExit();
        }
    }

    private void displayFrameworkBugMessageAndExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("警告");
        builder.setMessage("抱歉，相机出现问题，您可能需要重启设备");
        builder.setPositiveButton("确定", new FinishListener(this));
        builder.setOnCancelListener(new FinishListener(this));
        builder.show();
    }

    public void restartPreviewAfterDelay(long delayMS) {
        if (handler != null) {
            handler.sendEmptyMessageDelayed(com.zf.myzxing.R.id.restart_preview, delayMS);
        }
        resetStatusView();
    }

    private void resetStatusView() {
        viewfinderView.setVisibility(View.VISIBLE);
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private boolean isHasCourseTypeId = true;
    protected List<YJCourseTypeModel> yjCourseTypeModelList;



    private void saveGrade(String course, int num) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("course", course);
        editor.putInt("courseNum", num);
        editor.commit();
    }

}
