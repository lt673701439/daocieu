package com.liketry.interaction.opt.orderRefund.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.liketry.interaction.opt.order.action.IOrderAction;
import com.taikang.iu.com.CommonUtil;
import com.taikang.iu.com.ServiceUtil;
import com.taikang.iu.com.UploadFile;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.model.UserBO;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;


/**
  * orderRefundController
  */
@Controller("orderRefundController")
@RequestMapping(value="/orderRefund")
public class OrderRefundController  extends BaseController  {
		
	@Resource(name=IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showOrderConfirmIndexPage() {
		return "orderRefund/orderRefundIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getOrderList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = orderAction.queryForPage(page);
		 
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("/refundView")
	public String showOrderEditPage(String rowId,String orderType,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId);
			model.addAttribute("orderType",orderType);
		}
		
		return "orderRefund/refundView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getOrderById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("orderId", rowId);
		return orderAction.findOne(param);
	}
	
	/**
	 * 退款
	 */
	@RequestMapping(value="/refund")
	@ResponseBody
	public Map<String, String> refund(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		
		LoginUser user = UserUtils.getUser();
		UserBO userBo = user.getUser();
		Dto param = getParamAsDto(request);
		
		//0元订单不能退款
		if(new Double(0).equals(param.getAsDouble("payPrice"))){
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "订单金额为0元，不能退款！");
			return map;
		}
		
		//调订单退单接口
        logger.info("<======orderRefundController.refund===start===>");
        //按规则进行base64加密传输
        Dto data = new BaseDto();
        data.put("orderId",param.getAsString("orderId"));
        data.put("orderCode",param.getAsString("orderCode"));
        data.put("userId",userBo.getUserId());
        data.put("payPrice",param.getAsString("payPrice"));
        data.put("backPrice",param.getAsString("backPrice"));
        data.put("tradeType",param.getAsString("payType"));
        
        String jsonEncode = CommonUtil.getBase64(data.toJson());
        		
		WebClient client = ServiceUtil.createClient("/order_api/orderRefund?data="+jsonEncode);
        client.type("application/json;charset=UTF-8");
        Response response = client.get();
        
        // 转换返回值为jsonNode
        JsonNode node = ServiceUtil.convertJsonNode(response);
        String code = node.get("code").asText();
        // 为返回值赋值
        if(code!=null&&"success".equals(code)){
        	map.put(RTN_RESULT, "true");
        }else{
        	map.put(RTN_RESULT, "false");
        }
        map.put(MESSAGE_INFO, node.get("data").asText());
        
		return map;
	}
	
}

