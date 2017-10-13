package com.liketry.service;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.Promotion;

/**
 * Created by pengyy
 */
public interface PromotionService extends IService<Promotion> {
	
	/**
	 * 新增活动
	 * @param t
	 * @param request
	 * @return
	 */
	public Boolean insertPromotion(Promotion t,HttpServletRequest request);
	
	/**
	 * 修改活动
	 * @param t
	 * @param request
	 * @return
	 */
	public Boolean updatePromotion(Promotion t,HttpServletRequest request);
}
