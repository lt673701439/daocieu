package com.liketry.interaction.opt.orderdetail.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.orderdetail.model.OrderDetailBO;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.liketry.interaction.opt.orderdetail.service.IOrderDetailService;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
 
  
/**
  * OrderDetailServiceImpl
  */
 @Service(IOrderDetailService.SERVICE_ID)
 public class OrderDetailServiceImpl extends BaseServiceImpl 
 implements IOrderDetailService<OrderDetailBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(OrderDetailBO orderDetail) {
		logger.debug("<======OrderDetailServiceImpl--insertOrderDetail======>");		
		appDao.insert("OrderDetail.addOrderDetail",orderDetail);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(OrderDetailBO orderDetail) {
		logger.debug("<======OrderDetailServiceImpl--updateOrderDetail======>");
		appDao.update("OrderDetail.updateOrderDetail",orderDetail);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(OrderDetailBO orderDetail) {
		logger.debug("<======OrderDetailServiceImpl--deleteOrderDetail======>");
		appDao.delete("OrderDetail.deleteOrderDetail",orderDetail);
	}
	
	/**
      * 查询数据
      */
	public OrderDetailBO findOne(OrderDetailBO orderDetail) {
		logger.debug("<======OrderDetailServiceImpl--findOrderDetail======>");
		
		OrderDetailBO orderDetailBackBO=appDao.queryOneObject("OrderDetail.findOneOrderDetail", orderDetail);
		return orderDetailBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<OrderDetailBO> findAll(OrderDetailBO  orderDetail) {
		logger.debug("<======OrderDetailServiceImpl--findAllOrderDetail======>");
		return appDao.queryForEntityList("OrderDetail.findAllOrderDetail", orderDetail);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======OrderDetailServiceImpl--queryOrderDetailForPage======>");
//		return appDao.queryForPage("OrderDetail.queryOrderDetailForPage", currentPage);
		return appDao.queryForPage("OrderDetail.queryOrderDetailByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======OrderDetailServiceImpl--findAllMapOrderDetail======>");		
		return appDao.queryForMapList("OrderDetail.findAllMapOrderDetail", param);
	}

	@Override
	public List<Dto> findEffectOrderDetailList(Dto param) {
		logger.debug("<======OrderDetailServiceImpl--findEffectOrderDetailList======>");		
		return appDao.queryForMapList("OrderDetail.findEffectOrderDetailList", param);
	}
	
	
 }
  