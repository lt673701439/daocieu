package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.Merchant;
import com.liketry.mapper.MerchantMapper;
import com.liketry.service.MerChantService;
import com.liketry.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * author Simon
 * create 2017/8/28
 * 商户
 */
@Slf4j
@Service
public class MerchantServiceImpl extends ServiceImpl<MerchantMapper, Merchant> implements MerChantService {
    private final MerchantMapper merchantMapper;

    @Autowired
    public MerchantServiceImpl(MerchantMapper merchantMapper) {

        this.merchantMapper = merchantMapper;
    }

    //根据商户id返回最后登录的手机
    @Override
    public String selectByDevice(String merchantId) {
        return merchantMapper.selectByDevice(merchantId);
    }

    @Override
    public String[] selectByMobile(boolean receiveAll, String[] selectMerchant) {
        List<String> mobiles;
        if (receiveAll) {
            mobiles = merchantMapper.selectAllMobile();
        } else {
            mobiles = new ArrayList<>();
            for (String id : selectMerchant)
                mobiles.add(merchantMapper.selectByMobile(id));
        }
        int size = mobiles.size() / Constants.MAX_SMS + (mobiles.size() % Constants.MAX_SMS > 0 ? 1 : 0);
        log.info("send sms message: size= " + size);
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            List<String> item;
            if (i < size - 1)
                item = mobiles.subList(i * Constants.MAX_ALIAS, (i + 1) * Constants.MAX_ALIAS);
            else
                item = mobiles.subList(i * Constants.MAX_ALIAS, mobiles.size());
            result[i] = StringUtils.join(item, ",");
        }
        return result;
    }
}