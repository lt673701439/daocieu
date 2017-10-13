package com.liketry.interaction.benison.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.constants.Constants;
import com.liketry.interaction.benison.model.User;
import com.liketry.interaction.benison.service.UserService;
import com.liketry.interaction.benison.util.StringUtils;

/**
 * 用户controller
 * 
 * @author pengyy
 *
 */
@Controller
@RequestMapping("user")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * 用户列表页面
     * @param pageNum
     * @param pageSize
     * @param type
     * @return
     */
    @RequestMapping("")
    String showUserIndex() {
        return "/view/user/userIndex.jsp";
    }

    /**
     * 获取用户列表数据（带条件）
     * @param page
     * @param rows
     * @return
     */
    @ResponseBody
    @RequestMapping("/list")
    Map<String, Object> getScenicSpotPage(int page, int rows,HttpServletRequest request) {
    	
    	Map<String,Object> param = new HashMap<String,Object>();
    	if(!StringUtils.isEmpty(request.getParameter("userPhone"))){
    		param.put("userPhone", request.getParameter("userPhone"));
    	}
    	if(!StringUtils.isEmpty(request.getParameter("openId"))){
    		param.put("openId", request.getParameter("openId"));
    	}
    	if(!StringUtils.isEmpty(request.getParameter("user"))){
    		param.put("user", request.getParameter("user"));
    	}
        PageInfo<User> pageInfo = userService.findUserList(page, rows,param);
        Map<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());
        map.put("rows", pageInfo.getList());
        return map;
    }
    
    /**
	 * 打开新增页面
	 * @return   
	 * String
	 */
	@RequestMapping("/add")
	public String showAddPage() {
		return "/view/user/userAdd.jsp"; 
	}

    /**
	 * 打开修改页面
	 * @return
	 * String
	 */
	@RequestMapping("edit")
	public String showEditPage(String userId,Model model) {
		
		if(userId!=null && !userId.equals(""))
		{
			model.addAttribute("userId",userId );
		}
		
		return "/view/user/userEdit.jsp"; 
	}
	
	/**
	 * 打开详细页面
	 * @return
	 * String
	 */
	@RequestMapping("/view")
	public String showSellerViewPage(String userId,Model model) {
		
		if(userId!=null && !userId.equals(""))
		{
			model.addAttribute("userId",userId);
		}
		
		return "/view/user/userView.jsp"; 
	}
	
	/**
	 * 获取一条记录详细信息，用来填充修改界面
	 * @return
	 */
	@RequestMapping("/getOne")
	@ResponseBody
	public User getUserById(@RequestParam("userId")String userId)
	{
		return userService.findOneUser(userId);
	}
	
	/**
	 * 保存新增或修改的记录，将其持久化到数据库中
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	private Map<String,Object> saveUserInfo(HttpServletRequest request)
	{
		Map<String,Object> map=new HashMap<String,Object>();
		String userId = request.getParameter("userId");
		if(userId ==null ||"".equals(userId))
		{
			User oldOne = userService.findOneByOpenId(request.getParameter("openId"));
			
			// 校验用户是否存在
			if(oldOne != null){
				map.put(Constants.MESSAGE_INFO, "该openId已存在！");
				map.put(Constants.RTN_RESULT, "false");
				return map;
			}
			
			User user = new User();
			user.setUserId(UUID.randomUUID().toString().replace("-", ""));
			user.setUserPhone(request.getParameter("userPhone"));
			user.setUserNickname(request.getParameter("user"));
			user.setUserIcon(request.getParameter("userIcon"));
			user.setOpenId(request.getParameter("openId"));
			LocalDate localDate = LocalDate.now();
		    ZoneId zone = ZoneId.systemDefault();
		    Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
		    Date date = Date.from(instant);
			user.setCreatedTime(date);
			user.setDelflag("0");// “0”代表“未删除”
			
			// 添加用户
			userService.insertObject(user);
			map.put(Constants.MESSAGE_INFO, "添加成功！");
		}else
		{
			// 查询数据库里用户数据
			User oldOne = userService.findOneUser(userId);
			oldOne.setUserNickname(request.getParameter("user"));
			
			// 更新用户数据
			userService.updateObject(oldOne);
			map.put(Constants.MESSAGE_INFO, "修改成功！");
		}
		map.put(Constants.RTN_RESULT, "true");
		
		return map;
	}
	
}