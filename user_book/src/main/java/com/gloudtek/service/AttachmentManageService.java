package com.gloudtek.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.AttachmentManageEntity;

/**
 * 附件管理
 * 
 * @author Seac.Cong
 * @email seac.cong@gloudtek.com
 * @date 2017-09-12 10:03:32
 */
public interface AttachmentManageService {
	
	AttachmentManageEntity queryObject(String id);
	
	List<AttachmentManageEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(AttachmentManageEntity attachmentManage);
	
	void update(AttachmentManageEntity attachmentManage);
	
	void delete(String id);
	
	void deleteBatch(String[] ids);
}
