package com.youjing.yjeducation.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * user  秦伟宁
 * Date 2016/3/3
 * Time 15:11
 */
public class CheckPhone {
    public static boolean isMobileNum(String mobiles)
    {
        //        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-3,5-9]))\\d{8}$");

        Pattern p = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }
}
