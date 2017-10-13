package com.liketry.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.TbDict;
import com.liketry.web.vm.JsTreeVM;


/**
 * Created by liketry
 */
public interface TbDictService extends IService<TbDict> {

    /**
     * 根据分类编号查询字典
     * @param classCode
     * @return
     */
    List<TbDict> findByClassCode(String classCode);
    
    /**
     * 根据分类编号获取字典树
     * @param classCode
     * @return
     */
    List<JsTreeVM> findDictTreeByClassCode(String id);
}
