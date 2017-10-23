package com.liketry.interaction.opt.benisontemplate.controller;

import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import com.alibaba.druid.util.StringUtils;
import com.liketry.interaction.opt.benison.action.IBenisonAction;
import com.liketry.interaction.opt.benisonimg.action.IBenisonImgAction;
import com.liketry.interaction.opt.benisontemplate.action.IBenisonTemplateAction;
import com.liketry.interaction.opt.benisontype.action.IBenisonTypeAction;
import com.liketry.interaction.opt.order.action.IOrderAction;
import com.liketry.interaction.opt.screen.action.IScreenAction;
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
  * BenisonTemplateController
  */
@Controller("benisonTemplateController")
@RequestMapping(value="/benisonTemplate")
public class BenisonTemplateController  extends BaseController  {
		
	@Resource(name=IBenisonTemplateAction.ACTION_ID)
	private IBenisonTemplateAction benisonTemplateAction;
	
	@Resource(name=IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
	
	@Resource(name=IScreenAction.ACTION_ID)
	private IScreenAction screenAction;
	
	@Resource(name=IBenisonAction.ACTION_ID)
	private IBenisonAction benisonAction;
	
	@Resource(name=IBenisonTypeAction.ACTION_ID)
	private IBenisonTypeAction benisonTypeAction;
	
	@Resource(name=IBenisonImgAction.ACTION_ID)
	private IBenisonImgAction benisonImgAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showBenisonTemplateIndexPage() {
		return "benisonTemplate/benisonTemplateIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getBenisonTemplateList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = benisonTemplateAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showBenisonTemplateAddPage(String rowId,Model model) {
		
		return "benisonTemplate/benisonTemplateAdd"; 
	}

	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showBenisonTemplateEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("templateId",rowId );
			model.addAttribute("urlList", getStrokeUrlList(rowId));
		}
		
		return "benisonTemplate/benisonTemplateEdit"; 
	}
	
	/**
	 * 打开查看详情页面
	 * @return
	 */
	@RequestMapping("view")
	public String showBenisonTemplateViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("templateId",rowId );
			model.addAttribute("urlList", getStrokeUrlList(rowId));
		}
		
		return "benisonTemplate/benisonTemplateView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getBenisonTemplateById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("templateId", rowId);
		Dto benisonTemplate = benisonTemplateAction.findOne(param);
		benisonTemplate.replace("titleColour", benisonTemplate.getAsString("titleColour"), "#"+benisonTemplate.getAsString("titleColour"));
		benisonTemplate.replace("bodyColour", benisonTemplate.getAsString("bodyColour"), "#"+benisonTemplate.getAsString("bodyColour"));
		benisonTemplate.replace("tailColour", benisonTemplate.getAsString("tailColour"), "#"+benisonTemplate.getAsString("tailColour"));
		//查询屏幕名称
		Dto screenParam = new BaseDto();
		screenParam.put("screenId", benisonTemplate.get("screenId"));
		Dto screen = screenAction.findOne(screenParam);
		benisonTemplate.put("screenName", screen.get("screenName"));
		//查询祝福语内容
		Dto benisonParam = new BaseDto();
		benisonParam.put("benisonId", benisonTemplate.get("benisonId"));
		Dto benison = benisonAction.findOne(benisonParam);
		benisonTemplate.put("benisonContent", benison.get("benisonContent"));
		//查询祝福语类型名称
		Dto benisonTypeParam = new BaseDto();
		benisonTypeParam.put("typeId", benisonTemplate.get("typeId"));
		Dto benisonType = benisonTypeAction.findOne(benisonTypeParam);
		benisonTemplate.put("typeName", benisonType.get("typeName"));
		//查询背景图片名称
		Dto benisonImgParam = new BaseDto();
		benisonImgParam.put("imgId", benisonTemplate.get("bgImgId"));
		Dto benisonImg = benisonImgAction.findOne(benisonImgParam);
		benisonTemplate.put("imgName", benisonImg.get("imgName"));
		
		return benisonTemplate;
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveBenisonTemplateInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		
		//校验字体
//		StringBuffer strs = new  StringBuffer();//返回页面后用来更换字体
//		StringBuffer strFonts = new StringBuffer();//页面提示信息用
		
