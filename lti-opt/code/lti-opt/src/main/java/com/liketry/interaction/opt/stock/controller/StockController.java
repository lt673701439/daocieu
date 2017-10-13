package com.liketry.interaction.opt.stock.controller;

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

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;

import java.util.Map;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.liketry.interaction.opt.orderdetail.action.IOrderDetailAction;
import com.liketry.interaction.opt.stock.action.IStockAction;
import com.liketry.interaction.opt.stock.model.StockBO;


/**
  * StockController
  */
@Controller("stockController")
@RequestMapping(value="/stock")
public class StockController  extends BaseController  {
		
	@Resource(name=IStockAction.ACTION_ID)
	private IStockAction stockAction;
	
	@Resource(name=IOrderDetailAction.ACTION_ID)
	private IOrderDetailAction orderDetailAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showStockIndexPage() {
		return "stock/stockIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getStockList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = stockAction.queryForPage(page);
		
		//根据播放日期和起始时间判断该库存的商品的下架
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		List<Dto> stockList = currentPage.getPageItems();
		try {
			for(Dto stock:stockList){
				stock.put("commodity_status", 1);
				if(dateFormat.parse(TKDateTimeUtils.getCurrentDate()).compareTo(dateFormat.parse(stock.getAsString("stock_date")))>0){
					//如果当前日期大于播放日期，则令商品状态为下架
					stock.put("commodity_status", 2);
				}else if(dateFormat.parse(TKDateTimeUtils.getCurrentDate()).compareTo(dateFormat.parse(stock.getAsString("stock_date")))==0){
					//日期相同，比较时间
					if(timeFormat.format(TKDateTimeUtils.getTodayDateTime()).compareTo(stock.getAsString("shelf_time"))>0){
						//如果当前时间小于商品的下架时间,则令商品状态为下架
						stock.put("commodity_status", 2);
					}
				}
				//查询该商品的导出时间及导出人
				Dto orderDetail = new BaseDto();
				orderDetail.put("commodity_id", stock.get("commodity_id"));
				orderDetail.put("play_date", stock.get("stock_date"));
				//查询已播放和已支付的订单详情
				List<Dto> orderDetailList = orderDetailAction.findEffectOrderDetailList(orderDetail);
				if(orderDetailList!=null && orderDetailList.size()>0){
					stock.put("export_by", orderDetailList.get(0).get("export_by"));	
					stock.put("export_time", orderDetailList.get(0).get("export_time"));	
				}
			}	
		} catch (ParseException e) {
			e.printStackTrace();
		}
		map.put("rows", stockList);
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showStockEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "stock/stockEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 * String
	 */
	@RequestMapping("/view")
	public String showStockViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId);
		}
		
		return "stock/stockView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getStockById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("stockId", rowId);
		return stockAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveStockInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		// 修改信息
		Dto param = getParamAsDto(request);
		if(param.get("stockId") != null && !"".equals(param.get("stockId"))){
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("stockId", param.get("stockId"));
			Dto oldOne = stockAction.findOne(params);
			
			// 更新数据
			oldOne.put("stockStatus", param.get("stockStatus"));
			oldOne.put("sales", param.get("sales"));
			oldOne.put("stock", param.get("stock"));
			oldOne.put("scheduleStatus", param.get("scheduleStatus"));
			
			oldOne.put("modified_by", loginId);
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", oldOne.getAsInteger("version")+1);
			
			stockAction.updateObject(oldOne);
			map.put(MESSAGE_INFO, "修改成功！");
			map.put(RTN_RESULT, "true");
		}else {
			map.put(MESSAGE_INFO, "登录用户不存在！");
			map.put(RTN_RESULT, "false");
		}
		
		return map;
		
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteStock(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		stockAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

