package com.liketry.web;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.liketry.domain.TbDict;
import com.liketry.service.TbDictService;
import com.liketry.web.vm.JsTreeVM;
import com.liketry.web.vm.ResultVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by liketry
 */
@Api(value="字典内容服务",description="供平台管理调用")
@RestController
@RequestMapping("/sys/dict")
public class TbDictController extends BaseController<TbDictService, TbDict> {

    /**
     * 根据分类编号获取字典
     * @param code
     * @return
     */
    @GetMapping("/getlist/{code}")
    public ResultVM getList(@PathVariable String code) {
        List<TbDict> list = service.findByClassCode(code);
        return ResultVM.ok(list);
    }
    
    /**
     * 根据分类编号获取字典树
     * @param code
     * @return
     */
    @ApiOperation(value="根据分类编号获取字典树")
    @ApiImplicitParams({
   	 @ApiImplicitParam(name = "id", value = "字典id", required = true,dataType = "String",paramType="query")
    })
    @GetMapping("/getDictTree")
    public ResultVM getDictTree(String id) {
        List<JsTreeVM> list = service.findDictTreeByClassCode(id);
        return ResultVM.ok(list);
    }
    
}
