package com.liketry.interaction.opt.order.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.util.TextUtils;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liketry.interaction.opt.order.action.IOrderAction;
import com.liketry.interaction.opt.orderdetail.action.IOrderDetailAction;
import com.liketry.interaction.opt.orderdetail.model.OrderDetailBO;
import com.liketry.interaction.opt.stock.action.IStockAction;
import com.liketry.interaction.opt.stock.model.StockBO;
import com.liketry.interaction.opt.stockdetail.action.IStockDetailAction;
import com.taikang.iu.com.ExcelUtil;
import com.taikang.iu.com.MailUtils;
import com.taikang.iu.com.PropertiesUtils;
import com.taikang.iu.com.SmsUtils;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.properties.PropertiesFile;
import com.taikang.udp.framework.core.properties.PropertiesHandler;
import com.taikang.udp.framework.core.properties.PropertiesHandlerFactory;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.model.UserBO;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;

import net.sf.json.JSONObject;

/**
 * OrderController
 */
@Controller("orderController")
@RequestMapping(value = "/order")
public class OrderController extends BaseController {

	@Resource(name = IOrderAction.ACTION_ID)
	private IOrderAction orderAction;
	
	@Resource(name = IOrderDetailAction.ACTION_ID)
	private IOrderDetailAction orderDetailAction;
	
	@Resource(name=IStockAction.ACTION_ID)
	private IStockAction stockAction;
	
	@Resource(name=IStockDetailAction.ACTION_ID)
	private IStockDetailAction stockDetailAction;
	
	protected static PropertiesUtils pro = PropertiesUtils.getInstance();
	
	/**
	 * 打开主查询页面
	 * 
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showOrderIndexPage() {
		return "order/orderIndex";
	}
	
	/**
	 * 打开退单申请页面页面
	 * 
	 * @return 页面地址
	 */
	@RequestMapping("/returnOrderIndex")
	public String showReturnOrderIndexPage() {
		return "order/returnOrderIndex";
	}
	
	/**
	 * 打开退单确认页面
	 * 
	 * @return 页面地址
	 */
	@RequestMapping("/confrimOrderIndex")
	public String showConfrimOrderIndexPage() {
		return "order/confrimOrderIndex";
	}

	/**
	 * 查询信息列表
	 * 
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> getOrderList(HttpServletRequest request, CurrentPage page) {
		Map<String, Object> map = new HashMap<String, Object>();

		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = orderAction.queryForPage(page);

		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());

		return map;
	}

	/**
	 * 打开新增或修改页面
	 * 
	 * @return
	 */
	@RequestMapping("edit")
	public String showOrderEditPage(String rowId, Model model) {

		if (rowId != null && !rowId.equals("")) {
			model.addAttribute("rowId", rowId);
		}

		return "order/orderEdit";
	}

	/**
	 * 打开详细页面
	 * 
	 * @return String
	 */
	@RequestMapping("/view")
	public String showOrderViewPage(String rowId, Model model) {

		if (rowId != null && !rowId.equals("")) {
			model.addAttribute("rowId", rowId);
		}

		return "order/orderView";
	}

	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getOrderById(@RequestParam("rowId") String rowId) {
		Dto param = new BaseDto();
		param.put("orderId", rowId);
		return orderAction.findOne(param);
	}

	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String, String> saveOrderInfo(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();

		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());

		// 修改信息
		Dto param = getParamAsDto(request);
		if (param.get("orderId") != null && !"".equals(param.get("orderId"))) {
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("orderId", param.get("orderId"));
			Dto oldOne = orderAction.findOne(params);

			// 更新数据
			oldOne.put("transactionNo", param.get("transactionNo"));
			oldOne.put("orderStatus", param.get("orderStatus"));
			oldOne.put("userNickname", param.get("userNickname"));
			oldOne.put("userPhone", param.get("userPhone"));
			oldOne.put("blessName", param.get("blessName"));
			oldOne.put("writeName", param.get("writeName"));
			oldOne.put("totalPrice", param.get("totalPrice"));
			// oldOne.put("payPrice", param.get("payPrice"));

			oldOne.put("modified_by", loginId);
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", oldOne.getAsInteger("version") + 1);

			orderAction.updateObject(oldOne);
			map.put(MESSAGE_INFO, "修改成功！");
			map.put(RTN_RESULT, "true");
		} else {
			map.put(MESSAGE_INFO, "登录用户不存在！");
			map.put(RTN_RESULT, "false");
		}

