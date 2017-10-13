package com.liketry.interaction.benison.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
 * 缓存类
 * 
 * @author wuke
 */
public class CacheUtils {
	
	private static final Logger log = LoggerFactory.getLogger(CacheUtils.class);
	
	/**
	 * 商品库存的缓存 
	 * MAP的KEY是商品ID, 值 0：代表此商品库存未锁定；值1：代表此商品库存已被锁定。
	 */
	private static Map<String, String> stockMapCache = new HashMap<String, String>();
 
	/**
	 * 锁定库存   true为锁定成功，false为锁定失败
	 * @param commodityIdList
	 * @return
	 */
	public static boolean lockStockMapCache(List<String> commodityIdList){
		
		// 已锁定的商品ID列表
		List<String> lockedList = new ArrayList<String>();
		
		// 如有锁定失败，则释放已锁定的ID
		for(String commodityId : commodityIdList){
			
			// 缓存中不含此ID，或者值为“0”，则锁定
			if(CacheUtils.stockMapCache.get(commodityId) == null || "0".equals(CacheUtils.stockMapCache.get(commodityId))){
				// 锁定成功
				CacheUtils.stockMapCache.put(commodityId,"1");
				lockedList.add(commodityId);
				
			}else {
				// 等待0.5秒继续查询缓存值，6次，共等待3秒
				// 等待次数
				int waitNum = 6;
				for(int i=0; i<waitNum; i++){
					
					// 等待
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// 锁定成功
					if("0".equals(CacheUtils.stockMapCache.get(commodityId))){
						CacheUtils.stockMapCache.put(commodityId,"1");
						lockedList.add(commodityId);
					}
				}
				
				// 锁定失败
				// 释放已锁定的ID
				for(String lockedId : lockedList){
					CacheUtils.stockMapCache.put(lockedId,"0");
				}
				
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 释放库存
	 * @param commodityIdList
	 * @return
	 */
	public static void releaseStockMapCache(List<String> commodityIdList){
		for(String commodityId : commodityIdList){
			if(CacheUtils.stockMapCache.get(commodityId) != null && "1".equals(CacheUtils.stockMapCache.get(commodityId))){
				CacheUtils.stockMapCache.put(commodityId,"0");
			}
		}
	}
}
