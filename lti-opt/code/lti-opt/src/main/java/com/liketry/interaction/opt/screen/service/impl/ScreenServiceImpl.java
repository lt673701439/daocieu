package com.liketry.interaction.opt.screen.service.impl;
 
import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.screen.service.IScreenService;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.screen.model.ScreenBO;
 
  
/**
  * ScreenServiceImpl
  */
 @Service(IScreenService.SERVICE_ID)
 public class ScreenServiceImpl extends BaseServiceImpl 
 implements IScreenService<ScreenBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(ScreenBO screen) {
		logger.debug("<======ScreenServiceImpl--insertScreen======>");		
		appDao.insert("Screen.addScreen",screen);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(ScreenBO screen) {
		logger.debug("<======ScreenServiceImpl--updateScreen======>");
		appDao.update("Screen.updateScreen",screen);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(ScreenBO screen) {
		logger.debug("<======ScreenServiceImpl--deleteScreen======>");
		appDao.delete("Screen.deleteScreen",screen);
	}
	
	/**
      * 查询数据
      */
	public ScreenBO findOne(ScreenBO screen) {
		logger.debug("<======ScreenServiceImpl--findScreen======>");
		
		ScreenBO screenBackBO=appDao.queryOneObject("Screen.findOneScreen", screen);
		return screenBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<ScreenBO> findAll(ScreenBO  screen) {
		logger.debug("<======ScreenServiceImpl--findAllScreen======>");
		return appDao.queryForEntityList("Screen.findAllScreen", screen);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======ScreenServiceImpl--queryScreenForPage======>");
		return appDao.queryForPage("Screen.queryScreenByConForPage", currentPage);
//		return appDao.queryForPage("Screen.queryScreenForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======ScreenServiceImpl--findAllMapScreen======>");		
		return appDao.queryForMapList("Screen.findAllMapScreen", param);
	}
	
	/**
     * 查询所有数据 根据spotId
     */
	public List<Dto> findAllBySpotId(Dto param){
		logger.debug("<======ScreenServiceImpl--findAllBySpotId======>");		
		return appDao.queryForMapList("Screen.findAllBySpotId", param);
	}
 }
  