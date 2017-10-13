package com.liketry.interaction.opt.orderdetail.controller;

import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import com.liketry.interaction.opt.orderdetail.action.IOrderDetailAction;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.taikang.udp.framework.core.web.BaseController;
import java.util.Map;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;


/**
  * OrderDetailController
  */
@Controller("orderDetailController")
@RequestMapping(value="/orderDetail")
public class OrderDetailController  extends BaseController  {
		
	@Resource(name=IOrderDetailAction.ACTION_ID)
	private IOrderDetailAction orderDetailAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showOrderDetailIndexPage() {
		return "orderDetail/orderDetailIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getOrderDetailList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = orderDetailAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showOrderDetailEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "orderDetail/orderDetailEdit"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getOrderDetailById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		return orderDetailAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveOrderDetailInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		Dto param = getParamAsDto(request);
		if(param.get("rowId") ==null ||"".equals(param.get("rowId")))
		{
			orderDetailAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			orderDetailAction.updateObject(param);
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
	public Map<String, String> deleteOrderDetail(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		orderDetailAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

