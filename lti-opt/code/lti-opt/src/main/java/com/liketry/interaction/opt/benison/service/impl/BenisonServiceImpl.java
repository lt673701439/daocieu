package com.liketry.interaction.opt.benison.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.liketry.interaction.opt.benison.model.BenisonBO;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.benison.service.IBenisonService;
 
  
/**
  * BenisonServiceImpl
  */
 @Service(IBenisonService.SERVICE_ID)
 public class BenisonServiceImpl extends BaseServiceImpl 
 implements IBenisonService<BenisonBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(BenisonBO benison) {
		logger.debug("<======BenisonServiceImpl--insertBenison======>");		
		appDao.insert("Benison.addBenison",benison);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(BenisonBO benison) {
		logger.debug("<======BenisonServiceImpl--updateBenison======>");
		appDao.update("Benison.updateBenison",benison);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(BenisonBO benison) {
		logger.debug("<======BenisonServiceImpl--deleteBenison======>");
		appDao.delete("Benison.deleteBenison",benison);
	}
	
	/**
      * 查询数据
      */
	public BenisonBO findOne(BenisonBO benison) {
		logger.debug("<======BenisonServiceImpl--findBenison======>");
		
		BenisonBO benisonBackBO=appDao.queryOneObject("Benison.findOneBenison", benison);
		return benisonBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<BenisonBO> findAll(BenisonBO  benison) {
		logger.debug("<======BenisonServiceImpl--findAllBenison======>");
		return appDao.queryForEntityList("Benison.findAllBenison", benison);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======BenisonServiceImpl--queryBenisonForPage======>");
		return appDao.queryForPage("Benison.queryBenisonByConForPage", currentPage);
//		return appDao.queryForPage("Benison.queryBenisonForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======BenisonServiceImpl--findAllMapBenison======>");		
		return appDao.queryForMapList("Benison.findAllMapBenison", param);
	}
	
	/**
     * 根据是否有关联祝福语数据
     */
	public List<Dto> findAllBySpotId(Dto param){
		logger.debug("<======BenisonServiceImpl--findAllBySpotId======>");		
		return appDao.queryForMapList("Benison.findAllBySpotId", param);
	}
 }
  