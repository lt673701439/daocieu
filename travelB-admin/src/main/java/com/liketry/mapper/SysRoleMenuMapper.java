package com.liketry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.SysRoleMenu;

import java.util.List;

/**
 * Created by liketry
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

    /**
     * 根据角色id查询菜单id
     * @param roleId
     * @return
     */
    List<String> selectMenuIdsByRoleId(String roleId);
}
