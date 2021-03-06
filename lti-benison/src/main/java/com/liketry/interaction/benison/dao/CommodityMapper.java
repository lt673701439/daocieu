package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.Commodity;
import com.liketry.interaction.benison.vo.api.CommodityVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommodityMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_commodity
     *
     * @mbggenerated Tue Jul 04 10:30:30 CST 2017
     */
    int deleteByPrimaryKey(String commodityId);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_commodity
     *
     * @mbggenerated Tue Jul 04 10:30:30 CST 2017
     */
    int insert(Commodity record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_commodity
     *
     * @mbggenerated Tue Jul 04 10:30:30 CST 2017
     */
    int insertSelective(Commodity record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_commodity
     *
     * @mbggenerated Tue Jul 04 10:30:30 CST 2017
     */
    Commodity selectByPrimaryKey(String commodityId);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_commodity
     *
     * @mbggenerated Tue Jul 04 10:30:30 CST 2017
     */
    int updateByPrimaryKeySelective(Commodity record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_commodity
     *
     * @mbggenerated Tue Jul 04 10:30:30 CST 2017
     */
    int updateByPrimaryKey(Commodity record);

    //根据屏幕id查询商品
    List<CommodityVo> selectByScreenId(@Param("screenId") String screenId, @Param("reserveDate") Date reserveDate);

    //获取定制屏幕商品
    List<CommodityVo> selectCustomCommodity(@Param("type") int type, @Param("screenId") String screenId, @Param("reserveDate") Date reserveDate);

    //根据屏幕id查询最近商品
    List<CommodityVo> selectByNearCommodity(@Param("screenId") String screenId, @Param("reserveDate") String reserveDate);

    //根据活动id和商品id查询最近商品
    List<CommodityVo> selectPromotionNearCommodity(@Param("promotionId") String promotionId, @Param("commodityId") String commodityId);

    //根据活动的id,商品id，和查询时间返回单个商品
    List<CommodityVo> selectPromotionSingleCommodity(@Param("promotionId") String promotionId, @Param("commodityId") String commodityId, @Param("reserveDate") Date reserveDate);

    //根据屏幕id查询最近(定制)商品
    List<CommodityVo> selectByNearCustomCommodity(@Param("type") int type, @Param("screenId") String screenId, @Param("reserveDate") String reserveDate);

    //获取某天可下单商品数量
    int selectLatestCommodityStock(@Param("screenId") String screenId, @Param("reserveDate") String reserveDate);

    //获取某天可下单商品数量(活动使用)
    int selectPromotionLatestCommodityStock(@Param("promotionId") String promotionId, @Param("commodityId") String commodityId, @Param("reserveDate") String reserveDate);

    //根据活动id 商品id 和选择的日期，确定这天已经有几个限定活动商品订单
    int getLimitSize(@Param("promotionId") String promotionId, @Param("commodityId") String commodityId, @Param("reserveDate") String reserveDate);
}