package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Intent;
import android.os.Bundle;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lecloud.download.control.DownloadCenter;
import com.lecloud.download.info.LeDownloadInfo;
import com.lecloud.download.observer.LeDownloadObserver;
import com.letv.controller.PlayProxy;
import com.letv.universal.iplay.EventPlayProxy;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.model.YJCourseModel;
import com.youjing.yjeducation.ui.actualize.activity.YJDownLoadPlayActivity;

import org.vwork.mobile.ui.AVAdapterItem;
import org.vwork.mobile.ui.AVVirtualActivity;
import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;

import java.util.List;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 17:55
 */

@VLayoutTag(R.layout.activity_download_not)
public class AYJDownLoadNoActivity extends AVVirtualActivity implements IVClickDelegate {

    private String TAG = "AYJDownLoadNoActivity";
    @VViewTag(R.id.lv_course_download)
    private ListView mLv_course_download;

    private MyAdapter mAdapter = null;

    private DownloadCenter mDownloadCenter;
    private YJCourseModel mYjCourseModel;
    private List<LeDownloadInfo> mDownloadInfos;
    @Override
    protected void onLoadedView() {
        mYjCourseModel = YJGlobal.getYjCourseModel();

        mDownloadCenter = DownloadCenter.getInstances(getContext());
        mDownloadCenter.registerDownloadObserver(observer);
        mDownloadInfos = mDownloadCenter.getDownloadInfoList();

        if (mAdapter == null) {
            mAdapter = new MyAdapter(mDownloadInfos);
            mLv_course_download.setAdapter(mAdapter);
        } else {
            mAdapter.setData(mDownloadInfos);
            mAdapter.notifyDataSetChanged();
        }

        String uu = "40ff268ca7";
        String code = "6c658686bf";
        downloadClick(uu, code);
    }
    @Override
    public void onClick(View view) {

    }

    private class MyAdapter extends BaseAdapter {

        private List<LeDownloadInfo> data;

        public MyAdapter(List<LeDownloadInfo> infos) {
            data = infos;
        }

        public void setData(List<LeDownloadInfo> infos) {
            data = infos;
        }

        @Override
        public int getCount() {
            if (data == null) {
                return 0;
            }
            return 1;
        }

