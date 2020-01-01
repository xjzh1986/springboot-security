package com.reach.deepev.auth.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6311634417785743132L;
	private String msg;
	private String username;
	private String token;
	private String i18n;
	private List<String> roles = new ArrayList<String>();
	private int status;
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getI18n() {
		return i18n;
	}

	public void setI18n(String i18n) {
		this.i18n = i18n;
	}
}
