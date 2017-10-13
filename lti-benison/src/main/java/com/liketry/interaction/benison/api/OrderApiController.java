package com.liketry.interaction.benison.api;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.model.Order;
import com.liketry.interaction.benison.model.OrderDetail;
import com.liketry.interaction.benison.model.PromotionDetail;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.BenisonService;
import com.liketry.interaction.benison.service.OrderDetailService;
import com.liketry.interaction.benison.service.OrderService;
import com.liketry.interaction.benison.service.PromotionService;
import com.liketry.interaction.benison.util.CacheUtils;
import com.liketry.interaction.benison.util.PropertiesUtils;
import com.liketry.interaction.benison.util.StringUtils;
import com.liketry.interaction.benison.util.UserUtils;
import com.liketry.interaction.benison.util.WeChatUtils;

/**
 * 订单api
 *
 * @author pengyy
 */
@RestController
@RequestMapping("order_api")
public class OrderApiController {
	
	private static final Logger log = LoggerFactory.getLogger(OrderApiController.class);
	
    @Autowired
    OrderService orderService;
    
    @Autowired
    OrderDetailService orderDetailService;
    
    @Autowired
    BenisonService benisonService;
    
    @Autowired
    PromotionService promotionService;

    /**
     * 根据订单状态和openId查询订单列表
     * @param page
     * @param size
     * @param orderStatus
     * @param openId
     * @return
     */
    @RequestMapping("/findOrderListByStatus")
    Result<JSONObject> findOrderListByStatus(String data) {
    	
    	log.info("<========OrderApiController.findOrderListByStatus=========start===========>");
    	
    	List<Order> orderList = null;
    	
    	JSONObject json = UserUtils.decrypt2Str(data);
    	JSONObject returnjson = new JSONObject();
    	
    	int page = json.getInteger("page");
    	int size = json.getInteger("size");
    	String orderStatus = json.getString("orderStatus");
    	String userId = json.getString("userId");
    	
    	if(StringUtils.isEmpty(userId)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"参数不能为空",returnjson);
    	}
    	
    	orderList = orderService.findOrderListByStatus(page, size, orderStatus,userId);
    	
    	//订单总条数
    	int count = orderService.getAllCount(orderStatus,userId);
    	
    	returnjson.put("total", count);
    	returnjson.put("list", orderList);
    	
