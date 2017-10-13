package com.taikang.udp.sys.service.impl;
 
import com.taikang.udp.sys.service.ISeqnoService;
import com.taikang.udp.sys.model.SeqnoBO;
import java.util.List;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import java.util.Arrays;
import com.taikang.udp.framework.common.datastructre.Dto;
import org.springframework.stereotype.Service;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
 
  
/**
  * SeqnoServiceImpl
  */
 @Service(ISeqnoService.SERVICE_ID)
 public class SeqnoServiceImpl extends BaseServiceImpl 
 implements ISeqnoService<SeqnoBO>  
  {
  	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(SeqnoBO seqno) {
		logger.debug("<======SeqnoServiceImpl--insertSeqno======>");		
		appDao.insert("Seqno.addSeqno",seqno);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(SeqnoBO seqno) {
		logger.debug("<======SeqnoServiceImpl--updateSeqno======>");
		appDao.update("Seqno.updateSeqno",seqno);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(SeqnoBO seqno) {
		logger.debug("<======SeqnoServiceImpl--deleteSeqno======>");
		appDao.delete("Seqno.deleteSeqno",seqno);
	}
	
	/**
      * 查询数据
      */
	public SeqnoBO findOne(SeqnoBO seqno) {
		logger.debug("<======SeqnoServiceImpl--findSeqno======>");
		
		SeqnoBO seqnoBackBO=appDao.queryOneObject("Seqno.findOneSeqno", seqno);
		return seqnoBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<SeqnoBO> findAll(SeqnoBO  seqno) {
		logger.debug("<======SeqnoServiceImpl--findAllSeqno======>");
		return appDao.queryForEntityList("Seqno.findAllSeqno", seqno);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======SeqnoServiceImpl--querySeqnoForPage======>");
		return appDao.queryForPage("Seqno.querySeqnoForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======SeqnoServiceImpl--findAllMapSeqno======>");		
		return appDao.queryForMapList("Seqno.findAllMapSeqno", param);
	}
 }
  