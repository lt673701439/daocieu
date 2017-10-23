package com.liketry.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.Promotion;
import com.liketry.service.PromotionService;
import com.liketry.util.PropertiesUtils;
import com.liketry.util.ShiroUtils;
import com.liketry.util.UploadFile;
import com.liketry.web.vm.ResultVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 活动接口
 *
 * @author pengyy
 */
@Api(value="活动服务",description="供平台管理调用")
@RestController
@RequestMapping("sys/promotion")
@Slf4j
public class PromotionController extends BaseController<PromotionService,Promotion>{
	
	/**
     * 新增
     * @param t
     * @return
     */
	@ApiOperation(value="新增活动")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "t", value = "活动对象", required = true,dataType = "Promotion")
	   })
	@PostMapping
    public ResultVM create(@RequestBody Promotion t) {
    	
    	String msg = checkPromotion(t);
    	
    	if(msg != null){
    		return ResultVM.error(msg);
    	}

        t.setCreateUserId(ShiroUtils.getUserId());
        t.setCreateTime(new Date());
        t.setUpdateTime(new Date());
        t.setUpdateUserId(ShiroUtils.getUserId());
        
        if(service.insert(t)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }
    
    /**
     * 更新
     * @param t
     * @return
     */
	@ApiOperation(value="更新活动")
	   @ApiImplicitParams({
		   @ApiImplicitParam(name = "t", value = "活动对象", required = true,dataType = "Promotion")
	   })
	@PutMapping
    public ResultVM update(@RequestBody Promotion t) {
    	
    	if(StringUtils.isBlank(t.getId())){
    		return ResultVM.error("主键不能为null");
    	}

    	String msg = checkPromotion(t);
    	
    	if(msg != null){
    		return ResultVM.error(msg);
    	}

        t.setUpdateTime(new Date());
        t.setUpdateUserId(ShiroUtils.getUserId());
        
        if(service.updateById(t)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	@ApiOperation(value="上传活动图片")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "request", value = "request请求（内含图片流）", required = true,dataType = "HttpServletRequest")
	   })
	@PostMapping(consumes = "multipart/form-data", value = "/uploadPromotionPic")
	public ResultVM uploadPromotionPic(HttpServletRequest request){
		
		//上传图片
		String realUrl = null;
		String upLoadPath = PropertiesUtils.getInstance().getValue("default_upload_filepath");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		try {
			realUrl = UploadFile.getInstance().uploadFile(request,upLoadPath,"PROMOTION/"+sdf.format(new Date()),"PRO");
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

    /**
     * 删除
     * @param id
     * @return
     */
	@ApiOperation(value="删除活动")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "id", value = "活动ID", required = true,dataType = "String")
	   })
    @DeleteMapping("/{id}")
    public ResultVM delete(@PathVariable String id) {

    	Promotion promotion =  service.selectById(id);
    	//上架活动不能删除
    	if(StringUtils.isNotBlank(promotion.getPromotionStatus())
    			&& "1".equals(promotion.getPromotionStatus())){
    		return ResultVM.error("该活动正处于上架状态，不能被删除！");
    	}

        if(service.deleteById(id)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }

    /**
     * 校验活动录入项
     * @param t
     * @return
     */
    private String checkPromotion(Promotion t){

		String startTime = t.getStartTime();//开始时间
		String endTime = t.getEndTime();//结束时间
		String addTime = t.getAddTime();//上架时间
		String removeTime = t.getRemoveTime();//下架时间
		String promotionName = t.getPromotionName();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	//校验时间
		try {
			
			//t.setAddTime(addTime);
			//t.setRemoveTime(removeTime);
			//t.setStartTime(startTime);
			//t.setEndTime(endTime);
			
			// 如果开始时间大于结束时间，则返回
			if(StringUtils.isNotBlank(startTime)&&StringUtils.isNotBlank(endTime)){
				if(sdf.parse(endTime).before(sdf.parse(startTime))){
					return "开始时间大于等于了结束时间，请修改！";
				}
			}
			
			//如果上架时间大于下架时间，则返回
			if(StringUtils.isNotBlank(addTime)&&StringUtils.isNotBlank(removeTime)){
				if(sdf.parse(removeTime).before(sdf.parse(addTime))){
					return "上架时间大于等于了下架时间，请修改！";
				}
			}
			
			//如果是新增需要校验活动名称是否重复
			if(StringUtils.isBlank(t.getId())){
				if(StringUtils.isNotBlank(promotionName)){
					Promotion promotion = new Promotion();
					promotion.setPromotionName(promotionName);
					Promotion onePromotion = service.selectOne(new EntityWrapper<Promotion>(promotion));
					if(onePromotion!=null){
						return "活动名称重复，请修改！";
					}
				}
			}
			
		} catch (ParseException e) {
			return "时间格式填写有误，请重新填写（格式说明：yyyy-MM-dd HH:mm:ss）！";
		}
		
		return null;
    }

	/**
	 * 时间转换格式（例：2017-09-25T00:36:05.000Z ->  2017-09-25 00:36:05）
	 * @param time
	 * @return
	 */
	private String formatTime(String time){

		if(StringUtils.isNotBlank(time)){

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				time = format.format(sdf.parse(time));
			} catch (ParseException e) {
				log.error("<===活动时间转换错误，异常信息：{}======>",e);
			}
		}
		return time;
	}
}