package com.youjing.yjeducation.ui.dispaly.activity;

import android.nfc.Tag;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.mobileim.lib.model.message.IFileMessage;
import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.model.YJTeacherModel;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.wiget.CustomImage;

import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVVirtualActivity;
import org.vwork.mobile.ui.adapter.VAdapter;
import org.vwork.mobile.ui.delegate.IVAdapterDelegate;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.notification.IVNotificationListener;

import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:55
 */

@VLayoutTag(R.layout.activity_rich_ranking_top_ok)
public  class AYJRichRankingTopActivity extends AVVirtualActivity implements IVAdapterDelegate {
    @VViewTag(R.id.lv_live_rich_ranking)
    private ListView lv_live_rich_ranking;

    private VAdapter adapter = null;
    private List<YJUser> yjRichUserList;
    private String TAG = "AYJRichRankingTopActivity";
    @Override
    protected void onLoadedView() {
        lv_live_rich_ranking.setScrollbarFadingEnabled(true);
        StringUtils.Log(TAG,"AYJRichRankingTopActivity");
        bindNotifyListener();
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJCourseDownLoadItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjRichUserList != null){
            return yjRichUserList.size();
        }else {
            return 0;
        }
    }

    @VLayoutTag(R.layout.live_ranking_top)
    class YJCourseDownLoadItem extends AVAdapterItem {
        @VViewTag(R.id.txt_ranking_num)
        private TextView txt_ranking_num;
        @VViewTag(R.id.img_whale_money)
        private ImageView img_whale_money;
        @VViewTag(R.id.txt_whale_money_num)
        private TextView txt_whale_money_num;
        @VViewTag(R.id.txt_resume)
        private TextView txt_resume;
        @VViewTag(R.id.img_arrow)
        private ImageView img_arrow;
        @VViewTag(R.id.txt_user_name)
        private TextView txt_user_name;
        @VViewTag(R.id.img_user_photo)
        private CustomImage img_user_photo;
        @Override
        public void update(int position) {
            txt_ranking_num.setText(position + 1 + "");
            if (yjRichUserList != null){
                YJUser yjUser = yjRichUserList.get(position);
                if (!TextUtils.isEmpty(yjUser.getNickName())){
                    txt_user_name.setText(yjUser.getNickName());
                }
                if (!TextUtils.isEmpty(yjUser.getPic())){
                    BitmapUtils.create(getContext()).display(img_user_photo, yjUser.getPic());
                }
            }

        }
    }
    private void bindNotifyListener() {
        addListener(YJNotifyTag.RICH_RANKING_TOP, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                yjRichUserList =(List<YJUser>) value;
                if(adapter == null){
                    adapter = new VAdapter(AYJRichRankingTopActivity.this, lv_live_rich_ranking);
                    lv_live_rich_ranking.setAdapter(adapter);
                    StringUtils.Log(TAG, "yjRichUserList=" + yjRichUserList);
                }else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
