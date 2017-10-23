package com.liketry.interaction.benison.service;

import com.liketry.interaction.benison.vo.api.CouponVO;

/**
 * 优惠码service
 *
 * @author pengyy
 */
public interface CouponService {

	public CouponVO getCouponByCode(String code);
}
