package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.ConfCustom;

public interface ConfCustomMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_conf_custom
	 * @mbggenerated  Mon Aug 14 10:04:37 CST 2017
	 */
	int deleteByPrimaryKey(String customId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_conf_custom
	 * @mbggenerated  Mon Aug 14 10:04:37 CST 2017
	 */
	int insert(ConfCustom record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_conf_custom
	 * @mbggenerated  Mon Aug 14 10:04:37 CST 2017
	 */
	int insertSelective(ConfCustom record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_conf_custom
	 * @mbggenerated  Mon Aug 14 10:04:37 CST 2017
	 */
	ConfCustom selectByPrimaryKey(String customId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_conf_custom
	 * @mbggenerated  Mon Aug 14 10:04:37 CST 2017
	 */
	int updateByPrimaryKeySelective(ConfCustom record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_conf_custom
	 * @mbggenerated  Mon Aug 14 10:04:37 CST 2017
	 */
	int updateByPrimaryKey(ConfCustom record);
}