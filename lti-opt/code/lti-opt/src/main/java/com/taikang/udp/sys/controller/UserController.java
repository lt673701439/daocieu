package com.taikang.udp.sys.controller;

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
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.common.util.TKDateTimeUtils;
import com.taikang.udp.framework.core.exception.TKCheckedException;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.framework.core.web.log.Log;
import com.taikang.udp.framework.core.web.log.LogLevel;
import com.taikang.udp.framework.core.web.log.OperType;
import com.taikang.udp.sys.action.IRoleAction;
import com.taikang.udp.sys.action.ISystemAction;
import com.taikang.udp.sys.action.IUserAction;
import com.taikang.udp.sys.util.DictUtils;
import com.taikang.udp.sys.util.Tree;
import com.taikang.udp.sys.util.UserUtils;
import com.taikang.udp.sys.util.vo.LoginUser;

/**
 * UserController
 */
@Controller("userController")
@RequestMapping(value = "user")
public class UserController extends BaseController {

	@Resource(name = IUserAction.ACTION_ID)
	private IUserAction userAction;

	@Resource(name = IRoleAction.ACTION_ID)
	private IRoleAction roleAction;

	@Resource(name = ISystemAction.SERVICE_ID)
	private ISystemAction iSystemAction;

//	@Resource(name = IEmployeeAction.ACTION_ID)
//	private IEmployeeAction employeeAction;

	/*
	 * 跳转到密码修改页面
	 */
	@RequestMapping("/changePwd")
	private String tochangePwd(Model model) {
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		model.addAttribute("userName", user.getUserName());
		model.addAttribute("userId", user.getUserId());
		return "/uptPwd";
	}

	/**
	 * 打开主查询页面
	 * 
	 * @return 页面地址
	 */
	@RequestMapping("")
	public String showUserIndexPage(
			@RequestParam("reqMenuId") String reqMenuId, Model model) {

		if (reqMenuId != null && !reqMenuId.equals("")) {
			model.addAttribute("reqMenuId", reqMenuId);
		}
		return "sys/user/userIndex";
	}

