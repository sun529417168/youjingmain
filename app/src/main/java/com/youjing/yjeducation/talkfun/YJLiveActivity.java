package com.youjing.yjeducation.talkfun;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcelable;


import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.util.CropCircleTransfermation;
import com.youjing.yjeducation.util.DimensionUtils;
import com.youjing.yjeducation.util.StringUtils;
import com.squareup.picasso.Picasso;
import com.talkfun.sdk.HtSdk;
import com.talkfun.sdk.consts.MtConsts;
import com.talkfun.sdk.event.Callback;
import com.talkfun.sdk.event.HtLotteryListener;
import com.talkfun.sdk.event.HtVoteListener;
import com.talkfun.sdk.event.LiveInListener;
import com.talkfun.sdk.event.dispatchEvent.HtDispatchChatMessageListener;
import com.talkfun.sdk.event.dispatchEvent.HtDispatchFlowerListener;
import com.talkfun.sdk.event.dispatchEvent.HtDispatchNoticeListener;
import com.talkfun.sdk.event.dispatchEvent.HtDispatchQuestionListener;
import com.talkfun.sdk.event.dispatchEvent.HtDispatchRollAnnounceListener;
import com.talkfun.sdk.event.dispatchEvent.HtDispatchRoomMemberNumListener;
import com.talkfun.sdk.module.ChatEntity;
import com.talkfun.sdk.module.HtBaseMessageEntity;
import com.talkfun.sdk.module.NoticeEntity;
import com.talkfun.sdk.module.QuestionEntity;
import com.talkfun.sdk.module.RollEntity;
import com.talkfun.sdk.module.RoomInfo;
import com.talkfun.sdk.module.VoteEntity;
import com.talkfun.sdk.module.VotePubEntity;
import com.talkfun.sdk.module.system.HtLotteryStopSystemEntity;
import com.talkfun.sdk.module.system.HtSystemBroadcastEntity;
import com.talkfun.sdk.module.system.HtVoteSystemEntity;


import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * user  秦伟宁
 * Date 2016/4/6
 * Time 15:45
 */
