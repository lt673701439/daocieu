package com.liketry.interaction.opt.coupontype.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.coupontype.action.ICouponTypeAction;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.coupontype.model.CouponTypeBO;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.coupontype.service.ICouponTypeService;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * CouponTypeAction
  */
  @Service(ICouponTypeAction.ACTION_ID)
public class CouponTypeActionImpl extends BaseActionImpl 
  implements ICouponTypeAction {

    /**
      * 注入service
      */
    @Resource(name=ICouponTypeService.SERVICE_ID)
	private ICouponTypeService<CouponTypeBO> couponTypeService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======CouponTypeAction--addCouponType======>");
		
		CouponTypeBO couponTypeBO = BaseDto.toModel(CouponTypeBO.class , param);
		couponTypeService.insertObject(couponTypeBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======CouponTypeAction--updateCouponType======>");
		
		CouponTypeBO couponTypeBO = BaseDto.toModel(CouponTypeBO.class , param);
		couponTypeService.updateObject(couponTypeBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======CouponTypeAction--deleteCouponType======>");
		
		CouponTypeBO couponTypeBO = BaseDto.toModel(CouponTypeBO.class , param);
		couponTypeService.deleteObject(couponTypeBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======CouponTypeAction--findOneCouponType======>");
		
		CouponTypeBO couponTypeBO = BaseDto.toModel(CouponTypeBO.class , param);
		return couponTypeService.findOne(couponTypeBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======CouponTypeAction--findAllCouponType======>");
				
		return couponTypeService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======CouponTypeAction--queryCouponTypeForPage======>");
		
		return couponTypeService.queryForPage(currentPage);
	}	
	
	/**
     * 查询单条数据
     */
	public Dto findLastOne(Dto param) {
		logger.debug("<======CouponTypeAction--findLastOne======>");
		
		CouponTypeBO couponTypeBO = BaseDto.toModel(CouponTypeBO.class , param);
		return couponTypeService.findLastOne(couponTypeBO).toDto();//返回的BO对象自动转换成Dto返回
	} 
}
