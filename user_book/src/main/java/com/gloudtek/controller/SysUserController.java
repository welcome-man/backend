package com.gloudtek.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gloudtek.dao.SysUserDao;
import com.gloudtek.entity.BasePage;
import com.gloudtek.entity.SysRoleEntity;
import com.gloudtek.entity.SysUserEntity;
import com.gloudtek.service.SysRoleService;
import com.gloudtek.service.SysUserRoleService;
import com.gloudtek.service.SysUserService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;
import com.gloudtek.utils.RoleUtil;
import com.gloudtek.utils.ShiroUtils;

/**
 * 系统用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/api/sys/user")
public class SysUserController extends AbstractController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserRoleService sysUserRoleService;
	
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserDao sysUserDao;

	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
//	@RequiresPermissions("sys:user:list")
	public R list(@RequestBody BasePage basePage) {
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (basePage.getCurrentPage() - 1) * basePage.getPageSize());
		map.put("limit", basePage.getPageSize());
		
		// 查询列表数据
		List<SysUserEntity> list = sysUserService.queryList(map);
		//查询列表数据
		List<SysRoleEntity> rolelist = sysRoleService.queryList(map);
		List sumlist =new ArrayList<>();
		sumlist.add(list);
		sumlist.add(rolelist);
		int total = sysUserService.queryTotal(map);

		PageUtils pageUtil = new PageUtils(sumlist, total, basePage.getCurrentPage(), basePage.getPageSize());

		return R.ok().put("page", pageUtil);
	}	
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		SysRoleEntity roleEntity =sysUserDao.queryRoleName(super.getUser().getUserId());
		SysUserEntity user =	super.getUser();
		user.setRoleName(roleEntity.getRoleName());
		return R.ok().put("user", user);
	}

	/**
	 * 用户信息
	 */
	@RequestMapping("/infos")
//	@RequiresPermissions("sys:user:info")
	public R info(@RequestBody Long userId) {
		SysUserEntity userlist = sysUserService.queryObject(userId);
		return R.ok().put("user", userlist);
	}

	
	/**
	 * 根据用户ID获取用户对应角色
	 */
	@RequestMapping("/infouserrole/{userId}")
	public R infouserrole(@PathVariable("userId") Long userId) {
		Long type = sysUserService.infouserrole(userId);
		return R.ok().put("type", type);
	}

	/**
	 * 保存用户
	 * @param roleUtil 
	 */
	@RequestMapping("/save")
//	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUserEntity user) {
		if (StringUtils.isBlank(user.getUsername())) {
			return R.error("用户名不能为空");
		}
		if (StringUtils.isBlank(user.getPassword())) {
			return R.error("密码不能为空");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", user.getUsername());
		map.put("userId", user.getUserId());
		if (sysUserDao.selectName(map) > 0) {
			return R.error("您输入的用户账号已存在");
		} else {
			sysUserService.save(user); 
		}
		RoleUtil roleUtil =new RoleUtil();
		roleUtil.setUserId(user.getUserId());
		sysUserRoleService.saveRole(roleUtil.getUserId());
		return R.ok();
	}


	/**
	 * 修改用户
	 */
	@RequestMapping("/update")
//	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user) {
//		if (StringUtils.isBlank(user.getUsername())) {
//			return R.error("用户名不能为空");
//		}
		Map<String,Object> map= new HashMap<String,Object>();
		map.put("username",user.getUsername());
        map.put("userId",user.getUserId());
		if (sysUserDao.selectName(map) >0) {
			return R.error("您输入的用户账号已存在");
		} else {
		// user.setAccount(user.getUsername());
		sysUserService.update(user);
		return R.ok();
		}
	}

	/**
	 * 保存用户角色(如1.超级管理员2.管理员3.用户)
	 */
	@RequestMapping("/saverole")
	// @RequiresPermissions("sys:user:save")
	public R saverole(@RequestBody RoleUtil roleUtil) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", roleUtil.getUserId());
		if (roleUtil.getUserId() == 1) {
			return R.error("系统异常:你已经是超级管理员不能在选择");
		}
		if (roleUtil.getRoleId() == 1) {
			return R.error("系统异常:超级管理员已被选定");
		} else {
			sysUserRoleService.updateRole(roleUtil);
		}
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@RequestMapping("/delete")
//	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds) {
		if (ArrayUtils.contains(userIds, 1L)) {
			return R.error("系统管理员不能删除");
		}

		if (ArrayUtils.contains(userIds, getUserId())) {
			return R.error("当前用户不能删除");
		}

		sysUserService.deleteBatch(userIds);

		return R.ok();
	}

	/**
	 * 删除用户
	 */
	@RequestMapping("/delUser")
	@RequiresPermissions("api:sys:user:delUser")
	public R delUser(@RequestBody Long[] suerId) {
		List<Long> list = new ArrayList<>();
		for (int i = 0; i < suerId.length; i++) {
			list.add(suerId[i]);
		}
		sysUserService.delUser(list);
		return R.ok("删除成功");
	}

	/**
	 * 修改用户，用户信息页面
	 */
	@RequestMapping("/updateInfo")
	// @RequiresPermissions("sys:user:update")
	public R updateInfo(@RequestBody SysUserEntity user) {

		if (StringUtils.isBlank(user.getUsername())) {
			return R.error("用户名不能为空");
		}
		user.setUpdateTime(new Date());
		user.setUpdateBy(user.getUsername());
		sysUserService.updateInfo(user);
		SysUserEntity users = sysUserService.queryByUserId(Long.toString(user.getUserId()));
		Subject subject = ShiroUtils.getSubject();
		String password = users.getPassword();
		UsernamePasswordToken token = new UsernamePasswordToken(users.getUsername(), password);
		subject.login(token);
		return R.ok();
	}

}
