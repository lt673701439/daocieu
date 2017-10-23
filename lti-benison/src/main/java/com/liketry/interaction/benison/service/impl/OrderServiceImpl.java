/**
 * @author pengyy
 * created at 2017/5/23 18:15
 */
package com.liketry.interaction.benison.service.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.dao.CouponMapper;
import com.liketry.interaction.benison.dao.OrderDetailMapper;
import com.liketry.interaction.benison.dao.OrderMapper;
import com.liketry.interaction.benison.dao.ScreenMapper;
import com.liketry.interaction.benison.dao.StockDetailMapper;
import com.liketry.interaction.benison.dao.StockMapper;
import com.liketry.interaction.benison.dao.UserMapper;
import com.liketry.interaction.benison.model.BenisonTemplate;
import com.liketry.interaction.benison.model.Coupon;
import com.liketry.interaction.benison.model.Order;
import com.liketry.interaction.benison.model.OrderDetail;
import com.liketry.interaction.benison.model.Screen;
import com.liketry.interaction.benison.model.Stock;
import com.liketry.interaction.benison.model.StockDetail;
import com.liketry.interaction.benison.model.User;
import com.liketry.interaction.benison.service.BizSeqNoService;
import com.liketry.interaction.benison.service.OrderService;
import com.liketry.interaction.benison.util.HttpUtil;
import com.liketry.interaction.benison.util.MakeImgUtils;
import com.liketry.interaction.benison.util.PropertiesUtils;
import com.liketry.interaction.benison.util.SensitivewordFilter;
import com.liketry.interaction.benison.util.SmsUtils;
import com.liketry.interaction.benison.util.StringUtils;
import com.liketry.interaction.benison.util.UserUtils;
import com.liketry.interaction.benison.util.WeChatUtils;

/**
 * 订单service
 * 
 * @author pengyy
 *
 */
@Service
public class OrderServiceImpl implements OrderService {
	
    @Autowired
    OrderMapper orderMapper;
    
    @Autowired
    OrderDetailMapper orderDetailMapper;
    
    @Autowired
    StockMapper stockMapper;
    
    @Autowired
    BizSeqNoService bizSeqNoService;
    
    @Autowired
    StockDetailMapper stockDetailMapper;
    
    @Autowired
    ScreenMapper screenMapper;
    
    @Autowired
    UserMapper userMapper;
    
    @Autowired
    CouponMapper couponMapper;
    
    
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    
    private static PropertiesUtils pro = PropertiesUtils.getInstance();
    
    /**
     * 根据用户Id查询订单列表
     */
    @Override
    public PageInfo<Order> findOrderListByUserId(int pageSize, int pageNumber,String userId) {
        PageHelper.startPage(pageSize, pageNumber);
        List<Order> user = orderMapper.findOrderListByUserId(userId);
        PageInfo<Order> page = new PageInfo<Order>(user);
        return page;
    }
    
    /**
     * 根据状态和openId查询订单列表
     */
    @Override
    public List<Order> findOrderListByStatus(int pageSize, int pageNumber,String orderStatus,String userId) {
    	
    	log.info("<=====OrderServiceImpl.findOrderListByStatus====start=======>");
    	
//        PageHelper.startPage(pageSize, pageNumber);
        //查询订单列表
        List<Order> orderList = orderMapper.findOrderListByStatus((pageSize-1)*pageNumber,pageSize*pageNumber,orderStatus,userId);
        
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if(orderList!=null && orderList.size()>0){
        	orderList.forEach(order->{
            	String orderId = order.getOrderId();
            	order.setNewCreateTime(sdf.format(order.getCreatedTime()));//时间转换格式输出
            	//查询订单列表详细
            	List<OrderDetail> orderDetailList = orderDetailMapper.findOrderDetailList(orderId);
            	order.setOrderDetailList(orderDetailList);
            });
        }
        
//        PageInfo<Order> page = new PageInfo<Order>(orderList);
        return orderList;
    }
    
    /**
     * 取消订单
     */
	@Override
	public String updateOrderStatus(String orderId,String userId) {
		
		log.info("<=====OrderServiceImpl.updateOrderStatus====start=======>");
		
		//判断订单是否存在
		Order order = orderMapper.selectByPrimaryKey(orderId);
		if(order==null){
			return "该订单不存在！";
		}
		
		order.setOrderStatus(SystemConstants.canceled);
		order.setModifiedBy(userId);
		order.setModifiedTime(UserUtils.getCurrentDate());
		order.setVersion(order.getVersion()+1);
		
		int count = orderMapper.updateByPrimaryKey(order);
		
		if(count>0){
			return "操作成功！";
		}else{
			log.error("<=====cancel order is failed===orderCode:{}=====>",order.getOrderCode());
			return "操作失败！";
		}
	}
	
