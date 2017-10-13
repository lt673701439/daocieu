package com.liketry.interaction.benison.service;

import com.liketry.interaction.benison.model.IdentifyingCode;

/**
 * 验证码service
 *
 * @author pengyy
 */
public interface IdentifyingCodeService {

	/**
	 * 新增验证码
	 * @param identifyingCode 验证码实体类
	 * @return
	 */
	Boolean insert(IdentifyingCode identifyingCode);
}
