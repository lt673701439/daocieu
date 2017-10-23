package com.liketry.service;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.OrderRecord;
import org.springframework.stereotype.Service;

/**
 * author Simon
 * create 2017/8/30
 * 订单
 */
@Service
public interface OrderRecordService extends IService<OrderRecord> {

    //获取收支总和
    float generalIncome(String merchantId, String startTime, String endTime);
}
