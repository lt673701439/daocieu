package com.liketry.interaction.benison.service.impl;

import com.github.pagehelper.PageHelper;
import com.liketry.interaction.benison.dao.CommodityCostMapper;
import com.liketry.interaction.benison.dao.CommodityMapper;
import com.liketry.interaction.benison.dao.PromotionDetailMapper;
import com.liketry.interaction.benison.model.Commodity;
import com.liketry.interaction.benison.model.CommodityCost;
import com.liketry.interaction.benison.service.CommodityService;
import com.liketry.interaction.benison.vo.api.CommodityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 商品service实现类
 *
 * @author Simon
 */
@Service
public class CommodityServiceImpl implements CommodityService {
    @Autowired
    CommodityMapper commodityMapper;

    @Autowired
    CommodityCostMapper commodityCostMapper;

    @Autowired
    PromotionDetailMapper promotionDetailMapper;

    /**
     * 根据屏幕id和时间查询当天商品
     *
     * @param screenId
     * @param reserveDate
     * @return
     */
    @Override
    public List<CommodityVo> findByScreenId(String screenId, Date reserveDate) {
        return commodityMapper.selectByScreenId(screenId, reserveDate);
    }


    /**
     * 根据屏幕id和时间查询当天商品
     *
     * @param screenId
     * @param date
     * @return
     */
    @Override
    public List<CommodityVo> findByNearCommodity(String screenId, String date) {
        return commodityMapper.selectByNearCommodity(screenId, date);
    }

    /**
     * 根据商品ID查询价格体系列表
     */
    @Override
    public List<CommodityCost> getCommodityCostList(String commodityId) {
        return commodityCostMapper.getCommodityCostList(commodityId);
    }

    /**
     * 分页获取定制商品信息
     *
     * @return 可定制商品
     */
    @Override
    public List<CommodityVo> findCustomCommodity(int type, String screenId, Date reserveDate) {
        return commodityMapper.selectCustomCommodity(type, screenId, reserveDate);
    }

    /**
     * 获取活动期间中，返回商品所有商品
     *
     * @param promotionId 活动id
     * @param commodityId 商品id
     * @return
     */
    @Override
    public List<CommodityVo> findPromotionNearCommodity(String promotionId, String commodityId) {
        return commodityMapper.selectPromotionNearCommodity(promotionId, commodityId);
    }

    /**
     * 根据活动的单个商品
     *
     * @param promotionId 活动id
     * @param commodityId 商品id
     * @param reserveDate 下单时间
     * @return
     */
    @Override
    public List<CommodityVo> findPromotionSingleCommodity(String promotionId, String commodityId, Date reserveDate) {
        return commodityMapper.selectPromotionSingleCommodity(promotionId, commodityId, reserveDate);
    }

    @Override
    public List<CommodityVo> findByNearCustomCommodity(int type, String screenId, String reserveDate) {
        return commodityMapper.selectByNearCustomCommodity(type, screenId, reserveDate);
    }

    /**
     * 获取最近几天是否有可下单商品
     *
     * @param day      查询天数
     * @param screenId 屏幕id
     * @return 是否有可用日期
     */
    @Override
    public boolean[] findLatestCommodityStock(String screenId, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        boolean[] stock = new boolean[day];
        Calendar calendar = Calendar.getInstance();
        for (int i = 0; i < day; i++) {
            stock[i] = commodityMapper.selectLatestCommodityStock(screenId, format.format(calendar.getTime())) > 0;
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return stock;
    }

    //获取最近几天是否有可下订单
    @Override
    public boolean[] findPromotionLatestCommodityStock(String promotionId, String commodityId, int day) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        boolean[] stock = new boolean[day];
        Calendar calendar = Calendar.getInstance();
        int limit = promotionDetailMapper.findDetails(promotionId, commodityId).get(0).getCommodityNum();//获取活动关联商品最多下单数量
        for (int i = 0; i < day; i++) {
            int commoditySize = commodityMapper.selectPromotionLatestCommodityStock(promotionId, commodityId, format.format(calendar.getTime()));
            String playDate = format.format(calendar.getTime());
            int num = commodityMapper.getLimitSize(promotionId, commodityId, playDate);//当天关联此活动下单数
            stock[i] = commoditySize > 0 && (limit == 0 || num < limit);
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return stock;
    }

    @Override
    public int getLimitSize(String promotionId, String commodityId, String reserveDate) {
        return commodityMapper.getLimitSize(promotionId, commodityId, reserveDate);
    }
}