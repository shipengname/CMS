package com.shipeng.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.shipeng.bean.Article;
import com.shipeng.bean.Comment;
import com.shipeng.bean.User;
import com.shipeng.vo.UserVO;

public interface UserDao {
	
	public List<User> getUserList(@Param("username")String username);
	
	public int updateLocked(User user);
	
	public User getOne(@Param("id")Integer id);

	public User login(User user);

	public int getCountByUserName(@Param("username")String username);

	public void addUser(UserVO user);
	
	public Article getOneArticle(Integer id);

	public int advice(Comment comment);
}
