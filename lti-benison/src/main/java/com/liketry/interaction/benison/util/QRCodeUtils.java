package com.liketry.interaction.benison.util;

import com.alibaba.fastjson.JSONObject;
import com.liketry.interaction.benison.constants.SystemConstants;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author Simon
 * create 2017/9/13
 * 获取二维码
 */
public class QRCodeUtils {
    //获取小程序码路径
    public static String getMINACode(String merchantId) {
        File file = new File(SystemConstants.QR_CODE_PATH + merchantId + ".jpg");
        if (file.exists() && file.isFile())
            return SystemConstants.URL_QR_CODE + merchantId + ".jpg";
        else
            return createMINACode(merchantId);
    }

    //制作小程序码
    public static String createMINACode(String merchantId) {
        final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + SystemConstants.WX_APPID + "&secret=" + SystemConstants.WX_SECRET;
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputReader = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        StringBuilder builder = new StringBuilder();
        String line = null;
        int length = -1;
        try {
            //获取token
            connection = (HttpURLConnection) new URL(TOKEN_URL).openConnection();
            connection.connect();
            inputStream = connection.getInputStream();
            inputReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputReader);
            while ((line = bufferedReader.readLine()) != null)
                builder.append(line);
            final String token = JSONObject.parseObject(builder.toString()).get("access_token").toString();
            //获取二维码
            String QR_CODE_PATH = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token;
            JSONObject obj = new JSONObject();
            obj.put("scene", merchantId);
            byte[] data = (obj.toString()).getBytes();
            connection = (HttpURLConnection) new URL(QR_CODE_PATH).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("contentType", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            connection.connect();
            outputStream = connection.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            inputStream = connection.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            File file = new File(SystemConstants.QR_CODE_PATH);
            boolean createStatus = true;
            if (!file.exists() || file.isFile())
                createStatus = file.mkdirs();
            if (!createStatus)
                return null;
            file = new File(SystemConstants.QR_CODE_PATH + merchantId + ".jpg");
            if (file.exists() && file.isFile())
                createStatus = file.delete();
            if (!createStatus)
                return null;
            createStatus = file.createNewFile();
            if (!createStatus)
                return null;
            outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[4096];
            while ((length = inputStream.read(bytes)) != -1)
                outputStream.write(bytes, 0, length);
            outputStream.flush();
            return SystemConstants.URL_QR_CODE + merchantId + ".jpg";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
                    connection = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 路径
     *
     * @return
     */
    public static String createQRCode(String merchantId) {
        final String TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + SystemConstants.WX_APPID + "&secret=" + SystemConstants.WX_SECRET;
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        InputStreamReader inputReader = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        BufferedInputStream bufferedInputStream = null;
        StringBuilder builder = new StringBuilder();
        String line = null;
        int length = -1;
        try {
            //获取token
            connection = (HttpURLConnection) new URL(TOKEN_URL).openConnection();
            connection.connect();
            inputStream = connection.getInputStream();
            inputReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputReader);
            while ((line = bufferedReader.readLine()) != null)
                builder.append(line);
            final String token = JSONObject.parseObject(builder.toString()).get("access_token").toString();
            //获取二维码
            String QR_CODE_PATH = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + token;
            JSONObject obj = new JSONObject();
            obj.put("path", "pages/index/index?scene=" + merchantId);
            obj.put("width", 430);
            byte[] data = (obj.toString()).getBytes();
            connection = (HttpURLConnection) new URL(QR_CODE_PATH).openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("connection", "keep-alive");
            connection.setRequestProperty("Charsert", "UTF-8");
            connection.setRequestProperty("contentType", "application/json");
            connection.setRequestProperty("Content-Length", String.valueOf(data.length));
            connection.connect();
            outputStream = connection.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            inputStream = connection.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            File file = new File(SystemConstants.QR_CODE_PATH);
            boolean createStatus = true;
            if (!file.exists() || file.isFile())
                createStatus = file.mkdirs();
            if (!createStatus)
                return null;
            file = new File(SystemConstants.QR_CODE_PATH + merchantId + ".jpg");
            if (!file.exists())
                createStatus = file.createNewFile();
            if (!createStatus)
                return null;
            outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[4096];
            while ((length = inputStream.read(bytes)) != -1)
                outputStream.write(bytes, 0, length);
            outputStream.flush();
            return SystemConstants.URL_QR_CODE + merchantId + ".jpg";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
                    connection = null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
