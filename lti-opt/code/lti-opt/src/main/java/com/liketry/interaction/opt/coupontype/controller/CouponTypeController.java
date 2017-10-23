package com.liketry.interaction.opt.coupontype.controller;

import java.util.Arrays;
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
import com.liketry.interaction.opt.coupontype.action.ICouponTypeAction;
import java.util.Map;
import java.util.UUID;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;


/**
  * CouponTypeController
  */
@Controller("couponTypeController")
@RequestMapping(value="/couponType")
public class CouponTypeController  extends BaseController  {
		
	@Resource(name=ICouponTypeAction.ACTION_ID)
	private ICouponTypeAction couponTypeAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showCouponTypeIndexPage() {
		return "coupontype/coupontypeIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getCouponTypeList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = couponTypeAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showCouponTypeAddPage(String rowId,Model model) {
		
		return "coupontype/coupontypeAdd"; 
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showCouponTypeEditPage(String rowId,String couponTypeValue,
			String publishType,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
			model.addAttribute("couponTypeValue",couponTypeValue);
			model.addAttribute("publishTypeValue",publishType);
		}
		
		return "coupontype/coupontypeEdit"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getCouponTypeById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("couponTypeId", rowId);
		return couponTypeAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveCouponTypeInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		if(param.get("couponTypeId") ==null ||"".equals(param.get("couponTypeId")))
		{
			param.put("couponTypeId", UUID.randomUUID().toString().replace("-", ""));
			
			String type = null;
			//区分优惠码类型
			if("0".equals(param.getAsString("couponType"))){
				type = "TJ";
			}else if("1".equals(param.getAsString("couponType"))){
				type = "ZK";
			}else if("2".equals(param.getAsString("couponType"))){
				type = "DK";
			}
			
			// 获取最新优惠码类型编号
			Dto lastParam = new BaseDto();
			lastParam.put("couponTypeCode", type);
			Dto lastTemplate = couponTypeAction.findLastOne(lastParam);
			
			// 截取尾号
			String lastNum = "001";
			if(lastTemplate != null && !lastTemplate.isEmpty()){
				String lastTemplateCode = lastTemplate.getAsString("couponTypeCode");
				int oldLastNum = Integer.parseInt(lastTemplateCode.substring(lastTemplateCode.length()-3));
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
			
			param.put("couponTypeCode", type+lastNum);
			param.put("createdBy", loginId);
			param.put("createdTime", TKDateTimeUtils.getTodayTimeStamp());
			param.put("delflag", "1"); // “1”代表“有效”
			param.put("version", 1);
			couponTypeAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			Dto params = new BaseDto();
			params.put("couponTypeId", param.get("couponTypeId"));
			Dto oldOne = couponTypeAction.findOne(params);
			
			if(oldOne!=null){
				// 更新数据
				oldOne.put("couponName", param.get("couponName"));
				oldOne.put("validType", param.get("validType"));
				oldOne.put("couponType", param.get("couponType"));
				oldOne.put("specialOffer", param.get("specialOffer"));
				oldOne.put("discount", param.get("discount"));
				oldOne.put("deduction", param.get("deduction"));
				oldOne.put("screenIds", param.get("screenIds"));
				oldOne.put("commodityIds", param.get("commodityIds"));
				oldOne.put("benisonTypeIds", param.get("benisonTypeIds"));
				oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
				oldOne.put("modifiedBy", loginId);
				oldOne.put("version", oldOne.getAsInteger("version")+1);
				
				couponTypeAction.updateObject(param);
				map.put(MESSAGE_INFO, "更新成功！");
			}
			
		}
		map.put(RTN_RESULT, "true");
		
		return map;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteCouponType(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("couponTypeId", rowId);
		couponTypeAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

