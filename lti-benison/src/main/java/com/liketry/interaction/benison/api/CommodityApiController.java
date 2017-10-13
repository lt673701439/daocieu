package com.liketry.interaction.benison.api;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.dao.SpotMapper;
import com.liketry.interaction.benison.model.CommodityCost;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.CommodityService;
import com.liketry.interaction.benison.service.ScreenService;
import com.liketry.interaction.benison.service.SpotService;
import com.liketry.interaction.benison.util.CommodityUtils;
import com.liketry.interaction.benison.util.PropertiesUtils;
import com.liketry.interaction.benison.util.StringUtils;
import com.liketry.interaction.benison.vo.api.CommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 商品api控制器
 *
 * @author Simon
 */
@RestController
@RequestMapping("commodity_api")
public class CommodityApiController {
    private final int ERROR_DATE = 1002;//日期格式错误
    private final int ERROR_DAY = 1003;//天数格式错误
    private final int RESERVE_DAY = Integer.valueOf(PropertiesUtils.getInstance().getValue("commodity_day_range_after"));

    private final CommodityService commodityService;
    @Autowired
    private ScreenService screenService;

    @Autowired
    public CommodityApiController(CommodityService commodityService) {
        this.commodityService = commodityService;
    }

    /**
     * 根据屏幕id获取商品信息
     *
     * @param screenId    屏幕id
     * @param reserveDate 预定日期
     * @return 商品信息集合
     */
    @RequestMapping("getScreenCommodity")
    Result<List<CommodityVo>> getCommodity(@RequestParam String screenId, @RequestParam String reserveDate) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(reserveDate);
        } catch (Exception e) {
            return new Result<>(ERROR_DATE, "日期格式错误");
        }
        List<CommodityVo> datas = commodityService.findByScreenId(screenId, date);
        datas = CommodityUtils.changeSaleList(datas);
        return new Result<>(SystemConstants.RESULT_SUCCESS, datas);
    }

    /**
     * 根据屏幕id获取最近的商品
     *
     * @param screenId 屏幕id
     * @return 商品信息集合
     */
    @RequestMapping("getNearTimeCommodity")
    Result<CommodityVo> getNearCommodity(@RequestParam String screenId) throws Exception {
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        String currentDay = format.format(calendar.getTime());
        String selectDay;
        CommodityVo result = null;
        boolean isWhile = true;
        int day = 0;
        while (isWhile && day < RESERVE_DAY) {
            day++;
            selectDay = format.format(calendar.getTime());
            if (isContinue(screenId, calendar.getTime())) {//如果是黄山景区，并且选择时间是今天、明天、后天，一律无货
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                continue;
            }
            List<CommodityVo> datas = commodityService.findByNearCommodity(screenId, selectDay);
            if (datas == null || datas.isEmpty()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                continue;
            }
            for (CommodityVo vo : datas) {
                //字段赋值
                vo.setTimeStart(vo.getStartTime());
                vo.setTimeEnd(vo.getEndTime());
                if (!selectDay.equals(currentDay) && Integer.valueOf(vo.getStock()) > 0 && "1".equals(vo.getCommodityStatus()) && "1".equals(vo.getStockStatus()) && "0".equals(vo.getScheduleStatus())) {//不是同一天,并且库存大于0
                    result = vo;
                    result.setValidDate(selectDay);
                    isWhile = false;
                    break;
                } else {//是同一天
                    Calendar cur = Calendar.getInstance();
                    Calendar voc = Calendar.getInstance();
                    voc.setTime(timeFormat.parse(vo.getShelfTime()));
                    voc.set(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH), cur.get(Calendar.DAY_OF_MONTH));
                    if (voc.getTimeInMillis() > cur.getTimeInMillis() && Integer.valueOf(vo.getStock()) > 0 && "1".equals(vo.getCommodityStatus()) && "1".equals(vo.getStockStatus()) && "0".equals(vo.getScheduleStatus())) {//下架时间大于当前时间
                        result = vo;
                        result.setValidDate(selectDay);
                        isWhile = false;
                        break;
                    }
                }
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        result = CommodityUtils.changeSale(result);
        return new Result<>(SystemConstants.RESULT_SUCCESS, result);
    }

    /**
     * 根据屏幕id获取商品信息
     *
     * @return 商品信息集合
     */
    @RequestMapping("getCommodityCostList")
    Result<List<CommodityCost>> getCommodityList(@RequestParam String commodityId) {
        List<CommodityCost> list = null;
        if (StringUtils.isEmpty(commodityId)) {
            return new Result<>(SystemConstants.RESULT_SUCCESS, "参数不能为null", list);
        }
        list = commodityService.getCommodityCostList(commodityId);
        return new Result<>(SystemConstants.RESULT_SUCCESS, "调用成功", list);
    }

    //获取定制商品
    @RequestMapping("getCustomCommodity")
    Result<List<CommodityVo>> getCustomCommodity(@RequestParam int type, @RequestParam String screenId, @RequestParam String reserveDate) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(reserveDate);
        } catch (Exception e) {
            return new Result<>(ERROR_DATE, "日期格式错误");
        }
        List<CommodityVo> customCommodity = commodityService.findCustomCommodity(type, screenId, date);
        customCommodity = CommodityUtils.changeSaleList(customCommodity);
        return new Result<>(SystemConstants.RESULT_SUCCESS, customCommodity);
    }


    /**
     * 根据屏幕id获取最近的(定制)商品
     *
     * @param screenId 屏幕id
     * @return 商品信息集合
     */
    @RequestMapping("getNearTimeCustomCommodity")
    Result<CommodityVo> getNearCustomCommodity(@RequestParam int type, @RequestParam String screenId) throws Exception {
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        String currentDay = format.format(calendar.getTime());
        String selectDay;
        CommodityVo result = null;
        boolean isWhile = true;
        int day = 0;
        while (isWhile && day < RESERVE_DAY) {
            day++;
            selectDay = format.format(calendar.getTime());
            if (isContinue(screenId, calendar.getTime())) {//如果是黄山景区，并且选择时间是今天、明天、后天，一律无货
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                continue;
            }
            List<CommodityVo> datas = commodityService.findByNearCustomCommodity(type, screenId, selectDay);
            if (datas == null || datas.isEmpty()) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                continue;
            }
            for (CommodityVo vo : datas) {
                //字段赋值
                vo.setTimeStart(vo.getStartTime());
                vo.setTimeEnd(vo.getEndTime());
                if (!selectDay.equals(currentDay) && Integer.valueOf(vo.getStock()) > 0 && "1".equals(vo.getCommodityStatus()) && "1".equals(vo.getStockStatus()) && "0".equals(vo.getScheduleStatus())) {//不是同一天,并且库存大于0
                    result = vo;
                    result.setValidDate(selectDay);
                    isWhile = false;
                    break;
                } else {//是同一天
                    Calendar cur = Calendar.getInstance();
                    Calendar voc = Calendar.getInstance();
                    voc.setTime(timeFormat.parse(vo.getShelfTime()));
                    voc.set(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH), cur.get(Calendar.DAY_OF_MONTH));
                    if (voc.getTimeInMillis() > cur.getTimeInMillis() && Integer.valueOf(vo.getStock()) > 0 && "1".equals(vo.getCommodityStatus()) && "1".equals(vo.getStockStatus()) && "0".equals(vo.getScheduleStatus())) {//下架时间大于当前时间
                        result = vo;
                        result.setValidDate(selectDay);
                        isWhile = false;
                        break;
                    }
                }
            }
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return new Result<>(SystemConstants.RESULT_SUCCESS, result);
    }

    /**
     * 获取最近几天是否有可下单的商品
     *
     * @param day 查询最近几天
     * @return 包含当天的连续天数状态
     */
    @RequestMapping("getLatestCommodityStock")
    public Result<boolean[]> getLatestCommodityStock(@RequestParam String screenId, @RequestParam int day) {
        if (day < 1 || day > 365)
            return new Result<>(ERROR_DAY, "天数格式错误");
        boolean[] status = commodityService.findLatestCommodityStock(screenId, day);
        return new Result<>(SystemConstants.RESULT_SUCCESS, status);
    }

    /**
     * 获取最近几天是否有可下单的商品(活动)
     *
     * @param promotionId 活动id
     * @param commodityId 商品id
     * @param day         天数
     * @return 包含当天的连续天数状态
     */
    @RequestMapping("getPromotionLatestCommodityStock")
    public Result<boolean[]> getPromotionLatestCommodityStock(@RequestParam String promotionId, @RequestParam String commodityId, @RequestParam int day) {
        if (day < 1 || day > 365)
            return new Result<>(ERROR_DAY, "天数格式错误");
        boolean[] status = commodityService.findPromotionLatestCommodityStock(promotionId, commodityId, day);
        return new Result<>(SystemConstants.RESULT_SUCCESS, status);
    }


    private static final String HUANGSHAN_SCREEN_ID = PropertiesUtils.getInstance().getValue("HuangShan_screenId");
    private static final int RESERVE_DAY_HUANGSHAN = Integer.valueOf(PropertiesUtils.getInstance().getValue("HuangShang_day_range_after"));

    //针对黄山景区做的(三天内不让下单)
    public static boolean isContinue(String screenId, Date selectDate) {

        if (HUANGSHAN_SCREEN_ID.equals(screenId)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, RESERVE_DAY_HUANGSHAN);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long standardTime = calendar.getTimeInMillis();
            calendar.setTime(selectDate);
            long selectTime = calendar.getTimeInMillis();
            return selectTime < standardTime;
        } else {
            return false;
        }
    }

    //针对黄山景区做的(三天内不让下单)
    public static boolean isContinue(String screenId, String selectDate) throws Exception {
        if (HUANGSHAN_SCREEN_ID.equals(screenId)) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, RESERVE_DAY_HUANGSHAN);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            long standardTime = calendar.getTimeInMillis();
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(selectDate));
            long selectTime = calendar.getTimeInMillis();
            return selectTime < standardTime;
        } else {
            return false;
        }
    }
}