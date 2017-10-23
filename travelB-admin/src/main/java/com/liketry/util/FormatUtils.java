package com.liketry.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormatUtils {

    public static String getCommonDateStr(Date date) {
        if (date == null)
            return "";
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getShortDateStr(Date date) {
        if (date == null)
            return "";
        try {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getChineseDate(String date) {
        if (date == null)
            return "";
        try {
            Date date2 = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(date);
            return new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA).format(date2);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getChineseDate(Date date) {
        if (date == null)
            return "";
        try {
            return new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA).format(date);
        } catch (Exception e) {
            return "";
        }
    }
}
