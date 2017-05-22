package com.youjing.yjeducation.talkfun;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.talkfun.sdk.event.OnVideoStatusChangeListener;
import com.talkfun.sdk.module.AlbumItemEntity;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.talkfun.plackback.PlaybackChatFragment;
import com.youjing.yjeducation.talkfun.plackback.PlaybackQuestionFragment;
import com.youjing.yjeducation.talkfun.plackback.PlaybackSectionFragment;
import com.youjing.yjeducation.util.DimensionUtils;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.talkfun.sdk.HtSdk;
import com.talkfun.sdk.consts.MtConsts;
import com.talkfun.sdk.data.PlaybackDataManage;
import com.talkfun.sdk.event.PlaybackListener;
import com.talkfun.sdk.module.PlaybackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * user  秦伟宁
 * Date 2016/4/28
 * Time 16:40
 */
public  class YJPlaybackModeTwo extends BasicHtActivity implements ViewPager.OnPageChangeListener,
        PlaybackListener, PlaybackSectionFragment.PlaybackSectionInterface, View.OnTouchListener,OnVideoStatusChangeListener {
    private String token;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.ppt_container)
    RelativeLayout pptContainer;
    @Bind(R.id.video_container)
    FrameLayout videoViewContainer;
    @Bind(R.id.operation_btn_container)
    LinearLayout operationContainer;
    @Bind(R.id.notice_tab)
    TextView sectionTv;
    @Bind(R.id.video_visibility_iv)
    ImageView videoVisibleIv;

    @Bind(R.id.chat_tab_layout)
    RelativeLayout chatTabLayout;
    @Bind(R.id.question_tab_layout)
    RelativeLayout questionTabLayout;
    @Bind(R.id.notice_tab_layout)
    RelativeLayout noticeTabLayout;
    @Bind(R.id.chat_red_dot)
    ImageView chatRedDot;
    @Bind(R.id.question_red_dot)
    ImageView questionRedDot;
    @Bind(R.id.notice_red_dot)
    ImageView noticeRedDot;
    @Bind(R.id.chat_tab)
    TextView chatTab;
    @Bind(R.id.question_tab)
    TextView questionTab;

    //    @Bind(R.id.play_container)
//    LinearLayout linearContainer;
//    @Bind(R.id.tab_container)
//    LinearLayout tab_container;
    //seek bar 区域
    @Bind(R.id.seek_bar_layout)
    LinearLayout seekbarLayout;
    @Bind(R.id.seek_bar)
    SeekBar seekBar;
    @Bind(R.id.controller_iv)
    ImageView controlIv;
    @Bind(R.id.duration)
    TextView durationTv;
    @Bind(R.id.go_back)
    ImageView goBack;

    private int mode;
    private boolean isLiveStart = false;
    private Resources res;
    private List<Fragment> fragmentList;
    private FragmentListAdapter fragmentListAdapter;
    private int oldPager = 0;

    //    private DispatchChatMessage dispatchChatMessage;
//    private DispatchQuestion dispatchQuestion;
    //    private DispatchNotice dispatchNotice;
    private DispatchChapter dispatchChapter;
    private boolean isPlaying = true;
//    private float xCoordinate = 0;
//    private float yCoordinate = 0;
//    private int deviceWidth; //设备宽度
//    private int deviceHeight;//设备高度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        token = getIntent().getStringExtra("token");
        mode = getIntent().getIntExtra("mode", 1);
        boolean isPlayback = getIntent().getBooleanExtra("isPlayback", false);
        if (isPlayback) {
            setContentView(R.layout.playback_layout);
            ButterKnife.bind(this);
            initView();
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        sectionTv.setText("章节");
        deviceHeight = DimensionUtils.getScreenHeight(this);
        deviceWidth = DimensionUtils.getScreenWidth(this);
        videoViewContainer.setOnTouchListener(this);
        updateLayout();
        initViewPager();
        res = this.getResources();
        HtSdk.getInstance().init(pptContainer, videoViewContainer, MtConsts.MT_MODE_CUSTOM, token, true);
        //点播必须初始化的接口
        HtSdk.getInstance().setPlaybackListener(this);
        videoViewContainer.setVisibility(View.INVISIBLE);

        //将控件移动到前面
        operationContainer.bringToFront();
        seekbarLayout.bringToFront();
        goBack.bringToFront();
        hideTitleBar();
    }

    /**
     * 初始化聊天、问答、公告tab页
     */
    private void initViewPager() {
        fragmentList = new ArrayList<>();

        PlaybackChatFragment playbackChatFragment = PlaybackChatFragment.create("chat_info");
        fragmentList.add(playbackChatFragment);
        PlaybackQuestionFragment questionFragment = PlaybackQuestionFragment.create("question_info");
        fragmentList.add(questionFragment);
        PlaybackSectionFragment sectionFragment = PlaybackSectionFragment.create("section");
        dispatchChapter = sectionFragment;
        fragmentList.add(sectionFragment);
        fragmentListAdapter = new FragmentListAdapter(this, fragmentList);
        viewPager.setAdapter(fragmentListAdapter);
        viewPager.addOnPageChangeListener(this);
    }
