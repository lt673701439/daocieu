package com.liketry.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.SysMenu;
import com.liketry.domain.TbDict;
import com.liketry.mapper.TbDictMapper;
import com.liketry.service.TbDictService;
import com.liketry.web.vm.JsTreeVM;
import com.liketry.web.vm.TreeStateVM;

/**
 * Created by liketry
 */
@Service
public class TbDictServiceImpl extends ServiceImpl<TbDictMapper,TbDict> implements TbDictService {
    @Override
    public List<TbDict> findByClassCode(String classCode) {
        return baseMapper.selectByClassCode(classCode);
    }

	@Override
	public List<JsTreeVM> findDictTreeByClassCode(String id) {
		
		List<JsTreeVM> treeVoList = new ArrayList<JsTreeVM>();
        EntityWrapper<TbDict> wrapper = new EntityWrapper<TbDict>();
        wrapper.orderBy("tb_dict.sort");
        wrapper.in("dict_class_id", id);
        
        List<TbDict> tbDictList = selectList(wrapper);
        
        for (TbDict tbDict : tbDictList) {
            JsTreeVM jsTreeVM = new JsTreeVM();
            jsTreeVM.setId(tbDict.getId());
            jsTreeVM.setParent(StringUtils.isBlank(tbDict.getParentId())?"#":tbDict.getParentId());
            jsTreeVM.setText(tbDict.getText());
            jsTreeVM.setCode(tbDict.getCode());
            //树的状态
//            TreeStateVM state = new TreeStateVM();
//            jsTreeVM.setState(state);
            treeVoList.add(jsTreeVM);
        }
        
        return treeVoList;
	}
	
}
