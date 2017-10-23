package com.liketry.interaction.opt.coupon.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.springframework.web.bind.annotation.RequestParam;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;

import java.util.HashMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;
import com.liketry.interaction.opt.coupon.action.ICouponAction;
import java.util.Map;
import java.util.Random;

import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.iu.com.ExcelUtil;
import com.taikang.udp.framework.common.datastructre.Dto;


/**
  * CouponController
  */
@Controller("couponController")
@RequestMapping(value="/coupon")
public class CouponController  extends BaseController  {
		
	@Resource(name=ICouponAction.ACTION_ID)
	private ICouponAction couponAction;
		
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showCouponIndexPage() {
		return "coupon/couponIndex"; 
	}
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getCouponList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = couponAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}
	
	/**
	 * 打开新增页面
	 * @return
	 */
	@RequestMapping("add")
	public String showCouponAddPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("couponId",rowId );
		}
		
		return "coupon/couponAdd"; 
	}

	/**
	 * 打开新增或修改页面
	 * @return
	 */
	@RequestMapping("edit")
	public String showCouponEditPage(String rowId,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("rowId",rowId );
		}
		
		return "coupon/couponEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 */
	@RequestMapping("view")
	public String showCouponViewPage(String rowId,String publishTargetType,Model model) {
		
		if(rowId!=null && !rowId.equals(""))
		{
			model.addAttribute("publishTargetTypeValue",publishTargetType );
			model.addAttribute("rowId",rowId );
		}
		
		return "coupon/couponView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getCouponById(@RequestParam("rowId")String rowId)
	{
		Dto param = new BaseDto();
		param.put("couponId", rowId);
		return couponAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveCouponInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		Dto param = getParamAsDto(request);
		if(param.get("rowId") ==null ||"".equals(param.get("rowId")))
		{
			couponAction.insertObject(param);
			map.put(MESSAGE_INFO, "新增成功！");
		}
		else
		{
			couponAction.updateObject(param);
			map.put(MESSAGE_INFO, "更新成功！");
		}
		map.put(RTN_RESULT, "true");
		
		return map;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteCoupon(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("rowId", rowId);
		couponAction.deleteObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
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
		List<Dto> list = couponAction.findAllCoupon(param);
		String keys[] = { "coupon_code", "coupon_name","coupon_type", "publish_date", "publish_target_type", "personal_name", "id_card", "merchant_name", "business_licence", "valid_date", "publish_by",
				"publish_date", "use_type", "order_code" };// map中的key
		String columnNames[] = { "优惠码", "优惠码名称","优惠码类型", "发布日期", "发布对象", "个人姓名", "身份证号", "商户姓名", "营业执照编号", "有效日期", "发布人", "发布日期", "是否使用", "订单编号" };// 列名

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		// 制作Workbook
		try {
			ExcelUtil.createWorkBookByDto("优惠码数据", list, keys, columnNames).write(os);
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
	 * 发布优惠码
	 * @return
	 */
	@RequestMapping("/publish")
	@ResponseBody
	private Map<String,String> publishCode(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		
		Dto param = getParamAsDto(request);
		param.put("publishBy", user.getUserName());
		param.put("publishDate", TKDateTimeUtils.getTodayTimeStamp());
		param.put("createdBy", loginId);
		param.put("createdTime", TKDateTimeUtils.getTodayTimeStamp());
		param.put("delflag", "1"); // “1”代表“有效”
		param.put("version", 1);
		couponAction.makeActivatedcode(param);
		map.put(MESSAGE_INFO, "更新成功！");
		map.put(RTN_RESULT, "true");
		
		return map;
	}
	
}

