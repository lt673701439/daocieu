package com.liketry.service;

import org.springframework.stereotype.Service;

/**
 * author Simon
 * create 2017/8/30
 * code编码
 */
@Service
public interface CodeService {

    /**
     * author Simon
     * create 2017/8/30
     * 获取商户编码
     */
    String getCTMCode();

    /**
     * author Simon
     * create 2017/8/30
     * 获取订账单编码
     */
    String getBODCode(String merchantCode);

    /**
     * 获取首付款单编码
     *
     * @param merchantCode 商户编号
     * @param isRCP        是否是收款
     * @return 编码
     */
    String getBillCode(String merchantCode, boolean isRCP);
}
