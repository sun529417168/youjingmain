package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;

import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.util.StringUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lecloud.download.control.DownloadCenter;
import com.lecloud.entity.ActionInfo;
import com.lecloud.entity.LiveInfo;
import com.lecloud.entity.LiveStatus;
import com.lecloud.entity.LiveStatusCallback;
import com.lecloud.leutils.LeLog;
import com.lecloud.log.KLog;
import com.lecloud.volley.VolleyError;
import com.letv.controller.LetvPlayer;
import com.letv.controller.PlayContext;
import com.letv.controller.PlayProxy;
import com.letv.controller.imp.LetvPlayerControllerImp;
import com.letv.controller.interfacev1.ILetvPlayerController;
import com.letv.controller.interfacev1.ISplayerController;
import com.letv.skin.interfacev1.IActionCallback;
import com.letv.skin.utils.UIPlayContext;
import com.letv.skin.v4.V4PlaySkin;
import com.letv.universal.iplay.EventPlayProxy;
import com.letv.universal.iplay.ISplayer;
import com.letv.universal.iplay.OnPlayStateListener;
import com.letv.universal.play.util.PlayerParamsHelper;
import com.letv.universal.widget.ILeVideoView;
import com.letv.universal.widget.ReSurfaceView;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.adapter.CourseRecomendBookAdapter;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJUserStudyData;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.model.YJSubjectModel;
import com.youjing.yjeducation.talkfun.NetMonitor;
import com.youjing.yjeducation.ui.actualize.activity.YJBuyCourseActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJMainActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJRechargeWhaleMoney;
import com.youjing.yjeducation.ui.actualize.dialog.YJLoginDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJShareDialog;
import com.youjing.yjeducation.ui.dispaly.dialog.AYJCourseExpiredDialog;
import com.youjing.yjeducation.util.ACache;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DES;
import com.youjing.yjeducation.util.DialogUtil;
import com.youjing.yjeducation.util.LetvParamsUtils;
import com.youjing.yjeducation.util.MyRequestUtils;
import com.youjing.yjeducation.util.PlayerAssistant;
import com.youjing.yjeducation.util.PlayerFactory;
import com.youjing.yjeducation.util.PlayerSkinFactory;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.util.YjGetUserInfo;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.IVActivity;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;
import org.vwork.utils.base.VParams;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * user  秦伟宁
 * Date 2016/3/5
 * Time 15:25
 */
@VLayoutTag(R.layout.activity_play_new)
public abstract class AYJCoursePlayNewActivity extends BaseSwipeBackActivity implements IVClickDelegate, IVAdapterDelegate,YjGetUserInfo.UserCoin {

    @VViewTag(R.id.btn_apply)
    private Button mBtn_apply;
    @VViewTag(R.id.img_play_return)
    private ImageView mImg_play_return;
    @VViewTag(R.id.img_play_bg)
    private ImageView img_play_bg;
    @VViewTag(R.id.img_play_start_de)
    private ImageView mImg_play_start_de;
    @VViewTag(R.id.re_play_start_de)
    private RelativeLayout mRe_play_start_de;
    @VViewTag(R.id.videobody)
    private V4PlaySkin v4PlaySkin;
    @VViewTag(R.id.re_advice_phone)
    private RelativeLayout re_advice_phone;
    @VViewTag(R.id.re_advice_qq)
    private RelativeLayout re_advice_qq;
    @VViewTag(R.id.img_advice_phone)
    private ImageView img_advice_phone;
    @VViewTag(R.id.img_advice_qq)
    private ImageView img_advice_qq;
    @VViewTag(R.id.ll_bottom)
    private LinearLayout ll_bottom;


    protected Dialog progDialog;
    protected boolean isPlayFlag = false;
    protected boolean mFlag = true;
    private String TAG = "AYJCoursePlayNewActivity";
    public final static String DATA = "data";
    protected boolean mIsLogin;
    protected int mPosition = -1;

    protected boolean isYJCustomLiveActivityDestroy = true;
    //记录上一次点击的位置
    protected int mLastPosition = -1;
    public static final VParamKey<YJCourseModel> YJ_COURSE_MODEL = new VParamKey<YJCourseModel>(null);
    public static final VParamKey<String> CATALOGID = new VParamKey<String>(null);
    protected String catalogId;//播放的item的ID
    protected int indexLogId = -1;

    //判断是否是第一次播放记录学生观看行为时使用
    protected boolean isFirstPlay = true;
    private LetvsSimplePlayBoard playBoard;
    private MyAdapter adapter = null;
    private VAdapter mVAdapter;
    private Bundle bundle;
    private ISplayer iSplayer;
    protected YJCourseModel mYjCourseModel;
    private Bitmap NO_LOAD_BITMAP;
    private boolean isTry = true;
    private Timer timer;
    private boolean isFirst = true;
    private List<YJGradeModel> list;
    private List mGradeList;
    private int mGrade, mSubject;
    private List<YJSubjectModel> mSubjectModelList;
    private List subjectList;
    protected List<YJCourseModel> mYjCourseModelList;
    private int viewId[] = {R.mipmap.course_detail_info, R.mipmap.course_detail_phone};

    private int playPosition = 0;
    private int mplayPosition = 0;
    private IUiListener qqShareListener;
    private static final String ACTION_NAME = "发送广播";
    private ACache mCache;
    private String isScan  = "";

    private CourseRecomendBookAdapter courseRecomendBookAdapter;
    protected GridView gridview;
    private ListView mLv_course_detail;
    private ListView view_course_related_list;
    private ImageView imageView;
    private List<View> lists = new ArrayList<View>();
    private ViewPagerAdapter viewPagerAdapter;
    private Bitmap cursor;
    private int offSet;
    private int currentItem;
    private Matrix matrix = new Matrix();
    private int bmWidth;
    private Animation animation;
    private LinearLayout mbook_foot_item;
    @VViewTag(R.id.textView1)
    private TextView textView1;
    @VViewTag(R.id.textView2)
    private TextView textView2;
    @VViewTag(R.id.textView3)
    private TextView textView3;
    @VViewTag(R.id.view_line_one)
    private View view_line_one;
    @VViewTag(R.id.view_line_two)
    private View view_line_two;
    @VViewTag(R.id.view_line_three)
    private View view_line_three;
    @VViewTag(R.id.viewPager)
    private ViewPager viewPager;


