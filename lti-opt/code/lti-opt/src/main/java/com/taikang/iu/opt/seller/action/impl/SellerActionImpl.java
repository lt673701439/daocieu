package com.taikang.iu.opt.seller.action.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taikang.iu.opt.seller.action.ISellerAction;
import com.taikang.iu.opt.seller.model.SellerBO;
import com.taikang.iu.opt.seller.service.ISellerService;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.sys.model.UserBO;
import com.taikang.udp.sys.service.IUserService;

/**
 * 商户信息管理
 * @author t-wuke
 * @version [版本号，默认V1.0.0]
 * @Credited 2015年3月18日 上午10:14:22
 */
@Service(ISellerAction.ACTION_ID)
public class SellerActionImpl extends BaseActionImpl implements ISellerAction {

    /**
      * 注入service
      */
    @Resource(name=ISellerService.SERVICE_ID)
	private ISellerService<SellerBO> sellerService;	
    
    @Resource(name=IUserService.SERVICE_ID)
	private IUserService<UserBO> userService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======SellerAction--addSeller======>");
		
		// 新建商户信息
		SellerBO sellerBO = BaseDto.toModel(SellerBO.class , param);
		sellerService.insertObject(sellerBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======SellerAction--updateSeller======>");
		
		SellerBO sellerBO = BaseDto.toModel(SellerBO.class , param);
		sellerService.updateObject(sellerBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======SellerAction--deleteSeller======>");
		
		SellerBO sellerBO = BaseDto.toModel(SellerBO.class , param);
		sellerService.deleteObject(sellerBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======SellerAction--findOneSeller======>");
		
		SellerBO sellerBO = BaseDto.toModel(SellerBO.class , param);
		return sellerService.findOne(sellerBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======SellerAction--findAllSeller======>");
				
		return sellerService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======SellerAction--querySellerForPage======>");
		
		return sellerService.queryForPage(currentPage);
	}


	public List<Dto> findAllSeller(Dto param) {
		logger.debug("<======SellerAction--findOneSeller======>");
		
		//SellerBO sellerBO = BaseDto.toModel(SellerBO.class , param);
		return  sellerService.findAllSellers(param);
	}	
}
