package com.liketry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.Merchant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantMapper extends BaseMapper<Merchant> {}