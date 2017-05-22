package com.youjing.yjeducation.ui.dispaly.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.BitmapUtils;
import com.youjing.yjeducation.R;
import com.youjing.yjeducation.core.BaseSwipeBackActivity;
import com.youjing.yjeducation.core.YJBaseActivity;
import com.youjing.yjeducation.core.YJGlobal;
import com.youjing.yjeducation.core.YJNotifyTag;
import com.youjing.yjeducation.core.YJTitleLayoutController;
import com.youjing.yjeducation.model.YJUser;
import com.youjing.yjeducation.takephoto.app.TakePhoto;
import com.youjing.yjeducation.takephoto.app.TakePhotoFragmentActivity;
import com.youjing.yjeducation.takephoto.compress.CompressConfig;
import com.youjing.yjeducation.takephoto.model.CropOptions;
import com.youjing.yjeducation.ui.actualize.activity.YJChangeParentOrName;
import com.youjing.yjeducation.ui.actualize.dialog.YJSelectAreaDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJSelectBirthdayDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJSelectGradeDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJSelectSexDialog;
import com.youjing.yjeducation.ui.actualize.dialog.YJSelectSubjectDialog;
import com.youjing.yjeducation.ui.dispaly.dialog.YJSetUserPhotoDialog;
import com.youjing.yjeducation.util.ClickUtil;
import com.youjing.yjeducation.util.FileUtils;
import com.youjing.yjeducation.util.GetDistrictData;
import com.youjing.yjeducation.util.SharedUtil;
import com.youjing.yjeducation.util.TranslationUtils;
import com.youjing.yjeducation.wiget.CustomImage;

import org.vwork.mobile.ui.delegate.IVClickDelegate;
import org.vwork.mobile.ui.utils.VLayoutTag;
import org.vwork.mobile.ui.utils.VViewTag;
import org.vwork.utils.notification.IVNotificationListener;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * user  秦伟宁
 * Date 2016/3/31
 * Time 18:34
 */
@VLayoutTag(R.layout.activity_user_info)
public abstract class AYJUserInfoActivity extends TakePhotoFragmentActivity implements IVClickDelegate, TakePhoto.TakeResultListener {

    @VViewTag(R.id.re_subject)
    private RelativeLayout mRe_subject;
    @VViewTag(R.id.re_nick_name)
    private RelativeLayout mRe_nick_name;
    @VViewTag(R.id.re_QQ_num)
    private RelativeLayout mRe_QQ_num;
    @VViewTag(R.id.txt_QQ_num_show)
    private TextView mTxt_QQ_num_show;
    @VViewTag(R.id.re_lock_grade)
    private RelativeLayout mRe_lock_grade;
    @VViewTag(R.id.re_birthday)
    private RelativeLayout mRe_birthday;
    @VViewTag(R.id.re_sex)
    private RelativeLayout mRe_sex;
    @VViewTag(R.id.re_area)
    private RelativeLayout mRe_area;
    @VViewTag(R.id.img_user_photo)
    private CustomImage mImg_user_photo;
    @VViewTag(R.id.txt_user_sex)
    private TextView mTxt_user_sex;
    @VViewTag(R.id.txt_user_area)
    private TextView mTxt_user_area;
    @VViewTag(R.id.txt_user_birthday)
    private TextView mTxt_user_birthday;
    @VViewTag(R.id.txt_user_nick_name_show)
    private TextView mEt_user_nick_name;
    //    @VViewTag(R.id.cb_grade_lock)
//    private CheckBox mCb_grade_lock;
    @VViewTag(R.id.txt_phone_num_show)
    private TextView mTxt_phone_num_show;
    @VViewTag(R.id.re_parent_num)
    private RelativeLayout mRe_parent_num;
    @VViewTag(R.id.lay_answer_time_order)
    private RelativeLayout lay_answer_time_order;

    @VViewTag(R.id.txt_user_nick_name_show)
    private TextView mTxt_user_nick_name_show;
    @VViewTag(R.id.txt_parent_num_show)
    private TextView mTxt_parent_num_show;
    @VViewTag(R.id.txt_grade)
    private TextView mTxt_grade;
    @VViewTag(R.id.txt_subject_show)
    private TextView mTxt_subject_show;

    private File mTempFile;
    private File mCameraFile;
    private String mPath;
    private int status = 0;
    private int USE_CAMERA_FILE = 1;
    private int USE_TEMP_FILE = 2;