public class YJLiveActivity extends  BasicHtActivity  implements ViewPager.OnPageChangeListener,
        LiveInListener, HtDispatchQuestionListener, HtDispatchRoomMemberNumListener,
        HtDispatchChatMessageListener, HtDispatchFlowerListener, HtDispatchNoticeListener,
        HtDispatchRollAnnounceListener, HtSystemBroadcastReceiver.SystemMessageReceiver,
        HtLotteryListener, HtVoteListener, View.OnTouchListener {

    private int mode;
    private String token;
    @Bind(R.id.chat_tab)
    TextView chatTab;
    @Bind(R.id.question_tab)
    TextView questionTab;
    @Bind(R.id.notice_tab)
    TextView noticeTab;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
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

    List<android.support.v4.app.Fragment> fragmentList;
    FragmentListAdapter fragmentListAdapter;
    Resources res;
//    @Bind(R.id.ppt_container)
//    RelativeLayout pptContainer;

    //    @Bind(R.id.video_container)
//    FrameLayout videoViewContainer;
//    @Bind(R.id.play_container)
//    LinearLayout linearContainer;
//    @Bind(R.id.tab_container)
//    LinearLayout tab_container;
    @Bind(R.id.fullScreen_iv)
    ImageView ivFullScreen;
    @Bind(R.id.operation_btn_container)
    LinearLayout operationContainer;
    @Bind(R.id.video_visibility_iv)
    ImageView videoVisibleIv;
    @Bind(R.id.network_choice_iv)
    ImageView networkChoiceIv;
    @Bind(R.id.title_bar)
    RelativeLayout titlebarContainer;
    @Bind(R.id.tv_live_title)
    TextView tvLiveTitle;
    @Bind(R.id.tv_zhubo)
    TextView tvZhubo;
    @Bind(R.id.tv_memberTotal)
    TextView tvMemberTotal;
    @Bind(R.id.head)
    ImageView headIc;

    private DispatchChatMessage dispatchChatMessage;
    private DispatchQuestion dispatchQuestion;
    private DispatchNotice dispatchNotice;
    private HtDispatchFlowerListener dispatchFlowerInfo;

    private ChatFragment chatFragment;
    private QuestionFragment questionFragment;
    private NoticeFragment noticeFragment;

    private final String TAG = "ModeTwoActivity";
    //    private PowerManager.WakeLock wakeLock;
    private int oldPager = 0;
    private boolean isLiveStart = false;
    private RollEntity rollEntity = null;
    private final int SPEED = 5;//移动一个像素要5毫秒
    private LinearLayout rollLayout;
    private ImageView stopRollIv;
    private TextView rollContentHolder;
    private LinearLayout roll;
    //    private int deviceWidth; //设备宽度
//    private int deviceHeight;//设备高度
    private Context mContext;
    /*聊天消息列表*/
    private ArrayList<HtBaseMessageEntity> chatMessageEntityList = new ArrayList<>();
    /*问答消息列表*/
    private ArrayList<QuestionEntity> questionEntitiesList = new ArrayList<>();
    /*公告消息*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        token = getIntent().getStringExtra("token");
        mode = getIntent().getIntExtra("mode", MtConsts.MT_MODE_CUSTOM);
        if (mode == MtConsts.MT_MODE_CUSTOM) {
            setContentView(R.layout.playing_activity_layout);
            ButterKnife.bind(this);
            mContext = this;
            initView();
        }
    }

    /**
     * 初始化布局
     */
    private void initView() {
        deviceHeight = DimensionUtils.getScreenHeight(this);
        deviceWidth = DimensionUtils.getScreenWidth(this);
        updateLayout();
       initViewPager();
        res = this.getResources();
        videoViewContainer.setOnTouchListener(this);
        hideVideoContainer();
        HtSdk.getInstance().init(pptContainer, videoViewContainer, MtConsts.MT_MODE_CUSTOM, token);
        HtSdk.getInstance().setHtSdkListener(this);
        HtSdk.getInstance().setHtDispatchChatMessageListener(this);
        HtSdk.getInstance().setHtDispatchNoticeListener(this);
        HtSdk.getInstance().setHtDispatchQuestionListener(this);
        HtSdk.getInstance().setHtDispatchRollAnnounceListener(this);
        HtSdk.getInstance().setHtDispatchRoomMemberNumListener(this);
        HtSdk.getInstance().setHtDispatchFlowerListener(this);
        HtSdk.getInstance().setHtLotteryListener(this);
        HtSdk.getInstance().setHtVoteListener(this);

       // HtSdk.getInstance().setHtDispatchMessageListener(this);


//        HtSdk.create().on(MtConsts.CHAT_SEND, broadcastListener);
//        HtSdk.create().on(MtConsts.QUESTION, broadcastListener);
//        HtSdk.create().on(MtConsts.ANSWER, broadcastListener);
//        HtSdk.create().on(MtConsts.NOTICE, broadcastListener);
//        HtSdk.create().on(MtConsts.QUESTION_LIST, broadcastListener);
        operationContainer.bringToFront();
        titlebarContainer.bringToFront();
        hideTitleBar();
    }

    /**
     * 初始化聊天、问答、公告tab页
     */
    private void initViewPager() {
        fragmentList = new LinkedList<>();
        createChatFragment();
        createQuestionFragment();
        createNoticeFragment();
        fragmentListAdapter = new FragmentListAdapter(this, fragmentList);
        viewPager.setAdapter(fragmentListAdapter);
        viewPager.setOnPageChangeListener(this);
    }

    private void createChatFragment() {
        chatFragment = ChatFragment.create(chatMessageEntityList);
        dispatchChatMessage = chatFragment;
        dispatchFlowerInfo = chatFragment;
        fragmentList.add(chatFragment);
    }

    private void createQuestionFragment() {
        questionFragment = QuestionFragment.create(questionEntitiesList);
        dispatchQuestion = questionFragment;
        fragmentList.add(questionFragment);
    }
    private void createNoticeFragment() {
        noticeFragment = new NoticeFragment();
        dispatchNotice = noticeFragment;
        fragmentList.add(noticeFragment);
    }


    @Override
    public void reclaim() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            gobackAction();
            YJUserStudyData.liveCatalogEndStudy();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private HtSystemBroadcastReceiver system = new HtSystemBroadcastReceiver(this);

    @Override
    protected void onResume() {
        super.onResume();
//        wakeLock.acquire();
        updateLayout();
        HtSdk.getInstance().onResume();
        //注册感兴趣的系统事件
        String broadcastFilter = MtConsts.SYSTEM_BROADCAST_PREFIX + MtConsts.SYSTEM_BROADCAST_BROADCAST;
        String lotteryFilter = MtConsts.SYSTEM_BROADCAST_PREFIX + MtConsts.LOTTERY_RESULT;
        String voteResult = MtConsts.SYSTEM_BROADCAST_PREFIX + MtConsts.VOTE_PUB;
        IntentFilter systemFilter = new IntentFilter();
        systemFilter.addAction(broadcastFilter);
        systemFilter.addAction(lotteryFilter);
        systemFilter.addAction(voteResult);
        LocalBroadcastManager.getInstance(this).registerReceiver(system, systemFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        HtSdk.getInstance().onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(system);

    }

    @Override
    protected void onStop() {
        super.onStop();
        HtSdk.getInstance().onStop();
    }

    private void hideVideoContainer() {
        if (videoViewContainer != null) {
            videoViewContainer.setVisibility(View.INVISIBLE);
        }
    }

    private void showVideoContainer() {
        if (videoViewContainer != null) {
            videoViewContainer.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mode == MtConsts.MT_MODE_CUSTOM)
            HtSdk.getInstance().release();
//        if (wakeLock != null && wakeLock.isHeld())
//            wakeLock.release();
    }

    @OnClick({R.id.chat_tab_layout, R.id.question_tab_layout, R.id.notice_tab_layout,
            R.id.fullScreen_iv, R.id.iv_go_back,
            R.id.video_visibility_iv, R.id.ppt_container, R.id.network_choice_iv})
    void onClick(View v) {
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
            case R.id.iv_go_back:
                gobackAction();
                break;
            case R.id.ppt_container:
                if (isTitleBarShow) {
                    hideTitleBar();
                } else {
                    showTitleBar();
                }
                break;
            case R.id.network_choice_iv:
                HtSdk.getInstance().showNetworkChoiceDialog();
                break;
            default:
                break;
        }
    }

    private void onVideoVisible() {
        if (isLiveStart) {
            if (HtSdk.getInstance().isVideoShow()) {
                HtSdk.getInstance().hideVideo();
                hideVideoContainer();
                videoVisibleIv.setImageResource(R.mipmap.video_invisible);
            } else {
                HtSdk.getInstance().showVideo();
                showVideoContainer();
                videoVisibleIv.setImageResource(R.mipmap.video_visible);
            }
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * 聊天、问答、公告Tab页切换
     */
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
                if (chatFragment == null) {
                    createChatFragment();
//                    chatFragment.setChatMessageEntityList(chatMessageEntityList);
                }
                oldPager = 0;
                break;
            case 1:
                if (questionRedDot.getVisibility() == View.VISIBLE) {
                    questionRedDot.setVisibility(View.INVISIBLE);
                }
                questionTabLayout.setBackgroundResource(R.drawable.tab_select_bg);
                questionTab.setTextColor(res.getColor(R.color.blue));
               if (questionFragment == null) {
                    createQuestionFragment();
//                    questionFragment.setQuestionEntitiesList(questionEntitiesList);
                }
                oldPager = 1;
                break;
            case 2:
                if (noticeRedDot.getVisibility() == View.VISIBLE) {
                    noticeRedDot.setVisibility(View.INVISIBLE);
                }
                noticeTabLayout.setBackgroundResource(R.drawable.tab_select_bg);
                noticeTab.setTextColor(res.getColor(R.color.blue));
               if (noticeFragment == null) {
                    createNoticeFragment();
                }
                oldPager = 2;
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
                noticeTab.setTextColor(res.getColor(R.color.black));
                break;
            default:
                break;
        }
    }

    private boolean isSelectOtherPager(int selectPager) {
        return oldPager != selectPager;
    }

    @Override
    public void show() {
        titlebarContainer.setVisibility(View.VISIBLE);
        operationContainer.setVisibility(View.VISIBLE);
    }

    @Override
   public void hide() {
        titlebarContainer.setVisibility(View.GONE);
        operationContainer.setVisibility(View.GONE);
    }

    /**
     * SDK初始化完成事件
     */
    @Override
    public void onLaunch() {
        roomInfo = HtSdk.getInstance().getRoomInfo();
        if (roomInfo != null) {
            tvMemberTotal.setText(roomInfo.getMemberTotal() + "人");
            tvZhubo.setText(roomInfo.getZhuBo().getNickname());
            if (roomInfo.getAction().equals("wait")) {
                tvLiveTitle.setText(R.string.live_wait_title);
            }
            if (!TextUtils.isEmpty(roomInfo.getZhuBo().getP40())) {
                Picasso.with(this).load(roomInfo.getZhuBo().getP150())
                        .transform(new CropCircleTransfermation())
                        .into(headIc);
            }
        }
        showTitleBar();
    }

    //视频开始
    @Override
    public void onVideoStart() {
        showVideoContainer();
    }

    //视频关闭
    @Override
    public void onVideoStop() {
        hideVideoContainer();
    }

    private RoomInfo roomInfo;

    //直播开始
    @Override
    public void onLiveStart() {
       isLiveStart = true;
        roomInfo = HtSdk.getInstance().getRoomInfo();
        if (roomInfo == null)
            return;
        tvLiveTitle.setText(roomInfo.getLiveTitle());
        if (roomInfo.getNoticeEntity() != null) {
            dispatchNotice.getNotice(roomInfo.getNoticeEntity());
            if (viewPager.getCurrentItem() != 2) {
                noticeRedDot.setVisibility(View.VISIBLE);
            }
        }
        if (roomInfo.getRollEntity() != null) {
            receiveRollAnnounce(roomInfo.getRollEntity());
        }
    }


    boolean isInitRollingLayout = false;

    /**
     * 初始化滚动通知的布局
     */
    private void initRollinglayout() {
        View view = LayoutInflater.from(this).inflate(R.layout.rolling_layout, null);
        rollLayout = (LinearLayout) view.findViewById(R.id.roll_layout);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        rollLayout.setLayoutParams(lp);
        roll = (LinearLayout) view.findViewById(R.id.roll);
        rollContentHolder = (TextView) view.findViewById(R.id.roll_inner_tv);
        stopRollIv = (ImageView) view.findViewById(R.id.stop_roll);
        stopRollIv.setOnClickListener(rollClick);
        rollLayout.setOnClickListener(rollClick);
        if (pptContainer != null) {
            pptContainer.removeView(rollLayout);
            pptContainer.addView(rollLayout);
        }
        isInitRollingLayout = true;
    }

    /**
     * 初始化滚动通知
     *
     * @param roll
     */
    private void initRollAnnounce(RollEntity roll) {
        if (roll != null)
            this.rollEntity = roll;
    }

    /**
     * 显示滚动通知
     */
    private void showRollAnnounce() {
        if (rollEntity != null && !TextUtils.isEmpty(rollEntity.getContent())) {
            rollContentHolder.setText(rollEntity.getContent());
            rollContentHolder.post(new Runnable() {
                @Override
                public void run() {
                    TranslateAnimation translateAnimation = new TranslateAnimation(roll.getWidth(),
                            -rollContentHolder.getWidth(), 0, 0);
                    translateAnimation.setInterpolator(new LinearInterpolator());
                    translateAnimation.setDuration(roll.getWidth() * SPEED);
                    translateAnimation.setRepeatCount(Animation.INFINITE);
                    translateAnimation.setRepeatMode(Animation.RESTART);
                    rollContentHolder.startAnimation(translateAnimation);
                    if (rollEntity.getDuration() != null)
                        autoDismissRollAnnounceBar(Long.parseLong(rollEntity.getDuration()));
                }
            });
        }
    }

    private CountDownTimer rollAnnounceTimer;

    /**
     * 自动隐藏滚动通知栏
     *
     * @param duration
     */
    private void autoDismissRollAnnounceBar(long duration) {
        if (rollAnnounceTimer != null)
            rollAnnounceTimer.cancel();
        duration *= 1000;
        rollAnnounceTimer = new CountDownTimer(duration, duration) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if (rollLayout != null) {
                    rollLayout.setVisibility(View.GONE);
                    rollContentHolder.clearAnimation();
                    rollAnnounceTimer = null;
                }
            }
        };
        rollAnnounceTimer.start();
    }

    private View.OnClickListener rollClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.stop_roll:
                    rollLayout.setVisibility(View.GONE);
                    break;
                case R.id.roll_layout:
                    if (!TextUtils.isEmpty(rollEntity.getLink())) {
                        Toast.makeText(getApplicationContext(), rollEntity.getLink(), Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    };

    //直播结束
    @Override
    public void onLiveStop() {
        isLiveStart = false;
        tvLiveTitle.setText(R.string.live_stop_title);
        if (rollAnnounceTimer != null) {
            rollAnnounceTimer.cancel();
            if (rollLayout != null) {
                rollLayout.setVisibility(View.GONE);
            }
        }
    }

    //用户被强制退出
    @Override
    public void memberForceout() {
        String reason = getResources().getString(R.string.member_forceout);
        memberOut(reason);
    }

    //用户被踢
    @Override
    public void memberKick() {
        String reason = getResources().getString(R.string.member_kick);
        memberOut(reason);
    }


    private void memberOut(String reason) {
        AlertDialog memberKick = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(reason);
        builder.setTitle(R.string.tips);
        builder.setPositiveButton((R.string.goback), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                YJLiveActivity.this.finish();
            }
        });
        memberKick = builder.create();
        memberKick.setCancelable(false);
        memberKick.show();
    }

    //网络连接断开
    @Override
    public void onConnectNetFail() {
        showConnectFailDialog();
    }

    //接收聊天消息
    @Override
    public void receiveChatMessage(ChatEntity chatMessageEntity) {
        dispatchChatMessage.getChatMessage(chatMessageEntity);
        showNewChatMsg();
    }

    private void showNewChatMsg() {
        if (viewPager.getCurrentItem() != 0) {
            chatRedDot.setVisibility(View.VISIBLE);
        }
    }

    private List<QuestionEntity> notAnswerQuestions = new ArrayList<>();

    //接受问答消息
    @Override
    public void receiveQuestion(QuestionEntity questionEntity) {
        if (questionEntity != null) {
            //如果是自己的问题,或者该问题有答案,则直接显示
            if (questionEntity.isHasAnswer() || roomInfo.getUser().getXid().equals(questionEntity.getXid() + "")) {
                dispatchQuestion.getQuestion(questionEntity);
                showNewQuestion();
            } else {
                //如果是答案,则在没有回答的列表中查询是否包含该问题的答案.
                //如果该答案的问题在没有回答的列表中, 则将问题和答案都传给 QuestionFragment .
                String sn = questionEntity.getSn();
                if (sn.equals("-1") || sn.equals("0")) {
                    int temp = notAnswerQuestions.size();
                    String qid = questionEntity.getQid();
                    for (int i = temp - 1; i >= 0; i--) {
                        if (notAnswerQuestions.get(i).getQid().equals(qid)) {
                            //找到该答案的问题.则将该问题传入QuestionFragment .并把问题从没有回答的问题列表中移除
                            dispatchQuestion.getQuestion(notAnswerQuestions.get(i));
                            notAnswerQuestions.remove(i);
                            break;
                        }
                    }
                    //无论是否找到该答案的问题.都将该答案传给QuestionFragment
                    dispatchQuestion.getQuestion(questionEntity);
                    showNewQuestion();
                } else {
                    // 如果是老师的提问,则直接显示
                    if (questionEntity.getRole().equals("admin") || questionEntity.getRole().equals("spadmin")) {
                        dispatchQuestion.getQuestion(questionEntity);
                        showNewQuestion();
                    } else {
                        //如果是问题.则直接插入没有回答的问题列表
                        notAnswerQuestions.add(questionEntity);
                    }
                }
            }
        }
    }

    private void showNewQuestion() {
        if (viewPager.getCurrentItem() != 1) {
            questionRedDot.setVisibility(View.VISIBLE);
        }
    }

    //接受公告消息
    @Override
    public void receiveNotice(NoticeEntity noticeEntity) {
        if (TextUtils.isEmpty(noticeEntity.getContent()))
            return;
        dispatchNotice.getNotice(noticeEntity);
        if (viewPager.getCurrentItem() != 2) {
            noticeRedDot.setVisibility(View.VISIBLE);
        }
    }

    //接受滚动通知
    @Override
    public void receiveRollAnnounce(RollEntity rollEntity) {
        if (rollEntity == null)
            return;
        if (!isInitRollingLayout) {
            initRollinglayout();
        }
        initRollAnnounce(rollEntity);
        rollLayout.setVisibility(View.VISIBLE);
        showRollAnnounce();
    }

    //房间人数变化
    public void updateMemberTotal(int total) {
        tvMemberTotal.setText(total + "人");
    }

    //接收系统广播
    @Override
    public void receiverSystemMessage(Intent intent) {
        if (intent != null) {
            String type = intent.getStringExtra("type");
            Parcelable parcelable = intent.getParcelableExtra("msg");
            switch (type) {
                case MtConsts.SYSTEM_BROADCAST_BROADCAST:
                    HtSystemBroadcastEntity entity = (HtSystemBroadcastEntity) parcelable;
                    receiveHtSystemBroadcast(entity);
                    break;
                case MtConsts.LOTTERY_RESULT:
                    HtLotteryStopSystemEntity stopEntity = (HtLotteryStopSystemEntity) parcelable;
                    receiverHtSystemLotteryResult(stopEntity);
                    break;
                case MtConsts.VOTE_PUB:
                    HtVoteSystemEntity voteSystemEntity = (HtVoteSystemEntity) parcelable;
                    receiverHtSystemVoteResult(voteSystemEntity);
                    break;
                default:
                    break;

            }
            showNewChatMsg();
        }
    }

    //将系统广播插入聊天
    private void receiveHtSystemBroadcast(HtSystemBroadcastEntity entity) {
        ChatEntity chatEntity = new ChatEntity();
        String prefix = getResources().getString(R.string.public_broadcast);
        chatEntity.setMsg(prefix + entity.getMessage());
        chatEntity.setRole("system_broadcast");
        chatEntity.setTime(entity.getTime());
        dispatchChatMessage.getChatMessage(chatEntity);
    }

    //将抽奖结果插入聊天
    private void receiverHtSystemLotteryResult(HtLotteryStopSystemEntity entity) {
        ChatEntity chatEntity = new ChatEntity();
        String notify = getResources().getString(R.string.ht_notify);
        chatEntity.setMsg(String.format(notify, entity.getLaunchName(), entity.getNickname()));
        chatEntity.setRole("system_lottery");
        chatEntity.setTime(entity.getTime());
        dispatchChatMessage.getChatMessage(chatEntity);
    }

    //将投票结果插入聊天
    private void receiverHtSystemVoteResult(HtVoteSystemEntity voteSystemEntity) {
        String notify = getResources().getString(R.string.ht_vote_notify);
        voteSystemEntity.setContent(String.format(notify, voteSystemEntity.getNickname(), voteSystemEntity.getTime()));
        voteSystemEntity.setRole("system_vote");
        dispatchChatMessage.getChatMessage((HtBaseMessageEntity) voteSystemEntity);
    }

    //获取鲜花数
    @Override
    public void getFlowerNum(int num) {
        dispatchFlowerInfo.getFlowerNum(num);
    }

    //获取第一朵鲜花剩余的时间
    @Override
    public void getFlowerLeftTime(int time) {
        dispatchFlowerInfo.getFlowerLeftTime(time);
    }

    //获取鲜花数
    @Override
    public void getTotalFlower(int total) {
        dispatchFlowerInfo.getTotalFlower(total);
    }

    //送花成功
    @Override
    public void sendSuccess(String name, String role, int amount, String time) {
        dispatchFlowerInfo.sendSuccess(name, role, amount, time);
    }



    public void connectVideoError(String error) {
        StringUtils.tip(getApplicationContext(), error);
    }


    private WeakReference<VoteFragment> voteFragment;
    private WeakReference<VoteResultFragment> voteResultFragment;
    private WeakReference<LotteryFragment> lotteryFragment;//抽奖


    //抽奖开始
    @Override
    public void lotteryStart() {
        if (lotteryFragment != null && lotteryFragment.get().isVisible())
            lotteryFragment.get().dismiss();
        lotteryFragment = new WeakReference<LotteryFragment>(LotteryFragment.create(true, "", false));
        lotteryFragment.get().show(((FragmentActivity) mContext).getSupportFragmentManager(), "lottery");
    }

    //抽奖结束
    @Override
    public void lotteryStop(String name, boolean isCurrentUser) {
        if (lotteryFragment != null && lotteryFragment.get().isVisible()) {
            lotteryFragment.get().dismiss();
        }
        lotteryFragment = new WeakReference<LotteryFragment>(LotteryFragment.create(false, name, isCurrentUser));
        lotteryFragment.get().show(((FragmentActivity) mContext).getSupportFragmentManager(), "lottery");
    }

    //投票开始
    @Override
    public void voteStart(VoteEntity voteEntity) {
        if (voteEntity == null)
            return;
        boolean isSingleChoice = !(voteEntity.getOptional() > 1);
        resetVoteFragment();
        voteFragment = new WeakReference<VoteFragment>(VoteFragment.create(voteEntity, isSingleChoice, mCallback));
        voteFragment.get().show(((FragmentActivity) mContext).getSupportFragmentManager(), "vote");
    }

    //投票结束
    @Override
    public void voteStop(VotePubEntity votePubEntity) {
        if (votePubEntity == null)
            return;
        resetVoteFragment();
        voteResultFragment = new WeakReference<VoteResultFragment>(VoteResultFragment.create(votePubEntity));
        voteResultFragment.get().show(((FragmentActivity) mContext).getSupportFragmentManager(), "vote_success");
    }

    //重置投票
    private void resetVoteFragment() {
        if (voteFragment != null && voteFragment.get().isVisible()) {
            voteFragment.get().dismiss();
        }
        if (voteResultFragment != null && voteResultFragment.get().isVisible())
            voteResultFragment.get().dismiss();
        if (lotteryFragment != null && lotteryFragment.get().isVisible())
            lotteryFragment.get().dismiss();
    }

    //投票回调
    private Callback mCallback = new Callback() {
        @Override
        public void success(Object result) {
            VoteSuccess voteSuccess = new VoteSuccess();
            voteSuccess.show(((FragmentActivity) mContext).getSupportFragmentManager(), "vote_success");
        }

        @Override
        public void failed(String failed) {
            try {
                String content = URLEncoder.encode(failed, "utf-8");
                Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    };
}
