package com.liketry.interaction.opt.chart.controller;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liketry.interaction.opt.order.action.IOrderAction;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;

import net.sf.json.JSONArray;


/**
  * OrderController
  */
@Controller("screenTimeOrderController")
@RequestMapping(value="/screenTimeOrder")
public class ScreenTimeOrderController  extends BaseController  {
		
	@Resource(name=IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
		
	
	/**
	 * 打开屏幕时间段订单页面
	 * @return 页面地址
	 */
	@RequestMapping("/showScreenTimeOrder")
	public String showScreenTimeOrder() {
		return "chart/screenTimeOrder";
	}
	
	/**
	 *获取图订单汇总数据
	 */
	@RequestMapping(value="/getOrderCountForPic")
	@ResponseBody
	public List<Dto> getOrderCountForPic(String nameBy) {
		Dto order = new BaseDto();
		order.put("nameBy", nameBy);
		//查询各屏幕下的订单总数
		List<Dto> nameOrderList = orderAction.findOrderCount4Name(order);
		
		if(nameOrderList != null && nameOrderList.size()>0){
			//创建时间段列表
			List<Dto> timeList = getColumns();
			//查出该屏幕下，各时间段的订单数
			nameOrderList.forEach(orderCount->{
				
				List<Dto> timeOrderList = new ArrayList<Dto>();
				
				timeList.forEach(time->{
					time.put("screenId", orderCount.get("screenId"));
					Dto timeOrder = new BaseDto();
					timeOrder.put("timeQuantum", time.get("timeQuantum"));
					timeOrder.put("total", orderAction.findOneTimeOrderCount(time));
					timeOrderList.add(timeOrder);
				});
				orderCount.put("timeOrderList", timeOrderList);
			});
		}
		logger.info("<======returnParam==="+JSONArray.fromObject(nameOrderList)+"=========>");
		return nameOrderList;
	}
	
	/**
	 *获取表订单汇总数据
	 */
	@RequestMapping(value="/getOrderCountForData")
	@ResponseBody
	public Map<String, Object> getOrderCountForData(CurrentPage page) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Dto> columnList = getColumns();
		//将列做为条件
		Dto columns = new BaseDto();
		columns.put("columns", columnList);
		page.setParamObject(columns);
		//查询订单中得播放时间列表
		CurrentPage screenTimeOrderPage = orderAction.findScreenTimeOrderList(page);
		List<Dto> screenTimeOrderList = screenTimeOrderPage.getPageItems();
		
		//查询订单总数
		Dto param = new BaseDto();
		int count = orderAction.getAllCount(param);
		
		if(screenTimeOrderList!= null &&screenTimeOrderList.size()>0){
			
			screenTimeOrderList.forEach(screenTimeOrder->{
				//如果为null则，设置为0
				screenTimeOrder.put("total0", StringUtils.isEmpty(screenTimeOrder.get("total0"))?0:screenTimeOrder.getAsInteger("total0"));
				screenTimeOrder.put("total1", StringUtils.isEmpty(screenTimeOrder.get("total1"))?0:screenTimeOrder.getAsInteger("total1"));
				screenTimeOrder.put("total2", StringUtils.isEmpty(screenTimeOrder.get("total2"))?0:screenTimeOrder.getAsInteger("total2"));
				screenTimeOrder.put("total3", StringUtils.isEmpty(screenTimeOrder.get("total3"))?0:screenTimeOrder.getAsInteger("total3"));
				screenTimeOrder.put("total4", StringUtils.isEmpty(screenTimeOrder.get("total4"))?0:screenTimeOrder.getAsInteger("total4"));
				screenTimeOrder.put("total5", StringUtils.isEmpty(screenTimeOrder.get("total5"))?0:screenTimeOrder.getAsInteger("total5"));
				screenTimeOrder.put("total6", StringUtils.isEmpty(screenTimeOrder.get("total6"))?0:screenTimeOrder.getAsInteger("total6"));
				screenTimeOrder.put("total7", StringUtils.isEmpty(screenTimeOrder.get("total7"))?0:screenTimeOrder.getAsInteger("total7"));
				screenTimeOrder.put("total8", StringUtils.isEmpty(screenTimeOrder.get("total8"))?0:screenTimeOrder.getAsInteger("total8"));
				screenTimeOrder.put("total9", StringUtils.isEmpty(screenTimeOrder.get("total9"))?0:screenTimeOrder.getAsInteger("total9"));
				//求出每一时间段订单数的占比
				screenTimeOrder.put("rate0", percent(StringUtils.isEmpty(screenTimeOrder.get("total0"))?0:screenTimeOrder.getAsInteger("total0"),count));
				screenTimeOrder.put("rate1", percent(StringUtils.isEmpty(screenTimeOrder.get("total1"))?0:screenTimeOrder.getAsInteger("total1"),count));
				screenTimeOrder.put("rate2", percent(StringUtils.isEmpty(screenTimeOrder.get("total2"))?0:screenTimeOrder.getAsInteger("total2"),count));
				screenTimeOrder.put("rate3", percent(StringUtils.isEmpty(screenTimeOrder.get("total3"))?0:screenTimeOrder.getAsInteger("total3"),count));
				screenTimeOrder.put("rate4", percent(StringUtils.isEmpty(screenTimeOrder.get("total4"))?0:screenTimeOrder.getAsInteger("total4"),count));
				screenTimeOrder.put("rate5", percent(StringUtils.isEmpty(screenTimeOrder.get("total5"))?0:screenTimeOrder.getAsInteger("total5"),count));
				screenTimeOrder.put("rate6", percent(StringUtils.isEmpty(screenTimeOrder.get("total6"))?0:screenTimeOrder.getAsInteger("total6"),count));
				screenTimeOrder.put("rate7", percent(StringUtils.isEmpty(screenTimeOrder.get("total7"))?0:screenTimeOrder.getAsInteger("total7"),count));
				screenTimeOrder.put("rate8", percent(StringUtils.isEmpty(screenTimeOrder.get("total8"))?0:screenTimeOrder.getAsInteger("total8"),count));
				screenTimeOrder.put("rate9", percent(StringUtils.isEmpty(screenTimeOrder.get("total9"))?0:screenTimeOrder.getAsInteger("total9"),count));
			});
			
		}
		
		logger.info("<======returnParam==="+JSONArray.fromObject(screenTimeOrderList)+"=========>");
		map.put("total", screenTimeOrderPage.getTotalRows());
		map.put("rows", screenTimeOrderList);
		
		return map;
	}
	
