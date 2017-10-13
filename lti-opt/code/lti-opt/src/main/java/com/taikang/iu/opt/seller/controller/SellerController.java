package com.taikang.iu.opt.seller.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taikang.iu.com.ExcelUtil;
import com.taikang.iu.opt.seller.action.ISellerAction;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.exception.TKCheckedException;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.action.IRoleAction;
import com.taikang.udp.sys.action.IUserAction;
import com.taikang.udp.sys.action.IUserRoleAction;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.sequence.BusinessSeqGenerator;
import com.taikang.udp.sys.util.vo.LoginUser;

/**
 * 商户信息管理
 * @author t-wuke
 * @version [版本号，默认V1.0.0]
 * @Credited 2015年3月18日 上午10:14:22
 */
@Controller("sellerController")
@RequestMapping(value="/seller")
public class SellerController extends BaseController  {
		
	@Resource(name=ISellerAction.ACTION_ID)
	private ISellerAction sellerAction;
	
	/**
	 * 注入用户角色service
	 */
	
	@Resource(name=IUserRoleAction.ACTION_ID)
	private IUserRoleAction userRoleAction;
	
	/**
	 * 注入角色service
	 */
	@Resource(name=IRoleAction.ACTION_ID)
	private IRoleAction roleAction;
		
	@Resource(name=IUserAction.ACTION_ID)
	private IUserAction userAction;
	
