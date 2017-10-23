/**
 * @author Simon
 * @time 2017/5/31
 * 常量
 */
package com.liketry.ministore.common;

public interface Constants {
    //上线改动
    boolean IS_DEBUG = true;

    //以下上线不改动
    String URL_SERVER = "http://211.159.151.156:8070";
    //    String URL_DEVELOP = "http://211.159.151.156:8090";
    String URL_DEVELOP = "http://192.168.1.222:8070";
    @SuppressWarnings("all")
    String DOMAIN = (IS_DEBUG ? URL_DEVELOP : URL_SERVER).concat("/");
    String IMG_DOMAIN = "http://211.159.151.156:8080/lti-opt/static/upload/";
    String APP_TAG = "like_try:";
    String SHARED_PREFERENCES_NAME = "liketry";
    String INTEGER_DATA = "data";
    String INTEGER_TYPE = "type";
    String INTEGER_ATTACH = "attach";
    String DB_NAME = "liketry";
    String WX_ID = "wxa955ea34202fc9be";
    String WX_SHOP_ID = "1484075382";

    String M_KEY_USER = "user";//用户信息
    String M_KEY_UID = "UID";//用户ID
    String M_KEY_MID = "MID";//商户ID
}
