package com.youjing.yjeducation.util;

import android.text.TextUtils;
import com.youjing.yjeducation.util.StringUtils;

import com.youjing.yjeducation.common.config.YJConfig;
import com.youjing.yjeducation.core.YJGlobal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * user  秦伟宁
 * Date 2016/3/10
 * Time 10:04
 */
public class MakeSign {
    private static List<String> keyList = null;
    private static String MD5Sign = "";


    public static String makeSignNew(Map<String,Object> map)  {

        if(keyList == null){
            keyList = new ArrayList<String>();
        }else {
            keyList.clear();
        }
        for(String key : map.keySet()){
            keyList.add(key);
        }

        Collections.sort(keyList);
        StringBuffer buffer = new StringBuffer();
        for (String key:keyList){
            buffer.append(key).append(map.get(key));
        }
        StringUtils.Log("buffer======",buffer.toString());
        String  MD5Sign = "";
        try {
            MD5Sign = MD5.MD5(buffer.toString() + YJConfig.MD5SIGN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyList.clear();
        if(!TextUtils.isEmpty(MD5Sign)){
            return MD5Sign;
        }else {
            return "";
        }

    }


    public static String makeSign(Map<String,Object> map)  {
        map.put("platform", YJConfig.PLATFORM);
        map.put("version", YJGlobal.getVersionName());
        map.put("deviceId",YJGlobal.getDeviceid());

        if(keyList == null){
            keyList = new ArrayList<String>();
        }else {
            keyList.clear();
        }
        for(String key : map.keySet()){
            keyList.add(key);
        }

        Collections.sort(keyList);
        StringBuffer buffer = new StringBuffer();
        for (String key:keyList){
            buffer.append(key).append(map.get(key));
        }
        String  MD5Sign = "";
        try {
            MD5Sign = MD5.MD5(buffer.toString() + YJConfig.MD5SIGN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyList.clear();
        if(!TextUtils.isEmpty(MD5Sign)){
            return MD5Sign;
        }else {
            return "";
        }

    }
    public static String makeSignLogined(Map<String,Object> map)  {
        map.put("platform", YJConfig.PLATFORM);
        map.put("version", YJGlobal.getVersionName());
        map.put("deviceId",YJGlobal.getDeviceid());
        map.put("customerToken", YJGlobal.getCustomertoken());
        map.put("customerId", YJGlobal.getCustomerId());
        if(keyList == null){
            keyList = new ArrayList<String>();
        }else {
            keyList.clear();
        }
        for(String key : map.keySet()){
            keyList.add(key);
        }

        Collections.sort(keyList);
        StringBuffer buffer = new StringBuffer();
        for (String key:keyList){
            buffer.append(key).append(map.get(key));
        }
        String  MD5Sign = "";
        try {
            MD5Sign = MD5.MD5(buffer.toString() + YJConfig.MD5SIGN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        keyList.clear();
        if(!TextUtils.isEmpty(MD5Sign)){
            return MD5Sign;
        }else {
            return "";
        }

    }
}
