package com.liketry.interaction.opt.commoditycost.controller;

import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;

import com.liketry.interaction.opt.commodity.action.ICommodityAction;
import com.liketry.interaction.opt.commoditycost.action.ICommodityCostAction;
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


/**
  * CommodityCostController
  */
@Controller("commodityCostController")
@RequestMapping(value="/commodityCost")
public class CommodityCostController  extends BaseController  {
		
	@Resource(name=ICommodityCostAction.ACTION_ID)
	private ICommodityCostAction commodityCostAction;
	
	@Resource(name=ICommodityAction.ACTION_ID)
	private ICommodityAction commodityAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showCommodityCostIndexPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			// 取商品信息
			Dto param = new BaseDto();
			param.put("commodityId", rowId);
			Dto commodity =  commodityAction.findOne(param);
			
			model.addAttribute("commodityId",rowId );
			model.addAttribute("commodityCode",commodity.get("commodityCode"));
			model.addAttribute("commodityName",commodity.get("commodityName"));
		}
		
		return "commodityCost/commodityCostIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getCommodityCostList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = commodityCostAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("/add")
	public String showCommodityCostAddPage(String commodityId,Model model) {
		
		if(commodityId!=null && !commodityId.equals(""))
		{			
			model.addAttribute("commodityId",commodityId );
		}
		return "commodityCost/commodityCostAdd";  
	}
	
	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showCommodityCostEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "commodityCost/commodityCostEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showCommodityCostViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "commodityCost/commodityCostView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getCommodityCostById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("costId", rowId);
		return commodityCostAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveCommodityCostInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		// 页面信息
		Dto param = getParamAsDto(request);
		
		if(param.get("costId") == null || "".equals(param.get("costId"))){
			
			// 新增操作
			param.put("costId", UUID.randomUUID().toString().replace("-", ""));
			
			// 添加商品价格体系条目
			commodityCostAction.insertObject(param);
			map.put(MESSAGE_INFO, "添加成功！");
			map.put(RTN_RESULT, "true");
		}else {
			
			// 更新操作
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("costId", param.get("costId"));
			Dto oldOne = commodityCostAction.findOne(params);
			
			oldOne.put("costType", param.get("costType"));
			oldOne.put("costName", param.get("costName"));
			oldOne.put("costPrice", param.get("costPrice"));
			oldOne.put("skuId", param.get("skuId")); // 借用代表序号
			
			commodityCostAction.updateObject(oldOne);
			map.put(MESSAGE_INFO, "修改成功！");
			map.put(RTN_RESULT, "true");
		}
		
		return map;
		
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteCommodityCost(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("costId", rowId);
		commodityCostAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

