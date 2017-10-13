package com.liketry.interaction.opt.promotiondetail.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liketry.interaction.opt.promotion.action.IPromotionAction;
import com.liketry.interaction.opt.promotiondetail.action.IPromotionDetailAction;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;


/**
  * PromotionDetailController
  */
@Controller("promotionDetailController")
@RequestMapping(value="/promotionDetail")
public class PromotionDetailController  extends BaseController  {
		
	@Resource(name=IPromotionDetailAction.ACTION_ID)
	private IPromotionDetailAction promotionDetailAction;
		
	@Resource(name=IPromotionAction.ACTION_ID)
	private IPromotionAction promotionAction;
	
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showPromotionDetailIndexPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			// 取促销活动信息
			Dto param = new BaseDto();
			param.put("promotionId", rowId);
			Dto promotion =  promotionAction.findOne(param);
			
			model.addAttribute("promotionId",rowId );
			model.addAttribute("promotionCode",promotion.get("promotionCode"));
			model.addAttribute("promotionName",promotion.get("promotionName"));
		}
		
		return "promotionDetail/promotionDetailIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getPromotionDetailList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = promotionDetailAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("/add")
	public String showPromotionDetailAddPage(String promotionId,Model model) {
		
		if(promotionId!=null && !promotionId.equals(""))
		{
			model.addAttribute("promotionId",promotionId );
		}
		
		return "promotionDetail/promotionDetailAdd"; 
	}
	
	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showPromotionDetailEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "promotionDetail/promotionDetailEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showPromotionDetailViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "promotionDetail/promotionDetailView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getPromotionDetailById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("detailId", rowId);
		return promotionDetailAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> savePromotionDetailInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		// 修改信息
		Dto param = getParamAsDto(request);
		
		if(param.get("detailId") == null || "".equals(param.get("detailId"))){
			
			// 新增操作
			// 取促销活动信息
			Dto promotionParam = new BaseDto();
			promotionParam.put("promotionId", param.get("promotionId"));
			Dto promotion =  promotionAction.findOne(promotionParam);
			
			// 校验促销活动信息
			if(promotion == null && "".equals(promotion)){
				map.put(MESSAGE_INFO, "促销活动不存在,无法添加促销商品");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			param.put("detailId", UUID.randomUUID().toString().replace("-", ""));
			
			// 其它信息已在页面添加到param里
			
			param.put("createdBy", loginId);
			param.put("createdTime", TKDateTimeUtils.getTodayTimeStamp());
			param.put("delflag", "1"); // “1”代表“有效”
			param.put("version", 1);
			
			// 添加商品Detail
			promotionDetailAction.insertObject(param);
			map.put(MESSAGE_INFO, "添加成功！");
			map.put(RTN_RESULT, "true");
		}else {
			
			// 更新操作
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("detailId", param.get("detailId"));
			Dto oldOne = promotionDetailAction.findOne(params);
			
			oldOne.put("discountRatio", param.get("discountRatio"));
			
			oldOne.put("modified_by", loginId);
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", oldOne.getAsInteger("version")+1);
			
			promotionDetailAction.updateObject(oldOne);
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
	public Map<String, String> deletePromotionDetail(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("detailId", rowId);
		promotionDetailAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

