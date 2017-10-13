package com.liketry.interaction.opt.confcustom.controller;

import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;

import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;

import java.util.Map;
import java.util.UUID;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.liketry.interaction.opt.confcustom.action.IConfCustomAction;


/**
  * ConfCustomController
  */
@Controller("confCustomController")
@RequestMapping(value="/confCustom")
public class ConfCustomController  extends BaseController  {
		
	@Resource(name=IConfCustomAction.ACTION_ID)
	private IConfCustomAction confCustomAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showConfCustomIndexPage() {
		return "confCustom/confCustomIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getConfCustomList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = confCustomAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("view")
	public String showConfCustomViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("customId",rowId);
		}
		
		return "confCustom/confCustomView"; 
	}
	
	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showConfCustomEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("customId",rowId);
		}
		
		return "confCustom/confCustomEdit"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getConfCustomById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("customId", rowId);
		return confCustomAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveConfCustomInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		LoginUser user = UserUtils.getUser();
		
		Dto param = getParamAsDto(request);
		if(param.get("customId") ==null ||"".equals(param.get("customId")))
		{
			param.put("customId", UUID.randomUUID().toString().replace("-", ""));
			param.put("createdTime", TKDateTimeUtils.getTodayDateTime());
			param.put("createdBy", user.getUserId());
			param.put("version", 1);
			param.put("delflag", 1);
			confCustomAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			Dto params = new BaseDto();
			params.put("customId", param.get("customId"));
			Dto oldOne = confCustomAction.findOne(params);
			
			if(oldOne!=null){
				oldOne.put("customType", param.get("customType"));
				oldOne.put("customPrice", param.get("customPrice"));
				oldOne.put("modifiedTime", TKDateTimeUtils.getTodayDateTime());
				oldOne.put("modifiedBy", user.getUserId());
				oldOne.put("version", oldOne.getAsInteger("version")+1);
				confCustomAction.updateObject(oldOne);
			}
			map.put(MESSAGE_INFO, "更新成功！");
		}
		map.put(RTN_RESULT, "true");
		
		return map;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteConfCustom(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("customId", rowId);
		confCustomAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

