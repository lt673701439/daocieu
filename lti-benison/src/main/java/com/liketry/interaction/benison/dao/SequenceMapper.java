package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.Sequence;

public interface SequenceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_sequence
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int deleteByPrimaryKey(String seqType);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_sequence
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int insert(Sequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_sequence
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int insertSelective(Sequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_sequence
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    Sequence selectByPrimaryKey(String seqType);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_sequence
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int updateByPrimaryKeySelective(Sequence record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bsc_sequence
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int updateByPrimaryKey(Sequence record);
}