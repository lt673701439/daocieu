package com.liketry.mapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.DivideRule;

public interface DivideRuleMapper extends BaseMapper<DivideRule>{

	List<DivideRule> findRepeatLimit(DivideRule t);
	
	List<DivideRule> selectNewListByMap(Map<String, Object> map);
}