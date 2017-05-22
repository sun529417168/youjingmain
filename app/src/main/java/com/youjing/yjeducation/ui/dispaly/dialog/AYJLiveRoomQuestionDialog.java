package com.youjing.yjeducation.ui.dispaly.dialog;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJAnwserResultModel;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJQuestionsModel;
import com.youjing.yjeducation.question.CustomerInfo;
import com.youjing.yjeducation.question.GlobalProperty;
import com.youjing.yjeducation.question.QuestionsData;
import com.youjing.yjeducation.question.QuestionsDataInfo;
import com.youjing.yjeducation.question.QuestionsViewPagerFragment;
import com.youjing.yjeducation.question.RequestUrl;
import com.youjing.yjeducation.question.TiXingJieShao;
import com.youjing.yjeducation.question.YJQuestionsViewPagerFragment;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJAnswerOverDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJAnswerResultDialog;
import com.youjing.yjeducation.util.TimeUtil;
import com.youjing.yjeducation.wiget.CustomToast;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVDialog;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * user  秦伟宁
 * Date 2016/6/16
 * Time 11:12
 */
@VLayoutTag(R.layout.dialog_live_question)
public class AYJLiveRoomQuestionDialog extends AVDialog implements IVClickDelegate{

    @VViewTag(R.id.img_delete)
    private RelativeLayout img_delete;
    @VViewTag(R.id.vPager_QuestionsMain_content)
    private static ViewPager viewPager;
    @VViewTag(R.id.txt_page_current)
    private TextView txt_page_current;
    @VViewTag(R.id.txt_page_size)
    private TextView txt_page_size;
    @VViewTag(R.id.txt_time)
    private TextView txt_time;
   @VViewTag(R.id.txt_question_over)
    private TextView txt_question_over;
    @VViewTag(R.id.re_next_question)
    private RelativeLayout re_next_question;
    @VViewTag(R.id.re_last_question)
    private RelativeLayout re_last_question;


    private String TAG = "AYJLiveRoomQuestionDialog";

    private Integer type;
    private String chapterId, paperId, terms, knowledge, subjectId, customerId, sectionId;
    private List<QuestionsViewPagerFragment> fragments = new ArrayList<QuestionsViewPagerFragment>();
    private List<YJQuestionsViewPagerFragment> mfragments = new ArrayList<YJQuestionsViewPagerFragment>();
    private List<QuestionsDataInfo> infos;
    public static Map<String, String> questionsAnswerMap = null;
    public List<TiXingJieShao> list;
    private static Map<String, String> mark;
    public static String[] ls_write;
    private String currentMark;
    private int lookAtPosition = 0, currentPageScrollStatus;
    private String currentExamQuestionid = "";// 当前页面试题ID



    public static final VParamKey<String> COURSECATALOGID = new VParamKey<>(null);
    public static final VParamKey<String> COURSEVIDEOID = new VParamKey<>(null);
    public static final VParamKey<String> COURSEID = new VParamKey<>(null);
    private String courseVideoId,courseId,courseCatalogId;
    private int sum;

