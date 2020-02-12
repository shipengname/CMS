package com.bawei.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.bawei.bean.enums.Gender;

public class User implements Serializable{
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	    
	private static final long serialVersionUID = -1362560461482958246L;
	private Integer id;
	private String username;
	private String password;
	private String nickname;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date   birthday;
	private Gender gender;
	private Integer locked;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updated;
	private String role;
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", nickname=" + nickname
				+ ", birthday=" + birthday + ", gender=" + gender + ", locked=" + locked + ", created=" + created
				+ ", updated=" + updated + ", role=" + role + "]";
	}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Date getUpdated() {
		return updated;
	}
	public void setUpdated(Date updated) {
		this.updated = updated;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}
