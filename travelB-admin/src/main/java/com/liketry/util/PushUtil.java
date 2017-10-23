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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
            case Constants.DEVICE_IOS:
                return getIOSAliasData(title, content, id, actionType);
            case Constants.DEVICE_ANDROID:
                return getAndroidAliasData(title, content, id, actionType);
            default:
                return null;
        }
    }


    //获取根据ios别名生成数据
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
        data.put("description", "single");
        return JSON.toJSONString(data);
    }

    //获取根据android别名生成数据
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

    //获取ios广播数据
    public static String getIOSBroadcastData(String title, boolean immediately, Date pushTime, String pushContent, int actionType, Map<String, Object> extras) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("appkey", Constants.UMENG_IOS_APP_KEY);
        data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        data.put("type", TYPE_BROADCAST);
        HashMap<String, String> alert = new HashMap<>();
        alert.put("title", title);
        alert.put("body", pushContent);
        HashMap<String, Object> aps = new HashMap<>();
        aps.put("alert", alert);
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("aps", aps);
        payload.put("action_type", actionType);
        if (extras != null && !extras.isEmpty()) {
            for (Map.Entry<String, Object> entry : extras.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                payload.put(key, value);
            }
        }
        data.put("payload", payload);
        if (!immediately) {
            HashMap<String, Object> policy = new HashMap<>();
            policy.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pushTime));
            data.put("policy", policy);
        }
        data.put("production_mode", Constants.PRODUCTION_MODE);
        data.put("description", "broadcast");
        return JSON.toJSONString(data);
    }

    //获取android广播数据
    public static String getAndroidBroadcastData(String title, boolean immediately, Date pushTime, String pushContent, int actionType, Map<String, Object> extras) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("appkey", Constants.UMENG_ANDROID_APP_KEY);
        data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        data.put("type", TYPE_BROADCAST);

        HashMap<String, Object> body = new HashMap<>();
        body.put("custom", "");
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("display_type", "message");
        payload.put("body", body);
        data.put("payload", payload);
        HashMap<String, Object> extra = new HashMap<>();
        extra.put("action_type", actionType);
        if (extras != null && !extras.isEmpty()) {
            for (Map.Entry<String, Object> entry : extras.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                extra.put(key, value);
            }
        }
        data.put("extra", extra);
        if (!immediately) {
            HashMap<String, Object> policy = new HashMap<>();
            policy.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pushTime));
            data.put("policy", policy);
        }
        data.put("production_mode", Constants.PRODUCTION_MODE);
        return JSON.toJSONString(data);
    }

    //获取ios组播数据
    public static String getIOSGroupCastData(String title, boolean immediately, Date pushTime, String pushContent, int actionType, String merchants, Map<String, Object> extras) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("appkey", Constants.UMENG_IOS_APP_KEY);
        data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        data.put("type", TYPE_CUSTOMIZEDCAST);
        data.put("alias_type", ALIAS_TYPE);
        data.put("alias", merchants);
        HashMap<String, String> alert = new HashMap<>();
        alert.put("title", title);
        alert.put("body", pushContent);
        HashMap<String, Object> aps = new HashMap<>();
        aps.put("alert", alert);
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("aps", aps);
        payload.put("action_type", actionType);
        if (extras != null && !extras.isEmpty()) {
            for (Map.Entry<String, Object> entry : extras.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                payload.put(key, value);
            }
        }
        data.put("payload", payload);
        if (!immediately) {
            HashMap<String, Object> policy = new HashMap<>();
            policy.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pushTime));
            data.put("policy", policy);
        }
        data.put("production_mode", Constants.PRODUCTION_MODE);
        return JSON.toJSONString(data);
    }

    //获取android组播数据
    public static String getAndroidGroupCastData(String title, boolean immediately, Date pushTime, String pushContent, int actionType, String merchants, Map<String, Object> extras) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("appkey", Constants.UMENG_ANDROID_APP_KEY);
        data.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        data.put("type", TYPE_CUSTOMIZEDCAST);
        data.put("alias_type", ALIAS_TYPE);
        data.put("alias", merchants);
        HashMap<String, Object> body = new HashMap<>();
        body.put("custom", "");
        HashMap<String, Object> payload = new HashMap<>();
        payload.put("display_type", "message");
        payload.put("body", body);
        data.put("payload", payload);
        HashMap<String, Object> extra = new HashMap<>();
        extra.put("action_type", actionType);
        if (extras != null && !extras.isEmpty()) {
            for (Map.Entry<String, Object> entry : extras.entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue();
                extra.put(key, value);
            }
        }
        data.put("extra", extra);
        if (!immediately) {
            HashMap<String, Object> policy = new HashMap<>();
            policy.put("start_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(pushTime));
            data.put("policy", policy);
        }
        data.put("production_mode", Constants.PRODUCTION_MODE);
        return JSON.toJSONString(data);
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