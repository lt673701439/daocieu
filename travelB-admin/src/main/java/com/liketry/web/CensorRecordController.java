package com.liketry.web;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.liketry.domain.CensorRecord;
import com.liketry.service.CensorRecordService;
import com.liketry.util.StringTools;
import com.liketry.web.vm.ResultVM;
import com.liketry.web.vm.SmartPageVM;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

/**
 * author Simon
 * create 2017/9/11
 * 修订记录
 */
@Slf4j
@RestController
@RequestMapping("censorRecord")
public class CensorRecordController extends BaseController<CensorRecordService, CensorRecord> {
    @PostMapping("/getSmartData")
    public ResultVM getSmartData(@RequestBody SmartPageVM<CensorRecord> spage) {
        log.info("<======列表请求参数json:==" + JSONObject.toJSONString(spage) + "=====>");
        Page<CensorRecord> page = new Page<CensorRecord>(spage.getPagination().getStart(), spage.getPagination().getNumber());
        if (StringUtils.isBlank(spage.getSort().getPredicate()))
            spage.getSort().setPredicate("update_time");
        page.setOrderByField(spage.getSort().getPredicate());
        page.setAsc(spage.getSort().getReverse());
        EntityWrapper<CensorRecord> wrapper = new EntityWrapper<CensorRecord>();
        if (spage.getSearch() != null) {
            Field[] fields = spage.getSearch().getClass().getDeclaredFields();
            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    Object value = field.get(spage.getSearch());
                    if (null != value && !value.equals("") && !value.equals(0)) {//默认int型数据值为0
                        String fieldname = StringTools.underscoreName(field.getName());
                        wrapper.like(fieldname, value.toString());
                    }
                    field.setAccessible(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return ResultVM.ok(service.selectPage(page, wrapper));
    }


    @PostMapping("/getData")
    public ResultVM getData(@RequestBody SmartPageVM<CensorRecord> spage) {




        return null;
    }
}
