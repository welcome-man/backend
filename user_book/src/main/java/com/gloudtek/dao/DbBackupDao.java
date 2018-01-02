package com.gloudtek.dao;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.DbBackupEntity;

public interface DbBackupDao extends BaseDao<DbBackupEntity> {
	
	List<DbBackupEntity> queryByCondition(Map<String,Object> map);
	
	Integer queryTotalByCondition(Map<String,Object> map);
	
	List<DbBackupEntity> getRecordByCondition(Map<String,Object> map);
	
}
