package com.gloudtek.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gloudtek.dao.SysUserRoleDao;
import com.gloudtek.entity.SysUserRoleEntity;
import com.gloudtek.service.SysUserRoleService;
import com.gloudtek.service.SysUserService;
import com.gloudtek.utils.RoleUtil;



/**
 * 用户与角色对应关系
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:45:48
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Autowired
	private SysUserService sysUserService;

	@Override
	public void saveOrUpdate(Long userId) {
		//先删除用户与角色关系
		sysUserRoleDao.delete(userId);
		
		//保存用户与角色关系
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		sysUserRoleDao.save(map);
	}

	@Override
	public List<Long> queryRoleIdList(Long userId) {
		return sysUserRoleDao.queryRoleIdList(userId);
	}

	@Override
	public void delete(Long userId) {
		sysUserRoleDao.delete(userId);
	}

	@Override
	public void updateRole(RoleUtil roleUtil) {
		sysUserRoleDao.updateRole(roleUtil);
		
	}

	@Override
	public void saveRole(Long userId) {
		sysUserRoleDao.saveRole(userId);
		
	}

	@Override
	public List<SysUserRoleEntity> queryList(Map<String, Object> map) {
		return sysUserRoleDao.queryList(map);
	}
}
