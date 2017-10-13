package com.liketry.interaction.opt.benisonimg.action.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.liketry.interaction.opt.benisonimg.action.IBenisonImgAction;
import com.liketry.interaction.opt.benisonimg.model.BenisonImgBO;
import com.liketry.interaction.opt.benisonimg.service.IBenisonImgService;
import com.taikang.iu.com.CommonUtil;
import com.taikang.iu.com.UploadFile;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;

/**
  * BenisonImgAction
  */
  @Service(IBenisonImgAction.ACTION_ID)
public class BenisonImgActionImpl extends BaseActionImpl 
  implements IBenisonImgAction {
	  
	private ArrayList<HashMap<String, String>> fileList = new ArrayList<HashMap<String, String>>();
	
	public List<HashMap<String, String>> getUpFileList() {
		return this.fileList;
	}

    /**
      * 注入service
      */
    @Resource(name=IBenisonImgService.SERVICE_ID)
	private IBenisonImgService<BenisonImgBO> benisonImgService;	
    
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======BenisonImgAction--addBenisonImg======>");
		
		BenisonImgBO benisonImgBO = BaseDto.toModel(BenisonImgBO.class , param);
		benisonImgService.insertObject(benisonImgBO);
	}
	
	/**
	  * 上传图片并新增数据
	  */
	public void insertObjectAndUpload(Dto param,HttpServletRequest request) {
		logger.debug("<======BenisonImgAction--insertObjectAndUpload======>");
		//上传图片
		String realUrl = null;
		String upLoadPath = CommonUtil.uploadFilePath();
		try {
			realUrl = UploadFile.uploadFile(request,upLoadPath,"BENISONIMG/"+param.getAsString("imgCode").substring(0,4),param.getAsString("imgCode").substring(0,4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(realUrl!=null && !"".equals(realUrl)){
			param.put("imgUrl", realUrl);
		}
		BenisonImgBO benisonImgBO = BaseDto.toModel(BenisonImgBO.class , param);
		benisonImgService.insertObject(benisonImgBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======BenisonImgAction--updateBenisonImg======>");
		
		BenisonImgBO benisonImgBO = BaseDto.toModel(BenisonImgBO.class , param);
		benisonImgService.updateObject(benisonImgBO);
	}
	
	/**
	  * 上传图片并修改数据
	  */
	public void updateObjectAndUpload(Dto param,HttpServletRequest request) {
		logger.debug("<======BenisonImgAction--updateObjectAndUpload======>");
		//上传图片
		String realUrl = null;
		String upLoadPath = CommonUtil.uploadFilePath();
		try {
			realUrl = UploadFile.uploadFile(request,upLoadPath,"BENISONIMG/"+param.getAsString("imgCode").substring(0,4),param.getAsString("imgCode").substring(0,4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(realUrl!=null && !"".equals(realUrl)){
			param.put("imgUrl", realUrl);
		}
		BenisonImgBO benisonImgBO = BaseDto.toModel(BenisonImgBO.class , param);
		benisonImgService.updateObject(benisonImgBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======BenisonImgAction--deleteBenisonImg======>");
		
		BenisonImgBO benisonImgBO = BaseDto.toModel(BenisonImgBO.class , param);
		benisonImgService.deleteObject(benisonImgBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======BenisonImgAction--findOneBenisonImg======>");
		
		BenisonImgBO benisonImgBO = BaseDto.toModel(BenisonImgBO.class , param);
		return benisonImgService.findOne(benisonImgBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======BenisonImgAction--findAllBenisonImg======>");
				
		return benisonImgService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======BenisonImgAction--queryBenisonImgForPage======>");
		
		return benisonImgService.queryForPage(currentPage);
	}	
	
}
