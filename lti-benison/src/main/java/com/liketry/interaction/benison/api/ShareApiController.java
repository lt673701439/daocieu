package com.liketry.interaction.benison.api;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.model.Order;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.BenisonService;
import com.liketry.interaction.benison.service.OrderService;
import com.liketry.interaction.benison.util.MakeImgUtils;
import com.liketry.interaction.benison.util.PropertiesUtils;
import com.liketry.interaction.benison.util.StringUtils;
import com.liketry.interaction.benison.util.UserUtils;

/**
 * 分享接口
 * @author pengyy
 */
@RestController
@RequestMapping("share_api")
public class ShareApiController {
	
	private static final Logger log = LoggerFactory.getLogger(ShareApiController.class);

	@Autowired
    OrderService orderService;
	
	@Autowired
	BenisonService benisonService;
	
	/**
	 * 详情分享图片
	 * @return
	 */
	@RequestMapping("/sharePictureForDetail") 
    Result<String> sharePictureForDetail(String data) {
		 
		 JSONObject json  = UserUtils.decrypt2Str(data);
		 log.info("<==sharePictureForDetail==入参json:"+json+"====>");
		 
		 String orderId = json.getString("orderId");
		 String playDate = json.getString("playDate");//播放时间
		 Order order = orderService.findOneOrder(orderId);
		 
		 if(order!=null){
			 
			 //分享图为空，则制作，否则，直接返回
			 if(StringUtils.isEmpty(order.getShareUrl())){
				 
				 String writeName = order.getWriteName();//落款人
				 String blessName = order.getBlessName();//祝福对象
				 String templateId = order.getTemplateId();//模板ID
				 String screenName = order.getScreenName();//屏幕名称
				 List<String> picUrlList = new ArrayList<String>();//存放生成图片的urlList
				 SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
				 String date = sdf.format(UserUtils.getCurrentDate());
				 
				 BenisonTemplate benisonTemplate =  benisonService.findBenisonTemplateById(templateId);
				 
				 String path = PropertiesUtils.getInstance().getValue("default_upload_filepath")+"share/order/"+date+"/";//图片的输出路径
				 
				 //重新处理屏幕名称
				 if(!StringUtils.isEmpty(screenName)){
					 screenName = StringUtils.fixName(screenName);
				 }
				 
				 //横条图制作
				 Map<String,String> corssBandMap = MakeImgUtils.makeCrossbandPic(screenName, playDate,path);
				 if("false".equals(corssBandMap.get("result"))){
					 return new Result<>(SystemConstants.RESULT_FALSE,corssBandMap.get("msg"),null); 
				 }
				 picUrlList.add(corssBandMap.get("msg"));
				 
				 //背景图制作
				 //校验模板是否存在,如果存在，走普通订单分享，不存在，走定制
				 if(benisonTemplate!=null){
					
					 Map<String,String> backMap = MakeImgUtils.makeImg(benisonTemplate, writeName, blessName,path,750);
					 if("false".equals(backMap.get("result"))){
						 return new Result<>(SystemConstants.RESULT_FALSE,backMap.get("msg"),null); 
					 }
					 picUrlList.add(backMap.get("msg"));
					 
				 }else{
					 //拷贝结果图url用来制作分享图
					 if(!StringUtils.isEmpty(order.getResultUrl())){
						 String fileName = "order"+UUID.randomUUID().toString().replace("-", "")+"."+
								 order.getResultUrl().substring(order.getResultUrl().indexOf(".")+1,order.getResultUrl().length());//新的文件名
						 String newUrl = MakeImgUtils.copyFile(PropertiesUtils.getInstance().getValue("default_upload_filepath")+order.getResultUrl(),path+fileName);
						 picUrlList.add(newUrl);
					 }else{
						 return new Result<>(SystemConstants.RESULT_FALSE,"很抱歉，没有可以用来分享的图片！",null); 
					 }
				 }
				 
				 //图片拼接
				 String fielPath = path+"order"+UUID.randomUUID().toString().replace("-", "")+
						 corssBandMap.get("msg").substring(corssBandMap.get("msg").indexOf("."),corssBandMap.get("msg").length());//输出后的文件路径及名称
				 
				 Map<String,String>  stitchMap = MakeImgUtils.stitchPic(picUrlList,fielPath);
				 
				 if("false".equals(stitchMap.get("result"))){
					 return new Result<>(SystemConstants.RESULT_FALSE,stitchMap.get("msg"),null); 
				 }
				 
				 //删除多余图片
				 if(picUrlList!=null && picUrlList.size()>0){
					 picUrlList.forEach(picUrl->{
						 File file = new File(picUrl);
				    		if(file.exists()){
				    			file.delete();
				    		} 
					 });
				 }
				 
				 //修改订单，更新分享url
				 order.setShareUrl(fielPath.replace(PropertiesUtils.getInstance().getValue("default_upload_filepath"), ""));
				 orderService.updateOrder(order);
				 
				 return new Result<>(SystemConstants.RESULT_SUCCESS,"分享成功",fielPath.
						 replace(PropertiesUtils.getInstance().getValue("default_upload_filepath"), ""));
				 
			 }else{
				 return new Result<>(SystemConstants.RESULT_SUCCESS,"分享成功",order.getShareUrl()); 
			 }
			  
		 }else{
			 return new Result<>(SystemConstants.RESULT_FALSE,"订单不存在",null); 
		 }
		 
    }
	 
