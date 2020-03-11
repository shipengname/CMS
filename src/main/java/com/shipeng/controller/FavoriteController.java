package com.shipeng.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Favorite;
import com.shipeng.bean.User;
import com.shipeng.exception.CMSRuntimeException;
import com.shipeng.service.FavoriteService;

@Controller
@RequestMapping("favorite")
public class FavoriteController {
	
	@Autowired
	private FavoriteService service;
	
	@RequestMapping("selects")
	public String selects(Model m,@RequestParam(defaultValue = "1")int pageNum,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user!=null) {
			PageInfo info=service.selects(pageNum,5,user);
			m.addAttribute("info", info);
			return "my/favo";			
		}else {
			return "index/login";
		}
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public boolean delete(String id) {
		int i = service.delete(id);
		if(i>0) {
			return true;
		}
		return false;
	}
	@ResponseBody
	@RequestMapping("addFavo")
	public int addFavo(Favorite favorite,HttpSession session) {
		User user = (User) session.getAttribute("user");
		if(user!=null) {
			favorite.setUser(user);	
		}
		int i=0;
		try {
			i=service.addFavo(favorite);
		} catch (CMSRuntimeException e) {
			i=-1;
			e.getMessage();
			e.printStackTrace();
		}
		return i;
	}
}
