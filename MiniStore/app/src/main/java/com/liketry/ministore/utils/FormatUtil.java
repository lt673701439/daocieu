/**
 * @author Simon
 * @time 2017/6/6
 * 格式化工具类
 */
package com.liketry.ministore.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtil {
    private final static SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private final static SimpleDateFormat COMMON_DAY_DF = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private final static SimpleDateFormat APP_DF = new SimpleDateFormat("MM/dd/yyyy", Locale.CHINA);

    public static String getOneBitDecimal(double value) {
        try {
            DecimalFormat df = new DecimalFormat("0.0");
            return df.format(value);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 带异常保护的String换行int类型，异常返回-1；
     */
    public static int getInt(String str) {
        if (str == null)
            return -1;
        try {
            return Integer.valueOf(str);
        } catch (Exception e) {
            return -1;
        }
    }

    public static String getNormalTime(long time) {
        try {
            return DF.format(new Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getAppTime(Date time) {
        try {
            return APP_DF.format(time);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getCommonDay(Date time) {
        try {
            return COMMON_DAY_DF.format(time);
        } catch (Exception e) {
            return "";
        }
    }
}