package com.liketry.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author Simon
 * create 2017/9/13
 * 获取二维码
 */
@Component
public class QRCodeUtils {
    private static String BENISON_URL;

    @Value("${current-project.benison-url}")
    public void setBenisonUrl(String benisonUrl) {
        BENISON_URL = benisonUrl;
    }

    //连接大屏系统获取二维码
    public static String getBenisonQRCode(String merchantId) {
        final String CODE_URL = BENISON_URL + "?merchantId=" + merchantId;
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputReader = null;
        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            //获取token
            //获取二维码
            connection = (HttpURLConnection) new URL(CODE_URL).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("contentType", "application/json");
            connection.connect();
            inputStream = connection.getInputStream();
            inputReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputReader);
            while ((line = bufferedReader.readLine()) != null)
                builder.append(line);
            JSONObject jsonObject = JSONObject.parseObject(builder.toString());
            final String code = jsonObject.get("code").toString();
            if ("success".equals(code)) {
                return jsonObject.get("data").toString();
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputReader != null) {
                try {
                    inputReader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
