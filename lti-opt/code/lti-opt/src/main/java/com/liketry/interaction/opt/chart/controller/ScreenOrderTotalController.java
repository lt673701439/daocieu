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
 * testChart
 */
@Controller("screenOrderTotalController")
@RequestMapping(value="/screenOrderTotal")
public class ScreenOrderTotalController extends BaseController  {
	
	@Resource(name=IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
	
	/**
	 * 打开屏幕订单统计页面
	 * @return 页面地址
	 */
	@RequestMapping("/showScreenOrderTotal")
	public String showScreenOrderTotalPage(Model model) {
		
		Dto param = new BaseDto();
		
		return "chart/screenOrderTotal";
	}
	
	/**
	 * 打开屏幕订单价格页面
	 * @return 页面地址
	 */
	@RequestMapping("/showScreenOrderPrice")
	public String showScreenOrderPricePage(Model model) {
		
		Dto param = new BaseDto();
//		model.addAttribute("columns",getColumns(param).toJson());
		
		return "chart/screenPriceTotal";
	}
	
	/**
	 *获取图订单汇总数据
	 */
	@RequestMapping(value="/getOrderCountForPic")
	@ResponseBody
	public Dto getOrderCountForPic(String nameBy) {
		
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
				
				if(typeOrderList !=null && typeOrderList.size()>0){
					
					//查询出该屏幕，该类型下的各祝福语的订单数
					typeOrderList.forEach(typeOrder->{
						
						param.put("typeId",typeOrder.get("typeId"));
						List<Dto> benisonTypeOrderList = orderAction.findOrderCountList2Benison(param);
						typeOrder.put("benisonTypeOrderList", benisonTypeOrderList);
						
					});
				}
				
			});
		}
		
		Dto data = getPicForData(nameOrderList);
		
		if(!data.isEmpty()){
			logger.info("<======returnParam==="+data.toJson()+"=========>");
		}
		
		return data;
	}
	
	/**
	 * 组装hicharts图形数据
	 * @param nameOrderList
	 * @return
	 */
	public Dto getPicForData(List<Dto> nameOrderList) {
		
		Dto data = new BaseDto();
		
		List<Dto> screenList = new ArrayList<Dto>();//屏幕
		List<Dto> typeList = new ArrayList<Dto>();//类型
		List<Dto> benisonList = new ArrayList<Dto>();//祝福语
		
		if(nameOrderList!=null && nameOrderList.size()>0){
			
			for(int i=0;i<nameOrderList.size();i++){
				
				Dto nameOrder = nameOrderList.get(i);
				
				//一级柱状图内容
	        	Dto screen = new BaseDto();
	        	screen.put("y", nameOrder.get("total"));
	        	screen.put("name", nameOrder.get("screenName"));
	        	screen.put("drilldown", nameOrder.get("screenName"));
	        	screenList.add(screen);
	        	
	        	//二级柱状图内容
            	Dto data2 = new  BaseDto();
//            	data2.put("name", nameOrder.get("screenName"));
            	data2.put("id", nameOrder.get("screenName"));
                List<Dto> childrenList = (List<Dto>)nameOrder.get("typeOrderList");
                
                List<Dto> typeOrderList = new ArrayList<Dto>();
                if(childrenList != null && childrenList.size()>0){
                	
                	for(Dto children:childrenList){
                		
                		 Dto type = new BaseDto();
                		 type.put("y", children.get("total"));
                		 type.put("name", children.get("typeName"));
                		 type.put("drilldown", children.getAsString("typeName")+i);
                		 typeOrderList.add(type);
                		 
                		 //三级柱状图内容
                		 Dto data3 = new  BaseDto();
//                		 data3.put("name",  children.get("typeName"));
                		 data3.put("id",  children.getAsString("typeName")+i);
                		 List<Dto> typeChildrenList = (List<Dto>)children.get("benisonTypeOrderList");
                		 
                		 List<Dto> benisonOrderList = new ArrayList<Dto>();
                		 if(typeChildrenList != null && typeChildrenList.size() >0){
                			 typeChildrenList.forEach(typeChild->{
                        		 Dto benison = new  BaseDto();
                        		 benison.put("y", typeChild.get("total"));
                        		 benison.put("name", typeChild.get("benisonCode"));
                        		 benisonOrderList.add(benison);
                			 });
                       	 }
                		 data3.put("data", benisonOrderList);
                		 typeList.add(data3);
                	 }
                }
                
                data2.put("data", typeOrderList);
                typeList.add(data2);
			}
		}
		
//		data.put("name","屏幕名称");
		data.put("screenList", screenList);
		data.put("typeList", typeList);
		
		return data;
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
			List<Dto> thirdColumsList = orderAction.findThirdColumns(queryParam);
			
			//组装数据
			for(Dto timeOrder:timeOrderList){
				
				if(timeOrder!=null){
					
					Dto param = new BaseDto();
					param.put("playDate", timeOrder.getAsString("playDate"));
					param.put("nameBy", queryParam.get("nameBy"));
					param.put("thirdColumsList", thirdColumsList);
					//查询该播放时间的各屏幕的各类型订单
					List<Dto> benisonOrderList = orderAction.findBenisonOrderList(param);
					
					//将订单放入时间列表中
					if(benisonOrderList != null && benisonOrderList.size()>0){
						if(benisonOrderList.get(0)!=null){
							timeOrder.putAll(benisonOrderList.get(0));
						}
					}
					
				}
			}
			
			//组装页脚数据
			Dto param = new BaseDto();
			param.put("nameBy", queryParam.get("nameBy"));
			param.put("thirdColumsList", thirdColumsList);
			footerList = orderAction.findBenisonOrderList(param);
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
	
	/**
	 * 动态获取页面显示的列名称
	 * @return
	 */
	@RequestMapping(value="/getColumns")
	@ResponseBody
	public Dto getColumns(String screenId){
		
		Dto param = new BaseDto();
		param.put("screenId", screenId);
		Dto map = new BaseDto();
		
		//查询第一行列的名称
		List<Dto> firstColumsList = orderAction.findNewFirstColumns(param);
		
		List<Dto> newFirstCoumsList = new ArrayList<Dto>();
		if(firstColumsList!=null && firstColumsList.size()>0){
			//动态获取屏幕列
			firstColumsList.forEach(firstColum2->{
				Dto newFirstColum = new BaseDto();
				newFirstColum.put("field", firstColum2.get("screenCode"));
				newFirstColum.put("title", firstColum2.get("screenName"));
				newFirstColum.put("width", 900);
				newFirstColum.put("colspan", firstColum2.getAsInteger("colspan"));
				newFirstCoumsList.add(newFirstColum);
			});
		}
		
		//查询第二行列的名称
		List<Dto> secondColumsList = orderAction.findNewSecondColumns(param);
		
		List<Dto> newSecondColumsList = new ArrayList<Dto>();
		if(secondColumsList!=null && secondColumsList.size()>0){
			//动态获取屏幕列
			Dto secondColum = new BaseDto();
			secondColum.put("field", "playDate");
			secondColum.put("title", "日期/祝福类型");
			secondColum.put("width", 90);
			secondColum.put("rowspan", 2);
			newSecondColumsList.add(secondColum);
			secondColumsList.forEach(secondColum2->{
				Dto newSecondColum = new BaseDto();
				newSecondColum.put("field", secondColum2.get("typeId"));
				newSecondColum.put("title", secondColum2.get("TypeName"));
				newSecondColum.put("width", 400);
				newSecondColum.put("colspan", secondColum2.get("colspan"));
				newSecondColumsList.add(newSecondColum);
			});
		}
		
		//查询第三行列的名称
		List<Dto> thirdColumsList = orderAction.findThirdColumns(param);
		
		List<Dto> newThirdColumsList = new ArrayList<Dto>();
		if(thirdColumsList!=null && thirdColumsList.size()>0){
			//动态获取屏幕列
			thirdColumsList.forEach(thirdColum->{
				Dto newThirdColum = new BaseDto();
				newThirdColum.put("field", thirdColum.get("typeField"));
				newThirdColum.put("title", thirdColum.get("benisonCode"));
				newThirdColum.put("width", 80);
				newThirdColumsList.add(newThirdColum);
			});
		}
		
		map.put("firstColumsList", newFirstCoumsList);
		map.put("secondColumsList", newSecondColumsList);
		map.put("thirdColumsList", newThirdColumsList);
		
		return map;
	}
}
