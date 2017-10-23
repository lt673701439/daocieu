package com.taikang.iu.job.service.impl;




import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.liketry.interaction.opt.coupon.action.ICouponAction;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.exception.app.TKDaoException;
import com.taikang.udp.framework.core.web.BaseController;


@Service("couponJob")
public class CouponJob extends BaseController{
	
	@Resource(name=ICouponAction.ACTION_ID)
	private ICouponAction couponJobAction;
	
	/**
	 * 每天00:05执行定时任务，检查发布的优惠码状态，如果发现已经过了有效期，则“未使用”优惠码状态变为“已过期”。
	 */
	@SuppressWarnings("unchecked")
	public void freshCouponStatus() {
		
		logger.debug("<======CouponJob--freshCouponStatus======>");
		Dto param = new BaseDto();
		param.put("useType", "1");//未使用
		List<Dto> couponList = couponJobAction.findAll(param);
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if(couponList!=null && couponList.size()>0){
			//判断，如果商品的有效日期大于今天，则修改状态为已过期
			for(Dto coupon:couponList){
				try {
					Date validDate = coupon.getAsDate("valid_date");
					Date currentDate = TKDateTimeUtils.getTodayDate(); 
					//先判断播放日期是否已过期
					if(validDate.before(currentDate)){
						Dto newCoupon = new BaseDto();
						newCoupon.put("couponId", coupon.get("coupon_id"));
						newCoupon.put("useType", 2);//已过期
						couponJobAction.updateObject(newCoupon);
					}
				}catch(Exception e){
					logger.error(" update failed ,Exception= "+e.getMessage());
					throw new TKDaoException("","CouponJob","freshCouponStatus"," 更新优惠码状态时发生错误! ",e);
				}
			}
		}
		
	}
	
}
