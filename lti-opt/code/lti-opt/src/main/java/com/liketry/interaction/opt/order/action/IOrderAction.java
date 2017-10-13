package com.liketry.interaction.opt.order.action;

import java.util.List;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.action.IBaseAction;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;


/**
  * IOrderAction
  */

public interface IOrderAction extends IBaseAction { 

	final String ACTION_ID = "orderAction"; 	
	
	public List<Dto> findAllOld(Dto param);
	
	public List<Dto> findAllByTemplateId(Dto param);
	
	public List<Dto> findOrderCountList2Name(Dto param);
	
	public List<Dto> findOrderCount4Name(Dto param);
	
	public List<Dto> findOrderCountList2Type(Dto param);
	
	public List<Dto> findOrderCountList2Benison(Dto param);
	
	public CurrentPage findTimeOrderList(CurrentPage currentPage);
	
	public List<Dto> findTypeOrderList(Dto param);
	
	public List<Dto> findBenisonOrderList(Dto param);
	
	public List<Dto> findFirstColumns(Dto param);
	
	public List<Dto> findNewFirstColumns(Dto param);
	
	public List<Dto> findSecondColumns(Dto param);
	
	public List<Dto> findNewSecondColumns(Dto param);
	
	public List<Dto> findThirdColumns(Dto param);
	
	public List<Dto> findFirstColumnsForSOT(Dto param);
	
	public List<Dto> findSecondColumnsForSOT(Dto param);

	public int findOneTimeOrderCount(Dto param);
	
	public CurrentPage findScreenTimeOrderList(CurrentPage currentPage);
	
	public int getAllCount(Dto param);
	
	public List<Dto> findAllOrderByStatus(Dto param);
}
