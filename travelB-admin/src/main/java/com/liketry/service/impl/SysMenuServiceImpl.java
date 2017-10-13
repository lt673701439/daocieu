package com.liketry.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.SysMenu;
import com.liketry.domain.SysRoleMenu;
import com.liketry.mapper.SysMenuMapper;
import com.liketry.mapper.SysRoleMenuMapper;
import com.liketry.service.SysMenuService;
import com.liketry.util.MenuTreeUtil;
import com.liketry.web.vm.JsTreeVM;
import com.liketry.web.vm.MenuVM;
import com.liketry.web.vm.TreeStateVM;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by liketry
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<JsTreeVM> getMenuTree(String roleId) {
        List<JsTreeVM> treeVoList = new ArrayList<JsTreeVM>();
        EntityWrapper<SysMenu> wrapper = new EntityWrapper<SysMenu>();
        wrapper.orderBy("sys_menu.menu_order");
        List<SysMenu> menuList = selectList(wrapper);
        List<String> menuIds = new ArrayList<String>();
        if (StringUtils.isNotBlank(roleId)) {
            menuIds = sysRoleMenuMapper.selectMenuIdsByRoleId(roleId);
        }
        for (SysMenu sysMenu : menuList) {
            JsTreeVM jsTreeVM = new JsTreeVM();
            jsTreeVM.setId(sysMenu.getId());
            jsTreeVM.setParent(sysMenu.getParentId()==null?"#":sysMenu.getParentId());
            jsTreeVM.setText(sysMenu.getTitle());
            jsTreeVM.setIcon(sysMenu.getIcon());
            TreeStateVM state = new TreeStateVM();
            if (menuIds.size()>0&&menuIds.contains(sysMenu.getId())){
                state.setSelected(true);
            }
            jsTreeVM.setState(state);
            treeVoList.add(jsTreeVM);
        }
        return treeVoList;
    }

    @Override
    public boolean insert(SysMenu entity) {
        boolean flag = super.insert(entity);

        //自动分配给拥有父菜单的角色权限
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.selectList(
                new EntityWrapper<SysRoleMenu>(new SysRoleMenu(entity.getParentId())));
        for (SysRoleMenu roleMenu: roleMenuList){
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setMenuId(entity.getId());
            sysRoleMenu.setRoleId(roleMenu.getRoleId());
            sysRoleMenuMapper.insert(sysRoleMenu);
        }
        return flag;
    }

    @Override
    public Set<String> getPermissions(String userId) {

        List<String> menuList = baseMapper.getPermissions(userId);
        //用户权限列表
        Set<String> permsSet = new HashSet<String>();
        for(String perm : menuList){
            if(StringUtils.isBlank(perm)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perm.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public List<MenuVM> getMenu(String userId) {
        List<SysMenu> menuList = baseMapper.selectMenuByUserId(userId);
        return MenuTreeUtil.getMenu(menuList);
    }
}
