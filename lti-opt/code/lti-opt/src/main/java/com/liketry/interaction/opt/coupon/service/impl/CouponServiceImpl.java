package com.liketry.interaction.opt.coupon.service.impl;
 
import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.coupon.model.CouponBO;
import com.liketry.interaction.opt.coupon.service.ICouponService;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
 
  
/**
  * CouponServiceImpl
  */
 @Service(ICouponService.SERVICE_ID)
 public class CouponServiceImpl extends BaseServiceImpl 
 implements ICouponService<CouponBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(CouponBO coupon) {
		logger.debug("<======CouponServiceImpl--insertCoupon======>");		
		appDao.insert("Coupon.addCoupon",coupon);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(CouponBO coupon) {
		logger.debug("<======CouponServiceImpl--updateCoupon======>");
		appDao.update("Coupon.updateCoupon",coupon);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(CouponBO coupon) {
		logger.debug("<======CouponServiceImpl--deleteCoupon======>");
		appDao.delete("Coupon.deleteCoupon",coupon);
	}
	
	/**
      * 查询数据
      */
	public CouponBO findOne(CouponBO coupon) {
		logger.debug("<======CouponServiceImpl--findCoupon======>");
		
		CouponBO couponBackBO=appDao.queryOneObject("Coupon.findOneCoupon", coupon);
		return couponBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<CouponBO> findAll(CouponBO  coupon) {
		logger.debug("<======CouponServiceImpl--findAllCoupon======>");
		return appDao.queryForEntityList("Coupon.findAllCoupon", coupon);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======CouponServiceImpl--queryCouponForPage======>");
		return appDao.queryForPage("Coupon.queryCouponForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======CouponServiceImpl--findAllMapCoupon======>");		
		return appDao.queryForMapList("Coupon.findAllMapCoupon", param);
	}
	
	/**
     *  导出查询
     */
	public List<Dto> findAllCouponMap(Dto param){
		logger.debug("<======CouponServiceImpl--findAllCouponMap======>");		
		return appDao.queryForMapList("Coupon.findAllCouponMap", param);
	}
 }
  