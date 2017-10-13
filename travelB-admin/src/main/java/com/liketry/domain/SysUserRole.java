package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by liketry
 */
@TableName("sys_user_role")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysUserRole extends BaseModel<SysUserRole> {

    private String roleId;
    private String userId;

}
