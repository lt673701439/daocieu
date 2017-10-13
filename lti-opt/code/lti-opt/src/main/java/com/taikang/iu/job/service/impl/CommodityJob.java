package com.taikang.iu.job.service.impl;




import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.liketry.interaction.opt.commodity.action.ICommodityAction;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.exception.app.TKDaoException;
import com.taikang.udp.framework.core.web.BaseController;


@Service("commodityJob")
public class CommodityJob extends BaseController{
	
	@Resource(name=ICommodityAction.ACTION_ID)
	private ICommodityAction commodityAction;
	
	/**
	 * 每分钟刷新一次，查看订单，如果10分钟未支付，自动取消
	 */
	@SuppressWarnings("unchecked")
	public void freshCommodityStatus() {
		
		logger.debug("<======CommodityJob--freshCommodityStatus======>");
		Dto param = new BaseDto();
		param.put("commodityStatus", "1");//待支付
		List<Dto> commodityList = commodityAction.findAll(param);
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		
		if(commodityList!=null && commodityList.size()>0){
			//判断，如果商品播放时间已经超过当前时间，则下架商品
			for(Dto commodity:commodityList){
				try {
					Date orderDate = commodity.getAsDate("end_date");
					Date currentDate = TKDateTimeUtils.getTodayDate(); 
					//先判断播放日期是否已过期
					if(orderDate.before(currentDate)){
						//再判断播放时间是否超时
						if(dateFormat.parse(commodity.getAsString("end_time")).before(dateFormat.parse(dateFormat.format(currentDate)))){
							Dto newCommodity = new BaseDto();
							newCommodity.put("commodityId", commodity.get("commodity_id"));
							newCommodity.put("commodityStatus", 2);//下架
							commodityAction.updateObject(newCommodity);
						}
					}
				}catch(Exception e){
					logger.error(" update failed ,Exception= "+e.getMessage());
					throw new TKDaoException("","CommodityJob","freshCommodityStatus"," 更新商品状态时发生错误! ",e);
				}
			}
		}
		
	}
	
}
