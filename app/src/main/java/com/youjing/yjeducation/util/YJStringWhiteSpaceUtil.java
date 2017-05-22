package com.youjing.yjeducation.util;

/**
 * User 秦伟宁
 * Date 2016/1/14
 * Time 13:17
 */
public class YJStringWhiteSpaceUtil {
    public static boolean checkIsWhiteSpace(char c)
    {
        boolean isWhiteSpace = false;
        if (Character.isWhitespace(c))
        {
            isWhiteSpace = true;
        }
        return isWhiteSpace;
    }
}
