package com.liketry.interaction.opt.common.service;
 
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.taikang.udp.framework.common.util.logger.LoggerFactory;
import com.taikang.udp.framework.core.persistence.dao.IBaseDao;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
 
  
/**
  * CommonService
  */
 @Service("commonService")
 public class CommonService {
	 
	 public static final Logger logger = LoggerFactory.getLogger();
	 
	 @Resource(name="appDao")
	 protected IBaseDao appDao;
  	 	 	
	 /**
      * 分页查询数据
      */
	 public CurrentPage queryForPage(CurrentPage currentPage, String findSql) {
		return appDao.queryForPage(findSql, currentPage);
	 }
 }
  