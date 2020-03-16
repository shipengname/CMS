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
	/**
	 * 
	    * @Title: 查看收藏文章
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param m
	    * @param @param pageNum
	    * @param @param session
	    * @param @return    参数
	    * @return String    返回类型
	    * @throws
	 */
	@RequestMapping("selects")
	public String selects(Model m,@RequestParam(defaultValue = "1")int pageNum,HttpSession session) {
		//从session作用域中提取user
		User user = (User) session.getAttribute("user");
		//判断是否为空
		if(user!=null) {
			//如果当前有用户登录那么查询收藏的文章
			PageInfo info=service.selects(pageNum,5,user);
			m.addAttribute("info", info);
			return "my/favo";			
		}else {
			//否则调转到登录页面
			return "index/login";
		}
	}
	/**
	 * 
	    * @Title:删除收藏的文章
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param id
	    * @param @return    参数
	    * @return boolean    返回类型
	    * @throws
	 */
	//运用ajax方法
	@ResponseBody
	@RequestMapping("delete")
	public boolean delete(String id) {
		int i = service.delete(id);
		if(i>0) {
			return true;
		}
		return false;
	}
	/**
	 * 
	    * @Title: 收藏文章
	    * @Description: TODO(这里用一句话描述这个方法的作用)
	    * @param @param favorite
	    * @param @param session
	    * @param @return    参数
	    * @return int    返回类型
	    * @throws
	 */
	@ResponseBody
	@RequestMapping("addFavo")
	public int addFavo(Favorite favorite,HttpSession session) {
		//从session作用域中提取user
		User user = (User) session.getAttribute("user");
		//如果不为空
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
