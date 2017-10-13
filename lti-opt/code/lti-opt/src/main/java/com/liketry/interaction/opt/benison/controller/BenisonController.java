package com.liketry.interaction.opt.benison.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liketry.interaction.opt.benison.action.IBenisonAction;
import com.liketry.interaction.opt.benisontemplate.action.IBenisonTemplateAction;
import com.liketry.interaction.opt.benisontype.action.IBenisonTypeAction;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.sequence.BusinessSeqGenerator;
import com.taikang.udp.sys.util.vo.LoginUser;


/**
  * BenisonController
  */
@Controller("benisonController")
@RequestMapping(value="/benison")
public class BenisonController  extends BaseController  {
		
	@Resource(name=IBenisonAction.ACTION_ID)
	private IBenisonAction benisonAction;
	
	@Resource(name=IBenisonTemplateAction.ACTION_ID)
	private IBenisonTemplateAction benisonTemplateAction;
	
	@Resource(name=IBenisonTypeAction.ACTION_ID)
	private IBenisonTypeAction benisonTypeAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showBenisonIndexPage() {
		return "benison/benisonIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getBenisonList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = benisonAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showBenisonAddPage() {
		
		return "benison/benisonAdd"; 
	}

	
	/**
	 * 打开修改
	 * @return
	 */
	@RequestMapping("edit")
	public String showBenisonEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("benisonId",rowId );
		}
		
		return "benison/benisonEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showBenisonViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("benisonId",rowId );
		}
		
		return "benison/benisonView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getBenisonById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("benisonId", rowId);
		param = benisonAction.findOne(param);
		//查询祝福语类型名称
		Dto benisonTypeParam = new BaseDto();
		benisonTypeParam.put("typeId", param.get("typeId"));
		Dto benisonType = benisonTypeAction.findOne(benisonTypeParam);
		param.put("typeName", benisonType.get("typeName"));
		return param;
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveBenisonInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		if(param.get("benisonId") ==null ||"".equals(param.get("benisonId")))
		{
			param.put("benisonId", UUID.randomUUID().toString().replace("-", ""));
			param.put("benisonCode", "BIS"+BusinessSeqGenerator.getInstance("benison_code").nextId());
			
			//如果规则内容为null则将祝福语内容拷贝一份，填入
			if(StringUtils.isEmpty(param.get("ruleContent"))){
				param.put("ruleContent", param.get("benisonContent"));	
			}
			benisonAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			Dto params = new BaseDto();
			params.put("benisonId", param.get("benisonId"));
			Dto oldOne = benisonAction.findOne(params);
			
			if(oldOne!=null){
				// 更新数据
				oldOne.put("typeId", param.get("typeId"));
				oldOne.put("benisonCode", param.get("benisonCode"));
				oldOne.put("benisonContent", param.get("benisonContent"));
				oldOne.put("ruleContent", param.get("ruleContent"));
				
				benisonAction.updateObject(oldOne);
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
	public Map<String, String> deleteBenison(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("benisonId", rowId);
		
		//查询是否有关联模板数据
		List<Dto> benisonTemplatelist = benisonTemplateAction.findAllByBenisonId(param);
		
		if(benisonTemplatelist!=null && benisonTemplatelist.size()>0){
			
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "该祝福语与模板有绑定，不能删除！");
		}else{
			benisonAction.deleteObject(param);
			
			map.put(RTN_RESULT, "true");
			map.put(MESSAGE_INFO, "操作成功！");
		}
		
		return map;
	}
}

