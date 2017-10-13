package com.liketry.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.DivideRule;

/**
 * Created by liketry
 */
public interface DivideRuleService extends IService<DivideRule> {
	
    /**
     * 查询重叠的区间列表
     * @param userId
     * @return
     */
    List<DivideRule> findRepeatLimit(DivideRule t);
    
    
    /**
     * 根据类型和金额，查询出符合的区间范围
     * @param map
     * @return
     */
    List<DivideRule> selectNewListByMap(Map<String,Object> map);
}
