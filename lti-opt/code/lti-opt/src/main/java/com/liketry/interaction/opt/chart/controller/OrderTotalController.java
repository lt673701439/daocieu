package com.liketry.interaction.opt.chart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@Controller("orderTotalController")
@RequestMapping(value="/orderTotal")
public class OrderTotalController  extends BaseController  {
		
	@Resource(name=IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
		
	
	/**
	 * 打开订单汇总统计页面
	 * @return 页面地址
	 */
	@RequestMapping("/showOrderCount")
	public String showOrderCountPage(Model model) {
		Dto param = new BaseDto();
		model.addAttribute("columns",getColumns(param).toJson());
		return "chart/orderCount";
	}
	
	/**
	 * 打开收入汇总统计页面
	 * @return 页面地址
	 */
	@RequestMapping("/showOrderPriceCount")
	public String showOrderPriceCountPage(Model model) {
		Dto param = new BaseDto();
		model.addAttribute("columns",getColumns(param).toJson());
		return "chart/orderPriceCount";
	}
	
	/**
	 * 动态获取页面显示的列名称
	 * @return
	 */
	public Dto getColumns(Dto param){
		
		Dto map = new BaseDto();
		//查询第一行列的名称
		List<Dto> firstColumsList = orderAction.findFirstColumns(param);
		//查询第二行列的名称
		List<Dto> secondColumsList = orderAction.findSecondColumns(param);
		map.put("firstColumsList", firstColumsList);
		map.put("secondColumsList", secondColumsList);
		
		return map;
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
		List<Dto> nameOrderList = orderAction.findOrderCountList2Name(order);
		if(nameOrderList != null && nameOrderList.size()>0){
			//查出该屏幕下，各节日下的订单数
			nameOrderList.forEach(orderCount->{
				Dto param = new BaseDto();
				param.put("screenId",orderCount.get("screenId"));
				param.put("nameBy",nameBy);
				List<Dto> typeOrderList = orderAction.findOrderCountList2Type(param);
				orderCount.put("typeOrderList", typeOrderList);
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
	public Map<String, Object> getOrderCountForData(HttpServletRequest request,CurrentPage page) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		List<Dto> footerList = new ArrayList<Dto>();//页脚列表
		Dto queryParam = getParamAsDto(request);//请求参数
		
		page.setParamObject(queryParam);//请求条件包括分页信息
		
		//查询订单中得播放时间列表
		CurrentPage timeOrderPage = orderAction.findTimeOrderList(page);
		List<Dto> timeOrderList = timeOrderPage.getPageItems();
		
		if(timeOrderList!= null && timeOrderList.size()>0){
			
			//节目名称list及field
			List<Dto> secondColumsList = orderAction.findSecondColumns(queryParam);
			
			//组装数据
			for(Dto timeOrder:timeOrderList){
				
				if(timeOrder!=null){
					
					Dto param = new BaseDto();
					param.put("playDate", timeOrder.getAsString("playDate"));
					param.put("nameBy", queryParam.get("nameBy"));
					param.put("secondColumsList", secondColumsList);
					//查询该播放时间的各屏幕的各类型订单
					List<Dto> typeOrderList = orderAction.findTypeOrderList(param);
					
					//将订单放入时间列表中
					if(typeOrderList != null && typeOrderList.size()>0){
						if(typeOrderList.get(0)!=null){
							timeOrder.putAll(typeOrderList.get(0));
						}
					}
					
				}
			}
			
			//组装页脚数据
			Dto param = new BaseDto();
			param.put("nameBy", queryParam.get("nameBy"));
			param.put("secondColumsList", secondColumsList);
			footerList = orderAction.findTypeOrderList(param);
			if(footerList!=null&& footerList.size()>0){
				footerList.forEach(footerDto->{
					footerDto.put("playDate", "合计");
				});
			}
		}
		
		logger.info("<======returnParam==="+JSONArray.fromObject(timeOrderList)+"=========>");
		map.put("total", timeOrderPage.getTotalRows());
		map.put("rows", timeOrderList);
		map.put("footer", footerList);
		
		return map;
	}
	
}

