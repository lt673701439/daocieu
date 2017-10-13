package com.liketry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.liketry.domain.CensorRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CensorRecordMapper extends BaseMapper<CensorRecord> {
    List<CensorRecord> selectByPage(@Param("merchantId") String merchantId,@Param("order")  String order,@Param("orderStatus") String orderStatus);
}