    private LayoutInflater inflater;
    private YjGetUserInfo yjGetUserInfo;
    protected String coin;


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0: {
                    mImg_play_start_de.setVisibility(View.INVISIBLE);
                    break;
                }
                case 1: {
                    mImg_play_start_de.setVisibility(View.VISIBLE);
                    break;
                }
                case 2: {
                    if (playBoard != null) {
                        ISplayer player = playBoard.getPlayer();
                        if (player != null) {
                            player.pause();
                            //  showToast("试听已结束，请购买课程后观看");
                            mImg_play_start_de.setVisibility(View.VISIBLE);
                            mRe_play_start_de.setVisibility(View.VISIBLE);
                            isTry = false;
                        }
                    }
                    break;
                }
                case 4: {
                    AYJCourseExpiredDialog yjCourseExpiredDialog = new AYJCourseExpiredDialog();
                    VParams data = createTransmitData(AYJCourseExpiredDialog.TXT_INFO, "请续费后观看");
                    showDialog(yjCourseExpiredDialog, data);
                    break;
                }
                case 5: {
                    AYJCourseExpiredDialog yjCourseExpiredDialog = new AYJCourseExpiredDialog();
                    VParams data = createTransmitData(AYJCourseExpiredDialog.TXT_INFO, "请购买其他课程");
                    showDialog(yjCourseExpiredDialog, data);
                    break;
                }
                case 6: {
                    startPlay();
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    public void getCoin(IVActivity view, YJUser mYjUser) {
        coin = mYjUser.getCoin();
        if (Integer.parseInt(coin) >= mYjCourseModel.getPayCoin()) {
            goToBuyCourse();
        } else {
            goToRechaarge(true);
        }
    }
    @Override
    public void onClick(View view) {
        if (view == textView1) {
            viewPager.setCurrentItem(0);
        } else if (view == textView2) {
            viewPager.setCurrentItem(1);
        } else if (view == textView3) {
            viewPager.setCurrentItem(2);
        } else if (view == re_advice_phone) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            callPhone();
        } else if (view == img_advice_phone) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            callPhone();
        } else if (view == re_advice_qq) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            callQQ();
        } else if (view == img_advice_qq) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            callQQ();
        } else if (view == mBtn_apply) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            buyCourse();
        } else if (view == mImg_play_return) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            finish();
        } else if (view == mRe_play_start_de) {

        } else if (view == mImg_play_start_de) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startPlay();
        }

    }

    private void startPlay() {
        positionFlag++;

        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
        if (mIsLogin) {
            if (mYjCourseModel.getCourseCatalogList().get(select).getCourseVideoShape().equals("DVR")) {
                if (!mYjCourseModel.getCourseCatalogList().get(select).getCourseVideoStatus().equals("Normal")) {
                    showToast("课程未上传");
                    return;
                }
            }
            if (mYjCourseModel.getCourseCatalogList().get(select).getCourseVideoShape().equals("DVR")) {
                if (!TextUtils.isEmpty(mYjCourseModel.getCourseServiceStatus()) && mYjCourseModel.getCourseServiceStatus().equals("OVER")) {
                    if (!TextUtils.isEmpty(mYjCourseModel.getCourseStatus()) && mYjCourseModel.getCourseStatus().equals("SELLING")) {
                        if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                            yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                        } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                            goToBuyCourse();
                            //startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                        }
                    }
                } else {
                    if (mYjCourseModel.getIsBuy().equals("Yes") || mYjCourseModel.getCoursePayWay().equals("FREE")) {
                        if (playerController != null) {
                            if (playerController.getIsPlayerController().isPlayCompleted()) {
                                playerController.getIsPlayerController().resetPlay();
                                mImg_play_start_de.setVisibility(View.GONE);
                                mRe_play_start_de.setVisibility(View.GONE);
                                img_play_bg.setVisibility(View.GONE);
                            } else {
                                if (playBoard != null) {
                                    iSplayer = playBoard.getPlayer();
                                    if (iSplayer == null) {
                                        showToast("暂无视频");
                                        return;
                                    } else {
                                        isPlayFlag = true;
                                        long duration = Long.parseLong(mYjCourseModel.getLastStudyTime());
                                        StringUtils.Log(TAG, "duration=" + duration);
                                        StringUtils.Log(TAG, "iSplayer.getDuration()=" + iSplayer.getDuration());
                                        // if (duration *1000 < iSplayer.getDuration()) {
                                        iSplayer.seekTo(duration * 1000);
                                        //    }
                                        iSplayer.start();
                                        isFirst = false;
                                        mImg_play_start_de.setVisibility(View.GONE);
                                        mRe_play_start_de.setVisibility(View.GONE);
                                        img_play_bg.setVisibility(View.GONE);
                                        YJUserStudyData.catalogBeginStudy(mYjCourseModel, playPosition, true);

                                    }
                                } else {
                                    showToast("暂无视频");
                                    return;
                                }
                            }
                        }
                    } else {
                        int i = 0;
                        String num = mYjCourseModel.getLastNumber();
                        if (Integer.parseInt(num) == 0) {
                            i = Integer.parseInt(num);
                        } else {
                            i = Integer.parseInt(num) - 1;
                        }
                        if (mYjCourseModel.getCourseCatalogList().get(select).getIsTry().equals("Yes")) {
                            if (isTry) {
                                if (playBoard != null) {
                                    iSplayer = playBoard.getPlayer();

                                    if (iSplayer == null) {
                                        showToast("暂无视频");
                                        return;
                                    } else {
                                        iSplayer.start();
                                        isPlayFlag = true;
                                        long duration = Long.parseLong(mYjCourseModel.getLastStudyTime());
                                        StringUtils.Log(TAG, "duration=" + duration);
                                        StringUtils.Log(TAG, "iSplayer.getDuration()=" + iSplayer.getDuration());
                                        //  if (duration < iSplayer.getDuration()) {
                                        iSplayer.seekTo(duration * 1000);
                                        //  }

                                        StringUtils.Log(TAG, "iSplayer.getDuration()=" + iSplayer.getDuration());
                                        mImg_play_start_de.setVisibility(View.GONE);
                                        mRe_play_start_de.setVisibility(View.GONE);
                                        img_play_bg.setVisibility(View.GONE);
                                        isFirst = false;
                                        YJUserStudyData.catalogBeginStudy(mYjCourseModel, playPosition, true);

                                        // pause();
                                    }
                                } else {
                                    showToast("暂无视频");
                                    return;
                                }
                            } else {
                                //试听结束后操作
                                if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                    yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                                } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                    goToBuyCourse();
                                    // startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                                }
                            }

                        } else {
                            if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                            } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                goToBuyCourse();
                                //startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                            }
                        }
                    }
                }
            } else {
                if (!TextUtils.isEmpty(mYjCourseModel.getCourseServiceStatus()) && mYjCourseModel.getCourseServiceStatus().equals("OVER")) {
                    if (!TextUtils.isEmpty(mYjCourseModel.getCourseStatus()) && mYjCourseModel.getCourseStatus().equals("SELLING")) {
                        if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                            yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                        } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                            goToBuyCourse();
                            // startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                        }
                    }
                } else {
                    if (mYjCourseModel.getCoursePayWay().equals("FREE") || mYjCourseModel.getIsBuy().equals("Yes")) {
                        mOnPause = false;
                        if (!isYJCustomLiveActivityDestroy) {
                            showToast("请稍后进入直播间");
                            return;
                        }
                        if (mYjCourseModel.getCourseCatalogList().get(select).getLiveStatus().equals("ing")) {
                            if (!isFirstPlay) {
                                YJUserStudyData.catalogEndStudyNoP(mYjCourseModel, mplayPosition);
                            }
                            if (isYJCustomLiveActivityDestroy) {
                                getTakenLive(select, false, 1);
                            } else {
                                showToast("请稍后进入直播间");
                            }
                        }
                        if (mYjCourseModel.getCourseCatalogList().get(select).getLiveStatus().equals("over")) {
                            if (!isFirstPlay) {
                                YJUserStudyData.catalogEndStudyNoP(mYjCourseModel, mplayPosition);
                            }

                            if ("Yes".equals(mYjCourseModel.getCourseCatalogList().get(select).getIsReplay())) {
                                getTakenBack(select);
                            }
                            if ("No".equals(mYjCourseModel.getCourseCatalogList().get(select).getIsReplay())) {
//                                showDialog(new YJIsReplayDialog());
                                getTakenLive(select, true, 2);//true 代表是否弹出预约页面， 1代表弹直播未开始，2代表弹出直播已结束
                            }
                        }
                        if (mYjCourseModel.getCourseCatalogList().get(select).getLiveStatus().equals("no")) {
//                                showToast("直播未开始");
                            getTakenLive(select, true, 1);
                        }
                    } else {
                        if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                            yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                        } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                            goToBuyCourse();
                            // startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                        }
                    }
                }
            }

        } else {
            //  YJLoginDialog dialog = new YJLoginDialog();
            //  showDialog(dialog);
            startActivity(YJLoginDialog.class);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCache = ACache.get(this);
        yjGetUserInfo = new YjGetUserInfo(this,this);
        if (SharedUtil.getInteger(getContext(), "baseIndex", 0) != -1) {
            SharedUtil.setInteger(getContext(), "baseIndex", SharedUtil.getInteger(getContext(), "baseIndex", 0) + 1);
        }
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageView = (ImageView) findViewById(R.id.cursor);
        lists.add(getLayoutInflater().inflate(R.layout.view_course_directories, null));
        lists.add(getLayoutInflater().inflate(R.layout.view_course_details, null));
        lists.add(getLayoutInflater().inflate(R.layout.view_course_related, null));
        //   initeCursor();

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);

        mbook_foot_item = (LinearLayout) inflater.inflate(R.layout.item_course_realted_layout, null);
        gridview = (GridView) mbook_foot_item.findViewById(R.id.item_course_realted_layoutGridview);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));


        StringUtils.setGuidImage(this, R.id.activity_play_new_layout, viewId, "FirstPlayNew");
        String courseId = getIntent().getStringExtra("courseId");
        catalogId = getIntent().getStringExtra("catalogId");
        isScan = getIntent().getStringExtra("isScan");
        if (NetMonitor.isNetworkAvailable(this)) {
            progDialog = DialogUtil.showWaitDialog(getContext());//加载数据的loading 先注释掉 用的时候换样式
            getCourseCatalogDetail(courseId);
        } else {
            mYjCourseModel = (YJCourseModel) mCache.getAsObject("mYjCourseModel" + courseId);
            if (mYjCourseModel == null) {
                findViewById(R.id.activity_play_new_nothing).setVisibility(View.VISIBLE);
                findViewById(R.id.ll_bottom).setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
            } else {
                requestInit();
            }

        }
    }

    private void goToBuyCourse() {
        Intent intent = new Intent(getContext(), YJBuyCourseActivity.class);
        intent.putExtra("courseId", mYjCourseModel.getCourseId());
        startActivity(intent);
    }

    private void goToRechaarge(boolean visileFlag) {
        Intent intent = new Intent(getContext(), YJRechargeWhaleMoney.class);
        intent.putExtra("courseId", mYjCourseModel.getCourseId());
        intent.putExtra("visileFlag", visileFlag);
        startActivity(intent);
    }

    public void getCourseCatalogDetail(final String courseId) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_CATALOG, objectMap, mIsLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
                if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                    progDialog.dismiss();
                }
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
                            JSONObject jsonObject = new JSONObject(json.getString("data"));
                            mYjCourseModel = JSON.parseObject(jsonObject.getString("course"), YJCourseModel.class);
                            mCache.put("mYjCourseModel" + courseId, mYjCourseModel);
                            StringUtils.Log("视频列表",mYjCourseModel.getCourseCatalogList().toString());
                            requestInit();
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
                if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                    progDialog.dismiss();
                }
            }
        });
    }

    private void requestInit() {
        viewPagerAdapter = new ViewPagerAdapter(lists);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            // 当滑动式，顶部的imageView是通过animation缓慢的滑动
            @Override
            public void onPageSelected(int arg0) {
                switch (arg0) {
                    case 0:
                        if (currentItem == 1) {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 0, 0, 0);
                        } else if (currentItem == 2) {
                            animation = new TranslateAnimation(offSet * 4 + 2
                                    * bmWidth, 0, 0, 0);
                        }
                        textView1.setTextColor(getResources().getColor(R.color.black));
                        textView2.setTextColor(getResources().getColor(R.color.gray_text));
                        textView3.setTextColor(getResources().getColor(R.color.gray_text));
                        view_line_one.setBackgroundColor(getResources().getColor(R.color.blue_course_play));
                        view_line_two.setBackgroundColor(getResources().getColor(R.color.white));
                        view_line_three.setBackgroundColor(getResources().getColor(R.color.white));

                        break;
                    case 1:
                        if (currentItem == 0) {
                            animation = new TranslateAnimation(0, offSet * 2
                                    + bmWidth, 0, 0);
                        } else if (currentItem == 2) {
                            animation = new TranslateAnimation(4 * offSet + 2
                                    * bmWidth, offSet * 2 + bmWidth, 0, 0);
                        }
                        textView2.setTextColor(getResources().getColor(R.color.black));
                        textView1.setTextColor(getResources().getColor(R.color.gray_text));
                        textView3.setTextColor(getResources().getColor(R.color.gray_text));
                        view_line_two.setBackgroundColor(getResources().getColor(R.color.blue_course_play));
                        view_line_one.setBackgroundColor(getResources().getColor(R.color.white));
                        view_line_three.setBackgroundColor(getResources().getColor(R.color.white));
                        break;
                    case 2:
                        if (currentItem == 0) {
                            animation = new TranslateAnimation(0, 4 * offSet + 2
                                    * bmWidth, 0, 0);
                        } else if (currentItem == 1) {
                            animation = new TranslateAnimation(
                                    offSet * 2 + bmWidth, 4 * offSet + 2 * bmWidth,
                                    0, 0);
                        }
                        textView3.setTextColor(getResources().getColor(R.color.black));
                        textView2.setTextColor(getResources().getColor(R.color.gray_text));
                        textView1.setTextColor(getResources().getColor(R.color.gray_text));
                        view_line_three.setBackgroundColor(getResources().getColor(R.color.blue_course_play));
                        view_line_two.setBackgroundColor(getResources().getColor(R.color.white));
                        view_line_one.setBackgroundColor(getResources().getColor(R.color.white));
                }
                currentItem = arg0;
                StringUtils.Log(TAG, "currentItem==" + currentItem);
                animation.setDuration(150); // 光标滑动速度
                animation.setFillAfter(true);
                imageView.startAnimation(animation);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        if (mYjCourseModel.getRecommendBookList() != null) {
            if (courseRecomendBookAdapter == null) {
                courseRecomendBookAdapter = new CourseRecomendBookAdapter(this, getContext(), mYjCourseModel.getRecommendBookList(), mIsLogin);
                gridview.setAdapter(courseRecomendBookAdapter);
            } else {
                courseRecomendBookAdapter.notifyDataSetChanged();
            }
        }

        if (mYjCourseModel != null) {
            YJGlobal.setYjCourseModel(mYjCourseModel);
            StringUtils.Log(TAG, "mYjCourseModel=" + mYjCourseModel.toString());

            if (!TextUtils.isEmpty(isScan)) {
                if (!TextUtils.isEmpty(mYjCourseModel.getIsBuy()) && mYjCourseModel.getIsBuy().equals("No") && !mYjCourseModel.getCoursePayWay().equals("FREE")) {
                    buyCourse();
                }
            }

            if (!TextUtils.isEmpty(catalogId)) {
                for (int i = 0; i < mYjCourseModel.getCourseCatalogList().size(); i++) {
                    if (catalogId.equals(mYjCourseModel.getCourseCatalogList().get(i).getCourseCatalogId())) {
                        indexLogId = i;
                        StringUtils.Log("如果等于===", "mYjCourseModel=" + indexLogId);
                    }
                }
            }
            initData(1);
        }
    }


    private void initData(final int position) {
        timer = new Timer();
        if (!TextUtils.isEmpty(mYjCourseModel.getCourseServiceStatus())) {
            if (mYjCourseModel.getCourseServiceStatus().equals("OVER")) {
                if (!TextUtils.isEmpty(mYjCourseModel.getCourseStatus())) {
                    if (mYjCourseModel.getCourseStatus().equals("SELLING")) {
                        timer.schedule(new TimerTask() {
                            public void run() {
                                mHandler.sendEmptyMessage(4);
                            }
                        }, 600);

                    } else {
                        timer.schedule(new TimerTask() {
                            public void run() {
                                mHandler.sendEmptyMessage(5);
                            }
                        }, 600);
                    }
                } else {
                    timer.schedule(new TimerTask() {
                        public void run() {
                            mHandler.sendEmptyMessage(5);
                        }
                    }, 600);
                }
            }
        } else {
        }
        qqShareListener = new IUiListener() {
            @Override
            public void onCancel() {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast("分享取消");
            }

            @Override
            public void onComplete(Object response) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "sucess");
                // showToast(getString(R.string.share_success));
            }

            @Override
            public void onError(UiError e) {
                notifyListener(YJNotifyTag.SHARE_SUCESS_INFO, "fail");
                showToast(getString(R.string.share_fail));
            }
        };
        bindNotifyListener();

        if (mYjCourseModel != null) {


            if (mYjCourseModel.getLastNumber().equals("0") || mYjCourseModel.getLastNumber().equals("1")) {
                mPosition = 0;
            } else {
                mPosition = Integer.parseInt(mYjCourseModel.getLastNumber()) - 1;
            }
            if (mIsLogin) {
                if (!TextUtils.isEmpty(mYjCourseModel.getCourseServiceStatus()) && mYjCourseModel.getCourseServiceStatus().equals("OVER")) {
                    if (position == 1) {
                        viewPager.setCurrentItem(1);
                    }
                    if (!TextUtils.isEmpty(mYjCourseModel.getCourseStatus()) && mYjCourseModel.getCourseStatus().equals("SELLING")) {
                        ll_bottom.setVisibility(View.VISIBLE);
                    } else {
                        ll_bottom.setVisibility(View.VISIBLE);
                        ll_bottom.setBackgroundColor(getResources().getColor(R.color.gray_text));
                        mBtn_apply.setTextColor(getResources().getColor(R.color.black));
                    }
                } else {
                    if (mYjCourseModel.getCoursePayWay().equals("FREE")) {
                        if (position == 1) {
                            viewPager.setCurrentItem(0);
                        }
                        ll_bottom.setVisibility(View.GONE);
                    } else if (mYjCourseModel.getIsBuy().equals("Yes")) {
                        if (position == 1) {
                            viewPager.setCurrentItem(0);
                        }
                        ll_bottom.setVisibility(View.GONE);
                    } else if (mYjCourseModel.getIsBuy().equals("No")) {
                        if (position == 1) {
                            viewPager.setCurrentItem(1);
                        }
                        ll_bottom.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (position == 1) {
                    viewPager.setCurrentItem(1);
                }
                if (mYjCourseModel.getCoursePayWay().equals("FREE")) {
                    ll_bottom.setVisibility(View.GONE);
                } else if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                    ll_bottom.setVisibility(View.VISIBLE);
                } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                    ll_bottom.setVisibility(View.VISIBLE);
                }


            }
            if (!TextUtils.isEmpty(mYjCourseModel.getCoursePic())) {
                BitmapUtils.create(getContext()).display(img_play_bg, mYjCourseModel.getCoursePic());
            } else {
                img_play_bg.setImageResource(R.mipmap.img_no_data_bg);
            }

            for (int i = 0; i < mYjCourseModel.getCourseCatalogList().size(); i++) {
                try {
                    StringUtils.Log(TAG, "uu=" + DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(i).getCode()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (position == 1) {
                if (playBoard != null) {
                    playBoard.onDestroy();
                    playBoard = new LetvsSimplePlayBoard();
                } else {
                    playBoard = new LetvsSimplePlayBoard();
                }
            }
            StringUtils.Log(TAG, "mYjCourseModel.getLastNumber()=" + mYjCourseModel.getLastNumber());
            if (position == 2) {
                viewPager.setAdapter(viewPagerAdapter);
                viewPager.setCurrentItem(currentItem);
            }

            if (indexLogId == -1) {
                if (mYjCourseModel.getLastNumber().equals("0")) {
                    try {
                        if (position == 1) {
                            if (mYjCourseModel.getCourseCatalogList().get(0).getCourseVideoStatus().equals("Normal") && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(0).getUu().trim()) && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(0).getCode().trim())) {
                                bundle = LetvParamsUtils.setVodParams(DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(0).getUu()).trim(), DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(0).getCode()).trim(), "", "151398", "", false);

                                playBoard.init(bundle, v4PlaySkin);
                                StringUtils.Log(TAG, "getUu3" + mYjCourseModel.getCourseCatalogList().get(0).getUu());
                                playPosition = 0;
                                select = 0;
                            } else {
                                for (int j = 0; j <= mYjCourseModel.getCourseCatalogList().size(); j++) {
                                    if (mYjCourseModel.getCourseCatalogList().get(j).getCourseVideoStatus().equals("Normal") && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(j).getUu()) && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(j).getCode())) {
                                        bundle = LetvParamsUtils.setVodParams(DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(j).getUu()).trim(), DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(j).getCode()).trim(), "", "151398", "", false);
                                        playBoard.init(bundle, v4PlaySkin);
                                        StringUtils.Log(TAG, "getUu4" + mYjCourseModel.getCourseCatalogList().get(j).getUu());
                                        playPosition = j;
                                        select = j;
                                        break;
                                    }

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        int i = (Integer.parseInt(mYjCourseModel.getLastNumber())) - 1;
                        StringUtils.Log(TAG, "i=" + i);
                        if (position == 1) {
                            if (i < mYjCourseModel.getCourseCatalogList().size()) {
                                if (mYjCourseModel.getCourseCatalogList().get(i).getCourseVideoStatus().equals("Normal") && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(i).getUu()) && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(i).getCode())) {
                                    bundle = LetvParamsUtils.setVodParams(DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(i).getUu()).trim(), DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(i).getCode()).trim(), "", "151398", "", false);
                                    playBoard.init(bundle, v4PlaySkin);
                                    StringUtils.Log(TAG, "getUu1=" + mYjCourseModel.getCourseCatalogList().get(i).getUu());
                                    playPosition = i;
                                    select = i;
                                } else {
                                    for (int j = 0; j <= mYjCourseModel.getCourseCatalogList().size(); j++) {
                                        if (mYjCourseModel.getCourseCatalogList().get(j).getCourseVideoStatus().equals("Normal") && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(j).getUu()) && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(j).getCode())) {
                                            bundle = LetvParamsUtils.setVodParams(DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(j).getUu()).trim(), DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(j).getCode()).trim(), "", "151398", "", false);
                                            playBoard.init(bundle, v4PlaySkin);
                                            StringUtils.Log(TAG, "getUu2=" + mYjCourseModel.getCourseCatalogList().get(j).getUu());
                                            playPosition = j;
                                            select = j;
                                            break;
                                        }
                                    }
                                }
                            } else {
                                for (int j = 0; j <= mYjCourseModel.getCourseCatalogList().size(); j++) {
                                    if (mYjCourseModel.getCourseCatalogList().get(j).getCourseVideoStatus().equals("Normal") && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(j).getUu()) && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(j).getCode())) {
                                        bundle = LetvParamsUtils.setVodParams(DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(j).getUu()).trim(), DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(j).getCode()).trim(), "", "151398", "", false);
                                        playBoard.init(bundle, v4PlaySkin);
                                        StringUtils.Log(TAG, "getUu2=" + mYjCourseModel.getCourseCatalogList().get(j).getUu());
                                        playPosition = j;
                                        select = j;
                                        break;
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                if (position == 1) {
                    if (!TextUtils.isEmpty(catalogId) && indexLogId != -1) {
                        try {
                            bundle = LetvParamsUtils.setVodParams(DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(indexLogId).getUu()).trim(), DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(indexLogId).getCode()).trim(), "", "151398", "", false);
                            playBoard.init(bundle, v4PlaySkin);
                            playPosition = indexLogId;
                            select = indexLogId;
                            //mPosition = indexLogId;
                            if (playBoard != null) {
                                final Timer timer = new Timer();
                                timer.schedule(new TimerTask() {
                                    public void run() {
                                        mHandler.sendEmptyMessage(6);
                                        timer.cancel();
                                    }
                                }, 1000);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (playBoard != null) {
            playBoard.onResume();
        }
    }

    public static boolean mOnPause = true;

    @Override
    protected void onPause() {
        super.onPause();
        if (playBoard != null) {
            playBoard.onPause();
        }
//         mOnPause = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeListeners();
        if (playBoard != null) {
            playBoard.onDestroy();
        }
    }

    @Override
    public void finish() {
        super.finish();
        if (playBoard != null) {
            playBoard.onDestroy();
        }
    }

    int positionFlag = 0;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mFlag = false;
            if (mYjCourseModel != null && mIsLogin) {
                if (mYjCourseModel.getCoursePayWay().equals("FREE") || mYjCourseModel.getIsBuy().equals("Yes")) {
                    ll_bottom.setVisibility(View.GONE);
                    mImg_play_return.setVisibility(View.VISIBLE);
                } else {
                    ll_bottom.setVisibility(View.VISIBLE);
                    mImg_play_return.setVisibility(View.VISIBLE);
                }
            } else {
                ll_bottom.setVisibility(View.VISIBLE);
                mImg_play_return.setVisibility(View.VISIBLE);
            }
            if (playBoard != null) {
                iSplayer = playBoard.getPlayer();
                if (iSplayer != null && !iSplayer.isPlaying()) {
                    mImg_play_start_de.setVisibility(View.VISIBLE);
                }
            }
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (!mIsLogin) {
                mImg_play_start_de.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        playerController.getIsPlayerController().setScreenResolution(ISplayerController.SCREEN_ORIENTATION_USER_PORTRAIT);
                        startActivity(YJLoginDialog.class);
                    }
                });
            }
            mFlag = false;
            if (positionFlag == 0) {
                mFlag = true;
            }
            ll_bottom.setVisibility(View.GONE);
            mImg_play_return.setVisibility(View.GONE);
        }
        if (playBoard != null) {
            playBoard.onConfigurationChanged(newConfig);
        }
    }

    private int mStatus = -1;

    private void bindNotifyListener() {
        addListener(YJNotifyTag.USER_LOGIN, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
                getCourseCatalog(1);

            }
        });
        addListener(YJNotifyTag.PAY_SUCESS, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {

                mIsLogin = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean("isLogin", false);
                getCourseCatalog(2);
            }
        });

        addListener(YJNotifyTag.COURSE_CLOSE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                boolean colseFlag = (boolean) value;
                if (colseFlag) {
                    finish();
                }
            }
        });
        addListener(YJNotifyTag.YJCUSTOMLIVEACTIVITY_DESTROY, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                isYJCustomLiveActivityDestroy = true;
            }
        });


    }

    private void buyCourse() {

        if (mIsLogin) {
            if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
            } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                goToBuyCourse();
                //startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
            }
        } else {
            mStatus = 0;
            startActivity(YJLoginDialog.class);
        }
    }

    public int select = 0;


    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mYjCourseModel != null) {
                StringUtils.Log(TAG, "mYjCourseModel.getCourseCatalogList().size()=" + mYjCourseModel.getCourseCatalogList().size());
                return mYjCourseModel.getCourseCatalogList().size();
            } else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            if (mYjCourseModel != null) {
                return mYjCourseModel.getCourseCatalogList().size();
            } else {
                return 0;
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_details_item_new, null);
                holder = new ViewHolder();
                holder.mTxt_num = (TextView) convertView.findViewById(R.id.txt_num);
                holder.txt_learn = (TextView) convertView.findViewById(R.id.txt_learn);
                holder.mTxt_title = (TextView) convertView.findViewById(R.id.txt_title);
                holder.mTtxt_time = (TextView) convertView.findViewById(R.id.txt_time);
                holder.txt_status_live_one = (TextView) convertView.findViewById(R.id.txt_status_live_one);
                holder.txt_course_info = (TextView) convertView.findViewById(R.id.txt_course_info);
                holder.mView_line = (View) convertView.findViewById(R.id.view_line);
                holder.img_triangle = (ImageView) convertView.findViewById(R.id.img_triangle);


                holder.mRe_item = (RelativeLayout) convertView.findViewById(R.id.re_item);
                holder.re_lay_one = (RelativeLayout) convertView.findViewById(R.id.re_lay_one);
                holder.re_lay_two = (RelativeLayout) convertView.findViewById(R.id.re_lay_two);

                holder.txt_status_live = (TextView) convertView.findViewById(R.id.txt_status_live);
                holder.txt_title_two = (TextView) convertView.findViewById(R.id.txt_title_two);
                holder.txt_time_dowm = (TextView) convertView.findViewById(R.id.txt_time_dowm);
                holder.txt_status = (TextView) convertView.findViewById(R.id.txt_status);
                holder.txt_num_two = (TextView) convertView.findViewById(R.id.txt_num_two);
                holder.txt_time_two = (TextView) convertView.findViewById(R.id.txt_time_two);
                holder.img_triangle_two = (ImageView) convertView.findViewById(R.id.img_triangle_two);
                holder.mView_line_two = (View) convertView.findViewById(R.id.view_line_two);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == 0) {
                holder.mView_line.setVisibility(View.INVISIBLE);
                holder.mView_line_two.setVisibility(View.INVISIBLE);
            } else {
                holder.mView_line.setVisibility(View.VISIBLE);
                holder.mView_line_two.setVisibility(View.VISIBLE);
            }
            if (mYjCourseModel != null) {
                if (select == position) {
                    holder.img_triangle.setImageResource(R.mipmap.button_play_se);
                    holder.mTxt_num.setTextColor(getResources().getColor(R.color.blue_title));
                    holder.mTxt_title.setTextColor(getResources().getColor(R.color.blue_title));
                    holder.mTtxt_time.setTextColor(getResources().getColor(R.color.gray_text));

                    holder.txt_status.setTextColor(getResources().getColor(R.color.blue_title));
                    holder.txt_course_info.setTextColor(getResources().getColor(R.color.blue_title));
                    holder.txt_status_live.setBackgroundResource(R.drawable.shape_blue_round_nopadding);
                    holder.img_triangle_two.setImageResource(R.mipmap.button_play_se);
                    holder.txt_num_two.setTextColor(getResources().getColor(R.color.blue_title));
                    holder.txt_title_two.setTextColor(getResources().getColor(R.color.blue_title));
                } else {
                    holder.img_triangle.setImageResource(R.mipmap.button_play_de);
                    holder.mTxt_num.setTextColor(getResources().getColor(R.color.black));
                    holder.mTxt_title.setTextColor(getResources().getColor(R.color.black));
                    holder.mTtxt_time.setTextColor(getResources().getColor(R.color.gray_text));

                    holder.txt_status.setTextColor(getResources().getColor(R.color.gray_text));
                    holder.txt_course_info.setTextColor(getResources().getColor(R.color.gray_text));
                    holder.txt_status_live.setBackgroundResource(R.drawable.shape_yellow_round_nopadding);
                    holder.img_triangle_two.setImageResource(R.mipmap.button_play_de);
                    holder.txt_num_two.setTextColor(getResources().getColor(R.color.black));
                    holder.txt_title_two.setTextColor(getResources().getColor(R.color.black));
                }
                if (mFlag) {
                    if (playPosition == position) {
                        holder.img_triangle.setImageResource(R.mipmap.button_play_se);
                        holder.mTxt_num.setTextColor(getResources().getColor(R.color.blue_title));
                        holder.mTxt_title.setTextColor(getResources().getColor(R.color.blue_title));
                        holder.mTtxt_time.setTextColor(getResources().getColor(R.color.gray_text));

                        holder.txt_status.setTextColor(getResources().getColor(R.color.blue_title));
                        holder.txt_course_info.setTextColor(getResources().getColor(R.color.blue_title));
                        holder.txt_status_live.setBackgroundResource(R.drawable.shape_blue_round_nopadding);
                        holder.img_triangle_two.setImageResource(R.mipmap.button_play_se);
                        holder.txt_num_two.setTextColor(getResources().getColor(R.color.blue_title));
                        holder.txt_title_two.setTextColor(getResources().getColor(R.color.blue_title));
                    }
                    mFlag = false;
                }
                if (mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoShape().equals("DVR")) {
                    if (mYjCourseModel.getCourseCatalogList().get(position).getCourseVideoStatus().equals("Normal")) {
                        holder.re_lay_one.setVisibility(View.VISIBLE);
                        holder.re_lay_two.setVisibility(View.GONE);
                        if (0 <= position && position < 9) {
                            int p = position + 1;
                            holder.mTxt_num.setText("0" + p);
                            holder.txt_num_two.setText("0" + p);
                        } else {
                            holder.mTxt_num.setText(position + 1 + "");
                            holder.txt_num_two.setText(position + 1 + "");
                        }


                        holder.mTxt_title.setText(mYjCourseModel.getCourseCatalogList().get(position).getName());
                        if (mYjCourseModel.getCourseCatalogList().get(position).getIsTry().equals("Yes")) {
                            if (mYjCourseModel.getCoursePayWay().equals("FREE")) {
                                holder.txt_status_live_one.setText("视频");
                            } else {
                                if (mIsLogin) {
                                    if (mYjCourseModel.getIsBuy().equals("Yes")) {
                                        holder.txt_status_live_one.setText("视频");
                                    } else {
                                        holder.txt_status_live_one.setText("可试听");
                                    }
                                } else {
                                    holder.txt_status_live_one.setText("可试听");
                                }
                            }
                        } else {
                            holder.txt_status_live_one.setText("视频");
                        }
                        if (!TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getDuration())) {
                            StringUtils.Log(TAG, "getDuration=" + mYjCourseModel.getCourseCatalogList().get(position).getDuration());
                            holder.mTtxt_time.setText(TimeUtil.displayDuration(mYjCourseModel.getCourseCatalogList().get(position).getDuration()).replace(":", "'") + "\"");
                        } else {
                            holder.mTtxt_time.setVisibility(View.GONE);
                        }
                        holder.txt_course_info.setText("已开课");
                    } else {
                        holder.re_lay_one.setVisibility(View.GONE);
                        holder.re_lay_two.setVisibility(View.VISIBLE);
                        holder.txt_status.setVisibility(View.VISIBLE);
                        holder.txt_status_live.setVisibility(View.GONE);
                        holder.txt_status.setText("未开课");

                        if (0 <= position && position < 9) {
                            int p = position + 1;
                            holder.mTxt_num.setText("0" + p);
                            holder.txt_num_two.setText("0" + p);
                        } else {
                            holder.mTxt_num.setText(position + 1 + "");
                            holder.txt_num_two.setText(position + 1 + "");
                        }

                        holder.txt_title_two.setText(mYjCourseModel.getCourseCatalogList().get(position).getName());
                        holder.txt_time_dowm.setTextColor(getResources().getColor(R.color.gray_text));
                        holder.txt_num_two.setTextColor(getResources().getColor(R.color.gray_text));
                        holder.txt_title_two.setTextColor(getResources().getColor(R.color.gray_text));

                        holder.img_triangle_two.setImageResource(R.mipmap.button_play_de);
                        holder.txt_time_two.setVisibility(View.VISIBLE);
                        holder.txt_time_two.setTextColor(getResources().getColor(R.color.gray_text));
                        if (!TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getDuration())) {
                            holder.txt_time_two.setText(TimeUtil.displayDuration(mYjCourseModel.getCourseCatalogList().get(position).getDuration()).replace(":", "'") + "\"");
                        } else {
                            holder.txt_time_two.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getPlanDate()) && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getPlanDate())) {
                            String month = TimeUtil.getMonth(Long.parseLong(mYjCourseModel.getCourseCatalogList().get(position).getPlanDate()));
                            String day = TimeUtil.getDay(Long.parseLong(mYjCourseModel.getCourseCatalogList().get(position).getPlanDate()));
                            holder.txt_time_dowm.setText("预计开课时间: " + month + "月" + day + "日");
                        }
                    }
                } else {
                    holder.re_lay_one.setVisibility(View.GONE);
                    holder.re_lay_two.setVisibility(View.VISIBLE);
                    holder.txt_time_two.setVisibility(View.GONE);
                    if (0 <= position && position < 9) {
                        int p = position + 1;
                        holder.mTxt_num.setText("0" + p);
                        holder.txt_num_two.setText("0" + p);
                    } else {
                        holder.mTxt_num.setText(position + 1 + "");
                        holder.txt_num_two.setText(position + 1 + "");
                    }
                    holder.txt_title_two.setText(mYjCourseModel.getCourseCatalogList().get(position).getName());
                    if (!TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getLiveStatus())) {

                        if (mYjCourseModel.getCourseCatalogList().get(position).getLiveStatus().equals("ing")) {
                            holder.txt_status.setText("正在直播");
                            holder.txt_time_dowm.setTextColor(getResources().getColor(R.color.yellow_text));
                            holder.txt_status.setTextColor(getResources().getColor(R.color.yellow_text));
                        } else if (mYjCourseModel.getCourseCatalogList().get(position).getLiveStatus().equals("over")) {
                            holder.txt_time_dowm.setTextColor(getResources().getColor(R.color.gray_text));
                            if ("Yes".equals(mYjCourseModel.getCourseCatalogList().get(position).getIsReplay())) {
                                holder.txt_status.setText("已开课");
                            }
                            if ("No".equals(mYjCourseModel.getCourseCatalogList().get(position).getIsReplay())) {
                                holder.txt_status.setText("等待回放");
                            }
                        } else if (mYjCourseModel.getCourseCatalogList().get(position).getLiveStatus().equals("no")) {
                            holder.txt_time_dowm.setTextColor(getResources().getColor(R.color.gray_text));
                            holder.txt_status.setText("未开课");
                        }
                    } else {
                        holder.txt_time_dowm.setTextColor(getResources().getColor(R.color.gray_text));
                        holder.txt_status.setText("未开课");
                    }
                    if (!TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getPlanDate()) && !TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getPlanEndDate())) {
                        String planDate = TimeUtil.getYearTime(Long.parseLong(mYjCourseModel.getCourseCatalogList().get(position).getPlanDate()));
                        String planEndDate = TextUtils.isEmpty(mYjCourseModel.getCourseCatalogList().get(position).getPlanEndDate()) ? "" : TimeUtil.getSecondMInTime(Long.parseLong(mYjCourseModel.getCourseCatalogList().get(position).getPlanEndDate()));
                        holder.txt_time_dowm.setText((("3".equals(StringUtils.getDateDetail(planDate)) ? TimeUtil.getMonthTime(Long.parseLong(mYjCourseModel.getCourseCatalogList().get(position).getPlanDate())) : StringUtils.getDateDetail(planDate)) + " " + TimeUtil.getSecondMInTime(Long.parseLong(mYjCourseModel.getCourseCatalogList().get(position).getPlanDate()))) + "-" + planEndDate);
                    } else {
                        holder.txt_time_dowm.setVisibility(View.GONE);
                    }
                }
            }
            return convertView;
        }

        private class ViewHolder {
            private TextView mTxt_num;
            private TextView txt_learn;
            private TextView mTxt_title;
            private TextView mTtxt_time;
            private TextView txt_status_live_one;
            private TextView txt_course_info;
            private View mView_line;
            private View mView_line_two;
            private ImageView img_triangle;


            private RelativeLayout mRe_item;
            private RelativeLayout re_lay_one;
            private RelativeLayout re_lay_two;

            private TextView txt_status_live;
            private TextView txt_title_two;
            private TextView txt_time_dowm;
            private TextView txt_status;
            private TextView txt_time_two;
            private TextView txt_num_two;
            private ImageView img_triangle_two;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.handleResultData(data, qqShareListener);
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            switch (getRequestedOrientation()) {
                case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                    finish();
                    break;
                case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                    playerController.getIsPlayerController().setScreenResolution(ISplayerController.SCREEN_ORIENTATION_USER_PORTRAIT);

                    break;
                default:
                    break;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //重新获取课程详情页
    private void getCourseCatalog(final int position) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseId", mYjCourseModel.getCourseId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_COURSE_CATALOG, objectMap, mIsLogin, new TextHttpResponseHandler() {
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
                            JSONObject jsonObject = new JSONObject(json.getString("data"));
                            YJCourseModel yjCourseModel = JSON.parseObject(jsonObject.getString("course"), YJCourseModel.class);
                            StringUtils.Log(TAG, "yjCourseModel=" + yjCourseModel.toString());
                            mYjCourseModel = yjCourseModel;
                            initData(2);
                            if (position == 1) {
                                if (mStatus == 0) {
                                    if (mYjCourseModel.getIsBuy().equals("Yes")) {
                                        return;
                                    }
                                    if (mYjCourseModel.getIsBuy().equals("No")) {
                                        if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                            yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                                        } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                            goToBuyCourse();
                                            //startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                                        }
                                    }
                                } else if (mStatus == 1) {
                                }
                            }
                            break;
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private boolean isFistInit = true;

    public class ViewPagerAdapter extends PagerAdapter {

        List<View> viewLists;

        public ViewPagerAdapter(List<View> lists) {
            viewLists = lists;
        }

        //获得size
        @Override
        public int getCount() {
            return viewLists.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        //销毁Item
        @Override
        public void destroyItem(View view, int position, Object object) {
            ((ViewPager) view).removeView(viewLists.get(position));
        }

        //实例化Item
        @Override
        public Object instantiateItem(View view, int position) {

            StringUtils.Log(TAG, "ViewPagerAdapter position====" + position);
            mLv_course_detail = (ListView) viewLists.get(0).findViewById(R.id.lv_course_detail);
            WebView view_course_details_course_introduction = (WebView) viewLists.get(1).findViewById(R.id.view_course_details_course_introduction);
            WebView view_course_details_teacher_introduction = (WebView) viewLists.get(1).findViewById(R.id.view_course_details_teacher_introduction);
            ImageView mImg_rmb = (ImageView) viewLists.get(1).findViewById(R.id.img_rmb);
            TextView mTxt_money = (TextView) viewLists.get(1).findViewById(R.id.txt_money);
            TextView view_course_date = (TextView) viewLists.get(1).findViewById(R.id.view_course_date);
            TextView view_course_date_up = (TextView) viewLists.get(1).findViewById(R.id.view_course_date_up);
            LinearLayout view_course_details_customerLayout = (LinearLayout) viewLists.get(1).findViewById(R.id.view_course_details_customerLayout);
            LinearLayout view_course_details_shareLayout = (LinearLayout) viewLists.get(1).findViewById(R.id.view_course_details_shareLayout);
            RelativeLayout re_course_date = (RelativeLayout) viewLists.get(1).findViewById(R.id.re_course_date);
            ImageView img_learn_customer = (ImageView) viewLists.get(1).findViewById(R.id.img_learn_customer);
            ImageView img_learn_share = (ImageView) viewLists.get(1).findViewById(R.id.img_learn_share);
            TextView view_course_details_title = (TextView) viewLists.get(1).findViewById(R.id.view_course_details_title);
            RelativeLayout re_teacher_info = (RelativeLayout) viewLists.get(1).findViewById(R.id.re_teacher_info);
            RelativeLayout re_course_info = (RelativeLayout) viewLists.get(1).findViewById(R.id.re_course_info);
            View view_line_t = (View) viewLists.get(1).findViewById(R.id.view_line_t);

            view_course_related_list = (ListView) viewLists.get(2).findViewById(R.id.view_course_related_list);
            RelativeLayout view_course_related_layout = (RelativeLayout) viewLists.get(2).findViewById(R.id.view_course_related_layout);
            TextView toSelectCourse = (TextView) viewLists.get(2).findViewById(R.id.view_course_related_selection);
            ImageView nothing_imageview = (ImageView) viewLists.get(2).findViewById(R.id.nothing_imageview);

            if (mYjCourseModel != null) {
                if (mYjCourseModel.getRecommendBookList().size() > 0 || mYjCourseModel.getRecommendCourseList().size() > 0) {
                    view_course_related_layout.setVisibility(View.GONE);
                } else {
                    view_course_related_layout.setVisibility(View.VISIBLE);
                }
            }
            toSelectCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishAll();
                    startActivity(YJMainActivity.class);
                    notifyListener(YJNotifyTag.MAIN_LESSON, 0);

                }
            });
            if (isFistInit) {
                if (mYjCourseModel.getRecommendBookList().size() > 0) {
                    view_course_related_list.addFooterView(mbook_foot_item);
                    isFistInit = false;
                }
            }
            String courseName = "";
            if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getName())) {
                courseName = mYjCourseModel.getName();
            } else {
                courseName = "课程名称";
            }
            view_course_details_title.setText(courseName);

            String courseDesc = "";
            if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getCourseDescribe())) {
                re_course_info.setVisibility(View.VISIBLE);
                courseDesc = mYjCourseModel.getCourseDescribe();
            } else {
                courseDesc = "老师正在上传课程内容！";
                re_course_info.setVisibility(View.GONE);
                view_line_t.setVisibility(View.GONE);
            }
            initWebView(view_course_details_course_introduction, courseDesc);
            String teacherDesc = "";
            if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getTeacher().getTeacherDesc())) {
                teacherDesc = mYjCourseModel.getTeacher().getTeacherDesc();
                re_teacher_info.setVisibility(View.VISIBLE);
            } else {
                teacherDesc = "老师正在制作个人介绍！";
                re_teacher_info.setVisibility(View.GONE);
                view_line_t.setVisibility(View.GONE);
            }
            mLv_course_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    positionFlag++;
                    if (ClickUtil.isFastDoubleClick()) {
                        return;
                    }
                    if (mYjCourseModel.getCourseCatalogList().get(i).getCourseVideoShape().equals("DVR")) {
                        if (!mYjCourseModel.getCourseCatalogList().get(i).getCourseVideoStatus().equals("Normal")) {
                            showToast("课程未上传");
                            return;
                        }
                    }
                    if (mIsLogin) {
                        select = i;
                        adapter.notifyDataSetChanged();
                        if (!TextUtils.isEmpty(mYjCourseModel.getCourseServiceStatus()) && mYjCourseModel.getCourseServiceStatus().equals("OVER")) {
                            if (!TextUtils.isEmpty(mYjCourseModel.getCourseStatus()) && mYjCourseModel.getCourseStatus().equals("SELLING")) {
                                if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                    yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                                } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                    goToBuyCourse();
                                    //startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                                }
                            }
                        } else {
                            if (mYjCourseModel.getCourseCatalogList().get(i).getCourseVideoShape().equals("DVR")) {
                                if (mYjCourseModel.getIsBuy().equals("Yes") || mYjCourseModel.getCoursePayWay().equals("FREE")) {
                                    StringUtils.Log(TAG, "LastNumber=" + mYjCourseModel.getLastNumber());
                                    StringUtils.Log(TAG, "adapterView.getCount()=" + adapterView.getCount());
                                    if (playBoard != null) {
                                        iSplayer = playBoard.getPlayer();
                                        if (iSplayer != null) {
                                            if (iSplayer.isPlaying() && mPosition == i) {
                                                return;
                                            } else {
                                                if (mYjCourseModel.getCoursePayWay().equals("FREE") || mYjCourseModel.getIsBuy().equals("Yes")) {
                                                    try {
                                                        isPlayFlag = true;
                                                        playBoard = new LetvsSimplePlayBoard();
                                                        if (playBoard != null) {
                                                            mPosition = i;
                                                            iSplayer = playBoard.getPlayer();
                                                            if (playPosition == i && isFirst) {
                                                                playPosition = i;
                                                                long duration = Long.parseLong(mYjCourseModel.getLastStudyTime());
                                                                if (duration < iSplayer.getDuration()) {
                                                                    iSplayer.seekTo(duration * 1000);
                                                                }
                                                                iSplayer.start();
                                                                isFirst = false;
                                                                StringUtils.Log(TAG, "iSplayer.start()");
                                                                //记录开始学习行为
                                                                if (isFirstPlay) {
                                                                    YJUserStudyData.catalogBeginStudy(mYjCourseModel, playPosition, true);
                                                                    mplayPosition = playPosition;
                                                                    isFirstPlay = false;
                                                                }

                                                            } else {
                                                                playPosition = i;
                                                                iSplayer.stop();
                                                                iSplayer.reset();
                                                                StringUtils.Log(TAG, "iSplayer.prepareAsync()");
                                                                iSplayer.setParameter(iSplayer.getPlayerId(), LetvParamsUtils.setVodParams(DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(i).getUu()).trim(), DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(i).getCode()).trim(), "", "151398", "", false));
                                                                iSplayer.prepareAsync();
                                                                if (isFirstPlay) {
                                                                    YJUserStudyData.catalogBeginStudy(mYjCourseModel, playPosition, true);
                                                                    mplayPosition = playPosition;
                                                                    isFirstPlay = false;
                                                                } else {
                                                                    if (isComplete) {
                                                                        YJUserStudyData.catalogEndStudyNoP(mYjCourseModel, mplayPosition);
                                                                    }
                                                                    YJUserStudyData.catalogBeginStudy(mYjCourseModel, playPosition, false);
                                                                    mplayPosition = playPosition;
                                                                    isComplete = true;
                                                                }

                                                            }
                                                            //   }
                                                        } else {
                                                            showToast("暂无视频");
                                                            return;
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    if (playerController != null) {
                                                        if (playerController.getIsPlayerController().isPlayCompleted()) {
                                                            playerController.getIsPlayerController().resetPlay();
                                                            mImg_play_start_de.setVisibility(View.GONE);
                                                            mRe_play_start_de.setVisibility(View.GONE);
                                                            img_play_bg.setVisibility(View.GONE);
                                                        } else {
                                                            if (playBoard != null) {
                                                                iSplayer = playBoard.getPlayer();
                                                                mImg_play_start_de.setVisibility(View.GONE);
                                                                mRe_play_start_de.setVisibility(View.GONE);
                                                                img_play_bg.setVisibility(View.GONE);
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                                        yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                                                    } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                                        goToBuyCourse();
                                                        //startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                                                    }
                                                }
                                            }
                                        } else {
                                            StringUtils.Log(TAG, "iSplayer=kong");
                                        }
                                    } else {
                                        StringUtils.Log(TAG, "playBroad=kong");
                                    }
                                } else {
                                    if (mYjCourseModel.getCourseCatalogList().get(i).getIsTry().equals("Yes")) {
                                        if (isTry) {
                                            iSplayer = playBoard.getPlayer();
                                            if (iSplayer == null) {
                                                showToast("暂无视频");
                                            } else {
                                                if (iSplayer.isPlaying() && mPosition == i) {
                                                    return;
                                                }
                                                mPosition = i;
                                                iSplayer = playBoard.getPlayer();
                                                if (playPosition == i && isFirst) {
                                                    playPosition = i;
                                                    long duration = Long.parseLong(mYjCourseModel.getLastStudyTime());
                                                    //  if (duration < iSplayer.getDuration()) {
                                                    iSplayer.seekTo(duration * 1000);
                                                    //  }
                                                    iSplayer.start();
                                                    isFirst = false;
                                                    StringUtils.Log(TAG, "isTry iSplayer.start()");
                                                    //记录开始学习行为
                                                    if (isFirstPlay) {
                                                        YJUserStudyData.catalogBeginStudy(mYjCourseModel, playPosition, true);
                                                        mplayPosition = playPosition;
                                                        isFirstPlay = false;
                                                    }

                                                } else {
                                                    playPosition = i;
                                                    try {
                                                        iSplayer.stop();
                                                        iSplayer.reset();
                                                        iSplayer.setParameter(iSplayer.getPlayerId(), LetvParamsUtils.setVodParams(DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(i).getUu()).trim(), DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(i).getCode()).trim(), "", "151398", "", false));
                                                        iSplayer.prepareAsync();
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                    if (isFirstPlay) {
                                                        YJUserStudyData.catalogBeginStudy(mYjCourseModel, playPosition, true);
                                                        mplayPosition = playPosition;
                                                        isFirstPlay = false;
                                                    } else {
                                                        if (isComplete) {
                                                            YJUserStudyData.catalogEndStudyNoP(mYjCourseModel, mplayPosition);
                                                        }
                                                        YJUserStudyData.catalogBeginStudy(mYjCourseModel, playPosition, false);
                                                        mplayPosition = playPosition;
                                                        isComplete = true;
                                                    }
                                                }
                                                mImg_play_start_de.setVisibility(View.GONE);
                                                mRe_play_start_de.setVisibility(View.GONE);
                                                img_play_bg.setVisibility(View.GONE);
                                                // pause();
                                            }

                                        } else {
                                            if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                                yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                                            } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                                goToBuyCourse();
                                                //startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                                            }
                                            mOnPause = false;
                                            mImg_play_start_de.setVisibility(View.VISIBLE);
                                            mRe_play_start_de.setVisibility(View.VISIBLE);
                                            img_play_bg.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                            yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                                        } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                            goToBuyCourse();
                                            //startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                                        }
                                        mOnPause = false;
                                        mImg_play_start_de.setVisibility(View.VISIBLE);
                                        mRe_play_start_de.setVisibility(View.VISIBLE);
                                        img_play_bg.setVisibility(View.VISIBLE);
                                    }
                                }
                            } else {
                                if (mYjCourseModel.getCoursePayWay().equals("FREE") || mYjCourseModel.getIsBuy().equals("Yes")) {
                                    if (playBoard != null) {
                                        playBoard.onDestroy();
                                    }
                                    isPlayFlag = true;
                                    mOnPause = false;
                                    if (!isYJCustomLiveActivityDestroy) {
                                        showToast("请稍后进入直播间");
                                        return;
                                    }
                                    if (mYjCourseModel.getCourseCatalogList().get(i).getLiveStatus().equals("ing")) {
                                        StringUtils.Log(TAG, "ing i==" + i);
                                        mImg_play_start_de.setVisibility(View.VISIBLE);
                                        mRe_play_start_de.setVisibility(View.VISIBLE);
                                        img_play_bg.setVisibility(View.VISIBLE);
                                        if (!isFirstPlay) {
                                            YJUserStudyData.catalogEndStudyNoP(mYjCourseModel, mplayPosition);
                                        }
                                        if (isYJCustomLiveActivityDestroy) {
                                            getTakenLive(select, false, 1);
                                        } else {
                                            showToast("请稍后进入直播间");
                                        }
                                    }
                                    if (mYjCourseModel.getCourseCatalogList().get(i).getLiveStatus().equals("over")) {
                                        StringUtils.Log(TAG, "over i==" + i);
                                        mImg_play_start_de.setVisibility(View.VISIBLE);
                                        mRe_play_start_de.setVisibility(View.VISIBLE);
                                        img_play_bg.setVisibility(View.VISIBLE);

                                        if (!isFirstPlay) {
                                            YJUserStudyData.catalogEndStudyNoP(mYjCourseModel, mplayPosition);
                                        }
                                        if ("Yes".equals(mYjCourseModel.getCourseCatalogList().get(i).getIsReplay())) {
                                            getTakenBack(i);
                                        }
                                        if ("No".equals(mYjCourseModel.getCourseCatalogList().get(i).getIsReplay())) {
//                                showDialog(new YJIsReplayDialog());
                                            getTakenLive(i, true, 2);//true 代表是否弹出预约页面， 1代表弹直播未开始，2代表弹出直播已结束
                                        }
                                    }
                                    if (mYjCourseModel.getCourseCatalogList().get(i).getLiveStatus().equals("no")) {
                                        StringUtils.Log(TAG, "no i==" + i);
                                        mImg_play_start_de.setVisibility(View.VISIBLE);
                                        mRe_play_start_de.setVisibility(View.VISIBLE);
                                        img_play_bg.setVisibility(View.VISIBLE);
//                                    showToast("直播未开始");
                                        getTakenLive(i, true, 1);
                                    }
                                } else {
                                    if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                        yjGetUserInfo.getUserCoinInfo(AYJCoursePlayNewActivity.this);
                                    } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                        goToBuyCourse();
                                        // startActivity(createIntent(YJBuyCourseActivity.class, createTransmitData(YJBuyCourseActivity.COURSE_MODEL, mYjCourseModel).set(YJBuyCourseActivity.VISIBLE_FLAG, true)));
                                    }

                                    mOnPause = false;
                                    isPlayFlag = true;
                                    mImg_play_start_de.setVisibility(View.VISIBLE);
                                    mRe_play_start_de.setVisibility(View.VISIBLE);
                                    img_play_bg.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } else {
                   /* YJLoginDialog dialog = new YJLoginDialog();
                    showDialog(dialog);*/
                        startActivity(YJLoginDialog.class);
                    }
                    mPosition = i;
                }
            });
            initWebView(view_course_details_teacher_introduction, teacherDesc);
            if (mYjCourseModel != null) {
                if (mIsLogin) {
                    if (!TextUtils.isEmpty(mYjCourseModel.getCourseServiceStatus()) && mYjCourseModel.getCourseServiceStatus().equals("OVER")) {
                        re_course_date.setVisibility(View.GONE);
                        view_course_details_customerLayout.setVisibility(View.GONE);
                        if (!TextUtils.isEmpty(mYjCourseModel.getCourseStatus()) && mYjCourseModel.getCourseStatus().equals("SELLING")) {
                            mTxt_money.setVisibility(View.VISIBLE);
                            if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getCourseServiceType())) {
                                String courseServiceType = mYjCourseModel.getCourseServiceType();
                                if (courseServiceType.equals("serviceDate") && !TextUtils.isEmpty(mYjCourseModel.getServiceDate())) {
                                    view_course_date_up.setVisibility(View.GONE);
                                    view_course_date.setText("可观看时间截止于" + TimeUtil.getYearTime(Long.parseLong(mYjCourseModel.getServiceDate())));
                                } else if (courseServiceType.equals("serviceDay") && !TextUtils.isEmpty(mYjCourseModel.getServiceDay())) {
                                    view_course_date_up.setVisibility(View.VISIBLE);
                                    view_course_date.setText("课程剩余观看时间" + mYjCourseModel.getServiceDay() + "天");
                                }
                            }
                            if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                mImg_rmb.setVisibility(View.VISIBLE);
                                mTxt_money.setText(mYjCourseModel.getPayCoin() + "");
                            } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                mImg_rmb.setVisibility(View.GONE);
                                mTxt_money.setText("￥" + mYjCourseModel.getPayMoney());
                            } else if (mYjCourseModel.getCoursePayWay().equals("FREE")) {
                                view_course_details_customerLayout.setVisibility(View.GONE);
                                mImg_rmb.setVisibility(View.GONE);
                                mTxt_money.setVisibility(View.VISIBLE);
                                mTxt_money.setText("免费");
                                re_course_date.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        if (mYjCourseModel.getCoursePayWay().equals("FREE")) {
                            view_course_details_customerLayout.setVisibility(View.GONE);
                            mImg_rmb.setVisibility(View.GONE);
                            mTxt_money.setVisibility(View.VISIBLE);
                            mTxt_money.setText("免费");
                            re_course_date.setVisibility(View.GONE);
                        } else if (mYjCourseModel.getIsBuy().equals("Yes")) {
                            view_course_date_up.setVisibility(View.GONE);
                            view_course_details_customerLayout.setVisibility(View.VISIBLE);
                            mImg_rmb.setVisibility(View.GONE);
                            mTxt_money.setVisibility(View.VISIBLE);
                            mTxt_money.setText("已购买");
                            view_course_date_up.setVisibility(View.GONE);
                            re_course_date.setVisibility(View.VISIBLE);
                            if (!TextUtils.isEmpty(mYjCourseModel.getEndDate())) {
                                view_course_date.setText("可观看时间截止于" + TimeUtil.getYearTime(Long.parseLong(mYjCourseModel.getEndDate())));
                            }
                        } else if (mYjCourseModel.getIsBuy().equals("No")) {

                            if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getCourseServiceType())) {
                                String courseServiceType = mYjCourseModel.getCourseServiceType();
                                if (courseServiceType.equals("serviceDate") && !TextUtils.isEmpty(mYjCourseModel.getServiceDate())) {
                                    view_course_date_up.setVisibility(View.GONE);
                                    view_course_date.setText("可观看时间截止于" + TimeUtil.getYearTime(Long.parseLong(mYjCourseModel.getServiceDate())));
                                } else if (courseServiceType.equals("serviceDay") && !TextUtils.isEmpty(mYjCourseModel.getServiceDay())) {
                                    view_course_date_up.setVisibility(View.VISIBLE);
                                    view_course_date.setText("课程剩余观看时间" + mYjCourseModel.getServiceDay() + "天");
                                }
                            }
                            re_course_date.setVisibility(View.VISIBLE);
                            view_course_details_customerLayout.setVisibility(View.GONE);
                            mTxt_money.setVisibility(View.VISIBLE);
                            if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                                mImg_rmb.setVisibility(View.VISIBLE);
                                mTxt_money.setText(mYjCourseModel.getPayCoin() + "");
                            } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                                mImg_rmb.setVisibility(View.GONE);
                                mTxt_money.setText("￥" + mYjCourseModel.getPayMoney());
                            }
                        }
                    }
                } else {
                    view_course_details_customerLayout.setVisibility(View.GONE);
                    if (mYjCourseModel.getCoursePayWay().equals("FREE")) {
                        mImg_rmb.setVisibility(View.GONE);
                        mTxt_money.setVisibility(View.VISIBLE);
                        mTxt_money.setText("免费");
                        re_course_date.setVisibility(View.GONE);
                    } else if (mYjCourseModel.getCoursePayWay().equals("XNB")) {
                        if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getCourseServiceType())) {
                            String courseServiceType = mYjCourseModel.getCourseServiceType();
                            if (courseServiceType.equals("serviceDate") && !TextUtils.isEmpty(mYjCourseModel.getServiceDate())) {
                                view_course_date_up.setVisibility(View.GONE);
                                view_course_date.setText("可观看时间截止于" + TimeUtil.getYearTime(Long.parseLong(mYjCourseModel.getServiceDate())));
                            } else if (courseServiceType.equals("serviceDay") && !TextUtils.isEmpty(mYjCourseModel.getServiceDay())) {
                                view_course_date_up.setVisibility(View.VISIBLE);
                                view_course_date.setText("课程剩余观看时间" + mYjCourseModel.getServiceDay() + "天");
                            }
                        }
                        re_course_date.setVisibility(View.VISIBLE);
                        mTxt_money.setVisibility(View.VISIBLE);
                        mImg_rmb.setVisibility(View.VISIBLE);
                        mTxt_money.setText(mYjCourseModel.getPayCoin() + "");
                    } else if (mYjCourseModel.getCoursePayWay().equals("RMB")) {
                        if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getCourseServiceType())) {
                            String courseServiceType = mYjCourseModel.getCourseServiceType();
                            if (courseServiceType.equals("serviceDate") && !TextUtils.isEmpty(mYjCourseModel.getServiceDate())) {
                                view_course_date_up.setVisibility(View.GONE);
                                view_course_date.setText("可观看时间截止于" + TimeUtil.getYearTime(Long.parseLong(mYjCourseModel.getServiceDate())));
                            } else if (courseServiceType.equals("serviceDay") && !TextUtils.isEmpty(mYjCourseModel.getServiceDay())) {
                                view_course_date_up.setVisibility(View.VISIBLE);
                                view_course_date.setText("课程剩余观看时间" + mYjCourseModel.getServiceDay() + "天");
                            }
                        }
                        re_course_date.setVisibility(View.VISIBLE);
                        mTxt_money.setVisibility(View.VISIBLE);
                        mImg_rmb.setVisibility(View.GONE);
                        mTxt_money.setText("￥" + mYjCourseModel.getPayMoney());
                    }
                }
            }

            view_course_details_shareLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ClickUtil.isFastDoubleClick()) {
                        return;
                    }
                    if (playBoard != null) {
                        playBoard.onPause();
                    }
                    YJShareDialog yjShareDialog = new YJShareDialog();
                    showDialog(yjShareDialog);
                }
            });
            img_learn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ClickUtil.isFastDoubleClick()) {
                        return;
                    }
                    if (playBoard != null) {
                        playBoard.onPause();
                    }
                    YJShareDialog yjShareDialog = new YJShareDialog();
                    showDialog(yjShareDialog);
                }
            });
            img_learn_customer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callQQ();
                }
            });
            view_course_details_customerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    callQQ();
                }
            });


            view_course_related_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    mOnPause = false;
                    if (mYjCourseModel != null) {
                        String courseId = mYjCourseModel.getRecommendCourseList().get(i).getCourseId();
                        MyRequestUtils.getCourseCatalog(AYJCoursePlayNewActivity.this, courseId);
                    }
                }
            });
            if (adapter == null) {
                adapter = new MyAdapter();
                mLv_course_detail.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

            if (mVAdapter == null) {
                mVAdapter = new VAdapter(AYJCoursePlayNewActivity.this, view_course_related_list);
                view_course_related_list.setAdapter(mVAdapter);
            } else {
                mVAdapter.notifyDataSetChanged();
            }

            ((ViewPager) view).addView(viewLists.get(position), 0);
            return viewLists.get(position);
        }
    }

    private void initWebView(WebView webView, String stringInfo) {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.requestFocus();
        String head = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1,user-scalable=no\">";
        String body = "<html><header>" + head + "<style>body{padding:0; margin:0;}img{max-width:100%; width:100%; height:auto; display:block; margin:0 auto;}</style>" + "</header>"
                + stringInfo + "</body></html>";
        webView.loadData(body, "text/html; charset=UTF-8", null);
    }

    private void callPhone() {
        mOnPause = false;
        if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getConsultingTelephone())) {
            String phone = mYjCourseModel.getConsultingTelephone();
            StringUtils.Log(TAG, "phone=" + phone);
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            startActivity(intent);
        } else {
            showToast("客服坐席忙，请稍后再试！");
        }
    }

    private void callQQ() {
        mOnPause = false;
        if (mYjCourseModel != null && !TextUtils.isEmpty(mYjCourseModel.getConsultingQQ())) {
            String qq = mYjCourseModel.getConsultingQQ();
            StringUtils.Log(TAG, "qq=" + qq);
            String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        } else {
            showToast("客服正在解决问题，请稍等！");
        }
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJCourseItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (mYjCourseModel != null) {
            return mYjCourseModel.getRecommendCourseList().size();
        } else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.course_item_new)
    class YJCourseItem extends AVAdapterItem {
        @VViewTag(R.id.lay_item)
        private RelativeLayout mLay_item;
        @VViewTag(R.id.view_line)
        private View mView_line;
        @VViewTag(R.id.img_course_teacher)
        private ImageView mIimg_course_teacher;
        @VViewTag(R.id.txt_name)
        private TextView mTxt_name;
        @VViewTag(R.id.txt_teacher_name)
        private TextView mTxt_teacher_name;
        @VViewTag(R.id.txt_money_num)
        private TextView mTxt_money_num;
        @VViewTag(R.id.txt_rmb_num)
        private TextView mTxt_rmb_num;
        @VViewTag(R.id.txt_buy_info)
        private TextView txt_buy_info;
        @VViewTag(R.id.fl_rmb)
        private FrameLayout fl_rmb;
        @VViewTag(R.id.img_rmb)
        private ImageView img_rmb;
        @VViewTag(R.id.img_new_course)
        private ImageView img_new_course;
        @VViewTag(R.id.view_course_related_course_layout)
        private RelativeLayout view_course_related_course_layout;

        @Override
        public void update(int position) {
            if (mYjCourseModel != null) {
                if (position == 0) {
                    view_course_related_course_layout.setVisibility(View.VISIBLE);
                } else {
                    view_course_related_course_layout.setVisibility(View.GONE);
                }
                mYjCourseModelList = mYjCourseModel.getRecommendCourseList();
                mTxt_name.setText(mYjCourseModelList.get(position).getName());
                img_new_course.setVisibility(View.GONE);
                txt_buy_info.setVisibility(View.GONE);
                mTxt_teacher_name.setText(mYjCourseModelList.get(position).getTeacher().getTrueName());
                if (!TextUtils.isEmpty(mYjCourseModelList.get(position).getCoursePic())) {
                    BitmapUtils.create(getContext()).display(mIimg_course_teacher, mYjCourseModelList.get(position).getCoursePic());
                } else {
                    mIimg_course_teacher.setImageResource(R.mipmap.img_no_data_bg);
                }
                if (mYjCourseModelList.get(position).getCoursePayWay().equals("RMB")) {
                    img_rmb.setVisibility(View.VISIBLE);
                    fl_rmb.setVisibility(View.VISIBLE);
                    img_rmb.setImageResource(R.mipmap.img_rmb);
                    mTxt_money_num.setVisibility(View.VISIBLE);

                    mTxt_money_num.setText(mYjCourseModelList.get(position).getOriginalMoney() + "");
                    mTxt_rmb_num.setText(mYjCourseModelList.get(position).getPayMoney() + "");
                    if (mYjCourseModelList.get(position).getOriginalMoney() <= mYjCourseModelList.get(position).getPayMoney()) {
                        fl_rmb.setVisibility(View.GONE);
                    }

                } else if (mYjCourseModelList.get(position).getCoursePayWay().equals("XNB")) {
                    fl_rmb.setVisibility(View.VISIBLE);
                    img_rmb.setVisibility(View.VISIBLE);
                    mTxt_money_num.setVisibility(View.VISIBLE);

                    mTxt_money_num.setText(mYjCourseModelList.get(position).getOriginalCoin() + "");
                    img_rmb.setImageResource(R.mipmap.whale_money);
                    mTxt_rmb_num.setText(mYjCourseModelList.get(position).getPayCoin() + "");
                    if (mYjCourseModelList.get(position).getOriginalCoin() <= mYjCourseModelList.get(position).getPayCoin()) {
                        fl_rmb.setVisibility(View.GONE);
                    }
                } else if (mYjCourseModelList.get(position).getCoursePayWay().equals("FREE")) {
                    fl_rmb.setVisibility(View.GONE);
                    img_rmb.setVisibility(View.GONE);
                    mTxt_rmb_num.setText("免费");

                }


            }


        }

    }

    public abstract void getTakenLive(int position, boolean isShowDialog, int isLiveType);

    public abstract void getTakenBack(int position);

    public abstract void getTeacherList(String gradeId, String subjectId);


    private static final boolean USE_PLAYER_PROXY = true;
    boolean isPanoVideo;
    //////////////////////////////////////
