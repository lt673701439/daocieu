package com.liketry.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.BaseModel;
import com.liketry.util.ShiroUtils;
import com.liketry.util.StringTools;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 通用Controller（增删改查）
 * Created by liketry
 */
@Slf4j
public abstract class BaseController<S extends IService<T>, T extends BaseModel<T>> {

    @Autowired
    protected S service;

    /**
     * 根据smarttable对象分页查询
     * @param spage
     * @return
     */
    @ApiOperation(value="列表查询",notes="通用列表查询")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "spage", value = "SmartPage分页对象", required = true,dataType = "SmartPageVM")
	   })
    @PostMapping("/getSmartData")
    public ResultVM getSmartData(@RequestBody SmartPageVM<T> spage){
    	
    	log.info("<======列表请求参数json:=="+JSONObject.toJSONString(spage)+"=====>");
    	
        Page<T> page = new Page<T>(spage.getPagination().getStart()
                ,spage.getPagination().getNumber());

        if (StringUtils.isBlank(spage.getSort().getPredicate())) {
            spage.getSort().setPredicate("update_time");
        }
        page.setOrderByField(spage.getSort().getPredicate());
        page.setAsc(spage.getSort().getReverse());
        EntityWrapper<T> wrapper = new EntityWrapper<T>();
        if (spage.getSearch()!=null){
            Field[] fields = spage.getSearch().getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                try {
                    fields[i].setAccessible(true);
                    Object value = fields[i].get(spage.getSearch());
                    if (null != value && !value.equals("") && !value.equals(0)) {//默认int型数据值为0
                        String fieldname = StringTools.underscoreName(fields[i].getName());
                        wrapper.like(fieldname,value.toString());
                    }
                    fields[i].setAccessible(false);
                } catch (Exception e) {
                }
            }
        }
        return  ResultVM.ok(service.selectPage(page,wrapper));
    }

    /**
     * 新增
     * @param t
     * @return
     */
    @ApiOperation(value="新增",notes="通用新增")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "t", value = "实体对象", required = true,dataType = "T")
	   })
    @PostMapping
    public ResultVM create(@RequestBody T t) {

        t.setCreateUserId(ShiroUtils.getUserId());
        t.setCreateTime(new Date());
        t.setUpdateTime(new Date());
        t.setUpdateUserId(ShiroUtils.getUserId());
        if(service.insert(t)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }

    /**
     * 更新
     * @param t
     * @return
     */
    @ApiOperation(value="修改",notes="通用修改")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "t", value = "实体对象", required = true,dataType = "T")
	   })
    @PutMapping
    public ResultVM update(@RequestBody T t) {

        t.setUpdateTime(new Date());
        t.setUpdateUserId(ShiroUtils.getUserId());
        if(service.updateById(t)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }

    /**
     * 根据id获取实体对象
     * @param id
     * @return
     */
    @ApiOperation(value="查询详细",notes="通用查询详细")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "id", value = "主键", required = true,dataType = "String",paramType="path")
	   })
    @GetMapping("/{id}")
    public ResultVM getInfo(@PathVariable String id) {
        T t = service.selectById(id);
        if(t != null){
            return ResultVM.ok(t);
        }else{
            return ResultVM.error();
        }
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @ApiOperation(value="删除",notes="通用删除")
	   @ApiImplicitParams({
	  	 @ApiImplicitParam(name = "id", value = "主键", required = true,dataType = "String",paramType="path")
	   })
    @DeleteMapping("/{id}")
    public ResultVM delete(@PathVariable String id) {
        if(service.deleteById(id)){
            return ResultVM.ok();
        }else{
            return ResultVM.error();
        }
    }
}
