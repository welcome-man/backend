package com.gloudtek.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gloudtek.dao.SysUserDao;
import com.gloudtek.dao.SysUserRoleDao;
import com.gloudtek.entity.SysUserEntity;
import com.gloudtek.entity.SysUserRoleEntity;
import com.gloudtek.service.SysUserRoleService;
import com.gloudtek.service.SysUserService;
import com.gloudtek.utils.R;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysUserRoleDao sysUserRoleDao;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return sysUserDao.queryByUserName(username);
	}

	@Override
	public SysUserEntity queryByUserId(String userId) {
		return sysUserDao.queryByUserId(userId);
	}

	@Override
	public SysUserEntity queryObject(Long userId) {
		return sysUserDao.queryObject(userId);
	}

	@Override
	public List<SysUserEntity> queryList(Map<String, Object> map) {
		return sysUserDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		// sha256加密
		user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		sysUserDao.save(user);
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if (StringUtils.isBlank(user.getPassword())) {
			user.setPassword(null);
		} else {
			user.setPassword(new Sha256Hash(user.getPassword()).toHex());
		}
		sysUserDao.update(user);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] userId) {
		for (Long id : userId) {
			if (sysUserDao.infouserrole(id) == 2) {
				SysUserEntity user= sysUserDao.queryObject(id);
				//如果是删除的是客户管理员并删除对应的检定员
			} else {
				sysUserDao.deleteCustomer(id);
			}
		}
	}

	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}

	/**
	 * 用户管理-重置密码
	 */
	@Override
	public int ResetPassword(Long userId, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("newPassword", newPassword);
		return sysUserDao.ResetPassword(map);
	}

	@Override
	public SysUserEntity queryByUserPhone(String mobilenumber) {
		return sysUserDao.queryByUserPhone(mobilenumber);
	}


	@Override
	public Long getParentIdByUserId(Long userId) {
		return sysUserDao.getParentIdByUserId(userId);
	}

	/**
	 * 根据用户ID获取使用单位ID
	 */
	public String getUnitId(Long userid) {
		return sysUserDao.getUnitId(userid);
	}
	/**
	 * 用户总数
	 */
	public int queryTotaluser(Map<String, Object> map) {
		return sysUserDao.queryTotaluser(map);
	}

	/**
	 * 用户列表（超级管理员查看客户管理员信息列表）数据总条数
	 */
	public int clientManagerTotal(Map<String, Object> map) {
		return sysUserDao.clientManagerTotal(map);
	}

	/**
	 * 用户列表（超级管理员查看用户信息列表）数据总条数
	 */
	public int userListTotal(Map<String, Object> map) {
		return sysUserDao.userListTotal(map);
	}

	/**
	 * 删除用户
	 */
	public void delUser(List<Long> list) {
		sysUserDao.delUser(list);
	}


	/**
	 * 根据用户ID获取用户对应角色
	 */
	@Override
	public Long infouserrole(Long userId) {
		return sysUserDao.infouserrole(userId);
	}

	/**
	 * 根据单位名查询重复单位
	 */
	@Override
	public String repeatunit(String unitname) {
		return sysUserDao.repeatunit(unitname);
	}

	@Override
	public List<SysUserEntity> getUsersByParentId(Object id) {
		return sysUserDao.getUsersByParentId(id);
	}

	@Override
	public List<SysUserEntity> getUsersByUnitId(Object id) {
		return sysUserDao.getUsersByUnitId(id);
	}

	@Override
	public Integer getRoleTypeByUserId(Long userId) {
		return sysUserDao.getRoleTypeByUserId(userId);
	}

	@Override
	public void updateInfo(SysUserEntity user) {
		sysUserDao.updateInfo(user);

	}

	@Override
	public String customercheckusername(Map<String, Object> map) {
		return sysUserDao.customercheckusername(map);
	}


}
