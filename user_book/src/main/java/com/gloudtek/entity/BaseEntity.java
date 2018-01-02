package com.gloudtek.entity;

import java.util.Date;

public class BaseEntity {
	
	
	private Date updateTime;//更新时间
	
	private Date createTime = new Date();//创建时间
	
	private Integer isdelete = 0;//数据状态（0：未删除；1：删除；默认0）
	
	private String createBy;//创建人
	
	private String updateBy;//更新人

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	

}
