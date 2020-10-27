package com.example.smallfish.utils;

/**
 * 数据转换类
 * author : Iwen大大怪
 * create : 2020/10/27 12:06
 */
public class StringConvert {
    public static String convertFeedUgc(int count) {
        if (count < 10000) {
            return String.valueOf(count);
        }
        return count / 10000 + "W";
    }
}