	/**
     * 更新订单状态
     */
	@Override
	public String updateOrderStatus(String orderId, String userId, String status) {
		
		log.info("<=====OrderServiceImpl.updateOrderStatus====start=======>");
		
		//判断订单是否存在
		Order order = orderMapper.selectByPrimaryKey(orderId);
		if(order==null){
			return "该订单不存在！";
		}
		
		order.setOrderStatus(status);
		order.setModifiedBy(userId);
		order.setModifiedTime(UserUtils.getCurrentDate());
		order.setVersion(order.getVersion()+1);
		
		int count = orderMapper.updateByPrimaryKey(order);
		
		if(count>0){
			return "操作成功！";
		}else{
			log.error("<=====OrderServiceImpl.updateOrderStatus is failed===orderCode:{}=====>",order.getOrderCode());
			return "操作失败！";
		}
	}
	
	/**
     * 根据订单编号，更新订单状态、交易单号、支付价格、支付类型
     * 交易单号不能为空
     */
	@Override
	@Transactional
	public String updateOrderStatus(String orderId, String userId, String transactionNo, BigDecimal payPrice, String payType) {
		
		log.info("<=====OrderServiceImpl.updateOrderStatus====start=======>");
		
		//判断订单是否存在
		Order order = orderMapper.selectByPrimaryKey(orderId);
		if(order==null){
			return "该订单不存在！";
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		order.setOrderStatus("2"); // 已支付
		if(transactionNo != null){
			order.setTransactionNo(transactionNo);
		}
		order.setPayType(payType);
		order.setPayTime(dateFormat.format(new Date()));
		order.setPayPrice(payPrice);
		order.setModifiedBy(userId);
		order.setModifiedTime(UserUtils.getCurrentDate());
		order.setVersion(order.getVersion()+1);
		
		int count = orderMapper.updateByPrimaryKey(order);
		
		if(count>0){
			//修改优惠码状态
			String couponId = order.getCouponId();
			if(!StringUtils.isEmpty(couponId)){
				Coupon coupon = couponMapper.selectByPrimaryKey(couponId);
				if(coupon != null){
					coupon.setUseType("0"); //已使用
					coupon.setOrderCode(order.getOrderCode());
					coupon.setUseTime(UserUtils.getCurrentDate());
					coupon.setModifiedTime(UserUtils.getCurrentDate());
					coupon.setModifiedBy(order.getUserId());
					int couponCount = couponMapper.updateByPrimaryKey(coupon);
					if(couponCount <=0 ){
						log.info("<===支付时，修改优惠码状态错误，订单号：{}，优惠码Id：{}，=======>",order.getOrderCode(),couponId);
					}
				}
			}
			
			//同步收付单
			Map<String,Object> map = syncRecDisOrder(order,true);
			if("false".equals(map.get("flag"))){
				return map.get("msg").toString();
			}
			
			//更新订账单
			map = updateBookAcountOrder(order);
			if("false".equals(map.get("flag"))){
				return map.get("msg").toString();
			}
			
			log.info("<=====根据订单编号，更新订单状态、交易单号、支付价格、支付类型====操作成功！=======>");
			return "操作成功！";
		}else{
			log.error("<=====OrderServiceImpl.updateOrderStatus is failed===orderCode:{}=====>",order.getOrderCode());
			return "操作失败！";
		}
	}
	
	/**
	 * 新增订单
	 */
	@Override
	@Transactional
	public Map<String,Object> saveOrder(Order order,BenisonTemplate benisonTemplate) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");
    	
		//新增主订单
		String orderId = UUID.randomUUID().toString().replace("-", "");
		order.setOrderId(orderId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		
		//查询屏幕编号
		Screen screen = screenMapper.selectByPrimaryKey(order.getScreenId());
		String screenCode = screen.getScreenCode();
		if(StringUtils.isEmpty(screenCode)){
			map.put("flag",SystemConstants.RESULT_FALSE);
			map.put("msg",  "屏幕编号为空");
			return map;
		}
		
		// 根据编号获取最新的订单
		Order lastOrder = orderMapper.findLastOne(screenCode);
		
		// 截取尾号
		String lastNum = "001";
		if(lastOrder != null){
			String lastOrderCode = lastOrder.getOrderCode();
			int oldLastNum = Integer.parseInt(lastOrderCode.substring(lastOrderCode.length()-3));
			int newLastNum = oldLastNum+1;
			
			// 补位
			if(newLastNum <= 9){
				lastNum = "00"+newLastNum;
			}else if(newLastNum <= 99){
				lastNum = "0"+newLastNum;
			}else {
				lastNum = ""+newLastNum;
			}
		}
		
		log.info("<=======开始制作图片，时间：===="+sdf3.format(new Date())+"================>");
		//制作图片
		String path = PropertiesUtils.getInstance().getValue("default_upload_filepath")+"export/order/"+sdf2.format(UserUtils.getCurrentDate())+"/";
		
		Map<String,String> backMap = MakeImgUtils.makeImg(benisonTemplate, order.getWriteName(),order.getBlessName(),path,1200);
		
		log.info("<=======结束制作图片，时间：===="+sdf3.format(new Date())+"================>");
		
		if("false".equals(backMap.get("result"))){
			map.put("flag",SystemConstants.RESULT_FALSE);
			map.put("msg", backMap.get("msg"));
			return map;
		}
		
		order.setOrderCode("ORD"+screenCode+sdf.format(UserUtils.getCurrentDate()).replace("-", "")
				+lastNum);
		order.setCreatedTime(UserUtils.getCurrentDate());
		order.setResultUrl(backMap.get("msg").toString().
				 replace(PropertiesUtils.getInstance().getValue("default_upload_filepath"), ""));//制作好的图片地址
		order.setCreatedBy(order.getUserId());
		order.setOrderType("0");//0-普通，1-定制
		order.setConfirmStatus("0");//0-拒绝，1-有效
		order.setVersion(1);
		order.setDelflag("1");
		
		//如果是商户入口,同步订账单
		if(!StringUtils.isEmpty(order.getScene())){
			map = syncBookAcountOrder(order);
			if("false".equals(map.get("flag"))){
				map.put("flag",SystemConstants.RESULT_FALSE);
				map.put("msg", map.get("msg"));
				return map;
			}
			if(map.get("msg") != null){
				JSONObject json = (JSONObject)map.get("msg");
				order.setBookAcountId(json.getString("id"));
			}
		}
		
		int count = orderMapper.insert(order);
		
		Order newOrder = null;
		log.info("<=======开始新增订单详细，时间：===="+sdf3.format(new Date())+"================>");
		if(count>0){
			//新增订单详细
			List<OrderDetail> orderDetailList = order.getOrderDetailList();
			
			if(orderDetailList!=null && orderDetailList.size()>0){
				
				for(OrderDetail orderDetail:orderDetailList){
					
					orderDetail.setOrderId(orderId);
					orderDetail.setDetailId(UUID.randomUUID().toString().replace("-", ""));
					orderDetail.setCreatedBy(order.getUserId());
					orderDetail.setCreatedTime(UserUtils.getCurrentDate());
					orderDetail.setVersion(1);
					orderDetail.setDelflag("1");
					int orderDetailCount = orderDetailMapper.insert(orderDetail);
					log.info("<=======开始修改库存，时间：===="+sdf3.format(new Date())+"================>");
					//修改库存销量
					if(orderDetailCount>0 && !StringUtils.isEmpty(orderDetail.getCommodityId())){
						//查询商品是否有库存
						Stock stock = stockMapper.findStockByCommodityId(orderDetail.getCommodityId(),orderDetail.getPlayDate());
						if(stock!=null){
							if(stock.getStock()>0){
								stock.setSales(stock.getSales()+1);
								stock.setStock(stock.getStock()-1);
								stock.setModifiedBy(order.getUserId());
								stock.setModifiedTime(UserUtils.getCurrentDate());
								stock.setVersion(stock.getVersion()+1);
								stockMapper.updateByPrimaryKey(stock);
								//新增库存明细
								StockDetail stockDetail = new StockDetail();
								stockDetail.setDetailId(UUID.randomUUID().toString().replaceAll("-", ""));
								stockDetail.setOrderId(orderId);
								stockDetail.setStockId(stock.getStockId());
								stockDetail.setStartTime(stock.getStartTime());
								stockDetail.setEndTime(stock.getEndTime());
								stockDetail.setCreatedTime(UserUtils.getCurrentDate());
								stockDetail.setCreatedBy(order.getUserId());
								stockDetailMapper.insert(stockDetail);
							}else{
								map.put("flag",SystemConstants.RESULT_FALSE);
								map.put("msg",  "该时间段："+orderDetail.getTimeStart()+"-"+orderDetail.getTimeEnd()+"的商品库存不足，请重新选择商品");
								return map;
							}
						}else{
							map.put("flag",SystemConstants.RESULT_FALSE);
							map.put("msg",  "商品:"+orderDetail.getCommodityCode()+"没有相应库存信息");
							return map;
						}
						log.info("<=======结束修改库存，时间：===="+sdf3.format(new Date())+"================>");
					}else{
						map.put("flag",SystemConstants.RESULT_FALSE);
						map.put("msg",  "订单详细新增失败");
						return map;
					}
					log.info("<=======结束新增订单详细，时间：===="+sdf3.format(new Date())+"================>");
				}
			}
			//查询订单详情并返回页面
			newOrder = orderMapper.selectByPrimaryKey(orderId);
			
			if(newOrder!=null){
				newOrder.setOrderDetailList(orderDetailMapper.findOrderDetailList(orderId));
			}
			//发送邮件通知客服
//			MailUtils cn = MailUtils.getInstance();
			// 设置发件人地址、收件人地址和邮件标题
//	        cn.setAddress("yangyang.peng@liketry.com", "wei.zhong@liketry.com", "新订单提醒");
	        //发送
//	        cn.send("单号为"+newOrder.getOrderCode()+"的订单已提交，请您及时处理。");
			//短信json串
			JSONObject json = new JSONObject();
			json.put("code", newOrder.getOrderCode());
			try {
				SmsUtils.getInstance().sendSms(pro.getValue("sms_receivers"), pro.getValue("new_order_signName"), 
						pro.getValue("new_order_templateCode"), json.toJSONString());
			} catch (Exception e) {
				log.error("<=====短信发送失败，异常信息：{}===========>",e);
			}
			
			map.put("flag",SystemConstants.RESULT_SUCCESS);
			map.put("msg",  newOrder);
			return map;
		}else{
			map.put("flag",SystemConstants.RESULT_FALSE);
			map.put("msg",  "主订单新增失败");
			return map;
		}
		
	}
	
	/**
	 * 新增定制订单
	 */
	@Override
	@Transactional
	public Map<String,Object> saveCustomOrder(Order order) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//新增主订单
		String orderId = UUID.randomUUID().toString().replace("-", "");
		order.setOrderId(orderId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
		
		//查询屏幕编号
		Screen screen = screenMapper.selectByPrimaryKey(order.getScreenId());
		String screenCode = screen.getScreenCode();
		if(StringUtils.isEmpty(screenCode)){
			log.error("<=====该订单没有绑定相应的屏幕=======>");
		}
		
		// 根据编号获取最新的订单
		Order lastOrder = orderMapper.findLastOne(screenCode);
		
		// 截取尾号
		String lastNum = "001";
		if(lastOrder != null){
			String lastOrderCode = lastOrder.getOrderCode();
			int oldLastNum = Integer.parseInt(lastOrderCode.substring(lastOrderCode.length()-3));
			int newLastNum = oldLastNum+1;
			
			// 补位
			if(newLastNum <= 9){
				lastNum = "00"+newLastNum;
			}else if(newLastNum <= 99){
				lastNum = "0"+newLastNum;
			}else {
				lastNum = ""+newLastNum;
			}
		}
		
		//如果是自制订单，背景图拷贝结果图，用来审核
		if("1".equals(order.getOrderType())){//类型：0-普通，1-自制，2-DIY，3-定制
			order.setBkimgUrl(order.getResultUrl());
		}
		
		//如果是DIY订单，需要将背景图和祝福语内容合成
		if("2".equals(order.getOrderType())){
			
			if(!StringUtils.isEmpty(order.getBkimgUrl())){
				
				String localPath = "export/order/"+sdf2.format(UserUtils.getCurrentDate())+"/";
				String path = PropertiesUtils.getInstance().getValue("default_upload_filepath")+localPath;
				
				//祝福语内容敏感词提示
				if(!StringUtils.isEmpty(order.getBenisonContent())){
					SensitivewordFilter filter = new SensitivewordFilter();
					log.info("待检测语句字数：" + order.getBenisonContent().length());
					Set<String> set = filter.getSensitiveWord(order.getBenisonContent(), 1);
					if(set!=null&&set.size()>0){
						map.put("flag",SystemConstants.RESULT_FALSE);
						map.put("msg",  "语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
						return map;
					}
				}
				
				Map<String,String> backMap = MakeImgUtils.makeImgForCustomization(order,path);
				
				if("false".equals(backMap.get("result"))){
					map.put("flag",SystemConstants.RESULT_FALSE);
					map.put("msg", backMap.get("msg"));
					return map;
				}
				order.setResultUrl(localPath+backMap.get("fileName"));
			}
		}
		
		order.setOrderCode("ORD"+screenCode+sdf.format(UserUtils.getCurrentDate()).replace("-", "")
				+lastNum);
		order.setCreatedTime(UserUtils.getCurrentDate());
		order.setCreatedBy(order.getUserId());
//		order.setOrderType("1");
		order.setConfirmStatus("0");//0-拒绝，1-有效
		order.setVersion(1);
		order.setDelflag("1");
		
		//如果是商户入口,同步订账单
		if(!StringUtils.isEmpty(order.getScene())){
			map = syncBookAcountOrder(order);
			if("false".equals(map.get("flag"))){
				map.put("flag",SystemConstants.RESULT_FALSE);
				map.put("msg", map.get("msg"));
				return map;
			}
			if(map.get("msg") != null){
				JSONObject json = (JSONObject)map.get("msg");
				order.setBookAcountId(json.getString("id"));
			}
		}
				
		int count = orderMapper.insert(order);
		
		Order newOrder = null;
		if(count>0){
			//新增订单详细
			List<OrderDetail> orderDetailList = order.getOrderDetailList();
			
			if(orderDetailList!=null && orderDetailList.size()>0){
				
				for(OrderDetail orderDetail:orderDetailList){
					
					orderDetail.setOrderId(orderId);
					orderDetail.setDetailId(UUID.randomUUID().toString().replace("-", ""));
					orderDetail.setCreatedBy(order.getUserId());
					orderDetail.setCreatedTime(UserUtils.getCurrentDate());
					orderDetail.setVersion(1);
					orderDetail.setDelflag("1");
					int orderDetailCount = orderDetailMapper.insert(orderDetail);
					//修改库存销量
					if(orderDetailCount>0 && !StringUtils.isEmpty(orderDetail.getCommodityId())){
						//查询商品是否有库存
						Stock stock = stockMapper.findStockByCommodityId(orderDetail.getCommodityId(),orderDetail.getPlayDate());
						if(stock!=null){
							
							if(stock.getStock()>0){
								
								stock.setSales(stock.getSales()+1);
								stock.setStock(stock.getStock()-1);
								stock.setModifiedBy(order.getUserId());
								stock.setModifiedTime(UserUtils.getCurrentDate());
								stock.setVersion(stock.getVersion()+1);
								stockMapper.updateByPrimaryKey(stock);
								//新增库存明细
								StockDetail stockDetail = new StockDetail();
								stockDetail.setDetailId(UUID.randomUUID().toString().replaceAll("-", ""));
								stockDetail.setOrderId(orderId);
								stockDetail.setStockId(stock.getStockId());
								stockDetail.setStartTime(stock.getStartTime());
								stockDetail.setEndTime(stock.getEndTime());
								stockDetail.setCreatedTime(UserUtils.getCurrentDate());
								stockDetail.setCreatedBy(order.getUserId());
								stockDetailMapper.insert(stockDetail);
								
							}else{
								map.put("flag",SystemConstants.RESULT_FALSE);
								map.put("msg",  "该时间段："+orderDetail.getTimeStart()+"-"+orderDetail.getTimeEnd()+"的商品库存不足，请重新选择商品");
								return map;
							}
						}else{
							map.put("flag",SystemConstants.RESULT_FALSE);
							map.put("msg",  "商品:"+orderDetail.getCommodityCode()+"没有相应库存信息");
							return map;
						}
					}else{
						map.put("flag",SystemConstants.RESULT_FALSE);
						map.put("msg",  "订单详细新增失败");
						return map;
					}
				}
			}
			//查询订单详情并返回页面
			newOrder = orderMapper.selectByPrimaryKey(orderId);
			
			if(newOrder!=null){
				newOrder.setOrderDetailList(orderDetailMapper.findOrderDetailList(orderId));
			}
			
			//发送邮件通知客服
//			MailUtils cn = MailUtils.getInstance();
			// 设置发件人地址、收件人地址和邮件标题
//	        cn.setAddress("yangyang.peng@liketry.com", "wei.zhong@liketry.com", "新订单提醒");
	        //发送
//	        cn.send("单号为"+newOrder.getOrderCode()+"的订单已提交，请您及时处理。");
			//短信json串
			JSONObject json = new JSONObject();
			json.put("code", newOrder.getOrderCode());
			try {
				SmsUtils.getInstance().sendSms(pro.getValue("sms_receivers"), pro.getValue("new_order_signName"), 
						pro.getValue("new_order_templateCode"), json.toJSONString());
			} catch (Exception e) {
				log.error("<=====短信发送失败，异常信息：{}===========>",e);
			}
	        
	        map.put("flag",SystemConstants.RESULT_SUCCESS);
			map.put("msg",  newOrder);
			return map;
		}else{
			map.put("flag",SystemConstants.RESULT_FALSE);
			map.put("msg",  "主订单新增失败");
			return map;
		}
		
	}
	
	 /**
     * 退单
     */
	@Override
	@Transactional
	public String returnOrder(String orderId,String userId,String userType,String backReason,String userName,String tradeType) {
		
		log.info("<=====OrderServiceImpl.returnOrder====start=======>");
		
		String msg = null;
		Boolean orderFlag = false;//是否可以退单
		Boolean scheduleFlag = false;//是否排期
		//判断订单是否存在
		Order order = orderMapper.selectByPrimaryKey(orderId);
		if(order==null){
			return "该订单不存在,请向管理员查询问题！";
		}
		//如果订单为退单状态，则不进行退单操作
		if(order.getOrderStatus()!=null && SystemConstants.returnOrder.equals(order.getOrderStatus())){
			return "该订单已经退单，请不要重复操作！";
		}
		
		if("0".equals(userType)){
			//如果是客户，则判断订单状态
			String orderStatus = order.getOrderStatus();//订单状态
			
			if(orderStatus!=null && SystemConstants.Payment.equals(orderStatus)){
				//如果是待支付状态则更新为已取消
				order.setOrderStatus(SystemConstants.canceled);
				order.setModifiedBy(userId);
				order.setModifiedTime(UserUtils.getCurrentDate());
				order.setVersion(order.getVersion()+1);
				
				int count = orderMapper.updateByPrimaryKey(order);
				
				if(count>0){
					orderFlag = true;
					msg = "退单成功！";
				}else{
					log.error("<=====return order is failed===orderCode:{}=====>",order.getOrderCode());
					msg = "退单失败！";
				}
				
			}else if(orderStatus!=null && SystemConstants.Paid.equals(orderStatus)){
				//如果是已支付，判断库存的排期状态
				
				List<Stock> stockList = findStockByOrderId(orderId);
				//如果有一个商品的库存进入已排期，则订单不能退单
				for(Stock stock:stockList){
					String scheduleStatus = stock.getScheduleStatus();//排期状态
					if(SystemConstants.scheduleStatused.equals(scheduleStatus)){
						scheduleFlag = true;
					}
				}
				
				if(scheduleFlag){
					//已排期
					msg = "该订单已排期，不能退单！";
				}else{
					//未排期，判断订单价格
					Double price = order.getTotalPrice().doubleValue();
					if(price>1000){
						//订单价格大于1000
						orderFlag = true;
						msg = "请直接联系客服退款！";
					}else{
						
						//调用退款服务
						String refundFlag = null;
						try {
							
							if("JS".equals(tradeType)){
								refundFlag = WeChatUtils.wechatRefund_js(order.getOrderCode(),String.valueOf(order.getPayPrice()));
							}else if("APP".equals(tradeType)){
								refundFlag = WeChatUtils.wechatRefund(order.getOrderCode(),String.valueOf(order.getPayPrice()));
							}
							
						} catch (Exception e) {
							log.error("<=======订单退单失败：调用退款服务发生异常,异常信息为:"+e+"==============>");
						}
						
						if("SUCCESS".equals(refundFlag)){
							//退款成功后，修改订单状态为已退款
							order.setBackPrice(order.getPayPrice());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							order.setBackTime(sdf.format(UserUtils.getCurrentDate()));
							order.setOrderStatus(SystemConstants.refunded);
							order.setModifiedBy(userId);
							order.setModifiedTime(UserUtils.getCurrentDate());
							order.setVersion(order.getVersion()+1);
							
							//同步收付单
							Map<String,Object> map = syncRecDisOrder(order,false);
							if("false".equals(map.get("flag"))){
								msg = map.get("msg").toString();
							}
							
							//更新订账单
							map = updateBookAcountOrder(order);
							if("false".equals(map.get("flag"))){
								msg = map.get("msg").toString();
							}
							
							int count = orderMapper.updateByPrimaryKey(order);
							
							if(count>0){
								orderFlag = true;
								msg = "退单成功！";
							}else{
								log.error("<=====return order is failed===orderCode:{}=====>",order.getOrderCode());
								msg = "退单失败！";
							}
						}else{
							msg = "退款失败";
						}
						
					}
				}	
				
			}else{
				log.debug("<=====orderStatus is"+order.getOrderStatus()+"===orderCode:{}=====>",order.getOrderCode());
				msg = "不能退单！";
			}
		}else{
			
			//调用退款服务
			String refundFlag = null;
			try {
				
				if("JS".equals(tradeType)){
					refundFlag = WeChatUtils.wechatRefund_js(order.getOrderCode(),String.valueOf(order.getPayPrice()));
				}else if("APP".equals(tradeType)){
					refundFlag = WeChatUtils.wechatRefund(order.getOrderCode(),String.valueOf(order.getPayPrice()));
				}
				
			} catch (Exception e) {
				log.error("<=======订单退单失败：调用退款服务发生异常,异常信息为:"+e+"==============>");
			}
			
			if("SUCCESS".equals(refundFlag)){
				//如果是客服，直接退单
				order.setOrderStatus(SystemConstants.refunded);
				order.setBackPrice(order.getPayPrice());
				order.setBackReason(backReason);
				order.setBackBy(userName);
				order.setBackId(userId);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				order.setBackTime(sdf.format(UserUtils.getCurrentDate()));
				order.setModifiedBy(userId);
				order.setModifiedTime(UserUtils.getCurrentDate());
				order.setVersion(order.getVersion()+1);
				
				//同步收付单
				Map<String,Object> map = syncRecDisOrder(order,false);
				if("false".equals(map.get("flag"))){
					msg = map.get("msg").toString();
				}
				
				//更新订账单
				map = updateBookAcountOrder(order);
				if("false".equals(map.get("flag"))){
					msg = map.get("msg").toString();
				}
				
				int count = orderMapper.updateByPrimaryKey(order);
				
				if(count>0){
					orderFlag = true;
					msg = "退单成功！";
				}else{
					log.error("<=====return order is failed===orderCode:{}=====>",order.getOrderCode());
					msg = "订单编号："+order.getOrderCode()+"退单失败！";
				}
			}else{
				msg = "退款失败";
			}
		}
		
		if(orderFlag){
			
			//如果完成退单，更新库存状态
			List<Stock> stockList = findStockByOrderId(orderId);
			if(stockList!=null && stockList.size()>0){
				stockList.forEach(stock->{
					//修改库存销量
					stock.setSales(stock.getSales()-1);
					stock.setStock(stock.getStock()+1);
					stock.setStockStatus(SystemConstants.freedom);
					stock.setModifiedBy(order.getUserId());
					stock.setModifiedTime(UserUtils.getCurrentDate());
					stock.setVersion(stock.getVersion()+1);
					stockMapper.updateByPrimaryKey(stock);
					//删除对应的库存明细
					StockDetail stockDetail = new StockDetail();
					stockDetail.setOrderId(orderId);
					stockDetail.setStockId(stock.getStockId());
					stockDetailMapper.deleteStockDetail(stockDetail);
				});
			}
			
		}
		
		return msg;
	}
	
	/**
	 * 根据订单ID查询库存列表
	 * @param orderId
	 * @return
	 */
	public List<Stock> findStockByOrderId(String orderId){
		
		log.info("<====OrderServiceImpl.findStockByOrderId===start====>");
		
		List<OrderDetail> orderDetailList = orderDetailMapper.findOrderDetailList(orderId);
		List<Stock> stockList = new ArrayList<Stock>();
		if(orderDetailList!=null && orderDetailList.size()>0){
			orderDetailList.forEach(orderDetail->{
				//查询商品是否有库存
				if(!StringUtils.isEmpty(orderDetail.getCommodityId())){
					//查询商品是否有库存
					Stock stock = stockMapper.findStockByCommodityId(orderDetail.getCommodityId(),orderDetail.getPlayDate());
					if(stock!=null){
						stockList.add(stock);
					}else{
						log.error("<====commodityCode:{} have not stock!!============>",orderDetail.getCommodityCode());
					}
				}
			});
		}
		
		return stockList;
	}
	
	/**
	 * 根据主键查询一条订单记录
	 * @param orderId
	 * @return
	 */
	public Order findOneOrder(String orderId){
		
		return orderMapper.selectByPrimaryKey(orderId);
	}
	
	/**
     * 根据orderId查询订单详细列表
     */
    @Override
    public List<OrderDetail> findOrderDetailList(String orderId) {
    	
        return orderDetailMapper.findOrderDetailList(orderId);
    }
    
    /**
     * 修改订单信息
     * @param order
     * @return
     */
    public int updateOrder(Order order){
    	
    	return orderMapper.updateByPrimaryKey(order);
    }
	
    
    /**
     * 获取订单总数
     * @return
     */
    public int getAllCount(String orderStatus,String userId){
    	
    	return orderMapper.getAllCount(orderStatus,userId);
    }
    
    /**
     * 同步收付单
     * @param order(订单信息)
     * @param flag 是否是支付单
     */
    public Map<String,Object> syncRecDisOrder(Order order,Boolean flag){
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	//组装数据
    	JSONObject json = new JSONObject();
    	
    	//收付单金额：支付为正，退款为负
    	if(flag){
    		json.put("recDisPrice", order.getPayPrice());
    	}else{
    		json.put("recDisPrice", "-"+order.getBackPrice());
    	}
    	json.put("price", order.getPayPrice());//订单金额
    	json.put("commodityId", order.getScene());//商户ID
    	json.put("orderId", order.getOrderId());//订单ID
    	json.put("userId", order.getUserId());//用户ID
    	json.put("orderCode", order.getOrderCode());//订单编号
    	json.put("bookAcountId", order.getBookAcountId());//订账单ID
    	
    	String url = PropertiesUtils.getInstance().getValue("daoceu.serviceUrl");
    	log.info("<====同步收款单，json数据：{}==========>",json);
    	String msg = HttpUtil.sendPost(url+"api/rec_dis_api/recDicSync", json.toJSONString());
    	
    	if(msg ==null){
    		map.put("flag", false);
    		map.put("msg", "同步收付单失败：接口请求异常");
    	}
    	
		JSONObject returnParam = JSONObject.parseObject(msg);
		if(!"0".equals(returnParam.getString("code"))){
			map.put("flag", false);
    		map.put("msg", returnParam.getString("msg"));
		}else{
			map.put("flag", true);
    		map.put("msg", returnParam.get("result"));
		}
		
    	return map;
    } 
    
    
    /**
     * 同步订账单
     * @param order (订单信息)
     */
    public Map<String,Object> syncBookAcountOrder(Order order){
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	//组装数据
    	JSONObject json = new JSONObject();
    	
    	String userId = order.getUserId();
    	User user = userMapper.selectByPrimaryKey(userId);
    	if(user!=null){
    		json.put("orUserIcon", user.getUserIcon());//用户头像
    	}
    	json.put("bookAcountId", order.getBookAcountId());// 订账单ID
    	json.put("orMerchantId", order.getScene());//商户ID
    	json.put("orSourceId", order.getOrderId());//订单ID
    	json.put("orSourceCode", order.getOrderCode());//订单编号
    	json.put("orStatus", order.getOrderStatus());//订单状态
    	json.put("orType", order.getOrderType());//订单类型
    	json.put("orUserId", userId);//用户ID
    	json.put("orUserName", order.getUserNickname()); //用户姓名
    	json.put("orMobile", order.getUserPhone()); //用户手机号
    	json.put("orOrderTime", order.getCreatedTime()); //下单时间
    	
    	String url = PropertiesUtils.getInstance().getValue("daoceu.serviceUrl");
    	log.info("<====同步订账单，json数据：{}==========>",json);
    	String msg = HttpUtil.sendPost(url+"api/orderApi", json.toJSONString());
    	
    	if(msg ==null){
    		map.put("flag", false);
    		map.put("msg", "同步订账单失败：接口请求异常");
    	}
    	
		JSONObject returnParam = JSONObject.parseObject(msg);
		if(!"0".equals(returnParam.getString("code"))){
			map.put("flag", false);
    		map.put("msg", returnParam.getString("msg"));
		}else{
			map.put("flag", true);
			map.put("msg", returnParam.get("result"));
		}
		
    	return map;
    	
    }
    
    /**
     * 更新订账单
     * @param json 
     */
    public Map<String,Object> updateBookAcountOrder(Order order){
    	
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	//组装数据
    	JSONObject json = new JSONObject();
    	json.put("id", order.getBookAcountId());//订账单ID
    	
    	//订单状态
    	if(!StringUtils.isEmpty(order.getOrderStatus())){
    		json.put("orStatus", order.getOrderStatus());
    	}
    	
    	//支付时间
    	if(!StringUtils.isEmpty(order.getPayTime())){
    		json.put("orPayTime", order.getPayTime());
    	}
    	//支付金额
    	if(!StringUtils.isEmpty(String.valueOf(order.getPayPrice()))){
    		json.put("orPayPrice", order.getPayPrice());
    	}
    	//退款时间
    	if(!StringUtils.isEmpty(order.getBackTime())){
    		json.put("orRefundTime", order.getBackTime());
    	}
    	//退款金额
    	if(!StringUtils.isEmpty(String.valueOf(order.getBackPrice()))){
    		json.put("orRefundPrice", order.getBackPrice());
    	}
    	//退单时间
    	if(!StringUtils.isEmpty(order.getBackTime())){
    		json.put("orBackTime", order.getBackTime());
    	}
    	
    	String url = PropertiesUtils.getInstance().getValue("daoceu.serviceUrl");
    	log.info("<====更新订账单，json数据：{}==========>",json);
    	String msg = HttpUtil.sendPost(url+"api/orderApi/update", json.toJSONString());
    	
    	if(msg ==null){
    		map.put("flag", false);
    		map.put("msg", "更新订账单失败：接口请求异常");
    	}
    	
		JSONObject returnParam = JSONObject.parseObject(msg);
		if(!"0".equals(returnParam.getString("code"))){
			map.put("flag", false);
    		map.put("msg", returnParam.getString("msg"));
		}else{
			map.put("flag", true);
    		map.put("msg", returnParam.get("result"));
		}
		
    	return map;
    	
    }

	@Override
	public int getValidOrderList(String userId) {
		return orderMapper.getValidOrderList(userId);
	}

	@Override
	public int selectOrderCountByPIdAndCId(Map<String, Object> map) {
		return orderMapper.selectOrderCountByPIdAndCId(map);
	}

	@Override
	public int getZeroOrderList(Map<String,Object> map) {
		return orderMapper.getZeroOrderList(map);
	}
}
