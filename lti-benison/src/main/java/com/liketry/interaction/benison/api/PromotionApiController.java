package com.liketry.interaction.benison.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.liketry.interaction.benison.dao.StockMapper;
import com.liketry.interaction.benison.model.Stock;
import com.liketry.interaction.benison.service.ScreenService;
import com.liketry.interaction.benison.util.PropertiesUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.Promotion;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.CommodityService;
import com.liketry.interaction.benison.service.PromotionService;
import com.liketry.interaction.benison.util.CommodityUtils;
import com.liketry.interaction.benison.vo.api.CommodityVo;
import com.liketry.interaction.benison.vo.api.PromotionCommodityVo;

/**
 * 促销活动接口
 *
 * @author pengyy
 */
@RestController
@RequestMapping("promotion_api")
public class PromotionApiController {
    private final int RESERVE_DAY = Integer.valueOf(PropertiesUtils.getInstance().getValue("commodity_day_range_after"));
    private final int ERROR_DATE = 1002;//日期格式错误
    @Autowired
    PromotionService promotionService;

    @Autowired
    CommodityService commodityService;
    @Autowired
    private ScreenService screenService;
    @Autowired
    StockMapper stockMapper;

    @RequestMapping("getPromotionList")
    Result<List<Promotion>> getPromotionList() {
        return new Result<>(SystemConstants.RESULT_SUCCESS, promotionService.findPromotionList());
    }

    /**
     * 根据活动id 获取活动列表
     *
     * @param promotionId 活动id
     * @return 商品参数
     */
    @RequestMapping("getPromotionDetail")
    Result<List<PromotionCommodityVo>> getPromotionDetail(@RequestParam String promotionId) throws Exception {
        List<PromotionCommodityVo> commodities = promotionService.findPromotionDetailList(promotionId);
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        final DateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < commodities.size(); i++) {//遍历所有商品
            PromotionCommodityVo commodity = commodities.get(i);
            calendar.setTime(new Date());
            for (int j = 0; j < RESERVE_DAY; j++) {//商品七天内是否有可下单
                if (CommodityApiController.isContinue(commodity.getScreenId(), calendar.getTime())) {//如果是黄山景区，并且选择时间是今天、明天、后天，一律无货
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    continue;
                }
                String reserveDate = df.format(calendar.getTime());
                int limit = commodity.getCommodityNum();
                int saleSize = commodityService.getLimitSize(promotionId, commodity.getCommodityId(), reserveDate);
                Stock stock = stockMapper.findStockByCommodityId(commodity.getCommodityId(), reserveDate);
                //可下单为：库存大于0，活动数量小于设定数量，播放日期小于禁止下单日期
                if (saleSize < limit && stock != null && stock.getStock() > 0) {
                    if (j > 0 && "1".equals(stock.getStockStatus()) && "0".equals(stock.getScheduleStatus())) {//不是同一天,并且库存大于0
                        commodity.setPromotionLimit(true);
                        commodity.setStock(String.valueOf(stock.getStock()));
                        commodity.setValidDate(reserveDate);
                        commodity.setDisplay("1");
                        commodities.set(i, commodity);
                        break;
                    } else {
                        Calendar cur = Calendar.getInstance();
                        Calendar voc = Calendar.getInstance();
                        voc.setTime(timeFormat.parse(commodity.getShelfTime()));
                        voc.set(cur.get(Calendar.YEAR), cur.get(Calendar.MONTH), cur.get(Calendar.DAY_OF_MONTH));
                        if (voc.getTimeInMillis() > cur.getTimeInMillis() && stock.getStock() > 0 && "1".equals(stock.getStockStatus()) && "0".equals(stock.getScheduleStatus())) {//下架时间大于当前时间
                            commodity.setPromotionLimit(true);
                            commodity.setStock(String.valueOf(stock.getStock()));
                            commodity.setValidDate(reserveDate);
                            commodity.setDisplay("1");
                            commodities.set(i, commodity);
                            break;
                        }
                    }
                }
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        return new Result<>(SystemConstants.RESULT_SUCCESS, commodities);
    }

    /**
     * 根据活动id 获取参与数量
     *
     * @return
     */
    @RequestMapping("getPartakeNumber")
    Result<Integer> getPartakeNumber(@RequestParam String promotionId) {
        int size;
        if ("2a4de77a6a084ebca52b6d784b7dd91e".equals(promotionId)) {//如果进入到首单9.9活动
            size = promotionService.findFirstPromotionNumber();
        } else {
            size = promotionService.findPartakeNumber(promotionId);
            size += new Random().nextInt(11);
        }
        return new Result<>(SystemConstants.RESULT_SUCCESS, size);
    }

    /**
     * 根据商品id获取最近的商品
     *
     * @return 商品信息集合
     */
    @RequestMapping("getNearTimeCommodity")
    Result<CommodityVo> getPromotionNearCommodity(@RequestParam String promotionId, @RequestParam String commodityId) throws Exception {
        List<CommodityVo> commoditys = commodityService.findPromotionNearCommodity(promotionId, commodityId);
        if (commoditys == null || commoditys.isEmpty())
            return new Result<>(SystemConstants.RESULT_SUCCESS, null);
        CommodityVo result = null;
        for (CommodityVo commodity : commoditys) {
            if (!CommodityUtils.differentDay(commodity.getValidDate())) {//七天之外的订单没有
                continue;
            }
            if (CommodityApiController.isContinue(commodity.getScreenId(), commodity.getValidDate())) {//如果是黄山景区，并且选择时间是今天、明天、后天，一律无货
                continue;
            }
            if (Integer.valueOf(commodity.getStock()) > 0 && "1".equals(commodity.getCommodityStatus()) && "1".equals(commodity.getStockStatus()) && "0".equals(commodity.getScheduleStatus())) {
                int num = commodity.getCommodityNum();
                if (num > 0) {
                    int size = commodityService.getLimitSize(promotionId, commodityId, commodity.getValidDate());
                    if (size < num) {
                        result = commodity;
                        break;
                    }
                } else {
                    result = commodity;
                    break;
                }
            }
        }
        result = CommodityUtils.changeSale(result);
        return new Result<>(SystemConstants.RESULT_SUCCESS, result);
    }

    @RequestMapping("getPromotionSingleCommodity")
    Result<List<CommodityVo>> getPromotionSingleCommodity(@RequestParam String promotionId, @RequestParam String commodityId, @RequestParam String reserveDate) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).parse(reserveDate);
        } catch (Exception e) {
            return new Result<>(ERROR_DATE, "日期格式错误");
        }
        List<CommodityVo> result = new ArrayList<>();
        List<CommodityVo> commodityList = commodityService.findPromotionSingleCommodity(promotionId, commodityId, date);
        if (commodityList != null && !commodityList.isEmpty())
            result.add(commodityList.get(0));
        result = CommodityUtils.changeSaleList(result);
        return new Result<>(SystemConstants.RESULT_SUCCESS, result);
    }
}