package com.gloudtek.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gloudtek.entity.SysConfDetailEntity;
import com.gloudtek.entity.SysConfEntity;
import com.gloudtek.entity.SysConfigEntity;

/**
 * 系统配置信息
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月4日 下午6:46:16
 */
public interface SysConfigDao extends BaseDao<SysConfigEntity> {

	/**
	 * 根据key，查询value
	 */
	String queryByKey(String paramKey);

	/**
	 * 根据key，更新value
	 */
	int updateValueByKey(@Param("key") String key, @Param("value") String value);

	List<SysConfigEntity> getSysConfigListByKey(Map<String, Object> map);

	List<SysConfEntity> finaAllKey(Map<String, Object> map);

	public int gettotal(Map<String, Object> map);

	SysConfEntity getInfo(Long id);

	void addConfig(SysConfEntity sysConfEntity);

	void updateConfig(SysConfEntity sysConfEntity);

	void delConfig(Long id);

	List<SysConfDetailEntity> getDetail(Map<String, Object> map);

	int getDetailtotal(Map<String, Object> map);

	SysConfDetailEntity getDetailInfo(Long id);

	void addConfigDetail(SysConfDetailEntity sysConfDetailEntity);

	void delConfigDetail(Long id);

	void updateConfigDetail(SysConfDetailEntity sysConfDetailEntity);

}