//////////////////////////////////////
    private Context mContext = getContext();
    private String path = "";
    private Bundle mBundle;
    //////////////////////////////////////
    private boolean isContinue = true;
    private boolean isComplete = true;

    private int skinBuildType;
    private UIPlayContext uicontext;
    //////////////////////////////////////
    private V4PlaySkin skin;
    private int playMode;
    private long lastPosition;
    private ISplayer player;
    //////////////////////////////////////
    private PlayContext playContext;
    private ILeVideoView videoView;
    private ILetvPlayerController playerController;

    public class LetvsSimplePlayBoard {

        /**
         * 处理播放器的回调事件
         */
        private OnPlayStateListener playStateListener = new OnPlayStateListener() {
            @Override
            public void videoState(int state, Bundle bundle) {
                handleADState(state, bundle);// 处理广告类事件
                handleVideoInfoState(state, bundle);// 处理视频信息事件
                handlePlayState(state, bundle);// 处理播放器类事件
                handleLiveState(state, bundle);// 处理直播类事件

            }
        };

        // surfaceView生命周期
        private SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                KLog.d();
                stopAndRelease();
                KLog.d("ok");
            }

            @Override
            public void surfaceCreated(final SurfaceHolder holder) {
                if (!isPanoVideo) {
                    KLog.d();
                   /* if (onPause){
                        createOnePlayer(holder.getSurface());
                    }*/
                    createOnePlayer(holder.getSurface());
                    StringUtils.Log(TAG, "surfaceCreated");
                    KLog.d("ok");
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (player != null) {
                    PlayerParamsHelper.setViewSizeChange(player, width, height);
                }
            }
        };

        public void onResume() {
            resume();
        }

        public void onPause() {
            pause();
        }

        public void onDestroy() {
            destroy();
        }

        // ////////////////////////////////////////////////////////////////////////////////////////////
        // ////////////////////////////////////////////////////////////////////////////////////////////
        // ////////////////////////////////////////////////////////////////////////////////////////////

        public void onConfigurationChanged(Configuration newConfig) {
            if (videoView != null) {
                videoView.setVideoLayout(-1, 0);
            }
        }

        /**
         * 初始化下载模块
         */
        private void initDownload() {
            final String uuid = mBundle.getString(PlayProxy.PLAY_UUID);
            final String vuid = mBundle.getString(PlayProxy.PLAY_VUID);
            final DownloadCenter downloadCenter = DownloadCenter.getInstances(mContext);
            if (downloadCenter != null && downloadCenter.isDownloadCompleted(vuid)) {
                path = downloadCenter.getDownloadFilePath(vuid);
            }
            skin.setOnDownloadClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadCenter.allowShowMsg(false);
                    downloadCenter.setDownloadRateText(playContext.getDefinationIdByType(uicontext.getCurrentRateType()));
                    downloadCenter.downloadVideo("", uuid, vuid);
                }
            });
        }

        public void init(Bundle m, V4PlaySkin s) {
            mBundle = m;
            skin = s;
            playMode = mBundle.getInt(PlayProxy.PLAY_MODE, -1);
            switch (playMode) {
                case EventPlayProxy.PLAYER_VOD:
                    skinBuildType = V4PlaySkin.SKIN_TYPE_A;
                    break;
                case EventPlayProxy.PLAYER_LIVE:
                    skinBuildType = V4PlaySkin.SKIN_TYPE_B;
                    break;
                case EventPlayProxy.PLAYER_ACTION_LIVE:
                    skinBuildType = V4PlaySkin.SKIN_TYPE_C;
                    break;
                default:
                    break;
            }

            isPanoVideo = mBundle.getBoolean(PlayProxy.BUNDLE_KEY_ISPANOVIDEO);
            if (isPanoVideo) {
//            initPanoVideoView();
            } else {
                initVideoView();
            }
            initPlayContext();
            initPlayerSkin();
            if (playMode == EventPlayProxy.PLAYER_VOD) {
                //记录上次播放位置
                //  PlayerAssistant.loadLastPosition(playContext, mBundle.getString(PlayProxy.PLAY_UUID), mBundle.getString(PlayProxy.PLAY_VUID));
                initDownload();
            }
        }

        private void initPlayContext() {

            playContext = new PlayContext(mContext);
            playContext.setVideoContainer(skin);
            playContext.setUsePlayerProxy(USE_PLAYER_PROXY);
            playContext.setVideoContentView(videoView.getMysef());
            playerController = new LetvPlayerControllerImp();
            playerController.setPlayContext(playContext);


        }

        private void resume() {
            if (skin != null) {
                skin.onResume();
            }
            if (player != null && playContext.getErrorCode() == -1) {
                player.start();
            }
        }

        private void pause() {
            if (isContinue && skinBuildType == V4PlaySkin.SKIN_TYPE_A && player != null && (int) player.getCurrentPosition() > 0) {
                PlayerAssistant.saveLastPosition(mContext, mBundle.getString(PlayProxy.PLAY_UUID), mBundle.getString(PlayProxy.PLAY_VUID), (int) (player.getCurrentPosition()),
                        playContext.getCurrentDefinationType());
            }
            if (skin != null) {
                skin.onPause();
            }
            if (player != null) {
                player.pause();
            }
        }

        private void destroy() {
            if (skin != null) {
                skin.onDestroy();
            }
            if (playContext != null) {
                playContext.destory();
            }
        }

        /**
         * 初始化播放器皮肤
         */
        private void initPlayerSkin() {

            if (playMode == EventPlayProxy.PLAYER_ACTION_LIVE) {// 活动直播
                PlayerSkinFactory.initActionLivePlaySkin(skin, V4PlaySkin.SKIN_TYPE_C, mBundle.getString(PlayProxy.PLAY_ACTIONID), new IActionCallback() {
                    @Override
                    public void switchMultLive(String liveId) {
                        LeLog.d(TAG, "switchMultLive : " + liveId);
                        ((LetvPlayer) player).switchMultLive(liveId);
                    }

                    @Override
                    public ISplayer createPlayerCallback(SurfaceHolder holder, String path, OnPlayStateListener playStateListener) {
                        return PlayerAssistant.createActionLivePlayer(mContext, holder, path, playStateListener);
                    }
                }, isPanoVideo);
            } else {
                PlayerSkinFactory.initPlaySkin(skin, skinBuildType, isPanoVideo);
            }
            /**
             * 获取皮肤的上下文
             */
            uicontext = skin.getUIPlayContext();

        }

        private void initVideoView() {
            videoView = new ReSurfaceView(mContext);
            videoView.getHolder().addCallback(surfaceCallback);
            videoView.setVideoContainer(null);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            skin.addView(videoView.getMysef(), params);
        }

        /**
         * 停止播放，并且记录最后播放时间
         */
        private void stopAndRelease() {
            if (player != null) {
                KLog.d();
                if (player != null) {
                    lastPosition = player.getCurrentPosition();
                    if (mIsLogin) {
                        StringUtils.Log(TAG, "lastPosition=" + lastPosition);
                        if (mOnPause) {
                            YJUserStudyData.catalogEndStudy(mYjCourseModel, playPosition, lastPosition);
                        }

                    }
                }
                if (player != null) {
                    player.stop();
                }
                if (player != null) {
                    player.reset();
                }
                if (player != null) {
                    player.release();
                }
                if (player != null) {
                    player = null;
                }
                KLog.d("release ok!");
            }
        }

        // ////////////////////////////////////////////////////////////////////////////////////////////
        // ////////////////////////////////////////////////////////////////////////////////////////////
        // ////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * 创建一个新的播放器
         *
         * @param surface
         */
        private void createOnePlayer(Surface surface) {
            player = PlayerFactory.createOnePlayer(playContext, mBundle, playStateListener, surface);
            if (!TextUtils.isEmpty(path)) {
                playContext.setUsePlayerProxy(false);
            }
            player.setDataSource(path);
            if (lastPosition > 0 && mBundle.getInt(PlayProxy.PLAY_MODE) == EventPlayProxy.PLAYER_VOD) {
                player.seekTo(lastPosition);
            }
            player.prepareAsync();
            /**
             * 皮肤关联播放器
             */
            playerController.setPlayer(player);
            skin.registerController(playerController);
        }

        /**
         * 处理直播相关信息
         *
         * @param state
         * @param bundle
         */
        private void handleLiveState(int state, Bundle bundle) {
            switch (state) {
                case EventPlayProxy.PROXY_WATING_SELECT_ACTION_LIVE_PLAY:
                    /**
                     * 活动直播
                     */
                    PlayContext playContextAction = (PlayContext) player.getPlayContext();
                    ActionInfo actionInfo = playContextAction.getActionInfo();
                    if (actionInfo != null) {
                        // 皮肤层设置活动信息
                        uicontext.setActionInfo(actionInfo);
                        /**
                         * 如果当前活动直播是多个直播窗口， 选择一路可用的活动直播
                         */
                        LiveInfo liveInfo = actionInfo.getFirstCanPlayLiveInfo();
                        if (liveInfo != null) {
                            playContextAction.setLiveId(liveInfo.getLiveId());
                        }
                        // 活动直播中如果包含多个live信息 播放途中调用 LetvPlayer 中
                        // switchMultLive（liveId）切换播放
                    }
                    break;
                case EventPlayProxy.PROXY_SET_ACTION_LIVE_CURRENT_LIVE_ID:
                    // 当前播放的活动直播的liveID
                    uicontext.setMultCurrentLiveId(bundle.getString("liveId"));
                    break;

                default:
                    break;
            }
        }

        /**
         * 处理视频信息
         *
         * @param state
         * @param bundle
         */
        private void handleVideoInfoState(int state, Bundle bundle) {
            switch (state) {
                case EventPlayProxy.PROXY_WAITING_SELECT_DEFNITION_PLAY:
                    /**
                     * 皮肤获取码率
                     */
                    PlayContext playContext = (PlayContext) player.getPlayContext();
                    if (playContext != null) {
                        uicontext.setRateTypeItems(playContext.getDefinationsMap());
                    }

                    /**
                     * 获取码率
                     */
                    Map<Integer, String> definationsMap = playContext.getDefinationsMap();
                    if (definationsMap != null) {
                        Iterator<Map.Entry<Integer, String>> it = definationsMap.entrySet().iterator();
                        while (it.hasNext()) {
                            Map.Entry<Integer, String> next = it.next();
                            next.getKey();
                            next.getValue();
                        }
                    }


                    break;
                case EventPlayProxy.PROXY_VIDEO_INFO_REFRESH:
                    if (uicontext == null || player == null || player.getPlayContext() == null) {
                        return;
                    }
                    uicontext.setVideoTitle(((PlayContext) player.getPlayContext()).getVideoTitle());
//                    uicontext.setVideoTitle(mYjCourseModel.getCourseCatalogList().get(mLastPosition).getName());
                    uicontext.setDownloadable(((PlayContext) player.getPlayContext()).isCanbeDownload());
                    break;
            }
        }

        /**
         * 处理广告事件
         *
         * @param state
         * @param bundle
         */
        private void handleADState(int state, Bundle bundle) {
            switch (state) {
                case EventPlayProxy.PLAYER_PROXY_AD_START:
                    uicontext.setIsPlayingAd(true);// 广告播放时间
                    break;
                case EventPlayProxy.PLAYER_PROXY_AD_END:
                    uicontext.setIsPlayingAd(false);
                    break;
                default:
                    break;
            }
        }

        /**
         * 处理播放器事件
         *
         * @param state
         * @param bundle
         */
        private void handlePlayState(int state, Bundle bundle) {
            switch (state) {
                case ISplayer.MEDIA_EVENT_PREPARE_COMPLETE:
                    // TODO 获取当前播放的码率
                    uicontext.setCurrentRateType(playContext.getCurrentDefinationType());
                    if (uicontext.isClickPauseByUser() && player != null) {
                        player.setVolume(0, 0);
                    }
                    if (player != null) {
                        if (isPlayFlag) {
                            if (mOnPause) {
                                player.start();
                                StringUtils.Log(TAG, "mOnPause=true");
                            } else {
                                StringUtils.Log(TAG, "mOnPause=false");
                                player.pause();
                                player.reset();

                                mOnPause = true;
                            }
                        } else {

                        }
                    }
                    StringUtils.Log("1111111111111", "第" + mPosition + "条开始播放了");
                    break;
                case ISplayer.MEDIA_EVENT_FIRST_RENDER:
                    if (uicontext.isClickPauseByUser() && player != null) {
                        player.pause();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                player.setVolume(1, 1);
                            }
                        }, 300);
                    }
                    StringUtils.Log("1111111111111", "MEDIA_EVENT_FIRST_RENDER");
                    break;
                case ISplayer.MEDIA_EVENT_VIDEO_SIZE:
                    if (videoView != null && player != null) {
                        videoView.onVideoSizeChange(player.getVideoWidth(), player.getVideoHeight());
                    }
                    break;

                case ISplayer.MEDIA_EVENT_PLAY_COMPLETE:
                    if (uicontext.isClickPauseByUser() && player != null) {
                        player.getDuration();
                    }

                    if (mIsLogin) {
                        StringUtils.Log(TAG, "lastPosition=" + lastPosition);
                        YJUserStudyData.catalogEndStudy(mYjCourseModel, playPosition, 0);
                        isComplete = false;
                    }
                    //  catalogEndStudy(mPosition, player.getDuration());

                    if (!mYjCourseModel.getCoursePayWay().equals("FREE") && mYjCourseModel.getCourseCatalogList().get(playPosition).getIsTry().equals("Yes") && mYjCourseModel.getIsBuy().equals("No")) {
                        showToast("试听已结束，请购买课程");
                    }
                    StringUtils.Log("1111111111111", "MEDIA_EVENT_PLAY_COMPLETE");
                    /**
                     * 检查活动直播状态
                     */
                    PlayerAssistant.checkActionLiveStatus(player, new LiveStatusCallback() {
                        @Override
                        public void onSuccess(LiveStatus liveStatus) {
                            LeLog.dPrint("123", liveStatus.toString());
                        }

                        @Override
                        public void onFail(VolleyError error) {
                            LeLog.ePrint("123", "getActiveLiveStatus error ", error);
                        }
                    });
                    break;
            }
        }

        // //////////////////////////////////////////////////////////////////////////////////////////////////
        // //////////////////////////////////////////////////////////////////////////////////////////////////
        // //////////////////////////////////////////////////////////////////////////////////////////////////

        public ISplayer getPlayer() {
            return player;
        }

    }
}