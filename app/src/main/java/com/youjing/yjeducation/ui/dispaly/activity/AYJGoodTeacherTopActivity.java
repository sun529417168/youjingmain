package com.youjing.yjeducation.ui.dispaly.activity;

import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
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
public  class AYJGoodTeacherTopActivity extends AVVirtualActivity implements IVAdapterDelegate {
    @VViewTag(R.id.lv_live_rich_ranking)
    private ListView lv_live_rich_ranking;

    private VAdapter adapter = null;
    private List<YJTeacherModel> yjTeacherModelList;
    private String TAG = "AYJGoodTeacherTopActivity";
    @Override
    protected void onLoadedView() {
        StringUtils.Log(TAG, "AYJGoodTeacherTopActivity");
        lv_live_rich_ranking.setScrollbarFadingEnabled(true);

        bindNotifyListener();
    }

    @Override
    public AVAdapterItem createAdapterItem(View view, int i, int i1) {
        return new YJCourseDownLoadItem();
    }

    @Override
    public int getAdapterItemCount(View view) {
        if (yjTeacherModelList != null){
            return yjTeacherModelList.size();
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
            txt_ranking_num.setText(position+1+"");
            if (yjTeacherModelList != null){
                YJTeacherModel yjTeacherModel = yjTeacherModelList.get(position);
                if (!TextUtils.isEmpty(yjTeacherModel.getNickName())){
                    txt_user_name.setText(yjTeacherModel.getNickName());
                }
                if (!TextUtils.isEmpty(yjTeacherModel.getTeacherPic())){
                    BitmapUtils.create(getContext()).display(img_user_photo, yjTeacherModel.getTeacherPic());
                }
            }
        }
    }
    private void bindNotifyListener() {
        addListener(YJNotifyTag.TEACHER_RANKING_TOP, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                yjTeacherModelList =(List<YJTeacherModel>) value;
                if(adapter == null){
                    adapter = new VAdapter(AYJGoodTeacherTopActivity.this, lv_live_rich_ranking);
                    lv_live_rich_ranking.setAdapter(adapter);
                    StringUtils.Log(TAG, "yjStudyUserList=" + yjTeacherModelList);
                }else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
}
