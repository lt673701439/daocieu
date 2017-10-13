/**
 * @author pengyy
 * created at 2017/5/23 18:09
 */
package com.liketry.interaction.benison.service;

import com.github.pagehelper.PageInfo;
import com.liketry.interaction.benison.model.Spot;
import com.liketry.interaction.benison.model.User;

import java.util.List;
import java.util.Map;


public interface UserService {

    int insertObject(User user);

    PageInfo<User> findUserList(int pageSize, int pageNumber,Map<String,Object> param);

    int updateObject(User user);
    
    User findOneByOpenId(String openId);
    
    User findOneUser(String userId);

    User findUserByUserPhone(String phone);

    User findUserByOpenId(String phone);

    int updateUser(User user);
}