package com.liketry.interaction.opt.confcustom.service.impl;
 
import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.confcustom.model.ConfCustomBO;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.liketry.interaction.opt.confcustom.service.IConfCustomService;
 
  
/**
  * ConfCustomServiceImpl
  */
 @Service(IConfCustomService.SERVICE_ID)
 public class ConfCustomServiceImpl extends BaseServiceImpl 
 implements IConfCustomService<ConfCustomBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(ConfCustomBO confCustom) {
		logger.debug("<======ConfCustomServiceImpl--insertConfCustom======>");		
		appDao.insert("ConfCustom.addConfCustom",confCustom);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(ConfCustomBO confCustom) {
		logger.debug("<======ConfCustomServiceImpl--updateConfCustom======>");
		appDao.update("ConfCustom.updateConfCustom",confCustom);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(ConfCustomBO confCustom) {
		logger.debug("<======ConfCustomServiceImpl--deleteConfCustom======>");
		appDao.delete("ConfCustom.deleteConfCustom",confCustom);
	}
	
	/**
      * 查询数据
      */
	public ConfCustomBO findOne(ConfCustomBO confCustom) {
		logger.debug("<======ConfCustomServiceImpl--findConfCustom======>");
		
		ConfCustomBO confCustomBackBO=appDao.queryOneObject("ConfCustom.findOneConfCustom", confCustom);
		return confCustomBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<ConfCustomBO> findAll(ConfCustomBO  confCustom) {
		logger.debug("<======ConfCustomServiceImpl--findAllConfCustom======>");
		return appDao.queryForEntityList("ConfCustom.findAllConfCustom", confCustom);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======ConfCustomServiceImpl--queryConfCustomForPage======>");
		return appDao.queryForPage("ConfCustom.queryConfCustomForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======ConfCustomServiceImpl--findAllMapConfCustom======>");		
		return appDao.queryForMapList("ConfCustom.findAllMapConfCustom", param);
	}
 }
  