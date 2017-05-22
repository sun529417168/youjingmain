package com.youjing.yjeducation.talkfun.customtalkfun;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.squareup.picasso.Picasso;
import com.talkfun.sdk.HtSdk;
import com.talkfun.sdk.config.HtConfig;
import com.talkfun.sdk.consts.MtConsts;
import com.talkfun.sdk.event.Callback;
import com.talkfun.sdk.event.HtLotteryListener;
import com.talkfun.sdk.event.HtMessageListener;
import com.talkfun.sdk.event.HtVoteListener;
import com.talkfun.sdk.event.LiveInListener;
import com.talkfun.sdk.event.VideoConnectListener;
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
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJChatEntityModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJGiftModel;
import com.youjing.yjeducation.model.YJQQGroupInfoModel;
import com.youjing.yjeducation.model.YJTeacherModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.talkfun.BrevityGroup;
import com.youjing.yjeducation.talkfun.ChatAdapter;
import com.youjing.yjeducation.talkfun.ChatFragment;
import com.youjing.yjeducation.talkfun.DispatchChatMessage;
import com.youjing.yjeducation.talkfun.DispatchNotice;
import com.youjing.yjeducation.talkfun.DispatchPrivateChat;
import com.youjing.yjeducation.talkfun.DispatchQuestion;
import com.youjing.yjeducation.talkfun.FragmentListAdapter;
import com.youjing.yjeducation.talkfun.HtSystemBroadcastReceiver;
import com.youjing.yjeducation.talkfun.LotteryFragment;
import com.youjing.yjeducation.talkfun.NoticeFragment;
import com.youjing.yjeducation.talkfun.QuestionFragment;
import com.youjing.yjeducation.talkfun.VoteFragment;
import com.youjing.yjeducation.talkfun.VoteResultFragment;
import com.youjing.yjeducation.talkfun.VoteSuccess;
import com.youjing.yjeducation.ui.actualize.activity.YJGoodTeacherTopActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJRechargeWhaleMoney;
import com.youjing.yjeducation.ui.actualize.activity.YJRichRankingTopActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJStudyOverlordTopActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJEvaluationDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJEvaluationOverDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJLiveRedBagDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJLiveRoomQuestionDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJLiveShareDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJRecommendCourseDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJReservationCourseDialog;
import com.youjing.yjeducation.util.AnimationUtils;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.CropCircleTransfermation;
import com.youjing.yjeducation.util.DimensionUtils;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.util.YJLiveRoomUtil;
import com.youjing.yjeducation.util.YjGetUserInfo;
import com.youjing.yjeducation.wiget.HorizontalListView;
import com.youjing.yjeducation.wiget.HorizontalListViewAdapter;
import com.youjing.yjeducation.wiget.PeriscopeLayout;
import com.youjing.yjeducation.wiget.SoftKeyboardStateHelper;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.adapter.VPageViewAdapter;
import org.vwork.mobile.ui.delegate.IVPageViewAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVPageViewChangeDelegate;
import org.vwork.mobile.ui.listener.VPageViewChangeListener;
import org.vwork.mobile.ui.widget.VPageView;
import org.vwork.utils.base.VParams;
import org.vwork.utils.notification.IVNotificationListener;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * user  秦伟宁
 * Date 2016/6/4
 * Time 10:24
 */
