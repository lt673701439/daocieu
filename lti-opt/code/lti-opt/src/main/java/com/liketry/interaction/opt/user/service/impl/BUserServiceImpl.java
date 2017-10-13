package com.liketry.interaction.opt.user.service.impl;
 
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;
import com.liketry.interaction.opt.user.model.BUserBO;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.liketry.interaction.opt.user.service.IBUserService;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
 
  
/**
  * BUserServiceImpl
  */
 @Service(IBUserService.SERVICE_ID)
 public class BUserServiceImpl extends BaseServiceImpl 
 implements IBUserService<BUserBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(BUserBO bUser) {
		logger.debug("<======BUserServiceImpl--insertBUser======>");		
		appDao.insert("BUser.addBUser",bUser);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(BUserBO bUser) {
		logger.debug("<======BUserServiceImpl--updateBUser======>");
		appDao.update("BUser.updateBUser",bUser);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(BUserBO bUser) {
		logger.debug("<======BUserServiceImpl--deleteBUser======>");
		appDao.delete("BUser.deleteBUser",bUser);
	}
	
	/**
      * 查询数据
      */
	public BUserBO findOne(BUserBO bUser) {
		logger.debug("<======BUserServiceImpl--findBUser======>");
		
		BUserBO bUserBackBO=appDao.queryOneObject("BUser.findOneBUser", bUser);
		return bUserBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<BUserBO> findAll(BUserBO  bUser) {
		logger.debug("<======BUserServiceImpl--findAllBUser======>");
		return appDao.queryForEntityList("BUser.findAllBUser", bUser);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======BUserServiceImpl--queryBUserForPage======>");
//		return appDao.queryForPage("BUser.queryBUserForPage", currentPage);
		return appDao.queryForPage("BUser.queryBUserByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======BUserServiceImpl--findAllMapBUser======>");		
		return appDao.queryForMapList("BUser.findAllMapBUser", param);
	}
 }
  