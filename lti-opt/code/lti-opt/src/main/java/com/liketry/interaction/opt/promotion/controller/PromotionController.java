package com.liketry.interaction.opt.promotion.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import com.liketry.interaction.opt.promotion.action.IPromotionAction;
import com.liketry.interaction.opt.promotiondetail.action.IPromotionDetailAction;

import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKConstants;
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

import java.util.Map;
import java.util.UUID;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.iu.com.CommonUtil;
import com.taikang.udp.framework.common.datastructre.Dto;


/**
  * PromotionController
  */
@Controller("promotionController")
@RequestMapping(value="/promotion")
public class PromotionController  extends BaseController  {
		
	@Resource(name=IPromotionAction.ACTION_ID)
	private IPromotionAction promotionAction;
	
	@Resource(name=IPromotionDetailAction.ACTION_ID)
	private IPromotionDetailAction promotionDetailAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showPromotionIndexPage() {
		return "promotion/promotionIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getPromotionList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = promotionAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("/add")
	public String showPromotionAddPage() {
		return "promotion/promotionAdd"; 
	}
	
	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showPromotionEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
			model.addAttribute("urlList", getImgUrlList(rowId));
		}
		
		return "promotion/promotionEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 * String
	 */
	@RequestMapping("/view")
	public String showPromotionViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId);
			model.addAttribute("urlList", getImgUrlList(rowId));
		}
		
		return "promotion/promotionView"; 
	}
	
	/**
	 * 打开预览页面
	 * @return
	 * String
	 */
	@RequestMapping("/preview")
	public String showPromotionPreviewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals("")){
			
			model.addAttribute("rowId",rowId);
			model.addAttribute("urlList", getImgUrlList(rowId));
			
			// 获取基本信息
			Dto param = new BaseDto();
			param.put("promotionId", rowId);
			Dto promotion = promotionAction.findOne(param);
			
			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
			String endTime = dateFormat.format(promotion.getAsDate("endTime"));
			
			model.addAttribute("endTime",endTime);
			model.addAttribute("promotionDescription",promotion.get("promotionDescription"));
			
			// 获取商品列表
			param.put("urlHead", CommonUtil.RELATION_UPLOAD_FILEPATH);
			List<Dto> commodityList = promotionDetailAction.findAllCommodityById(param);
			model.addAttribute("commodityList",commodityList);
		}
		
		return "promotion/promotionPreview"; 
	}
	
	/**
	 * 打开描述页面
	 * @return
	 * String
	 */
	@RequestMapping("/desc")
	public String showPromotionDescPage(String rowId,Model model) {
		
		model.addAttribute("rowId",rowId);
		
		return "promotion/promotionDesc"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getPromotionById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("promotionId", rowId);
		return promotionAction.findOne(param);
	}
	
	/**
	 * 获取一条记录描述信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getDesc")
	@ResponseBody
	public Map<String,Object> getDescById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("promotionId", rowId);
		return promotionAction.getDesc(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> savePromotionInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		// 修改信息
		Dto param = getParamAsDto(request);
		
		if(param.get("promotionId") == null || "".equals(param.get("promotionId"))){
			
			
			param.put("promotionId", UUID.randomUUID().toString().replace("-", ""));
			// PRO201700005
			param.put("promotionCode", "PRO"+TKDateTimeUtils.getCurrentDate("yyyy").toString().replace("-", "")+"_"+BusinessSeqGenerator.getInstance("promotion_code").nextId()); 
			param.put("promotionType", "1");// 扩展字段
			param.put("promotionStatus", "1");// “1”代表上架
			
			param.put("createdBy", loginId);
			param.put("createdTime", TKDateTimeUtils.getTodayTimeStamp());
			param.put("delflag", "1"); // “1”代表“有效”
			param.put("version", 1);
			
			// 添加商品
			promotionAction.insertObjectAndUpload(param,request);
			map.put(MESSAGE_INFO, "添加成功！");
			map.put(RTN_RESULT, "true");
		}else {
			
			// 更新操作
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("promotionId", param.get("promotionId"));
			Dto oldOne = promotionAction.findOne(params);
			
			oldOne.put("promotionName", param.get("promotionName"));
			oldOne.put("promotionStatus", param.get("promotionStatus"));
			oldOne.put("promotionImg", param.get("promotionImg"));
			oldOne.put("backUp", param.get("backUp"));
			oldOne.put("sortNum", param.get("sortNum"));
			
			oldOne.put("addTime", param.get("addTime"));
			oldOne.put("removeTime", param.get("removeTime"));
			oldOne.put("startTime", param.get("startTime"));
			oldOne.put("endTime", param.get("endTime"));

			oldOne.put("isTogether", param.get("isTogether"));
			
			oldOne.put("modified_by", loginId);
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", oldOne.getAsInteger("version")+1);
			
			promotionAction.updateObjectAndUpload(oldOne,request,null);
			map.put(MESSAGE_INFO, "修改成功！");
			map.put(RTN_RESULT, "true");
		}
		
		return map;
		
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/saveDesc")
	@ResponseBody
	private Map<String,String> savePromotionDesc(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		// 修改信息
		Dto param = getParamAsDto(request);
		
		if(param.get("promotionId") == null || "".equals(param.get("promotionId"))){
			
			map.put(MESSAGE_INFO, "缺少促销活动主键值，请重新登录！");
			map.put(RTN_RESULT, "false");
		}else {
			
			// 保存描述信息
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("promotionId", param.get("promotionId"));
			Dto oldOne = promotionAction.findOne(params);
			
//			oldOne.put("promotionDescription", param.get("promotionDescription"));
			
			oldOne.put("modified_by", loginId);
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", oldOne.getAsInteger("version")+1);
			
			promotionAction.updateObjectAndUpload(oldOne,request,request.getParameter("promotionDescription"));
			map.put(MESSAGE_INFO, "保存成功！");
			map.put(RTN_RESULT, "true");
		}
		
		return map;
		
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deletePromotion(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("promotionId", rowId);
		promotionAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
	
	/**
	 * 获取图片路径
	 * @param imgId
	 * @return
	 */
	public List<Dto> getImgUrlList(String promotionId){
		Dto param = new BaseDto();
		param.put("promotionId", promotionId);
		Dto list = promotionAction.findOne(param);
		//组装数据
		List<Dto> urlList = new ArrayList<Dto>();
		String pictureUrl = list.getAsString("promotionImg");
		if (pictureUrl != null && !"".equals(pictureUrl)) {
			String[] split = pictureUrl.split(",");
			for (int i = 0; i < split.length; i++) {
				Dto map = new BaseDto();
				String url = CommonUtil.RELATION_UPLOAD_FILEPATH + split[i];
				map.put("url", url);
				urlList.add(map);
			}
		}
		return urlList;
	}
}

