package com.liketry.interaction.opt.benisonimg.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.sequence.BusinessSeqGenerator;
import com.taikang.udp.sys.util.vo.LoginUser;

import java.util.Map;
import java.util.UUID;

import com.liketry.interaction.opt.benisonimg.action.IBenisonImgAction;
import com.liketry.interaction.opt.benisontemplate.action.IBenisonTemplateAction;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.iu.com.CommonUtil;
import com.taikang.udp.framework.common.datastructre.Dto;


/**
  * BenisonImgController
  */
@Controller("benisonImgController")
@RequestMapping(value="/benisonImg")
public class BenisonImgController  extends BaseController  {
		
	@Resource(name=IBenisonImgAction.ACTION_ID)
	private IBenisonImgAction benisonImgAction;
	
	@Resource(name=IBenisonTemplateAction.ACTION_ID)
	private IBenisonTemplateAction benisonTemplateAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showBenisonImgIndexPage() {
		return "benisonImg/benisonImgIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getBenisonImgList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = benisonImgAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showBenisonImgAddPage() {
		
		return "benisonImg/benisonImgAdd"; 
	}
	

	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showBenisonImgEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("imgId",rowId );
			model.addAttribute("urlList", getImgUrlList(rowId));
		}
		
		return "benisonImg/benisonImgEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showBenisonImgViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("imgId",rowId );
			model.addAttribute("urlList", getImgUrlList(rowId));
		}
		
		return "benisonImg/benisonImgView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getBenisonImgById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("imgId", rowId);
		return benisonImgAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveBenisonImgInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		if(param.get("imgId") ==null ||"".equals(param.get("imgId")))
		{
			String imgId = UUID.randomUUID().toString().replace("-", "");
			param.put("imgId", imgId);
			param.put("imgCode", "BMG"+BusinessSeqGenerator.getInstance("img_code").nextId());
			benisonImgAction.insertObjectAndUpload(param,request);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			
			Dto params = new BaseDto();
			params.put("imgId", param.get("imgId"));
			Dto oldOne = benisonImgAction.findOne(params);
			
			if(oldOne!=null){
				// 更新数据
				oldOne.put("imgName", param.get("imgName"));
				oldOne.put("imgType", param.get("imgType"));
				oldOne.put("imgUrl", param.get("imgUrl"));
				
				benisonImgAction.updateObjectAndUpload(oldOne,request);
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
	public Map<String, String> deleteBenisonImg(@RequestParam("rowId") String rowId
			,@RequestParam("rowType") String rowType) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("imgId", rowId);
		param.put("imgType", rowType);
		
		//查询是否有关联模板数据
		List<Dto> benisonTemplatelist = benisonTemplateAction.findAllByImgId(param);
		
		if(benisonTemplatelist!=null && benisonTemplatelist.size()>0){
			
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "该祝福语图片与模板有绑定，不能删除！");
		}else{
			benisonImgAction.deleteObject(param);
			
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
	public List<Dto> getImgUrlList(String imgId){
		Dto param = new BaseDto();
		param.put("imgId", imgId);
		Dto list = benisonImgAction.findOne(param);
		//组装数据
		List<Dto> urlList = new ArrayList<Dto>();
		String pictureUrl = list.getAsString("imgUrl");
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

