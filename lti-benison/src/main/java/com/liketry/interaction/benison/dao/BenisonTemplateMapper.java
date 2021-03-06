package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.BenisonTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenisonTemplateMapper {
    /**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_benison_template
	 * @mbggenerated  Wed Oct 18 16:23:02 CST 2017
	 */
	int deleteByPrimaryKey(String templateId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_benison_template
	 * @mbggenerated  Wed Oct 18 16:23:02 CST 2017
	 */
	int insert(BenisonTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_benison_template
	 * @mbggenerated  Wed Oct 18 16:23:02 CST 2017
	 */
	int insertSelective(BenisonTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_benison_template
	 * @mbggenerated  Wed Oct 18 16:23:02 CST 2017
	 */
	BenisonTemplate selectByPrimaryKey(String templateId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_benison_template
	 * @mbggenerated  Wed Oct 18 16:23:02 CST 2017
	 */
	int updateByPrimaryKeySelective(BenisonTemplate record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_benison_template
	 * @mbggenerated  Wed Oct 18 16:23:02 CST 2017
	 */
	int updateByPrimaryKey(BenisonTemplate record);

	//根据屏幕id 祝福语类型id和内容id获取模板
    List<BenisonTemplate> selectBenisonTemplate(@Param("screenId") String screenId, @Param("imgId") String imgId, @Param("typeId") String typeId, @Param("benisonId") String benisonId);

    /**
     * 查询模板根据模板Id,结果包括imgUrl和ruleContent
     *
     * @param templateId模板ID
     */
    BenisonTemplate findBenisonTemplateById(String templateId);
}