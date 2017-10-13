package com.liketry.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.SysRole;
import com.liketry.domain.SysRoleMenu;
import com.liketry.mapper.SysRoleMapper;
import com.liketry.mapper.SysRoleMenuMapper;
import com.liketry.mapper.SysUserRoleMapper;
import com.liketry.service.SysRoleService;
import com.liketry.web.vm.JsTreeVM;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liketry
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper,SysRole> implements SysRoleService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Override
    public List<SysRole> getList(String userId) {
        List<SysRole> roleList = selectList(new EntityWrapper<SysRole>());
        if (StringUtils.isNotBlank(userId)){
            List<String> list = sysUserRoleMapper.selectRoleIdsByUserId(userId);
            for (SysRole role: roleList){
                if(list.contains(role.getId())){
                    role.setChecked(true);
                }
            }
        }
        return roleList;
    }

    @Override
    public boolean insert(SysRole entity) {
        boolean flag = super.insert(entity);
        for (JsTreeVM jsTreeVM : entity.getMenuTree()){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(entity.getId());
            sysRoleMenu.setMenuId(jsTreeVM.getId());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
        return flag;
    }

    @Override
    public boolean updateById(SysRole entity) {
        boolean flag = super.updateById(entity);
        SysRoleMenu roleMenu = new SysRoleMenu();
        roleMenu.setRoleId(entity.getId());
        sysRoleMenuMapper.delete(new EntityWrapper<SysRoleMenu>(roleMenu));
        for (JsTreeVM jsTreeVM : entity.getMenuTree()){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(entity.getId());
            sysRoleMenu.setMenuId(jsTreeVM.getId());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
        return flag;
    }
}
