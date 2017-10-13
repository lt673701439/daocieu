package com.liketry.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.IService;
import com.liketry.domain.IdentifyingCode;

/**
 * author pengyy
 * create 2017/9/21
 * 验证码service
 */
@Service
public interface IdentifyingCodeService extends IService<IdentifyingCode> {
	
	public String checkIdentifyingCode(String mobile,String type,String code);
	
}