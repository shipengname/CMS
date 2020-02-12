package com.bawei.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bawei.bean.Comment;
import com.bawei.bean.User;
import com.bawei.service.UserService;
import com.bawei.utils.CMSJsonUtil;
import com.bawei.vo.UserVO;
import com.bw.utils.StringUtil;
import com.github.pagehelper.PageInfo;
@RequestMapping("user")
@Controller
public class UserController {
	@Autowired
	private UserService service;
	
	
	@GetMapping("login")
	public String toLogin() {
		return "index/login";
	}

	//注册
	@ResponseBody
	@PostMapping("reg")
	public Object reg(UserVO user) {
		CMSJsonUtil cju = new CMSJsonUtil();
		boolean uname = StringUtil.isNotEmpty(user.getUsername());
		boolean upwd = StringUtil.isNotEmpty(user.getPassword());
		boolean urpwd = StringUtil.isNotEmpty(user.getRePassword());
		//判断为空
		if(!uname||!upwd||!urpwd) {
			cju.setMsg("用户名密码确认密码不能为空");
			return cju;
		}
		//验证俩次密码是否一致
		if(!user.getPassword().equals(user.getRePassword())) {
			cju.setMsg("俩次密码输入不一致");
			return cju;
		}
		//用户唯一
		int count=service.getCountByUserName(user.getUsername());
		if(count>0) {
			cju.setMsg("用户名存在请重新输入");
			return cju;
		} 
		//注册 添加
		service.addUser(user);
		cju.setMsg("true");
		return cju;
	}
	
	//跳转到注册页面
	@GetMapping("reg")
	public String toReg(HttpSession session) {
		return "index/reg";
	}
	
	//注销
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("user");
		return "redirect:/index";
	}
	
	//登錄
	@ResponseBody
	@PostMapping("login")
	public Object login(User user,HttpSession session) {
		CMSJsonUtil cju = new CMSJsonUtil();
		boolean uname = StringUtil.isNotEmpty(user.getUsername());
		boolean upwd = StringUtil.isNotEmpty(user.getPassword());
		if(!uname||!upwd) {
			cju.setMsg("用戶名密碼不能爲空");
			return cju;
		}
		User u=service.login(user);
		if(u==null) {
			cju.setMsg("用戶名不存在");
			return cju;
		}
		if(u.getLocked()==1) {
			cju.setMsg("該用戶被禁用請聯繫管理員");
			return cju;
		}
		if(!user.getPassword().equals(u.getPassword())) {
			cju.setMsg("密碼錯誤");
			return cju;
		}
		session.setAttribute("user", u);
		cju.setMsg("true");
		return cju;
	}
	
	@RequestMapping("selects")
	public String getUserList(Model m,String username,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "3") Integer pageSize) {
		PageInfo<User> info = service.getUserList(username, pageNum,pageSize);
		m.addAttribute("list", info.getList());
		m.addAttribute("username", username);
		m.addAttribute("info", info);
		return "admin/user";
	}
	
	@ResponseBody
	@RequestMapping("update")
	public boolean updateLocked(User user) {
		int locked = service.updateLocked(user);
		if(locked>0) {
			return true;
		}else {
			return false;
		}
	}
	//评论
	@RequestMapping("advice")
	public String advice(Model model,Comment comment,HttpSession session) {
		if(session.getAttribute("user")!=null) {
			int result=service.advice(comment);
			if(result>0) {
				return "redirect:/indexs/select?id="+comment.getArticle().getId();
			}
		}else {
			model.addAttribute("msg", "请先登录");
			return "index/login";
		}
		return "index/login";
	}
}
