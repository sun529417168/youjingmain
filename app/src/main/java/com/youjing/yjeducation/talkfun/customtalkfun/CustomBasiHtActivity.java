package com.youjing.yjeducation.talkfun.customtalkfun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.PowerManager;
import com.youjing.yjeducation.util.StringUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.talkfun.sdk.HtSdk;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.talkfun.BaseActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJLiveExitDialog;
import com.youjing.yjeducation.util.DimensionUtils;
import com.youjing.yjeducation.util.StringUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;

/**
 * user  秦伟宁
 * Date 2016/6/4
 * Time 10:15
 */
public abstract class CustomBasiHtActivity extends BaseActivity implements View.OnTouchListener {
    private ScheduledExecutorService lance;
    protected boolean isTitleBarShow = false;
    public PowerManager.WakeLock wakeLock;
    private float xCoordinate = 0;
    private float yCoordinate = 0;
    public int deviceWidth; //设备宽度
    public int deviceHeight;//设备高度
    @Bind(R.id.ppt_container)
    RelativeLayout pptContainer;
    @Bind(R.id.video_container)
    FrameLayout videoViewContainer;
    @Bind(R.id.play_container)
    LinearLayout linearContainer;
    @Bind(R.id.tab_container)
    LinearLayout tab_container;


    private String TAG = "CustomBasiHtActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PowerManager powerManager = (PowerManager) this.getSystemService(this.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "ModeTwoActivity");
    }

    @Override
    protected void onResume() {
        super.onResume();
        wakeLock.acquire();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (wakeLock != null && wakeLock.isHeld())
            wakeLock.release();
    }

    /**
     * 横竖屏切换
     */
    public void onChangeOrient() {
        StringUtils.Log("onChangeOrient", "onChangeOrient");
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    public static boolean isShowDialog = true;

    /**
     * 返回
     *
     * @return
     */
    public void gobackAction() {
        YJLiveExitDialog yjLiveExitDialog = new YJLiveExitDialog();

//        if(isShowDialog){
//            showDialog(yjLiveExitDialog);
//            isShowDialog = false;
//        }
        showDialog(yjLiveExitDialog);
      /*  if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            showExitDialog();
        }*/
    }

    /**
     * 显示退出对话框
     */
    private AlertDialog exitDialog;

    private void showExitDialog() {
        if (exitDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.exit);
            builder.setTitle(R.string.tips);
            builder.setPositiveButton((R.string.confirm), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    finish();
                }
            });
            builder.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            exitDialog = builder.create();
        }
        exitDialog.show();
    }


    /**
     * 显示加载失败对话框
     */
    public void showConnectFailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.not_connect);
        builder.setTitle(R.string.tips);
        builder.setPositiveButton((R.string.goback), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                reclaim();
            }
        });
        builder.setNegativeButton((R.string.refresh), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                HtSdk.getInstance().reload();
            }
        });
        AlertDialog loadFailDialog = builder.create();
        loadFailDialog.setCancelable(false);
        loadFailDialog.show();
    }

    abstract void reclaim();

    /**
     * 显示标题栏和操作按钮
     */
    protected final void showTitleBar() {
        if (lance != null && !lance.isShutdown())
            lance.shutdown();
        show();
        isTitleBarShow = true;
        //  autoDismiss();
    }

    //标题栏计时器. 3秒后自动隐藏
    private void autoDismiss() {
        Runnable sendBeatRunnable = new Runnable() {
            @Override
            public void run() {
                if (isTitleBarShow) {
                    if (lance != null && !lance.isShutdown()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (isTitleBarShow) {
                                    hideTitleBar();
                                }
                                lance.shutdown();
                            }
                        });
                    }
                }
            }
        };
        lance = Executors.newSingleThreadScheduledExecutor();
        lance.scheduleAtFixedRate(sendBeatRunnable, 3, 3, TimeUnit.SECONDS);
    }

    /**
     * 隐藏标题栏和操作按钮
     */
    protected final void hideTitleBar() {
        if (lance != null && !lance.isShutdown())
            lance.shutdown();
        hide();
        isTitleBarShow = false;
    }

    abstract void show();

    abstract void hide();

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        HtSdk.getInstance().onConfigurationChanged();

        /**
         * 横竖屏切换更新布局
         * */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearContainer.setOrientation(LinearLayout.HORIZONTAL);
            StringUtils.Log(TAG, "ORIENTATION_LANDSCAPE");
            updateLayout();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            linearContainer.setOrientation(LinearLayout.VERTICAL);
            StringUtils.Log(TAG, "ORIENTATION_PORTRAIT");
            updateLayout();
        }
    }

    /**
     * 更新布局
     */
    public void updateLayout() {
        int width = DimensionUtils.getScreenWidth(this);
        int height = DimensionUtils.getScreenHeight(this);
        //获取宽高
        int videoContainerX = 0;
        int videoContainerY = 0;
        int orientation = linearContainer.getOrientation();
        LinearLayout.LayoutParams tablp = (LinearLayout.LayoutParams) tab_container.getLayoutParams();
        if (orientation == LinearLayout.VERTICAL) {
            height = 3 * width / 4;
            tablp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            tablp.height = 0;
            tablp.weight = 1.0f;
            tab_container.setLayoutParams(tablp);
            videoContainerX = width - videoViewContainer.getLayoutParams().width;
            videoContainerY = height + getVideoYOffset();
        } else {
            width *= 1;
            tablp.width = 0;
            tablp.height = ViewGroup.LayoutParams.MATCH_PARENT;
            tablp.weight = 1.0f;
            tab_container.setLayoutParams(tablp);
            videoContainerX = width - videoViewContainer.getLayoutParams().width;
            videoContainerY = 0;
        }
        LinearLayout.LayoutParams flp = (LinearLayout.LayoutParams) pptContainer.getLayoutParams();
        flp.width = width;
        flp.height = height;
        pptContainer.setLayoutParams(flp);

        setViewXY(videoViewContainer, videoContainerX, videoContainerY);
    }

    /**
     * 视频容器的偏移量
     */
    public int getVideoYOffset() {
        return 0;
    }

    /**
     * 设置视频容器的位置
     */
    public void setViewXY(View target, int x, int y) {
        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(target.getLayoutParams());
//        margin.setMargins(x, y, x + margin.width, y + margin.height);
        margin.leftMargin = x;
        margin.topMargin = y;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(margin);
        target.setLayoutParams(layoutParams);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getRawX();
        float y = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xCoordinate = event.getX();
                yCoordinate = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                updateLayoutCoordinate((int) (x - xCoordinate), (int) (y - yCoordinate));
                break;
            case MotionEvent.ACTION_UP:
                updateLayoutCoordinate((int) (x - xCoordinate), (int) (y - yCoordinate));
                xCoordinate = 0;
                yCoordinate = 0;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 更新视频位置
     *
     * @param x
     * @param y
     */
    private void updateLayoutCoordinate(int x, int y) {
//        更新浮动窗口位置参数
        int orientation = getRequestedOrientation();
        int w, h;
        w = videoViewContainer.getWidth();
        h = videoViewContainer.getHeight();
        int width, height;
        //获取宽高
        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || orientation == -1) {
            width = deviceWidth;
            height = deviceHeight;
            StringUtils.Log(TAG, "1111111111");
        } else {
            StringUtils.Log(TAG, "2222222222");
            width = deviceWidth;
            height = deviceHeight;
        }
        //计算x, y 的值
        if (x <= 0) {
            x = 0;
        } else if (x >= width - w) {
            x = width - w;
        }
        if (y <= 0) {
            y = 0;
        } else if (y >= height - h) {
            y = height - h;
        }
        setViewXY(videoViewContainer, x, y);
    }

}