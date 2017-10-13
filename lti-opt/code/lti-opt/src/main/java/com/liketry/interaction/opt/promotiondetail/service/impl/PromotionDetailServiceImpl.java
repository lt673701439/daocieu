package com.liketry.interaction.opt.promotiondetail.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.liketry.interaction.opt.promotiondetail.model.PromotionDetailBO;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.promotiondetail.service.IPromotionDetailService;
 
  
/**
  * PromotionDetailServiceImpl
  */
 @Service(IPromotionDetailService.SERVICE_ID)
 public class PromotionDetailServiceImpl extends BaseServiceImpl 
 implements IPromotionDetailService<PromotionDetailBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(PromotionDetailBO promotionDetail) {
		logger.debug("<======PromotionDetailServiceImpl--insertPromotionDetail======>");		
		appDao.insert("PromotionDetail.addPromotionDetail",promotionDetail);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(PromotionDetailBO promotionDetail) {
		logger.debug("<======PromotionDetailServiceImpl--updatePromotionDetail======>");
		appDao.update("PromotionDetail.updatePromotionDetail",promotionDetail);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(PromotionDetailBO promotionDetail) {
		logger.debug("<======PromotionDetailServiceImpl--deletePromotionDetail======>");
		appDao.delete("PromotionDetail.deletePromotionDetail",promotionDetail);
	}
	
	/**
      * 查询数据
      */
	public PromotionDetailBO findOne(PromotionDetailBO promotionDetail) {
		logger.debug("<======PromotionDetailServiceImpl--findPromotionDetail======>");
		
		PromotionDetailBO promotionDetailBackBO=appDao.queryOneObject("PromotionDetail.findOnePromotionDetail", promotionDetail);
		return promotionDetailBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<PromotionDetailBO> findAll(PromotionDetailBO  promotionDetail) {
		logger.debug("<======PromotionDetailServiceImpl--findAllPromotionDetail======>");
		return appDao.queryForEntityList("PromotionDetail.findAllPromotionDetail", promotionDetail);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======PromotionDetailServiceImpl--queryPromotionDetailForPage======>");
//		return appDao.queryForPage("PromotionDetail.queryPromotionDetailForPage", currentPage);
		return appDao.queryForPage("PromotionDetail.queryPromotionDetailByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======PromotionDetailServiceImpl--findAllMapPromotionDetail======>");		
		return appDao.queryForMapList("PromotionDetail.findAllMapPromotionDetail", param);
	}
	
	/**
     *  查询商品列表
     */
	public List<Dto> findAllCommodityById(Dto param){
		logger.debug("<======PromotionDetailServiceImpl--findAllCommodityById======>");		
		return appDao.queryForMapList("PromotionDetail.findAllCommodityById", param);
	}
 }
  