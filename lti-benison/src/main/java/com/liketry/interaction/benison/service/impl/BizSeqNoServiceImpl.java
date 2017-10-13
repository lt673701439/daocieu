package com.liketry.interaction.benison.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liketry.interaction.benison.dao.BizSeqnoMapper;
import com.liketry.interaction.benison.model.BizSeqno;
import com.liketry.interaction.benison.service.BizSeqNoService;
import com.liketry.interaction.benison.util.StringUtils;

/**
 * 序列号实现类
 *
 * @author pengyy
 */
@Service
public class BizSeqNoServiceImpl implements BizSeqNoService {
	
	@Autowired
    BizSeqnoMapper bizSeqnoMapper;
	 
	 /**
		 * 获取序列号
		 * @return
		 */
	public String getSeqNoNextId(String code){
		int nexId = 0;
		BizSeqno bizSeqno = new BizSeqno();
		if(!StringUtils.isEmpty(code)){
			bizSeqno =  bizSeqnoMapper.selectByPrimaryKey(code);
			if(bizSeqno!=null){
				if(bizSeqno.getCurrValue()<bizSeqno.getMaxValue()){
					nexId =  bizSeqno.getCurrValue()+bizSeqno.gettIncrement();
					bizSeqno.setCurrValue(nexId);
					bizSeqnoMapper.updateByPrimaryKey(bizSeqno);
				}else{
					throw new IllegalArgumentException("<===序列号超过了最大值=====>");
				}
			}
		}
		return getFianlValue(bizSeqno,nexId);
	}
	
	/**
	 * 鎸夌収瑙勫垯缁勫缓娴佹按鍙�<br/> 
	 * @return String 鎸夎鍒欑粍寤哄畬鎴愭祦姘村彿
	 */
	private String getFianlValue(BizSeqno bizSeqno,int nexId) {

		String seqPrefix = transformNullValue(bizSeqno.getSeqPrefix());
		String seqSuffix = transformNullValue(bizSeqno.getSeqSuffix());
		String placeHolder = transformNullValue(bizSeqno.getPlaceHolder());
		String alignType = transformNullValue(bizSeqno.getAlignType());

		int maxLength = bizSeqno.getMaxLength();
		String currValueStr = String.valueOf(nexId);
		int len = seqPrefix.length() + seqSuffix.length() + currValueStr.length();

		int remain = maxLength - len;
		StringBuffer sb = new StringBuffer();
		sb.append(seqPrefix);
		if ("".equals(alignType) || "L".equalsIgnoreCase(alignType)) {
			for (int i = 0; i < remain; i++) {
				sb.append(placeHolder);
			}
			sb.append(currValueStr);
		} else {
			sb.append(currValueStr);
			for (int i = 0; i < remain; i++) {
				sb.append(placeHolder);
			}
		}
		sb.append(seqSuffix);
		return sb.toString();
	}
	
	public String transformNullValue(String str){
		if(str==null||"null".equalsIgnoreCase(str)){
			return "";
		}
		return str.trim();
	}
}
