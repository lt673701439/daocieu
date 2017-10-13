package com.liketry.interaction.opt.benison.service;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * IBenisonService
  */
 
 public interface IBenisonService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "benisonService";  
 	
 	public List<Dto> findAllBySpotId(Dto param);
 }
 
 
 