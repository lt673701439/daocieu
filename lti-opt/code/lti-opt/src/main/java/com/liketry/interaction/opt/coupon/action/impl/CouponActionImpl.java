package com.liketry.interaction.opt.coupon.action.impl;


import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import com.liketry.interaction.opt.coupon.model.CouponBO;
import com.liketry.interaction.opt.coupon.service.ICouponService;
import com.liketry.interaction.opt.coupontype.model.CouponTypeBO;
import com.liketry.interaction.opt.coupontype.service.ICouponTypeService;
import com.liketry.interaction.opt.coupon.action.ICouponAction;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;

/**
  * CouponAction
  */
  @Service(ICouponAction.ACTION_ID)
public class CouponActionImpl extends BaseActionImpl 
  implements ICouponAction {

    /**
      * 注入service
      */
    @Resource(name=ICouponService.SERVICE_ID)
	private ICouponService<CouponBO> couponService;	
    
    /**
     * 注入service
     */
   @Resource(name=ICouponTypeService.SERVICE_ID)
	private ICouponTypeService<CouponTypeBO> couponTypeService;	
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======CouponAction--addCoupon======>");
		
		CouponBO couponBO = BaseDto.toModel(CouponBO.class , param);
		couponService.insertObject(couponBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======CouponAction--updateCoupon======>");
		
		CouponBO couponBO = BaseDto.toModel(CouponBO.class , param);
		couponService.updateObject(couponBO);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======CouponAction--deleteCoupon======>");
		
		CouponBO couponBO = BaseDto.toModel(CouponBO.class , param);
		couponService.deleteObject(couponBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======CouponAction--findOneCoupon======>");
		
		CouponBO couponBO = BaseDto.toModel(CouponBO.class , param);
		return couponService.findOne(couponBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======CouponAction--findAllCoupon======>");
				
		return couponService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======CouponAction--queryCouponForPage======>");
		
		return couponService.queryForPage(currentPage);
	}
	
	/**
     * 导出查询
     */
	public List<Dto> findAllCoupon(Dto param) {
		logger.debug("<======CouponAction--findAllCoupon======>");
				
		return couponService.findAllCouponMap(param);
	} 
	
	/**
	 * 随机生成优惠码
	 * @param num
	 * @param pre
	 * @return
	 */
	public String makeActivatedcode(Dto param){
		
		String msg = null;
		
		int num = param.getAsInteger("couponNum");//发布个数
		if(num <= 0){
			msg = "优惠码数量必须大于0";
		}
		
		//确定前缀
		Dto couponType = new BaseDto();
		couponType.put("couponTypeId", param.get("couponTypeId"));
		CouponTypeBO couponTypeBO = couponTypeService.findOne(BaseDto.toModel(CouponTypeBO.class , couponType));
		if(couponTypeBO == null){
			msg = "请选择优惠码类型";
		}
		
		String coupontype = couponTypeBO.getCouponType();
		String pre = null;
		if("0".equals(coupontype)){
			pre = "TJ";
		}else if("1".equals(coupontype)){
			pre = "ZK";
		}else if("2".equals(coupontype)){
			pre = "DK";
		}
		
	    Random random = new Random();
	    String candicatedCode = "abcedefghijklmnopqrstuvwxyz";//优惠码包含小写字母
	    candicatedCode+=candicatedCode.toUpperCase();//优惠码包含大写字母
	    candicatedCode+="1234567890";//优惠码包含阿拉伯数字
	    
	    for(int i=0; i< num;i++){
	        String res ="";
	        for(int j=0;j<8;j++){
	            res+=candicatedCode.charAt(random.nextInt(candicatedCode.length()));
	        }
	        String activatedCode = pre+res;
	        //校验验证码是否已有,如果没有，则保存数据库，如果有，则重新生成一条
	        Dto coupon = new BaseDto();
	        coupon.put("couponCode", activatedCode);
	        List<CouponBO> list = couponService.findAll(BaseDto.toModel(CouponBO.class , coupon));
	        if(list != null && list.size()>0){
	        	i = i-1;
	        }else{
	        	param.put("couponCode", activatedCode);
	        	param.put("couponId", UUID.randomUUID().toString().replace("-", ""));
	        	param.put("useType", "1");//未使用
	        	couponService.insertObject(BaseDto.toModel(CouponBO.class , param));
	        }
	        
	    }
	    
	    return msg;
	} 
	
}
