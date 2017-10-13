package com.liketry.interaction.benison.util;

import com.alibaba.fastjson.JSON;
import com.liketry.interaction.benison.constants.SystemConstants;
import org.slf4j.LoggerFactory;

/**
 * author Simon
 * create 2017/8/18
 * 日志工具类
 */
public class LogUtils {

    public static String getLog(String type, Object input, Object output) {
        try {
            StringBuilder builder = new StringBuilder("tag=");
            builder.append(SystemConstants.LOG_TAG);
            builder.append(" type=");
            builder.append(type);
            if (input != null) {
                builder.append(" input=");
                builder.append(JSON.toJSONString(input));
            } else {
                builder.append(" input= NULL");
            }
            if (output != null) {
                builder.append(" output= ");
                builder.append(JSON.toJSONString(output));
            } else {
                builder.append(" output= NULL");
            }
            return builder.toString();
        } catch (Exception e) {
            LoggerFactory.getLogger(LogUtils.class).info("LogUtils throws exception!" + e.getMessage());
            return "LogUtils throws exception!";
        }
    }

}
