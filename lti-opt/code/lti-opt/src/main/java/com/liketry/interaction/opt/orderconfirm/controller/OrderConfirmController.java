package com.liketry.interaction.opt.orderconfirm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.liketry.interaction.opt.order.action.IOrderAction;
import com.liketry.interaction.opt.stock.action.IStockAction;
import com.liketry.interaction.opt.stock.model.StockBO;
import com.liketry.interaction.opt.stockdetail.action.IStockDetailAction;
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
  * OrderController
  */
@Controller("orderConfirmController")
@RequestMapping(value="/orderConfirm")
public class OrderConfirmController  extends BaseController  {
		
	@Resource(name=IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
	
	@Resource(name=IStockAction.ACTION_ID)
	private IStockAction stockAction;
	
	@Resource(name=IStockDetailAction.ACTION_ID)
	private IStockDetailAction stockDetailAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showOrderConfirmIndexPage() {
		return "orderConfirm/orderConfirmIndex"; 
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
	 * 打开详细页面
	 * @return
	 * String
	 */
	@RequestMapping("/view")
	public String showOrderViewPage(String rowId,String orderType,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId);
			model.addAttribute("orderType",orderType);
		}
		
		return "orderConfirm/orderConfirmView"; 
	}
	
	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("/handle")
	public String showOrderEditPage(String rowId,String orderType,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId);
			model.addAttribute("orderType",orderType);
		}
		
		return "orderConfirm/orderConfirmHandle"; 
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
	 * 确认-通过
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/pass")
	@ResponseBody
	private Map<String,String> pass(HttpServletRequest request, HttpServletResponse response)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		UserBO userBo = user.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);

		if(param.get("orderId") == null || "".equals(param.get("orderId"))){
			
			map.put(MESSAGE_INFO, "缺少订单主键值，请重新登录！");
			map.put(RTN_RESULT, "false");
			return map;
		}
		
		// 更新订单数据
		Dto params = new BaseDto();
		params.put("orderId", param.get("orderId"));
		Dto oldOne = orderAction.findOne(params);
		
		// 更新数据
		oldOne.put("confirmStatus", "1");
		oldOne.put("confirmTime", TKDateTimeUtils.formatDate(TKDateTimeUtils.getTodayDateTime(), "yyyy-MM-dd HH:mm:ss"));
		oldOne.put("confirmId", userBo.getUserId());
		oldOne.put("confirmBy", userBo.getUserName());

		// 当订单类型为‘定制设计’时，需要验证是否有图片文件
		if("3".equals(param.get("orderType"))){
			String date = TKDateTimeUtils.formatDate(TKDateTimeUtils.getTodayDateTime(), "yyyyMMddHHmmss");//文件夹的名称
			try {
				String realUrl = UploadFile.uploadFile(request,CommonUtil.uploadFilePath(),"order/"+date,param.getAsString("orderCode"));
				oldOne.put("resultUrl", realUrl);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		oldOne.put("modified_by", loginId);
		oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
		oldOne.put("version", oldOne.getAsInteger("version")+1);
		
		orderAction.updateObject(oldOne);
		
//		response.reset();
//		response.setContentType("text/plain;charset=utf-8");
		
		map.put(MESSAGE_INFO, "通过成功！");
		map.put(RTN_RESULT, "true");
		
		return map;
		
	}
	
	/**
	 * 确认-拒绝
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/reject")
	@ResponseBody
	@Transactional
	private Map<String,String> reject(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		UserBO userBo = user.getUser();
		String code = null;
		
		Dto param = getParamAsDto(request);

		//调订单退单接口
        logger.info("<======OrderController.returnOrder===start===>");

       
        Dto params = new BaseDto();
		params.put("orderId", param.get("orderId"));
		Dto oldOne = orderAction.findOne(params);
		
		if(new Double(0).equals(oldOne.getAsDouble("payPrice"))){
			// 订单的支付价格为0时，直接拒绝并修改订单状态
			oldOne.put("orderStatus", 6);
			oldOne.put("backPrice", new Double(0));
			oldOne.put("backReason", param.getAsString("confirmReason"));
			oldOne.put("backId", userBo.getUserId());
			oldOne.put("backBy", userBo.getUserName());
			oldOne.put("backTime", TKDateTimeUtils.formatDate(TKDateTimeUtils.getTodayDateTime(), "yyyy-MM-dd HH:mm:ss"));
			oldOne.put("modifiedBy", userBo.getUserId());
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", oldOne.getAsInteger("version")+1);
			code = "success";
		}else{
			//按规则进行base64加密传输
	        Dto data = new BaseDto();
	        data.put("orderId",param.getAsString("orderId"));
	        data.put("userId",userBo.getUserId());
	        data.put("userType",1);
	        data.put("backReason",param.getAsString("confirmReason"));
	        data.put("userName",userBo.getUserName());
	        data.put("tradeType",param.getAsString("payType"));
	        
	        String jsonEncode = CommonUtil.getBase64(data.toJson());
	        
			WebClient client = ServiceUtil.createClient("/order_api/returnOrder?data="+jsonEncode);
	        client.type("application/json;charset=UTF-8");
	        Response response = client.get();
	        
	        // 转换返回值为jsonNode
	        JsonNode node = ServiceUtil.convertJsonNode(response);
	        code = node.get("code").asText();
		}
        
        // 为返回值赋值
        if(code!=null&&"success".equals(code)){
        	
			//同时，更新库存状态
			Dto orderDto = new BaseDto();
			orderDto.put("order_id", oldOne.getAsString("orderId"));
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
					stockDetail.put("orderId", oldOne.getAsString("orderId"));
					stockDetail.put("stockId", stock.getStockId());
					Dto newStockDetail = stockDetailAction.findOne(stockDetail);
					stockDetailAction.deleteObject(newStockDetail);
				});
			}
			
    		// 更新订单数据
    		oldOne.put("confirmStatus", "2");
    		oldOne.put("confirmTime", TKDateTimeUtils.formatDate(TKDateTimeUtils.getTodayDateTime(), "yyyy-MM-dd HH:mm:ss"));
    		oldOne.put("confirmId", userBo.getUserId());
    		oldOne.put("confirmBy", userBo.getUserName());
    		oldOne.put("confirmReason", param.get("confirmReason"));
    		orderAction.updateObject(oldOne);
    		
        	map.put(RTN_RESULT, "true");
    		map.put(MESSAGE_INFO, "拒绝成功！");
        }else{
        	map.put(RTN_RESULT, "false");
    		map.put(MESSAGE_INFO, "拒绝失败！退单时发生异常。");
        }
		
		return map;
		
	}
	
	/**
	 * 下载背景图片
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/downloadImg")
	public String download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
	        Dto param = getParamAsDto(request);
	        // 导出文件名
		  	String fileUrl = param.getAsString("bkimgUrl");
		  	String fileExt = fileUrl.substring(fileUrl.lastIndexOf(".") + 1).toLowerCase();
		  	String fileName = "img_"+param.getAsString("orderCode") + "." + fileExt;
		  	
		  	// 文件服务器路径
		  	String filePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort() +
		  			CommonUtil.RELATION_UPLOAD_FILEPATH + param.getAsString("bkimgUrl");
	        
			// 根据文件扩展名定义MIME类型
		  	String mimeType = "image/jpeg";
		  	if("jpg".equals(fileExt) || "jpeg".equals(fileExt)){
		  		mimeType = "image/jpeg";
		  	}else if("gif".equals(fileExt)){
		  		mimeType = "image/gif";
		  	}else if("png".equals(fileExt)){
		  		mimeType = "image/png";
		  	}else if("bmp".equals(fileExt)){
		  		mimeType = "application/x-bmp";
		  	}else{
		  		logger.error("订单上传的背景图格式有误，正确格式包括：jpg、jpeg、gif、png、bmp");
		  	}
		  	
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType(mimeType+";charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName).getBytes(), "iso-8859-1"));

	        InputStream inputStream = null;
	        OutputStream outputStream = null;

		    try{    
		        URL url = new URL(filePath);
		        URLConnection conn = url.openConnection();
		        outputStream = response.getOutputStream();
		        inputStream = conn.getInputStream();
		        IOUtils.copy(inputStream, outputStream);
		    } catch (IOException e) {
		        System.err.println(e);
		    }finally { 
		        IOUtils.closeQuietly(inputStream); 
		        IOUtils.closeQuietly(outputStream); 
		    } 
		    
		    return null;
	}
}