    Timer timer;
    TimerTask  timerTask;
    private int  mTime;
    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1) {
                if (mTime >0 ){
                    mTime --;
                    txt_time.setText(TimeUtil.getTimeFromInt_(mTime));
                }else {
                    txt_time.setText("0:00");
                    timer.cancel();
                    timer = null;
                    isShowDialog = false;
                    getQuestionsResultForLive();
                }


            }
        }
    };
    @Override
    protected void onLoadedView() {

        courseCatalogId = getTransmitData(COURSECATALOGID);
        courseVideoId = getTransmitData(COURSEVIDEOID);
        courseId = getTransmitData(COURSEID);


        QuestionsMainViewPagerAdapter pagerAdapter = new QuestionsMainViewPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                //currentExamQuestionid = fragments.get(arg0).getInfo().getExamQuestionid();
                //currentMark = mark.get(currentExamQuestionid);
               // CustomerInfo.setExamQuestionId(currentExamQuestionid);
              /*  if ("-1".equals(currentMark)) {

                } else {

                }
                lookAtPosition = arg0;
*/
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                txt_page_current.setText (position+1+"");
                if (position == 0){
                    re_last_question.setVisibility(View.GONE);
                }else {
                    re_last_question.setVisibility(View.VISIBLE);
                }

                if (position == mfragments.size() - 1) {
                    if (currentPageScrollStatus == 1) {
                        // SubmitMode();
                    }
                    re_next_question.setVisibility(View.GONE);
                    txt_question_over.setVisibility(View.VISIBLE);
                }else {
                    re_next_question.setVisibility(View.VISIBLE);
                    txt_question_over.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentPageScrollStatus = state;

            }

        });


        if (!TextUtils.isEmpty(courseVideoId) && !TextUtils.isEmpty(courseId)){
            getQuestionsForLive();
        }

        getNotifyListener();
        // getQuestionsData();
    }
    @Override
    public void onClick(View view) {
        if (view == img_delete) {
            close();
        }else if(view == txt_question_over){
            getQuestionsResultForLive();
        }else if(view == re_next_question){
            NextPage();
        }else if(view == re_last_question){
            LastPage() ;
        }
    }

    public void getNotifyListener() {
        addListener(YJNotifyTag.ANSWER_OVER, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object o) {
                isShowDialog = false;
                getQuestionsResultForLive();
            }
        });
    }

    class QuestionsMainViewPagerAdapter extends FragmentStatePagerAdapter {

        public QuestionsMainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mfragments.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return mfragments.get(arg0);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

    public void getQuestionsData() {
        CustomerInfo.setQuestionsType(3);
        CustomerInfo.setCategoryId("4");

        switch (CustomerInfo.getQuestionsType()) {
            case GlobalProperty.TYPE_SCROLL:// 智能组卷
               /* type = CustomerInfo.getQuestionsType();
                terms = CustomerInfo.getScrollTerms();
                subjectId = CustomerInfo.getSubjectId();
                customerId = CustomerInfo.getCustomerId();*/
                // 06-22 18:09:37.576: D/22222222222222(19937): type=3  terms=0__0__0__50  subjectId=12  customerId=470472
                type=3;
                terms="0__0__0__50";
                subjectId="12";
                customerId="470472";
                CustomerInfo.setShowGradeType("TYPE_SCROLL");
                new QuestionsAsyncTask(type.toString(), null, null, terms, null,
                        subjectId, customerId, null, viewPager).execute();
                break;

        }
    }
    class QuestionsAsyncTask extends AsyncTask<String, Void, QuestionsData> {

        private QuestionsAsyncTask(String type, String chapterId,
                                   String paperId, String terms, String knowledge,
                                   String subjectId, String customerId, String sectionId,
                                   ViewPager viewPager) {

        }

        @Override
        protected QuestionsData doInBackground(String... params) {
            Map<String, Object> data = new HashMap<String, Object>();
            data.put("type", type);
            data.put("chapterId", chapterId);
            data.put("paperId", paperId);
            data.put("terms", terms);
            data.put("knowledge", knowledge);
            data.put("subjectId", subjectId);
            data.put("customerId", customerId);
            data.put("sectionId", sectionId);
            String result = RequestUrl.sendData(GlobalProperty.makePaper, data, getContext());
            if (result != null) {
                QuestionsData questionsData = JSON.parseObject(result, QuestionsData.class);

                return questionsData;
            }
            return null;
        }

        @Override
        protected void onPostExecute(QuestionsData result) {
            if (result != null) {
                if (Integer.valueOf(result.getC()) == 200
                        && result.getData() != null
                        && result.getData().size() > 0) {
                    CustomerInfo.setPaperResultId(result.getPaperResultId());
                    infos = result.getData();

                    StringUtils.Log(TAG,"infos="+infos.toString());
                    questionsAnswerMap = new HashMap<String, String>();
                    currentExamQuestionid = infos.get(0).getExamQuestionid();
                    CustomerInfo.setExamQuestionId(currentExamQuestionid);

                    for (QuestionsDataInfo info : infos) {
                        questionsAnswerMap.put(info.getId(), null);
                    }
                    int sum = infos.size();// 一共多少道题

                    mark = new HashMap<String, String>();
                    for (int i = 0; i < sum; i++) {// /////////////
                        fragments.add(new QuestionsViewPagerFragment(getActivity().getApplicationContext(), infos.get(i), (i + 1),
                                sum));
                        // 添加当前题目是否标记到map中
                        mark.put(infos.get(i).getExamQuestionid(), infos.get(i)
                                .getMarkId());

                    }
                    // 初始化第一个标记
                    currentExamQuestionid = fragments.get(0).getInfo()
                            .getExamQuestionid();
                    currentMark = mark.get(currentExamQuestionid);

                    QuestionsMainViewPagerAdapter pagerAdapter = new QuestionsMainViewPagerAdapter(getActivity().getSupportFragmentManager());
                    viewPager.setAdapter(pagerAdapter);
                    // viewPager.setOffscreenPageLimit(2);
                    //   initGridView(infos);
                }
            }
        }
    }
    public static void NextPage() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                YJQuestionsViewPagerFragment.setNextPage(true);
            }
        }, 300);

    }
    public static void LastPage() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        }, 300);

    }


    //获得答题的列表
    private void getQuestionsForLive() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseCatalogId", courseVideoId);
            objectMap.put("courseId", courseId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_QUESTIONS_LIVE, objectMap, true, new TextHttpResponseHandler() {
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
                            List<YJQuestionsModel> yjQuestionsModelList = JSON.parseArray(jsonData.getString("questionsList"), YJQuestionsModel.class);

                            //计时相关
                            String time = jsonData.getString("timeLimit");
                            StringUtils.Log(TAG, "yjQuestionsModelList=" + yjQuestionsModelList.toString());
                            if (!TextUtils.isEmpty(time) && !time.equals("0")) {
                                mTime = Integer.parseInt(time);
                                txt_time.setText(TimeUtil.getTimeFromInt_(mTime));
                            } else {
                                txt_time.setText("0:00");
                            }
                            timerTask = new TimerTask() {
                                @Override
                                public void run() {
                                    Message msg = new Message();
                                    msg.what = 1;
                                    timeHandler.sendMessage(msg);
                                }
                            };
                            timer = new Timer();
                            timer.schedule(timerTask, 1000, 1000);


                            questionsAnswerMap = new HashMap<String, String>();
                            for (YJQuestionsModel yjQuestionsModel : yjQuestionsModelList) {
                                questionsAnswerMap.put(yjQuestionsModel.getQuestionsId(), null);
                            }


                             sum = yjQuestionsModelList.size();// 一共多少道题
                            txt_page_size.setText("/" + sum);
                            for (int j = 0; j < sum; j++) {// /////////////
                                mfragments.add(new YJQuestionsViewPagerFragment(getActivity().getApplicationContext(), yjQuestionsModelList.get(j), (j + 1), sum));

                            }

                            QuestionsMainViewPagerAdapter pagerAdapter = new QuestionsMainViewPagerAdapter(getActivity().getSupportFragmentManager());
                            viewPager.setAdapter(pagerAdapter);
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


    private boolean isShowDialog = true;
    //答题结果
    private void getQuestionsResultForLive() {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("courseVideoId", courseVideoId);
            objectMap.put("courseCatalogId", courseCatalogId);
            objectMap.put("courseId", courseId);


            List<Map<String, Object>> answerList = new ArrayList<Map<String, Object>>();
            if (AYJLiveRoomQuestionDialog.questionsAnswerMap!=null) {
                Set<String> keys = AYJLiveRoomQuestionDialog.questionsAnswerMap.keySet();
                for (String key : keys) {
                    String answer = AYJLiveRoomQuestionDialog.questionsAnswerMap.get(key);
                    if (answer != null) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("questionsId", key);
                        map.put("answer", answer.substring(0, 1));
                        map.put("answerId", answer.substring(1,answer.length()));
                        StringUtils.Log(TAG, "questionsId=" + key);
                        StringUtils.Log(TAG, "answer=" + answer.substring(0, 1));
                        StringUtils.Log(TAG, "answerId="+answer.substring(1,answer.length()));
                        answerList.add(map);
                    }else {
                        if (isShowDialog){
                            YJAnswerOverDialog  dialog = new YJAnswerOverDialog();
                            showDialog(dialog);
                            return;
                        }

                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("questionsId", key);
                        answerList.add(map);
                    }
                }
            }
            objectMap.put("resultList", JSON.toJSONString(answerList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.GET_QUESTIONS_RESULT_LIVE, objectMap, true, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "getQuestionsResultForLive失败=" + s);
                showToast(getString(R.string.no_net_work));
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    StringUtils.Log(TAG, "getQuestionsResultForLive成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:
                            JSONObject jsonData = new JSONObject(json.getString("data"));
                            List<YJAnwserResultModel> yjAnwserResultModelList = JSON.parseArray(jsonData.getString("resultListMap"), YJAnwserResultModel.class);

                            close();
                            YJAnswerResultDialog yjAnswerResultDialog = new YJAnswerResultDialog();
                            VParams data = createTransmitData(yjAnswerResultDialog.ANSWER_RESULT_LIST, yjAnwserResultModelList);
                            showDialog(yjAnswerResultDialog,data);
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
    @Override
    public void destroy() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask = null;
        }
        super.destroy();
    }

}
