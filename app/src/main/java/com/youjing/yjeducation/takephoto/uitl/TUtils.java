package com.youjing.yjeducation.takephoto.uitl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;
import android.widget.Toast;


import com.youjing.yjeducation.takephoto.model.CropOptions;
import com.youjing.yjeducation.takephoto.model.TContextWrap;
import com.youjing.yjeducation.takephoto.model.TException;
import com.youjing.yjeducation.takephoto.model.TExceptionType;
import com.youjing.yjeducation.takephoto.model.TIntentWap;

import java.io.File;
import java.util.List;

/**
 * 工具类
 * Author: JPH
 * Date: 2016/7/26 10:04
 */
public class TUtils {
    private static final String TAG = IntentUtils.class.getName();

    /**
     * @param contextWrap
     * @param intentWap
     */
    public static void startActivityForResult(TContextWrap contextWrap,TIntentWap intentWap){
        if (contextWrap.getFragment()!=null){
            contextWrap.getFragment().startActivityForResult(intentWap.getIntent(),intentWap.getRequestCode());
        }else {
            contextWrap.getActivity().startActivityForResult(intentWap.getIntent(),intentWap.getRequestCode());
        }
    }
    /**
     * 安全地发送Intent
     * @param contextWrap
     * @param intentWapList 要发送的Intent以及候选Intent
     * @param defaultIndex 默认发送的Intent
     * @param isCrop 是否为裁切照片的Intent
     * @throws TException
     */
    public static void sendIntentBySafely(TContextWrap contextWrap, List<TIntentWap> intentWapList, int defaultIndex, boolean isCrop)throws TException {
        if (defaultIndex+1>intentWapList.size())throw new TException(isCrop? TExceptionType.TYPE_NO_MATCH_PICK_INTENT:TExceptionType.TYPE_NO_MATCH_CROP_INTENT);
        TIntentWap intentWap=intentWapList.get(defaultIndex);
        List result=contextWrap.getActivity().getPackageManager().queryIntentActivities(intentWap.getIntent(), PackageManager.MATCH_DEFAULT_ONLY);
        if (result.isEmpty()){
            sendIntentBySafely(contextWrap,intentWapList,++defaultIndex,isCrop);
        }else {
            startActivityForResult(contextWrap,intentWap);
        }
    }
    /**
     * 拍照前检查是否有相机
     **/
    public static void captureBySafely(TContextWrap contextWrap,TIntentWap intentWap)throws TException{
        List result=contextWrap.getActivity().getPackageManager().queryIntentActivities(intentWap.getIntent(), PackageManager.MATCH_DEFAULT_ONLY);
        if (result.isEmpty()){
            Toast.makeText(contextWrap.getActivity(), "没有找到相机", Toast.LENGTH_SHORT).show();
            throw new TException(TExceptionType.TYPE_NO_CAMERA);
        }else {
            startActivityForResult(contextWrap,intentWap);
        }
    }
    /**
     * 通过第三方工具裁切照片，当没有第三方裁切工具时，会自动使用自带裁切工具进行裁切
     * @param contextWrap
     * @param imageUri
     * @param outPutUri
     * @param options
     */
    public static void cropWithOtherAppBySafely(TContextWrap contextWrap, Uri imageUri, Uri outPutUri, CropOptions options){
        Intent nativeCropIntent=IntentUtils.getCropIntentWithOtherApp(imageUri, outPutUri,options);
        List result=contextWrap.getActivity().getPackageManager().queryIntentActivities(nativeCropIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (result.isEmpty()){
            cropWithOwnApp(contextWrap,imageUri,outPutUri,options);
        }else {
            try {
                imageUri= Uri.fromFile(new File(TUriParse.getFilePathWithDocumentsUri(imageUri, contextWrap.getActivity())));
            } catch (TException e) {
                e.printStackTrace();
            }
            startActivityForResult(contextWrap,new TIntentWap(IntentUtils.getCropIntentWithOtherApp(imageUri, outPutUri,options), TConstant.RC_CROP));
        }
    }
    /**
     * 通过TakePhoto自带的裁切工具裁切图片
     * @param contextWrap
     * @param imageUri
     * @param outPutUri
     * @param options
     */
    public static void cropWithOwnApp(TContextWrap contextWrap, Uri imageUri, Uri outPutUri, CropOptions options){
        if (options.getAspectX()*options.getAspectY()>0){
//            Crop.of(imageUri, outPutUri).withAspect(options.getAspectX(),options.getAspectY()).start(contextWrap.getActivity(),contextWrap.getFragment());
        }else if (options.getOutputX()*options.getOutputY()>0){
//            Crop.of(imageUri, outPutUri).withMaxSize(options.getOutputX(),options.getOutputY()).start(contextWrap.getActivity(),contextWrap.getFragment());
        }else {
//            Crop.of(imageUri, outPutUri).asSquare().start(contextWrap.getActivity(),contextWrap.getFragment());;
        }
    }
    /**
     * 是否裁剪之后返回数据
     **/
    public static boolean isReturnData() {
        String release= Build.VERSION.RELEASE;
        int sdk= Build.VERSION.SDK_INT;
        StringUtils.Log(TAG, "release:" + release + "sdk:" + sdk);
        String manufacturer = android.os.Build.MANUFACTURER;
        if (!TextUtils.isEmpty(manufacturer)) {
            if (manufacturer.toLowerCase().contains("lenovo")) {//对于联想的手机返回数据
                return true;
            }
        }
//        if (sdk>=21){//5.0或以上版本要求返回数据
//            return  true;
//        }
        return false;
    }
    /**
     * 显示圆形进度对话框
     *
     * @author JPH
     * Date 2014-12-12 下午7:04:09
     * @param activity
     * @param progressTitle
     *            显示的标题
     * @return
     */
    public static ProgressDialog showProgressDialog(final Activity activity,
                                                    String... progressTitle) {
        if(activity==null||activity.isFinishing())return null;
        String title = "提示";
        if (progressTitle != null && progressTitle.length > 0)
            title = progressTitle[0];
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(title);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }
}
