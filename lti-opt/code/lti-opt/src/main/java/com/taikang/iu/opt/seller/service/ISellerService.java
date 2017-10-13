package com.taikang.iu.opt.seller.service;

import java.util.List;

import com.taikang.iu.opt.seller.model.SellerBO;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;
import com.taikang.udp.framework.core.service.IBaseService;

/**
 * 商户信息管理
 * @author t-wuke
 * @version [版本号，默认V1.0.0]
 * @Credited 2015年3月18日 上午10:14:22
 */
 public interface ISellerService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "sellerService";

	List<Dto> findAllSellers(Dto param);  
 }
 
 
 