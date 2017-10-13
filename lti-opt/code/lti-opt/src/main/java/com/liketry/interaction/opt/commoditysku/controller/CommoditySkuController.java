package com.liketry.interaction.opt.commoditysku.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.sequence.BusinessSeqGenerator;
import com.taikang.udp.sys.util.vo.LoginUser;
import com.liketry.interaction.opt.commodity.action.ICommodityAction;
import com.liketry.interaction.opt.commoditysku.action.ICommoditySkuAction;
import java.util.Map;
import java.util.UUID;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;


/**
  * CommoditySkuController
  */
@Controller("commoditySkuController")
@RequestMapping(value="/commoditySku")
public class CommoditySkuController  extends BaseController  {
		
	@Resource(name=ICommoditySkuAction.ACTION_ID)
	private ICommoditySkuAction commoditySkuAction;
		
	@Resource(name=ICommodityAction.ACTION_ID)
	private ICommodityAction commodityAction;
	
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showCommoditySkuIndexPage(String rowId,Model model) {
		
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
		
		return "commoditySku/commoditySkuIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getCommoditySkuList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = commoditySkuAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("/add")
	public String showCommoditySkuAddPage(String commodityId,Model model) {
		
		if(commodityId!=null && !commodityId.equals(""))
		{			
			model.addAttribute("commodityId",commodityId );
		}
		return "commoditySku/commoditySkuAdd"; 
	}
	
	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showCommoditySkuEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "commoditySku/commoditySkuEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showCommoditySkuViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "commoditySku/commoditySkuView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getCommoditySkuById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("skuId", rowId);
		return commoditySkuAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveCommoditySkuInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		// 页面信息
		Dto param = getParamAsDto(request);
		
		if(param.get("skuId") == null || "".equals(param.get("skuId"))){
			
			// 新增操作
			// 取商品信息
			Dto commodityParam = new BaseDto();
			commodityParam.put("commodityId", param.get("commodityId"));
			Dto commodity =  commodityAction.findOne(commodityParam);
			
			// 校验商品信息
			if(commodity == null && "".equals(commodity)){
				map.put(MESSAGE_INFO, "商品信息不存在,无法添加商品sku！");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			param.put("skuId", UUID.randomUUID().toString().replace("-", ""));
			
			
			// 获取最新sku
			Dto lastParam = new BaseDto();
			lastParam.put("commodityId", param.get("commodityId"));
			Dto lastSku = commoditySkuAction.findLastOne(lastParam);
			
			// 截取尾号
			String lastNum = "001";
			if(lastSku != null && !lastSku.isEmpty()){
				String lastSkuCode = lastSku.getAsString("skuCode");
				int oldLastNum = Integer.parseInt(lastSkuCode.substring(lastSkuCode.length()-3));
				int newLastNum = oldLastNum+1;
				
				// 补位
				if(newLastNum <= 9){
					lastNum = "00"+newLastNum;
				}else if(newLastNum <= 99){
					lastNum = "0"+newLastNum;
				}else {
					lastNum = ""+newLastNum;
				}
			}
			
			// CMD_SCR001_001_001
			param.put("skuCode", commodity.get("commodityCode")+"_"+lastNum);
			param.put("skuStatus", "1");
			
			param.put("createdBy", loginId);
			param.put("createdTime", TKDateTimeUtils.getTodayTimeStamp());
			param.put("delflag", "1"); // “1”代表“有效”
			param.put("version", 1);
			
			// 添加商品sku
			commoditySkuAction.insertObject(param);
			map.put(MESSAGE_INFO, "添加成功！");
			map.put(RTN_RESULT, "true");
		}else {
			
			// 更新操作
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("skuId", param.get("skuId"));
			Dto oldOne = commoditySkuAction.findOne(params);
			
			oldOne.put("skuName", param.get("skuName"));
			oldOne.put("skuDescription", param.get("skuDescription"));
			oldOne.put("skuStatus", param.get("skuStatus"));
			oldOne.put("skuPrice", param.get("skuPrice"));
			
			oldOne.put("templateId", param.get("templateId"));
			oldOne.put("typeId", param.get("typeId"));
			oldOne.put("benisonId", param.get("benisonId"));
			
			oldOne.put("modified_by", loginId);
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", oldOne.getAsInteger("version")+1);
			
			commoditySkuAction.updateObject(oldOne);
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
	public Map<String, String> deleteCommoditySku(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("skuId", rowId);
		commoditySkuAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

