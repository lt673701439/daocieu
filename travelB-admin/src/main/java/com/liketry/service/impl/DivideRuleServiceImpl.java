package com.liketry.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.DivideRule;
import com.liketry.mapper.DivideRuleMapper;
import com.liketry.service.DivideRuleService;

/**
 * Created by liketry
 */
@Service
public class DivideRuleServiceImpl extends ServiceImpl<DivideRuleMapper,DivideRule> implements DivideRuleService {

	@Override
	public List<DivideRule> findRepeatLimit(DivideRule t) {
		
		return baseMapper.findRepeatLimit(t);
	}

	@Override
	public List<DivideRule> selectNewListByMap(Map<String, Object> map) {
		List<DivideRule> list = baseMapper.selectNewListByMap(map);
		return list;
	}

}
