package com.liketry.interaction.opt.order.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.order.service.IOrderService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.order.model.OrderBO;
import com.liketry.interaction.opt.order.action.IOrderAction;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * OrderAction
  */
  @Service(IOrderAction.ACTION_ID)
public class OrderActionImpl extends BaseActionImpl 
  implements IOrderAction {

    /**
      * 注入service
      */
    @Resource(name=IOrderService.SERVICE_ID)
	private IOrderService<OrderBO> orderService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======OrderAction--addOrder======>");
		
		OrderBO orderBO = BaseDto.toModel(OrderBO.class , param);
		orderService.insertObject(orderBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======OrderAction--updateOrder======>");
		
		OrderBO orderBO = BaseDto.toModel(OrderBO.class , param);
		orderService.updateObject(orderBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======OrderAction--deleteOrder======>");
		
		OrderBO orderBO = BaseDto.toModel(OrderBO.class , param);
		orderService.deleteObject(orderBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======OrderAction--findOneOrder======>");
		
		OrderBO orderBO = BaseDto.toModel(OrderBO.class , param);
		return orderService.findOne(orderBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
     * 查询所有数据
     */
	public List<Dto> findAllOld(Dto param) {
		logger.debug("<======OrderAction--findAllOrderOld======>");
				
		return orderService.findAllMapOld(param);
	} 
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======OrderAction--findAllOrder======>");
				
		return orderService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======OrderAction--queryOrderForPage======>");
		
		return orderService.queryForPage(currentPage);
	}	
	
	/**
     * 查询是否有关联订单数据
     */
	public List<Dto> findAllByTemplateId(Dto param) {
		logger.debug("<======OrderAction--findAllByTemplateId======>");
				
		return orderService.findAllByTemplateId(param);
	} 
	
	/**
     * 查询各屏幕下的订单总数
     */
	public List<Dto> findOrderCount4Name(Dto param) {
		logger.debug("<======OrderAction--findOrderCount4Name======>");
				
		return orderService.findOrderCount4Name(param);
	} 
	
	/**
     * 查询各屏幕下的订单总数
     */
	public List<Dto> findOrderCountList2Name(Dto param) {
		logger.debug("<======OrderAction--findOrderCountList2Name======>");
				
		return orderService.findOrderCountList2Name(param);
	} 
	
	/**
     * 查出该屏幕下，各节日下的订单数
     */
	public List<Dto> findOrderCountList2Type(Dto param) {
		logger.debug("<======OrderAction--findOrderCountList2Type======>");
				
		return orderService.findOrderCountList2Type(param);
	} 
	
	/**
     * 查出指定屏幕，类型下的祝福语的订单数
     */
	public List<Dto> findOrderCountList2Benison(Dto param) {
		logger.debug("<======OrderAction--findOrderCountList2Benison======>");
				
		return orderService.findOrderCountList2Benison(param);
	} 
	
	/**
     * 查询订单中得播放时间列表
     */
	public CurrentPage findTimeOrderList(CurrentPage currentPage) {
		logger.debug("<======OrderAction--findTimeOrderList======>");
				
		return orderService.findTimeOrderList(currentPage);
	}
	
	/**
     * 查询指定播放时间的类型订单列表
     */
	public List<Dto> findTypeOrderList(Dto param) {
		logger.debug("<======OrderAction--findTypeOrderList======>");
				
		return orderService.findTypeOrderList(param);
	}
	
	/**
     * 查询指定播放时间的祝福语订单列表
     */
	public List<Dto> findBenisonOrderList(Dto param) {
		logger.debug("<======OrderAction--findBenisonOrderList======>");
				
		return orderService.findBenisonOrderList(param);
	}
	
	/**
     * 查询第一行列的名称
     */
	public List<Dto> findFirstColumns(Dto param) {
		logger.debug("<======OrderAction--findFirstColumns======>");
				
		return orderService.findFirstColumns(param);
	}
	
	/**
     * 查询第一行列的名称(供屏幕订单统计用)
     */
	public List<Dto> findNewFirstColumns(Dto param) {
		logger.debug("<======OrderAction--findNewFirstColumns======>");
				
		return orderService.findNewFirstColumns(param);
	}
	
	/**
     * 查询第二行列的名称
     */
	public List<Dto> findSecondColumns(Dto param) {
		logger.debug("<======OrderAction--findSecondColumns======>");
				
		return orderService.findSecondColumns(param);
	}
	
	/**
     * 查询第二行列的名称(供屏幕订单统计用)
     */
	public List<Dto> findNewSecondColumns(Dto param) {
		logger.debug("<======OrderAction--findNewSecondColumns======>");
				
		return orderService.findNewSecondColumns(param);
	}
	
	/**
     * 查询第三行列的名称
     */
	public List<Dto> findThirdColumns(Dto param) {
		logger.debug("<======OrderAction--findThirdColumns======>");
				
		return orderService.findThirdColumns(param);
	}
	
	/**
     * 查询屏幕订单汇总统计第一行的列名
     */
	public List<Dto> findFirstColumnsForSOT(Dto param) {
		logger.debug("<======OrderAction--findFirstColumnsForSOT======>");
		return orderService.findFirstColumnsForSOT(param);
	}
	
	/**
     * 查询屏幕订单汇总统计第二行的列名
     */
	public List<Dto> findSecondColumnsForSOT(Dto param) {
		logger.debug("<======OrderAction--findSecondColumnsForSOT======>");
		return orderService.findSecondColumnsForSOT(param);
	}
	
	/**
     * 根据屏幕和时间查询订单数
     */
	public int findOneTimeOrderCount(Dto param) {
		logger.debug("<======OrderAction--findOneTimeOrderCount======>");
		return orderService.findOneTimeOrderCount(param);//返回的BO对象自动转换成Dto返回
	}  
	
	/**
     * 查询屏幕时间段订单分析列表
     */
	public CurrentPage findScreenTimeOrderList(CurrentPage currentPage) {
		logger.debug("<======OrderAction--findScreenTimeOrderList======>");
				
		return orderService.findScreenTimeOrderList(currentPage);
	}
	
	/**
     * 查询订单总数
     */
	public int getAllCount(Dto param) {
		logger.debug("<======OrderAction--getAllCount======>");
		return orderService.getAllCount(param);//返回的BO对象自动转换成Dto返回
	}  
	
	/**
     * 查询出所有早于当天日期且状态为已支付的订单
     */
	public List<Dto> findAllOrderByStatus(Dto param) {
		logger.debug("<======OrderAction--findAllOrderByStatus======>");
				
		return orderService.findAllOrderByStatus(param);
	} 
	
}
