package com.liketry.interaction.benison.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.liketry.interaction.benison.model.Order;

public interface OrderMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_order
	 * @mbggenerated  Thu Aug 17 11:36:04 CST 2017
	 */
	int deleteByPrimaryKey(String orderId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_order
	 * @mbggenerated  Thu Aug 17 11:36:04 CST 2017
	 */
	int insert(Order record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_order
	 * @mbggenerated  Thu Aug 17 11:36:04 CST 2017
	 */
	int insertSelective(Order record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_order
	 * @mbggenerated  Thu Aug 17 11:36:04 CST 2017
	 */
	Order selectByPrimaryKey(String orderId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_order
	 * @mbggenerated  Thu Aug 17 11:36:04 CST 2017
	 */
	int updateByPrimaryKeySelective(Order record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_order
	 * @mbggenerated  Thu Aug 17 11:36:04 CST 2017
	 */
	int updateByPrimaryKey(Order record);

	/**
     * 根据userId查询订单列表
     * @param userId
     * @return
     */
    List<Order> findOrderListByUserId(@Param(value="userId")String userId);
    
    /**
     * 根据状态和openId查询订单列表
     * @param userId
     * @return
     */
    List<Order> findOrderListByStatus(@Param(value="pageSize")int pageSize,@Param(value="pageNumber")int pageNumber,
    		@Param(value="orderStatus")String orderStatus,@Param(value="userId")String userId);
     
    /**
     * 根据屏幕编号查询最后一条订单记录
     * @param templateId
     * @return
     */
    Order findLastOne(@Param(value="screenCode")String screenCode);
    
    /**
     * 获取订单总数
     * @return
     */
    int getAllCount(@Param(value="orderStatus")String orderStatus,@Param(value="userId")String userId);
    
    /**
     * 获取该订单的有效订单数
     * @param userId
     * @return
     */
    int getValidOrderList(@Param(value="userId")String userId);
    
    /**
     * 查询该活动，该商品，当天的订单数
     * @param map
     * @return
     */
    int selectOrderCountByPIdAndCId(Map<String,Object> map);
    
    /**
     * 获取该订单的有效订单数
     * @param userId
     * @return
     */
    int getZeroOrderList(Map<String,Object> map);
    
}