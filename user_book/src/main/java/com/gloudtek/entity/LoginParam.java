package com.gloudtek.entity;

import java.io.Serializable;
/***
 * 登入页面验证
 * @author Administrator
 *
 */
public class LoginParam implements Serializable {

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private String captcha;
	private String account;
	

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

}
