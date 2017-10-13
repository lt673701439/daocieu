package com.liketry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.OrderRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;

public interface OrderRecordMapper extends BaseMapper<OrderRecord> {

    //获取特定商户，指定时间段内，营业收入
    float generalIncome(@Param("merchantId") String merchantId, @Param("start") String start, @Param("end") String end);
}