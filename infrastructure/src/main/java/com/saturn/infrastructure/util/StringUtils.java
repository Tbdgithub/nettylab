package com.saturn.infrastructure.util;

/**
 * Created by john.y on 2017-6-29.
 */
public class StringUtils {

    public static boolean isNullOrEmpty(String str) {
        return str == null ? true : str.equals("");
    }
}
