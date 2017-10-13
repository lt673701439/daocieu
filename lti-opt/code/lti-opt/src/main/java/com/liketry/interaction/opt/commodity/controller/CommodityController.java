package com.liketry.interaction.opt.commodity.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liketry.interaction.opt.commodity.action.ICommodityAction;
import com.liketry.interaction.opt.orderdetail.action.IOrderDetailAction;
import com.liketry.interaction.opt.screen.action.IScreenAction;
import com.taikang.iu.com.CommonUtil;
import com.taikang.iu.com.PicturePackZipTools;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.sequence.BusinessSeqGenerator;
import com.taikang.udp.sys.util.vo.LoginUser;


/**
  * CommodityController
  */
@Controller("commodityController")
@RequestMapping(value="/commodity")
public class CommodityController  extends BaseController  {
		
	@Resource(name=ICommodityAction.ACTION_ID)
	private ICommodityAction commodityAction;
		
	@Resource(name=IScreenAction.ACTION_ID)
	private IScreenAction screenAction;
	
	@Resource(name=IOrderDetailAction.ACTION_ID)
	private IOrderDetailAction orderDetailAction;
	
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showCommodityIndexPage() {
		return "commodity/commodityIndex"; 
	}
	
	/**
	 * 打开图片导出页面
	 * @return 页面地址
	 */
	@RequestMapping("/exportImage")
	public String showExportImage() {
		return "stock/exportImage"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getCommodityList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = commodityAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("/add")
	public String showCommodityAddPage() {
		return "commodity/commodityAdd"; 
	}

	/**
	 * 打开修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showCommodityEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
			model.addAttribute("urlList", getImgUrlList(rowId));
		}
		
		return "commodity/commodityEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 * String
	 */
	@RequestMapping("/view")
	public String showCommodityViewPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId);
			model.addAttribute("urlList", getImgUrlList(rowId));
		}
		
		return "commodity/commodityView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getCommodityById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("commodityId", rowId);
		return commodityAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveCommodityInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		
		if(param.get("commodityId") == null || "".equals(param.get("commodityId"))){
			
			// 校验
			if(!checkDate(param.get("startDate").toString(), param.get("endDate").toString(), map) // 日期校验
					|| !checkTime(param.get("startTime").toString(), param.get("endTime").toString(), map) // 时间校验	
					|| !checkRepeat(param, map) // 重叠时间校验 param里要包含：屏幕ID、开始结束日期和时间
					|| !checkNumber(param, map)){ // 计划售卖数量校验
				return map;
			}
			
			// 新增操作
			// 取屏幕信息
			Dto screenParam = new BaseDto();
			screenParam.put("screenId", param.get("screenId"));
			Dto screen =  screenAction.findOne(screenParam);
			
			// 校验屏幕信息
			if(screen == null && "".equals(screen)){
				map.put(MESSAGE_INFO, "屏幕信息不存在！");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			param.put("commodityId", UUID.randomUUID().toString().replace("-", ""));
			// CMD_SCR001_001
			param.put("commodityCode", "CMD_"+screen.get("screenCode")+"_"+BusinessSeqGenerator.getInstance("commodity_code").nextId()); 
			param.put("commodityStatus", "1");
			
			param.put("createdBy", loginId);
			param.put("createdTime", TKDateTimeUtils.getTodayTimeStamp());
			param.put("delflag", "1"); // “1”代表“有效”
			param.put("version", 1);
			
			// 添加商品
			commodityAction.insertObjectAndUpload(param,request);
			map.put(MESSAGE_INFO, "添加成功！");
			map.put(RTN_RESULT, "true");
		}else {
			
			// 更新操作
			// 查询旧信息
			Dto params = new BaseDto();
			params.put("commodityId", param.get("commodityId"));
			Dto oldOne = commodityAction.findOne(params);
			
			oldOne.put("commodityName", param.get("commodityName"));
			oldOne.put("commodityDescription", param.get("commodityDescription"));
			oldOne.put("commodityStatus", param.get("commodityStatus"));
			oldOne.put("commodityPrice", param.get("commodityPrice"));
			oldOne.put("commodityImg", param.get("commodityImg"));
			
//			oldOne.put("screenID", param.get("screenID"));
//			oldOne.put("startDate", param.get("startDate"));
//			oldOne.put("endDate", param.get("endDate"));
//			oldOne.put("timeFrame", param.get("timeFrame"));
//			oldOne.put("startTime", param.get("startTime"));
//			oldOne.put("endTime", param.get("endTime"));
//			oldOne.put("shelfTime", param.get("shelfTime"));
//			oldOne.put("planNumber", param.get("planNumber"));
//			oldOne.put("singleTime", param.get("singleTime"));
//			oldOne.put("playTimes", param.get("playTimes"));
			
			oldOne.put("modified_by", loginId);
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", oldOne.getAsInteger("version")+1);
			
			commodityAction.updateObjectAndUpload(oldOne,request);
			map.put(MESSAGE_INFO, "修改成功！");
			map.put(RTN_RESULT, "true");
		}
		
		return map;
		
	}
	
	/**
	 * 日期校验
	 */
	private boolean checkDate(String startDateStr, String endDateStr, Map<String,String> map){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			Date startDate = dateFormat.parse(startDateStr);
			Date endDate = dateFormat.parse(endDateStr);
			// 如果开始日期大于结束日期，则返回
			if(startDate.compareTo(endDate)>0){
				map.put(MESSAGE_INFO, "开始日期大于了结束日期，请修改！");
				map.put(RTN_RESULT, "false");
				return false;
			}
		} catch (ParseException e) {
			map.put(MESSAGE_INFO, "开始日期与结束日期填写有误，请重新填写！");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 时间校验
	 */
	private boolean checkTime(String startTimeStr, String endTimeStr, Map<String,String> map){
		
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		
		try {
			Date startTime = timeFormat.parse(startTimeStr);
			Date endTime = timeFormat.parse(endTimeStr);
			// 如果开始时间大于结束时间，则返回
			if(startTime.compareTo(endTime)>=0){
				map.put(MESSAGE_INFO, "开始时间大于等于了结束时间，请修改！");
				map.put(RTN_RESULT, "false");
				return false;
			}
		} catch (ParseException e) {
			map.put(MESSAGE_INFO, "开始时间与结束时间填写有误，请重新填写！");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 重叠时间校验 map里要包含：屏幕ID、开始结束日期和时间
	 */
	private boolean checkRepeat(Dto param, Map<String,String> map){
		
		// 校验数据是否有问题
		if(param.get("screenId") == null || param.get("startDate") == null || param.get("endDate") == null
				|| param.get("startTime") == null || param.get("endTime") == null){
			
			map.put(MESSAGE_INFO, "屏幕、日期、时间填写有误，请重新填写！");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		// 查询重叠数据
		List<Dto> list = commodityAction.findRepeatByDateAndTime(param);
		
		if(list != null && list.size() > 0){
			Dto repeat = list.get(0);
			String commodityCode = repeat.getAsString("commodity_code");
			String startDate = repeat.getAsString("start_date");
			String endDate = repeat.getAsString("end_date");
			String startTime = repeat.getAsString("start_time");
			String endTime = repeat.getAsString("end_time");
			
			map.put(MESSAGE_INFO, "屏幕、日期、时间与其它商品冲突，请重新填写！<br>"
					+ "【冲突商品编号："+commodityCode+"】<br>"
					+ "【开始结束日期："+startDate+" ~ "+endDate+"】<br>"
					+ "【开始结束时间："+startTime+" ~ "+endTime+"】");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 计划售卖数量校验
	 */
	private boolean checkNumber(Dto param, Map<String,String> map){
		
		// 校验数据是否有问题(开始结束时间已经在前面校验过了，这里就不再重复校验了。)
		if(param.getAsInteger("planNumber") == null || param.getAsInteger("singleTime") == null || param.getAsInteger("playTimes") == null){
			
			map.put(MESSAGE_INFO, "计划售卖数量、单次播放时间、播放次数填写有误，请重新填写！");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		// 准备数据
		String startTime = param.getAsString("startTime");
		String endTime = param.getAsString("endTime");
		
		int planNumber = param.getAsInteger("planNumber");
		int singleTime = param.getAsInteger("singleTime");
		int playTimes = param.getAsInteger("playTimes");
		
		// 计算最大售卖数量
		// 公式：(结束时间-开始时间)/(单次播放时间*播放次数) = 最大售卖数量
		int totalSecond = getSecondByhhmmss(endTime) - getSecondByhhmmss(startTime);
		int maxPlanNum = totalSecond/(singleTime*playTimes);
		
		// 校验计划售卖数量是否大于最大数量
		if(planNumber > maxPlanNum){
			map.put(MESSAGE_INFO, "计划售卖数量已超最大值"+maxPlanNum+"，请重新填写！");
			map.put(RTN_RESULT, "false");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 将hh:mm:ss格式的字符串转换成秒
	 */
	private int getSecondByhhmmss(String hhmmss){
		
		int totalSecond = 0;
		
		if(hhmmss.split(":") == null){
			logger.error("getSecondByhhmmss()的参数格式不正确，正确的格式为：hh:mm:ss，参数为："+hhmmss);
			return totalSecond;
		}
		
		// 分别定义时分秒
		int hh = 0;
		int mm = 0;
		int ss = 0;
		
		if(hhmmss.split(":").length > 0){
			hh = Integer.parseInt(hhmmss.split(":")[0])*3600;
		}
		
		if(hhmmss.split(":").length > 1){
			mm = Integer.parseInt(hhmmss.split(":")[1])*60;
		}
		
		if(hhmmss.split(":").length > 2){
			ss = Integer.parseInt(hhmmss.split(":")[2]);
		}
		
		totalSecond = hh + mm + ss;
		
		return totalSecond;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteCommodity(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();

		
		// 查询订单详细
		Dto orderDetail = new BaseDto();
		orderDetail.put("commodity_id", rowId);
		List<Dto> orderDetailList = orderDetailAction.findAll(orderDetail);

		// 如果订单详细中已经存在此商品信息，则不能删除此商品
		if(orderDetailList != null && orderDetailList.size() > 0){
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "此商品已下订单，不能删除！");
			return map;
		}
		
		// 执行删除
		Dto param = new BaseDto();
		param.put("commodityId", rowId);
		commodityAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "删除成功！");
		
		return map;
	}
	
	/**
	*	制作点播图片
	 * @throws IOException 
	*/
	@RequestMapping(value="/exportImg")
	@ResponseBody
	public Map<String, String> exportImg(@RequestParam("rowId") String rowId,@RequestParam("playDate") String playDate,
			@RequestParam("commodityStatus") String	commodityStatus,HttpServletResponse response) {
//		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("commodityId", rowId);
		param.put("playDate", playDate);
		param.put("commodity_status", commodityStatus);//区分商品表里的商品状态
		return commodityAction.exportImg(param);
	}
	
	/**
	*	导出点播图片压缩包
	 * @throws IOException 
	*/
	@RequestMapping(value="/compressZip")
	@ResponseBody
	public Map<String, String> compressZip(String path,String exportTime,String timeRange,
			String screenName,HttpServletResponse response) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		//将所有图片打成压缩包输出到客户端
		try {
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
	        response.setContentType("application/octet-stream");
	        //压缩包名称：大屏名_日期_时间段_图片数量,如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
	        File file = new File(path);
	        File [] files = file.listFiles();
	        String fileName = screenName+"_"+TKDateTimeUtils.formatToFormat(exportTime,"yyyy年MM月dd日")
	        +"_"+timeRange+"_"+files.length+"单";
	        response.setHeader("Content-Disposition", "attachment;filename=" +
	        		URLEncoder.encode(fileName+".zip", "UTF-8"));
	        PicturePackZipTools.compressZip(path, toClient,"UTF-8", false);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取图片路径
	 * @param imgId
	 * @return
	 */
	public List<Dto> getImgUrlList(String commodityId){
		Dto param = new BaseDto();
		param.put("commodityId", commodityId);
		Dto list = commodityAction.findOne(param);
		//组装数据
		List<Dto> urlList = new ArrayList<Dto>();
		String pictureUrl = list.getAsString("commodityImg");
		if (pictureUrl != null && !"".equals(pictureUrl)) {
			String[] split = pictureUrl.split(",");
			for (int i = 0; i < split.length; i++) {
				Dto map = new BaseDto();
				String url = CommonUtil.RELATION_UPLOAD_FILEPATH + split[i];
				map.put("url", url);
				urlList.add(map);
			}
		}
		return urlList;
	}
}

