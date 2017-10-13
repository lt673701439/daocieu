package com.liketry.interaction.opt.spot.service.impl;
 
import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.spot.model.SpotBO;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.spot.service.ISpotService;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
 
  
/**
  * SpotServiceImpl
  */
 @Service(ISpotService.SERVICE_ID)
 public class SpotServiceImpl extends BaseServiceImpl 
 implements ISpotService<SpotBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(SpotBO spot) {
		logger.debug("<======SpotServiceImpl--insertSpot======>");		
		appDao.insert("Spot.addSpot",spot);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(SpotBO spot) {
		logger.debug("<======SpotServiceImpl--updateSpot======>");
		appDao.update("Spot.updateSpot",spot);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(SpotBO spot) {
		logger.debug("<======SpotServiceImpl--deleteSpot======>");
		appDao.delete("Spot.deleteSpot",spot);
	}
	
	/**
      * 查询数据
      */
	public SpotBO findOne(SpotBO spot) {
		logger.debug("<======SpotServiceImpl--findSpot======>");
		
		SpotBO spotBackBO=appDao.queryOneObject("Spot.findOneSpot", spot);
		return spotBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<SpotBO> findAll(SpotBO  spot) {
		logger.debug("<======SpotServiceImpl--findAllSpot======>");
		return appDao.queryForEntityList("Spot.findAllSpot", spot);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======SpotServiceImpl--querySpotForPage======>");
		return appDao.queryForPage("Spot.querySpotByConForPage", currentPage);
//		return appDao.queryForPage("Spot.querySpotForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======SpotServiceImpl--findAllMapSpot======>");		
		return appDao.queryForMapList("Spot.findAllMapSpot", param);
	}
 }
  