	/**
	 * 打开主查询页面
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showSellerIndexPage() {
		return "seller/sellerIndex"; 
	}
	
	/**
	 * 导出excel
	 * @author t-lilong
	 * @Credited 2015年7月16日 下午4:18:17
	 * @return
	 */
	  @RequestMapping(value="/downloadSeller")
	    public String download(HttpServletRequest request,HttpServletResponse response) throws IOException{
	        String fileName="excel";
	        String sellerCode =request.getParameter("sellerCode");
	        byte[] newCustomName =request.getParameter("sellerName").getBytes("iso-8859-1");
	        String sellerName = new String(newCustomName,"UTF-8");
	        String sellerType =request.getParameter("sellerType");
	        String site =request.getParameter("site");//serviceSite
	        //填充projects数据
	        List<Dto> projects=createData(sellerCode,sellerName,sellerType,site);
	        List<Map<String,Object>> list=createExcelRecord(projects);
	        String columnNames[]={"商户编号","登录名","商户名称","商户类型","站点","商户服务电话","创建时间"};//列名
	        String keys[]   =    {"sellerCode","loginName","sellerName","sellerType","site","sellerTel","createdTime"};//map中的key
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        try {
	            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        byte[] content = os.toByteArray();
	        InputStream is = new ByteArrayInputStream(content);
	        // 设置response参数，可以打开下载页面
	        response.reset();
	        response.setContentType("application/vnd.ms-excel;charset=utf-8");
	        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
	        ServletOutputStream out = response.getOutputStream();
	        BufferedInputStream bis = null;
	        BufferedOutputStream bos = null;
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
	  
	    private List<Dto> createData(String sellerCode,String sellerName,String sellerType,String site) {
	    	Dto param = new BaseDto();
	    	if(sellerCode != null){
	    		param.put("seller_code", sellerCode);
	    	}
	    	if(sellerName != null){
	    		param.put("seller_name", sellerName);
	    	}
	    	if(sellerType != null){
	    		param.put("seller_type", sellerType);
	    	}
	    	if(site != null){
	    		param.put("site", site);
	    	}
	    	param.put("delflag", "0");
	    	List <Dto> list = sellerAction.findAllSeller(param);
	        return list;
	    }
	    private List<Map<String, Object>> createExcelRecord(List<Dto> projects) {
	        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("sheetName", "sheet1");
	        listmap.add(map);
	        Dto temp=null;
	        for (int j = 0; j < projects.size(); j++) {
	        	temp=projects.get(j);
	            Map<String, Object> mapValue = new HashMap<String, Object>();
	            mapValue.put("sellerCode", temp.getAsString("SELLER_CODE"));
	            mapValue.put("sellerName",temp.getAsString("SELLER_NAME"));
	            mapValue.put("sellerType",temp.getAsString("SELLER_TYPE"));
	            mapValue.put("sellerTel", temp.getAsString("SELLER_TEL"));
	            mapValue.put("createdTime", temp.getAsString("CREATED_TIME"));
	            mapValue.put("site",temp.getAsString("SITE"));
	            mapValue.put("loginName",temp.getAsString("LOGIN_NAME"));
	            listmap.add(mapValue);
	        }
	        return listmap;
	    }
	
	/**
	 * 查询信息列表
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String,Object> getSellerList(HttpServletRequest request,CurrentPage page){
		Map<String, Object> map = new HashMap<String, Object>();
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		Dto param = new BaseDto();
		Dto params = new BaseDto();
		Dto userRoleParam = new BaseDto();
		userRoleParam.put("roleCode", "yunyingm");
		Dto roleIdDto = roleAction.findRoleIdByCode(userRoleParam);
		roleIdDto.put("userId", loginId);
		Dto userRole = userRoleAction.findOne(roleIdDto);//查询是否是运营经理
		Dto  dto = getParamAsDto(request);
		if(userRole.isEmpty()){
			param.put("userId", loginId);
//			Dto loginUser = employeeAction.findOne(param);//通过用户ID查询该用户站点
//			String site = loginUser.getAsString("SITE");
//			String sites = dto.getAsString("SITE");
//			if(site.equals(sites)||"".equals(sites)||null==sites){
//				dto.put("site", site);
//			}else{
//				dto.put("site", "qwer");
//			}
		}
		page.setParamObject(dto);
		CurrentPage currentPage = sellerAction.queryForPage(page);
		
		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		
		return map;
	}

	/**
	 * 打开新增页面
	 * @return   
	 * String
	 */
	@RequestMapping("/add")
	public String oneRole() {
		return "seller/sellerAdd"; 
	}
	
	/**
	 * 打开修改页面
	 * @return
	 * String
	 */
	@RequestMapping("edit")
	public String showSellerEditPage(String sellerId,Model model) {
		
		if(sellerId!=null && !sellerId.equals(""))
		{
			model.addAttribute("sellerId",sellerId );
		}
		
		return "seller/sellerEdit"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 * String
	 */
	@RequestMapping("/view")
	public String showSellerViewPage(String sellerId,Model model) {
		
		if(sellerId!=null && !sellerId.equals(""))
		{
			model.addAttribute("sellerId",sellerId );
		}
		
		return "seller/sellerView"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getSellerById(@RequestParam("sellerId")String sellerId)
	{
		Dto param = new BaseDto();
		param.put("sellerId", sellerId);
		return sellerAction.findOne(param);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,String> saveSellerInfo(HttpServletRequest request)
	{
		Map<String,String> map=new HashMap<String,String>();
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		Dto param = getParamAsDto(request);
		Dto params = new BaseDto();
		if(param.get("sellerId") ==null ||"".equals(param.get("sellerId")))
		{
			Dto oldParam = new BaseDto();
			oldParam.put("userCode", param.get("loginName"));
			Dto oldOne = userAction.findOne(oldParam);
			
			// 校验登录名是有已存在
			if(oldOne != null && oldOne.get("userCode") != null){
				map.put(MESSAGE_INFO, "登录名已存在！");
				map.put(RTN_RESULT, "false");
				return map;
			}
			
			param.put("sellerId", UUID.randomUUID().toString().replace("-", ""));
			param.put("sellerState", "1"); // “1”代表“使用中”
			param.put("sellerCode", "SH"+TKDateTimeUtils.getCurrentDate().toString().replace("-", "")+BusinessSeqGenerator.getInstance("SELLER_CODE").nextId()); 
			param.put("createdBy", loginId);
			param.put("createdTime", TKDateTimeUtils.getTodayTimeStamp());
			param.put("delflag", "0"); // “0”代表“未删除”
			param.put("version", 1);
			
			// 添加商户
			sellerAction.insertObject(param);
			map.put(MESSAGE_INFO, "添加成功！");
		}
		else
		{
			Dto oldParam = new BaseDto();
			oldParam.put("sellerId", param.get("sellerId"));
			
			// 查询数据库里商户数据
			Dto oldOne = sellerAction.findOne(oldParam);
			oldOne.put("userId", oldOne.get("userId"));
			oldOne.put("sellerType", param.get("sellerType"));
			oldOne.put("modifiedBy", loginId);
			oldOne.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
			oldOne.put("version", Integer.parseInt(oldOne.get("version").toString())+1);
			
			// 更新商户数据
			sellerAction.updateObject(oldOne);
			map.put(MESSAGE_INFO, "修改成功！");
		}
		map.put(RTN_RESULT, "true");
		
		return map;
	}
	
	/**
	*删除一条或多条记录
	*/
	@RequestMapping(value="/del")
	@ResponseBody
	public Map<String, String> deleteSeller(@RequestParam("sellerId") String sellerId, @RequestParam("version") String version) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("sellerId", sellerId);
		// 删除改为逻辑删除
//		sellerAction.deleteObject(param);
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		param.put("modifiedBy", loginId);
		param.put("modifiedTime", TKDateTimeUtils.getTodayTimeStamp());
		param.put("delflag", "1"); // “1”代表“已删除”
		param.put("version", Integer.parseInt(version)+1);
		sellerAction.updateObject(param);
		
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");
		
		return map;
	}
	
	/**
	*	重置密码
	*/
	@RequestMapping(value="/resetPwd")
	@ResponseBody
	public Map<String, String> resetPwd(String sellerId,HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = getParamAsDto(request);
		
		if(sellerId != null){
			if(sellerId.indexOf(",")  != -1){
				sellerId = sellerId.substring(0, sellerId.indexOf(","));
			}
			// 获取商户初始密码
			Dto sellerParam = new BaseDto();
			sellerParam.put("sellerId", sellerId);
			Dto seller = sellerAction.findOne(sellerParam);
			
			// 获取用户数据
			Dto userParam = new BaseDto();
			userParam.put("userId", seller.get("userId"));
			Dto user = userAction.findOne(userParam);
			
			// 更新用户密码
			if(seller.get("loginInitPwd") != null){
				try {
					user.put("userPwd", UserUtils.entryptPassword(seller.get("loginInitPwd").toString()));
					userAction.updateObject(user);
					
					map.put(RTN_RESULT, "true");
					map.put(MESSAGE_INFO, "操作成功！");
				} catch (TKCheckedException e) {
					logger.error("<======SellerController--resetPwd--此商户初始密码加密时出错（"+param.get("sellerCode")+"）======>");
					e.printStackTrace();
					
					map.put(MESSAGE_INFO, "初始密码加密时出错，初始密码失败！");
					map.put(RTN_RESULT, "false");
				}
			}else {
				logger.error("<======SellerController--resetPwd--此商户没有初始密码（"+param.get("sellerCode")+"）======>");
				map.put(MESSAGE_INFO, "没有初始密码，初始密码失败！");
				map.put(RTN_RESULT, "false");
			}
		}
		
		return map;
	}
}

