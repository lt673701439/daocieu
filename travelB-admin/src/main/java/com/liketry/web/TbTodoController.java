package com.liketry.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.TbTodo;
import com.liketry.service.TbTodoService;
import com.liketry.util.ShiroUtils;
import com.liketry.web.vm.ResultVM;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liketry
 */
@RestController
@RequestMapping("/sys/todo")
public class TbTodoController extends BaseController<TbTodoService, TbTodo> {

    @GetMapping("/getlist")
    public ResultVM getList() {
        TbTodo tbTodo = new TbTodo();
        tbTodo.setCreateUserId(ShiroUtils.getUserId());
        List<TbTodo> list = service.selectList(new EntityWrapper<TbTodo>(tbTodo));
        return ResultVM.ok(list);
    }
}
