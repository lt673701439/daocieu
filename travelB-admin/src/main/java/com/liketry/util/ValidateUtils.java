package com.liketry.util;

import java.util.regex.Pattern;

/**
 * Created by BJAdmin on 2017/8/29.
 */
public class ValidateUtils {

    private static Pattern PATTERN_MOBILE = Pattern.compile("1\\d{10}");

    //验证是否是手机号
    public static boolean isMobile(String mobile) {
        return mobile != null && PATTERN_MOBILE.matcher(mobile).matches();
    }
}