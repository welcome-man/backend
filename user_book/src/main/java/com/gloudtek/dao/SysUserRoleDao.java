package com.gloudtek.dao;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.SysUserRoleEntity;
import com.gloudtek.utils.RoleUtil;


/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:34:46
 */
public interface SysUserRoleDao extends BaseDao<SysUserRoleEntity> {
	
	/**
	 * 根据用户ID，获取角色ID列表
	 */

	List<Long> queryRoleIdList(Long userId);
	
	List<SysUserRoleEntity> queryList(Map<String, Object> map);
	
	void saveOrUpdate(Long userId);
	
	void saveRole(Long userId);
	
	void updateRole(RoleUtil roleUtil);
	
	int deleteRoleUserByUserId(Long userId);

	
}
