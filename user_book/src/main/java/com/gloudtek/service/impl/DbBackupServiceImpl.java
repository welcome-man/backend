package com.gloudtek.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gloudtek.dao.DbBackupDao;
import com.gloudtek.entity.DbBackupEntity;
import com.gloudtek.service.DbBackupService;

@Service("dbBackupService")
public class DbBackupServiceImpl implements DbBackupService {
	
	@Autowired
	private DbBackupDao dbBackupDao;
	
	public List<DbBackupEntity> queryByCondition(Map<String,Object> map){
		return dbBackupDao.queryByCondition(map);
	}

	public Integer queryTotalByCondition(Map<String, Object> map) {
		return dbBackupDao.queryTotalByCondition(map);
	}

	public void save(DbBackupEntity dbBackupEntity) {
		dbBackupDao.save(dbBackupEntity);
	}

	public DbBackupEntity queryById(String id) {
		return dbBackupDao.queryObject(id);
	}

	public List<DbBackupEntity> getRecordByCondition(Map<String, Object> map) {
		return dbBackupDao.getRecordByCondition(map);
	}

	public void deleteBatch(Object ids[]) {
		dbBackupDao.deleteBatch(ids);
	}
	
}
