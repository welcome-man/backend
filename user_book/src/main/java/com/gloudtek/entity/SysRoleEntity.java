package com.gloudtek.entity;


import java.io.Serializable;
import java.util.List;

//角色
public class SysRoleEntity extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	//角色ID
	private Long roleId;

	//角色名称
	private String roleName;

	//备注
	private String remark;
	
	//角色类型(1：超级管理员；2：客户管理员；3：检定员)
	private Integer type;
	
	//角色菜单ID列表
	private List<Long> menuIdList;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Long> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Long> menuIdList) {
		this.menuIdList = menuIdList;
	}

}
