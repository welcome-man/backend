package com.gloudtek.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gloudtek.entity.LoginParam;
import com.gloudtek.entity.SysMenuEntity;
import com.gloudtek.entity.SysUserEntity;
import com.gloudtek.service.SysMenuService;
import com.gloudtek.service.SysUserService;
import com.gloudtek.utils.MailSendUtil;
import com.gloudtek.utils.R;
import com.gloudtek.utils.ShiroUtils;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

/**
 * 登录相关
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class SysLoginController extends AbstractController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private Producer producer;
	
	//获取验证码
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws ServletException, IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}
	
	//用户登录
	@ResponseBody
//	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	@RequestMapping("/sys/login")
	public R login(@RequestBody LoginParam loginParam) throws IOException {
		
		String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
		if(!loginParam.getCaptcha().equalsIgnoreCase(kaptcha)){
			return R.error("验证码不正确");
		}
		List<String> permsList = null;
		try{
			Subject subject = ShiroUtils.getSubject();
			//sha256加密
		    String password = new Sha256Hash(loginParam.getPassword()).toHex();
			UsernamePasswordToken token = new UsernamePasswordToken(loginParam.getUsername(), password);
			subject.login(token);
			SysUserEntity user = sysUserService.queryByUserName(loginParam.getUsername());
			long userId = user.getUserId();
			//全系统统帅，拥有最高权限（暂不开放）
			if(userId == 0){
				List<SysMenuEntity> menuList = sysMenuService.queryList(new HashMap<String, Object>());
				permsList = new ArrayList<>(menuList.size());
				for(SysMenuEntity menu : menuList){
					permsList.add(menu.getPerms());
				}
			}else{
				permsList = sysUserService.queryAllPerms(userId);
			}
			//用户权限列表
			Set<String> permsSet = new HashSet<String>();
			for(String perms : permsList){
				if(StringUtils.isBlank(perms)){
					continue;
				}
				permsSet.addAll(Arrays.asList(perms.trim().split(",")));
			}
//			ShiroUtils.getSession().setTimeout(1*60*1000);
		}catch (UnknownAccountException e) {
			return R.error(e.getMessage());
		}catch (IncorrectCredentialsException e) {
			return R.error(e.getMessage());
		}catch (LockedAccountException e) { 
			return R.error(e.getMessage());
		}catch (AuthenticationException e) {
			return R.error("账户验证失败");
		}
		//return R.ok();
		/** 为配合前端所提供的权限列表结果集 start */
		Map<String,String> map = new HashMap<>();
		for(int i=0;i<permsList.size();i++){
			String value = permsList.get(i);
			if((value!=null)&&(!("".equals(value)))){
				map.put(value, value);
			}
		}
		permsList = new ArrayList<>();
		Set<String> set = map.keySet();
		for(String str:set){
			for(String str2:str.split(",")){
				permsList.add(str2);
			}
		}
		return R.ok().put("premissionList", permsList);
		/** 丛海波要求为配合前端所提供的权限列表结果集 end */
	}
	
	//用户退出
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout() {
		ShiroUtils.logout();
		return "redirect:login.html";
	}
	
	//修改登录用户密码（新旧密码更改）
	@ResponseBody
	@RequestMapping("/sys/user/modifyPassword")
	public R password(String password, String newPassword){
		if(StringUtils.isBlank(newPassword)){
			return R.error("新密码不为能空");
		}
		//sha256加密
		password = new Sha256Hash(password).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword).toHex();
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}
		//退出
		ShiroUtils.logout();
		return R.ok();
	}
	
	//用户管理-重置密码
	@ResponseBody
	@RequestMapping(value ="/sys/user/ResetPassword", method = RequestMethod.POST)
	public R ResetPassword(Long id, String newPassword){
		System.out.println("--------"+id+"+++++++++++++"+newPassword);
		if(newPassword == ""){
			return R.error("密码不能为空");
		}
			//sha256加密
			newPassword = new Sha256Hash(newPassword).toHex();
			//更新密码
			int count = sysUserService.ResetPassword(id, newPassword);
			if(count == 0){
				return R.error("密码修改不成功");
			}	
		return R.ok("密码修改成功");
	}
	
	//重置登录用户密码（邮箱更改）
	@RequestMapping("/sys/user/resetPassword/{userName}")
	public R resetPassword(@PathVariable String userName) throws Exception {
		SysUserEntity sysUserEntity = sysUserService.queryByUserName(userName);
		if(sysUserEntity==null){
			return R.error("用户名不存在");
		}
		if(sysUserEntity.getEmail()==null||"".equals(sysUserEntity.getEmail())){
			return R.error("邮箱不存在");
		}
		String sendTime = String.valueOf(System.currentTimeMillis());
		String password = sysUserEntity.getPassword();
		String sid = new String(Base64.getEncoder().encode((userName+","+password+","+sendTime).getBytes("utf-8")));
		MailSendUtil.sendMail(sysUserEntity.getEmail(), "重置密码", "请点击下面的链接重置密码，有效时间为30分钟："+sid);
		return R.ok("重置密码链接已发送至邮箱，请及时修改");
	}
	
	//重置登录用户密码（邮箱更改）
	@RequestMapping("/sys/user/resetPasswordForSubmit/{sid}/{newPassword}")
	public R resetPasswordForSubmit(@PathVariable String sid,@PathVariable String newPassword) throws Exception {
		String secret[] = new String(Base64.getDecoder().decode(sid)).split(",");
		SysUserEntity sysUserEntity = sysUserService.queryByUserName(secret[0]);
		if(sysUserEntity==null){
			return R.error("用户名不存在");
		}
		if(sysUserEntity.getEmail()==null||"".equals(sysUserEntity.getEmail())){
			return R.error("邮箱不存在");
		}
		if(new Date().getTime()-Long.parseLong(secret[2])>30*60*1000){
			return R.error("链接超时");
		}
		sysUserService.updatePassword(sysUserEntity.getUserId(), secret[1], newPassword);
		ShiroUtils.logout();
		return R.ok("密码修改成功");
	}
	
	//session超时
	@RequestMapping(value = "sessionOut", method = RequestMethod.GET)
	@ResponseBody
	public R sessionOut() {
		return R.error(401,"session超时");
	}
	
}
