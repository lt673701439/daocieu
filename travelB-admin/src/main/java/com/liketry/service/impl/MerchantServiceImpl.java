package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.Merchant;
import com.liketry.mapper.MerchantMapper;
import com.liketry.service.MerChantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author Simon
 * create 2017/8/28
 * 商户
 */
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerChantService {
    private final MerchantMapper merchantMapper;

    @Autowired
    public MerchantServiceImpl(MerchantMapper merchantMapper) {
        this.merchantMapper = merchantMapper;
    }
}