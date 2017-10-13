package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.Spot;
import com.liketry.interaction.benison.vo.api.SpotVo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpotMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_spot
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int deleteByPrimaryKey(String spotId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_spot
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int insert(Spot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_spot
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int insertSelective(Spot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_spot
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    Spot selectByPrimaryKey(String spotId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_spot
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int updateByPrimaryKeySelective(Spot record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_spot
     *
     * @mbggenerated Thu May 18 10:32:25 CST 2017
     */
    int updateByPrimaryKey(Spot record);

    //查询所有
    List<Spot> selectAll(String spotStatus);

    //逻辑删除
    int deleteSport(String id);

    //获取非删除的所有景区的基本信息（id，name）
    List<Spot> selectBaseAll();

    //获取api使用的景区信息
    List<SpotVo> selectApiAll();
}