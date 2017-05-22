package com.youjing.yjeducation.question;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import com.youjing.yjeducation.util.StringUtils;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/6/22
 * Time 15:12
 */

public class QuestionsActivity extends FragmentActivity {
    private static ViewPager viewPager;
    private  TextView tv_title;

    private Integer type;
    private String chapterId, paperId, terms, knowledge, subjectId, customerId, sectionId;
    private List<QuestionsViewPagerFragment> fragments = new ArrayList<QuestionsViewPagerFragment>();
    private List<QuestionsDataInfo> infos;
    public static Map<String, String> questionsAnswerMap = null;
    public List<TiXingJieShao> list;
    private static Map<String, String> mark;
    public static String[] ls_write;
    private String currentMark;
    private int lookAtPosition = 0, currentPageScrollStatus;
    private android.app.Fragment detailsAssertFragment;

    private ListView listJieshao;
    private String currentExamQuestionid = "";// 当前页面试题ID
    private String TAG = "QuestionsActivity";// 当前页面试题ID
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.questions_layout);
        viewPager = (ViewPager) findViewById(R.id.vPager_QuestionsMain_content);
        tv_title = (TextView) findViewById(R.id.textView_QuestionsMain_title);
        // lv_typeChoise = (ListView)
        // findViewById(R.id.QuestionsMain_block_listview);

        QuestionsMainViewPagerAdapter pagerAdapter = new QuestionsMainViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                currentExamQuestionid = fragments.get(arg0).getInfo()
                        .getExamQuestionid();
                currentMark = mark.get(currentExamQuestionid);
                CustomerInfo.setExamQuestionId(currentExamQuestionid);
                if ("-1".equals(currentMark)) {

                } else {

                }
                lookAtPosition = arg0;

            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
                if (position == fragments.size() - 1) {
                    if (currentPageScrollStatus == 1) {
                        // SubmitMode();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                currentPageScrollStatus = state;
            }

        });

        getQuestionsData();

    }

    public void getQuestionsData() {
        CustomerInfo.setQuestionsType(3);
        CustomerInfo.setCategoryId("4");

        switch (CustomerInfo.getQuestionsType()) {
            case GlobalProperty.TYPE_SECTION:// 章节练习
                type = CustomerInfo.getQuestionsType();
                chapterId = CustomerInfo.getChapterId();
                subjectId = CustomerInfo.getSubjectId();
                customerId = CustomerInfo.getCustomerId();
                sectionId = CustomerInfo.getLeafId();


                tv_title.setText("章节练习");
                CustomerInfo.setShowGradeType("TYPE_SECTION");
                new QuestionsAsyncTask(type.toString(), chapterId, null, null,
                        null, subjectId, customerId, sectionId, viewPager)
                        .execute();
                break;

            case GlobalProperty.TYPE_TOPIC:// 历年真题
                type = CustomerInfo.getQuestionsType();
                paperId = CustomerInfo.getPaperId();
                subjectId = CustomerInfo.getSubjectId();
                customerId = CustomerInfo.getCustomerId();
                tv_title.setText("历年真题");
                CustomerInfo.setShowGradeType("TYPE_TOPIC");
                new QuestionsAsyncTask(type.toString(), null, paperId, null, null,
                        subjectId, customerId, null, viewPager).execute();
                break;

            case GlobalProperty.TYPE_SCROLL:// 智能组卷
               /* type = CustomerInfo.getQuestionsType();
                terms = CustomerInfo.getScrollTerms();
                subjectId = CustomerInfo.getSubjectId();
                customerId = CustomerInfo.getCustomerId();*/
                tv_title.setText("智能组卷");


               // 06-22 18:09:37.576: D/22222222222222(19937): type=3  terms=0__0__0__50  subjectId=12  customerId=470472

                type=3;
                terms="0__0__0__50";
                subjectId="12";
                customerId="470472";
                CustomerInfo.setShowGradeType("TYPE_SCROLL");
                new QuestionsAsyncTask(type.toString(), null, null, terms, null,
                        subjectId, customerId, null, viewPager).execute();
                break;

            case GlobalProperty.TYPE_PAY:// 考前押题
                type = CustomerInfo.getQuestionsType();
                paperId = CustomerInfo.getPaperId();
                subjectId = CustomerInfo.getSubjectId();
                customerId = CustomerInfo.getCustomerId();
                tv_title.setText("考前押题");
                CustomerInfo.setShowGradeType("TYPE_PAY");
                new QuestionsAsyncTask(type.toString(), null, paperId, null, null,
                        subjectId, customerId, null, viewPager).execute();
                break;

            case GlobalProperty.TYPE_CONTINUE:// 继续练习
                subjectId = CustomerInfo.getSubjectId();
                customerId = CustomerInfo.getCustomerId();
                paperId = "-1";

                break;

            case GlobalProperty.TYPE_ERROR:// 我的错题
                type = CustomerInfo.getQuestionsType();
                chapterId = CustomerInfo.getChapterId();
                subjectId = CustomerInfo.getSubjectId();
                customerId = CustomerInfo.getCustomerId();
                sectionId = CustomerInfo.getLeafId();
                new QuestionsAsyncTask(type.toString(), chapterId, null, null,
                        null, subjectId, customerId, sectionId, viewPager)
                        .execute();
                break;

            case GlobalProperty.TYPE_EXERCISE:// 我的练习
                subjectId = CustomerInfo.getSubjectId();
                customerId = CustomerInfo.getCustomerId();
                paperId = CustomerInfo.getPaperId();
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
            String result = RequestUrl.sendData(GlobalProperty.makePaper, data, QuestionsActivity.this);
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

                    StringUtils.Log(TAG, "infos=" + infos.toString());
                    questionsAnswerMap = new HashMap<String, String>();
                    currentExamQuestionid = infos.get(0).getExamQuestionid();
                    CustomerInfo.setExamQuestionId(currentExamQuestionid);

                    for (QuestionsDataInfo info : infos) {
                        questionsAnswerMap.put(info.getId(), null);
                    }
                    int sum = infos.size();// 一共多少道题

                    mark = new HashMap<String, String>();
                    for (int i = 0; i < sum; i++) {// /////////////
                        fragments.add(new QuestionsViewPagerFragment(
                                getApplicationContext(), infos.get(i), (i + 1),
                                sum));
                        // 添加当前题目是否标记到map中
                        mark.put(infos.get(i).getExamQuestionid(), infos.get(i)
                                .getMarkId());

                    }
                    // 初始化第一个标记
                    currentExamQuestionid = fragments.get(0).getInfo()
                            .getExamQuestionid();
                    currentMark = mark.get(currentExamQuestionid);

                    QuestionsMainViewPagerAdapter pagerAdapter = new QuestionsMainViewPagerAdapter(
                            getSupportFragmentManager());
                    viewPager.setAdapter(pagerAdapter);
                    // viewPager.setOffscreenPageLimit(2);
                    //   initGridView(infos);
                }
            }
        }
    }
    class QuestionsMainViewPagerAdapter extends FragmentStatePagerAdapter {

        public QuestionsMainViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragments.get(arg0);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

    public static void NextPage() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                QuestionsViewPagerFragment.setNextPage(true);
            }
        }, 300);

    }
}
