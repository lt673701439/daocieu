package com.liketry.util;

import com.alibaba.fastjson.JSON;
import com.liketry.web.vm.ResultVM;
import lombok.Data;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * author Simon
 * create 2017/9/1
 * android 推送模型
 */
public class PushUtil {
    private static final CloseableHttpClient CLIENT = HttpClients.createDefault();
    private static final String ALIAS_TYPE = "COMMON";
    private static final String UM_URL = "http://msg.umeng.com/api/send";
    public static final String TYPE_UNICAST = "unicast";//单播
    public static final String TYPE_LISTCAST = "listcast";//列播
    public static final String TYPE_FILECAST = "filecast";//文件播
    public static final String TYPE_BROADCAST = "broadcast";//广播
    public static final String TYPE_GROUPCAST = "groupcast";//组播
    public static final String TYPE_CUSTOMIZEDCAST = "customizedcast";//自有的alias进行推送
    public static final String DISPLAY_TYPE_MESSAGE = "message";
    public static final String DISPLAY_TYPE_NOTIFICATION = "notification";


    public static String getSingle(String device, String title, String content, String id, int actionType) {
        switch (device) {
            case "ios":
                return getIOSAliasData(title, content, id, actionType);
            case "android":
                return getAndroidAliasData(title, content, id, actionType);
            default:
                return null;
        }
    }


    private static String getIOSAliasData(String title, String content, String id, int actionType) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("appkey", Constants.UMENG_IOS_APP_KEY);
        data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        data.put("type", TYPE_CUSTOMIZEDCAST);
        data.put("alias_type", ALIAS_TYPE);
        data.put("alias", id);
        HashMap<String, String> alert = new HashMap<>();
        alert.put("title", title);
        alert.put("body", content);
        HashMap<String, Object> aps = new HashMap<>();
        aps.put("alert", alert);
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("aps", aps);
        payload.put("action_type", actionType);
        data.put("payload", payload);
        data.put("production_mode", Constants.PRODUCTION_MODE);
        return JSON.toJSONString(data);
    }

    private static String getAndroidAliasData(String title, String content, String id, int actionType) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("appkey", Constants.UMENG_IOS_APP_KEY);
        data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        data.put("type", TYPE_CUSTOMIZEDCAST);
        data.put("alias_type", ALIAS_TYPE);
        data.put("alias", id);
        HashMap<String, Object> body = new HashMap<>();


        HashMap<String, Object> payload = new HashMap<>();
        payload.put("display_type", DISPLAY_TYPE_MESSAGE);

        return null;
    }


//    //根据id发送数据
//    public static String getSingle(String title, String text, String id, String activity) {
//        PushModel info = new PushModel();
//        info.setType(TYPE_CUSTOMIZEDCAST);
//        info.setAlias_type("alias");
//        info.setAlias(id);
//        AndroidPayLoadBody body = new AndroidPayLoadBody();
//        body.ticker = "新信息";
//        body.title = title;
//        body.text = text;
//        body.activity = activity;
//        info.setPayload(new AndroidPayload(DISPLAY_TYPE_NOTIFICATION, body, null));
//        return JSON.toJSONString(info);
//    }

    //广播数据
    public static String getBroadCast(String title, String text) {
        PushModel info = new PushModel();
        info.setType(TYPE_BROADCAST);
        AndroidPayLoadBody body = new AndroidPayLoadBody();
        body.ticker = "新信息";
        body.title = title;
        body.text = text;
        info.setPayload(new AndroidPayload(DISPLAY_TYPE_NOTIFICATION, body, null));
        return JSON.toJSONString(info);
    }

    public static UMResultModel getUMData(String device, String body) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        int status = 500;
        try {
            String sign = DigestUtils.md5Hex(("POST" + UM_URL + body + (Constants.DEVICE_IOS.equals(device) ? Constants.UMENG_IOS_APP_MASTER : Constants.UMENG_ANDROID_APP_MASTER)).getBytes("utf8"));
            HttpPost post = new HttpPost(UM_URL + "?sign=" + sign);
            post.setHeader("User-Agent", "Mozilla/5.0");
            post.setEntity(new StringEntity(body, "UTF-8"));
            HttpResponse response = CLIENT.execute(post);
            status = response.getStatusLine().getStatusCode();
            reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String line;
            while ((line = reader.readLine()) != null)
                builder.append(line);
            reader.close();
        } catch (Exception e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {

                }
            }
        }
        return new UMResultModel(status, builder.toString());
    }

    @Data
    public static class UMResultModel {//数据返回类
        int statusCode;
        String content;

        public UMResultModel(int statusCode, String content) {
            this.statusCode = statusCode;
            this.content = content;
        }
    }
}


@Data
class PushModel { //主结构
    private boolean production_mode;//生产、测试
    private String appkey;
    private String timestamp;
    private String type;
    private String device_tokens;
    private String alias_type;
    private String alias;
    private String file_id;
    private Object filter;
    private AndroidPayload payload;
    private Object policy;
    private String description;

    PushModel() {
        this.production_mode = true;
        this.appkey = Constants.UMENG_ANDROID_APP_KEY;
        this.timestamp = String.valueOf(System.currentTimeMillis() / 1000);
    }
}

@Data
class AndroidPayload { //android 主结构
    String display_type;
    AndroidPayLoadBody body;
    HashMap<String, Object> extra;

    public AndroidPayload() {
    }

    AndroidPayload(String display_type, AndroidPayLoadBody body, HashMap<String, Object> extra) {
        this.display_type = display_type;
        this.body = body;
        this.extra = extra;
    }
}

@Data
class AndroidPayLoadBody { //android 主数据
    String ticker;
    String title;
    String text;
    String icon;
    String largeIcon;
    String img;
    String sound;
    String builder_id;
    String play_vibrate;
    String play_lights;
    String play_sound;
    String after_open;
    String url;
    String activity;
    String custom;
}