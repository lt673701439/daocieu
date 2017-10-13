package com.taikang.udp.sys.service.impl;
 
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.common.datastructre.impl.BaseBO;
import com.taikang.udp.framework.core.exception.app.TKBizException;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.taikang.udp.sys.model.RoleBO;
import com.taikang.udp.sys.model.RoleMenuBO;
import com.taikang.udp.sys.model.UserRoleBO;
import com.taikang.udp.sys.service.IRoleMenuService;
import com.taikang.udp.sys.service.IRoleService;
import com.taikang.udp.sys.service.IUserRoleService;
import com.taikang.udp.sys.util.sequence.SequenceGenerator;
import com.taikang.udp.sys.util.sequence.dao.SequenceDao;
 
  
/**
  * RoleServiceImpl
  */
 @Service(IRoleService.SERVICE_ID)
 public class RoleServiceImpl extends BaseServiceImpl 
 implements IRoleService<RoleBO>  
  {
	@Resource(name="sequenceDao")
	protected SequenceDao sequenceDao; 	 	

    /**
     * 注入service
     */
    @Resource(name=IUserRoleService.SERVICE_ID)
	private IUserRoleService<UserRoleBO> userRoleService;
    /**
     * 注入service
     */
   @Resource(name=IRoleMenuService.SERVICE_ID)
	private IRoleMenuService<RoleMenuBO> roleMenuService;
   
 	 	 	
 	/**
	  * 增加数据
	  */
	public void insertObject(RoleBO role) {
		logger.debug("<======RoleServiceImpl--insertRole======>");		
		RoleBO queryRole = new RoleBO();
		queryRole.setRoleCode(role.getRoleCode());
		List<RoleBO> roleLst = this.findAll(queryRole);
		if (roleLst != null && roleLst.size() >=1) {
			throw new TKBizException("err", "RoleServiceImpl", "insertObject",
			        "该角色编码:" + role.getRoleCode() + "已经存在！", null);
		}
		SequenceGenerator generator = SequenceGenerator.getInstance("User_Id",1);
		long roleId = generator.nextId();
		role.setRoleId(Integer.valueOf(String.valueOf(roleId)));
		appDao.insert("Role.addRole",role);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(RoleBO role) {
		logger.debug("<======RoleServiceImpl--updateRole======>");
		appDao.update("Role.updateRole",role);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(RoleBO role) {
		logger.debug("<======RoleServiceImpl--deleteRoleMenu======>");
		//删除角色的时候同时删除该角色和菜单的对应关系
		RoleMenuBO rolemenu = new RoleMenuBO();
		rolemenu.setRoleId(role.getRoleId());
		List<RoleMenuBO> RolemenuList = roleMenuService.findAll(rolemenu);
		for (int i = 0; i <RolemenuList.size(); i++) {
			if (RolemenuList.get(i) != null) {
				appDao.delete("RoleMenu.deleteRoleMenu",RolemenuList.get(i) );
			}
		}
		logger.debug("<======RoleServiceImpl--deleteUserRole======>");
		//删除角色的时候同时删除该用户和角色的对应关系
		UserRoleBO userrole = new UserRoleBO();
		userrole.setRoleId(role.getRoleId());
		List<UserRoleBO> UserroleList = userRoleService.findAll(userrole);
		for (int i = 0; i <UserroleList.size(); i++) {
			if (UserroleList.get(i) != null) {
				appDao.delete("UserRole.deleteUserRole",UserroleList.get(i) );
			}
		}
		logger.debug("<======RoleServiceImpl--deleteRole======>");
		appDao.delete("Role.deleteRole",role);
	}
	
	/**
      * 查询数据
      */
	public RoleBO findOne(RoleBO role) {
		logger.debug("<======RoleServiceImpl--findRole======>");
		
		RoleBO roleBackBO=appDao.queryOneObject("Role.findOneRole", role);
		return roleBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<RoleBO> findAll(RoleBO  role) {
		logger.debug("<======RoleServiceImpl--findAllRole======>");
		return appDao.queryForEntityList("Role.findAllRole", role);
	}
	
	
 	/**
 	 * 按条件分页查询角色信息<br/>
 	 * @param currentPage
 	 * @return   
 	 * CurrentPage
 	 */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======RoleServiceImpl--queryRoleForPage======>");
		return appDao.queryForPage("Role.queryRoleByConForPage", currentPage);
	}
		
	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======RoleServiceImpl--findAllMapRole======>");		
		return appDao.queryForMapList("Role.findAllMapRole", param);
	}
	
 	/**
 	 * 查询指定用户是否拥有角色信息列表<br/>
 	 * @param param
 	 * @return   
 	 * List<Dto>
 	 */
	public List<Dto> getUsersRoleList(Dto param) {
		logger.debug("<======RoleServiceImpl--findAllMapRole======>");

		String userCode = String.valueOf(param.get("userCode"));
		UserRoleBO userRoleBO = new UserRoleBO();
		List<UserRoleBO> userRoleList = null;
		// 查询该用户拥有的角色信息
		if (userCode != null && !"".equals(userCode)) {
			userRoleBO.setUserId(Integer.valueOf(userCode));
			userRoleList = userRoleService.findAll(userRoleBO);
		}

		List<Dto> roleList = appDao.queryForMapList("Role.findAllMapRole", param);
		
		// 设置指定用户是否拥有相应角色
		if (roleList != null && roleList.size() > 0) {
			for (int i = 0; i < roleList.size(); i++) {
				Dto dto = roleList.get(i);
				Integer roleId = dto.getAsInteger("Role_Id");
				// List roleList = dto.getDefaultAList();
				if (userRoleList == null || userRoleList.size() == 0) {
					// 该用户没有角色
					roleList.get(i).put("okflag", 0);
				} else {
					if (this.checkRoleIdInList(roleId, userRoleList)) {
						// 该用户有该角色
						roleList.get(i).put("okflag", 1);
					} else {
						// 该用户没有该角色
						roleList.get(i).put("okflag", 0);
					}
				}
			}
		}

		return roleList;
	}
	
	/**
	 * 判断指定角色是否存在于角色List中<br/>
	 * @param roleId
	 * @param userRoleList
	 * @return   
	 * boolean true 存在该角色 false 不存在该角色
	 */
	boolean checkRoleIdInList(Integer roleId, List<UserRoleBO> userRoleList) {
		UserRoleBO userRoleBO = null;
		for (int i = 0; i < userRoleList.size(); i++) {
			userRoleBO = userRoleList.get(i);
			if (userRoleBO.getRoleId().equals(roleId)) {
				return true;
			}
		}
		return false;
	}

	public BaseBO findRoleIdByCode(RoleBO roleBO) {
		logger.debug("<======RoleServiceImpl--findRole======>");
		
		RoleBO roleBackBO=appDao.queryOneObject("Role.findRoleIdByCode", roleBO);
		return roleBackBO;	
	}
 }
  