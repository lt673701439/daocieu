package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by liketry
 */
@TableName("tb_todo")
@Data
@EqualsAndHashCode(callSuper = false)
public class TbTodo extends BaseModel<TbTodo> {

    private String text;

}
