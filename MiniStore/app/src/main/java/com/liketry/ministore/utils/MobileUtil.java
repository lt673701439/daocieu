/**
 * @author Simon
 * @time 2017/6/7
 * 工具类
 */
package com.liketry.ministore.utils;


import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.liketry.ministore.common.App;

import java.util.regex.Pattern;

public class MobileUtil {

    public static boolean isMobile(String mobile) {
        return !TextUtils.isEmpty(mobile) && mobile.matches("^1\\d{10}$");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int toPx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int toDp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int getMobileWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        App app = (App) context.getApplicationContext();
        WindowManager manager = (WindowManager) app.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
