package com.liketry.web;

import com.liketry.domain.SysMenu;
import com.liketry.service.SysMenuService;
import com.liketry.web.vm.JsTreeVM;
import com.liketry.web.vm.ResultVM;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by liketry
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController<SysMenuService,SysMenu>{

    /**
     * 获取菜单树
     * @param roleId
     * @return
     */
    @GetMapping("/getMenuTree")
    public ResultVM getMenuTree(@RequestParam(required = false) String roleId) {
        List<JsTreeVM> list = service.getMenuTree(roleId);
        return ResultVM.ok(list);
    }
}
