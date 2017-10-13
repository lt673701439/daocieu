package com.liketry.interaction.opt.benisontype.controller;

import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.sequence.BusinessSeqGenerator;
import com.taikang.udp.sys.util.vo.LoginUser;

import java.util.Map;
import java.util.UUID;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.liketry.interaction.opt.benison.action.IBenisonAction;
import com.liketry.interaction.opt.benisontemplate.action.IBenisonTemplateAction;
import com.liketry.interaction.opt.benisontype.action.IBenisonTypeAction;


/**
  * BenisonTypeController
  */
@Controller("benisonTypeController")
@RequestMapping(value="/benisonType")
public class BenisonTypeController  extends BaseController  {
		
	@Resource(name=IBenisonTypeAction.ACTION_ID)
	private IBenisonTypeAction benisonTypeAction;
	
	@Resource(name=IBenisonAction.ACTION_ID)
	private IBenisonAction benisonAction;
	
	@Resource(name=IBenisonTemplateAction.ACTION_ID)
	private IBenisonTemplateAction benisonTemplateAction;
	
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showBenisonTypeIndexPage() {
		return "benisonType/benisonTypeIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getBenisonTypeList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = benisonTypeAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showBenisonTypeAddPage() {
		
		return "benisonType/benisonTypeAdd"; 
	}

	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showBenisonTypeEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("typeId",rowId );
		}
		
		return "benisonType/benisonTypeEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showBenisonTypeViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("typeId",rowId );
		}
		
		return "benisonType/benisonTypeView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getBenisonTypeById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("typeId", rowId);
		return benisonTypeAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveBenisonTypeInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		Dto param = getParamAsDto(request);
		if(param.get("typeId") ==null ||"".equals(param.get("typeId")))
		{
			param.put("typeId", UUID.randomUUID().toString().replace("-", ""));
			param.put("typeCode", "BYP"+BusinessSeqGenerator.getInstance("type_code").nextId());
			benisonTypeAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			Dto params = new BaseDto();
			params.put("typeId", param.get("typeId"));
			Dto oldOne = benisonTypeAction.findOne(params);
			
			if(oldOne!=null){
				// 更新数据
				oldOne.put("typeName", param.get("typeName"));
				oldOne.put("effectFlag", param.get("effectFlag"));
				oldOne.put("sortNum", param.get("sortNum"));
				
				benisonTypeAction.updateObject(oldOne);
				map.put(MESSAGE_INFO, "更新成功！");
			}
		}
		map.put(RTN_RESULT, "true");
		
		return map;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteBenisonType(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("typeId", rowId);
		
		//查询是否有关联祝福语数据
		List<Dto> benisonlist = benisonAction.findAllBySpotId(param);
		//查询是否有关联模板数据
		List<Dto> benisonTemplatelist = benisonTemplateAction.findAllByTypeId(param);
		
		if(benisonlist!=null && benisonlist.size()>0){
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "该祝福语类型与祝福语有绑定，不能删除！");
		}else if(benisonTemplatelist!=null && benisonTemplatelist.size()>0){
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "该祝福语类型与模板有绑定，不能删除！");
		}else{
			benisonTypeAction.deleteObject(param);
			map.put(RTN_RESULT, "true");
			map.put(MESSAGE_INFO, "操作成功！");
		}
		
		return map;
	}
}

