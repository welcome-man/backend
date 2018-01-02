package com.gloudtek.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.DbBackupEntity;

public interface DbBackupService {
	
	List<DbBackupEntity> queryByCondition(Map<String,Object> map);
	
	Integer queryTotalByCondition(Map<String,Object> map);
	
	void save(DbBackupEntity dbBackupEntity);
	
	DbBackupEntity queryById(String id);
	
	List<DbBackupEntity> getRecordByCondition(Map<String,Object> map);
	
	void deleteBatch(Object ids[]);
	
}
