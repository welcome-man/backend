package com.gloudtek.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gloudtek.entity.ScheduleJobLogEntity;
import com.gloudtek.service.ScheduleJobLogService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;
import com.gloudtek.utils.ScheUtil;

/**
 * 定时任务日志
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月1日 下午10:39:52
 */
@RestController
@RequestMapping("/sys/scheduleLog")
public class ScheduleJobLogController {
	
	@Autowired
	private ScheduleJobLogService scheduleJobLogService;
	
	//获取定时任务日志列表
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:log")
	public R list(@RequestBody ScheUtil scheUtil){
		Map<String, Object> map = new HashMap<>();
		map.put("jobId", scheUtil.getJobId());
		map.put("offset", (scheUtil.getCurrentPage() - 1) * scheUtil.getPageSize());
		map.put("limit", scheUtil.getPageSize());
		//查询列表数据
		List<ScheduleJobLogEntity> jobList = scheduleJobLogService.queryList(map);
		int total = scheduleJobLogService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(jobList, total, scheUtil.getPageSize(), scheUtil.getCurrentPage());
		return R.ok().put("page", pageUtil);
	}
	
	//单条定时任务日志信息
	@RequestMapping("/info/{logId}")
	public R info(@PathVariable("logId") Long logId){
		ScheduleJobLogEntity log = scheduleJobLogService.queryObject(logId);
		return R.ok().put("log", log);
	}
	
}
