package com.gloudtek.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gloudtek.dao.AttachmentManageDao;
import com.gloudtek.entity.AttachmentManageEntity;
import com.gloudtek.service.AttachmentManageService;

import java.util.List;
import java.util.Map;





@Service("attachmentManageService")
public class AttachmentManageServiceImpl implements AttachmentManageService {
	@Autowired
	private AttachmentManageDao attachmentManageDao;
	
	@Override
	public AttachmentManageEntity queryObject(String id){
		return attachmentManageDao.queryObject(id);
	}
	
	@Override
	public List<AttachmentManageEntity> queryList(Map<String, Object> map){
		return attachmentManageDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return attachmentManageDao.queryTotal(map);
	}
	
	@Override
	public void save(AttachmentManageEntity attachmentManage){
		attachmentManageDao.save(attachmentManage);
	}
	
	@Override
	public void update(AttachmentManageEntity attachmentManage){
		attachmentManageDao.update(attachmentManage);
	}
	
	@Override
	public void delete(String id){
		attachmentManageDao.delete(id);
	}
	
	@Override
	public void deleteBatch(String[] ids){
		attachmentManageDao.deleteBatch(ids);
	}
	
}
