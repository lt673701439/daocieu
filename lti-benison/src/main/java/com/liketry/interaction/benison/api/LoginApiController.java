package com.liketry.interaction.benison.api;

import com.alibaba.fastjson.JSONObject;
import com.liketry.interaction.benison.constants.SystemConstants;
import com.liketry.interaction.benison.model.User;
import com.liketry.interaction.benison.sdk.Result;
import com.liketry.interaction.benison.service.UserService;
import com.liketry.interaction.benison.util.LogUtils;
import com.liketry.interaction.benison.util.MD5Utils;
import com.liketry.interaction.benison.util.MobileUtils;
import com.liketry.interaction.benison.util.UserUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * author Simon
 * create 2017/7/19
 * 登录
 */
@RestController
@RequestMapping(value = "login_api", method = RequestMethod.POST)
public class LoginApiController {
    private static final Logger log = LoggerFactory.getLogger(UserApiController.class);
    private final int ERROR_MOBILE = 1001;//手机格式错误
    private final int ERROR_CODE = 1002;//验证码类型错误
    private final int ERROR_LOGIN = 1003;//登录类型错误
    private final int ERROR_USER = 1004;//用户已经存在
    private final int ERROR_PASSWORD_LENGTH = 1005;//密码必须大于等于六位
    private final int ERROR_VALUE = 1006;//登录value为空
    private final int ERROR_NOT_USER = 1007;//用户不存在
    private final int ERROR_PASSWORD_ERROR = 1008;//密码错误
    private final int ERROR_REGISTER_ERROR = 1009;//注册失败
    private final int ERROR_ANALYZE_ERROR = 1010;//解析异常

    private final int TYPE_CODE_LOGIN = 1; //1 获取验证码（登录）
    private final int TYPE_CODE_REGISTER = 2;//2 获取验证码（快速注册）

    private final int TYPE_LOGIN_CODE = 1; //1 验证码登录
    private final int TYPE_LOGIN_PASSWORD = 2;//2 密码登录

    private final UserService userService;

    @Autowired
    public LoginApiController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取验证码
     *
     * @param mobile
     * @param type   登录类型(TYPE_CODE_LOGIN=登录  TYPE_CODE_REGISTER=注册)
     * @return
     */
    @RequestMapping("getCode")
    Result<Integer> getCode(@RequestParam String mobile, @RequestParam int type) {
        if (!MobileUtils.valudateMobile(mobile))
            return new Result<>(ERROR_MOBILE, "手机格式错误");
        if (type != TYPE_CODE_LOGIN && type != TYPE_CODE_REGISTER)
            return new Result<>(ERROR_CODE, "验证码类型错误");
        int code = new Random().nextInt(8999) + 1000;
        return new Result<>(SystemConstants.RESULT_SUCCESS, code);
    }

    //快速注册
    @RequestMapping("register")
    Result<User> register(@RequestParam String data) {
        String mobile = null;
        String password = null;
        String code = null;
        try {
            JSONObject json = UserUtils.decrypt2Str(data);
            mobile = json.getString("mobile");
            password = json.getString("password");
            code = json.getString("code");
        } catch (Exception e) {
            return new Result<>(ERROR_ANALYZE_ERROR, "解析异常");
        }

        if (!MobileUtils.valudateMobile(mobile))
            return new Result<>(ERROR_MOBILE, "手机格式错误");
        User user = userService.findUserByUserPhone(mobile);
        if (user != null)
            return new Result<>(ERROR_USER, "用户已经存在");
        if (password == null || password.length() < 6)
            return new Result<>(ERROR_PASSWORD_LENGTH, "密码必须大于等于六位");
        String pwd = MD5Utils.MD5Encode(password, "UTF-8");
        user = new User();
        user.setUserPhone(mobile);
        user.setUserPwd(pwd);
        user.setDelflag("1");
        user.setUserId(UUID.randomUUID().toString().replace("-", ""));
        boolean status = userService.insertObject(user) == 1;
        if (status) {
            User newUser = userService.findUserByUserPhone(mobile);
            if (newUser == null)
                return new Result<>(ERROR_REGISTER_ERROR, "注册失败");
            return new Result<>(SystemConstants.RESULT_SUCCESS, newUser);
        } else {
            return new Result<>(ERROR_REGISTER_ERROR, "注册失败");
        }
    }

