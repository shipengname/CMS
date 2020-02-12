package com.bawei.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bawei.bean.Article;
import com.bawei.bean.Comment;
import com.bawei.bean.User;
import com.bawei.vo.UserVO;

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
