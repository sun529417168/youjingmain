package com.youjing.yjeducation.talkfun.customtalkfun;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.talkfun.sdk.module.ChatEntity;
import com.talkfun.sdk.module.HtBaseMessageEntity;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.model.YJChatEntityModel;
import com.youjing.yjeducation.model.YJGiftModel;
import com.youjing.yjeducation.talkfun.ExpressionUtil;
import com.youjing.yjeducation.util.DimensionUtils;
import com.youjing.yjeducation.util.YJLiveRoomUtil;
import com.youjing.yjeducation.wiget.CustomImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * user  秦伟宁
 * Date 2016/6/14
 * Time 11:47
 */
public class YJCustomChatAdapter  extends BaseAdapter {
    private List<YJChatEntityModel> chatLists = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Context mContext;
    private int MXA_LENGTH = 200;

    private YJGiftModel yjGiftModel;
    private String TAG = "YJCustomChatAdapter";

    public YJCustomChatAdapter(Context context, List<YJChatEntityModel> list) {
        mContext = context;
        chatLists = list;
        layoutInflater = LayoutInflater.from(context);

    }
    public void appendChatList(YJChatEntityModel yjChatEntityModel) {

        chatLists.add(yjChatEntityModel);
        if (chatLists.size() >= MXA_LENGTH) {
            chatLists.remove(0);
        }
        notifyDataSetChanged();

    }

    public void removeList() {
        StringUtils.Log("removeList", "removeList");
        if (chatLists.size()>=2){
            chatLists.remove(chatLists.size() - 2);
        }else {
            chatLists.remove(chatLists.size() - 1);
        }

        notifyDataSetChanged();
    }
    public void setChatLists(List<YJChatEntityModel> list) {
        chatLists = list;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return chatLists.size();
    }

    @Override
    public Object getItem(int position) {
        return chatLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.live_gift_item_layout, null);
            viewHolder.img_user_photo = (CustomImage)convertView.findViewById(R.id.img_user_photo);
            viewHolder.img_gift = (ImageView)convertView.findViewById(R.id.img_gift);
            viewHolder.txt_user_name = (TextView)convertView.findViewById(R.id.txt_user_name);
            viewHolder.txt_send_gift = (TextView)convertView.findViewById(R.id.txt_send_gift);
            viewHolder.txt_gift_num = (TextView)convertView.findViewById(R.id.txt_gift_num);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        YJChatEntityModel chatMessageEntity = (YJChatEntityModel) chatLists.get(position);
        String name = chatMessageEntity.getNickname();
        if (name != null & !TextUtils.isEmpty(name)) {
            viewHolder.txt_user_name.setText(name);
        }
        StringUtils.Log(TAG, "chatMessageEntity=" + chatMessageEntity.toString());
        String userUrl = chatMessageEntity.getAvatar();
        if (userUrl != null & !TextUtils.isEmpty(userUrl)) {
            BitmapUtils.create(mContext).display( viewHolder.img_user_photo, userUrl);
        }
        String content = chatMessageEntity.getMsg();
        if (content != null & !TextUtils.isEmpty(content) ) {
            //  viewHolder.contentTv.setText(content);
            if (content.startsWith("[gf_")) {
               // String mGiftId = content.substring(4, content.indexOf("_", 4));
                // String mGiftNum = content.substring(content.indexOf("_", 4) + 1, content.indexOf("]"));
                String mGiftId = "";
                String mGiftNum = "";
                try {
                    mGiftId = YJLiveRoomUtil.getGiftId(content);
                    StringUtils.Log(TAG, "mGiftId=" + mGiftId);

                    mGiftNum = YJLiveRoomUtil.getGiftNum(content);
                    if (!TextUtils.isEmpty(mGiftNum)){
                        viewHolder.txt_gift_num.setText("X"+mGiftNum);
                    }else {
                        viewHolder.txt_gift_num.setText("X1");
                    }
                    StringUtils.Log(TAG, "mGiftNum=" + mGiftNum);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (YJGlobal.getYjGiftModelList() != null) {
                    List<YJGiftModel> yjGiftModelList = YJGlobal.getYjGiftModelList();
                    for (int i = 0; i < yjGiftModelList.size(); i++) {
                        if (mGiftId.equals(yjGiftModelList.get(i).getVirtualGiftId())) {
                            yjGiftModel = yjGiftModelList.get(i);
                            break;
                        } else {
                            yjGiftModel = null;
                        }
                    }
                }
                if (yjGiftModel != null) {
                    BitmapUtils.create(mContext).display(viewHolder.img_gift, yjGiftModel.getGiftPic());
                    viewHolder.txt_send_gift.setText("送了"+yjGiftModel.getName());
                }

            }
        }
        return convertView;
    }

    class ViewHolder {
        CustomImage img_user_photo;
        ImageView img_gift;
        TextView txt_user_name;
        TextView txt_send_gift;
        TextView txt_gift_num;
    }

}
