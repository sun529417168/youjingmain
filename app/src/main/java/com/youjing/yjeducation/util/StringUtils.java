package com.youjing.yjeducation.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import com.youjing.yjeducation.util.StringUtils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.model.YJGradeModel;
import com.youjing.yjeducation.model.YJMyCourseModel;
import com.youjing.yjeducation.model.YJSubjectModel;
import com.youjing.yjeducation.ui.actualize.dialog.YJUpdateEvaluationDialog;

import org.vwork.mobile.ui.IVActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * if my son want to be programmer , i will break his legs.
 * Created by tony on 2015/10/19.
 */
public class StringUtils {

    public static void tip(Context context, int contentId) {
        Toast.makeText(context, getString(context, contentId), Toast.LENGTH_SHORT).show();
    }

    public static void tip(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    private static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    /**
     * 格式化时间
     * yyyy-MM-dd HH:mm:ss
     *
     * @param date 需要格式化的时间
     * @return 时间的字符串形式
     */
    public static String DataToString(Date date) {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        return sdformat.format(date);
    }

    /**
     * 当前的时间
     * yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的字符串
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//24小时制
        return sdformat.format(System.currentTimeMillis());
    }

    /**
     * 方法说明：我的课程里边的学习完成后显示的分钟数
     * stt
     * 创建时间：2016.05.25
     *
     * @param minute
     * @return
     */
    public static String getMinute(String minute) {
        double minuteTime = Double.parseDouble(minute);
        StringUtils.Log("初一===", minuteTime / Double.parseDouble("60") + "");
        return Math.round(minuteTime / Double.parseDouble("60")) + "";
    }

    /**
     * 除法精确到两位
     *
     * @param value1
     * @param value2
     * @return
     */
    public static String div(double value1, double value2) {

        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        StringUtils.Log("分钟数===", value1 + "");
        return b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
    }

    /**
     * 把时间戳转换成时间
     *
     * @param time
     * @return
     */
    public static String getDate(String time, int start, int end) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return subDate(times, start, end);
    }

    /**
     * 截取字符串
     *
     * @param date
     * @param start
     * @param end
     * @return
     */
    public static String subDate(String date, int start, int end) {
        return date.substring(start, end);
    }

    public static void setGradeAndSubject(Context context, YJCourseModel mYjCourseModel) {
        List mGradeList;
        int mGrade, mSubject;
        List<YJSubjectModel> mSubjectModelList = new ArrayList<YJSubjectModel>();
        List subjectList;
        mGrade = PreferenceManager.getDefaultSharedPreferences(context).getInt("gradeNum", 0);
        mSubject = PreferenceManager.getDefaultSharedPreferences(context).getInt("subjectNum", 0);
        List<YJGradeModel> list = YJGlobal.getGradeList();
        mGradeList = new ArrayList();
        if (list != null) {
            StringUtils.Log("年级课程信息===", list.toString());
            for (int i = 0; i < list.size(); i++) {
                mGradeList.add(list.get(i).getGradeName());
                if (mYjCourseModel.getGradeId().equals(list.get(i).getGradeId())) {
                    notifyCourse(context, list.get(i).getGradeName(), true);
                    SharedUtil.saveGrade(context, list.get(i).getGradeName(), i);

                    YJGlobal.setMy_grade(list.get(i).getGradeName());
                    YJGlobal.setMy_grade_id(mYjCourseModel.getGradeId());

                    mSubjectModelList = list.get(i).getSubjectVos();
                }
            }


        }
        subjectList = new ArrayList();
        if (mSubjectModelList != null) {
            for (int i = 0; i < mSubjectModelList.size(); i++) {
                subjectList.add(mSubjectModelList.get(i).getSubjectName());
                if (mYjCourseModel.getSubjectId().equals(mSubjectModelList.get(i).getSubjectId())) {
                    notifyCourse(context, mSubjectModelList.get(i).getSubjectName(), false);
                    SharedUtil.saveProject(context, mSubjectModelList.get(i).getSubjectName(), i);

                    YJGlobal.setMy_subjectId(mYjCourseModel.getSubjectId());
                    YJGlobal.setMy_subject(mSubjectModelList.get(i).getSubjectName());
                }
            }

        }
    }

    //发送一个广播 修改title年级
    public static void notifyCourse(Context context, String info, boolean mflag) {
        Intent mIntent = new Intent("发送广播");
        if (mflag) {
            mIntent.putExtra("grade", info);
        } else {
            mIntent.putExtra("subject", info);
        }

        //发送广播
        context.sendBroadcast(mIntent);
    }

    /**
     * stt
     * 2016.7.7
     * 方法说明：给app评分，当浏览的页面大于30个的时候弹出这个dialog
     *
     * @param activity
     */
    public static void showEvaluationDialog(IVActivity activity) {
        StringUtils.Log("现在是第几个", SharedUtil.getInteger(activity.getContext(), "baseIndex", 0) + "");
        if (SharedUtil.getInteger(activity.getContext(), "baseIndex", 0) == -1) {
            return;
        }
        if (SharedUtil.getInteger(activity.getContext(), "baseIndex", 0) > 30) {
            SharedUtil.setInteger(activity.getContext(), "baseIndex", -1);
            YJUpdateEvaluationDialog updateEvaluationDialog = new YJUpdateEvaluationDialog();
            activity.showDialog(updateEvaluationDialog);
        }
    }