public class YJCustomLiveActivity extends CustomBasiHtActivity implements ViewPager.OnPageChangeListener,
        LiveInListener, HtDispatchQuestionListener, HtDispatchRoomMemberNumListener,
        HtDispatchChatMessageListener, HtDispatchFlowerListener, HtDispatchNoticeListener,
        HtDispatchRollAnnounceListener, HtSystemBroadcastReceiver.SystemMessageReceiver, VideoConnectListener,
        HtLotteryListener, HtVoteListener, View.OnTouchListener, IVPageViewAdapterDelegate, IVPageViewChangeDelegate, HtMessageListener {
    private int mode, isLiveType;
    private String token;
    private String mTeacherId, courseVideoId, courseId, startTime, courseCatalogId, teacherName, courseName, endTime;

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
    @Bind(R.id.img_live_ranking)
    RelativeLayout img_live_ranking;
    @Bind(R.id.img_live_notice)
    RelativeLayout img_live_notice;

    List<Fragment> fragmentList;
    FragmentListAdapter fragmentListAdapter;
    Resources res;
    @Bind(R.id.fullScreen_iv)
    ImageView ivFullScreen;
    @Bind(R.id.operation_btn_container)
    LinearLayout operationContainer;
    @Bind(R.id.video_visibility_iv)
    ImageView videoVisibleIv;
    @Bind(R.id.network_choice_iv)
    ImageView networkChoiceIv;
    @Bind(R.id.exchange)
    ImageView exchange;
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
    @Bind(R.id.img_delete)
    RelativeLayout img_delete;
    @Bind(R.id.img_gift)
    ImageView img_gift;
    @Bind(R.id.re_notice)
    RelativeLayout re_notice;
    @Bind(R.id.re_chat_list)
    RelativeLayout re_chat_list;
    @Bind(R.id.re_gift)
    RelativeLayout re_gift;
    @Bind(R.id.re_intput)
    RelativeLayout re_intput;
    @Bind(R.id.input_edt)
    EditText input_edt;
    @Bind(R.id.txt_sent_gift)
    TextView txt_sent_gift;
    @Bind(R.id.txt_whale_money)
    TextView txt_whale_money;
    @Bind(R.id.btn_recharge)
    Button btn_recharge;
    @Bind(R.id.txt_rich_top)
    TextView txt_rich_top;
    @Bind(R.id.txt_learn_top)
    TextView txt_learn_top;
    @Bind(R.id.txt_teacher_top)
    TextView txt_teacher_top;
    @Bind(R.id.pager_ranking)
    VPageView pager_ranking;
    @Bind(R.id.txt_notice_roll)
    TextView txt_notice_roll;

    @Bind(R.id.img_input_gift)
    ImageView img_input_gift;

    @Bind(R.id.periscope)
    PeriscopeLayout periscope;
    @Bind(R.id.re_praise)
    RelativeLayout re_praise;


    @Bind(R.id.chat_lv)
    ListView chatLv;
    @Bind(R.id.horizon_listview)
    HorizontalListView horizon_listview;
    @Bind(R.id.txt_gift_num_decrease)
    TextView txt_gift_num_decrease;
    @Bind(R.id.txt_gift_num)
    TextView txt_gift_num;
    @Bind(R.id.txt_gift_num_add)
    TextView txt_gift_num_add;
    @Bind(R.id.img_gift_num_bg)
    ImageView img_gift_num_bg;
    @Bind(R.id.img_share)
    ImageView img_share;
    @Bind(R.id.img_import)
    ImageView img_import;
    @Bind(R.id.img_grab_red)
    ImageView img_grab_red;
    @Bind(R.id.btn_send)
    Button btn_send;
    @Bind(R.id.lv_gift_list_right)
    ListView lv_gift_list_right;
    @Bind(R.id.re_gift_list_right)
    RelativeLayout re_gift_list_right;
    @Bind(R.id.re_live_ranking)
    RelativeLayout re_live_ranking;
    @Bind(R.id.img_live_comment)
    RelativeLayout img_live_comment;
    @Bind(R.id.re_red)
    RelativeLayout re_red;
    @Bind(R.id.re_chat_list_up)
    RelativeLayout re_chat_list_up;

    private IUiListener qqShareListener;
    private List<YJCourseModel> yjCourseModelList;
    private List<YJCourseModel> yjCourseModelReservationList;
    private String redId;
    private String redBatch;
    private ChatAdapter chatAdapter;
    private YJCustomChatAdapter yjCustomChatAdapter;
    private DispatchChatMessage dispatchChatMessage;
    private DispatchQuestion dispatchQuestion;
    private DispatchNotice dispatchNotice;
    private HtDispatchFlowerListener dispatchFlowerInfo;
    private final String TAG = "YJCustomLiveActivity";
    //    private PowerManager.WakeLock wakeLock;
    private int oldPager = 0;
    private boolean isLiveStart = false;
    private RollEntity rollEntity = null;
    private final int SPEED = 5;//移动一个像素要5毫秒
    private LinearLayout rollLayout;
    private ImageView stopRollIv;
    private TextView rollContentHolder;
    private LinearLayout roll;
    Timer mTimer;
    TimerTask timerTask;
    private boolean flag = false;
    private int viewId[] = {R.mipmap.customlive_message, R.mipmap.customlive_score, R.mipmap.customlive_gift};
    //    private int deviceWidth; //设备宽度
//    private int deviceHeight;//设备高度
    private Context mContext;
    /*聊天消息列表*/
    private ArrayList<HtBaseMessageEntity> chatMessageEntityList = new ArrayList<>();
    //礼物列表
    private ArrayList<YJChatEntityModel> giftMessageEntityList = new ArrayList<>();
    /*问答消息列表*/
    private ArrayList<QuestionEntity> questionEntitiesList = new ArrayList<>();
    /*公告消息*/

    /*群列表*/
    List<BrevityGroup> groups = new LinkedList<>();
    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                yjCustomChatAdapter.removeList();
            } else if (msg.what == 2) {
                mTimePosition--;
                if (mTimePosition == 0) {
                    mTimePosition = 5;
//                    showToast("发送消息" + priaseTimes);
                    sendPraiseMsg(priaseTimes + "");
                    livePraise(priaseTimes);
                    priaseTimes = 0;
                    timerTask.cancel();
                }
            }else if (msg.what == 3){
                chatAdapter.notifyDataSetChanged();
                chatLv.setSelection(chatLv.getCount());
//                chatLv.smoothScrollToPosition(chatLv.getCount());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onChangeOrient();
        getGiftList();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        token = getIntent().getStringExtra("token");
        mTeacherId = getIntent().getStringExtra("teacherId");
        courseVideoId = getIntent().getStringExtra("courseVideoId");
        courseCatalogId = getIntent().getStringExtra("courseCatalogId");
        courseId = getIntent().getStringExtra("courseId");
        startTime = getIntent().getStringExtra("startTime");
        endTime = getIntent().getStringExtra("endTime");

        teacherName = getIntent().getStringExtra("teacherName");
        courseName = getIntent().getStringExtra("courseName");
        isLiveType = getIntent().getIntExtra("isLiveType", 1);
        mIsShowDialog = getIntent().getBooleanExtra("isShowDialog", false);


        mode = getIntent().getIntExtra("mode", MtConsts.MT_MODE_CUSTOM);
        if (mode == MtConsts.MT_MODE_CUSTOM) {
            setContentView(R.layout.playing_activity_layout_custome);
            ButterKnife.bind(this);
            mContext = this;
            initView();
        }
        StringUtils.setGuidImage(this, R.id.play_container, viewId, "FirstCustomLive");
    }
    /**
     * 初始化布局
     */
    private void initView() {
        deviceHeight = DimensionUtils.getScreenHeight(this);
        deviceWidth = DimensionUtils.getScreenWidth(this);
        onChangeOrient();
        updateLayout();
        initViewPager();
        res = this.getResources();
        videoViewContainer.setOnTouchListener(this);
        hideVideoContainer();
        HtSdk.getInstance().init(pptContainer, videoViewContainer, MtConsts.MT_MODE_CUSTOM, token);
        HtSdk.getInstance().setHtSdkListener(this);
        //新增
        HtSdk.getInstance().setVideoConnectListener(this);
        HtSdk.getInstance().setHtDispatchChatMessageListener(this);
        HtSdk.getInstance().setHtDispatchNoticeListener(this);
        HtSdk.getInstance().setHtDispatchQuestionListener(this);
        HtSdk.getInstance().setHtDispatchRollAnnounceListener(this);
        HtSdk.getInstance().setHtDispatchRoomMemberNumListener(this);
        HtSdk.getInstance().setHtDispatchFlowerListener(this);
        HtSdk.getInstance().setHtLotteryListener(this);
        HtSdk.getInstance().setHtVoteListener(this);

        operationContainer.bringToFront();
        titlebarContainer.bringToFront();
        hideTitleBar();

        if (mIsShowDialog) {
            if (isLiveType == 1) {
                reservationCourseDetail(false);
            } else if (isLiveType == 2) {
                reservationCourseDetail(true);
            }
            StringUtils.Log(TAG, "mIsShowDialog true");
            StringUtils.Log(TAG, "isLiveType=" + isLiveType);
        } else {
            StringUtils.Log(TAG, "mIsShowDialog false");
        }
        //排行榜viewpager
        VPageViewAdapter adapter = new VPageViewAdapter(this, pager_ranking);
        VPageViewChangeListener listener = new VPageViewChangeListener(this, pager_ranking);
        pager_ranking.setAdapter(adapter);
        pager_ranking.setChangeListener(listener);
        pager_ranking.setCurrentItem(0);


        YjGetUserInfo.getUserCoin((IVActivity) getContext(), txt_whale_money);

        //聊天适配器
        chatAdapter = new ChatAdapter(this, chatMessageEntityList);
        chatLv.setAdapter(chatAdapter);
        //右侧礼物适配器
        yjCustomChatAdapter = new YJCustomChatAdapter(this, giftMessageEntityList);
        lv_gift_list_right.setAdapter(yjCustomChatAdapter);
        lv_gift_list_right.setClickable(false);
        lv_gift_list_right.setScrollbarFadingEnabled(true);
        lv_gift_list_right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:

                        return true;
                    default:
                        break;
                }

                return false;
            }
        });

        if (chatLv.getCount() >= 4) {
            re_chat_list_up.setVisibility(View.GONE);
        } else {
            re_chat_list_up.setVisibility(View.VISIBLE);
        }
        chatLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chatAdapter.notifyDataSetChanged();
                if (isShowIMList) {
                    FrameLayout.LayoutParams s = new FrameLayout.LayoutParams(YJConfig.mScreenHeight / 2, DimensionUtils.dip2px(getApplicationContext(), 140));
                    //  s.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    re_chat_list.setLayoutParams(s);
                    re_chat_list_up.setLayoutParams(s);
                    isShowIMList = false;
//                    re_chat_list.setAnimation(moveToViewLocati());
                } else {
                    FrameLayout.LayoutParams s = new FrameLayout.LayoutParams(YJConfig.mScreenHeight / 2, DimensionUtils.dip2px(getApplicationContext(), 35));
                    // s.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    re_chat_list.setLayoutParams(s);
                    re_chat_list_up.setLayoutParams(s);
                    isShowIMList = true;
                    //  re_chat_list.setAnimation(moveToViewBo());
                    Message message = Message.obtain();
                    message.what = 3;
                    timeHandler.sendMessage(message);
                }
            }
        });
        re_chat_list_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatAdapter.notifyDataSetChanged();
                if (isShowIMList) {
                    FrameLayout.LayoutParams s = new FrameLayout.LayoutParams(YJConfig.mScreenHeight / 2, DimensionUtils.dip2px(getApplicationContext(), 140));
                    // s.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    re_chat_list.setLayoutParams(s);
                    re_chat_list_up.setLayoutParams(s);
                    StringUtils.Log(TAG, " isShowIMList re_chat_list_up");
                    isShowIMList = false;
//                    re_chat_list.setAnimation(moveToViewLocati());
                } else {
                    StringUtils.Log(TAG, " re_chat_list_up");
                    FrameLayout.LayoutParams s = new FrameLayout.LayoutParams(YJConfig.mScreenHeight / 2, DimensionUtils.dip2px(getApplicationContext(), 35));
                    // s.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, -1);
                    re_chat_list.setLayoutParams(s);
                    re_chat_list_up.setLayoutParams(s);
                    isShowIMList = true;
//                  re_chat_list.setAnimation(moveToViewBo());
                    Message message = Message.obtain();
                    message.what = 3;
                    timeHandler.sendMessage(message);
                }
            }
        });


        horizon_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (yjGiftModelList != null) {
                    if (!TextUtils.isEmpty(yjGiftModelList.get(i).getPayFee()) && yjGiftModelList.get(i).getPayFee().equals("Yes")) {
                        txt_gift_num.setText("1");
                        mGiftNum = 1;
                        img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_de);
                        txt_gift_num_add.setEnabled(false);
                        txt_gift_num_decrease.setEnabled(false);
                        StringUtils.Log(TAG, "txt_gift_num_add.setEnabled(false)");

                    } else {
                        StringUtils.Log(TAG, "txt_gift_num_add.setEnabled(true)");

                        if (mGiftNum == 1) {
                            img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_de);
                        } else {
                            img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_se);
                        }
                        txt_gift_num_add.setEnabled(true);
                        txt_gift_num_decrease.setEnabled(true);
                    }
                }
                mGiftListPosition = i;
                hListViewAdapter.setSelectIndex(i);
                hListViewAdapter.notifyDataSetChanged();
            }
        });

        //键盘收起按键监听
        final SoftKeyboardStateHelper softKeyboardStateHelper = new SoftKeyboardStateHelper(findViewById(R.id.ppt_container));
        softKeyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {

            }
            @Override
            public void onSoftKeyboardClosed() {
                re_intput.setVisibility(View.GONE);
                isInputShow = false;
            }
        });
        txt_notice_roll.setSelected(true);
        txt_notice_roll.setFocusable(true);
        bindNotifyListener();


        re_notice.setVisibility(View.GONE);
        isNoticeShow = false;


        isCommentClickable = isComment();
        getRichRankingTop();
        qqShareListener = new IUiListener() {
            @Override
            public void onCancel() {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast("分享取消");
            }

            @Override
            public void onComplete(Object response) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "sucess");
                //showToast(getString(R.string.share_success));
            }

            @Override
            public void onError(UiError e) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast(getString(R.string.share_fail));
            }
        };
    }

    private int priaseTimes = 0;
    private int mTimePosition = 5;
    private int mGiftListPosition = 0;
    private HorizontalListViewAdapter hListViewAdapter;
    private boolean isShowIMList = true;

    private DispatchPrivateChat dispatchPrivateChat;
    private ChatFragment chatFragment;
    private QuestionFragment questionFragment;
    private NoticeFragment noticeFragment;

    private boolean isRankingshow = true;
    private boolean isNoticeShow = true;
    private boolean isCommentshow = true;
    private boolean isGiftShow = false;
    private boolean isInputShow = false;
    private static boolean mIsShowDialog = false;

    private boolean isCommentClickable = false;
    private int reclen = 0;

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
        viewPager.addOnPageChangeListener(this);
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
    void reclaim() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if (mIsShowDialog) {
                finish();
            } else {
                gobackAction();
                return true;
            }

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

        //收到礼物监听
        HtSdk.getInstance().on("third:sendRed", this);

        //推荐课程监听
        HtSdk.getInstance().on("third:sendRecomendCourse", new HtMessageListener() {
            @Override
            public void receiveBroadcast(JSONObject jsonObject) {
                StringUtils.Log(TAG, "sendRecomendCourse");
                recommendCourseList();

            }
        });


        //监听评分
        HtSdk.getInstance().on("third:sendCourseScore", new HtMessageListener() {
            @Override
            public void receiveBroadcast(JSONObject jsonObject) {
                StringUtils.Log(TAG, "sendCourseScore");
                isCommentClickable = true;
                courseScoreDetail();
              /*  YJEvaluationDialog yjEvaluationDialog = new YJEvaluationDialog();
                showDialog(yjEvaluationDialog);*/

            }
        });
        //做题列表
        HtSdk.getInstance().on("third:sendQuestions", new HtMessageListener() {
            @Override
            public void receiveBroadcast(JSONObject jsonObject) {
                StringUtils.Log(TAG, "sendQuestions");
                YJLiveRoomQuestionDialog yjLiveRoomQuestionDialog = new YJLiveRoomQuestionDialog();
                VParams data = createTransmitData(yjLiveRoomQuestionDialog.COURSEID, courseId).set(yjLiveRoomQuestionDialog.COURSEVIDEOID, courseVideoId)
                        .set(yjLiveRoomQuestionDialog.COURSECATALOGID, courseCatalogId);
                showDialog(yjLiveRoomQuestionDialog, data);
            }
        });
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
        notifyListener(YJNotifyTag.YJCUSTOMLIVEACTIVITY_DESTROY,"");
        StringUtils.Log(TAG,"YJCustomLiveActivity  onDestroy()");
        if (mode == MtConsts.MT_MODE_CUSTOM)
            HtSdk.getInstance().release();

        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        new Thread(new MyThread()).interrupt();
    }

    @OnClick({R.id.chat_tab_layout, R.id.question_tab_layout, R.id.notice_tab_layout,
//            R.id.private_chat_tab_layout,
            R.id.fullScreen_iv, R.id.iv_go_back, R.id.exchange, R.id.img_import,
            R.id.video_visibility_iv, R.id.ppt_container, R.id.network_choice_iv, R.id.img_delete, R.id.img_gift, R.id.txt_gift_num_decrease,
            R.id.txt_gift_num_add, R.id.img_share, R.id.btn_send, R.id.img_live_ranking, R.id.txt_sent_gift, R.id.img_grab_red, R.id.btn_recharge,
            R.id.txt_rich_top, R.id.txt_learn_top, R.id.txt_teacher_top, R.id.img_live_comment, R.id.tv_zhubo, R.id.tv_memberTotal, R.id.img_live_notice,
            R.id.img_input_gift, R.id.tv_live_title, R.id.re_praise})
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
                if (isGiftShow) {
                    StringUtils.Log(TAG, "isGiftShow = false");
                    re_gift.setVisibility(View.GONE);
                    re_gift.setAnimation(AnimationUtils.moveToViewBottom(350));
                    isGiftShow = false;
                } else {
                    if (isTitleBarShow) {
                        hideTitleBar();
                    } else {
                        showTitleBar();
                    }
                }
                break;
            case R.id.network_choice_iv:
                HtSdk.getInstance().showNetworkChoiceDialog();
                break;
            case R.id.tv_live_title:

                break;
            case R.id.exchange:
                //先注释掉这行.该控件改作用来测试桌面分享
                HtSdk.getInstance().exchangeVideoAndPpt();
                break;
            case R.id.img_delete:
                re_notice.setVisibility(View.GONE);
                re_notice.setAnimation(AnimationUtils.upmoveToInvisable(300));
                isNoticeShow = false;
                break;
            case R.id.img_live_notice:
                if (isNoticeShow) {
                    re_notice.setVisibility(View.GONE);
                    re_notice.setAnimation(AnimationUtils.upmoveToInvisable(300));
                    isNoticeShow = false;
                } else {
                    re_notice.setVisibility(View.VISIBLE);
                    re_notice.setAnimation(AnimationUtils.upmoveToViewLocation(300));
                    isNoticeShow = true;
                }

                break;
            case R.id.img_gift:
                StringUtils.Log(TAG, "img_gift");
                re_gift.setVisibility(View.VISIBLE);
                re_gift.setAnimation(AnimationUtils.moveToViewLocation(350));
                isGiftShow = true;
                break;
            case R.id.tv_zhubo:
                break;
            case R.id.re_praise:
                mTimePosition = 5;
                if (priaseTimes == 0) {
                    if (mTimer == null) {
                        mTimer = new Timer();
                    }

                    if (timerTask != null) {
                        timerTask.cancel();
                    }
                    timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            timeHandler.sendEmptyMessage(2);
                        }
                    };
                    mTimer.schedule(timerTask, 0, 1000);
                }
                priaseTimes++;
                periscope.addHeart();
                break;
            case R.id.img_input_gift:
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(pptContainer.getWindowToken(), 0);
                //隐藏软键盘
                re_intput.setVisibility(View.GONE);
                isInputShow = false;

                re_gift.setVisibility(View.VISIBLE);
                re_gift.setAnimation(AnimationUtils.moveToViewLocation(350));
                isGiftShow = true;
                break;
            case R.id.tv_memberTotal:

                break;
            case R.id.img_live_ranking:
                if (isRankingshow) {
                    re_live_ranking.setVisibility(View.VISIBLE);
                    isRankingshow = false;
                } else {
                    re_live_ranking.setVisibility(View.GONE);
                    isRankingshow = true;
                }
                break;
            case R.id.img_import:
                re_intput.setVisibility(View.VISIBLE);
                isInputShow = true;
                input_edt.setFocusable(true);
                input_edt.setFocusableInTouchMode(true);
                input_edt.requestFocus();
                InputMethodManager m = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
            case R.id.txt_gift_num_decrease:
                if (mGiftNum <= 1) {
                    img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_de);
                } else {
                    if (mGiftNum == 2) {
                        mGiftNum--;
                        txt_gift_num.setText(mGiftNum + "");
                        img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_de);
                    } else {
                        mGiftNum--;
                        txt_gift_num.setText(mGiftNum + "");
                        img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_se);
                    }
                }
                break;
            case R.id.txt_gift_num_add:
                mGiftNum++;
                txt_gift_num.setText(mGiftNum + "");
                img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_se);
                break;

            case R.id.img_share:
                YJLiveShareDialog yjLiveShareDialog = new YJLiveShareDialog();
                showDialog(yjLiveShareDialog);

                break;
            case R.id.btn_send:

                String sendContent = input_edt.getText().toString().trim();
                if (sendContent.length() >= 150) {
                    showToast("输入内容不能大于150个字");
                    return;
                }
                sendMsg(sendContent, msgCallback);
                break;
            case R.id.img_grab_red:

                getRedInfo(redId, redBatch);

                break;
            case R.id.img_live_comment:
                if (isRankingshow == false) {
                    re_live_ranking.setVisibility(View.GONE);
                    isRankingshow = true;
                }
                if (isCommentClickable) {
                    courseScoreDetail();
                } else {
                    showToast("还不能评价哦，请耐心观看会在评价哦！");
                }
                break;
            case R.id.btn_recharge:
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                startActivity(YJRechargeWhaleMoney.class);
                break;
            case R.id.txt_rich_top:

                pager_ranking.setCurrentItem(0);
                getRichRankingTop();
                txt_rich_top.setTextColor(getResources().getColor(R.color.white));
                txt_learn_top.setTextColor(getResources().getColor(R.color.blue_title));
                txt_teacher_top.setTextColor(getResources().getColor(R.color.blue_title));
                break;
            case R.id.txt_learn_top:
                pager_ranking.setCurrentItem(1);
                getStudyRankingTop();
                txt_rich_top.setTextColor(getResources().getColor(R.color.blue_title));
                txt_learn_top.setTextColor(getResources().getColor(R.color.white));
                txt_teacher_top.setTextColor(getResources().getColor(R.color.blue_title));
                break;
            case R.id.txt_teacher_top:
                pager_ranking.setCurrentItem(2);
                getTeacherRankingTop();
                txt_rich_top.setTextColor(getResources().getColor(R.color.blue_title));
                txt_learn_top.setTextColor(getResources().getColor(R.color.blue_title));
                txt_teacher_top.setTextColor(getResources().getColor(R.color.white));
                break;
            case R.id.txt_sent_gift:
