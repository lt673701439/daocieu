package com.liketry.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.TbCalendar;
import com.liketry.service.TbCalendarService;
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
@RequestMapping("/sys/calendar")
public class TbCalendarController extends BaseController<TbCalendarService, TbCalendar> {

    @GetMapping("/getlist")
    public ResultVM getList() {
        TbCalendar calendar = new TbCalendar();
        calendar.setCreateUserId(ShiroUtils.getUserId());
        List<TbCalendar> list = service.selectList(new EntityWrapper<TbCalendar>(calendar));
        return ResultVM.ok(list);
    }
}
