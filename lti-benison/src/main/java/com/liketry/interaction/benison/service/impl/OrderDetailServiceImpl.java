/**
 * @author pengyy
 * created at 2017/5/23 18:15
 */
package com.liketry.interaction.benison.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.dao.BenisonMapper;
import com.liketry.interaction.benison.dao.OrderDetailMapper;
import com.liketry.interaction.benison.dao.OrderMapper;
import com.liketry.interaction.benison.model.Benison;
import com.liketry.interaction.benison.model.Order;
import com.liketry.interaction.benison.model.OrderDetail;
import com.liketry.interaction.benison.service.OrderDetailService;
import com.liketry.interaction.benison.util.StringUtils;
import com.liketry.interaction.benison.util.UserUtils;

/**
 * 订单详细service
 * 
 * @author pengyy
 *
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {
	
    @Autowired
    OrderDetailMapper orderDetailMapper;
    
    @Autowired
    OrderMapper orderMapper;
    
    private static final Logger log = LoggerFactory.getLogger(OrderDetailServiceImpl.class);
    
    
    /**
     * 查看订单详细
     */
	@Override
	public OrderDetail findOneOrderDetail(String orderDetailId) {
		
		log.info("<=====OrderDetailServiceImpl.findOneOrderDetail====start=======>");
		
		OrderDetail orderDetail = orderDetailMapper.selectByPrimaryKey(orderDetailId);
		
		String orderId = orderDetail.getOrderId();
		
		if(!StringUtils.isEmpty(orderId)){
			
			Order order = orderMapper.selectByPrimaryKey(orderId);
			
			if(order!=null){
				orderDetail.setBenisonContent(order.getBenisonContent());
				orderDetail.setBkimgUrl(order.getBkimgUrl());
				orderDetail.setBlessName(order.getBlessName());
				orderDetail.setWriteName(order.getWriteName());
			}
		}
		
		return orderDetail;
	}
    
    

}
