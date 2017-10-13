package com.liketry.interaction.opt.promotion.action.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.liketry.interaction.opt.promotion.action.IPromotionAction;
import com.liketry.interaction.opt.promotion.model.PromotionBO;
import com.liketry.interaction.opt.promotion.service.IPromotionService;
import com.taikang.iu.com.CommonUtil;
import com.taikang.iu.com.UploadFile;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;

/**
  * PromotionAction
  */
  @Service(IPromotionAction.ACTION_ID)
public class PromotionActionImpl extends BaseActionImpl 
  implements IPromotionAction {

    /**
      * 注入service
      */
    @Resource(name=IPromotionService.SERVICE_ID)
	private IPromotionService<PromotionBO> promotionService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======PromotionAction--addPromotion======>");
		
		PromotionBO promotionBO = BaseDto.toModel(PromotionBO.class , param);
		promotionService.insertObject(promotionBO);
	}
	
	/**
	  * 上传图片并新增数据
	  */
	public void insertObjectAndUpload(Dto param,HttpServletRequest request) {
		logger.debug("<======PromotionAction--insertObjectAndUpload======>");
		//上传图片
		String realUrl = null;
		String upLoadPath = CommonUtil.uploadFilePath();
		try {
			realUrl = UploadFile.uploadFile(request,upLoadPath,"PROMOTION/"+param.getAsString("promotionCode").substring(0,4),param.getAsString("promotionCode").substring(0,4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(realUrl!=null && !"".equals(realUrl)){
			param.put("promotionImg", realUrl);
		}
		PromotionBO promotionBO = BaseDto.toModel(PromotionBO.class , param);
		promotionService.insertObject(promotionBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======PromotionAction--updatePromotion======>");
		
		PromotionBO promotionBO = BaseDto.toModel(PromotionBO.class , param);
		promotionService.updateObject(promotionBO);
	}
	
	/**
	  * 上传图片并修改数据
	  */
	public void updateObjectAndUpload(Dto param,HttpServletRequest request,String description) {
		logger.debug("<======PromotionAction--updateObjectAndUpload======>");
		//上传图片
		String realUrl = null;
		String upLoadPath = CommonUtil.uploadFilePath();
		try {
			realUrl = UploadFile.uploadFile(request,upLoadPath,"PROMOTION/"+param.getAsString("promotionCode").substring(0,4),param.getAsString("promotionCode").substring(0,4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(realUrl!=null && !"".equals(realUrl)){
			param.put("promotionImg", realUrl);
		}
		PromotionBO promotionBO = BaseDto.toModel(PromotionBO.class , param);
		if(StringUtils.isNotBlank(description)){//使用DTO会替换#键
			promotionBO.setPromotionDescription(description);
		}
		promotionService.updateObject(promotionBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======PromotionAction--deletePromotion======>");
		
		PromotionBO promotionBO = BaseDto.toModel(PromotionBO.class , param);
		promotionService.deleteObject(promotionBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======PromotionAction--findOnePromotion======>");
		
		PromotionBO promotionBO = BaseDto.toModel(PromotionBO.class , param);
		return promotionService.findOne(promotionBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======PromotionAction--findAllPromotion======>");
				
		return promotionService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======PromotionAction--queryPromotionForPage======>");
		
		return promotionService.queryForPage(currentPage);
	}

	@Override
	public Map<String, Object> getDesc(Dto param) {
		logger.debug("<======PromotionAction--findOnePromotion======>");
		
		PromotionBO promotionBO = BaseDto.toModel(PromotionBO.class , param);
		promotionBO = promotionService.findOne(promotionBO);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("promotionId", promotionBO.getPromotionId());
		map.put("promotionDescription", promotionBO.getPromotionDescription());
		return map;//返回的BO对象自动转换成Dto返回
	}	
}
