package com.liketry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.TbDict;

import java.util.List;


/**
 * Created by liketry
 */
public interface TbDictMapper extends BaseMapper<TbDict> {

    /**
     * 根据分类编号查询字典数据
     * @param classCode
     * @return
     */
    List<TbDict> selectByClassCode(String classCode);
}
