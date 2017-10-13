package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.Censor;
import com.liketry.mapper.CensorMapper;
import com.liketry.service.CensorService;
import org.springframework.stereotype.Service;

@Service
public class CensorServiceImpl extends ServiceImpl<CensorMapper, Censor> implements CensorService {
}