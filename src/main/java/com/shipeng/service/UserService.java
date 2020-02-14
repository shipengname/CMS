package com.shipeng.service;


import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Comment;
import com.shipeng.bean.User;
import com.shipeng.vo.UserVO;

public interface UserService {
	public PageInfo<User> getUserList(String username,Integer pageNum,Integer pageSize);

	public int updateLocked(User user);

	public User getOne(int i);

	public User login(User user);

	public int getCountByUserName(String username);

	public void addUser(UserVO user);

	public int advice(Comment comment);
}
