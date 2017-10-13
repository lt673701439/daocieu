package com.taikang.udp.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.model.DictEntryBO;
import com.taikang.udp.sys.util.DictUtils;

@Controller("commonController")
@RequestMapping(value="")
public class CommonController extends BaseController {
		
	@Autowired
	HttpServletRequest request;
	
	@RequestMapping("/common/error/app-error")
	public String appErrorPage(){
		return "common/error/app-error";
	}
	
	@RequestMapping("/common/error/500")
	public String to500Page(){
		return "common/error/500";
	}
	
	@RequestMapping("/common/error/404")
	public String to404Page(){
		return "common/error/404";
	}
	
	@RequestMapping("/common/error/403")
	public String to403Page(){
		return "common/error/403";
	}
	
	/*
	@RequestMapping("/getDictlist")
	@ResponseBody
	public List<DictEntry> getDictlist(String dicttypeid)
	{
		if(dicttypeid !=null && !dicttypeid.equals(""))
    	{   
    		try{
    			DictManager dictManager=(DictManager)SpringContextHolder.getBean("dictManager");        		  
        		Map<String, List<DictEntry>> map=dictManager.getDictEntryMap();
        		List<DictEntry> list =map.get(dicttypeid);
        		
        		return list;
    		}catch(Exception e)
    		{
    			e.printStackTrace();
    			return null;
    		}
    	}
		else
			return null;
	}*/
}
