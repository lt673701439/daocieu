package com.liketry.web;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.liketry.domain.TbDictClass;
import com.liketry.service.TbDictClassService;
import com.liketry.web.vm.ResultVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liketry
 */
@Api(value="字典服务",description="供平台管理调用")
@RestController
@RequestMapping("/sys/dictclass")
public class TbDictClassController extends BaseController<TbDictClassService, TbDictClass> {

    /**
     * 获取字典分类集合
     * @return
     */
	@ApiOperation(value="获取字典分类集合")
    @GetMapping("/getlist")
    public ResultVM getList() {
        List<TbDictClass> list = service.selectList(new EntityWrapper<TbDictClass>());
        return ResultVM.ok(list);
    }
    
}
