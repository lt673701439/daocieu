/**
 * @author pengyy
 * created at 2017/5/23 18:09
 */
package com.liketry.interaction.benison.service;

import com.liketry.interaction.benison.model.OrderDetail;


public interface OrderDetailService {

   OrderDetail findOneOrderDetail(String orderDetailId); 

}