package com.liketry.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.Merchant;
import com.liketry.domain.OrderRecord;
import com.liketry.domain.RecDis;
import com.liketry.mapper.MerchantMapper;
import com.liketry.mapper.OrderRecordMapper;
import com.liketry.mapper.RecDisMapper;
import com.liketry.service.RecDisService;
import com.liketry.util.Constants;

/**
 * author pengyy
 */
@Service
public class RecDisServiceImpl extends ServiceImpl<RecDisMapper, RecDis> implements RecDisService {
	
	@Autowired
	private MerchantMapper merchantMapper;
	
	@Autowired
	private OrderRecordMapper orderRecordMapper;
	
	@Override
	@Transactional
	public Map<String,Object> insertRecAndOrder(JSONObject json,BigDecimal price,Merchant merchant){
		
		Map<String,Object> map = new HashMap<String,Object>();
		Date date = new Date();
		
		//更新商户余额
		merchant.setMerchantBalance(price.add(merchant.getMerchantBalance()));
		merchant.setUpdateTime(date);
		merchant.setUpdateUserId(json.getString("userId"));
		int count = merchantMapper.updateById(merchant);
		
		//更新订账单商户的收入或退款金额
		if(StringUtils.isNotBlank(json.getString("bookAcountId"))){
			
			OrderRecord orderRecord = new OrderRecord();
			orderRecord.setId(json.getString("bookAcountId"));
			if(price.compareTo(new BigDecimal(0))==1){
				orderRecord.setOrMerchantGain(price); //商户的收入金额
			}else if(price.compareTo(new BigDecimal(0))==-1){
				orderRecord.setOrMerchantBackPrice(price.abs());  //商户的退款金额
			}else{
				orderRecord.setOrMerchantGain(price);
				orderRecord.setOrMerchantBackPrice(price);
			}
			orderRecordMapper.updateById(orderRecord);
		}
	
		if(count > 0){
			//新增收付单记录
			RecDis recDis = new RecDis();
			recDis.setId(UUID.randomUUID().toString().replace("-", ""));
			recDis.setRecDisStatus(Constants.rec_dis_status_pass); //审核通过
			recDis.setRecIdsCode(json.getString("code")); //收款单号
			recDis.setRecDisType(Constants.rec_dis_type_gat); //收款单
			recDis.setCommdityId(merchant.getId()); //商户ID
			recDis.setOrderId(json.getString("orderId")); //订单ID
			recDis.setOrderCode(json.getString("orderCode")); //订单编号
			recDis.setRecDisPrice(price); //商户收入金额
			recDis.setRecDisBalance(merchant.getMerchantBalance()); //商户余额
			recDis.setEffectTime(date);//生效时间
			recDis.setTouchTime(date);//制单时间
			recDis.setTouchBy(json.getString("userId"));//制单人
			recDis.setCreateUserId(json.getString("userId"));
			recDis.setCreateTime(date);
			int count2 = baseMapper.insert(recDis);
			
			if(count2 > 0){
				map.put("flag", true);
				map.put("msg",recDis);
			}else{
				map.put("flag", false);
				map.put("msg","新增收付单失败");
			}
		}else{
			map.put("flag", false);
			map.put("msg","更新商户失败");
		}
		return map;
	}
	
	/**
	 * 计算可提现余额(七个自然日以前的收款单和付款单商户收入金额差)
	 * @param commdityId
	 * @return
	 */
	public BigDecimal findAllRecAndDis(String commdityId){
		
		//七天以前的收款单金额和
		BigDecimal recPrice = baseMapper.findAllRecOrDis(commdityId,Constants.rec_dis_type_gat);
		
		//七天以前的付款单金额和
		BigDecimal disPrice = baseMapper.findAllRecOrDis(commdityId,Constants.rec_dis_type_pay);
		
		return recPrice.subtract(disPrice);
	}

	@Override
	@Transactional
	public Map<String, Object> updateRecAndMerchant(RecDis recDis) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		//更新收付单信息
		int count = baseMapper.updateById(recDis);
		if(count > 0 ){
			
			//更新商户余额
			String commdityId = recDis.getCommdityId();
			Merchant merChant = merchantMapper.selectById(commdityId);
			
			if(merChant != null){
				
				merChant.setMerchantBalance(merChant.getMerchantBalance().subtract(recDis.getRecDisPrice()));
				merChant.setUpdateTime(recDis.getUpdateTime());
				merChant.setUpdateUserId(recDis.getUpdateUserId());
				int count2 = merchantMapper.updateById(merChant);
				if(count2>0){
					map.put("flag", true);
					map.put("msg", "确认成功！");
				}else{
					map.put("flag", false);
					map.put("msg", "更新商户余额失败！");
				}
			}else{
				map.put("flag", false);
				map.put("msg", "该商户不存在！");
			}
		}else{
			map.put("flag", false);
			map.put("msg", "更新收付单信息失败！");
		} 
		return map;
	}
	
	/**
	 * 该商户所有收款单或付款单金额之和
	 * @param commdityId 商户ID
	 * @param status 收付单类型
	 * @return
	 */
	public BigDecimal findAllRecOrDis(String commdityId,String type){
		return baseMapper.findAllNewRecOrDis(commdityId,type);
	}
	
}
