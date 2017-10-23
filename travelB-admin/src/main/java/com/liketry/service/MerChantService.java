package com.liketry.service;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.Merchant;
import org.springframework.stereotype.Service;

/**
 * author Simon
 * create 2017/8/28
 * 商户
 */
@Service
public interface MerChantService extends IService<Merchant> {
    //根据商户id返回最后登录的手机
    String selectByDevice(String merchantId);

    //根据商户id返回最后登录的手机 receiveAll为true是所有用户
    String[] selectByMobile(boolean receiveAll, String[] selectMerchant);
}