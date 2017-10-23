package com.liketry.api;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.IdentifyingCode;
import com.liketry.domain.User;
import com.liketry.service.IdentifyingCodeService;
import com.liketry.service.UserService;
import com.liketry.util.CommonUtils;
import com.liketry.util.Constants;
import com.liketry.util.PropertiesUtils;
import com.liketry.util.SmsUtils;
import com.liketry.web.BaseController;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Random;

/**
 * 用户api
 * @author pengyy
 */
@Slf4j
@Api(value="用户接口",description="供C端调用")
@RestController
@RequestMapping("api/user_api")
public class UserApi extends BaseController<UserService,User>{

	@Autowired
	IdentifyingCodeService identifyingCodeService;

	/**
	 * 用户注册
	 * @param json
	 * @return
	 */
	 @ApiOperation(value="用户注册")
		   @ApiImplicitParams({
		  	 @ApiImplicitParam(name = "json", value = "json数据，手机号：phone,验证码：code,类型：type", required = true,dataType = "JSONObject")
		   })
    @PostMapping(value = "/register")
    public ResultVM register(@RequestBody JSONObject json) {

    	log.info("<=====用户注册信息：{}======>",json);

    	if(json == null){
    		return ResultVM.error(Constants.data_null,"数据不能为null！");
    	}

    	//校验手机号格式
    	String loginName = json.getString("phone");
    	String code = json.getString("code");
    	String type = json.getString("type");

		if(!CommonUtils.valudateMobile(loginName)){
			return ResultVM.error(Constants.code_mobile_typeError,"手机号格式不对！");
		}

		//校验验证码
		String msg = identifyingCodeService.checkIdentifyingCode(loginName,code,type);

		if(msg!=null){
			return ResultVM.error(Constants.code_identifying_Error,msg);
		}

		//校验手机号的唯一性
		User param = new User();
		param.setLoginName(loginName);
		User oneUser = service.selectOne(new EntityWrapper<User>(param));

		if(oneUser!=null){
			return ResultVM.error(Constants.code_user_exist,"用户已存在！");
		}

    	User user = service.insertOneUser(param);

    	if(user!=null){
    		return ResultVM.ok(user);
    	}else{
    		return ResultVM.error("用户注册失败！");
    	}

    }

