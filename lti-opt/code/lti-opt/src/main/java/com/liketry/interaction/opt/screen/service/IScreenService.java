package com.liketry.interaction.opt.screen.service;

import java.util.Arrays;
import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.service.IBaseService;

/**
  * IScreenService
  */
 
 public interface IScreenService<T> extends IBaseService<T>{
 
 	final String SERVICE_ID = "screenService";  	
 	
 	public List<Dto> findAllBySpotId(Dto param);
 }
 
 
 