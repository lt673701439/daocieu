package com.liketry.service;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.SysUser;

/**
 * Created by liketry
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    SysUser findByUserName(String username);
}
