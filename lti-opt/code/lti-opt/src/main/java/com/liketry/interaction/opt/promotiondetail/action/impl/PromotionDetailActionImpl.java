package com.liketry.interaction.opt.promotiondetail.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.promotiondetail.action.IPromotionDetailAction;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.promotiondetail.model.PromotionDetailBO;
import com.liketry.interaction.opt.promotiondetail.service.IPromotionDetailService;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * PromotionDetailAction
  */
  @Service(IPromotionDetailAction.ACTION_ID)
public class PromotionDetailActionImpl extends BaseActionImpl 
  implements IPromotionDetailAction {

    /**
      * 注入service
      */
    @Resource(name=IPromotionDetailService.SERVICE_ID)
	private IPromotionDetailService<PromotionDetailBO> promotionDetailService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======PromotionDetailAction--addPromotionDetail======>");
		
		PromotionDetailBO promotionDetailBO = BaseDto.toModel(PromotionDetailBO.class , param);
		promotionDetailService.insertObject(promotionDetailBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======PromotionDetailAction--updatePromotionDetail======>");
		
		PromotionDetailBO promotionDetailBO = BaseDto.toModel(PromotionDetailBO.class , param);
		promotionDetailService.updateObject(promotionDetailBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======PromotionDetailAction--deletePromotionDetail======>");
		
		PromotionDetailBO promotionDetailBO = BaseDto.toModel(PromotionDetailBO.class , param);
		promotionDetailService.deleteObject(promotionDetailBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======PromotionDetailAction--findOnePromotionDetail======>");
		
		PromotionDetailBO promotionDetailBO = BaseDto.toModel(PromotionDetailBO.class , param);
		return promotionDetailService.findOne(promotionDetailBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======PromotionDetailAction--findAllPromotionDetail======>");
				
		return promotionDetailService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======PromotionDetailAction--queryPromotionDetailForPage======>");
		
		return promotionDetailService.queryForPage(currentPage);
	}
	
	/**
     * 查询商品列表
     */
	public List<Dto> findAllCommodityById(Dto param) {
		logger.debug("<======PromotionDetailAction--findAllCommodityById======>");
				
		return promotionDetailService.findAllCommodityById(param);
	} 
}
