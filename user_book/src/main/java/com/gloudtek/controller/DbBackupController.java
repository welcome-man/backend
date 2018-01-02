package com.gloudtek.controller;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gloudtek.entity.DbBackupEntity;
import com.gloudtek.entity.DbBackupSearchBean;
import com.gloudtek.entity.JdbcBean;
import com.gloudtek.service.DbBackupService;
import com.gloudtek.task.MysqlBackupTask;
import com.gloudtek.utils.FileUtil;
import com.gloudtek.utils.PageUtils;
import com.gloudtek.utils.R;

@Controller
@RequestMapping("/api/db")
public class DbBackupController extends AbstractController {
	
    @Autowired
    private DbBackupService dbBackupService;
    
	//列表
	@ResponseBody
	@RequestMapping("/list")
	@RequiresPermissions("db:backup:list")
	public R list(@RequestBody DbBackupSearchBean dbBackupSearchBean){
		Map<String, Object> map = new HashMap<>();
		map.put("offset", (dbBackupSearchBean.getCurrentPage() - 1) * dbBackupSearchBean.getPageSize());
		map.put("limit", dbBackupSearchBean.getPageSize());
		List<DbBackupEntity> list = dbBackupService.queryByCondition(map);
		int total = dbBackupService.queryTotalByCondition(map);
		PageUtils pageUtil = new PageUtils(list, total, dbBackupSearchBean.getPageSize(), dbBackupSearchBean.getCurrentPage());
		return R.ok().put("page", pageUtil);
	}
	
	//备份数据库(GET)
	@ResponseBody
	@RequestMapping("/backup")
	@RequiresPermissions("db:backup:backup")
	public R backup(){
		try {
			DbBackupEntity dbBackupEntity = new DbBackupEntity();
			dbBackupEntity.setId(UUID.randomUUID().toString());
			dbBackupEntity.setIsdelete(0);
			dbBackupEntity.setCreateBy(getUser().getUsername());
			dbBackupEntity.setCreateTime(new Date());
			String fileName = new SimpleDateFormat(MysqlBackupTask.SQL_BACKUP_PREFIX_FORMAT).format(new Date())+"_backup";
			String filePath = MysqlBackupTask.getFilePath(fileName+".sql");
			dbBackupEntity.setFileName(fileName);
			dbBackupEntity.setUrl(filePath);
			/*
			 * 动态获取数据库配置
			 */
			JdbcBean j = new JdbcBean();
			 Properties prop = new Properties();//读取properties 文件所用的类  
				try {   
		            InputStream in = DbBackupController.class.getResourceAsStream("db.properties");  
		            prop.load(in); // /加载属性列表  
		            String [] a = prop.getProperty("jdbc.url").split("/");
		            j.setIp(a[2].split(":")[0]); 
		            j.setDb(a[3].trim().split("[?]")[0]);
		            j.setPort(Integer.parseInt(a[2].split(":")[1]));
		            j.setUsername(prop.getProperty("jdbc.username"));
		            j.setPassword(prop.getProperty("jdbc.password"));
		            in.close();  
		        } catch (Exception e) {  
		            System.out.println(e.getMessage());  
		        }  

			boolean exportFlag = MysqlBackupTask.executeExportCommond(j,filePath);
			if(exportFlag){
				dbBackupService.save(dbBackupEntity);
				return R.ok("备份成功");
			}else{
				return R.error("备份失败");
			}
		} catch (Exception e) {
			return R.error("备份失败");
		}
	}
	
	//恢复数据库
	@ResponseBody
	@RequestMapping("/returnBack")
	@RequiresPermissions("db:backup:returnBack")
	public R returnBack(@RequestBody DbBackupEntity dbBackupEntity){
		try {
			dbBackupEntity = dbBackupService.queryById(dbBackupEntity.getId());
			if(dbBackupEntity==null||dbBackupEntity.getUrl()==null){
				return R.error("所要恢复的文件不存在");
			}
			boolean importFlag = MysqlBackupTask.executeImportCommond(new JdbcBean(),dbBackupEntity.getUrl());
			if(importFlag){
				return R.ok("恢复成功");
			}else{
				return R.error("恢复失败");
			}
		} catch (Exception e) {
			return R.error("恢复失败");
		}
	}
	
	//导出数据库
	@ResponseBody
	@RequestMapping("/exportDb/{id}")
	@RequiresPermissions("db:backup:exportDb")
	public void exportDb(HttpServletRequest request,HttpServletResponse response,@PathVariable(value="id") String id){
		try {
			DbBackupEntity dbBackupEntity = dbBackupService.queryById(id);
			if(dbBackupEntity!=null&&dbBackupEntity.getUrl()!=null){
				File file = new File(dbBackupEntity.getUrl());
				if(file.exists()){
					response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(dbBackupEntity.getFileName()+".sql","UTF-8"));  
					response.setContentType("application/OCTET-STREAM;charset=UTF-8");
					OutputStream os = response.getOutputStream();
					FileUtil.downloadFile(os,file);
					response.flushBuffer();
				}
			}
		} catch (Exception e) {
			
		}
	}
	
	//初始化数据库
	@ResponseBody
	@RequestMapping("/initDb")
	@RequiresPermissions("db:backup:initDb")
	public R initDb(HttpServletRequest request,HttpServletResponse response){
		try {
			String filePath1 = MysqlBackupTask.WINDOWS_UPLOAD_PATH+"init.sql";
			String filePath2 = MysqlBackupTask.LINUX_UPLOAD_PATH+"init.sql";
			File file1 = new File(filePath1);
			File file2 = new File(filePath2);
			if(!file1.exists()){
				if(!file2.exists()){
					return R.error("初始化失败");
				}else{
					boolean importFlag = MysqlBackupTask.executeImportCommond(new JdbcBean(),filePath2);
					if(importFlag){
						MysqlBackupTask.deleteFiles(MysqlBackupTask.LINUX_UPLOAD_PATH);
						return R.ok("初始化成功");
					}else{
						return R.error("初始化失败");
					}
				}
			}else{
				boolean importFlag = MysqlBackupTask.executeImportCommond(new JdbcBean(),filePath1);
				if(importFlag){
					MysqlBackupTask.deleteFiles(MysqlBackupTask.WINDOWS_UPLOAD_PATH);
					return R.ok("初始化成功");
				}else{
					return R.error("初始化失败");
				}
			}
		}catch(Exception e){
			return R.error("初始化失败");
		}
	}
	
}
