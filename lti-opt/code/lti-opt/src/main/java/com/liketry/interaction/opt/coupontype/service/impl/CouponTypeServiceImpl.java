package com.liketry.interaction.opt.coupontype.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.coupontype.model.CouponTypeBO;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.coupontype.service.ICouponTypeService;
 
  
/**
  * CouponTypeServiceImpl
  */
 @Service(ICouponTypeService.SERVICE_ID)
 public class CouponTypeServiceImpl extends BaseServiceImpl 
 implements ICouponTypeService<CouponTypeBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(CouponTypeBO couponType) {
		logger.debug("<======CouponTypeServiceImpl--insertCouponType======>");		
		appDao.insert("CouponType.addCouponType",couponType);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(CouponTypeBO couponType) {
		logger.debug("<======CouponTypeServiceImpl--updateCouponType======>");
		appDao.update("CouponType.updateCouponType",couponType);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(CouponTypeBO couponType) {
		logger.debug("<======CouponTypeServiceImpl--deleteCouponType======>");
		appDao.delete("CouponType.deleteCouponType",couponType);
	}
	
	/**
      * 查询数据
      */
	public CouponTypeBO findOne(CouponTypeBO couponType) {
		logger.debug("<======CouponTypeServiceImpl--findCouponType======>");
		
		CouponTypeBO couponTypeBackBO=appDao.queryOneObject("CouponType.findOneCouponType", couponType);
		return couponTypeBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<CouponTypeBO> findAll(CouponTypeBO  couponType) {
		logger.debug("<======CouponTypeServiceImpl--findAllCouponType======>");
		return appDao.queryForEntityList("CouponType.findAllCouponType", couponType);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======CouponTypeServiceImpl--queryCouponTypeForPage======>");
		return appDao.queryForPage("CouponType.queryCouponTypeForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======CouponTypeServiceImpl--findAllMapCouponType======>");		
		return appDao.queryForMapList("CouponType.findAllMapCouponType", param);
	}
	
	/**
     * 查询数据
     */
	public CouponTypeBO findLastOne(CouponTypeBO couponType) {
		logger.debug("<======CouponTypeServiceImpl--findLastOne======>");
		
		CouponTypeBO couponTypeBackBO=appDao.queryOneObject("CouponType.findLastOneCouponType", couponType);
		return couponTypeBackBO;		
	}
 }
  