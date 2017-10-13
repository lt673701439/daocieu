package com.liketry.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.RecDis;

public interface RecDisMapper extends BaseMapper<RecDis>{

	public BigDecimal findAllRecOrDis(@Param(value="commdityId")String commdityId,@Param(value="type")String type);
	
	public BigDecimal findAllNewRecOrDis(@Param(value="commdityId")String commdityId,@Param(value="type")String type);
	
}