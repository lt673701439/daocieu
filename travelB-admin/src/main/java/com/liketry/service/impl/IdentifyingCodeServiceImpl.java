package com.liketry.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.liketry.domain.IdentifyingCode;
import com.liketry.mapper.IdentifyingCodeMapper;
import com.liketry.service.IdentifyingCodeService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * author pengyy
 * create 2017/9/21
 * 商户
 */
@Service
public class IdentifyingCodeServiceImpl extends ServiceImpl<IdentifyingCodeMapper, IdentifyingCode> implements IdentifyingCodeService {

	/**
     * 校验验证码是否正确
     * @param mobile  手机号
     * @param type  类型
     * @param code  验证码
     * @return
     */
	public String checkIdentifyingCode(String mobile,String type,String code){

		String msg = null;

		//当前手机号，指定类型的验证码
     	EntityWrapper<IdentifyingCode> wrapper = new EntityWrapper<IdentifyingCode>();
     	wrapper.and("mobile={0} and code={1} and type={2}",mobile,type,code).orderBy("create_time", false).last("limit 1");
     	
     	List<IdentifyingCode> IdentifyingCodeList = baseMapper.selectList(wrapper);
     	
     	if(IdentifyingCodeList != null && IdentifyingCodeList.size()>0){
     		
     		Date sendTime = IdentifyingCodeList.get(0).getSendTime(); //发送时间
     		Date currentTime = new Date(); //当前时间
     		
     		//校验是否超时(1分钟)
     		if(currentTime.getTime() - sendTime.getTime() > 60000){
     			msg = "验证码超时，请重新发送！";
     		}
     		
     	}else{
     		msg = "验证码错误，请重新输入！";
     	}
     	
     	return msg;
     }
	
}