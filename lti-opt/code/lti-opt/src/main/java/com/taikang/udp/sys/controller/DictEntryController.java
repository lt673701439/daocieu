package com.taikang.udp.sys.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;

import java.util.HashMap;

import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKStringUtils;

import javax.annotation.Resource;

import com.taikang.udp.framework.core.web.BaseController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;

import com.taikang.udp.sys.action.IDictEntryAction;
import com.taikang.udp.sys.util.DictUtils;
import com.taikang.udp.sys.util.Tree;


/**
  *数据字典管理
  */
@Controller("dictEntryController")
@RequestMapping(value="/sys/dictEntry")
public class DictEntryController  extends BaseController  {
		
	@Resource(name=IDictEntryAction.ACTION_ID)
	private IDictEntryAction dictEntryAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showDictEntryIndexPage() {
		return "sys/dict/dictEntryIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getDictEntryList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = dictEntryAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showDictEntryEditPage(String dictTypeId,String dictId,Model model) {
				
		if(!TKStringUtils.isNullOrEmpty(dictTypeId))
		{
			model.addAttribute("dictTypeId",dictTypeId);
		}
		if(!TKStringUtils.isNullOrEmpty(dictId))
		{
			model.addAttribute("dictId",dictId);
		}
		
		return "sys/dict/dictEntryEdit"; 
	}
	
	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("add")
	public String showDictEntryAddPage(String dictTypeId,Model model) {
				
		if(!TKStringUtils.isNullOrEmpty(dictTypeId))
		{
			model.addAttribute("dictTypeId",dictTypeId);
		}
				
		return "sys/dict/dictEntryAdd"; 
	}
	
	/**
	 * 打开用户管理类别页面
	 * @return
	 */
	@RequestMapping("/toDictEntry")
	public String toDictEntry() {
		
		return "sys/dict/dictTypeIndex"; 
	}
	
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getDictEntryById(@RequestParam("dictTypeId")String dictTypeId,@RequestParam("dictId")String dictId)
	{
		Dto param = new BaseDto();
		param.put("dictTypeId", dictTypeId);
		param.put("dictId", dictId);
		return dictEntryAction.findOne(param);
	}
	
	/**
	 * 保存新增，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	private Map<String,String> saveDictEntryAdd(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		Dto param = getParamAsDto(request);
		
		dictEntryAction.insertObject(param);
		
		map.put(MESSAGE_INFO, "新增成功！");
		map.put(RTN_RESULT, "true");						
		
		return map;
	}
	/**
	 * 保存修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/saveEdit")
	@ResponseBody
	private Map<String,String> saveDictEntryEdit(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		Dto param = getParamAsDto(request);
		
		dictEntryAction.updateObject(param);
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "更新成功！");		
		
		return map;
	}
			
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteDictEntry(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = getParamAsDto(request);
		
		dictEntryAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
	
	/**
	 * 获得所有区域
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/getAreaList")
    public void getAreaList(String site,HttpServletResponse response)throws IOException{
    	   response.setContentType("text/json; charset=UTF-8");
		   response.setHeader("Cache-Control", "no-cache");
		   Dto dictEntry = new BaseDto();
		   if (site!=null && !"".equals(site)) {
			   dictEntry.put("parent_id", site);//父ID
		   }
		   dictEntry.put("dict_type_id", "AREACODE");//区域编码
	       List<Dto> dictEntryList = dictEntryAction.findAll(dictEntry);
		   List<String> listZtree = new ArrayList<String>();
			String treeNodes = "";
			//封装成json串，返回前台
		   if(dictEntryList.size() > 0){
		   for(int i = 0 ; i < dictEntryList.size(); i ++){
			   treeNodes = "{'dictId':'"+dictEntryList.get(i).getAsString("Dict_ID")+"','dictName':'"+dictEntryList.get(i).getAsString("Dict_Name")+"'}";
			   listZtree.add(treeNodes);
		   		}
		   }
		   JSONArray json = JSONArray.fromObject(listZtree);
		   response.getWriter().print(json); 
		   response.getWriter().flush();
		   response.getWriter().close();
    }
}

