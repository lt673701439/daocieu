package com.liketry.interaction.opt.orderdetail.action.impl;


import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.orderdetail.model.OrderDetailBO;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.orderdetail.service.IOrderDetailService;
import com.liketry.interaction.opt.orderdetail.action.IOrderDetailAction;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * OrderDetailAction
  */
  @Service(IOrderDetailAction.ACTION_ID)
public class OrderDetailActionImpl extends BaseActionImpl 
  implements IOrderDetailAction {

    /**
      * 注入service
      */
    @Resource(name=IOrderDetailService.SERVICE_ID)
	private IOrderDetailService<OrderDetailBO> orderDetailService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======OrderDetailAction--addOrderDetail======>");
		
		OrderDetailBO orderDetailBO = BaseDto.toModel(OrderDetailBO.class , param);
		orderDetailService.insertObject(orderDetailBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======OrderDetailAction--updateOrderDetail======>");
		
		OrderDetailBO orderDetailBO = BaseDto.toModel(OrderDetailBO.class , param);
		orderDetailService.updateObject(orderDetailBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======OrderDetailAction--deleteOrderDetail======>");
		
		OrderDetailBO orderDetailBO = BaseDto.toModel(OrderDetailBO.class , param);
		orderDetailService.deleteObject(orderDetailBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======OrderDetailAction--findOneOrderDetail======>");
		
		OrderDetailBO orderDetailBO = BaseDto.toModel(OrderDetailBO.class , param);
		return orderDetailService.findOne(orderDetailBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	@Override
	public List<Dto> findEffectOrderDetailList(Dto param) {
		logger.debug("<======OrderDetailAction--findEffectOrderDetailList======>");
		
		return orderDetailService.findEffectOrderDetailList(param);
	}

	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======OrderDetailAction--findAllOrderDetail======>");
				
		return orderDetailService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======OrderDetailAction--queryOrderDetailForPage======>");
		
		return orderDetailService.queryForPage(currentPage);
	}	
}
