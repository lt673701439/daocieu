package com.liketry.web;

import com.liketry.domain.SysUser;
import com.liketry.service.SysUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by liketry
 */
@RestController
@RequestMapping("/sys/users")
public class SysUserController extends BaseController<SysUserService,SysUser>{

}
