package com.gloudtek.service;

import java.util.List;
import java.util.Map;

import com.gloudtek.entity.SysConfDetailEntity;
import com.gloudtek.entity.SysConfEntity;
import com.gloudtek.entity.SysConfigEntity;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:49:01
 */
public interface SysConfigService {
	
	/**
	 * 保存配置信息
	 */
	public void save(SysConfigEntity config);
	
	/**
	 * 更新配置信息
	 */
	public void update(SysConfigEntity config);
	
	/**
	 * 根据key，更新value
	 */
	public void updateValueByKey(String key, String value);
	
	
	/**
	 * 删除配置信息
	 */
	public void deleteBatch(Long[] ids);
	
	/**
	 * 获取List列表
	 */
	public List<SysConfigEntity> queryList(Map<String, Object> map);
	/**
	 * 获取总记录数
	 */
	public int queryTotal(Map<String, Object> map);
	
	public SysConfigEntity queryObject(Long id);
	
	/**
	 * 根据key，获取配置的value值
	 * 
	 * @param key           key
	 * @param defaultValue  缺省值
	 */
	public String getValue(String key, String defaultValue);
	
	/**
	 * 根据key，获取value的Object对象
	 * @param key    key
	 * @param clazz  Object对象
	 */
	public <T> T getConfigObject(String key, Class<T> clazz) throws Exception;
	
	public List<SysConfigEntity> getSysConfigListByKey(Map<String, Object> map);
	
	public List<SysConfEntity> findAllKey(Map<String, Object> map);
	
	public int  getTotal(Map<String, Object> map);

	public SysConfEntity findkey(Long id);

	public List<SysConfDetailEntity> getDetailList(Map<String, Object> map);

	public int getDetailTotal(Map<String, Object> map);

	public void delConfig(Long id);

	
}
