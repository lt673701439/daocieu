package com.liketry.interaction.benison.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.liketry.interaction.benison.dao.PromotionDetailMapper;
import com.liketry.interaction.benison.model.PromotionDetail;
import com.liketry.interaction.benison.vo.api.PromotionCommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liketry.interaction.benison.dao.PromotionMapper;
import com.liketry.interaction.benison.model.Promotion;
import com.liketry.interaction.benison.service.PromotionService;

/**
 * 促销活动实现类
 *
 * @author pengyy
 */
@Service
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    PromotionMapper promotionMapper;

    @Autowired
    PromotionDetailMapper promotionDetailMapper;

    /**
     * 获取促销活动列表
     */
    public List<Promotion> findPromotionList() {
        return promotionMapper.findAllPromotion();
    }

    /**
     * 返回单一活动商品列表
     *
     * @param promotionId 活动id
     * @return
     */
    @Override
    public List<PromotionCommodityVo> findPromotionDetailList(String promotionId) {
        return promotionDetailMapper.findPromotionDetailList(promotionId);
    }


//    @Override
//    public List<PromotionCommodityVo> findPromotionDetailList(String promotionId, Date reserveDate) {
//        return promotionDetailMapper.findPromotionDetailList(null, promotionId, reserveDate);
//    }

    @Override
    public int findPartakeNumber(String promotionId) {
        return promotionDetailMapper.findPartakeNumber(promotionId);
    }

    @Override
    public List<PromotionDetail> getPromotionDetailList(Map<String, Object> map) {
        return promotionDetailMapper.getPromotionDetailList(map);
    }
}