//		Boolean flag = true;
//		if(!StringUtils.isEmpty(param.getAsString("titleType"))&&!checkFont(param.getAsString("titleType"))){
//			strs.append("titleType");
//			strFonts.append("抬头");
//			flag = false;
//		}
//		if(!StringUtils.isEmpty(param.getAsString("bodyType"))&&!checkFont(param.getAsString("bodyType"))){
//			if(strs.length()>0){
//				strs.append(",bodyType");
//				strFonts.append(",主体");
//			}else{
//				strs.append("bodyType");
//				strFonts.append("主体");
//				flag = false;
//			}
//		}
//		if(!StringUtils.isEmpty(param.getAsString("tailType"))&&!checkFont(param.getAsString("tailType"))){
//			if(strs.length()>0){
//				strs.append(",tailType");
//				strFonts.append(",落款");
//			}else{
//				strs.append("tailType");
//				strFonts.append("落款");
//				flag = false;
//			}
//		}
//		
//		if(!flag){
//			map.put("strs", strs.toString());
//			map.put(MESSAGE_INFO, "您输入的<"+strFonts+"字体>系统暂不支持；<br>点击“确认”，默认保存为黑体；点击“取消”，重新输入！");
//			map.put(RTN_RESULT, ""+flag);
//			return map;
//		}
		
		//code拼接屏幕编码
		if(param.get("templateId") ==null ||"".equals(param.get("templateId")))
		{
			//取得屏幕编号
			Dto screen = new BaseDto();
			screen.put("screenId", param.get("screenId"));
			Dto newScreen = screenAction.findOne(screen);
			String screenCode = null;
			if(newScreen!=null && newScreen.containsKey("screenCode")){
				screenCode = newScreen.getAsString("screenCode");
			}
			// 获取最新模板
			Dto lastParam = new BaseDto();
			lastParam.put("screenId", param.get("screenId"));
			Dto lastTemplate = benisonTemplateAction.findLastOne(lastParam);
			
			// 截取尾号
			String lastNum = "001";
			if(lastTemplate != null && !lastTemplate.isEmpty()){
				String lastTemplateCode = lastTemplate.getAsString("templateCode");
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
			
			param.put("templateId", UUID.randomUUID().toString().replace("-", ""));
			param.put("templateCode", "BTL_"+screenCode+"_"+lastNum);
			param.put("bgImgId", param.get("imgId"));
			benisonTemplateAction.insertObjectAndUpload(param,request);
			map.put(MESSAGE_INFO, "新增成功！");
			map.put(RTN_RESULT, "true");
		}
		else
		{
			Dto params = new BaseDto();
			params.put("templateId", param.get("templateId"));
			Dto oldOne = benisonTemplateAction.findOne(params);
			
			if(oldOne!=null){
				// 更新数据
				oldOne.put("templateName", param.get("templateName"));
				oldOne.put("screenId", param.get("screenId"));
				oldOne.put("typeId", param.get("typeId"));
				oldOne.put("benisonId", param.get("benisonId"));
				oldOne.put("bgImgId", param.get("imgId"));
//				oldOne.put("smImgId", param.get("smImgId"));
				oldOne.put("titleX", param.get("titleX"));
				oldOne.put("titleY", param.get("titleY"));
				oldOne.put("titleColour", param.get("titleColour"));
				oldOne.put("titleSize", param.get("titleSize"));
				oldOne.put("titleType", param.get("titleType"));
				oldOne.put("bodyX", param.get("bodyX"));
				oldOne.put("bodyY", param.get("bodyY"));
				oldOne.put("bodyColour", param.get("bodyColour"));
				oldOne.put("bodySize", param.get("bodySize"));
				oldOne.put("bodyType", param.get("bodyType"));
				oldOne.put("tailX", param.get("tailX"));
				oldOne.put("tailY", param.get("tailY"));
				oldOne.put("tailColour", param.get("tailColour"));
				oldOne.put("tailSize", param.get("tailSize"));
				oldOne.put("tailType", param.get("tailType"));
				oldOne.put("strokeFigure", param.get("strokeFigure"));
				oldOne.put("strokeX", param.get("strokeX"));
				oldOne.put("strokeY", param.get("strokeY"));
				oldOne.put("strokeAlpha", param.get("strokeAlpha"));
				benisonTemplateAction.updateObjectAndUpload(oldOne,request);
				map.put(MESSAGE_INFO, "更新成功！");
				map.put(RTN_RESULT, "true");
			}
		}
		return map;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteBenisonTemplate(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("templateId", rowId);
		
		//查询是否有关联订单数据
		List<Dto> orderlist = orderAction.findAllByTemplateId(param);
		
		if(orderlist!=null && orderlist.size()>0){
			
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "该祝福语模板与订单有绑定，不能删除！");
		}else{
			benisonTemplateAction.deleteObject(param);
			
			map.put(RTN_RESULT, "true");
			map.put(MESSAGE_INFO, "操作成功！");
		}
		
		return map;
	}
	
	/**
	 * 校验字体是否支持
	 * @param param
	 * @return
	 */
	public Boolean checkFont(String fontName){
		
		Boolean flag = false;
		
		//获取当前系统的所有字体
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
		//转换list进行比对校验
		List<String> localFontList = Arrays.asList(ge.getAvailableFontFamilyNames());
		logger.info("<====FontFamilyNames:======localFontList.toString()======>");
		if(localFontList!=null && localFontList.size()>0){
			for(String localFontName:localFontList){
				if(localFontName.equals(fontName)){
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取所有字体
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/getFontsList")
	@ResponseBody
	public List<Dto> getFontsList(){
		
		List<Dto> listDto = new ArrayList<Dto>();
		//获取当前系统的所有字体
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();  
		//转换list进行比对校验
		List<String> localFontList = Arrays.asList(ge.getAvailableFontFamilyNames());
		
		for(String localfontName:localFontList){
			Dto tmpDto = new BaseDto();
			tmpDto.put("fontId", localfontName);
			tmpDto.put("fontName", localfontName);
			listDto.add(tmpDto);
		}
		return listDto;
	}
	
	/**
	 * 获取图片路径
	 * @param templateId
	 * @return
	 */
	public List<Dto> getStrokeUrlList(String templateId){
		Dto param = new BaseDto();
		param.put("templateId", templateId);
		Dto list = benisonTemplateAction.findOne(param);
		//组装数据
		List<Dto> urlList = new ArrayList<Dto>();
		String pictureUrl = list.getAsString("strokeFigure");
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