    /**
     * stt
     * 2016.7.7
     * 方法说明：给app评分去市场
     */
    public static void toScore(IVActivity context) {
        Intent i = MarketUtils.getIntent(context.getContext());
        boolean b = MarketUtils.judge(context.getContext(), i);
        if (b == false) {
            context.startActivity(i);
        } else {
            context.showToast("您的手机没有安装应用市场");
        }
    }


    /**
     * stt
     * 2016.7.1
     * 方法说明：推送的红点是否显示
     */
    public static void showTitleRed(boolean isFlag, ImageView imageView) {
        if (isFlag) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * stt
     * 2016.7.15
     * 方法说明：判断Url是否是有用的
     */
    public static boolean checkURL(String url) {
        boolean value = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            int code = conn.getResponseCode();
            if (code != 200) {
                value = false;
            } else {
                value = true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * stt
     * 2016.7.15
     * 方法说明：判断Url是否是http请求的
     */
    private static final String acceptableSchemes[] = {"http:", "https:", "file:"};

    public static boolean isUrl(String url) {
        if (url == null) {
            return false;
        }

        for (int i = 0; i < acceptableSchemes.length; i++) {
            if (url.startsWith(acceptableSchemes[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * stt
     * 2016.7.18
     * 方法说明：判断时间是今天还是明天
     */
    public static String getDateDetail(String str) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(str);
            Date now = new Date();
            now = sdf.parse(sdf.format(now));
            long sl = date.getTime();
            long el = now.getTime();
            long ei = sl - el;
            int value = (int) (ei / (1000 * 60 * 60 * 24));
            if (value == 0) {
                return "今天";
            } else if (value == 1) {
                return "明天";
            } else {
                return "3";
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "3";
    }


    /**
     * stt
     * 2016.8.10
     * 方法说明：蒙层，也就是引导图
     */
    static int index = 0;

    public static void setGuidImage(Activity act, int viewId,
                                    final int imageId[], String preferenceName) {
        SharedPreferences preferences = act.getSharedPreferences(
                preferenceName, act.MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();
        final String key = act.getClass().getName() + "_firstLogin";
        if (!preferences.contains(key)) {
            editor.putBoolean(key, true);
            editor.commit();
        }

        // 判断是否首次登陆
        if (!preferences.getBoolean(key, true))
            return;

        View view = act.getWindow().getDecorView().findViewById(viewId);
        ViewParent viewParent = view.getParent();
        if (viewParent instanceof FrameLayout) {

            final FrameLayout frameLayout = (FrameLayout) viewParent;
            final ImageView guideImage = new ImageView(act.getApplication());
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            guideImage.setLayoutParams(params);
            guideImage.setScaleType(ImageView.ScaleType.FIT_XY);
            guideImage.setImageResource(imageId[0]);
            guideImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    index += 1;
                    if (index < imageId.length) {
                        frameLayout.removeView(guideImage);
                        guideImage.setImageResource(imageId[index]);
                        frameLayout.addView(guideImage);// 添加引导图片
                    } else {
                        index = 0;
                        frameLayout.removeView(guideImage);
                        editor.putBoolean(key, false);
                        editor.commit();
                    }
                }
            });
            frameLayout.addView(guideImage);// 添加引导图片
        }
    }

    /**
     * 获取本地的视频地址path
     *
     * @param context
     * @param VIDEO_NAME
     * @return
     */
    public static File copyVideoFile(Context context, String VIDEO_NAME) {
        File videoFile;
        try {
            FileOutputStream fos = context.openFileOutput(VIDEO_NAME, context.MODE_PRIVATE);
            InputStream in = context.getResources().openRawResource(R.raw.welcome_video);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        videoFile = context.getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");
        return videoFile;
    }

    public static String getMoneyStr(String payMoney) {
        DecimalFormat fnum = new DecimalFormat("##0.00");
        return fnum.format(Float.parseFloat(payMoney));
    }


    public static String getChannelName(Activity ctx) {
        if (ctx == null) {
            return "";
        }
        String channelName = "";
        try {
            PackageManager packageManager = ctx.getPackageManager();
            if (packageManager != null) {
                //注意此处为ApplicationInfo 而不是 ActivityInfo,因为友盟设置的meta-data是在application标签中，而不是某activity标签中，所以用ApplicationInfo
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(ctx.getPackageName(), PackageManager.GET_META_DATA);
                if (applicationInfo != null) {
                    if (applicationInfo.metaData != null) {
                        channelName = applicationInfo.metaData.getString("5721af0ae0f55a79af000b75");
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return channelName;
    }


    /**
     * stt
     * 2016.8.29
     * 方法说明：获取本地缓存
     *
     * @param objects
     * @return
     */
    public static Object arrayList(Object[] objects) {
        List<Object> list = new ArrayList<>();
        if (objects == null || objects.length == 0) {
            return list;
        }
        for (int i = 0; i < objects.length; i++) {
            list.add(objects[i]);
        }
        return list;
    }

    public static void  Log(String TAG,String info){
        if (YJConfig.isOutputLog){
            Log.d(TAG, info);
        }else {
        }
    }
}
