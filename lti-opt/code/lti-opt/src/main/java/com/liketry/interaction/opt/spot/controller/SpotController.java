package com.liketry.interaction.opt.spot.controller;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.liketry.interaction.opt.screen.action.IScreenAction;
import com.liketry.interaction.opt.spot.action.ISpotAction;
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
  * SpotController
  */
@Controller("spotController")
@RequestMapping(value="/spot")
public class SpotController  extends BaseController  {
		
	@Resource(name=ISpotAction.ACTION_ID)
	private ISpotAction spotAction;
	
	@Resource(name=IScreenAction.ACTION_ID)
	private IScreenAction screenAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showSpotIndexPage() {
		return "spot/spotIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getSpotList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = spotAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showSpotAddPage() {
		
		return "spot/spotAdd"; 
	}

	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showSpotEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("spotId",rowId );
		}
		
		return "spot/spotEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showSpotViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("spotId",rowId );
		}
		
		return "spot/spotEdit"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getSpotById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("spotId", rowId);
		return spotAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveSpotInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		if(param.get("spotId") ==null ||"".equals(param.get("spotId")))
		{
			param.put("spotId", UUID.randomUUID().toString().replace("-", ""));
			param.put("spotCode", "SPT"+BusinessSeqGenerator.getInstance("spot_code").nextId());
			spotAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			Dto params = new BaseDto();
			params.put("spotId", param.get("spotId"));
			Dto oldOne = spotAction.findOne(params);
			
			if(oldOne!=null){
				// 更新数据
				oldOne.put("spotName", param.get("spotName"));
				oldOne.put("spotStatus", param.get("spotStatus"));
				oldOne.put("spotProvince", param.get("spotProvince"));
				oldOne.put("spotCity", param.get("spotCity"));
				oldOne.put("spotAddress", param.get("spotAddress"));
				oldOne.put("spotDescription", param.get("spotDescription"));
				
				spotAction.updateObject(oldOne);
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
	public Map<String, String> deleteSpot(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("spotId", rowId);
		//查询是否有关联屏幕数据
		List<Dto> screenlist = screenAction.findAllBySpotId(param);
		
		if(screenlist!=null && screenlist.size()>0){
			
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "该景区与屏幕有绑定，不能删除！");
		}else{
			spotAction.deleteObject(param);
			
			map.put(RTN_RESULT, "true");
			map.put(MESSAGE_INFO, "操作成功！");
		}
		
		return map;
	}
}

