package com.liketry.interaction.opt.user.controller;

import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import com.liketry.interaction.opt.user.action.IBUserAction;
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

import java.util.Map;
import java.util.UUID;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;


/**
  * BUserController
  */
@Controller("bUserController")
@RequestMapping(value="/bUser")
public class BUserController  extends BaseController  {
		
	@Resource(name=IBUserAction.ACTION_ID)
	private IBUserAction bUserAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showBUserIndexPage() {
		return "bUser/bUserIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getBUserList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = bUserAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showBUserEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "bUser/bUserEdit"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getBUserById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("userId", rowId);
		return bUserAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
//	@RequestMapping("/save")
//	@ResponseBody
//	private Map<String,String> saveBUserInfo(HttpServletRequest request)
//	{
//		Map<String,String> map=new HashMap<String,String>();
//		
//		Dto param = getParamAsDto(request);
//		if(param.get("rowId") ==null ||"".equals(param.get("rowId")))
//		{
//			bUserAction.insertObject(param);
//			map.put(MESSAGE_INFO, "新增成功！");
//		}
//		else
//		{
//			bUserAction.updateObject(param);
//			map.put(MESSAGE_INFO, "更新成功！");
//		}
//		map.put(RTN_RESULT, "true");
//		
//		return map;
//	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveBuserInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		// 修改信息
		Dto param = getParamAsDto(request);
		if(param.get("userId") != null && !"".equals(param.get("userId"))){
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("userId", param.get("userId"));
			Dto oldOne = bUserAction.findOne(params);
			
			// 更新数据
			oldOne.put("userMail", param.get("userMail"));
			oldOne.put("userPhone", param.get("userPhone"));
			
			bUserAction.updateObject(oldOne);
			map.put(MESSAGE_INFO, "修改成功！");
			map.put(RTN_RESULT, "true");
		}else {
			map.put(MESSAGE_INFO, "登录用户不存在！");
			map.put(RTN_RESULT, "false");
		}
		
		return map;
		
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteBUser(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		bUserAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
}

