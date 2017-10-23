package com.liketry.ministore.control.img;

import android.widget.ImageView;

import com.liketry.ministore.common.Constants;

/**
 * author Simon
 * create 2017/7/11
 * glide管理
 */

public class ImgManager {

    //图片加载
    public static void loadImg(ImageView img, String url) {
        GlideApp.with(img).asBitmap().load(Constants.IMG_DOMAIN + url).into(img);
    }
}