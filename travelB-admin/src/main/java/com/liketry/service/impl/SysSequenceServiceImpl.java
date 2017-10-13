package com.liketry.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.RecDis;
import com.liketry.domain.SysSequence;
import com.liketry.mapper.RecDisMapper;
import com.liketry.mapper.SysSequenceMapper;
import com.liketry.service.SysSequenceService;
import org.springframework.stereotype.Service;

@Service
public class SysSequenceServiceImpl extends ServiceImpl<SysSequenceMapper, SysSequence> implements SysSequenceService {
}
