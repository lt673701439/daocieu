package com.liketry.interaction.benison.dao;

import com.liketry.interaction.benison.model.StockDetail;

public interface StockDetailMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_stock_detail
	 * @mbggenerated  Wed May 31 18:10:18 CST 2017
	 */
	int deleteByPrimaryKey(String detailId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_stock_detail
	 * @mbggenerated  Wed May 31 18:10:18 CST 2017
	 */
	int insert(StockDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_stock_detail
	 * @mbggenerated  Wed May 31 18:10:18 CST 2017
	 */
	int insertSelective(StockDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_stock_detail
	 * @mbggenerated  Wed May 31 18:10:18 CST 2017
	 */
	StockDetail selectByPrimaryKey(String detailId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_stock_detail
	 * @mbggenerated  Wed May 31 18:10:18 CST 2017
	 */
	int updateByPrimaryKeySelective(StockDetail record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table bu_stock_detail
	 * @mbggenerated  Wed May 31 18:10:18 CST 2017
	 */
	int updateByPrimaryKey(StockDetail record);
	
	/**
	 * 删除对应的库存明细
	 */
	int deleteStockDetail(StockDetail record);
}