package com.liketry.interaction.opt.benisontemplate.service.impl;
 
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.benisontemplate.service.IBenisonTemplateService;
import com.liketry.interaction.opt.benisontemplate.model.BenisonTemplateBO;
 
  
/**
  * BenisonTemplateServiceImpl
  */
 @Service(IBenisonTemplateService.SERVICE_ID)
 public class BenisonTemplateServiceImpl extends BaseServiceImpl 
 implements IBenisonTemplateService<BenisonTemplateBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(BenisonTemplateBO benisonTemplate) {
		logger.debug("<======BenisonTemplateServiceImpl--insertBenisonTemplate======>");		
		appDao.insert("BenisonTemplate.addBenisonTemplate",benisonTemplate);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(BenisonTemplateBO benisonTemplate) {
		logger.debug("<======BenisonTemplateServiceImpl--updateBenisonTemplate======>");
		appDao.update("BenisonTemplate.updateBenisonTemplate",benisonTemplate);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(BenisonTemplateBO benisonTemplate) {
		logger.debug("<======BenisonTemplateServiceImpl--deleteBenisonTemplate======>");
		appDao.delete("BenisonTemplate.deleteBenisonTemplate",benisonTemplate);
	}
	
	/**
      * 查询数据
      */
	public BenisonTemplateBO findOne(BenisonTemplateBO benisonTemplate) {
		logger.debug("<======BenisonTemplateServiceImpl--findBenisonTemplate======>");
		
		BenisonTemplateBO benisonTemplateBackBO=appDao.queryOneObject("BenisonTemplate.findOneBenisonTemplate", benisonTemplate);
		return benisonTemplateBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<BenisonTemplateBO> findAll(BenisonTemplateBO  benisonTemplate) {
		logger.debug("<======BenisonTemplateServiceImpl--findAllBenisonTemplate======>");
		return appDao.queryForEntityList("BenisonTemplate.findAllBenisonTemplate", benisonTemplate);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======BenisonTemplateServiceImpl--queryBenisonTemplateForPage======>");
		return appDao.queryForPage("BenisonTemplate.queryBenisonTemplateByConForPage", currentPage);
//		return appDao.queryForPage("BenisonTemplate.queryBenisonTemplateForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======BenisonTemplateServiceImpl--findAllMapBenisonTemplate======>");		
		return appDao.queryForMapList("BenisonTemplate.findAllMapBenisonTemplate", param);
	}
	
	/**
     * 根据祝福语ID查询是否有关联模板数据
     */
	public List<Dto> findAllByBenisonId(Dto param){
		logger.debug("<======BenisonTemplateServiceImpl--findAllByBenisonId======>");		
		return appDao.queryForMapList("BenisonTemplate.findAllByBenisonId", param);
	}
	
	/**
     * 根据祝福语图片ID查询是否有关联模板数据
     */
	public List<Dto> findAllByImgId(Dto param){
		logger.debug("<======BenisonTemplateServiceImpl--findAllByImgId======>");
		List<Dto> imgList = new ArrayList<Dto>();
		if(param.getAsString("imgType")!=null && "0".equals(param.getAsString("imgType"))){
			imgList = appDao.queryForMapList("BenisonTemplate.findAllByBgImgId", param);
		}else if(param.getAsString("imgType")!=null && "1".equals(param.getAsString("imgType"))){
			imgList = appDao.queryForMapList("BenisonTemplate.findAllBySmImgId", param);
		}
		return imgList;
	}
	
	/**
     * 根据祝福语类型ID查询是否有关联模板数据
     */
	public List<Dto> findAllByTypeId(Dto param){
		logger.debug("<======BenisonTemplateServiceImpl--findAllByTypeId======>");		
		return appDao.queryForMapList("BenisonTemplate.findAllByTypeId", param);
	}
	
	/**
     * 根据屏幕ID查询是否有关联模板数据
     */
	public List<Dto> findAllByScreenId(Dto param){
		logger.debug("<======BenisonTemplateServiceImpl--findAllByScreenId======>");		
		return appDao.queryForMapList("BenisonTemplate.findAllByScreenId", param);
	}
	
	/**
     * 根据屏幕ID查询最新一条模板
     */
	public BenisonTemplateBO findLastOne(BenisonTemplateBO benisonTemplate) {
		logger.debug("<======BenisonTemplateServiceImpl--findLastOne======>");
		
		BenisonTemplateBO benisonTemplateBackBO=appDao.queryOneObject("BenisonTemplate.findLastOneBenisonTemplate", benisonTemplate);
		return benisonTemplateBackBO;		
	}
	
	/**
     * 根据屏幕ID查询最新一条模板
     */
	public BenisonTemplateBO findOneBenisonTemplate(BenisonTemplateBO benisonTemplate) {
		logger.debug("<======BenisonTemplateServiceImpl--findOneBenisonTemplate======>");
		
		BenisonTemplateBO benisonTemplateBackBO=appDao.queryOneObject("BenisonTemplate.findOneBenisonTemplateById", benisonTemplate);
		return benisonTemplateBackBO;		
	}
 }
  