//
//    /**
//     * 更新布局
//     */
//    private void updateLayout() {
//        int width = DimensionUtils.getScreenWidth(this);
//        int height = DimensionUtils.getScreenHeight(this);
//        int videoContainerX = 0;
//        int videoContainerY = 0;
//        int orientation = linearContainer.getOrientation();
//        LinearLayout.LayoutParams tablp = (LinearLayout.LayoutParams) tab_container.getLayoutParams();
//        if (orientation == LinearLayout.VERTICAL) {
//            height = 3 * width / 4;
//            tablp.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            tablp.height = 0;
//            tablp.weight = 1.0f;
//            tab_container.setLayoutParams(tablp);
//            videoContainerX = width - videoViewContainer.getLayoutParams().width;
//            videoContainerY = height;
//        } else {
//            width *= 0.7;
//            tablp.width = 0;
//            tablp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//            tablp.weight = 1.0f;
//            tab_container.setLayoutParams(tablp);
//            videoContainerX = width - videoViewContainer.getLayoutParams().width;
//            videoContainerY = 0;
//        }
//        LinearLayout.LayoutParams flp = (LinearLayout.LayoutParams) pptContainer.getLayoutParams();
//        flp.width = width;
//        flp.height = height;
//        pptContainer.setLayoutParams(flp);
//        setViewXY(videoViewContainer, videoContainerX, videoContainerY);
//    }

//    /**
//     * 设置视频容器的位置
//     */
//    public void setViewXY(View target, int x, int y) {
//        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(target.getLayoutParams());
//        margin.setMargins(x, y, x + margin.width, y + margin.height);
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(margin);
//        target.setLayoutParams(layoutParams);
//    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mode == MtConsts.MT_MODE_CUSTOM)
            HtSdk.getInstance().onResume();
        updateSeekBar();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mode == MtConsts.MT_MODE_CUSTOM)
            HtSdk.getInstance().onPause();
        stopUpdateSeekBar();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mode == MtConsts.MT_MODE_CUSTOM) {
            HtSdk.getInstance().release();
        }

        PlaybackDataManage.getInstance().releaseAll();
    }

//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        HtSdk.getInstance().onConfigurationChanged();
//
//        /**
//         * 横竖屏切换更新布局
//         * */
//        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            linearContainer.setOrientation(LinearLayout.HORIZONTAL);
//            updateLayout();
//        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            linearContainer.setOrientation(LinearLayout.VERTICAL);
//            updateLayout();
//        }
//    }

    @Override

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (!isSelectOtherPager(position))
            return;
        restoreTab();
        switch (position) {
            case 0:
                if (chatRedDot.getVisibility() == View.VISIBLE) {
                    chatRedDot.setVisibility(View.INVISIBLE);
                }
                chatTabLayout.setBackgroundResource(R.drawable.tab_select_bg);
                chatTab.setTextColor(res.getColor(R.color.blue));
                oldPager = 0;
                break;
            case 1:
                if (questionRedDot.getVisibility() == View.VISIBLE) {
                    questionRedDot.setVisibility(View.INVISIBLE);
                }
                questionTabLayout.setBackgroundResource(R.drawable.tab_select_bg);
                questionTab.setTextColor(res.getColor(R.color.blue));
                oldPager = 1;
                break;
            case 2:
                if (noticeRedDot.getVisibility() == View.VISIBLE) {
                    noticeRedDot.setVisibility(View.INVISIBLE);
                }
                noticeTabLayout.setBackgroundResource(R.drawable.tab_select_bg);
                sectionTv.setTextColor(res.getColor(R.color.blue));
                oldPager = 2;
                dispatchChapter.switchToChapter();
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 更新tab文本的颜色
     */
    private void restoreTab() {
        switch (oldPager) {
            case 0:
                chatTabLayout.setBackgroundResource(R.drawable.tab_unselect_bg);
                chatTab.setTextColor(res.getColor(R.color.black));
                break;
            case 1:
                questionTabLayout.setBackgroundResource(R.drawable.tab_unselect_bg);
                questionTab.setTextColor(res.getColor(R.color.black));
                break;
            case 2:
                noticeTabLayout.setBackgroundResource(R.drawable.tab_unselect_bg);
                sectionTv.setTextColor(res.getColor(R.color.black));
                break;
            default:
                break;
        }
    }

    private boolean isSelectOtherPager(int selectPager) {
        return oldPager != selectPager;
    }

    @OnClick({R.id.chat_tab_layout, R.id.question_tab_layout, R.id.notice_tab_layout, R.id.fullScreen_iv,
            R.id.video_visibility_iv, R.id.controller_iv, R.id.go_back, R.id.ppt_container})
    void onClick(View v) {
        StringUtils.Log(getClass().getName(), v.getClass().getName());
        switch (v.getId()) {
            case R.id.chat_tab_layout:
                viewPager.setCurrentItem(0);
                break;
            case R.id.question_tab_layout:
                viewPager.setCurrentItem(1);
                break;
            case R.id.notice_tab_layout:
                viewPager.setCurrentItem(2);
                break;
            case R.id.fullScreen_iv:
                onChangeOrient();
                break;
            case R.id.video_visibility_iv:
                onVideoVisible();
                break;
            case R.id.controller_iv:
                if (isPlaying) {
                    pause();
                    isPlaying = false;
                } else {
                    play();
                    isPlaying = true;
                }
                break;
            case R.id.go_back:
                gobackAction();
                break;
            case R.id.ppt_container:
                if (isTitleBarShow) {
                    hideTitleBar();
                } else {
                    showTitleBar();
                }
                break;
            default:
                break;
        }
    }

//    /**
//     * 横竖屏切换
//     */
//    private void onChangeOrient() {
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }
//        updateLayout();
//    }


    private void onVideoVisible() {
        if (HtSdk.getInstance().isVideoShow()) {
            HtSdk.getInstance().hideVideo();
            videoViewContainer.setVisibility(View.INVISIBLE);
            videoVisibleIv.setImageResource(R.mipmap.video_invisible);
        } else {
            HtSdk.getInstance().showVideo();
            videoViewContainer.setVisibility(View.VISIBLE);
            videoVisibleIv.setImageResource(R.mipmap.video_visible);
        }
    }

