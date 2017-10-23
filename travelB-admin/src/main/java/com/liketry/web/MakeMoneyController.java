package com.liketry.web;

import com.liketry.domain.MakeMoney;
import com.liketry.service.MakeMoneyService;
import com.liketry.util.PropertiesUtils;
import com.liketry.util.UploadFile;
import com.liketry.web.vm.ResultVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 赚钱课堂接口
 * @author pengyy
 */
@Api(value="赚钱课堂",description="供平台管理调用")
@RestController
@RequestMapping("sys/makeMoney")
@Slf4j
public class MakeMoneyController extends BaseController<MakeMoneyService,MakeMoney>{
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	@ApiOperation(value="上传赚钱课堂图片")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "request", value = "request请求（内含图片流）", required = true,dataType = "HttpServletRequest")
	   })
	@PostMapping(consumes = "multipart/form-data", value = "/uploadMakeMoneyPic")
	public ResultVM uploadMakeMoneyPic(HttpServletRequest request){
		
		//上传图片
		String realUrl = null;
		String upLoadPath = PropertiesUtils.getInstance().getValue("default_upload_filepath");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		try {
			realUrl = UploadFile.getInstance().uploadFile(request,upLoadPath,"MAKEMONEY/"+sdf.format(new Date()),"MKM");
		} catch (Exception e) {
			log.info("<======活动图片格式错误：{}========>",e);
			return  ResultVM.error("图片上传失败！");
		}
		
		if(realUrl!=null){
			return  ResultVM.ok(realUrl);
		}else{
			return  ResultVM.error("图片上传失败！");
		}
		
	}
}