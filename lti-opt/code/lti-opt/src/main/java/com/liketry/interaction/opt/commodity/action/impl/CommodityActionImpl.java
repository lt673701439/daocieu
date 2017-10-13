package com.liketry.interaction.opt.commodity.action.impl;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.liketry.interaction.opt.benisontemplate.model.BenisonTemplateBO;
import com.liketry.interaction.opt.benisontemplate.service.IBenisonTemplateService;
import com.liketry.interaction.opt.commodity.action.ICommodityAction;
import com.liketry.interaction.opt.commodity.model.CommodityBO;
import com.liketry.interaction.opt.commodity.service.ICommodityService;
import com.liketry.interaction.opt.order.model.OrderBO;
import com.liketry.interaction.opt.order.service.IOrderService;
import com.liketry.interaction.opt.orderdetail.model.OrderDetailBO;
import com.liketry.interaction.opt.orderdetail.service.IOrderDetailService;
import com.liketry.interaction.opt.stock.model.StockBO;
import com.liketry.interaction.opt.stock.service.IStockService;
import com.sun.image.codec.jpeg.ImageFormatException;
import com.taikang.iu.com.CommonUtil;
import com.taikang.iu.com.FontText;
import com.taikang.iu.com.UploadFile;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.action.impl.BaseActionImpl;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.sys.model.UserBO;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;

/**
  * CommodityAction
  */
  @Service(ICommodityAction.ACTION_ID)
