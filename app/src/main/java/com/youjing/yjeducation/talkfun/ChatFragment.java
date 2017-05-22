package com.youjing.yjeducation.talkfun;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youjing.yjeducation.R;
import com.youjing.yjeducation.model.ExpressionData;
import com.youjing.yjeducation.util.DimensionUtils;
import com.youjing.yjeducation.util.StringUtils;
import com.youjing.yjeducation.wiget.SendGiftPopWindow;
import com.talkfun.sdk.HtSdk;
import com.talkfun.sdk.config.HtConfig;
import com.talkfun.sdk.consts.MtConsts;
import com.talkfun.sdk.event.Callback;
import com.talkfun.sdk.event.dispatchEvent.HtDispatchFlowerListener;
import com.talkfun.sdk.module.ChatEntity;
import com.talkfun.sdk.module.HtBaseMessageEntity;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 良好的代码风格能给人带来一个美好的下午
 * Created by tony on 2015/9/15.
 */
public class ChatFragment extends Fragment implements OnExpressionSelectedListener, DispatchChatMessage
        , TextWatcher, HtDispatchFlowerListener {
    @Bind(R.id.chat_lv)
    ListView chatLv;
    @Bind(R.id.input_edt)
    EditText inputEdt;
    @Bind(R.id.send_btn)
    TextView sendBtn;
    @Bind(R.id.expression)
    ImageView expressionBtn;
    @Bind(R.id.expressionLayout)
    LinearLayout expressionLayout;
    @Bind(R.id.send)
    RelativeLayout sendFlower;
    @Bind(R.id.flower_num)
    TextView flowerNumTv;
    @Bind(R.id.flower_btn)
    ImageView flowerBtn;
    boolean isShow = false;
//    String tag;

    private String sendContent;
    private ChatAdapter chatAdapter;
    private ArrayList<HtBaseMessageEntity> chatMessageEntityList = new ArrayList<>();
    private String[] expressionChars = {"[img_gift_pen]","[aha]", "[hard]", "[good]", "[love]", "[flower]", "[cool]", "[why]", "[pitiful]", "[amaz]", "[bye]"};
    private int[] expressionResIds = {R.mipmap.img_gift_pen,R.mipmap.aha, R.mipmap.hard, R.mipmap.good, R.mipmap.love, R.mipmap.flower,
            R.mipmap.cool, R.mipmap.why, R.mipmap.pitiful, R.mipmap.amaz, R.mipmap.bye};

    public ChatFragment() {

    }


 /*   public ChatFragment(ArrayList<HtBaseMessageEntity> list) {
        chatMessageEntityList = list;
    }
*/
    public static ChatFragment create(ArrayList<HtBaseMessageEntity> list) {
        ChatFragment cf = new ChatFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", list);
//        bundle.putString("chat", tag);
        cf.setArguments(bundle);
        return cf;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isShow = isVisibleToUser;
        if (isVisibleToUser && chatAdapter != null)
            chatAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ExpressionUtil.edtImgWidth = DimensionUtils.dip2px(activity, 24);
        ExpressionUtil.edtImgHeight = DimensionUtils.dip2px(activity, 24);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().getSerializable("list") != null) {
            chatMessageEntityList = (ArrayList<HtBaseMessageEntity>) getArguments().getSerializable("list");
        }
    }

     @Nullable
      @Override
       public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
           super.onCreateView(inflater, container, savedInstanceState);
           View layout = inflater.inflate(R.layout.livein_chat_fragment_layout, container, false);
           ButterKnife.bind(this, layout);
           chatAdapter = new ChatAdapter(getActivity(), chatMessageEntityList);
           chatLv.setAdapter(chatAdapter);
           inputEdt.addTextChangedListener(this);
           expressionBtn.setVisibility(View.VISIBLE);
           initExpression();

           return layout;
       }



    public void setChatMessageEntityList(ArrayList<HtBaseMessageEntity> chatMessageEntityList) {
//        this.chatMessageEntityList = chatMessageEntityList;
        chatAdapter.setChatLists(chatMessageEntityList);

    }

    @OnClick({R.id.send_btn, R.id.expression, R.id.input_edt, R.id.send})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_btn:
                sendContent = inputEdt.getText().toString().trim();
                sendMsg(sendContent, msgCallback);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        while (true) {
//                            mHandler.sendMessage(new Message());
//                            try {
//                                Thread.sleep(300);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                }).start();
//
                break;
            case R.id.expression:
                showExpressionPage(!isExpressionShow);
                break;
            case R.id.input_edt:
                if (isExpressionShow)
                    showExpressionPage(false);
                break;
            case R.id.send:
                sendFlower();
                break;
            default:
                break;
        }
    }

    private int i = 0;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsg("i : " + i);
            chatEntity.setRole("user");
            chatEntity.setNickname("tony");
            appendList(chatEntity);
            i++;
        }
    };

    //发送消息
    private void sendMsg(String sendContent, Callback callback) {
        if (isExpressionShow)
            showExpressionPage(false);
        if (!TextUtils.isEmpty(sendContent)) {
            HtSdk.getInstance().emit(MtConsts.CHAT_SEND, sendContent, callback);
            inputEdt.setText("");
            InputMethodManager imm =
                    (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(inputEdt.getWindowToken(), 0);
        }
    }
    private void sendFlower() {
        if (HtConfig.isLiveIn()) {
            HtSdk.getInstance().sendFlower();
            flowerNumTv.setText("0");
            flowerNum = 0;

            //自定义送礼物页面
        /*   SendGiftPopWindow  morePopWindow = new SendGiftPopWindow(getActivity());
            morePopWindow.showPopupWindow(chatLv);*/

        } else {
            StringUtils.tip(getActivity(), "还没上课");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("list", chatMessageEntityList);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        try {
            if (savedInstanceState == null)
                return;
            ArrayList<HtBaseMessageEntity> list = (ArrayList<HtBaseMessageEntity>) savedInstanceState.getSerializable("list");
            if (list != null && list.size() > 0) {
                if (chatAdapter != null) {
                    chatAdapter.setChatLists(list);
                    chatMessageEntityList = list;
                }
            }
        } catch (NullPointerException e) {

        }
    }

    private boolean isExpressionShow = false;

    private void showExpressionPage(boolean visible) {
        isExpressionShow = visible;
        if (visible)
            expressionLayout.setVisibility(View.VISIBLE);
        else
            expressionLayout.setVisibility(View.GONE);
    }

    private void initExpression() {
        ExpressionData dataAction = new ExpressionData();
        try {
            dataAction.parseData(expressionChars, expressionResIds);
            ExpressionView emotionView = new ExpressionView(this.getActivity(), dataAction);
            emotionView.setOnEmotionSelectedListener(this);
            expressionLayout.addView(emotionView);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Callback msgCallback = new Callback() {
        @Override
        public void success(Object result) {
            if (result != null) {
                JSONObject obj = (JSONObject) result;
             //   appendList(ChatEntity.onExplainChatMessage(obj));
            }
        }

        @Override
        public void failed(String failed) {
            if (!TextUtils.isEmpty(failed)) {
                StringUtils.tip(getActivity(), failed);
            }
        }
    };

    /**
     * 该回调用于直播
     *
     * @param chatMessageEntity 消息实例
     */
    @Override
    public void getChatMessage(HtBaseMessageEntity chatMessageEntity) {
      /*  if (chatMessageEntity != null) {
            chatAdapter.appendList(chatMessageEntity);
            if (chatLv != null)
                chatLv.setSelection(chatLv.getBottom());
        }*/
    }

    @Override
    public void OnExpressionSelected(ExpressionEntity entity) {
        if (entity == null) return;
        SpannableString spannableString = ExpressionUtil.getExpressionString(this.getActivity(), entity.resId, entity.character);
        inputEdt.append(spannableString);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (TextUtils.isEmpty(inputEdt.getText().toString())) {
            flowerNumTv.setText(flowerNum + "");
            sendBtn.setVisibility(View.INVISIBLE);
            flowerBtn.setVisibility(View.VISIBLE);
            flowerNumTv.setVisibility(View.INVISIBLE);
        } else {
            sendBtn.setVisibility(View.VISIBLE);
            flowerBtn.setVisibility(View.INVISIBLE);
            flowerNumTv.setVisibility(View.INVISIBLE);
            sendBtn.setText("发送");
        }
    }

    private int flowerNum = 0;

    @Override
    public void getFlowerNum(int num) {
        flowerNum = num;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                flowerNumTv.setText(flowerNum + "");
            }
        });
    }

    @Override
    public void getFlowerLeftTime(int time) {
        String tips = String.format("剩余%s秒可以获取第一朵花", time);
        StringUtils.tip(getActivity(), tips);

    }

    @Override
    public void getTotalFlower(int total) {
        flowerNum = total;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                flowerNumTv.setText(flowerNum + "");
            }
        });
    }

    //送花成功的回调
    @Override
    public void sendSuccess(String name, String role, int amount, String time) {
        if (amount <= 0) {
            return;
        } else {
            String flower = "";
            for (int i = 0; i < amount; i++) {
                flower += "[flower]";
            }
            ChatEntity chatEntity = new ChatEntity();
            chatEntity.setMsg(flower);
            chatEntity.setRole(role);
            chatEntity.setNickname(name);
            appendList(chatEntity);
        }
    }

    private void appendList(final ChatEntity chatEntity) {
        chatAdapter.appendList(chatEntity);
        if (chatLv != null)
            chatLv.setSelection(chatLv.getBottom());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
