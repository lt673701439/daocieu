package com.liketry.interaction.opt.screen.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liketry.interaction.opt.benisontemplate.action.IBenisonTemplateAction;
import com.liketry.interaction.opt.screen.action.IScreenAction;
import com.liketry.interaction.opt.spot.action.ISpotAction;
import com.taikang.iu.com.CommonUtil;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.sequence.BusinessSeqGenerator;
import com.taikang.udp.sys.util.vo.LoginUser;


/**
  * ScreenController
  */
@Controller("screenController")
@RequestMapping(value="/screen")
public class ScreenController  extends BaseController  {
		
	@Resource(name=IScreenAction.ACTION_ID)
	private IScreenAction screenAction;
	
	@Resource(name=IBenisonTemplateAction.ACTION_ID)
	private IBenisonTemplateAction benisonTemplateAction;
	
	@Resource(name=ISpotAction.ACTION_ID)
	private ISpotAction spotAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showScreenIndexPage() {
		return "screen/screenIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getScreenList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = screenAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/getScreenNameList")
	@ResponseBody
	public List<Dto> getScreenNameList(){
		
		List<Dto> returnList = new ArrayList<Dto>();
		Dto param = new BaseDto();
		List<Dto> screenList = screenAction.findAll(param);
		screenList.forEach(screen->{
			Dto returnParam = new BaseDto();
			returnParam.put("id", screen.get("screen_id"));
			returnParam.put("text", screen.get("screen_name"));
			returnList.add(returnParam);
		});
		
		return returnList;
	}

	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showScreenAddPage() {
		
		return "screen/screenAdd"; 
	}

	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showScreenEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("urlList", getImgUrlList(rowId));
			model.addAttribute("screenId",rowId );
		}
		
		return "screen/screenEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showScreenViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("urlList", getImgUrlList(rowId));
			model.addAttribute("screenId",rowId );
		}
		
		return "screen/screenView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getScreenById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("screenId", rowId);
		param = screenAction.findOne(param);
		//查询景区名称
		Dto spotParam = new BaseDto();
		spotParam.put("spotId", param.get("spotId"));
		Dto spot = spotAction.findOne(spotParam);
		param.put("spotName", spot.get("spotName"));
				
		return param;
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveScreenInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		if(param.get("screenId") ==null ||"".equals(param.get("screenId")))
		{
			param.put("screenId", UUID.randomUUID().toString().replace("-", ""));
			param.put("screenCode", "SCR"+BusinessSeqGenerator.getInstance("screen_code").nextId());
			screenAction.insertObjectAndUpload(param,request);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			
			Dto params = new BaseDto();
			params.put("screenId", param.get("screenId"));
			Dto oldOne = screenAction.findOne(params);
			
			if(oldOne!=null){
				// 更新数据
				oldOne.put("spotId", param.get("spotId"));
				oldOne.put("screenName", param.get("screenName"));
				oldOne.put("screenStatus", param.get("screenStatus"));
				oldOne.put("screenLocation", param.get("screenLocation"));
				oldOne.put("screenLongitude", param.get("screenLongitude"));
				oldOne.put("screenDimension", param.get("screenDimension"));
				oldOne.put("screenLong", param.get("screenLong"));
				oldOne.put("screenWide", param.get("screenWide"));
				oldOne.put("screenSize", param.get("screenSize"));
				oldOne.put("screenResolution", param.get("screenResolution"));
				oldOne.put("screenDescription", param.get("screenDescription"));
				
				screenAction.updateObjectAndUpload(oldOne,request);
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
	public Map<String, String> deleteScreen(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("screenId", rowId);
		//查询是否有关联模板数据
		List<Dto> benisonTemplatelist = benisonTemplateAction.findAllByScreenId(param);
		
		if(benisonTemplatelist!=null && benisonTemplatelist.size()>0){
			
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "该屏幕与模板有绑定，不能删除！");
		}else{
			screenAction.deleteObject(param);
			
			map.put(RTN_RESULT, "true");
			map.put(MESSAGE_INFO, "操作成功！");
		}
		
		return map;
	}
	
	/**
	 * 获取图片路径
	 * @param imgId
	 * @return
	 */
	public List<Dto> getImgUrlList(String screenId){
		Dto param = new BaseDto();
		param.put("screenId", screenId);
		Dto list = screenAction.findOne(param);
		//组装数据
		List<Dto> urlList = new ArrayList<Dto>();
		String pictureUrl = list.getAsString("screenImg");
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

