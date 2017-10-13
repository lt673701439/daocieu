package com.liketry.interaction.opt.promotion.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.promotion.model.PromotionBO;
import com.liketry.interaction.opt.promotion.service.IPromotionService;
 
  
/**
  * PromotionServiceImpl
  */
 @Service(IPromotionService.SERVICE_ID)
 public class PromotionServiceImpl extends BaseServiceImpl 
 implements IPromotionService<PromotionBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(PromotionBO promotion) {
		logger.debug("<======PromotionServiceImpl--insertPromotion======>");		
		appDao.insert("Promotion.addPromotion",promotion);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(PromotionBO promotion) {
		logger.debug("<======PromotionServiceImpl--updatePromotion======>");
		appDao.update("Promotion.updatePromotion",promotion);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(PromotionBO promotion) {
		logger.debug("<======PromotionServiceImpl--deletePromotion======>");
		appDao.delete("Promotion.deletePromotion",promotion);
	}
	
	/**
      * 查询数据
      */
	public PromotionBO findOne(PromotionBO promotion) {
		logger.debug("<======PromotionServiceImpl--findPromotion======>");
		
		PromotionBO promotionBackBO=appDao.queryOneObject("Promotion.findOnePromotion", promotion);
		return promotionBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<PromotionBO> findAll(PromotionBO  promotion) {
		logger.debug("<======PromotionServiceImpl--findAllPromotion======>");
		return appDao.queryForEntityList("Promotion.findAllPromotion", promotion);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======PromotionServiceImpl--queryPromotionByConForPage======>");
//		return appDao.queryForPage("Promotion.queryPromotionForPage", currentPage);
		return appDao.queryForPage("Promotion.queryPromotionByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======PromotionServiceImpl--findAllMapPromotion======>");		
		return appDao.queryForMapList("Promotion.findAllMapPromotion", param);
	}
 }
  