package com.youjing.yjeducation.util;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.youjing.yjeducation.R;

/**
 * @author stt
 *         创建于2015.5.20
 *         说明：清楚缓存的dialog
 *         无返回值
 */
public class DialogUtil {
    /**
     * 加载数据时的等待对话框
     * 孙腾腾
     * 创建时间：2016.05.20
     *
     * @param context
     * @return
     */
    public static Dialog showWaitDialog(final Context context) {
        Dialog dialog = new Dialog(context, R.style.DialogStyle);
        View view = LayoutInflater.from(context).inflate(
                R.layout.loading_dialog, null);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 1.0f; // 透明显示效果
        window.setAttributes(params);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.show();
        dialog.setContentView(view);
        dialog.setCancelable(false);

        return dialog;

    }

    /**
     * 获得金币，经验的dialog
     * 孙腾腾
     * 创建时间：2016.05.30
     *
     * @param context
     * @return
     */
    public static Dialog showTaskDialog(final Context context, String rewardCoin, String rewardExp) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_task_sucess, null);
        TextView textView = (TextView) view.findViewById(R.id.dialog_task_info);
        textView.setText("获得" + rewardCoin + "鲸币" + "，获得" + rewardExp + "经验");
        Button btn = (Button) view.findViewById(R.id.dialog_task_info_ok);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setContentView(view);
        dialog.setCancelable(true);
        return dialog;
    }

    /**
     * 直播列表，课程详情没有回放数据的时候弹出的dialog
     * 孙腾腾
     * 创建时间：2016.06.06
     *
     * @param context
     * @return
     */
    public static Dialog showIsReplayDialog(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_isreplay, null);
        Button btn = (Button) view.findViewById(R.id.dialot_isreplay_close);
//        TextView textView = (TextView) view.findViewById(R.id.dialog_task_info);
//        SpannableStringBuilder builder = new SpannableStringBuilder(textView.getText().toString());
//        ForegroundColorSpan yellowSpan = new ForegroundColorSpan(R.color.yellow_text);
//        builder.setSpan(yellowSpan, 5, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        textView.setText(builder);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.setContentView(view);
        dialog.setCancelable(true);
        return dialog;
        //最好
    }

    /**
     * 升级框的dialog
     * 孙腾腾
     * 创建时间：2016.06.24
     *
     * @param context
     * @return
     */
    public static Dialog showUpgradeLevelDialog(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.DialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_upgrade_level, null);
        Button btn = (Button) view.findViewById(R.id.dialog_upgrade_level_share);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setAttributes(params);
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setContentView(view);
        dialog.setCancelable(true);
        dialog.show();
        return dialog;
        //最好
    }
}
