package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.CouponType;

public interface CouponTypeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_coupon_type
     *
     * @mbggenerated Tue Oct 17 20:22:47 CST 2017
     */
    int deleteByPrimaryKey(String couponTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_coupon_type
     *
     * @mbggenerated Tue Oct 17 20:22:47 CST 2017
     */
    int insert(CouponType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_coupon_type
     *
     * @mbggenerated Tue Oct 17 20:22:47 CST 2017
     */
    int insertSelective(CouponType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_coupon_type
     *
     * @mbggenerated Tue Oct 17 20:22:47 CST 2017
     */
    CouponType selectByPrimaryKey(String couponTypeId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_coupon_type
     *
     * @mbggenerated Tue Oct 17 20:22:47 CST 2017
     */
    int updateByPrimaryKeySelective(CouponType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table bu_coupon_type
     *
     * @mbggenerated Tue Oct 17 20:22:47 CST 2017
     */
    int updateByPrimaryKey(CouponType record);
}