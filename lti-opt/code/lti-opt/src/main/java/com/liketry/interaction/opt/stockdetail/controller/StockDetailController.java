package com.liketry.interaction.opt.stockdetail.controller;

import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liketry.interaction.opt.order.action.IOrderAction;
import com.liketry.interaction.opt.stockdetail.action.IStockDetailAction;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.taikang.udp.framework.core.web.BaseController;
import java.util.Map;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;


/**
  * StockDetailController
  */
@Controller("stockDetailController")
@RequestMapping(value="/stockDetail")
public class StockDetailController  extends BaseController  {
		
	@Resource(name=IStockDetailAction.ACTION_ID)
	private IStockDetailAction stockDetailAction;
	
	@Resource(name=IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showStockDetailIndexPage() {
		return "stockDetail/stockDetailIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getStockDetailList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = stockDetailAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showStockDetailEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "stockDetail/stockDetailEdit"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getStockDetailById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		return stockDetailAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveStockDetailInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		Dto param = getParamAsDto(request);
		if(param.get("rowId") ==null ||"".equals(param.get("rowId")))
		{
			stockDetailAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			stockDetailAction.updateObject(param);
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
	public Map<String, String> deleteStockDetail(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		stockDetailAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
	
	/**
	 * 根据库存ID查询库存详细列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/getStockDetailListById")
	@ResponseBody
	public Map<String,Object> getStockDetailListById(String stockId,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		Dto param = new BaseDto();
		param.put("stock_id", stockId);//库存ID
		page.setParamObject(param);
		CurrentPage currentPage = stockDetailAction.queryForPage(page);
		
		List<Dto> list = currentPage.getPageItems();
		
		if(list!=null && list.size()>0){
			
			for(Dto stockDetail:list){
				Dto order = new BaseDto();
				order.put("orderId", stockDetail.getAsString("order_id"));
				Dto newOrder = orderAction.findOne(order);
				
				if(newOrder!=null){
					stockDetail.put("order_code", newOrder.getAsString("orderCode"));
				}
			}
		}
		map.put("rows", list);
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

}

