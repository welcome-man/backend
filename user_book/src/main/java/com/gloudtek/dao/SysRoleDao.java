package com.gloudtek.dao;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.SysRoleEntity;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:33:33
 */
public interface SysRoleDao extends BaseDao<SysRoleEntity> {
	String selectRoleName(Long userId);
	
	List<String> selectRoleNames(Long userId);
	
	List<String> selectRoleId(Long userId);
	
	List<String> selectsponsorDepartment(Map<String,Object> map);
}
