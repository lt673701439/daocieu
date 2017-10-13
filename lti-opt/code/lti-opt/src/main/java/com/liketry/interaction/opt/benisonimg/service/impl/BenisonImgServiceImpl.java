package com.liketry.interaction.opt.benisonimg.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.benisonimg.model.BenisonImgBO;
import com.liketry.interaction.opt.benisonimg.service.IBenisonImgService;
 
  
/**
  * BenisonImgServiceImpl
  */
 @Service(IBenisonImgService.SERVICE_ID)
 public class BenisonImgServiceImpl extends BaseServiceImpl 
 implements IBenisonImgService<BenisonImgBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(BenisonImgBO benisonImg) {
		logger.debug("<======BenisonImgServiceImpl--insertBenisonImg======>");		
		appDao.insert("BenisonImg.addBenisonImg",benisonImg);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(BenisonImgBO benisonImg) {
		logger.debug("<======BenisonImgServiceImpl--updateBenisonImg======>");
		appDao.update("BenisonImg.updateBenisonImg",benisonImg);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(BenisonImgBO benisonImg) {
		logger.debug("<======BenisonImgServiceImpl--deleteBenisonImg======>");
		appDao.delete("BenisonImg.deleteBenisonImg",benisonImg);
	}
	
	/**
      * 查询数据
      */
	public BenisonImgBO findOne(BenisonImgBO benisonImg) {
		logger.debug("<======BenisonImgServiceImpl--findBenisonImg======>");
		
		BenisonImgBO benisonImgBackBO=appDao.queryOneObject("BenisonImg.findOneBenisonImg", benisonImg);
		return benisonImgBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<BenisonImgBO> findAll(BenisonImgBO  benisonImg) {
		logger.debug("<======BenisonImgServiceImpl--findAllBenisonImg======>");
		return appDao.queryForEntityList("BenisonImg.findAllBenisonImg", benisonImg);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======BenisonImgServiceImpl--queryBenisonImgForPage======>");
		return appDao.queryForPage("BenisonImg.queryBenisonImgByConForPage", currentPage);
//		return appDao.queryForPage("BenisonImg.queryBenisonImgForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======BenisonImgServiceImpl--findAllMapBenisonImg======>");		
		return appDao.queryForMapList("BenisonImg.findAllMapBenisonImg", param);
	}
 }
  