		return map;

	}

	/**
	 * 删除一条或多条记录
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	public Map<String, String> deleteOrder(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		orderAction.deleteObject(param);

		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");

		return map;
	}

//	/**
//	 * 退单
//	 */
//	@RequestMapping(value = "/returnOrder")
//	@ResponseBody
//	public Map<String, String> returnOrder(HttpServletRequest request) {
//		Map<String, String> map = new HashMap<String, String>();
//
//		LoginUser user = UserUtils.getUser();
//		UserBO userBo = user.getUser();
//		Dto param = getParamAsDto(request);
//		// 调订单退单接口
//		logger.info("<======OrderController.returnOrder===start===>");
//		// 按规则进行base64加密传输
//		Dto data = new BaseDto();
//		data.put("orderId", param.getAsString("orderId"));
//		data.put("userId", userBo.getUserId());
//		data.put("userType", 1);
//		data.put("backReason", param.getAsString("backReason"));
//		data.put("userName", userBo.getUserName());
//		data.put("tradeType", param.getAsString("payType"));
//
//		String jsonEncode = CommonUtil.getBase64(data.toJson());
//
//		WebClient client = ServiceUtil.createClient("/order_api/returnOrder?data=" + jsonEncode);
//		client.type("application/json;charset=UTF-8");
//		Response response = client.get();
//
//		// 转换返回值为jsonNode
//		JsonNode node = ServiceUtil.convertJsonNode(response);
//		String code = node.get("code").asText();
//		// 为返回值赋值
//		if (code != null && "success".equals(code)) {
//			map.put(RTN_RESULT, "true");
//			map.put(MESSAGE_INFO, node.get("data").asText());
//		} else {
//			map.put(RTN_RESULT, "false");
//			map.put(MESSAGE_INFO, "退单失败！");
//		}
//
//		return map;
//	}
	
	/**
	 * 退单
	 * 
	 * @param orderId
	 * @param backPrice
	 *            退单金额
	 * @param backReason
	 *            退单原因
	 * @param backRemarks
	 *            退款备注
	 * @return 状态
	 */
	@ResponseBody
	@RequestMapping(value = "/returnOrder")
	@Transactional
	public Map<String, String> returnOrder(HttpServletRequest request) {
		LoginUser user = UserUtils.getUser();
		Map<String, String> map = new HashMap<String, String>();
		map.put(RTN_RESULT, "false");
		Dto param = getParamAsDto(request);
		String orderId = param.getAsString("orderId");
		float backPrice = param.getAsFloat("backPrice");
		String status = param.getAsString("status");
		String backRemarks = param.getAsString("backRemarks");
		Dto order = new BaseDto();
		order.put("orderId", orderId);
		Dto dto = orderAction.findOne(order);
		
		if (dto == null) {
			map.put(MESSAGE_INFO, "此数据不存在！");
		} else {
			String confirmStatus = dto.getAsString("confirmStatus");
			if(TextUtils.isEmpty(confirmStatus) || !"1".equals(confirmStatus)) {
				map.put(MESSAGE_INFO, "订单未确认！");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			String orderStatus = dto.getAsString("orderStatus");
			
			if(TextUtils.isEmpty(orderStatus) || "0".equals(orderStatus) || "1".equals(orderStatus)
					|| "3".equals(orderStatus) || "4".equals(orderStatus)){
				map.put(MESSAGE_INFO, "订单状态不符合退单要求！");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			if("5".equals(orderStatus)){
				map.put(MESSAGE_INFO, "该订单已退单，请勿重复操作！");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			if("6".equals(orderStatus)){
				map.put(MESSAGE_INFO, "该订单已退款，不可以重复退单！");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			String priceObj = dto.getAsString("payPrice");
			if (TextUtils.isEmpty(priceObj)) {
				map.put(MESSAGE_INFO, "此订单未支付");
				return map;
			}
			float price = Float.valueOf(priceObj);
			if (backPrice > price) {
				map.put(MESSAGE_INFO, "退款金额不能大于支付金额！");
				return map;
			}
			if (backPrice > 100000 || String.valueOf(backPrice).split("\\.")[1].length() > 2) {
				map.put(MESSAGE_INFO, "退款金额参数异常！");
				return map;
			}
			
			//退单时修改状态，否则只修改备注
			if(status != null && "5".equals(status)){
				dto.put("backPrice", backPrice);
				dto.put("backRemarks", backRemarks);
				dto.put("orderStatus", 5);
				//如果完成退单，更新库存状态
				Dto orderDto = new BaseDto();
				orderDto.put("order_id", orderId);
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
						stockDetail.put("orderId", orderId);
						stockDetail.put("stockId", stock.getStockId());
						Dto newStockDetail = stockDetailAction.findOne(stockDetail);
						stockDetailAction.deleteObject(newStockDetail);
					});
				}
			}else{
				dto.put("backPrice", backPrice);
				dto.put("backRemarks", backRemarks);
				dto.put("backReason", ""); //决绝后，退单申请的理由为空，可以导出图片
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dto.put("backTime", sdf.format(TKDateTimeUtils.getTodayDateTime()));
			dto.put("backBy", user.getUserName());
			dto.put("modifiedBy", user.getUserId());
			dto.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			orderAction.updateObject(dto);
			map.put(MESSAGE_INFO, "退单成功！");
			map.put(RTN_RESULT, "true");
		}
		return map;
	}

	/**
	 * 打开退单页面
	 * 
	 * @return 页面地址
	 */
	@RequestMapping("returnView")
	public String showReturnViewPage(String rowId, Model model) {

		if (rowId != null && !rowId.equals("")) {
			model.addAttribute("rowId", rowId);
		}
		return "order/returnView";
	}

	/**
	 * 导出订单列表excel
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/download")
	public String download(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// 导出文件名
		String fileName = "excel_" + TKDateTimeUtils.getCurrentDate().toString().replace("-", "") + ".xlsx";

		Dto param = getParamAsDto(request);
		List<Dto> list = orderAction.findAll(param);
		String keys[] = { "created_time", "order_code", "transaction_no", "order_status", "total_price", "pay_time", "back_time", "back_by", "back_reason", "user_nickname", "user_phone",
				"screen_name", "bless_name", "write_name" };// map中的key
		String columnNames[] = { "创建时间", "订单编号", "交易单号", "订单状态", "订单总价(元)", "支付时间", "退单时间", "退单人员", "退单原因", "用户昵称", "联系电话", "屏幕", "祝福对象", "落款人" };// 列名

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		// 制作Workbook
		try {
			ExcelUtil.createWorkBookByDto("订单数据", list, keys, columnNames).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);

		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName).getBytes(), "iso-8859-1"));

		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		ServletOutputStream out = response.getOutputStream();

		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}
		return null;
	}

	/**
	 * 打开申请退单页面
	 * 
	 * @return 页面地址
	 */
	@RequestMapping("applyOrderView")
	public String showApplyOrderPage(@RequestParam String rowId, Model model) {
		if (!TextUtils.isBlank(rowId))
			model.addAttribute("rowId", rowId);
		return "order/applyOrder";
	}

	/**
	 * 申请退单
	 */
	@ResponseBody
	@RequestMapping(value = "/applyOrder")
	public Map<String, String> applyOrder(@RequestParam String orderId, @RequestParam String backReason) {
		
		Map<String, String> map = new HashMap<String, String>();
		LoginUser user = UserUtils.getUser();
		UserBO userBo = user.getUser();
		Dto params = new BaseDto();
		params.put("orderId", orderId);
		Dto dto = orderAction.findOne(params);
		
		if(new Double(0).equals(dto.getAsDouble("payPrice"))){
			map.put(RTN_RESULT, "false");
    		map.put(MESSAGE_INFO, "订单金额为0元，不允许退单！");
    		return map;
		}
		
		Dto param = new BaseDto();
		param.put("order_id", orderId);
		List<Dto> orderDetailList = orderDetailAction.findAll(param);
		
		String confirmStatus = dto.getAsString("confirmStatus");
		if(TextUtils.isEmpty(confirmStatus) || !"1".equals(confirmStatus)) {
			map.put(MESSAGE_INFO, "订单未确认！");
			map.put(RTN_RESULT, "false");
			return map;
		}
				
		String orderStatus = dto.getAsString("orderStatus");
		
		if(TextUtils.isEmpty(orderStatus) || "0".equals(orderStatus) || "1".equals(orderStatus)
				|| "3".equals(orderStatus) || "4".equals(orderStatus)){
			map.put(MESSAGE_INFO, "订单状态不符合退单要求！");
			map.put(RTN_RESULT, "false");
			return map;
		}
		
		if("5".equals(orderStatus)){
			map.put(MESSAGE_INFO, "该订单已退单，请勿重复操作！");
			map.put(RTN_RESULT, "false");
			return map;
		}
		
		if("6".equals(orderStatus)){
			map.put(MESSAGE_INFO, "该订单已退款，不可以重复退单！");
			map.put(RTN_RESULT, "false");
			return map;
		}
			
		if (dto != null) {
			
			if(TextUtils.isEmpty(userBo.getPhoneNum())||TextUtils.isEmpty(userBo.getPhoneNum())){
				map.put(MESSAGE_INFO, "请设置管理员的手机号！");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			dto.put("modifiedBy", user.getUserId());
			dto.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			dto.put("backBy", user.getUserName());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dto.put("backTime", sdf.format(TKDateTimeUtils.getTodayDateTime()));
			dto.put("backReason", backReason);
			orderAction.updateObject(dto);
			
			//短信通知,数据组装
			JSONObject json = new JSONObject();
			json.put("order_code", dto.getAsString("orderCode")); //订单号
			String orderType = dto.getAsString("orderType"); // 订单类型
			if("0".equals(orderType)){
				json.put("order_type", "普通");
			}else if("1".equals(orderType)){
				json.put("order_type", "自制上传");
			}else if("2".equals(orderType)){
				json.put("order_type", "DIY排版");
			}else if("3".equals(orderType)){
				json.put("order_type", "定制设计");
			}
			json.put("screen_name", dto.getAsString("screenName"));	// 屏幕名称
			if(orderDetailList!=null && orderDetailList.size()>0){
				json.put("play_date", orderDetailList.get(0).getAsString("play_date"));	//播放日期
				StringBuffer strs = new StringBuffer();
				for(int i=0;i<orderDetailList.size();i++){
					Dto orderDetail = orderDetailList.get(i);
					if(i==0){
						strs.append(orderDetail.getAsString("time_start"));
						strs.append("~");
						strs.append(orderDetail.getAsString("time_end"));
					}
					if(i>0){
						strs.append(",");
						strs.append(orderDetail.getAsString("time_start"));
						strs.append("~");
						strs.append(orderDetail.getAsString("time_end"));
					}
				}
				json.put("play_time", strs.toString());	// 播放时间
			}
			json.put("pay_price", dto.getAsString("payPrice"));	// 支付金额
			json.put("nick_name", dto.getAsString("userNickname"));	// 用户名称
			json.put("phone_number", dto.getAsString("userPhone"));	// 联系方式
			json.put("reason", dto.getAsString("backReason"));	// 退单原因
			json.put("back_name", dto.getAsString("backTime"));	// 退单时间
			
			try {
				SmsUtils.getInstance().sendSms(userBo.getPhoneNum(), pro.getValue("return_order_signName"), 
						pro.getValue("return_order_templateCode"), json.toString());
			} catch (Exception e) {
				logger.error("<=====短信发送失败，异常信息：{}===========>",e);
			}
			
			map.put(MESSAGE_INFO, "申请成功！");
			map.put(RTN_RESULT, "true");
		} 
		
		return map;
	}
	
	/**
	 * 邮件通知 
	 * @param userBo 管理员信息
	 * @param dto 订单信息
	 * @param orderDetailList 订单详情列表
	 * @deprecated 由于公司邮件服务器不能接收465端口邮件，阿里服务器不能发送25端口邮件，所以此方法暂时取缔，改发短信通知
	 */
	private void sendMail(UserBO userBo,Dto dto,List<Dto> orderDetailList){
		
		//退单成功后发送邮件
		MailUtils cn = MailUtils.getInstance();
		//设置发件人，用户名，密码，主题参数
		cn.setAddress(userBo.getUserEmail(), userBo.getUserEmail(), userBo.getEmailPwd(),"退单申请【"+dto.getAsString("orderCode")+"】，请处理");
		//按格式组装邮件内容
		StringBuffer strs = new StringBuffer();
		strs.append("您好！<br/>有退单申请，请您审批。具体信息如下：<br/>===========================<br/>");
		strs.append("单号：");
		strs.append(dto.getAsString("orderCode"));
		strs.append("<br/>");
		strs.append("订单类型：");
		String orderType = dto.getAsString("orderType");
		if("0".equals(orderType)){
			strs.append("普通");
		}else if("1".equals(orderType)){
			strs.append("自制上传");
		}else if("2".equals(orderType)){
			strs.append("DIY排版");
		}else if("3".equals(orderType)){
			strs.append("定制设计");
		}
		
		strs.append("<br/>");
		strs.append("播放大屏：");
		strs.append(dto.getAsString("screenName"));
		strs.append("<br/>");
		
		if(orderDetailList!=null && orderDetailList.size()>0){
			strs.append("播放日期：");
			strs.append(orderDetailList.get(0).getAsString("play_date"));
			strs.append("<br/>");
			for(int i=0;i<orderDetailList.size();i++){
				Dto orderDetail = orderDetailList.get(i);
				if(i==0){
					strs.append("播放时间：");
					strs.append(orderDetail.getAsString("time_start"));
					strs.append("~");
					strs.append(orderDetail.getAsString("time_end"));
				}
				if(i>0){
					strs.append(",");
					strs.append(orderDetail.getAsString("time_start"));
					strs.append("~");
					strs.append(orderDetail.getAsString("time_end"));
				}
			}
			strs.append("<br/>");
		}
		
		strs.append("支付金额：");
		strs.append(dto.getAsString("payPrice"));
		strs.append("<br/>");
		strs.append("用户：");
		strs.append(dto.getAsString("userNickname"));
		strs.append("<br/>");
		strs.append("联系方式：");
		strs.append(dto.getAsString("userPhone"));
		strs.append("<br/>===========================<br/>");
		strs.append("退单原因：");
		strs.append(dto.getAsString("backReason"));
		strs.append("<br/>");
		strs.append("退单时间：");
		strs.append(dto.getAsString("backTime"));
		strs.append("<br/>===========================<br/>");
		cn.send(strs.toString());
		
	}
	
}
