package com.liketry.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableName;

import lombok.Data;
import lombok.EqualsAndHashCode;

@TableName("bu_divide_rule")
@Data
@EqualsAndHashCode(callSuper = false)
public class DivideRule extends BaseModel<DivideRule>{
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.id
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private String id;
	
	private String ruleCode;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.type
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private String type;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.commodity_id
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	
	private BigDecimal upperLimit;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.is_upper
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private int isUpper;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.lower_limit
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private BigDecimal lowerLimit;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.is_lower
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private int isLower;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.ratio
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private BigDecimal ratio;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.price
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private BigDecimal price;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.create_user_id
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private String createUserId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.create_time
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.update_user_id
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private String updateUserId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.update_time
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private Date updateTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.version
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private Integer version;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column bu_divide_rule.delflag
	 * @mbggenerated  Mon Aug 28 12:31:56 CST 2017
	 */
	private String delflag;

	
}