package com.youjing.yjeducation.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.telephony.TelephonyManager;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 17:37
 */
public class FileUtils {
    public static final String ROOT_DIR = "AppCartoon";
    public static final String DOWNLOAD_DIR = "download";
    public static final String CACHE_DIR = "cache";
    public static final String ICON_DIR = "pics";

    /**
     * 判断SD卡是否挂载
     */
    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取下载目录
     */
    public static String getDownloadDir(Context context) {
        return getDir(DOWNLOAD_DIR, context);
    }

    /**
     * 获取缓存目录
     */
    public static String getCacheDir(Context context) {
        return getDir(CACHE_DIR, context);
    }

    /**
     * 获取icon目录
     */
    public static String getIconDir(Context context) {
        return getDir(ICON_DIR, context);
    }

    /**
     * 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录
     */
    public static String getDir(String name, Context context) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getExternalStoragePath());
        } else {
            sb.append(getCachePath(context));
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }

    /**
     * 获取SD下的应用目录
     */
    public static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }

    /**
     * 获取应用的cache目录
     */
    public static String getCachePath(Context context) {
        File f = context.getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }

    /**
     * 创建文件夹
     */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }

    /**
     * 把字符串数据写入文件
     *
     * @param content 需要写入的字符串
     * @param path    文件路径名称
     * @param append  是否以添加的模式写入
     * @return 是否写入成功
     */
    public static boolean writeFile(byte[] content, String path, boolean append) {
        boolean res = false;
        File f = new File(path);
        RandomAccessFile raf = null;
        try {
            if (f.exists()) {
                if (!append) {
                    f.delete();
                    f.createNewFile();
                }
            } else {
                f.createNewFile();
            }
            if (f.canWrite()) {
                raf = new RandomAccessFile(f, "rw");
                raf.seek(raf.length());
                raf.write(content);
                res = true;
            }
        } catch (Exception e) {
//			LogUtils.e(e);
        } finally {
//			IOUtils.close(raf);
        }
        return res;
    }

    //获取应用的版本号
    public static String getVersionName(Context context)
    {
        // 获取packagemanager的实例
        PackageManager packageManager =context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }

    public static  String getDeviceId(Context context){
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        if(deviceid == null || deviceid.length() == 0){
            WifiManager manager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (manager != null) {
                deviceid = manager.getConnectionInfo().getMacAddress();
            }
        }
        return deviceid;
    }

}