    private int cropX = 170;
    private int cropY = 200;
    private String TAG = "AYJUserInfoActivity";
    private File filepath;
    private YJUser mYjUser;
    private Bitmap LOAD_BITMAP;
    private Bitmap NO_LOAD_BITMAP;
    private String imagePath;
    private File file;
    @Override
    protected void onLoadedView() {
        YJTitleLayoutController.initTitleBuleBg(this, "设置个人资料", true);
        mYjUser = YJGlobal.getYjUser();
        if (SharedUtil.getInteger(getContext(), "baseIndex", 0) != -1) {
            SharedUtil.setInteger(getContext(), "baseIndex", SharedUtil.getInteger(getContext(), "baseIndex", 0) + 1);
        }

        if (null != mYjUser) {
//            if (mYjUser.getSex().equals("Male")) {
//                mTxt_user_sex.setText("男");
//            } else if (mYjUser.getSex().equals("Female")) {
//                mTxt_user_sex.setText("女");
//            } else if (mYjUser.getSex().equals("Unknown")) {
//                mTxt_user_sex.setText("保密");
//            }

            mTxt_user_sex.setText(mYjUser.getSex().equals("Male") ? "男" : mYjUser.getSex().equals("Female") ? "女" : mYjUser.getSex().equals("Unknown") ? "保密" : "未填写");
            mTxt_user_birthday.setText(TextUtils.isEmpty(mYjUser.getBirthday()) || "未填写".equals(mYjUser.getBirthday()) ? "未填写" : mYjUser.getBirthday());
            mEt_user_nick_name.setText(TextUtils.isEmpty(mYjUser.getNickName()) || "未填写".equals(mYjUser.getNickName()) ? "未填写" : mYjUser.getNickName());
            mTxt_phone_num_show.setText(TextUtils.isEmpty(mYjUser.getMobile()) ? "未填写" : mYjUser.getMobile());
            mTxt_grade.setText(TextUtils.isEmpty(mYjUser.getGradeId()) ? "未填写" : TranslationUtils.translationIdGradeName(mYjUser.getGradeId()));
            mTxt_subject_show.setText(TextUtils.isEmpty(mYjUser.getSubjectId()) ? "未填写" : TranslationUtils.translationIdSubjectName(mYjUser.getSubjectId()));
            mTxt_parent_num_show.setText(TextUtils.isEmpty(mYjUser.getParentMobile()) || "未填写".equals(mYjUser.getParentMobile()) ? "未填写" : mYjUser.getParentMobile());
            mTxt_QQ_num_show.setText(TextUtils.isEmpty(mYjUser.getQqNumber()) || "未填写".equals(mYjUser.getQqNumber()) ? "未填写" : mYjUser.getQqNumber());

            file = new File(FileUtils.getDir("youjing", getContext()) + "/province_data.xml");
            if (!file.exists()) {
                GetDistrictData.setData(getContext());
            }else {
                mTxt_user_area.setText(TextUtils.isEmpty(mYjUser.getAddressProvinceId()) ? "未填写" : TextUtils.isEmpty(mYjUser.getAddressDistrictId()) ? "未填写" : TextUtils.isEmpty(mYjUser.getAddressCityId()) ? "未填写" : TranslationUtils.searchIdDistrictName(mYjUser.getAddressDistrictId(), mYjUser.getAddressProvinceId(), mYjUser.getAddressCityId(),getContext()));
            }
            imagePath = mYjUser.getPic();
            initPhoto(imagePath);
        }


        mTempFile = new File(getDefHeadFile());
        mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "DCIM/" + "Camera";
        filepath = new File(mPath);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }
        mCameraFile = new File(filepath, getPhotoFileName());

        bindNotifyListener();
    }

    @Override
    public void onClick(View view) {
        if (view == mRe_subject) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            if (TextUtils.isEmpty(mYjUser.getGradeId())) {
                showToast("请先选择年级");
            } else {
                showDialog(new YJSelectSubjectDialog());
            }
        } else if (view == mRe_lock_grade) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showDialog(new YJSelectGradeDialog());
//            if(mCb_grade_lock.isChecked()){
//                mCb_grade_lock.setChecked(false);
//            }else {
//                mCb_grade_lock.setChecked(true);
//            }


        } else if (view == lay_answer_time_order) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showDialog(new YJSetUserPhotoDialog());
        } else if (view == mRe_birthday) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showDialog(new YJSelectBirthdayDialog());
        } else if (view == mRe_sex) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showDialog(new YJSelectSexDialog());
        } else if (view == mRe_area) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            if (!file.exists()) {
                GetDistrictData.setData(getContext());
            }else {
                showDialog(new YJSelectAreaDialog());
            }
        } else if (view == mImg_user_photo) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            showDialog(new YJSetUserPhotoDialog());
        } else if (view == mRe_nick_name) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(createIntent(YJChangeParentOrName.class, createTransmitData(YJChangeParentOrName.YJ_CHANGE, mTxt_user_nick_name_show.getText().toString().trim() + ",昵称")));
        } else if (view == mRe_parent_num) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(createIntent(YJChangeParentOrName.class, createTransmitData(YJChangeParentOrName.YJ_CHANGE, mTxt_parent_num_show.getText().toString().trim() + ",家长手机号")));
        } else if (view == mRe_QQ_num) {
            if (ClickUtil.isFastDoubleClick()) {
                return;
            }
            startActivity(createIntent(YJChangeParentOrName.class, createTransmitData(YJChangeParentOrName.YJ_CHANGE, mTxt_QQ_num_show.getText().toString().trim() + ",QQ号")));
        }

    }


    private void bindNotifyListener() {

        addListener(YJNotifyTag.HEAD_USER, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String head = (String) value;
                initPhoto(head);
            }
        });
        addListener(YJNotifyTag.USER_SEX, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String sex = (String) value;
                mTxt_user_sex.setText(TextUtils.isEmpty(sex) ? "未填写" : sex);
                setUserSex(sex);

            }
        });
        addListener(YJNotifyTag.USER_AREA, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String area = (String) value;
                StringUtils.Log("hujiachen", "area=" + area.split(",")[2].split("-")[0]);
                mTxt_user_area.setText(TextUtils.isEmpty(area.split(",")[2].split("-")[0]) ? "未填写" : area.split(",")[2].split("-")[0]);//.split("|")[0]
                setArea(area);
            }
        });
        addListener(YJNotifyTag.USER_BIRTHDAY, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String birthday = (String) value;
                mTxt_user_birthday.setText(TextUtils.isEmpty(birthday) ? "未填写" : birthday);
                setUserBirthday(birthday);
            }
        });

        addListener(YJNotifyTag.USER_GRADE, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String gradeId = ((String) value).split(",")[1];
                String gradeName = ((String) value).split(",")[0];
                mTxt_grade.setText(TextUtils.isEmpty(gradeName) ? "未填写" : gradeName);
                setGrade(gradeId);
            }
        });

        addListener(YJNotifyTag.USER_SUBJECT, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String subjectId = ((String) value).split(",")[1];
                String subjectName = ((String) value).split(",")[0];
                mTxt_subject_show.setText(TextUtils.isEmpty(subjectName) ? "未填写" : subjectName);
                setSubjet(subjectId);
            }
        });
