package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.liketry.web.vm.JsTreeVM;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * Created by liketry
 */
@TableName("sys_role")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysRole extends BaseModel<SysRole> {

    private String roleName;
    private String remark;
    @TableField(exist=false)
    private Boolean checked;
    @TableField(exist=false)
    private List<JsTreeVM> menuTree;

    public SysRole() {
    }

    public SysRole(String roleName) {
        this.roleName = roleName;
    }
}
