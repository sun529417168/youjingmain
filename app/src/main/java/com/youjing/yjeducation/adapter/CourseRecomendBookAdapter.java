package com.youjing.yjeducation.adapter;

import android.content.Context;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.loopj.android.http.TextHttpResponseHandler;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJReqURLProtocol;
import com.youjing.yjeducation.core.YJStudentReqManager;
import com.youjing.yjeducation.model.YJRecommendBook;
import com.youjing.yjeducation.ui.dispaly.activity.AYJBookWebviewActivity;
import com.youjing.yjeducation.ui.dispaly.activity.AYJCoursePlayNewActivity;
import com.youjing.yjeducation.util.StringUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.vwork.mobile.ui.IVActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author stt
 *         类说明：课程首页的底部课程类型的适配器
 *         创建时间：2016.7.2
 */
public class CourseRecomendBookAdapter extends BaseAdapter {
    private List<YJRecommendBook> recommendBookArrayList = new ArrayList<YJRecommendBook>();
    private Context context;
    private LayoutInflater layoutInflater;
    private CourseRecomendBookAdapter courseRecomendBookAdapter;
    private IVActivity  ivActivity;
    private boolean isLogin;
    public CourseRecomendBookAdapter(IVActivity ivActivity,Context context, List<YJRecommendBook> recommendBookArrayList,boolean isLogin) {
        this.recommendBookArrayList = recommendBookArrayList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.ivActivity = ivActivity;
        this.isLogin  = isLogin;
    }

    @Override
    public int getCount() {
        return recommendBookArrayList.size() == 0 || recommendBookArrayList == null ? 0 : recommendBookArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return recommendBookArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_course_related_book_item, null);
            holder.item_course_related_book_text = (TextView) convertView.findViewById(R.id.item_course_related_book_text);
            holder.item_course_related_book_image = (ImageView) convertView.findViewById(R.id.item_course_related_book_image);
            holder.ll_gridview_item = (LinearLayout) convertView.findViewById(R.id.ll_gridview_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final YJRecommendBook yjRecommendBook = recommendBookArrayList.get(position);
        holder.item_course_related_book_text.setText(yjRecommendBook.getName());
        BitmapUtils.create(context).display(holder.item_course_related_book_image, yjRecommendBook.getBookPic());

        holder.ll_gridview_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AYJCoursePlayNewActivity.mOnPause  = false;
                if(!TextUtils.isEmpty(yjRecommendBook.getBookId())){
                    bookClick(yjRecommendBook.getBookId());
                }
                if(!TextUtils.isEmpty(yjRecommendBook.getMicroShopAddress())){
                    ivActivity.startActivity(ivActivity.createIntent(AYJBookWebviewActivity.class, ivActivity.createTransmitData(AYJBookWebviewActivity.URL, yjRecommendBook.getMicroShopAddress())));
                }
            }
        });
        holder.item_course_related_book_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AYJCoursePlayNewActivity.mOnPause  = false;
                if(!TextUtils.isEmpty(yjRecommendBook.getBookId())){
                    bookClick(yjRecommendBook.getBookId());
                }
                if(!TextUtils.isEmpty(yjRecommendBook.getMicroShopAddress())){
                    ivActivity.startActivity(ivActivity.createIntent(AYJBookWebviewActivity.class, ivActivity.createTransmitData(AYJBookWebviewActivity.URL, yjRecommendBook.getMicroShopAddress())));
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView item_course_related_book_text;
        ImageView item_course_related_book_image;
        LinearLayout ll_gridview_item;
    }


    public void bookClick(String bookID) {
       final String TAG = "CourseRecomendBookAdapter";
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap.put("bookId", bookID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        YJStudentReqManager.getServerData(YJReqURLProtocol.BOOK_COURSE, objectMap, isLogin, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                StringUtils.Log(TAG, "失败=" + s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {

                    StringUtils.Log(TAG, "成功s=" + s);
                    JSONObject json = null;
                    json = new JSONObject(s);
                    switch (json.getInt("code")) {
                        case 200:

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

}