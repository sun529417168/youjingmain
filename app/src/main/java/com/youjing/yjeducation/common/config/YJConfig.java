package com.youjing.yjeducation.common.config;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:19
 */
public class YJConfig {

    public static String ENCRYPTKEY;
    public static String PLATFORM;
    public static String MD5SIGN;
    public static int mScreenWidth;
    public static int mScreenHeight;
    public static String IM_APP_KEY; //im appkey

    public static String APP_ID;//微信appId
    public static String SINA_APP_KEY;//新浪_appkey
    public static String SINA_APP_SECRET;//新浪_appsecret

    public static String BUGLY_APP_ID;
    public static String YJ_HTTP_ADDRESS;
    public static String YJ_SHARE_HTTP_ADDRESS;

    public static String QQ_APP_ID;//qq appId

    public static boolean isOutputLog;
    public enum YJServerConnection {
        MainServer, TestServer
    }

    public static YJServerConnection CURRENT_SERVER = YJServerConnection.MainServer;

    static {
        if (CURRENT_SERVER == YJServerConnection.MainServer) {
            isOutputLog = false;
            ENCRYPTKEY = "12345678";
            PLATFORM = "Android";
            MD5SIGN = "qiqifei!@#";
            APP_ID = "wxaecdf663744b428e";
            IM_APP_KEY = "23362570";
            SINA_APP_KEY = "2757620975";
            SINA_APP_SECRET = "bd044eb4fa06944d68989c69df9a81cc";
            QQ_APP_ID = "1105311125";
            BUGLY_APP_ID = "900031735";
            YJ_HTTP_ADDRESS = "http://android.youjing.cn/";
            YJ_SHARE_HTTP_ADDRESS = "http://m.youjing.cn/app/invite.do";
        } else if (CURRENT_SERVER == YJServerConnection.TestServer) {
            isOutputLog = true;
            ENCRYPTKEY = "12345678";
            PLATFORM = "Android";
            MD5SIGN = "qiqifei!@#";
            APP_ID = "wxaecdf663744b428e";
            IM_APP_KEY = "23356353";
            SINA_APP_KEY = "2757620975";
            SINA_APP_KEY = "bd044eb4fa06944d68989c69df9a81cc";
            QQ_APP_ID = "1105311125";
            BUGLY_APP_ID = "900031735";
            YJ_HTTP_ADDRESS = "http://10.10.10.219:8080/";
//            YJ_HTTP_ADDRESS = "http://10.10.10.219:8081/";
//            YJ_HTTP_ADDRESS = "http://10.10.10.226:8080/";
            YJ_SHARE_HTTP_ADDRESS = "http://10.10.10.219:6080/app/invite.do";
//            YJ_SHARE_HTTP_ADDRESS = "http://10.10.10.226:6080/app/invite.do";
        }
    }
}
