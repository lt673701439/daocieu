package com.liketry.interaction.benison.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * author Simon
 * create 2017/7/31
 * 区域工具类
 */
public class AreaUtil {

    /**
     * 根据输入的经纬度和索要的距离半径，返回两个坐标构成一个两倍raidus为半径的矩形
     *
     * @param longitude 经度
     * @param latitude  维度
     * @param raidus    距离半径
     * @return 一个矩形经纬度。
     */
    public static double[] getRectangleAround(double longitude, double latitude, int raidus) {
        final String UNIT_LONGITUDE = "85339.06";//经度每隔1度，相差米数
        final String UNIT_LATITUDE = "111319.49";//维度每隔1度,相差米数
        BigDecimal longitudeBig = new BigDecimal(String.valueOf(raidus)).divide(new BigDecimal(UNIT_LONGITUDE), 6, RoundingMode.HALF_UP);
        double leftLongitude = new BigDecimal(String.valueOf(longitude)).subtract(longitudeBig).doubleValue();
        double rightLongitude = new BigDecimal(String.valueOf(longitude)).add(longitudeBig).doubleValue();
        BigDecimal latitudeBig = new BigDecimal(String.valueOf(raidus)).divide(new BigDecimal(UNIT_LATITUDE), 6, RoundingMode.HALF_UP);
        double topLatitude = new BigDecimal(String.valueOf(latitude)).subtract(latitudeBig).doubleValue();
        double downLatitude = new BigDecimal(String.valueOf(latitude)).add(latitudeBig).doubleValue();
        return new double[]{leftLongitude, topLatitude, rightLongitude, downLatitude};
    }

    //获取两个经纬度之间的距离
    public static double getDistance(double long1, double lat1, double long2, double lat2) {
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        double sa2 = Math.sin((lat1 - lat2) / 2.0);
        double sb2 = Math.sin(((long1 - long2) * Math.PI / 180.0) / 2.0);
        return 12756274 * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));

    }
}
