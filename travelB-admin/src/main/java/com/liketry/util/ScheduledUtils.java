package com.liketry.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.liketry.domain.Promotion;
import com.liketry.mapper.PromotionMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务类
 * @author pengyy
 */
@Slf4j
@Component
public class ScheduledUtils {
	
	@Resource
    private PromotionMapper promotionMapper;

	/**
	 * 定时更新活动状体
	 * 每10分钟刷新一次，查看活动，如果超过下架时间，则更新状态为下架
	 */
	@Scheduled(cron = "0 0/10 * * * ?")
    public void freshPromotionStatus() {
		
		log.info("<======ScheduledUtils--freshPromotionStatus======>");
		
		Promotion param = new Promotion();
		param.setPromotionStatus("1");//状态：1-上架，2-下架
		List<Promotion> promotionList = promotionMapper.selectList(new EntityWrapper<Promotion>(param));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(promotionList!=null && promotionList.size()>0){
			
			for(Promotion promotion:promotionList){
				
				try {
					
					if(StringUtils.isNotBlank(promotion.getRemoveTime()) && StringUtils.isNotBlank(promotion.getAddTime())){
						Date removeDate = sdf.parse(promotion.getRemoveTime());
						Date addDate = sdf.parse(promotion.getAddTime());
						Date currentDate = new Date(); 
						//判断，如果活动下架时间已经超过当前时间或还没到上架时间，则下架活动
						if(removeDate.before(currentDate) || addDate.after(currentDate)){ 
							promotion.setPromotionStatus("2");//下架
							promotionMapper.updateById(promotion);
						}
					}
					
				}catch(Exception e){
					log.error(" update failed ,Exception= "+e);
				}
			}
		}
    }
	
}
