package com.gloudtek.entity;

import java.io.Serializable;

public class SysConfEntity extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String key;

	private String remark;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
