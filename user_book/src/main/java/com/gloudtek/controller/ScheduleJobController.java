package com.gloudtek.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gloudtek.entity.ScheduleJobEntity;
import com.gloudtek.service.ScheduleJobService;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;
import com.gloudtek.utils.RRException;
import com.gloudtek.utils.ScheUtil;

/**
 * 定时任务
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月28日 下午2:16:40
 */
@RestController
@RequestMapping("/sys/schedule")
public class ScheduleJobController {
	
	@Autowired
	private ScheduleJobService scheduleJobService;
	
	//定时任务列表
	@RequestMapping("/list")
	@RequiresPermissions("sys:schedule:list")
	public R list(@RequestBody ScheUtil scheUtil){
		Map<String, Object> map = new HashMap<>();
		map.put("beanName", scheUtil.getBeanName());
		map.put("offset", (scheUtil.getCurrentPage() - 1) * scheUtil.getPageSize());
		map.put("limit", scheUtil.getPageSize());
		//查询列表数据
		List<ScheduleJobEntity> jobList = scheduleJobService.queryList(map);
		int total = scheduleJobService.queryTotal(map);
		PageUtils pageUtil = new PageUtils(jobList, total, scheUtil.getPageSize(), scheUtil.getCurrentPage());
		return R.ok().put("page", pageUtil);
	}
	
	//保存定时任务
	@RequestMapping("/save")
	@RequiresPermissions("sys:schedule:save")
	public R save(@RequestBody ScheduleJobEntity scheduleJob){
		//数据校验
		verifyForm(scheduleJob);
		scheduleJobService.save(scheduleJob);
		return R.ok();
	}
	
	//修改定时任务
	@RequestMapping("/update")
	@RequiresPermissions("sys:schedule:update")
	public R update(@RequestBody ScheduleJobEntity scheduleJob){
		//数据校验
		verifyForm(scheduleJob);
		scheduleJobService.update(scheduleJob);
		return R.ok();
	}
	
	//删除定时任务
	@RequestMapping("/delete")
	@RequiresPermissions("sys:schedule:delete")
	public R delete(@RequestBody Long[] jobIds){
		scheduleJobService.deleteBatch(jobIds);
		return R.ok();
	}
	
	//修改时获取单条定时任务信息
	@RequestMapping("/info/{jobId}")
	@RequiresPermissions("sys:schedule:info")
	public R info(@PathVariable("jobId") Long jobId){
		ScheduleJobEntity schedule = scheduleJobService.queryObject(jobId);
		return R.ok().put("schedule", schedule);
	}
	
	//暂停定时任务
	@RequestMapping("/pause")
	@RequiresPermissions("sys:schedule:pause")
	public R pause(@RequestBody Long[] jobIds){
		scheduleJobService.pause(jobIds);
		return R.ok();
	}
	
	//恢复定时任务
	@RequestMapping("/resume")
	@RequiresPermissions("sys:schedule:resume")
	public R resume(@RequestBody Long[] jobIds){
		scheduleJobService.resume(jobIds);
		return R.ok();
	}
	
	//立即执行任务
	@RequestMapping("/run")
	@RequiresPermissions("sys:schedule:run")
	public R run(@RequestBody Long[] jobIds){
		scheduleJobService.run(jobIds);
		return R.ok();
	}
	
	//验证参数是否正确
	private void verifyForm(ScheduleJobEntity scheduleJob){
		if(StringUtils.isBlank(scheduleJob.getBeanName())){
			throw new RRException("bean名称不能为空");
		}
		if(StringUtils.isBlank(scheduleJob.getMethodName())){
			throw new RRException("方法名称不能为空");
		}
		if(StringUtils.isBlank(scheduleJob.getCronExpression())){
			throw new RRException("cron表达式不能为空");
		}
	}
	
}
