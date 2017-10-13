package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by liketry
 */
@TableName("tb_dict")
@Data
@EqualsAndHashCode(callSuper = false)
public class TbDict extends BaseModel<TbDict> {

	private String parentId;
	private int sort;
    private String code;
    private String text;
    private String remark;
    private String dictClassId;

}
