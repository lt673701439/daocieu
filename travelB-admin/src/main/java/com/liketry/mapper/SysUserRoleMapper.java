package com.liketry.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.liketry.domain.SysUserRole;

import java.util.List;

/**
 * Created by liketry
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 根据用户id查询角色id集合
     * @param userId
     * @return
     */
    List<String> selectRoleIdsByUserId(String userId);
}
