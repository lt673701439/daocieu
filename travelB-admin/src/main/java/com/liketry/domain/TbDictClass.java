package com.liketry.domain;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by liketry
 */
@TableName("tb_dict_class")
@Data
@EqualsAndHashCode(callSuper = false)
public class TbDictClass extends BaseModel<TbDictClass> {

	private int sort;
	private String text;
    private String code;
    private String remark;

}