	 /**
	  * 预览分享图片
	  * @return
	  */
	@RequestMapping("/sharePictureForPreview") 
    Result<String> sharePictureForPreview(String data) {
		 
		 JSONObject json  = UserUtils.decrypt2Str(data);
		 log.info("<==sharePictureForPreview==入参json:"+json+"====>");
		 
		 String playDate = json.getString("playDate");//播放时间
		 String writeName = json.getString("writeName");//落款人
		 String blessName = json.getString("blessName");//祝福对象
		 String templateId = json.getString("templateId");//模板ID
		 String screenName = json.getString("screenName");//屏幕名称
		 List<String> picUrlList = new ArrayList<String>();//存放生成图片的urlList
		 SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
		 String date = sdf.format(UserUtils.getCurrentDate());
		 
		 BenisonTemplate benisonTemplate =  benisonService.findBenisonTemplateById(templateId);
		 
		 //校验模板是否存在
		 if(benisonTemplate==null){
			 return new Result<>(SystemConstants.RESULT_FALSE,"模板不存在",null);  
		 }
		 
		 String path = PropertiesUtils.getInstance().getValue("default_upload_filepath")+"temp/order/"+date+"/";//图片的输出路径
		 
		 //重新处理屏幕名称
		 if(!StringUtils.isEmpty(screenName)){
			 screenName = StringUtils.fixName(screenName);
		 }
		 
		 //横条图制作
		 Map<String,String> corssBandMap = MakeImgUtils.makeCrossbandPic(screenName, playDate,path);
		 if("false".equals(corssBandMap.get("result"))){
			 return new Result<>(SystemConstants.RESULT_FALSE,corssBandMap.get("msg"),null); 
		 }
		 picUrlList.add(corssBandMap.get("msg"));
		 
		//背景图制作
		 Map<String,String> backMap = MakeImgUtils.makeImg(benisonTemplate, writeName, blessName,path,750);
		 if("false".equals(backMap.get("result"))){
			 return new Result<>(SystemConstants.RESULT_FALSE,backMap.get("msg"),null); 
		 }
		 picUrlList.add(backMap.get("msg"));
		 
		 //图片拼接
		 String fielPath = path+"order"+UUID.randomUUID().toString().replace("-", "")+
				 corssBandMap.get("msg").substring(corssBandMap.get("msg").indexOf("."),corssBandMap.get("msg").length());//输出后的文件路径及名称
		 
		 Map<String,String>  stitchMap = MakeImgUtils.stitchPic(picUrlList,fielPath);
		 
		 if("false".equals(stitchMap.get("result"))){
			 return new Result<>(SystemConstants.RESULT_FALSE,stitchMap.get("msg"),null); 
		 }
		 
		 //删除多余图片
		 if(picUrlList!=null && picUrlList.size()>0){
			 picUrlList.forEach(picUrl->{
				 File file = new File(picUrl);
		    		if(file.exists()){
		    			file.delete();
		    		} 
			 });
		 }
		 
		 return new Result<>(SystemConstants.RESULT_SUCCESS,"分享成功",fielPath.
				 replace(PropertiesUtils.getInstance().getValue("default_upload_filepath"), ""));
		 
    }
	
	/**
	  * 预览图片
	  * @return
	  */
	@RequestMapping("/pictureForPreview") 
   Result<String> pictureForPreview(String data) {
		 
		 JSONObject json  = UserUtils.decrypt2Str(data);
		 log.info("<==sharePictureForDetail==入参json:"+json+"====>");
		 
		 String writeName = json.getString("writeName");//落款人
		 String blessName = json.getString("blessName");//祝福对象
		 String templateId = json.getString("templateId");//模板ID
		 SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMddHHmmss");
		 String date = sdf.format(UserUtils.getCurrentDate());
		 
		 BenisonTemplate benisonTemplate =  benisonService.findBenisonTemplateById(templateId);
		 
		 //校验模板是否存在
		 if(benisonTemplate==null){
			 return new Result<>(SystemConstants.RESULT_FALSE,"模板不存在",null);  
		 }
		 
		 String path = PropertiesUtils.getInstance().getValue("default_upload_filepath")+"temp/order/"+date+"/";//图片的输出路径
		 //背景图制作
		 Map<String,String> backMap = MakeImgUtils.makeImg(benisonTemplate, writeName, blessName,path,1200);
		 
		 if("false".equals(backMap.get("result"))){
			 return new Result<>(SystemConstants.RESULT_FALSE,backMap.get("msg"),null); 
		 }
		 
		 return new Result<>(SystemConstants.RESULT_SUCCESS,"预览成功",backMap.get("msg").
				 replace(PropertiesUtils.getInstance().getValue("default_upload_filepath"), ""));
		 
   }
	 
}