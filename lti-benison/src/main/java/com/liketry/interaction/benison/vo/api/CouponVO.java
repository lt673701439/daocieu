package com.liketry.interaction.benison.vo.api;

import java.math.BigDecimal;

import com.liketry.interaction.benison.model.Coupon;

/**
 *祝福语信息
 *
 *@author Simon
 */
public class CouponVO extends Coupon{

	private String couponTypeCode;

    private String couponName;

    private String couponType;

    private String validType;

    private String publishType;

    private BigDecimal specialOffer;

    private BigDecimal discount;

    private BigDecimal deduction;
    
    private String newValidDate;
    
    private String newPublishDate;
    
	public String getCouponTypeCode() {
		return couponTypeCode;
	}

	public void setCouponTypeCode(String couponTypeCode) {
		this.couponTypeCode = couponTypeCode;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}

	public String getPublishType() {
		return publishType;
	}

	public void setPublishType(String publishType) {
		this.publishType = publishType;
	}

	public BigDecimal getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(BigDecimal specialOffer) {
		this.specialOffer = specialOffer;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDeduction() {
		return deduction;
	}

	public void setDeduction(BigDecimal deduction) {
		this.deduction = deduction;
	}

	public String getNewValidDate() {
		return newValidDate;
	}

	public void setNewValidDate(String newValidDate) {
		this.newValidDate = newValidDate;
	}

	public String getNewPublishDate() {
		return newPublishDate;
	}

	public void setNewPublishDate(String newPublishDate) {
		this.newPublishDate = newPublishDate;
	}
    
}
