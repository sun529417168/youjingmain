package com.youjing.yjeducation.wiget;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.common.config.YJConfig;

import org.vwork.mobile.ui.utils.VLayoutTag;

/**
 * user  秦伟宁
 * Date 2016/3/22
 * Time 14:17
 */
@VLayoutTag(R.layout.custom_toast)
public class CustomToast extends Toast {

    public CustomToast(Context context) {
        super(context);
    }
    public static Toast makeText(Context context, CharSequence text, int duration) {
        Toast result = new Toast(context);
        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.custom_toast, null);


        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(YJConfig.mScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);

       /* WindowManager mWdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams param = new WindowManager.LayoutParams(YJConfig.mScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.windowAnimations = R.style.anim_view;*/
        //实例化ImageView和TextView对象
        TextView textView = (TextView) layout.findViewById(R.id.txt_toast);
        textView.setText(text);
        textView.setLayoutParams(params);

        result.setView(layout);

        //设置toast的位置 Toast提示的位置xOffset:大于0向右移，小于0向左移）
        float hOffset = context.getResources().getDimension(R.dimen.title_height);
        result.setGravity(Gravity.TOP, 0, (int) hOffset);
        result.setDuration(duration);
        result.show();

        return result;
    }


    public static Toast makeTexts(Context context, CharSequence text, int duration) {
        Toast result = new Toast(context);
        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.dialog_info_success, null);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        //layout.setLayoutParams(params);
       /* WindowManager mWdm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams param = new WindowManager.LayoutParams(YJConfig.mScreenWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        param.windowAnimations = R.style.anim_view;*/
        //实例化ImageView和TextView对象
        TextView textView = (TextView) layout.findViewById(R.id.txt_down);
        textView.setText(text);
      //  textView.setLayoutParams(params);

        result.setView(layout);

        //设置toast的位置 Toast提示的位置xOffset:大于0向右移，小于0向左移）
        result.setGravity(Gravity.CENTER, 0, 0);
        result.setDuration(duration);
        result.show();

        return result;
    }

    public static Toast toastCards(Context context,String info){
        Toast toast = new Toast(context);
        //获取LayoutInflater对象
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //由layout文件创建一个View对象
        View layout = inflater.inflate(R.layout.toast_cards, null);
        TextView text = (TextView) layout.findViewById(R.id.toast_cards_coinNum);
        text.setText(info);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
        return toast;
    }


}