        @Override
        public Object getItem(int position) {
            if (data == null) {
                return null;
            }
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final LeDownloadInfo info = data.get(position);

            ViewHolder viewHolder = null;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = View.inflate(getContext(), R.layout.course_download_item, null);
                viewHolder.mTxt_download_type = (TextView)convertView.findViewById(R.id.txt_download_type);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (info.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_WAITING) {
                viewHolder.mTxt_download_type.setText("等待中");
                viewHolder.mTxt_download_type.setEnabled(false);
            } else if (info.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_DOWNLOADING) {
                viewHolder.mTxt_download_type.setText("暂停");
                viewHolder.mTxt_download_type.setEnabled(true);
            } else if (info.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_STOP) {
                viewHolder.mTxt_download_type.setText("继续");
                viewHolder.mTxt_download_type.setEnabled(true);
            } else if (info.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_SUCCESS) {
                viewHolder.mTxt_download_type.setText("播放");
                viewHolder.mTxt_download_type.setEnabled(true);
            }else if (info.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_NO_DISPATCH) {
                viewHolder.mTxt_download_type.setText("正在调度");
                viewHolder.mTxt_download_type.setEnabled(true);
            }else if (info.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_NO_PERMISSION) {
                viewHolder.mTxt_download_type.setText("没有权限");
                viewHolder.mTxt_download_type.setEnabled(true);
            }else if (info.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_URL_REQUEST_FAILED) {
                viewHolder.mTxt_download_type.setText("请求失败");
                viewHolder.mTxt_download_type.setEnabled(true);
            }else if(info.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_DISPATCHING){
                viewHolder.mTxt_download_type.setText("调度中");
                viewHolder.mTxt_download_type.setEnabled(true);
            }else{
                viewHolder.mTxt_download_type.setText("重试");
                viewHolder.mTxt_download_type.setEnabled(true);
            }
            viewHolder.mTxt_download_type.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pauseClick(info);
                }
            });

           /* remove.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mDownloadCenter.cancelDownload(info, true);
                }
            });*/
            return convertView;
        }

    }

    class ViewHolder{
        private TextView mTxt_download_type;
    }





    @VLayoutTag(R.layout.course_download_item)
    class YJCourseDownLoadItem extends AVAdapterItem {

        private List<LeDownloadInfo> data;

        public YJCourseDownLoadItem(List<LeDownloadInfo> infos) {
            data = infos;
        }

        public void setData(List<LeDownloadInfo> infos) {
            data = infos;
        }

        @VViewTag(R.id.lay_item)
        private RelativeLayout mLay_item;
        @VViewTag(R.id.view_line)
        private View mView_line;
        @VViewTag(R.id.img_course_teacher)
        private ImageView mIimg_course_teacher;
        @VViewTag(R.id.txt_name)
        private TextView mTxt_name;
        @VViewTag(R.id.txt_teacher_name)
        private TextView mTxt_teacher_name;
        @VViewTag(R.id.txt_download_type)
        private TextView mTxt_download_type;


        @Override
        public void update(final int position) {
            super.update(position);
            mLay_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        //String uu = DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(0).getUu()).trim();
                       // String code = DES.decryptDES(mYjCourseModel.getCourseCatalogList().get(0).getCode()).trim();

                        String uu = "40ff268ca7";
                        String code = "6c658686bf";
                        downloadClick(uu, code);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }



    }

    private void downloadClick(String uu, String vu) {
        DownloadCenter downloadCenter = DownloadCenter.getInstances(getContext());
//		downloadCenter.allowShowMsg(false);
        downloadCenter.downloadVideo("", uu, vu);
    }
    private LeDownloadObserver observer = new LeDownloadObserver() {

        @Override
        public void onDownloadSuccess(LeDownloadInfo info) {
            StringUtils.Log(TAG, "onDownloadSuccess" + info.getFileName());
            notifyData();
        }

        @Override
        public void onDownloadStop(LeDownloadInfo info) {
            StringUtils.Log(TAG, "onDownloadStop" + info.getFileName());
            notifyData();
        }

        @Override
        public void onDownloadStart(LeDownloadInfo info) {
            StringUtils.Log(TAG, "onDownloadStart" + info.getFileName());
            notifyData();
        }

        @Override
        public void onDownloadProgress(LeDownloadInfo info) {
            StringUtils.Log(TAG, "onDownloadProgress" + info.getFileName() + ",progress:" + info.getProgress());
            notifyData();
        }

        @Override
        public void onDownloadFailed(LeDownloadInfo info, String msg) {
            notifyData();
        }

        @Override
        public void onDownloadCancel(LeDownloadInfo info) {
            notifyData();
        }


        @Override
        public void onDownloadInit(LeDownloadInfo info, String msg) {
            notifyData();
        }
    };
    private void notifyData() {
        mDownloadInfos = mDownloadCenter.getDownloadInfoList();
        mAdapter.setData(mDownloadInfos);
        mAdapter.notifyDataSetChanged();
    }

    private void pauseClick(LeDownloadInfo downloadInfo2) {
        if (downloadInfo2.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_DOWNLOADING) {
            mDownloadCenter.stopDownload(downloadInfo2);
        } else if (downloadInfo2.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_STOP) {
            mDownloadCenter.resumeDownload(downloadInfo2);
        } else if(downloadInfo2.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_FAILED){
            mDownloadCenter.retryDownload(downloadInfo2);
        }else if(downloadInfo2.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_SUCCESS){
            Intent intent = new Intent(getContext(),YJDownLoadPlayActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putInt(PlayProxy.PLAY_MODE, EventPlayProxy.PLAYER_VOD);
            mBundle.putString(PlayProxy.PLAY_UUID, downloadInfo2.getUu());
            mBundle.putString(PlayProxy.PLAY_VUID, downloadInfo2.getVu());
            intent.putExtra(YJDownLoadPlayActivity.DATA, mBundle);
            startActivity(intent);
        }else if(downloadInfo2.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_URL_REQUEST_FAILED){
            mDownloadCenter.retryDownload(downloadInfo2);
        }else if(downloadInfo2.getDownloadState() == LeDownloadObserver.DOWLOAD_STATE_NO_PERMISSION){
           showToast("没有下载权限");
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mDownloadCenter != null) {
            mDownloadCenter.unregisterDownloadObserver(observer);
        }
    }


}
