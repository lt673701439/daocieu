package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.User;
import com.liketry.mapper.UserMapper;
import com.liketry.service.UserService;
import com.liketry.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by liketry
 */

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

	/**
	 * 用户注册
	 * entity
	 */
	public User insertOneUser(User entity){
		
		String userId = UUID.randomUUID().toString().replace("-", "");
		
		entity.setId(userId);
		entity.setCreateUserId(userId);
		entity.setUserStatus(Constants.userStatus_ok);//用户状态：0-可用
		
		//密码加密，如果密码为空，给一个6位默认密码
		if(StringUtils.isBlank(entity.getUserPwd())){
			String pwd = String.valueOf((int)((Math.random()*9+1)*100000));
			entity.setUserPwd(new Sha256Hash(pwd).toHex());
		}
		int count = baseMapper.insert(entity);
		
		if(count>0){
			return baseMapper.selectById(userId);
		}else{
			return null;
		}
		
	}
	
	/**
	 * 微信绑定手机号
	 * param entity
	 */
	public Boolean bindMobile(User entity){
		
		int count= baseMapper.updateById(entity);
		
		if(count>0){
			return true;
		}else{
			return false;
		}
	}	
}
