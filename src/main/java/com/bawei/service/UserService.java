package com.bawei.service;


import com.bawei.bean.Comment;
import com.bawei.bean.User;
import com.bawei.vo.UserVO;
import com.github.pagehelper.PageInfo;

public interface UserService {
	public PageInfo<User> getUserList(String username,Integer pageNum,Integer pageSize);

	public int updateLocked(User user);

	public User getOne(int i);

	public User login(User user);

	public int getCountByUserName(String username);

	public void addUser(UserVO user);

	public int advice(Comment comment);
}
