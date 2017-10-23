package com.liketry.ministore.utils;

import android.text.TextUtils;
import android.util.Log;

import com.liketry.ministore.common.Constants;

/**
 * 自定义日志，单字母方法打印系统tag，双字母方法打印自定义tag
 */
public class LogUtil {

    public static void i(Object... objs) {
        ii(Constants.APP_TAG, objs);
    }

    public static void ii(String tag, Object... objs) {
        if (!Constants.IS_DEBUG)
            return;
        Log.i(tag, concatString(objs));
    }

    public static void e(Object... objs) {
        ee(Constants.APP_TAG, objs);
    }

    public static void ee(String tag, Object... objs) {
        if (!Constants.IS_DEBUG)
            return;
        Log.e(tag, concatString(objs));
    }

    public static void v(Object... objs) {
        vv(Constants.APP_TAG, objs);
    }

    public static void vv(String tag, Object... objs) {
        if (!Constants.IS_DEBUG)
            return;
        Log.v(tag, concatString(objs));
    }

    public static void d(Object... objs) {
        dd(Constants.APP_TAG, objs);
    }

    public static void dd(String tag, Object... objs) {
        if (!Constants.IS_DEBUG)
            return;
        Log.d(tag, concatString(objs));
    }

    private static String concatString(Object... objs) {
        String info = null;
        try {
            if (objs == null)
                return "log parameters is null";
            StringBuffer sb = new StringBuffer("");
            for (Object obj : objs) {
                if (obj == null)
                    sb.append(" null ");
                else
                    sb.append(obj.toString());
            }
            info = sb.toString();
        } catch (Exception e) {
            try {
                Log.e(Constants.APP_TAG, "Log have error: ".concat(e.getMessage()));
            } catch (Exception e1) {
                Log.e(Constants.APP_TAG, "输出日志 Exception 信息崩溃");
            }
            info = "log print error";
        }
        if (TextUtils.isEmpty(info))
            return "log parameter is null";
        else
            return info;
    }
}
