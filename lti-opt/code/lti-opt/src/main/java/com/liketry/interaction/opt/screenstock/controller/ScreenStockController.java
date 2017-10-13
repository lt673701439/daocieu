package com.liketry.interaction.opt.screenstock.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;

import java.util.HashMap;
import org.springframework.stereotype.Controller;

import com.liketry.interaction.opt.commodity.action.ICommodityAction;
import com.liketry.interaction.opt.screen.action.IScreenAction;
import com.liketry.interaction.opt.screenstock.action.IScreenStockAction;
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


/**
  * ScreenStockController
  */
@Controller("screenStockController")
@RequestMapping(value="/screenStock")
public class ScreenStockController  extends BaseController  {
		
	@Resource(name=IScreenStockAction.ACTION_ID)
	private IScreenStockAction screenStockAction;
	
	@Resource(name=ICommodityAction.ACTION_ID)
	private ICommodityAction commodityAction;
	
	@Resource(name=IScreenAction.ACTION_ID)
	private IScreenAction screenAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showScreenStockIndexPage() {
		return "screenStock/screenStockIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getScreenStockList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = screenStockAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showScreenStockAddPage() {
		return "screenStock/screenStockAdd"; 
	}

	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showScreenStockEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("stockId",rowId );
		}
		
		return "screenStock/screenStockEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showScreenStockViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("stockId",rowId );
		}
		
		return "screenStock/screenStockView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getScreenStockById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("stockId", rowId);
		return screenStockAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveScreenStockInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		// 校验
		if(!checkDate(param.get("startDate").toString(), param.get("endDate").toString(), map) // 日期校验
				|| !checkTime(param.get("startTime").toString(), param.get("endTime").toString(), map) // 时间校验	
				|| !checkRepeat(param, map)){ // 重叠时间校验 param里要包含：屏幕ID、开始结束日期和时间
			return map;
		}
					
		if(param.get("stockId") ==null ||"".equals(param.get("stockId")))
		{
			param.put("stockId", UUID.randomUUID().toString().replace("-", ""));
			param.put("createdTime", TKDateTimeUtils.getCurrentDate());
			param.put("createdBy", loginId);
			param.put("version", 1);
			param.put("delflag", 1);
			screenStockAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
			
		}
		else
		{
			Dto params = new BaseDto();
			params.put("stockId", param.get("stockId"));
			Dto oldOne = screenStockAction.findOne(params);
			
			if(oldOne!=null){
				// 更新数据
//				oldOne.put("startDate", param.get("startDate"));
//				oldOne.put("endDate", param.get("endDate"));
//				oldOne.put("startTime", param.get("startTime"));
//				oldOne.put("endTime", param.get("endTime"));
				oldOne.put("modifiedTime", TKDateTimeUtils.getCurrentDate());
				oldOne.put("modifiedBy", loginId);
				oldOne.put("version", oldOne.getAsInteger("version")+1);
				
				screenStockAction.updateObject(param);
				map.put(MESSAGE_INFO, "更新成功！");
			}
		}
		map.put(RTN_RESULT, "true");
		
		return map;
	}
	
	/**
	 *  日期校验
	 * @param startDateStr
	 * @param endDateStr
	 * @param map
	 * @return
	 */
	private boolean checkDate(String startDateStr, String endDateStr, Map<String,String> map){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date startDate = dateFormat.parse(startDateStr);
			Date endDate = dateFormat.parse(endDateStr);
			// 如果开始日期大于结束日期，则返回
			if(startDate.compareTo(endDate)>0){
				map.put(MESSAGE_INFO, "开始日期大于了结束日期，请修改！");
				map.put(RTN_RESULT, "false");
				return false;
			}
		} catch (ParseException e) {
			map.put(MESSAGE_INFO, "开始日期与结束日期填写有误，请重新填写！");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		return true;
	}
	
	/**
	 *  时间校验
	 * @param startTimeStr
	 * @param endTimeStr
	 * @param map
	 * @return
	 */
	private boolean checkTime(String startTimeStr, String endTimeStr, Map<String,String> map){
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		try {
			Date startTime = timeFormat.parse(startTimeStr);
			Date endTime = timeFormat.parse(endTimeStr);
			// 如果开始时间大于结束时间，则返回
			if(startTime.compareTo(endTime)>=0){
				map.put(MESSAGE_INFO, "开始时间大于等于了结束时间，请修改！");
				map.put(RTN_RESULT, "false");
				return false;
			}
		} catch (ParseException e) {
			map.put(MESSAGE_INFO, "开始时间与结束时间填写有误，请重新填写！");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		return true;
	}
	
	/**
	 *  重叠时间校验 map里要包含：屏幕ID、开始结束日期和时间
	 * @param param
	 * @param map
	 * @return
	 */
	private boolean checkRepeat(Dto param, Map<String,String> map){
		
		// 校验数据是否有问题
		if(param.get("screenId") == null || param.get("startDate") == null || param.get("endDate") == null
				|| param.get("startTime") == null || param.get("endTime") == null){
			
			map.put(MESSAGE_INFO, "屏幕、日期、时间填写有误，请重新填写！");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		// 查询重叠商品数据
		List<Dto> commdityList = commodityAction.findRepeatByDateAndTime(param);
		
		if(commdityList != null && commdityList.size() > 0){
			Dto repeat = commdityList.get(0);
			String commodityCode = repeat.getAsString("commodity_code");
			String startDate = repeat.getAsString("start_date");
			String endDate = repeat.getAsString("end_date");
			String startTime = repeat.getAsString("start_time");
			String endTime = repeat.getAsString("end_time");
			
			map.put(MESSAGE_INFO, "屏幕、日期、时间与其它商品冲突，请重新填写！<br>"
					+ "【冲突商品编号："+commodityCode+"】<br>"
					+ "【开始结束日期："+startDate+" ~ "+endDate+"】<br>"
					+ "【开始结束时间："+startTime+" ~ "+endTime+"】");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		//查询重叠的屏幕库存数据
		List<Dto> screenStockList = screenStockAction.findRepeatByDateAndTime(param);
				
				if(screenStockList != null && screenStockList.size() > 0){
					Dto repeat = screenStockList.get(0);
					String screenCode = repeat.getAsString("screen_code");
					String startDate = repeat.getAsString("start_date");
					String endDate = repeat.getAsString("end_date");
					String startTime = repeat.getAsString("start_time");
					String endTime = repeat.getAsString("end_time");
					
					map.put(MESSAGE_INFO, "屏幕、日期、时间与其它屏幕冲突，请重新填写！<br>"
							+ "【冲突屏幕编号："+screenCode+"】<br>"
							+ "【开始结束日期："+startDate+" ~ "+endDate+"】<br>"
							+ "【开始结束时间："+startTime+" ~ "+endTime+"】");
					map.put(RTN_RESULT, "false");
					return false;
				}
		return true;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteScreenStock(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = new BaseDto();
		param.put("stockId", rowId);
		
		Dto oldOne = screenStockAction.findOne(param);
		
		if(oldOne!=null){
			// 更新数据
			oldOne.put("modifiedTime", TKDateTimeUtils.getCurrentDate());
			oldOne.put("modifiedBy", loginId);
			oldOne.put("version", oldOne.getAsInteger("version")+1);
			oldOne.put("delflag",0);
			
			screenStockAction.updateObject(oldOne);
		}
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

