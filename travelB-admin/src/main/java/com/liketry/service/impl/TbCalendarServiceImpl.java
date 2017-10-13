package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.TbCalendar;
import com.liketry.mapper.TbCalendarMapper;
import com.liketry.service.TbCalendarService;
import org.springframework.stereotype.Service;

/**
 * Created by liketry
 */
@Service
public class TbCalendarServiceImpl extends ServiceImpl<TbCalendarMapper,TbCalendar> implements TbCalendarService {
}
