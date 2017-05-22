package com.youjing.yjeducation.takephoto.uitl;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;


import com.youjing.yjeducation.takephoto.model.TException;
import com.youjing.yjeducation.takephoto.model.TExceptionType;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Uri解析工具类
 * Author: JPH
 * Date: 2015/8/26 0026 16:23
 */
public class TUriParse {
    private static final String TAG = IntentUtils.class.getName();
    /**
     * 通过URI获取文件的路径
     * @param uri
     * @param activity
     * @return
     * Author JPH
     * Date 2016/6/6 0006 20:01
     */
    public static String getFilePathWithUri(Uri uri, Activity activity)throws TException {
        if(uri==null){
            StringUtils.Log(TAG, "uri is null,activity may have been recovered?");
            throw new TException(TExceptionType.TYPE_URI_NULL);
        }
        String picturePath = null;
        String scheme=uri.getScheme();
        if (ContentResolver.SCHEME_CONTENT.equals(scheme)){
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = activity.getContentResolver().query(uri,
                    filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);  //获取照片路径
            cursor.close();
        }else if (ContentResolver.SCHEME_FILE.equals(scheme)){
            picturePath=uri.getPath();
        }
        if (TextUtils.isEmpty(picturePath))throw new TException(TExceptionType.TYPE_URI_PARSE_FAIL);
        if (!TImageFiles.checkMimeType(activity,TImageFiles.getMimeType(activity,uri)))throw new TException(TExceptionType.TYPE_NOT_IMAGE);
        return picturePath;
    }
    /**
     * 通过从文件中得到的URI获取文件的路径
     * @param uri
     * @param activity
     * @return
     * Author JPH
     * Date 2016/6/6 0006 20:01
     */
    public static String getFilePathWithDocumentsUri(Uri uri,Activity activity) throws TException {
        if(uri==null){
            StringUtils.Log(TAG, "uri is null,activity may have been recovered?");
            return null;
        }
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())&&uri.getPath().contains("document")){
            File tempFile = TImageFiles.getTempFile(activity,uri);
            try {
                TImageFiles.inputStreamToFile(activity.getContentResolver().openInputStream(uri),tempFile);
                return tempFile.getPath();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new TException(TExceptionType.TYPE_NO_FIND);
            }
        }else {
            return getFilePathWithUri(uri,activity);
        }
    }
}