	/**
	 * 获取时间列
	 * @return
	 */
	public List<Dto> getColumns(){
		
		//时间列表（前端页面需要固定时间列数量，所以这里暂时写死）
		List<Dto> timeOrderList = new ArrayList<Dto>();
		
		Dto time1 = new BaseDto();
		time1.put("timeStart", "08:00");
		time1.put("timeEnd", "09:00");
		time1.put("timeQuantum", "08:00-09:00");
		timeOrderList.add(time1);
		Dto time2 = new BaseDto();
		time2.put("timeStart", "09:00");
		time2.put("timeEnd", "10:00");
		time2.put("timeQuantum", "09:00-10:00");
		timeOrderList.add(time2);
		Dto time3 = new BaseDto();
		time3.put("timeStart", "10:00");
		time3.put("timeEnd", "11:00");
		time3.put("timeQuantum", "10:00-11:00");
		timeOrderList.add(time3);
		Dto time4 = new BaseDto();
		time4.put("timeStart", "11:00");
		time4.put("timeEnd", "12:00");
		time4.put("timeQuantum", "11:00-12:00");
		timeOrderList.add(time4);
		Dto time5 = new BaseDto();
		time5.put("timeStart", "12:00");
		time5.put("timeEnd", "13:00");
		time5.put("timeQuantum", "12:00-13:00");
		timeOrderList.add(time5);
		Dto time6 = new BaseDto();
		time6.put("timeStart", "13:00");
		time6.put("timeEnd", "14:00");
		time6.put("timeQuantum", "13:00-14:00");
		timeOrderList.add(time6);
		Dto time7 = new BaseDto();
		time7.put("timeStart", "14:00");
		time7.put("timeEnd", "15:00");
		time7.put("timeQuantum", "14:00-15:00");
		timeOrderList.add(time7);
		Dto time8 = new BaseDto();
		time8.put("timeStart", "15:00");
		time8.put("timeEnd", "16:00");
		time8.put("timeQuantum", "15:00-16:00");
		timeOrderList.add(time8);
		Dto time9 = new BaseDto();
		time9.put("timeStart", "16:00");
		time9.put("timeEnd", "17:00");
		time9.put("timeQuantum", "16:00-17:00");
		timeOrderList.add(time9);
		Dto time10 = new BaseDto();
		time10.put("timeStart", "17:00");
		time10.put("timeEnd", "18:00");
		time10.put("timeQuantum", "17:00-18:00");
		timeOrderList.add(time10);
		
		return timeOrderList;
	}
	
	/**
	 * 计算百分比
	 * @param p1
	 * @param p2
	 * @return
	 */
	static String percent(int p1,int p2){
		
		  String str;
		  
		  double p3=(double)p1/(double)p2;
		  NumberFormat nf=NumberFormat.getPercentInstance();
		  nf.setMinimumFractionDigits(2);
		  str=nf.format(p3); 
		  
		  return str;
	}
	
}

