package com.liketry.interaction.benison.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.model.Order;
import com.liketry.interaction.benison.service.OrderService;

/**
 * 订单controller
 * 
 * @author pengyy
 *
 */
@Controller
@RequestMapping("order")
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    /**
     * 根据userId获取订单列表数据
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping("/listByUserId")
    Map<String, Object> getScenicSpotPage(int page, int rows,String userId) {
        PageInfo<Order> pageInfo = orderService.findOrderListByUserId(page, rows, userId);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        return map;
    }
	
}