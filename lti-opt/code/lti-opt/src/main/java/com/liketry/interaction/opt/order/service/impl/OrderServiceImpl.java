package com.liketry.interaction.opt.order.service.impl;
 
import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.order.service.IOrderService;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.order.model.OrderBO;
 
  
/**
  * OrderServiceImpl
  */
 @Service(IOrderService.SERVICE_ID)
 public class OrderServiceImpl extends BaseServiceImpl 
 implements IOrderService<OrderBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(OrderBO order) {
		logger.debug("<======OrderServiceImpl--insertOrder======>");		
		appDao.insert("Order.addOrder",order);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(OrderBO order) {
		logger.debug("<======OrderServiceImpl--updateOrder======>");
		appDao.update("Order.updateOrder",order);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(OrderBO order) {
		logger.debug("<======OrderServiceImpl--deleteOrder======>");
		appDao.delete("Order.deleteOrder",order);
	}
	
	/**
      * 查询数据
      */
	public OrderBO findOne(OrderBO order) {
		logger.debug("<======OrderServiceImpl--findOrder======>");
		
		OrderBO orderBackBO=appDao.queryOneObject("Order.findOneOrder", order);
		return orderBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<OrderBO> findAll(OrderBO  order) {
		logger.debug("<======OrderServiceImpl--findAllOrder======>");
		return appDao.queryForEntityList("Order.findAllOrder", order);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======OrderServiceImpl--queryOrderForPage======>");
//		return appDao.queryForPage("Order.queryOrderForPage", currentPage);
		return appDao.queryForPage("Order.queryOrderByConForPage", currentPage);
	}
	
	/**
     * 查询所有数据 ，重新组装为map
     */
	public List<Dto> findAllMapOld(Dto param){
		logger.debug("<======OrderServiceImpl--findAllMapOrderOld======>");		
		return appDao.queryForMapList("Order.findAllMapOrderOld", param);
	}
	
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======OrderServiceImpl--findAllMapOrder======>");		
		return appDao.queryForMapList("Order.findAllMapOrder", param);
	}
	
	/**
     * 查询是否有关联订单数据
     */
	public List<Dto> findAllByTemplateId(Dto param){
		logger.debug("<======OrderServiceImpl--findAllByTemplateId======>");		
		return appDao.queryForMapList("Order.findAllByTemplateId", param);
	}
	
	/**
     * 根据商品ID查询订单列表
     */
	public List<Dto> findAllMapByComId(Dto param){
		logger.debug("<======OrderServiceImpl--findAllMapByComId======>");		
		return appDao.queryForMapList("Order.findAllMapByComId", param);
	}
	
	/**
     * 获取各屏幕的订单数
     */
	public List<Dto> findOrderCountList2Name(Dto param){
		logger.debug("<======OrderServiceImpl--findOrderCountList2Name======>");		
		return appDao.queryForMapList("Order.findOrderCountList2Name", param);
	}
	
	/**
     * 获取各屏幕的订单数
     */
	public List<Dto> findOrderCount4Name(Dto param){
		logger.debug("<======OrderServiceImpl--findOrderCount4Name======>");		
		return appDao.queryForMapList("Order.findOrderCount4Name", param);
	}
	
   /**
    * 获取各类型的订单数
    */
	public List<Dto> findOrderCountList2Type(Dto param){
		logger.debug("<======OrderServiceImpl--findOrderCountList2Type======>");		
		return appDao.queryForMapList("Order.findOrderCountList2Type", param);
	}
	
	/**
     * 获取指定的屏幕，类型下祝福语的订单数
     */
	public List<Dto> findOrderCountList2Benison(Dto param){
		logger.debug("<======OrderServiceImpl--findOrderCountList2Benison======>");		
		return appDao.queryForMapList("Order.findOrderCountList2Benison", param);
	}
	
  /**
    * 获取订单的播放时间列表
    */
	public CurrentPage findTimeOrderList(CurrentPage currentPage){
		logger.debug("<======OrderServiceImpl--findTimeOrderList======>");		
		return appDao.queryForPage("Order.findTimeOrderList",currentPage);
	}
	
  /**
    * 获取各屏幕下的各类型订单总数
    */
	public List<Dto> findTypeOrderList(Dto param){
		logger.debug("<======OrderServiceImpl--findTypeOrderList======>");		
		return appDao.queryForMapList("Order.findTypeOrderList", param);
	}
	
  /**
    * 查询指定播放时间的祝福语订单列表
    */
	public List<Dto> findBenisonOrderList(Dto param){
		logger.debug("<======OrderServiceImpl--findBenisonOrderList======>");		
		return appDao.queryForMapList("Order.findBenisonOrderList", param);
	}	
	
  /**
    * 查询第一行列的名称
    */
	public List<Dto> findFirstColumns(Dto param){
		logger.debug("<======OrderServiceImpl--findFirstColumns======>");		
		return appDao.queryForMapList("Order.findFirstColumns", param);
	}
	
  /**
    * 查询第一行列的名称(供屏幕订单统计用)
    */
	public List<Dto> findNewFirstColumns(Dto param){
		logger.debug("<======OrderServiceImpl--findNewFirstColumns======>");		
		return appDao.queryForMapList("Order.findNewFirstColumns", param);
	}


  /**
    * 查询第二行列的名称
    */
	public List<Dto> findSecondColumns(Dto param){
		logger.debug("<======OrderServiceImpl--findSecondColumns======>");		
		return appDao.queryForMapList("Order.findSecondColumns", param);
	}
	
  /**
    * 查询第二行列的名称(供屏幕订单统计用)
    */
	public List<Dto> findNewSecondColumns(Dto param){
		logger.debug("<======OrderServiceImpl--findNewSecondColumns======>");		
		return appDao.queryForMapList("Order.findNewSecondColumns", param);
	}	
	
  /**
    * 查询第三行列的名称
    */
	public List<Dto> findThirdColumns(Dto param){
		logger.debug("<======OrderServiceImpl--findThirdColumns======>");		
		return appDao.queryForMapList("Order.findThirdColumns", param);
	}
	
  /**
    * 查询屏幕订单汇总统计第一行的列名
    */
	public List<Dto> findFirstColumnsForSOT(Dto param){
		logger.debug("<======OrderServiceImpl--findFirstColumnsForSOT======>");		
		return appDao.queryForMapList("Order.findFirstColumnsForSOT", param);
	}

  /**
    * 查询屏幕订单汇总统计第二行的列名
    */
	public List<Dto> findSecondColumnsForSOT(Dto param){
		logger.debug("<======OrderServiceImpl--findSecondColumnsForSOT======>");		
		return appDao.queryForMapList("Order.findSecondColumnsForSOT", param);
	}
	
	/**
     * 根据屏幕和时间查询出订单数
     */
	public int findOneTimeOrderCount(Dto param) {
		logger.debug("<======OrderServiceImpl--findOneTimeOrderCount======>");
		return appDao.queryCount("Order.findOneTimeOrderCount", param);		
	}
	
  /**
    * 获取订单的播放时间列表
    */
	public CurrentPage findScreenTimeOrderList(CurrentPage currentPage){
		logger.debug("<======OrderServiceImpl--findScreenTimeOrderList======>");		
		return appDao.queryForPage("Order.findScreenTimeOrderList",currentPage);
	}
	
	/**
     * 查询订单总数
     */
	public int getAllCount(Dto param) {
		logger.debug("<======OrderServiceImpl--getAllCount======>");
		return appDao.queryCount("Order.getAllCount", param);		
	}
	
	/**
     * 查询出所有早于当天日期且状态为已支付的订单
     */
	public List<Dto> findAllOrderByStatus(Dto param){
		logger.debug("<======OrderServiceImpl--findAllOrderByStatus======>");		
		return appDao.queryForMapList("Order.findAllOrderByStatus", param);
	}
 }
  