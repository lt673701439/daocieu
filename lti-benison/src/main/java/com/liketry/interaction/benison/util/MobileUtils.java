package com.liketry.interaction.benison.util;

import com.sun.corba.se.impl.monitoring.MonitoredObjectImpl;

import java.util.regex.Pattern;

/**
 * author Simon
 * create 2017/7/19
 * 手机相关处理
 */
public class MobileUtils {
    private static Pattern patter = Pattern.compile("1[0-9]{10}");

    //验证手机号基本格式
    public static boolean valudateMobile(String mobile) {
        return !(mobile == null || mobile.length() != 11) && patter.matcher(mobile).matches();
    }
}