        return new Result<>(SystemConstants.RESULT_SUCCESS,"调用成功",returnjson);
    }
    
    /**
     * 根据orderId查询订单带详细表
     * @return
     */
    @RequestMapping("/findOneOrderById")
    Result<Order> findOneOrderById(String data) {
    	
    	log.info("<========OrderApiController.findOneOrderById=========start===========>");
    	
    	Order order = null;
    	
    	JSONObject json = UserUtils.decrypt2Str(data);
    	
    	String orderId = json.getString("orderId");
    	
    	if(StringUtils.isEmpty(orderId)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"参数不能为空",order);
    	}
    	
    	order = orderService.findOneOrder(orderId);
    	
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	
    	if(order!= null){
    		order.setNewCreateTime(sdf.format(order.getCreatedTime()));//时间转换格式输出
    		//查询订单列表详细
    		List<OrderDetail> orderDetailList = orderService.findOrderDetailList(orderId);
    		order.setOrderDetailList(orderDetailList);
    	}
    	
        return new Result<>(SystemConstants.RESULT_SUCCESS,"调用成功",order);
    }
    
    /**
     * 取消订单
     * @return
     */
    @RequestMapping("/updateOrderStatus")
    Result<String> updateOrderStatus(String data) {
    	
    	log.info("<========OrderApiController.updateOrderStatus=========start===========>");
    	
    	JSONObject json = UserUtils.decrypt2Str(data);
    	
    	String orderId = json.getString("orderId");
    	String userId = json.getString("userId");
    	
    	if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(userId)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"参数不能为空");
    	}
    	
        return new Result<>(SystemConstants.RESULT_SUCCESS,orderService.updateOrderStatus(orderId,userId));
    }
    
    
    /**
     * 查看订单详情
     * @return
     */
    @RequestMapping("/showOrderDetail")
    Result<OrderDetail> showOrderDetail(String data) {
    	
    	log.info("<========OrderApiController.showOrderDetail=========start===========>");
    	
    	JSONObject json = UserUtils.decrypt2Str(data);
    	
    	String orderDetailId = json.getString("orderDetailId");
    	
        return new Result<>(SystemConstants.RESULT_SUCCESS,orderDetailService.findOneOrderDetail(orderDetailId));
    }
    
    /**
     * 新增订单
     * @return
     */
    @RequestMapping(value="/saveOrder")
    Result<Order> saveOrder(String data) {

    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
    	log.info("<=======开始新增订单，时间：===="+sdf.format(new Date())+"================>");
    	//获取json串
    	JSONObject json = UserUtils.decrypt2Str(data);
    	Order order = JSONObject.toJavaObject(json, Order.class);
    	
    	//校验模板是否存在
    	String templateId = order.getTemplateId();
    	BenisonTemplate benisonTemplate = new BenisonTemplate();
    	
    	if(StringUtils.isEmpty(templateId)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"模板ID不能为null",null);
    	}else{
    		benisonTemplate = benisonService.findBenisonTemplateById(templateId);
    		if(benisonTemplate == null){
    			return new Result<>(SystemConstants.RESULT_FALSE,"当前模板不存在！",null);
    		}
    	}
    	
    	List<OrderDetail> orderDetailList = order.getOrderDetailList();
		
    	if(orderDetailList != null && orderDetailList.size()>0){
    		
    		//商品时间校验
    		String msg = checkCommodityDate(orderDetailList.get(0).getPlayDate(),order.getScreenId());
    		
    		if(msg != null){
    			return new Result<>(SystemConstants.RESULT_FALSE,msg,null);
    		}
    		
    		// 活动订单校验
    		if(!StringUtils.isEmpty(order.getPromotionId())){
    			
    			msg = checkPromotionCommdityOrderCount(order.getPromotionId(),orderDetailList);
        		
        		if(msg != null){
        			return new Result<>(SystemConstants.RESULT_FALSE,msg,null);
        		}
        		
    		}
    		
    		// 0元订单校验
    		if(new BigDecimal(0).equals(order.getTotalPrice())){
    			
    			msg = confirmZeroOrder(order.getUserId(),order.getTotalPrice());
    			
    			if(msg != null){
    				return new Result<>(SystemConstants.RESULT_FALSE,msg,null);
    			}
    		}
			
    		log.info("<=======开始库存占用，时间：===="+sdf.format(new Date())+"================>");
    		// 查商品库存缓存，并锁定
    		List<String> commodityIdList = new ArrayList<String>();
    		
    		orderDetailList.forEach(orderDetail->{
    			String commodityId = orderDetail.getCommodityId();
    			commodityIdList.add(commodityId);
    		});
    		
    		// 锁定库存缓存
    		if(!CacheUtils.lockStockMapCache(commodityIdList)){
    			return new Result<>(SystemConstants.RESULT_FALSE,"库存被占用，请过一会儿再创建！",null);
    		}
    		
    		Map<String,Object> map = new HashMap<String,Object>();
    		
    		try {
    			map = orderService.saveOrder(order,benisonTemplate);
    			// 释放库存缓存
        		CacheUtils.releaseStockMapCache(commodityIdList);
			} catch (Exception e) {
				// 释放库存缓存
			log.error(e.toString());
	    		CacheUtils.releaseStockMapCache(commodityIdList);
			}
    		log.info("<=======结束库存占用，时间：===="+sdf.format(new Date())+"================>");
    		log.info("<=======结束新增订单，时间：===="+sdf.format(new Date())+"================>");
    		
    		if("success".equals(map.get("flag"))){
    			return new Result<>(SystemConstants.RESULT_SUCCESS,"调用成功",(Order)map.get("msg"));
    		}else{
    			return new Result<>(SystemConstants.RESULT_FALSE,map.get("msg").toString(),null);
    		}
    		
    	}else{
    		return new Result<>(SystemConstants.RESULT_FALSE,"订单商品不能为空！",null);
    	}
        
    }
    
	/**
     * 新增定制订单
     * @return
     */
    @RequestMapping(value="/saveCustomOrder")
    Result<Order> saveCustomOrder(String data) {

    	//获取json串
    	JSONObject json = UserUtils.decrypt2Str(data);
    	Order order = JSONObject.toJavaObject(json, Order.class);
    	
    	List<OrderDetail> orderDetailList = order.getOrderDetailList();
		
    	if(orderDetailList != null && orderDetailList.size()>0){
    		
    		// 商品日期校验
    		String msg = checkCommodityDate(orderDetailList.get(0).getPlayDate(),order.getScreenId());
    		
    		if(msg != null){
    			return new Result<>(SystemConstants.RESULT_FALSE,msg,null);
    		}
    		
    		// 查商品库存缓存，并锁定
    		List<String> commodityIdList = new ArrayList<String>();
    		
    		orderDetailList.forEach(orderDetail->{
    			String commodityId = orderDetail.getCommodityId();
    			commodityIdList.add(commodityId);
    		});
    		
    		// 锁定库存缓存
    		if(!CacheUtils.lockStockMapCache(commodityIdList)){
    			return new Result<>(SystemConstants.RESULT_FALSE,"库存被占用，请过一会儿再创建！",null);
    		}
    		
    		Map<String,Object> map = new HashMap<String,Object>();
    		
    		try {
    			map = orderService.saveCustomOrder(order);
    			// 释放库存缓存
        		CacheUtils.releaseStockMapCache(commodityIdList);
			} catch (Exception e) {
				// 释放库存缓存
	    		CacheUtils.releaseStockMapCache(commodityIdList);
			}
    		
    		if("success".equals(map.get("flag"))){
    			return new Result<>(SystemConstants.RESULT_SUCCESS,"调用成功",(Order)map.get("msg"));
    		}else{
    			return new Result<>(SystemConstants.RESULT_FALSE,map.get("msg").toString(),null);
    		}
    		
    	}else{
    		return new Result<>(SystemConstants.RESULT_FALSE,"订单商品不能为空！",null);
    	}
    	
    }
    
    /**
     * 退单
     * @return
     */
    @RequestMapping("/returnOrder")
    Result<String> returnOrder(String data) {
    	
    	log.info("<========OrderApiController.returnOrder=========start===========>");
    	
    	JSONObject json = UserUtils.decrypt2Str(data);
    	
    	String orderId = json.getString("orderId");
    	String userId = json.getString("userId");
    	String userType = json.getString("userType");
    	String backReason = json.getString("backReason");
    	String userName = json.getString("userName");
    	String tradeType = json.getString("tradeType");//JS为小程序端，APP为APP端
    	
    	if(StringUtils.isEmpty(orderId) || StringUtils.isEmpty(userId)
    			|| StringUtils.isEmpty(userType)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"参数不能为空");
    	}
    	
        return new Result<>(SystemConstants.RESULT_SUCCESS,orderService.returnOrder(orderId,userId,userType,backReason,userName,tradeType));
    }
    
    /**
     * 退款（后台功能，财务专用）
     * @return
     */
    @RequestMapping("/orderRefund")
    Result<String> orderRefund(String data) {
    	
    	log.info("<========OrderApiController.orderRefund=========start===========>");
    	
    	JSONObject json = UserUtils.decrypt2Str(data);
    	
    	String orderId = json.getString("orderId");
    	String orderCode = json.getString("orderCode");
    	String payPrice = json.getString("payPrice");
    	String backPrice = json.getString("backPrice");
    	String userId = json.getString("userId");
    	String tradeType = json.getString("tradeType");//JS为小程序端，APP为APP端
    	
    	if(StringUtils.isEmpty(orderCode)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"退款失败，请检测您输入的订单号");
    	}
    	
    	if(StringUtils.isEmpty(backPrice)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"退款失败，请检测您输入的退款金额");
    	}
    	
    	if(StringUtils.isEmpty(tradeType)){
    		return new Result<>(SystemConstants.RESULT_FALSE,"退款失败，请检测您的退单来源");
    	}
    	
    	String refundFlag = "FAIL";
    	
    	try {
	    	if("JS".equals(tradeType)){
				refundFlag = WeChatUtils.wechatRefund_js_pc(orderCode,payPrice,backPrice);
			}else if("APP".equals(tradeType)){
				refundFlag = WeChatUtils.wechatRefund_pc(orderCode,payPrice,backPrice);
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    		return new Result<>(SystemConstants.RESULT_FALSE,"退款发生异常");
		}
    	
    	if("SUCCESS".equals(refundFlag)){
    		//修改订单状态
    		Order order = orderService.findOneOrder(orderId);
    		
    		if(order!=null){
    			order.setBackPrice(new BigDecimal(backPrice));
    			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    			order.setBackTime(sdf.format(UserUtils.getCurrentDate()));
    			order.setModifiedBy(userId);
    			order.setModifiedTime(UserUtils.getCurrentDate());
    			order.setOrderStatus("6");//订单状态：6-已退款
    			
    			//同步收付单
	    		Map<String,Object> map = orderService.syncRecDisOrder(order,false);
	    		if("false".equals(map.get("flag"))){
	    			return  new Result<>(SystemConstants.RESULT_FALSE,map.get("msg").toString());
	    		}
	    		
	    		//更新订账单
	    		map = orderService.updateBookAcountOrder(order);
	    		if("false".equals(map.get("flag"))){
	    			return  new Result<>(SystemConstants.RESULT_FALSE,map.get("msg").toString());
	    		}
	    		
    			orderService.updateOrder(order);
    		}
    		
    		return new Result<>(SystemConstants.RESULT_SUCCESS,"退款成功");
    	}else{
    		return new Result<>(SystemConstants.RESULT_FALSE,"退款失败");
    	}
        
    }
    
    /**
     * 校验一个用户一天内只能下一个0元订单
     * @param userId
     * @param payPrice
     * @return
     */
    private String confirmZeroOrder(String userId,BigDecimal totalPrice){
    	
    	String msg = null;
    	
    	Map<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("userId", userId);
    	paramMap.put("totalPrice", totalPrice);
    	
    	int count = orderService.getZeroOrderList(paramMap);
    	
    	log.info("<=====该用户的0元订单数：{}===========>",count);
    	
    	if(count > 0){
    		msg = "您今天已经下了一个0元订单，请选择其他类型订单";
    	}
    	
		return msg;
    }
    
    /**
     * 查询用户是否有订单记录（包括：未支付，已支付，已播放）
     * @param json
     */
    @GetMapping("/confirmFirstOrder")
    public Result<Object> confirmFirstOrder(String data){
    	
    	JSONObject json = UserUtils.decrypt2Str(data);
    	
    	if(json == null){
    		return new Result<>(SystemConstants.RESULT_FALSE,"数据不能为空！"); 
    	}
    	
    	Map<String,Object> returnMap = new HashMap<String,Object>();
    	
    	String userId = json.getString("userId");
    	int count = orderService.getValidOrderList(userId);
    	
    	log.info("<=====该用户的有效订单数：{}===========>",count);
    	
    	if(count > 0){
    		returnMap.put("firstOrder", 0); // 普通用户
    	}else{
    		returnMap.put("firstOrder", 1); // 首单用户
    	}
    	
		return new Result<>(SystemConstants.RESULT_SUCCESS,returnMap);
    }
    
    /**
     * 校验商品是否符合下单时间内
     * @param playDate
     * @param screenId
     * @return
     */
    public String checkCommodityDate(String playDate,String screenId){
    	
    	String commodity_day_range_after = PropertiesUtils.getInstance().getValue("commodity_day_range_after");
    	String HuangShang_day_range_after = PropertiesUtils.getInstance().getValue("HuangShang_day_range_after");
    	
    	log.info("<===商品时间的校验：{}天后的商品不能下单======>",commodity_day_range_after);
    	
    	if(!StringUtils.isEmpty(playDate)){
    		
    		String HuangShanScreenId = PropertiesUtils.getInstance().getValue("HuangShan_screenId"); // 黄山屏幕Id
    		
    		try {
    			
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    		
	    		Calendar sevenLater = Calendar.getInstance();  
	    		Date today = sdf.parse(sdf.format(new Date()));
				sevenLater.setTime(today);  
				sevenLater.add(Calendar.DATE, Integer.parseInt(commodity_day_range_after)); 
				Date sevenDayAfter = sdf.parse(sdf.format(sevenLater.getTime()));
	    		
	    		if(HuangShanScreenId.equals(screenId)){
	    			log.info("<=====黄山商品{}天后才可以下单======>",HuangShang_day_range_after);
	        		// 黄山景区三天内不能下单，七天外不能下单
	    			Calendar threeBefore = Calendar.getInstance();  
	    			threeBefore.setTime(today);  
	    			threeBefore.add(Calendar.DATE, Integer.parseInt(HuangShang_day_range_after)); 
	    			Date threeDayBefore = sdf.parse(sdf.format(threeBefore.getTime()));
	    			if(sdf.parse(playDate).after(sevenDayAfter)){
    					return "该商品播放日期不在七天范围内，请您重新选择日期！";
    				}
	    			if(sdf.parse(playDate).before(threeDayBefore)){
    					return "黄山送祝福需要您提前三天预定，请您重新选择日期！";
    				}
	        	}else{
	        		// 其他景区七天外不能下单
    				if(sdf.parse(playDate).after(sevenDayAfter)){
    					return "该商品播放日期不在七天范围内，请您重新选择日期！";
    				}
	        	}
    		
    		} catch (ParseException e) {
				return "播放日期格式有误（格式说明：yyyy-MM-dd）";
			}
    		
    	}else{
			return "请您重新选择日期";
		}
		
		return null;
    }
    
    /**
     * 校验该活动下的该商品已下的订单数（不得超过规定数目）
     * @param promotionId
     * @param orderDetailList
     * @return
     */
    private String checkPromotionCommdityOrderCount(String promotionId, List<OrderDetail> orderDetailList) {
    	
    	log.info("<=====校验活动下的商品订单数，活动Id:{},订单详情列表:{}=========>",promotionId);
		String msg = null;
		
    	for(OrderDetail orderDetail:orderDetailList){
    		
    		String commodityId = orderDetail.getCommodityId();
    		Map<String,Object> paramMap = new HashMap<String,Object>();
    		paramMap.put("promotionId", promotionId);
    		paramMap.put("commodityId", commodityId);
    		
    		//查询活动下该商品的个数
    		List<PromotionDetail> promotionDetailList = promotionService.getPromotionDetailList(paramMap);
    		if(promotionDetailList != null && promotionDetailList.size() > 0){
    			PromotionDetail promotionDetail = promotionDetailList.get(0);
    			int commodityNum = promotionDetail.getCommodityNum();
    			
    			if(commodityNum >= 0){
    				//查询该活动，该商品，当天的订单数
        			paramMap.put("playDate", orderDetail.getPlayDate());
        			int count = orderService.selectOrderCountByPIdAndCId(paramMap);
        			if(count > commodityNum){
        				msg = orderDetail.getPlayDate()+"日活动关联的商品只能定制"+commodityNum+"个，请选择其他商品或活动！";
        			}
    			}
    			
    		}else{
    			log.error("<===活动ID：{}，商品ID：{}=========>",promotionId,commodityId);
    			msg = "活动与商品关联错误！";
    		}
    		
    	}
    	
		return msg;
	}
}
