package com.taikang.udp.sys.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.action.IDictTypeAction;
import com.taikang.udp.sys.util.vo.Tree;


/**
  * DictTypeController
  */
@Controller("dictTypeController")
@RequestMapping(value="/sys/dictType")
public class DictTypeController  extends BaseController  {
		
	@Resource(name=IDictTypeAction.ACTION_ID)
	private IDictTypeAction dictTypeAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("/init")
	public String showDictTypeIndexPage() {
		return "sys/dict/dictTypeIndex"; 
	}
	
	/*
	 * 查询字典类别树形菜单
	 * */
	@RequestMapping("/getDictTypeTree")
	@ResponseBody
	public List<Tree> getDictTypeInfo(){	
		List<Tree> backList=new ArrayList<Tree>();
		
		Tree tree = new Tree();
		tree.setChildren(dictTypeAction.findAllDictType());
		tree.setText("所有类别");
		backList.add(tree);
			
		return backList;
	}

	/*
	 * 查询字典类别树形菜单
	 * */
	@RequestMapping("/getAllDictTypeTree")
	@ResponseBody
	public List<Tree> getAllDictTypeTree() {
		List<Tree> backList=new ArrayList<Tree>();
		Tree tree = new Tree();
		tree.setChildren(dictTypeAction.findAllDictTypeTree());
		tree.setText("所有类别");
		backList.add(tree);
			
		return backList;
	}

	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getDictTypeList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = dictTypeAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showDictTypeEditPage(String dictTypeId,Model model) {
		
		if(dictTypeId!=null && !dictTypeId.equals(""))
		{
			model.addAttribute("dictTypeId",dictTypeId );
		}
		
		return "sys/dict/dictTypeEdit"; 
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String toAddTypeEditPage() {
		return "sys/dict/dictTypeAdd"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getDictTypeById(@RequestParam("dictTypeId")String dictTypeId)
	{
		Dto param = new BaseDto();
		param.put("dictTypeId", dictTypeId);
		return dictTypeAction.findOne(param);
	}
	
	/**
	 * 保存新增的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/saveAdd")
	@ResponseBody
	private Map<String,String> saveDictTypeInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		Dto param = getParamAsDto(request);		
		dictTypeAction.insertObject(param);
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
	private Map<String,String> saveEditDictTypeInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		Dto param = getParamAsDto(request);		
		dictTypeAction.updateObject(param);
		map.put(MESSAGE_INFO, "修改成功！");		
		map.put(RTN_RESULT, "true");
		
		return map;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteDictType(@RequestParam("dictType") String dictType) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("dictTypeId", dictType);
		dictTypeAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

