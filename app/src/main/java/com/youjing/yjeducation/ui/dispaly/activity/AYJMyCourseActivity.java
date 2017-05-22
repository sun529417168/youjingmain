package com.youjing.yjeducation.ui.dispaly.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJMyCourseModel;
import com.youjing.yjeducation.talkfun.NetMonitor;
import com.youjing.yjeducation.ui.actualize.activity.YJCoursePlayNewActivity;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJShareDialog;
import com.youjing.yjeducation.util.ACache;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.DialogUtil;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.util.TimeUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.ArrayList;
import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/4/12
 * Time 21:00
 */
@VLayoutTag(R.layout.activity_my_course)
public abstract class AYJMyCourseActivity extends BaseSwipeBackActivity implements IVClickDelegate, IVAdapterDelegate {
    @VViewTag(R.id.lv_my_course)
    private ListView mLv_my_course;
    private String TAG = "YJMyCourseActivity";
    private VAdapter mVAdapter;

    private List<YJMyCourseModel> yjCourseModelList;
    private IUiListener qqShareListener;
    private ACache mCache;
    protected Dialog progDialog;

    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "我的课程", true);
        if (SharedUtil.getInteger(getContext(), "baseIndex", 0) != -1) {
            SharedUtil.setInteger(getContext(), "baseIndex", SharedUtil.getInteger(getContext(), "baseIndex", 0) + 1);
        }
        mCache = ACache.get(this);
        if (NetMonitor.isNetworkAvailable(this)) {//如果有网络走请求数据，否则本地缓存
            progDialog = DialogUtil.showWaitDialog(getContext());//加载数据的loading 先注释掉 用的时候换样式
            initData();
        } else {
            yjCourseModelList = (List<YJMyCourseModel>) StringUtils.arrayList((Object[]) mCache.getAsObject("yjCourseModelList"));
            initUI();
        }

    }

    private void initUI() {
        if (yjCourseModelList == null) {
            showToast(getString(R.string.no_net_work));
            finish();
        }
        if (yjCourseModelList.size() == 0) {
            findViewById(R.id.relayout_my_course).setBackgroundColor(getResources().getColor(R.color.huise));
            mLv_my_course.setVisibility(View.GONE);
            findViewById(R.id.my_course_nothing).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.relayout_my_course).setBackgroundColor(getResources().getColor(R.color.white));
            mLv_my_course.setVisibility(View.VISIBLE);
            findViewById(R.id.my_course_nothing).setVisibility(View.GONE);
        }

        if (mVAdapter == null) {
            mVAdapter = new VAdapter(this, mLv_my_course);
            mLv_my_course.setAdapter(mVAdapter);
        } else {
            mVAdapter.notifyDataSetChanged();
        }
        mLv_my_course.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (ClickUtil.isFastDoubleClick()) {
                    return;
                }
                Intent in = new Intent(AYJMyCourseActivity.this, YJCoursePlayNewActivity.class);
                in.putExtra("courseId", yjCourseModelList.get(i).getCourseId());
                startActivity(in);
            }
        });


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

    private void initData() {
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MY_COURSE, null, true, new TextHttpResponseHandler() {
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
                if (progDialog.isShowing()) {//加载数据的loading 先注释掉 用的时候换样式
                    progDialog.dismiss();
                }
                try {
                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            yjCourseModelList = JSON.parseArray(jsonData.getString("customerCourseList"), YJMyCourseModel.class);
                            mCache.put("yjCourseModelList", yjCourseModelList.toArray());
                            initUI();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_QQ_SHARE) {
            if (resultCode == Constants.ACTIVITY_OK) {
                Tencent.onActivityResultData(requestCode, resultCode, data, qqShareListener);
            }
        }
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJOrderItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjCourseModelList != null) {
            return yjCourseModelList.size();
        } else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.my_course_item)
    class YJOrderItem extends AVAdapterItem {
        @VViewTag(R.id.txt_name)
        private TextView mTxt_name;
        @VViewTag(R.id.txt_teacher_name)
        private TextView mTxt_teacher_name;
        @VViewTag(R.id.txt_learn_info)
        private TextView mTxt_learn_info;
        @VViewTag(R.id.ll_learn_over)
        private LinearLayout mLl_learn_over;
        @VViewTag(R.id.txt_learn_statue)
        private TextView mTxt_learn_statue;
        @VViewTag(R.id.view_line)
        private View mView_line;
        @VViewTag(R.id.view_line_background)
        private ProgressBar mView_line_background;
        @VViewTag(R.id.img_course_teacher)
        private ImageView mImg_course_teacher;
        @VViewTag(R.id.img_learn_share)
        private ImageView mImg_learn_share;
        @VViewTag(R.id.my_course_item_lookAllTime)
        private TextView mMy_course_item_lookAllTime;
        @VViewTag(R.id.my_course_item_lookCount)
        private TextView mMy_course_item_lookCount;
        @VViewTag(R.id.my_course_item_courseRanking)
        private TextView mMy_course_item_courseRanking;
        @VViewTag(R.id.my_course_item_share_text)
        private TextView mMy_course_item_share_text;

        private int bgWidth;

        @Override
        public void update(int position) {
            super.update(position);

//            bgWidth = mView_line_background.getMeasuredWidth();// 北京空间长度

            if (yjCourseModelList != null) {
                mTxt_name.setText(yjCourseModelList.get(position).getName());
                mTxt_teacher_name.setText(yjCourseModelList.get(position).getTrueName());
                mView_line_background.setProgress(TextUtils.isEmpty(yjCourseModelList.get(position).getStudyScheduler()) || "0".equals(yjCourseModelList.get(position).getStudyScheduler()) ? 0 : Integer.parseInt(yjCourseModelList.get(position).getStudyScheduler()));
                if (!TextUtils.isEmpty(yjCourseModelList.get(position).getCoursePic())) {
                    BitmapUtils.create(getContext()).display(mImg_course_teacher, yjCourseModelList.get(position).getCoursePic());
                } else {
                    mImg_course_teacher.setImageResource(R.mipmap.img_no_data_bg);
                }
                // 课程剩余时间
                int restTime = TimeUtil.compareTime(TimeUtil.getCurrentTime(), yjCourseModelList.get(position).getEndDate());
                if (yjCourseModelList.get(position).getCourseServiceStatus().equals("OVER")) {
                    mTxt_learn_info.setText("课程服务已结束");
                } else {
                    if (restTime > 0) {
                        mTxt_learn_info.setText("课程有效期剩余" + restTime + "天");
                    } else {
                        mTxt_learn_info.setText("课程服务已结束");
                    }
                }


                //上次学习课程时间
                int learnTime = Integer.parseInt(yjCourseModelList.get(position).getLastStudyTime());

                int yellow = Color.parseColor("#ff9f19");
                int bule = Color.parseColor("#99ccff");
                if (yjCourseModelList.get(position).getStudyStatus().equals("Study_Over")) {//学完
                    mLl_learn_over.setVisibility(View.VISIBLE);
                    mTxt_learn_statue.setText("学习完成");
                    mView_line.setBackgroundColor(yellow);
                    mMy_course_item_lookAllTime.setText("0".equals(yjCourseModelList.get(position).getLookAllTime()) ? "0" : StringUtils.getMinute(yjCourseModelList.get(position).getLookAllTime()));
                    mMy_course_item_lookCount.setText(yjCourseModelList.get(position).getLookCount());
                    if (TextUtils.isEmpty(yjCourseModelList.get(position).getCourseRanking()) || "0".equals(yjCourseModelList.get(position).getCourseRanking())) {
                        mMy_course_item_courseRanking.setText("暂无");
                        mMy_course_item_courseRanking.setTextSize(17);
                    } else {
                        mMy_course_item_courseRanking.setText(yjCourseModelList.get(position).getCourseRanking());
                    }
                    changeWidth(mView_line, 1, 1.0);
                } else if (yjCourseModelList.get(position).getStudyStatus().equals("Study_Ing")) {//学习中
                    mTxt_learn_statue.setText("上次观看到 第" + yjCourseModelList.get(position).getLastNumber() + "节 " + TimeUtil.displayDuration(learnTime).replace(":", "'") + "\"");
                    mLl_learn_over.setVisibility(View.GONE);
                    mView_line.setBackgroundColor(bule);
                    changeWidth(mView_line, Integer.parseInt(yjCourseModelList.get(position).getStudyScheduler()), 100.0);
                } else if (yjCourseModelList.get(position).getStudyStatus().equals("Study_No")) {//没开始学
                    mLl_learn_over.setVisibility(View.GONE);
                    mView_line.setBackgroundColor(bule);
                    changeWidth(mView_line, 0, 1.0);
                    mTxt_learn_statue.setText("尚未开始学习");
                }

            } else {

            }
            mImg_learn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    YJShareDialog dialog = new YJShareDialog();
                    showDialog(dialog);
                }
            });
            mMy_course_item_share_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    YJShareDialog dialog = new YJShareDialog();
                    showDialog(dialog);
                }
            });
        }

        public void changeWidth(View view, int currentCharcter, Double allCharcter) {
            RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) view.getLayoutParams();// 取控件view当前的布局参数
            Double cunter = currentCharcter / allCharcter;// 百分比
            linearParams.width = (int) (cunter * YJConfig.mScreenWidth);
            view.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件view
        }
    }


}