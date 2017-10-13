package com.liketry.interaction.benison.vo.api;

/**
 * 活动商品模型
 */
public class PromotionCommodityVo extends CommodityVo {
    private String detailId;//详情id
    private String promotionId;//活动id
    private Float discountRatio;//折扣
    private String screenName;//屏幕名称
    private String screenLocation;//屏幕地址
    private Boolean promotionLimit;//限制销售数量

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public Float getDiscountRatio() {
        return discountRatio;
    }

    public void setDiscountRatio(Float discountRatio) {
        this.discountRatio = discountRatio;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenLocation() {
        return screenLocation;
    }

    public void setScreenLocation(String screenLocation) {
        this.screenLocation = screenLocation;
    }

    public Boolean getPromotionLimit() {
        return promotionLimit;
    }

    public void setPromotionLimit(Boolean promotionLimit) {
        this.promotionLimit = promotionLimit;
    }
}