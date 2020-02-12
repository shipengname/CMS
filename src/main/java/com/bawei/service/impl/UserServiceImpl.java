package com.bawei.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bawei.bean.Comment;
import com.bawei.bean.User;
import com.bawei.dao.UserDao;
import com.bawei.service.UserService;
import com.bawei.vo.UserVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao adao;

	@Override
	public PageInfo<User> getUserList(String username,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<User> list=adao.getUserList(username);
		PageInfo<User> page = new PageInfo<>(list);
		return page;
	}
	public int updateLocked(User user) {
		return adao.updateLocked(user);
	}
	public User getOne(int id) {
		return adao.getOne(id);
	}
	@Override
	public User login(User user) {
		return adao.login(user);
	}
	@Override
	public int getCountByUserName(String username) {
		// TODO Auto-generated method stub
		return adao.getCountByUserName(username);
	}
	@Override
	public void addUser(UserVO user) {
		adao.addUser(user);
	}
	@Override
	public int advice(Comment comment) {
		return adao.advice(comment);
	}
}
