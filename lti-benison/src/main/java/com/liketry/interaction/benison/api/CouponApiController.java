package com.liketry.interaction.benison.api;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.CouponService;
import com.liketry.interaction.benison.vo.api.CouponVO;

/**
 * 优惠码接口
 *
 * @author pengyy
 */
@RestController
@RequestMapping("coupon_api")
public class CouponApiController {
	
	 private static final Logger log = LoggerFactory.getLogger(CouponApiController.class);
	
    @Autowired
    CouponService couponService;

    /**
     * 验证优惠码是否存在
     * @param code 优惠码
     * @return
     */
    @GetMapping("validCoupon")
    Result<CouponVO> validCoupon(String code) {
    	
    	log.info("<=======校验优惠码，code:{}==========>",code);
    	
    	CouponVO couponVO = couponService.getCouponByCode(code);
    	
		String msg = validCode(couponVO);
    	if(msg != null){
    		return new Result<CouponVO>(SystemConstants.RESULT_FALSE,msg,null);
    	}
		
    	//日期格式转换
    	couponVO = fomatDate(couponVO);
    	
		return new Result<CouponVO>(SystemConstants.RESULT_SUCCESS,"返回成功",couponVO);
		
    }

    /**
     * 计算优惠价格
     * @param price 订单原价
     * @param code 优惠码
     * @return
     */
    @GetMapping("countCouponPrice")
    Result<String> countCouponPrice(String price,String code) {
    	
    	log.info("<=======计算优惠价格，原价:{}，优惠码：{}==========>",price,code);
    	
    	CouponVO couponVO = couponService.getCouponByCode(code);
    	
    	String msg = validCode(couponVO);
    	if(msg != null){
    		return new Result<String>(SystemConstants.RESULT_FALSE,msg,null);
    	}
    	
    	String couponType = couponVO.getCouponType(); //优惠码类型
    	
    	String newPrice = null;
    	
    	if("0".equals(couponType)){
    		//如果特价，直接返回优惠特价价格
    		newPrice = String.valueOf(couponVO.getSpecialOffer());
    	}else if("1".equals(couponType)){
    		//如果折扣，返回计算后的价格
    		BigDecimal radio = couponVO.getDiscount().divide(BigDecimal.valueOf(100));
    		newPrice = String.valueOf(new BigDecimal(price).multiply(radio).setScale(2,BigDecimal.ROUND_UP));
    	}else if("2".equals(couponType)){
    		//返回价格差
    		BigDecimal deduction = couponVO.getDeduction();
    		newPrice = String.valueOf(new BigDecimal(price).subtract(deduction));
    	}
    	
    	return new Result<String>(SystemConstants.RESULT_SUCCESS,"返回成功",newPrice);
    	
    }
    
    /**
     * 校验优惠码
     * @param couponVO 优惠码对象
     * @return
     */
    private String validCode(CouponVO couponVO){
    	
    	String msg = null;
    	
    	if(couponVO == null){
    		msg = "优惠码不存在";
    	}else{
    		
    		String validType = couponVO.getValidType(); //有效状态 0-有效、1-无效
    		if("1".equals(validType)){
    			msg = "优惠码已失效";
    		}else{
    			String useType = couponVO.getUseType(); //使用状态 0-已使用、1-未使用、2-已过期
        		if("0".equals(useType)){
        			msg = "优惠码已使用";
        		}else if("2".equals(useType)){
        			msg = "优惠码已过期";
        		}
    		}
    		
    	}
    	
    	return msg;
    }
    
    /**
     * 优惠码日期格式转换
     * @param couponVO
     * @return
     */
    private CouponVO fomatDate(CouponVO couponVO){
    	
    	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
    	SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	if(couponVO.getValidDate() != null){
    		couponVO.setNewValidDate(date.format(couponVO.getValidDate()));
    	}
    	
    	if(couponVO.getPublishDate() != null){
    		couponVO.setNewPublishDate(dateTime.format(couponVO.getPublishDate()));
    	}
    	
    	return couponVO;
    }
}