//    /**
//     * 返回
//     *
//     * @return
//     */
//    private void gobackAction() {
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        } else {
//            showExitDialog();
//        }
//    }

//    /**
//     * 显示退出对话框
//     */
//    private AlertDialog exitDialog;
//
//    private void showExitDialog() {
//        if (exitDialog == null) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(R.string.exit);
//            builder.setTitle(R.string.tips);
//            builder.setPositiveButton((R.string.confirm), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    finish();
//                }
//            });
//            builder.setNegativeButton((R.string.cancel), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            exitDialog = builder.create();
//        }
//        exitDialog.show();
//    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            gobackAction();
            YJUserStudyData.liveCatalogEndStudy();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点播初始化完成回调
     */
    @Override
    public void initSuccess() {
        showTitleBar();
        videoViewContainer.setVisibility(View.VISIBLE);
        seekBar.setClickable(true);
        seekBar.setOnSeekBarChangeListener(onSeekBarChangeListener);
        String duration = PlaybackInfo.getInstance().getDuration();
        int i = Integer.valueOf(duration);
        seekBar.setMax(i);
    }

    @Override
    public void onConnectNetFail() {
        showConnectFailDialog();
    }

    private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            durationTv.setText(TimeUtil.displayHHMMSS(progress));
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            int progress = seekBar.getProgress();
            seekTo(progress);
        }
    };

    private void play() {
        setPlayingStatus();
        HtSdk.getInstance().playbackResumeVideo();
        updateSeekBar();
    }

    private void pause() {
        setPauseStatus();
        HtSdk.getInstance().playbackPauseVideo();
        stopUpdateSeekBar();
    }

    private void setPlayingStatus() {
        controlIv.setImageResource(R.mipmap.pause);
        isPlaying = true;
    }

    private void setPauseStatus() {
        controlIv.setImageResource(R.mipmap.play);
        isPlaying = false;
    }


    private ScheduledExecutorService scheduledPool;
    private ScheduledFuture<?> loadingHandle;
    private int seekBarProgress = 0;

    private void updateSeekBar() {
        if (scheduledPool != null && !scheduledPool.isShutdown()) {
            /**停止上一次周期计时执行*/
            stopUpdateSeekBar();
        }
        Runnable updateSeekBar = new Runnable() {
            @Override
            public void run() {
                /**获得当前已播放秒数*/
                int currentTime =(int) HtSdk.getInstance().getVideoCurrentTime();
                /**更新进度条进度*/
                if (seekBarProgress >= 0 && currentTime > seekBarProgress) {
                    seekBarProgress =  currentTime;
                    if (seekBarProgress >= 0 && seekBarProgress <= Integer.valueOf(PlaybackInfo.getInstance().getDuration())) {
                        seekBar.setProgress(seekBarProgress);
                    }
                }
            }
        };
        /**获取线程池，周期执行更新进度条进度*/
        scheduledPool = Executors.newSingleThreadScheduledExecutor();
        loadingHandle = scheduledPool.scheduleAtFixedRate(updateSeekBar, 1, 1, TimeUnit.SECONDS);
    }

    private void stopUpdateSeekBar() {
        if (loadingHandle != null)
            loadingHandle.cancel(true);
        loadingHandle = null;
        scheduledPool = null;
    }

    @Override
    public void show() {
        seekbarLayout.setVisibility(View.VISIBLE);
        operationContainer.setVisibility(View.VISIBLE);
        goBack.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        seekbarLayout.setVisibility(View.INVISIBLE);
        operationContainer.setVisibility(View.INVISIBLE);
        goBack.setVisibility(View.INVISIBLE);
    }

