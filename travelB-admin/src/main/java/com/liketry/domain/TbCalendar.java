package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by liketry
 */
@TableName("tb_calendar")
@Data
@EqualsAndHashCode(callSuper = false)
public class TbCalendar extends BaseModel<TbCalendar> {

    private String title;
    private String start;
    private String end;
    private String color;

}
