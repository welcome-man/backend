package com.gloudtek.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.SysUserEntity;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService {
	
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
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	SysUserEntity queryObject(Long userId);
	
	/**
	 * 查询用户列表
	 */
	List<SysUserEntity> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	void save(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);
	
	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	int updatePassword(Long userId, String password, String newPassword);
	
	/**
	 * 用户管理-重置密码
	 */
	int ResetPassword(Long userId, String newPassword);
	
	Long getParentIdByUserId(Long userId);
	
	/**
	 *根据用户ID获取使用单位ID 
	 */
	String getUnitId(Long userid);
	
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
	 * 删除用户（超级管理员新增用户）
	 */
	void delUser(List<Long> list);
	
	/**
	 *根据用户ID获取用户对应角色 
	 */
	Long infouserrole(Long userId);
	
	/**
	 *根据单位名查询重复单位 
	 */
	String repeatunit (String unitname);
	
	List<SysUserEntity> getUsersByParentId(Object id);
	
	List<SysUserEntity> getUsersByUnitId(Object id);
	
	Integer getRoleTypeByUserId(Long userId);

	void updateInfo(SysUserEntity user);

	/*
	 * 用户名重复查询
	 */
	String customercheckusername( Map<String,Object> map);

	
}
