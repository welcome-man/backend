package com.gloudtek.entity;

import java.io.Serializable;
import java.util.Date;



/**
 * 附件管理
 * 
 * @author Seac.Cong
 * @email seac.cong@gloudtek.com
 * @date 2017-09-12 10:03:32
 */
public class AttachmentManageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//id
	private String id;
	private String shareId;
	//附件名称
	private String attachmentName;
	//文件名
	private String fileName;
	//附件url
	private String attachmentUrl;
	//创建时间
	private Date createTime;
	//更新人
	private String updateBy;
	//
	private String createBy;
	//更新时间
	private Date updateTime;
	//是否删除（0：未删除；1：删除）
	private Integer isdelete;
	//专案id
	private String caseId;

	/**
	 * 设置：id
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取：id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置：附件名称
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}
	/**
	 * 获取：附件名称
	 */
	public String getAttachmentName() {
		return attachmentName;
	}
	/**
	 * 设置：文件名
	 */

	/**
	 * 设置：附件url
	 */
	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}

	public String getShareId() {
		return shareId;
	}
	public void setShareId(String shareId) {
		this.shareId = shareId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/**
	 * 获取：附件url
	 */
	public String getAttachmentUrl() {
		return attachmentUrl;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：更新人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：是否删除（0：未删除；1：删除）
	 */
	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	/**
	 * 获取：是否删除（0：未删除；1：删除）
	 */
	public Integer getIsdelete() {
		return isdelete;
	}
	public String getCaseId() {
		return caseId;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	
}
