package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJMedalModel;
import com.youjing.yjeducation.ui.actualize.activity.YJLoginActivity;
import com.youjing.yjeducation.ui.actualize.dialog.YJMedalDetailsDialog;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.base.VParams;

import java.util.List;

/**
 * @author stt
 *         类说明：个人中心勋章
 *         创建时间：2016.5.12
 */
@VLayoutTag(R.layout.activity_medal_gridview)
public class AYJMedalActivity extends YJBaseActivity {
    private String TAG = "AYJMedalActivity";
    @VViewTag(R.id.medal_gridview)
    private GridView gridview;
    List<YJMedalModel> mYJMedalModelList;
    private YJMedalAdapter yjMedalAdapter;

    @Override
    protected void onLoadedView() {
        super.onLoadedView();
        YJTitleLayoutController.initTitleBuleBg(this, "勋章", true);
        getMedalList();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                YJMedalDetailsDialog medalDetailsDialog = new YJMedalDetailsDialog();
                VParams data = createTransmitData(YJMedalDetailsDialog.MEDAL_MODEL, mYJMedalModelList.get(i));
                showDialog(medalDetailsDialog,data);
                medalDetailsDialog.setCancelable(true);// 设置点击屏幕Dialog不消失
            }
        });
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));//取消GridView中Item选中时默认的背景色
    }

    public void getMedalList() {
            YJStudentReqManager.getServerData(YJReqURLProtocol.GET_MEDAL_LIST, null, true, new TextHttpResponseHandler() {
                      @Override
                      public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                          StringUtils.Log(TAG, "失败=" + s);
                          showToast(getString(R.string.no_net_work));
                      }
                      @Override
                      public void onSuccess(int i, Header[] headers, String s) {
                          try {
                              StringUtils.Log(TAG, "成功s=" + s);
                              JSONObject json = null;
                              json = new JSONObject(s);
                              switch (json.getInt("code")) {
                                  case 200:
                                      JSONObject jsonData = new JSONObject(json.getString("data"));
                                      mYJMedalModelList = JSON.parseArray(jsonData.getString("medalVoList"), YJMedalModel.class);
                                      if (yjMedalAdapter == null){
                                          yjMedalAdapter = new YJMedalAdapter(AYJMedalActivity.this);
                                          gridview.setAdapter(yjMedalAdapter);
                                      }else {
                                          yjMedalAdapter.notifyDataSetChanged();
                                      }
//                            notifyListener(YJNotifyTag.OPEN_CLASS_LIST, yjOpenClassModelList);
//                            mMsgListView.stopRefresh();
                                      break;
                                  case 300:
                                      break;
                                  case 400:
                                      break;
                                  case 401:
                                      break;
                                  case 402:
                                      break;
                                  case 500:
                                      break;
                                  case 600:
                                      startActivity(createIntent(YJLoginActivity.class, createTransmitData(YJLoginActivity.LOGIN_OUT, 1).set(YJLoginActivity.MY_LOGIN_OUT, true)));
                                      finishAll();
                                      break;
                                  default:
                                      break;
                              }

                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }
                  });
    }


    public class YJMedalAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater layoutInflater;
        public YJMedalAdapter() {
        }
        public YJMedalAdapter(Context context) {
            this.mContext = context;
            layoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mYJMedalModelList == null ? 0 : mYJMedalModelList.size();
        }

        @Override
        public Object getItem(int i) {
            return mYJMedalModelList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                view = layoutInflater.inflate(R.layout.item_medal_gridview, null);
                holder = new ViewHolder();
                holder.imageview = (ImageView) view.findViewById(R.id.item_medal_gridview_image);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            YJMedalModel yjMedalModel = mYJMedalModelList.get(i);
            if ("Yes".equals(yjMedalModel.getIsGet())) {
                BitmapUtils.create(getContext()).display(holder.imageview, yjMedalModel.getIconUrl());
            } else if ("No".equals(yjMedalModel.getIsGet())) {
                BitmapUtils.create(getContext()).display(holder.imageview, yjMedalModel.getNotGetIcon());
            }
            return view;
        }
    }
    private static class ViewHolder {
        ImageView imageview;
    }
}
