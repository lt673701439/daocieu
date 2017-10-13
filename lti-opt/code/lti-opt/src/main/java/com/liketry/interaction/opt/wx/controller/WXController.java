package com.liketry.interaction.opt.wx.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.liketry.interaction.opt.util.WeChatUtils;
import com.taikang.iu.com.ExcelUtil;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.web.BaseController;


/**
  * OrderController
  */
@Controller("wxController")
@RequestMapping(value="/wx")
public class WXController  extends BaseController  {
		
	/**
	 * 打开对账单下载页面
	 * @return 页面地址
	 */
	@RequestMapping("/bill")
	public String showOrderIndexPage() {
		return "wx/bill"; 
	}
	
	/**
	 * 导出订单列表excel
	 * @return
	 */
	@RequestMapping(value="/downloadBill")
	public String download(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		Dto param = getParamAsDto(request);
		
		String startDate = param.getAsString("startDate");
		String endDate = param.getAsString("endDate");
		String tradeType = param.getAsString("tradeType");
		
		if(startDate == null || endDate == null){
			return "开始和结束日期都不能为空，请重新填写！";
		}
		
		// 获取微信对账单数据列表
		List<Dto> dataList = WeChatUtils.getBillDataList(startDate, endDate, "ALL", tradeType);
		
		String keys[] = {"pay_time","mch_id","order_code","transaction_no","pay_type","pay_status","pay_price","back_price",};//map中的key
        String columnNames[]={"交易时间","商户号","订单编号","交易单号","交易类型","交易状态","交易金额(元)","退款金额(元)"};//列名
        
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        
        // 制作Workbook
        try {
            ExcelUtil.createWorkBookByDto("对账单数据", dataList, keys, columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
		// 导出文件名
		String fileName= "APP对账单_"+ startDate.replaceAll("-", "") + "~" + endDate.replaceAll("-", "") + ".xlsx";
		if("JS".equals(tradeType)){
			fileName= "小程序对账单_"+ startDate.replaceAll("-", "") + "~" + endDate.replaceAll("-", "") + ".xlsx";
		}
	        
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("GB2312"), "ISO_8859_1"));
        
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
}

