package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.BankCard;
import com.liketry.mapper.BankCardMapper;
import com.liketry.service.BankCardService;
import org.springframework.stereotype.Service;

/**
 * author Simon
 * create 2017/8/30
 * 银行卡
 */
@Service
public class BankCardServiceImpl extends ServiceImpl<BankCardMapper, BankCard> implements BankCardService {
}
