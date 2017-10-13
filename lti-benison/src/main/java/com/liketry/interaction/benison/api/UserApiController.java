package com.liketry.interaction.benison.api;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.IdentifyingCode;
import com.liketry.interaction.benison.model.User;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.IdentifyingCodeService;
import com.liketry.interaction.benison.service.UserService;
import com.liketry.interaction.benison.util.MobileUtils;
import com.liketry.interaction.benison.util.PropertiesUtils;
import com.liketry.interaction.benison.util.SmsUtils;
import com.liketry.interaction.benison.util.StringUtils;
import com.liketry.interaction.benison.util.UserUtils;

/**
 * 用户APIController
 *
 * @author Simon
 */
@RestController
@RequestMapping("user_api")
public class UserApiController {
	
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);
    private final UserService service;

    @Autowired
    public UserApiController(UserService service) {
        this.service = service;
    }
    
    @Autowired
    IdentifyingCodeService identifyingCodeService;

    /**
     * author Simon
     * create 2017/8/16
     * 上传资料
     */
    @RequestMapping(value = "update")
    Result<Boolean> update(@RequestParam String data) {
        JSONObject json = UserUtils.decrypt2Str(data);
        User user = JSONObject.toJavaObject(json, User.class);
        int status = service.updateUser(user);
        return new Result<>(SystemConstants.RESULT_SUCCESS, null, status == 1);
    }
    
    /**
     * 获取验证码
     * @param json
     * @return
     */
    @GetMapping(value="/getIdentifyingCode")
    public Result<String> getCode(String data) {
    	
    	JSONObject json = UserUtils.decrypt2Str(data);

    	log.info("<=====发送验证码json:{}========>",json);
    	
    	if(json == null){
    		return new Result<String>(SystemConstants.RESULT_FALSE,"数据不能为空");
    	}

    	String mobile = json.getString("mobile");
    	int type = json.getInteger("type");// 1-绑定手机号

        if (!MobileUtils.valudateMobile(mobile)){
        	return new Result<String>(SystemConstants.RESULT_FALSE,"手机格式错误,请重新输入手机号!");
        }

        try {

        	PropertiesUtils pro = PropertiesUtils.getInstance();
        	String signName = null;   // 签名
        	String templateCode = null;  // 模板
        	JSONObject smsJson = new JSONObject();
        	String code = String.valueOf(new Random().nextInt(8999) + 1000);
        	smsJson.put("code",code);

	        if(type == SystemConstants.CODE_BIND){
	        	//绑定手机号
	        	signName = pro.getValue("bind_signName");
	        	templateCode = pro.getValue("bind_templateCode");
	        }else{
	        	return new Result<String>(SystemConstants.RESULT_FALSE,"验证码类型错误");
	        }

	        //增加验证码记录
	        IdentifyingCode identifyingCode = new IdentifyingCode();
	        identifyingCode.setId(UUID.randomUUID().toString().replace("-", ""));
        	identifyingCode.setCode(code);
        	identifyingCode.setType(String.valueOf(type));
        	identifyingCode.setMobile(mobile);
        	identifyingCode.setSendTime(new Date());
        	identifyingCode.setCreateTime(UserUtils.getCurrentDate());
        	
        	if(identifyingCodeService.insert(identifyingCode)){

        		SendSmsResponse response = SmsUtils.getInstance().sendSms(mobile,signName, templateCode, smsJson.toString());

     	        if("OK".equals(response.getCode())){
     	       		return new Result<String>(SystemConstants.RESULT_SUCCESS,"验证码发送成功！",null);
     	       	}else{
     	       		return new Result<String>(SystemConstants.RESULT_FALSE,response.getMessage() == null ? "发送验证码异常":response.getMessage());
     	       	}

        	}else{
        		return new Result<String>(SystemConstants.RESULT_FALSE,"数据库发生错误");
        	}

        } catch (Exception e) {
        	return new Result<String>(SystemConstants.RESULT_FALSE,"验证码发送失败："+e);
		}

    }
    
    /**
     * 微信绑定手机号
     * @param data
     * @return
     */
    @GetMapping(value="/bindMobile")
    public Result<Object> bindMobile(String data){
    	
    	JSONObject json = UserUtils.decrypt2Str(data);

		log.info("<=====绑定手机号信息：{}======>",json);

		String openId = json.getString("openId");
		String userPhone = json.getString("mobile");
		
		//校验openId是否唯一
		User oneUser4openId = service.findUserByOpenId(openId);

		if(oneUser4openId != null){
			
			//判断手机号不为空，判断是否相等
			if(!StringUtils.isEmpty(oneUser4openId.getUserPhone())){

				if(!oneUser4openId.getUserPhone().equals(userPhone)){
					//不等，则已有绑定，不能更改
					return new Result<>(SystemConstants.RESULT_FALSE,"用户微信号已绑定其他手机号！");
				}
			}
		}

		User user = new User();
		user.setUserPhone(userPhone);

		User oneUser = service.findUserByUserPhone(userPhone);

		if(oneUser == null){
			
    		return new Result<>(SystemConstants.RESULT_FALSE,"用户不存在！");
		}else{

			//判断用户手机号是否已绑定微信号
			if(!StringUtils.isEmpty(oneUser.getOpenId()) && !oneUser.getOpenId().equals(openId)){

				return new Result<>(SystemConstants.RESULT_FALSE,"用户手机号已绑定其他微信！");
			}else{

				oneUser.setOpenId(openId);
				int count= service.updateObject(oneUser);

				if(count>0){
		    		return new Result<>(SystemConstants.RESULT_SUCCESS,oneUser);
		    	}else{
		    		return new Result<>(SystemConstants.RESULT_FALSE,"绑定失败");
		    	}
			}
		}

    }


//    /**
//     * 微信小程序获取openid
//     *
//     * @param code 微信code
//     * @return
//     */
//    @RequestMapping(value = "getOid")
//    Result<String> getOpenId(@RequestParam String code) {
//        String param = "appid=" + SystemConstants.WX_APPID + "&secret=" + SystemConstants.WX_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
//        BufferedReader reader = null;
//        StringBuilder builder = new StringBuilder();
//        try {
//            URLConnection connection = new URL("https://api.weixin.qq.com/sns/jscode2session").openConnection();
//            connection.setDoOutput(true);
//            connection.setDoInput(true);
//            PrintWriter out = new PrintWriter(connection.getOutputStream());
//            out.print(param);
//            out.flush();
//            out.close();
//            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
//            String tempLine;
//            while ((tempLine = reader.readLine()) != null) {
//                builder.append(tempLine);
//            }
//            String openid = JSONObject.parseObject(builder.toString()).get("openid").toString();
//
//
//
//
//
//            return new Result<>(SystemConstants.RESULT_SUCCESS, null, openid);
//        } catch (Exception e) {
//            try {
//                if (reader != null)
//                    reader.close();
//            } catch (Exception e1) {
//                log.error("reader close exception");
//            }
//        }
//        return new Result<>(SystemConstants.RESULT_FALSE);
//    }
}