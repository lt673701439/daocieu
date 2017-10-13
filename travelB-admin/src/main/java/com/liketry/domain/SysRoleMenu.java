package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by liketry
 */
@TableName("sys_role_menu")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRoleMenu extends BaseModel<SysRoleMenu> {

    private String roleId;
    private String menuId;

    public SysRoleMenu(String menuId) {
        this.menuId = menuId;
    }

    public SysRoleMenu() {
    }
}
