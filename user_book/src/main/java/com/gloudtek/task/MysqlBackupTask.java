package com.gloudtek.task;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.gloudtek.entity.DbBackupEntity;
import com.gloudtek.entity.JdbcBean;
import com.gloudtek.service.DbBackupService;

public class MysqlBackupTask {
	
    public static final String WINDOWS_UPLOAD_PATH = "F:/data/user_book/dbfiles/";
    
    public static final String LINUX_UPLOAD_PATH = "/data/user_book/dbfiles/";
    
    public static final String SQL_BACKUP_PREFIX_FORMAT = "yyyyMMddHHmmss";
    
    private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    private DbBackupService dbBackupService;

	//每天23点执行一次(0 0 23 * * ?);每一分钟执行一次(0 */1 * * * ?)
	//本方法实现两个功能(1:每天23点定时自动备份;2:只保留最近20天的备份记录)
    public void schedulerSqlBackup(){
    	try {
    		DbBackupEntity dbBackupEntity = new DbBackupEntity();
    		dbBackupEntity.setId(UUID.randomUUID().toString());
    		dbBackupEntity.setIsdelete(0);
    		dbBackupEntity.setCreateBy("system");
    		dbBackupEntity.setCreateTime(new Date());
    		String fileName = new SimpleDateFormat(SQL_BACKUP_PREFIX_FORMAT).format(new Date())+"_backup";
    		String filePath = getFilePath(fileName+".sql");
    		dbBackupEntity.setFileName(fileName);
    		dbBackupEntity.setUrl(filePath);
    		boolean exportFlag = executeExportCommond(new JdbcBean(),filePath);//执行备份
    		if(exportFlag){
    			dbBackupService.save(dbBackupEntity);//保存备份信息到数据库
    			logger.info("定时备份执行成功");
    		}else{
    			logger.error("定时备份执行失败");
    		}
		} catch (Exception e) {
			logger.error("定时备份发生异常，异常信息为："+e.getMessage());
		}
    	//即使定时备份失败,依然需要执行只保留20天的备份清理计划
    	//获取超过20天的所有备份记录,删除数据库数据并且删除备份文件
    	try {
    		List<DbBackupEntity> list = dbBackupService.getRecordByCondition(new HashMap<String,Object>());
        	if(list!=null&&list.size()!=0){
        		Object ids[] = new Object[list.size()]; 
        		for(int i=0;i<list.size();i++){
        			DbBackupEntity each = list.get(i);
        			String url = each.getUrl();
        			new File(url).delete();
        			ids[i] = each.getId();
        		}
        		dbBackupService.deleteBatch(ids);
        	}
        	logger.info("备份数据清理执行成功");
		} catch (Exception e) {
			logger.error("备份数据清理执行失败，失败原因为："+e.getMessage());
		}
    }
    
	//执行导出命令
	public static boolean executeExportCommond(JdbcBean jdbcBean,String filePath) throws Exception {
		String sql = "mysqldump -P "+jdbcBean.getPort()+" -h "+jdbcBean.getIp()+
				     " -u "+jdbcBean.getUsername()+" -p"+jdbcBean.getPassword()+
				     " "+jdbcBean.getDb()+" --default-character-set=utf8 "+
				     "--lock-tables=false --result-file="+filePath;
		Process process = Runtime.getRuntime().exec(sql);
		if(process.waitFor()==0){
			return true;
		}
		return false;
	}
	
	//执行导入命令
	public static boolean executeImportCommond(JdbcBean jdbcBean,String filePath) throws Exception {
		String sql_1 = "mysql -P "+jdbcBean.getPort()+" -h "+jdbcBean.getIp()+
				       " -u "+jdbcBean.getUsername()+" -p"+jdbcBean.getPassword()+
				       " --default-character-set=utf8";
		String sql_2 = "use "+jdbcBean.getDb();
		String sql_3 = "source "+filePath;
		Process process = Runtime.getRuntime().exec(sql_1);
		OutputStream os = process.getOutputStream();  
	    OutputStreamWriter writer = new OutputStreamWriter(os);  
	    writer.write(sql_2 + "\r\n" + sql_3);  
	    writer.flush();  
	    writer.close();  
	    os.close();
	    if(process.waitFor()==0){
			return true;
		}
		return false;
	}
	
	//获得文件路径
	public static String getFilePath(String fileName){
		String os = System.getProperty("os.name");  
		String rootPath;
		String filPath;
		if(os.toLowerCase().startsWith("win")){  
			rootPath = WINDOWS_UPLOAD_PATH;
		}else{
			rootPath = LINUX_UPLOAD_PATH;
		}
		if(!new File(rootPath).exists()){
			new File(rootPath).mkdirs();
		}
		if(fileName==null){
			filPath = rootPath+new SimpleDateFormat(SQL_BACKUP_PREFIX_FORMAT).format(new Date())+"_backup.sql";
		}else{
			filPath = rootPath+fileName;
		}
		return filPath;
	}
	
	public static void deleteFiles(String fileRootPath){
		File file = new File(fileRootPath);
		if(file.isDirectory()){
			File files[] = file.listFiles();
			for(int i=0;i<files.length;i++){
				File eachFile = files[i];
				if(!"init.sql".equals(eachFile.getName())){
					eachFile.delete();
				}
			}
		}
	}
	
}
