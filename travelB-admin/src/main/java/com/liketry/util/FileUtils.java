package com.liketry.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author Simon
 * create 2017/9/12
 * 文件工具类
 */
public class FileUtils {
    /**
     * 获取通用路径
     *
     * @param folderName 文件存储路径
     * @param suffix     文件后缀
     * @return folderName/日期(yyyyMMdd)/uuid.后缀
     */
    public static String getPathAndCreate(String folderName, String suffix) {
        final String path_today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String ROOT_LOCAL = PropertiesUtils.getInstance().getValue("default_upload_filepath");
        File folder = new File(ROOT_LOCAL + folderName + path_today);
        if (!folder.exists() || folder.isFile())
            folder.mkdirs();
        return folderName + path_today + "/" + CommonUtils.getId() + "." + suffix;
    }

    public static boolean saveFile(String path, MultipartFile multipartFile) {
        String ROOT_LOCAL = PropertiesUtils.getInstance().getValue("default_upload_filepath");
        File file = new File(ROOT_LOCAL + path);
        if (file.exists())
            throw new RuntimeException(path + " exists");
        try {
            if (!file.createNewFile())
                throw new RuntimeException(path + " create failed");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        FileOutputStream outputStream = null;
        BufferedOutputStream bufferedOutputStream = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(outputStream);
            inputStream = multipartFile.getInputStream();
            bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] bytes = new byte[4096];
            int length;
            while ((length = bufferedInputStream.read(bytes)) != -1)
                bufferedOutputStream.write(bytes, 0, length);
            bufferedOutputStream.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (Exception e) {
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                    outputStream = null;
                } catch (Exception e) {
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                    bufferedInputStream = null;
                } catch (Exception e) {
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                    inputStream = null;
                } catch (Exception e) {
                }
            }
        }
    }
}
