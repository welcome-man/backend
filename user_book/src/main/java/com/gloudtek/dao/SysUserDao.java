package com.gloudtek.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gloudtek.entity.SysRoleEntity;
import com.gloudtek.entity.SysUserEntity;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:11
 */
public interface SysUserDao extends BaseDao<SysUserEntity> {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUserEntity queryByUserName(String username);
	
	SysUserEntity queryByUserPhone(String mobilenumber);
	
	SysUserEntity queryByUserId(String userId);
	
	/**
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);
	
	/**
	 * 用户管理-重置密码
	 */
	int ResetPassword(Map<String, Object> map);
	
	Long getParentIdByUserId(Long userId);
	
	
	/**
	 *根据用户ID获取使用单位ID 
	 */
	String getUnitId(Long userid);
	
	/**
	 *删除客户管理员 
	 */
	
	void delClientManager(List<Long> list);
	/**
	 *用户总数 
	 */
	int	queryTotaluser(Map<String, Object> map);
	/**
	 * 用户列表（超级管理员查看客户管理员信息列表）数据总条数
	 */
	int clientManagerTotal(Map<String, Object> map);
	
	/**
	 * 用户列表（超级管理员查看用户信息列表）数据总条数
	 */
	int userListTotal(Map<String, Object> map);
	/**
	 *删除用户 
	 */
	void delUser(List<Long> list);
	
	List<SysUserEntity> getUsersByParentId(Object id);
	
	List<SysUserEntity> getUsersByUnitId(Object id);
	
	/**
	 * 根据用户ID获取角色名称
	 */
	Integer getRoleTypeByUserId(Long userId);
	
	/**
	 *根据用户ID获取用户对应角色 
	 */
	Long infouserrole(Long userId);
	
	/**
	 *根据单位名查询重复单位 
	 */
	String repeatunit (String unitname);

	void updateInfo(SysUserEntity user);
	/*
	 * 管理员账号重复
	 */
	int selectAccount(Map<String,Object> map);
	/*
	 * 使用单位重复
	 */
	int selectUnitName(Map<String,Object> map);
	
	/*
	 * 用户名重复查询
	 */
	String customercheckusername(Map<String,Object> map);
/**
 * 用户名重复
 * @param username
 * @return
 */
	int selectName(Map<String,Object> map);
	
	int selectUserName(Map<String,Object> map);

	String getUnitId2(Long long1);

	void deleteUnit(String unitId);
	
	void deleteCheckUnit(String unitId);

	void deleteByUnitId(String unitId);

	void deleteCustomer(Long l);

	void deleteAuthCodeByUnitId(@Param(value = "unitId") String unitId,@Param(value = "userId") Long long1);

	SysRoleEntity queryRoleName(Long userId);

	List<SysUserEntity> findRoleInfoByUserId(Long userId);
	
	
	
	
}