public class CommodityActionImpl extends BaseActionImpl 
  implements ICommodityAction {

    /**
      * 注入service
      */
    @Resource(name=ICommodityService.SERVICE_ID)
	private ICommodityService<CommodityBO> commodityService;	
    
    /**
     * 注入service
     */
   @Resource(name=IStockService.SERVICE_ID)
	private IStockService<StockBO> stockService;
   
   /**
    * 注入订单service
    */
  @Resource(name=IOrderService.SERVICE_ID)
	private IOrderService<OrderBO> orderService;
  
  /**
   * 注入订单service
   */
 @Resource(name=IOrderDetailService.SERVICE_ID)
	private IOrderDetailService<OrderDetailBO> orderDetailService;
  
  /**
   * 注入模板service
   */
  @Resource(name=IBenisonTemplateService.SERVICE_ID)
	private IBenisonTemplateService<BenisonTemplateBO> benisonTemplateService;
	
	/**
	  * 增加数据
	  */
	public void insertObject(Dto param) {
		logger.debug("<======CommodityAction--insertObject======>");
		
		CommodityBO commodityBO = BaseDto.toModel(CommodityBO.class , param);
		commodityService.insertObject(commodityBO);
	}
	
	/**
	  * 新增数据并上传图片
	  */
	public void insertObjectAndUpload(Dto param,HttpServletRequest request) {
		logger.debug("<======CommodityAction--insertObjectAndUpload======>");
		//上传图片
		String realUrl = null;
		String upLoadPath = CommonUtil.uploadFilePath();
		try {
			realUrl = UploadFile.uploadFile(request,upLoadPath,"COMMODITY/"+param.getAsString("commodityCode").substring(0,12),param.getAsString("commodityCode").substring(0,12));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(realUrl!=null && !"".equals(realUrl)){
			param.put("commodityImg", realUrl);
		}
		CommodityBO commodityBO = BaseDto.toModel(CommodityBO.class , param);
		commodityService.insertObject(commodityBO);
		
		// 插入库存
		logger.debug("<======CommodityAction--addStock======>");
		
		String timeFrame = param.get("timeFrame").toString();
		String startDateStr = param.get("startDate").toString(); // 2017-06-01
		String endDateStr = param.get("endDate").toString();
		
		Date startDate = new Date();
		Date endDate = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			startDate = dateFormat.parse(startDateStr);
			endDate = dateFormat.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 1:每天
		if("1".equals(timeFrame)){
			//循环日期
			Calendar ca = Calendar.getInstance();
			Date curDate = startDate;
			while(curDate.compareTo(endDate)<=0){
			      ca.setTime(curDate);
			      //业务处理
			      insertStock(param, dateFormat.format(curDate));
			      
			      ca.add(ca.DATE, 1);
			      curDate = ca.getTime();
			}
			
		// 2:每周
		}else if("2".equals(timeFrame)){
			//循环日期
			Calendar ca = Calendar.getInstance();
			Date curDate = startDate;
			while(curDate.compareTo(endDate)<=0){
			      ca.setTime(curDate);
			      //业务处理
			      insertStock(param, dateFormat.format(curDate));
			      
			      ca.add(ca.DATE, 7);
			      curDate = ca.getTime();
			}
			
		// 3:每月
		}else if("3".equals(timeFrame)){
			//循环日期
			Calendar ca = Calendar.getInstance();
			Date curDate = startDate;
			while(curDate.compareTo(endDate)<=0){
			      ca.setTime(curDate);
			      //业务处理
			      insertStock(param, dateFormat.format(curDate));
			      
			      ca.add(Calendar.MONTH, 1);
			      curDate = ca.getTime();
			}
			
		// 4:每季度
		}else if("4".equals(timeFrame)){
			//循环日期
			Calendar ca = Calendar.getInstance();
			Date curDate = startDate;
			while(curDate.compareTo(endDate)<=0){
			      ca.setTime(curDate);
			      //业务处理
			      insertStock(param, dateFormat.format(curDate));
			      
			      ca.add(Calendar.MONTH, 3);
			      curDate = ca.getTime();
			}
			
		// 5:每年
		}else if("5".equals(timeFrame)){
			//循环日期
			Calendar ca = Calendar.getInstance();
			Date curDate = startDate;
			while(curDate.compareTo(endDate)<=0){
			      ca.setTime(curDate);
			      //业务处理
			      insertStock(param, dateFormat.format(curDate));
			      
			      ca.add(Calendar.MONTH, 12);
			      curDate = ca.getTime();
			}
		}

	}
	
	/**
     *  插入库存数据
     */
	public void insertStock(Dto param, String stockDate){
		
		logger.debug("<======CommodityAction--insertStock======>");
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto stockParam = new BaseDto();
		
		// 组装数据
		stockParam.put("stockId", UUID.randomUUID().toString().replace("-", ""));
		stockParam.put("commodityId",param.get("commodityId"));
		stockParam.put("stockStatus", "1");
		stockParam.put("stockDate", stockDate);
		stockParam.put("startTime", param.get("startTime"));
		stockParam.put("endTime", param.get("endTime"));
		
		stockParam.put("sales", "0");
		stockParam.put("stock", param.get("planNumber"));
		stockParam.put("scheduleStatus", "0");
		stockParam.put("createdBy", loginId);
		stockParam.put("createdTime", TKDateTimeUtils.getTodayTimeStamp());
		stockParam.put("delflag", "1"); // “1”代表“有效”
		stockParam.put("version", 1);
		
		StockBO stockBO = BaseDto.toModel(StockBO.class , stockParam);
		stockService.insertObject(stockBO);
	}
	
	/**
      * 修改数据
      */
	public void updateObject(Dto param){
		logger.debug("<======CommodityAction--updateCommodity======>");
		
		CommodityBO commodityBO = BaseDto.toModel(CommodityBO.class , param);
		commodityService.updateObject(commodityBO);
	}
	
	/**
     * 修改数据并上传图片
     */
	public void updateObjectAndUpload(Dto param,HttpServletRequest request){
		logger.debug("<======CommodityAction--updateObjectAndUpload======>");
		//上传图片
		String realUrl = null;
		String upLoadPath = CommonUtil.uploadFilePath();
		try {
			realUrl = UploadFile.uploadFile(request,upLoadPath,"COMMODITY/"+param.getAsString("commodityCode").substring(0,12),param.getAsString("commodityCode").substring(0,12));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(realUrl!=null && !"".equals(realUrl)){
			param.put("commodityImg", realUrl);
		}
		CommodityBO commodityBO = BaseDto.toModel(CommodityBO.class , param);
		commodityService.updateObject(commodityBO);
	}


	 /**
      * 删除数据
      */
	public void deleteObject(Dto param) {
		logger.debug("<======CommodityAction--deleteCommodity======>");
		
		CommodityBO commodityBO = BaseDto.toModel(CommodityBO.class , param);
		commodityService.deleteObject(commodityBO);
	}
	
	/**
      * 查询单条数据
      */
	public Dto findOne(Dto param) {
		logger.debug("<======CommodityAction--findOneCommodity======>");
		
		CommodityBO commodityBO = BaseDto.toModel(CommodityBO.class , param);
		return commodityService.findOne(commodityBO).toDto();//返回的BO对象自动转换成Dto返回
	}  
	
	/**
      * 查询所有数据
      */
	public List<Dto> findAll(Dto param) {
		logger.debug("<======CommodityAction--findAllCommodity======>");
				
		return commodityService.findAllMap(param);
	} 
	
		
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage){
		logger.debug("<======CommodityAction--queryCommodityForPage======>");
		
		CurrentPage newCurrentPage = commodityService.queryForPage(currentPage);
		List<Dto> list = newCurrentPage.getPageItems();
		if(list!=null&& list.size()>0){
			list.forEach(commodity->{
				String commodityId = commodity.getAsString("commodity_id");
				Dto param =  new BaseDto();
				param.put("commodityId", commodityId);
				List<Dto> orderList = orderService.findAllMapByComId(param);
				if(orderList!=null && orderList.size()>0){
					Dto order = orderList.get(0);
					commodity.put("export_time", order.getAsString("export_time"));
					commodity.put("export_by", order.getAsString("export_by"));
				}
				
			});
		}
		
		return newCurrentPage;
	}
	
	/**
     * 根据日期和时间查询商品列表
     */
	public List<Dto> findAllByDateAndTime(Dto param) {
		logger.debug("<======CommodityAction--findAllByDateAndTime======>");
				
		return commodityService.findAllByDateAndTime(param);
	} 
	
	/**
     * 根据日期和时间查询重叠商品列表
     */
	public List<Dto> findRepeatByDateAndTime(Dto param) {
		logger.debug("<======CommodityAction--findRepeatByDateAndTime======>");
				
		return commodityService.findRepeatByDateAndTime(param);
	} 
	
	/**
     * 导出点播图片
     */
	public Map<String, String> exportImg(Dto param) {
		
		logger.debug("<======CommodityAction--exportImg======>");
		Map<String ,String> map = new HashMap<>();
		LoginUser user = UserUtils.getUser();
		UserBO userBo = user.getUser();
		String date = TKDateTimeUtils.formatDate(TKDateTimeUtils.getTodayDateTime(), "yyyyMMddHHmmss");//文件夹的名称
		String exportTime = null;
		//查询出要导出的商品
		CommodityBO commodityBO = BaseDto.toModel(CommodityBO.class , param);
		commodityBO = commodityService.findOne(commodityBO);
//		if(param.getAsString("commodity_status") == null || !"2".equals(param.getAsString("commodity_status"))){
//			//库存中的该商品未下架
//			map.put("result", "false");
//			map.put("msg", "商品:【"+commodityBO.getCommodityCode()+"】未下架，请下架后再进行导出操作！");
//			return map;
//		}else{
			//商品已下架，根据商品Id查询所有订单
			List<Dto> orderList = orderService.findAllMapByComId(param);
			if(orderList!=null && orderList.size()>0){
				//判断每一个订单的状态，如果有一个不符合(草稿，待支付，关闭)则不能进行导出操作
				for(Dto newOrder:orderList){
					String orderStatus = newOrder.getAsString("order_status");//订单状态
					String confirmStatus = newOrder.getAsString("confirm_status");//确认状态
					if(StringUtils.isEmpty(orderStatus)){
						map.put("result", "false");
						map.put("msg", "订单:【"+newOrder.getAsString("order_code")+"】为空状态，请检查订单信息再导出！");
						return map;
					}else if("0".equals(orderStatus)){
						map.put("result", "false");
						map.put("msg", "订单:【"+newOrder.getAsString("order_code")+"】为草稿状态，请支付后再导出！");
						return map;
					}else if("1".equals(orderStatus)){
						map.put("result", "false");
						map.put("msg", "订单:【"+newOrder.getAsString("order_code")+"】为待支付状态，请支付后再导出！");
						return map;
					}else if("0".equals(confirmStatus)){
						map.put("result", "false");
						map.put("msg", "订单:【"+newOrder.getAsString("order_code")+"】为待确认状态，请确认后再导出！");
						return map;
					}else if(!StringUtils.isEmpty(newOrder.getAsString("back_reason"))){
						map.put("result", "false");
						map.put("msg", "订单:【"+newOrder.getAsString("order_code")+"】已提交退单申请，不能导出！");
						return map;
					}
				}
				//订单更新及导出图片操作
				for(Dto order:orderList){
					
					String tempalteId = order.getAsString("template_id");
					BenisonTemplateBO benisonTemplate = new BenisonTemplateBO();
					benisonTemplate.setTemplateId(tempalteId);
					
					//查询该模板下的背景图和祝福语
					BenisonTemplateBO  benisonTemplateBO  = benisonTemplateService.findOneBenisonTemplate(benisonTemplate);
					
					//判断订单类型,0-普通，其余-定制
					String orderType = order.getAsString("order_type");
					if("0".equals(orderType)){
						map = makeImg(benisonTemplateBO,order,date);	
					}else {
						map = makeCustomImg(order,date);
					}
					
					if("true".equals(map.get("result"))){
						//导出成功,更新订单表
						order.put("orderId", order.get("order_id"));
						order.put("resultUrl", map.get("msg") == null?map.get("msg"):
							map.get("msg").toString().replace(CommonUtil.uploadFilePath(), ""));
						order.put("modifiedBy", userBo.getUserId());
						order.put("modifiedTime", TKDateTimeUtils.getCurrentDate());
//						order.put("exportBy", userBo.getUserName());
//						order.put("exportTime", exportTime = TKDateTimeUtils.getCurrentDate());
						order.put("version", order.getAsInteger("version")+1);
						
						OrderBO orderBO = BaseDto.toModel(OrderBO.class , order);
						orderService.updateObject(orderBO);
						//更新订单明细表导出人，导出时间
						Dto orderDetail = new BaseDto();
						orderDetail.put("order_id", order.get("order_id"));
						orderDetail.put("commodity_id", commodityBO.getCommodityId());
						orderDetail.put("play_date", param.get("playDate"));
						List<Dto> orderDetailList = orderDetailService.findAllMap(orderDetail);
						for(Dto orderDetailDto :orderDetailList){
							orderDetailDto.put("detailId", orderDetailDto.get("detail_id"));
							orderDetailDto.put("modifiedBy", userBo.getUserId());
							orderDetailDto.put("modifiedTime", TKDateTimeUtils.getCurrentDate());
							orderDetailDto.put("exportId", userBo.getUserId());
							orderDetailDto.put("exportBy", userBo.getUserName());
							orderDetailDto.put("exportTime", exportTime = TKDateTimeUtils.getCurrentDate());
							orderDetailDto.put("version", order.getAsInteger("version")+1);
							OrderDetailBO orderDetailBO = BaseDto.toModel(OrderDetailBO.class , orderDetailDto);
							orderDetailService.updateObject(orderDetailBO);
						}
//						map.put("msg", "图片已成功导出至文件夹"+map.get("path"));
						map.put("msg", "导出成功！");
					}
					
				}
			}else{
				map.put("result", "false");
				map.put("msg", "商品:【"+commodityBO.getCommodityCode()+"】没有相应的订单，请下单后再进行导出操作！");
				return map;
			}
			
		//记录商品状态为已排期
		String commodityId = commodityBO.getCommodityId();
		StockBO stockBO = new StockBO();
		stockBO.setCommodityId(commodityId);
		stockBO.setStockDate(param.getAsString("playDate"));
		List<StockBO> stockList = stockService.findAll(stockBO);
		if(stockList!=null&&stockList.size()>0){
			String statrTime = TKDateTimeUtils.formatToFormat(stockList.get(0).getStartTime(), "HH时mm分");
			String endTime = TKDateTimeUtils.formatToFormat(stockList.get(0).getEndTime(), "HH时mm分");
			map.put("timeRange", statrTime+"至"+endTime);
			stockList.forEach(stock->{
				stock.setScheduleStatus("1");//已排期
				stock.setModifiedBy(String.valueOf(userBo.getUserId()));
				stock.setModifiedTime(TKDateTimeUtils.getTodayTimeStamp());
				stock.setVersion(stock.getVersion()+1);
				stockService.updateObject(stock);
			});
		}else{
			logger.error("<===商品【"+commodityBO.getCommodityCode()+"】没有相应的库存，修改库存状态失败==========>");
		}
		map.put("exportTime", exportTime);
		map.put("screenName", commodityBO.getScreenName());
		return map;
//	} 
}
	
	/**
	 * 对普通订单制作图片
	 * @param benisonContent
	 * @param imgUrl
	 */
	public Map<String,String> makeImg(BenisonTemplateBO benisonTemplateBO,Dto order,String date){  
	
	Map<String,String> map = new HashMap<String,String>();
	String newImgUrl = null;
	 try {  
		//设置抬头的字体
		FontText titleText = new FontText(order.getAsString("bless_name"),benisonTemplateBO.getTitleColour(), Integer.valueOf(benisonTemplateBO.getTitleSize()), benisonTemplateBO.getTitleType());
		//设置主体内容的字体
		FontText bodyText = new FontText(benisonTemplateBO.getRuleContent(),benisonTemplateBO.getBodyColour(), Integer.valueOf(benisonTemplateBO.getBodySize()), benisonTemplateBO.getBodyType());
		//设置落款内容的字体
		FontText tailText = new FontText(order.getAsString("write_name"),benisonTemplateBO.getTailColour(), Integer.valueOf(benisonTemplateBO.getTailSize()), benisonTemplateBO.getTailType());
		
		//拷贝图片到另一个文件夹
		String path = CommonUtil.uploadFilePath()+"export/"+date+"/";
		logger.info("<=========path:{}=========>",path);
		map.put("path", path);
		//获取文件格式
		String fileFormat =benisonTemplateBO.getImgUrl().substring(benisonTemplateBO.getImgUrl().indexOf(".")+1,benisonTemplateBO.getImgUrl().length()); 
		logger.info("<=========fileFormat:{}=========>",fileFormat);
		String fileName = order.get("order_code")+"."+fileFormat;//新的文件名
		Boolean dirFlag = createDir(path);
		if(!dirFlag){
			map.put("result", "false");
			map.put("msg", "创建文件夹失败");
			return map;
		}
		newImgUrl = copyFile(CommonUtil.uploadFilePath()+benisonTemplateBO.getImgUrl(),path+fileName);
		if(StringUtils.isEmpty(newImgUrl)){
			map.put("result", "false");
			map.put("msg", "拷贝文件失败");
			return map;
		}
		//制作图片
		File file = new File(newImgUrl);
		
		BufferedImage buffImg =ImageIO.read(file);
        //创建画笔
        Graphics2D g = buffImg.createGraphics();
        //写抬头
        g.setColor(getColor(titleText.getWm_text_color()));
        g.setBackground(Color.white);
        g.setFont(getFont(titleText));
        g.drawString(titleText.getText(), Integer.valueOf(benisonTemplateBO.getTitleX()), Integer.valueOf(benisonTemplateBO.getTitleY()));//在图片上写文字:内容,X坐标，Y坐标
        //写主体
        g.setColor(getColor(bodyText.getWm_text_color()));
        g.setBackground(Color.white);
        g.setFont(getFont(bodyText));
        //主体文字自动换行处理
        newline(buffImg,bodyText,g,benisonTemplateBO,order);
        //写落款
        g.setColor(getColor(tailText.getWm_text_color()));
        g.setBackground(Color.white);
        g.setFont(getFont(tailText));
        g.drawString(tailText.getText(), Integer.valueOf(benisonTemplateBO.getTailX()), Integer.valueOf(benisonTemplateBO.getTailY()));//在图片上写文字:内容,X坐标，Y坐标
        //关闭画笔
        g.dispose();

        FileOutputStream out = new FileOutputStream(newImgUrl);
        //创键编码器，用于编码内存中的图象数据。            
        ImageIO.write(buffImg,fileFormat,out);
//        is.close();  
        out.flush();
        out.close();
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (ImageFormatException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
	 	map.put("result", "true");
		map.put("msg", newImgUrl);
        return map; 
    }  
	
	/**
	 * 文字处理
	 * @param text
	 * @return
	 */
	public Font getFont(FontText text){
		Font font = null;
        if (!StringUtils.isEmpty(text.getWm_text_font())
                && text.getWm_text_size() != null) {
            font = new Font(text.getWm_text_font(), Font.BOLD,
            		text.getWm_text_size());
        } else {
            font = new Font(null, Font.BOLD, 15);
        }
        
        return font;
	}
	
	/**
	 * 颜色处理
	 * @param color
	 * @return
	 */
	public Color getColor(String color) {
        if (color.charAt(0) == '#') {
            color = color.substring(1);
        }
        if (color.length() != 6) {
            return null;
        }
        try {
            int r = Integer.parseInt(color.substring(0, 2), 16);
            int g = Integer.parseInt(color.substring(2, 4), 16);
            int b = Integer.parseInt(color.substring(4), 16);
            return new Color(r, g, b);
        } catch (NumberFormatException nfe) {
            return null;
        }
    }
	
	/**
	 * 复制图片
	 * @param src:原文件
	 * @param target:目标文件
	 */
	public String copyFile(String src,String target)  
    {     
       File srcFile = new File(src);    
       File targetFile = new File(target);    
       try {    
           InputStream in = new FileInputStream(srcFile);     
           OutputStream out = new FileOutputStream(targetFile);    
           byte[] bytes = new byte[1024];    
           int len = -1;    
           while((len=in.read(bytes))!=-1)  
           {    
               out.write(bytes, 0, len);    
           }    
           in.close();    
           out.close();  
           logger.info("文件复制成功");   
       } catch (FileNotFoundException e) {    
           e.printStackTrace();    
       } catch (IOException e) {    
           e.printStackTrace();    
       }    
       	return target;
    }  
	
	/**
	 * 创建文件夹
	 * @param destDirName
	 * @return
	 */
	public boolean createDir(String destDirName) {  
        File dir = new File(destDirName);  
        if (!dir.exists()) {  
        	if (!destDirName.endsWith(File.separator)) {  
                destDirName = destDirName + File.separator;  
            }  
            //创建目录  
            if (dir.mkdirs()) {  
                logger.info("创建目录" + destDirName + "成功！");  
                return true;  
            } else {  
            	logger.error("创建目录" + destDirName + "失败！");  
                return false;  
            }   
        }  
        return true;  
    }
	
	/**
	 * 文字自定义换行
	 * @param image：图片对象
	 * @param bodyText:字体对象
	 * @param g:画笔对象
	 * @param benisonTemplateBO:模板对象
	 */
	public void newline(BufferedImage image,FontText bodyText,Graphics2D g,BenisonTemplateBO benisonTemplateBO,Dto order){
		
		logger.info("《====换行解析内容:{}=====》",bodyText.getText());
		
		//获取图片宽
		int srcImgWidth = image.getWidth(null);
		
		int textAreaWidth = 0;//文本域宽度
		int tempX = Integer.valueOf(benisonTemplateBO.getBodyX());//中心点横坐标
		int tempY = Integer.valueOf(benisonTemplateBO.getBodyY());//中心点纵坐标
		
		String text = bodyText.getText();//文字内容
		
		try {
			
			//按条件拆解字符
			if(!StringUtils.isEmpty(text)){
				
				//替换祝福人姓名
				if(text.contains("@blessName")){
					text = text.replace("@blessName", order.getAsString("bless_name"));
				}
				
				//替换落款人姓名
				if(text.contains("@writeName")){
					text = text.replace("@writeName", order.getAsString("write_name"));
				}
				
				//取出文本域宽度
				if(text.contains("@-")&&text.contains("-@")){
					textAreaWidth = Integer.valueOf(text.substring(text.indexOf("@-")+2, text.indexOf("-@")));
					text = text.replace("@-"+textAreaWidth+"-@","");
				}
				
				logger.info("《========图片宽度:"+srcImgWidth+"======文本域宽度:"+ textAreaWidth + "==========>");
				
				String[] strs = text.split("/n");
				
				for(String str:strs){
					
					int width = 0;
					int startX = 0;
					
					if(str.contains("/r")){
						
						String newStr = str.replace("/r", "");
						//如果字符中包含/r，判断字符串是否超出文本域宽度
						width = g.getFontMetrics(g.getFont()).charsWidth(newStr.toCharArray(),0,newStr.length());
						
						if(width>textAreaWidth){
							
							//如果超出长度，则分解开，换行写在图片上
							String[] chidrenStrs = str.split("/r");
							for(String chidrenStr:chidrenStrs){
								
								width = g.getFontMetrics(g.getFont()).charsWidth(chidrenStr.toCharArray(),0,chidrenStr.length());
								
								//每行的起始横坐标
								if(chidrenStr.contains("@*")&&chidrenStr.contains("*@")){
									//保留原来的规则
									startX = Integer.valueOf(chidrenStr.substring(chidrenStr.indexOf("@*")+2, chidrenStr.indexOf("*@")));
									chidrenStr= str.substring(chidrenStr.indexOf("*@")+2,chidrenStr.length());
								}else{
									startX = tempX-width/2;
								}
								g.drawString(chidrenStr, startX, tempY);
								//如果不是最后一行，则换行
								if(!chidrenStr.equals(chidrenStrs[chidrenStrs.length-1])){
									tempY += bodyText.getWm_text_size() + 17;
								}
							}
						}else{
							
							width = g.getFontMetrics(g.getFont()).charsWidth(newStr.toCharArray(),0,newStr.length());
							
							//每行的起始横坐标
							if(newStr.contains("@*")&&newStr.contains("*@")){
								//保留原来的规则
								startX = Integer.valueOf(newStr.substring(newStr.indexOf("@*")+2, newStr.indexOf("*@")));
								newStr= str.substring(newStr.indexOf("*@")+2,newStr.length());
							}else{
								startX = tempX-width/2;
							}
							g.drawString(newStr, startX, tempY);
							
						}
						
					}else{
						
						width = g.getFontMetrics(g.getFont()).charsWidth(str.toCharArray(),0,str.length());
						
						//每行的起始横坐标
						if(str.contains("@*")&&str.contains("*@")){
							//保留原来的规则
							startX = Integer.valueOf(str.substring(str.indexOf("@*")+2, str.indexOf("*@")));
							str= str.substring(str.indexOf("*@")+2,str.length());
						}else{
							startX = tempX-width/2;
						}
						g.drawString(str, startX, tempY);
						
					}
					
					//如果不是最后一行，则换行
					if(!str.equals(strs[strs.length-1])){
						tempY += bodyText.getWm_text_size() + 17;
					}
					logger.info("《=====单行水印文字总长度:"+ width +"============>");
				}
			}
			
		} catch (Exception e) {
			logger.error("<===CommodityActionImpl.newline=====祝福语内容规则解析出现问题！=====>");;
		}
		
	}
	
	/**
	 * 对定制订单拷贝至相应的文件夹等待导出
	 * @param benisonContent
	 * @param imgUrl
	 */
	public Map<String,String> makeCustomImg(Dto order,String date){  
	
		Map<String,String> map = new HashMap<String,String>();
		String newImgUrl = null;
		//拷贝图片到另一个文件夹
		String path = CommonUtil.exportUploadFilePath()+date+"/";
		logger.info("<=========path:{}=========>",path);
		map.put("path", path);
		//获取文件格式
		String fileFormat =order.getAsString("result_url").substring(order.getAsString("result_url").indexOf(".")+1,order.getAsString("result_url").length()); 
		logger.info("<=========fileFormat:{}=========>",fileFormat);
		String fileName = order.get("order_code")+"."+fileFormat;//新的文件名
		Boolean dirFlag = createDir(path);
		if(!dirFlag){
			map.put("result", "false");
			map.put("msg", "创建文件夹失败");
			return map;
		}
		newImgUrl = copyFile(CommonUtil.uploadFilePath()+order.getAsString("result_url"),path+fileName);
		if(StringUtils.isEmpty(newImgUrl)){
			map.put("result", "false");
			map.put("msg", "拷贝文件失败");
			return map;
		}
	 	map.put("result", "true");
		map.put("msg", newImgUrl);
	    return map; 
    }  
	
}