	/**
     * 更新用户
     * @param t
     * @return
     */
    @ApiOperation(value="修改用户信息",notes="修改用户信息")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "t", value = "实体对象", required = true,dataType = "User")
	   })
    @PutMapping
    public ResultVM update(@RequestBody User t) {

    	String userId = t.getId();
    	User user = service.selectById(userId);
    	if(user == null){
    		return ResultVM.error(Constants.data_null,"数据不能为空");
    	}

    	//登陆密码加密
		if(!StringUtils.isBlank(t.getUserPwd())){
			user.setUserPwd(new Sha256Hash(t.getUserPwd()).toHex());
		}

		//提现密码加密
		if(!StringUtils.isBlank(t.getCashPwd())){
			user.setCashPwd(new Sha256Hash(t.getCashPwd()).toHex());
		}

		user.setUpdateTime(new Date());
		user.setUpdateUserId(t.getId());

        if(service.updateById(user)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }

    /**
     * 用户密码登录
     * @param param
     * @return
     */
	 @ApiOperation(value="用户密码登陆")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "param", value = "用户实体对象", required = true,dataType = "User")
	   })
    @RequestMapping(value="/login",method = RequestMethod.POST)
    public ResultVM login(@RequestBody User param) {

		log.info("<=====用户登陆信息："+JSONObject.toJSONString(param)+"======>");

    	if(param == null){
    		return ResultVM.error(Constants.data_null,"数据为空！");
    	}

    	String msg = checkUser(param);
    	if(msg!=null){
    		ResultVM.error(Constants.user_Illegal,msg);
    	}

    	String loginName = param.getLoginName();
		String pwd = param.getUserPwd();

		//密码加密
		if(!StringUtils.isBlank(pwd)){
			pwd = new Sha256Hash(pwd).toHex();
		}

		User user = new User();
		user.setLoginName(loginName);
		user.setUserPwd(pwd);

		User newUser = service.selectOne(new EntityWrapper<User>(user));

    	if(newUser!=null){
			//修改登录来源
			newUser.setLoginSource(param.getLoginSource());
			service.updateById(newUser);
    		return ResultVM.ok(newUser);
    	}else{
    		return ResultVM.error(Constants.code_user_noExist,"用户名或密码错误！");
    	}

    }

    /**
     * 微信登陆(openId)
     * @param param
     * @return
     */
	 @ApiOperation(value="用户微信登陆")
	   @ApiImplicitParams({
		  @ApiImplicitParam(name = "param", value = "用户实体对象", required = true,dataType = "User")
	   })
    @RequestMapping(value="/wxLogin",method = RequestMethod.POST)
    public ResultVM wxLogin(@RequestBody User param) {

		log.info("<=====用户登陆信息："+JSONObject.toJSONString(param)+"======>");

    	if(param == null){
    		return ResultVM.error(Constants.data_null,"数据为空！");
    	}

    	String msg = checkUser(param);
    	if(msg!=null){
    		ResultVM.error(Constants.user_Illegal,msg);
    	}

    	String openId = param.getOpenId();

		User user = new User();
		user.setOpenId(openId);

    	User newUser = service.selectOne(new EntityWrapper<User>(user));

    	if(newUser!=null){
    		return ResultVM.ok(newUser);
    	}else{
    		return ResultVM.error(Constants.code_user_noExist,"用户未绑定！");
    	}

    }

    /**
     * 获取验证码
     * @param json
     * @return
     */
	 @ApiOperation(value="获取验证码")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "json", value = "json数据串,mobile:手机号,type：验证码类型（0-注册,1-添加银行卡" +
				 ",2-微信绑定手机号,3-找回密码）", required = true,dataType = "String")
	   })
    @PostMapping(value="/getIdentifyingCode")
    public ResultVM getCode(@RequestBody JSONObject json) {

    	log.info("<=====发送验证码json:{}========>",json);
    	if(json == null){
    		return ResultVM.error(Constants.data_null,"数据为null");
    	}

    	String mobile = json.getString("mobile");
    	int type = json.getInteger("type");//0-注册,1-添加银行卡，2-绑定微信

        if (!CommonUtils.valudateMobile(mobile)){
        	 return ResultVM.error(Constants.code_mobile_typeError,"手机格式错误");
        }

        try {

        	PropertiesUtils pro = PropertiesUtils.getInstance();
        	String signName = null;   //签名
        	String templateCode = null;  //模板
        	JSONObject smsJson = new JSONObject();
        	String code = String.valueOf(new Random().nextInt(8999) + 1000);
        	smsJson.put("code",code);

	        if(type == Constants.TYPE_CODE_REGISTER){
	        	//注册
	        	signName = pro.getValue("register_signName");
	        	templateCode = pro.getValue("register_templateCode");
	        }else if(type == Constants.TYPE_CODE_ADDCARD){
	        	//添加银行卡
	        	signName = pro.getValue("addcard_signName");
	        	templateCode = pro.getValue("addcard_templateCode");
	        }else if(type == Constants.TYPE_CODE_BIND){
				//绑定微信
				signName = pro.getValue("bindwx_signName");
				templateCode = pro.getValue("bindwx_templateCode");
			}else if(type == Constants.TYPE_CODE_FINDPWD){
				//找回密码
				signName = pro.getValue("findpwd_signName");
				templateCode = pro.getValue("findpwd_templateCode");
			}else{
	        	return ResultVM.error(Constants.code_identifying_typeError,"验证码类型错误");
	        }

	        //增加验证码记录
	        IdentifyingCode identifyingCode = new IdentifyingCode();
        	identifyingCode.setCode(code);
        	identifyingCode.setType(String.valueOf(type));

        	identifyingCode.setMobile(mobile);
        	identifyingCode.setSendTime(new Date());

        	if(identifyingCodeService.insert(identifyingCode)){

        		SendSmsResponse response = SmsUtils.getInstance().sendSms(mobile,signName, templateCode, smsJson.toString());

     	        if("OK".equals(response.getCode())){
     	       		 return ResultVM.ok("验证码发送成功！");
     	       	}else{
     	       		return ResultVM.error(response.getMessage() == null ? "发送验证码异常":response.getMessage());
     	       	}

        	}else{
        		return ResultVM.error("数据库发生错误");
        	}

        } catch (Exception e) {
			return ResultVM.error("验证码发送失败："+e);
		}

    }

    /**
     * 微信绑定手机号
     * @param json
     * @return
     */
	 @ApiOperation(value="绑定手机号")
	   @ApiImplicitParams({
			   @ApiImplicitParam(name = "json", value = "json数据，手机号：phone," +
					   "微信ID：openId,验证码：code,类型：type", required = true,dataType = "JSONObject")
	   })
    @RequestMapping(value="/bindMobile",method = RequestMethod.POST)
    public ResultVM bindMobile(@RequestBody JSONObject json){

		log.info("<=====绑定手机号信息：{}======>",json);

		if(json == null){
			return ResultVM.error(Constants.data_null,"入参数据不能为空！");
		}

    	String userPhone = json.getString("phone");
		String openId = json.getString("openId");
		String code = json.getString("code");
		String type = json.getString("type");

		 //校验验证码
		 String msg = identifyingCodeService.checkIdentifyingCode(userPhone,code,type);

		 if(msg!=null){
			 return ResultVM.error(Constants.code_identifying_Error,msg);
		 }

		//校验openId是否唯一
		User user4openId = new User();
		user4openId.setOpenId(openId);
		User oneUser4openId = service.selectOne(new EntityWrapper<User>(user4openId));

		if(oneUser4openId != null){
			//判断手机号不为空，判断是否相等
			if(!StringUtils.isBlank(oneUser4openId.getUserPhone())){

				if(!oneUser4openId.getUserPhone().equals(userPhone)){
					//不等，则已有绑定，不能更改
					return ResultVM.error(Constants.binded_wxNum_error,"用户微信号已绑定其他手机号！");
				}else{
					//相等，则已绑定过，返回该用户信息
					return ResultVM.ok(oneUser4openId);
				}
			}
		}

		User user = new User();
		user.setLoginName(userPhone);

		User oneUser = service.selectOne(new EntityWrapper<User>(user));

		if(oneUser == null){

			//如果用户不存在，创建用户
			user.setOpenId(openId);
			user.setLoginName(userPhone);
			User newUser = service.insertOneUser(user);

			if(newUser!=null){
	    		return ResultVM.ok(newUser);
	    	}else{
	    		return ResultVM.error("用户注册失败！");
	    	}

		}else{

			//判断用户手机号是否已绑定微信号
			if(!StringUtils.isBlank(oneUser.getOpenId()) && !oneUser.getOpenId().equals(openId)){

				return ResultVM.error(Constants.binded_phone_error,"用户手机号已绑定其他微信！");

			}else{

				oneUser.setOpenId(openId);
				Boolean flag = service.bindMobile(oneUser);

				if(flag){
		    		return ResultVM.ok(oneUser);
		    	}else{
		    		return ResultVM.error("绑定失败");
		    	}
			}
		}

    }

    /**
     * 找回密码
     * @param json
     * @return
	 */
	@ApiOperation(value="找回密码")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "json", value = "json数据，手机号：phone," +
					"验证码：code,类型：type", required = true,dataType = "JSONObject")
	})
	@RequestMapping(value="/findPwd",method = RequestMethod.POST)
	public ResultVM findPwd(@RequestBody JSONObject json){

		log.info("<=====找回密码信息：{}======>",json);

		if(json == null){
			return ResultVM.error(Constants.data_null,"入参数据不能为空！");
		}

		String userPhone = json.getString("phone");
		String code = json.getString("code");
		String type = json.getString("type");

		//校验验证码
		String msg = identifyingCodeService.checkIdentifyingCode(userPhone,code,type);

		if(msg!=null){
			return ResultVM.error(Constants.code_identifying_Error,msg);
		}

		User user = new User();
		user.setLoginName(userPhone);

		User oneUser = service.selectOne(new EntityWrapper<User>(user));

		if(oneUser == null){
			return ResultVM.error(Constants.code_user_noExist,"用户不存在！");
		}else{
			return ResultVM.ok(oneUser);
		}

	}

	@Override
	public ResultVM getSmartData(SmartPageVM<User> spage) {
		return super.getSmartData(spage);
	}

	/**
     * 修改密码
     * @param data
     * @return
     */
	 @ApiOperation(value="修改密码")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "data", value = "json数据串（包括原密码:pwd，新密码:newPwd，用户Id:id，" +
				 "type:0-登陆/1-提现/2-忘记密码）,手机号:phone", required = true,dataType = "String")
	   })
    @RequestMapping(value="/changePwd",method = RequestMethod.POST)
    public ResultVM changePwd(@RequestBody String data) {

		JSONObject json = CommonUtils.parse2Json(data);
		log.info("<=====修改密码信息：{}======>",json);

		if(json==null){
			 return ResultVM.error(Constants.data_null,"数据不能为空");
		}

		String type = json.getString("type");
		String phone = json.getString("phone");
		String userId = json.getString("id");

		User user = null;
		if(Constants.pwd_forget.equals(type)){
			//忘记密码，用手机号查询
			User newUser = new User();
			newUser.setLoginName(phone);
			user = service.selectOne(new EntityWrapper<User>(newUser));
		}else{
			//修改密码，用id查询
			user = service.selectById(userId);
		}

		if(user == null){
			return ResultVM.error(Constants.code_user_noExist,"用户不存在");
		}

		String newPwd = json.getString("newPwd");
		String pwd = json.getString("pwd");

		if(StringUtils.isBlank(newPwd)){
			return ResultVM.error(Constants.data_null,"新密码不能为空");
		}

		//判断修改的是登陆密码还是提现密码
		if(Constants.pwd_login.equals(type)){
			//校验原密码是否相同
			if(StringUtils.isBlank(pwd) ||
					!new Sha256Hash(pwd).toHex().equals(user.getUserPwd())){
				return ResultVM.error(Constants.error_pwd,"原密码输入错误，请重新输入！");
			}
			user.setUserPwd(new Sha256Hash(newPwd).toHex());

		}else if(Constants.pwd_cash.equals(type)){
			//校验原密码是否相同
			if(StringUtils.isBlank(pwd) ||
					!new Sha256Hash(pwd).toHex().equals(user.getCashPwd())){
				return ResultVM.error(Constants.error_pwd,"原密码输入错误，请重新输入！");
			}
			user.setCashPwd(new Sha256Hash(newPwd).toHex());

		}else if(Constants.pwd_forget.equals(type)){
			//忘记密码不需要校验原密码
			user.setCashPwd(new Sha256Hash(newPwd).toHex());
		}else{
			return ResultVM.error(Constants.data_null,"类型不能为空");
		}

		user.setUpdateTime(new Date());
		user.setUpdateUserId(userId);

		if(service.updateById(user)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }

    /**
     * 校验用户是否可用
     * @param param
     * @return
     */
    private String checkUser(User param){

    	String msg = null;
    	String userStatus = param.getUserStatus();

    	if(StringUtils.isBlank(userStatus) || Constants.userStatus_notOk.equals(userStatus)){
    		msg = "用户状态不可用！";
    	}

    	return msg;
    }

}