	/**
	 * 查询信息列表
	 * 
	 * @return 分页列表数据
	 */
	@RequestMapping("/Conlist")
	@ResponseBody
	@Log(message = "按条件查询用户信息", operType = OperType.READ, operName = "分页查询用户", level = LogLevel.TRACE)
	public Map<String, Object> getUserByConList(HttpServletRequest request,
			CurrentPage page, @RequestParam("user_code") String userCode,
			@RequestParam("user_name") String userName,
			@RequestParam("user_type") String userType) {
		Map<String, Object> map = new HashMap<String, Object>();

		if (userCode == null || ",".equals(userCode.trim())
				|| "".equals(userCode.trim())) {
			userCode = null;
		}
		if (userName == null || ",".equals(userName.trim())
				|| "".equals(userName.trim())) {
			userName = null;
		}
		if (userType == null || ",".equals(userType.trim())
				|| "".equals(userType.trim())) {
			userType = null;
		}

		List<Tree> list = DictUtils.getChildDictTreeLst("", "MultiState");
		// Dto param = getParamAsDto(request);
		// String user_code = request.getParameter("user_code");
		request.setAttribute("user_code", userCode);
		request.setAttribute("user_name", userName);
		request.setAttribute("user_type", userType);
		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = userAction.queryByCondition(page);

		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());
		return map;
	}
	
	
	/**
	 * 查询信息列表
	 * 
	 * @return 分页列表数据
	 */
	@RequestMapping("/downLoadUser")
	@Log(message = "按条件查询用户信息", operType = OperType.READ, operName = "分页查询用户", level = LogLevel.TRACE)
	public void downLoadUser(HttpServletRequest request,HttpServletResponse response,
			 @RequestParam("usercode") String userCode,
			@RequestParam("username") String userName,
			@RequestParam("usertype") String userType) throws IOException{
		 String fileName="UserExcel";
		 byte[] newCustomName =userName.getBytes("iso-8859-1");
		 userName = new String(newCustomName,"UTF-8");
		Dto param=new BaseDto();
		if(userCode!=null&&!"".equals(userCode)){
			param.put("user_code", userCode);
		}
		if(userName!=null&&!"".equals(userName)){
			param.put("user_name", userName);
		}
		if(userType!=null&&!"".equals(userType)){
			param.put("user_type", userType);
		}
		 List<Dto> projects=createData(param);
		 List<Map<String, Object>> listMap = createMapList(projects);
		 String columnNames[]={"商户编号","用户姓名","用户类型","用户性质","是否在岗","所属部门"};//列名
	        String keys[]   =    {"User_Code","User_Name","User_Type","User_Nature","Work_Status","User_Dept"};//map中的key
	       
	        ByteArrayOutputStream os = new ByteArrayOutputStream();
	        try {
	            ExcelUtil.createWorkBook(listMap,keys,columnNames).write(os);
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
		
	}

	private List<Map<String, Object>> createMapList(List<Dto> projects) {
		List<Map<String,Object>> listMap=new ArrayList<Map<String,Object>>();
		Map firstMap=new HashMap<String, Object>();		
		firstMap.put("sheetName", "sheet1");
		listMap.add(firstMap);
		 for (Dto dto : projects) {
			Map map=dto;
			 map.put("sheetName", "sheet1");
			String type=(String)map.get("User_Type");//用户类型（0：客户，1：员工，2：商户）
			if("0".equals(type)){
				map.put("User_Type","客户");
			}else if("1".equals(type)){
				map.put("User_Type","员工");
			}else if("2".equals(type)){
				map.put("User_Type","商户");
			}else{
				map.put("User_Type","");
			}
			String nature=(String)map.get("User_Nature");//用户性质:H：总公司 B：分公司
			if("H".equalsIgnoreCase(nature)){
				map.put("User_Nature","总公司");
			}else if("B".equalsIgnoreCase(nature)){
				map.put("User_Nature","分公司");
			}else{
				map.put("User_Nature","");
			}
			String status=(String)map.get("Work_Status");//工作状态:1、在岗0、离岗
			if("1".equals(status)){
				map.put("Work_Status","在岗");
			}else if("0".equals(status)){
				map.put("Work_Status","离岗");
			}else{
				map.put("Work_Status","");
			}
			listMap.add(map);
		}
		return listMap;
	}
	
	

	private List<Dto> createData(Dto param) {
		return userAction.queryAllUsersByCondition(param);
	}

	/**
	 * 角色设置
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toSetRo")
	public String setUserRole(@RequestParam("userCode") String userCode,
			@RequestParam("userName") String userName,
			HttpServletRequest request, Model model) {
		if (userCode != null && !userCode.equals("")) {
			model.addAttribute("userCode", userCode);
		}
		if (userName != null && !userName.equals("")) {
			model.addAttribute("userName", userName);
		}
		Dto param = getParamAsDto(request);
		List<Dto> roleList = roleAction.findAll(param);
		model.addAttribute("roleList", roleList);
		return "sys/user/setUserRole";

	}
	/**
	 * 员工管理，添加角色设置
	 * @param userCode
	 * @param userName
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/toSetRoT")
	public String setUserRoleT(@RequestParam("userCode") String userCode,
			@RequestParam("userName") String userName,
			HttpServletRequest request, Model model) {
		if (userCode != null && !userCode.equals("")) {
			model.addAttribute("userCode", userCode);
		}
		if (userName != null && !userName.equals("")) {
			model.addAttribute("userName", userName);
		}
		Dto param = getParamAsDto(request);
		List<Dto> roleList = roleAction.findAll(param);
		model.addAttribute("roleList", roleList);
		return "sys/user/setUserRoleT";

	}
	/**
	 * 机构设置
	 * 
	 * @param userId
	 * @param model
	 * @return
	 */
	@RequestMapping("/toSetOrg")
	public String setOrgaize(@RequestParam("userId") String userId,
			@RequestParam("userName") String userName,
			HttpServletRequest request, Model model) {
		if (userId != null && !userId.equals("")) {
			model.addAttribute("userId", userId);
		}
		if (userName != null && !userName.equals("")) {
			model.addAttribute("userName", userName);
		}
		return "sys/user/setUserOrganize";
	}

	/**
	 * 查询信息列表
	 * 
	 * @return 分页列表数据
	 */
	@RequestMapping("/list")
	@ResponseBody
	public Map<String, Object> getUserList(HttpServletRequest request,
			CurrentPage page) {
		Map<String, Object> map = new HashMap<String, Object>();

		page.setParamObject(getParamAsDto(request));
		CurrentPage currentPage = userAction.queryForPage(page);

		map.put("rows", currentPage.getPageItems());
		map.put("total", currentPage.getTotalRows());

		return map;
	}

	/**
	 * 打开新增用户页面<br/>
	 * 
	 * @return
	 */
	@RequestMapping("add")
	public String showUserAddPage(String rowId, Model model) {
		return "sys/user/userAdd";
	}

	/**
	 * 打开修改页面<br/>
	 * 
	 * @param rowId
	 * @param model
	 * @return
	 */
	@RequestMapping("edit")
	public String showUserEditPage(String rowId, Model model) {
		if (rowId != null && !rowId.equals("")) {
			model.addAttribute("userId", rowId);
		}
		return "sys/user/userEdit";
	}

	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * 
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public Dto getUserById(@RequestParam("rowId") String rowId) {
		Dto param = new BaseDto();
		param.put("userId", rowId);
		return userAction.findOne(param);
	}

	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/save")
	@ResponseBody
	@Log(message = "保存用户信息", operType = OperType.READ, operName = "保存用户信息", level = LogLevel.TRACE)
	private Map<String, String> saveUserInfo(HttpServletRequest request) {
		
		Map<String, String> map = new HashMap<String, String>();
		
		Dto param = getParamAsDto(request);
		LoginUser user = UserUtils.getUser();
		
		String loginId = String.valueOf(user.getUserId());
		String userPwd = String.valueOf(param.get("userPwd"));
		
		if (param.get("userId") == null || "".equals(param.get("userId"))) {
			
			try {
				// 新增用户时初始化用户密码
				param.put("userPwd", UserUtils.entryptPassword(userPwd));
				param.put("workStatus", "1");
				param.put("userStatus", "1");
				param.put("userType", "1");
				param.put("workStatus", "1");
				param.put("workStatus", "1");
				param.put("creator", loginId);
				param.put("createTime", TKDateTimeUtils.getTodayTimeStamp());
				
				// 新增用户
				userAction.insertObject(param);
				map.put(MESSAGE_INFO, "新增成功！");
				
			} catch (TKCheckedException e) {
				
				map.put(RTN_RESULT, "false");
				map.put(MESSAGE_INFO, "加密密码时发生错误！");
				return map;
			} catch (Exception e) {
				
				map.put(RTN_RESULT, "false");
				map.put(MESSAGE_INFO, "登录名或用户名的长度不符合规则！");
				return map;
			}
		} else {
			param.put("lastModby", loginId);
			param.put("lastModtime", TKDateTimeUtils.getTodayTimeStamp());
			userAction.updateObject(param);
			map.put(MESSAGE_INFO, "更新成功！");
		}
		map.put(RTN_RESULT, "true");

		return map;
	}

	/**
	 * 重置密码
	 */
	@RequestMapping(value = "/rSetPwd")
	@ResponseBody
	@Log(message = "重置指定用户密码", operType = OperType.READ, operName = "重置用户密码", level = LogLevel.TRACE)
	public Map<String, String> rSetPsw(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = getParamAsDto(request);
		String passwd;
		try {
			passwd = UserUtils.entryptPassword(param.getAsString("userCode"));
		} catch (TKCheckedException e) {
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "加密密码时发生错误！");
			return map;
		}
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		param.put("userPwd", passwd);
		// 判断重置的是否为当前用户
		if (loginId.equals(param.get("userId"))) {
			// 当前用户时更新shiro
			iSystemAction.updatePasswordById(Integer.valueOf(loginId),
					user.getUserName(), param.getAsString("userCode"));
		} else {
			userAction.updateObject(param);
		}
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "密码重置成功！");
		return map;
	}

	/**
	 * 打开设置权限页面<br/>
	 * 
	 * @param roleName
	 * @param roleId
	 * @param roleCode
	 * @param model
	 * @return String
	 */
	@RequestMapping("/toshowAuthorith")
	public String setAuth(String roleName, String roleId, String roleCode,
			Model model) {
		if (roleName != null && !roleName.equals("")) {
			model.addAttribute("roleName", roleName);
		}
		if (roleId != null && !roleId.equals("")) {
			model.addAttribute("roleId", roleId);
		}
		if (roleCode != null && !roleCode.equals("")) {
			model.addAttribute("roleCode", roleCode);
		}
		return "sys/user/showAuthorith";
	}

	/**
	 * 删除一条或多条记录
	 */
	@RequestMapping(value = "/del")
	@ResponseBody
	@Log(message = "删除指定用户", operType = OperType.READ, operName = "用户删除", level = LogLevel.TRACE)
	public Map<String, String> deleteUser(@RequestParam("rowId") String rowId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = new BaseDto();
		param.put("userId", rowId);
		userAction.deleteObject(param);

		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "操作成功！");

		return map;
	}

	@RequestMapping("/pwdSave")
	@ResponseBody
	@Log(message = "修改指定用户密码", operType = OperType.READ, operName = "修改用户密码", level = LogLevel.TRACE)
	public Map<String, String> pwdSave(HttpServletRequest request) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = getParamAsDto(request);
		String userPwd = param.getAsString("userPwd");
		String userId = String.valueOf(param.get("userId"));

		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		String entrypuserPwd;
		try {
			entrypuserPwd = UserUtils.entryptPassword(userPwd);
			param.put("userPwd", entrypuserPwd);
		} catch (TKCheckedException e) {
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "加密密码时发生错误！");
			return map;
		}
		// 判断重置的是否为当前用户
		if (loginId.equals(param.get("userId"))) {
			// 当前用户时更新shiro
			iSystemAction.updatePasswordById(Integer.valueOf(loginId),
					user.getUserName(), userPwd);
		} else {
			userAction.updateObject(param);
		}
		map.put(RTN_RESULT, "true");
		map.put(MESSAGE_INFO, "密码重置成功！");
		return map;
	}

	/**
	 * 修改用户机构信息<br/>
	 * 
	 * @param request
	 * @return Map<String,String>
	 */
	@RequestMapping("/setCom")
	@ResponseBody
	@Log(message = "修改指定用户机构", operType = OperType.READ, operName = "修改用户机构信息", level = LogLevel.TRACE)
	private Map<String, String> setCom(HttpServletRequest request,
			@RequestParam("Com_Id") String Com_Id,
			@RequestParam("userId") String userId) {
		Map<String, String> map = new HashMap<String, String>();
		Dto param = getParamAsDto(request);
		LoginUser user = UserUtils.getUser();
		String loginId = String.valueOf(user.getUserId());
		// String userPwd = String.valueOf(param.get("userPwd"));
		if (userId == null || "".equals(userId)) {
			map.put(RTN_RESULT, "false");
			map.put(MESSAGE_INFO, "没有传入需要设置机构的用户！");
			return map;
		} else {
			param.put("userId", userId);
			param.put("userCom", Com_Id);
			param.put("lastModby", loginId);
			param.put("lastModtime", TKDateTimeUtils.getTodayTimeStamp());
			userAction.updateObject(param);
			map.put(MESSAGE_INFO, "更新成功！");
		}
		map.put(RTN_RESULT, "true");

		return map;
	}
}
