package com.taikang.udp.sys.action.impl;


import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import javax.annotation.Resource;
import com.taikang.udp.sys.service.ISeqnoService;
import com.taikang.udp.sys.model.SeqnoBO;
import java.util.List;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import java.util.Arrays;
import com.taikang.udp.framework.common.datastructre.Dto;
import org.springframework.stereotype.Service;
import com.taikang.udp.sys.action.ISeqnoAction;

/**
  * SeqnoAction
  */
  @Service(ISeqnoAction.ACTION_ID)
public class SeqnoActionImpl extends BaseActionImpl 
  implements ISeqnoAction {

    /**
      * 注入service
      */
    @Resource(name=ISeqnoService.SERVICE_ID)
	private ISeqnoService<SeqnoBO> seqnoService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======SeqnoAction--addSeqno======>");
		
		SeqnoBO seqnoBO = BaseDto.toModel(SeqnoBO.class , param);
		seqnoService.insertObject(seqnoBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======SeqnoAction--updateSeqno======>");
		
		SeqnoBO seqnoBO = BaseDto.toModel(SeqnoBO.class , param);
		seqnoService.updateObject(seqnoBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======SeqnoAction--deleteSeqno======>");
		
		SeqnoBO seqnoBO = BaseDto.toModel(SeqnoBO.class , param);
		seqnoService.deleteObject(seqnoBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======SeqnoAction--findOneSeqno======>");
		
		SeqnoBO seqnoBO = BaseDto.toModel(SeqnoBO.class , param);
		return seqnoService.findOne(seqnoBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======SeqnoAction--findAllSeqno======>");
				
		return seqnoService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======SeqnoAction--querySeqnoForPage======>");
		
		return seqnoService.queryForPage(currentPage);
	}	
}
