package com.gloudtek.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.SysRoleEntity;


/**
 * 角色
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:42:52
 */
public interface SysRoleService {
	
	SysRoleEntity queryObject(Long roleId);
	
	List<SysRoleEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRoleEntity role);
	
	void update(SysRoleEntity role);
	
	void deleteBatch(Long[] roleIds);
	//查询role_name
	String selectRoleName(Long userId);
	
	List<String> selectRoleNames(Long userId);
	
	List<String> selectRoleId(Long userId);
	
	List<String> selectsponsorDepartment(Map<String,Object> map);
}
