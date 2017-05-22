package com.youjing.yjeducation.util;

import com.youjing.yjeducation.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * user  秦伟宁
 * Date 2016/6/17
 * Time 13:29
 */
public class YJLiveRoomUtil {
    //礼物Id
    public static String getGiftId(String str) throws Exception {
        String regEx = "^\\[gf_([1-9]\\d*)_([1-9]\\d*)\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
       // String str = "[gf_11_21]【测试学员】发送【鲜花】*2";//字符串
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
           /* for (int i = 0; i <= matcher.groupCount(); i++) {
                StringUtils.Log("YJLiveRoomUtil", "matcher.group(i)=" + matcher.group(i));
            }*/
            return matcher.group(1);
        }
            return "";
    }

    //礼物数量
    public static String getGiftNum(String str) throws Exception {
        String regEx = "^\\[gf_([1-9]\\d*)_([1-9]\\d*)\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
        // String str = "[gf_11_21]【测试学员】发送【鲜花】*2";//字符串
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
           /* for (int i = 0; i <= matcher.groupCount(); i++) {
                StringUtils.Log("YJLiveRoomUtil", "matcher.group(i)=" + matcher.group(i));
            }*/
            return matcher.group(2);
        }
        return "";
    }

    public static boolean isGiftStr(String str) throws Exception {
        String regEx = "^\\[gf_([1-9]\\d*)_([1-9]\\d*)\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
        //String str = "[gf_11_21]【测试学员】发送【鲜花】*2";//字符串
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
           return true;
        }
        return false;
    }


    //红包ID
    public static String getRedId(String str) throws Exception {
        String regEx = "^\\[red_([1-9]\\d*)_([1-9]\\d*)\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    //红包批次
    public static String getRedBatch(String str) throws Exception {
        String regEx = "^\\[red_([1-9]\\d*)_([1-9]\\d*)\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }
    public static boolean isRedStr(String str) throws Exception {
        //String str = "[red_1_1]【30元鲸币手气红包】发送成功";//字符串
        String regEx = "^\\[red_msg\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }
    public static boolean isUserRedInfo(String str) throws Exception {
        //String str = "[red_1_1]【30元鲸币手气红包】发送成功";//字符串
        String regEx = "^\\[red_([1-9]\\d*)_([1-9]\\d*)\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


    public static boolean isPraiseStr(String str) throws Exception {
        String regEx = "^\\[lcl_([1-9]\\d*)\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return true;
        }
        return false;
    }


    //点赞数量
    public static String getPraiseNum(String str) throws Exception {
        String regEx = "^\\[lcl_([1-9]\\d*)\\]";//规则
        Pattern pattern = Pattern.compile(regEx);//正则
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
