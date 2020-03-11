package com.shipeng.bean;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class Favorite implements Serializable{
	
	    /**
	    * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	    */
	    
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String text;
	private String url;
	private Integer user_id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date created;
	private User user;
	@Override
	public String toString() {
		return "Favorite [id=" + id + ", text=" + text + ", url=" + url + ", user_id=" + user_id + ", created="
				+ created + ", user=" + user + "]";
	}
	public Favorite() {
		super();
	}
	public Favorite(Integer id, String text, String url, Integer user_id, Date created, User user) {
		super();
		this.id = id;
		this.text = text;
		this.url = url;
		this.user_id = user_id;
		this.created = created;
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
