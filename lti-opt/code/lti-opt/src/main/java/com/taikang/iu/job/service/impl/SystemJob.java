package com.taikang.iu.job.service.impl;




import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taikang.iu.job.service.ISystemJob;
import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseDto;
import com.taikang.udp.framework.core.exception.app.TKDaoException;
import com.taikang.udp.framework.core.web.BaseController;
import com.taikang.udp.sys.action.ISeqnoAction;


@Service(ISystemJob.SERVICE_ID)
public class SystemJob extends BaseController implements ISystemJob{
	
	@Resource(name=ISeqnoAction.ACTION_ID)
	private ISeqnoAction seqnoAction;
	
	/**
	 * 每天0点定时设定当前值为1
	 */
	@SuppressWarnings("unchecked")
	public void freshCurrent() {
		
		logger.debug("<======SystemJob--updateCurrent======>");
		Dto param = new BaseDto();
		param.put("currValue", "0");
		try
		{	
			seqnoAction.updateObject(param);
		}catch(Exception e)
		{
			logger.error(" update failed ,Exception= "+e.getMessage());
			throw new TKDaoException("","SequenceDaoImpl","update"," 更新序列信息时发生错误! ",e);
		}
		
	}
	
	
}
