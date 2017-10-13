package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.OrderRecord;
import com.liketry.mapper.OrderRecordMapper;
import com.liketry.mapper.SysMenuMapper;
import com.liketry.service.OrderRecordService;
import org.springframework.stereotype.Service;

/**
 * author Simon
 * create 2017/8/30
 * 订单
 */
@Service
public class OrderRecordServiceImpl extends ServiceImpl<OrderRecordMapper, OrderRecord> implements OrderRecordService {

    @Override
    public float generalIncome(String merchantId, String startTime, String endTime) {
        return baseMapper.generalIncome(merchantId, startTime, endTime);
    }
}