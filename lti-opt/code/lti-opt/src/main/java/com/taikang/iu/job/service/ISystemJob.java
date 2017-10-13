package com.taikang.iu.job.service;

public interface ISystemJob{
	final String SERVICE_ID = "systemJob";
	
	/**
	 * 每天0点定时刷新当前值
	 * @author t-pengyangyang
	 * @Credited 2015年4月21日 上午10:27:09
	 * @return
	 */
	public void freshCurrent();
	
}
