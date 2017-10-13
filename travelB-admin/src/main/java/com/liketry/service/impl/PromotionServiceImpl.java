package com.liketry.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.Promotion;
import com.liketry.mapper.PromotionMapper;
import com.liketry.service.PromotionService;
import com.liketry.util.PropertiesUtils;
import com.liketry.util.UploadFile;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by pengyy
 */
@Slf4j
@Service
public class PromotionServiceImpl extends ServiceImpl<PromotionMapper,Promotion> implements PromotionService {

	/**
	 * 新增活动
	 */
	@Transactional
	public Boolean insertPromotion(Promotion t,HttpServletRequest request){
		
		//上传图片
		String realUrl = null;
		
		String upLoadPath = PropertiesUtils.getInstance().getValue("default_upload_filepath");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		try {
			realUrl = UploadFile.getInstance().uploadFile(request,upLoadPath,"PROMOTION/"+sdf.format(new Date()),"PRO");
			if(StringUtils.isNotBlank(realUrl)){
				t.setPromotionImg(realUrl);
			}
			int count =  baseMapper.insert(t);
			
			if(count>0){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			log.info("<======活动图片格式错误：{}========>",e);
			return false;
		}
		
	}
	
	/**
	 * 修改活动
	 */
	public Boolean updatePromotion(Promotion t,HttpServletRequest request){
		
		//上传图片
		String realUrl = null;
		
		String upLoadPath = PropertiesUtils.getInstance().getValue("default_upload_filepath");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		try {
			realUrl = UploadFile.getInstance().uploadFile(request,upLoadPath,"PROMOTION/"+sdf.format(new Date()),"PRO");
			if(StringUtils.isNotBlank(realUrl)){
				t.setPromotionImg(realUrl);
			}
			int count =  baseMapper.updateById(t);
			
			if(count>0){
				return true;
			}else{
				return false;
			}
			
		} catch (Exception e) {
			log.info("<======活动图片格式错误：{}========>",e);
			return false;
		}
		
	}
	
}