    /**
     * 登录
     * <p>
     * type   TYPE_LOGIN_CODE 验证码 TYPE_LOGIN_PASSWORD 密码
     * mobile 电话
     * value  根据type 判断传入的值
     *
     * @return 用户信息
     */
    @RequestMapping("login")
    Result<User> login(@RequestParam String data) {
        int type;
        String mobile;
        String value;
        try {
            JSONObject json = UserUtils.decrypt2Str(data);
            type = json.getInteger("type");
            mobile = json.getString("mobile");
            value = json.getString("value");
        } catch (Exception e) {
            return new Result<>(ERROR_ANALYZE_ERROR, "解析异常");
        }
        if (!MobileUtils.valudateMobile(mobile))
            return new Result<>(ERROR_MOBILE, "手机格式错误");
        if (value == null)
            return new Result<>(ERROR_VALUE, "value为空");
        User user = userService.findUserByUserPhone(mobile);
        if (user == null)
            return new Result<>(ERROR_NOT_USER, "该用户不存在");
        switch (type) {
            case TYPE_LOGIN_CODE:
                user.setUserPwd(null);
                return new Result<>(SystemConstants.RESULT_SUCCESS,null,user,createJwtToken(user.getUserId()));
            case TYPE_LOGIN_PASSWORD:
                String pwd = MD5Utils.MD5Encode(value, "UTF-8");
                if (!pwd.equals(user.getUserPwd()))
                    return new Result<>(ERROR_PASSWORD_ERROR, "密码错误");
                user.setUserPwd(null);
                return new Result<>(SystemConstants.RESULT_SUCCESS,null,user,createJwtToken(user.getUserId()));
            default:
                return new Result<>(ERROR_LOGIN, "验证码类型错误");
        }
    }

    /**
     * 微信小程序获取openid
     *
     * @param code 微信code
     * @return
     */
    @RequestMapping(value = "getOid", method = RequestMethod.GET)
    Result<User> getOpenId(@RequestParam String code) {
        String param = "appid=" + SystemConstants.WX_APPID + "&secret=" + SystemConstants.WX_SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            URLConnection connection = new URL("https://api.weixin.qq.com/sns/jscode2session").openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String tempLine;
            while ((tempLine = reader.readLine()) != null) {
                builder.append(tempLine);
            }
            String openid = JSONObject.parseObject(builder.toString()).get("openid").toString();
            User user = userService.findUserByOpenId(openid);
            if (user != null) {
            	 return new Result<>(SystemConstants.RESULT_SUCCESS, null, user, createJwtToken(user.getUserId()));
            } else {
                user = new User();
                user.setDelflag("1");
                user.setUserId(UUID.randomUUID().toString().replace("-", ""));
                user.setOpenId(openid);
                boolean status = userService.insertObject(user) == 1;
                if (status) {
                    User newUser = userService.findUserByOpenId(openid);
                    if (newUser == null)
                        return new Result<>(ERROR_REGISTER_ERROR, "登录失败");
                    log.info(LogUtils.getLog("WX_LOGIN", code, newUser));
                    return new Result<>(SystemConstants.RESULT_SUCCESS, newUser, createJwtToken(user.getUserId()),0);
                } else {
                    return new Result<>(ERROR_REGISTER_ERROR, "登录失败");
                }
            }
        } catch (Exception e) {
            try {
                if (reader != null)
                    reader.close();
            } catch (Exception e1) {
                log.error("reader close exception");
            }
        }
        return new Result<>(SystemConstants.RESULT_FALSE);
    }
    
    private String createJwtToken(String userId){
    	String jwtToken = Jwts.builder().setSubject(userId).claim("userId", userId).setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "liketrybenison").compact();
    	
    	return jwtToken;
    }
}