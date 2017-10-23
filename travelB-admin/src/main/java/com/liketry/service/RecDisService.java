package com.liketry.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.Merchant;
import com.liketry.domain.RecDis;
import com.liketry.web.vm.RecDisVM;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

/**
 * author pengyy
 */
@Service
public interface RecDisService extends IService<RecDis> {
	
	/**
	 * 更新商户余额并新增收付单记录
	 * @param json 
	 * @param price 分成金额
	 * @param merchant 商户
	 * @return
	 */
	public Map<String,Object> insertRecAndOrder(JSONObject json,BigDecimal price,Merchant merchant);
	
	/**
	 * 计算可提现余额(七个自然日以前的收款单和付款单商户收入金额差)
	 * @param commdityId
	 * @return
	 */
	public BigDecimal findAllRecAndDis(String commdityId);
	
	/**
	 * 该商户所有收款单或付款单金额之和
	 * @param commdityId 商户ID
	 * @param type 收付单类型
	 * @return
	 */
	public BigDecimal findAllRecOrDis(String commdityId,String type);
	
	/**
	 * 更新商户余额并更新收付单
	 * @param recDis
	 * @return
	 */
	public Map<String,Object> updateRecAndMerchant(RecDis recDis);

	/**
	 * 查询收付单及银行卡信息
	 * @param id
	 * @return
	 */
	RecDisVM selectNewOne(String id);
}
