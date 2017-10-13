package com.taikang.udp.sys.controller;

import java.util.Map;
import java.util.List;
import org.springframework.stereotype.Controller;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import java.util.HashMap;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.web.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
import com.taikang.udp.sys.action.ILogAction;


/**
  * LogController
  */
@Controller("logController")
@RequestMapping(value="/log")
public class LogController  extends BaseController  {
		
	@Resource(name=ILogAction.ACTION_ID)
	private ILogAction logAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showLogIndexPage() {
		return "/sys/log/logIndex"; 
	}
	
	/**
	 * 按条件查询日志信息列表<br/>
	 * @param request
	 * @param page
	 * @return   
	 * Map<String,Object>
	 */
	@RequestMapping("/querylist")
	@ResponseBody
	public Map<String, Object> getBycomLogList(HttpServletRequest request,
	        CurrentPage page) {
		Map<String, Object> map = new HashMap<String, Object>();

		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = logAction.queryByconForPage(page);

		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());

		return map;
	}
	
	/**
	 * 打开详细信息展示页面<br/>
	 * @param rowId
	 * @param model
	 * @return   
	 * String
	 */
	@RequestMapping("show")
	public String showDetailPage(String rowId,Model model) {
		if(rowId!=null && !rowId.equals("")) {
			model.addAttribute("rowId",rowId );
		}
		return "/sys/log/logShow"; 
	}

	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getLogList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = logAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showLogEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "/sys/log/logEdit"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getLogById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("logId", rowId);
		return logAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveLogInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		Dto param = getParamAsDto(request);
		if(param.get("rowId") ==null ||"".equals(param.get("rowId")))
		{
			logAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			logAction.updateObject(param);
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
	public Map<String, String> deleteLog(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		logAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

