package com.liketry.interaction.benison.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liketry.interaction.benison.dao.IdentifyingCodeMapper;
import com.liketry.interaction.benison.model.IdentifyingCode;
import com.liketry.interaction.benison.service.IdentifyingCodeService;

/**
 * 验证码service实现类
 *
 * @author pengyy
 */
@Service
public class IdentifyingCodeServiceImpl implements IdentifyingCodeService {
	
    @Autowired
    private IdentifyingCodeMapper identifyingCodeMapper;

	@Override
	public Boolean insert(IdentifyingCode identifyingCode) {
		
		int count = identifyingCodeMapper.insert(identifyingCode);
		
		if(count>0){
			return true;
		}else{
			return false;
		}
	}

}
