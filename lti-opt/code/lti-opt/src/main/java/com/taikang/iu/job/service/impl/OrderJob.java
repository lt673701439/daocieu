package com.taikang.iu.job.service.impl;




import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.liketry.interaction.opt.order.action.IOrderAction;
import com.liketry.interaction.opt.stock.action.IStockAction;
import com.liketry.interaction.opt.stock.model.StockBO;
import com.liketry.interaction.opt.stockdetail.action.IStockDetailAction;
import com.taikang.iu.com.PropertiesUtils;
import com.taikang.iu.com.ServiceUtil;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.exception.app.TKDaoException;
import com.taikang.udp.framework.core.web.BaseController;

import net.sf.json.JSONObject;


@Service("orderJob")
public class OrderJob extends BaseController{
	
	@Resource(name=IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
	
   @Resource(name=IStockAction.ACTION_ID)
	private IStockAction stockAction;
   
   @Resource(name=IStockDetailAction.ACTION_ID)
	private IStockDetailAction stockDetailAction;
	
	/**
	 * 每分钟刷新一次，查看订单，如果10分钟未支付，自动取消
	 */
	@SuppressWarnings("unchecked")
	public void freshOrderStatus() {
		
		logger.debug("<======OrderJob--freshOrderStatus======>");
		Dto param = new BaseDto();
		param.put("orderStatus", "1");//待支付
		List<Dto> orderList = orderAction.findAll(param);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if(orderList!=null && orderList.size()>0){
			//判断，如果当前时间大于订单创建时间10分钟，则更新状态为取消
			for(Dto order:orderList){
				try {
					Date orderDate = dateFormat.parse(order.getAsString("created_time"));
					Date currentDate = TKDateTimeUtils.getTodayDateTime();  
					if(currentDate.getTime()-orderDate.getTime()>1000 * 60 * 10){
							Dto newOrder = new BaseDto();
							newOrder.put("orderId", order.get("order_id"));
							newOrder.put("orderStatus", 4);//已取消
							orderAction.updateObject(newOrder);
							//恢复库存
							Dto orderDto = new BaseDto();
							orderDto.put("order_id", order.get("order_id"));
							List<StockBO> stockList = stockAction.findAllByOrderId(orderDto);
							if(stockList!=null && stockList.size()>0){
								stockList.forEach(stock->{
									//修改库存销量
									Dto stockDto = new BaseDto();
									stockDto.put("stockId", stock.getStockId());
									stockDto.put("sales", stock.getSales()-1);
									stockDto.put("stock", stock.getStock()+1);
									stockDto.put("stockStatus", 1);
									stockDto.put("version", stock.getVersion()+1);
									stockAction.updateObject(stockDto);
									//删除对应的库存明细
									Dto stockDetail = new BaseDto();
									stockDetail.put("orderId", order.get("order_id"));
									stockDetail.put("stockId", stock.getStockId());
									Dto newStockDetail = stockDetailAction.findOne(stockDetail);
									stockDetailAction.deleteObject(newStockDetail);
								});
							}
							
							//取消订单时，调到此一游接口，推送消息
							JSONObject json = new JSONObject();
							json.put("orSourceId", order.get("order_id"));
							json.put("orType", order.get("order_type"));
							json.put("orSourceCode", order.get("order_code"));
							String msg = ServiceUtil.sendPost(PropertiesUtils.getInstance().getValue("daoceu.serviceUrl"+"api/orderApi/back"),json.toString());
							
							if(msg == null){
								logger.info("<==定时器，取消订单时，发送消息失败,订单ID:{},订单code:{},订单类型:{}=========>",
										order.get("order_id"),order.get("order_code"),order.get("order_type"));
					    	}
							
					}
				}catch(Exception e){
					logger.error(" update failed ,Exception= "+e.getMessage());
					throw new TKDaoException("","OrderJob","freshOrderStatus"," 更新订单状态时发生错误! ",e);
				}
			}
		}
		
	}
	
	/**
	 * 10分钟刷新一次，查看订单，如果已播放，自动更改状态
	 */
	@SuppressWarnings("unchecked")
	public void freshOrderStatus2BOfang() {
		
		logger.debug("<======OrderJob--freshOrderStatus2BOfang======>");
		Dto param = new BaseDto();
		//查询出所有早于当天日期且状态为已支付的订单
		List<Dto> orderList = orderAction.findAllOrderByStatus(param);
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		
		if(orderList!=null && orderList.size()>0){
			
			Date currentDate = TKDateTimeUtils.getTodayDate(); 
			String orderId = null;
			String timeStart = null;
			//判断当前时间和商品播放时间,如果
			for(Dto order:orderList){
				try {
					
					//判断播放日期，如果播放日期早于今天，则修改状态已播放
					if(order.getAsDate("play_date")!=null && order.getAsDate("play_date").before(dateFormat2.parse(dateFormat2.format(currentDate)))){
						Dto newOrder = new BaseDto();
						newOrder.put("orderId", order.getAsString("order_id"));
						newOrder.put("orderStatus", 3);//已播放
						orderAction.updateObject(newOrder);
					}else{
						
						if(orderId == null || !order.getAsString("order_id").equals(orderId)){
							orderId = order.getAsString("order_id");
							timeStart = order.getAsString("time_start");
							//判断商品开始时间，如果大于当前时间，则修改订单状态为已播放
							if(dateFormat.parse(timeStart).before(dateFormat.parse(dateFormat.format(currentDate)))){
								Dto newOrder = new BaseDto();
								newOrder.put("orderId", orderId);
								newOrder.put("orderStatus", 3);//已播放
								orderAction.updateObject(newOrder);
							}
						}
					}
				
				}catch(Exception e){
					logger.error(" update failed ,Exception= "+e.getMessage());
					throw new TKDaoException("","OrderJob","freshOrderStatus2BOfang"," 更新订单状态时发生错误! ",e);
				}
			}
		}
		
	}
	
	
}
