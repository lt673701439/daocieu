/**
 * @author pengyy
 * created at 2017/5/23 18:09
 */
package com.liketry.interaction.benison.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.model.Order;
import com.liketry.interaction.benison.model.OrderDetail;


public interface OrderService {

    PageInfo<Order> findOrderListByUserId(int pageSize, int pageNumber,String userId);
    
    List<Order> findOrderListByStatus(int pageSize, int pageNumber,String orderStatus,String openId);
    
    String updateOrderStatus(String orderId,String userId);
    
    String updateOrderStatus(String orderId,String userId,String status);
    
    String updateOrderStatus(String orderId,String userId,String transactionNo,BigDecimal payPrice, String payType);

    String returnOrder(String orderId,String userId,String userType,String backReason,String userName,String tradeType);
    
    Map<String,Object> saveOrder(Order order,BenisonTemplate benisonTemplate);
    
    Map<String,Object> saveCustomOrder(Order order);
    
    Order findOneOrder(String orderId);
    
    List<OrderDetail> findOrderDetailList(String orderId);
    
    public int updateOrder(Order order);
    
    int getAllCount(String orderStatus,String userId);
    
    public Map<String,Object> syncRecDisOrder(Order order,Boolean flag);
    
    public Map<String,Object> syncBookAcountOrder(Order order);
    
    public Map<String,Object> updateBookAcountOrder(Order order);
    
    /**
     * 获取该用户的有效订单数
     * @param userId
     * @return
     */
    int getValidOrderList(String userId);
    
    /**
     * 查询该活动，该商品，当天的订单数
     * @param map
     * @return
     */
    int selectOrderCountByPIdAndCId(Map<String,Object> map);
    
    /**
     * 获取该用户的0元订单数
     * @param map
     * @return
     */
    int getZeroOrderList(Map<String,Object> map);
    
}