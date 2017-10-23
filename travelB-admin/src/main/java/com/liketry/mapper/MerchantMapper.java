package com.liketry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MerchantMapper extends BaseMapper<Merchant> {
    //根据商户id返回设备标识
    String selectByDevice(@Param("merchantId") String merchantId);

    //获取所有正常商户电话
    List<String> selectAllMobile();

    //根据商户id返回商户电话
    String selectByMobile(@Param("merchantId") String merchantId);
}