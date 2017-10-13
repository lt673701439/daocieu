package com.liketry.interaction.benison.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.liketry.interaction.benison.model.User;

public interface UserMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_user
     *
     * @mbggenerated Wed Jul 19 17:59:30 CST 2017
     */
    int deleteByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_user
     *
     * @mbggenerated Wed Jul 19 17:59:30 CST 2017
     */
    int insert(User record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_user
     *
     * @mbggenerated Wed Jul 19 17:59:30 CST 2017
     */
    int insertSelective(User record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_user
     *
     * @mbggenerated Wed Jul 19 17:59:30 CST 2017
     */
    User selectByPrimaryKey(String userId);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_user
     *
     * @mbggenerated Wed Jul 19 17:59:30 CST 2017
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table bu_user
     *
     * @mbggenerated Wed Jul 19 17:59:30 CST 2017
     */
    int updateByPrimaryKey(User record);

    /**
     * 查询用户列表
     *
     * @return
     */
    List<User> findUserList(Map<String, Object> param);

    /**
     * 根据openId查询�?条用户信�?
     *
     * @return
     */
    User findOneByOpenId(@Param(value = "openId") String openId);

    /**
     * 根据用户电话查询用户
     *
     * @param userPhone
     * @return
     */
    User selectUserByUserPhone(@Param("userPhone") String userPhone);

    /**
     * 根据用户openid，查询用户
     *
     * @param openId
     * @return 用户信息
     */
    User selectUserByOpenId(@Param("openId") String openId);

    /**
     * 更改用户资料
     */
    int updateUser(@Param("userId") String userId,@Param("nick") String nick,@Param("sex") String sex,@Param("province") String province,@Param("city") String city,@Param("icon") String icon);
}