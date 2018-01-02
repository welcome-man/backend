package com.gloudtek.entity;

import java.io.Serializable;
import java.util.List;

//系统用户
public class SysUserEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// 用户ID
	private Long userId;

	// 父ID
	private Long parentId;

	// 用户名
	private String username;

	// 用户账号
	private String account;

	// 密码
	private transient String password;

	// 手机号
	private String phone;

	// 联系电话
	private String telephone;

	// 邮箱
	private String email;

	// 备注
	private String remark;

	// 状态（0：禁用；1：正常）
	private Integer status;

	// 性别（0：男1：女）
	private Integer sex;
	
    //roleId
	private Long roleId;
	
	//roleName名称
	private String roleName;
	
	//SysRole表
	private List<SysRoleEntity> SysRolelist;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<SysRoleEntity> getSysRolelist() {
		return SysRolelist;
	}

	public void setSysRolelist(List<SysRoleEntity> sysRolelist) {
		SysRolelist = sysRolelist;
	}

	
}