// 昵称
        addListener(YJNotifyTag.USER_NICKNAME, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String nickName = (String) value;
                mEt_user_nick_name.setText(TextUtils.isEmpty(nickName) || "未填写".equals(nickName) ? "未填写" : nickName);
                setNickName(nickName);
            }
        });
// 家长手机号
        addListener(YJNotifyTag.USER_PARENT, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String parentNo = (String) value;
                mTxt_parent_num_show.setText(TextUtils.isEmpty(parentNo) || "未填写".equals(parentNo) ? "未填写" : parentNo);
                setParent(parentNo);
            }
        });
        //QQ号码
        addListener(YJNotifyTag.USER_QQ, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                String qqNum = (String) value;
                mTxt_QQ_num_show.setText(TextUtils.isEmpty(qqNum) || "未填写".equals(qqNum) ? "未填写" : qqNum);
                setQQNum(qqNum);
            }
        });


        addListener(YJNotifyTag.USER_PHOTO, new IVNotificationListener() {
            @Override
            public void onNotify(String s, Object value) {
                boolean sdCardExist = Environment.getExternalStorageState()
                        .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
                if (!sdCardExist){
                    showToast("没有可用的SD卡");
                    return;
                }
                File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                CompressConfig compressConfig = new CompressConfig.Builder().setMaxSize(50 * 1024).setMaxPixel(800).create();
                CropOptions cropOptions = new CropOptions.Builder().setAspectX(1).setAspectY(1).setWithOwnCrop(false).create();
                int type = (int) value;
                switch (type) {
                    case YJSetUserPhotoDialog.PHOTO_CAMERA:// 相机
                        getTakePhoto().onEnableCompress(compressConfig, true).onPickFromCaptureWithCrop(imageUri, cropOptions);
                        break;
                    case YJSetUserPhotoDialog.PHOTO_FILE:// 相册
                        getTakePhoto().onEnableCompress(compressConfig, true).onPickFromGalleryWithCrop(imageUri, cropOptions);
                        break;
                }
            }
        });
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(String msg) {
        super.takeFail(msg);
    }

    @Override
    public void takeSuccess(String imagePath) {
        super.takeSuccess(imagePath);
        showImg(imagePath);
    }

    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
        mImg_user_photo.setImageBitmap(bitmap);

        OutputStream os = null;
        try {
            os = new FileOutputStream(mCameraFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, os);
            os.flush();
            os.close();
            saveCameraPhoto(mCameraFile);
        } catch (IOException e) {
            e.getStackTrace();
        }
    }

    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".png";
    }

    private void initPhoto(String head) {
        if (LOAD_BITMAP == null) {
            LOAD_BITMAP = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo);
        }
        if (NO_LOAD_BITMAP == null) {
            NO_LOAD_BITMAP = BitmapFactory.decodeResource(getResources(), R.mipmap.img_app_logo);
        }
        StringUtils.Log(TAG, "head = " + head);
        BitmapUtils.create(getContext()).display(mImg_user_photo, head, LOAD_BITMAP, NO_LOAD_BITMAP);
    }


    protected abstract String getDefHeadFile();

    protected abstract String getCacheImagePath();

    protected abstract void setUserSex(String sex);

    protected abstract void setUserBirthday(String birthday);

    protected abstract void saveCameraPhoto(File aFile);

    protected abstract void setGrade(String id);

    protected abstract void setSubjet(String id);

    protected abstract void setNickName(String name);

    protected abstract void setParent(String parent);

    protected abstract void setQQNum(String parent);

    protected abstract void setArea(String area);
}
