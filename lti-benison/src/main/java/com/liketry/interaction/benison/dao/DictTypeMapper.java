package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.DictType;

public interface DictTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_dict_type
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int deleteByPrimaryKey(String dictTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_dict_type
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int insert(DictType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_dict_type
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int insertSelective(DictType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_dict_type
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    DictType selectByPrimaryKey(String dictTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_dict_type
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int updateByPrimaryKeySelective(DictType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_dict_type
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int updateByPrimaryKey(DictType record);
}