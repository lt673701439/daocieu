package com.taikang.udp.sys.service.impl;
 
import java.util.List;

import org.springframework.stereotype.Service;

import com.taikang.udp.framework.common.datastructre.Dto;
import com.taikang.udp.framework.core.exception.app.TKBizException;
import com.taikang.udp.framework.core.persistence.pagination.CurrentPage;
import com.taikang.udp.framework.core.service.impl.BaseServiceImpl;
import com.taikang.udp.sys.model.UserBO;
import com.taikang.udp.sys.service.IUserService;
import com.taikang.udp.sys.util.sequence.SequenceGenerator;
 
  
/**
  * UserServiceImpl
  */
 @Service(IUserService.SERVICE_ID)
 public class UserServiceImpl extends BaseServiceImpl 
 implements IUserService<UserBO>  
  {

	/**
	  * 增加数据
	  */
	public void insertObject(UserBO user) {
		logger.debug("<======UserServiceImpl--insertUser======>");
		UserBO queryuser = new UserBO();
		queryuser.setUserCode(user.getUserCode());
		List<UserBO> userLst = this.findAll(queryuser);
		if (userLst != null && userLst.size() >=1) {
			throw new TKBizException("err", "UserServiceImpl", "insertObject",
			        "该用户编码:" + user.getUserCode() + "已经存在！", null);
		}
//		SequenceGenerator generator = SequenceGenerator.getInstance("User_Id",1);
//		long nextid = generator.nextId();
		//调用后台接口获取序列userId
		int nextid = (int)SequenceGenerator.getInstance("User_Id",1).nextId();
		user.setUserId(nextid);
		
//		logger.debug("<======EmployeeServiceImpl--insertEmployee======>");
//		EmployeeBO employee = new EmployeeBO();
//		employee.setEmployeeId(UUID.randomUUID().toString().replace("-", ""));
//		employee.setEmployeeCode("YG"+TKDateTimeUtils.getCurrentDate().toString().replace("-", "")+BusinessSeqGenerator.getInstance("EMPLOYEE_CODE").nextId());
//		employee.setUserId(Integer.valueOf(String.valueOf(nextid)));
//		employee.setEmployeeTel(user.getUserPhone());
//		employee.setEmployeeName(user.getUserName());
//		employee.setCreatedBy(String.valueOf(user.getCreator()));
//		employee.setCreatedTime(TKDateTimeUtils.getTodayTimeStamp());
//		employee.setModifiedBy(String.valueOf(user.getCreator()));
//		employee.setModifiedTime(TKDateTimeUtils.getTodayTimeStamp());
//		employee.setVersion(1);
//		employee.setDelflag("0");
//		
//		appDao.insert("Employee.addEmployee",employee);
		appDao.insert("User.addUser",user);
	} 	
 	
 	/**
      * 修改数据
      */
	public void updateObject(UserBO user) {
		logger.debug("<======UserServiceImpl--updateUser======>");
		appDao.update("User.updateUser",user);
	}

	 /**
      * 删除数据
      */
	public void deleteObject(UserBO user) {
		logger.debug("<======UserServiceImpl--deleteUser======>");
		appDao.delete("User.deleteUser",user);
	}
	
	/**
      * 查询数据
      */
	public UserBO findOne(UserBO user) {
		logger.debug("<======UserServiceImpl--findUser======>");
		
		UserBO userBackBO=appDao.queryOneObject("User.findOneUser", user);
		return userBackBO;		
	}
	
	/**
      * 查询所有数据
      */
	public List<UserBO> findAll(UserBO  user) {
		logger.debug("<======UserServiceImpl--findAllUser======>");
		return appDao.queryForEntityList("User.findAllUser", user);
	}
	
	
	 /**
      * 分页查询数据
      */
	public CurrentPage queryForPage(CurrentPage currentPage) {
		logger.debug("<======UserServiceImpl--queryUserForPage======>");
//		return appDao.queryForPage("User.queryUserForPage", currentPage);
		return appDao.queryForPage("User.queryUserByConForPage", currentPage);
	}

	 /**
     * 分页查询数据
     */
	public CurrentPage queryByCondition(CurrentPage currentPage) {
		logger.debug("<======UserServiceImpl--queryUserForPage======>");
		return appDao.queryForPage("user_deal.queryByCondition", currentPage);
	}

	/**
      * 查询所有数据 ，重新组装为map
      */
	public List<Dto> findAllMap(Dto param){
		logger.debug("<======UserServiceImpl--findAllMapUser======>");		
		return appDao.queryForMapList("User.findAllMapUser", param);
	}

	public void insertUser(UserBO userBO) {
		logger.debug("<======UserServiceImpl--insertUser======>");		
		appDao.insert("User.addUser",userBO);
		
	}

	public List<Dto> queryAllUsersByCondition(Dto param) {
		return appDao.queryForMapList("user_deal.queryAllUsersByCondition", param);
	}
 }
  