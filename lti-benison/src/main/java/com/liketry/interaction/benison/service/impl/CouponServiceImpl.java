package com.liketry.interaction.benison.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.liketry.interaction.benison.dao.CouponMapper;
import com.liketry.interaction.benison.service.CouponService;
import com.liketry.interaction.benison.vo.api.CouponVO;

/**
 * 优惠码service实现类
 *
 * @author pengyy
 */
@Service
public class CouponServiceImpl implements CouponService {
	
    @Autowired
    private CouponMapper couponMapper;

	@Override
	public CouponVO getCouponByCode(String code) {
		return couponMapper.selectByCode(code);
	}

    
}
