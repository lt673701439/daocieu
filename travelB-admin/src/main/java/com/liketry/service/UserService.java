package com.liketry.service;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.User;

/**
 * Created by pengyy
 */
public interface UserService extends IService<User> {
	
	public User insertOneUser(User entity);
	
	public Boolean bindMobile(User str);
	
}
