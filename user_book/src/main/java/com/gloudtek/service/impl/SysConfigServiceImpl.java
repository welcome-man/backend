package com.gloudtek.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gloudtek.dao.SysConfigDao;
import com.gloudtek.entity.SysConfDetailEntity;
import com.gloudtek.entity.SysConfEntity;
import com.gloudtek.entity.SysConfigEntity;
import com.gloudtek.service.SysConfigService;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigDao sysConfigDao;

	@Override
	public void save(SysConfigEntity config) {
		sysConfigDao.save(config);
	}

	@Override
	public void update(SysConfigEntity config) {
		sysConfigDao.update(config);
	}

	@Override
	public void updateValueByKey(String key, String value) {
		sysConfigDao.updateValueByKey(key, value);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		sysConfigDao.deleteBatch(ids);
	}

	@Override
	public List<SysConfigEntity> queryList(Map<String, Object> map) {
		return sysConfigDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysConfigDao.queryTotal(map);
	}

	@Override
	public SysConfigEntity queryObject(Long id) {
		return sysConfigDao.queryObject(id);
	}

	@Override
	public String getValue(String key, String defaultValue) {
		String value = sysConfigDao.queryByKey(key);
		if (StringUtils.isBlank(value)) {
			return defaultValue;
		}
		return value;
	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) throws Exception {
		String value = getValue(key, null);
		if (StringUtils.isNotBlank(value)) {
			return JSON.parseObject(value, clazz);
		}

		return clazz.newInstance();
	}

	@Override
	public List<SysConfigEntity> getSysConfigListByKey(Map<String, Object> map) {
		return sysConfigDao.getSysConfigListByKey(map);
	}

	@Override
	public List<SysConfEntity> findAllKey(Map<String, Object> map) {

		return sysConfigDao.finaAllKey(map);
	}

	@Override
	public int getTotal(Map<String, Object> map) {

		return sysConfigDao.gettotal(map);
	}

	@Override
	public SysConfEntity findkey(Long id) {

		return sysConfigDao.getInfo(id);
	}

	@Override
	public List<SysConfDetailEntity> getDetailList(Map<String, Object> map) {

		return sysConfigDao.getDetail(map);
	}

	@Override
	public int getDetailTotal(Map<String, Object> map) {

		return sysConfigDao.getDetailtotal(map);
	}

	@Override
	public void delConfig(Long id) {
		sysConfigDao.delConfig(id);
		
		
	}
}
