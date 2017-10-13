package com.liketry.interaction.benison.util;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.vo.api.CommodityVo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * author Simon
 * create 2017/8/3
 * 商品工具
 */
public class CommodityUtils {
    private final static int RESERVE_DAY = Integer.valueOf(PropertiesUtils.getInstance().getValue("commodity_day_range_after"));

    //更改销售情况
    public static List<CommodityVo> changeSaleList(List<CommodityVo> datas) {
        if (datas == null || datas.isEmpty())
            return datas;
        for (CommodityVo vo : datas) {
            vo.setTimeStart(vo.getStartTime());
            vo.setTimeEnd(vo.getEndTime());
            int sales = Integer.valueOf(vo.getSales());
            int stock = Integer.valueOf(vo.getStock());
            if (stock == 0)
                continue;
            int total = sales + stock;
            float scale = sales * 1.0f / total;
            if (scale <= 0.3) {
                sales = (int) (total * 0.3);
            } else if (scale <= 0.4) {
                sales = (int) (total * 1.3f);
            } else if (scale <= 0.6) {
                sales = (int) (total * 1.2f);
            } else if (scale <= 0.75) {
                sales = (int) (total * 1.1f);
            } else if (scale < 1) {
                sales = (int) (total * 0.9f);
            }
            if (sales > total)
                sales = total;
            stock = total - sales;
            vo.setSales(String.valueOf(sales));
            vo.setStock(String.valueOf(stock));
        }
        return datas;
    }

    //更改销售情况
    public static CommodityVo changeSale(CommodityVo data) {
        if (data == null)
            return null;
        data.setTimeStart(data.getStartTime());
        data.setTimeEnd(data.getEndTime());
        int sales = Integer.valueOf(data.getSales());
        int stock = Integer.valueOf(data.getStock());
        if (stock == 0)
            return data;
        int total = sales + stock;
        float scale = sales * 1.0f / total;
        if (scale <= 0.3) {
            sales = (int) (total * 0.3);
        } else if (scale <= 0.4) {
            sales = (int) (total * 1.3f);
        } else if (scale <= 0.6) {
            sales = (int) (total * 1.2f);
        } else if (scale <= 0.75) {
            sales = (int) (total * 1.1f);
        } else if (scale < 1) {
            sales = (int) (total * 0.9f);
        }
        if (sales > total)
            sales = total;
        stock = total - sales;
        data.setSales(String.valueOf(sales));
        data.setStock(String.valueOf(stock));
        return data;
    }

    //是否是七天内
    public static boolean differentDay(String date) {
        SimpleDateFormat df = new SimpleDateFormat(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, RESERVE_DAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        try {
            return df.parse(date).getTime() - calendar.getTimeInMillis() < 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}