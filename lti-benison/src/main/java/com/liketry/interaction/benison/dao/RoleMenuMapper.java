package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.RoleMenu;

public interface RoleMenuMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_role_menu
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int deleteByPrimaryKey(String relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_role_menu
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int insert(RoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_role_menu
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int insertSelective(RoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_role_menu
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    RoleMenu selectByPrimaryKey(String relationId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_role_menu
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int updateByPrimaryKeySelective(RoleMenu record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_role_menu
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int updateByPrimaryKey(RoleMenu record);
}