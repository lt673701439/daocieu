package com.liketry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.RecDis;
import com.liketry.web.vm.RecDisVM;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

public interface RecDisMapper extends BaseMapper<RecDis>{

	public BigDecimal findAllRecOrDis(@Param(value="commdityId")String commdityId,@Param(value="type")String type);
	
	public BigDecimal findAllNewRecOrDis(@Param(value="commdityId")String commdityId,@Param(value="type")String type);

	RecDisVM selectNewOne(@Param(value="id") String id);
}