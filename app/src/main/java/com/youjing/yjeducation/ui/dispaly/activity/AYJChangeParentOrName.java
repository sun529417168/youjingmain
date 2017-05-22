package com.youjing.yjeducation.ui.dispaly.activity;

import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.util.CheckPhone;
import com.youjing.yjeducation.wiget.CustomToast;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParamKey;

/**
 * Created by w7 on 2016/4/30.
 * 修改家长手机号和昵称，QQ号码
 */
@VLayoutTag(R.layout.activity_change_name_or_parent)
public abstract class AYJChangeParentOrName extends YJBaseActivity implements IVClickDelegate {

    @VViewTag(R.id.txt_back)
    private TextView mTxt_back;
    @VViewTag(R.id.txt_title)
    private TextView mTxt_title;
    @VViewTag(R.id.txt_submit)
    private TextView mTxt_submit;
    @VViewTag(R.id.txt_something)
    private EditText mTxt_something;
    @VViewTag(R.id.btn_delete)
    private ImageView mBtn_delete;

    String title = "";
    String content = "";
    public static final VParamKey<String> YJ_CHANGE = new VParamKey<String>(null);

    @Override
    protected void onLoadedView() {

        title =TextUtils.isEmpty(getTransmitData(YJ_CHANGE).split(",")[1])?"": getTransmitData(YJ_CHANGE).split(",")[1];
        content =TextUtils.isEmpty(getTransmitData(YJ_CHANGE).split(",")[0])?"": getTransmitData(YJ_CHANGE).split(",")[0];

        mTxt_title.setText(title);
        if (!TextUtils.isEmpty(title) && title.equals("家长手机号")) {
            mTxt_something.setHint("");
        } else if (!TextUtils.isEmpty(title) && title.equals("昵称")) {
            mTxt_something.setHint("昵称必须由2-8个字符组成");
        } else if (!TextUtils.isEmpty(title) && title.equals("QQ号")) {
            mTxt_something.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_NORMAL);
            mTxt_something.setHint("请输入正确的QQ号码");
        }

        mTxt_something.setText(content);
        mTxt_something.addTextChangedListener(textWatcher);

    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            if (s.length() == 0) {
                mTxt_submit.setTextColor(getResources().getColor(R.color.parentOrName_no));
                mTxt_submit.setClickable(false);
            } else {
                mTxt_submit.setTextColor(getResources().getColor(R.color.parentOrName_yes));
                mTxt_submit.setClickable(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    @Override
    public void onClick(View view) {
        if (view == mBtn_delete) {// 清空文本
            mTxt_something.setText("");
        } else if (view == mTxt_back) {//取消
            finish();
        } else if (view == mTxt_submit) {// 提交
            if (title.equals("昵称")) {
                String somthing = mTxt_something.getText().toString().trim();
                if (somthing.length() >= 9) {
                    CustomToast.makeText(getContext(), "用户名过长，最多设置8个字", Toast.LENGTH_SHORT).show();
                    return;
                } else if (somthing.length() == 1) {
                    CustomToast.makeText(getContext(), "昵称必须由2-8个字符组成", Toast.LENGTH_SHORT).show();
                } else {
                    changeSomthing(somthing, "NICKNAME");
                }
            } else if (title.equals("家长手机号")) {
                String somthing = mTxt_something.getText().toString().trim();
                if (CheckPhone.isMobileNum(somthing)) {
                    changeSomthing(somthing, "PARENT");
                } else {
                    showToast("请输入正确的手机号");
                }
            } else if ("QQ号".equals(title)) {
                String somthing = mTxt_something.getText().toString().trim();
                if (somthing.length() > 5&&somthing.length()<12) {
                    changeSomthing(somthing, "QQ");
                } else {
                    showToast("请输入正确的QQ号");
                }
            }
        }
    }

    protected abstract void changeSomthing(String somthing, String kinds);// 内容，类型(昵称，还是电话)
}
