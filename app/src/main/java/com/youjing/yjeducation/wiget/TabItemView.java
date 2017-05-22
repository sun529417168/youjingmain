package com.youjing.yjeducation.wiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;

/**
 * user  秦伟宁
 * Date 2016/3/21
 * Time 18:38
 */
public class TabItemView extends LinearLayout implements View.OnClickListener {

    private Context mContext;

    private ImageView contentLogo;//tab图片显示
    private TextView contentText;//tab文字提示

    private int logoBackResourceId;//图片资源id
    private String textString;//文字字符串
    private int textColor;//文字显示颜色
    private float textSize;//文字显示大小
    private int contentLogoWidth,contentLogoHeigth;//显示图片大小
    private static final float defaultTextSize = 16;//文字默认大熊啊
    private int defaultColor,selectedColor;//默认颜色
    private TabClickListner mClickListner;//点击监听回调事件


    public TabItemView(Context context) {
        this(context, null);
    }

    public TabItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        init(attrs);
        addView();
    }

    private void init(AttributeSet attrs){
        this.setOnClickListener(this);
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.TabItemView);//获取属性集合
        logoBackResourceId = ta.getResourceId(R.styleable.TabItemView_contentLogoBack, -1);//获取图片资源id
        textColor = ta.getColor(R.styleable.TabItemView_contentTextColor, getResources().getColor(android.R.color.black));//获取文字颜色</span>
        textSize = ta.getDimensionPixelSize(R.styleable.TabItemView_contentTextSize, Dp2Px(mContext, defaultTextSize));//获取显示的文字大小</span>
        textString = ta.getString(R.styleable.TabItemView_contentTextString);//获取显示的文字
        contentLogoWidth = ta.getDimensionPixelSize(R.styleable.TabItemView_contentLogoWidth, LayoutParams.WRAP_CONTENT);//获取显示图片的尺寸大小</span>
        contentLogoHeigth = ta.getDimensionPixelSize(R.styleable.TabItemView_contentLogoHeigth,LayoutParams.WRAP_CONTENT);//获取显示图片的尺寸大小</span>

        ta.recycle();
        defaultColor = mContext.getResources().getColor(R.color.black);
        selectedColor = mContext.getResources().getColor(R.color.blue);
    }
    //添加显示图片和文字的控件
    private void addView(){
        contentLogo = new ImageView(mContext);
        contentLogo.setFocusable(false);
        contentLogo.setClickable(false);
        LayoutParams logoParams = new LayoutParams(contentLogoWidth,contentLogoHeigth);
        contentLogo.setLayoutParams(logoParams);
        if(logoBackResourceId != -1){
            contentLogo.setBackgroundResource(logoBackResourceId);
        }else{
            throw new InflateException("未设置填充图片资源");
        }

        this.addView(contentLogo);

        if(!TextUtils.isEmpty(textString)){
            contentText = new TextView(mContext);
            contentText.setFocusable(false);
            contentText.setClickable(false);
            LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
            textParams.topMargin = Dp2Px(mContext,3);
            contentText.setLayoutParams(textParams);
            contentText.setTextColor(textColor);
            contentText.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
            contentText.setText(textString);
            this.addView(contentText);
        }
    }

    @Override
    public void onClick(View v) {
        setTabSelected(true);
        if(mClickListner != null){
            mClickListner.onTabClick(this);//回调点击事件
        }
    }

    /**
     *设置点击监听事件
     */
    public void setTabClickListener(TabClickListner listner){
        this.mClickListner = listner;
    }

    /**
     *设置填充图片资源
     */
    public void setContentLogoBack(int resourceId){
        contentLogo.setBackgroundResource(resourceId);
    }

    /**
     *设置填充文字
     */
    public void setContentTextString(String text){
        if(contentText != null){
            contentText.setText(text);
        }
    }

    /**
     *设置选中状态
     */
    public void setTabSelected(boolean enable){
        if(contentLogo != null){
            contentLogo.setSelected(enable);
        }
        if(contentText != null){
            if(enable){
                contentText.setTextColor(selectedColor);
            }else{
                contentText.setTextColor(defaultColor);
            }
        }
    }

    public interface TabClickListner{
        void onTabClick(View view);
    }

    public  int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}