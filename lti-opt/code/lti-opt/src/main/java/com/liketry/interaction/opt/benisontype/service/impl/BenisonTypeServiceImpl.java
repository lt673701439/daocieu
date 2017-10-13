package com.liketry.interaction.opt.benisontype.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.benisontype.service.IBenisonTypeService;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.benisontype.model.BenisonTypeBO;
 
  
/**
  * BenisonTypeServiceImpl
  */
 @Service(IBenisonTypeService.SERVICE_ID)
 public class BenisonTypeServiceImpl extends BaseServiceImpl 
 implements IBenisonTypeService<BenisonTypeBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(BenisonTypeBO benisonType) {
		logger.debug("<======BenisonTypeServiceImpl--insertBenisonType======>");		
		appDao.insert("BenisonType.addBenisonType",benisonType);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(BenisonTypeBO benisonType) {
		logger.debug("<======BenisonTypeServiceImpl--updateBenisonType======>");
		appDao.update("BenisonType.updateBenisonType",benisonType);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(BenisonTypeBO benisonType) {
		logger.debug("<======BenisonTypeServiceImpl--deleteBenisonType======>");
		appDao.delete("BenisonType.deleteBenisonType",benisonType);
	}
	
	/**
      * 查询数据
      */
	public BenisonTypeBO findOne(BenisonTypeBO benisonType) {
		logger.debug("<======BenisonTypeServiceImpl--findBenisonType======>");
		
		BenisonTypeBO benisonTypeBackBO=appDao.queryOneObject("BenisonType.findOneBenisonType", benisonType);
		return benisonTypeBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<BenisonTypeBO> findAll(BenisonTypeBO  benisonType) {
		logger.debug("<======BenisonTypeServiceImpl--findAllBenisonType======>");
		return appDao.queryForEntityList("BenisonType.findAllBenisonType", benisonType);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======BenisonTypeServiceImpl--queryBenisonTypeForPage======>");
		return appDao.queryForPage("BenisonType.queryBenisonTypeByConForPage", currentPage);
//		return appDao.queryForPage("BenisonType.queryBenisonTypeForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======BenisonTypeServiceImpl--findAllMapBenisonType======>");		
		return appDao.queryForMapList("BenisonType.findAllMapBenisonType", param);
	}
 }
  