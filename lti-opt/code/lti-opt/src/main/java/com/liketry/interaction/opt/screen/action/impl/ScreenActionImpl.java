package com.liketry.interaction.opt.screen.action.impl;


import java.util.Arrays;
import java.util.List;
import com.liketry.interaction.opt.screen.service.IScreenService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.iu.com.CommonUtil;
import com.taikang.iu.com.UploadFile;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.screen.action.IScreenAction;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.liketry.interaction.opt.screen.model.ScreenBO;

/**
  * ScreenAction
  */
  @Service(IScreenAction.ACTION_ID)
public class ScreenActionImpl extends BaseActionImpl 
  implements IScreenAction {

    /**
      * 注入service
      */
    @Resource(name=IScreenService.SERVICE_ID)
	private IScreenService<ScreenBO> screenService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======ScreenAction--addScreen======>");
		
		ScreenBO screenBO = BaseDto.toModel(ScreenBO.class , param);
		screenService.insertObject(screenBO);
	}
	
	/**
	  * 增加数据并上传图片
	  */
	public void insertObjectAndUpload(Dto param,HttpServletRequest request) {
		logger.debug("<======ScreenAction--insertObjectAndUpload======>");
		//上传图片
		String realUrl = null;
		String upLoadPath = CommonUtil.uploadFilePath();
		try {
			realUrl = UploadFile.uploadFile(request,upLoadPath,"SCREEN/"+param.getAsString("screenCode").substring(0,4),param.getAsString("screenCode").substring(0,4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(realUrl!=null && !"".equals(realUrl)){
			param.put("screenImg", realUrl);
		}
		ScreenBO screenBO = BaseDto.toModel(ScreenBO.class , param);
		screenService.insertObject(screenBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======ScreenAction--updateScreen======>");
		
		ScreenBO screenBO = BaseDto.toModel(ScreenBO.class , param);
		screenService.updateObject(screenBO);
	}
	
	/**
     * 修改数据并上传图片
     */
	public void updateObjectAndUpload(Dto param,HttpServletRequest request){
		logger.debug("<======ScreenAction--updateObjectAndUpload======>");
		//上传图片
		String realUrl = null;
		String upLoadPath = CommonUtil.uploadFilePath();
		try {
			realUrl = UploadFile.uploadFile(request,upLoadPath,"SCREEN/"+param.getAsString("screenCode").substring(0,4),param.getAsString("screenCode").substring(0,4));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(realUrl!=null && !"".equals(realUrl)){
			param.put("screenImg", realUrl);
		}
		ScreenBO screenBO = BaseDto.toModel(ScreenBO.class , param);
		screenService.updateObject(screenBO);
	}


	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======ScreenAction--deleteScreen======>");
		
		ScreenBO screenBO = BaseDto.toModel(ScreenBO.class , param);
		screenService.deleteObject(screenBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======ScreenAction--findOneScreen======>");
		
		ScreenBO screenBO = BaseDto.toModel(ScreenBO.class , param);
		return screenService.findOne(screenBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======ScreenAction--findAllScreen======>");
				
		return screenService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======ScreenAction--queryScreenForPage======>");
		
		return screenService.queryForPage(currentPage);
	}	
	
	/**
     * 查询所有数据根据spotId
     */
	public List<Dto> findAllBySpotId(Dto param) {
		logger.debug("<======ScreenAction--findAllBySpotId======>");
				
		return screenService.findAllBySpotId(param);
	} 
}