//                SpannableString spannableString = ExpressionUtil.getGiftString(getContext(), R.mipmap.img_gift_pen, "[img_gift_pen]");
                //txt_gift_num.getText();

                if (!TextUtils.isEmpty(yjGiftModelList.get(mGiftListPosition).getPayFee()) && yjGiftModelList.get(mGiftListPosition).getPayFee().equals("Yes")) {
                    if (reclen == 0) {
                        reclen = 180;
                        flag = false;
                        new Thread(new MyThread()).start();
                        sendGift(yjGiftModelList.get(mGiftListPosition));
                        re_gift.setVisibility(View.GONE);
                        re_gift.setAnimation(AnimationUtils.moveToViewBottom(500));
                        isGiftShow = false;
                    } else {
                        showToast("请" + reclen + "秒之后再发送");
                    }
                } else {
                    sendGift(yjGiftModelList.get(mGiftListPosition));
                    re_gift.setVisibility(View.GONE);
                    re_gift.setAnimation(AnimationUtils.moveToViewBottom(500));
                    isGiftShow = false;
                }

//                HtSdk.getInstance().emit(MtConsts.QUESTION, YJGlobal.getYjUser().getPic(), msgCallback);

                //sendFlower();
                break;
            default:
                break;
        }
    }


    final Handler handler = new Handler() {          // handle
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (reclen > 0) {
                        reclen--;
                    } else if (reclen == 0) {
                        flag = true;
                    }
                    StringUtils.Log("reclen", reclen + "");
                    break;
            }
        }
    };


    public class MyThread implements Runnable {      // thread
        @Override
        public void run() {
            while (!flag) {
                try {
                    Thread.sleep(1000);     // sleep 1000ms
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (Exception e) {
                }
            }
        }
    }


    private void sendFlower() {
        if (HtConfig.isLiveIn()) {
            HtSdk.getInstance().sendFlower();
        } else {
            StringUtils.tip(getContext(), "还没上课");
        }
    }

    //发送消息
    private void sendMsg(String sendContent, Callback callback) {

        if (!TextUtils.isEmpty(sendContent)) {
            HtSdk.getInstance().emit(MtConsts.CHAT_SEND, sendContent, callback);
            input_edt.setText("");
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(input_edt.getWindowToken(), 0);
            re_intput.setVisibility(View.GONE);
            isInputShow = false;
        } else {
            //showToast("请输入内容");
        }
    }

    //发送礼物
    private void sendGiftMsg(YJGiftModel yjGiftModel, int mGiftNum, Callback callback) {

        if (yjGiftModel != null) {
            String mGiftId = yjGiftModel.getVirtualGiftId();
            String mGiftName = yjGiftModel.getName();

            String sendContent = "[gf_" + mGiftId + "_" + mGiftNum + "]" + "【" + YJGlobal.getYjUser().getNickName() + "】发送" + "【" + mGiftName + "】x" + mGiftNum;
            HtSdk.getInstance().emit(MtConsts.CHAT_SEND, sendContent, callback);
            input_edt.setText("");
        } else {
            //showToast("请输入内容");
        }
    }

    //发送红包
    private void sendRedMsg(String mWhaleNum) {

        if (!TextUtils.isEmpty(mWhaleNum) && !mWhaleNum.equals("0")) {
            String sendContent = "[red_msg]" + "【" + YJGlobal.getYjUser().getNickName() + "】抢了" + "【" + mWhaleNum + "鲸币】红包";
            HtSdk.getInstance().emit(MtConsts.CHAT_SEND, sendContent, msgCallback);
            input_edt.setText("");
        }
    }

    //发送点赞数量
    private void sendPraiseMsg(String mPraiseNum) {
        if (!TextUtils.isEmpty(mPraiseNum) && !mPraiseNum.equals("0")) {
            String sendContent = "[lcl_" + mPraiseNum + "]" + "【" + YJGlobal.getYjUser().getNickName() + "】点赞x" + mPraiseNum;
            HtSdk.getInstance().emit(MtConsts.CHAT_SEND, sendContent, mPraiseCallback);
            StringUtils.Log(TAG, "mPraiseNum=" + mPraiseNum);
        }
    }

    private Callback msgCallback = new Callback() {
        @Override
        public void success(Object result) {
            if (result != null) {
                JSONObject obj = (JSONObject) result;
                appendList(ChatEntity.onExplainChatMessage(obj));
            }
        }

        @Override
        public void failed(String failed) {
            if (!TextUtils.isEmpty(failed)) {
                StringUtils.tip(getContext(), failed);
            }
        }
    };
    private Callback mPraiseCallback = new Callback() {
        @Override
        public void success(Object result) {
        }

        @Override
        public void failed(String failed) {
        }
    };

    Timer timer = new Timer();

    private void appendList(final ChatEntity chatEntity) {

        StringUtils.Log(TAG, "chatEntity=" + chatEntity.toString());
        chatAdapter.appendList(chatEntity);

        if (chatLv.getCount() >= 4) {
            re_chat_list_up.setVisibility(View.GONE);
            StringUtils.Log(TAG, "appendList chatLv.getCount()=" + chatLv.getCount());
        } else {
            re_chat_list_up.setVisibility(View.VISIBLE);
        }
        if (chatLv != null)
            chatLv.setSelection(chatLv.getBottom());

        try {
            if (YJLiveRoomUtil.isGiftStr(chatEntity.getMsg())) {
                YJChatEntityModel yjChatEntityModel = new YJChatEntityModel();
                yjChatEntityModel.setMsg(chatEntity.getMsg());
                yjChatEntityModel.setAvatar(chatEntity.getAvatar());
                yjChatEntityModel.setNickname(chatEntity.getNickname());

                yjCustomChatAdapter.appendChatList(yjChatEntityModel);


                if (lv_gift_list_right != null) {
                    lv_gift_list_right.setSelection(lv_gift_list_right.getBottom());
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        timeHandler.sendEmptyMessage(1);
                    }
                }, 6000);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private int mGiftNum = 1;
    private boolean isStartShareDesktop = false;

    private void onVideoVisible() {
        if (isLiveStart) {
            if (HtSdk.getInstance().isVideoShow()) {
                HtSdk.getInstance().hideVideo();
                hideVideoContainer();
                videoVisibleIv.setImageResource(R.mipmap.video_off);
            } else {
                HtSdk.getInstance().showVideo();
                showVideoContainer();
                videoVisibleIv.setImageResource(R.mipmap.video_on);
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

    private boolean isFirst = true;

    @Override
    void show() {

        operationContainer.setVisibility(View.GONE);
        if (!isGiftShow) {
            titlebarContainer.setVisibility(View.VISIBLE);

            if (isNoticeShow) {
                re_notice.setVisibility(View.VISIBLE);
            }
        } else {
            re_gift.setVisibility(View.GONE);
            re_gift.setAnimation(AnimationUtils.moveToViewBottom(350));
            isGiftShow = false;

        }

        if (isInputShow) {
            re_notice.setVisibility(View.GONE);
            titlebarContainer.setVisibility(View.GONE);

            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(pptContainer.getWindowToken(), 0);
            //隐藏软键盘
            re_intput.setVisibility(View.GONE);
            isInputShow = false;
        }


    }

    private boolean isFistEnter = true;

    @Override
    void hide() {
        if (isGiftShow) {
            re_gift.setVisibility(View.GONE);
            re_gift.setAnimation(AnimationUtils.moveToViewBottom(350));
            isGiftShow = false;
        }
        titlebarContainer.setVisibility(View.GONE);
        operationContainer.setVisibility(View.GONE);

        if (isNoticeShow) {
            re_notice.setVisibility(View.GONE);
        }
        re_live_ranking.setVisibility(View.GONE);
        isRankingshow = true;

        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(pptContainer.getWindowToken(), 0);
        //隐藏软键盘
        re_intput.setVisibility(View.GONE);
        isInputShow = false;

    }


    /**
     * SDK初始化完成事件
     */
    @Override
    public void onLaunch() {
        roomInfo = HtSdk.getInstance().getRoomInfo();
        if (roomInfo != null) {
            tvMemberTotal.setText(roomInfo.getMemberTotal() + "人");
            tvZhubo.setText(teacherName);
            if (roomInfo.getAction().equals("wait")) {
                tvLiveTitle.setText(R.string.live_wait_title);
                // reservationCourseDetail();
                StringUtils.Log(TAG, "onLaunch");
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
        tvLiveTitle.setText(courseName);

        StringUtils.Log(TAG, "onLiveStart");
        notifyListener(YJNotifyTag.COLSE_DIALOG_RESERVATION, null);

        if (roomInfo.getNoticeEntity() != null) {
            dispatchNotice.getNotice(roomInfo.getNoticeEntity());
            re_notice.setVisibility(View.VISIBLE);

            isNoticeShow = true;
            titlebarContainer.setVisibility(View.VISIBLE);
            txt_notice_roll.setText(roomInfo.getNoticeEntity().getContent());

            StringUtils.Log(TAG, "roomInfo.getNoticeEntity()=" + roomInfo.getNoticeEntity().getContent());

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
        reservationCourseDetail(true);
        mIsShowDialog = true;
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
                YJCustomLiveActivity.this.finish();
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
        if (chatLv.getCount() >= 4) {
            re_chat_list_up.setVisibility(View.GONE);
            StringUtils.Log(TAG, "receiveChatMessage chatLv.getCount()=" + chatLv.getCount());
        } else {
            re_chat_list_up.setVisibility(View.VISIBLE);
        }

        if (chatMessageEntity != null) {
            try {
                if (YJLiveRoomUtil.isRedStr(chatMessageEntity.getMsg())) {
                    String msg = chatMessageEntity.getMsg();
                    StringUtils.Log(TAG, "chatMessage=" + chatMessageEntity.getMsg());
                    String content = msg.substring(msg.indexOf("]") + 1, msg.length());
                    chatMessageEntity.setMsg(content);
                } else if (YJLiveRoomUtil.isGiftStr(chatMessageEntity.getMsg())) {

                    YJChatEntityModel yjChatEntityModel = new YJChatEntityModel();
                    yjChatEntityModel.setMsg(chatMessageEntity.getMsg());
                    yjChatEntityModel.setAvatar(chatMessageEntity.getAvatar());
                    yjChatEntityModel.setNickname(chatMessageEntity.getNickname());

                    yjCustomChatAdapter.appendChatList(yjChatEntityModel);

                    StringUtils.Log(TAG, "yjCustomChatAdapter chatMessageEntity=" + chatMessageEntity.toString());
                    if (lv_gift_list_right != null) {
                        lv_gift_list_right.setSelection(lv_gift_list_right.getBottom());
                    }
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            timeHandler.sendEmptyMessage(1);
                        }
                    }, 6000);

                    String msg = chatMessageEntity.getMsg();
                    StringUtils.Log(TAG, "chatMessage=" + chatMessageEntity.getMsg());
                    String content = msg.substring(msg.indexOf("]") + 1, msg.length());
                    chatMessageEntity.setMsg(content);

                } else if (YJLiveRoomUtil.isPraiseStr(chatMessageEntity.getMsg())) {
                    int praiseNum = Integer.parseInt(YJLiveRoomUtil.getPraiseNum(chatMessageEntity.getMsg()));
                  /*  if (praiseNum >= 5){
                        praiseNum = 5;
                    }*/
                    for (int i = 0; i < praiseNum; i++) {
                        periscope.addHeart();
                    }
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            StringUtils.Log(TAG, "chatMessageEntity=" + chatMessageEntity.toString());
            chatAdapter.appendList(chatMessageEntity);
            if (chatLv != null)
                chatLv.setSelection(chatLv.getBottom());

        }
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
        //showToast(noticeEntity.getContent());
        txt_notice_roll.setText(noticeEntity.getContent());

        re_notice.setVisibility(View.VISIBLE);


        isNoticeShow = true;
        titlebarContainer.setVisibility(View.VISIBLE);

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
    @Override
    public void updateMemberTotal(int i) {

        StringUtils.Log(TAG, "i");
        tvMemberTotal.setText(i + "人");
    }

    //接收系统广播
    @Override
    public void receiverSystemMessage(Intent intent) {
        if (intent != null) {
            String type = intent.getStringExtra("type");
            StringUtils.Log(TAG, "type=" + type);
            Parcelable parcelable = intent.getParcelableExtra("msg");
            StringUtils.Log(TAG, "parcelable=" + parcelable.toString());
            StringUtils.Log(TAG, "type=" + type);
            switch (type) {
                case MtConsts.SYSTEM_BROADCAST_BROADCAST:
                    HtSystemBroadcastEntity entity = (HtSystemBroadcastEntity) parcelable;
                    StringUtils.Log(TAG, "entity.getMessage()=" + entity.getMessage());
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

    @Override
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
            }
        }
    }


    private List<YJGiftModel> yjGiftModelList;

    //获取礼物列表
    private void getGiftList() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_GIFT_LIST, null, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjGiftModelList = JSON.parseArray(jsonData.getString("virtualGiftList"), YJGiftModel.class);
                            if (yjGiftModelList != null && yjGiftModelList.size() > 0) {
                                YJGlobal.setYjGiftModelList(yjGiftModelList);

                                if (yjGiftModelList != null) {
                                    if (!TextUtils.isEmpty(yjGiftModelList.get(0).getPayFee()) && yjGiftModelList.get(0).getPayFee().equals("Yes")) {
                                        txt_gift_num.setText("1");
                                        mGiftNum = 1;
                                        img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_de);
                                        txt_gift_num_add.setEnabled(false);
                                        txt_gift_num_decrease.setEnabled(false);

                                    } else {
                                        if (mGiftNum == 1) {
                                            img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_de);
                                        } else {
                                            img_gift_num_bg.setImageResource(R.mipmap.img_gift_num_se);
                                        }
                                        txt_gift_num_add.setEnabled(true);
                                        txt_gift_num_decrease.setEnabled(true);
                                    }
                                }
                            }
                            if (hListViewAdapter == null) {
                                hListViewAdapter = new HorizontalListViewAdapter(getApplicationContext(), yjGiftModelList);
                                horizon_listview.setAdapter(hListViewAdapter);
                            } else {
                                hListViewAdapter.notifyDataSetChanged();
                            }
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
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

    //发送礼物
    private void sendGift(final YJGiftModel giftModel) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("incomeSideId", mTeacherId);
            objectMap.put("incomeSideType", "Teacher");
            objectMap.put("virtualGiftId", giftModel.getVirtualGiftId());
            objectMap.put("virtualGiftNum", Integer.parseInt((String) txt_gift_num.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        YJStudentReqManager.getServerData(YJReqURLProtocol.SEND_GIFT, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            String userCoin = jsonData.getString("customerCoin");
                            YJGlobal.getYjUser().setCoin(userCoin);

                            txt_whale_money.setText(userCoin);
                            if (giftModel != null) {
                                sendGiftMsg(giftModel, Integer.parseInt((String) txt_gift_num.getText()), msgCallback);
                            }
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            showToast("消费数量需大于0");
                            break;
                        case 402:
                            showToast("礼物不存在");
                            break;
                        case 403:
                            showToast("余额不足");
                            startActivity(YJRechargeWhaleMoney.class);
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //抢红包
    private void getRedInfo(String redId, String redBatch) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("redId", redId);
            objectMap.put("redBatch", redBatch);
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_RED_INFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            String redCoin = jsonData.getString("redCoin");
                            String mCoin = jsonData.getString("coin");

                            YJGlobal.getYjUser().setCoin(mCoin);
                            txt_whale_money.setText(mCoin);
                            YJLiveRedBagDialog yjLiveRedBagDialog = new YJLiveRedBagDialog();
                            VParams data = createTransmitData(YJLiveRedBagDialog.WHALE_NUM, redCoin);
                            showDialog(yjLiveRedBagDialog, data);
                            sendRedMsg(redCoin);
                            re_red.setVisibility(View.GONE);
                        case 300:
                            re_red.setVisibility(View.GONE);
                            break;
                        case 400:
                            YJLiveRedBagDialog b = new YJLiveRedBagDialog();
                            VParams c = createTransmitData(YJLiveRedBagDialog.WHALE_NUM, "");
                            showDialog(b, c);
                            re_red.setVisibility(View.GONE);
                            break;
                        case 401:

                            YJLiveRedBagDialog dialog = new YJLiveRedBagDialog();
                            VParams mData = createTransmitData(YJLiveRedBagDialog.WHALE_NUM, "");
                            showDialog(dialog, mData);
                            re_red.setVisibility(View.GONE);
                            break;
                        case 402:
                            YJLiveRedBagDialog di = new YJLiveRedBagDialog();
                            VParams datas = createTransmitData(YJLiveRedBagDialog.WHALE_NUM, "");
                            showDialog(di, datas);
                            re_red.setVisibility(View.GONE);
                            break;
                        case 500:
                            YJLiveRedBagDialog d = new YJLiveRedBagDialog();
                            VParams a = createTransmitData(YJLiveRedBagDialog.WHALE_NUM, "");
                            showDialog(d, a);
                            re_red.setVisibility(View.GONE);

                            break;
                        case 600:
                            re_red.setVisibility(View.GONE);
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //获得课程评分详情
    private void courseScoreDetail() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.COURSE_SCORE_DETAIL, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));

                            JSONObject jsonObject = jsonData.getJSONObject("courseScoreRecord");
                            String scoreId = jsonObject.getString("scoreId");
                            String commentScore = jsonObject.getString("commentScore");
                            String commentContent = jsonObject.getString("commentContent");
                            VParams data = createTransmitData(YJEvaluationOverDialog.SCORE, commentScore).set(YJEvaluationOverDialog.SCOREREMARK, commentContent);

                            YJEvaluationOverDialog yjEvaluationOverDialog = new YJEvaluationOverDialog();
                            showDialog(yjEvaluationOverDialog, data);
                        case 300:
                            break;
                        case 400:
                            YJEvaluationDialog yjEvaluationDialog = new YJEvaluationDialog();
                            showDialog(yjEvaluationDialog);
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //推荐课程列表
    private void recommendCourseList() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseVideoId", courseVideoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_RECOMMEND, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));

                            yjCourseModelList = JSON.parseArray(jsonData.getString("courseList"), YJCourseModel.class);
                            YJRecommendCourseDialog yjRecommendCourseDialog = new YJRecommendCourseDialog();

                            VParams data = createTransmitData(yjRecommendCourseDialog.RECOMMEND_COURSE_MODEL_LIST, yjCourseModelList).set(yjRecommendCourseDialog.COURSE_VIDEOID, courseVideoId);
                            showDialog(yjRecommendCourseDialog, data);
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    //课程评分
    private void courseScore(String scoreRemark, String score) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseId", courseId);
            objectMap.put("score", score);
            objectMap.put("scoreRemark", scoreRemark);
        } catch (Exception e) {
            e.printStackTrace();
        }

        YJStudentReqManager.getServerData(YJReqURLProtocol.COURSE_SCORE, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));

                            showToast("评分成功");
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //直播用户预约情况
    private void reservationCourseDetail(final boolean isLiveOver) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_LIVE_RESERVATIONINFO, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjCourseModelReservationList = JSON.parseArray(jsonData.getString("courseList"), YJCourseModel.class);

                            String isReservation = jsonData.getString("isReservation");
                            long currentDate = jsonData.getLong("currentDate");

                            if (TextUtils.isEmpty(startTime)) {
                                startTime = currentDate + "";
                            }

                            long difTime = Long.parseLong(startTime) - currentDate;
                            if (TextUtils.isEmpty(endTime)) {
                                endTime = currentDate + "";
                            }
                            if (isLiveOver) {
                                difTime = 3 * 60 * 60 * 1000 - (currentDate - Long.parseLong(endTime));
                            }

                            StringUtils.Log(TAG, "currentDate=" + currentDate);
                            StringUtils.Log(TAG, "startTime=" + startTime);
                            StringUtils.Log(TAG, "endTime=" + endTime);


                            String android_key = "";
                            String qqGroup = "";
                            String qqGroupInfo = jsonData.getString("qqGroupInfo");
                            if (!TextUtils.isEmpty(qqGroupInfo)) {
                                YJQQGroupInfoModel yjqqGroupInfoModel = JSON.parseObject(jsonData.getString("qqGroupInfo"), YJQQGroupInfoModel.class);

                                if (yjqqGroupInfoModel != null) {
                                    android_key = yjqqGroupInfoModel.getAndroid_key();
                                    qqGroup = yjqqGroupInfoModel.getQQGroupNumber();
                                }
                            }

                            StringUtils.Log(TAG, "difTime=" + difTime);

                            if (HtSdk.getInstance().getRoomInfo() != null) {
                                StringUtils.Log(TAG, "roomInfo.getMemberTotal()=" + HtSdk.getInstance().getRoomInfo().getMemberTotal());

                                YJReservationCourseDialog yjReservationCourseDialog = new YJReservationCourseDialog();
                                VParams data = createTransmitData(yjReservationCourseDialog.TEACHER_NAME, teacherName).set(yjReservationCourseDialog.COURSEVIDEOID, courseVideoId)
                                        .set(yjReservationCourseDialog.COURSEID, courseId).set(yjReservationCourseDialog.RESERVATION_COURSE_MODEL_LIST, yjCourseModelReservationList).set(yjReservationCourseDialog.USER_NUM, HtSdk.getInstance().getRoomInfo().getMemberTotal() + "人")
                                        .set(yjReservationCourseDialog.ISRESERVATION, isReservation).set(yjReservationCourseDialog.DIF_TIME, difTime).set(yjReservationCourseDialog.ANDROID_KEY, android_key).set(yjReservationCourseDialog.QQ_GROUP, qqGroup)
                                        .set(yjReservationCourseDialog.COURSECATALOGID, courseCatalogId).set(yjReservationCourseDialog.IS_LIVE_OVEW, isLiveOver).set(yjReservationCourseDialog.COURSE_TITLE,courseName);
                                showDialog(yjReservationCourseDialog, data);
                            }
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
                            startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                            finishAll();
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //直播点赞接口
    private void livePraise(final int clickLikeCount) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("clickLikeCount", clickLikeCount);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.PRAISE_CLICK_LIVE, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            StringUtils.Log(TAG, "点赞成功" + clickLikeCount);
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<YJUser> yjStudyUserList;

    //直播排行榜--学霸榜
    private void getStudyRankingTop() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.LIVE_RANKING_STUDY, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjStudyUserList = JSON.parseArray(jsonData.getString("studyTimesRankingList"), YJUser.class);
                            notifyListener(YJNotifyTag.STUDY_RANKING_TOP, yjStudyUserList);
                            StringUtils.Log(TAG, "yjStudyUserList=" + yjStudyUserList);
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<YJUser> yjRichUserList;

    //直播排行榜--富豪榜
    private void getRichRankingTop() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.LIVE_RANKING_RICH, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjRichUserList = JSON.parseArray(jsonData.getString("richRankingList"), YJUser.class);
                            notifyListener(YJNotifyTag.RICH_RANKING_TOP, yjRichUserList);
                            StringUtils.Log(TAG, "yjRichUserList=" + yjRichUserList);
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private List<YJTeacherModel> yjTeacherModelList;

    //直播排行榜--名师榜
    private void getTeacherRankingTop() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.LIVE_RANKING_TEACHER, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjTeacherModelList = JSON.parseArray(jsonData.getString("teacherRankingList"), YJTeacherModel.class);
                            notifyListener(YJNotifyTag.TEACHER_RANKING_TOP, yjTeacherModelList);
                            StringUtils.Log(TAG, "yjTeacherModelList=" + yjTeacherModelList);
                        case 300:
                            break;
                        case 400:
                            break;
                        case 401:
                            break;
                        case 402:
                            break;
                        case 403:
                            break;
                        case 500:
                            break;
                        case 600:
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public Fragment[] createPageViewFragments(VPageView vPageView) {
        return new Fragment[]{startVirtualActivity(new YJRichRankingTopActivity()), startVirtualActivity(new YJStudyOverlordTopActivity()), startVirtualActivity(new YJGoodTeacherTopActivity())};
    }

    @Override
    public void onPageViewChanged(VPageView vPageView, int i, int i1) {

    }

    @Override
    public void receiveBroadcast(JSONObject jsonObject) {
        if (jsonObject != null) {
            try {
                StringUtils.Log(TAG, "jsonObject=" + jsonObject.toString());

                JSONObject json = jsonObject.getJSONObject("args");
                redId = json.getString("redId");
                redBatch = json.getString("redBatch");

                StringUtils.Log(TAG, "redId=" + redId);
                StringUtils.Log(TAG, "redBatch=" + redBatch);

                if (!TextUtils.isEmpty(redId) && !TextUtils.isEmpty(redBatch)) {
                    re_red.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private void bindNotifyListener() {
        addListener(YJNotifyTag.COURSE_SCORE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String content = (String) value;

                String score = content.substring(0, content.indexOf("_"));
                String scoreRemark = content.substring((content.indexOf("_") + 1), content.length());

                StringUtils.Log(TAG, "score=" + score);
                StringUtils.Log(TAG, "scoreRemark=" + scoreRemark);

                courseScore(scoreRemark, score);
            }
        });
        addListener(YJNotifyTag.PAY_SUCESS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                YjGetUserInfo.getUserCoin((IVActivity) getContext(), txt_whale_money);
            }
        });

    }


    private boolean isComment() {

        long currentDate = System.currentTimeMillis();

        StringUtils.Log(TAG, "currentDate" + currentDate);

        if (!TextUtils.isEmpty(endTime)) {
            long time = Long.parseLong(endTime) - currentDate;
            StringUtils.Log(TAG, "endTime" + endTime);
            if (time <= 10 * 60 * 1000) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


}
