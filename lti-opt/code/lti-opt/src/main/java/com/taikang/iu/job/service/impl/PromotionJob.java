package com.taikang.iu.job.service.impl;




import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.liketry.interaction.opt.promotion.action.IPromotionAction;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.exception.app.TKDaoException;
import com.taikang.udp.framework.core.web.BaseController;


@Service("promotionJob")
public class PromotionJob extends BaseController{
	
	@Resource(name=IPromotionAction.ACTION_ID)
	private IPromotionAction promotionAction;
	
	/**
	 * 每10分钟刷新一次，查看活动，如果超过下架时间，则更新状态为下架
	 */
	@SuppressWarnings("unchecked")
	public void freshPromotionStatus() {
		
		logger.debug("<======PromotionJob--freshPromotionStatus======>");
		Dto param = new BaseDto();
		param.put("promotionStatus", "1");//状态：1-上架，2-下架
		List<Dto> promotionList = promotionAction.findAll(param);
		
		if(promotionList!=null && promotionList.size()>0){
			//判断，如果活动下架时间已经超过当前时间，则下架活动
			for(Dto promotion:promotionList){
				try {
					Date orderDate = promotion.getAsTimestamp("remove_time");
					Date currentDate = TKDateTimeUtils.getTodayDate(); 
					//先判断播放日期是否已过期
					if(orderDate.before(currentDate)){
						Dto newPromotion = new BaseDto();
						newPromotion.put("promotionId", promotion.get("promotion_id"));
						newPromotion.put("promotionStatus", 2);//下架
						promotionAction.updateObject(newPromotion);
					}
				}catch(Exception e){
					logger.error(" update failed ,Exception= "+e.getMessage());
					throw new TKDaoException("","PromotionJob","freshPromotionStatus"," 更新活动状态时发生错误! ",e);
				}
			}
		}
		
	}
	
}
