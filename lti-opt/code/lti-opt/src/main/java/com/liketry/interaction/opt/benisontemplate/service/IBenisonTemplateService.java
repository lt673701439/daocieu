package com.liketry.interaction.opt.benisontemplate.service;

import java.util.Arrays;
import java.util.List;

import com.liketry.interaction.opt.benisontemplate.model.BenisonTemplateBO;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * IBenisonTemplateService
  */
 
 public interface IBenisonTemplateService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "benisonTemplateService";  	
 	
 	public List<Dto> findAllByBenisonId(Dto param);
 	public List<Dto> findAllByImgId(Dto param);
 	public List<Dto> findAllByTypeId(Dto param);
 	public List<Dto> findAllByScreenId(Dto param);
 	public BenisonTemplateBO findLastOne(BenisonTemplateBO benisonTemplate);
 	public BenisonTemplateBO findOneBenisonTemplate(BenisonTemplateBO benisonTemplate);
 }
 
 
 