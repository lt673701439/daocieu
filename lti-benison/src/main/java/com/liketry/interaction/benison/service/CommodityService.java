package com.liketry.interaction.benison.service;

import java.util.Date;
import java.util.List;

import com.liketry.interaction.benison.model.CommodityCost;
import com.liketry.interaction.benison.vo.api.CommodityVo;

/**
 * 商品服务类
 *
 * @author Simon
 */
public interface CommodityService {

    List<CommodityVo> findByScreenId(String screenId, Date reserveDate);

    List<CommodityVo> findByNearCommodity(String screenId, String date);

    List<CommodityCost> getCommodityCostList(String commodityId);

    List<CommodityVo> findCustomCommodity(int type, String screenId, Date reserveDate);

    List<CommodityVo> findPromotionNearCommodity(String promotionId, String commodityId);

    List<CommodityVo> findPromotionSingleCommodity(String promotionId, String commodityId, Date reserveDate);

    List<CommodityVo> findByNearCustomCommodity(int type, String screenId, String reserveDate);

    boolean[] findLatestCommodityStock(String screenId,int day);

    boolean[] findPromotionLatestCommodityStock(String promotionId, String commodityId,int day);

    int getLimitSize(String promotionId, String commodityId, String reserveDate );
}
