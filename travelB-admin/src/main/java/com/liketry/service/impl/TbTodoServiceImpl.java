package com.liketry.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.TbTodo;
import com.liketry.mapper.TbTodoMapper;
import com.liketry.service.TbTodoService;
import org.springframework.stereotype.Service;

/**
 * Created by liketry
 */
@Service
public class TbTodoServiceImpl extends ServiceImpl<TbTodoMapper,TbTodo> implements TbTodoService {
}
