package com.gloudtek.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtil {
    
	//写文件
	public static void writeFile(InputStream inputStream,File file) throws Exception{
		OutputStream outputStream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];//1KB
		while (inputStream.read(buffer) != -1) {
			outputStream.write(buffer);
		}
		outputStream.close();
		inputStream.close();
	}
	
	//下文件
	public static void downloadFile(OutputStream outputStream,File file) throws Exception{
		InputStream inputStream = new FileInputStream(file);
		byte[] buffer = new byte[1024];//1KB
		while (inputStream.read(buffer) != -1) {
			outputStream.write(buffer);
		}
		outputStream.close();
		inputStream.close();
	}
	
}
