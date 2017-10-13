/**
 * @author pengyy
 * created at 2017/5/23 18:15
 */
package com.liketry.interaction.benison.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.dao.UserMapper;
import com.liketry.interaction.benison.model.User;
import com.liketry.interaction.benison.service.UserService;

/**
 * 用户service
 * 
 * @author pengyy
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
    @Autowired
    UserMapper userMapper;

    @Override
    public PageInfo<User> findUserList(int pageSize, int pageNumber,Map<String,Object> param) {
        PageHelper.startPage(pageSize, pageNumber);
        List<User> user = userMapper.findUserList(param);
        PageInfo<User> page = new PageInfo<User>(user);
        return page;
    }

	@Override
	public int insertObject(User user) {
		return userMapper.insert(user);
	}

	@Override
	public int updateObject(User user) {
		return userMapper.updateByPrimaryKey(user);
	}

	@Override
	public User findOneByOpenId(String openId) {
		return userMapper.findOneByOpenId(openId);
	}

	@Override
	public User findOneUser(String userId) {
		return userMapper.selectByPrimaryKey(userId);
	}

	@Override
	public User findUserByUserPhone(String phone) {
		return userMapper.selectUserByUserPhone(phone);
	}

	//根据openid查询用户
	@Override
	public User findUserByOpenId(String phone) {
		return userMapper.selectUserByOpenId(phone);
	}

	@Override
	public int updateUser(User user) {
		return userMapper.updateUser(user.getUserId(),user.getUserNickname(),user.getUserSex(),user.getUserProvince(),user.getUserCity(),user.getUserIcon());
	}
}