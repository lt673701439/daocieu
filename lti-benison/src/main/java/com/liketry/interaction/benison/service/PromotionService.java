package com.liketry.interaction.benison.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liketry.interaction.benison.model.Promotion;
import com.liketry.interaction.benison.model.PromotionDetail;
import com.liketry.interaction.benison.vo.api.PromotionCommodityVo;
import org.apache.ibatis.annotations.Param;

/**
 * 促销活动service
 *
 * @author pengyy
 */
public interface PromotionService {

    List<Promotion> findPromotionList();

    List<PromotionCommodityVo> findPromotionDetailList(String promotionId);

    int findPartakeNumber(@Param("promotionId") String promotionId);

    List<PromotionDetail> getPromotionDetailList(Map<String, Object> map);

    //获取首单9.9元活动的下单数
    int findFirstPromotionNumber();
}