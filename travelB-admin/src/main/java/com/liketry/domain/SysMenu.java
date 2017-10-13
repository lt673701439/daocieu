package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by liketry
 */
@TableName("sys_menu")
@Data
@EqualsAndHashCode(callSuper = false)
public class SysMenu extends BaseModel<SysMenu> {

    private String parentId;
    private String name;
    private String type;
    private String icon;
    private String title;
    private Integer level;
    private Integer menuOrder;


}
