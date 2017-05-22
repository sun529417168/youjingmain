package com.zf.myzxing;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.http.util.EncodingUtils;

/**
 * Created by ASUS on 2016/6/11.
 */
public class JsonFileUtils {
    /**
     * 首页内容缓存 写文件， 一般写在\data\data\com.xx\files\里面
     *
     * @param context
     * @param fileName
     * @param message
     */
    public static void writeDataFiles(Context context, String fileName, String message) {
        try {
            FileOutputStream fout = context.openFileOutput(fileName, context.MODE_PRIVATE);

            byte[] bytes = message.getBytes();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 读文件在./data/data/com.tt/files/下面
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readFileData(Context context, String fileName) {
        String res = "";
        try {
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res = EncodingUtils.getString(buffer, "UTF-8");
//            res = new String(buffer,"UTF-8");
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        }
        return res;

    }
}
