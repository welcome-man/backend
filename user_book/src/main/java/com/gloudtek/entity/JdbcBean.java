package com.gloudtek.entity;

public class JdbcBean {
	
	private int port = 3306;
	
	private String ip = "rm-bp1bv3ltisrw540c1o.mysql.rds.aliyuncs.com";
	
	private String username = "root";
	
	private String password = "15850046261Lm";
	
	private String db = "user_book";

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getDb() {
		return db;
	}

	public void setDb(String db) {
		this.db = db;
	}
	
	
}
