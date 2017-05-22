package com.youjing.yjeducation.talkfun;

public class MainConsts {
    private static final String LIVE_LOGIN = "http://demo.talk-fun.com/login.php?key=%s&nickname=%s"; //直播登录接口
    private static final String PLAY_BACK_LOGIN = "http://demo.talk-fun.com/api/playback.php?liveid=%s";//点播登录接口
    public static final String UPDATE_INFO_URL = "http://fp1.talk-fun.com/install_files/mobile/android/huantuoDemo/update.json";

    public static String getLiveLogInUrl(String name, String key) {
        return String.format(LIVE_LOGIN, key, name);
    }

    public static String getPlaybackLogInUrl(String liveid) {
        return String.format(PLAY_BACK_LOGIN, liveid);
    }

    private static Boolean isConnected = false;

    public static void setIsConnected(boolean setConnected) {
        synchronized (isConnected) {
            isConnected = setConnected;
        }
    }

    public static boolean isConnected(){
        synchronized (isConnected) {
            return isConnected;
        }
    }

}
