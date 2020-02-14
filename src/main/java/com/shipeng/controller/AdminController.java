package com.shipeng.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bw.utils.StringUtil;
import com.github.pagehelper.PageInfo;
import com.shipeng.bean.Article;
import com.shipeng.bean.Category;
import com.shipeng.bean.Channel;
import com.shipeng.bean.Slide;
import com.shipeng.bean.User;
import com.shipeng.service.ArticleService;
import com.shipeng.service.CategoryService;
import com.shipeng.service.ChannelService;
import com.shipeng.service.SlideService;
import com.shipeng.service.UserService;
import com.shipeng.utils.CMSJsonUtil;
@Controller
public class AdminController {
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private ChannelService channelService;
	
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SlideService slideService;
	@Autowired
	private UserService userService;
	//首页的入口
	@RequestMapping("index")
	public String index(Model m,Article article,@RequestParam(defaultValue = "1")Integer pageNum,@RequestParam(defaultValue = "5")Integer pageSize) {
		//查询所有的栏目
		List<Channel> channelList= channelService.selects();
		m.addAttribute("channelList", channelList);
		if(article.getChannel_id()!=null) {
			List<Category> cates = categoryService.selects(article.getChannel_id());
			m.addAttribute("cates", cates);
			PageInfo<Article> info = articleService.selectByAdmin(article, pageNum, pageSize);
			m.addAttribute("info", info);
		}else {
			//查询所有的广告作为轮播图
			List<Slide> slideList=slideService.selects();
			m.addAttribute("slideList", slideList);	
			//查询所有的热门文章
			article.setHot(1);
			PageInfo<Article> info = articleService.selectByAdmin(article, pageNum, pageSize);
			m.addAttribute("articleList", info.getList());
		}
		m.addAttribute("article", article);
		PageInfo<Article> info2 = articleService.selectByAdmin(article, pageNum, 10);
		m.addAttribute("newArcitles", info2.getList());
		return "index/index";
	}
	
	//后台管理的人口
	@RequestMapping("admin")
	public String admin() {
		return "admin/index";
	}
	
	//个人中心的入口
	@RequestMapping("my")
	public String admin(Model m,@RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "3") Integer pageSize,Article article) {
		PageInfo<Article> info = articleService.selectByAdmin(article,pageNum,pageSize);
		m.addAttribute("list", info.getList());
		m.addAttribute("article", new Article());
		m.addAttribute("info", info);
		m.addAttribute("article", article);
		return "my/index";
	}
	
	@RequestMapping("admin/login")
	public String toLogin() {
		return "admin/login";
	}

	
	//登錄
	@ResponseBody
	@RequestMapping("login")
	public Object login(User user,HttpSession session) {
		CMSJsonUtil cju = new CMSJsonUtil();
		boolean uname = StringUtil.isNotEmpty(user.getUsername());
		boolean upwd = StringUtil.isNotEmpty(user.getPassword());
		if(!uname||!upwd) {
			cju.setMsg("用戶名密碼不能爲空");
			return cju;
		}
		User u=userService.login(user);
		if(u==null) {
			cju.setMsg("用戶名不存在");
			return cju;
		}
		//验证是否是管理员
		if(!u.getRole().equals("1")) {
			cju.setMsg("请输入管理员帐户");
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
}
