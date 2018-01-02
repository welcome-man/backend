package com.gloudtek.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gloudtek.entity.AttachmentManageEntity;
import com.gloudtek.service.AttachmentManageService;
import com.gloudtek.utils.R;

@Controller
@RequestMapping("/api/v1/video")
public class VideoController  extends  AbstractController{
	public final String WINDOWS_UPLOAD_PATH = "C:/software/apache-tomcat-8.5.23/wtpwebapps/user_book/video/";

	public final String LINUX_UPLOAD_PATH = "/home/webserver/tomcat-8.5.23/webapps/user_book/video/";
	@Autowired
	private AttachmentManageService attachmentManageService;

	/* 附件上传 */
	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public R upload(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("file") MultipartFile multipartFile) {
		try {
			String uploadFileName = multipartFile.getOriginalFilename();// 得到上传文件的文件名称
			String os = System.getProperty("os.name");
			String rootPath;
			String filPath;
			if (os.toLowerCase().startsWith("win")) {
				rootPath = WINDOWS_UPLOAD_PATH;
			} else {
				rootPath = LINUX_UPLOAD_PATH;
			}
			try {
				String split[] = uploadFileName.split("\\.");
				filPath = rootPath + UUID.randomUUID().toString() + "." + split[split.length - 1];
			} catch (Exception e) {
				filPath = rootPath + UUID.randomUUID().toString()+ "." + uploadFileName;
			}
			File file = new File(rootPath);
			if (!file.exists()) {
				file.mkdirs();
			}
			InputStream is = multipartFile.getInputStream();
			FileOutputStream fos = new FileOutputStream(filPath);
			byte[] buffer = new byte[1024];
			int length = 0;
			while ((length = is.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}
			is.close();
			fos.close();
			AttachmentManageEntity att = new AttachmentManageEntity();
			String attachmentManageId = UUID.randomUUID().toString();
			att.setId(attachmentManageId);
			att.setAttachmentName(uploadFileName);
			att.setAttachmentUrl(filPath);
			att.setFileName(uploadFileName);
			attachmentManageService.save(att);
			return R.ok("文件上传成功").put("attachmentManageId", attachmentManageId).put("url", filPath);
		} catch (Exception e) {
			return R.error("文件上传失败");
		}
	}
	
	
	
	/**
	 * 下载附件
	 * 
	 * @param req
	 * @param response
	 */
	@RequestMapping(value = "/download/{attachmentManageId}", produces = { "application/json;charset=UTF-8" })
	public R testdownLoad(HttpServletRequest req, HttpServletResponse response,
			@PathVariable("attachmentManageId") String attachmentManageId) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String downloadURL = attachmentManageService.queryObject(attachmentManageId).getAttachmentUrl();
			bis = new BufferedInputStream(new FileInputStream(new File(downloadURL)));
			String fileName = attachmentManageService.queryObject(attachmentManageId).getFileName();
			if (!new File(fileName).exists()) {
				new File(fileName).mkdirs();
			}
			int length = 0;
			byte[] buffer = new byte[8192];
			response.reset();
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "utf-8")); // 设置响应头名称
			response.setContentType("application/octet-stream; charset=UTF-8"); // 设置响应返回的格式和编码
			bos = new BufferedOutputStream(response.getOutputStream());
			while ((length = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, length);
			}

			return R.ok("附加成功下载");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return R.ok("附加成功失败");
		} finally {

			try {
				bis.close();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("下载异常");
			}

		}
	}
}