//    private AlertDialog loadFailDialog;
//
//    /**
//     * 显示加载失败对话框
//     */
//    private void showConnectFailDialog() {
//        if (loadFailDialog == null) {
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setMessage(R.string.not_connect);
//            builder.setTitle(R.string.tips);
//            builder.setPositiveButton((R.string.goback), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    PlaybackModeTwo.this.finish();
//                }
//            });
//            builder.setNegativeButton((R.string.refresh), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                    HtSdk.getInstance().reload();
//                }
//            });
//            loadFailDialog = builder.create();
//        }
//        loadFailDialog.setCancelable(false);
//        loadFailDialog.show();
//    }


    @Override
    public void reclaim() {
        finish();
    }

    @Override
    public void seekTo(long progress) {
        HtSdk.getInstance().playbackSeekTo(progress);
        //seek to 会自动播放,所以要设置成播放状态
//        setPlayingStatus();
//        seekBarProgress = Math.round(progress / 1000);

        seekBarProgress = (int)progress;
        updateSeekBar();
    }

    @Override
    public void onVideoStatusChange(int status, String msg) {
        switch (status) {
            case OnVideoStatusChangeListener.STATUS_PAUSE:
                setPauseStatus();
                break;
            case OnVideoStatusChangeListener.STATUS_PLAYING:
                setPlayingStatus();
                break;
            case OnVideoStatusChangeListener.STATUS_ERROR:
                StringUtils.tip(getApplicationContext(), msg);
                break;
            case OnVideoStatusChangeListener.STATUS_COMPLETED:
                // 播放完毕后重新播放
                // Toast.makeText(this,"Video completed",Toast.LENGTH_SHORT).show();
                if(PlaybackInfo.getInstance().isAlbum()){
                    int currentIndex = PlaybackInfo.getInstance().getCurrentAlbumIndex();
                    List<AlbumItemEntity> albumItemEntities = PlaybackDataManage.getInstance().getAlbumList();
                    if((albumItemEntities.size() <= 1 )|| (currentIndex >= albumItemEntities.size() -1)){
                        currentIndex = 0;
                    }else{
                        currentIndex++;
                    }
                    seekBarProgress = 0;
                    HtSdk.getInstance().playAlbum(albumItemEntities.get(currentIndex));
                    return;
                }
                HtSdk.getInstance().replayVideo();
                seekBarProgress = 0;
                updateSeekBar();
                break;

        }
    }

//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        float x = event.getRawX();
//        float y = event.getRawY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                xCoordinate = event.getX();
//                yCoordinate = event.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                updateLayoutCoordinate((int) (x - xCoordinate), (int) (y - yCoordinate));
//                break;
//            case MotionEvent.ACTION_UP:
//                updateLayoutCoordinate((int) (x - xCoordinate), (int) (y - yCoordinate));
//                xCoordinate = 0;
//                yCoordinate = 0;
//                break;
//            default:
//                break;
//        }
//        return true;
//    }
//
//    private void updateLayoutCoordinate(int x, int y) {
////        更新浮动窗口位置参数
//        int orientation = getRequestedOrientation();
//        int w, h;
//        w = videoViewContainer.getWidth();
//        h = videoViewContainer.getHeight();
//        int width, height;
//        //获取宽高
//        if (orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT || orientation == -1) {
//            width = deviceWidth;
//            height = deviceHeight;
//        } else {
//            width = deviceHeight;
//            height = deviceWidth;
//        }
//        //计算x, y 的值
//        if (x <= 0) {
//            x = 0;
//        } else if (x >= width - w) {
//            x = width - w;
//        }
//        if (y <= 0) {
//            y = 0;
//        } else if (y >= height - h) {
//            y = height - h;
//        }
//
//        ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(videoViewContainer.getLayoutParams());
//        margin.leftMargin = x;
//        margin.topMargin = y;
//        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(margin);
//        videoViewContainer.setLayoutParams(lp);
//    }

  /*  @Override
    public void connectVideoError(String error) {
        StringUtils.tip(getApplicationContext(), error);
    }*/
}